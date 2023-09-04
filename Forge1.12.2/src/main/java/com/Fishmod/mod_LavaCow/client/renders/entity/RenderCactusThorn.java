package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.entities.projectiles.EntityCactusThorn;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderCactusThorn extends RenderArrow<EntityCactusThorn> {
	private static final ResourceLocation TEXTURES = new ResourceLocation("mod_lavacow:textures/mobs/projectile/cactus_thorn.png");

	public RenderCactusThorn(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityCactusThorn entity) {
		return TEXTURES;
	}
}
