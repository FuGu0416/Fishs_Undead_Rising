package com.Fishmod.mod_LavaCow.block;

import java.util.Random;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.init.FURBlockRegistry;
import com.Fishmod.mod_LavaCow.init.FURWorldRegistry;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.block.MushroomBlock;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FURShroomBlock extends MushroomBlock implements IGrowable {
	public static final IntegerProperty AGE = BlockStateProperties.AGE_1;

	public FURShroomBlock(AbstractBlock.Properties p_i48363_1_, String RegistryName)
    {
		super(p_i48363_1_);
		this.setRegistryName(RegistryName);
		this.registerDefaultState(this.stateDefinition.any().setValue(this.getAgeProperty(), Integer.valueOf(0)));
    }
	
	@Override
	public void onPlace(BlockState stateIn, World worldIn, BlockPos pos, BlockState p_220082_4_, boolean p_220082_5_) {
		worldIn.setBlock(pos, this.getStateForAge(this.RANDOM.nextInt(2)), 2);
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
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
		if(this.getBlock().equals(FURBlockRegistry.GLOWSHROOM)) {
			if(rand.nextInt(100) < FURConfig.pSpreadRate_Glowshroom.get())
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
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (this == FURBlockRegistry.GLOWSHROOM && rand.nextInt(16) == 0) {
			spawnParticles(worldIn, pos);
		}
		
		super.animateTick(stateIn, worldIn, pos, rand);
	}
		
	static void spawnParticles(World worldIn, BlockPos pos) {
		Random random = worldIn.random;

		for(Direction direction : Direction.values()) {
			BlockPos blockpos = pos.relative(direction);
		    if (!worldIn.getBlockState(blockpos).isSolidRender(worldIn, blockpos)) {
		    	Direction.Axis direction$axis = direction.getAxis();
		        double d1 = direction$axis == Direction.Axis.X ? 0.5D + 0.5625D * (double)direction.getStepX() : (double)random.nextFloat();
		        double d2 = direction$axis == Direction.Axis.Y ? 0.5D + 0.5625D * (double)direction.getStepY() : (double)random.nextFloat();
		        double d3 = direction$axis == Direction.Axis.Z ? 0.5D + 0.5625D * (double)direction.getStepZ() : (double)random.nextFloat();
		        worldIn.addParticle(new RedstoneParticleData(0.0F, 0.98F, 0.93F, 1.0F), (double)pos.getX() + d1, (double)pos.getY() + d2, (double)pos.getZ() + d3, 0.0D, 0.0D, 0.0D);
		    }
		}
	}
    
    @Override
    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.below();
        BlockState blockstate = worldIn.getBlockState(blockpos);	
        if (blockstate.is(BlockTags.MUSHROOM_GROW_BLOCK)) {
           return true;
        } else {
           return ((/*SpawnUtil.isDay(worldIn) &&*/ this == FURBlockRegistry.GLOWSHROOM) || worldIn.getRawBrightness(pos, 0) < 13) && blockstate.canSustainPlant(worldIn, blockpos, net.minecraft.util.Direction.UP, this);
        }
    }
     
    public boolean growMushroom(ServerWorld worldIn, BlockPos pos, BlockState state, Random rand) {
        worldIn.removeBlock(pos, false);
        ConfiguredFeature<?, ?> configuredfeature;
        if (this == FURBlockRegistry.GLOWSHROOM) {
           configuredfeature = FURWorldRegistry.HUGE_GLOWSHROOM_CF;
        } else {
           if (this != Blocks.RED_MUSHROOM) {
              worldIn.setBlock(pos, state, 3);
              return false;
           }

           configuredfeature = Features.HUGE_RED_MUSHROOM;
        }

        if (configuredfeature.place(worldIn, worldIn.getChunkSource().getGenerator(), rand, pos)) {
           return true;
        } else {
           worldIn.setBlock(pos, state, 3);
           return false;
        }
    }
       
    @Override
	public boolean isValidBonemealTarget(IBlockReader blockreader, BlockPos pos, BlockState state, boolean isClient) {
        return this == FURBlockRegistry.GLOWSHROOM;
    }
    
    @Override
    public boolean isBonemealSuccess(World worldIn, Random rand, BlockPos pos, BlockState state) {
    	return this == FURBlockRegistry.GLOWSHROOM && (double)rand.nextFloat() < 0.4D;
    }
    
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(AGE);
    }
}
