package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.entities.projectiles.EntitySludgeJet;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.util.ResourceLocation;

public class RenderSludgeJet extends Render<EntityFireball>{

    public RenderSludgeJet(RenderManager rendermanagerIn) {
        super(rendermanagerIn);
    }    
    
    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(EntitySludgeJet entity, double x, double y, double z, float entityYaw, float partialTicks)
    {	
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityFireball entity) {
		return null;
	}
}
