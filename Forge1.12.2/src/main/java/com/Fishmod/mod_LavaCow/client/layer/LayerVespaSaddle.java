package com.Fishmod.mod_LavaCow.client.layer;

import com.Fishmod.mod_LavaCow.client.model.entity.ModelVespa;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderVespa;
import com.Fishmod.mod_LavaCow.entities.flying.EntityVespa;

import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class LayerVespaSaddle implements LayerRenderer<EntityVespa> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("mod_lavacow:textures/mobs/vespa/vespa_saddle.png");
    private final RenderVespa vespaRenderer;
    private final ModelVespa vespaModel = new ModelVespa();

    public LayerVespaSaddle(RenderVespa vespaRendererIn)
    {
        this.vespaRenderer = vespaRendererIn;
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }

	@Override
	public void doRenderLayer(EntityVespa entitylivingbaseIn, float limbSwing, float limbSwingAmount,
			float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (entitylivingbaseIn.canBeSteered()) {
            this.vespaRenderer.bindTexture(TEXTURE);
            this.vespaModel.setModelAttributes(this.vespaRenderer.getMainModel());
            this.vespaModel.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks);
            this.vespaModel.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entitylivingbaseIn);
            this.vespaModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }		
	}
}
