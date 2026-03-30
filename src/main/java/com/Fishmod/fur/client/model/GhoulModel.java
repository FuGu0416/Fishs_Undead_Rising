package com.Fishmod.fur.client.model;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.GhoulEntity;

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
public class GhoulModel extends GeoModel<GhoulEntity> {
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
		new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/ghoul/ghoul.png"),
		new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/ghoul/ghoul1.png"),
		new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/ghoul/ghoul2.png"),
		new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/ghoul/ghoul3.png"),
		new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/ghoul/ghoul4.png")
	};	
	
    private static final ResourceLocation ANIMATIONS = new ResourceLocation(mod_LavaCow.MODID, "animations/ghoul.animation.json");

    private static final ResourceLocation MODEL = new ResourceLocation(mod_LavaCow.MODID, "geo/ghoul.geo.json");
    
    @Override
    public ResourceLocation getTextureResource(GhoulEntity object) {
        return TEXTURES[object.getSkin()];
    }

    @Override
    public ResourceLocation getAnimationResource(GhoulEntity animatable) {
        return ANIMATIONS;
    }

	@Override
	public ResourceLocation getModelResource(GhoulEntity animatable) {
		return MODEL;
	}
	
    @Override
    public void setCustomAnimations(GhoulEntity animatable, long instanceId, AnimationState<GhoulEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");
     
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
    
    @Nullable
    @Override
	public RenderType getRenderType(GhoulEntity p_230496_1_, ResourceLocation texture) {
    	return RenderType.entityTranslucent(this.getTextureResource(p_230496_1_));
    }
}
