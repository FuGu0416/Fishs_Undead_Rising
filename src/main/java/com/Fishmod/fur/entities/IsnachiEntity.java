package com.Fishmod.fur.entities;

import java.util.List;
import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
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
    
    public static AttributeSupplier.Builder createAttributesIsnachi() {    	
        return Monster.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.25D)
        		.add(Attributes.FOLLOW_RANGE, 16.0D)
        		.add(Attributes.MAX_HEALTH, 16.0D/*FURConfig.Isnachi_Health.get()*/)
        		.add(Attributes.ATTACK_DAMAGE, 2.0D/*FURConfig.Isnachi_Attack.get()*/);
    }   
    
    public static boolean checkIsnachiSpawnRules(EntityType<? extends IsnachiEntity> p_223316_0_, ServerLevelAccessor p_223316_1_, MobSpawnType p_223316_2_, BlockPos p_223316_3_, RandomSource p_223316_4_) {
        return Monster.checkMonsterSpawnRules(p_223316_0_, p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_);
    }
    
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void aiStep() {
        super.aiStep();
        
        if (this.getIsHanging()) {
        	this.setDeltaMovement(Vec3.ZERO);

	        if(!this.isPassenger()) {
		        if(this.level().canSeeSky(this.blockPosition())) {
		        	this.setIsHanging(false);
		        }
		        
		        List<Entity> list = this.level().getEntities(this, this.getBoundingBox().expandTowards(2.0D, 35.0D, 2.0D));
		        
	        	for (Entity entity1 : list) {
	        		if (entity1.getY() < this.getY() && ((entity1 instanceof Player && !((Player)entity1).isCreative()) || entity1 instanceof AbstractVillager)) {
	        			this.setTarget((LivingEntity) entity1);
	        			this.setIsHanging(false);
	        			break;
	        		}
	        	}  
	        }
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
            
            if(this.getIsClimbing()) {
            	this.setIsClimbing(false);
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
        //this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Foglet_Health.get());
        //this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Foglet_Attack.get());
    	this.setHealth(this.getMaxHealth());
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
        private BlockPos TreePos;
    	
    	public AIClimbimgTree() {
        }
    	
    	private boolean canClimb() {
    		return !IsnachiEntity.this.level().getBlockState(IsnachiEntity.this.blockPosition().above()).canOcclude() && IsnachiEntity.this.level().getBlockState(IsnachiEntity.this.blockPosition().above()).is(BlockTags.LEAVES);
    	}

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
    	@Override
        public boolean canUse() {
    		
    		if (IsnachiEntity.this.getSkin() != 1) {
    			return false;
    		}
    		
            int i = (int) Math.floor(IsnachiEntity.this.getX());
            int j = (int) Math.floor(IsnachiEntity.this.getY());
            int k = (int) Math.floor(IsnachiEntity.this.getZ());
            BlockPos blockpos = new BlockPos(i, j, k);
            
            TreePos = null;
            
            for(int x = -1 ; x <= 1 ; x++)
            	for(int z = -1 ; z <= 1 ; z++) {
            		if(IsnachiEntity.this.level().getBlockState(blockpos.offset(x, 0, z)).is(BlockTags.LOGS)) {
            			TreePos = new BlockPos(x, 0, z);
            			break;
            		}
            	}
            
            return !IsnachiEntity.this.isOnFire() 
            		&& !IsnachiEntity.this.isAggressive() 
            		&& !IsnachiEntity.this.level().canSeeSky(blockpos) 
            		&& TreePos != null 
            		&& this.canClimb();
        }
    	
        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        @Override
        public boolean canContinueToUse() {
            return this.canClimb();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
    	@Override
        public void start() {
            super.start();
            IsnachiEntity.this.setIsClimbing(true);
            IsnachiEntity.this.getNavigation().stop();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
    	@Override
        public void stop() {
        	super.stop();
            IsnachiEntity.this.setIsClimbing(false);
            IsnachiEntity.this.setIsHanging(true);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
    	@Override
        public void tick() {		
        	if (IsnachiEntity.this.getDeltaMovement().y < 0.0D) {
        		IsnachiEntity.this.setPosRaw(IsnachiEntity.this.getX(), IsnachiEntity.this.getY() + 0.2D, IsnachiEntity.this.getZ());
        	}
        	
        	if (TreePos != null) {
            	IsnachiEntity.this.yBodyRot = (TreePos.getX() * 270.0F + (float) Math.toDegrees(Math.atan(TreePos.getZ() / (TreePos.getX() + 0.0000001D)))) % 360.0F;
        	}
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
