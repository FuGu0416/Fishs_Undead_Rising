package com.Fishmod.mod_LavaCow.client.layer;

import com.Fishmod.mod_LavaCow.client.renders.entity.RenderScarecrow;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityScarecrow;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerScarecrowCollar implements LayerRenderer<EntityScarecrow> {
	private static final ResourceLocation SCARF_LOCATION = new ResourceLocation("mod_lavacow:textures/mobs/scarecrow/scarecrow_scarf.png");
    private final RenderScarecrow scarecrowRenderer;

	public LayerScarecrowCollar(RenderScarecrow render) {
		this.scarecrowRenderer = render;
	}
	
    public void doRenderLayer(EntityScarecrow entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (entity.isTamed() && !entity.isInvisible())
        {
            this.scarecrowRenderer.bindTexture(SCARF_LOCATION);
            float[] afloat = entity.getCollarColor().getColorComponentValues();
            GlStateManager.color(afloat[0], afloat[1], afloat[2]);
            this.scarecrowRenderer.getMainModel().render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }

	@Override
	public boolean shouldCombineTextures() {
		return true;
	}
}
