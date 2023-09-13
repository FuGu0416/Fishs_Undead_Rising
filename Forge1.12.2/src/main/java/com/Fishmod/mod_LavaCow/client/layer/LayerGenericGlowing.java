package com.Fishmod.mod_LavaCow.client.layer;

import com.Fishmod.mod_LavaCow.entities.EntityBoneWorm;
import com.Fishmod.mod_LavaCow.entities.EntityCactyrant;
//import com.Fishmod.mod_LavaCow.entities.EntitySludgeLord;
import com.Fishmod.mod_LavaCow.entities.EntityZombieMushroom;
import com.Fishmod.mod_LavaCow.entities.flying.EntityEnigmoth;
import com.Fishmod.mod_LavaCow.entities.tameable.EntitySalamander;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerGenericGlowing<T extends EntityLiving> implements LayerRenderer<T>{
    private ResourceLocation SPIDER_EYES;
    private final RenderLiving<T> Renderer;
    private static ResourceLocation[] TEXTURES_GLOW_SALAMANDER = {
    		new ResourceLocation("mod_lavacow:textures/mobs/salamander/salamander_glow.png"),
    		new ResourceLocation("mod_lavacow:textures/mobs/salamander/salamanderlesser_glow.png")
    };
    
	private static final ResourceLocation[] TEXTURES_GLOW_BONE_WORM = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/boneworm/boneworm_glow.png")
	};
    
	/*private static final ResourceLocation[] TEXTURES_GLOW_SLUDGE_LORD = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/sludgelord/sludgelord_glow.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/sludgelord/sludgelord_glow1.png")
	};*/
	
	private static final ResourceLocation[] TEXTURES_GLOW_CACTYRANT = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/cactyrant/cactyrant_glow.png")
	};
    
	private static final ResourceLocation[] TEXTURES_GLOW_ENIGMOTH = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/enigmoth/enigmoth_eyes.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/enigmoth/enigmoth_eyes1.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/enigmoth/enigmoth_eyes2.png")
	};
    
    public LayerGenericGlowing(RenderLiving<T> RendererIn, ResourceLocation TextureIn)
    {
        this.Renderer = RendererIn;
        SPIDER_EYES = TextureIn;
    }

    public void doRenderLayer(T entitylivingIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if(entitylivingIn instanceof EntityZombieMushroom && ((EntityZombieMushroom)entitylivingIn).getSkin() == 0)
        	return;
        
        if(entitylivingIn instanceof EntitySalamander) {
        	if(((EntitySalamander)entitylivingIn).isNymph()) {
        		SPIDER_EYES = TEXTURES_GLOW_SALAMANDER[1];
        	} else {
        		SPIDER_EYES = TEXTURES_GLOW_SALAMANDER[0];
        	}
        }
        
        if(entitylivingIn instanceof EntityBoneWorm) {
        	if (((EntityBoneWorm)entitylivingIn).getSkin() == 1) {
        		SPIDER_EYES = TEXTURES_GLOW_BONE_WORM[0];
        	} else {
        		return;
        	}
        }
        
        if(entitylivingIn instanceof EntityEnigmoth) {
        		SPIDER_EYES = TEXTURES_GLOW_ENIGMOTH[((EntityEnigmoth)entitylivingIn).getSkin()];
        }
        
        if(entitylivingIn instanceof EntityCactyrant) {
        	if (((EntityCactyrant)entitylivingIn).getSkin() == 2) {
        		SPIDER_EYES = TEXTURES_GLOW_CACTYRANT[0];
        	} else {
        		return;
        	}
        }
        
        /*if(entitylivingIn instanceof EntitySludgeLord) {
        	if (((EntitySludgeLord)entitylivingIn).getSkin() == 1) {
        		SPIDER_EYES = TEXTURES_GLOW_SLUDGE_LORD[1];
        	} else {
        		SPIDER_EYES = TEXTURES_GLOW_SLUDGE_LORD[0];
        	}
        }*/
        
    	this.Renderer.bindTexture(SPIDER_EYES);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
        GlStateManager.disableLighting();
        int i = 61680;
        int j = i % 65536;
        int k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
        GlStateManager.enableLighting();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.Renderer.getMainModel().render(entitylivingIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        i = entitylivingIn.getBrightnessForRender();
        j = i % 65536;
        k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
        this.Renderer.setLightmap(entitylivingIn);
        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
    }

    public boolean shouldCombineTextures()
    {
        return true;
    }
}
