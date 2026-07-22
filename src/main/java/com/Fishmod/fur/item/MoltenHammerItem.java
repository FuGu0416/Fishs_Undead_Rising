package com.Fishmod.fur.item;

import java.util.List;
import java.util.function.Supplier;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.core.SpawnUtil;
import com.Fishmod.fur.init.FURItemRegistry;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class MoltenHammerItem extends FURWeaponItem {

	public MoltenHammerItem(Properties properties, Tier material, int damage, float attackspeed, double reach, Supplier<Item> repair, Boolean hasDesc) {
		super(properties, material, damage, attackspeed, reach, repair, hasDesc);
	}
	
    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {	
		int i = attacker.getMainHandItem().getEnchantmentLevel(Enchantments.FIRE_ASPECT);			
		target.setSecondsOnFire((i + 2) * 4);
		target.playSound(SoundEvents.ZOMBIE_ATTACK_IRON_DOOR, 1.0F, 0.85F);
			
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity living, int remainingticks) {
    	if (!(living instanceof Player player) || (this.getUseDuration(stack) - remainingticks) < 6) return;
    	
		float f = BowItem.getPowerForTime((this.getUseDuration(stack) - remainingticks));
		double radius = 5.0D * f;
		float damage;
		SimpleParticleType particle;
		SimpleParticleType particle1;
		
		if (stack.getItem() == FURItemRegistry.SOULFORGED_HAMMER.get()) {
			damage = 10.0F;
			particle = ParticleTypes.SOUL_FIRE_FLAME;
			particle1 = ParticleTypes.SOUL;
		} else { // Molten Hammer
			damage = 8.0F;
			particle = ParticleTypes.FLAME;
			particle1 = ParticleTypes.CAMPFIRE_COSY_SMOKE;
		}
		
		int fire_aspect = stack.getEnchantmentLevel(Enchantments.FIRE_ASPECT);
		int knockback = stack.getEnchantmentLevel(Enchantments.KNOCKBACK);

		List<Entity> list = level.getEntities(player, player.getBoundingBox().inflate(radius));
		for (Entity entity1 : list) {
			if ((entity1 instanceof LivingEntity && !(entity1 instanceof TamableAnimal)) || (entity1 instanceof TamableAnimal && !((TamableAnimal)entity1).isOwnedBy(player)) || (entity1 instanceof Player && FURConfig.MoltenHammer_PVP.get())) {
				entity1.setSecondsOnFire(2 * fire_aspect);
				entity1.hurt(entity1.damageSources().playerAttack(player), damage + EnchantmentHelper.getDamageBonus(stack, ((LivingEntity) entity1).getMobType()));

				if (knockback > 0)
					((LivingEntity)entity1).setDeltaMovement(((LivingEntity)entity1).getDeltaMovement().add((float)knockback * 0.5F, (player.getX() - entity1.getX())/player.distanceTo(entity1), (player.getZ() - entity1.getZ())/player.distanceTo(entity1)));

				// Post-attack enchantment procs (bane-of-arthropods slowdown, corrosive corrode, and
				// any future doPostAttack enchantment) — same hook vanilla melee uses.
				EnchantmentHelper.doPostDamageEffects(player, entity1);
			}
		}
		
		SpawnUtil.LavaBurst(level, player.getX(), player.getY() + 1.0D, player.getZ(), radius, particle);
		SpawnUtil.LavaBurst(level, player.getX(), player.getY() + 1.0D, player.getZ(), radius * 0.5D, particle1);

        stack.hurtAndBreak(16, player, (entity) -> {
			entity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
		});
        
        player.playSound(SoundEvents.GENERIC_EXPLODE, 1.0F, 1.0F);
        player.getCooldowns().addCooldown(this, 80);
    }
    
    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPEAR;
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
