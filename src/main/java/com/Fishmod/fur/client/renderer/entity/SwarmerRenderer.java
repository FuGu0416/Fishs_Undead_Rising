package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.client.model.SwarmerModel;
import com.Fishmod.fur.entities.aquatic.PiranhaEntity;
import com.Fishmod.fur.entities.aquatic.SwarmerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class SwarmerRenderer extends GeoEntityRenderer<SwarmerEntity> {	

    public SwarmerRenderer(EntityRendererProvider.Context rendermanagerIn) {
        super(rendermanagerIn, new SwarmerModel());
        this.shadowRadius = 0.3F;
    }
    
    @Override
	public ResourceLocation getTextureLocation(SwarmerEntity entity) {
    	return super.getTextureLocation(entity);
    }
    
    @Override
    protected void applyRotations(SwarmerEntity entityLiving, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, poseStack, ageInTicks, rotationYaw, partialTicks);
        float f = (float) (4.3F * Math.sin(0.6F * ageInTicks));
        poseStack.mulPose(Axis.YP.rotationDegrees(f));
        
        if (entityLiving instanceof PiranhaEntity) {
        	poseStack.scale(0.8F, 0.8F, 0.8F);
        }
	}
}
