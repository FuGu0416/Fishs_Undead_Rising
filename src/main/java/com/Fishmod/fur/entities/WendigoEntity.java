package com.Fishmod.fur.entities;

import java.util.EnumSet;
import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.core.SpawnUtil;
import com.Fishmod.fur.data.providers.FUREntityTypeTagsProvider;
import com.Fishmod.fur.entities.ai.EntityAIPickupMeat;
import com.Fishmod.fur.entities.ai.FURMeleeAttackGoal;
import com.Fishmod.fur.init.FURSoundRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class WendigoEntity extends Monster implements GeoEntity {
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	
    private static final RawAnimation IDLE = RawAnimation.begin().thenPlay("wendigo.model.idle");
    private static final RawAnimation WALK = RawAnimation.begin().thenPlay("wendigo.model.walk");
    private static final RawAnimation ATTACK_L = RawAnimation.begin().thenPlay("wendigo.model.attack_l_blend");
    private static final RawAnimation ATTACK_R = RawAnimation.begin().thenPlay("wendigo.model.attack_r_blend");
    private static final RawAnimation ATTACK_SMASH = RawAnimation.begin().thenPlay("wendigo.model.slam_blend");
    //private static final RawAnimation ROAR = RawAnimation.begin().thenPlay("wendigo.model.roar");
    private static final RawAnimation LEAP_START = RawAnimation.begin().thenPlay("wendigo.model.leap_start");
    private static final RawAnimation LEAP = RawAnimation.begin().thenPlay("wendigo.model.leap");
    private static final RawAnimation LEAP_END = RawAnimation.begin().thenPlay("wendigo.model.leap_end");
    
	private static final EntityDataAccessor<Boolean> POUNCING = SynchedEntityData.defineId(WendigoEntity.class, EntityDataSerializers.BOOLEAN);
	public static final int ATTACK_TIMER = 40;
	public static final int JUMP_TIMER = 240;
	
	/** set the Cooldown to pounce attack*/
	private int jumpTimer;
	/** 40: Attack with both hands 41: right hand 42: left hand */
	public byte AttackStance;
	
	public WendigoEntity(EntityType<? extends WendigoEntity> entityType, Level worldIn) {
        super(entityType, worldIn);
        this.xpReward = 20;
    }
	
    @Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(POUNCING, Boolean.valueOf(false));
	}
	
    @Override
    protected void registerGoals() {
        if (!FURConfig.SunScreen_Mode.get())this.goalSelector.addGoal(1, new FleeSunGoal(this, 1.0D));
    	this.goalSelector.addGoal(2, new AIWendigoLeapAtTarget(this, 0.7F));
        this.goalSelector.addGoal(3, new AttackGoal(this)); 
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
    	this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    	this.targetSelector.addGoal(2, new EntityAIPickupMeat<>(this, ItemEntity.class, true));
    	this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
    	this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 0, true, false, (target) -> {
    		return ((LivingEntity)target).attackable() && target.getType().is(FUREntityTypeTagsProvider.WENDIGO_TARGETS);
    	}));	 	
    }
    
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.25D)
        		.add(Attributes.FOLLOW_RANGE, 35.0D)
        		.add(Attributes.MAX_HEALTH, 60.0D)
        		.add(Attributes.ATTACK_DAMAGE, 8.0D)
        		.add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }
    
    public static boolean checkWendigoSpawnRules(EntityType<? extends WendigoEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return Monster.checkMonsterSpawnRules(entityType, level, spawnType, pos, random);
    }
    
    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    @Override
    public int getMaxSpawnClusterSize() {
        return 3;
    }
	
    @Override
    public double getMyRidingOffset() {
        return -0.85D;
    }
    
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void tick() {
    	super.tick();
    	
        if (this.jumpTimer > 0) {
            --this.jumpTimer;
        }
        
        if (this.isPouncing() && this.onGround()) {
        	this.setPouncing(false);
        }
        
    	if (!FURConfig.SunScreen_Mode.get() && this.isSunBurnTick()) {
    		this.setSecondsOnFire(40);
        } 
    }
    
    /**
     * Called when the entity is attacked.
     */
	@Override
    public boolean hurt(DamageSource source, float amount) {
    	if(source.is(DamageTypeTags.IS_FIRE))
    		return super.hurt(source, 2.0F * amount);
    	return super.hurt(source, amount);
    }
	
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {        
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Wendigo_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Wendigo_Attack.get());
    	this.setHealth(this.getMaxHealth());
    	
    	return super.finalizeSpawn(level, difficulty, spawnType, livingdata, tag);
    }
        
    public void setAttackStance(byte byteIn) {
    	this.AttackStance = byteIn;
    }
    
    public byte getAttackStance() {
    	return this.AttackStance;
    }
    
    public void setPouncing(boolean pouncing) {
    	this.getEntityData().set(POUNCING, Boolean.valueOf(pouncing));
    }
    
    public boolean isPouncing() {
    	return this.getEntityData().get(POUNCING).booleanValue();
    }
    
    /**
     * Handler for {@link World#setEntityState}
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
    	if (id == 4) {
            this.AttackStance = id;
            this.triggerAnim("trigger_controller", "attack_smash");
    	} else if (id == 5) {
            this.AttackStance = id;
            this.triggerAnim("trigger_controller", "attack_r");
    	} else if (id == 6) {
            this.AttackStance = id;
            this.triggerAnim("trigger_controller", "attack_l");
    	} else if (id == 7) {	
    		this.jumpTimer = JUMP_TIMER;
    	} else {
            super.handleEntityEvent(id);
        }
    }
    
    static class AIWendigoLeapAtTarget extends Goal {
 	   /** The entity that is leaping. */
 	   private final WendigoEntity leaper;
 	   /** The entity that the leaper is leaping towards. */
 	   private LivingEntity leapTarget;

 	   public AIWendigoLeapAtTarget(WendigoEntity leapingEntity, float leapMotionYIn) {
 	      this.leaper = leapingEntity;
 	      this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
 	   }
 	   
 	   /**
 	    * Returns whether the EntityAIBase should begin execution.
 	    */
 	   public boolean canUse() {
 		   this.leapTarget = this.leaper.getTarget();
 	       if (this.leapTarget == null || this.leaper.jumpTimer > 0) {
 	    	   return false;
 	       } else {
 	    	   float d0 = this.leaper.distanceTo(this.leapTarget);
 	    	   if (!(d0 < 12.0F) && !(d0 > 20.0F)) {
    			   return this.leaper.onGround();
 	    	   } else {
 	    		   return false;
 	         }
 	      }
 	   }
 	   
 	   /**
 	    * Returns whether an in-progress EntityAIBase should continue executing
 	    */
 	   public boolean canContinueToUse() {
 		   return this.leaper.onGround() && this.leaper.jumpTimer >= (JUMP_TIMER - 6);
 	   }
 	   
 	   /**
 	    * Keep ticking a continuous task that has already been started
 	    */
 	   public void tick() {
 		   Vec3 vector3d1 = new Vec3(this.leapTarget.getX() - this.leaper.getX(), 0.0D, this.leapTarget.getZ() - this.leaper.getZ());
 		   float d0 = this.leaper.distanceTo(this.leapTarget);	
 		   this.leaper.getLookControl().setLookAt(this.leapTarget, 30.0F, 30.0F);
 		  
 		   if (this.leaper.jumpTimer == (JUMP_TIMER - 6)) {
 	 		   if (vector3d1.lengthSqr() > 1.0E-7D) {
 	 			   vector3d1 = vector3d1.normalize().scale(Math.min(d0, 15) * 0.2F);
 	 		   }

 	 		   this.leaper.setDeltaMovement(vector3d1.x, vector3d1.y + 0.3F + 0.1F * SpawnUtil.clamp(this.leapTarget.getEyeY() - this.leaper.getY(), 0, 2), vector3d1.z);
 	 		   this.leaper.setPouncing(true);
 		   }
 	   }

 	   /**
 	    * Execute a one shot task or start executing a continuous task
 	    */
 	   public void start() {		   
 		  Vec3 vector3d = this.leapTarget.position().subtract(this.leaper.position());
 		     
           this.leaper.getNavigation().stop();
           this.leaper.setYHeadRot(-((float) Math.atan2(vector3d.x, vector3d.z)) * (180F / (float) Math.PI));
           this.leaper.yBodyRot = this.leaper.getYHeadRot(); 
 		   this.leaper.playSound(FURSoundRegistry.WENDIGO_ATTACK.get(), 0.75F, 0.8F);
 		   
 		   this.leaper.jumpTimer = JUMP_TIMER;
 		   this.leaper.level().broadcastEntityEvent(this.leaper, (byte)7);
 	   }  	
 	   
       /**
        * Reset the task's internal state. Called when this task is interrupted by another one
        */
       public void resetTask() {
       }
    }

	@Override
    public float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return 2.4F;
    }
    
	@Override
    public int getAmbientSoundInterval() {
        return 200;
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return FURSoundRegistry.WENDIGO_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FURSoundRegistry.WENDIGO_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.WENDIGO_DEATH.get();
    }

	@Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    	this.playSound(SoundEvents.ZOMBIE_STEP, 0.15F, 1.0F);
	}

    /**
     * Get this Entity's CreatureAttribute
     */
    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }  

    /**
     * Entity won't drop items or experience points if this returns false
     */
    @Override
    protected boolean shouldDropLoot() {
       return !this.isOnFire() || this.lastHurtByPlayer != null;
    }
    
    static class AttackGoal extends FURMeleeAttackGoal {
        public AttackGoal(PathfinderMob mob) {
           super(mob, 1.25D, false, ATTACK_TIMER);
        }

    	protected int atkTimerMax() {
    		return ATTACK_TIMER;
    	}
    	
    	protected int atkTimerHit() {
    		if (((WendigoEntity) this.mob).getAttackStance() == (byte)4) { 
    			return 6;
    		} else {
    			return 10;
    		}
    	}
    	
    	protected byte atkTimerEvent() {
            ((WendigoEntity)this.mob).AttackStance = (byte)(4 + this.mob.getRandom().nextInt(3));
    		return ((WendigoEntity)this.mob).AttackStance;
    	}
    	
    	protected void dmgEvent(LivingEntity target) {
    		this.mob.swing(InteractionHand.MAIN_HAND);
    		float f = (float)this.mob.getAttributeValue(Attributes.ATTACK_DAMAGE);
    		float f1 = (float)this.mob.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
    		float f2 = this.mob.level().getCurrentDifficultyAt(this.mob.blockPosition()).getEffectiveDifficulty();
    		
    		if (((WendigoEntity)this.mob).AttackStance == (byte)4) {
    			f *= 1.5F;
    		}
    		
			for (LivingEntity entitylivingbase : this.mob.level().getEntitiesOfClass(LivingEntity.class, this.mob.getBoundingBox().inflate(1.5D))) {
                if (!this.mob.equals(entitylivingbase) && !this.mob.isAlliedTo(entitylivingbase)) {
                	if (!(entitylivingbase instanceof TamableAnimal && ((TamableAnimal) entitylivingbase).isOwnedBy(this.mob))) {
                		boolean flag = entitylivingbase.hurt(this.mob.damageSources().mobAttack(this.mob), f);
                		            			            			
                		if (flag) {
                			if (entitylivingbase instanceof Player) {
                				((Player) entitylivingbase).disableShield(true);
                			}
                			
                			if (f1 > 0.0F && entitylivingbase instanceof LivingEntity) {
                				((LivingEntity)entitylivingbase).knockback(f1 * 0.5F, (double)Math.sin(this.mob.getYRot() * ((float)Math.PI / 180F)), (double)(-Math.cos(this.mob.getYRot() * ((float)Math.PI / 180F))));
                				this.mob.setDeltaMovement(this.mob.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
                			}

                			this.mob.doEnchantDamageEffects(this.mob, entitylivingbase);
                			this.mob.setLastHurtMob(entitylivingbase);
                			
                            if (this.mob.getMainHandItem().isEmpty() && this.mob.isOnFire() && this.mob.getRandom().nextFloat() < f2 * 0.3F) {
                            	entitylivingbase.setSecondsOnFire(2 * (int)f2);
                            }
                            
                            if (entitylivingbase instanceof LivingEntity) {
                                ((LivingEntity)entitylivingbase).addEffect(new MobEffectInstance(MobEffects.HUNGER, 7 * 20 * (int)f2, 4));
                            }
                		}   		         
                	}
                }
            }    		

			this.mob.level().playSound((Player)null, this.mob.getX(), this.mob.getY(), this.mob.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, this.mob.getSoundSource(), 1.0F, 1.0F);           		
    	}
	}

    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
    	if (this.isPouncing()) {
    		if (this.jumpTimer >= (JUMP_TIMER - 8)) {
    			state.getController().setAnimation(LEAP_START);
    			state.getController().setAnimation(LEAP);
    		} else if (this.onGround()) {
    			state.getController().setAnimation(LEAP_END);
    		}
    	} else if (state.isMoving() || !this.getNavigation().isDone()) {
            state.getController().setAnimation(WALK);
        } else {
            state.getController().setAnimation(IDLE);
        }
        
        return PlayState.CONTINUE;
    }

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "controller", 5, this::predicate));
		controllers.add(new AnimationController<>(this, "trigger_controller", 5, state -> PlayState.STOP)
				.triggerableAnim("attack_l", ATTACK_L)
				.triggerableAnim("attack_r", ATTACK_R)
				.triggerableAnim("attck_smash", ATTACK_SMASH));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
