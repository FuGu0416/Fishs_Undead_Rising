package com.Fishmod.fur.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class UnburiedRenderer extends AbstractUnburiedRenderer {
	public UnburiedRenderer(Context rendermanagerIn) {
		super(rendermanagerIn);
		this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
	}
}
