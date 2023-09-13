package com.Fishmod.mod_LavaCow.client.layer;

import com.Fishmod.mod_LavaCow.client.model.entity.ModelEnigmoth;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderEnigmoth;
import com.Fishmod.mod_LavaCow.entities.flying.EntityEnigmoth;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class LayerEnigmothSaddle implements LayerRenderer<EntityEnigmoth> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("mod_lavacow:textures/mobs/enigmoth/enigmoth_saddle.png");
    private final RenderEnigmoth pigRenderer;
    private final ModelEnigmoth pigModel = new ModelEnigmoth();

    public LayerEnigmothSaddle(RenderEnigmoth pigRendererIn)
    {
        this.pigRenderer = pigRendererIn;
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }

	@Override
	public void doRenderLayer(EntityEnigmoth entitylivingbaseIn, float limbSwing, float limbSwingAmount,
			float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (entitylivingbaseIn.getSaddled())
        {
            this.pigRenderer.bindTexture(TEXTURE);
            this.pigModel.setModelAttributes(this.pigRenderer.getMainModel());
            this.pigModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }		
	}
}
