package com.Fishmod.fur.entities;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.data.providers.FURBlockTagsProvider;
import com.Fishmod.fur.data.providers.FURStructureTagsProvider;
import com.Fishmod.fur.mod_LavaCow;
import com.google.common.collect.Maps;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Marker;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
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
 * Grave Robber — a raid-capable illager that wields an iron shovel and barters,
 * ported from MC 1.16.5.
 *
 * <p>1.20.1 changes vs. the original:
 * <ul>
 *   <li>The 1.16.5 {@code die()} branch that spawned a {@code GraveRobberGhostEntity} was
 *       intentionally dropped — the ghost is not ported to 1.20.1.</li>
 *   <li>Vanilla goal renames: {@code SwimGoal}→{@code FloatGoal},
 *       {@code RaidOpenDoorGoal}→{@code RaiderOpenDoorGoal},
 *       {@code FindTargetGoal}→{@code HoldGroundAttackGoal},
 *       {@code RandomWalkingGoal}→{@code RandomStrollGoal}, {@code LookAtGoal}→{@code LookAtPlayerGoal}.</li>
 *   <li>Bartering uses the modern {@link LootParams} API.</li>
 * </ul>
 */
public class GraveRobberEntity extends AbstractIllager implements GeoEntity {
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

	// PLACEHOLDER: only "graverobber.model.idle" exists in graverobber.animation.json so far (a
	// scarf/shirt scale nudge to avoid z-fighting, no real keyframes yet) - the rest are referenced
	// here so the state machine is ready, but will just hold pose until their clips are authored.
	// Priority mirrors the old getArmPose() branch order below (isUsingItem/offhand-held first, then
	// looting gesture, then aggressive, then celebrating, with walk/idle folded in as the fallback).
	private static final RawAnimation IDLE = RawAnimation.begin().thenPlay("graverobber.model.idle");
	private static final RawAnimation WALK = RawAnimation.begin().thenPlay("graverobber.model.walk");
	private static final RawAnimation ATTACK = RawAnimation.begin().thenPlay("graverobber.model.attack");
	private static final RawAnimation DIG = RawAnimation.begin().thenPlay("graverobber.model.dig");
	private static final RawAnimation OFFER = RawAnimation.begin().thenPlay("graverobber.model.offer");
	private static final RawAnimation CELEBRATE = RawAnimation.begin().thenPlay("graverobber.model.celebrate");

	/** Bartering loot table (emerald in offhand → traded item). */
	private static final ResourceLocation TRADE_LOOT = new ResourceLocation(mod_LavaCow.MODID, "gameplay/graverobber_bartering");

	/** Purely cosmetic "digging/opening" pose flag for {@link TombLootFlavorGoal} — kept separate
	 *  from {@link #isUsingItem()} (used by {@link RetreatAndHealGoal}) since the two are unrelated. */
	private static final EntityDataAccessor<Boolean> DATA_LOOTING_GESTURE = SynchedEntityData.defineId(GraveRobberEntity.class, EntityDataSerializers.BOOLEAN);

	public int tradeTimer = 0;

	/** Whether this robber has already placed its one flavor lantern (see {@link LightTombGoal}). */
	private boolean hasPlacedLantern;

	/** Whether this robber has already made its one push into the tomb's interior (see {@link EnterTombGoal}). */
	private boolean hasEnteredTomb;

