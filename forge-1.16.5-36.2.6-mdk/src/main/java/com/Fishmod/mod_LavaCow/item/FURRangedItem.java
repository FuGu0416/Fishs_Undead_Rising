package com.Fishmod.mod_LavaCow.item;

import java.util.List;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.entities.projectiles.CactusThornEntity;
import com.Fishmod.mod_LavaCow.entities.projectiles.DeathCoilEntity;
import com.Fishmod.mod_LavaCow.entities.projectiles.EnchantableFireBallEntity;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
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
	   if (!(stack.getItem() instanceof ShootableItem) || this.ammo == null) {
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
        
     	if (!itemstack.isEmpty() || (flag || this.ammo == null)) {
     		if (itemstack.isEmpty()) {
     			itemstack = new ItemStack(this.ammo);
 			}
	    } else return ActionResult.fail(playerIn.getItemInHand(handIn));
	         
        if (!worldIn.isClientSide) {
			Vector3d lookVec = playerIn.getLookAngle();
			int power_lvl = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
			int punch_lvl = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack);
			int flame_lvl = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, stack);
			if (this.shot.equals(FUREntityRegistry.CACTUS_THORN)) {
	        	CactusThornEntity abstractarrowentity = new CactusThornEntity(playerIn.level, playerIn);
	        	abstractarrowentity.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, 0.0F, 2.0F, 2.0F);                       
	        	playerIn.playSound(FURSoundRegistry.RANDOM_THORN_SHOOT, 1.0F, 1.0F / (playerIn.getRandom().nextFloat() * 0.4F + 0.8F));
	        	        	
                if (power_lvl > 0) {
                   abstractarrowentity.setBaseDamage(abstractarrowentity.getBaseDamage() + (double)power_lvl * 0.1D + 0.1D);
                }
           
                if (punch_lvl > 0) {
                   abstractarrowentity.setKnockback(punch_lvl);
                }

                if (flame_lvl > 0) {
                   abstractarrowentity.setSecondsOnFire(100);
                }
                
                if (random.nextFloat() < 0.25F) {
	                stack.hurtAndBreak(1, playerIn, (p_220009_1_) -> {
	                    p_220009_1_.broadcastBreakEvent(playerIn.getUsedItemHand());
	                });
                }
                
                if (flag) {
                    abstractarrowentity.pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
                }
                
                playerIn.level.addFreshEntity(abstractarrowentity);
				if (!flag && !playerIn.isCreative()) {
					itemstack.shrink(1);
					if (itemstack.isEmpty()) {
						playerIn.inventory.removeItem(itemstack);
					}
				}
			} else if (this.shot.equals(FUREntityRegistry.DEATHCOIL)) {
				DeathCoilEntity entitysnowball = (DeathCoilEntity) this.shot.create(worldIn);
        		entitysnowball.moveTo(playerIn.getX() + lookVec.x * 1.0D, playerIn.getY() + (double)(playerIn.getBbHeight()),playerIn.getZ() + lookVec.z * 1.0D);
	            entitysnowball.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, 0.0F, 0.75F, 1.0F);
	            entitysnowball.setOwner(playerIn);
	            
				if (power_lvl > 0) {
					((DeathCoilEntity) entitysnowball).setDamage(((DeathCoilEntity) entitysnowball).getDamage() * (1.0F + (power_lvl + 1) * 0.25F));
				}
				  
				if (punch_lvl > 0) {
					((DeathCoilEntity) entitysnowball).setKnockbackStrength(punch_lvl);
				}
				  
				if (flame_lvl > 0) {
					((DeathCoilEntity) entitysnowball).setSecondsOnFire(100);
				}
				
	            worldIn.addFreshEntity(entitysnowball);
	            playerIn.getItemInHand(handIn).hurtAndBreak(1, playerIn, (p_220045_0_) -> {
	    			p_220045_0_.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
	    		});
				worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), FURSoundRegistry.SKELETONKING_SPELL_TOSS, SoundCategory.PLAYERS, 1.0F, 1.0F / (playerIn.getRandom().nextFloat() * 0.4F + 1.2F));
				playerIn.getCooldowns().addCooldown(this, 40 - (power_lvl * 2));
        	} else {			 
				Entity entityammo = this.shot.create(worldIn);
				((AbstractFireballEntity)entityammo).setOwner(playerIn);
				
				if(this.shot.equals(FUREntityRegistry.WAR_SMALL_FIREBALL)) {
					entityammo.setDeltaMovement(entityammo.getDeltaMovement().add(lookVec.scale(2.5D)));
					entityammo.moveTo(playerIn.getX() + lookVec.x * 1.0D, playerIn.getY() + (double)(playerIn.getBbHeight()), playerIn.getZ() + lookVec.z * 1.0D);
					worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.BLAZE_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (playerIn.getRandom().nextFloat() * 0.4F + 1.2F));
				}
				if(this.shot.equals(FUREntityRegistry.PIRANHA_LAUNCHER)) {
					entityammo.setDeltaMovement(entityammo.getDeltaMovement().add(lookVec.scale(2.0D)).add(0.0D, 0.15D, 0.0D));
					entityammo.moveTo(playerIn.getX() + lookVec.x * 1.0D, playerIn.getY() + (double)(playerIn.getBbHeight()) - 0.5D, playerIn.getZ() + lookVec.z * 1.0D);
					worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), FURSoundRegistry.RANDOM_PIRANHA_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (playerIn.getRandom().nextFloat() * 0.4F + 1.2F));
				}
				 
				if (power_lvl > 0) {
					((EnchantableFireBallEntity) entityammo).setDamage(((EnchantableFireBallEntity) entityammo).getDamage() * (1.0F + (power_lvl + 1) * 0.25F));
				}
				  
				if (punch_lvl > 0) {
					((EnchantableFireBallEntity) entityammo).setKnockbackStrength(punch_lvl);
				}
				  
				if (flame_lvl > 0) {
					((EnchantableFireBallEntity) entityammo).setFlame(true);
				}
				 				 
				worldIn.addFreshEntity(entityammo);
	            playerIn.getItemInHand(handIn).hurtAndBreak(1, playerIn, (p_220045_0_) -> {
	    			p_220045_0_.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
	    		});
				
				if (!flag && !playerIn.isCreative()) {
					itemstack.shrink(1);
					if (itemstack.isEmpty()) {
						playerIn.inventory.removeItem(itemstack);
					}
				}
				playerIn.getCooldowns().addCooldown(this, 20 - (power_lvl * 2));
			}
			
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
