package com.Fishmod.fur.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GastroAcidParticle extends SimpleAnimatedParticle {

	private GastroAcidParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, float Red, float Green, float Blue, float Offset, SpriteSet sprites) {	
		super(level, x, y, z, sprites, 0.0F);
		float colorOffset = this.random.nextFloat() * Offset;
		
		this.quadSize = 0.5F;
		this.setAlpha(1.0F);
		this.setColor(Red, Green - colorOffset, Blue - colorOffset);
		this.lifetime = (int)((double)(this.quadSize * 12.0F) / (Math.random() * (double)0.8F + (double)0.2F));
		this.setSpriteFromAge(sprites);
		this.hasPhysics = false;
		this.xd = xSpeed;
		this.yd = ySpeed;
		this.zd = zSpeed;
		this.friction = 0.0F;
	}

	public void tick() {
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		if (this.age++ >= this.lifetime) {
			this.remove();
		} else {
			this.setSpriteFromAge(this.sprites);
			if (this.age > this.lifetime / 2) {
				this.setAlpha(1.0F - ((float)this.age - (float)(this.lifetime / 2)) / (float)this.lifetime);
			}

			this.move(this.xd, this.yd, this.zd);
			if (this.level.getBlockState(BlockPos.containing(this.x, this.y, this.z)).isAir()) {
				this.yd -= (double)0.008F;
			}

			this.xd *= (double)0.92F;
			this.yd *= (double)0.92F;
			this.zd *= (double)0.92F;
			if (this.onGround) {
				this.xd *= (double)0.7F;
				this.zd *= (double)0.7F;
			}
		}
	}
   
	@OnlyIn(Dist.CLIENT)
	public static class GastroAcidFactory implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprites;

		public GastroAcidFactory(SpriteSet sprites) {
			this.sprites = sprites;
		}

		public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			return new GastroAcidParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, 0.56F, 0.42F, 0.42F, 0.37F, this.sprites);
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	public static class SludgeJetFactory implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprites;

		public SludgeJetFactory(SpriteSet sprites) {
			this.sprites = sprites;
		}

		public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			return new GastroAcidParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, 0.0F, 0.3F, 0.5F, 0.12F, this.sprites);
		}
	}
	
}
