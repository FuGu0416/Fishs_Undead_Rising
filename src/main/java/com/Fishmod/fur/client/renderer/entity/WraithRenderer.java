package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.entities.floating.WraithEntity;
import com.Fishmod.fur.client.layer.LayerWraith;
import com.Fishmod.fur.client.model.WraithModel;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.core.object.Color;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class WraithRenderer extends GeoEntityRenderer<WraithEntity> {
	/** Fixed opacity for the primary (always-drawn) pass, so it reads as a translucent "ghost"
	 *  base underneath {@link LayerWraith}'s opaque copy of the same texture, which fades in/out
	 *  via {@link WraithEntity#getFadeIn}. Replaces the old separate "_overlay" texture approach —
	 *  both layers now draw the same per-variant art, just at different alpha. */
	private static final float GHOST_BASE_ALPHA = 0.4F;

    public WraithRenderer(EntityRendererProvider.Context rendermanagerIn) {
    	super(rendermanagerIn, new WraithModel());
    	this.addRenderLayer(new LayerWraith<>(this));
        this.shadowRadius = 0.0F;
    }

    @Override
    public ResourceLocation getTextureLocation(WraithEntity entity) {
    	return super.getTextureLocation(entity);
    }

    @Override
    protected int getBlockLightLevel(WraithEntity entity, BlockPos pos) {
        return 8;
    }

    @Override
    public Color getRenderColor(WraithEntity animatable, float partialTick, int packedLight) {
        return Color.ofRGBA(1F, 1F, 1F, GHOST_BASE_ALPHA);
    }
}
