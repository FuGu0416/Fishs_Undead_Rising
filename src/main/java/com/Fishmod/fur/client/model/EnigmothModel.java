package com.Fishmod.fur.client.model;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.flying.EnigmothEntity;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

/**
 * ModelZombie - Either Mojang or a mod author
 * Created using Tabula 7.0.1
 */
public class EnigmothModel extends GeoModel<EnigmothEntity> {	
	private static final ResourceLocation TEXTURES = new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/enigmoth/enigmoth.png");
	
	private static final ResourceLocation TEXTURES_CHILD = new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/enigmoth/enigmoth_larva.png");
	
    private static final ResourceLocation ANIMATIONS = new ResourceLocation(mod_LavaCow.MODID, "animations/enigmoth.animation.json");
    private static final ResourceLocation ANIMATIONS_CHILD = new ResourceLocation(mod_LavaCow.MODID, "animations/enigmoth_larva.animation.json");
    private static final ResourceLocation MODEL = new ResourceLocation(mod_LavaCow.MODID, "geo/enigmoth.geo.json");
    private static final ResourceLocation MODEL_CHILD = new ResourceLocation(mod_LavaCow.MODID, "geo/enigmoth_larva.geo.json");
    
    @Override
    public ResourceLocation getTextureResource(EnigmothEntity object) {
        return object.isBaby() ? TEXTURES_CHILD : TEXTURES;
    }

    @Override
    public ResourceLocation getAnimationResource(EnigmothEntity animatable) {
        return animatable.isBaby() ? ANIMATIONS_CHILD : ANIMATIONS;
    }

	@Override
	public ResourceLocation getModelResource(EnigmothEntity animatable) {
		return animatable.isBaby() ? MODEL_CHILD : MODEL;
	}
	
    @Override
    public void setCustomAnimations(EnigmothEntity animatable, long instanceId, AnimationState<EnigmothEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");
    	
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }        
    }
    
    @Nullable
    @Override
	public RenderType getRenderType(EnigmothEntity p_230496_1_, ResourceLocation texture) {
    	return RenderType.entityTranslucent(this.getTextureResource(p_230496_1_));
    }
}
