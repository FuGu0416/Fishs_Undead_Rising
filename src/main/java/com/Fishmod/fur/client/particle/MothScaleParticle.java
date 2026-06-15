package com.Fishmod.fur.client.particle;

import org.joml.Vector3f;

import com.Fishmod.fur.particle.MothScaleOptions;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * END_ROD-styled particle that takes its tint from {@link MothScaleOptions} (RGB + scale).
 *
 * <p>It mirrors vanilla {@link net.minecraft.client.particle.EndRodParticle} (same gravity,
 * lifetime and end_rod sprite) but, because that class' constructor is package-private, we
 * extend {@link SimpleAnimatedParticle} directly and apply the supplied color via
 * {@link #setColor(float, float, float)}. No fade color is set, so the chosen RGB is preserved
 * for the particle's whole life.
 */
@OnlyIn(Dist.CLIENT)
public class MothScaleParticle extends SimpleAnimatedParticle {
	private MothScaleParticle(ClientLevel level, double x, double y, double z, double dx, double dy, double dz, float scale, Vector3f color, SpriteSet sprites) {
		super(level, x, y, z, sprites, 0.0125F);
		this.xd = dx;
		this.yd = dy;
		this.zd = dz;
		this.quadSize *= 0.75F * scale;
		this.lifetime = 60 + this.random.nextInt(12);
		this.setColor(color.x(), color.y(), color.z());
		this.setSpriteFromAge(sprites);
	}

	public void move(double dx, double dy, double dz) {
		this.setBoundingBox(this.getBoundingBox().move(dx, dy, dz));
		this.setLocationFromBoundingbox();
	}

	@OnlyIn(Dist.CLIENT)
	public static class Provider implements ParticleProvider<MothScaleOptions> {
		private final SpriteSet sprites;

		public Provider(SpriteSet sprites) {
			this.sprites = sprites;
		}

		public Particle createParticle(MothScaleOptions options, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
			return new MothScaleParticle(level, x, y, z, dx, dy, dz, options.getScale(), options.getColor(), this.sprites);
		}
	}
}
