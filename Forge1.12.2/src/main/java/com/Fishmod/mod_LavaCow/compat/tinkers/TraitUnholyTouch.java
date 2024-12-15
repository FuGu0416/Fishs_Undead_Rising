package com.Fishmod.mod_LavaCow.compat.tinkers;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class TraitUnholyTouch extends AbstractTrait {

    private static float bonusDamage = 4.0F;

    public TraitUnholyTouch() {
        super("mod_lavacow.unholytouch", 0x7af2ff);
    }

    @Override
    public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
        if (target.getCreatureAttribute() != EnumCreatureAttribute.UNDEAD) {
            newDamage += bonusDamage;
        }

        return super.damage(tool, player, target, damage, newDamage, isCritical);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        if (wasHit && target.isEntityAlive() && target.getCreatureAttribute() != EnumCreatureAttribute.UNDEAD) {
            target.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 5 * 20, 0, false, true));
        }
    }
}
