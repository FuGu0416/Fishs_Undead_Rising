package com.Fishmod.fur.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.HugeExplosionParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BansheeShriekParticle extends HugeExplosionParticle {
	protected BansheeShriekParticle(ClientLevel level, double x, double y, double z, double scale, SpriteSet sprites) {
		super(level, x, y, z, scale, sprites);
		this.lifetime = 16;
		this.quadSize = 1.5F;
		this.setSpriteFromAge(sprites);
	}

	@OnlyIn(Dist.CLIENT)
	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprites;

		public Provider(SpriteSet sprites) {
			this.sprites = sprites;
		}

		public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			return new BansheeShriekParticle(level, x, y, z, xSpeed, this.sprites);
		}
	}
}