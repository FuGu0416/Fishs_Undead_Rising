package com.Fishmod.mod_LavaCow.effect;

import com.Fishmod.mod_LavaCow.mod_LavaCow;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;

public class EffectFragile extends Effect {	
	public EffectFragile() {
        super(EffectType.HARMFUL, 0x35272A);
        this.setRegistryName(mod_LavaCow.MODID, "fragile");
	}
    
    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        float maxHealth = entityLivingBaseIn.getMaxHealth();
        
        if ((entityLivingBaseIn.getHealth() / maxHealth) < (0.05f * (amplifier + 1))) {
            if (entityLivingBaseIn instanceof PlayerEntity) {
            	PlayerEntity player = (PlayerEntity) entityLivingBaseIn;
                if (player.isSpectator() || player.isCreative())
                    return;
            }
            entityLivingBaseIn.hurt(DamageSource.WITHER.bypassInvul().bypassArmor(), maxHealth);
        }
    }
    
    @Override
    public boolean isDurationEffectTick(int p_76397_1_, int p_76397_2_) {
       return true;
    }

}
