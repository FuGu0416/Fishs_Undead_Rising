package com.Fishmod.mod_LavaCow.effect;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.math.vector.Vector3d;

public class EffectFear extends Effect {

	public EffectFear() {
        super(EffectType.HARMFUL, 0xCD5A1E);
        this.setRegistryName(mod_LavaCow.MODID, "fear");
	}
    
    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {       
        if(entityLivingBaseIn instanceof CreatureEntity) {
        	CreatureEntity mob = ((CreatureEntity) entityLivingBaseIn);
        	Vector3d vec = RandomPositionGenerator.getLandPos(mob, 20, 5);
        	mob.setTarget(null);
        	mob.setLastHurtByMob(null);
        	if(vec != null) {
        		mob.getNavigation().moveTo(vec.x, vec.y, vec.z, 1.0D);
        	}
        }
    }
    
    @Override
    public boolean isDurationEffectTick(int p_76397_1_, int p_76397_2_) {
        int i = 5 >> p_76397_2_;
        if (i > 0) {
           return p_76397_1_ % i == 0;
        } else {
           return true;
        }
    }
}
