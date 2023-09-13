package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.client.model.FishModelBase;
import com.Fishmod.mod_LavaCow.entities.flying.EntityFlyingMob;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModelFlyingBase extends FishModelBase {
	protected ModelFlyingBase.State state = ModelFlyingBase.State.FLYING;
	protected int HoverCounter = 10;
    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime)
    {
    	if(entitylivingbaseIn instanceof EntityFlyingMob) {
    		
    		EntityFlyingMob entity = (EntityFlyingMob)entitylivingbaseIn;
    	
	    	if (entity.getLandTimer() > 10) {
	    		this.state = ModelFlyingBase.State.WAITING;
	    	} else if (entity.getAttackTimer() > 0) {
	    		this.state = ModelFlyingBase.State.ATTACKING;
	    	} else if (entity.getHoverTimer() < 20) {
	    		this.state = ModelFlyingBase.State.HOVERING;
	    	} else {
	    		this.state = ModelFlyingBase.State.FLYING;	    		
	    	}
	    	
	    	if (entity.onGround) {
	    		entity.setHoverTimer(30);	    		
	    	} else if (entity.lastTickPosX <= 0.05D && entity.lastTickPosZ <= 0.05D) {
	    		if(entity.getHoverTimer() > 0)
	    			entity.setHoverTimer(entity.getHoverTimer() - 1);
	    	} else if (entity.getHoverTimer() < 60) {
	    		entity.setHoverTimer(entity.getHoverTimer() + 1);
	    	}	
    	}
    }
    
    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
	
    @SideOnly(Side.CLIENT)
    static enum State
    {
        FLYING,
        HOVERING,
        ATTACKING,
        WAITING;
    }
}
