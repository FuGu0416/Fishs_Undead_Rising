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
 * ModelFamineArmor - Fish0016054
 * Created using Tabula 7.1.0
 */
@OnlyIn(Dist.CLIENT)
public class FamineArmorModel<T extends LivingEntity> extends HumanoidModel<T> {
	
    public FamineArmorModel(ModelPart root) {
    	super(root);
    }
    
    public static LayerDefinition createArmorLayer(CubeDeformation deformation) {
		MeshDefinition meshdefinition = HumanoidModel.createMesh(deformation, 0.0F);
		PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition head = partdefinition.getChild("head");
   	
        head.addOrReplaceChild("antler_r", CubeListBuilder.create().texOffs(56, 33).addBox(-8.0F, -8.0F, 0.0F, 8.0F, 8.0F, 0.0F, deformation), PartPose.offsetAndRotation(-2.0F, -8.0F, 0.0F, -0.6829F, 0.0F, 0.0F));
        head.addOrReplaceChild("antler_l", CubeListBuilder.create().texOffs(56, 33).mirror().addBox(0.0F, -8.0F, 0.0F, 8.0F, 8.0F, 0.0F, deformation).mirror(false), PartPose.offsetAndRotation(2.0F, -8.0F, 0.0F, -0.6829F, 0.0F, 0.0F));
        head.addOrReplaceChild("snout", CubeListBuilder.create().texOffs(35, 33).addBox(-3.0F, -1.5F, -4.0F, 6.0F, 3.0F, 4.0F, deformation), PartPose.offsetAndRotation(0.0F, -5.5F, -5.0F, 0.0F, 0.0F, 0.0F));
        head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(35, 41).addBox(-3.0F, -1.5F, -4.0F, 6.0F, 3.0F, 4.0F, deformation), PartPose.offsetAndRotation(0.0F, -1.6F, -5.0F, 0.0F, 0.0F, 0.0F));
    
        return LayerDefinition.create(meshdefinition, 64, 64);
    }      
}
