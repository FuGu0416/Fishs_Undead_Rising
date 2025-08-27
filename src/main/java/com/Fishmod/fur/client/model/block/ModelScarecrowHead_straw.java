package com.Fishmod.fur.client.model.block;

import com.Fishmod.fur.mod_LavaCow;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

//Made with Blockbench 4.12.6
//Exported for Minecraft version 1.17 or later with Mojang mappings
//Paste this class into your mod and generate all required imports


public class ModelScarecrowHead_straw<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(mod_LavaCow.MODID, "scarecrowhead_straw"), "main");
	private final ModelPart root;
	private final ModelPart Head;
	@SuppressWarnings("unused")
	private final ModelPart Stem;

	public ModelScarecrowHead_straw(ModelPart root) {
		this.root = root.getChild("root");
		this.Head = this.root.getChild("Head");
		this.Stem = this.Head.getChild("Stem");
	}

	@SuppressWarnings("unused")
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition Head = root.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(40, 49).addBox(-3.0F, -8.0F, -3.0F, 6.0F, 8.0F, 6.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition Stem = Head.addOrReplaceChild("Stem", CubeListBuilder.create().texOffs(33, 48).addBox(-1.5F, -3.0F, -1.5F, 3.0F, 4.0F, 3.0F), PartPose.offsetAndRotation(0.0F, -7.6F, 2.3F, -0.8196F, 0.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 128, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}