package com.Fishmod.fur.entities;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class IsnachiEntity extends FogletEntity {	
	public IsnachiEntity(EntityType<? extends IsnachiEntity> p_i48549_1_, Level worldIn) {
        super(p_i48549_1_, worldIn);
    }
	
	@Override
    protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new AIClimbimgTree());
    }
	
	@Override
    protected void applyEntityAI() {
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new IsnachiEntity.DropGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(4, new IsnachiEntity.DropGoal<>(this, AbstractVillager.class, false));
        this.targetSelector.addGoal(4, new IsnachiEntity.DropGoal<>(this, IronGolem.class, true));
    }
    
    public static AttributeSupplier.Builder createAttributesIsnachi() {    	
        return Monster.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.25D)
        		.add(Attributes.FOLLOW_RANGE, 16.0D)
        		.add(Attributes.MAX_HEALTH, 16.0D)
        		.add(Attributes.ATTACK_DAMAGE, 2.0D);
    }   
    
    public static boolean checkIsnachiSpawnRules(EntityType<? extends IsnachiEntity> p_223316_0_, ServerLevelAccessor p_223316_1_, MobSpawnType p_223316_2_, BlockPos p_223316_3_, RandomSource p_223316_4_) {
        return Monster.checkMonsterSpawnRules(p_223316_0_, p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_);
    }    
	
    /**
     * Keep ticking a continuous task that has already been started
     */
    @Override
    public void tick() {
    	super.tick();
    	
        if (this.getIsHanging()) {
        	this.setDeltaMovement(Vec3.ZERO);
    	}   	
    }
	
	@Override
    public boolean causeFallDamage(float p_150093_, float p_150094_, DamageSource p_150095_) {
    	if(this.getTarget() != null) {
    		return false;
    	}  	
    	
    	return super.causeFallDamage(p_150093_, p_150094_, p_150095_);
    }
    
    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean hurt(DamageSource source, float amount) {
    	if (super.hurt(source, amount)) {
            LivingEntity LivingEntity = this.getTarget();
        	
            if (LivingEntity == null && source.getDirectEntity() instanceof LivingEntity) {
                LivingEntity = (LivingEntity)source.getDirectEntity();
            }
            
            if(this.getIsHanging()) {
            	this.setIsHanging(false);
            	this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(16.0D);
            }

            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
	@Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_213386_1_, DifficultyInstance difficulty, MobSpawnType p_213386_3_, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag p_213386_5_) {
    	this.setSkin(1);
    	
 	   	return super.finalizeSpawn(p_213386_1_, difficulty, p_213386_3_, livingdata, p_213386_5_);
 	}
        
	@Override
    protected float getStandingEyeHeight(Pose p_213348_1_, EntityDimensions p_213348_2_) {
        if(getIsHanging())
        	return this.getBbHeight() * 0.0F;
        else
        	return this.getBbHeight() * 0.8F;
    }
		
	@Override
    public float getWalkTargetValue(BlockPos p_205022_1_, LevelReader p_205022_2_) {
    	if (p_205022_2_.getBlockState(p_205022_1_).is(BlockTags.LOGS)) {
    		return 10.0F;
    	} else {
    		return super.getWalkTargetValue(p_205022_1_, p_205022_2_);
    	}
    }
    
    public class AIClimbimgTree extends Goal {    	
    	public AIClimbimgTree() {
        }
    	
    	private boolean canClimb() {
    		return IsnachiEntity.this.level().getBlockState(IsnachiEntity.this.blockPosition().above()).isAir() 
    				&& !IsnachiEntity.this.level().canSeeSky(IsnachiEntity.this.blockPosition());
    	}

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
    	@Override
        public boolean canUse() {          
            return IsnachiEntity.this.onGround() 
    				&& !IsnachiEntity.this.isAggressive()
    				&& IsnachiEntity.this.getTarget() == null
    				&& this.canClimb();
        }
    	
        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        @Override
        public boolean canContinueToUse() {
            return !IsnachiEntity.this.isAggressive()
    				&& IsnachiEntity.this.getTarget() == null
    				&& this.canClimb();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
    	@Override
        public void start() {
            super.start();
            IsnachiEntity.this.getNavigation().stop();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
    	@Override
        public void stop() {
        	super.stop();
        	if (!IsnachiEntity.this.isAggressive()) {
	            IsnachiEntity.this.setIsHanging(true);
	            IsnachiEntity.this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(4.0D);
        	}
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
    	@Override
        public void tick() {		
        	if (IsnachiEntity.this.getDeltaMovement().y < 0.0D) {
        		IsnachiEntity.this.setPosRaw(IsnachiEntity.this.getX(), IsnachiEntity.this.getY() + 1.0D, IsnachiEntity.this.getZ());
        	}
        }
    }
    
    public class DropGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
		public DropGoal(Mob p_26064_, Class<T> p_26065_, boolean p_26066_) {
			super(p_26064_, p_26065_, p_26066_);
		}
		
		protected AABB getTargetSearchArea(double p_26069_) {
			return this.mob.getBoundingBox().inflate(p_26069_, 64.0D, p_26069_);
		}   	
		
		public void start() {
        	if (((FogletEntity) this.mob).getIsHanging()) {
        		((FogletEntity) this.mob).setIsHanging(false);
        		this.mob.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(16.0D);
        	}
        	
			super.start();
		}
    }
    
    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
	}
}
