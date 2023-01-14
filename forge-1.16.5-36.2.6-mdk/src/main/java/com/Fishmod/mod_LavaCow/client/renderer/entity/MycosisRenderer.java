package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.client.layer.LayerGenericHeldItem;
import com.Fishmod.mod_LavaCow.client.layer.LayerMycosis;
import com.Fishmod.mod_LavaCow.client.layer.LayerUnburiedArmor;
import com.Fishmod.mod_LavaCow.client.model.entity.UnburiedModel;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.MycosisEntity;
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
public class MycosisRenderer extends MobRenderer<MycosisEntity, UnburiedModel<MycosisEntity>> {
	private static final ResourceLocation TEXTURES_EYE = new ResourceLocation("mod_lavacow:textures/mobs/unburied/unburied3_eyes.png");
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/unburied/unburied2.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/unburied/unburied3.png")
	};
	
	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getPath());
    }

    public MycosisRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new UnburiedModel<MycosisEntity>(), 0.5F);
        this.addLayer(new LayerMycosis<>(this));
        this.addLayer(new EyeLayer(this));
        this.addLayer(new LayerGenericHeldItem<>(this, 0.0F, 0.15F, -0.6F, 1.0F));
        this.addLayer(new LayerUnburiedArmor<>(this));
    }
    
    @Override
	public ResourceLocation getTextureLocation(MycosisEntity entity) {
    	return TEXTURES[entity.getSkin()];
    }
       
    @Override
    protected void scale(MycosisEntity entity, MatrixStack matrixStackIn, float partialTickTime) {
    	if(entity.isBaby()) {
    		matrixStackIn.scale(0.6F, 0.6F, 0.6F);
    	}
    }
      
    class EyeLayer extends LayerRenderer<MycosisEntity, UnburiedModel<MycosisEntity>> {

        public EyeLayer(MycosisRenderer render) {
            super(render);
        }

        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, MycosisEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            if(entitylivingbaseIn.getSkin() != 0 && (!SpawnUtil.isDay(entitylivingbaseIn.level) || entitylivingbaseIn.level.dimensionType().hasCeiling() || !entitylivingbaseIn.level.canSeeSky(entitylivingbaseIn.blockPosition()))) {
	        	IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.eyes(TEXTURES_EYE));
	            this.getParentModel().renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            }
        }
    }
}
