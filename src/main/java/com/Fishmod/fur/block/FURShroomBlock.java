package com.Fishmod.fur.block;

import org.joml.Vector3f;

import com.Fishmod.fur.init.FURBlockRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FURShroomBlock extends MushroomBlock implements BonemealableBlock {
	public static final IntegerProperty AGE = BlockStateProperties.AGE_1;

	public FURShroomBlock(Properties p_i48363_1_) {
		super(p_i48363_1_, TreeFeatures.HUGE_BROWN_MUSHROOM);
		this.registerDefaultState(this.stateDefinition.any().setValue(this.getAgeProperty(), Integer.valueOf(0)));
    }
	
	@Override
	public void onPlace(BlockState stateIn, Level worldIn, BlockPos pos, BlockState p_220082_4_, boolean p_220082_5_) {
		worldIn.setBlock(pos, this.getStateForAge(worldIn.random.nextInt(2)), 2);
	}
	
    protected int getAge(BlockState p_185527_1_) {
		return p_185527_1_.getValue(this.getAgeProperty());
	}

    public BlockState getStateForAge(int p_185528_1_) {
        return this.defaultBlockState().setValue(this.getAgeProperty(), Integer.valueOf(p_185528_1_));
    }
	
    public IntegerProperty getAgeProperty() {
        return AGE;
    }
    
	@Override
	public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
		if(this.asBlock().equals(FURBlockRegistry.GLOWSHROOM.get())) {
			if(rand.nextInt(100) < 100/*FURConfig.pSpreadRate_Glowshroom.get()*/)
				super.randomTick(state, worldIn, pos, rand);
		} else
			super.randomTick(state, worldIn, pos, rand);
	}
	
	/**
	    * Called periodically clientside on blocks near the player to show effects (like furnace fire particles). Note that
	    * this method is unrelated to {@link randomTick} and {@link #needsRandomTick}, and will always be called regardless
	    * of whether the block can receive random update ticks
	    */
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
		if (this == FURBlockRegistry.GLOWSHROOM.get() && rand.nextInt(16) == 0) {
			spawnParticles(worldIn, pos);
		}
		
		super.animateTick(stateIn, worldIn, pos, rand);
	}
		
	static void spawnParticles(Level worldIn, BlockPos pos) {
		RandomSource random = worldIn.random;

		for(Direction direction : Direction.values()) {
			BlockPos blockpos = pos.relative(direction);
		    if (!worldIn.getBlockState(blockpos).isSolidRender(worldIn, blockpos)) {
		    	Direction.Axis direction$axis = direction.getAxis();
		        double d1 = direction$axis == Direction.Axis.X ? 0.5D + 0.5625D * (double)direction.getStepX() : (double)random.nextFloat();
		        double d2 = direction$axis == Direction.Axis.Y ? 0.5D + 0.5625D * (double)direction.getStepY() : (double)random.nextFloat();
		        double d3 = direction$axis == Direction.Axis.Z ? 0.5D + 0.5625D * (double)direction.getStepZ() : (double)random.nextFloat();
		        worldIn.addParticle(new DustParticleOptions(new Vector3f(0.0F, 0.98F, 0.93F), 1.0F), (double)pos.getX() + d1, (double)pos.getY() + d2, (double)pos.getZ() + d3, 0.0D, 0.0D, 0.0D);
		    }
		}
	}
    
    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.below();
        BlockState blockstate = worldIn.getBlockState(blockpos);	
        if (blockstate.is(BlockTags.MUSHROOM_GROW_BLOCK)) {
           return true;
        } else {
           return ((this == FURBlockRegistry.GLOWSHROOM.get()) || worldIn.getRawBrightness(pos, 0) < 13) && blockstate.canSustainPlant(worldIn, blockpos, Direction.UP, this);
        }
    }
     
    public boolean growMushroom(ServerLevel worldIn, BlockPos pos, BlockState state, RandomSource rand) {
		return false;
        /*worldIn.removeBlock(pos, false);
        ConfiguredFeature<?, ?> configuredfeature;
        if (this == FURBlockRegistry.GLOWSHROOM.get()) {
           configuredfeature = FURWorldRegistry.HUGE_GLOWSHROOM_CF;
        } else {
           if (this != Blocks.RED_MUSHROOM) {
              worldIn.setBlock(pos, state, 3);
              return false;
           }

           configuredfeature = TreeFeatures.HUGE_RED_MUSHROOM;
        }

        if (configuredfeature.place(worldIn, worldIn.getChunkSource().getGenerator(), rand, pos)) {
           return true;
        } else {
           worldIn.setBlock(pos, state, 3);
           return false;
        }*/
    }
       
    @Override
	public boolean isValidBonemealTarget(LevelReader blockreader, BlockPos pos, BlockState state, boolean isClient) {
        return this == FURBlockRegistry.GLOWSHROOM.get();
    }
    
    @Override
    public boolean isBonemealSuccess(Level worldIn, RandomSource rand, BlockPos pos, BlockState state) {
    	return this == FURBlockRegistry.GLOWSHROOM.get() && (double)rand.nextFloat() < 0.4D;
    }
    
    @Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(AGE);
    }
}
