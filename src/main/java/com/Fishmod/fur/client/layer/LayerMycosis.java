package com.Fishmod.fur.client.layer;

import com.Fishmod.fur.block.FURShroomBlock;
import com.Fishmod.fur.entities.tameable.unburied.MycosisEntity;
import com.Fishmod.fur.entities.tameable.unburied.UnburiedEntity;
import com.Fishmod.fur.init.FURBlockRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.cache.texture.AutoGlowingTexture;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.util.RenderUtils;

public class LayerMycosis extends GeoRenderLayer<UnburiedEntity> {
	public LayerMycosis(GeoRenderer<UnburiedEntity> mycosisRenderer) {
		super(mycosisRenderer);
	}

    @Override
    public void renderForBone(PoseStack poseStack, UnburiedEntity entity, GeoBone model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
    	if (!(model.getName().equals("helmet") || model.getName().equals("Body_chest"))) return;
    	if (entity instanceof MycosisEntity mycosis && mycosis.isSheared()) return;

    	BlockState blockstate;
    	if (entity.getSkin() == 2) {
    		// Pick a Glowshroom variant (AGE_2 → glowshroom1/2/3) from the UUID mixed with the bone name,
    		// so the helmet and Body_chest shrooms can differ while each stays stable per entity+bone
    		// (no per-frame flicker).
    		int seed = entity.getUUID().hashCode() * 31 + model.getName().hashCode();
    		int age = Math.floorMod(seed, FURShroomBlock.AGE.getPossibleValues().size());
    		blockstate = FURBlockRegistry.GLOWSHROOM.get().defaultBlockState().setValue(FURShroomBlock.AGE, Integer.valueOf(age));
    	} else {
    		blockstate = FURBlockRegistry.CORDY_SHROOM.get().defaultBlockState().setValue(FURShroomBlock.AGE, Integer.valueOf(1));
    	}
    	
    	poseStack.pushPose();
    	RenderUtils.translateAndRotateMatrixForBone(poseStack, model);
    	
    	if (model.getName().equals("helmet")) {
    		poseStack.translate(-0.5f, 0.5f, -0.5f);
    	} else if (model.getName().equals("Body_chest")) {
    		poseStack.translate(-0.5f, 0.625f, -0.25f);
    		poseStack.mulPose(Axis.XP.rotationDegrees(60.0F));
    	}

        // Render at the entity's scene light (not FULL_BRIGHT): the Glowshroom model bakes its glow via
        // forge_data fullbright faces on the "_e" overlay, so the emissive texture lights up on its own
        // while the rest of the shroom is lit normally. Forcing FULL_BRIGHT would flatten that glow out.
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(blockstate, poseStack, bufferSource, packedLight, packedOverlay, ModelData.EMPTY, null);

        poseStack.popPose();
    }
    
	/**
	 * Get the render type to use for this glowlayer renderer
	 * <p>
	 * Uses a custom RenderType similar to {@link RenderType#eyes(ResourceLocation)} by default, which may not be ideal in all circumstances
	 */
	protected RenderType getRenderType(UnburiedEntity animatable) {
		return AutoGlowingTexture.getRenderType(getTextureResource(animatable));
	}

	/**
	 * This is the method that is actually called by the render for your render layer to function.<br>
	 * This is called <i>after</i> the animatable has been rendered, but before supplementary rendering like nametags.
	 */
	@Override
	public void render(PoseStack poseStack, UnburiedEntity animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
		if (animatable.getSkin() != 2) return;
		
		RenderType emissiveRenderType = getRenderType(animatable);

		getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable, emissiveRenderType,
							   bufferSource.getBuffer(emissiveRenderType), partialTick, LightTexture.FULL_SKY, OverlayTexture.NO_OVERLAY,
							   1, 1, 1, 1);
	}
}
