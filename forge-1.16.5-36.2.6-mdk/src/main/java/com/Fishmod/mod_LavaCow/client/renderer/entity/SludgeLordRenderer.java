package com.Fishmod.mod_LavaCow.client.renderer.entity;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.model.entity.SludgeLordModel;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.SludgeLordEntity;
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
public class SludgeLordRenderer extends MobRenderer<SludgeLordEntity, SludgeLordModel<SludgeLordEntity>> {
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/sludgelord/sludgelord.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/sludgelord/sludgelord1.png")
	};
	
	private static final ResourceLocation[] TEXTURES_EYE  = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/sludgelord/sludgelord_glow.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/sludgelord/sludgelord_glow1.png")
	};
	
	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getPath());
    }

    public SludgeLordRenderer(EntityRendererManager rendermanagerIn) {
    	super(rendermanagerIn, new SludgeLordModel<>(), 1.0F);
    	this.addLayer(new EyeLayer(this));
    }
    
    @Override
	public ResourceLocation getTextureLocation(SludgeLordEntity entity) {
        return TEXTURES[entity.getSkin()];
    }
    
    @Nullable
    @Override
    protected RenderType getRenderType(SludgeLordEntity p_230496_1_, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
    	return RenderType.entityTranslucent(this.getTextureLocation(p_230496_1_));
    }
    
    class EyeLayer extends LayerRenderer<SludgeLordEntity, SludgeLordModel<SludgeLordEntity>> {
        public EyeLayer(SludgeLordRenderer render) {
            super(render);
        }

        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, SludgeLordEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        	if (!SpawnUtil.isDay(entitylivingbaseIn.level) || entitylivingbaseIn.level.dimensionType().hasCeiling() || !entitylivingbaseIn.level.canSeeSky(entitylivingbaseIn.blockPosition())) {
	        	IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.eyes(TEXTURES_EYE[entitylivingbaseIn.getSkin()]));
	            this.getParentModel().renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        	}
        }
    }
}
