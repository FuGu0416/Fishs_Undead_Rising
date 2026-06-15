package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.entities.tameable.MimicEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.Fishmod.fur.client.layer.LayerMimicChest;
import com.Fishmod.fur.client.model.MimicModel;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class MimicRenderer extends GeoEntityRenderer<MimicEntity> {
	
    public MimicRenderer(EntityRendererProvider.Context rendermanagerIn) {
    	super(rendermanagerIn, new MimicModel());
    	this.addRenderLayer(new LayerMimicChest<>(this));
        this.shadowRadius = 0.5F;
    }
    
    @Override
    public ResourceLocation getTextureLocation(MimicEntity entity) {
    	return super.getTextureLocation(entity);
    }    
    
    @Override
    protected void applyRotations(MimicEntity entity, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks) {
    	super.applyRotations(entity, poseStack, ageInTicks, rotationYaw, partialTicks);
    	if (entity.isBaby()) {
        	poseStack.scale(0.5F, 0.5F, 0.5F);
        }    	
    	
    	if (entity.isInSittingPose()) {
    		this.shadowRadius = 0.0F;
    	} else {
    		this.shadowRadius = 0.5F;
    	}
	}
}
