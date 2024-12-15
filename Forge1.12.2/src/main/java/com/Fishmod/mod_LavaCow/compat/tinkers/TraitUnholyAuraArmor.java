package com.Fishmod.mod_LavaCow.compat.tinkers;

import java.util.List;

import com.google.common.collect.ImmutableList;

import c4.conarm.lib.armor.ArmorModifications;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import slimeknights.tconstruct.library.Util;

public class TraitUnholyAuraArmor extends AbstractArmorTrait {
    private static final float MODIFIER = 0.4F;

    public TraitUnholyAuraArmor() {
        super("mod_lavacow.unholy_aura", 0x7AF2FF);
    }

    @Override
    public float onDamaged(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingDamageEvent evt) {
        if (source.getTrueSource() instanceof EntityLivingBase) {
            if (!player.world.isRemote && ((EntityLivingBase) source.getTrueSource()).getCreatureAttribute() != EnumCreatureAttribute.UNDEAD) {
                ((EntityLivingBase) source.getTrueSource()).addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 5 * 20, 0));
            }
        }

        return super.onDamaged(armor, player, source, damage, newDamage, evt);
    }

    @Override
    public ArmorModifications getModifications(EntityPlayer player, ArmorModifications mods, ItemStack armor, DamageSource source, double damage, int slot) {
        if (source.getTrueSource() instanceof EntityLivingBase) {
            if (((EntityLivingBase) source.getTrueSource()).getCreatureAttribute() != EnumCreatureAttribute.UNDEAD) {
                return super.getModifications(player, mods, armor, source, damage, slot);
            }
        }
        mods.addEffectiveness(MODIFIER);
        return super.getModifications(player, mods, armor, source, damage, slot);
    }

    @Override
    public List<String> getExtraInfo(ItemStack armor, NBTTagCompound modifierTag) {
        String loc = String.format(LOC_Extra, getModifierIdentifier());

        return ImmutableList.of(Util.translateFormatted(loc, Util.dfPercent.format(MODIFIER)));
    }
}
