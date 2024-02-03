package com.Fishmod.mod_LavaCow.entities;

import java.util.EnumSet;
import java.util.Random;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.ai.EntityAIPickupMeat;
import com.Fishmod.mod_LavaCow.entities.ai.FURMeleeAttackGoal;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;
import com.Fishmod.mod_LavaCow.init.FURTagRegistry;

import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.FleeSunGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WendigoEntity extends MonsterEntity implements IAggressive {
	private static final DataParameter<Boolean> POUNCING = EntityDataManager.defineId(WendigoEntity.class, DataSerializers.BOOLEAN);
	public static final int ATTACK_TIMER = 20;
	
	private int attackTimer;
	/** set the Cooldown to pounce attack*/
	private int jumpTimer;
	/** 40: Attack with both hands 41: right hand 42: left hand */
	public byte AttackStance;
	
	public WendigoEntity(EntityType<? extends WendigoEntity> p_i48549_1_, World worldIn) {
        super(p_i48549_1_, worldIn);
        this.xpReward = 20;
    }
	
    @Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(POUNCING, Boolean.valueOf(false));
	}
	
    @Override
    protected void registerGoals() {
        if(!FURConfig.SunScreen_Mode.get())this.goalSelector.addGoal(1, new FleeSunGoal(this, 1.0D));
    	this.goalSelector.addGoal(2, new LeapAtTargetGoal(this, 0.4F));
    	this.goalSelector.addGoal(2, new AIWendigoLeapAtTarget(this, 0.7F));
        this.goalSelector.addGoal(3, new AttackGoal(this)); 
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
    	this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    	this.targetSelector.addGoal(2, new EntityAIPickupMeat<>(this, ItemEntity.class, true));
    	this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    	this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, CreatureEntity.class, 0, true, false, (p_210136_0_) -> {
    		ITag<EntityType<?>> tag = EntityTypeTags.getAllTags().getTag(FURTagRegistry.WENDIGO_TARGETS);
    		return tag != null && p_210136_0_ instanceof LivingEntity && ((LivingEntity)p_210136_0_).attackable() && p_210136_0_.getType().is(tag);
    	}));	 	
    }
    
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.25D)
        		.add(Attributes.FOLLOW_RANGE, 35.0D)
        		.add(Attributes.MAX_HEALTH, FURConfig.Wendigo_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, FURConfig.Wendigo_Attack.get())
        		.add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }
    
    public static boolean checkWendigoSpawnRules(EntityType<? extends WendigoEntity> p_223316_0_, IWorld p_223316_1_, SpawnReason p_223316_2_, BlockPos p_223316_3_, Random p_223316_4_) {
        return MonsterEntity.checkMonsterSpawnRules(p_223316_0_, (IServerWorld) p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_);//SpawnUtil.isAllowedDimension(this.dimension);
    }
    
    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    @Override
    public int getMaxSpawnClusterSize() {
       return 1;
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
    	this.noPhysics = (this.getY() > SpawnUtil.getHeight(this).getY() + 0.5D);
    	super.tick();
        this.noPhysics = false;
    	
        if (this.attackTimer > 0) {
            --this.attackTimer;
        }
        
        if (this.jumpTimer > 0) {
            --this.jumpTimer;
        }
        
        if (this.isPouncing() && this.isOnGround()) {
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
    	if(source.isFire())
    		return super.hurt(source, 2.0F * amount);
    	return super.hurt(source, amount);
    }
	
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT p_213386_5_) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Wendigo_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Wendigo_Attack.get());
    	this.setHealth(this.getMaxHealth());
        
    	return super.finalizeSpawn(p_213386_1_, difficulty, p_213386_3_, livingdata, p_213386_5_);
    }
        
    public int getAttackTimer() {
    	return this.attackTimer;
    }
    
	@Override
	public void setAttackTimer(int i) {
		this.attackTimer = i;
	}
    
    @OnlyIn(Dist.CLIENT)
    public void setAttackStance(byte byteIn) {
    	this.AttackStance = byteIn;
    }
    
    @OnlyIn(Dist.CLIENT)
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
    	if (id == 4 || id == 5 || id == 6) {
    		this.attackTimer = ATTACK_TIMER;
    		this.AttackStance = id;
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
    			   return this.leaper.isOnGround();
 	    	   } else {
 	    		   return false;
 	         }
 	      }
 	   }
 	   
 	   /**
 	    * Returns whether an in-progress EntityAIBase should continue executing
 	    */
 	   public boolean canContinueToUse() {
 		   return this.leaper.isOnGround() && this.leaper.jumpTimer >= 235;
 	   }
 	   
 	   /**
 	    * Keep ticking a continuous task that has already been started
 	    */
 	   public void tick() {
 		   Vector3d vector3d1 = new Vector3d(this.leapTarget.getX() - this.leaper.getX(), 0.0D, this.leapTarget.getZ() - this.leaper.getZ());
 		   float d0 = this.leaper.distanceTo(this.leapTarget);	

 		   if (this.leaper.jumpTimer == 235) {
 	 		   if (vector3d1.lengthSqr() > 1.0E-7D) {
 	 			   vector3d1 = vector3d1.normalize().scale(Math.min(d0, 15) * 0.2F);
 	 		   }

 	 		   this.leaper.setDeltaMovement(vector3d1.x, vector3d1.y + 0.3F + 0.1F * MathHelper.clamp(this.leapTarget.getEyeY() - this.leaper.getY(), 0, 2), vector3d1.z);
 	 		   this.leaper.setPouncing(true);
 		   }
 	   }

 	   /**
 	    * Execute a one shot task or start executing a continuous task
 	    */
 	   public void start() {		   
 		   Vector3d vector3d = this.leapTarget.position().subtract(this.leaper.position());
 		     
           this.leaper.getNavigation().stop();
           this.leaper.setYHeadRot(-((float) Math.atan2(vector3d.x, vector3d.z)) * (180F / (float) Math.PI));
           this.leaper.yBodyRot = this.leaper.getYHeadRot(); 
 		   this.leaper.playSound(FURSoundRegistry.WENDIGO_ATTACK, 0.75F, 0.8F);
 		   
 		   this.leaper.jumpTimer = 240;
 	   }  	
 	   
       /**
        * Reset the task's internal state. Called when this task is interrupted by another one
        */
       public void resetTask() {
       }
    }

	@Override
    public float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
        return 2.4F;
    }
    
	@Override
    public int getAmbientSoundInterval() {
        return 200;
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return FURSoundRegistry.WENDIGO_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FURSoundRegistry.WENDIGO_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.WENDIGO_DEATH;
    }

	@Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    	this.playSound(SoundEvents.ZOMBIE_STEP, 0.15F, 1.0F);
	}

    /**
     * Get this Entity's CreatureAttribute
     */
    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.UNDEAD;
    }  

    /**
     * Entity won't drop items or experience points if this returns false
     */
    @Override
    protected boolean shouldDropLoot() {
       return !this.isOnFire() || this.lastHurtByPlayer != null;
    }
    
    static class AttackGoal extends FURMeleeAttackGoal {
        public AttackGoal(CreatureEntity p_i46676_1_) {
           super(p_i46676_1_, 1.25D, false);
        }

    	protected int atkTimerMax() {
    		return ATTACK_TIMER;
    	}
    	
    	protected int atkTimerHit() {
    		return 10;
    	}
    	
    	protected byte atkTimerEvent() {
            ((WendigoEntity)this.mob).AttackStance = (byte)(4 + this.mob.getRandom().nextInt(3));
    		return ((WendigoEntity)this.mob).AttackStance;
    	}
    	
    	protected void dmgEvent(LivingEntity target) {
    		this.mob.swing(Hand.MAIN_HAND);
    		float f = (float)this.mob.getAttributeValue(Attributes.ATTACK_DAMAGE);
    		float f1 = (float)this.mob.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
    		float f2 = this.mob.level.getCurrentDifficultyAt(this.mob.blockPosition()).getEffectiveDifficulty();
    		
    		if (((WendigoEntity)this.mob).AttackStance == (byte)4) {
    			f *= 1.5F;
    			if (target instanceof PlayerEntity) {
    				((PlayerEntity) target).disableShield(true);
    			}
    		}
        	
    		boolean flag = target.hurt(DamageSource.mobAttack(this.mob), f);
    		if (flag) {
    			if (f1 > 0.0F && target instanceof LivingEntity) {
    				((LivingEntity)target).knockback(f1 * 0.5F, (double)MathHelper.sin(this.mob.yRot * ((float)Math.PI / 180F)), (double)(-MathHelper.cos(this.mob.yRot * ((float)Math.PI / 180F))));
    				this.mob.setDeltaMovement(this.mob.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
    			}

    			this.mob.doEnchantDamageEffects(this.mob, target);
    			this.mob.setLastHurtMob(target);
    			
                if (this.mob.getMainHandItem().isEmpty() && this.mob.isOnFire() && this.mob.getRandom().nextFloat() < f2 * 0.3F) {
                	target.setSecondsOnFire(2 * (int)f2);
                }
                
                if (target instanceof LivingEntity) {
                    ((LivingEntity)target).addEffect(new EffectInstance(Effects.HUNGER, 7 * 20 * (int)f2, 4));
                }
    		}   		         
    	}
	}
}
