package com.Fishmod.fur.entities.floating;

import java.util.EnumSet;
import javax.annotation.Nullable;

import com.Fishmod.fur.entities.ICharging;
import com.Fishmod.fur.entities.ai.FloatingMoveControl;
import com.Fishmod.fur.entities.ai.FloatingMoveRandomGoal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FloatingMobEntity extends Monster implements ICharging {
	protected static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(FloatingMobEntity.class, EntityDataSerializers.BYTE);
	protected int spellTicks;
	
	public FloatingMobEntity(EntityType<? extends FloatingMobEntity> p_i48549_1_, Level worldIn) {
        super(p_i48549_1_, worldIn);
        this.moveControl = new FloatingMoveControl(this);
    }
	
    /**
     * Tries to move the entity towards the specified location.
     */
	@Override
    public void move(MoverType type, Vec3 p_213315_2_) {
        super.move(type, p_213315_2_);
        this.checkInsideBlocks();
    }
	
	@Override
    protected void registerGoals() {
		super.registerGoals();
        this.goalSelector.addGoal(1, new AICastingApell());
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));    
        /*if(!FURConfig.SunScreen_Mode.get())*/this.goalSelector.addGoal(5, new FleeSunGoal(this, 1.0D));
        this.goalSelector.addGoal(7, this.wanderGoal());
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
    }
    
    protected Goal wanderGoal() {
    	return new FloatingMoveRandomGoal(this);
    }
    
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes();
    }
    
    public static boolean checkBansheeSpawnRules(EntityType<? extends FloatingMobEntity> p_223316_0_, ServerLevelAccessor p_223316_1_, MobSpawnType p_223316_2_, BlockPos p_223316_3_, RandomSource p_223316_4_) {
        return Monster.checkMonsterSpawnRules(p_223316_0_, p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_);
    }
    
    @Override
    protected void defineSynchedData() {
    	super.defineSynchedData();
        this.getEntityData().define(DATA_FLAGS_ID, (byte)0);
    }
       
    @Nullable
    protected ParticleOptions ParticleType() {
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
            this.level().addParticle(this.ParticleType(), this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), d0, d1, d2);
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
    	
    	if (/*!FURConfig.SunScreen_Mode.get() && */this.isSunBurnTick()) {
    		this.setSecondsOnFire(8);
        }
    	
    	if (this.getTarget() != null) this.noPhysics = true;
    	super.tick();
        this.noPhysics = false;
        this.setNoGravity(true);
    }
    
	@Override
	public boolean doHurtTarget(Entity par1Entity) {
		boolean flag = super.doHurtTarget(par1Entity);
		
        if (flag) {
        	this.level().broadcastEntityEvent(this, (byte)4);
        }
        
		return flag;
	}

    @Override
	protected void checkFallDamage(double p_184231_1_, boolean p_184231_3_, BlockState p_184231_4_, BlockPos p_184231_5_) {
	}
	
	@Override
	protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
	}
	
	@Override
    public float getWalkTargetValue(BlockPos p_205022_1_, LevelReader p_205022_2_) {
    	if (p_205022_2_.getBrightness(LightLayer.BLOCK, p_205022_1_) > 11) {
    		return -1.0F;
    	} else {
    		return super.getWalkTargetValue(p_205022_1_, p_205022_2_);
    	}
    }
	
    protected PathNavigation createNavigation(Level p_175447_1_) {
    	FlyingPathNavigation flyingpathnavigator = new FlyingPathNavigation(this, p_175447_1_) {
           public boolean isStableDestination(BlockPos p_188555_1_) {
              return !this.level.getBlockState(p_188555_1_.below()).isAir();
           }
        };
        flyingpathnavigator.setCanOpenDoors(false);
        flyingpathnavigator.setCanFloat(true);
        flyingpathnavigator.setCanPassDoors(true);
        return flyingpathnavigator;
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
    
    @Override
    protected float getStandingEyeHeight(Pose p_213348_1_, EntityDimensions p_213348_2_) {
        return p_213348_2_.height * 0.8F;
    }
}
