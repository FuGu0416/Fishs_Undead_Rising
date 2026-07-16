package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.client.model.SpectralCutlassModel;
import com.Fishmod.fur.entities.tameable.SpectralCutlassEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

/**
 * GeckoLib renderer for {@link SpectralCutlassEntity}. The geometry ({@code spectral_cutlass.geo.json}) is
 * a bare rig whose {@code base} bone carries the idle / dash / attack animation; the actual visible blade
 * is the cutlass <em>item</em>, rendered onto that bone via {@link BlockAndItemGeoLayer} so it moves exactly
 * with the bone. The item transform is {@link ItemDisplayContext#NONE} (bone animation drives placement),
 * with an extra +90° yaw so the blade faces edge-on rather than flat, and it renders emissive (full-bright).
 *
 * <p>Over the blade's last {@link SpectralCutlassEntity#EXPIRY_WARNING_TICKS} the item is drawn through an
 * alpha-reducing buffer wrapper so it <em>pulses translucent</em> as an expiry warning (client-only; no
 * particles or sound). Remaining life is read from the entity's synced {@code lifeTicks}.
 */
@OnlyIn(Dist.CLIENT)
public class SpectralCutlassRenderer extends GeoEntityRenderer<SpectralCutlassEntity> {

	public SpectralCutlassRenderer(EntityRendererProvider.Context context) {
		super(context, new SpectralCutlassModel());
		this.shadowRadius = 0.0F;

		this.addRenderLayer(new BlockAndItemGeoLayer<SpectralCutlassEntity>(this) {
			@Override
			protected ItemStack getStackForBone(GeoBone bone, SpectralCutlassEntity animatable) {
				return "base".equals(bone.getName()) ? animatable.getDisplayStack() : null;
			}

			@Override
			protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, SpectralCutlassEntity animatable) {
				return ItemDisplayContext.NONE;
			}

			@Override
			protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack, SpectralCutlassEntity animatable,
											  MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {
				poseStack.mulPose(Axis.XP.rotationDegrees(30.0F));
				poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
				// Expiry warning: pulse the blade translucent over its final ticks. Outside the window this is 1.0 (opaque).
				float alpha = expiryAlpha(animatable, partialTick);
				MultiBufferSource buffers = alpha < 1.0F ? new AlphaBufferSource(bufferSource, alpha) : bufferSource;
				// Render the blade emissive (full-bright), so it glows regardless of world light.
				super.renderStackForBone(poseStack, bone, stack, animatable, buffers, partialTick, LightTexture.FULL_BRIGHT, packedOverlay);
			}
		});
	}

	/**
	 * Blade opacity for the current frame. 1.0 (fully opaque) until the last
	 * {@link SpectralCutlassEntity#EXPIRY_WARNING_TICKS}; inside that window it pulses via {@code sin} — the
	 * pulse speeds up and its brightness ceiling fades as the blade nears expiry.
	 */
	private static float expiryAlpha(SpectralCutlassEntity blade, float partialTick) {
		int life = blade.getLifeTicks();
		if (life > SpectralCutlassEntity.EXPIRY_WARNING_TICKS || life <= 0) {
			return 1.0F;
		}
		float remain = life / (float) SpectralCutlassEntity.EXPIRY_WARNING_TICKS; // 1 -> 0 across the window
		float phase = blade.tickCount + partialTick;
		float speed = 0.3F + (1.0F - remain) * 0.5F;             // pulses faster the closer it is to expiring
		float pulse = (Mth.sin(phase * speed) + 1.0F) * 0.5F;    // 0..1
		float ceiling = 0.3F + 0.6F * remain;                    // peak opacity also fades toward expiry
		return Mth.clamp(0.12F + (ceiling - 0.12F) * pulse, 0.1F, 1.0F);
	}

	/** Wraps a buffer source so every quad the item draws goes through an {@link AlphaVertexConsumer} at the given alpha. */
	private static final class AlphaBufferSource implements MultiBufferSource {
		private final MultiBufferSource delegate;
		private final float alpha;

		AlphaBufferSource(MultiBufferSource delegate, float alpha) {
			this.delegate = delegate;
			this.alpha = alpha;
		}

		@Override
		public VertexConsumer getBuffer(RenderType type) {
			return new AlphaVertexConsumer(this.delegate.getBuffer(type), this.alpha);
		}
	}

	/**
	 * Delegating {@link VertexConsumer} that scales vertex alpha. The item's baked quads are written through
	 * the Forge {@code putBulkData} overload (which carries an alpha argument), and per-vertex paths (tint /
	 * enchantment glint) go through {@link #color}; both are scaled so the whole blade fades uniformly. World
	 * item rendering uses a translucent-cull render type, so the reduced alpha actually blends.
	 */
	private static final class AlphaVertexConsumer implements VertexConsumer {
		private final VertexConsumer delegate;
		private final float alpha;

		AlphaVertexConsumer(VertexConsumer delegate, float alpha) {
			this.delegate = delegate;
			this.alpha = alpha;
		}

		@Override
		public VertexConsumer vertex(double x, double y, double z) {
			return this.delegate.vertex(x, y, z);
		}

		@Override
		public VertexConsumer color(int r, int g, int b, int a) {
			return this.delegate.color(r, g, b, (int) (a * this.alpha));
		}

		@Override
		public VertexConsumer uv(float u, float v) {
			return this.delegate.uv(u, v);
		}

		@Override
		public VertexConsumer overlayCoords(int u, int v) {
			return this.delegate.overlayCoords(u, v);
		}

		@Override
		public VertexConsumer uv2(int u, int v) {
			return this.delegate.uv2(u, v);
		}

		@Override
		public VertexConsumer normal(float x, float y, float z) {
			return this.delegate.normal(x, y, z);
		}

		@Override
		public void endVertex() {
			this.delegate.endVertex();
		}

		@Override
		public void defaultColor(int r, int g, int b, int a) {
			this.delegate.defaultColor(r, g, b, a);
		}

		@Override
		public void unsetDefaultColor() {
			this.delegate.unsetDefaultColor();
		}

		@Override
		public void putBulkData(PoseStack.Pose pose, BakedQuad quad, float[] brightness, float red, float green, float blue,
								float alpha, int[] lightmap, int overlay, boolean readExistingColor) {
			this.delegate.putBulkData(pose, quad, brightness, red, green, blue, alpha * this.alpha, lightmap, overlay, readExistingColor);
		}
	}
}
