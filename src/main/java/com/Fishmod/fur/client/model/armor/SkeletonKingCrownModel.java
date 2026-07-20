package com.Fishmod.fur.client.model.armor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Worn model for the Crown of Rule (1.20.1 port of the 1.16.5 ModelCrown): a 9x5x9 crown box
 * sitting on the head bone. The armor texture (textures/armors/kings_crown/kings_crown.png,
 * 128x64) is fully transparent except the crown pixels at (92,23), so the inherited head/hat
 * cubes render invisible and only the 3D crown shows (same as 1.16.5, whose head cube also
 * sampled a transparent region). Pose and part visibility are copied from the vanilla armor
 * model by Forge's getGenericArmorModel, so no setupAnim override is needed (the 1.16.5
 * armor-stand special case is handled by that copy as well).
 * <p>
 * IMPORTANT: the 9x5x9 box dimensions here are load-bearing for more than just its own shape —
 * CubeListBuilder auto-lays out each face's UV rectangle from texOffs(92, 23) based on the exact
 * xSize/ySize/zSize passed to addBox, and the texture art at that offset was painted for that
 * exact unwrap. Changing the addBox size (as a first attempt at shrinking the crown did) reflows
 * that UV footprint onto the wrong region of the sheet and makes the crown sample blank/transparent
 * pixels — i.e. it disappears rather than shrinks. The correct way to resize this cosmetically
 * without touching UVs is a runtime scale on the baked ModelPart (see the constructor), which
 * scales the rendered mesh around its own pivot without changing what texture region it reads.
 */
@OnlyIn(Dist.CLIENT)
public class SkeletonKingCrownModel<T extends LivingEntity> extends HumanoidModel<T> {
	private static final float CROWN_SCALE = 1.0F;

	public SkeletonKingCrownModel(ModelPart root) {
		super(root);
		ModelPart crown = root.getChild("head").getChild("crown");
		crown.xScale = CROWN_SCALE;
		crown.yScale = CROWN_SCALE;
		crown.zScale = CROWN_SCALE;
	}

	public static LayerDefinition createArmorLayer(CubeDeformation deformation) {
		MeshDefinition meshdefinition = HumanoidModel.createMesh(deformation, 0.0F);
		PartDefinition head = meshdefinition.getRoot().getChild("head");

		// 1.16.5: Crown.setPos(0, -6, 0.1); texOffs(92, 23).addBox(-4.5, -4, -4.5, 9, 5, 9, modelSize=1.0)
		head.addOrReplaceChild("crown", CubeListBuilder.create()
				.texOffs(92, 23).addBox(-4.5F, -4.0F, -4.5F, 9.0F, 5.0F, 9.0F, deformation),
				PartPose.offset(0.0F, -6.0F, 0.1F));

		return LayerDefinition.create(meshdefinition, 128, 64);
	}
}
