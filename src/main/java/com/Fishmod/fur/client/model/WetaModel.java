package com.Fishmod.fur.client.model;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.tameable.WetaEntity;

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
public class WetaModel extends GeoModel<WetaEntity> {
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/weta/weta.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/weta/weta1.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/weta/weta2.png")
	};	
	
    private static final ResourceLocation ANIMATIONS = new ResourceLocation(mod_LavaCow.MODID, "animations/weta.animation.json");

    private static final ResourceLocation MODEL = new ResourceLocation(mod_LavaCow.MODID, "geo/weta.geo.json");
    
    @Override
    public ResourceLocation getTextureResource(WetaEntity object) {
        return TEXTURES[object.isBaby()? 1 : object.getSkin()];
    }

    @Override
    public ResourceLocation getAnimationResource(WetaEntity animatable) {
        return ANIMATIONS;
    }

	@Override
	public ResourceLocation getModelResource(WetaEntity animatable) {
		return MODEL;
	}
	
    @Override
    public void setCustomAnimations(WetaEntity animatable, long instanceId, AnimationState<WetaEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("Head");    	        
        EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        if (head != null) {
            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
    
    @Nullable
    @Override
	public RenderType getRenderType(WetaEntity entity, ResourceLocation texture) {
    	return RenderType.entityTranslucent(this.getTextureResource(entity));
    }
}
