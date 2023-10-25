package com.Fishmod.mod_LavaCow.effect;

import com.Fishmod.mod_LavaCow.mod_LavaCow;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;

public class EffectVoidScales extends Effect {

	public EffectVoidScales() {
        super(EffectType.HARMFUL, 0xD146FF);
        this.setRegistryName(mod_LavaCow.MODID, "void_scales");
	}
	
    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {       
    	double d0 = entityLivingBaseIn.getX();
    	double d1 = entityLivingBaseIn.getY();
    	double d2 = entityLivingBaseIn.getZ();
         
    	for(int i = 0; i < 16; ++i) {
            double d3 = d0 + (entityLivingBaseIn.getRandom().nextDouble() - 0.5D) * 16.0D;
            double d4 = MathHelper.clamp(d1 + (double)(entityLivingBaseIn.getRandom().nextInt(16) - 8), 0.0D, (double)(entityLivingBaseIn.level.getHeight() - 1));
            double d5 = d2 + (entityLivingBaseIn.getRandom().nextDouble() - 0.5D) * 16.0D;
            
            if (entityLivingBaseIn.isPassenger()) {
            	entityLivingBaseIn.stopRiding();
            }

            net.minecraftforge.event.entity.living.EntityTeleportEvent.ChorusFruit event = net.minecraftforge.event.ForgeEventFactory.onChorusFruitTeleport(entityLivingBaseIn, d3, d4, d5);
            if (event.isCanceled()) return;
            if (entityLivingBaseIn.randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true)) {
            	SoundEvent soundevent = entityLivingBaseIn instanceof FoxEntity ? SoundEvents.FOX_TELEPORT : SoundEvents.CHORUS_FRUIT_TELEPORT;
            	entityLivingBaseIn.level.playSound((PlayerEntity)null, d0, d1, d2, soundevent, SoundCategory.PLAYERS, 1.0F, 1.0F);
            	entityLivingBaseIn.playSound(soundevent, 1.0F, 1.0F);
            	break;
            }
    	}			       
    }
    
    @Override
    public boolean isDurationEffectTick(int p_76397_1_, int p_76397_2_) {
        int i = (20 * 10) >> p_76397_2_;
        if (i > 0) {
           return p_76397_1_ % i == 0;
        } else {
           return p_76397_1_ % 20 == 0;
        }
    }
}
