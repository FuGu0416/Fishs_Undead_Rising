package com.Fishmod.mod_LavaCow.client.layer;

import java.util.Calendar;

import com.Fishmod.mod_LavaCow.client.renders.entity.RenderMimic;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityMimic;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class LayerMimicChest implements LayerRenderer<EntityMimic> {
    private static final ResourceLocation TEXTURE_ENDER = new ResourceLocation("textures/entity/chest/ender.png");
    private static final ResourceLocation TEXTURE_XMAS = new ResourceLocation("textures/entity/chest/christmas.png");
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
        Calendar calendar = entitylivingbaseIn.world.getCurrentDate();

        if (calendar.get(2) + 1 == 12 && calendar.get(5) == 25) {
        	this.pigRenderer.bindTexture(TEXTURE_XMAS);
        } else {
            String chestTexture = entitylivingbaseIn.getChestTexture();

            // In the event that compatibility is no longer enabled (or the mod was removed), we reset the chest texture.
            if (!EntityMimic.TEXTURE_POOL.contains(chestTexture)) {
                chestTexture = "minecraft:textures/entity/chest/normal.png";
            }

            this.pigRenderer.bindTexture(new ResourceLocation(chestTexture));
        }
        
        if (entitylivingbaseIn.getSkin() == entitylivingbaseIn.getVoidSkin()) {
            this.pigRenderer.bindTexture(TEXTURE_ENDER);
        }

        this.pigRenderer.getMainModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }
}
