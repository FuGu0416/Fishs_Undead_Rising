package com.Fishmod.mod_LavaCow.client.particle;

import net.minecraft.block.material.Material;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GastroAcidParticle extends SimpleAnimatedParticle {

	private GastroAcidParticle(ClientWorld p_i232435_1_, double p_i232435_2_, double p_i232435_4_, double p_i232435_6_, double p_i232435_8_, double p_i232435_10_, double p_i232435_12_, float Red, float Green, float Blue, float Offset, IAnimatedSprite p_i232435_14_) {	
		super(p_i232435_1_, p_i232435_2_, p_i232435_4_, p_i232435_6_, p_i232435_14_, 0.0F);
		float colorOffset = this.random.nextFloat() * Offset;
		
		this.quadSize = 0.5F;
		this.setAlpha(1.0F);
		this.setColor(Red, Green - colorOffset, Blue - colorOffset);
		this.lifetime = (int)((double)(this.quadSize * 12.0F) / (Math.random() * (double)0.8F + (double)0.2F));
		this.setSpriteFromAge(p_i232435_14_);
		this.hasPhysics = false;
		this.xd = p_i232435_8_;
		this.yd = p_i232435_10_;
		this.zd = p_i232435_12_;
		this.setBaseAirFriction(0.0F);
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
			if (this.level.getBlockState(new BlockPos(this.x, this.y, this.z)).getMaterial().equals(Material.AIR)) {
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
	public static class GastroAcidFactory implements IParticleFactory<BasicParticleType> {
		private final IAnimatedSprite sprites;

		public GastroAcidFactory(IAnimatedSprite p_i50599_1_) {
			this.sprites = p_i50599_1_;
		}

		public Particle createParticle(BasicParticleType p_199234_1_, ClientWorld p_199234_2_, double p_199234_3_, double p_199234_5_, double p_199234_7_, double p_199234_9_, double p_199234_11_, double p_199234_13_) {
			return new GastroAcidParticle(p_199234_2_, p_199234_3_, p_199234_5_, p_199234_7_, p_199234_9_, p_199234_11_, p_199234_13_, 0.56F, 0.42F, 0.42F, 0.37F, this.sprites);
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	public static class SludgeJetFactory implements IParticleFactory<BasicParticleType> {
		private final IAnimatedSprite sprites;

		public SludgeJetFactory(IAnimatedSprite p_i50599_1_) {
			this.sprites = p_i50599_1_;
		}

		public Particle createParticle(BasicParticleType p_199234_1_, ClientWorld p_199234_2_, double p_199234_3_, double p_199234_5_, double p_199234_7_, double p_199234_9_, double p_199234_11_, double p_199234_13_) {
			return new GastroAcidParticle(p_199234_2_, p_199234_3_, p_199234_5_, p_199234_7_, p_199234_9_, p_199234_11_, p_199234_13_, 0.0F, 0.3F, 0.5F, 0.12F, this.sprites);
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	public static class SapJetFactory implements IParticleFactory<BasicParticleType> {
		private final IAnimatedSprite sprites;

		public SapJetFactory(IAnimatedSprite p_i50599_1_) {
			this.sprites = p_i50599_1_;
		}

		public Particle createParticle(BasicParticleType p_199234_1_, ClientWorld p_199234_2_, double p_199234_3_, double p_199234_5_, double p_199234_7_, double p_199234_9_, double p_199234_11_, double p_199234_13_) {
			return new GastroAcidParticle(p_199234_2_, p_199234_3_, p_199234_5_, p_199234_7_, p_199234_9_, p_199234_11_, p_199234_13_, 1.0F, 0.68F, 0.20F, 0.19F, this.sprites);
		}
	}
}
