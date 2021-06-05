package com.Fishmod.mod_LavaCow.client.renders.tileentity;

import com.Fishmod.mod_LavaCow.blocks.BlockScarecrowHead;
import com.Fishmod.mod_LavaCow.client.model.block.ModelScarecrowHead_common;
import com.Fishmod.mod_LavaCow.client.model.block.ModelScarecrowHead_plague;
import com.Fishmod.mod_LavaCow.client.model.block.ModelScarecrowHead_straw;
import com.Fishmod.mod_LavaCow.init.Modblocks;
import com.Fishmod.mod_LavaCow.tileentity.TileEntityScarecrowHead;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class TileEntityScarecrowHeadRenderer extends TileEntitySpecialRenderer<TileEntityScarecrowHead> {
	private static final ResourceLocation TEXTURE = new ResourceLocation("mod_lavacow:textures/mobs/scarecrow/scarecrow.png");
	private ModelBase modelbase;
	private final ModelScarecrowHead_common MODEL_COMMON = new ModelScarecrowHead_common();
	private final ModelScarecrowHead_straw MODEL_STRAW = new ModelScarecrowHead_straw();
	private final ModelScarecrowHead_plague MODEL_PLAGUE = new ModelScarecrowHead_plague();
	public static TileEntityScarecrowHeadRenderer instance;
	
	public TileEntityScarecrowHeadRenderer(int skullType) {
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
	public void render(TileEntityScarecrowHead tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		if(tile == null || !tile.hasWorld()) {
			renderTileAsItem(x, y, z);
			return;
		}
		renderTile(tile, x, y, z, partialTicks, destroyStage, alpha);
	}
	
    public void renderTile(TileEntityScarecrowHead te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
		IBlockState state = te.getWorld().getBlockState(te.getPos());
		if(state == null || (state.getBlock() != Modblocks.SCARECROWHEAD_COMMON && state.getBlock() != Modblocks.SCARECROWHEAD_STRAW && state.getBlock() != Modblocks.SCARECROWHEAD_PLAGUE))
			return;
    	EnumFacing enumfacing = state.getValue(BlockScarecrowHead.FACING);
        this.renderSkull((float)x, (float)y, (float)z, enumfacing, (float)(te.getSkullRotation() * 360) / 16.0F, te.getSkullType(), destroyStage);
    }
    
	private void renderTileAsItem(double x, double y, double z) {
		GlStateManager.pushMatrix();
		bindTexture(TEXTURE);
		GlStateManager.translate((float) x, (float) y, (float) z);
		//GlStateManager.rotate(210.0F, 0.0F, 0.0F, 1.0F);
		//GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
		//GlStateManager.rotate(-45.0F, 1.0F, 0.0F, 0.0F);		
		GlStateManager.scale(1.0F, 1.0F, 1.0F);
		modelbase.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		GlStateManager.popMatrix();
	}
    
    public void setRendererDispatcher(TileEntityRendererDispatcher rendererDispatcherIn)
    {
        super.setRendererDispatcher(rendererDispatcherIn);
        instance = this;
    }

    public void renderSkull(float x, float y, float z, EnumFacing facing, float rotationIn, int skullType, int destroyStage)
    {  	
    	bindTexture(TEXTURE);
    	
		if(facing == null)
			return;

		switch (facing) {
			case DOWN:
				break;
			case UP:
				break;
			case NORTH:
				GlStateManager.pushMatrix();
				GlStateManager.translate(x + 0.5D, y, z + 0.5D);
				GlStateManager.scale(1.0F, 1.0F, 1.0F);
				GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
				modelbase.render((Entity)null, 0.0F, 0.0F, 0.0F, rotationIn, 0.0F, 0.0625F);
				GlStateManager.popMatrix();
				break;
			case SOUTH:
				GlStateManager.pushMatrix();
				GlStateManager.translate(x + 0.5D, y, z + 0.5D);
				GlStateManager.scale(1.0F, 1.0F, 1.0F);
				GlStateManager.rotate(0.0F, 0.0F, 1.0F, 0.0F);
				modelbase.render((Entity)null, 0.0F, 0.0F, 0.0F, rotationIn, 0.0F, 0.0625F);
				GlStateManager.popMatrix();
				break;
			case WEST:
				GlStateManager.pushMatrix();
				GlStateManager.translate(x + 0.5D, y, z + 0.5D);
				GlStateManager.scale(1.0F, 1.0F, 1.0F);
				GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
				modelbase.render((Entity)null, 0.0F, 0.0F, 0.0F, rotationIn, 0.0F, 0.0625F);
				GlStateManager.popMatrix();
				break;
			case EAST:
				GlStateManager.pushMatrix();
				GlStateManager.translate(x + 0.5D, y, z + 0.5D);
				GlStateManager.scale(1.0F, 1.0F, 1.0F);
				GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
				modelbase.render((Entity)null, 0.0F, 0.0F, 0.0F, rotationIn, 0.0F, 0.0625F);
				GlStateManager.popMatrix();
				break;
		}
    }
}
