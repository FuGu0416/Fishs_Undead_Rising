package com.Fishmod.mod_LavaCow.blocks;

import com.Fishmod.mod_LavaCow.mod_LavaCow;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockMoltenAlloy extends Block {
    
	public BlockMoltenAlloy() {
		super(Material.IRON, MapColor.NETHERRACK);
		this.setHardness(5.0F);
		this.setResistance(10.0F);
		this.setCreativeTab(mod_LavaCow.TAB_ITEMS);
		this.setSoundType(SoundType.METAL);
	}
	
	@Override
    public void onEntityWalk(World world, BlockPos pos, Entity entity) {
        if (!entity.isImmuneToFire() && entity instanceof EntityLivingBase && !EnchantmentHelper.hasFrostWalkerEnchantment((EntityLivingBase)entity)) {
            entity.attackEntityFrom(DamageSource.HOT_FLOOR, 1.0F);
        }

        super.onEntityWalk(world, pos, entity);
    }
	
	@Override
    public boolean isFireSource(World world, BlockPos pos, EnumFacing side) {
        return true;
    }
}
