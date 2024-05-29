package com.Fishmod.mod_LavaCow.item;

import java.util.List;
import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.init.FishItems;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMoltenAxe extends ItemAxe {

	public ItemMoltenAxe(String registryName) {
		super(FishItems.TOOL_MOLTEN, 9.0F, -3.0F);
        setUnlocalizedName(mod_LavaCow.MODID + "." + registryName);
        setRegistryName(registryName);
        this.setCreativeTab(mod_LavaCow.TAB_ITEMS);
	}
	
	@Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		super.hitEntity(stack, target, attacker);
		
		// Stacks with Fire Aspect
		target.setFire(8 + 4 * EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, attacker.getHeldItem(attacker.getActiveHand())));
        return true;
    }
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
		if(state.getBlock().canHarvestBlock(worldIn, pos, (EntityPlayer)entityLiving)) {
			entityLiving.playSound(SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, 1.0F, 1.0F);
			double j = 1.2D;
			
			for(int i = 0; i < 16; i++) {
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.5D + Item.itemRand.nextDouble() * j - j/2, pos.getY() + 0.5D + Item.itemRand.nextDouble() * j - j/2, pos.getZ() + 0.5D + Item.itemRand.nextDouble() * j - j/2, 0.0D, 0.0D, 0.0D);
				worldIn.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5D + Item.itemRand.nextDouble() * j - j/2, pos.getY() + 0.5D + Item.itemRand.nextDouble() * j - j/2, pos.getZ() + 0.5D + Item.itemRand.nextDouble() * j - j/2, 0.0D, 0.0D, 0.0D);	
			}
		}
				
		return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
	}
	
    /**
     * Return an item rarity from EnumRarity
     */
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }
	
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack) {
		return (par2ItemStack.getItem() == FishItems.MOLTENBEEF && par2ItemStack.getItem() != Items.DIAMOND) ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flag) {
			list.add(TextFormatting.YELLOW + I18n.format("tootip.mod_lavacow.moltenaxe"));
	}
}
