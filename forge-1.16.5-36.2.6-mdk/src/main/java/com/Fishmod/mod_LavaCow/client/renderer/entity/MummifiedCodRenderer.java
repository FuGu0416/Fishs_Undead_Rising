package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.entities.aquatic.UndeadFishEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.CodModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MummifiedCodRenderer extends MobRenderer<UndeadFishEntity, CodModel<UndeadFishEntity>> {
	private static final ResourceLocation TEXTURES = new ResourceLocation("mod_lavacow:textures/mobs/fish/mummified_cod.png");

	public MummifiedCodRenderer(EntityRendererManager p_i48864_1_) {
		super(p_i48864_1_, new CodModel<>(), 0.3F);
	}

	public ResourceLocation getTextureLocation(UndeadFishEntity p_110775_1_) {
		return TEXTURES;
	}

	protected void setupRotations(UndeadFishEntity p_225621_1_, MatrixStack p_225621_2_, float p_225621_3_, float p_225621_4_, float p_225621_5_) {
		super.setupRotations(p_225621_1_, p_225621_2_, p_225621_3_, p_225621_4_, p_225621_5_);
		float f = 4.3F * MathHelper.sin(0.6F * p_225621_3_);
		p_225621_2_.mulPose(Vector3f.YP.rotationDegrees(f));
		if (!p_225621_1_.isInWater()) {
			p_225621_2_.translate((double)0.1F, (double)0.1F, (double)-0.1F);
			p_225621_2_.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
		}
	}
}