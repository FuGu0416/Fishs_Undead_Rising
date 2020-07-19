package com.Fishmod.mod_LavaCow.client.layer;

import com.Fishmod.mod_LavaCow.entities.EntityZombieMushroom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerGenericGlowing implements LayerRenderer<EntityLiving>{
    private ResourceLocation SPIDER_EYES;
    private final RenderLiving Renderer;

    public LayerGenericGlowing(RenderLiving RendererIn, ResourceLocation TextureIn)
    {
        this.Renderer = RendererIn;
        SPIDER_EYES = TextureIn;
    }

    public void doRenderLayer(EntityLiving entitylivingIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if(entitylivingIn instanceof EntityZombieMushroom && ((EntityZombieMushroom)entitylivingIn).getSkin() == 0)
        	return;
    	
    	this.Renderer.bindTexture(SPIDER_EYES);
        GlStateManager.enableBlend();
        //GlStateManager.disableAlpha();
        //GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);

        GlStateManager.depthMask(true);

        int i = 61680;
        int j = i % 65536;
        int k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
        this.Renderer.getMainModel().render(entitylivingIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
        i = entitylivingIn.getBrightnessForRender();
        j = i % 65536;
        k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
        this.Renderer.setLightmap(entitylivingIn);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }
}
