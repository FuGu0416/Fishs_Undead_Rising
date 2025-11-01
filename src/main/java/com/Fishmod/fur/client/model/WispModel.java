package com.Fishmod.fur.client.model;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.tameable.WispEntity;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

/**
 * ModelZombie - Either Mojang or a mod author
 * Created using Tabula 7.0.1
 */
public class WispModel extends GeoModel<WispEntity> {
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/wisp/wisp.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/wisp/wisp1.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/wisp/wisp2.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/wisp/wisp3.png")
	};
    private static final ResourceLocation ANIMATIONS = new ResourceLocation(mod_LavaCow.MODID, "animations/wisp.animation.json");

    private static final ResourceLocation MODEL = new ResourceLocation(mod_LavaCow.MODID, "geo/wisp.geo.json");
    
    @Override
    public ResourceLocation getTextureResource(WispEntity object) {
        return TEXTURES[object.getSkin()];
    }

    @Override
    public ResourceLocation getAnimationResource(WispEntity animatable) {
        return ANIMATIONS;
    }

	@Override
	public ResourceLocation getModelResource(WispEntity animatable) {
		return MODEL;
	}
	
    @Override
    public void setCustomAnimations(WispEntity animatable, long instanceId, AnimationState<WispEntity> animationState) {

    }
}
