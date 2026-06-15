package com.Fishmod.fur.client.model;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.UndertakerEntity;

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
public class UndertakerModel extends GeoModel<UndertakerEntity> {
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/undertaker/undertaker.png")
	};	
	
    private static final ResourceLocation ANIMATIONS = new ResourceLocation(mod_LavaCow.MODID, "animations/undertaker.animation.json");

    private static final ResourceLocation MODEL = new ResourceLocation(mod_LavaCow.MODID, "geo/undertaker.geo.json");
    
    @Override
    public ResourceLocation getTextureResource(UndertakerEntity object) {
        return TEXTURES[0];
    }

    @Override
    public ResourceLocation getAnimationResource(UndertakerEntity animatable) {
        return ANIMATIONS;
    }

	@Override
	public ResourceLocation getModelResource(UndertakerEntity animatable) {
		return MODEL;
	}
	
    @Override
    public void setCustomAnimations(UndertakerEntity animatable, long instanceId, AnimationState<UndertakerEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("Neck");
    	
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(-101.74F * Mth.DEG_TO_RAD + entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
    
    @Nullable
    @Override
	public RenderType getRenderType(UndertakerEntity entity, ResourceLocation texture) {
    	return RenderType.entityTranslucent(this.getTextureResource(entity));
    }
}
