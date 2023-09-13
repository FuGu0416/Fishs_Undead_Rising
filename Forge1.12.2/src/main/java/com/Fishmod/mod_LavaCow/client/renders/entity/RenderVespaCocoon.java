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
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/vespa/vespa_cocoon.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/enigmoth/enigmoth_cocoon.png"),
	};
	static {
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getResourcePath());
    }
	
    public RenderVespaCocoon(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelVespaCocoon(), 0.2F);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EntityVespaCocoon entity) {
        return TEXTURES[entity.getSkin()];
    }
    
    @Override
	protected void preRenderCallback(EntityVespaCocoon entity, float partialTickTime) {
    	float f = (1.2f + 0.2f * MathHelper.sin(entity.ticksExisted * 0.25F));
    	
    	if(!entity.isChild())GlStateManager.scale(1.5F, f, 1.5F);
	}
}
