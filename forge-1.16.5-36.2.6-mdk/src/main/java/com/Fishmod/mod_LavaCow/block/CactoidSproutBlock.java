package com.Fishmod.mod_LavaCow.block;

import java.util.Random;

import com.Fishmod.mod_LavaCow.entities.tameable.CactoidEntity;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;

public class CactoidSproutBlock extends Block {
	private static final VoxelShape SPROUT_AABB = Block.box(3.0D, 0.0D, 3.0D, 12.0D, 7.0D, 12.0D);
	public static final IntegerProperty HATCH = BlockStateProperties.AGE_25;
	
	public CactoidSproutBlock(Properties p_i48440_1_) {
		super(p_i48440_1_);
		this.registerDefaultState(this.stateDefinition.any().setValue(HATCH, Integer.valueOf(0)));
	}
	
	protected boolean mayPlaceOn(BlockState blockstate, IBlockReader p_200014_2_, BlockPos pos) {
		return blockstate.is(BlockTags.SAND) || blockstate.getBlock().equals(Blocks.SOUL_SAND);
	}
	   
	@Override
	public void randomTick(BlockState blockstate, ServerWorld worldIn, BlockPos pos, Random rand) {
		if (onSand(worldIn, pos) && worldIn.getDifficulty() != Difficulty.PEACEFUL) {
			int i = blockstate.getValue(HATCH);
			if (i < 25 && rand.nextFloat() < 0.001F) {
				worldIn.setBlock(pos, blockstate.setValue(HATCH, Integer.valueOf(i + 1)), 2);
			} else {
	            worldIn.removeBlock(pos, false);
            	worldIn.levelEvent(2001, pos, Block.getId(blockstate));
                CactoidEntity cactoidentity = FUREntityRegistry.CACTOID.create(worldIn);
                cactoidentity.setAge(-24000);
                cactoidentity.moveTo((double)pos.getX() + 0.3D, (double)pos.getY(), (double)pos.getZ() + 0.3D, 0.0F, 0.0F);
                cactoidentity.playSound(SoundEvents.BEE_POLLINATE, 1.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.8F));

                if(worldIn.dimensionType().ultraWarm() || blockstate.getBlock().equals(Blocks.SOUL_SAND)) {
                	cactoidentity.setSkin(3);
                }
                
                worldIn.addFreshEntity(cactoidentity);
			}
		}
	}
	
	public static boolean onSand(IBlockReader p_203168_0_, BlockPos pos) {
		return isSand(p_203168_0_, pos.below());
	}

	public static boolean isSand(IBlockReader p_241473_0_, BlockPos pos) {
		return p_241473_0_.getBlockState(pos).is(BlockTags.SAND) || p_241473_0_.getBlockState(pos).getBlock().equals(Blocks.SOUL_SAND);
	}
	
	@Override
	public AbstractBlock.OffsetType getOffsetType() {
		return AbstractBlock.OffsetType.XZ;
	}
	
	@Override
	public VoxelShape getShape(BlockState blockstate, IBlockReader p_220053_2_, BlockPos pos, ISelectionContext p_220053_4_) {
		Vector3d vector3d = blockstate.getOffset(p_220053_2_, pos);
		return SPROUT_AABB.move(vector3d.x(), vector3d.y(), vector3d.z());
	}
	
	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
		p_206840_1_.add(HATCH);
	}
}
