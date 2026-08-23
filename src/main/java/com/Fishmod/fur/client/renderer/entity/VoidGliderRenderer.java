package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.entities.flying.VoidGliderEntity;
import com.Fishmod.fur.client.model.VoidGliderModel;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

@OnlyIn(Dist.CLIENT)
public class VoidGliderRenderer extends GeoEntityRenderer<VoidGliderEntity> {

    public VoidGliderRenderer(EntityRendererProvider.Context rendermanagerIn) {
    	super(rendermanagerIn, new VoidGliderModel());
        this.shadowRadius = 0.5F;

    	this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(VoidGliderEntity entity) {
    	return super.getTextureLocation(entity);
    }

    /** Applies the entity's randomly-rolled {@code SIZE[]} variant (see {@link VoidGliderEntity#getScale}). */
    @Override
    protected void applyRotations(VoidGliderEntity entity, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks) {
    	super.applyRotations(entity, poseStack, ageInTicks, rotationYaw, partialTicks);
    	float scale = entity.getScale();
    	poseStack.scale(scale, scale, scale);
    }
}
