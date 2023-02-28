package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.client.model.entity.BeelzebubModel;
import com.Fishmod.mod_LavaCow.entities.flying.BeelzebubEntity;
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

public class BeelzebubRenderer extends MobRenderer<BeelzebubEntity, BeelzebubModel<BeelzebubEntity>> {
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/beelzebub/beelzebub.png"),
	};
	private static final ResourceLocation TEXTURES_GLAND = new ResourceLocation("mod_lavacow:textures/mobs/beelzebub/beelzebub_gland.png");
	
	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getPath());
    }

    public BeelzebubRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new BeelzebubModel<BeelzebubEntity>(), 0.5F);
        this.addLayer(new SaddleLayer<>(this, this.getModel(), new ResourceLocation("mod_lavacow:textures/mobs/beelzebub/beelzebub_saddle.png")));
        //this.addLayer(new GlandLayer(this));
    }
    
    @Override
    public ResourceLocation getTextureLocation(BeelzebubEntity entity) {
    	return TEXTURES[entity.getSkin()];
    }
    
    @Override
    public void render(BeelzebubEntity p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
    	super.render(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
    	this.model.isHarvestable = p_225623_1_.canHarvest();
    }
    
    class GlandLayer extends LayerRenderer<BeelzebubEntity, BeelzebubModel<BeelzebubEntity>> {    	
        public GlandLayer(BeelzebubRenderer render) {
            super(render);
        }

        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, BeelzebubEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            if(entitylivingbaseIn.canHarvest()) {
	        	IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.entityCutoutNoCull(TEXTURES_GLAND));
	            this.getParentModel().renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            }
        }
    }    
}
