package com.Fishmod.fur.entities.projectiles;

import com.Fishmod.fur.init.FUREffectRegistry;
import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURParticleRegistry;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

/**
 * Death Coil — a short-lived Wither-flame projectile fired by the Forsaken Staff (and the Forsaken
 * mob). On a living hit it deals its damage and applies Wither + Fragile scaled by local difficulty;
 * it self-destructs after 30 ticks. Ported from the 1.16.5 module to 1.20.1 Mojmap.
 */
public class DeathCoilEntity extends AbstractHurtingProjectile {
	private float damage = 4.0F;
	protected int knockbackStrength;

	public DeathCoilEntity(EntityType<? extends DeathCoilEntity> type, Level level) {
		super(type, level);
	}

	public DeathCoilEntity(Level level, LivingEntity shooter, double x, double y, double z) {
		super(FUREntityRegistry.DEATHCOIL.get(), shooter, x, y, z, level);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.tickCount >= 30) {
			this.discard();
		}
	}

	@Override
	protected void onHitEntity(EntityHitResult result) {
		super.onHitEntity(result);
		Entity shooter = this.getOwner();
		Entity target = result.getEntity();

		if (!this.level().isClientSide() && target instanceof LivingEntity && shooter != null && target != shooter) {
			if (!(shooter instanceof Player)) {
				this.setDamage((float) ((LivingEntity) shooter).getAttributeValue(Attributes.ATTACK_DAMAGE));
			}

			if (target.hurt(this.damageSources().mobProjectile(this, (LivingEntity) shooter), this.getDamage())) {
				float localDifficulty = this.level().getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
				((LivingEntity) target).addEffect(new MobEffectInstance(MobEffects.WITHER, 10 * 20 * (int) localDifficulty, 1));
				((LivingEntity) target).addEffect(new MobEffectInstance(FUREffectRegistry.FRAGILE.get(), 10 * 20 * (int) localDifficulty, 2));

				if (this.isOnFire()) {
					target.setSecondsOnFire(5);
				}

				if (this.knockbackStrength > 0) {
					Vec3 vec = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale((double) this.knockbackStrength * 0.6D);
					if (vec.lengthSqr() > 0.0D) {
						target.push(vec.x, 0.1D, vec.z);
					}
				}

				if (shooter instanceof LivingEntity) {
					this.doEnchantDamageEffects((LivingEntity) shooter, target);
				}
			}

			this.discard();
		}
	}

	@Override
	protected void onHit(HitResult result) {
		super.onHit(result);
		if (!this.level().isClientSide()) {
			this.discard();
		}
	}

	@Override
	public boolean isPickable() {
		return false;
	}

	@Override
	protected boolean shouldBurn() {
		return false;
	}

	public void setDamage(float damageIn) {
		this.damage = damageIn;
	}

	public float getDamage() {
		return this.damage;
	}

	/** Sets the amount of knockback the coil applies when it hits a mob. */
	public void setKnockbackStrength(int knockbackStrengthIn) {
		this.knockbackStrength = knockbackStrengthIn;
	}

	@Override
	protected ParticleOptions getTrailParticle() {
		return this.isOnFire() ? ParticleTypes.FLAME : FURParticleRegistry.WITHER_FLAME.get();
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
