package com.Fishmod.fur.item;

import com.Fishmod.fur.entities.projectiles.FangDaggerEntity;
import com.Fishmod.fur.init.FURSoundRegistry;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class FangDaggerItem extends FURWeaponItem {

	public FangDaggerItem(Properties PropertiesIn, Tier materialIn, int damageIn, float attackspeedIn, double reachIn, Item repair, Boolean hasDescIn) {
		super(PropertiesIn, materialIn, damageIn, attackspeedIn, reachIn, repair, hasDescIn);
	}

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity living, int remainingticks) {
    	if (!(living instanceof Player player) || (this.getUseDuration(stack) - remainingticks) < 6) return;

    	if (!level.isClientSide) {
    		FangDaggerEntity abstractarrowentity = new FangDaggerEntity(level, player);
        	abstractarrowentity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.0F, 2.0F); 
        	
        	abstractarrowentity.fire_aspect = stack.getEnchantmentLevel(Enchantments.FIRE_ASPECT);
        	abstractarrowentity.sharpness = stack.getEnchantmentLevel(Enchantments.SHARPNESS);
        	abstractarrowentity.knockback = stack.getEnchantmentLevel(Enchantments.KNOCKBACK);
        	abstractarrowentity.bane_of_arthropods = stack.getEnchantmentLevel(Enchantments.BANE_OF_ARTHROPODS);
        	abstractarrowentity.smite = stack.getEnchantmentLevel(Enchantments.SMITE);
        	abstractarrowentity.lifesteal = 0;//stack.getEnchantmentLevel(FUREnchantmentRegistry.LIFESTEAL);
        	abstractarrowentity.poisonous = 0;//stack.getEnchantmentLevel(FUREnchantmentRegistry.POISONOUS);
        	abstractarrowentity.corrosive = 0;//stack.getEnchantmentLevel(FUREnchantmentRegistry.CORROSIVE);
        	abstractarrowentity.baseDamage = (int) this.getDamage();
        	
        	abstractarrowentity.pickup = AbstractArrow.Pickup.DISALLOWED;
        	level.addFreshEntity(abstractarrowentity);
    		stack.hurtAndBreak(1, player, (p_289501_) -> {
                p_289501_.broadcastBreakEvent(player.getUsedItemHand());
             });
    		level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), FURSoundRegistry.RANDOM_FANG_DAGGER_THROW.get(), SoundSource.PLAYERS, 1.0F, 1.0F / (living.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
    	}
    	
    	player.getCooldowns().addCooldown(this, 10);
    }
    
    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getDamageValue() >= stack.getMaxDamage() - 1) {
            return InteractionResultHolder.fail(stack);
        } else {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(stack);
        }
    }    
}
