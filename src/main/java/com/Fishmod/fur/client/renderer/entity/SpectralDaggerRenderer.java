package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.entities.tameable.SpectralDaggerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

/**
 * PLACEHOLDER renderer for {@link SpectralDaggerEntity} (Phase 1).
 *
 * Draws the dagger's own item model, floating tip-down and slowly self-rotating, with no bespoke
 * assets. The Phase 2 GeckoLib model/renderer (translucent cyan-white blade, dash pose, expiry
 * transparency pulse) is specified in PLACEHOLDER.md and will replace this class.
 */
public class SpectralDaggerRenderer extends EntityRenderer<SpectralDaggerEntity> {
	private final ItemRenderer itemRenderer;

	public SpectralDaggerRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.itemRenderer = context.getItemRenderer();
		this.shadowRadius = 0.0F;
	}

	@Override
	public void render(SpectralDaggerEntity entity, float yaw, float partialTicks, PoseStack pose, MultiBufferSource buffer, int light) {
		pose.pushPose();
		pose.translate(0.0D, entity.getBbHeight() * 0.5D, 0.0D);

		float spin = (entity.tickCount + partialTicks) * (entity.isCharging() ? 24.0F : 6.0F);
		pose.mulPose(Axis.YP.rotationDegrees(spin));
		pose.mulPose(Axis.XP.rotationDegrees(180.0F)); // blade tip pointing down
		pose.scale(1.2F, 1.2F, 1.2F);

		ItemStack stack = entity.getDisplayStack();
		this.itemRenderer.renderStatic(stack, ItemDisplayContext.GROUND, light, OverlayTexture.NO_OVERLAY, pose, buffer, entity.level(), entity.getId());

		pose.popPose();
		super.render(entity, yaw, partialTicks, pose, buffer, light);
	}

	@Override
	public ResourceLocation getTextureLocation(SpectralDaggerEntity entity) {
		return InventoryMenu.BLOCK_ATLAS;
	}
}
