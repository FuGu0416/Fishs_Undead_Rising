package com.Fishmod.mod_LavaCow.client.layer;

import com.Fishmod.mod_LavaCow.client.renders.RenderMimic;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityMimic;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import java.util.Arrays;
import java.util.ArrayList;

public class LayerMimicChest implements LayerRenderer<EntityMimic> {
    private static final ResourceLocation TEXTURE_ENDER = new ResourceLocation("textures/entity/chest/ender.png");
    // This texture pool method works for now, but if we start adding in more textures for chests it could cause
    // problems (since the texture ID would remain the same but the list entries would change).
    // If we eventually need to expand, maybe we can work out something with UUIDs?
    // Oh well! Until we need more textures, this should work.
    public static ArrayList<ResourceLocation> texturePool = new ArrayList<ResourceLocation>(Arrays.asList(
            new ResourceLocation("textures/entity/chest/normal.png")
    ));
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
        if (entitylivingbaseIn.getSkin() == entitylivingbaseIn.getVoidSkin()) {
            this.pigRenderer.bindTexture(TEXTURE_ENDER);
        } else {
            int chestTexture = entitylivingbaseIn.getChestTexture();

            // In the event that Quark compatibility is no longer enabled (or the mod was removed), we reset the chest texture.
            if (chestTexture >= texturePool.size()) {
                chestTexture = 0;
            }

            this.pigRenderer.bindTexture(texturePool.get(chestTexture));
        }
        //this.pigRenderer.bindTexture(entitylivingbaseIn.getSkin() == entitylivingbaseIn.getVoidSkin() ? TEXTURE_ENDER : TEXTURE_NORMAL);
        //this.pigModel.setModelAttributes(this.pigRenderer.getMainModel());
        //this.pigModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        this.pigRenderer.getMainModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
	}
}
