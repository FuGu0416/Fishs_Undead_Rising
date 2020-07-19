package com.Fishmod.mod_LavaCow.client.layer;

import com.Fishmod.mod_LavaCow.client.renders.RenderMimic;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityMimic;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class LayerMimicChest implements LayerRenderer<EntityMimic> {
    private static final ResourceLocation TEXTURE_ENDER = new ResourceLocation("textures/entity/chest/ender.png");
    private static final ResourceLocation TEXTURE_NORMAL = new ResourceLocation("textures/entity/chest/normal.png");
    private final RenderMimic pigRenderer;

    public LayerMimicChest(RenderMimic pigRendererIn)
    {
        this.pigRenderer = pigRendererIn;
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }

	@Override
	public void doRenderLayer(EntityMimic entitylivingbaseIn, float limbSwing, float limbSwingAmount,
			float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {

            this.pigRenderer.bindTexture(entitylivingbaseIn.getSkin() == entitylivingbaseIn.getVoidSkin() ? TEXTURE_ENDER : TEXTURE_NORMAL);
            //this.pigModel.setModelAttributes(this.pigRenderer.getMainModel());
            //this.pigModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);	
            this.pigRenderer.getMainModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);	
	}
}
