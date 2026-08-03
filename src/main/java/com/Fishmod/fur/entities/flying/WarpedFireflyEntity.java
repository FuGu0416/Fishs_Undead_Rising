package com.Fishmod.fur.entities.flying;

import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.data.providers.FURItemTagsProvider;
import com.Fishmod.fur.init.FURBlockRegistry;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

/**
 * Warped Firefly — a passive, leash-able cave flyer ported from MC 1.16.5.
 *
 * <p>1.20.1 changes vs. the original:
 * <ul>
 *   <li>Rendered through GeckoLib instead of the hand-coded {@code WarpedFireflyModel}.</li>
 *   <li>The bespoke {@code WanderGoal} was dropped in favour of the shared
 *       {@link FlyingMobEntity.AIRandomFly} (same as the Ghost Ray port).</li>
 *   <li>Movement now uses the base {@code FlyingMoveHelper} instead of a vanilla
 *       {@code FlyingMovementController}.</li>
 * </ul>
 *
 * <p>Gameplay is preserved: feeding it an item from {@link FURItemTagsProvider#WARPED_FIREFLY_FOOD}
 * (Glowstone Dust, Warped Fungus) spawns a one-shot light orb - a {@link FURBlockRegistry#GLOWING_AIR}
 * block - at its position; feeding is on a 30s cooldown, independent of whether an earlier orb is
 * still glowing. The orb's entire 60s lifetime (hold, fade, self-removal) is owned by
 * {@code GlowingAirBlock} itself, not tracked by this entity. It flees Enigmoths and is tempted by
 * Warped Fungus.
 */
public class WarpedFireflyEntity extends FlyingMobEntity implements GeoEntity {
	private static final int FEED_COOLDOWN_TICKS = 30 * 20;

	private int feedCooldown = 0;

	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("warpedfirefly.model.idle");
	private static final RawAnimation FLY = RawAnimation.begin().thenLoop("warpedfirefly.model.fly");
	private static final RawAnimation INTERACT = RawAnimation.begin().thenPlay("warpedfirefly.model.interact");

	public WarpedFireflyEntity(EntityType<? extends WarpedFireflyEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, EnigmothEntity.class, 6.0F, 1.0D, 1.2D));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(Items.WARPED_FUNGUS, Items.WARPED_FUNGUS_ON_A_STICK), false));
		this.goalSelector.addGoal(8, new FlyingMobEntity.AIRandomFly(this, 1.0D));
	}

	public static AttributeSupplier.Builder createAttributes() {
		// Static defaults only (config can't be read at registration — it crashes datagen).
		// The real WarpedFirefly_Health is applied in finalizeSpawn().
		return Mob.createMobAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.6D)
				.add(Attributes.MAX_HEALTH, 10.0D)
				.add(Attributes.ATTACK_DAMAGE, 0.0D)
				.add(Attributes.FLYING_SPEED, 0.6D);
	}

	@Override
	public boolean removeWhenFarAway(double distance) {
		return !this.isLeashed();
	}

	@Override
	protected boolean shouldDespawnInPeaceful() {
		return false;
	}

	@Override
	public boolean canBeLeashed(Player player) {
		return !this.isLeashed();
	}

	@Override
	public void setLeashedTo(Entity leashHolder, boolean sendPacket) {
		this.setPersistenceRequired();
		super.setLeashedTo(leashHolder, sendPacket);
	}

	@Override
	public void tick() {
		super.tick();

		if (!this.level().isClientSide() && this.feedCooldown > 0) {
			--this.feedCooldown;
		}
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		InteractionResult actionresulttype = super.mobInteract(player, hand);

		if (this.feedCooldown <= 0 && itemstack.is(FURItemTagsProvider.WARPED_FIREFLY_FOOD)) {
			this.feedCooldown = FEED_COOLDOWN_TICKS;
			this.setPersistenceRequired();

			if (this.level().getBlockState(this.blockPosition()).isAir()) {
				this.level().setBlock(this.blockPosition(), FURBlockRegistry.GLOWING_AIR.get().defaultBlockState(), 3);
			}

			if (!player.getAbilities().instabuild) {
				itemstack.shrink(1);
			}

			this.playSound(SoundEvents.BEE_LOOP, 1.0F, 1.0F);
			this.level().broadcastEntityEvent(this, (byte) 7);
			if (player instanceof ServerPlayer serverPlayer) {
				CriteriaTriggers.SUMMONED_ENTITY.trigger(serverPlayer, this);
			}

			return InteractionResult.SUCCESS;
		}

		return actionresulttype;
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
		return dimensions.height * 0.5F;
	}

	@Override
	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData entityLivingData, @Nullable CompoundTag tag) {
		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.WarpedFirefly_Health.get());
		this.setHealth(this.getMaxHealth());

		return super.finalizeSpawn(worldIn, difficulty, reason, entityLivingData, tag);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.feedCooldown = compound.getInt("feedCooldown");
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("feedCooldown", this.feedCooldown);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return null;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.BEE_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.BEE_DEATH;
	}

	@Override
	public MobType getMobType() {
		return MobType.ARTHROPOD;
	}

	/**
	 * Handler for {@link Level#setEntityState}
	 */
	@Override
	@OnlyIn(Dist.CLIENT)
	public void handleEntityEvent(byte id) {
		if (id == 7) {
			this.triggerAnim("trigger_controller", "interact");
		} else {
			super.handleEntityEvent(id);
		}
	}

	// ── GeckoLib ──────────────────────────────────────────────────────────────

	private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
		if (this.onGround()) {
			state.getController().setAnimation(IDLE);
		} else {
			state.getController().setAnimation(FLY);
		}
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "controller", 5, this::predicate));
		controllers.add(new AnimationController<>(this, "trigger_controller", 5, state -> PlayState.STOP)
				.triggerableAnim("interact", INTERACT));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
