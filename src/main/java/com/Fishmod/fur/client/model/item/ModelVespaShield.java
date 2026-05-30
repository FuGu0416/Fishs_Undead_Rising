package com.Fishmod.fur.client.model.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Ported from Tabula model (1.16.5 ModelRenderer) to the 1.20.1 ModelPart/LayerDefinition system.
 * Texture: 128x64, uses fur:textures/mobs/vespa/vespa.png.
 *
 * Hierarchy:
 *   uabdomen1
 *     uabdomen2
 *       uabdomen3
 *         uabdomen4
 *         stinger3
 *       stinger2
 *     stinger1
 */
@OnlyIn(Dist.CLIENT)
public class ModelVespaShield extends Model {

    private final ModelPart uAbdomen1;

    public ModelVespaShield(ModelPart root) {
        super(RenderType::entityCutoutNoCull);
        this.uAbdomen1 = root.getChild("uabdomen1");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition uAbdomen1 = root.addOrReplaceChild("uabdomen1",
                CubeListBuilder.create().texOffs(66, 48).addBox(-5.0F, -1.5F, 0.0F, 10, 3, 6),
                PartPose.offsetAndRotation(0.0F, 1.0F, 5.0F, -1.0927506F, 3.1415927F, 0.0F));

        PartDefinition uAbdomen2 = uAbdomen1.addOrReplaceChild("uabdomen2",
                CubeListBuilder.create().texOffs(88, 0).addBox(-6.0F, -2.0F, 0.0F, 12, 4, 8),
                PartPose.offsetAndRotation(0.0F, 0.0F, 4.0F, -0.22759094F, 0.0F, 0.0F));

        uAbdomen1.addOrReplaceChild("stinger1",
                CubeListBuilder.create().texOffs(84, 36).addBox(-1.0F, -1.0F, 0.0F, 2, 2, 6),
                PartPose.offsetAndRotation(0.0F, 1.0F, 3.0F, 1.5707963F, 0.0F, 0.0F));

        PartDefinition uAbdomen3 = uAbdomen2.addOrReplaceChild("uabdomen3",
                CubeListBuilder.create().texOffs(100, 28).addBox(-4.0F, -1.5F, 0.0F, 8, 3, 6),
                PartPose.offsetAndRotation(0.0F, 0.0F, 6.0F, -0.22759094F, 0.0F, 0.0F));

        uAbdomen2.addOrReplaceChild("stinger2",
                CubeListBuilder.create().texOffs(84, 36).mirror().addBox(-1.0F, -1.0F, 0.0F, 2, 2, 6),
                PartPose.offsetAndRotation(0.0F, -1.0F, 4.0F, 1.5707963F, 0.0F, 0.0F));

        uAbdomen3.addOrReplaceChild("uabdomen4",
                CubeListBuilder.create().texOffs(84, 28).addBox(-2.0F, -0.5F, 0.0F, 4, 2, 4),
                PartPose.offsetAndRotation(0.0F, -1.0F, 5.0F, -0.22759094F, 0.0F, 0.0F));

        uAbdomen3.addOrReplaceChild("stinger3",
                CubeListBuilder.create().texOffs(84, 36).addBox(-1.0F, -1.0F, 0.0F, 2, 2, 6),
                PartPose.offsetAndRotation(0.0F, 0.5F, 3.6F, 1.5707963F, 0.0F, 0.0F));

        return LayerDefinition.create(mesh, 128, 64);
    }

    @Override
    public void renderToBuffer(PoseStack pose, VertexConsumer buffer, int packedLight, int packedOverlay,
                               float r, float g, float b, float a) {
        uAbdomen1.render(pose, buffer, packedLight, packedOverlay, r, g, b, a);
    }
}
