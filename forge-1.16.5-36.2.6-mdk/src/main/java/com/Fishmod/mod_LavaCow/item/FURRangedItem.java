package com.Fishmod.mod_LavaCow.item;

import java.util.List;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.entities.projectiles.EnchantableFireBallEntity;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShootableItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FURRangedItem extends BowItem {
		
	private Item ammo = null;
	private final EntityType <? extends Entity > shot;
	
	public FURRangedItem(String registryName, Item AmmoIn, EntityType <? extends Entity > shotIn, Item.Properties p_i48522_1_) {
		super(p_i48522_1_);
        this.ammo = AmmoIn;
        this.shot = shotIn;
        this.setRegistryName(registryName);
	}

	public ItemStack getProjectile(ItemStack stack, PlayerEntity playerIn) {
	   if (!(stack.getItem() instanceof ShootableItem)) {
		   return ItemStack.EMPTY;
       } else {
    	   Predicate<ItemStack> predicate = ((ShootableItem)stack.getItem()).getSupportedHeldProjectiles();
    	   ItemStack itemstack = ShootableItem.getHeldProjectile(playerIn, predicate);
    	   if (!itemstack.isEmpty()) {
    		   return itemstack;
    	   } else {
    		   predicate = ((ShootableItem)stack.getItem()).getAllSupportedProjectiles();

    		   for(int i = 0; i < playerIn.inventory.getContainerSize(); ++i) {
    			   ItemStack itemstack1 = playerIn.inventory.getItem(i);
	               if (predicate.test(itemstack1)) {
	            	   return itemstack1;
	               }
	            }

	            return playerIn.abilities.instabuild ? new ItemStack(this.ammo) : ItemStack.EMPTY;
    	   }
       }
	}
			
	@Override		
    public Predicate<ItemStack> getAllSupportedProjectiles() {
	    return (p_220002_0_) -> {
	        return p_220002_0_.getItem().equals(this.ammo);
	    };
	}
		
	@Override
	public void releaseUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {

	}
		
	/**
	* Called when the player stops using an Item (stops holding the right mouse button).
	*/
	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack stack = playerIn.getItemInHand(handIn);
		boolean flag = playerIn.abilities.instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0;
        ItemStack itemstack = this.getProjectile(stack, playerIn);

     	if (!itemstack.isEmpty() || flag) {
     		if (itemstack.isEmpty()) {
     			itemstack = new ItemStack(this.ammo);
 			}
	    }
	    else return ActionResult.fail(playerIn.getItemInHand(handIn));
	         
	    boolean flag1 = playerIn.abilities.instabuild || (itemstack.getItem().equals(this.ammo) && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0);

        if (!worldIn.isClientSide) {
			Vector3d lookVec = playerIn.getLookAngle();
			 
			Entity entityammo = this.shot.create(worldIn);
			((AbstractFireballEntity)entityammo).setOwner(playerIn);
			
			if(this.shot.equals(FUREntityRegistry.WAR_SMALL_FIREBALL)) {
				entityammo.setDeltaMovement(entityammo.getDeltaMovement().add(lookVec.scale(2.5D)));
				entityammo.moveTo(playerIn.getX() + lookVec.x * 1.0D, playerIn.getY() + (double)(playerIn.getBbHeight()), playerIn.getZ() + lookVec.z * 1.0D);
			}
			if(this.shot.equals(FUREntityRegistry.PIRANHA_LAUNCHER)) {
				entityammo.setDeltaMovement(entityammo.getDeltaMovement().add(lookVec.scale(2.0D)).add(0.0D, 0.5D, 0.0D));
				entityammo.moveTo(playerIn.getX() + lookVec.x * 1.0D, playerIn.getY() + (double)(playerIn.getBbHeight()) + 1.75D, playerIn.getZ() + lookVec.z * 1.0D);
			}
			 
			int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
			if (j > 0) {
				((EnchantableFireBallEntity) entityammo).setDamage(((EnchantableFireBallEntity) entityammo).getDamage() * (1.0F + (j + 1) * 0.25F));
			}
			  
			int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack);
			if (k > 0) {
				((EnchantableFireBallEntity) entityammo).setKnockbackStrength(k);
			}
			  
			if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, stack) > 0) {
				((EnchantableFireBallEntity) entityammo).setFlame(true);
			}
			 				 
			worldIn.addFreshEntity(entityammo);
            playerIn.getItemInHand(handIn).hurtAndBreak(1, playerIn, (p_220045_0_) -> {
    			p_220045_0_.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
    		});
			worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.BLAZE_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (playerIn.getRandom().nextFloat() * 0.4F + 1.2F));
			if (!flag1 && !playerIn.isCreative()) {
				itemstack.shrink(1);
				if (itemstack.isEmpty()) {
					playerIn.inventory.removeItem(itemstack);
				}
			}
			playerIn.getCooldowns().addCooldown(this, 20 - (j * 2));
			  
			return ActionResult.consume(playerIn.getItemInHand(handIn));
        }
		
		return ActionResult.consume(playerIn.getItemInHand(handIn));
	}

    /**
    * How long it takes to use or consume an item
    */
	@Override
    public int getUseDuration(ItemStack stack) {
		return 320;
    }
   
	@Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TranslationTextComponent("tootip." + this.getRegistryName()).withStyle(TextFormatting.YELLOW));
	}
}
