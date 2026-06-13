package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.entities.projectiles.MoltenGlobEntity;
import com.Fishmod.fur.mod_LavaCow;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Renders the Molten Glob as a 3D textured cube (a custom {@code cube_all} model), shrunk to
 * projectile size and tumbling as it flies. Drawn full-bright so the molten block glows in dark
 * caves. The texture follows the shooter's skin via {@link MoltenGlobEntity#getVariant()}:
 * variant 0 = {@code molten_glob}, variant 1 = {@code molten_glob1} (soul).
 *
 * <p>Both models are baked on demand via {@code ModelEvent.RegisterAdditional} (see ClientProxy)
 * and keyed by the plain {@link ResourceLocation}s below.
 */
@OnlyIn(Dist.CLIENT)
public class MoltenGlobRenderer extends EntityRenderer<MoltenGlobEntity> {

	/** Extra models registered for baking; files live at {@code assets/fur/models/<path>.json}. */
	public static final ResourceLocation MODEL = new ResourceLocation(mod_LavaCow.MODID, "molten_glob");
	public static final ResourceLocation MODEL_SOUL = new ResourceLocation(mod_LavaCow.MODID, "molten_glob1");

	/** Block models are a full 1×1×1 cube; shrink to roughly the projectile's hitbox. */
	private static final float SCALE = 0.5F;

	public MoltenGlobRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(MoltenGlobEntity entity, float yaw, float partialTicks, PoseStack pose, MultiBufferSource buffer, int packedLight) {
		pose.pushPose();
		pose.scale(SCALE, SCALE, SCALE);

		// Tumble about the block's centre so it reads as a flying chunk of molten rock.
		float spin = (entity.tickCount + partialTicks) * 12.0F;
		pose.mulPose(Axis.YP.rotationDegrees(spin));
		pose.mulPose(Axis.XP.rotationDegrees(spin * 0.6F));

		// Block models are drawn from their (0,0,0) corner — offset so the centre sits on the entity.
		pose.translate(-0.5D, -0.5D, -0.5D);

		BlockRenderDispatcher dispatcher = Minecraft.getInstance().getBlockRenderer();
		ModelBlockRenderer modelRenderer = dispatcher.getModelRenderer();
		BakedModel model = Minecraft.getInstance().getModelManager().getModel(entity.getVariant() == 1 ? MODEL_SOUL : MODEL);
		VertexConsumer consumer = buffer.getBuffer(RenderType.solid());
		modelRenderer.renderModel(pose.last(), consumer, null, model, 1.0F, 1.0F, 1.0F, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);

		pose.popPose();
		super.render(entity, yaw, partialTicks, pose, buffer, packedLight);
	}

	@Override
	public ResourceLocation getTextureLocation(MoltenGlobEntity entity) {
		return TextureAtlas.LOCATION_BLOCKS;
	}
}
