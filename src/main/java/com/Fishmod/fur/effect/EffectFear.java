package com.Fishmod.fur.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

public class EffectFear extends MobEffect {

	public EffectFear() {
        super(MobEffectCategory.HARMFUL, 0xCD5A1E);
	}
    
    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {       
        if(entityLivingBaseIn instanceof PathfinderMob) {
        	PathfinderMob mob = ((PathfinderMob) entityLivingBaseIn);
        	Vec3 vec = LandRandomPos.getPos(mob, 20, 5);
        	mob.setTarget(null);
        	mob.setLastHurtByMob(null);
        	if(vec != null) {
        		mob.getNavigation().moveTo(vec.x, vec.y, vec.z, 1.0D);
        	}
        }
    }
    
    @Override
    public boolean isDurationEffectTick(int p_76397_1_, int p_76397_2_) {
        int i = 10;
        if (i > 0) {
           return p_76397_1_ % i == 0;
        } else {
           return true;
        }
    }
}
