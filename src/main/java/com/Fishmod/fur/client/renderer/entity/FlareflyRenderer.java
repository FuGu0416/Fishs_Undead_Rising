package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.entities.flying.FlareflyEntity;
import com.Fishmod.fur.client.model.FlareflyModel;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

@OnlyIn(Dist.CLIENT)
public class FlareflyRenderer extends GeoEntityRenderer<FlareflyEntity> {

	public FlareflyRenderer(EntityRendererProvider.Context rendermanagerIn) {
		super(rendermanagerIn, new FlareflyModel());
		this.shadowRadius = 0.4F;
		// Emissive glow: GeckoLib auto-loads the sibling "<texture>_glowmask.png".
		this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
	}

	@Override
	public ResourceLocation getTextureLocation(FlareflyEntity entity) {
		return super.getTextureLocation(entity);
	}
}
