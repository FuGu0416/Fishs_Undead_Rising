package com.Fishmod.mod_LavaCow.client.renderer.tileentity;

import com.Fishmod.mod_LavaCow.block.ScarecrowHeadBlock;
import com.Fishmod.mod_LavaCow.client.model.block.ModelScarecrowHead_common;
import com.Fishmod.mod_LavaCow.client.model.block.ModelScarecrowHead_plague;
import com.Fishmod.mod_LavaCow.client.model.block.ModelScarecrowHead_straw;
import com.Fishmod.mod_LavaCow.tileentity.ScarecrowHeadTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScarecrowHeadTileEntityRenderer<T extends ScarecrowHeadTileEntity> extends TileEntityRenderer<T> {
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/scarecrow/scarecrow.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/scarecrow/scarecrow1.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/scarecrow/scarecrow2.png")
	};
	private EntityModel<Entity> modelbase;
	private ResourceLocation texture;
	private final ModelScarecrowHead_common MODEL_COMMON = new ModelScarecrowHead_common();
	private final ModelScarecrowHead_straw MODEL_STRAW = new ModelScarecrowHead_straw();
	private final ModelScarecrowHead_plague MODEL_PLAGUE = new ModelScarecrowHead_plague();
	
	public ScarecrowHeadTileEntityRenderer(int skullType, TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
		this.texture = TEXTURES[skullType];
    	switch (skullType) {
			case 0:
				modelbase = MODEL_COMMON;
				break;
			case 1:
				modelbase = MODEL_STRAW;
				break;
			case 2:
				modelbase = MODEL_PLAGUE;
				break;
			default:
				modelbase = MODEL_COMMON;
				break;
		}
	}

	@Override
	public void render(T tile, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {	
		matrixStackIn.pushPose();
		if(tile != null && tile.hasLevel()) {
			BlockState blockstate = tile.getBlockState();
			float f1 = -22.5F * blockstate.getValue(ScarecrowHeadBlock.ROTATION);
			matrixStackIn.translate(0.5D, 0.0D, 0.5D);	
			matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(f1));
		}
		matrixStackIn.scale(1.0F, 1.0F, 1.0F);
		modelbase.renderToBuffer(matrixStackIn, bufferIn.getBuffer(RenderType.entityCutoutNoCull(this.texture)), combinedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		matrixStackIn.popPose();
	}
}
