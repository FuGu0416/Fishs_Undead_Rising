package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.client.layer.LayerMycosis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class MycosisRenderer extends AbstractUnburiedRenderer {
	public MycosisRenderer(EntityRendererProvider.Context rendermanagerIn) {
		super(rendermanagerIn);
		this.addRenderLayer(new LayerMycosis(this));
	}
}
