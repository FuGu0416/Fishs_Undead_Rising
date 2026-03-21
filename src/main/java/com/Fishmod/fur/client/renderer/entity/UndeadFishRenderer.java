package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.client.model.UndeadFishModel;
import com.Fishmod.fur.entities.aquatic.UndeadFishEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class UndeadFishRenderer extends GeoEntityRenderer<UndeadFishEntity> {	

    public UndeadFishRenderer(EntityRendererProvider.Context rendermanagerIn) {
        super(rendermanagerIn, new UndeadFishModel());
        this.shadowRadius = 0.3F;
    }
    
    @Override
	public ResourceLocation getTextureLocation(UndeadFishEntity entity) {
    	return super.getTextureLocation(entity);
    }
    
    @Override
    protected void applyRotations(UndeadFishEntity entityLiving, PoseStack p_225621_2_, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, p_225621_2_, ageInTicks, rotationYaw, partialTicks);
        float f = (float) (4.3F * Math.sin(0.6F * ageInTicks));
        p_225621_2_.mulPose(Axis.YP.rotationDegrees(f));
        
		if (!entityLiving.isInWater()) {
			p_225621_2_.translate(0.1F, 0.1F, -0.1F);
			p_225621_2_.mulPose(Axis.ZP.rotationDegrees(90.0F));
		}
	}
}
