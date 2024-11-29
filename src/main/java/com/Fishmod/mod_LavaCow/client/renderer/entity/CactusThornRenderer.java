package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.entities.projectiles.CactusThornEntity;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CactusThornRenderer extends ArrowRenderer<CactusThornEntity> {
	public static final ResourceLocation TEXTURE = new ResourceLocation("mod_lavacow:textures/mobs/projectile/cactus_thorn.png");

	public CactusThornRenderer(EntityRendererManager p_i46549_1_) {
		super(p_i46549_1_);
	}

	@Override
	public ResourceLocation getTextureLocation(CactusThornEntity p_110775_1_) {
		return TEXTURE;
	}
}
