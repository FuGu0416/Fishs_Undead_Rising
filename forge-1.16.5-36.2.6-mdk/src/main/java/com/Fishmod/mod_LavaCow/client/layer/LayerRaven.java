package com.Fishmod.mod_LavaCow.client.layer;

import com.Fishmod.mod_LavaCow.client.model.entity.RavenModel;
import com.Fishmod.mod_LavaCow.entities.tameable.RavenEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LayerRaven<T extends RavenEntity, M extends RavenModel<T>> extends LayerRenderer<T, M> {
    private static final ResourceLocation SPIDER_EYES = new ResourceLocation("mod_lavacow:textures/mobs/raven/raven_eyes.png");

    public LayerRaven(IEntityRenderer<T, M> p_i226039_1_)
    {
    	super(p_i226039_1_);
    }

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int p_225628_3_, T entityIn, float p_225628_5_, float p_225628_6_, float p_225628_7_, 
			float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        if(this.renderType(entityIn) != null) {
            IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.renderType(entityIn));
            this.getParentModel().renderToBuffer(matrixStackIn, ivertexbuilder, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
        
        /*
         * Render held item
         */
        ItemStack itemstack = entityIn.getMainHandItem();
        
        matrixStackIn.pushPose();
        this.getParentModel().head.translateAndRotate(matrixStackIn);
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
        matrixStackIn.scale(0.5F, 0.5F, 0.5F);
        matrixStackIn.translate(0.0F, -0.1F, -0.5F);
        
        if (itemstack.getItem().equals(Items.BONE)) {       	
        	matrixStackIn.translate(-0.25F, 0.5F, 1.0F);    
        	matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
        }
        
        if (itemstack.getItem().equals(Items.COD)) {
        	matrixStackIn.translate(-0.25F, 1.25F, 1.0F);    
        	matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
        	matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90.0F));
        }
        
        if (itemstack.getItem() instanceof BlockItem && !(itemstack.getItem() instanceof BlockNamedItem)) {
        	matrixStackIn.translate(0.0F, 0.75F, 0.5F);    
        	matrixStackIn.scale(0.5F, 0.5F, 0.5F);
        	matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
        }
        
        if (itemstack.getItem().equals(Items.WHEAT_SEEDS)) {
        	
        }
        
        Minecraft.getInstance().getItemInHandRenderer().renderItem(entityIn, itemstack, ItemCameraTransforms.TransformType.HEAD, true, matrixStackIn, bufferIn, p_225628_3_);
        matrixStackIn.popPose();
    }

	public RenderType renderType(T entityIn) {		
		return (entityIn.getSkin() == 2) ? null : RenderType.eyes(SPIDER_EYES);
	}
}
