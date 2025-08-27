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


public class ModelScarecrowHead_plague<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(mod_LavaCow.MODID, "scarecrowhead_plague"), "main");
	private final ModelPart root;
	private final ModelPart Head3;
	private final ModelPart Beak;
	@SuppressWarnings("unused")
	private final ModelPart Beak1;
	@SuppressWarnings("unused")
	private final ModelPart Hat;

	public ModelScarecrowHead_plague(ModelPart root) {
		this.root = root.getChild("root");
		this.Head3 = this.root.getChild("Head3");
		this.Beak = this.Head3.getChild("Beak");
		this.Beak1 = this.Beak.getChild("Beak1");
		this.Hat = this.Head3.getChild("Hat");
	}

	@SuppressWarnings("unused")
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition Head3 = root.addOrReplaceChild("Head3", CubeListBuilder.create().texOffs(64, 10).addBox(-3.0F, -2.6F, -7.7F, 6.0F, 6.0F, 8.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F));
		PartDefinition Beak = Head3.addOrReplaceChild("Beak", CubeListBuilder.create().texOffs(64, 26).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 4.0F, 4.0F), PartPose.offsetAndRotation(0.0F, 2.9F, -2.2F, 0.2276F, 0.0F, 0.0F));
		PartDefinition Beak1 = Beak.addOrReplaceChild("Beak1", CubeListBuilder.create().texOffs(80, 28).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F), PartPose.offsetAndRotation(0.0F, 3.2F, -0.7F, 0.5009F, 0.0F, 0.0F));
		PartDefinition Hat = Head3.addOrReplaceChild("Hat", CubeListBuilder.create().texOffs(90, 0).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 1.0F), PartPose.offset(0.0F, 0.4F, -5.7F));
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