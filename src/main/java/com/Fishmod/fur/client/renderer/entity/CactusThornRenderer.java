package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.projectiles.CactusThornEntity;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CactusThornRenderer extends ArrowRenderer<CactusThornEntity> {
	public static final ResourceLocation TEXTURE = new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/projectile/cactus_thorn.png");

	public CactusThornRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(CactusThornEntity entity) {
		return TEXTURE;
	}
}
