package com.Fishmod.mod_LavaCow.mobeffect;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;

public class MobEffectImmolation extends MobEffectMod {

	public MobEffectImmolation() {
		super("immolation", false, 234, 140, 21);
	}
	
	@Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
		List<Entity> list = entityLivingBaseIn.world.getEntitiesWithinAABB(EntityLivingBase.class, entityLivingBaseIn.getEntityBoundingBox().grow(2.0D));
    	for (Entity entity1 : list) {
    		if (entity1 instanceof EntityLivingBase && !entity1.isOnSameTeam(entityLivingBaseIn) && entity1 != entityLivingBaseIn && !(entity1 instanceof EntityAnimal)) {
    			entity1.setFire(2 + amplifier);
    		}
    	}
    }
	
    @Override
    public boolean isReady(int duration, int amplifier) {
        int i = 20;
        if (i > 0) {
           return duration % i == 0;
        } else {
           return true;
        }
    }
}
