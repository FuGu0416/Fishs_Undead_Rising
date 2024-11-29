package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.client.model.entity.WarpedFireflyModel;
import com.Fishmod.mod_LavaCow.entities.flying.WarpedFireflyEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WarpedFireflyRenderer extends MobRenderer<WarpedFireflyEntity, WarpedFireflyModel<WarpedFireflyEntity>> {		
	private static final ResourceLocation TEXTURES_EYE = new ResourceLocation("mod_lavacow:textures/mobs/warped_firefly/warped_firefly_glow.png");
	private static final ResourceLocation TEXTURES = new ResourceLocation("mod_lavacow:textures/mobs/warped_firefly/warped_firefly.png");	
	
	static{
        System.out.println(TEXTURES.getPath());
    }

    public WarpedFireflyRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new WarpedFireflyModel<>(), 0.4F);
        this.addLayer(new EyeLayer(this));
    }
    
    @Override
	public ResourceLocation getTextureLocation(WarpedFireflyEntity entity) {
    	return TEXTURES;
    }
    
    class EyeLayer extends LayerRenderer<WarpedFireflyEntity, WarpedFireflyModel<WarpedFireflyEntity>> {
        public EyeLayer(WarpedFireflyRenderer render) {
            super(render);
        }

        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, WarpedFireflyEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        	IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.eyes(TEXTURES_EYE));
            this.getParentModel().renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
