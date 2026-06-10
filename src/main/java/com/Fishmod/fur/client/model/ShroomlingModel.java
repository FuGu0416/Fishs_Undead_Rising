package com.Fishmod.fur.client.model;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.tameable.ShroomlingEntity;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class ShroomlingModel extends GeoModel<ShroomlingEntity> {
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/shroomling/shroomling.png")
	};

    private static final ResourceLocation ANIMATIONS = new ResourceLocation(mod_LavaCow.MODID, "animations/shroomling.animation.json");

    private static final ResourceLocation MODEL = new ResourceLocation(mod_LavaCow.MODID, "geo/shroomling.geo.json");

    @Override
    public ResourceLocation getTextureResource(ShroomlingEntity object) {
        return TEXTURES[0];
    }

    @Override
    public ResourceLocation getAnimationResource(ShroomlingEntity animatable) {
        return ANIMATIONS;
    }

	@Override
	public ResourceLocation getModelResource(ShroomlingEntity animatable) {
		return MODEL;
	}

    @Override
    public void setCustomAnimations(ShroomlingEntity animatable, long instanceId, AnimationState<ShroomlingEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("Head_skull");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }

    @Nullable
    @Override
	public RenderType getRenderType(ShroomlingEntity p_230496_1_, ResourceLocation texture) {
    	return RenderType.entityTranslucent(this.getTextureResource(p_230496_1_));
    }
}
