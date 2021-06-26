package com.Fishmod.mod_LavaCow.item;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityRaven;
import com.Fishmod.mod_LavaCow.init.FishItems;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRavenWhistle extends ItemFishCustom {

	private UUID OrderEntityID;
	
	public ItemRavenWhistle(String registryName) {
		super(registryName, null, mod_LavaCow.TAB_ITEMS, true);
        this.maxStackSize = 1;
	}
	
    @Override
    public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
    	if (!itemStack.hasTagCompound()) {
	    	NBTTagCompound nbttagcompound = new NBTTagCompound();
	        itemStack.setTagCompound(nbttagcompound);
    	}
    }
    
    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int f, boolean f1) {
        if (!stack.hasTagCompound()) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();         
        	stack.setTagCompound(nbttagcompound);
        }
    }
    
    /**
     * Called when the equipped item is right clicked.
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        Vec3d vec3d = playerIn.getPositionEyes(1.0F);
        Vec3d vec3d1 = playerIn.getLook(1.0F);
        Vec3d vec3d2 = vec3d.addVector(vec3d1.x * 30.0D, vec3d1.y * 30.0D, vec3d1.z * 30.0D);
        BlockPos pos = worldIn.rayTraceBlocks(vec3d, vec3d2, false, false, true).getBlockPos();
    	
        if(this.OrderEntityID != null) { 	
        	Entity entity = SpawnUtil.getEntityByUniqueId(this.OrderEntityID, worldIn);
        	if(playerIn.getDistance(entity) < 16.0F) {
	        	((EntityRaven) entity).setTargetLocation(pos.getX(), pos.getY(), pos.getZ());
				playerIn.playSound(FishItems.ENTITY_RAVEN_CALL, 1.0F, 1.0F);
				playerIn.getCooldownTracker().setCooldown(this, 30);
				playerIn.getHeldItem(handIn).setAnimationsToGo(5);
				 
				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        	} else if(playerIn.isServerWorld()) {
        		playerIn.sendStatusMessage(new TextComponentTranslation(entity.getName()).appendSibling(new TextComponentTranslation("command.mod_lavacow.whistle_err")), true);
        	}
        }
        
    	return super.onItemRightClick(worldIn, playerIn, handIn);
    }
	
    /**
     * Returns true if the item can be used on the given entity, e.g. shears on sheep.
     */
    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand)
    {
        if(target instanceof EntityRaven && ((EntityRaven) target).getOwnerId() != null && ((EntityRaven) target).getOwnerId().equals(playerIn.getUniqueID())) {
        	this.OrderEntityID = target.getUniqueID();
        	playerIn.getHeldItem(hand).getTagCompound().setString("OrderName", target.getName());
        	playerIn.getHeldItem(hand).getTagCompound().setInteger("OrderID", target.getEntityId());

        	if(playerIn.isServerWorld()) {
        		playerIn.sendStatusMessage(new TextComponentTranslation(target.getName()).appendSibling(new TextComponentTranslation("command.mod_lavacow.whistle")), true);
        	}
        	
        	return true;
        }
    	
    	return false;
    }
    
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flag) {
		super.addInformation(stack, worldIn, list, flag);
		
		if(stack.hasTagCompound()) {
			list.add(TextFormatting.DARK_GRAY + stack.getTagCompound().getString("OrderName"));
		}
			
	}

}
