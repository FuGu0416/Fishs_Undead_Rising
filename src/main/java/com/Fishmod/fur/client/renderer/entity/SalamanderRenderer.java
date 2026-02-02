package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.entities.tameable.SalamanderEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.client.layer.LayerSaddle;
import com.Fishmod.fur.client.model.SalamanderModel;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

@OnlyIn(Dist.CLIENT)
public class SalamanderRenderer extends GeoEntityRenderer<SalamanderEntity> {	
    public SalamanderRenderer(EntityRendererProvider.Context rendermanagerIn) {
    	super(rendermanagerIn, new SalamanderModel());
        this.shadowRadius = 1.0F;
        
    	this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    	this.addRenderLayer(new LayerSaddle<>(this, new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/salamander/salamander_saddle.png")));
    }
    
    @Override
    protected void applyRotations(SalamanderEntity entity, PoseStack p_225621_2_, float ageInTicks, float rotationYaw, float partialTicks) {
    	super.applyRotations(entity, p_225621_2_, ageInTicks, rotationYaw, partialTicks);
    	switch (entity.getGrowingStage()) {
			case 0:
				p_225621_2_.scale(1.0F, 1.0F, 1.0F);
				break;
			case 1:
				p_225621_2_.scale(0.8F, 0.8F, 0.8F);
				break;
			case 2:
				p_225621_2_.scale(1.25F, 1.25F, 1.25F);
				break;
			default:
				p_225621_2_.scale(1.5F, 1.5F, 1.5F);
				break;   			
		}
	}   	
}
