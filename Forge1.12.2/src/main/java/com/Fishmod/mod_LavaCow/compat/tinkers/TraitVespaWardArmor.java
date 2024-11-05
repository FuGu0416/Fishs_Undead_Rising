package com.Fishmod.mod_LavaCow.compat.tinkers;

import com.Fishmod.mod_LavaCow.init.FishItems;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import slimeknights.tconstruct.shared.client.ParticleEffect;
import slimeknights.tconstruct.tools.TinkerTools;

@SuppressWarnings("deprecation")
public class TraitVespaWardArmor extends AbstractArmorTrait {
    private static final float CHANCE = 0.3F;

    public TraitVespaWardArmor() {
        super("mod_lavacow.vespa_ward", 0x85E214);
    }

    @Override
    public float onDamaged(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingDamageEvent evt) {
        if (source.getTrueSource() instanceof EntityLivingBase && random.nextFloat() < CHANCE) {
            poisonStingDamage(armor, player, (EntityLivingBase) source.getTrueSource());
            ((EntityLivingBase) source.getTrueSource()).playSound(FishItems.RANDOM_THORN_SHOOT, 1.0F, 2.0F);

            if (!player.world.isRemote) {
                ((EntityLivingBase) source.getTrueSource()).addPotionEffect(new PotionEffect(MobEffects.POISON, 6 * 20, 0));
            }
        }

        return super.onDamaged(armor, player, source, damage, newDamage, evt);
    }

    private void poisonStingDamage(ItemStack armor, EntityPlayer player, EntityLivingBase target) {
        DamageSource damageSource = DamageSource.causeThornsDamage(player);
        int oldHurtResistantTime = target.hurtResistantTime;
        int armorDamage = 1;
        int attackDamage = random.nextInt(4) + 1;

        if (attackEntitySecondary(damageSource, attackDamage, target, true, false, false)) {
            TinkerTools.proxy.spawnEffectParticle(ParticleEffect.Type.HEART_CACTUS, target, 1);
            armorDamage = 3;
        }

        ArmorHelper.damageArmor(armor, damageSource, armorDamage, player, EntityLiving.getSlotForItemStack(armor).getIndex());
        target.hurtResistantTime = oldHurtResistantTime;
    }

}
