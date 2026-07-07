package com.Fishmod.fur.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

/**
 * Sporerot: a harmful fungal affliction that reproduces Hunger I + Slowness I directly, rather than
 * bundling the real MobEffects.
 *
 * <p>The Slowness I slowdown is a MOVEMENT_SPEED attribute modifier (-15%, MULTIPLY_TOTAL) attached to
 * this effect at registration (see {@code FUREffectRegistry}). The Hunger I drain is reproduced here in
 * {@link #applyEffectTick} via food exhaustion at the same rate as vanilla Hunger. Driving the behaviour
 * from the effect class keeps it self-contained and consistent with the other FUR effects (e.g.
 * EffectInfested, which likewise pairs a movement modifier with exhaustion in applyEffectTick).
 */
public class EffectSporerot extends MobEffect {

    public EffectSporerot() {
        super(MobEffectCategory.HARMFUL, 0x0BFFFA);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        // Hunger I behaviour: drain food exhaustion each tick at vanilla Hunger's rate (0.005 per level).
        if (entityLivingBaseIn instanceof Player player) {
            player.causeFoodExhaustion(0.005F * (float)(amplifier + 1));
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        // Tick every game tick so the exhaustion above is applied continuously.
        return true;
    }
}
