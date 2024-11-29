package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.entities.projectiles.SandBurstEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EvokerFangsModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SandBurstRenderer extends EntityRenderer<SandBurstEntity> {
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("textures/entity/illager/evoker_fangs.png");
    private final EvokerFangsModel<SandBurstEntity> model = new EvokerFangsModel<>();

    public SandBurstRenderer(EntityRendererManager p_i47208_1_)
    {
        super(p_i47208_1_);
    }

    /**
     * Renders the desired {@code T} type Entity.
     */
    @Override
    public void render(SandBurstEntity p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
        float f = p_225623_1_.getAnimationProgress(p_225623_3_);
        if (f != 0.0F) {
           float f1 = 2.0F;
           if (f > 0.9F) {
              f1 = (float)((double)f1 * ((1.0D - (double)f) / (double)0.1F));
           }

           p_225623_4_.pushPose();
           p_225623_4_.mulPose(Vector3f.YP.rotationDegrees(90.0F - p_225623_1_.yRot));
           p_225623_4_.scale(-f1, -f1, f1);
           p_225623_4_.translate(0.0D, (double)-0.626F, 0.0D);
           p_225623_4_.scale(0.5F, 0.5F, 0.5F);
           this.model.setupAnim(p_225623_1_, f, 0.0F, 0.0F, p_225623_1_.yRot, p_225623_1_.xRot);
           IVertexBuilder ivertexbuilder = p_225623_5_.getBuffer(this.model.renderType(TEXTURE_LOCATION));
           this.model.renderToBuffer(p_225623_4_, ivertexbuilder, p_225623_6_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
           p_225623_4_.popPose();
           super.render(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
        }
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
	@Override
	public ResourceLocation getTextureLocation(SandBurstEntity entity) {
		return TEXTURE_LOCATION;
	}
}