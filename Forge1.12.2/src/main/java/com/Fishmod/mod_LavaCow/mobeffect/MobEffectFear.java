package com.Fishmod.mod_LavaCow.mobeffect;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.Vec3d;

public class MobEffectFear extends MobEffectMod {

	public MobEffectFear() {
		super("fear", true, 205, 90, 30);
	}
	
	@Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
        if(entityLivingBaseIn instanceof EntityCreature) {
        	EntityCreature mob = ((EntityCreature) entityLivingBaseIn);
        	Vec3d vec = RandomPositionGenerator.getLandPos(mob, 20, 5);
        	mob.setAttackTarget(null);
        	mob.setRevengeTarget(null);
        	if(vec != null) {
        		mob.getNavigator().tryMoveToXYZ(vec.x, vec.y, vec.z, 1.35D);
        	}
        }
    }
	
    @Override
    public boolean isReady(int duration, int amplifier) {
        int i = 10;
        if (i > 0) {
           return duration % i == 0;
        } else {
           return true;
        }
    }
}
