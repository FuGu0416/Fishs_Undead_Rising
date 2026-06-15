package com.Fishmod.fur.client.model;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.WendigoEntity;

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
public class WendigoModel extends GeoModel<WendigoEntity> {
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/wendigo/wendigo.png")
	};	
	
    private static final ResourceLocation ANIMATIONS = new ResourceLocation(mod_LavaCow.MODID, "animations/wendigo.animation.json");

    private static final ResourceLocation MODEL = new ResourceLocation(mod_LavaCow.MODID, "geo/wendigo.geo.json");
    
    @Override
    public ResourceLocation getTextureResource(WendigoEntity object) {
        return TEXTURES[0];
    }

    @Override
    public ResourceLocation getAnimationResource(WendigoEntity animatable) {
        return ANIMATIONS;
    }

	@Override
	public ResourceLocation getModelResource(WendigoEntity animatable) {
		return MODEL;
	}
	
    @Override
    public void setCustomAnimations(WendigoEntity animatable, long instanceId, AnimationState<WendigoEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("Head");
    	
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
    
    @Nullable
    @Override
	public RenderType getRenderType(WendigoEntity entity, ResourceLocation texture) {
    	return RenderType.entityTranslucent(this.getTextureResource(entity));
    }
}
