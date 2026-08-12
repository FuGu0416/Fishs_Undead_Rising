package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.client.model.entity.ModelBoneWorm;
import com.Fishmod.mod_LavaCow.entities.EntityBoneWorm;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderBoneWorm extends RenderLiving<EntityBoneWorm> {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[]{
            new ResourceLocation("mod_lavacow:textures/mobs/boneworm/boneworm.png")
    };

    public RenderBoneWorm(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelBoneWorm(), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityBoneWorm entity) {
        return TEXTURES[entity.getSkin()];
    }

    /**
     * Renders the desired {@code T} type Entity.
     *
     * <p>Fixed 2026-08-12 (per maintainer request, to match 1.16.5/1.20.1's already-fixed
     * behaviour): this used to always call {@code super.doRender(...)} regardless of dig depth -
     * unlike the newer versions, this renderer never actually hid the model while burrowed, it
     * only zeroed the shadow past 3.0. Combined with {@link EntityBoneWorm#attackEntityFrom} using
     * that same lone 3.0 threshold for invulnerability, the worm was fully visible the entire time
     * it was dug in. Now gated on {@link EntityBoneWorm#isHidden()}, the same check
     * {@code attackEntityFrom} uses, so visibility and invulnerability can't disagree.
     */
    public void doRender(EntityBoneWorm entity, double x, double y, double z, float entityYaw, float partialTicks) {
        boolean hidden = entity.isHidden();
        this.shadowSize = hidden ? 0.0F : 0.5F;
        if (!hidden)
            super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    protected void preRenderCallback(EntityBoneWorm entity, float partialTickTime) {
        // Fixed 2026-08-12: this translate used to be +locationFix (moving the model UP as it dug
        // in) instead of -locationFix (sinking it down) - the sign 1.16.5/1.20.1 both use, and the
        // one that actually reads as "burrowing into the ground". Left uncaught before since the
        // model was always fully visible anyway (see doRender above), so the wrong-direction float
        // was easy to miss without ever seeing the worm actually disappear.
        GlStateManager.translate(0.0D, -entity.getLocationFix(), 0.0D);
        GlStateManager.rotate(90.0F * (float) entity.getLocationFix(), 0.0F, 1.0F, 0.0F);
    }
}
