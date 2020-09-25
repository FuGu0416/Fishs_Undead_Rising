package com.Fishmod.mod_LavaCow.item;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityGhostBomb;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityHolyGrenade;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntitySonicBomb;
import com.Fishmod.mod_LavaCow.init.FishItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemHolyGrenade extends ItemFishCustom{

	public ItemHolyGrenade(String registryName) {
		super(registryName, null, mod_LavaCow.TAB_ITEMS, true);
	}
	
    /**
     * Called when the equipped item is right clicked.
     */
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (!playerIn.capabilities.isCreativeMode)
        {
            itemstack.shrink(1);
        }

        worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!worldIn.isRemote)
        {
        	if(itemstack.getItem().equals(FishItems.HOLY_GRENADE)) {
	        	EntityHolyGrenade entitysnowball = new EntityHolyGrenade(worldIn, playerIn);
	            entitysnowball.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, -20.0F, 0.5F, 1.0F);
	            worldIn.spawnEntity(entitysnowball);
        	}
        	else if(itemstack.getItem().equals(FishItems.GHOSTBOMB)) {
	        	EntityGhostBomb entitysnowball = new EntityGhostBomb(worldIn, playerIn);
	            entitysnowball.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, -20.0F, 0.5F, 1.0F);
	            worldIn.spawnEntity(entitysnowball);
        	}
        	else if(itemstack.getItem().equals(FishItems.SONICBOMB)) {
	        	EntitySonicBomb entitysnowball = new EntitySonicBomb(worldIn, playerIn);
	            entitysnowball.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, -20.0F, 0.5F, 1.0F);
	            worldIn.spawnEntity(entitysnowball);
        	}
        }

        playerIn.addStat(StatList.getObjectUseStats(this));
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
    }
}
