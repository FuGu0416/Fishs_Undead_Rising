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
	private LocustSwarmParticle(ClientLevel level, double x, double y, double z) {
		super(level, x, y, z, 0.0D, 0.0D, 0.0D);
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

	public float getQuadSize(float partialTick) {
		return this.quadSize * Mth.clamp(((float)this.age + partialTick) / (float)this.lifetime * 32.0F, 0.0F, 1.0F);
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

		public Factory(SpriteSet sprite) {
			this.sprite = sprite;
		}

		public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			LocustSwarmParticle heartparticle = new LocustSwarmParticle(level, x, y, z);
			heartparticle.pickSprite(this.sprite);
			return heartparticle;
		}
	}
}
