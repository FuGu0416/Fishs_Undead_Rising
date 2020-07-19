package com.Fishmod.mod_LavaCow.item;

import java.util.List;
import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMoltenAxe extends ItemAxe{

	public ItemMoltenAxe(String registryName) {
		super(ToolMaterial.DIAMOND, 5.0F, -3.0F);
        setUnlocalizedName(mod_LavaCow.MODID + "." + registryName);
        setRegistryName(registryName);
        this.setCreativeTab(mod_LavaCow.TAB_ITEMS);
	}
	
	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		stack.addEnchantment(Enchantments.FIRE_ASPECT, 2);
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (!stack.isItemEnchanted())
			stack.addEnchantment(Enchantments.FIRE_ASPECT, 2);
	}
		
	/**
	    * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
	    */
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
		Material material = state.getMaterial();
		//List<ItemStack> ores = OreDictionary.getOres("logWood");
		//List<ItemStack> drops = new ArrayList();
		
		if(material == Material.WOOD)
		{
			entityLiving.playSound(SoundEvents.BLOCK_FIRE_AMBIENT, 1.0F, 1.0F);
			//worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
			double j = 1.2D;
			for(int i = 0; i < 16; i++)
			{
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.5D + Item.itemRand.nextDouble() * j - j/2, pos.getY() + 0.5D + Item.itemRand.nextDouble() * j - j/2, pos.getZ() + 0.5D + Item.itemRand.nextDouble() * j - j/2, 0.0D, 0.0D, 0.0D);
				worldIn.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5D + Item.itemRand.nextDouble() * j - j/2, pos.getY() + 0.5D + Item.itemRand.nextDouble() * j - j/2, pos.getZ() + 0.5D + Item.itemRand.nextDouble() * j - j/2, 0.0D, 0.0D, 0.0D);	
			}
			//worldIn.createExplosion(entityLiving, pos.getX(), pos.getY(), pos.getZ(), 1.0f, true);
		}
				
		return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
	}
	
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{
		return (par2ItemStack.getItem() == Items.GUNPOWDER && par2ItemStack.getItem() != Items.DIAMOND) ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flag) {
			list.add(TextFormatting.YELLOW + I18n.format("tootip.mod_lavacow.moltenaxe"));
	}
}
