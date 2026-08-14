package com.Fishmod.fur.entities;

import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;

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
	public IsnachiEntity(EntityType<? extends IsnachiEntity> entityType, Level worldIn) {
        super(entityType, worldIn);
    }

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.setSkin(1);
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
    
    public static boolean checkIsnachiSpawnRules(EntityType<? extends IsnachiEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return Monster.checkMonsterSpawnRules(entityType, level, spawnType, pos, random);
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
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
    	if(this.getTarget() != null) {
    		return false;
    	}  	
    	
    	return super.causeFallDamage(fallDistance, multiplier, source);
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
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Foglet_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Foglet_Attack.get());
    	this.setHealth(this.getMaxHealth());
		this.setSkin(1);
    	
 	   	return super.finalizeSpawn(level, difficulty, spawnType, livingdata, tag);
 	}
        
	@Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        if(getIsHanging())
        	return this.getBbHeight() * 0.0F;
        else
        	return this.getBbHeight() * 0.8F;
    }
		
	@Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
    	if (level.getBlockState(pos).is(BlockTags.LOGS)) {
    		return 10.0F;
    	} else {
    		return super.getWalkTargetValue(pos, level);
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
		public DropGoal(Mob mob, Class<T> targetType, boolean mustSee) {
			super(mob, targetType, mustSee);
		}
		
		protected AABB getTargetSearchArea(double targetDistance) {
			return this.mob.getBoundingBox().inflate(targetDistance, 64.0D, targetDistance);
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
