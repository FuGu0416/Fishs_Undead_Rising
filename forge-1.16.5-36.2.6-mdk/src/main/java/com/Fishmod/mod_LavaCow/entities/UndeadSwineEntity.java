package com.Fishmod.mod_LavaCow.entities;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.init.FURBlockRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;
import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.ai.goal.FleeSunGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class UndeadSwineEntity extends MonsterEntity implements IAggressive {
	
	/** Used to control movement as well as wool regrowth. Set to 40 on handleHealthUpdate and counts down with each tick. */
	private int sheepTimer;
	private int attackTimer;
	private EatGrassGoal EatGrassGoal;
	private AIChargeAttack entityAICharge;
	private final List<Block> DIGOUT_SHROOM = Lists.newArrayList(
				FURBlockRegistry.GLOWSHROOM, 
				FURBlockRegistry.CORDY_SHROOM, 
				FURBlockRegistry.VEIL_SHROOM, 
				Blocks.BROWN_MUSHROOM, 
				Blocks.RED_MUSHROOM
			);
	
    public UndeadSwineEntity(EntityType<? extends UndeadSwineEntity> p_i48549_1_, World worldIn) {
        super(p_i48549_1_, worldIn);       
        this.xpReward = 20;
    }
    
    @Override
    protected void registerGoals() {
    	this.EatGrassGoal = new EatGrassGoal(this);
    	this.entityAICharge = new UndeadSwineEntity.AIChargeAttack(this);
    	this.goalSelector.addGoal(2, this.entityAICharge);
        this.goalSelector.addGoal(4, this.EatGrassGoal);
        if(!FURConfig.SunScreen_Mode.get())this.goalSelector.addGoal(4, new FleeSunGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new UndeadSwineEntity.AttackGoal(this));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
    	this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    	this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }
    
    @Override
    protected void customServerAiStep() {
        this.sheepTimer = this.EatGrassGoal.getEatAnimationTick();
        super.customServerAiStep();
	}
 
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.25D)
        		.add(Attributes.FOLLOW_RANGE, 16.0D)
        		.add(Attributes.MAX_HEALTH, FURConfig.UndeadSwine_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, FURConfig.UndeadSwine_Attack.get())
        		.add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }
    
    public static boolean checkUndeadSwineSpawnRules(EntityType<? extends UndeadSwineEntity> p_223316_0_, IWorld p_223316_1_, SpawnReason p_223316_2_, BlockPos p_223316_3_, Random p_223316_4_) {
        return MonsterEntity.checkMonsterSpawnRules(p_223316_0_, (IServerWorld) p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_);//SpawnUtil.isAllowedDimension(this.dimension);
    }
    
    @Override
    public void push(Entity entityIn) {
    	super.push(entityIn);
		if(this.entityAICharge != null && this.entityAICharge.isCharging()) {
			this.doHurtTarget(entityIn);
			entityIn.setDeltaMovement(entityIn.getDeltaMovement().add(this.getLookAngle().normalize().multiply(0.8D, 1.6D, 0.8D)));
		}
    }
    
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void tick() {
        if (this.level.isClientSide()) {
            this.sheepTimer = Math.max(0, this.sheepTimer - 1);
        }
        
        if (this.attackTimer > 0) {
            --this.attackTimer;
        }
                  
    	if (!FURConfig.SunScreen_Mode.get() && this.isSunBurnTick()) {
    		this.setSecondsOnFire(40);
        }

        super.tick();
    }
    
    @Override
	public boolean doHurtTarget(Entity entityIn) {
        boolean flag = super.doHurtTarget(entityIn);     
        this.attackTimer = 10;
        this.level.broadcastEntityEvent(this, (byte)4);
        if (flag) {
            float f = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
            if (this.getMainHandItem().isEmpty() && this.isOnFire() && this.random.nextFloat() < f * 0.3F) {
            	entityIn.setSecondsOnFire(2 * (int)f);
            }
        }

        return flag;
	}
    
    @OnlyIn(Dist.CLIENT)
    public boolean isDigging() {
    	return this.sheepTimer > 0;
    }
    
    @OnlyIn(Dist.CLIENT)
    public int getAttackTimer() {
       return this.attackTimer;
    }
    
	@Override
	public void setAttackTimer(int i) {
		this.attackTimer = i;
	}
    
    /**
     * Handler for {@link World#setEntityState}
     */
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {       
    	if (id == 4) {
            this.attackTimer = 10;
        } else if (id == 10) {
          this.sheepTimer = 40;
        } else {
          super.handleEntityEvent(id);
        }
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT p_213386_5_) {
        livingdata = super.finalizeSpawn(p_213386_1_, difficulty, p_213386_3_, livingdata, p_213386_5_);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.UndeadSwine_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.UndeadSwine_Attack.get());
    	this.setHealth(this.getMaxHealth());
    	
        if (this.getRandom().nextInt(100) == 0) {
            SkeletonEntity entityRider = EntityType.SKELETON.create(this.level);
            entityRider.moveTo(this.getX(), this.getY(), this.getZ(), this.yRot, 0.0F);
            entityRider.finalizeSpawn(p_213386_1_, difficulty, SpawnReason.JOCKEY, (ILivingEntityData)null, (CompoundNBT)null);
            p_213386_1_.addFreshEntity(entityRider);
            entityRider.startRiding(this);
        } else if (this.getRandom().nextInt(100) == 1) {
            ZombieEntity entityRider = EntityType.ZOMBIE.create(this.level);
            entityRider.moveTo(this.getX(), this.getY(), this.getZ(), this.yRot, 0.0F);
            entityRider.finalizeSpawn(p_213386_1_, difficulty, SpawnReason.JOCKEY, (ILivingEntityData)null, (CompoundNBT)null);
            p_213386_1_.addFreshEntity(entityRider);
            entityRider.startRiding(this, true);
        }

        return livingdata;
    }
    
    static class AIChargeAttack extends Goal {
        private final UndeadSwineEntity charger;
        private int attackStep;
        private int attackTime;
        
        public AIChargeAttack(UndeadSwineEntity entityIn) {
           this.charger = entityIn;
           this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        @Override
        public boolean canUse() {
        	LivingEntity LivingEntity = this.charger.getTarget();
        	return LivingEntity != null && LivingEntity.isAlive() && LivingEntity.getY() <= this.charger.getY() && this.charger.distanceToSqr(LivingEntity) > 6.0D;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        @Override
        public void start() {
           this.attackStep = 0;
           this.charger.playSound(FURSoundRegistry.UNDEADSWINE_CHARGE, 1.0F, 1.0F);
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        @Override
        public void stop() {
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        @Override
        public void tick() {
           --this.attackTime;
           LivingEntity LivingEntity = this.charger.getTarget();

           if (LivingEntity != null) {
               double d0 = this.charger.distanceTo(LivingEntity);

               if (this.charger.getBoundingBox().intersects(LivingEntity.getBoundingBox())) {
                  if (this.attackTime <= 0) {
                     this.attackTime = 30;
                     this.charger.doHurtTarget(LivingEntity);
                     LivingEntity.setDeltaMovement(LivingEntity.getDeltaMovement().add(this.charger.getLookAngle().normalize().multiply(0.8D, 1.6D, 0.8D)));
                  }
               } else if (d0 < this.getFollowDistance() * this.getFollowDistance()) {
                  double v = 4.0D;
            	  double d1 = v * (LivingEntity.getX() - this.charger.getX())/d0;
                  double d2 = v * (LivingEntity.getY() - this.charger.getY())/d0;
                  double d3 = v * (LivingEntity.getZ() - this.charger.getZ())/d0;
                  if (this.attackTime <= 0) {
                     ++this.attackStep;
                     if (this.attackStep > 20) {
                    	 this.charger.getMoveControl().setWantedPosition(this.charger.getX() + d1, this.charger.getY() + d2, this.charger.getZ() + d3, 2.0D);
                     } else if(this.attackStep > 100) {
                    	 this.attackTime = 60;
                    	 this.attackStep = 0;
                     }
                  }
                  this.charger.getLookControl().setLookAt(LivingEntity, 100.0F, 100.0F);
               } else {
                  this.charger.getMoveControl().setWantedPosition(LivingEntity.getX(), LivingEntity.getY(), LivingEntity.getZ(), 1.0D);
               }
           }

           super.tick();
        }
        
        private double getFollowDistance() {
        	ModifiableAttributeInstance iattributeinstance = this.charger.getAttribute(Attributes.FOLLOW_RANGE);
            return iattributeinstance == null ? 16.0D : iattributeinstance.getBaseValue();
        }
        
        public boolean isCharging() {
        	return this.attackStep > 20;
        }         
    }
    
    static class AttackGoal extends MeleeAttackGoal {
        public AttackGoal(UndeadSwineEntity entityIn) {
           super(entityIn, 1.0D, true);
        }
        
        @Override
        protected double getAttackReachSqr(LivingEntity p_179512_1_) {
            return (double)(this.mob.getBbWidth() * this.mob.getBbWidth() + p_179512_1_.getBbWidth());
        }
	}  
    
    /**
     * This function applies the benefits of growing back wool and faster growing up to the acting entity. (This function
     * is used in the AIEatGrass)
     */
    public void ate() {
    	this.spawnAtLocation(new ItemStack(DIGOUT_SHROOM.get(this.random.nextInt(DIGOUT_SHROOM.size()))).getItem(), 1); 
    }
    
    /**
     * Get this Entity's CreatureAttribute
     */
    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.UNDEAD;
    }
    
	@Override
    public float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
        return this.getBbHeight() * 0.6F;
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ZOGLIN_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FURSoundRegistry.UNDEADSWINE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.UNDEADSWINE_DEATH;
    }

	@Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    	this.playSound(SoundEvents.ZOGLIN_STEP, 0.15F, 1.0F);
	}

    /**
     * Entity won't drop items or experience points if this returns false
     */
    @Override
    protected boolean shouldDropLoot() {
       return !this.isOnFire() || this.lastHurtByPlayer != null;
    }
}
