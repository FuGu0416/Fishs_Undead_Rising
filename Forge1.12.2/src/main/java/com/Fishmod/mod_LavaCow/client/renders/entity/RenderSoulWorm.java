package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.client.layer.LayerGenericGlowing;
import com.Fishmod.mod_LavaCow.client.model.entity.ModelBoneWorm;
import com.Fishmod.mod_LavaCow.entities.EntitySoulWorm;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderSoulWorm extends RenderLiving<EntitySoulWorm> {
    private static final ResourceLocation TEXTURES_EYE = new ResourceLocation("mod_lavacow:textures/mobs/soulworm/soulworm_glow.png");
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[]{
            new ResourceLocation("mod_lavacow:textures/mobs/soulworm/soulworm.png"),
            new ResourceLocation("mod_lavacow:textures/mobs/soulworm/soulworm1.png")
    };

    public RenderSoulWorm(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelBoneWorm(), 0.5F);
        this.addLayer(new LayerGenericGlowing<>(this, TEXTURES_EYE));
    }

    @Override
    protected ResourceLocation getEntityTexture(EntitySoulWorm entity) {
        return TEXTURES[entity.getSkin()];
    }

    /**
     * Renders the desired {@code T} type Entity.
     *
     * <p>Fixed 2026-08-12 - same bug and same fix as {@code RenderBoneWorm#doRender}: this used to
     * always call {@code super.doRender(...)} regardless of dig depth instead of hiding the model
     * once {@link EntitySoulWorm#isHidden()} (inherited from {@code EntityBoneWorm}), the same
     * check {@code attackEntityFrom} uses for invulnerability.
     */
    public void doRender(EntitySoulWorm entity, double x, double y, double z, float entityYaw, float partialTicks) {
        boolean hidden = entity.isHidden();
        this.shadowSize = hidden ? 0.0F : 0.5F;
        if (!hidden)
            super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    protected void preRenderCallback(EntitySoulWorm entity, float partialTickTime) {
        // Fixed 2026-08-12 - same sign bug and same fix as RenderBoneWorm#preRenderCallback: this
        // was +locationFix (moving the model up as it dug in) instead of -locationFix (sinking it
        // down, the direction that actually reads as "burrowing").
        GlStateManager.translate(0.0D, -entity.getLocationFix(), 0.0D);
        GlStateManager.rotate(90.0F * (float) entity.getLocationFix(), 0.0F, 1.0F, 0.0F);
    }
}
