package com.Fishmod.fur.entities.tameable;

import java.util.UUID;

import javax.annotation.Nullable;

import com.Fishmod.fur.entities.ICharging;
import com.Fishmod.fur.entities.ai.EntityChargeAttackGoal;
import com.Fishmod.fur.entities.ai.FloatingMoveControl;
import com.Fishmod.fur.entities.ai.FloatingMoveRandomGoal;
import com.Fishmod.fur.entities.ai.FlyerFollowOwnerGoal;
import com.Fishmod.fur.entities.misc.SpectralCutlassItemEntity;
import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURItemRegistry;
import com.Fishmod.fur.item.SpectralCutlassItem;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
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
 * A flying phantom blade summoned by the {@link SpectralCutlassItem}. Fights alongside its owner for
 * {@link #lifeTicks} ticks, carrying the real dagger {@link ItemStack} in its main hand so that
 * enchantments (Sharpness/Smite/Fire Aspect/Knockback/Looting) and item damage apply automatically
 * through {@link #doHurtTarget(Entity)}.
 *
 * <h3>Core invariant: the carried ItemStack must NEVER be lost.</h3>
 * Exactly one of two outcomes fires, guarded by {@link #itemReturned}:
 * <ul>
 *   <li><b>Killed by an attack</b> (a {@link LivingEntity} is the damage source's entity — melee,
 *       arrows, creeper explosions): the dagger drops at the death location as a floating, glowing
 *       {@link SpectralCutlassItemEntity}.</li>
 *   <li><b>Everything else</b> (lifetime expiry, environmental death, /kill, void, owner
 *       logout/death/dimension change, any destroying {@code remove()}): the dagger returns to the
 *       owner's inventory, or drops at the owner's position if that isn't possible.</li>
 * </ul>
 */
public class SpectralCutlassEntity extends FURTameableEntity implements ICharging, GeoEntity {
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("spectral_cutlass.model.idle");
	private static final RawAnimation DASH = RawAnimation.begin().thenPlay("spectral_cutlass.model.dash");
	private static final RawAnimation ATTACK = RawAnimation.begin().thenPlay("spectral_cutlass.model.attack");

	/** ~4 seconds: the client-side expiry pulse window — the blade pulses translucent over its last {@value} ticks. */
	public static final int EXPIRY_WARNING_TICKS = 80;

	/**
	 * Remaining lifetime, mirrored to the client so {@code SpectralCutlassRenderer} can pulse the blade's
	 * transparency across the final {@link #EXPIRY_WARNING_TICKS}. The server-authoritative counter is the
	 * {@link #lifeTicks} field; this synced value is only pushed once it enters the warning window, so there
	 * is no per-tick packet traffic for the (much longer) rest of the summon.
	 */
	private static final EntityDataAccessor<Integer> DATA_LIFE_TICKS =
			SynchedEntityData.defineId(SpectralCutlassEntity.class, EntityDataSerializers.INT);

	public boolean isCharging = false;
	private int lifeTicks = SpectralCutlassItem.SUMMON_DURATION;
	private int attackTimer = 0;
	/** Guards the return/drop so it fires exactly once across die()/remove()/expiry paths. */
	private boolean itemReturned = false;
	/**
	 * Locked in at summon time from the summoner's creative flag. When true the blade neither drops nor
	 * returns its carried copy on death/expiry/removal — the creative player kept the original, so there
	 * is nothing to give back (vanilla creative convention, e.g. the Loyalty trident). Persisted in NBT
	 * so a mid-summon gamemode switch (or the owner being offline at cleanup) cannot change the outcome.
	 */
	private boolean fromCreative = false;

	public SpectralCutlassEntity(EntityType<? extends SpectralCutlassEntity> type, Level level) {
		super(type, level);
		this.moveControl = new FloatingMoveControl(this);
		this.setNoGravity(true);
		this.setPersistenceRequired();
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_LIFE_TICKS, SpectralCutlassItem.SUMMON_DURATION);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.32D)
				.add(Attributes.FOLLOW_RANGE, 24.0D)
				.add(Attributes.MAX_HEALTH, 16.0D)
				.add(Attributes.ATTACK_DAMAGE, 1.0D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
	}

	/** Each level of Unbreaking on the summoning item adds this fraction of base max-health (MULTIPLY_BASE). */
	private static final double HEALTH_PER_UNBREAKING_LEVEL = 0.5D;
	private static final UUID UNBREAKING_HEALTH_UUID = UUID.fromString("A1E6C3D2-9B4F-4E7A-8C21-3F5D7B9E0A11");

	/**
	 * Scales the blade's max health proportionally with the summoning item's Unbreaking (耐久) level:
	 * +{@value #HEALTH_PER_UNBREAKING_LEVEL} of base health per level via a MULTIPLY_BASE modifier. Applied
	 * once at summon; the permanent modifier persists across reloads. Callers should
	 * {@code setHealth(getMaxHealth())} afterwards to fill the widened bar.
	 */
	public void applyUnbreakingHealthBonus(int level) {
		AttributeInstance maxHealth = this.getAttribute(Attributes.MAX_HEALTH);
		if (maxHealth == null) {
			return;
		}
		if (maxHealth.getModifier(UNBREAKING_HEALTH_UUID) != null) {
			maxHealth.removeModifier(UNBREAKING_HEALTH_UUID);
		}
		if (level > 0) {
			maxHealth.addPermanentModifier(new AttributeModifier(UNBREAKING_HEALTH_UUID, "Unbreaking health bonus",
					HEALTH_PER_UNBREAKING_LEVEL * level, AttributeModifier.Operation.MULTIPLY_BASE));
		}
	}

	@Override
	protected void registerGoals() {
		super.registerGoals(); // installs the base wander goal (FloatingMoveRandomGoal, see below)
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(3, new EntityChargeAttackGoal(this));
		this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.2D, true));
		this.goalSelector.addGoal(5, new FlyerFollowOwnerGoal(this, 1.0D, 10.0F, 4.0F, true, 24.0D));
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.applyEntityAI();
	}

	protected void applyEntityAI() {
		// Owner-assist targeting only. wantsToAttack (from FURTameableEntity) already keeps the blade
		// from striking the owner's other tamed mobs; no extra exclusion list (Creepers are fair game).
		this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
		this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
	}

	@Override
	protected net.minecraft.world.entity.ai.goal.Goal wanderGoal() {
		return new FloatingMoveRandomGoal(this);
	}

	@Override
	protected PathNavigation createNavigation(Level level) {
		FlyingPathNavigation nav = new FlyingPathNavigation(this, level) {
			@Override
			public boolean isStableDestination(BlockPos pos) {
				return !this.level.getBlockState(pos.below()).isAir();
			}
		};
		nav.setCanOpenDoors(false);
		nav.setCanFloat(true);
		nav.setCanPassDoors(true);
		return nav;
	}

	@Override
	public void move(MoverType type, Vec3 pos) {
		super.move(type, pos);
		this.checkInsideBlocks();
	}

	// ------------------------------------------------------------------------------------------
	// Summon wiring
	// ------------------------------------------------------------------------------------------

	/** Give the blade the real dagger stack and make sure vanilla equipment-drop can never fire it. */
	public void setCarriedStack(ItemStack stack) {
		this.setItemSlot(EquipmentSlot.MAINHAND, stack);
		this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
	}

	public void setLifeTicks(int ticks) {
		this.lifeTicks = ticks;
		this.entityData.set(DATA_LIFE_TICKS, ticks);
	}

	/** Locks in whether this summon came from a creative player (no return/drop, no durability spend). */
	public void setFromCreative(boolean value) {
		this.fromCreative = value;
	}

	/** Remaining lifetime in ticks. Reads the synced value so it is valid on both sides (the client uses it for the expiry pulse). */
	public int getLifeTicks() {
		return this.entityData.get(DATA_LIFE_TICKS);
	}

	// ------------------------------------------------------------------------------------------
	// Combat: durability drain + player-loot attribution
	// ------------------------------------------------------------------------------------------

	@Override
	public boolean doHurtTarget(Entity target) {
		if (this.attackTimer > 0) {
			return false;
		}
		this.attackTimer = 20; // ~1 hit / second

		// Attribute kills/hits to the owner so player-only loot conditions (e.g. Looting) can apply.
		if (target instanceof LivingEntity living && this.getOwner() instanceof Player owner) {
			living.setLastHurtByPlayer(owner);
		}

		boolean flag = super.doHurtTarget(target);

		if (flag && !this.level().isClientSide()) {
			this.level().broadcastEntityEvent(this, (byte) 40); // play the attack animation on clients
			this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 1.0F, 1.0F);
			ItemStack held = this.getMainHandItem();
			if (!held.isEmpty() && held.isDamageableItem()) {
				// ORDER MATTERS: check BEFORE draining. If this hit would reach the never-break
				// threshold, clamp to maxDamage-1 and dissipate (item-RETURN path) instead of letting
				// the drain destroy the stack.
				if (held.getDamageValue() + 1 >= held.getMaxDamage() - 1) {
					held.setDamageValue(held.getMaxDamage() - 1);
					this.dissipate();
				} else {
					LivingEntity owner = this.getOwner();
					held.hurtAndBreak(1, owner != null ? owner : this, e -> {});
				}
			}
		}

		return flag;
	}

	/** Trigger the return-path dissipation exactly once. */
	private void dissipate() {
		if (!this.itemReturned) {
			this.discard(); // -> remove(DISCARDED) -> returnItemToOwner()
		}
	}

	// ------------------------------------------------------------------------------------------
	// Lifecycle
	// ------------------------------------------------------------------------------------------

	@Override
	public void tick() {
		super.tick();

		if (this.attackTimer > 0) {
			this.attackTimer--;
		}

		if (this.level().isClientSide()) {
			return;
		}

		this.setNoGravity(true);

		// Stay glued to the attack target's eye line for the whole engagement (not just the charge dash):
		// each tick, smoothly track the target's eye Y whenever it has a living target. The blade is
		// gravity-free and AI-driven, so this owns the vertical placement while XZ stays with the goals.
		LivingEntity heightTarget = this.getTarget();
		if (heightTarget != null && heightTarget.isAlive()) {
			double desiredY = heightTarget.getEyeY();
			double newY = this.getY() + (desiredY - this.getY()) * 0.3D;
			this.setPos(this.getX(), newY, this.getZ());
			Vec3 v = this.getDeltaMovement();
			this.setDeltaMovement(v.x, v.y * 0.6D, v.z); // damp vertical drift so it settles at eye line

			// Deal damage on real hitbox overlap. The goal-based range checks (EntityChargeAttackGoal /
			// MeleeAttackGoal) compare feet-position distance, which stays large because the blade hovers
			// at the target's eye line ~1.7 blocks above its feet — so they never fire and the two just
			// push. doHurtTarget self-gates on attackTimer, so this can't double-hit.
			if (this.getBoundingBox().inflate(0.2D).intersects(heightTarget.getBoundingBox())) {
				this.doHurtTarget(heightTarget);
			}
		}

		if (this.itemReturned) {
			return;
		}

		// Owner logged out (offline -> null) or changed dimension (resolved but in another Level).
		Player owner = this.resolveOnlineOwner();
		if (owner == null || owner.level() != this.level()) {
			this.discard(); // return path
			return;
		}

		this.lifeTicks--;
		if (this.lifeTicks <= EXPIRY_WARNING_TICKS) {
			this.entityData.set(DATA_LIFE_TICKS, this.lifeTicks); // sync only inside the pulse window
		}
		if (this.lifeTicks <= 0) {
			this.discard(); // expiry -> return path
		}
	}

	@Override
	public void die(DamageSource cause) {
		if (!this.level().isClientSide() && !this.itemReturned) {
			if (cause.getEntity() instanceof LivingEntity) {
				this.dropItemAtDeath();
			} else {
				this.returnItemToOwner();
			}
			this.itemReturned = true;
		}
		super.die(cause);
	}

	@Override
	public void remove(RemovalReason reason) {
		if (!this.level().isClientSide() && !this.itemReturned && reason.shouldDestroy()) {
			this.returnItemToOwner();
			this.itemReturned = true;
		}
		super.remove(reason);
	}

	private void returnItemToOwner() {
		ItemStack stack = this.getMainHandItem();
		this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
		if (stack.isEmpty()) {
			return;
		}

		Player owner = this.resolveOnlineOwner();
		if (owner != null) {
			// Creative kept its item, so only skip giving one back — the chime + return particles still play.
			if (!this.fromCreative) {
				boolean delivered = owner.isAlive() && owner.getInventory().add(stack);
				if (!delivered) {
					Vec3 p = owner.position();
					this.spawnFloatingDagger(owner.level(), stack, p.x, p.y + 0.5D, p.z);
				}
			}
			owner.level().playSound(null, owner.getX(), owner.getY(), owner.getZ(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 0.8F, 0.6F);
			this.spawnReturnParticles(owner);
		} else {
			// Owner offline/absent: drop where the blade is, so the item is never lost (survival only).
			if (!this.fromCreative) {
				this.spawnFloatingDagger(this.level(), stack, this.getX(), this.getY(), this.getZ());
			}
			this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 0.8F, 0.6F);
		}
	}

	private void dropItemAtDeath() {
		ItemStack stack = this.getMainHandItem();
		this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
		if (stack.isEmpty()) {
			return;
		}

		// Creative drops nothing on death, but the break SFX + soul burst still play.
		if (!this.fromCreative) {
			this.spawnFloatingDagger(this.level(), stack, this.getX(), this.getY(), this.getZ());
		}
		this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 0.9F, 0.8F);
		this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.VEX_DEATH, SoundSource.PLAYERS, 0.9F, 1.2F);
		if (this.level() instanceof ServerLevel sl) {
			sl.sendParticles(ParticleTypes.SOUL, this.getX(), this.getY() + 0.3D, this.getZ(), 20, 0.25D, 0.35D, 0.25D, 0.02D);
		}
	}

	private void spawnFloatingDagger(Level level, ItemStack stack, double x, double y, double z) {
		if (level.isClientSide()) {
			return;
		}
		SpectralCutlassItemEntity item = new SpectralCutlassItemEntity(FUREntityRegistry.SPECTRAL_CUTLASS_ITEM.get(), level);
		item.setPos(x, y, z);
		item.setItem(stack);
		item.setDeltaMovement(0.0D, 0.1D, 0.0D);
		item.setPickUpDelay(20);
		level.addFreshEntity(item);
	}

	private void spawnReturnParticles(Player owner) {
		if (!(this.level() instanceof ServerLevel sl)) {
			return;
		}
		Vec3 from = this.position().add(0.0D, this.getBbHeight() * 0.5D, 0.0D);
		Vec3 dir = owner.position().add(0.0D, owner.getBbHeight() * 0.5D, 0.0D).subtract(from);
		double len = dir.length();
		if (len < 1.0E-4D) {
			return;
		}
		dir = dir.normalize();
		for (int i = 0; i < 12; i++) {
			double dist = this.random.nextDouble() * Math.min(len, 6.0D);
			sl.sendParticles(ParticleTypes.SOUL, from.x + dir.x * dist, from.y + dir.y * dist, from.z + dir.z * dist, 1, 0.05D, 0.05D, 0.05D, 0.01D);
		}
	}

	@Nullable
	private Player resolveOnlineOwner() {
		UUID id = this.getOwnerUUID();
		if (id == null || this.level().getServer() == null) {
			return null;
		}
		return this.level().getServer().getPlayerList().getPlayer(id);
	}

	// ------------------------------------------------------------------------------------------
	// NBT — carried stack persists via Mob's HandItems; we persist lifeTicks + the return guard.
	// ------------------------------------------------------------------------------------------

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("LifeTicks", this.lifeTicks);
		tag.putBoolean("ItemReturned", this.itemReturned);
		tag.putBoolean("FromCreative", this.fromCreative);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.lifeTicks = tag.contains("LifeTicks") ? tag.getInt("LifeTicks") : SpectralCutlassItem.SUMMON_DURATION;
		this.entityData.set(DATA_LIFE_TICKS, this.lifeTicks);
		this.itemReturned = tag.getBoolean("ItemReturned");
		this.fromCreative = tag.getBoolean("FromCreative");
		this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
	}

	// ------------------------------------------------------------------------------------------
	// Disabled tameable behaviours + flight/immunity tweaks
	// ------------------------------------------------------------------------------------------

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		return InteractionResult.PASS; // no sit toggle, no feeding
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return false;
	}

	@Override
	@Nullable
	public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mate) {
		return null;
	}

	@Override
	public boolean canBeLeashed(Player player) {
		return false;
	}

	@Override
	protected boolean isCommandable() {
		return false;
	}

	@Override
	public boolean isSummonedMinion() {
		return true;
	}

	@Override
	public boolean removeWhenFarAway(double distance) {
		return false;
	}

	@Override
	public boolean isInvulnerableTo(DamageSource source) {
		if (source.is(DamageTypes.IN_WALL) || source.is(DamageTypes.CRAMMING)) {
			return true;
		}
		return super.isInvulnerableTo(source);
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.is(DamageTypeTags.IS_FALL)) {
			return false;
		}
		return super.hurt(source, amount);
	}

	@Override
	public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
		return false;
	}

	@Override
	protected void checkFallDamage(double y, boolean onGround, net.minecraft.world.level.block.state.BlockState state, BlockPos pos) {
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
		return dimensions.height * 0.5F;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return null;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return null;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return null;
	}

	@Override
	protected void playStepSound(BlockPos pos, net.minecraft.world.level.block.state.BlockState state) {
	}

	// ------------------------------------------------------------------------------------------
	// ICharging + GeckoLib (fully complete — see the Spectral Cutlass entry in PLACEHOLDERS.md "Resolved")
	// ------------------------------------------------------------------------------------------

	@Override
	public boolean isCharging() {
		return this.isCharging;
	}

	@Override
	public void setIsCharging(boolean bool) {
		this.isCharging = bool;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void handleEntityEvent(byte id) {
		if (id == 6) {
			this.isCharging = true;
		} else if (id == 40) {
			this.triggerAnim("trigger_controller", "attack");
		} else {
			super.handleEntityEvent(id);
		}
	}

	private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
		state.getController().setAnimation(this.isCharging ? DASH : IDLE);
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "controller", 5, this::predicate));
		controllers.add(new AnimationController<>(this, "trigger_controller", 5, state -> PlayState.STOP).triggerableAnim("attack", ATTACK));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	/** Convenience for the (placeholder) renderer to reference the item's own model. */
	public ItemStack getDisplayStack() {
		ItemStack held = this.getMainHandItem();
		return held.isEmpty() ? new ItemStack(FURItemRegistry.SPECTRAL_CUTLASS.get()) : held;
	}
}
