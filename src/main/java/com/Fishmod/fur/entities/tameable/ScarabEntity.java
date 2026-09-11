package com.Fishmod.fur.entities.tameable;

import java.util.EnumSet;

import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.core.SpawnUtil;
import com.Fishmod.fur.init.FURBlockRegistry;
import com.Fishmod.fur.init.FUREffectRegistry;
import com.Fishmod.fur.init.FURSoundRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
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
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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

public class ScarabEntity extends FURTameableEntity implements GeoEntity {
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final RawAnimation IDLE = RawAnimation.begin().thenPlay("scarab.model.idle");
    private static final RawAnimation WALK = RawAnimation.begin().thenPlay("scarab.model.walk");
    private static final RawAnimation FLY = RawAnimation.begin().thenPlay("scarab.model.fly");
    private static final RawAnimation ATTACK = RawAnimation.begin().thenPlay("scarab.model.attack");
    private static final RawAnimation BURROW_UP = RawAnimation.begin().thenPlay("scarab.model.burrow_up");
    private static final RawAnimation BURROW_DOWN = RawAnimation.begin().thenPlay("scarab.model.burrow_down");

	private static final EntityDataAccessor<Integer> SKIN_TYPE = SynchedEntityData.defineId(ScarabEntity.class, EntityDataSerializers.INT);
	// scarab.model.burrow_down is only 10 ticks (0.5s), but "controller" (registerControllers below)
	// has a 5-tick transition blend, and it spends that blending FROM idle/walk INTO burrow_down before
	// the clip's own content is even visible - a raw 10-tick budget meant discard() fired right as the
	// blend finished, so the scarab appeared to just vanish without ever visibly playing burrow_down.
	// 5 (transition) + 10 (clip) so the clip is actually on screen for its full length before discard().
	private static final int DAY_SINK_TICKS = 15;
	// 0.5s, matching scarab.model.burrow_up's real length. Independently ticked down on both sides
	// (server for the AIBurrowingUp freeze goal, client purely to know when to release the triggered
	// animation's hold) - same non-networked-countdown design UnburiedEntity's spellTicks already uses.
	private static final int BURROW_UP_TICKS = 10;
	private int attackTimer = 10;
	private int limitedLifeTicks;
	private boolean isSmoking = false;
	private boolean isBurrowingDown = false;
	private int daySinkTimer = -1;
	private int burrowUpTicks = 0;
	// finalizeSpawn() runs before the entity is added/tracked in the level, so broadcastEntityEvent
	// there wouldn't reliably reach anyone - set this instead and let tick() (which only ever runs on
	// an already-tracked entity) fire the real trigger on its very first tick after spawning.
	private boolean pendingBurrowUp = false;

	public ScarabEntity(EntityType<? extends ScarabEntity> entityType, Level worldIn) {
        super(entityType, worldIn);
        this.limitedLifeTicks = -1;
        this.xpReward = 3;
    }
	
