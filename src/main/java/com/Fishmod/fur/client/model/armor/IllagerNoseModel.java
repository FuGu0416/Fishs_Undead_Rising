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
 * Worn model for the Illager Nose (1.20.1 port of the 1.16.5 ModelIllagerNose): a 2x4x2 nose box
 * hanging off the head bone. The armor texture (textures/armors/illager_nose.png, 64x64) is fully
 * transparent except the nose pixels at (56,32), so the inherited head/hat cubes render invisible
 * and only the 3D nose shows on the wearer's face. Pose and part visibility are copied from the
 * vanilla armor model by Forge's getGenericArmorModel, so no setupAnim override is needed
 * (the 1.16.5 armor-stand special case is handled by that copy as well).
 */
@OnlyIn(Dist.CLIENT)
public class IllagerNoseModel<T extends LivingEntity> extends HumanoidModel<T> {

	public IllagerNoseModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createArmorLayer(CubeDeformation deformation) {
		MeshDefinition meshdefinition = HumanoidModel.createMesh(deformation, 0.0F);
		PartDefinition head = meshdefinition.getRoot().getChild("head");

		// 1.16.5: nose.setPos(0, 1, -5); texOffs(56, 32).addBox(-1, -4, -1, 2, 4, 2, 0.0F)
		head.addOrReplaceChild("nose", CubeListBuilder.create()
				.texOffs(56, 32).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, CubeDeformation.NONE),
				PartPose.offset(0.0F, 1.0F, -5.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}
