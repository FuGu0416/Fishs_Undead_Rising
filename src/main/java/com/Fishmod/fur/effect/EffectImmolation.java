package com.Fishmod.fur.effect;

import java.util.List;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class EffectImmolation extends MobEffect {

	public EffectImmolation() {
        super(MobEffectCategory.BENEFICIAL, 0xEA8C15);
	}
	
    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {       
    	List<LivingEntity> list = entityLivingBaseIn.level().getEntitiesOfClass(LivingEntity.class, entityLivingBaseIn.getBoundingBox().inflate(2.0D));
    	for (LivingEntity entity1 : list) {
    		if (!entity1.isAlliedTo(entityLivingBaseIn) && entity1 != entityLivingBaseIn) {
    			entity1.setSecondsOnFire(2 + amplifier);
    		}
    	}        
    }
    
    @Override
    public boolean isDurationEffectTick(int p_76397_1_, int p_76397_2_) {
        int i = 20;
        if (i > 0) {
           return p_76397_1_ % i == 0;
        } else {
           return true;
        }
    }
}
