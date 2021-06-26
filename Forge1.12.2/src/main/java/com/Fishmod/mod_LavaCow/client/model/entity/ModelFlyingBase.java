package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.flying.EntityFlyingMob;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModelFlyingBase extends ModelBase {
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
    	
	    	if(entity.getAttackTimer() > 0) {
	    		this.state = ModelFlyingBase.State.ATTACKING;
	    	}
	    	else if(HoverCounter < 10) {
	    		this.state = ModelFlyingBase.State.HOVERING;
	    	}
	    	else {
	    		this.state = ModelFlyingBase.State.FLYING;	    		
	    	}
	    	
	    	if(entity.lastTickPosX == entity.posX && entity.lastTickPosZ == entity.posZ) {
	    		if(HoverCounter > 0)HoverCounter--;
	    	}
    		else if(HoverCounter < 10) HoverCounter++;
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
        ATTACKING;
    }
}
