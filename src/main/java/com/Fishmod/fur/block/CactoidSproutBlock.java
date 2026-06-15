package com.Fishmod.fur.block;

import com.Fishmod.fur.entities.tameable.CactoidEntity;
import com.Fishmod.fur.init.FUREntityRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CactoidSproutBlock extends Block {
	private static final VoxelShape SPROUT_AABB = Block.box(3.0D, 0.0D, 3.0D, 12.0D, 7.0D, 12.0D);
	public static final IntegerProperty HATCH = BlockStateProperties.AGE_25;
	
	public CactoidSproutBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(HATCH, Integer.valueOf(0)));
	}
	
	protected boolean mayPlaceOn(BlockState blockstate, BlockGetter getter, BlockPos pos) {
		return blockstate.is(BlockTags.SAND) || blockstate.getBlock().equals(Blocks.SOUL_SAND);
	}
	   
	@Override
	public void randomTick(BlockState blockstate, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
		if (onSand(worldIn, pos) && worldIn.getDifficulty() != Difficulty.PEACEFUL) {
			int i = blockstate.getValue(HATCH);
			if (i < 25 && rand.nextFloat() < 0.001F) {
				worldIn.setBlock(pos, blockstate.setValue(HATCH, Integer.valueOf(i + 1)), 2);
			} else {
	            worldIn.removeBlock(pos, false);
            	worldIn.levelEvent(2001, pos, Block.getId(blockstate));
                CactoidEntity cactoidentity = FUREntityRegistry.CACTOID.get().spawn(worldIn, (ItemStack)null, null, pos, MobSpawnType.BREEDING, false, false);
                cactoidentity.setAge(-24000);
                cactoidentity.moveTo((double)pos.getX() + 0.3D, (double)pos.getY(), (double)pos.getZ() + 0.3D, 0.0F, 0.0F);
                cactoidentity.playSound(SoundEvents.BEE_POLLINATE, 1.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.8F));

                if(worldIn.getBiome(pos).containsTag(BiomeTags.IS_NETHER) || worldIn.getBlockState(pos.below()).getBlock().equals(Blocks.SOUL_SAND)) {
                	cactoidentity.setSkin(3);
                }
                
                worldIn.addFreshEntity(cactoidentity);
			}
		}
	}
	
	public static boolean onSand(ServerLevel level, BlockPos pos) {
		return isSand(level, pos.below());
	}

	public static boolean isSand(ServerLevel level, BlockPos pos) {
		return level.getBlockState(pos).is(BlockTags.SAND) || level.getBlockState(pos).getBlock().equals(Blocks.SOUL_SAND);
	}
	
	@Override
	public VoxelShape getShape(BlockState blockstate, BlockGetter getter, BlockPos pos, CollisionContext context) {
		Vec3 vector3d = blockstate.getOffset(getter, pos);
		return SPROUT_AABB.move(vector3d.x(), vector3d.y(), vector3d.z());
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(HATCH);
	}
}
