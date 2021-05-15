package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.client.layer.LayerGenericGlowing;
import com.Fishmod.mod_LavaCow.client.layer.LayerGenericHeldItem;
import com.Fishmod.mod_LavaCow.client.layer.LayerUnburiedArmor;
import com.Fishmod.mod_LavaCow.client.model.entity.ModelUnburied;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityUnburied;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderUnburied extends RenderLiving<EntityUnburied> {
	private static ResourceLocation TEXTURES_EYE = new ResourceLocation("mod_lavacow:textures/mobs/unburied/unburied_eyes.png");
	private static ResourceLocation TEXTURES = new ResourceLocation("mod_lavacow:textures/mobs/unburied/unburied.png");
	static{
        System.out.println(TEXTURES.getResourcePath());
    }

    public RenderUnburied(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelUnburied(), 0.5F);
        this.addLayer(new LayerGenericGlowing(this, TEXTURES_EYE));
        LayerUnburiedArmor layerbipedarmor = new LayerUnburiedArmor(this);
        this.addLayer(new LayerGenericHeldItem(this, 0.0F, 0.15F, -0.6F, 1.0F));
        this.addLayer(layerbipedarmor);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EntityUnburied entity) {
        return TEXTURES;
    }
    
    @Override
	protected void preRenderCallback(EntityUnburied entity, float partialTickTime) {
    	if(entity.isChild()) {
    		GlStateManager.scale(0.6F, 0.6F, 0.6F);
    	}
    	GlStateManager.translate(0.0D, (double)entity.getSpellTicks()/20.0D, 0.0D);
	}
}
