package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.client.layer.LayerMimicChest;
import com.Fishmod.mod_LavaCow.client.model.entity.MimicModel;
import com.Fishmod.mod_LavaCow.entities.tameable.MimicEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MimicRenderer extends MobRenderer<MimicEntity, MimicModel<MimicEntity>> {
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/mimic/mimic.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/mimic/mimic2.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/mimic/mimic3.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/mimic/mimicvoid.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/mimic/mimic4.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/mimic/mimic5.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/mimic/mimicnether.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/mimic/mimic6.png")
	};
	
	public static int getVoidSkin() {
		return 3;
	}
	
	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getPath());
    }
	
	public MimicRenderer(EntityRendererManager rendermanagerIn) {
		super(rendermanagerIn, new MimicModel<>(), 0.4F);		
        this.addLayer(new EyeLayer(this));
        this.addLayer(new LayerMimicChest<>(this));
    }

	@Override
	public ResourceLocation getTextureLocation(MimicEntity entity) {
		return TEXTURES[entity.getSkin()];
	}
	
    @Override
	protected void scale(MimicEntity entity, MatrixStack matrixStackIn, float partialTickTime) { 
    	if(entity.isBaby()) {
    		matrixStackIn.scale(0.5F, 0.5F, 0.5F);
    	}
	}

    @Override
    protected int getBlockLightLevel(MimicEntity entity, BlockPos pos) {
        return entity.getSkin() == entity.getVoidSkin() ? 7 : super.getBlockLightLevel(entity, pos);
    }
    
    class EyeLayer extends LayerRenderer<MimicEntity, MimicModel<MimicEntity>> {

        public EyeLayer(MimicRenderer render) {
            super(render);
        }

        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, MimicEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            if(entitylivingbaseIn.getSkin() == 6) {
	        	IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.eyes(TEXTURES[entitylivingbaseIn.getSkin()]));
	            this.getParentModel().renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            }
        }
    }
}
