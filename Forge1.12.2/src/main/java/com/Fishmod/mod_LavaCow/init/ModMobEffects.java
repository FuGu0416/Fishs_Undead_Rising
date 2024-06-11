package com.Fishmod.mod_LavaCow.init;

import com.Fishmod.mod_LavaCow.mobeffect.MobEffectCharmingPheromone;
import com.Fishmod.mod_LavaCow.mobeffect.MobEffectCorroded;
import com.Fishmod.mod_LavaCow.mobeffect.MobEffectFear;
import com.Fishmod.mod_LavaCow.mobeffect.MobEffectFlourished;
import com.Fishmod.mod_LavaCow.mobeffect.MobEffectFragile;
import com.Fishmod.mod_LavaCow.mobeffect.MobEffectFragileKing;
import com.Fishmod.mod_LavaCow.mobeffect.MobEffectImmolation;
import com.Fishmod.mod_LavaCow.mobeffect.MobEffectInfested;
import com.Fishmod.mod_LavaCow.mobeffect.MobEffectSoiled;
import com.Fishmod.mod_LavaCow.mobeffect.MobEffectThorned;
import com.Fishmod.mod_LavaCow.mobeffect.MobEffectVoidDust;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.util.Constants.AttributeModifierOperation;

public class ModMobEffects {
	public static final Potion CORRODED = new MobEffectCorroded();
	public static final Potion SOILED = new MobEffectSoiled();
	public static final Potion INFESTED = new MobEffectInfested().registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890", -0.1D, AttributeModifierOperation.MULTIPLY);
	public static final Potion FRAGILE = new MobEffectFragile();
	public static final Potion FRAGILE_KING = new MobEffectFragileKing().registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_DAMAGE, "2ED903A6-66A0-4C50-8A9F-E5C125A84A98", -0.1D, AttributeModifierOperation.MULTIPLY);
	public static final Potion FEAR = new MobEffectFear().registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_DAMAGE, "22653B89-116E-49DC-9B6B-9971489B5BE5", -0.2D, AttributeModifierOperation.MULTIPLY).registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", -0.2D, AttributeModifierOperation.MULTIPLY);
	public static final Potion THORNED = new MobEffectThorned();
	public static final Potion IMMOLATION = new MobEffectImmolation();
	public static final Potion CHARMING_PHEROMONE = new MobEffectCharmingPheromone();
	public static final Potion FLOURISHED = new MobEffectFlourished();
	public static final Potion VOID_DUST = new MobEffectVoidDust();
}
