package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.client.model.entity.ModelVespaCocoon;
import com.Fishmod.mod_LavaCow.entities.EntityVespaCocoon;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderVespaCocoon extends RenderLiving<EntityVespaCocoon>  {
	private static ResourceLocation TEXTURES = new ResourceLocation("mod_lavacow:textures/mobs/vespa_cocoon.png");
	static{
        System.out.println(TEXTURES.getResourcePath());
    }
    //public static final Factory FACTORY = new Factory();

    public RenderVespaCocoon(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelVespaCocoon(), 0.2F);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EntityVespaCocoon entity) {
        return TEXTURES;
    }
    
    @Override
	protected void preRenderCallback(EntityVespaCocoon entity, float partialTickTime) {
    	float f = (1.0f + 0.2f * MathHelper.sin(entity.ticksExisted * 0.25F));
    	
    	if(!entity.isChild())GlStateManager.scale(1.2F, f, 1.2F);
	}
}
