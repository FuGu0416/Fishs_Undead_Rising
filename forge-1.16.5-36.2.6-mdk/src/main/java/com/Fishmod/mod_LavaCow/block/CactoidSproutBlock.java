package com.Fishmod.mod_LavaCow.block;

import java.util.Random;

import com.Fishmod.mod_LavaCow.entities.tameable.CactoidEntity;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
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
		return blockstate.is(BlockTags.SAND);
	}
	   
	public void randomTick(BlockState blockstate, ServerWorld worldIn, BlockPos pos, Random rand) {
		if (onSand(worldIn, pos)) {
			int i = blockstate.getValue(HATCH);
			if (i < 25 && rand.nextFloat() < 0.001F) {
				worldIn.setBlock(pos, blockstate.setValue(HATCH, Integer.valueOf(i + 1)), 2);
			} else {
	            worldIn.removeBlock(pos, false);
            	worldIn.levelEvent(2001, pos, Block.getId(blockstate));
                CactoidEntity turtleentity = FUREntityRegistry.CACTOID.create(worldIn);
                turtleentity.setAge(-24000);
                turtleentity.moveTo((double)pos.getX() + 0.3D, (double)pos.getY(), (double)pos.getZ() + 0.3D, 0.0F, 0.0F);
                if(worldIn.dimensionType().ultraWarm())
                	turtleentity.setSkin(3);
                worldIn.addFreshEntity(turtleentity);
			}
		}
	}
	
	public static boolean onSand(IBlockReader p_203168_0_, BlockPos pos) {
		return isSand(p_203168_0_, pos.below());
	}

	public static boolean isSand(IBlockReader p_241473_0_, BlockPos pos) {
		return p_241473_0_.getBlockState(pos).is(BlockTags.SAND);
	}
	
	public VoxelShape getShape(BlockState blockstate, IBlockReader p_220053_2_, BlockPos pos, ISelectionContext p_220053_4_) {
		return SPROUT_AABB;
	}	
	
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
		p_206840_1_.add(HATCH);
	}
}
