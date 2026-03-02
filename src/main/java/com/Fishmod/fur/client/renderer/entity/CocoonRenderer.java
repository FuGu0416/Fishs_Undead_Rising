package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.entities.tameable.CocoonEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.Fishmod.fur.client.model.CocoonModel;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class CocoonRenderer extends GeoEntityRenderer<CocoonEntity> {
	
    public CocoonRenderer(EntityRendererProvider.Context rendermanagerIn) {
    	super(rendermanagerIn, new CocoonModel());
        this.shadowRadius = 0.2F;
    }
    
    @Override
    protected void applyRotations(CocoonEntity entity, PoseStack p_225621_2_, float ageInTicks, float rotationYaw, float partialTicks) {
    	super.applyRotations(entity, p_225621_2_, ageInTicks, rotationYaw, partialTicks);
    	p_225621_2_.scale(1.5F, 1.5F, 1.5F);  	
	}
}
