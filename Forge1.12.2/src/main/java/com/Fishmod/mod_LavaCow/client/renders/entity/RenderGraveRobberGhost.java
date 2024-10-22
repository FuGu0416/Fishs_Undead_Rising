package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.client.layer.LayerGenericGlowing;
import com.Fishmod.mod_LavaCow.client.model.entity.ModelGraveRobberGhost;
import com.Fishmod.mod_LavaCow.entities.floating.EntityGraveRobberGhost;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderGraveRobberGhost extends RenderLiving<EntityGraveRobberGhost> {
    private static final ResourceLocation TEXTURES_EYE = new ResourceLocation("mod_lavacow:textures/mobs/graverobber/graverobber_ghost_glow.png");
    private static final ResourceLocation TEXTURE = new ResourceLocation("mod_lavacow:textures/mobs/graverobber/graverobber_ghost.png");

    public RenderGraveRobberGhost(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelGraveRobberGhost(), 0.0F);
        this.addLayer(new LayerGenericGlowing<>(this, TEXTURES_EYE));
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityGraveRobberGhost entity) {
        return TEXTURE;
    }

    @Override
    protected void preRenderCallback(EntityGraveRobberGhost entity, float partialTickTime) {
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1F, 1F, 1F, 0.85F);
    }
}
