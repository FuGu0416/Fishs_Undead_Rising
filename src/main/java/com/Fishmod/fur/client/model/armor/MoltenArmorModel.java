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
 * ModelFelArmor - Fish0016054
 * Created using Tabula 7.0.1
 */
@OnlyIn(Dist.CLIENT)
public class MoltenArmorModel<T extends LivingEntity> extends HumanoidModel<T> {

    public MoltenArmorModel(ModelPart root) {
    	super(root);
    }

	public static LayerDefinition createArmorLayer(CubeDeformation deformation) {
		MeshDefinition meshdefinition = HumanoidModel.createMesh(deformation, 0.0F);
		PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition head = partdefinition.getChild("head");
        PartDefinition leftArm = partdefinition.getChild("left_arm");
        PartDefinition rightArm = partdefinition.getChild("right_arm");
        
		head.addOrReplaceChild("helmet", CubeListBuilder.create()
				.texOffs(49, 35).addBox(-5.0F, -6.0F, -2.0F, 1.0F, 2.0F, 2.0F, deformation)
				.texOffs(41, 32).mirror().addBox(6.0F, -9.0F, -2.0F, 2.0F, 5.0F, 2.0F, deformation).mirror(false)
				.texOffs(49, 35).mirror().addBox(4.0F, -6.0F, -2.0F, 1.0F, 2.0F, 2.0F, deformation).mirror(false)
				.texOffs(41, 32).addBox(-8.0F, -9.0F, -2.0F, 2.0F, 5.0F, 2.0F, deformation), PartPose.offset(0.0F, 0.0F, 0.0F));

		leftArm.addOrReplaceChild("shoulder_l", CubeListBuilder.create().texOffs(41, 39).mirror().addBox(-3.0F, -3.0F, -3.0F, 4.0F, 5.0F, 5.0F, deformation).mirror(false), PartPose.offsetAndRotation(3.0F, 0.0F, 0.5F, 0.0F, 0.0F, -0.2618F));

		rightArm.addOrReplaceChild("shoulder_r", CubeListBuilder.create().texOffs(41, 39).addBox(-1.0F, -3.0F, -3.0F, 4.0F, 5.0F, 5.0F, deformation), PartPose.offsetAndRotation(-3.0F, 0.0F, 0.5F, 0.0F, 0.0F, 0.2618F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

}
