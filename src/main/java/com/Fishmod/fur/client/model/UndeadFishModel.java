package com.Fishmod.fur.client.model;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.aquatic.UndeadFishEntity;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

/**
 * ModelZombie - Either Mojang or a mod author
 * Created using Tabula 7.0.1
 */
public class UndeadFishModel extends GeoModel<UndeadFishEntity> {	
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
	    new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/fish/bone_trout.png"),
	    new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/fish/mummified_cod.png")
	};
		
    private static final ResourceLocation[] ANIMATIONS = new ResourceLocation[] {
	    new ResourceLocation(mod_LavaCow.MODID, "animations/bone_trout.animation.json"),
	    new ResourceLocation(mod_LavaCow.MODID, "animations/mummified_cod.animation.json")
    };

    private static final ResourceLocation[] MODEL = new ResourceLocation[] {
	    new ResourceLocation(mod_LavaCow.MODID, "geo/bone_trout.geo.json"),
	    new ResourceLocation(mod_LavaCow.MODID, "geo/mummified_cod.geo.json")
    };
    
    @Override
    public ResourceLocation getTextureResource(UndeadFishEntity object) {
        return TEXTURES[object.getSkin()];
    }

    @Override
    public ResourceLocation getAnimationResource(UndeadFishEntity animatable) {
        return ANIMATIONS[animatable.getSkin()];
    }

	@Override
	public ResourceLocation getModelResource(UndeadFishEntity animatable) {
		return MODEL[animatable.getSkin()];
	}
	
    @Override
    public void setCustomAnimations(UndeadFishEntity animatable, long instanceId, AnimationState<UndeadFishEntity> animationState) {
    	super.setCustomAnimations(animatable, instanceId, animationState);
    }
    
    @Nullable
    @Override
	public RenderType getRenderType(UndeadFishEntity p_230496_1_, ResourceLocation texture) {
    	return RenderType.entityTranslucent(this.getTextureResource(p_230496_1_));
    }
}
