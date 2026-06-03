package com.Fishmod.fur.entities.flying;

import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.init.FUREffectRegistry;
import com.Fishmod.fur.init.FURItemRegistry;
import com.Fishmod.fur.init.FURSoundRegistry;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

/**
 * Ghost Ray — a passive, drifting flyer ported from MC 1.16.5.
 *
 * <p>1.20.1 changes vs. the original:
 * <ul>
 *   <li>End-only: the Overworld and Nether spawn branches (and the Soul-Sand-Valley skin)
 *       were removed; it now spawns only in the End (gated by the biome modifier).</li>
 *   <li>Attracted to a player holding Enigmoth Dust (謎影蛾鱗粉) via a {@link TemptGoal}.</li>
 *   <li>Immune to the Void Dust (虛空鱗粉) effect — see {@link #canBeAffected}.</li>
 *   <li>Rendered through GeckoLib instead of a hand-coded model.</li>
 * </ul>
 */
public class GhostRayEntity extends FlyingMobEntity implements GeoEntity {
	public static final float[] SIZE = {1.0F, 1.4F, 1.8F, 2.2F};
	/** Texture index. End-only now, so this is fixed to the End skin (2) at spawn, but kept for save compatibility. */
	private static final EntityDataAccessor<Integer> SKIN_TYPE = SynchedEntityData.defineId(GhostRayEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SIZE_VARIANT = SynchedEntityData.defineId(GhostRayEntity.class, EntityDataSerializers.INT);

	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("ghostray.idle");

	public GhostRayEntity(EntityType<? extends GhostRayEntity> p_i48549_1_, Level worldIn) {
		super(p_i48549_1_, worldIn);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.0D));
		// Item 8: drawn to a player holding Enigmoth Dust (謎影蛾鱗粉).
		this.goalSelector.addGoal(4, new TemptGoal(this, 1.1D, Ingredient.of(FURItemRegistry.ENIGMOTH_DUST.get()), false));
		this.goalSelector.addGoal(5, new FlyingMobEntity.AIRandomFly(this, 1.0D));
	}

	public static AttributeSupplier.Builder createAttributes() {
		// Static defaults only (config can't be read at registration — it crashes datagen).
		// The real GhostRay_Health is applied in finalizeSpawn().
		return Monster.createMobAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.03D)
				.add(Attributes.MAX_HEALTH, 20.0D)
				.add(Attributes.ATTACK_DAMAGE, 0.0D)
				.add(Attributes.FLYING_SPEED, 0.03D);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(SKIN_TYPE, Integer.valueOf(2));
		this.getEntityData().define(SIZE_VARIANT, Integer.valueOf(this.getRandom().nextInt(GhostRayEntity.SIZE.length)));
	}

	/** End-only spawn. The biome modifier restricts this to End biomes; here we only reject Peaceful + need a dark, valid spot. */
	public static boolean checkGhostRaySpawnRules(EntityType<? extends GhostRayEntity> type, ServerLevelAccessor world, MobSpawnType reason, BlockPos pos, RandomSource rand) {
		return FlyingMobEntity.checkFlyerSpawnRulesNoRestriction(type, world, reason, pos, rand);
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 1;
	}

	@Override
	public void tick() {
		this.noPhysics = true;
		super.tick();
		this.noPhysics = false;
		this.setNoGravity(true);
	}

	public float getScale() {
		return GhostRayEntity.SIZE[this.getSize()];
	}

	@Override
	protected float getStandingEyeHeight(Pose p_213348_1_, EntityDimensions p_213348_2_) {
		return p_213348_2_.height * 0.7F;
	}

	/** Lashing out at a Ghost Ray weakens the attacker. */
	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.getDirectEntity() instanceof LivingEntity attacker) {
			attacker.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 6 * 20, 2));
		}

		return super.hurt(source, amount);
	}

	/** Ghost Rays are immune to the Void Dust (虛空鱗粉) effect. */
	@Override
	public boolean canBeAffected(MobEffectInstance effect) {
		return effect.getEffect() != FUREffectRegistry.VOID_DUST.get() && super.canBeAffected(effect);
	}

	@Override
	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData entityLivingData, @Nullable CompoundTag p_213386_5_) {
		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.GhostRay_Health.get());
		this.setHealth(this.getMaxHealth());
		this.setSkin(2); // End variant only.

		return super.finalizeSpawn(worldIn, difficulty, reason, entityLivingData, p_213386_5_);
	}

	public int getSkin() {
		return this.getEntityData().get(SKIN_TYPE).intValue();
	}

	public void setSkin(int skinType) {
		this.getEntityData().set(SKIN_TYPE, Integer.valueOf(skinType));
	}

	public int getSize() {
		return this.getEntityData().get(SIZE_VARIANT).intValue();
	}

	public void setSize(int size) {
		this.getEntityData().set(SIZE_VARIANT, Integer.valueOf(size));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setSkin(compound.getInt("Variant"));
		this.setSize(compound.getInt("Size"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("Variant", getSkin());
		compound.putInt("Size", this.getSize());
	}

	@Override
	public int getAmbientSoundInterval() {
		return 1000;
	}

	@Override
	public SoundSource getSoundSource() {
		return SoundSource.NEUTRAL;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return FURSoundRegistry.GHOSTRAY_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return FURSoundRegistry.GHOSTRAY_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return FURSoundRegistry.GHOSTRAY_DEATH.get();
	}

	@Override
	protected float getSoundVolume() {
		return 0.5F;
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
