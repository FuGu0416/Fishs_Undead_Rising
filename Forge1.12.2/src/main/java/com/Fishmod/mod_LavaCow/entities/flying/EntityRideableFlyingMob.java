package com.Fishmod.mod_LavaCow.entities.flying;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.init.Modkeys;
import com.Fishmod.mod_LavaCow.message.PacketMountSpecial;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityRideableFlyingMob extends EntityFlyingMob {
	private static final DataParameter<Boolean> SADDLED = EntityDataManager.<Boolean>createKey(EntityRideableFlyingMob.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Byte> CONTROL_STATE = EntityDataManager.<Byte>createKey(EntityRideableFlyingMob.class, DataSerializers.BYTE);
	public int abilityCooldown;
	protected int spellTicks;
	
	public EntityRideableFlyingMob(World worldIn, int heightLimit) {
		super(worldIn, heightLimit);
		this.abilityCooldown = 0;
	}
	
    protected void entityInit() {
    	super.entityInit();
        this.getDataManager().register(SADDLED, Boolean.valueOf(false));
        this.getDataManager().register(CONTROL_STATE, Byte.valueOf((byte) 0));
    }
    
	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		this.tasks.addTask(1, new AICastingSpell());
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
	}
	
    public boolean isSpellcasting() {
    	return this.spellTicks > 0;
    }
    
    public int getSpellTicks() {
        return this.spellTicks;
    }
    
    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
    	ItemStack itemstack = player.getHeldItem(hand);
    	boolean flag = this.isBreedingItem(itemstack);
        
        if (!flag && this.isOwner(player) && this.isTamed() && this.canBeSteered() && !this.isChild() && !this.isBeingRidden()) {
        	if (itemstack.getItem() instanceof ItemShears) {
    			this.setSaddled(false); 
    			if (!this.world.isRemote) {
    				this.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
    				this.dropItem(Items.SADDLE, 1);
    			}
    			return true;
    		} else if (!player.isSneaking() && !player.isRiding()) {
        	   player.startRiding(this);        	   
        	   return true;
    		}
        }
        
        if (!flag && this.isOwner(player) && this.isTamed() && !this.canBeSteered() && !this.isChild() && itemstack.getItem().equals(Items.SADDLE)) {
    		this.setSaddled(true);
            itemstack.shrink(1);
            if(!this.world.isRemote) {
            	this.playSound(SoundEvents.ENTITY_HORSE_SADDLE, 0.5F, 1.0F);
            }
            return true;
		}
                
        return super.processInteract(player, hand);
    }

    public double getMountedYOffset()
    {
        return (double)this.height * 0.9D;
    }
    
    @Override
    public boolean isMovementBlocked() {
    	return this.isSitting();
    }
    
    @Override
    public void updatePassenger(Entity passenger) {
        super.updatePassenger(passenger);
        if (this.isPassenger(passenger)) {
            renderYawOffset = rotationYaw;
            this.rotationYaw = passenger.rotationYaw;
        }
    }
    
    @Nullable
    public Entity getControllingPassenger() {
        for (Entity passenger : this.getPassengers()) {
            if (passenger instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) passenger;
                if (this.isTamed() && this.getOwnerId() != null && this.getOwnerId().equals(player.getUniqueID())) {
                    return player;
                }
            }
        }
        return null;
    }
    
    public boolean canBeControlledByRider() {
    	return this.getControllingPassenger() instanceof EntityPlayer;
    }
    
    public boolean isRidingPlayer(EntityPlayer player) {
        return this.getControllingPassenger() != null && this.getControllingPassenger() instanceof EntityPlayer && this.getControllingPassenger().getUniqueID().equals(player.getUniqueID());
    }
    
    public int abilityCooldown() {
    	return 0;
    }
    
    @Override
    public void onUpdate()
    {
        super.onUpdate();
        
        if (this.abilityCooldown > 0) {
        	this.abilityCooldown--;
        }
        
        if (this.spellTicks > 0) {
            --this.spellTicks;
            this.motionX = 0.0D;
            this.motionY = 0.0D;
            this.motionZ = 0.0D;
        }
        
    	if(this.isUp() && !this.isDown()) {
    		this.motionY += 0.05F;
    	}
    	
    	if(!this.isUp() && this.isDown()) {
    		this.motionY += -0.05F;
    	}
    }
    
    @Override
    public void onLivingUpdate() {
    	super.onLivingUpdate();
    		
    	if (this.world.isRemote) {
            this.ClientControl();
        }
    	
        if (!this.world.isRemote && this.isEntityAlive()) {
            if (this.rand.nextInt(900) == 0 && this.deathTime == 0) {
               this.heal(1.0F);
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    protected void ClientControl() {
    	Minecraft game = Minecraft.getMinecraft();
    	
		if (this.abilityCooldown == 0 && Modkeys.MOUNT_SPECIAL.isKeyDown() && this.isRidingPlayer(game.player) && this.getLandTimer() <= 10) {
			this.abilityCooldown = this.abilityCooldown();
			mod_LavaCow.NETWORK_WRAPPER.sendToServer(new PacketMountSpecial(this.getEntityId(), this.posX, this.posY, this.posZ));
		}
    	
    	if (this.isRidingPlayer(game.player)) {
        	this.setControlState(0, game.gameSettings.keyBindJump.isKeyDown());
        	this.setControlState(1, Modkeys.MOUNT_DOWN.isKeyDown());
    	}
    }
    
    public void setSaddled(boolean saddled) {
        this.dataManager.set(SADDLED, Boolean.valueOf(saddled));
    }
    
    private boolean isUp() {
    	return (getDataManager().get(CONTROL_STATE).byteValue() & 1) == 1;
    }
    
    private boolean isDown() {
    	return ((getDataManager().get(CONTROL_STATE).byteValue() >> 1) & 1) == 1;
    }
    
    private void setControlState(int byteLoc, boolean stateIn) {
        byte prevState = getDataManager().get(CONTROL_STATE).byteValue();
        if (stateIn) {
        	getDataManager().set(CONTROL_STATE, (byte) (prevState | (1 << byteLoc)));
        } else {
        	getDataManager().set(CONTROL_STATE, (byte) (prevState & ~(1 << byteLoc)));
        }
    }
    
    /**
     * returns true if all the conditions for steering the entity are met. For pigs, this is true if it is being ridden
     * by a player and the player is holding a carrot-on-a-stick
     */
    public boolean canBeSteered() {
    	return Boolean.valueOf(this.dataManager.get(SADDLED).booleanValue());
    }
    
    @Override
    public void travel(float strafe, float vertical, float forward) {
    	if (this.isEntityAlive()) {
    		if (this.isBeingRidden() && this.canBeControlledByRider() && this.canBeSteered()) {
    			EntityLivingBase controller = (EntityLivingBase)this.getControllingPassenger();
    			this.rotationYaw = controller.rotationYaw;
    			this.prevRotationYaw = this.rotationYaw;
    			this.rotationPitch = controller.rotationPitch * 0.5F;
    			this.setRotation(this.rotationYaw, this.rotationPitch);
    			this.renderYawOffset = this.rotationYaw;
    			this.rotationYawHead = this.rotationYaw;
    			this.stepHeight = 1.0F;
    			this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1F;
    			strafe = controller.moveStrafing * 0.5F;
    			forward = controller.moveForward;

    			if (this.canPassengerSteer()) {
    				super.travel(strafe, vertical, forward);
    			} else {
                this.motionX = 0.0D;
                this.motionY = 0.0D;
                this.motionZ = 0.0D;
            }

    			this.prevLimbSwingAmount = this.limbSwingAmount;
    			double d1 = this.posX - this.prevPosX;
    			double d0 = this.posZ - this.prevPosZ;
    			float f1 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;

    			if (f1 > 1.0F) {
    				f1 = 1.0F;
    			}

    			this.limbSwingAmount += (f1 - this.limbSwingAmount) * 0.4F;
    			this.limbSwing += this.limbSwingAmount;
    		}
    		else {
    			super.travel(strafe, vertical, forward);
    		}
    	}
	}
    
    public void onDeath(DamageSource cause) {
        super.onDeath(cause);

        if (!this.world.isRemote) {
            if (this.canBeSteered()) {
                this.dropItem(Items.SADDLE, 1);
            }
        }
    }
    
    /**
     * Writes the extra NBT data specific to this type of entity. Should <em>not</em> be called from outside this class;
     * use {@link #writeUnlessPassenger} or {@link #writeWithoutTypeId} instead.
     */
    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
       super.writeEntityToNBT(compound);
       compound.setBoolean("Saddled", this.canBeSteered());
       compound.setInteger("SpellTicks", this.spellTicks);
       
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
       super.readEntityFromNBT(compound);
       this.setSaddled(compound.getBoolean("Saddled"));
       this.spellTicks = compound.getInteger("SpellTicks");
    }
    
    public class AICastingSpell extends EntityAIBase {
        public AICastingSpell() {
        	this.setMutexBits(3);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            return EntityRideableFlyingMob.this.getSpellTicks() > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            super.startExecuting();
            EntityRideableFlyingMob.this.navigator.clearPath();
        }
        
        public void resetTask() {
            super.resetTask();
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask() {
            if (EntityRideableFlyingMob.this.getAttackTarget() != null) {
            	EntityRideableFlyingMob.this.getLookHelper().setLookPositionWithEntity(EntityRideableFlyingMob.this.getAttackTarget(), (float)EntityRideableFlyingMob.this.getHorizontalFaceSpeed(), (float)EntityRideableFlyingMob.this.getVerticalFaceSpeed());
            }
        }
    }
}
