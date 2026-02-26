package com.Fishmod.fur.block;

import org.joml.Vector3f;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.init.FURBlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.resources.ResourceLocation;
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
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState p_220082_4_, boolean p_220082_5_) {
		level.setBlock(pos, this.getStateForAge(level.random.nextInt(2)), 3);
	}
	
    protected int getAge(BlockState state) {
		return state.getValue(this.getAgeProperty());
	}

    public BlockState getStateForAge(int p_185528_1_) {
        return this.defaultBlockState().setValue(this.getAgeProperty(), Integer.valueOf(p_185528_1_));
    }
	
    public IntegerProperty getAgeProperty() {
        return AGE;
    }
	
	/**
	    * Called periodically clientside on blocks near the player to show effects (like furnace fire particles). Note that
	    * this method is unrelated to {@link randomTick} and {@link #needsRandomTick}, and will always be called regardless
	    * of whether the block can receive random update ticks
	    */
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level worldIn, BlockPos pos, RandomSource rand) {
		if (this == FURBlockRegistry.GLOWSHROOM.get() && rand.nextInt(16) == 0) {
			spawnParticles(worldIn, pos);
		}
		
		super.animateTick(state, worldIn, pos, rand);
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
    
    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        var feature = level.registryAccess()
            .registryOrThrow(Registries.CONFIGURED_FEATURE)
            .get(new ResourceLocation(mod_LavaCow.MODID, "huge_glowshroom"));

        level.removeBlock(pos, false);
        
        if (!feature.place(level, level.getChunkSource().getGenerator(), random, pos)) {
        	level.setBlock(pos, state, 3);
        }
    }
       
    @Override
	public boolean isValidBonemealTarget(LevelReader blockreader, BlockPos pos, BlockState state, boolean isClient) {
        return this == FURBlockRegistry.GLOWSHROOM.get();
    }
    
    @Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(AGE);
    }
}
