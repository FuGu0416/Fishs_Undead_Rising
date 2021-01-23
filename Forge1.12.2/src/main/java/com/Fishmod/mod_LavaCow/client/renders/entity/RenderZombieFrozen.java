package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.client.layer.LayerGenericGlowing;
import com.Fishmod.mod_LavaCow.client.layer.LayerGenericHeldItem;
import com.Fishmod.mod_LavaCow.client.layer.LayerUnburiedArmor;
import com.Fishmod.mod_LavaCow.client.model.entity.ModelUnburied;
import com.Fishmod.mod_LavaCow.entities.EntityZombieFrozen;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderZombieFrozen extends RenderLiving<EntityZombieFrozen> {
	private static ResourceLocation TEXTURES_EYE = new ResourceLocation("mod_lavacow:textures/mobs/unburied/unburied1_eyes.png");
	private static ResourceLocation TEXTURES = new ResourceLocation("mod_lavacow:textures/mobs/unburied/unburied1.png");
	static{
        System.out.println(TEXTURES.getResourcePath());
    }

    public RenderZombieFrozen(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelUnburied(), 0.5F);
        this.addLayer(new LayerGenericGlowing(this, TEXTURES_EYE));
        this.addLayer(new LayerGenericHeldItem(this, 0.0F, 0.15F, -0.6F, 1.0F));
        LayerUnburiedArmor layerbipedarmor = new LayerUnburiedArmor(this);
        this.addLayer(layerbipedarmor);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EntityZombieFrozen entity) {
        return TEXTURES;
    }
    
    @Override
	protected void preRenderCallback(EntityZombieFrozen entity, float partialTickTime) {
    	if(entity.isChild()) {
    		GlStateManager.scale(0.6F, 0.6F, 0.6F);
    	}
	}
}
