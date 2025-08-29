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

public class FearParticle extends RisingParticle {
	private final SpriteSet sprites;

	private FearParticle(ClientLevel p_i232426_1_, double p_i232426_2_, double p_i232426_4_, double p_i232426_6_, double p_i232426_8_, double p_i232426_10_, double p_i232426_12_, SpriteSet p_i232426_14_) {
		super(p_i232426_1_, p_i232426_2_, p_i232426_4_, p_i232426_6_, p_i232426_8_, p_i232426_10_, p_i232426_12_);
		this.sprites = p_i232426_14_;
		this.scale(1.5F);
		this.setSpriteFromAge(p_i232426_14_);
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

		public Factory(SpriteSet p_i232428_1_) {
			this.sprite = p_i232428_1_;
		}

		public Particle createParticle(SimpleParticleType p_199234_1_, ClientLevel p_199234_2_, double p_199234_3_, double p_199234_5_, double p_199234_7_, double p_199234_9_, double p_199234_11_, double p_199234_13_) {
			FearParticle fearparticle = new FearParticle(p_199234_2_, p_199234_3_, p_199234_5_, p_199234_7_, p_199234_9_, p_199234_11_, p_199234_13_, this.sprite);
			fearparticle.setAlpha(1.0F);
			return fearparticle;
		}
	}
}