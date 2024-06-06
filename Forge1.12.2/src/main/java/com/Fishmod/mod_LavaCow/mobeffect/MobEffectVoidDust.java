package com.Fishmod.mod_LavaCow.mobeffect;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;

public class MobEffectVoidDust extends MobEffectMod {

	public MobEffectVoidDust() {
		super("void_dust", true, 209, 70, 255);
	}
	
    @Override
    public boolean isReady(int duration, int amplifier)
    {
        int i = (20 * 10) >> amplifier;
        if (i > 0) {
           return duration % i == 0;
        } else {
           return duration % 20 == 0;
        }
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        double d0 = entity.posX;
        double d1 = entity.posY;
        double d2 = entity.posZ;

        for (int i = 0; i < 16; ++i) {
            double d3 = entity.posX + (entity.world.rand.nextDouble() - 0.5D) * 16.0D;
            double d4 = MathHelper.clamp(entity.posY + (double) (entity.world.rand.nextInt(32) - 8), 0.0D, entity.world.getActualHeight() - 1);
            double d5 = entity.posZ + (entity.world.rand.nextDouble() - 0.5D) * 16.0D;

            if (entity.isRiding()) {
                entity.dismountRidingEntity();
            }

            EnderTeleportEvent event = new EnderTeleportEvent(((EntityLivingBase) entity), d3, d4, d5, 0);
            if (event.isCanceled() || entity.isDead || !entity.isNonBoss() || entity instanceof EntityEnderman || entity instanceof EntityEndermite || entity instanceof EntityShulker) return;
            if (((EntityLivingBase) entity).attemptTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ())) {
                entity.world.playSound(null, d0, d1, d2, SoundEvents.ENTITY_ILLAGER_MIRROR_MOVE, SoundCategory.PLAYERS, 1.0F, 1.0F);
                entity.playSound(SoundEvents.ENTITY_ILLAGER_MIRROR_MOVE, 1.0F, 1.0F);
                ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 30, amplifier));
                break;
            }
        }
    }
}
