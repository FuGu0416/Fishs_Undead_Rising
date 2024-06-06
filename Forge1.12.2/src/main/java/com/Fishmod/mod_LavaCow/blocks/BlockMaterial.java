package com.Fishmod.mod_LavaCow.blocks;

import com.Fishmod.mod_LavaCow.mod_LavaCow;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockMaterial extends Block {
    
	public BlockMaterial(Material material, MapColor mapColor, SoundType soundType) {
		super(material, mapColor);
		this.setHardness(3.0F);
		this.setResistance(10.0F);
		this.setCreativeTab(mod_LavaCow.TAB_ITEMS);
		this.setSoundType(soundType);
	}
}
