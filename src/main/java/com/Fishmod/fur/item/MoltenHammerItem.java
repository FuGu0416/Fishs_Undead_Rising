package com.Fishmod.fur.item;

import java.util.List;

import com.Fishmod.fur.core.SpawnUtil;
import com.Fishmod.fur.init.FUREffectRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class MoltenHammerItem extends FURWeaponItem {

	public MoltenHammerItem(Properties properties, Tier material, int damage, float attackspeed, double reach, Item repair, Boolean hasDesc) {
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
		int[] enchantment_list = new int[10];		
		enchantment_list[0] = stack.getEnchantmentLevel(Enchantments.FIRE_ASPECT);
		enchantment_list[1] = stack.getEnchantmentLevel(Enchantments.SHARPNESS);
		enchantment_list[2] = stack.getEnchantmentLevel(Enchantments.KNOCKBACK);
		enchantment_list[3] = stack.getEnchantmentLevel(Enchantments.BANE_OF_ARTHROPODS);
		enchantment_list[4] = stack.getEnchantmentLevel(Enchantments.SMITE);
		//enchantment_list[5] = stack.getEnchantmentLevel(FUREnchantmentRegistry.LIFESTEAL);
		//enchantment_list[6] = stack.getEnchantmentLevel(FUREnchantmentRegistry.POISONOUS);
		//enchantment_list[7] = stack.getEnchantmentLevel(FUREnchantmentRegistry.CORROSIVE);
		enchantment_list[8] = stack.getEnchantmentLevel(Enchantments.UNBREAKING);
		//enchantment_list[9] = stack.getEnchantmentLevel(FUREnchantmentRegistry.DOMINION);
		
		List<Entity> list = level.getEntities(player, player.getBoundingBox().inflate(radius));
		for (Entity entity1 : list) {
			if ((entity1 instanceof LivingEntity && !(entity1 instanceof TamableAnimal)) || (entity1 instanceof TamableAnimal && !((TamableAnimal)entity1).isOwnedBy(player))/* || (entity1 instanceof Player && FURConfig.MoltenHammer_PVP.get())*/) {
				entity1.setSecondsOnFire(2 * enchantment_list[0]);
				entity1.hurt(entity1.damageSources().playerAttack(player) , 8.0F + (enchantment_list[1] > 0 ? (0.5f * enchantment_list[1] + 0.5f) : 0.0f)
						+ (((LivingEntity) entity1).getMobType().equals(MobType.ARTHROPOD) ? (float)enchantment_list[3] : 0)
						+ (((LivingEntity) entity1).getMobType().equals(MobType.UNDEAD) ? (float)enchantment_list[4] : 0));
				
				if (enchantment_list[2] > 0)
					((LivingEntity)entity1).setDeltaMovement(((LivingEntity)entity1).getDeltaMovement().add((float)enchantment_list[2] * 0.5F, (player.getX() - entity1.getX())/player.distanceTo(entity1), (player.getZ() - entity1.getZ())/player.distanceTo(entity1)));
				
	            if (enchantment_list[3] > 0 && (((LivingEntity) entity1).getMobType().equals(MobType.ARTHROPOD))) {
	                int i = 20 + level.random.nextInt(10 * enchantment_list[3]);
	                ((LivingEntity)entity1).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, i, 3));
	            }
	            
	            if (enchantment_list[6] > 0)
	    			((LivingEntity)entity1).addEffect(new MobEffectInstance(MobEffects.POISON, 8*20, enchantment_list[6] - 1));
	            
	            if (enchantment_list[7] > 0)
	            	((LivingEntity)entity1).addEffect(new MobEffectInstance(FUREffectRegistry.CORRODED.get(), 4*20, enchantment_list[7] - 1));
			}
		}
		
		SpawnUtil.LavaBurst(level, player.getX(), player.getY() + 1.0D, player.getZ(), radius, ParticleTypes.FLAME);
		SpawnUtil.LavaBurst(level, player.getX(), player.getY() + 1.0D, player.getZ(), radius * 0.5D, ParticleTypes.CAMPFIRE_COSY_SMOKE);
        stack.hurtAndBreak(16, player, (p_220045_0_) -> {
			p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
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
