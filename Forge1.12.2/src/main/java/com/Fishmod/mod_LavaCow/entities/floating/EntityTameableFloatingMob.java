package com.Fishmod.mod_LavaCow.entities.floating;

import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.IAggressive;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityFishTameable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityMoveHelper;
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

public class EntityTameableFloatingMob extends EntityFishTameable implements IAggressive {	
	private static final DataParameter<Byte> DATA_FLAGS_ID = EntityDataManager.<Byte>createKey(EntityTameableFloatingMob.class, DataSerializers.BYTE);
	private boolean isAggressive = false;
	private int attackTimer = 0;
	protected int spellTicks;
	protected boolean daytimeBurning;
	
	public EntityTameableFloatingMob(World worldIn) {
        super(worldIn);
        this.moveHelper = new EntityTameableFloatingMob.AIMoveControl(this);
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
        this.tasks.addTask(7, this.wanderGoal());
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
    }
    
    protected AIMoveRandom wanderGoal() {
    	return new EntityTameableFloatingMob.AIMoveRandom();
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
			this.attackTimer = 5;
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
    
    public class AIChargeAttack extends EntityAIBase {
        public AIChargeAttack() {
            this.setMutexBits(1);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            if (EntityTameableFloatingMob.this.getAttackTarget() != null && !EntityTameableFloatingMob.this.getMoveHelper().isUpdating() && EntityTameableFloatingMob.this.rand.nextInt(7) == 0) {
                return EntityTameableFloatingMob.this.getDistanceSq(EntityTameableFloatingMob.this.getAttackTarget()) > 4.0D;
            }
            else {
                return false;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            return EntityTameableFloatingMob.this.getMoveHelper().isUpdating() && EntityTameableFloatingMob.this.isCharging() && EntityTameableFloatingMob.this.getAttackTarget() != null && EntityTameableFloatingMob.this.getAttackTarget().isEntityAlive();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            EntityLivingBase entitylivingbase = EntityTameableFloatingMob.this.getAttackTarget();
            Vec3d vec3d = entitylivingbase.getPositionEyes(1.0F);
            EntityTameableFloatingMob.this.moveHelper.setMoveTo(vec3d.x, vec3d.y, vec3d.z, 1.2D);
        	EntityTameableFloatingMob.this.setIsCharging(true);
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask() {
        	EntityTameableFloatingMob.this.setIsCharging(false);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask() {
            EntityLivingBase entitylivingbase = EntityTameableFloatingMob.this.getAttackTarget();
            
                if (EntityTameableFloatingMob.this.getEntityBoundingBox().intersects(entitylivingbase.getEntityBoundingBox())) {
                    EntityTameableFloatingMob.this.attackEntityAsMob(entitylivingbase);
                	EntityTameableFloatingMob.this.setIsCharging(false);
                }
                else {
                    double d0 = EntityTameableFloatingMob.this.getDistanceSq(entitylivingbase);

                    if (d0 < 9.0D) {
                        Vec3d vec3d = entitylivingbase.getPositionEyes(1.0F);
                        EntityTameableFloatingMob.this.moveHelper.setMoveTo(vec3d.x, vec3d.y, vec3d.z, 1.0D);
                        if (EntityTameableFloatingMob.this.attackTimer == 0) {
                        	EntityTameableFloatingMob.this.attackTimer = 5;
                        	EntityTameableFloatingMob.this.world.setEntityState(EntityTameableFloatingMob.this, (byte)4);
                    }
                }
            }
        }
    }
    
    class AIMoveControl extends EntityMoveHelper {
        public AIMoveControl(EntityTameableFloatingMob entity) {
            super(entity);
        }

        public void onUpdateMoveHelper() {
            if (this.action == EntityMoveHelper.Action.MOVE_TO) {
                double d0 = this.posX - EntityTameableFloatingMob.this.posX;
                double d1 = this.posY - EntityTameableFloatingMob.this.posY;
                double d2 = this.posZ - EntityTameableFloatingMob.this.posZ;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                d3 = (double)MathHelper.sqrt(d3);

                if (d3 < EntityTameableFloatingMob.this.getEntityBoundingBox().getAverageEdgeLength()) {
                    this.action = EntityMoveHelper.Action.WAIT;
                    EntityTameableFloatingMob.this.motionX *= 0.5D;
                    EntityTameableFloatingMob.this.motionY *= 0.5D;
                    EntityTameableFloatingMob.this.motionZ *= 0.5D;
                }
                else {
                	EntityTameableFloatingMob.this.motionX += d0 / d3 * 0.05D * this.speed;
                	EntityTameableFloatingMob.this.motionY += d1 / d3 * 0.05D * this.speed;
                    EntityTameableFloatingMob.this.motionZ += d2 / d3 * 0.05D * this.speed;

                    if (EntityTameableFloatingMob.this.getAttackTarget() == null) {
                    	EntityTameableFloatingMob.this.rotationYaw = -((float)MathHelper.atan2(EntityTameableFloatingMob.this.motionX, EntityTameableFloatingMob.this.motionZ)) * (180F / (float)Math.PI);
                        EntityTameableFloatingMob.this.renderYawOffset = EntityTameableFloatingMob.this.rotationYaw;
                    }
                    else {
                        double d4 = EntityTameableFloatingMob.this.getAttackTarget().posX - EntityTameableFloatingMob.this.posX;
                        double d5 = EntityTameableFloatingMob.this.getAttackTarget().posZ - EntityTameableFloatingMob.this.posZ;
                        EntityTameableFloatingMob.this.rotationYaw = -((float)MathHelper.atan2(d4, d5)) * (180F / (float)Math.PI);
                        EntityTameableFloatingMob.this.renderYawOffset = EntityTameableFloatingMob.this.rotationYaw;
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
            return !EntityTameableFloatingMob.this.getMoveHelper().isUpdating() && EntityTameableFloatingMob.this.rand.nextInt(7) == 0;
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
            BlockPos blockpos = EntityTameableFloatingMob.this.getPosition();
            int groundHeight = SpawnUtil.getHeight(EntityTameableFloatingMob.this).getY();
            int y = EntityTameableFloatingMob.this.rand.nextInt(11) - 5;
            
            if(groundHeight > 0) {
            	y = Math.min(groundHeight + 4 - blockpos.getY(), y);
        	}
            
            for (int i = 0; i < 3; ++i) {
                BlockPos blockpos1 = blockpos.add(EntityTameableFloatingMob.this.rand.nextInt(15) - 7, y, EntityTameableFloatingMob.this.rand.nextInt(15) - 7);

                if (EntityTameableFloatingMob.this.world.isAirBlock(blockpos1)) {
                    EntityTameableFloatingMob.this.moveHelper.setMoveTo((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 0.25D);

                    if (EntityTameableFloatingMob.this.getAttackTarget() == null) {
                        EntityTameableFloatingMob.this.getLookHelper().setLookPosition((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 180.0F, 20.0F);
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
