package com.Fishmod.fur.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

public class LocustSwarmParticle extends TextureSheetParticle  {
	private LocustSwarmParticle(ClientLevel p_i232394_1_, double p_i232394_2_, double p_i232394_4_, double p_i232394_6_) {
		super(p_i232394_1_, p_i232394_2_, p_i232394_4_, p_i232394_6_, 0.0D, 0.0D, 0.0D);
		this.xd *= 0.10000000149011612D;
		this.yd *= 0.10000000149011612D;
		this.zd *= 0.10000000149011612D;
		this.quadSize *= 0.2F + this.random.nextFloat() * 0.55F;
		this.lifetime = (int) (5.0D / (Math.random() * 0.8D + 0.2D));
		this.hasPhysics = false;
		this.roll = (float)Math.random() * ((float)Math.PI * 0.4F);
	}

	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	public float getQuadSize(float p_217561_1_) {
		return this.quadSize * Mth.clamp(((float)this.age + p_217561_1_) / (float)this.lifetime * 32.0F, 0.0F, 1.0F);
	}

	public void tick() {
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		if (this.age++ >= this.lifetime) {
			this.remove();
		} else {
			this.move(this.xd, this.yd, this.zd);
			
			this.xd *= 1.015D;
			this.yd *= 0.9599999785423279D;
			this.zd *= 0.9599999785423279D;
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static class Factory implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprite;

		public Factory(SpriteSet p_i50747_1_) {
			this.sprite = p_i50747_1_;
		}

		public Particle createParticle(SimpleParticleType p_199234_1_, ClientLevel p_199234_2_, double p_199234_3_, double p_199234_5_, double p_199234_7_, double p_199234_9_, double p_199234_11_, double p_199234_13_) {
			LocustSwarmParticle heartparticle = new LocustSwarmParticle(p_199234_2_, p_199234_3_, p_199234_5_, p_199234_7_);
			heartparticle.pickSprite(this.sprite);
			return heartparticle;
		}
	}
}
