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

    static {
        for (ResourceLocation texture : TEXTURES)
            System.out.println(texture.getResourcePath());
    }

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
     */
    public void doRender(EntitySoulWorm entity, double x, double y, double z, float entityYaw, float partialTicks) {
        this.shadowSize = entity.getLocationFix() > 3.0D ? 0.0F : 0.5F;
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    protected void preRenderCallback(EntitySoulWorm entity, float partialTickTime) {
        GlStateManager.translate(0.0D, entity.getLocationFix(), 0.0D);
        GlStateManager.rotate(90.0F * (float) entity.getLocationFix(), 0.0F, 1.0F, 0.0F);
    }
}
