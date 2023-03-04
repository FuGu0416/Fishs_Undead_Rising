package com.Fishmod.mod_LavaCow.entities;

import com.Fishmod.mod_LavaCow.entities.flying.BeelzebubEntity;
import com.Fishmod.mod_LavaCow.entities.flying.VespaEntity;
import com.Fishmod.mod_LavaCow.entities.tameable.FURTameableEntity;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class VespaCocoonEntity extends FURTameableEntity {
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.defineId(VespaCocoonEntity.class, DataSerializers.INT);
	private int Lifespan = 8 * 20;
	
	public VespaCocoonEntity(EntityType<? extends VespaCocoonEntity> p_i48549_1_, World worldIn) {
        super(p_i48549_1_, worldIn);
    }
	
	@Override
    protected void registerGoals() {
		super.registerGoals();
    }
    
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
        		.add(Attributes.MAX_HEALTH, 20.0D)
        		.add(Attributes.ARMOR, 10.0D)
        		.add(Attributes.MOVEMENT_SPEED, 0.0D)
        		.add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }
    
    @Override
    protected void defineSynchedData() {
    	super.defineSynchedData();
    	this.getEntityData().define(SKIN_TYPE, Integer.valueOf(0));
    }
    
    protected boolean isCommandable() {
    	return false;
    }
    
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void aiStep() {
        super.aiStep();
        
        if (this.tickCount >= Lifespan) {
        	this.playSound(SoundEvents.SLIME_SQUISH, 1.0F, 1.0F);
        	
    		if (!this.level.isClientSide) {		
    			if (this.getType().equals(FUREntityRegistry.VESPACOCOON) && this.getSkin() == 0) {
		    		VespaEntity adult = FUREntityRegistry.VESPA.create(this.level);
		    		adult.moveTo(this.getX(), this.getY(), this.getZ(), this.yRot, this.xRot);
		    		this.level.addFreshEntity(adult);
		    		if (this.isTame() && this.getOwner() instanceof PlayerEntity) {
		    			adult.tame((PlayerEntity) this.getOwner());
		    		}
    			} else if (this.getType().equals(FUREntityRegistry.BEELZEBUBPUPA)) {
		    		BeelzebubEntity adult = FUREntityRegistry.BEELZEBUB.create(this.level);
		    		adult.moveTo(this.getX(), this.getY(), this.getZ(), this.yRot, this.xRot);
		    		this.level.addFreshEntity(adult);
		    		if (this.isTame() && this.getOwner() instanceof PlayerEntity) {
		    			adult.tame((PlayerEntity) this.getOwner());
		    		}    				
    			}
    		}
        	
    		this.remove();
        }
    }
    
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void tick() {   	
        super.tick();
        this.setDeltaMovement(0.0D, this.getDeltaMovement().y, 0.0D);
    }
    
    public int getSkin() {
    	return this.getEntityData().get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
    	this.getEntityData().set(SKIN_TYPE, Integer.valueOf(skinType));
    }
    
    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean hurt(DamageSource source, float amount) {
		if (source.equals(DamageSource.IN_WALL) || source.equals(DamageSource.DROWN))
			return false;
		return super.hurt(source, amount);
    }
    
    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundNBT compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("lifespan", Lifespan);
		compound.putInt("Variant", this.getSkin());
	}

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
		super.readAdditionalSaveData(compound);
		Lifespan = compound.getInt("lifespan");
		this.setSkin(compound.getInt("Variant"));
	}
    
    @Override
    public boolean isImmobile() {
        return true;
    }
    
    /**
     * Applies a velocity to the entities, to push them away from eachother.
     */
    @Override
    public void push(Entity entityIn) {
    }

    protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
        return 0.8F;
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.WOOL_BREAK;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.PARASITE_DEATH;
    }

    public CreatureAttribute getMobType() {
	    return CreatureAttribute.ARTHROPOD;
	}
}
