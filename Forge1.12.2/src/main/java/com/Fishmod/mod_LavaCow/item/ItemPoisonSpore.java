package com.Fishmod.mod_LavaCow.item;

import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.blocks.BlockScarecrowHead;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityScarecrow;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.init.Modblocks;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemPoisonSpore extends ItemFishCustom{
	
	private EnumRarity Rarity;
	
	public ItemPoisonSpore(String registryName, CreativeTabs tab, EnumRarity rarity, boolean hasTooltip) {
    	super(registryName, null, tab, hasTooltip);
    	this.Rarity = rarity;
        this.setMaxStackSize(64);
    }
	
    /**
     * Returns true if this item has an enchantment glint. By default, this returns
     * <code>stack.isItemEnchanted()</code>, but other items can override it (for instance, written books always return
     * true).
     *  
     * Note that if you override this method, you generally want to also call the super version (on {@link Item}) to get
     * the glint for enchanted items. Of course, that is unnecessary if the overwritten version always returns true.
     */
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return false;
    }
    
    /**
     * Checks isDamagable and if it cannot be stacked
     */
    public boolean isEnchantable(ItemStack stack)
    {
        return false;
    }
    
    /**
     * Return an item rarity from EnumRarity
     */
    public EnumRarity getRarity(ItemStack stack)
    {
        return this.Rarity;
    }
    
	/**
	* Called when this item is used when targetting a Block
	*/
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(player.getHeldItem(hand).getItem() == FishItems.UNDYINGHEART) {
			
			if((worldIn.getBlockState(pos.up()).getBlock().equals(Modblocks.SCARECROWHEAD_COMMON) || worldIn.getBlockState(pos.up()).getBlock().equals(Modblocks.SCARECROWHEAD_STRAW) || worldIn.getBlockState(pos.up()).getBlock().equals(Modblocks.SCARECROWHEAD_PLAGUE)) && worldIn.getBlockState(pos.down()).getBlock().equals(Blocks.HAY_BLOCK) && worldIn.getBlockState(pos).getBlock().equals(Blocks.HAY_BLOCK)) {
				BlockScarecrowHead block = ((BlockScarecrowHead)worldIn.getBlockState(pos.up()).getBlock());
				int type = block.type;
				
				if(!player.isCreative())
					player.getHeldItem(hand).shrink(1);
				
				worldIn.destroyBlock(pos, false);
				worldIn.destroyBlock(pos.down(), false);
				worldIn.destroyBlock(pos.up(), false);
				
				if(!worldIn.isRemote) {
		        	EntityScarecrow entitywolf = new EntityScarecrow(worldIn);
		        	
		        	entitywolf.setTamedBy(player);
		        	entitywolf.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0.0F, 0.0F);
		        	entitywolf.setSkin(type);
		        	worldIn.spawnEntity(entitywolf);
		        	
		            for (EntityPlayerMP entityplayermp : worldIn.getEntitiesWithinAABB(EntityPlayerMP.class, entitywolf.getEntityBoundingBox().grow(5.0D)))
		            {
		                CriteriaTriggers.SUMMONED_ENTITY.trigger(entityplayermp, entitywolf);
		            }
	        	}
			}
			
			return EnumActionResult.SUCCESS;
		}
		
		return super.onItemUse(player, worldIn, pos, hand, facing, hitZ, hitZ, hitZ);
	}
    
    public static NBTTagList getEnchantments(ItemStack p_92110_0_)
    {
        NBTTagCompound nbttagcompound = p_92110_0_.getTagCompound();
        return nbttagcompound != null ? nbttagcompound.getTagList("StoredEnchantments", 10) : new NBTTagList();
    }
    
    /**
     * allows items to add custom lines of information to the mouseover description
     */
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        NBTTagList nbttaglist = getEnchantments(stack);

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound.getShort("id");
            Enchantment enchantment = Enchantment.getEnchantmentByID(j);

            if (enchantment != null)
            {
                tooltip.add(enchantment.getTranslatedName(nbttagcompound.getShort("lvl")));
            }
        }
    }

    /**
     * Adds an stored enchantment to an enchanted book ItemStack
     */
    public static void addEnchantment(ItemStack p_92115_0_, EnchantmentData stack)
    {
        NBTTagList nbttaglist = getEnchantments(p_92115_0_);
        boolean flag = true;

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);

            if (Enchantment.getEnchantmentByID(nbttagcompound.getShort("id")) == stack.enchantment)
            {
                if (nbttagcompound.getShort("lvl") < stack.enchantmentLevel)
                {
                    nbttagcompound.setShort("lvl", (short)stack.enchantmentLevel);
                }

                flag = false;
                break;
            }
        }

        if (flag)
        {
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
            nbttagcompound1.setShort("id", (short)Enchantment.getEnchantmentID(stack.enchantment));
            nbttagcompound1.setShort("lvl", (short)stack.enchantmentLevel);
            nbttaglist.appendTag(nbttagcompound1);
        }

        if (!p_92115_0_.hasTagCompound())
        {
            p_92115_0_.setTagCompound(new NBTTagCompound());
        }

        p_92115_0_.getTagCompound().setTag("StoredEnchantments", nbttaglist);
    }
	
    /**
     * Returns the ItemStack of an enchanted version of this item.
     */
	public static ItemStack getEnchantedItemStack(EnchantmentData p_92111_0_)
    {
        ItemStack itemstack = new ItemStack(FishItems.POISONSPORE);
        addEnchantment(itemstack, p_92111_0_);
        return itemstack;
    }

}
