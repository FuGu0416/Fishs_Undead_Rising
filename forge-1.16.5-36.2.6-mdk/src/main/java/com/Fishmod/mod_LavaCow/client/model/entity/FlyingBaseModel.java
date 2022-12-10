package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.flying.FlyingMobEntity;

import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FlyingBaseModel<T extends Entity> extends FURBaseModel<T> {
	protected FlyingBaseModel.State state = FlyingBaseModel.State.FLYING;
	
    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
	public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {	
    	if(entityIn instanceof FlyingMobEntity) {  		
    		FlyingMobEntity entity = (FlyingMobEntity)entityIn;
    		
	    	if (entity.getLandTimer() > 10) {
	    		this.state = FlyingBaseModel.State.WAITING;
	    	} else if (entity.getAttackTimer() > 0) {
	    		this.state = FlyingBaseModel.State.ATTACKING;
	    	} else if (entity.getHoverTimer() < 20) {
	    		this.state = FlyingBaseModel.State.HOVERING;
	    	} else {
	    		this.state = FlyingBaseModel.State.FLYING;	    		
	    	}
	    	
	    	if (entity.isOnGround()) {
	    		entity.setHoverTimer(30);	    		
	    	} else if (entity.getDeltaMovement().x <= 0.05D && entity.getDeltaMovement().z <= 0.05D) {
	    		if(entity.getHoverTimer() > 0)
	    			entity.setHoverTimer(entity.getHoverTimer() - 1);
	    	} else if (entity.getHoverTimer() < 60) {
	    		entity.setHoverTimer(entity.getHoverTimer() + 1);
	    	}	   
	    	
	    	if (entity.isOnGround()) {
	    		if (entity.getLandTimer() < 20) {
	    			entity.setLandTimer(entity.getLandTimer() + 1);
	    		}
	    	} else {
	    		if (entity.getLandTimer() > 0) {
	    			entity.setLandTimer(entity.getLandTimer() - 1);
	    		}
	    	}
    	}
    }
	
    static enum State
    {
        FLYING,
        HOVERING,
        ATTACKING,
        WAITING;
    }
}
