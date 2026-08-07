package com.Fishmod.fur.entities.flying;

import javax.annotation.Nullable;

import com.Fishmod.fur.block.GlowingAirBlock;
import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.data.providers.FURItemTagsProvider;
import com.Fishmod.fur.entities.ai.FlareflyPollinateGoal;
import com.Fishmod.fur.init.FURBiomesRegistry;
import com.Fishmod.fur.init.FURBlockRegistry;
import com.Fishmod.fur.init.FUREffectRegistry;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
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
import net.minecraft.world.level.biome.Biomes;
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
 * Flarefly — a passive, leash-able cave flyer ported from MC 1.16.5.
 *
 * <p>1.20.1 changes vs. the original:
 * <ul>
 *   <li>Rendered through GeckoLib instead of the hand-coded {@code FlareflyModel}.</li>
 *   <li>The bespoke {@code WanderGoal} was dropped in favour of the shared
 *       {@link FlyingMobEntity.AIRandomFly} (same as the Ghost Ray port).</li>
 *   <li>Movement now uses the base {@code FlyingMoveHelper} instead of a vanilla
 *       {@code FlyingMovementController}.</li>
 * </ul>
 *
 * <p>Gameplay is preserved: feeding it an item from {@link FURItemTagsProvider#FLAREFLY_FOOD}
 * (Glowstone Dust, Warped Fungus) spawns a one-shot light orb - a {@link FURBlockRegistry#GLOWING_AIR}
 * block - at its position; feeding is on a {@link FURConfig#Flarefly_Feed_Cooldown} cooldown
 * (default 30s), independent of whether an earlier orb is still glowing. The orb's entire lifetime
 * (hold, fade, self-removal), controlled by {@link FURConfig#Flarefly_Light_Duration} (default 60s),
 * is owned by {@code GlowingAirBlock} itself, not tracked by this entity. It flees Enigmoths and is
 * tempted by Warped Fungus. Being struck by a non-player attacker triggers the same light-orb flash,
 * sharing feeding's cooldown (so a hit right after feeding, or a flurry of hits, only ever produces
 * one orb per cooldown window); the attacker is dazed with {@link FUREffectRegistry#FEAR} for 5s
 * (repeatedly clearing its attack target for the duration) only when that flash actually fires - a
 * hit landing while the cooldown is still up does neither.
 */
public class FlareflyEntity extends FlyingMobEntity implements GeoEntity {
	private static final int DAZZLE_FEAR_TICKS = 5 * 20;

	/** 0 = default, 1 = Lush Caves variant, 2 = Luminous Undergrove variant (see {@link #finalizeSpawn}). Drives texture selection only. */
	private static final EntityDataAccessor<Integer> SKIN_TYPE = SynchedEntityData.defineId(FlareflyEntity.class, EntityDataSerializers.INT);

	private int feedCooldown = 0;

	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("flarefly.model.idle");
	private static final RawAnimation FLY = RawAnimation.begin().thenLoop("flarefly.model.fly");
	private static final RawAnimation INTERACT = RawAnimation.begin().thenPlay("flarefly.model.interact");

	public FlareflyEntity(EntityType<? extends FlareflyEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SKIN_TYPE, Integer.valueOf(0));
	}

	public int getSkin() {
		return this.entityData.get(SKIN_TYPE).intValue();
	}

	public void setSkin(int skinType) {
		this.entityData.set(SKIN_TYPE, Integer.valueOf(skinType));
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, EnigmothEntity.class, 6.0F, 1.0D, 1.2D));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(Items.WARPED_FUNGUS, Items.WARPED_FUNGUS_ON_A_STICK), false));
		this.goalSelector.addGoal(5, new FlareflyPollinateGoal(this));
		this.goalSelector.addGoal(8, new FlyingMobEntity.AIRandomFly(this, 1.0D));
	}

	public static AttributeSupplier.Builder createAttributes() {
		// Static defaults only (config can't be read at registration — it crashes datagen).
		// The real Flarefly_Health is applied in finalizeSpawn().
		return Mob.createMobAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.6D)
				.add(Attributes.MAX_HEALTH, 10.0D)
				.add(Attributes.ATTACK_DAMAGE, 0.0D)
				.add(Attributes.FLYING_SPEED, 0.6D);
	}

	/**
	 * Luminous Undergrove is a naturally-lit cave biome — skip the darkness check there so Flarefly
	 * can still spawn in its home biome, same carve-out as {@code MycosisEntity.checkMycosisSpawnRules}.
	 * Everywhere else (Lush Caves, Warped Forest) falls back to the normal darkness-gated flyer rule,
	 * unchanged.
	 */
	public static boolean checkFlareflySpawnRules(EntityType<? extends FlareflyEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		if (level.getBiome(pos).is(FURBiomesRegistry.LUMINOUS_UNDERGROVE)) {
			return level.getDifficulty() != Difficulty.PEACEFUL;
		}
		return FlyingMobEntity.checkFlyerSpawnRulesNoRestriction(entityType, level, spawnType, pos, random);
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

		if (this.feedCooldown <= 0 && itemstack.is(FURItemTagsProvider.FLAREFLY_FOOD)) {
			this.feedCooldown = FURConfig.Flarefly_Feed_Cooldown.get() * 20;
			this.setPersistenceRequired();

			if (this.level().getBlockState(this.blockPosition()).isAir()) {
				GlowingAirBlock.spawn(this.level(), this.blockPosition());
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
	public boolean hurt(DamageSource source, float amount) {
		boolean hurt = super.hurt(source, amount);

		if (hurt && !this.level().isClientSide() && source.getEntity() instanceof Mob attacker
				&& this.feedCooldown <= 0 && this.level().getBlockState(this.blockPosition()).isAir()) {
			this.feedCooldown = FURConfig.Flarefly_Feed_Cooldown.get() * 20;
			GlowingAirBlock.spawn(this.level(), this.blockPosition());

			// Immediate daze, then FEAR keeps re-clearing the target every 10 ticks for the full
			// duration so the attacker can't just re-acquire us (or anything else) mid-flash. Only
			// fires alongside an actual orb flash - a hit that's on cooldown does neither.
			attacker.setTarget(null);
			attacker.addEffect(FUREffectRegistry.fear(DAZZLE_FEAR_TICKS, 0));
		}

		return hurt;
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
		return dimensions.height * 0.5F;
	}

	@Override
	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData entityLivingData, @Nullable CompoundTag tag) {
		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Flarefly_Health.get());
		this.setHealth(this.getMaxHealth());

		var biome = worldIn.getBiome(this.blockPosition());
		if (biome.is(FURBiomesRegistry.LUMINOUS_UNDERGROVE)) {
			this.setSkin(2);
		} else if (biome.is(Biomes.LUSH_CAVES)) {
			this.setSkin(1);
		} else {
			this.setSkin(0);
		}

		return super.finalizeSpawn(worldIn, difficulty, reason, entityLivingData, tag);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.feedCooldown = compound.getInt("feedCooldown");
		this.setSkin(compound.getInt("Variant"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("feedCooldown", this.feedCooldown);
		compound.putInt("Variant", this.getSkin());
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
