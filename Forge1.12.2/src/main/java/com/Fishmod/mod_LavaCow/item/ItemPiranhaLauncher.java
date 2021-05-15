package com.Fishmod.mod_LavaCow.item;

import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityEnchantableFireBall;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemPiranhaLauncher extends ItemBow{
		
		private String ammo = null;
		private EnumRarity Rarity;
		private String Tooltip = null;
		private final Class <? extends Entity > shot;
	
		public ItemPiranhaLauncher(String registryName, String AmmoIn, Class <? extends Entity > shotIn, EnumRarity rarity) {
	        this.maxStackSize = 1;
	        this.ammo = AmmoIn;
	        this.Rarity = rarity;
	        this.shot = shotIn;
	        this.Tooltip = "tootip." + mod_LavaCow.MODID + "." + registryName;
	        this.setMaxDamage(384);
	        this.setCreativeTab(CreativeTabs.COMBAT);
	        setUnlocalizedName(mod_LavaCow.MODID + "." + registryName);
	        setRegistryName(registryName);
		}
		
	    /**
	     * Return an item rarity from EnumRarity
	     */
	    public EnumRarity getRarity(ItemStack stack)
	    {
	        return this.Rarity;
	    }
		
	    protected ItemStack findAmmo(EntityPlayer player)
	    {
	        if (this.isArrow(player.getHeldItem(EnumHand.OFF_HAND)))
	        {
	            return player.getHeldItem(EnumHand.OFF_HAND);
	        }
	        else if (this.isArrow(player.getHeldItem(EnumHand.MAIN_HAND)))
	        {
	            return player.getHeldItem(EnumHand.MAIN_HAND);
	        }
	        else
	        {
	            for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
	            {
	                ItemStack itemstack = player.inventory.getStackInSlot(i);

	                if (this.isArrow(itemstack))
	                {
	                    return itemstack;
	                }
	            }

	            return ItemStack.EMPTY;
	        }
	    }
	
		@Override
		protected boolean isArrow(ItemStack stack) {
			//return stack.getItem() == FishItems.ZOMBIEPIRANHA_ITEM;
			return stack.getItem().getUnlocalizedName().equalsIgnoreCase(this.ammo);
		}
		
		@Override
		public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft){return;}
		
		/**
		* Called when the player stops using an Item (stops holding the right mouse button).
		*/
		public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
			ItemStack stack = playerIn.getHeldItem(handIn);
		         boolean flag = playerIn.isCreative() || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
		         ItemStack itemstack = this.findAmmo(playerIn);
		         //System.out.println("\"" + stack.getItem().getUnlocalizedName() + "\" \"" + this.ammo + "\"");
		         if (!itemstack.isEmpty() || flag) {
		             if (itemstack.isEmpty()) {
		                //itemstack = new ItemStack(FishItems.ZOMBIEPIRANHA_ITEM);
		            	 itemstack = new ItemStack(Item.getByNameOrId(this.ammo));
		             }
		         }
		         else return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
		         
		         boolean flag1 = playerIn.isCreative() || (this.isArrow(itemstack) && EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0);
		         /*if(!flag1)*/
		         if (!worldIn.isRemote) {
					 Vec3d lookVec = playerIn.getLookVec();
					 
					 Entity entityammo = EntityList.newEntity(this.shot, worldIn);
					 ((EntityFireball)entityammo).shootingEntity = playerIn;
					 
					 entityammo.addVelocity(lookVec.x * 2.5D, lookVec.y * 2.5D, lookVec.z * 2.5D);
					 
					 int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
					 if (j > 0) {
						 ((EntityEnchantableFireBall) entityammo).setDamage(((EntityEnchantableFireBall) entityammo).getDamage() * (1.0F + (j + 1) * 0.25F));
					 }
					  
					 int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
					 if (k > 0) {
						 ((EntityEnchantableFireBall) entityammo).setKnockbackStrength(k);
					 }
					  
					 if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0) {
						 ((EntityEnchantableFireBall) entityammo).setFlame(true);
					  }
					  entityammo.setPosition(playerIn.posX + lookVec.x * 1.0D, playerIn.posY + (double)(playerIn.height), playerIn.posZ + lookVec.z * 1.0D);
					  worldIn.spawnEntity(entityammo);
					  stack.damageItem(1, playerIn);
					  worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F));
					  if (!flag1 && !playerIn.isCreative()) {
					     itemstack.shrink(1);
					     if (itemstack.isEmpty()) {
					        playerIn.inventory.deleteStack(itemstack);
					     }
					  }
					  playerIn.getCooldownTracker().setCooldown(this, 20 - (j * 2));
					  
					  return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
		         }
			
			return new ActionResult<>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
		}
		
		/**
		 * Gets the velocity of the arrow entity from the bow's charge
		 */
		public static float getArrowVelocity(int charge) {
		      return 0.0F;
		}
	
	   /**
	    * How long it takes to use or consume an item
	    */
	   public int getUseDuration(ItemStack stack) {
	      return 320;
	   }
	   
	   /**
	    * returns the action that specifies what animation to play when the items is being used
	    */
	   public EnumAction getUseAction(ItemStack stack) {
	      return EnumAction.BOW;
	   }
	   
		@Override
		@SideOnly(Side.CLIENT)
		public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flag) {
			list.add(TextFormatting.YELLOW/* + "" + TextFormatting.ITALIC*/ + I18n.format(this.Tooltip));
		}
}
