package com.Fishmod.fur.client.model;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.tameable.unburied.UnburiedEntity;

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
public class UnburiedModel extends GeoModel<UnburiedEntity> {
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/unburied/unburied.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/unburied/unburied1.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/unburied/unburied2.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/unburied/unburied3.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/unburied/unburied4.png")
	};
    private static final ResourceLocation ANIMATIONS = new ResourceLocation(mod_LavaCow.MODID, "animations/unburied.animation.json");

    private static final ResourceLocation MODEL = new ResourceLocation(mod_LavaCow.MODID, "geo/unburied.geo.json");
    
    @Override
    public ResourceLocation getTextureResource(UnburiedEntity object) {
        return TEXTURES[object.getSkin()];
    }

    @Override
    public ResourceLocation getAnimationResource(UnburiedEntity animatable) {
        return ANIMATIONS;
    }

	@Override
	public ResourceLocation getModelResource(UnburiedEntity animatable) {
		return MODEL;
	}
	
    @Override
    public void setCustomAnimations(UnburiedEntity animatable, long instanceId, AnimationState<UnburiedEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("Head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
    
    @Nullable
    @Override
	public RenderType getRenderType(UnburiedEntity p_230496_1_, ResourceLocation texture) {
    	return RenderType.entityTranslucent(this.getTextureResource(p_230496_1_));
    }
}
