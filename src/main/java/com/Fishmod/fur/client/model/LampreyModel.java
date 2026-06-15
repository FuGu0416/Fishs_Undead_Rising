package com.Fishmod.fur.client.model;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.aquatic.LampreyEntity;
import software.bernie.geckolib.model.GeoModel;

/**
 * ModelZombiePiranha - Fish0016054
 * Created using Tabula 7.0.1
 */
public class LampreyModel extends GeoModel<LampreyEntity> {
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/lamprey.png")
	};
	
    private static final ResourceLocation ANIMATIONS = new ResourceLocation(mod_LavaCow.MODID, "animations/lamprey.animation.json");

    private static final ResourceLocation MODEL = new ResourceLocation(mod_LavaCow.MODID, "geo/lamprey.geo.json");	
    
    @Override
    public ResourceLocation getTextureResource(LampreyEntity object) {
        return TEXTURES[0];
    }

    @Override
    public ResourceLocation getAnimationResource(LampreyEntity animatable) {
        return ANIMATIONS;
    }

	@Override
	public ResourceLocation getModelResource(LampreyEntity animatable) {
		return MODEL;
	}
	
    @Nullable
    @Override
	public RenderType getRenderType(LampreyEntity entity, ResourceLocation texture) {
    	return RenderType.entityTranslucent(this.getTextureResource(entity));
    }
}
