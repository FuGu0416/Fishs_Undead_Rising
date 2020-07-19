package com.Fishmod.mod_LavaCow.compat.tinkers;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class TraitFamine extends AbstractTrait {

    public TraitFamine() {
        super("mod_lavacow.famine", TextFormatting.DARK_RED);
    }

    @Override
    public String getLocalizedName() {
        return Util.translate(String.format(LOC_Name, "mod_lavacow.famine"));
    }

    @Override
    public String getLocalizedDesc() {
        return Util.translate(String.format(LOC_Desc, "mod_lavacow.famine"));
    }
    
    @Override
    public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
		if(entity instanceof EntityPlayer && isSelected) {
			((EntityLivingBase)entity).addPotionEffect(new PotionEffect(MobEffects.HUNGER, 7 * 20, 4));
		}
    }

    @Override
    public void afterBlockBreak(ItemStack tool, World world, IBlockState state, BlockPos pos, EntityLivingBase player, boolean wasEffective) {
        if (wasEffective && player instanceof EntityPlayer) {
        	RefillHunger(player);
        }
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        if (player instanceof EntityPlayer && target.getCreatureAttribute() != EnumCreatureAttribute.UNDEAD) {
        	RefillHunger(player);
        }
    }
    
    private void RefillHunger(EntityLivingBase player) {
			((EntityPlayer)player).getFoodStats().addStats(1, 0.0F);
    }
}
