package com.Fishmod.mod_LavaCow.blocks;

import java.util.Random;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.init.Modblocks;
import com.Fishmod.mod_LavaCow.worldgen.WorldGenCemeterySmall;
import com.Fishmod.mod_LavaCow.worldgen.WorldGenLargeGlowShroom;

import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockMushroom;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid=mod_LavaCow.MODID)
public class BlockGlowShroom extends BlockMushroom{
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 1);
	private boolean isGlowshroom;
	
	/*static Block glowshroom;
	
	public static void init() {
		glowshroom = new BlockBasic("glowshroom", Material.PLANTS).setCreativeTab(CreativeTabs.DECORATIONS).setLightLevel(1.0f);
	}*/
	
	@Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {AGE});
    }
	
    protected int getAge(IBlockState state)
    {
        return ((Integer)state.getValue(this.getAgeProperty())).intValue();
    }

    public IBlockState withAge(int age)
    {
        return this.getDefaultState().withProperty(this.getAgeProperty(), Integer.valueOf(age));
    }
	
    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        return this.withAge(meta);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return this.getAge(state);
    }
	
	public BlockGlowShroom(boolean isGlowshroom)
    {
		super();
		this.blockSoundType = SoundType.PLANT;
		this.isGlowshroom = isGlowshroom;
		this.setLightLevel(isGlowshroom? 1.0F : 0.0F);
		this.setCreativeTab(mod_LavaCow.TAB_ITEMS);
    }
	
    protected PropertyInteger getAgeProperty()
    {
        return AGE;
    }
		
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if(rand.nextInt(100) < Modconfig.pSpreadRate_Glowshroom)
			super.updateTick(worldIn, pos, state, rand);
	}
	
	/**
	    * Called periodically clientside on blocks near the player to show effects (like furnace fire particles). Note that
	    * this method is unrelated to {@link randomTick} and {@link #needsRandomTick}, and will always be called regardless
	    * of whether the block can receive random update ticks
	    */
    @SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (this.isGlowshroom && rand.nextInt(16) == 0) {
			spawnParticles(worldIn, pos);
		}
		
		super.randomDisplayTick(stateIn, worldIn, pos, rand);
	}
	
	public static void spawnParticles(World worldIn, BlockPos pos) {
	      //double d0 = 0.5625D;
	      Random random = worldIn.rand;

	      for(EnumFacing enumfacing : EnumFacing.values()) {
	         BlockPos blockpos = pos.offset(enumfacing);
	         if (!worldIn.getBlockState(blockpos).isOpaqueCube() && worldIn.isRemote) {
	            EnumFacing.Axis enumfacing$axis = enumfacing.getAxis();
	            double d1 = enumfacing$axis == EnumFacing.Axis.X ? 0.5D + 0.5625D * (double)enumfacing.getFrontOffsetX() : (double)random.nextFloat();
	            double d2 = enumfacing$axis == EnumFacing.Axis.Y ? 0.5D + 0.5625D * (double)enumfacing.getFrontOffsetY() : (double)random.nextFloat();
	            double d3 = enumfacing$axis == EnumFacing.Axis.Z ? 0.5D + 0.5625D * (double)enumfacing.getFrontOffsetZ() : (double)random.nextFloat();
	            mod_LavaCow.PROXY.spawnCustomParticle("spore", worldIn, (double)pos.getX() + d1, (double)pos.getY() + d2, (double)pos.getZ() + d3, 0.0D, 0.0D, 0.0D, 0.0F, 0.98F, 0.93F);
	         }
	      }

	   }
    
    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
    {
        if (pos.getY() >= 0 && pos.getY() < 256)
        {
            IBlockState iblockstate = worldIn.getBlockState(pos.down());

            if (iblockstate.getBlock() == Blocks.MYCELIUM)
            {
                return true;
            }
            else if (iblockstate.getBlock() == Blocks.DIRT && iblockstate.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.PODZOL)
            {
                return true;
            }
            else
            {
            	return ((SpawnUtil.isDay(worldIn) && this.isGlowshroom) || worldIn.getLight(pos) < 13) && iblockstate.getBlock().canSustainPlant(iblockstate, worldIn, pos.down(), net.minecraft.util.EnumFacing.UP, this);
            }
        }
        else
        {
            return false;
        }
    }
    
    public boolean generateBigMushroom(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        worldIn.setBlockToAir(pos);
        WorldGenerator worldgenerator = null;
        
        if(this.isGlowshroom)
        	worldgenerator = new WorldGenLargeGlowShroom(Modblocks.GLOWSHROOM_BLOCK_CAP, Modblocks.GLOWSHROOM_BLOCK_STEM);
        
        //worldgenerator = new WorldGenCemeterySmall();

        if (worldgenerator != null && worldgenerator.generate(worldIn, rand, pos))
        {
            return true;
        }
        else
        {
            worldIn.setBlockState(pos, state, 3);
            return false;
        }
    }
    
    @Override
    public net.minecraftforge.common.EnumPlantType getPlantType(net.minecraft.world.IBlockAccess world, BlockPos pos)
    {
        return net.minecraftforge.common.EnumPlantType.Cave;
    }
    
    @Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
    {
        return this.isGlowshroom;
    	//return false;
    }
    
    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
    	return this.isGlowshroom && (double)rand.nextFloat() < 0.4D;
    	//return false;
    }
}
