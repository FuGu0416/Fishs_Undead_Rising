package com.Fishmod.fur.item;

import java.util.List;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import com.Fishmod.fur.entities.projectiles.CactusThornEntity;
import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURSoundRegistry;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FURRangedItem extends BowItem {
		
	private Item ammo = null;
	private final EntityType <? extends Entity > shot;
	
	public FURRangedItem(Item AmmoIn, EntityType <? extends Entity > shotIn, Item.Properties p_i48522_1_) {
		super(p_i48522_1_);
        this.ammo = AmmoIn;
        this.shot = shotIn;
	}

	public ItemStack getProjectile(ItemStack stack, Player playerIn) {
	   if (!(stack.getItem() instanceof ProjectileWeaponItem) || this.ammo == null) {
		   return ItemStack.EMPTY;
       } else {
    	   Predicate<ItemStack> predicate = ((ProjectileWeaponItem)stack.getItem()).getSupportedHeldProjectiles();
    	   ItemStack itemstack = ProjectileWeaponItem.getHeldProjectile(playerIn, predicate);
    	   if (!itemstack.isEmpty()) {
    		   return itemstack;
    	   } else {
    		   predicate = ((ProjectileWeaponItem)stack.getItem()).getAllSupportedProjectiles();

    		   for(int i = 0; i < playerIn.getInventory().getContainerSize(); ++i) {
    			   ItemStack itemstack1 = playerIn.getInventory().getItem(i);
	               if (predicate.test(itemstack1)) {
	            	   return itemstack1;
	               }
	            }

	            return playerIn.getAbilities().instabuild ? new ItemStack(this.ammo) : ItemStack.EMPTY;
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
	public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {

	}
		
	/**
	* Called when the player stops using an Item (stops holding the right mouse button).
	*/
	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		ItemStack stack = playerIn.getItemInHand(handIn);
		boolean flag = playerIn.getAbilities().instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0;
        ItemStack itemstack = this.getProjectile(stack, playerIn);
        
     	if (!itemstack.isEmpty() || (flag || this.ammo == null)) {
     		if (itemstack.isEmpty()) {
     			itemstack = new ItemStack(this.ammo);
 			}
	    } else return InteractionResultHolder.fail(playerIn.getItemInHand(handIn));
	         
        if (!worldIn.isClientSide) {
        	//Vec3 lookVec = playerIn.getLookAngle();
			int power_lvl = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
			int punch_lvl = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack);
			int flame_lvl = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, stack);
			if (this.shot.equals(FUREntityRegistry.CACTUS_THORN.get())) {
	        	CactusThornEntity abstractarrowentity = new CactusThornEntity(worldIn, playerIn);
	        	abstractarrowentity.shootFromRotation(playerIn, playerIn.getXRot(), playerIn.getYRot(), 0.0F, 2.0F, 2.0F);                       
	        	worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), FURSoundRegistry.RANDOM_THORN_SHOOT.get(), SoundSource.PLAYERS, 1.0F, 1.0F / (playerIn.getRandom().nextFloat() * 0.4F + 1.2F));
	        	
                if (power_lvl > 0) {
                   abstractarrowentity.setBaseDamage(abstractarrowentity.getBaseDamage() + (double)power_lvl * 0.1D + 0.1D);
                }
           
                if (punch_lvl > 0) {
                   abstractarrowentity.setKnockback(punch_lvl);
                }

                if (flame_lvl > 0) {
                   abstractarrowentity.setSecondsOnFire(100);
                }
                
                if (playerIn.getRandom().nextFloat() < 0.25F) {
	                stack.hurtAndBreak(1, playerIn, (p_220009_1_) -> {
	                    p_220009_1_.broadcastBreakEvent(playerIn.getUsedItemHand());
	                });
                }
                
                if (flag) {
                    abstractarrowentity.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                }
                
                worldIn.addFreshEntity(abstractarrowentity);
				if (!flag && !playerIn.isCreative()) {
					itemstack.shrink(1);
					if (itemstack.isEmpty()) {
						playerIn.getInventory().removeItem(itemstack);
					}
				}
			}/* else if (this.shot.equals(FUREntityRegistry.DEATHCOIL)) {
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
			}*/
			
			return InteractionResultHolder.consume(playerIn.getItemInHand(handIn));
        }
		
		return InteractionResultHolder.consume(playerIn.getItemInHand(handIn));
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
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(Component.translatable(this.getDescriptionId() +  ".desc").withStyle(ChatFormatting.YELLOW));
	}
}
