package com.Fishmod.fur.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

/**
 * Hidden marker effect for the Ghostly Armor full-set "spirit form" state (see
 * {@link com.Fishmod.fur.item.GhostlyArmorItem}). Carries no gameplay logic of its own — it is applied
 * with {@code showIcon}/{@code showParticles} both false (no vanilla HUD icon or swirl; see
 * PLACEHOLDERS.md for a real icon eventually replacing that), and every bit of actual behavior
 * (invulnerability, blocking attacks/item use, the translucent render) is driven off
 * {@code hasEffect(SPIRIT_FORM)} elsewhere, both server- and client-side. Its own duration countdown is
 * all that's needed to time the state out automatically.
 */
public class EffectSpiritForm extends MobEffect {

	public EffectSpiritForm() {
		super(MobEffectCategory.BENEFICIAL, 0xB0A0FF);
	}
}
