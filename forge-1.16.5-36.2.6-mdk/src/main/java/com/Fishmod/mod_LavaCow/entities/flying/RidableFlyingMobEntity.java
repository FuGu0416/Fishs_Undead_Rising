package com.Fishmod.mod_LavaCow.entities.flying;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.init.FURKeybindRegistry;
import com.Fishmod.mod_LavaCow.message.MessageMountSpecial;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IEquipable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RidableFlyingMobEntity extends FlyingMobEntity implements IEquipable {
	private static final DataParameter<Boolean> SADDLED = EntityDataManager.defineId(RidableFlyingMobEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Byte> CONTROL_STATE = EntityDataManager.defineId(RidableFlyingMobEntity.class, DataSerializers.BYTE); // BIT(0): up, BIT(1): down, BIT(2): ability 
	public int abilityCooldown;
	
	public RidableFlyingMobEntity(EntityType<? extends FlyingMobEntity> p_i48549_1_, World worldIn) {
		super(p_i48549_1_, worldIn);
		this.abilityCooldown = 0;
	}

    @Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SADDLED, Boolean.valueOf(false));
		this.entityData.define(CONTROL_STATE, Byte.valueOf((byte) 0));
	}
    
    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
    	ItemStack itemstack = player.getItemInHand(hand); 
    	boolean flag = this.isFood(itemstack);
    	
    	if (!flag && this.isOwnedBy(player) && this.isSaddled() && !this.isVehicle()) {
        	if (itemstack.getItem().equals(Items.SHEARS)) {
    			this.setSaddled(false);  			
    			this.spawnAtLocation(Items.SADDLE, 1);      		
    			return ActionResultType.sidedSuccess(this.level.isClientSide);
    		} else if (!player.isSecondaryUseActive() && !player.isPassenger()) {
        	   player.startRiding(this);        	   
        	   return ActionResultType.sidedSuccess(this.level.isClientSide);
    		}    	
        }
 
        ActionResultType actionResultType = itemstack.interactLivingEntity(player, this, hand);
        
        if (actionResultType.consumesAction()) {
        	return actionResultType;
        }
        
    	return super.mobInteract(player, hand);
    }
    
    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    @Override
    public double getPassengersRidingOffset() {
        return (double)this.getBbHeight() * 1.3D;
    }
    
    @Override
    public void positionRider(Entity passenger) {
        super.positionRider(passenger);
        if (passenger instanceof MobEntity) {
        	this.yBodyRot = ((MobEntity)passenger).yBodyRot;
            this.yRot = passenger.yRot;
        }
    }
    
    @Nullable
    public Entity getControllingPassenger() {
        for (Entity passenger : this.getPassengers()) {
            if (passenger instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) passenger;
                if (this.isTame() && this.getOwnerUUID() != null && this.getOwnerUUID().equals(player.getUUID())) {
                    return player;
                }
            }
        }
        return null;
    }
    
    public boolean canBeControlledByRider() {
    	return this.getControllingPassenger() instanceof PlayerEntity;
    }
    
    public boolean isRidingPlayer(PlayerEntity player) {
        return this.getControllingPassenger() != null && this.getControllingPassenger() instanceof PlayerEntity && this.getControllingPassenger().getUUID().equals(player.getUUID());
    }
    
    public int abilityCooldown() {
    	return 0;
    }
    
    @Override
    public void tick() {
    	super.tick();

        if (this.abilityCooldown > 0) {
        	this.abilityCooldown--;
        }
        
    	if(this.isUp() && !this.isDown()) {
    		this.setDeltaMovement(this.getDeltaMovement().add(0.0F, 0.05F, 0.0F));
    	}
    	
    	if(!this.isUp() && this.isDown()) {
    		this.setDeltaMovement(this.getDeltaMovement().add(0.0F, -0.05F, 0.0F));
    	}   	
    }
    
    @Override
    public void aiStep() {
    	super.aiStep();
    		
    	if (this.level.isClientSide) {
            this.ClientControl();
        }
    }
    
    @OnlyIn(Dist.CLIENT)
    protected void ClientControl() {
    	Minecraft game = Minecraft.getInstance();
    	
		if (this.abilityCooldown == 0 && FURKeybindRegistry.MOUNT_SPECIAL.isDown() && this.isRidingPlayer(game.player) && this.getLandTimer() <= 10) {
			this.abilityCooldown = this.abilityCooldown();
			mod_LavaCow.NETWORK.sendToServer(new MessageMountSpecial(this.getId(), this.getX(), this.getY(), this.getZ()));
		}
    	
    	if (this.isRidingPlayer(game.player)) {
        	this.setControlState(0, game.options.keyJump.isDown());
        	this.setControlState(1, FURKeybindRegistry.MOUNT_DOWN.isDown());
    	}
	
    }
    
    /**
     * Set or remove the saddle of the pig.
     */
    public void setSaddled(boolean saddled) {
    	this.getEntityData().set(SADDLED, Boolean.valueOf(saddled));
    }
    
    /**
     * returns true if all the conditions for steering the entity are met. For pigs, this is true if it is being ridden
     * by a player and the player is holding a carrot-on-a-stick
     */
    @Override
    public boolean isSaddled() {
    	return this.getEntityData().get(SADDLED).booleanValue();
    }
    
    private boolean isUp() {
    	return (entityData.get(CONTROL_STATE).byteValue() & 1) == 1;
    }
    
    private boolean isDown() {
    	return ((entityData.get(CONTROL_STATE).byteValue() >> 1) & 1) == 1;
    }
    
    private void setControlState(int byteLoc, boolean stateIn) {
        byte prevState = entityData.get(CONTROL_STATE).byteValue();
        if (stateIn) {
            entityData.set(CONTROL_STATE, (byte) (prevState | (1 << byteLoc)));
        } else {
            entityData.set(CONTROL_STATE, (byte) (prevState & ~(1 << byteLoc)));
        }
    }
    
    @Override
    public void travel(Vector3d p_213352_1_) {
        if (this.isAlive()) {
	        if (this.isVehicle() && this.canBeControlledByRider() && this.isSaddled()) {
	        	LivingEntity controller = (LivingEntity)this.getControllingPassenger();
	        	this.yRot = controller.yRot;
	            this.yRotO = this.yRot;
	            this.xRot = controller.xRot * 0.5F;
	            this.setRot(this.yRot, this.xRot);
	            this.yBodyRot = this.yRot;
	            this.yHeadRot = this.yRot;
	            this.maxUpStep = 1.0F;
	            this.flyingSpeed = this.getSpeed() * 0.1F;
	            float f = controller.xxa * 0.5F;
	            float f1 = controller.zza;
	
	            if (this.isControlledByLocalInstance()) {
	                super.travel(new Vector3d((double)f, p_213352_1_.y, (double)f1));
	            } else {
	            	this.setDeltaMovement(Vector3d.ZERO);
	            }         
	            
	            this.calculateEntityAnimation(this, false);
	        } else {
	            super.travel(p_213352_1_);
	        }
        }
    }
    
	@Override
	public boolean isSaddleable() {
		return this.isAlive() && !this.isBaby() && this.isTame();
	}
    
    @Override
    protected void dropEquipment() {
    	super.dropEquipment();
        if (this.isSaddled()) {
           this.spawnAtLocation(Items.SADDLE);
        }
	}
    
    @Override
    public void equipSaddle(@Nullable SoundCategory p_230266_1_) {
    	this.setSaddled(true);
        if (p_230266_1_ != null) {
           this.level.playSound((PlayerEntity)null, this, SoundEvents.HORSE_SADDLE, p_230266_1_, 0.5F, 1.0F);
        }
	}
    
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.setSaddled(compound.getBoolean("Saddled"));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Saddled", this.isSaddled());
    }
}
