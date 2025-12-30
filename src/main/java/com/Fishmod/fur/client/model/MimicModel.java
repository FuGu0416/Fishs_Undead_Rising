package com.Fishmod.fur.client.model;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.tameable.MimicEntity;

import software.bernie.geckolib.model.GeoModel;

/**
 * ModelZombiePiranha - Fish0016054
 * Created using Tabula 7.0.1
 */
public class MimicModel extends GeoModel<MimicEntity> {
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/mimic/mimic.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/mimic/mimic1.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/mimic/mimic2.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/mimic/mimic3.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/mimic/mimic4.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/mimic/mimic5.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/mimic/mimicvoid.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/mimic/mimicnether.png")			
	};
	
    private static final ResourceLocation ANIMATIONS = new ResourceLocation(mod_LavaCow.MODID, "animations/mimic.animation.json");

    private static final ResourceLocation MODEL = new ResourceLocation(mod_LavaCow.MODID, "geo/mimic.geo.json");	

	public static int getTombSkin() {
		return TEXTURES.length - 3;
	}
	
	public static int getVoidSkin() {
		return getTombSkin() + 1;
	}
    
	public static int getNetherSkin() {
		return getTombSkin() + 2;
	}
	   
    @Override
    public ResourceLocation getTextureResource(MimicEntity object) {
        return TEXTURES[object.getSkin()];
    }

    @Override
    public ResourceLocation getAnimationResource(MimicEntity animatable) {
        return ANIMATIONS;
    }

	@Override
	public ResourceLocation getModelResource(MimicEntity animatable) {
		return MODEL;
	}
	
    @Nullable
    @Override
	public RenderType getRenderType(MimicEntity p_230496_1_, ResourceLocation texture) {
    	return RenderType.entityTranslucent(this.getTextureResource(p_230496_1_));
    }
}
