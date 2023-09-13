package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.entities.projectiles.EntityMothScales;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.util.ResourceLocation;

public class RenderMothScales extends Render<EntityFireball>{

    public RenderMothScales(RenderManager rendermanagerIn) {
        super(rendermanagerIn);
    }    
    
    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(EntityMothScales entity, double x, double y, double z, float entityYaw, float partialTicks)
    {	
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityFireball entity) {
		return null;
	}
}
