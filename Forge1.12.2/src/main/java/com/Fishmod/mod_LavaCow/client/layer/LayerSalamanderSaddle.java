package com.Fishmod.mod_LavaCow.client.layer;

import com.Fishmod.mod_LavaCow.client.model.entity.ModelSalamander;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderSalamander;
import com.Fishmod.mod_LavaCow.entities.tameable.EntitySalamander;

import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class LayerSalamanderSaddle implements LayerRenderer<EntitySalamander> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("mod_lavacow:textures/mobs/salamander/salamander_saddle.png");
    private final RenderSalamander pigRenderer;
    private final ModelSalamander pigModel = new ModelSalamander();

    public LayerSalamanderSaddle(RenderSalamander pigRendererIn)
    {
        this.pigRenderer = pigRendererIn;
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }

	@Override
	public void doRenderLayer(EntitySalamander entitylivingbaseIn, float limbSwing, float limbSwingAmount,
			float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (entitylivingbaseIn.canBeSteered())
        {
            this.pigRenderer.bindTexture(TEXTURE);
            this.pigModel.setModelAttributes(this.pigRenderer.getMainModel());
            this.pigModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }		
	}
}
