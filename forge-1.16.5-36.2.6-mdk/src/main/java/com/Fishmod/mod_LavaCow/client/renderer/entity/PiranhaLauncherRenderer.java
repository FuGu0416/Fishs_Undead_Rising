package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.client.model.entity.SwarmerModel;
import com.Fishmod.mod_LavaCow.entities.projectiles.PiranhaLauncherEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class PiranhaLauncherRenderer extends EntityRenderer<PiranhaLauncherEntity> {
	private final SwarmerModel<PiranhaLauncherEntity> model = new SwarmerModel<>();
	private static final ResourceLocation TEXTURES = new ResourceLocation("mod_lavacow:textures/mobs/piranha/zombiepiranha.png");

    public PiranhaLauncherRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn);
    }
    
    public void render(PiranhaLauncherEntity p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
        p_225623_4_.pushPose();
        p_225623_4_.scale(-1.0F, -1.0F, 1.0F);
        float f = MathHelper.rotLerp(p_225623_1_.yRotO, p_225623_1_.yRot, p_225623_3_);
        IVertexBuilder ivertexbuilder = p_225623_5_.getBuffer(this.model.renderType(this.getTextureLocation(p_225623_1_)));
        p_225623_4_.translate(0.0F, -1.5F, 0.0F);
        p_225623_4_.mulPose(Vector3f.YP.rotationDegrees(f));
        this.model.renderToBuffer(p_225623_4_, ivertexbuilder, p_225623_6_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        p_225623_4_.popPose();
        super.render(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
     }

	@Override
	public ResourceLocation getTextureLocation(PiranhaLauncherEntity entity) {
		return TEXTURES;
	}
}
