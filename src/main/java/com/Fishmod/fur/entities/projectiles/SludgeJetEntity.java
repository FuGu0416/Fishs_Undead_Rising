package com.Fishmod.fur.entities.projectiles;

import com.Fishmod.fur.init.FUREffectRegistry;
import com.Fishmod.fur.init.FURParticleRegistry;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

/**
 * Sludge Jet — the Shroomlord's default ranged glob: a slow, gravity-affected blob that
 * trails sludge particles and, on a direct hit, deals the shooter's attack damage and inflicts
 * Slowness + Blindness. Ported from 1.16.5; now built on the mod's {@link EnchantableFireBallEntity}
 * (a vanilla {@code Fireball}) so it plugs straight into {@link com.Fishmod.fur.entities.ai.FURRangeAttackGoal}.
 */
public class SludgeJetEntity extends EnchantableFireBallEntity {

	@SuppressWarnings("unchecked")
	public SludgeJetEntity(EntityType<?> entityType, Level worldIn) {
		super((EntityType<? extends SludgeJetEntity>) entityType, worldIn);
	}

	public SludgeJetEntity(EntityType<? extends SludgeJetEntity> entityType, LivingEntity shooter, double accelX, double accelY, double accelZ, Level worldIn) {
		super(entityType, shooter, accelX, accelY, accelZ, worldIn);
	}

	public SludgeJetEntity(EntityType<? extends SludgeJetEntity> entityType, double x, double y, double z, double accelX, double accelY, double accelZ, Level worldIn) {
		super(entityType, x, y, z, accelX, accelY, accelZ, worldIn);
	}

	@Override
	public void tick() {
		// Heavy glob: bleed off upward power so the shot arcs down (matches the launch curve set in the goal).
		if (!this.horizontalCollision && !this.verticalCollision)
			this.yPower -= 0.006D;

		if (this.level().isClientSide())
			for (int i = 0; i < 4 + this.random.nextInt(4); i++) {
				this.level().addParticle(FURParticleRegistry.SLUDGE_JET.get(), this.getX() + this.random.nextDouble() * 0.5D, this.getY() + 0.5D + this.random.nextDouble() * 0.5D, this.getZ() + this.random.nextDouble() * 0.5D, 0.0D, 0.0D, 0.0D);
			}

		super.tick();
	}

	@Override
	protected void onHitEntity(EntityHitResult result) {
		super.onHitEntity(result);
		Entity owner = this.getOwner();
		if (!this.level().isClientSide() && owner instanceof LivingEntity shooter && result.getEntity() instanceof LivingEntity victim && victim != owner) {
			this.setDamage((float) shooter.getAttributeValue(Attributes.ATTACK_DAMAGE));

			if (victim.hurt(this.damageSources().indirectMagic(this, shooter), this.getDamage())) {
				int local_difficulty = (int) this.level().getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
				victim.addEffect(new MobEffectInstance(FUREffectRegistry.SPOREROT.get(), 4 * 20 * local_difficulty, 3));
				this.doEnchantDamageEffects(shooter, victim);
			}
		}
		// EnchantableFireBallEntity#onHit discards the projectile after the dispatch.
	}

	/** Slows the shot far more than a straight fireball so it reads as a thrown blob. */
	@Override
	protected float getInertia() {
		return 0.33F;
	}

	@Override
	protected boolean shouldBurn() {
		return false;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		return false;
	}
}
