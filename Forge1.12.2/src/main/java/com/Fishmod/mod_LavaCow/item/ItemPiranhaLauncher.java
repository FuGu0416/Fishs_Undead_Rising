package com.Fishmod.mod_LavaCow.item;

import java.util.List;
import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityCactusThorn;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityDeathCoil;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityEnchantableFireBall;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityPiranhaLauncher;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityWarSmallFireball;
import com.Fishmod.mod_LavaCow.init.FishItems;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
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

public class ItemPiranhaLauncher extends ItemBow {
		
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
		
	    @Override
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

		         if (!itemstack.isEmpty() || flag) {
		             if (itemstack.isEmpty()) {
		            	 itemstack = new ItemStack(Item.getByNameOrId(this.ammo));
		             }
		         }
		         else return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
	
		         if (!worldIn.isRemote) {
					 Vec3d lookVec = playerIn.getLookVec();
					 
			         int power_lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
			         int punch_lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
			         int flame_lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack);
			         
			         if (this.shot.equals(EntityCactusThorn.class)) {
				        	EntityCactusThorn abstractarrowentity = new EntityCactusThorn(playerIn.world, playerIn);
				        	abstractarrowentity.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 2.0F, 2.0F);                       
				        	        	
			                if (power_lvl > 0) {
			                   abstractarrowentity.setDamage(abstractarrowentity.getDamage() + (double)power_lvl * 0.1D + 0.1D);
			                }
			           
			                if (punch_lvl > 0) {
			                   abstractarrowentity.setKnockbackStrength(punch_lvl);
			                }

			                if (flame_lvl > 0) {
			                   abstractarrowentity.setFire(100);
			                }
			                
			                if (playerIn.world.rand.nextFloat() < 0.25F) {
					            stack.damageItem(1, playerIn);
			                }
			                
			                if (flag) {
			                    abstractarrowentity.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
			                }
			                
			                playerIn.world.spawnEntity(abstractarrowentity);
							if (!flag && !playerIn.isCreative()) {
								itemstack.shrink(1);
								if (itemstack.isEmpty()) {
									playerIn.inventory.deleteStack(itemstack);
								}
							}
							worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, FishItems.THORN_SHOOT, SoundCategory.PLAYERS, 0.95F, 1.0F / (playerIn.world.rand.nextFloat() * 0.4F + 0.8F));
						} else if (this.shot.equals(EntityDeathCoil.class)) {
							EntityDeathCoil entitysnowball = (EntityDeathCoil) EntityList.newEntity(this.shot, worldIn);
			        		entitysnowball.setPosition(playerIn.posX + lookVec.x * 1.0D, playerIn.posY + (double)(playerIn.height),playerIn.posZ + lookVec.z * 1.0D);
				            entitysnowball.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 0.75F, 1.0F);
				            
							if (power_lvl > 0) {
								((EntityDeathCoil) entitysnowball).setDamage(((EntityDeathCoil) entitysnowball).getDamage() * (1.0F + (power_lvl + 1) * 0.25F));
							}
							  
							if (punch_lvl > 0) {
								//((EntityDeathCoil) entitysnowball).setKnockbackStrength(punch_lvl);
							}
							  
							if (flame_lvl > 0) {
								((EntityDeathCoil) entitysnowball).setFire(100);
							}
							
				            worldIn.spawnEntity(entitysnowball);
				            stack.damageItem(1, playerIn);
							worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, FishItems.ENTITY_SKELETONKING_SPELL_TOSS, SoundCategory.PLAYERS, 1.0F, 1.0F / (playerIn.world.rand.nextFloat() * 0.4F + 1.2F));
							playerIn.getCooldownTracker().setCooldown(this, 40 - (power_lvl * 2));
						} else if (this.shot.equals(EntityPiranhaLauncher.class)) {
							EntityPiranhaLauncher entityammo = (EntityPiranhaLauncher) EntityList.newEntity(this.shot, worldIn);
							entityammo.addVelocity(lookVec.x * 2.0D, lookVec.y * 2.0D, lookVec.z * 2.0D);
							entityammo.setPosition(playerIn.posX + lookVec.x * 1.0D, playerIn.posY + (double)(playerIn.height), playerIn.posZ + lookVec.z * 1.0D);
				            
							if (power_lvl > 0) {
								((EntityPiranhaLauncher)entityammo).setDamage(((EntityPiranhaLauncher) entityammo).getDamage() * (1.0F + (power_lvl + 1) * 0.25F));
							}
							  
							if (punch_lvl > 0) {
								((EntityPiranhaLauncher)entityammo).setKnockbackStrength(punch_lvl);
							}
							  
							if (flame_lvl > 0) {
								((EntityPiranhaLauncher)entityammo).setFire(100);
							}
							
				            worldIn.spawnEntity(entityammo);
				            stack.damageItem(1, playerIn);
							worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, FishItems.PIRANHA_SHOOT, SoundCategory.PLAYERS, 0.85F, 1.0F / (playerIn.world.rand.nextFloat() * 0.4F + 1.2F));
							if (!flag && !playerIn.isCreative()) {
								itemstack.shrink(1);
									if (itemstack.isEmpty()) {
										playerIn.inventory.deleteStack(itemstack);
									}
								}
							playerIn.getCooldownTracker().setCooldown(this, 40 - (power_lvl * 2));
			        	} else {			 
							Entity entityammo = EntityList.newEntity(this.shot, worldIn);
							((EntityFireball)entityammo).shootingEntity = playerIn;
							
							if(this.shot.equals(EntityWarSmallFireball.class)) {
								entityammo.addVelocity(lookVec.x * 2.5D, lookVec.y * 2.5D, lookVec.z * 2.5D);
								entityammo.setPosition(playerIn.posX + lookVec.x * 1.0D, playerIn.posY + (double)(playerIn.height), playerIn.posZ + lookVec.z * 1.0D);
							}
							 
							if (power_lvl > 0) {
								((EntityEnchantableFireBall) entityammo).setDamage(((EntityEnchantableFireBall) entityammo).getDamage() * (1.0F + (power_lvl + 1) * 0.25F));
							}
							  
							if (punch_lvl > 0) {
								((EntityEnchantableFireBall) entityammo).setKnockbackStrength(punch_lvl);
							}
							  
							if (flame_lvl > 0) {
								((EntityEnchantableFireBall) entityammo).setFlame(true);
							}
							 				 
							worldIn.spawnEntity(entityammo);
				            stack.damageItem(1, playerIn);
							worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (playerIn.world.rand.nextFloat() * 0.4F + 1.2F));
							if (!flag && !playerIn.isCreative()) {
								itemstack.shrink(1);
									if (itemstack.isEmpty()) {
										playerIn.inventory.deleteStack(itemstack);
									}
								}
								playerIn.getCooldownTracker().setCooldown(this, 20 - (power_lvl * 2));
							}
			         
			         			return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
			        		}
		         
		         			return new ActionResult<>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
						}
	
	   /**
	    * How long it takes to use or consume an item
	    */
	   public int getUseDuration(ItemStack stack) {
	      return 320;
	   }
	   
		@Override
		@SideOnly(Side.CLIENT)
		public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flag) {
			list.add(TextFormatting.YELLOW + I18n.format(this.Tooltip));
		}
}
