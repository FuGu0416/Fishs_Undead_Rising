package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.entities.tameable.WetaEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.Fishmod.fur.client.model.WetaModel;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class WetaRenderer extends GeoEntityRenderer<WetaEntity> {	
    public WetaRenderer(EntityRendererProvider.Context rendermanagerIn) {
        super(rendermanagerIn, new WetaModel());
        this.shadowRadius = 0.5F;
    }
    
    @Override
    public ResourceLocation getTextureLocation(WetaEntity entity) {
    	return super.getTextureLocation(entity);
    }
    
    @Override
    protected void applyRotations(WetaEntity entity, PoseStack p_225621_2_, float ageInTicks, float rotationYaw, float partialTicks) {
    	super.applyRotations(entity, p_225621_2_, ageInTicks, rotationYaw, partialTicks);
    	if (entity.isBaby()) {
        	p_225621_2_.scale(0.5F, 0.5F, 0.5F);
        }    	
	}
}
