package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.projectiles.FURArrowEntity;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FURArrowRenderer extends ArrowRenderer<FURArrowEntity> {
	private int skinType;
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
		new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/projectile/ghoul_arrow.png"),
		new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/projectile/fang_arrow.png")
	};
	
	public FURArrowRenderer(EntityRendererProvider.Context p_i46549_1_, int typeIn) {
		super(p_i46549_1_);
		this.skinType = typeIn;		
	}

	@Override
	public ResourceLocation getTextureLocation(FURArrowEntity p_110775_1_) {
		return TEXTURES[this.skinType];
	}
}
