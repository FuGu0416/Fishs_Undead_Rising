package com.Fishmod.fur.client.model;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.flying.BeelzebubEntity;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

@OnlyIn(Dist.CLIENT)
public class BeelzebubModel extends GeoModel<BeelzebubEntity> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/beelzebub/beelzebub.png");

    private static final ResourceLocation ANIMATIONS = new ResourceLocation(mod_LavaCow.MODID, "animations/beelzebub.animation.json");

    private static final ResourceLocation MODEL = new ResourceLocation(mod_LavaCow.MODID, "geo/beelzebub.geo.json");

    @Override
    public ResourceLocation getTextureResource(BeelzebubEntity entity) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(BeelzebubEntity animatable) {
        return ANIMATIONS;
    }

    @Override
    public ResourceLocation getModelResource(BeelzebubEntity animatable) {
        return MODEL;
    }

    @Override
    public void setCustomAnimations(BeelzebubEntity animatable, long instanceId, AnimationState<BeelzebubEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("Head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }

        // 1.16.5's harvestable "swollen gland" cue: BeelzebubModel#renderToBuffer re-rendered UAbdomen1
        // (cascading to UAbdomen2/UAbdomen3) a second time at a 1.05/1.3/1.05 scale with a small
        // (0, -0.2, -0.05) block-space nudge, on both the base texture pass and the gland-glow layer
        // pass since both went through the same renderToBuffer call. GeckoLib has no such per-call
        // double-render, so this scales the abdomen bone itself instead -- one pose, inherited by every
        // render-layer pass (see LayerBeelzebubGland) exactly like 1.16.5's shared renderToBuffer did.
        // The nudge is converted from 1.16.5's raw block-space PoseStack.translate to GeckoLib's
        // pixel-space bone position (x16): (-3.2, -0.8) on Y/Z.
        CoreGeoBone abdomen = getAnimationProcessor().getBone("UAbdomen1");
        if (abdomen != null) {
            if (animatable.canHarvest()) {
                abdomen.updateScale(1.05F, 1.3F, 1.05F);
                abdomen.setPosY(abdomen.getPosY() - 3.2F);
                abdomen.setPosZ(abdomen.getPosZ() - 0.8F);
            } else {
                abdomen.updateScale(1.0F, 1.0F, 1.0F);
            }
        }
    }

    @Nullable
    @Override
    public RenderType getRenderType(BeelzebubEntity entity, ResourceLocation texture) {
        return RenderType.entityTranslucent(this.getTextureResource(entity));
    }
}