	@Override
    protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SKIN_TYPE, Integer.valueOf(0));
    }
	
    @Override
    protected void registerGoals() {
    	// Priority 0 (above FloatGoal's JUMP-only claim) so a burrowing scarab (up on spawn, or down on
    	// its daytime disappear) can't move/attack/look, but can still be kept from drowning if it
    	// happens to be underwater.
    	if (!FURConfig.SunScreen_Mode.get())this.goalSelector.addGoal(0, new AIBurrowing());
    	this.goalSelector.addGoal(1, new FloatGoal(this));
    	this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, RavenEntity.class, 8.0F, 1.0D, 1.4D));
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
        		.add(Attributes.MOVEMENT_SPEED, 0.263D)
        		.add(Attributes.MAX_HEALTH, 8.0D)
        		.add(Attributes.ATTACK_DAMAGE, 1.0D);
    }
    
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
    protected boolean isCommandable() {
    	return false;
    }

	@Override
    public boolean isSummonedMinion() {
    	return true;
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

    	// Dust puffs while burrowing (both up on spawn and down on daytime disappear) - same
    	// BlockParticleOption-from-the-ground-below trick as UnburiedEntity's birth particles, so it
    	// reads as sand/dirt/whatever's actually underfoot rather than a hardcoded texture.
    	if ((this.burrowUpTicks > 0 || this.isBurrowingDown) && this.level().isClientSide()) {
    		BlockPos below = this.getOnPos().below();
    		BlockState groundState = this.level().getBlockState(below);

    		if (groundState.isSolidRender(this.level(), below)) {
    			for (int i = 0; i < 4; i++) {
    				this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, groundState).setPos(below),
    						this.getX() + (double) (this.random.nextFloat() * this.getBbWidth() * 2.0F) - (double) this.getBbWidth(),
    						this.getY() + (double) (this.random.nextFloat() * this.getBbWidth() * 2.0F) - (double) this.getBbWidth(),
    						this.getZ() + (double) (this.random.nextFloat() * this.getBbWidth() * 2.0F) - (double) this.getBbWidth(),
    						this.random.nextGaussian() * 0.02D, this.random.nextGaussian() * 0.02D, this.random.nextGaussian() * 0.02D);
    			}
    		}
    	}

    	super.aiStep();
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
	@Override
    public void tick() {
		super.tick();

    	if (this.pendingBurrowUp) {
    		this.pendingBurrowUp = false;
    		this.startBurrowUp();
    	}

    	if (this.burrowUpTicks > 0) {
    		--this.burrowUpTicks;

    		// scarab.model.burrow_up is authored `hold_on_last_frame` (same as Unburied's birth clip -
    		// see that fix earlier), so it freezes on its last pose once it finishes playing unless
    		// something releases the hold. #hurt's interrupt path (event 44) handles the cut-short
    		// case; this handles the normal-completion case, client-side only, the instant this
    		// (independently-ticking) copy of burrowUpTicks reaches 0 on its own.
    		if (this.burrowUpTicks == 0 && this.level().isClientSide()) {
    			this.getAnimatableInstanceCache().<ScarabEntity>getManagerForId(this.getId())
    					.stopTriggeredAnimation("trigger_controller", "burrow_up");
    		}
    	}

    	if (this.attackTimer > 0)
    		this.attackTimer--;

    	if (this.limitedLifeTicks >= 0 && this.tickCount >= this.limitedLifeTicks) {
            if (FURConfig.Show_Expire_Death_Message.get() && !this.level().isClientSide() && this.level().getGameRules().getBoolean(GameRules.RULE_SHOWDEATHMESSAGES) && this.getOwner() instanceof Player) {
                this.getOwner().sendSystemMessage(SpawnUtil.TimeupDeathMessage(this));
            }
            this.level().broadcastEntityEvent(this, (byte)11);
            this.playSound(this.getDeathSound(), this.getSoundVolume(), this.getVoicePitch());
            this.discard();
        }

    	// Wild scarabs are neutral vermin, not daylight-proof - once the sun's up they dig back into
    	// the ground and vanish rather than lingering around indefinitely. Owned pets are unaffected.
    	// Only triggers while standing on sand/sandstone/red sand/red sandstone (see
    	// #getInfestedReplacement) - elsewhere there's no ground to convincingly burrow into and no
    	// infested block to leave behind. DAY_SINK_TICKS covers the controller's transition blend *and*
    	// burrow_down's real length - see that field's own comment - so discard() lands right as the
    	// fully-blended-in clip finishes.
    	// `burrowUpTicks <= 0` guard: without it, a scarab spawned in daylight (e.g. spawn egg) would
    	// roll into this on the very same tick pendingBurrowUp fires burrow_up, starting burrow_down
    	// (and the daySink discard countdown) while burrow_up is still playing - trigger_controller and
    	// the main controller would then show both animations at once. Wait for burrow_up to actually
    	// finish before this can even be considered.
    	if (!this.level().isClientSide() && this.getOwner() == null) {
    		if (this.daySinkTimer < 0) {
    			if (this.burrowUpTicks <= 0 && this.level().isDay() && this.getTarget() == null && this.level().canSeeSky(this.blockPosition())
    					&& getInfestedReplacement(this.level().getBlockState(this.getOnPos().below()).getBlock()) != null
    					&& this.getRandom().nextFloat() < 0.02F) {
    				this.daySinkTimer = DAY_SINK_TICKS;
    				this.level().broadcastEntityEvent(this, (byte)42);
    			}
    		} else if (--this.daySinkTimer <= 0) {
    			// Re-checked rather than reusing the block seen when the countdown started - the ground
    			// may have changed (mined, exploded, etc.) during the burrow_down animation.
    			BlockPos below = this.getOnPos().below();
    			Block infested = getInfestedReplacement(this.level().getBlockState(below).getBlock());
    			if (infested != null) {
    				this.level().setBlockAndUpdate(below, infested.defaultBlockState());
    			}
    			this.discard();
    		}
    	}
	}

	/**
	 * Maps the block a wild scarab is standing on to the disguised "infested" block it leaves
	 * behind when it burrows in for the day (see {@link #tick}), or {@code null} if that block
	 * isn't one scarabs can burrow into. Only the four base sand/sandstone blocks count - smooth,
	 * cut, chiseled, etc. variants are intentionally not included.
	 */
	@Nullable
	private static Block getInfestedReplacement(Block groundBlock) {
		if (groundBlock == Blocks.SAND) {
			return FURBlockRegistry.INFESTED_SAND.get();
		} else if (groundBlock == Blocks.RED_SAND) {
			return FURBlockRegistry.INFESTED_RED_SAND.get();
		} else if (groundBlock == Blocks.SANDSTONE) {
			return FURBlockRegistry.INFESTED_SANDSTONE.get();
		} else if (groundBlock == Blocks.RED_SANDSTONE) {
			return FURBlockRegistry.INFESTED_RED_SANDSTONE.get();
		}

		return null;
	}

	/**
	 * Starts the burrow-up spawn flourish: freezes movement/attack/look via {@link AIBurrowingUp} for
	 * {@link #BURROW_UP_TICKS}, unless interrupted early by {@link #hurt}. Called from {@link #tick}
	 * for every normal spawn path (see {@link #finalizeSpawn}/{@link #pendingBurrowUp}), and directly
	 * by {@code InfestedBlock} for its ambush spawn (which bypasses finalizeSpawn entirely but
	 * calls this only after the entity is already added to the level, so broadcasting here is safe).
	 */
	public void startBurrowUp() {
		this.burrowUpTicks = BURROW_UP_TICKS;

		if (!this.level().isClientSide()) {
			this.level().broadcastEntityEvent(this, (byte) 43);
		}
	}

	@Override
    public boolean doHurtTarget(Entity entityIn) {
        boolean flag = super.doHurtTarget(entityIn);

        if (flag) {
        	this.attackTimer = 10;
        	this.level().broadcastEntityEvent(this, (byte)40);
        	
            if(entityIn instanceof LivingEntity) {
	            this.weaponEnchants.applyOnHit(this, (LivingEntity)entityIn);

            	((LivingEntity)entityIn).addEffect(new MobEffectInstance(FUREffectRegistry.SOILED.get(), 8 * 20, 1));
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
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Scarab_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Scarab_Attack.get());
    	this.setHealth(this.getMaxHealth());
    	// Deferred to the entity's first tick - see #pendingBurrowUp for why.
    	this.pendingBurrowUp = true;

    	return super.finalizeSpawn(worldIn, difficulty, spawnType, livingdata, tag);
    }

	@Override
    public float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.6F;
    }

	/**
	* Called when the entity is attacked. Real damage taken while burrowing up interrupts that
	* animation immediately, same as UnburiedEntity's birth animation.
	*/
    @Override
	public boolean hurt(DamageSource source, float amount) {
    	if (source.is(DamageTypeTags.IS_FALL)) {
    		return false;
    	}

    	boolean wasBurrowingUp = this.burrowUpTicks > 0;
    	boolean hurt = super.hurt(source, amount);

    	if (hurt && wasBurrowingUp) {
    		this.burrowUpTicks = 0;
    		this.level().broadcastEntityEvent(this, (byte) 44);
    	}

    	return hurt;
	}

    /**
     * Handler for {@link World#setEntityState}
     */
	@OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
		if (id == 40) {
            this.attackTimer = 10;
        } else if (id == 11) {
            this.isSmoking = true;
        } else if (id == 42) {
            this.isBurrowingDown = true;
        } else if (id == 43) {
        	this.burrowUpTicks = BURROW_UP_TICKS;
        	this.triggerAnim("trigger_controller", "burrow_up");
        } else if (id == 44) {
        	this.burrowUpTicks = 0;
        	this.getAnimatableInstanceCache().<ScarabEntity>getManagerForId(this.getId())
        			.stopTriggeredAnimation("trigger_controller", "burrow_up");
        } else {
            super.handleEntityEvent(id);
        }
    }

    /**
     * Freezes movement/looking while either burrowing window is active - {@link #burrowUpTicks} (spawn)
     * or {@link #daySinkTimer} (daytime disappear, tracked server-side; {@link #isBurrowingDown} is the
     * client-only mirror set by the entity-event handler, not usable here since goals only ever run
     * server-side) - same claim-the-flags trick as {@code UnburiedEntity.AIBirthing}/
     * {@code SkeletonKingEntity.DoNothingGoal}. Deliberately omits {@code JUMP} (unlike those two) so
     * {@link FloatGoal}, one priority below, can still keep the scarab from drowning while frozen.
     */
    class AIBurrowing extends Goal {
        public AIBurrowing() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        public boolean canUse() {
            return ScarabEntity.this.burrowUpTicks > 0 || ScarabEntity.this.daySinkTimer >= 0;
        }

        public void start() {
            ScarabEntity.this.getNavigation().stop();
        }
    }

    class AICopyOwnerTarget extends TargetGoal {
    	private final TargetingConditions copyOwnerTargeting = TargetingConditions.forNonCombat().ignoreLineOfSight().ignoreInvisibilityTesting();
    	private LivingEntity owner = ScarabEntity.this.getOwner();
    	
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
            ScarabEntity.this.setTarget(((Monster) this.owner).getTarget());
            super.start();
        }
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return FURSoundRegistry.SCARAB_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FURSoundRegistry.SCARAB_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.SCARAB_DEATH.get();
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
    	this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Scarab_Health.get() + ((float)this.weaponEnchants.getUnbreaking() * 2.0F));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("LifeTicks", this.limitedLifeTicks - this.tickCount);
        compound.putInt("Variant", getSkin());
    }

    /**
     * Get this Entity's MobType
     */
    @Override
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }
    
    @Override
    public boolean shouldDropLoot() {
    	return !this.isTame() || (this.isTame() && !(this.getOwner() instanceof Player));
    }

    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
		if (this.isBurrowingDown) {
			state.getController().setAnimation(BURROW_DOWN);
		} else if (this.onGround()) {
			if (state.isMoving() || !this.getNavigation().isDone()) {
				state.getController().setAnimation(WALK);
			} else {
				state.getController().setAnimation(IDLE);
			}
		} else {
			state.getController().setAnimation(FLY);
		}

        return PlayState.CONTINUE;
    }

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "controller", 5, this::predicate));
		controllers.add(new AnimationController<>(this, "trigger_controller", 5, state -> PlayState.STOP)
				.triggerableAnim("attack", ATTACK)
				.triggerableAnim("burrow_up", BURROW_UP));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
