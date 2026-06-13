package com.Fishmod.fur.entities.flying;

import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.init.FURBlockRegistry;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
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
 * <p>Gameplay is preserved: feeding it Glowstone Dust (8 min) or a Warped Fungus
 * (3 min) makes it emit light by stamping a temporary {@code GLOWING_AIR} block at
 * its position; it flees Enigmoths and is tempted by Warped Fungus.
 */
public class WarpedFireflyEntity extends FlyingMobEntity implements GeoEntity {
	private static final EntityDataAccessor<BlockPos> GLOWING_POS = SynchedEntityData.defineId(WarpedFireflyEntity.class, EntityDataSerializers.BLOCK_POS);
	private int glowTimer = 0;

	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("warpedfirefly.idle");

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
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(GLOWING_POS, BlockPos.ZERO);
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

		if (this.glowTimer > -6) {
			--this.glowTimer;
		}

		if (!this.level().isClientSide() && this.glowTimer > -6) {
			if (this.level().getBlockState(this.blockPosition()).isAir()) {
				if (this.tickCount % 5 == 0 && this.level().getBlockState(this.getGlowingPos()).is(FURBlockRegistry.GLOWING_AIR.get())) {
					this.level().setBlock(this.getGlowingPos(), Blocks.AIR.defaultBlockState(), 3);
				}

				if (this.tickCount % 5 == 0 && this.glowTimer > 0) {
					this.level().setBlock(this.blockPosition(), FURBlockRegistry.GLOWING_AIR.get().defaultBlockState(), 3);
					this.setGlowingPos(this.blockPosition());
				}
			}
		}
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		Item item = itemstack.getItem();
		InteractionResult actionresulttype = super.mobInteract(player, hand);
		if (this.glowTimer == -6) {
			if (item.equals(Items.GLOWSTONE_DUST)) {
				this.glowTimer = 8 * 60 * 20 + 6;
				this.setPersistenceRequired();
				if (player instanceof ServerPlayer) {
					CriteriaTriggers.SUMMONED_ENTITY.trigger((ServerPlayer) player, this);
				}
			} else if (item.equals(Items.WARPED_FUNGUS)) {
				this.glowTimer = 3 * 60 * 20 + 6;
				this.setPersistenceRequired();
				if (player instanceof ServerPlayer) {
					CriteriaTriggers.SUMMONED_ENTITY.trigger((ServerPlayer) player, this);
				}
			} else {
				return actionresulttype;
			}

			if (!player.getAbilities().instabuild) {
				itemstack.shrink(1);
			}

			this.playSound(SoundEvents.BEE_LOOP, 1.0F, 1.0F);

			return InteractionResult.SUCCESS;
		} else {
			return actionresulttype;
		}
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

	public BlockPos getGlowingPos() {
		return this.getEntityData().get(GLOWING_POS);
	}

	public void setGlowingPos(BlockPos pos) {
		this.getEntityData().set(GLOWING_POS, pos);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.glowTimer = compound.getInt("glowTimer");
		this.setGlowingPos(new BlockPos(compound.getInt("glowPosX"), compound.getInt("glowPosY"), compound.getInt("glowPosZ")));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("glowPosX", this.getGlowingPos().getX());
		compound.putInt("glowPosY", this.getGlowingPos().getY());
		compound.putInt("glowPosZ", this.getGlowingPos().getZ());
		compound.putInt("glowTimer", this.glowTimer);
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

	// ── GeckoLib ──────────────────────────────────────────────────────────────

	private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
		state.getController().setAnimation(IDLE);
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "controller", 5, this::predicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
