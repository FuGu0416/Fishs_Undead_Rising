package com.Fishmod.mod_LavaCow.compat.rlcombat;

import bettercombat.mod.event.RLCombatCriticalHitEvent;
import bettercombat.mod.event.RLCombatSweepEvent;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.init.ModEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class RLCombatCompat {

    /*
     *   - Allow offhand crit to get correct modifier
     *   - If compat is loaded, cancel original handling
     */
    @SubscribeEvent
    public static void doCritBoost(RLCombatCriticalHitEvent event) {
        EntityLivingBase attacker = event.getEntityPlayer();
        ItemStack stack = event.getOffhand() ? attacker.getHeldItemOffhand() : attacker.getHeldItemMainhand();
        if (stack.isEmpty()) return;

        int level = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.CRITICAL_BOOST, stack);
        if (level > 0) {
            event.setDamageModifier(event.getDamageModifier() + (level * 0.15F));
        }
    }

    /*
     *   - Allow offhand sweep to get correct modifier
     *   - If compat is loaded, cancel original handling
     */
    @SubscribeEvent
    public static void onScytheSweep(RLCombatSweepEvent event) {
        if (event.getItemStack().getItem() == FishItems.REAPERS_SCYTHE) {
            EntityPlayer attacker = event.getEntityPlayer();
            event.setDoSweep(true);
            event.setSweepingAABB(event.getSweepingAABB().grow(1.0F, 0, 1.0F));
            attacker.world.playSound((EntityPlayer) null, attacker.posX, attacker.posY, attacker.posZ, FishItems.ENTITY_SCARECROW_SCYTHE, attacker.getSoundCategory(), 1.0F, 1.0F / (attacker.world.rand.nextFloat() * 0.4F + 0.8F));
        }
    }
}
