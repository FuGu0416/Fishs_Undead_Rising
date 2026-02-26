package com.Fishmod.fur.entities.flying;

import java.util.EnumSet;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.init.FURKeybindRegistry;
import com.Fishmod.fur.message.MessageMountSpecial;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RidableFlyingMobEntity extends FlyingMobEntity implements Saddleable {
	private static final EntityDataAccessor<Boolean> SADDLED = SynchedEntityData.defineId(RidableFlyingMobEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Byte> CONTROL_STATE = SynchedEntityData.defineId(RidableFlyingMobEntity.class, EntityDataSerializers.BYTE); // BIT(0): up, BIT(1): down, BIT(2): ability 
	public int abilityCooldown;
	protected int spellTicks;
	
	public RidableFlyingMobEntity(EntityType<? extends FlyingMobEntity> p_i48549_1_, Level worldIn) {
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
    protected void registerGoals() {
    	super.registerGoals();		
        this.goalSelector.addGoal(1, new AICastingApell());
	    this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
	    this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
    }
    
    public boolean isSpellcasting() {
    	return this.spellTicks > 0;
    }
    
    public int getSpellTicks() {
        return this.spellTicks;
    }
    
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
    	ItemStack itemstack = player.getItemInHand(hand); 
    	boolean flag = this.isFood(itemstack);
    	
    	if (!flag && this.isOwnedBy(player) && this.isSaddled() && !this.isVehicle()) {
        	if (itemstack.getItem().equals(Items.SHEARS)) {
    			this.setSaddled(false);  			
    			this.spawnAtLocation(Items.SADDLE, 1);      		
    			return InteractionResult.sidedSuccess(this.level().isClientSide);
    		} else if (!player.isSecondaryUseActive() && !player.isPassenger()) {
        	   player.startRiding(this);        	   
        	   return InteractionResult.sidedSuccess(this.level().isClientSide);
    		}    	
        }
        
    	return super.mobInteract(player, hand);
    }
    
    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    @Override
    public double getPassengersRidingOffset() {
        return (double)this.getBbHeight() * 0.9D;
    }
    
    @Override
    public void positionRider(Entity passenger, Entity.MoveFunction p_19958_) {
        super.positionRider(passenger);
        if (passenger instanceof Mob) {
        	this.yBodyRot = ((Mob)passenger).yBodyRot;
        	this.setYRot(passenger.getYRot());
        }
    }
    
    @Nullable
    public LivingEntity getControllingPassenger() {
        for (Entity passenger : this.getPassengers()) {
            if (passenger instanceof Player) {
                Player player = (Player) passenger;
                if (this.isTame() && this.getOwnerUUID() != null && this.getOwnerUUID().equals(player.getUUID())) {
                    return player;
                }
            }
        }
        return null;
    }
    
    public boolean canBeControlledByRider() {
    	return this.getControllingPassenger() instanceof Player;
    }
    
    public boolean isRidingPlayer(Player player) {
        return this.getControllingPassenger() != null && this.getControllingPassenger() instanceof Player && this.getControllingPassenger().getUUID().equals(player.getUUID());
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
        
        if (this.spellTicks > 0) {
            --this.spellTicks;
            this.setDeltaMovement(Vec3.ZERO);
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
    		
    	if (this.level().isClientSide) {
            this.ClientControl();
        }
    	
        if (!this.level().isClientSide && this.isAlive()) {
            if (this.random.nextInt(900) == 0 && this.deathTime == 0) {
               this.heal(1.0F);
            }
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
    public void travel(Vec3 p_213352_1_) {
        if (this.isAlive()) {
	        if (this.isVehicle() && this.canBeControlledByRider() && this.isSaddled()) {
	        	LivingEntity controller = (LivingEntity)this.getControllingPassenger();
	        	this.setYRot(controller.getYRot());
	            this.yRotO = this.getYRot();
	            this.setXRot(controller.getXRot() * 0.5F);
	            this.setRot(this.getYRot(), this.getXRot());
	            this.yBodyRot = this.getYRot();
	            this.yHeadRot = this.getYRot();
	            this.setMaxUpStep(1.0F);
	            float f = controller.xxa * 0.5F;
	            float f1 = controller.zza;
	
	            if (this.isControlledByLocalInstance()) {
	                super.travel(new Vec3((double)f, p_213352_1_.y, (double)f1));
	            } else {
	            	this.setDeltaMovement(Vec3.ZERO);
	            }         
	            
	            this.calculateEntityAnimation(false);
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
    public void equipSaddle(@Nullable SoundSource p_230266_1_) {
    	this.setSaddled(true);
        if (p_230266_1_ != null) {
           this.level().playSound((Player)null, this, SoundEvents.HORSE_SADDLE, p_230266_1_, 0.5F, 1.0F);
        }
	}
    
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setSaddled(compound.getBoolean("Saddled"));
        this.spellTicks = compound.getInt("SpellTicks");
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Saddled", this.isSaddled());
        compound.putInt("SpellTicks", this.spellTicks);
    }
	
	public class AICastingApell extends Goal {	
        public AICastingApell() {
        	this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean canUse() {
            return RidableFlyingMobEntity.this.getSpellTicks() > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            super.start();
            RidableFlyingMobEntity.this.getNavigation().stop();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
            super.stop();
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (RidableFlyingMobEntity.this.getTarget() != null) {
                RidableFlyingMobEntity.this.getLookControl().setLookAt(RidableFlyingMobEntity.this.getTarget(), (float)RidableFlyingMobEntity.this.getMaxHeadYRot(), (float)RidableFlyingMobEntity.this.getMaxHeadXRot());
            }
        }
    }
}
