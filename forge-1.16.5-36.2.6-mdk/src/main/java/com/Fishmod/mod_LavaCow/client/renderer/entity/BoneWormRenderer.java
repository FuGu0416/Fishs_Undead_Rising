package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.client.model.entity.BoneWormModel;
import com.Fishmod.mod_LavaCow.entities.BoneWormEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BoneWormRenderer extends MobRenderer<BoneWormEntity, BoneWormModel<BoneWormEntity>> {
	private static ResourceLocation TEXTURES_EYE = new ResourceLocation("mod_lavacow:textures/mobs/boneworm/boneworm_glow.png");
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/boneworm/boneworm.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/boneworm/boneworm1.png")
	};
	
	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getPath());
    }

    public BoneWormRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new BoneWormModel<BoneWormEntity>(), 0.5F);
        this.addLayer(new EyeLayer(this));
    }
    
    @Override
    public ResourceLocation getTextureLocation(BoneWormEntity entity) {
        return TEXTURES[entity.getSkin()];
    }
    
    /**
     * Renders the desired {@code T} type Entity.
     */
    @Override
    public void render(BoneWormEntity entity, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
    	this.shadowRadius = entity.getLocationFix() > 3.0D ? 0.0F : 0.5F;
    	if(entity.getLocationFix() < 1.5D)
    		super.render(entity, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
    }
    
    @Override
    protected void setupRotations(BoneWormEntity entity, MatrixStack p_225621_2_, float ageInTicks, float rotationYaw, float partialTicks) {
    	p_225621_2_.translate(0.0D, -entity.getLocationFix(), 0.0D);
    	p_225621_2_.mulPose(Vector3f.YP.rotationDegrees(90.0F * (float) entity.getLocationFix()));
    	super.setupRotations(entity, p_225621_2_, ageInTicks, rotationYaw, partialTicks);
	}
    
    class EyeLayer extends LayerRenderer<BoneWormEntity, BoneWormModel<BoneWormEntity>> {

        public EyeLayer(BoneWormRenderer render) {
            super(render);
        }

        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, BoneWormEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            if(entitylivingbaseIn.getSkin() != 0) {
	        	IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.eyes(TEXTURES_EYE));
	            this.getParentModel().renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 0.5F, 0.5F, 0.5F, 1.0F);
            }
        }
    }    
}
