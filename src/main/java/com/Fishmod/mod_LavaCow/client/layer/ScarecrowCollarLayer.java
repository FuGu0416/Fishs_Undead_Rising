package com.Fishmod.mod_LavaCow.client.layer;

import com.Fishmod.mod_LavaCow.client.model.entity.ScarecrowModel;
import com.Fishmod.mod_LavaCow.entities.tameable.ScarecrowEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScarecrowCollarLayer extends LayerRenderer<ScarecrowEntity, ScarecrowModel<ScarecrowEntity>> {
	private static final ResourceLocation SCARF_LOCATION = new ResourceLocation("mod_lavacow:textures/mobs/scarecrow/scarecrow_scarf.png");

	public ScarecrowCollarLayer(IEntityRenderer<ScarecrowEntity, ScarecrowModel<ScarecrowEntity>> p_i50914_1_) {
		super(p_i50914_1_);
	}

	public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, ScarecrowEntity p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
		if (!p_225628_4_.isInvisible()) {
			float[] afloat = p_225628_4_.getCollarColor().getTextureDiffuseColors();
			renderColoredCutoutModel(this.getParentModel(), SCARF_LOCATION, p_225628_1_, p_225628_2_, p_225628_3_, p_225628_4_, afloat[0], afloat[1], afloat[2]);
		}
	}
}
