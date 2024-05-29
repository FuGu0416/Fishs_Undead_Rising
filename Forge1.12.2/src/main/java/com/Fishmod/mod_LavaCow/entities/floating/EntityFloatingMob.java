package com.Fishmod.mod_LavaCow.entities.floating;

import java.util.Random;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.IAggressive;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityFloatingMob extends EntityMob implements IAggressive {	
	private static final DataParameter<Byte> DATA_FLAGS_ID = EntityDataManager.<Byte>createKey(EntityFloatingMob.class, DataSerializers.BYTE);
	private boolean isAggressive = false;
	private int attackTimer = 0;
	protected int spellTicks;
	protected boolean daytimeBurning;
	
	public EntityFloatingMob(World worldIn) {
        super(worldIn);
        this.moveHelper = new EntityFloatingMob.AIMoveControl(this);
        this.daytimeBurning = false;
    }
	
    /**
     * Tries to move the entity towards the specified location.
     */
    public void move(MoverType type, double x, double y, double z) {
        super.move(type, x, y, z);
        this.doBlockCollisions();
    }
	
    protected void initEntityAI() {
    	super.initEntityAI();
        this.tasks.addTask(1, new AICastingApell());
		this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, false));
        if(!Modconfig.SunScreen_Mode && daytimeBurning())this.tasks.addTask(5, new EntityAIFleeSun(this, 1.0D));
        this.tasks.addTask(7, this.wanderGoal());
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
    }
    
    protected AIMoveRandom wanderGoal() {
    	return new EntityFloatingMob.AIMoveRandom();
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
    }
	
	public boolean getCanSpawnHere() {
		return SpawnUtil.isAllowedDimension(this.dimension) && super.getCanSpawnHere();
	}
	
    protected void entityInit() {
    	super.entityInit();
    	this.dataManager.register(DATA_FLAGS_ID, Byte.valueOf((byte)0));
    }
    
    protected String ParticleType() {
    	return "spore";
    }
    
	private boolean getFloaterFlag(int p_190656_1_) {
        int i = this.dataManager.get(DATA_FLAGS_ID);
        return (i & p_190656_1_) != 0;
	}

	private void setFloaterFlag(int byte_loc, boolean bool) {
        int i = this.dataManager.get(DATA_FLAGS_ID);
        
        if (bool) {
           i = i | byte_loc;
        } else {
           i = i & ~byte_loc;
        }

        this.dataManager.set(DATA_FLAGS_ID, (byte)(i & 255));
	}
    
    public boolean isSpellcasting() {
    	return this.getFloaterFlag(2);
    }
    
    @SideOnly(Side.CLIENT)
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
    
    public boolean daytimeBurning() {
        return daytimeBurning;
    }
	
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void onLivingUpdate() {
		super.onLivingUpdate();
		
        if(this.ticksExisted % 2 == 0 && this.getEntityWorld().isRemote) {
            double d0 = this.rand.nextGaussian() * 0.02D;
            double d1 = this.rand.nextGaussian() * 0.02D;
            double d2 = this.rand.nextGaussian() * 0.02D;
        	mod_LavaCow.PROXY.spawnCustomParticle(this.ParticleType(), this.world, this.posX + (double)(new Random().nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + (double)(new Random().nextFloat() * this.height), this.posZ + (double)(new Random().nextFloat() * this.width * 2.0F) - (double)this.width, d0, d1, d2, 0.20F, 0.21F, 0.23F);
        }
    }
    
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void onUpdate() {
        if (this.spellTicks > 0) {
            --this.spellTicks;
        }
        
        if (this.attackTimer > 0) {
            --this.attackTimer;
        }
    	
    	if (!Modconfig.SunScreen_Mode && this.world.isDaytime() && !this.world.isRemote && this.daytimeBurning()) {
    		float f = this.getBrightness();
    		if (f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.world.canSeeSky(new BlockPos(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ)))this.setFire(8);
    	}
    	
    	this.noClip = true;
    	super.onUpdate();
    	this.noClip = false;
    	this.setNoGravity(true);
    }
    
    protected void updateAITasks() {
        super.updateAITasks();
        
        if(this.getAttackTarget() != null) {       		
        		isAggressive = true;
        		this.world.setEntityState(this, (byte)11);
        	} else {
        		isAggressive = false;
        		this.world.setEntityState(this, (byte)34);
        	}
    }
	
    public boolean isAggressive() {
    	return isAggressive;
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
    public void fall(float distance, float damageMultiplier) {
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
    }
    
    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }
	
    /**
     * Handler for {@link World#setEntityState}
     */
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
		switch(id) {
		case 10:
			this.spellTicks = 30;
			break;
		case 4:
			this.attackTimer = 20;
			break;
		case 11:
			this.isAggressive = true;
			break;
		case 34:
			this.isAggressive = false;
			break;
		default:
			super.handleStatusUpdate(id);
			break;
		}
    }
    
    public class AICastingApell extends EntityAIBase {
        public AICastingApell() {
            this.setMutexBits(3);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            return EntityFloatingMob.this.getSpellTicks() > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            super.startExecuting();
            EntityFloatingMob.this.setIsCasting(true);
            EntityFloatingMob.this.navigator.clearPath();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask() {
            super.resetTask();
            EntityFloatingMob.this.setIsCasting(false);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask() {
            if (EntityFloatingMob.this.getAttackTarget() != null) {
                EntityFloatingMob.this.getLookHelper().setLookPositionWithEntity(EntityFloatingMob.this.getAttackTarget(), (float)EntityFloatingMob.this.getHorizontalFaceSpeed(), (float)EntityFloatingMob.this.getVerticalFaceSpeed());
            }
        }
    }
    
    public class AIChargeAttack extends EntityAIBase {
        public AIChargeAttack() {
            this.setMutexBits(1);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            if (EntityFloatingMob.this.getAttackTarget() != null && !EntityFloatingMob.this.getMoveHelper().isUpdating() && EntityFloatingMob.this.rand.nextInt(7) == 0) {
                return EntityFloatingMob.this.getDistanceSq(EntityFloatingMob.this.getAttackTarget()) > 4.0D;
            }
            else {
                return false;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            return EntityFloatingMob.this.getMoveHelper().isUpdating() && EntityFloatingMob.this.isCharging() && EntityFloatingMob.this.getAttackTarget() != null && EntityFloatingMob.this.getAttackTarget().isEntityAlive();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            EntityLivingBase entitylivingbase = EntityFloatingMob.this.getAttackTarget();
            Vec3d vec3d = entitylivingbase.getPositionEyes(1.0F);
            EntityFloatingMob.this.moveHelper.setMoveTo(vec3d.x, vec3d.y, vec3d.z, 1.2D);
        	EntityFloatingMob.this.setIsCharging(true);
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask() {
        	EntityFloatingMob.this.setIsCharging(false);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask() {
            EntityLivingBase entitylivingbase = EntityFloatingMob.this.getAttackTarget();
            
                if (EntityFloatingMob.this.getEntityBoundingBox().intersects(entitylivingbase.getEntityBoundingBox())) {
                    EntityFloatingMob.this.attackEntityAsMob(entitylivingbase);
                	EntityFloatingMob.this.setIsCharging(false);
                }
                else {
                    double d0 = EntityFloatingMob.this.getDistanceSq(entitylivingbase);

                    if (d0 < 9.0D) {
                        Vec3d vec3d = entitylivingbase.getPositionEyes(1.0F);
                        EntityFloatingMob.this.moveHelper.setMoveTo(vec3d.x, vec3d.y, vec3d.z, 1.0D);
                        if (EntityFloatingMob.this.attackTimer == 0) {
                        	EntityFloatingMob.this.attackTimer = 20;
                        	EntityFloatingMob.this.world.setEntityState(EntityFloatingMob.this, (byte)4);
                    }
                }
            }
        }
    }
    
    class AIMoveControl extends EntityMoveHelper {
        public AIMoveControl(EntityFloatingMob entity) {
            super(entity);
        }

        public void onUpdateMoveHelper() {
            if (this.action == EntityMoveHelper.Action.MOVE_TO) {
                double d0 = this.posX - EntityFloatingMob.this.posX;
                double d1 = this.posY - EntityFloatingMob.this.posY;
                double d2 = this.posZ - EntityFloatingMob.this.posZ;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                d3 = (double)MathHelper.sqrt(d3);

                if (d3 < EntityFloatingMob.this.getEntityBoundingBox().getAverageEdgeLength()) {
                    this.action = EntityMoveHelper.Action.WAIT;
                    EntityFloatingMob.this.motionX *= 0.5D;
                    EntityFloatingMob.this.motionY *= 0.5D;
                    EntityFloatingMob.this.motionZ *= 0.5D;
                }
                else {
                	EntityFloatingMob.this.motionX += d0 / d3 * 0.05D * this.speed;
                	EntityFloatingMob.this.motionY += d1 / d3 * 0.05D * this.speed;
                    EntityFloatingMob.this.motionZ += d2 / d3 * 0.05D * this.speed;

                    if (EntityFloatingMob.this.getAttackTarget() == null) {
                    	EntityFloatingMob.this.rotationYaw = -((float)MathHelper.atan2(EntityFloatingMob.this.motionX, EntityFloatingMob.this.motionZ)) * (180F / (float)Math.PI);
                        EntityFloatingMob.this.renderYawOffset = EntityFloatingMob.this.rotationYaw;
                    }
                    else {
                        double d4 = EntityFloatingMob.this.getAttackTarget().posX - EntityFloatingMob.this.posX;
                        double d5 = EntityFloatingMob.this.getAttackTarget().posZ - EntityFloatingMob.this.posZ;
                        EntityFloatingMob.this.rotationYaw = -((float)MathHelper.atan2(d4, d5)) * (180F / (float)Math.PI);
                        EntityFloatingMob.this.renderYawOffset = EntityFloatingMob.this.rotationYaw;
                    }
                }
            }
        } 
    }
    
    class AIMoveRandom extends EntityAIBase {
        public AIMoveRandom() {
            this.setMutexBits(1);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            return !EntityFloatingMob.this.getMoveHelper().isUpdating() && EntityFloatingMob.this.rand.nextInt(7) == 0;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            return false;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask() {
            BlockPos blockpos = EntityFloatingMob.this.getPosition();
            int groundHeight = SpawnUtil.getHeight(EntityFloatingMob.this).getY();
            int y = EntityFloatingMob.this.rand.nextInt(11) - 5;
            
            if(groundHeight > 0) {
            	y = Math.min(groundHeight + 4 - blockpos.getY(), y);
        	}
            
            for (int i = 0; i < 3; ++i) {
                BlockPos blockpos1 = blockpos.add(EntityFloatingMob.this.rand.nextInt(15) - 7, y, EntityFloatingMob.this.rand.nextInt(15) - 7);

                if (EntityFloatingMob.this.world.isAirBlock(blockpos1)) {
                    EntityFloatingMob.this.moveHelper.setMoveTo((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 0.25D);

                    if (EntityFloatingMob.this.getAttackTarget() == null) {
                        EntityFloatingMob.this.getLookHelper().setLookPosition((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 180.0F, 20.0F);
                    }

                    break;
                }
            }
        }
    }

    public float getEyeHeight() {
        return this.height * 0.8F;
    }
}
