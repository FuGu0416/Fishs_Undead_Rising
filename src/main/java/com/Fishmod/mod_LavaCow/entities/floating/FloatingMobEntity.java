package com.Fishmod.mod_LavaCow.entities.floating;

import java.util.EnumSet;
import java.util.Random;
import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.IAggressive;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.FleeSunGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FloatingMobEntity extends MonsterEntity implements IAggressive{
	protected static final DataParameter<Byte> DATA_FLAGS_ID = EntityDataManager.defineId(FloatingMobEntity.class, DataSerializers.BYTE);
	private int attackTimer = 0;
	protected int spellTicks;
	
	public FloatingMobEntity(EntityType<? extends FloatingMobEntity> p_i48549_1_, World worldIn) {
        super(p_i48549_1_, worldIn);
        this.moveControl = new FloatingMobEntity.AIMoveControl(this);
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
		super.registerGoals();
        this.goalSelector.addGoal(1, new AICastingApell());
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));    
        if(!FURConfig.SunScreen_Mode.get())this.goalSelector.addGoal(5, new FleeSunGoal(this, 1.0D));
        this.goalSelector.addGoal(7, this.wanderGoal());
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
    }
    
    protected Goal wanderGoal() {
    	return new FloatingMobEntity.AIMoveRandom();
    }
    
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes();
    }
    
    public static boolean checkBansheeSpawnRules(EntityType<? extends FloatingMobEntity> p_223316_0_, IWorld p_223316_1_, SpawnReason p_223316_2_, BlockPos p_223316_3_, Random p_223316_4_) {
        return MonsterEntity.checkMonsterSpawnRules(p_223316_0_, (IServerWorld) p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_);//SpawnUtil.isAllowedDimension(this.dimension);
    }
    
    @Override
    protected void defineSynchedData() {
    	super.defineSynchedData();
        this.getEntityData().define(DATA_FLAGS_ID, (byte)0);
    }
       
    @Nullable
    protected IParticleData ParticleType() {
    	return null;
    }

	private boolean getFloaterFlag(int p_190656_1_) {
        int i = this.entityData.get(DATA_FLAGS_ID);
        return (i & p_190656_1_) != 0;
	}

	private void setFloaterFlag(int byte_loc, boolean bool) {
        int i = this.entityData.get(DATA_FLAGS_ID);
        if (bool) {
           i = i | byte_loc;
        } else {
           i = i & ~byte_loc;
        }

        this.entityData.set(DATA_FLAGS_ID, (byte)(i & 255));
	}
	
    public boolean isSpellcasting() {
    	 return this.getFloaterFlag(2);
    }
    
    @OnlyIn(Dist.CLIENT)
    public boolean isSpellcastingC() {
    	 return this.getFloaterFlag(2);
    }
    
    public void setIsCasting(boolean bool) {
    	this.setFloaterFlag(2, bool);
    }

	public boolean isCharging() {
        return this.getFloaterFlag(1);
	}

	public void setIsCharging(boolean bool) {
        this.setFloaterFlag(1, bool);
	}
	
    public int getSpellTicks() {
        return this.spellTicks;
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
            this.level.addParticle(this.ParticleType(), this.getRandomX(1.0D), this.getRandomY() + (this.getBbHeight() * 0.75D), this.getRandomZ(1.0D), d0, d1, d2);
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
    	
    	this.noPhysics = true;
    	super.tick();
        this.noPhysics = false;
        this.setNoGravity(true);
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
    
	@Override
	protected boolean makeFlySound() {
		return true;
	}
	
	@Override
	protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
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
        
    public class AICastingApell extends Goal {
        public AICastingApell() {
        	this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean canUse() {
            return FloatingMobEntity.this.getSpellTicks() > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            super.start();
            FloatingMobEntity.this.setIsCasting(true);
            FloatingMobEntity.this.navigation.stop();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
            super.stop();
            FloatingMobEntity.this.setIsCasting(false);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (FloatingMobEntity.this.getTarget() != null) {
                FloatingMobEntity.this.getLookControl().setLookAt(FloatingMobEntity.this.getTarget(), (float)FloatingMobEntity.this.getMaxHeadYRot(), (float)FloatingMobEntity.this.getMaxHeadXRot());
            }
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
            if (FloatingMobEntity.this.getTarget() != null && !FloatingMobEntity.this.getMoveControl().hasWanted() && FloatingMobEntity.this.getRandom().nextInt(7) == 0) {
                return FloatingMobEntity.this.distanceToSqr(FloatingMobEntity.this.getTarget()) > 4.0D;
            } else {
                return false;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return FloatingMobEntity.this.getMoveControl().hasWanted() && FloatingMobEntity.this.isCharging() && FloatingMobEntity.this.getTarget() != null && FloatingMobEntity.this.getTarget().isAlive();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            LivingEntity LivingEntity = FloatingMobEntity.this.getTarget();
            Vector3d vec3d = LivingEntity.getEyePosition(1.0F);
            FloatingMobEntity.this.moveControl.setWantedPosition(vec3d.x, vec3d.y, vec3d.z, 1.2D);
            FloatingMobEntity.this.setIsCharging(true);
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
        	FloatingMobEntity.this.setIsCharging(false);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            LivingEntity livingentity = FloatingMobEntity.this.getTarget();
            if (FloatingMobEntity.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
               FloatingMobEntity.this.doHurtTarget(livingentity);
               FloatingMobEntity.this.setIsCharging(false);
            } else {
               double d0 = FloatingMobEntity.this.distanceToSqr(livingentity);
               if (d0 < 9.0D) {
                  Vector3d vector3d = livingentity.getEyePosition(1.0F);
                  FloatingMobEntity.this.moveControl.setWantedPosition(vector3d.x, vector3d.y, vector3d.z, 1.0D);
                  if(FloatingMobEntity.this.attackTimer == 0) {
	   	               FloatingMobEntity.this.attackTimer = 20;
	   	               FloatingMobEntity.this.level.broadcastEntityEvent(FloatingMobEntity.this, (byte)4);
                  }
               }
            }
        }
    }
    
    class AIMoveControl extends MovementController {
        public AIMoveControl(FloatingMobEntity Banshee) {
            super(Banshee);
        }

        public void tick() {            
            if (this.operation == MovementController.Action.MOVE_TO) {
                Vector3d vector3d = new Vector3d(this.wantedX - FloatingMobEntity.this.getX(), this.wantedY - FloatingMobEntity.this.getY(), this.wantedZ - FloatingMobEntity.this.getZ());
                double d0 = vector3d.length();
                if (d0 < FloatingMobEntity.this.getBoundingBox().getSize()) {
                   this.operation = MovementController.Action.WAIT;
                   FloatingMobEntity.this.setDeltaMovement(FloatingMobEntity.this.getDeltaMovement().scale(0.5D));
                } else {
                   FloatingMobEntity.this.setDeltaMovement(FloatingMobEntity.this.getDeltaMovement().add(vector3d.scale(this.speedModifier * 0.05D / d0)));
                   if (FloatingMobEntity.this.getTarget() == null) {
                      Vector3d vector3d1 = FloatingMobEntity.this.getDeltaMovement();
                      FloatingMobEntity.this.yRot = -((float)MathHelper.atan2(vector3d1.x, vector3d1.z)) * (180F / (float)Math.PI);
                      FloatingMobEntity.this.yBodyRot = FloatingMobEntity.this.yRot;
                   } else {
                      double d2 = FloatingMobEntity.this.getTarget().getX() - FloatingMobEntity.this.getX();
                      double d1 = FloatingMobEntity.this.getTarget().getZ() - FloatingMobEntity.this.getZ();
                      FloatingMobEntity.this.yRot = -((float)MathHelper.atan2(d2, d1)) * (180F / (float)Math.PI);
                      FloatingMobEntity.this.yBodyRot = FloatingMobEntity.this.yRot;
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
            return !FloatingMobEntity.this.getMoveControl().hasWanted() && FloatingMobEntity.this.getRandom().nextInt(7) == 0;
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
            BlockPos blockpos = FloatingMobEntity.this.blockPosition();
            int groundHeight = SpawnUtil.getHeight(FloatingMobEntity.this).getY();
            int y = FloatingMobEntity.this.getRandom().nextInt(11) - 5;

            if (groundHeight > 0) {
            	y = Math.min(groundHeight + 4 - blockpos.getY(), y);
            }
            
            for(int i = 0; i < 3; ++i) {
            	Vector3d vector3d = this.findPos();
            	if (vector3d != null) {
            		FloatingMobEntity.this.moveControl.setWantedPosition(vector3d.x + 0.5D, vector3d.y + 0.5D, vector3d.z + 0.5D, 0.25D);
            		if (FloatingMobEntity.this.getTarget() == null) {
            			FloatingMobEntity.this.getLookControl().setLookAt(vector3d.x + 0.5D, vector3d.y + 0.5D, vector3d.z + 0.5D, 180.0F, 20.0F);
            		}
            		break;
            	}
            }
        }
        
        @Nullable
        private Vector3d findPos() {
           Vector3d vector3d;
           vector3d = FloatingMobEntity.this.getViewVector(0.0F);
           Vector3d vector3d2 = RandomPositionGenerator.getAboveLandPos(FloatingMobEntity.this, 8, 7, vector3d, ((float)Math.PI / 2F), 2, 1);
           return vector3d2 != null ? vector3d2 : RandomPositionGenerator.getAirPos(FloatingMobEntity.this, 8, 4, -2, vector3d, (double)((float)Math.PI / 2F));
        }
    }

    @Override
    protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
        return p_213348_2_.height * 0.8F;
    }
}
