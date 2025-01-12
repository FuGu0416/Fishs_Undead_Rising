package com.Fishmod.mod_LavaCow.entities.tameable;

import java.util.EnumSet;
import java.util.Random;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.entities.ai.FlyerFollowOwnerGoal;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NonTamedTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtByTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class GhostSwarmerEntity extends FURTameableEntity implements IFlyingAnimal {
	   
	public GhostSwarmerEntity(EntityType<? extends GhostSwarmerEntity> p_i48549_1_, World worldIn) {
		super(p_i48549_1_, worldIn);
	      this.moveControl = new FlyingMovementController(this, 20, true);
	}
	
    /**
     * Tries to move the entity towards the specified location.
     */
	@Override
    public void move(MoverType type, Vector3d p_213315_2_) {
        super.move(type, p_213315_2_);
        this.checkInsideBlocks();
    }
	
    @Override
    protected boolean isCommandable() {
    	return false;
    }
	
    @Override
    protected void registerGoals() {
    	super.registerGoals();        
        this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(3, new GhostSwarmerEntity.AIChargeAttack());
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));  
        this.goalSelector.addGoal(8, new GhostSwarmerEntity.AIMoveRandom());
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
    	this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this).setAlertOthers(GhostSwarmerEntity.class));
        this.targetSelector.addGoal(4, new NonTamedTargetGoal<>(this, PlayerEntity.class, false, (p_213440_0_) -> {
            return true;
        }));
    }
    
    @Override
    protected Goal wanderGoal() {
    	return new GhostSwarmerEntity.AIMoveRandom();
    }
    
    @Override
    protected Goal followGoal() {
    	return new FlyerFollowOwnerGoal(this, 1.0D, 10.0F, 4.0F, true, 24.0D);
    }
    
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.3D)
        		.add(Attributes.FLYING_SPEED, 0.6F)
        		.add(Attributes.FOLLOW_RANGE, 16.0D)
        		.add(Attributes.MAX_HEALTH, FURConfig.GhostSwarmer_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, FURConfig.GhostSwarmer_Health.get())
        		.add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }
    
    public static boolean checkWispSpawnRules(EntityType<? extends GhostSwarmerEntity> p_223316_0_, IWorld p_223316_1_, SpawnReason p_223316_2_, BlockPos p_223316_3_, Random p_223316_4_) {
        return FURTameableEntity.checkMonsterSpawnRules(p_223316_0_, (IServerWorld) p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_);//SpawnUtil.isAllowedDimension(this.dimension);
    }
    
    @Override
    protected void defineSynchedData() {
    	super.defineSynchedData();
    }
    
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void aiStep() {
        super.aiStep();
    }
	
	@Override
    public void tick() {
    	this.noPhysics = true;
    	super.tick();
        this.noPhysics = false;
        this.setNoGravity(true);
	}
	
	@Override
    public boolean doHurtTarget(Entity p_70652_1_) {
        this.playSound(FURSoundRegistry.SWARMER_ATTACK, 1.0F, 1.0F);
        return super.doHurtTarget(p_70652_1_);
	}
    
    @Override
    public boolean causeFallDamage(float p_225503_1_, float p_225503_2_) {
        return false;
	}

    @Override
	protected void checkFallDamage(double p_184231_1_, boolean p_184231_3_, BlockState p_184231_4_, BlockPos p_184231_5_) {
	}
    
    protected ItemStack getFishBucket() {
    	ItemStack stack = new ItemStack(FURItemRegistry.GHOSTSWARMER_BUCKET);
        CompoundNBT compoundnbt = new CompoundNBT();
        this.addAdditionalSaveData(compoundnbt);
        stack.getOrCreateTag().put("GhostSwarmerData", compoundnbt);
        
        if (this.hasCustomName()) {
            stack.setHoverName(this.getCustomName());
        }
        
        return stack;
    }
    
    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
    	ItemStack itemstack = player.getItemInHand(hand);   	
        
        if (itemstack.getItem() == Items.BUCKET && this.isAlive()) {
        	this.tame(player);
            this.playSound(SoundEvents.BUCKET_FILL_FISH, 1.0F, 1.0F);
            itemstack.shrink(1);
            ItemStack itemstack1 = this.getFishBucket();
            if (!this.level.isClientSide) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayerEntity) player, itemstack1);
            }

            if (itemstack.isEmpty()) {
                player.setItemInHand(hand, itemstack1);
            } else if (!player.inventory.add(itemstack1)) {
                player.drop(itemstack1, false);
            }

            this.remove();            
            return ActionResultType.sidedSuccess(this.level.isClientSide);
        }
        
        return super.mobInteract(player, hand);
    }
       
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT p_213386_5_) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.GhostSwarmer_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.GhostSwarmer_Attack.get());
    	this.setHealth(this.getMaxHealth());

    	this.setDeltaMovement(this.getDeltaMovement().add(0.0D, 0.5D, 0.0D));
    	
    	return super.finalizeSpawn(worldIn, difficulty, p_213386_3_, livingdata, p_213386_5_);
    }
    
    protected PathNavigator createNavigation(World p_175447_1_) {
        FlyingPathNavigator flyingpathnavigator = new FlyingPathNavigator(this, p_175447_1_) {
           public boolean isStableDestination(BlockPos p_188555_1_) {
              return !this.level.getBlockState(p_188555_1_.below()).getMaterial().equals(Material.AIR);
           }
        };
        flyingpathnavigator.setCanOpenDoors(false);
        flyingpathnavigator.setCanFloat(true);
        flyingpathnavigator.setCanPassDoors(true);
        return flyingpathnavigator;
	}
    
    @Override
    protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
        return p_213348_2_.height * 0.5F;
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
    	return FURSoundRegistry.SWARMER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FURSoundRegistry.BANSHEE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.FURNACE_FIRE_CRACKLE;
    }
    
    protected SoundEvent getSpellSound() {
        return null;
    }
    
	@Override
	protected boolean isMovementNoisy() {
		return false;
	}
	
	@Override
    protected void playStepSound(BlockPos pos, BlockState state) {
	} 
    
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
    }
	
	@Override
    public float getWalkTargetValue(BlockPos p_205022_1_, IWorldReader p_205022_2_) {
    	if (p_205022_2_.getBrightness(LightType.BLOCK, p_205022_1_) > 11) {
    		return -1.0F;
    	} else {
    		return super.getWalkTargetValue(p_205022_1_, p_205022_2_);
    	}
    }
	
    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.UNDEAD;
    }
    
    class AIChargeAttack extends Goal {
    	
        public AIChargeAttack() {
        	this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean canUse() {
            if (GhostSwarmerEntity.this.getTarget() != null/* && !WispEntity.this.getMoveControl().hasWanted() && WispEntity.this.getRandom().nextInt(7) == 0*/) {
                return GhostSwarmerEntity.this.distanceToSqr(GhostSwarmerEntity.this.getTarget()) > 4.0D;
            } else {
                return false;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return GhostSwarmerEntity.this.getMoveControl().hasWanted() && GhostSwarmerEntity.this.getTarget() != null && GhostSwarmerEntity.this.getTarget().isAlive();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            LivingEntity LivingEntity = GhostSwarmerEntity.this.getTarget();
            Vector3d vec3d = LivingEntity.getEyePosition(1.0F);
            GhostSwarmerEntity.this.moveControl.setWantedPosition(vec3d.x, vec3d.y, vec3d.z, 1.2D);
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            LivingEntity livingentity = GhostSwarmerEntity.this.getTarget();
            if (GhostSwarmerEntity.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
               GhostSwarmerEntity.this.doHurtTarget(livingentity);
            } else {
               double d0 = GhostSwarmerEntity.this.distanceToSqr(livingentity);
               if (d0 < 9.0D) {
                  Vector3d vector3d = livingentity.getEyePosition(1.0F);
                  GhostSwarmerEntity.this.moveControl.setWantedPosition(vector3d.x, vector3d.y, vector3d.z, 1.0D);
               }
            }
        }
    }
    
    class AIMoveRandom extends Goal {
    	AIMoveRandom() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    	}

    	public boolean canUse() {
    		return GhostSwarmerEntity.this.navigation.isDone() && GhostSwarmerEntity.this.random.nextInt(10) == 0;
    	}

    	public boolean canContinueToUse() {
            return GhostSwarmerEntity.this.navigation.isInProgress();
    	}

    	public void start() {
            Vector3d vector3d = this.findPos();
            if (vector3d != null) {
               GhostSwarmerEntity.this.navigation.moveTo(GhostSwarmerEntity.this.navigation.createPath(new BlockPos(vector3d), 1), 1.0D);
            }
    	}

    	@Nullable
    	private Vector3d findPos() {
            Vector3d vector3d = GhostSwarmerEntity.this.getViewVector(0.0F);
            Vector3d vector3d2 = RandomPositionGenerator.getAboveLandPos(GhostSwarmerEntity.this, 8, 7, vector3d, ((float)Math.PI / 2F), 2, 1);
            return vector3d2 != null ? vector3d2 : RandomPositionGenerator.getAirPos(GhostSwarmerEntity.this, 8, 4, -2, vector3d, (double)((float)Math.PI / 2F));
    	}
    }
}
