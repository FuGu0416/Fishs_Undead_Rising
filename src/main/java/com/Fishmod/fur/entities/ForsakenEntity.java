package com.Fishmod.fur.entities;

import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.entities.ai.EntityChargeAttackGoal;
import com.Fishmod.fur.entities.projectiles.DeathCoilEntity;
import com.Fishmod.fur.init.FUREffectRegistry;
import com.Fishmod.fur.init.FURItemRegistry;
import com.Fishmod.fur.item.SkeletonKingCrownItem;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.entity.BannerPattern;
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
 * Forsaken — a shield-charging/ranged desert skeleton champion, ported from 1.16.5. Four skins pick a
 * different loadout: 0 = shield + iron sword (shield-bash charge), 1 = iron sword only, 2 (default) =
 * bow (inherits {@link AbstractSkeleton}'s own bow behaviour unmodified), 3 = Forsaken Staff (fires
 * {@link DeathCoilEntity}).
 *
 * <p>1.20.1 changes vs. the original:
 * <ul>
 *   <li>The charge-knockback {@link #push} override is rewritten on {@link CadavoarEntity}'s
 *       "only knock back if the hit actually landed" fix (see its javadoc) rather than the 1.16.5
 *       original's unconditional every-tick shove.</li>
 *   <li>The shield's custom banner pattern now uses vanilla's modern {@code BannerPattern.Builder}
 *       instead of 1.16.5's hand-rolled {@code createPatternTag} helper.</li>
 *   <li>Dropped 1.16.5's {@code hurt(DamageSource, float)} override — it only ever called
 *       {@code super.hurt(...)} unchanged, a no-op left over from some earlier revision.</li>
 * </ul>
 */
public class ForsakenEntity extends AbstractSkeleton implements ICharging, GeoEntity {
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

	// PLACEHOLDER geo/animation: a plain vanilla-biped-shaped GeckoLib mesh (standard Java Edition UV
	// layout on a 64x32 texture) with handle_l/handle_r locator bones added for holding sword/bow/
	// shield/staff, so the real vanilla skeleton.png texture maps onto it correctly. idle/walk/attack
	// clips are empty (no real keyframes yet). Tracked in PLACEHOLDERS.md.
	private static final RawAnimation IDLE = RawAnimation.begin().thenPlay("forsaken.model.idle");
	private static final RawAnimation WALK = RawAnimation.begin().thenPlay("forsaken.model.walk");
	private static final RawAnimation ATTACK = RawAnimation.begin().thenPlay("forsaken.model.attack");

	private static final EntityDataAccessor<Integer> SKIN_TYPE = SynchedEntityData.defineId(ForsakenEntity.class, EntityDataSerializers.INT);

	private final EntityChargeAttackGoal chargeGoal = new EntityChargeAttackGoal(this);
	private final RangedAttackGoal staffGoal = new RangedAttackGoal(this, 1.25D, 40, 20.0F) {
		@Override
		public void stop() {
			super.stop();
			ForsakenEntity.this.setAggressive(false);
		}

		@Override
		public void start() {
			super.start();
			ForsakenEntity.this.setAggressive(true);
		}
	};

	private boolean isCharging = false;

	public ForsakenEntity(EntityType<? extends ForsakenEntity> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SKIN_TYPE, this.random.nextInt(4));
	}

	// Hardcoded defaults (matching FURConfig.Forsaken_Health/Attack's own default values) rather than
	// reading the config directly - datagen (runData) invokes createAttributes() before config is
	// loaded and crashes on ConfigValue#get(), same fix already used by BeelzebubEntity/CadavoarEntity.
	// The config-driven value is applied afterwards in finalizeSpawn().
	public static AttributeSupplier.Builder createAttributes() {
		return AbstractSkeleton.createAttributes()
				.add(Attributes.MAX_HEALTH, 30.0D)
				.add(Attributes.ATTACK_DAMAGE, 4.0D);
	}

	/**
	 * While charging, flings anything it collides with along the way - see {@link EntityChargeAttackGoal}
	 * for the dedicated hit against the entity it's actually chasing. Only applies the extra knockback if
	 * the hit actually landed (same fix {@link CadavoarEntity#push} uses): {@code push()} has no cooldown
	 * of its own and fires every tick two bounding boxes overlap, so without this check the target's own
	 * hurt-resistance window (which already prevents the damage itself from stacking) would still let the
	 * knockback reapply every single tick.
	 */
	@Override
	public void push(Entity entityIn) {
		super.push(entityIn);

		if (this.isCharging() && !this.hasPassenger(entityIn) && !this.isAlliedTo(entityIn) && this.doHurtTarget(entityIn)) {
			entityIn.setDeltaMovement(entityIn.getDeltaMovement().add(this.getLookAngle().normalize().multiply(0.8D, 1.6D, 0.8D)));
		}
	}

	@Override
	protected boolean isSunBurnTick() {
		return false;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.SKELETON_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.SKELETON_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.SKELETON_DEATH;
	}

	@Override
	protected SoundEvent getStepSound() {
		return SoundEvents.SKELETON_STEP;
	}

	@Override
	protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
		super.dropCustomDeathLoot(source, looting, recentlyHit);
		Entity entity = source.getEntity();
		if (entity instanceof Creeper creeper && creeper.canDropMobsSkull()) {
			creeper.increaseDroppedSkulls();
			this.spawnAtLocation(Items.SKELETON_SKULL);
		}
	}

	/** Forsaken hands out its own equipment in {@link #finalizeSpawn} instead of the vanilla bow default. */
	@Override
	protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
	}

	@Override
	public void reassessWeaponGoal() {
		if (this.level() != null && !this.level().isClientSide) {
			this.goalSelector.removeGoal(this.staffGoal);
			this.goalSelector.removeGoal(this.chargeGoal);
			ItemStack itemstack = this.getItemBySlot(EquipmentSlot.MAINHAND);
			if (itemstack.getItem() == FURItemRegistry.FORSAKEN_STAFF.get()) {
				this.goalSelector.addGoal(4, this.staffGoal);
			} else {
				super.reassessWeaponGoal();
				if (this.getOffhandItem().getItem() == Items.SHIELD) {
					this.goalSelector.addGoal(2, this.chargeGoal);
				}
			}
		}
	}

	@Override
	public void performRangedAttack(LivingEntity target, float distanceFactor) {
		ItemStack itemstack = this.getItemBySlot(EquipmentSlot.MAINHAND);
		if (itemstack.getItem() == FURItemRegistry.FORSAKEN_STAFF.get()) {
			DeathCoilEntity deathcoil = new DeathCoilEntity(this.level(), this, 0.0D, 0.0D, 0.0D);
			deathcoil.setPos(
					this.getX() - (double) (this.getBbWidth() + 1.0F) * 0.5D * (double) Mth.sin(this.yBodyRot * ((float) Math.PI / 180F)),
					this.getEyeY() - 0.1D,
					this.getZ() + (double) (this.getBbWidth() + 1.0F) * 0.5D * (double) Mth.cos(this.yBodyRot * ((float) Math.PI / 180F)));
			double d0 = target.getX() - this.getX();
			double d1 = target.getY(0.3333333333333333D) - deathcoil.getY();
			double d2 = target.getZ() - this.getZ();
			float f = Mth.sqrt((float) (d0 * d0 + d2 * d2)) * 0.2F;
			deathcoil.shoot(d0, d1 + (double) f, d2, 1.5F, 10.0F);

			if (!this.isSilent()) {
				this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.BLAZE_SHOOT,
						this.getSoundSource(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
			}

			this.level().addFreshEntity(deathcoil);
		} else {
			super.performRangedAttack(target, distanceFactor);
		}
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag tag) {
		SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, spawnData, tag);

		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Forsaken_Health.get());
		this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Forsaken_Attack.get());
		this.setHealth(this.getMaxHealth());

		switch (this.getSkin()) {
			case 0 -> {
				ItemStack shield = new ItemStack(Items.SHIELD);
				ListTag patterns = new BannerPattern.Builder()
						.addPattern(FURItemRegistry.PATTERN_SKELETONKING.getKey(), DyeColor.WHITE)
						.toListTag();
				CompoundTag blockEntityTag = shield.getOrCreateTagElement("BlockEntityTag");
				blockEntityTag.put("Patterns", patterns);
				blockEntityTag.putInt("Base", DyeColor.BLACK.getId());

				this.setItemSlot(EquipmentSlot.OFFHAND, shield);
				this.getAttribute(Attributes.ARMOR).setBaseValue(4.0D);
				this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.4D);
				this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.22D);
				this.getOffhandItem().setDamageValue(this.getRandom().nextInt(this.getOffhandItem().getMaxDamage()));
				this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
			}
			case 1 -> this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
			case 3 -> this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(FURItemRegistry.FORSAKEN_STAFF.get()));
			default -> this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
		}

		this.getMainHandItem().setDamageValue(this.getRandom().nextInt(this.getMainHandItem().getMaxDamage()));
		this.populateDefaultEquipmentEnchantments(this.getRandom(), difficulty);
		this.reassessWeaponGoal();

		return data;
	}

	@Override
	public boolean doHurtTarget(Entity entityIn) {
		if (!super.doHurtTarget(entityIn)) {
			return false;
		} else {
			if (entityIn instanceof LivingEntity living) {
				living.addEffect(new MobEffectInstance(FUREffectRegistry.FRAGILE.get(), 200, 2));
			}

			return true;
		}
	}

	@Override
	public boolean isAlliedTo(Entity entity) {
		if (entity == null) {
			return false;
		} else if (entity == this) {
			return true;
		} else if (super.isAlliedTo(entity)) {
			return true;
		} else if (entity instanceof SkeletonKingEntity || entity instanceof ForsakenEntity) {
			return SkeletonKingCrownItem.getOwnerId(this) == null;
		} else {
			return false;
		}
	}

	@Override
	public boolean isCharging() {
		return this.isCharging;
	}

	@Override
	public void setIsCharging(boolean charging) {
		this.isCharging = charging;
	}

	@Override
	protected AbstractArrow getArrow(ItemStack stack, float velocity) {
		AbstractArrow abstractarrow = super.getArrow(stack, velocity);
		if (abstractarrow instanceof Arrow arrow) {
			arrow.addEffect(new MobEffectInstance(FUREffectRegistry.FRAGILE.get(), 200, 2));
		}

		return abstractarrow;
	}

	public int getSkin() {
		return this.entityData.get(SKIN_TYPE);
	}

	public void setSkin(int skinType) {
		this.entityData.set(SKIN_TYPE, skinType);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setSkin(compound.getInt("Variant"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("Variant", this.getSkin());
	}

	private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
		if (this.isAggressive()) {
			state.getController().setAnimation(ATTACK);
		} else if (state.isMoving() || !this.getNavigation().isDone()) {
			state.getController().setAnimation(WALK);
		} else {
			state.getController().setAnimation(IDLE);
		}

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
