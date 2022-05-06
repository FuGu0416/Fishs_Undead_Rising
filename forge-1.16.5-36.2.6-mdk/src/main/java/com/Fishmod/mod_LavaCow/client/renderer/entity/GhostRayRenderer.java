package com.Fishmod.mod_LavaCow.client.renderer.entity;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.model.entity.GhostRayModel;
import com.Fishmod.mod_LavaCow.entities.flying.GhostRayEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class GhostRayRenderer extends MobRenderer<GhostRayEntity, GhostRayModel<GhostRayEntity>> {
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/ghostray/ghostray.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/ghostray/ghostray2.png")
	};
	
	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getPath());
    }

    public GhostRayRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new GhostRayModel<GhostRayEntity>(), 0.5F);
    }
    
    @Override
    public ResourceLocation getTextureLocation(GhostRayEntity entity) {
    	return TEXTURES[entity.getSkin()];
    }
    
    @Override
	protected void scale(GhostRayEntity entity, MatrixStack matrixStackIn, float partialTickTime) {
    	if(!entity.isBaby()) {
    		matrixStackIn.scale(GhostRayEntity.SIZE[entity.getSize()], GhostRayEntity.SIZE[entity.getSize()], GhostRayEntity.SIZE[entity.getSize()]);
    	}
	}
    
    protected int getBlockLightLevel(GhostRayEntity p_225624_1_, BlockPos p_225624_2_) {
        return 15;
    }
    
    @Nullable
    @Override
    protected RenderType getRenderType(GhostRayEntity p_230496_1_, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
    	return RenderType.entityTranslucent(this.getTextureLocation(p_230496_1_));
    }
}
