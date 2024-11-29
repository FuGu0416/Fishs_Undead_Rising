package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.client.model.entity.SkeletonKingModel;
import com.Fishmod.mod_LavaCow.entities.SkeletonKingEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SkeletonKingRenderer extends MobRenderer<SkeletonKingEntity, SkeletonKingModel<SkeletonKingEntity>> {
	private static final ResourceLocation TEXTURES = new ResourceLocation("mod_lavacow:textures/mobs/skeletonking.png");
	
	static{
        System.out.println(TEXTURES.getPath());
    }

	public SkeletonKingRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new SkeletonKingModel<>(), 0.5F);
    }
    
    @Override
	public ResourceLocation getTextureLocation(SkeletonKingEntity entity) {
        return TEXTURES;
    }
}
