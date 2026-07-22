package com.Fishmod.fur.entities.tameable;

import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.core.SpawnUtil;
import com.Fishmod.fur.init.FUREffectRegistry;
import com.Fishmod.fur.init.FURSoundRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ShroomlingEntity extends FURTameableEntity implements GeoEntity {
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("shroomling.model.idle");
	private static final RawAnimation WALK = RawAnimation.begin().thenLoop("shroomling.model.walk");
	private static final RawAnimation ATTACK = RawAnimation.begin().thenPlay("shroomling.model.attack_blend");

	private static final EntityDataAccessor<Integer> SKIN_TYPE = SynchedEntityData.defineId(ShroomlingEntity.class, EntityDataSerializers.INT);
	/** Index into {@link #SPORE_EFFECTS} of the spore effect this shroomling carries. Synced so the client can tint the ambient particles. */
	private static final EntityDataAccessor<Integer> SPORE_EFFECT = SynchedEntityData.defineId(ShroomlingEntity.class, EntityDataSerializers.INT);
	/** Packed RGB colour of an injected custom spore effect, or {@code -1} when this shroomling uses its table effect. Synced so the bubble layer can tint to the injected potion. */
	private static final EntityDataAccessor<Integer> INJECTED_SPORE_COLOR = SynchedEntityData.defineId(ShroomlingEntity.class, EntityDataSerializers.INT);

	/**
	 * The pool of spore effects a wild shroomling may carry. Each entry bakes in its own
	 * amplifier and duration. When an untamed shroomling is killed, its carried effect is
	 * released onto the killer (see {@link #die}). Kept as a fixed table so the synced index
	 * stays stable across save/load.
	 */
	private static final java.util.function.Supplier<MobEffectInstance>[] SPORE_EFFECTS = makeSporeEffects();

	@SuppressWarnings("unchecked")
	private static java.util.function.Supplier<MobEffectInstance>[] makeSporeEffects() {
		return new java.util.function.Supplier[] {
			(java.util.function.Supplier<MobEffectInstance>) () -> new MobEffectInstance(MobEffects.WEAKNESS, 				10 * 20, 0),
			(java.util.function.Supplier<MobEffectInstance>) () -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 			10 * 20, 0),
			(java.util.function.Supplier<MobEffectInstance>) () -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED,			10 * 20, 0),
			(java.util.function.Supplier<MobEffectInstance>) () -> new MobEffectInstance(FUREffectRegistry.CORRODED.get(), 	10 * 20, 0),
			(java.util.function.Supplier<MobEffectInstance>) () -> new MobEffectInstance(FUREffectRegistry.SPOREROT.get(), 	10 * 20, 0),
			(java.util.function.Supplier<MobEffectInstance>) () -> new MobEffectInstance(FUREffectRegistry.FLOURISHED.get(),10 * 20, 0),
		};
	}

	private int limitedLifeTicks;
	private boolean isSmoking = false;
	/** Server-side custom spore effect injected via the Sporecaller; overrides the table effect for the death burst. {@code null} when none is injected. */
	@Nullable
	private MobEffectInstance customSporeEffect = null;

	public ShroomlingEntity(EntityType<? extends ShroomlingEntity> entityType, Level worldIn) {
        super(entityType, worldIn);
        this.limitedLifeTicks = -1;
    }

	@Override
    protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SKIN_TYPE, Integer.valueOf(0));
		this.entityData.define(SPORE_EFFECT, Integer.valueOf(0));
		this.entityData.define(INJECTED_SPORE_COLOR, Integer.valueOf(-1));
    }

	public int getSporeEffect() {
		return this.entityData.get(SPORE_EFFECT).intValue();
	}

	public void setSporeEffect(int index) {
		this.entityData.set(SPORE_EFFECT, Mth.clamp(index, 0, SPORE_EFFECTS.length - 1));
	}

	/**
	 * Strips this shroomling of any spore effect: no table effect, no injected effect. {@link #buildSporeEffect}
	 * then returns {@code null} (no death burst) and the bubble falls back to {@link #DEFAULT_BUBBLE_COLOR}.
	 * Used for shroomlings summoned by an un-injected Sporecaller. The {@code -1} index is a sentinel
	 * outside the {@link #SPORE_EFFECTS} table range.
	 */
	public void clearSporeEffect() {
		this.customSporeEffect = null;
		this.entityData.set(SPORE_EFFECT, -1);
		this.entityData.set(INJECTED_SPORE_COLOR, -1);
	}

	/**
	 * Tints the bubble to a given colour without giving the shroomling any burst payload. Used when a
	 * beneficial injected potion is handed to the summoner instead of becoming a spore: the shroomling
	 * shows the potion's colour but releases nothing on death.
	 */
	public void setSporeColorOnly(int color) {
		this.customSporeEffect = null;
		this.entityData.set(SPORE_EFFECT, -1);
		this.entityData.set(INJECTED_SPORE_COLOR, color);
	}

	/**
	 * Sets the potion effect this shroomling releases onto its target when its spores burst (see
	 * {@link #die}). This does NOT apply the effect to the shroomling itself; it only stores the
	 * payload, overriding the random table effect. The Sporecaller only ever holds one potion, so a
	 * later injection replaces this in place.
	 */
	public void setSporeEffect(MobEffectInstance effect) {
		this.customSporeEffect = new MobEffectInstance(effect);
		this.entityData.set(INJECTED_SPORE_COLOR, effect.getEffect().getColor());
	}

	/**
	 * Fresh instance of the spore-burst payload: the injected custom effect when present, otherwise the
	 * table effect for the synced index. {@code null} only if the index is somehow out of range.
	 */
	@Nullable
	private MobEffectInstance buildSporeEffect() {
		if (this.customSporeEffect != null) {
			return new MobEffectInstance(this.customSporeEffect);
		}
		int i = this.getSporeEffect();
		return (i >= 0 && i < SPORE_EFFECTS.length) ? SPORE_EFFECTS[i].get() : null;
	}

	/** Default bubble tint for a shroomling carrying no spore effect. */
	private static final int DEFAULT_BUBBLE_COLOR = 0x1EFFF7;

	/**
	 * Packed RGB colour of the carried spore effect — used client-side to tint the
	 * {@code shroomling_bubble} render layer. Uses the synced injected colour when a potion has been
	 * injected, otherwise the colour of the table effect for the synced {@link #SPORE_EFFECT} index.
	 * Falls back to {@link #DEFAULT_BUBBLE_COLOR} when there is no effect.
	 */
	public int getSporeColor() {
		int injected = this.entityData.get(INJECTED_SPORE_COLOR);
		if (injected != -1) {
			return injected;
		}
		MobEffectInstance carried = this.buildSporeEffect();
		return carried != null ? carried.getEffect().getColor() : DEFAULT_BUBBLE_COLOR;
	}

	public static boolean checkShroomlingSpawnRules(EntityType<? extends ShroomlingEntity> type, ServerLevelAccessor world, MobSpawnType reason, BlockPos pos, RandomSource rand) {
		return world.getDifficulty() != Difficulty.PEACEFUL && FURTameableEntity.checkMobSpawnRules(type, world, reason, pos, rand);
	}

    @Override
    protected void registerGoals() {
    	this.goalSelector.addGoal(1, new FloatGoal(this));
    	this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
    	this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
    	this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));

        this.applyEntityAI();
    }

    protected void applyEntityAI() {
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
    	this.targetSelector.addGoal(4, new AICopyOwnerTarget(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.25D)
        		.add(Attributes.MAX_HEALTH, 20.0D)
        		.add(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    @Override
    public void setLimitedLife(int limitedLifeTicksIn) {
    	if (limitedLifeTicksIn != 0) {
    		this.limitedLifeTicks = limitedLifeTicksIn;
    	}
    }

    public int getSkin() {
        return this.entityData.get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
        this.entityData.set(SKIN_TYPE, Integer.valueOf(skinType));
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        // Shroomlings are fungal creatures — immune to the Sporerot affliction.
        if (effect.getEffect() == FUREffectRegistry.SPOREROT.get()) {
            return false;
        }
        return super.canBeAffected(effect);
    }

	@Override
    protected boolean isCommandable() {
    	return false;
    }

	@Override
    public boolean isSummonedMinion() {
    	// Shroomling is now a wild, tameable neutral mob rather than a player summon, so it
    	// must NOT be dismissed on owner logout — a tamed one should persist like any pet.
    	return false;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void aiStep() {
        if (this.isSmoking) {
	    	for (int j = 0; j < 8; ++j) {
	            float f = this.random.nextFloat() * ((float)Math.PI * 2F);
	            float f1 = this.getBbHeight() * 0.4F + this.random.nextFloat() * 0.5F;
	            float f2 = Mth.sin(f) * f1;
	            float f3 = Mth.cos(f) * f1;
	            Level world = this.level();
	            SimpleParticleType enumparticletypes = ParticleTypes.CAMPFIRE_COSY_SMOKE;
	            double d0 = this.getX() + (double)f2;
	            double d1 = this.getZ() + (double)f3;
	            world.addParticle(enumparticletypes, d0, this.getBoundingBox().minY + (double)f1, d1, 0.0D, 0.05D, 0.0D);
	        }
        }

    	super.aiStep();
    }

    /**
     * Called frequently so the entity can update its state every tick as required.
     */
	@Override
    public void tick() {
		super.tick();

    	if (this.limitedLifeTicks >= 0 && this.tickCount >= this.limitedLifeTicks) {
            if (FURConfig.Show_Expire_Death_Messege.get() && !this.level().isClientSide() && this.level().getGameRules().getBoolean(GameRules.RULE_SHOWDEATHMESSAGES) && this.getOwner() instanceof Player) {
                this.getOwner().sendSystemMessage(SpawnUtil.TimeupDeathMessage(this));
            }
        	this.level().broadcastEntityEvent(this, (byte)11);
            this.playSound(this.getDeathSound(), this.getSoundVolume(), this.getVoicePitch());
            this.discard();
        }
	}

	@Override
    public boolean doHurtTarget(Entity entityIn) {
        boolean flag = super.doHurtTarget(entityIn);

        if (flag) {
            if(entityIn instanceof LivingEntity) {
	            this.weaponEnchants.applyOnHit(this, (LivingEntity)entityIn);

	            // Summoned/tamed shroomlings deliver their carried spore effect to the target on hit
	            // (instead of the wild death burst). buildSporeEffect() is null for spore-less /
	            // beneficial-only shroomlings, so only one carrying a (negative) spore actually applies it.
	            // Wild shroomlings are excluded here — they still burst on death (see die()).
	            if (this.isTame()) {
	            	MobEffectInstance spore = this.buildSporeEffect();
	            	if (spore != null) {
	            		((LivingEntity)entityIn).addEffect(spore);
	            	}
	            }
            }
        }

        return flag;
    }

    /**
     * If Animal, checks if the age timer is negative
     */
	@Override
    public boolean isBaby() {
       return true;
    }

    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Shroomling_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Shroomling_Attack.get());
    	this.setHealth(this.getMaxHealth());
    	this.setSkin(this.random.nextInt(2));
    	this.setSporeEffect(this.random.nextInt(SPORE_EFFECTS.length));

    	return super.finalizeSpawn(worldIn, difficulty, spawnType, livingdata, tag);
    }

	@Override
    public float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return 0.6F;
    }

    /**
     * Handler for {@link net.minecraft.world.level.Level#broadcastEntityEvent}
     */
	@OnlyIn(Dist.CLIENT)
	@Override
    public void handleEntityEvent(byte id) {
    	if (id == 11) {
            this.isSmoking = true;
        } else {
            super.handleEntityEvent(id);
        }
    }

    class AICopyOwnerTarget extends TargetGoal {
    	private final TargetingConditions copyOwnerTargeting = TargetingConditions.forNonCombat().ignoreLineOfSight().ignoreInvisibilityTesting();
    	private LivingEntity owner = ShroomlingEntity.this.getOwner();

        public AICopyOwnerTarget(PathfinderMob creature) {
            super(creature, false);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean canUse() {
            return this.owner != null && this.owner instanceof Monster && ((Monster) this.owner).isAggressive() && this.canAttack(((Monster) this.owner).getTarget(), this.copyOwnerTargeting);
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            ShroomlingEntity.this.setTarget(((Monster) this.owner).getTarget());
            super.start();
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return FURSoundRegistry.SHROOMLING_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FURSoundRegistry.SHROOMLING_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.SHROOMLING_DEATH.get();
    }

	@Override
    protected void playStepSound(BlockPos pos, BlockState state) {
	    this.playSound(SoundEvents.CHICKEN_STEP, 0.15F, 1.0F);
	}

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
       super.readAdditionalSaveData(compound);
        this.setLimitedLife(compound.getInt("LifeTicks"));
        this.setSkin(compound.getInt("Variant"));
        // Set the raw saved index (don't clamp) so the "no spore effect" sentinel (-1) survives a reload.
        this.entityData.set(SPORE_EFFECT, compound.getInt("SporeEffect"));
    	this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Shroomling_Health.get() + ((float)this.weaponEnchants.getUnbreaking() * 2.0F));
    	if (compound.contains("CustomSporeEffect", Tag.TAG_COMPOUND)) {
    		this.customSporeEffect = MobEffectInstance.load(compound.getCompound("CustomSporeEffect"));
    	}
    	// Bubble colour is persisted directly so the colour-only (beneficial) state survives a reload.
    	this.entityData.set(INJECTED_SPORE_COLOR, compound.contains("InjectedSporeColor", Tag.TAG_INT) ? compound.getInt("InjectedSporeColor") : -1);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("LifeTicks", this.limitedLifeTicks - this.tickCount);
        compound.putInt("Variant", getSkin());
        compound.putInt("SporeEffect", this.getSporeEffect());
        compound.putInt("InjectedSporeColor", this.entityData.get(INJECTED_SPORE_COLOR));
        if (this.customSporeEffect != null) {
            compound.put("CustomSporeEffect", this.customSporeEffect.save(new CompoundTag()));
        }
    }

    /**
     * Get this Entity's MobType
     */
    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    /** Max distance (blocks) a wild shroomling's burst spores can reach the last-hit target. */
    private static final double SPORE_BURST_RANGE = 2.0D;

    /**
     * On death, a WILD (untamed) shroomling bursts its spore sac onto the last entity it hit, provided
     * that entity is still within {@link #SPORE_BURST_RANGE} blocks (plays a spore-burst sound). This is
     * left unchanged. Summoned/tamed shroomlings do NOT burst — they deliver their spore on attack
     * instead (see {@link #doHurtTarget}).
     */
    @Override
    public void die(DamageSource cause) {
        if (!this.level().isClientSide() && !this.isTame()) {
            MobEffectInstance carried = this.buildSporeEffect();
            if (carried != null) {
                LivingEntity target = this.getLastHurtMob();
                if (target != null && target.isAlive() && this.distanceTo(target) <= SPORE_BURST_RANGE) {
                    target.addEffect(carried);
                    this.level().playSound(null, target.getX(), target.getY(), target.getZ(),
                            SoundEvents.PUFFER_FISH_BLOW_UP, this.getSoundSource(), 1.0F, 1.0F);
                }
            }
        }

        super.die(cause);
    }

    @Override
    public boolean shouldDropLoot() {
    	return !this.isTame() || (this.isTame() && !(this.getOwner() instanceof Player));
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
