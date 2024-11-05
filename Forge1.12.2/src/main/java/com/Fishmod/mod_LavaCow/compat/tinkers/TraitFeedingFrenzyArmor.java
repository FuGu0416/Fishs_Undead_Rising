package com.Fishmod.mod_LavaCow.compat.tinkers;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;

import com.google.common.collect.Multimap;

import c4.conarm.lib.traits.AbstractArmorTrait;

import com.Fishmod.mod_LavaCow.init.ModMobEffects;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class TraitFeedingFrenzyArmor extends AbstractArmorTrait {
    protected static final UUID[] DAMAGE_MODIFIERS = new UUID[]{UUID.fromString("a979bb47-ef8f-4990-9e9c-8ddf5b01ffb2"),
            UUID.fromString("6a57f600-b3bd-4a61-8c36-29b37dd0c26e"),
            UUID.fromString("4d40b226-9371-4582-807a-f62de451f4e4"),
            UUID.fromString("23de01b5-894a-463b-b033-38c4d46e37d9")};
    private static final double DAMAGE_PER_LEVEL = 0.25D;

    public TraitFeedingFrenzyArmor() {
        super("mod_lavacow.feeding_frenzy", TextFormatting.DARK_RED);
    }

    @Override
    public void onArmorTick(ItemStack armor, World world, EntityPlayer player) {
        if (player instanceof EntityPlayer && ToolHelper.getCurrentDurability(armor) >= 1) {
            player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 2, 4, true, false));
            player.addPotionEffect(new PotionEffect(ModMobEffects.CORRODED, 2, 2, true, false));
        }
    }

    @Override
    public void getAttributeModifiers(@Nonnull EntityEquipmentSlot slot, ItemStack stack, Multimap<String, AttributeModifier> attributeMap) {
        if (slot == EntityLiving.getSlotForItemStack(stack)) {
            attributeMap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(DAMAGE_MODIFIERS[slot.getIndex()], "Feeding Frenzy trait modifier", DAMAGE_PER_LEVEL, 2));
        }
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        String loc = String.format(LOC_Extra, getModifierIdentifier());

        return ImmutableList.of(Util.translateFormatted(loc, Util.dfPercent.format(DAMAGE_PER_LEVEL)));
    }
}
