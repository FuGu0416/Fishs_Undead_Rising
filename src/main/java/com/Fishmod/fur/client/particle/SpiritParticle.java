package com.Fishmod.fur.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.RisingParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * The bespoke "soul wisp" particle discussed as a replacement for reusing vanilla {@code ParticleTypes.SOUL}
 * everywhere - currently just a plain (untinted) rising wisp, mirroring {@link FearParticle}'s structure.
 * Not wired into any effect yet (e.g. Ghostly Armor's dodge/spirit-form particles still use vanilla SOUL).
 */
public class SpiritParticle extends RisingParticle {
	private final SpriteSet sprites;

	private SpiritParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites) {
		super(level, x, y, z, xSpeed, ySpeed, zSpeed);
		this.sprites = sprites;
		this.scale(1.5F);
		this.setSpriteFromAge(sprites);
	}

	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
	}

	public void tick() {
		super.tick();
		if (!this.removed) {
			this.setSpriteFromAge(this.sprites);
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static class Factory implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprite;

		public Factory(SpriteSet sprite) {
			this.sprite = sprite;
		}

		public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			SpiritParticle spiritparticle = new SpiritParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, this.sprite);
			spiritparticle.setAlpha(1.0F);
			return spiritparticle;
		}
	}
}
