package com.Fishmod.fur.effect;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class EffectVoidDust extends MobEffect {

	public EffectVoidDust() {
        super(MobEffectCategory.HARMFUL, 0xD146FF);
	}
	
    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {       
    	double d0 = entityLivingBaseIn.getX();
    	double d1 = entityLivingBaseIn.getY();
    	double d2 = entityLivingBaseIn.getZ();
    	Level level = entityLivingBaseIn.level();
         
    	for(int i = 0; i < 16; ++i) {
            double d3 = d0 + (entityLivingBaseIn.getRandom().nextDouble() - 0.5D) * 16.0D;
            double d4 = Mth.clamp(d1 + (double)(entityLivingBaseIn.getRandom().nextInt(16) - 8), 0.0D, (double)(level.getHeight() - 1));
            double d5 = d2 + (entityLivingBaseIn.getRandom().nextDouble() - 0.5D) * 16.0D;
            
            if (entityLivingBaseIn.isPassenger()) {
            	entityLivingBaseIn.stopRiding();
            }
            
            Vec3 vec3 = entityLivingBaseIn.position();
            level.gameEvent(GameEvent.TELEPORT, vec3, GameEvent.Context.of(entityLivingBaseIn));
            net.minecraftforge.event.entity.EntityTeleportEvent.ChorusFruit event = net.minecraftforge.event.ForgeEventFactory.onChorusFruitTeleport(entityLivingBaseIn, d3, d4, d5);
            if (event.isCanceled()) return;
            if (entityLivingBaseIn.randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true)) {
            	SoundEvent soundevent = entityLivingBaseIn instanceof Fox ? SoundEvents.FOX_TELEPORT : SoundEvents.CHORUS_FRUIT_TELEPORT;
            	level.playSound((Player)null, d0, d1, d2, soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
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
