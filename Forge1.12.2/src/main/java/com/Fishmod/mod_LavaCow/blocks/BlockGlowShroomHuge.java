package com.Fishmod.mod_LavaCow.blocks;

import java.util.Random;

import com.Fishmod.mod_LavaCow.mod_LavaCow;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHugeMushroom;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockGlowShroomHuge extends BlockHugeMushroom{

	private boolean isCap = false;
	
	public BlockGlowShroomHuge(MapColor colour, Block drop, boolean isCap) {
		super(Material.WOOD, colour, drop);
		setHardness(0.2F);
		this.setCreativeTab(mod_LavaCow.TAB_ITEMS);
		this.setSoundType(SoundType.WOOD);
		this.isCap = isCap;
		if(this.isCap) {
			this.setLightLevel(1.0f);
			this.setTickRandomly(true);
		}
	}
	
	/**
	    * Called periodically clientside on blocks near the player to show effects (like furnace fire particles). Note that
	    * this method is unrelated to {@link randomTick} and {@link #needsRandomTick}, and will always be called regardless
	    * of whether the block can receive random update ticks
	    */
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (this.isCap && rand.nextInt(16) == 0) {
			BlockGlowShroom.spawnParticles(worldIn, pos);
		}
		
		super.randomDisplayTick(stateIn, worldIn, pos, rand);
	}
}
