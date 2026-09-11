package com.Fishmod.fur.client.model;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.CadavoarEntity;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

/**
 * PLACEHOLDER — running on a copy of the Salamander geo/animation (see PLACEHOLDERS.md); the
 * 1.16.5 UndeadSwineModel (hand-coded Java) was not auto-convertible.
 */
public class CadavoarModel extends GeoModel<CadavoarEntity> {
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/cadavoar/cadavoar.png")
	};

    private static final ResourceLocation ANIMATIONS = new ResourceLocation(mod_LavaCow.MODID, "animations/cadavoar.animation.json");

    private static final ResourceLocation MODEL = new ResourceLocation(mod_LavaCow.MODID, "geo/cadavoar.geo.json");

    @Override
    public ResourceLocation getTextureResource(CadavoarEntity object) {
        return TEXTURES[0];
    }

    @Override
    public ResourceLocation getAnimationResource(CadavoarEntity animatable) {
        return ANIMATIONS;
    }

	@Override
	public ResourceLocation getModelResource(CadavoarEntity animatable) {
		return MODEL;
	}

    @Override
    public void setCustomAnimations(CadavoarEntity animatable, long instanceId, AnimationState<CadavoarEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("Head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }

    @Nullable
    @Override
	public RenderType getRenderType(CadavoarEntity entity, ResourceLocation texture) {
    	return RenderType.entityTranslucent(this.getTextureResource(entity));
    }
}
