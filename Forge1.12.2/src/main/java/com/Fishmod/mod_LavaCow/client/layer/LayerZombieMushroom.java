package com.Fishmod.mod_LavaCow.client.layer;

import com.Fishmod.mod_LavaCow.client.renders.RenderZombieMushroom;
import com.Fishmod.mod_LavaCow.entities.EntityZombieMushroom;
import com.Fishmod.mod_LavaCow.init.Modblocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;

public class LayerZombieMushroom implements LayerRenderer<EntityZombieMushroom>{
	
	private final RenderZombieMushroom mooshroomRenderer;
	private IBlockState shroom_on_head = null;

    public LayerZombieMushroom(RenderZombieMushroom mooshroomRendererIn)
    {
        this.mooshroomRenderer = mooshroomRendererIn;
    }

    public void doRenderLayer(EntityZombieMushroom entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (!entitylivingbaseIn.isChild() && !entitylivingbaseIn.isInvisible())
        {
            BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
            float shroom_scale = 1.0F;// - 0.5F * entitylivingbaseIn.getSkin();
            this.shroom_on_head = entitylivingbaseIn.getSkin() == 1 ? Modblocks.GLOWSHROOM.getStateFromMeta(1) : Modblocks.CORDY_SHROOM.getStateFromMeta(1);
            this.mooshroomRenderer.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            
            GlStateManager.enableCull();
            GlStateManager.cullFace(GlStateManager.CullFace.FRONT);
            GlStateManager.pushMatrix();
            this.mooshroomRenderer.getMainModel().Body_base.postRender(0.0625F);
            this.mooshroomRenderer.getMainModel().Body_waist.postRender(0.0625F);
            this.mooshroomRenderer.getMainModel().Body_chest.postRender(0.0625F);
            this.mooshroomRenderer.getMainModel().Neck0.postRender(0.0625F);
            this.mooshroomRenderer.getMainModel().Neck1.postRender(0.0625F);
            this.mooshroomRenderer.getMainModel().Head.postRender(0.0625F);
            GlStateManager.scale(shroom_scale, -1.0F * shroom_scale, shroom_scale);
            GlStateManager.translate(0.0F, 0.675F, -0.25F);
            GlStateManager.rotate(12.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(-0.5F, 0.5F - shroom_scale, 0.5F);
            blockrendererdispatcher.renderBlockBrightness(shroom_on_head, 1.0F);
            GlStateManager.popMatrix();
            
            GlStateManager.pushMatrix();
            this.mooshroomRenderer.getMainModel().Body_base.postRender(0.0625F);
            this.mooshroomRenderer.getMainModel().Body_waist.postRender(0.0625F);
            this.mooshroomRenderer.getMainModel().Body_chest.postRender(0.0625F);
            GlStateManager.scale(shroom_scale, -1.0F * shroom_scale, shroom_scale);
            GlStateManager.translate(0.0F, 0.1F, 0.65F);
            GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.translate(-0.5F, 0.5F - shroom_scale, 0.5F);
            blockrendererdispatcher.renderBlockBrightness(shroom_on_head, 1.0F);
            GlStateManager.popMatrix();
            
            GlStateManager.cullFace(GlStateManager.CullFace.BACK);
            GlStateManager.disableCull();
        }
    }

    public boolean shouldCombineTextures()
    {
        return true;
    }

}
