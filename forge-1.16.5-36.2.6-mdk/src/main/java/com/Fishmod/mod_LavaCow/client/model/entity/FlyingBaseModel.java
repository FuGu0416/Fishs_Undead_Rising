package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.flying.FlyingMobEntity;

import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FlyingBaseModel<T extends Entity> extends FURBaseModel<T> {
	protected FlyingBaseModel.State state = FlyingBaseModel.State.FLYING;
	protected int HoverCounter = 10;
	
    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
	public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {	
    	if(entityIn instanceof FlyingMobEntity) {  		
    		FlyingMobEntity entity = (FlyingMobEntity)entityIn;
    		
	    	if (entity.isOnGround()) {
	    		this.state = FlyingBaseModel.State.WAITING;
	    	} else if (entity.getAttackTimer() > 0) {
	    		this.state = FlyingBaseModel.State.ATTACKING;
	    	} else if (HoverCounter < 10) {
	    		this.state = FlyingBaseModel.State.HOVERING;
	    	} else {
	    		this.state = FlyingBaseModel.State.FLYING;	    		
	    	}
	    	
	    	if(entity.getDeltaMovement().x <= 0.05 && entity.getDeltaMovement().z <= 0.05) {
	    		if(HoverCounter > 0)HoverCounter--;
	    	}
    		else if(HoverCounter < 10) HoverCounter++;
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
