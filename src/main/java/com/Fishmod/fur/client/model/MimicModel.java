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

	// Baby form ("mimic_spawn") - same index order/meaning as TEXTURES above (see getTombSkin()/
	// getVoidSkin()/getNetherSkin()), just the baby's own dedicated art instead of a scaled-down adult.
	private static final ResourceLocation[] BABY_TEXTURES = new ResourceLocation[] {
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/mimic/mimic_spawn.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/mimic/mimic_spawn1.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/mimic/mimic_spawn2.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/mimic/mimic_spawn3.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/mimic/mimic_spawn4.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/mimic/mimic_spawn5.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/mimic/mimic_spawnvoid.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/mimic/mimic_spawnnether.png")
	};

    private static final ResourceLocation ANIMATIONS = new ResourceLocation(mod_LavaCow.MODID, "animations/mimic.animation.json");
    private static final ResourceLocation BABY_ANIMATIONS = new ResourceLocation(mod_LavaCow.MODID, "animations/mimic_spawn.animation.json");

    private static final ResourceLocation MODEL = new ResourceLocation(mod_LavaCow.MODID, "geo/mimic.geo.json");
    private static final ResourceLocation BABY_MODEL = new ResourceLocation(mod_LavaCow.MODID, "geo/mimic_spawn.geo.json");

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
        return (object.isBaby() ? BABY_TEXTURES : TEXTURES)[object.getSkin()];
    }

    @Override
    public ResourceLocation getAnimationResource(MimicEntity animatable) {
        return animatable.isBaby() ? BABY_ANIMATIONS : ANIMATIONS;
    }

	@Override
	public ResourceLocation getModelResource(MimicEntity animatable) {
		return animatable.isBaby() ? BABY_MODEL : MODEL;
	}
	
    @Nullable
    @Override
	public RenderType getRenderType(MimicEntity entity, ResourceLocation texture) {
    	return RenderType.entityTranslucent(this.getTextureResource(entity));
    }
}