	public GraveRobberEntity(EntityType<? extends GraveRobberEntity> entityType, Level level) {
		super(entityType, level);
		this.setCanPickUpLoot(true);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_LOOTING_GESTURE, false);
	}

	public void setLootingGesture(boolean looting) {
		this.entityData.set(DATA_LOOTING_GESTURE, looting);
	}

	public boolean isLootingGesture() {
		return this.entityData.get(DATA_LOOTING_GESTURE);
	}

	/** Grave Robber is always left-handed (maintainer-specified trait, not a per-instance roll like
	 *  vanilla's random {@code setLeftHanded} chance) -
	 *  {@link com.Fishmod.fur.client.renderer.entity.GraveRobberRenderer} uses this to decide which
	 *  of the {@code handle_l}/{@code handle_r} bones gets the main-hand item. */
	@Override
	public boolean isLeftHanded() {
		return true;
	}

	@Override
	protected void customServerAiStep() {
		if (!this.isNoAi() && GoalUtils.hasGroundPathNavigation(this)) {
			boolean flag = ((ServerLevel) this.level()).isRaided(this.blockPosition());
			((GroundPathNavigation) this.getNavigation()).setCanOpenDoors(flag);
		}

		super.customServerAiStep();
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new GraveRobberEntity.RetreatAndHealGoal(this));
		this.goalSelector.addGoal(2, new AbstractIllager.RaiderOpenDoorGoal(this));
		this.goalSelector.addGoal(3, new Raider.HoldGroundAttackGoal(this, 10.0F));
		this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, true));
		this.goalSelector.addGoal(5, new GraveRobberEntity.EnterTombGoal(this));
		this.goalSelector.addGoal(6, new GraveRobberEntity.LightTombGoal(this));
		this.goalSelector.addGoal(7, new GraveRobberEntity.TombLootFlavorGoal(this));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, Raider.class)).setAlertOthers());
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 0, true, false, (target) -> {
			return this.getHealth() > this.getMaxHealth() * 0.5F && target.getMobType().equals(MobType.UNDEAD);
		}));
		this.goalSelector.addGoal(8, new GraveRobberEntity.TradeGoal(this));
		this.goalSelector.addGoal(9, new RandomStrollGoal(this, 0.6D));
		this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
		this.goalSelector.addGoal(11, new LookAtPlayerGoal(this, Mob.class, 8.0F));
	}

	public static AttributeSupplier.Builder createAttributes() {
		// Static defaults at registration; real config values applied in finalizeSpawn().
		return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, (double) 0.35F)
				.add(Attributes.FOLLOW_RANGE, 12.0D)
				.add(Attributes.MAX_HEALTH, 20.0D)
				.add(Attributes.ATTACK_DAMAGE, 5.0D);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public AbstractIllager.IllagerArmPose getArmPose() {
		if (this.isUsingItem()) {
			// Reuses the existing raised-arm pose (already driven for the offhand trade item)
			// so the potion drink doesn't need a dedicated animation state.
			return AbstractIllager.IllagerArmPose.CROSSBOW_HOLD;
		} else if (this.isLootingGesture()) {
			// No GeckoLib/dedicated dig or open-pot animation exists for this vanilla-model
			// illager, so the flavor-loot gesture reuses the crossbow-reload pose (hands working
			// at chest height) for both suspicious_sand and decorated_pot targets.
			return AbstractIllager.IllagerArmPose.CROSSBOW_CHARGE;
		} else if (this.isAggressive()) {
			return AbstractIllager.IllagerArmPose.ATTACKING;
		} else if (!this.getOffhandItem().isEmpty()) {
			return AbstractIllager.IllagerArmPose.CROSSBOW_HOLD;
		} else {
			return this.isCelebrating() ? AbstractIllager.IllagerArmPose.CELEBRATING : AbstractIllager.IllagerArmPose.CROSSED;
		}
	}

	@Override
	public boolean canHoldItem(ItemStack stack) {
		return stack.getItem() == Items.EMERALD && !this.isAggressive() && this.getOffhandItem().isEmpty();
	}

	@Override
	protected boolean canReplaceCurrentItem(ItemStack stack_pickup, ItemStack stack_onhand) {
		return this.getMainHandItem().getItem() == Items.IRON_SHOVEL && this.getOffhandItem().isEmpty();
	}

	@Override
	protected void pickUpItem(ItemEntity stack) {
		this.onItemPickup(stack);
		this.setItemSlot(EquipmentSlot.OFFHAND, stack.getItem());
		this.setGuaranteedDrop(EquipmentSlot.OFFHAND);
		this.take(stack, stack.getItem().getCount());
		stack.discard();
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.tradeTimer = compound.getInt("tradeTimer");
		this.hasPlacedLantern = compound.getBoolean("hasPlacedLantern");
		this.hasEnteredTomb = compound.getBoolean("hasEnteredTomb");
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("tradeTimer", this.tradeTimer);
		compound.putBoolean("hasPlacedLantern", this.hasPlacedLantern);
		compound.putBoolean("hasEnteredTomb", this.hasEnteredTomb);
	}

	@Override
	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag tag) {
		SpawnGroupData ilivingentitydata = super.finalizeSpawn(level, difficulty, spawnType, spawnData, tag);
		((GroundPathNavigation) this.getNavigation()).setCanOpenDoors(true);
		this.populateDefaultEquipmentSlots(this.getRandom(), difficulty);
		this.populateDefaultEquipmentEnchantments(this.getRandom(), difficulty);

		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.GraveRobber_Health.get());
		this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.GraveRobber_Attack.get());
		this.setHealth(this.getMaxHealth());

		return ilivingentitydata;
	}

	// PatrollingMonster.finalizeSpawn() has a 6% chance to make any non-PATROL/EVENT/STRUCTURE spawn
	// a "patrol leader" wearing an Ominous Banner (Raid.createIllagerBanner(), 2.0F drop chance) -
	// harmless flavor for vanilla patrols, but not a look Grave Robbers should ever have (tomb-ambush
	// or otherwise). Overriding this to false is the same pattern vanilla's own Witch uses to opt out.
	@Override
	public boolean canBeLeader() {
		return false;
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
		if (this.getCurrentRaid() == null) {
			this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SHOVEL));
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void handleEntityEvent(byte id) {
		switch (id) {
			case 4:
				this.tradeTimer = 60;
				break;
			default:
				super.handleEntityEvent(id);
				break;
		}
	}

	@Override
	public boolean isAlliedTo(Entity other) {
		if (super.isAlliedTo(other)) {
			return true;
		} else if (other instanceof LivingEntity && ((LivingEntity) other).getMobType() == MobType.ILLAGER) {
			return this.getTeam() == null && other.getTeam() == null;
		} else {
			return false;
		}
	}

	/**
	 * Self-preservation goal modeled on vanilla {@code Witch}'s potion-drinking logic:
	 * below 50% health, retreat a short distance from the current target and drink a
	 * Healing II potion, then resume normal AI. Unlike Witch (which drinks in place while
	 * kiting at range), this also issues a one-off flee-position move since GraveRobber is
	 * a melee illager with no ranged kiting goal to fall back on.
	 *
	 * <p>The actual effect application, item consumption, and completion timing are left to
	 * vanilla's {@code Mob#startUsingItem}/{@code LivingEntity#completeUsingItem} machinery
	 * (same path {@code PotionItem} uses) rather than reimplemented — for a non-player
	 * {@code LivingEntity} that path already applies the potion's effects without shrinking
	 * the stack or spawning a glass bottle. This goal restores the original mainhand item once
	 * the drink completes.
	 */
	static class RetreatAndHealGoal extends Goal {
		private static final float HEAL_HEALTH_THRESHOLD = 0.5F;
		private static final int HEAL_COOLDOWN_TICKS = 100; // 5s, prevents an instant re-trigger loop
		private static final int RETREAT_DISTANCE_XZ = 16;
		private static final int RETREAT_DISTANCE_Y = 7;

		private final GraveRobberEntity mob;
		private ItemStack cachedMainHand = ItemStack.EMPTY;
		private int nextHealTick;

		public RetreatAndHealGoal(GraveRobberEntity entity) {
			this.mob = entity;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		}

		@Override
		public boolean canUse() {
			return this.mob.tickCount >= this.nextHealTick && this.mob.getHealth() < this.mob.getMaxHealth() * HEAL_HEALTH_THRESHOLD;
		}

		@Override
		public boolean canContinueToUse() {
			return this.mob.isUsingItem();
		}

		@Override
		public void start() {
			LivingEntity target = this.mob.getTarget();
			Vec3 retreatPos = target == null ? null : DefaultRandomPos.getPosAway(this.mob, RETREAT_DISTANCE_XZ, RETREAT_DISTANCE_Y, target.position());
			if (retreatPos != null) {
				this.mob.getNavigation().moveTo(retreatPos.x, retreatPos.y, retreatPos.z, 1.0D);
			}

			this.cachedMainHand = this.mob.getMainHandItem().copy();
			this.mob.setItemSlot(EquipmentSlot.MAINHAND, PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.STRONG_HEALING));
			this.mob.startUsingItem(InteractionHand.MAIN_HAND);
			if (!this.mob.isSilent()) {
				this.mob.level().playSound(null, this.mob.getX(), this.mob.getY(), this.mob.getZ(), SoundEvents.WITCH_DRINK,
						this.mob.getSoundSource(), 1.0F, 0.8F + this.mob.getRandom().nextFloat() * 0.4F);
			}
		}

		@Override
		public void stop() {
			// Vanilla's completeUsingItem() already applied the potion's effects by this point;
			// it leaves the (unconsumed) potion stack sitting in the mainhand, so restore equipment here.
			this.mob.stopUsingItem();
			this.mob.setItemSlot(EquipmentSlot.MAINHAND, this.cachedMainHand);
			this.cachedMainHand = ItemStack.EMPTY;
			this.nextHealTick = this.mob.tickCount + HEAL_COOLDOWN_TICKS;
		}
	}

	/**
	 * Purely cosmetic "rival looter" flavor: when idle (no combat target, not fleeing/drinking —
	 * see {@link RetreatAndHealGoal}), walk to a nearby {@link FURBlockTagsProvider#TOMB_LOOT_FLAVOR}
	 * block (suspicious sand / decorated pot) and mime digging/opening it for a few seconds.
	 *
	 * <p>Does not touch block state, spawn items, or write NBT to the target block — it only
	 * flips {@link #isLootingGesture()} for the pose and plays a sound. Nothing here should ever
	 * be relied on to grant loot; that stays entirely with the block's own vanilla behavior.
	 */
	static class TombLootFlavorGoal extends Goal {
		private static final int SEARCH_RADIUS_XZ = 8;
		private static final int SEARCH_RADIUS_Y = 4;
		private static final int RESCAN_INTERVAL_TICKS = 20; // avoid rescanning the area every tick while idle
		private static final int APPROACH_TIMEOUT_TICKS = 100; // give up if the target turns out unreachable
		private static final int GESTURE_DURATION_TICKS = 80; // 4s
		private static final int GESTURE_COOLDOWN_TICKS = 150; // 7.5s, avoids flickering between targets
		private static final double INTERACT_RANGE_SQR = 3.0D * 3.0D;

		private final GraveRobberEntity mob;
		private BlockPos targetPos;
		private boolean gestureStarted;
		private int gestureTimer;
		private int approachTimer;
		private int nextScanTick;
		private int nextTriggerTick;

		public TombLootFlavorGoal(GraveRobberEntity entity) {
			this.mob = entity;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		}

		@Override
		public boolean canUse() {
			if (this.mob.getTarget() != null || this.mob.isUsingItem()) {
				return false;
			}
			if (this.mob.tickCount < this.nextTriggerTick || this.mob.tickCount < this.nextScanTick) {
				return false;
			}

			this.nextScanTick = this.mob.tickCount + RESCAN_INTERVAL_TICKS;
			this.targetPos = this.findNearbyTombLootBlock();
			return this.targetPos != null;
		}

		@Override
		public boolean canContinueToUse() {
			if (this.mob.getTarget() != null || this.targetPos == null || this.approachTimer > APPROACH_TIMEOUT_TICKS) {
				return false;
			}
			if (!this.mob.level().getBlockState(this.targetPos).is(FURBlockTagsProvider.TOMB_LOOT_FLAVOR)) {
				return false;
			}
			return !this.gestureStarted || this.gestureTimer > 0;
		}

		@Override
		public void start() {
			this.gestureStarted = false;
			this.gestureTimer = 0;
			this.approachTimer = 0;
		}

		@Override
		public void tick() {
			this.mob.getLookControl().setLookAt(this.targetPos.getX() + 0.5D, this.targetPos.getY() + 0.5D, this.targetPos.getZ() + 0.5D);

			if (this.gestureStarted) {
				if (this.gestureTimer > 0) {
					this.gestureTimer--;
				}
				return;
			}

			this.approachTimer++;
			if (this.mob.distanceToSqr(this.targetPos.getX() + 0.5D, this.targetPos.getY() + 0.5D, this.targetPos.getZ() + 0.5D) > INTERACT_RANGE_SQR) {
				if (this.mob.getNavigation().isDone()) {
					this.mob.getNavigation().moveTo(this.targetPos.getX() + 0.5D, this.targetPos.getY(), this.targetPos.getZ() + 0.5D, 1.0D);
				}
			} else {
				this.gestureStarted = true;
				this.gestureTimer = GESTURE_DURATION_TICKS;
				this.mob.getNavigation().stop();
				this.mob.setLootingGesture(true);
				this.mob.playSound(SoundEvents.BRUSH_GENERIC, 1.0F, 1.0F);
			}
		}

		@Override
		public void stop() {
			if (this.gestureStarted) {
				this.mob.playSound(SoundEvents.BRUSH_SAND_COMPLETED, 1.0F, 1.0F);
			}

			this.mob.setLootingGesture(false);
			this.gestureStarted = false;
			this.gestureTimer = 0;
			this.targetPos = null;
			this.nextTriggerTick = this.mob.tickCount + GESTURE_COOLDOWN_TICKS;
		}

		@Nullable
		private BlockPos findNearbyTombLootBlock() {
			BlockPos origin = this.mob.blockPosition();
			BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
			BlockPos closest = null;
			double closestDistSqr = Double.MAX_VALUE;

			for (int dx = -SEARCH_RADIUS_XZ; dx <= SEARCH_RADIUS_XZ; dx++) {
				for (int dz = -SEARCH_RADIUS_XZ; dz <= SEARCH_RADIUS_XZ; dz++) {
					for (int dy = -SEARCH_RADIUS_Y; dy <= SEARCH_RADIUS_Y; dy++) {
						cursor.setWithOffset(origin, dx, dy, dz);
						double distSqr = cursor.distSqr(origin);
						if (distSqr < closestDistSqr && distSqr <= (double) (SEARCH_RADIUS_XZ * SEARCH_RADIUS_XZ)
								&& this.mob.level().getBlockState(cursor).is(FURBlockTagsProvider.TOMB_LOOT_FLAVOR)) {
							closest = cursor.immutable();
							closestDistSqr = distSqr;
						}
					}
				}
			}

			return closest;
		}
	}

	/**
	 * One-time push into the tomb's interior: a robber that finds itself inside the
	 * {@link FURStructureTagsProvider#ROYAL_TOMB} structure but still far from a designated interior
	 * point (e.g. one just spawned by {@code FURServerEvents#onTombAmbush} at the entrance marker)
	 * heads for that point before any other idle behaviour gets a chance to grab it - otherwise a
	 * freshly-spawned robber never has a reason to leave the entrance, since
	 * {@link LightTombGoal}/{@link TombLootFlavorGoal} only ever look a handful of blocks around
	 * wherever it currently stands. Fires at most once per mob (see {@link #hasEnteredTomb}).
	 *
	 * <p>Targets a {@code fur:tomb_depths}-tagged {@link Marker} baked into the structure NBT (same
	 * pattern as the {@code fur:tomb_entrance} marker {@code FURServerEvents#onTombAmbush} spawns at)
	 * rather than the structure's bounding-box centre - the tomb's footprint is sprawling and
	 * irregular (separate courtyards/wings), so the geometric centre of its axis-aligned bounding box
	 * routinely lands in open sand between the actual built rooms, sending robbers wandering back out
	 * of the tomb instead of into it. If no such marker exists in a given instance (not yet added, or
	 * an older already-generated instance predating it), this goal simply never fires for it - no
	 * fallback to the old bounding-box behaviour, since that is what caused the bug.
	 *
	 * <p>{@code Attributes.FOLLOW_RANGE} directly bounds vanilla pathfinding's search-region size,
	 * node-visit budget, and max path length ({@link net.minecraft.world.entity.ai.navigation.PathNavigation#createPath}
	 * scales all three off it) - the mob's normal 12-block follow range is nowhere near enough for a
	 * route from a surface entrance down into a deep underground chamber, so this goal temporarily
	 * boosts it for the duration of the push (same cache-and-restore pattern {@link TradeGoal} already
	 * uses for movement speed), rather than raising the mob's base follow range permanently and
	 * widening its combat detection range at all times.
	 *
	 * <p>If a {@code fur:tomb_depths}-tagged {@link Marker} is far from the entrance, a single long
	 * {@code moveTo} call tends to path across open desert toward the target's raw direction rather
	 * than through the tomb's actual (winding) interior corridors - the wide-open search region is
	 * more inviting to A* than a long detour, even when that surface route is a dead end. An optional
	 * {@code fur:tomb_waypoint}-tagged marker (e.g. placed at the top of the real staircase down)
	 * breaks the journey into short, unambiguous hops that don't have that problem; if no waypoint
	 * marker exists, this falls back to a single direct hop to the depths marker.
	 */
	static class EnterTombGoal extends Goal {
		private static final int RESCAN_INTERVAL_TICKS = 20;
		private static final double ARRIVE_DISTANCE_SQR = 6.0D * 6.0D;
		private static final int TIMEOUT_TICKS = 400; // 20s
		private static final double PATHFINDING_FOLLOW_RANGE = 48.0D; // wide enough to path entrance-to-depths

		private final GraveRobberEntity mob;
		private List<BlockPos> waypoints;
		private int waypointIndex;
		private double cachedFollowRange;
		private int timer;
		private int nextScanTick;

		EnterTombGoal(GraveRobberEntity entity) {
			this.mob = entity;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		@Override
		public boolean canUse() {
			if (this.mob.hasEnteredTomb || this.mob.getTarget() != null) {
				return false;
			}
			if (this.mob.tickCount < this.nextScanTick) {
				return false;
			}
			this.nextScanTick = this.mob.tickCount + RESCAN_INTERVAL_TICKS;

			if (!(this.mob.level() instanceof ServerLevel serverLevel)) {
				return false;
			}
			StructureStart structureStart = serverLevel.structureManager().getStructureWithPieceAt(this.mob.blockPosition(), FURStructureTagsProvider.ROYAL_TOMB);
			if (!structureStart.isValid()) {
				return false;
			}

			BoundingBox box = structureStart.getBoundingBox();
			AABB searchArea = new AABB(box.minX(), box.minY(), box.minZ(), box.maxX() + 1, box.maxY() + 1, box.maxZ() + 1);
			List<Marker> depths = serverLevel.getEntitiesOfClass(Marker.class, searchArea, marker -> marker.getTags().contains("fur:tomb_depths"));
			if (depths.isEmpty()) {
				this.mob.hasEnteredTomb = true; // no depths marker in this instance - nothing to push toward
				return false;
			}

			BlockPos depthsPos = depths.get(0).blockPosition();
			if (this.hasArrived(depthsPos)) {
				this.mob.hasEnteredTomb = true; // already deep enough - e.g. spawned naturally inside
				return false;
			}

			List<Marker> waypointMarkers = serverLevel.getEntitiesOfClass(Marker.class, searchArea, marker -> marker.getTags().contains("fur:tomb_waypoint"));
			this.waypoints = new ArrayList<>();
			if (!waypointMarkers.isEmpty()) {
				this.waypoints.add(waypointMarkers.get(0).blockPosition());
			}
			this.waypoints.add(depthsPos);
			this.waypointIndex = 0;
			return true;
		}

		@Override
		public boolean canContinueToUse() {
			return !this.mob.hasEnteredTomb && this.mob.getTarget() == null && this.timer < TIMEOUT_TICKS;
		}

		@Override
		public void start() {
			this.timer = 0;
			this.cachedFollowRange = this.mob.getAttribute(Attributes.FOLLOW_RANGE).getBaseValue();
			this.mob.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(PATHFINDING_FOLLOW_RANGE);
			this.moveToCurrentWaypoint();
		}

		@Override
		public void tick() {
			this.timer++;

			BlockPos current = this.waypoints.get(this.waypointIndex);
			if (this.hasArrived(current)) {
				this.waypointIndex++;
				if (this.waypointIndex >= this.waypoints.size()) {
					this.mob.hasEnteredTomb = true;
					return;
				}
				this.moveToCurrentWaypoint();
				return;
			}
			if (this.mob.getNavigation().isDone()) {
				this.moveToCurrentWaypoint();
			}
		}

		@Override
		public void stop() {
			if (this.timer >= TIMEOUT_TICKS) {
				this.mob.hasEnteredTomb = true; // couldn't get there - stop retrying and let idle AI take over
			}
			this.mob.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(this.cachedFollowRange);
			this.mob.getNavigation().stop();
			this.waypoints = null;
		}

		private void moveToCurrentWaypoint() {
			BlockPos target = this.waypoints.get(this.waypointIndex);
			this.mob.getNavigation().moveTo(target.getX() + 0.5D, target.getY(), target.getZ() + 0.5D, 1.0D);
		}

		/** Uses the target's own Y (not the mob's current Y) so vertical distance actually counts -
		 *  a robber stuck directly above/below a target must not read as "arrived". */
		private boolean hasArrived(BlockPos target) {
			return this.mob.distanceToSqr(target.getX() + 0.5D, target.getY(), target.getZ() + 0.5D) <= ARRIVE_DISTANCE_SQR;
		}
	}

	/**
	 * Purely cosmetic "let there be light" flavor: once inside the {@link FURStructureTagsProvider#ROYAL_TOMB}
	 * structure and idle (no combat target, not drinking — same gate as {@link TombLootFlavorGoal}), a robber
	 * scans for a nearby dark, air-filled spot with a sturdy floor or ceiling and places a single lantern
	 * there, explaining how they can see what they're digging through in an otherwise pitch-black tomb.
	 *
	 * <p>Fires at most once per mob (see {@link #hasPlacedLantern}), so the number of lanterns a tomb ends
	 * up with is naturally capped by how many robbers are actually in it, rather than growing unbounded.
	 */
	static class LightTombGoal extends Goal {
		private static final int SEARCH_RADIUS_XZ = 8;
		private static final int SEARCH_RADIUS_Y = 4;
		private static final int RESCAN_INTERVAL_TICKS = 20; // avoid rescanning the area every tick while idle
		private static final int APPROACH_TIMEOUT_TICKS = 100; // give up if the target turns out unreachable
		private static final int LIGHT_THRESHOLD = 8; // vanilla mob spawn threshold is 0; this is "too dark to work by"
		private static final double INTERACT_RANGE_SQR = 3.0D * 3.0D;

		private final GraveRobberEntity mob;
		private BlockPos targetPos;
		private BlockState placementState;
		private int approachTimer;
		private int nextScanTick;

		public LightTombGoal(GraveRobberEntity entity) {
			this.mob = entity;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		}

		@Override
		public boolean canUse() {
			if (this.mob.hasPlacedLantern || this.mob.getTarget() != null || this.mob.isUsingItem()) {
				return false;
			}
			if (this.mob.tickCount < this.nextScanTick) {
				return false;
			}
			this.nextScanTick = this.mob.tickCount + RESCAN_INTERVAL_TICKS;

			if (!this.mob.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)
					|| !(this.mob.level() instanceof ServerLevel serverLevel)
					|| !serverLevel.structureManager().getStructureWithPieceAt(this.mob.blockPosition(), FURStructureTagsProvider.ROYAL_TOMB).isValid()) {
				return false;
			}

			this.targetPos = this.findDarkSpot();
			return this.targetPos != null;
		}

		@Override
		public boolean canContinueToUse() {
			if (this.mob.hasPlacedLantern || this.mob.getTarget() != null || this.targetPos == null || this.approachTimer > APPROACH_TIMEOUT_TICKS) {
				return false;
			}
			return this.mob.level().getBlockState(this.targetPos).isAir();
		}

		@Override
		public void start() {
			this.approachTimer = 0;
		}

		@Override
		public void tick() {
			this.mob.getLookControl().setLookAt(this.targetPos.getX() + 0.5D, this.targetPos.getY() + 0.5D, this.targetPos.getZ() + 0.5D);
			this.approachTimer++;

			if (this.mob.distanceToSqr(this.targetPos.getX() + 0.5D, this.targetPos.getY() + 0.5D, this.targetPos.getZ() + 0.5D) > INTERACT_RANGE_SQR) {
				if (this.mob.getNavigation().isDone()) {
					this.mob.getNavigation().moveTo(this.targetPos.getX() + 0.5D, this.targetPos.getY(), this.targetPos.getZ() + 0.5D, 1.0D);
				}
				return;
			}

			this.mob.getNavigation().stop();
			this.mob.level().setBlock(this.targetPos, this.placementState, 3);
			SoundType soundType = this.placementState.getSoundType();
			this.mob.playSound(soundType.getPlaceSound(), soundType.getVolume(), soundType.getPitch());
			this.mob.hasPlacedLantern = true;
		}

		@Override
		public void stop() {
			this.targetPos = null;
			this.placementState = null;
		}

		@Nullable
		private BlockPos findDarkSpot() {
			Level level = this.mob.level();
			BlockPos origin = this.mob.blockPosition();
			BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
			BlockPos closest = null;
			double closestDistSqr = Double.MAX_VALUE;

			for (int dx = -SEARCH_RADIUS_XZ; dx <= SEARCH_RADIUS_XZ; dx++) {
				for (int dz = -SEARCH_RADIUS_XZ; dz <= SEARCH_RADIUS_XZ; dz++) {
					for (int dy = -SEARCH_RADIUS_Y; dy <= SEARCH_RADIUS_Y; dy++) {
						cursor.setWithOffset(origin, dx, dy, dz);
						double distSqr = cursor.distSqr(origin);
						if (distSqr >= closestDistSqr || distSqr > (double) (SEARCH_RADIUS_XZ * SEARCH_RADIUS_XZ)) {
							continue;
						}
						if (!level.getBlockState(cursor).isAir() || level.getMaxLocalRawBrightness(cursor) >= LIGHT_THRESHOLD) {
							continue;
						}

						BlockState lanternState = this.orientLantern(level, cursor);
						if (lanternState == null) {
							continue;
						}

						closest = cursor.immutable();
						this.placementState = lanternState;
						closestDistSqr = distSqr;
					}
				}
			}

			return closest;
		}

		@Nullable
		private BlockState orientLantern(Level level, BlockPos pos) {
			if (level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP)) {
				return Blocks.LANTERN.defaultBlockState().setValue(LanternBlock.HANGING, false);
			}
			if (level.getBlockState(pos.above()).isFaceSturdy(level, pos.above(), Direction.DOWN)) {
				return Blocks.LANTERN.defaultBlockState().setValue(LanternBlock.HANGING, true);
			}
			return null;
		}
	}

	static class TradeGoal extends Goal {
		private final GraveRobberEntity mob;

		public TradeGoal(GraveRobberEntity entity) {
			this.mob = entity;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		private static List<ItemStack> getItemStacks(GraveRobberEntity mob) {
			LootTable loottable = mob.level().getServer().getLootData().getLootTable(TRADE_LOOT);
			LootParams lootparams = (new LootParams.Builder((ServerLevel) mob.level()))
					.withParameter(LootContextParams.ORIGIN, mob.position())
					.withParameter(LootContextParams.THIS_ENTITY, mob)
					.create(LootContextParamSets.PIGLIN_BARTER);
			return loottable.getRandomItems(lootparams);
		}

		@Override
		public boolean canUse() {
			return this.mob.tradeTimer <= 0 && !this.mob.getOffhandItem().isEmpty() && !this.mob.isAggressive();
		}

		@Override
		public boolean canContinueToUse() {
			return this.mob.tradeTimer > 0 && !this.mob.getOffhandItem().isEmpty() && !this.mob.isAggressive();
		}

		@Override
		public void start() {
			this.mob.tradeTimer = 60;
			this.mob.level().broadcastEntityEvent(this.mob, (byte) 4);
			this.mob.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.15F);
		}

		@Override
		public void tick() {
			if (this.mob.tradeTimer > 0) {
				this.mob.tradeTimer--;
			}
		}

		@Override
		public void stop() {
			this.mob.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.35F);
			this.mob.tradeTimer = 0;

			if (!this.mob.isAggressive()) {
				List<ItemStack> lootList = getItemStacks(this.mob);
				if (lootList.size() > 0) {
					ItemStack loot = lootList.remove(0).copy();
					this.mob.spawnAtLocation(loot);
				}
			} else {
				this.mob.spawnAtLocation(this.mob.getOffhandItem().copy());
			}

			ItemStack stack = this.mob.getOffhandItem().copy();
			stack.setCount(stack.getCount() - 1);
			this.mob.setItemSlot(EquipmentSlot.OFFHAND, stack);
		}
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.VINDICATOR_AMBIENT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.VINDICATOR_DEATH;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.VINDICATOR_HURT;
	}

	@Override
	public void applyRaidBuffs(int wave, boolean unused) {
		ItemStack itemstack = new ItemStack(Items.IRON_SHOVEL);
		Raid raid = this.getCurrentRaid();
		int i = 1;
		if (wave > raid.getNumGroups(Difficulty.NORMAL)) {
			i = 2;
		}

		boolean flag = this.random.nextFloat() <= raid.getEnchantOdds();
		if (flag) {
			Map<Enchantment, Integer> map = Maps.newHashMap();
			map.put(Enchantments.KNOCKBACK, i);
			EnchantmentHelper.setEnchantments(map, itemstack);
		}

		this.setItemSlot(EquipmentSlot.MAINHAND, itemstack);
	}

	@Override
	public SoundEvent getCelebrateSound() {
		return SoundEvents.VINDICATOR_CELEBRATE;
	}

	private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
		if (this.isUsingItem() || !this.getOffhandItem().isEmpty()) {
			state.getController().setAnimation(OFFER);
		} else if (this.isLootingGesture()) {
			state.getController().setAnimation(DIG);
		} else if (this.isAggressive()) {
			state.getController().setAnimation(ATTACK);
		} else if (this.isCelebrating()) {
			state.getController().setAnimation(CELEBRATE);
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
