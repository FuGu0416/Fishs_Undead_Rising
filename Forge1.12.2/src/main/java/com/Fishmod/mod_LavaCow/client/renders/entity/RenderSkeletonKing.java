package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.client.model.entity.ModelSkeletonKing;
import com.Fishmod.mod_LavaCow.entities.EntitySkeletonKing;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderSkeletonKing extends RenderLiving<EntitySkeletonKing>{
	private static ResourceLocation TEXTURES = new ResourceLocation("mod_lavacow:textures/mobs/skeletonking.png");
	
	static{
        System.out.println(TEXTURES.getResourcePath());
    }

	public RenderSkeletonKing(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelSkeletonKing(), 0.5F);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EntitySkeletonKing entity) {
        return TEXTURES;
    }
    
    @Override
	protected void preRenderCallback(EntitySkeletonKing entity, float partialTickTime) {
	}
}
