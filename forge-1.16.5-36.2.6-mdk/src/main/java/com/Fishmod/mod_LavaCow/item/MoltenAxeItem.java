package com.Fishmod.mod_LavaCow.item;

import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.google.common.collect.Sets;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MoltenAxeItem extends AxeItem{
	public static final Set<Material> DIGGABLE_MATERIALS = Sets.newHashSet(Material.WOOD, Material.NETHER_WOOD, Material.PLANT, Material.REPLACEABLE_PLANT, Material.BAMBOO, Material.VEGETABLE);
	
	public MoltenAxeItem() {
		super(ItemTier.DIAMOND, 5.0F, -3.0F, (new Item.Properties()).tab(mod_LavaCow.TAB).fireResistant());
	}
	
	@Override
	public void onCraftedBy(ItemStack stack, World worldIn, PlayerEntity playerIn) {
		stack.enchant(Enchantments.FIRE_ASPECT, 2);
	}

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (!stack.isEnchanted())
			stack.enchant(Enchantments.FIRE_ASPECT, 2);
	}
		
	/**
	    * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
	    */
	@Override
	public boolean mineBlock(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
		Material material = state.getMaterial();
		
		if(DIGGABLE_MATERIALS.contains(material))
		{
			System.out.println(material.toString());
			worldIn.playSound((PlayerEntity)null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.FIRE_AMBIENT, entityLiving.getSoundSource(), 1.0F, 1.0F);
			double j = 1.2D;
			for(int i = 0; i < 16; i++)
			{
				((ServerWorld) worldIn).sendParticles(ParticleTypes.SMOKE, pos.getX() + 0.5D + Item.random.nextDouble() * j - j/2, pos.getY() + 0.5D + Item.random.nextDouble() * j - j/2, pos.getZ() + 0.5D + Item.random.nextDouble() * j - j/2, 1, 0.0D, 0.0D, 0.0D, 0.0D);
				((ServerWorld) worldIn).sendParticles(ParticleTypes.FLAME, pos.getX() + 0.5D + Item.random.nextDouble() * j - j/2, pos.getY() + 0.5D + Item.random.nextDouble() * j - j/2, pos.getZ() + 0.5D + Item.random.nextDouble() * j - j/2, 1, 0.0D, 0.0D, 0.0D, 0.0D);	
			}
		}
				
		return super.mineBlock(stack, worldIn, state, pos, entityLiving);
	}
	
	@Override
	public boolean isValidRepairItem(ItemStack par1ItemStack, ItemStack par2ItemStack) {
		return par2ItemStack.getItem() == Items.GUNPOWDER || super.isValidRepairItem(par1ItemStack, par2ItemStack);
	}
	
	@Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    	tooltip.add(new TranslationTextComponent("tootip." + this.getRegistryName()).withStyle(TextFormatting.YELLOW));
	}	
}
