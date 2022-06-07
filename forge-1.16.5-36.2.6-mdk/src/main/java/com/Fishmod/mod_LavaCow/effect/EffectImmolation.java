package com.Fishmod.mod_LavaCow.effect;

import java.util.List;

import com.Fishmod.mod_LavaCow.mod_LavaCow;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class EffectImmolation extends Effect {

	public EffectImmolation() {
        super(EffectType.BENEFICIAL, 0xEA8C15);
        this.setRegistryName(mod_LavaCow.MODID, "immolation");
	}
	
    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {       
    	List<Entity> list = entityLivingBaseIn.level.getEntitiesOfClass(LivingEntity.class, entityLivingBaseIn.getBoundingBox().inflate(2.0D));
    	for (Entity entity1 : list) {
    		if (entity1 instanceof LivingEntity && !entity1.equals(entityLivingBaseIn)) {
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
