package com.Fishmod.fur.client.model;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.aquatic.SwarmerEntity;
import software.bernie.geckolib.model.GeoModel;

/**
 * ModelZombiePiranha - Fish0016054
 * Created using Tabula 7.0.1
 */
public class SwarmerModel extends GeoModel<SwarmerEntity> {
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/swarmer/piranha.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/swarmer/swarmer.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/swarmer/swarmer1.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/swarmer/swarmer2.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/swarmer/swarmer3.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/swarmer/swarmer4.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/swarmer/swarmer5.png")
	};
	
    private static final ResourceLocation ANIMATIONS = new ResourceLocation(mod_LavaCow.MODID, "animations/swarmer.animation.json");

    private static final ResourceLocation MODEL = new ResourceLocation(mod_LavaCow.MODID, "geo/swarmer.geo.json");	
    
    @Override
    public ResourceLocation getTextureResource(SwarmerEntity object) {
        return TEXTURES[object.getSkin()];
    }

    @Override
    public ResourceLocation getAnimationResource(SwarmerEntity animatable) {
        return ANIMATIONS;
    }

	@Override
	public ResourceLocation getModelResource(SwarmerEntity animatable) {
		return MODEL;
	}
	
    @Nullable
    @Override
	public RenderType getRenderType(SwarmerEntity entity, ResourceLocation texture) {
    	return RenderType.entityTranslucent(this.getTextureResource(entity));
    }
}
