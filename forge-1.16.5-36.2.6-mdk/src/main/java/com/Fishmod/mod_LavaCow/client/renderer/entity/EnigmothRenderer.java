package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.client.model.entity.EnigmothModel;
import com.Fishmod.mod_LavaCow.entities.flying.EnigmothEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class EnigmothRenderer extends MobRenderer<EnigmothEntity, EnigmothModel<EnigmothEntity>> {
	private static final ResourceLocation TEXTURES_EYE  = new ResourceLocation("mod_lavacow:textures/mobs/enigmoth/enigmoth_eyes.png");
	private static final ResourceLocation TEXTURES_EYE_CHILD  = new ResourceLocation("mod_lavacow:textures/mobs/enigmoth/enigmoth_larva_glow.png");
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/enigmoth/enigmoth.png"),
	};
	private static final ResourceLocation[] TEXTURES_CHILD = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/enigmoth/enigmoth_larva.png"),
	};

	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getPath());
    }

    public EnigmothRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new EnigmothModel<EnigmothEntity>(), 0.5F);
        this.addLayer(new EyeLayer(this));
        this.addLayer(new SaddleLayer<>(this, this.getModel(), new ResourceLocation("mod_lavacow:textures/mobs/enigmoth/enigmoth_saddle.png")));
    }
    
    @Override
    public ResourceLocation getTextureLocation(EnigmothEntity entity) {
    	return entity.isBaby() ? TEXTURES_CHILD[entity.getSkin()] : TEXTURES[entity.getSkin()];
    }
    
    @Override
    public void render(EnigmothEntity p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
    	super.render(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
    }   
    
    class EyeLayer extends LayerRenderer<EnigmothEntity, EnigmothModel<EnigmothEntity>> {

        public EyeLayer(EnigmothRenderer render) {
            super(render);
        }

        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, EnigmothEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        	IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.eyes(entitylivingbaseIn.isBaby() ? TEXTURES_EYE_CHILD : TEXTURES_EYE));
            this.getParentModel().renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
