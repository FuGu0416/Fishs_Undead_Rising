package com.Fishmod.mod_LavaCow.client.renders;

import com.Fishmod.mod_LavaCow.client.model.entity.ModelBoneWorm;
import com.Fishmod.mod_LavaCow.entities.EntityBoneWorm;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderBoneWorm extends RenderLiving<EntityBoneWorm>{
	
	private static ResourceLocation TEXTURES = new ResourceLocation("mod_lavacow:textures/mobs/boneworm.png");
	static{
        System.out.println(TEXTURES.getResourcePath());
    }

    public RenderBoneWorm(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelBoneWorm(), 0.5F);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EntityBoneWorm entity) {
        return TEXTURES;
    }
    
    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(EntityBoneWorm entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
    	this.shadowSize = entity.getLocationFix() > 3.0D ? 0.0F : 0.5F;
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
    
    @Override
	protected void preRenderCallback(EntityBoneWorm entity, float partialTickTime) {
    	//if(!entity.isChild())GlStateManager.scale(1.8F, 1.8F, 1.8F);
    	GlStateManager.translate(0.0D, entity.getLocationFix(), 0.0D);
    	GlStateManager.rotate(90.0F * (float) entity.getLocationFix(), 0.0F, 1.0F, 0.0F);
    	//System.out.println("OXO " + entity.getLocationFix());
	}
}
