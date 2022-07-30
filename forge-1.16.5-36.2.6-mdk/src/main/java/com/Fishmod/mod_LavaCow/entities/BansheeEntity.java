package com.Fishmod.mod_LavaCow.entities;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.init.FUREffectRegistry;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;

import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.FleeSunGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BansheeEntity extends MonsterEntity implements IAggressive{
	
	private static final DataParameter<Byte> CASTING = EntityDataManager.defineId(BansheeEntity.class, DataSerializers.BYTE);
	private int attackTimer = 0;
	protected int spellTicks;
	
	public BansheeEntity(EntityType<? extends BansheeEntity> p_i48549_1_, World worldIn) {
        super(p_i48549_1_, worldIn);
        this.moveControl = new BansheeEntity.AIMoveControl(this);
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
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new AICastingApell());
        this.goalSelector.addGoal(2, new BansheeEntity.AIUseSpell());
        this.goalSelector.addGoal(3, new BansheeEntity.AIChargeAttack());  
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));    
        if(!FURConfig.SunScreen_Mode.get())this.goalSelector.addGoal(5, new FleeSunGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new BansheeEntity.AIMoveRandom());
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
    	this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    	this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }
    
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.25D)
        		.add(Attributes.FOLLOW_RANGE, 32.0D)
        		.add(Attributes.MAX_HEALTH, FURConfig.Banshee_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, FURConfig.Banshee_Attack.get());
    }
    
    public static boolean checkBansheeSpawnRules(EntityType<? extends BansheeEntity> p_223316_0_, IWorld p_223316_1_, SpawnReason p_223316_2_, BlockPos p_223316_3_, Random p_223316_4_) {
        return MonsterEntity.checkMonsterSpawnRules(p_223316_0_, (IServerWorld) p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_);//SpawnUtil.isAllowedDimension(this.dimension);
    }
    
    @Override
    protected void defineSynchedData() {
    	super.defineSynchedData();
        this.getEntityData().define(CASTING, Byte.valueOf((byte)0));
    }
    
    public boolean isSpellcasting() {
    	return (((Byte)this.getEntityData().get(CASTING)).byteValue() & 1) != 0;
    }
    
    @OnlyIn(Dist.CLIENT)
    public boolean isSpellcastingC() {
    	return (((Byte)this.getEntityData().get(CASTING)).byteValue() & 1) != 0;
    }
    
    public int getSpellTicks() {
        return this.spellTicks;
    }
    
    @Nullable
    protected IParticleData ParticleType() {
    	return new RedstoneParticleData(0.20F, 0.21F, 0.23F, 0.6F);
    }
	
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void aiStep() {
        super.aiStep();
        
        if(this.tickCount % 2 == 0 && this.ParticleType() != null) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            this.level.addParticle(this.ParticleType(), this.getRandomX(1.0D), this.getRandomY() + 1.0D, this.getRandomZ(1.0D), d0, d1, d2);
        }     	
        
        if(this.getType() == FUREntityRegistry.BANSHEE && this.getSpellTicks() > 8 && this.getSpellTicks() < 13) {
        	this.level.addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY() + this.getBbHeight(), this.getZ(), 0.0D, 1.0D, 0.0D);
        }
    }
    
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void tick() {
        if (this.spellTicks > 0) {
            --this.spellTicks;
        }
        
        if (this.attackTimer > 0) {
        	--this.attackTimer;
        }
    	
    	if (!FURConfig.SunScreen_Mode.get() && this.isSunBurnTick()) {
    		this.setSecondsOnFire(8);
        }
    	
    	this.noPhysics = (this.getY() > SpawnUtil.getHeight(this).getY() + 0.5D && this.getTarget()== null);
    	super.tick();
        this.noPhysics = false;
        this.setNoGravity(true);
    }

    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT p_213386_5_) {
        if(this.getType().equals(FUREntityRegistry.BANSHEE)) {
	    	this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Banshee_Health.get());
	        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Banshee_Attack.get());
	    	this.setHealth(this.getMaxHealth());
        }
        
    	return super.finalizeSpawn(p_213386_1_, difficulty, p_213386_3_, livingdata, p_213386_5_);
    }
    
    @Override
	public int getAttackTimer() {
		return this.attackTimer;
	}
    
	@Override
	public void setAttackTimer(int i) {
		this.attackTimer = i;
	}
    
    @Override
    public boolean causeFallDamage(float p_225503_1_, float p_225503_2_) {
        return false;
	}

    @Override
	protected void checkFallDamage(double p_184231_1_, boolean p_184231_3_, BlockState p_184231_4_, BlockPos p_184231_5_) {
	}
	
    /**
     * Handler for {@link World#setEntityState}
     */
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        if (id == 10) {
        	this.spellTicks = 30;
        } else if (id == 4) {
            this.attackTimer = 20;
        } else {
            super.handleEntityEvent(id);
        }
    }
    
    public void setIsCasting(boolean isHanging) {
        byte b0 = ((Byte)this.getEntityData().get(CASTING)).byteValue();

        if (isHanging) {
            this.getEntityData().set(CASTING, Byte.valueOf((byte)(b0 | 1)));
        } else {
            this.getEntityData().set(CASTING, Byte.valueOf((byte)(b0 & -2)));
        }
    }
    
    public class AICastingApell extends Goal {
        public AICastingApell() {
        	this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean canUse() {
            return BansheeEntity.this.getSpellTicks() > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            super.start();
            BansheeEntity.this.setIsCasting(true);
            BansheeEntity.this.navigation.stop();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
            super.stop();
            BansheeEntity.this.setIsCasting(false);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (BansheeEntity.this.getTarget() != null) {
                BansheeEntity.this.getLookControl().setLookAt(BansheeEntity.this.getTarget(), (float)BansheeEntity.this.getMaxHeadYRot(), (float)BansheeEntity.this.getMaxHeadXRot());
            }
            
            if(BansheeEntity.this.getSpellTicks() > 8 && BansheeEntity.this.getSpellTicks() < 13) {
            	BansheeEntity.this.level.addParticle(ParticleTypes.EXPLOSION, BansheeEntity.this.getX(), BansheeEntity.this.getY() + BansheeEntity.this.getBbHeight(), BansheeEntity.this.getZ(), 0.0D, 1.0D, 0.0D);
            }
        }
    }
    
    public class AIUseSpell extends Goal {
        protected int spellWarmup;
        protected int spellCooldown;

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean canUse() {
            if (BansheeEntity.this.getTarget() == null) {
                return false;
            } else if (BansheeEntity.this.isSpellcasting()) {
                return false;
            } else {
            	return BansheeEntity.this.tickCount >= this.spellCooldown && BansheeEntity.this.distanceTo(BansheeEntity.this.getTarget()) < 3.0F;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return BansheeEntity.this.getTarget() != null && this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.spellWarmup = this.getCastWarmupTime();
            BansheeEntity.this.spellTicks = this.getCastingTime();
            BansheeEntity.this.level.broadcastEntityEvent(BansheeEntity.this, (byte)10);
            this.spellCooldown = BansheeEntity.this.tickCount + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();

            if (soundevent != null) {
                BansheeEntity.this.playSound(soundevent, 1.0F, 1.0F);
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            --this.spellWarmup;

            if (this.spellWarmup == 5) {
                this.castSpell();
                BansheeEntity.this.playSound(BansheeEntity.this.getSpellSound(), 4.0F, 1.2F);                        
            }
        }

        protected void castSpell() {
        	List<Entity> list = BansheeEntity.this.level.getEntities(BansheeEntity.this, BansheeEntity.this.getBoundingBox().inflate(FURConfig.Banshee_Ability_Radius.get()));
        	BansheeEntity.this.level.broadcastEntityEvent(BansheeEntity.this, (byte)11);
        	
        	for (Entity entity1 : list) {
        		if (entity1 instanceof LivingEntity) {                 
        			if (((LivingEntity)entity1).getMobType() != CreatureAttribute.UNDEAD) {
        				float local_difficulty = BansheeEntity.this.level.getCurrentDifficultyAt(BansheeEntity.this.blockPosition()).getEffectiveDifficulty();
        				((LivingEntity)entity1).addEffect(new EffectInstance(FUREffectRegistry.FEAR, 2 * 20 * (int)local_difficulty, 2));       				
        				((LivingEntity)entity1).hurt(DamageSource.mobAttack(BansheeEntity.this).setMagic(), (float) BansheeEntity.this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 1.0F);
        			}
        		}
        	} 
        }

        protected int getCastWarmupTime() {
            return 20;
        }

        protected int getCastingTime() {
            return 30;
        }

        protected int getCastingInterval() {
            return 320;
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound() {
            return null;
        }
    }
    
    class AIChargeAttack extends Goal {
    	
        public AIChargeAttack() {
        	this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean canUse() {
            if (BansheeEntity.this.getTarget() != null && !BansheeEntity.this.getMoveControl().hasWanted() && BansheeEntity.this.getRandom().nextInt(7) == 0) {
                return BansheeEntity.this.distanceToSqr(BansheeEntity.this.getTarget()) > 4.0D;
            } else {
                return false;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return BansheeEntity.this.getMoveControl().hasWanted() && BansheeEntity.this.getTarget() != null && BansheeEntity.this.getTarget().isAlive();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            LivingEntity LivingEntity = BansheeEntity.this.getTarget();
            Vector3d vec3d = LivingEntity.getEyePosition(1.0F);
            BansheeEntity.this.moveControl.setWantedPosition(vec3d.x, vec3d.y, vec3d.z, 1.2D);
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
            LivingEntity livingentity = BansheeEntity.this.getTarget();
            if (BansheeEntity.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
               BansheeEntity.this.doHurtTarget(livingentity);
            } else {
               double d0 = BansheeEntity.this.distanceToSqr(livingentity);
               if (d0 < 9.0D) {
                  Vector3d vector3d = livingentity.getEyePosition(1.0F);
                  BansheeEntity.this.moveControl.setWantedPosition(vector3d.x, vector3d.y, vector3d.z, 1.0D);
                  if(BansheeEntity.this.attackTimer == 0) {
	   	               BansheeEntity.this.attackTimer = 20;
	   	               BansheeEntity.this.level.broadcastEntityEvent(BansheeEntity.this, (byte)4);
                  }
               }
            }
        }
    }
    
    class AIMoveControl extends MovementController {
        public AIMoveControl(BansheeEntity Banshee) {
            super(Banshee);
        }

        public void tick() {
            if (this.operation == MovementController.Action.MOVE_TO) {
                Vector3d vector3d = new Vector3d(this.wantedX - BansheeEntity.this.getX(), this.wantedY - BansheeEntity.this.getY(), this.wantedZ - BansheeEntity.this.getZ());
                double d0 = vector3d.length();
                if (d0 < BansheeEntity.this.getBoundingBox().getSize()) {
                   this.operation = MovementController.Action.WAIT;
                   BansheeEntity.this.setDeltaMovement(BansheeEntity.this.getDeltaMovement().scale(0.5D));
                } else {
                   BansheeEntity.this.setDeltaMovement(BansheeEntity.this.getDeltaMovement().add(vector3d.scale(this.speedModifier * 0.05D / d0)));
                   if (BansheeEntity.this.getTarget() == null) {
                      Vector3d vector3d1 = BansheeEntity.this.getDeltaMovement();
                      BansheeEntity.this.yRot = -((float)MathHelper.atan2(vector3d1.x, vector3d1.z)) * (180F / (float)Math.PI);
                      BansheeEntity.this.yBodyRot = BansheeEntity.this.yRot;
                   } else {
                      double d2 = BansheeEntity.this.getTarget().getX() - BansheeEntity.this.getX();
                      double d1 = BansheeEntity.this.getTarget().getZ() - BansheeEntity.this.getZ();
                      BansheeEntity.this.yRot = -((float)MathHelper.atan2(d2, d1)) * (180F / (float)Math.PI);
                      BansheeEntity.this.yBodyRot = BansheeEntity.this.yRot;
                   }
                }
            }
        }
    }
    
    class AIMoveRandom extends Goal {
        public AIMoveRandom() {
        	this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean canUse() {
            return !BansheeEntity.this.getMoveControl().hasWanted() && BansheeEntity.this.getRandom().nextInt(7) == 0 && !BansheeEntity.this.isAggressive();
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return false;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {            
            BlockPos blockpos = BansheeEntity.this.blockPosition();
            int y = BansheeEntity.this.getRandom().nextInt(11) - 5;
           
            y = Math.min(SpawnUtil.getHeight(BansheeEntity.this).getY() + FURConfig.FlyingHeight_limit.get(), y);

            for(int i = 0; i < 3; ++i) {
               BlockPos blockpos1 = blockpos.offset(BansheeEntity.this.random.nextInt(15) - 7, y, BansheeEntity.this.random.nextInt(15) - 7);
               if (BansheeEntity.this.level.isEmptyBlock(blockpos1)) {
                  BansheeEntity.this.moveControl.setWantedPosition((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 0.25D);
                  if (BansheeEntity.this.getTarget() == null) {
                     BansheeEntity.this.getLookControl().setLookAt((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 180.0F, 20.0F);
                  }
                  break;
               }
            }
        }
    }

    @Override
    protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
        return p_213348_2_.height * 0.8F;
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return FURSoundRegistry.BANSHEE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FURSoundRegistry.BANSHEE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.BANSHEE_DEATH;
    }
    
    protected SoundEvent getSpellSound() {
        return FURSoundRegistry.BANSHEE_ATTACK;
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.UNDEAD;
    }
}
