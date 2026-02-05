package com.Fishmod.fur.item;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.Fishmod.fur.entities.projectiles.CactusThornEntity;
import com.Fishmod.fur.entities.projectiles.EnchantableFireBallEntity;
import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURItemRegistry;
import com.Fishmod.fur.init.FURSoundRegistry;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FURRangedItem extends CrossbowItem {
		
	private Item ammo = null;
	private final Supplier<EntityType<? extends Entity>> shot;
	
	public FURRangedItem(Item ammo, Supplier<EntityType<?>> shot, Item.Properties properties) {
		super(properties);
        this.ammo = ammo;
        this.shot = shot;
	}

	public ItemStack getProjectile(ItemStack stack, Player player) {
	   if (!(stack.getItem() instanceof ProjectileWeaponItem) || this.ammo == null) {
		   return ItemStack.EMPTY;
       } else {
    	   Predicate<ItemStack> predicate = ((ProjectileWeaponItem)stack.getItem()).getSupportedHeldProjectiles();
    	   ItemStack itemstack = ProjectileWeaponItem.getHeldProjectile(player, predicate);
    	   if (!itemstack.isEmpty()) {
    		   return itemstack;
    	   } else {
    		   predicate = ((ProjectileWeaponItem)stack.getItem()).getAllSupportedProjectiles();

    		   for(int i = 0; i < player.getInventory().getContainerSize(); ++i) {
    			   ItemStack itemstack1 = player.getInventory().getItem(i);
	               if (predicate.test(itemstack1)) {
	            	   return itemstack1;
	               }
	            }

	            return player.getAbilities().instabuild ? new ItemStack(this.ammo) : ItemStack.EMPTY;
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
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }
	
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        EnchantmentCategory category = enchantment.category;
        
        if (stack.getItem().equals(FURItemRegistry.THORN_SHOOTER.get())) {
	        return category.equals(EnchantmentCategory.BOW)
	            || category.equals(EnchantmentCategory.CROSSBOW);
        } else if (stack.getItem().equals(FURItemRegistry.WAR.get())) {
	        return enchantment != Enchantments.PIERCING &&
	        	   enchantment != Enchantments.FLAMING_ARROWS &&
	        		(category.equals(EnchantmentCategory.BOW)
	            || category.equals(EnchantmentCategory.CROSSBOW));
        }
        
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }
	
	public static boolean isCharged(ItemStack stack) {
		CompoundTag compoundtag = stack.getTag();
		return compoundtag != null && compoundtag.getBoolean("Charged");
	}
		
	@Override
	public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
	}
	
	@Override
	public void onUseTick(Level level, LivingEntity user, ItemStack stack, int remaining) {
	    if (!(user instanceof Player player)) return;

		boolean flag = player.getAbilities().instabuild || stack.getEnchantmentLevel(Enchantments.INFINITY_ARROWS) > 0;
        ItemStack itemstack = this.getProjectile(stack, player);
        int quick_charge_lvl = stack.getEnchantmentLevel(Enchantments.QUICK_CHARGE);
        
     	if (!itemstack.isEmpty() || (flag || this.ammo == null)) {
     		if (itemstack.isEmpty()) {
     			itemstack = new ItemStack(this.ammo);
 			}
	    } else return;
     	
	    if (!level.isClientSide && remaining % (7 - quick_charge_lvl) == 0) {
			int power_lvl = stack.getEnchantmentLevel(Enchantments.POWER_ARROWS);
			int punch_lvl = stack.getEnchantmentLevel(Enchantments.PUNCH_ARROWS);
			int flame_lvl = stack.getEnchantmentLevel(Enchantments.FLAMING_ARROWS);
			int piercing_lvl = stack.getEnchantmentLevel(Enchantments.PIERCING);
			int multishot_lvl = stack.getEnchantmentLevel(Enchantments.MULTISHOT);
			
			if (this.shot.get().equals(FUREntityRegistry.CACTUS_THORN.get())) {
				for (int i = ((multishot_lvl > 0) ? -1 : 0); i < ((multishot_lvl > 0) ? 2 : 1) ; i++) {
		        	CactusThornEntity abstractarrowentity = new CactusThornEntity(level, player);
		        	abstractarrowentity.shootFromRotation(player, player.getXRot(), player.getYRot() + (10.0F * i), 0.0F, 2.0F, 2.0F);                       
		        	level.playSound(null, player.getX(), player.getY(), player.getZ(), FURSoundRegistry.RANDOM_THORN_SHOOT.get(), SoundSource.PLAYERS, 1.0F, 1.0F / (player.getRandom().nextFloat() * 0.4F + 1.2F));
		        	
	                if (power_lvl > 0) {
	                	abstractarrowentity.setBaseDamage(abstractarrowentity.getBaseDamage() + (double)power_lvl * 0.1D + 0.1D);
	                }
	           
	                if (punch_lvl > 0) {
	                	abstractarrowentity.setKnockback(punch_lvl);
	                }
	
	                if (flame_lvl > 0) {
	                	abstractarrowentity.setSecondsOnFire(100);
	                }
	                
	                if (piercing_lvl > 0) {
	                	abstractarrowentity.setPierceLevel((byte)i);
	                }
	                
	                if (player.getRandom().nextFloat() < 0.25F) {
		                stack.hurtAndBreak(1, player, (p_220009_1_) -> {
		                    p_220009_1_.broadcastBreakEvent(player.getUsedItemHand());
		                });
	                }
	                
	                if (flag) {
	                    abstractarrowentity.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
	                }
	                
	                level.addFreshEntity(abstractarrowentity);
					if (!flag && !player.isCreative()) {
						itemstack.shrink(1);
						if (itemstack.isEmpty()) {
							player.getInventory().removeItem(itemstack);
							break;
						}
					}
				}
			/*} else if (this.shot.equals(FUREntityRegistry.DEATHCOIL)) {
				DeathCoilEntity entitysnowball = (DeathCoilEntity) this.shot.create(level);
        		entitysnowball.moveTo(player.getX() + lookVec.x * 1.0D, player.getY() + (double)(player.getBbHeight()),player.getZ() + lookVec.z * 1.0D);
	            entitysnowball.shootFromRotation(player, player.xRot, player.yRot, 0.0F, 0.75F, 1.0F);
	            entitysnowball.setOwner(player);
	            
				if (power_lvl > 0) {
					((DeathCoilEntity) entitysnowball).setDamage(((DeathCoilEntity) entitysnowball).getDamage() * (1.0F + (power_lvl + 1) * 0.25F));
				}
				  
				if (punch_lvl > 0) {
					((DeathCoilEntity) entitysnowball).setKnockbackStrength(punch_lvl);
				}
				  
				if (flame_lvl > 0) {
					((DeathCoilEntity) entitysnowball).setSecondsOnFire(100);
				}
				
	            level.addFreshEntity(entitysnowball);
	            player.getItemInHand(hand).hurtAndBreak(1, player, (p_220045_0_) -> {
	    			p_220045_0_.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
	    		});
				level.playSound(null, player.getX(), player.getY(), player.getZ(), FURSoundRegistry.SKELETONKING_SPELL_TOSS, SoundCategory.PLAYERS, 1.0F, 1.0F / (player.getRandom().nextFloat() * 0.4F + 1.2F));
				player.getCooldowns().addCooldown(this, 40 - (power_lvl * 2));*/
        	}
	    }
	}
		
	/**
	* Called when the player stops using an Item (stops holding the right mouse button).
	*/
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		stack.getOrCreateTag().putBoolean("Charged", true);
		if (stack.getItem().equals(FURItemRegistry.THORN_SHOOTER.get())) {
		    player.startUsingItem(hand);
		} else if (stack.getItem().equals(FURItemRegistry.WAR.get())) {
			ItemStack itemstack = this.getProjectile(stack, player);
			boolean flag = player.getAbilities().instabuild || stack.getEnchantmentLevel(Enchantments.INFINITY_ARROWS) > 0;
			
	     	if (!itemstack.isEmpty() || (flag || this.ammo == null)) {
	     		if (itemstack.isEmpty()) {
	     			itemstack = new ItemStack(this.ammo);
	 			}
		    } else return super.use(level, player, hand);
	     	
			Vec3 lookVec = player.getLookAngle();	
			int power_lvl = stack.getEnchantmentLevel(Enchantments.POWER_ARROWS);
			int punch_lvl = stack.getEnchantmentLevel(Enchantments.PUNCH_ARROWS);
			int flame_lvl = stack.getEnchantmentLevel(Enchantments.FLAMING_ARROWS);
			int multishot_lvl = stack.getEnchantmentLevel(Enchantments.MULTISHOT);
			int quick_charge_lvl = stack.getEnchantmentLevel(Enchantments.QUICK_CHARGE);
			float angle = (float) Math.toRadians(10);
			
	        
	        
	        for (int i = ((multishot_lvl > 0) ? -1 : 0); i < ((multishot_lvl > 0) ? 2 : 1) ; i++) {
				Entity entityammo = this.shot.get().create(level);
				((Fireball)entityammo).setOwner(player);
				
				if (this.shot.get().equals(FUREntityRegistry.WAR_SMALL_FIREBALL.get())) {
					entityammo.setDeltaMovement(entityammo.getDeltaMovement().add(lookVec.scale(2.5D).yRot(angle * i)));
					entityammo.moveTo(player.getX() + lookVec.x * 1.0D, player.getEyeY() - 0.1D, player.getZ() + lookVec.z * 1.0D);
					level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (player.getRandom().nextFloat() * 0.4F + 1.2F));
				}
				
				/*if (this.shot.equals(FUREntityRegistry.PIRANHA_LAUNCHER)) {
					entityammo.setDeltaMovement(entityammo.getDeltaMovement().add(lookVec.scale(2.0D)).add(0.0D, 0.15D, 0.0D));
					entityammo.moveTo(player.getX() + lookVec.x * 1.0D, player.getY() + (double)(player.getBbHeight()) - 0.5D, player.getZ() + lookVec.z * 1.0D);
					level.playSound(null, player.getX(), player.getY(), player.getZ(), FURSoundRegistry.RANDOM_PIRANHA_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (player.getRandom().nextFloat() * 0.4F + 1.2F));
				}*/
				 
				if (power_lvl > 0) {
					((EnchantableFireBallEntity) entityammo).setDamage(((EnchantableFireBallEntity) entityammo).getDamage() * (1.0F + (power_lvl + 1) * 0.25F));
				}
				  
				if (punch_lvl > 0) {
					((EnchantableFireBallEntity) entityammo).setKnockbackStrength(punch_lvl);
				}
				  
				if (flame_lvl > 0) {
					((EnchantableFireBallEntity) entityammo).setFlame(true);
				}
				 				 
				level.addFreshEntity(entityammo);
	            
	            stack.hurtAndBreak(1, player, (p_220009_1_) -> {
	                p_220009_1_.broadcastBreakEvent(player.getUsedItemHand());
	            });
				
				if (!flag && !player.isCreative()) {
					itemstack.shrink(1);
					if (itemstack.isEmpty()) {
						player.getInventory().removeItem(itemstack);
						break;
					}
				}
	        }
			
			player.getCooldowns().addCooldown(this, 20 - (quick_charge_lvl * 2));
		}
	    
	    return InteractionResultHolder.consume(player.getItemInHand(hand));
	}

    /**
    * How long it takes to use or consume an item
    */
	@Override
    public int getUseDuration(ItemStack stack) {
		return 72000;
    }
	
    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }
   
	@Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.translatable(this.getDescriptionId() +  ".desc").withStyle(ChatFormatting.YELLOW));
	}
}
