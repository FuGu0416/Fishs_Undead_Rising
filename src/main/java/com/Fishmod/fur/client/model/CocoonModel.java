package com.Fishmod.fur.client.model;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.tameable.CocoonEntity;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

/**
 * ModelZombie - Either Mojang or a mod author
 * Created using Tabula 7.0.1
 */
public class CocoonModel extends GeoModel<CocoonEntity> {
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/cocoon/cocoon.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/cocoon/cocoon1.png")
	};	
	
    private static final ResourceLocation ANIMATIONS = new ResourceLocation(mod_LavaCow.MODID, "animations/cocoon.animation.json");

    private static final ResourceLocation MODEL = new ResourceLocation(mod_LavaCow.MODID, "geo/cocoon.geo.json");
    
    @Override
    public ResourceLocation getTextureResource(CocoonEntity object) {
        return TEXTURES[object.getSkin()];
    }

    @Override
    public ResourceLocation getAnimationResource(CocoonEntity animatable) {
        return ANIMATIONS;
    }

	@Override
	public ResourceLocation getModelResource(CocoonEntity animatable) {
		return MODEL;
	}	
    
    @Nullable
    @Override
	public RenderType getRenderType(CocoonEntity entity, ResourceLocation texture) {
    	return RenderType.entityTranslucent(this.getTextureResource(entity));
    }
}
