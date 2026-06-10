package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.entities.flying.WarpedFireflyEntity;
import com.Fishmod.fur.client.model.WarpedFireflyModel;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

@OnlyIn(Dist.CLIENT)
public class WarpedFireflyRenderer extends GeoEntityRenderer<WarpedFireflyEntity> {

	public WarpedFireflyRenderer(EntityRendererProvider.Context rendermanagerIn) {
		super(rendermanagerIn, new WarpedFireflyModel());
		this.shadowRadius = 0.4F;
		// Emissive glow: GeckoLib auto-loads the sibling "<texture>_glowmask.png".
		this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
	}

	@Override
	public ResourceLocation getTextureLocation(WarpedFireflyEntity entity) {
		return super.getTextureLocation(entity);
	}
}
