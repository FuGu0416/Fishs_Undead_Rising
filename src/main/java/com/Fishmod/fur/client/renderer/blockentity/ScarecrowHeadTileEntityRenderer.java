package com.Fishmod.fur.client.renderer.blockentity;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.block.ScarecrowHeadBlock;
import com.Fishmod.fur.block.blockentity.ScarecrowHeadTileEntity;
import com.Fishmod.fur.client.model.block.ModelScarecrowHead_common;
import com.Fishmod.fur.client.model.block.ModelScarecrowHead_plague;
import com.Fishmod.fur.client.model.block.ModelScarecrowHead_straw;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;

public class ScarecrowHeadTileEntityRenderer<T extends ScarecrowHeadTileEntity> implements BlockEntityRenderer<T> {
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation(mod_LavaCow.MODID, "textures/armors/scarecrow/scarecrow.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/armors/scarecrow/scarecrow1.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/armors/scarecrow/scarecrow2.png")
	};
	private EntityModel<Entity> modelbase;
	private ResourceLocation texture;
	
	public ScarecrowHeadTileEntityRenderer(int skullType, Context context) {
		super();
		this.texture = TEXTURES[skullType];
    	switch (skullType) {
			case 0:
			default:
	            this.modelbase = new ModelScarecrowHead_common<>(context.bakeLayer(ModelScarecrowHead_common.LAYER_LOCATION));			
				break;
			case 1:
				this.modelbase = new ModelScarecrowHead_straw<>(context.bakeLayer(ModelScarecrowHead_straw.LAYER_LOCATION));			
				break;
			case 2:
				this.modelbase = new ModelScarecrowHead_plague<>(context.bakeLayer(ModelScarecrowHead_plague.LAYER_LOCATION));			
				break;
		}
	}

	@Override
	public void render(T tile, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		matrixStackIn.pushPose();
		if(tile != null && tile.hasLevel()) {
			BlockState blockstate = tile.getBlockState();
			float f1 = -22.5F * blockstate.getValue(ScarecrowHeadBlock.ROTATION);
			matrixStackIn.translate(0.5D, 0.0D, 0.5D);	
			matrixStackIn.mulPose(Axis.YP.rotationDegrees(f1));
		}
		matrixStackIn.mulPose(Axis.ZP.rotationDegrees(180.F));
		matrixStackIn.translate(0.0D, -1.5D, 0.0D);	
		matrixStackIn.scale(1.0F, 1.0F, 1.0F);
		modelbase.renderToBuffer(matrixStackIn, bufferIn.getBuffer(RenderType.entityCutoutNoCull(this.texture)), combinedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		matrixStackIn.popPose();		
	}
}
