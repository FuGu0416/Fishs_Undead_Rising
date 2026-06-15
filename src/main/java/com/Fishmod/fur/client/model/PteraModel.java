package com.Fishmod.fur.client.model;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.flying.PteraEntity;

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
public class PteraModel extends GeoModel<PteraEntity> {
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
		new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/ptera/ptera.png"),
		new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/ptera/ptera1.png"),
		new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/ptera/ptera2.png"),
		new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/ptera/ptera3.png"),
		new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/ptera/ptera4.png"),
		new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/ptera/ptera5.png")
	};	
	
    private static final ResourceLocation ANIMATIONS = new ResourceLocation(mod_LavaCow.MODID, "animations/ptera.animation.json");

    private static final ResourceLocation MODEL = new ResourceLocation(mod_LavaCow.MODID, "geo/ptera.geo.json");
    
    @Override
    public ResourceLocation getTextureResource(PteraEntity object) {
        return TEXTURES[object.getSkin()];
    }

    @Override
    public ResourceLocation getAnimationResource(PteraEntity animatable) {
        return ANIMATIONS;
    }

	@Override
	public ResourceLocation getModelResource(PteraEntity animatable) {
		return MODEL;
	}
	
    @Override
    public void setCustomAnimations(PteraEntity animatable, long instanceId, AnimationState<PteraEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("Head");
    	
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
    
    @Nullable
    @Override
	public RenderType getRenderType(PteraEntity entity, ResourceLocation texture) {
    	return RenderType.entityTranslucent(this.getTextureResource(entity));
    }
}
