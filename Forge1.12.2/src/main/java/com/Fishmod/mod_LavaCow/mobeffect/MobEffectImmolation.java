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
    public void performEffect(EntityLivingBase entity, int amplifier) {
        List<Entity> list = entity.world.getEntitiesWithinAABB(EntityLivingBase.class, entity.getEntityBoundingBox().grow(3.0D));

        for (Entity target : list) {
            if (target instanceof EntityLivingBase && !target.isImmuneToFire() && !target.isOnSameTeam(entity) && target != entity && !(target instanceof EntityAnimal)) {
                target.setFire(3 + 3 * amplifier);
            }
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        int i = 10 >> amplifier;

        if (i > 0) {
            return duration % i == 0;
        } else {
            return true;
        }
    }
}
