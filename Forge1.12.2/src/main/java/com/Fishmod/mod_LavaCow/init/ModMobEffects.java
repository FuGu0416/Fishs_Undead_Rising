package com.Fishmod.mod_LavaCow.init;

import com.Fishmod.mod_LavaCow.mobeffect.MobEffectCorroded;
import com.Fishmod.mod_LavaCow.mobeffect.MobEffectInfested;
import com.Fishmod.mod_LavaCow.mobeffect.MobEffectSoiled;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraft.util.math.MathHelper;

public class ModMobEffects {
	public static final Potion CORRODED = new MobEffectCorroded();
	public static final Potion SOILED = new MobEffectSoiled();
	public static final Potion INFESTED = new MobEffectInfested().registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, MathHelper.getRandomUUID().toString(), -0.1D, 2);
}
