package com.Fishmod.mod_LavaCow.client.layer;

import com.Fishmod.mod_LavaCow.client.model.entity.ModelRaven;
import com.Fishmod.mod_LavaCow.client.renders.entity.RenderRaven;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityRaven;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerRaven <T extends EntityRaven> implements LayerRenderer<T>{
    private static final ResourceLocation SPIDER_EYES = new ResourceLocation("mod_lavacow:textures/mobs/raven/raven_eyes.png");
    private final RenderRaven spiderRenderer;

    public LayerRaven(RenderRaven spiderRendererIn)
    {
        this.spiderRenderer = spiderRendererIn;
    }

    public void doRenderLayer(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if(entitylivingbaseIn.getSkin() != 2) {
        	this.spiderRenderer.bindTexture(SPIDER_EYES);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
            GlStateManager.disableLighting();
            int i = 61680;
            int j = i % 65536;
            int k = i / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
            GlStateManager.enableLighting();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.spiderRenderer.getMainModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            i = entitylivingbaseIn.getBrightnessForRender();
            j = i % 65536;
            k = i / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
            this.spiderRenderer.setLightmap(entitylivingbaseIn);
            GlStateManager.depthMask(true);
            GlStateManager.disableBlend();
        }
        
        /*
         * Render held item
         */
        ItemStack itemstack = entitylivingbaseIn.getHeldItemMainhand();
        
        GlStateManager.pushMatrix();
        ((ModelRaven)this.spiderRenderer.getMainModel()).head.postRender(0.0625F);
        GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
        //GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.scale(0.5F, 0.5F, 0.5F);
        GlStateManager.translate(0.0F, -0.1F, -0.5F);
        if(itemstack.getItem().equals(Items.BONE)) {
        	GlStateManager.translate(-0.25F, 0.5F, 1.0F);
        	GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);        	
        }
        Minecraft.getMinecraft().getItemRenderer().renderItemSide(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.HEAD, true);
        GlStateManager.popMatrix();
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }
}
