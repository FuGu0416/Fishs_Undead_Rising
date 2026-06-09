package com.Fishmod.fur.entities.projectiles;

import java.util.List;

import com.Fishmod.fur.init.FUREntityRegistry;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

/**
 * Molten Pool — a lingering puddle of molten rock left by a {@link MoltenGlobEntity} impact.
 *
 * <p>It is an {@link AreaEffectCloud} (so it keeps the cloud's built-in lava-particle disc and
 * timed lifetime), but instead of applying a potion it sets nearby entities on fire and deals
 * periodic fire damage — vanilla effects can't ignite, so the burn is done by hand here. Extra
 * flame/smoke particles are layered on client-side for a fierier look.
 */
public class MoltenPoolEntity extends AreaEffectCloud {

	/** Ticks between damage applications (every 0.5s). */
	private static final int DAMAGE_INTERVAL = 10;
	/** Fire damage dealt per application. */
	private static final float DAMAGE = 2.0F;
	/** Seconds an entity standing in the pool keeps burning. */
	private static final int FIRE_SECONDS = 3;

	public MoltenPoolEntity(EntityType<? extends AreaEffectCloud> type, Level level) {
		super(type, level);
	}

	public MoltenPoolEntity(Level level, double x, double y, double z) {
		this(FUREntityRegistry.MOLTEN_POOL.get(), level);
		this.setPos(x, y, z);
	}

	@Override
	public void tick() {
		// Keeps the AreaEffectCloud lifetime + lava-particle disc + auto-discard at end of duration.
		super.tick();

		if (this.isWaiting()) {
			return;
		}

		if (this.level().isClientSide) {
			this.spawnBurningParticles();
			return;
		}

		// Server: ignite and damage anything standing in the pool, on a fixed cadence.
		if (this.tickCount % DAMAGE_INTERVAL == 0) {
			this.burnEntitiesInside();
		}
	}

	private void burnEntitiesInside() {
		float radius = this.getRadius();
		LivingEntity owner = this.getOwner();
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox());
		for (LivingEntity target : list) {
			if (target == owner || !target.isAlive()) {
				continue;
			}
			double dx = target.getX() - this.getX();
			double dz = target.getZ() - this.getZ();
			if (dx * dx + dz * dz <= radius * radius) {
				// Fire-typed damage: fire-immune mobs (nether dwellers) shrug it off, like real lava.
				target.setSecondsOnFire(FIRE_SECONDS);
				target.hurt(this.damageSources().onFire(), DAMAGE);
			}
		}
	}

	private void spawnBurningParticles() {
		float radius = this.getRadius();
		if (radius <= 0.0F) {
			return;
		}
		for (int i = 0; i < 2; ++i) {
			double angle = this.random.nextDouble() * Math.PI * 2.0D;
			double dist = Math.sqrt(this.random.nextDouble()) * radius;
			double px = this.getX() + Math.cos(angle) * dist;
			double pz = this.getZ() + Math.sin(angle) * dist;
			this.level().addParticle(ParticleTypes.FLAME, px, this.getY() + 0.1D, pz, 0.0D, 0.02D, 0.0D);
			if (this.random.nextInt(3) == 0) {
				this.level().addParticle(ParticleTypes.LARGE_SMOKE, px, this.getY() + 0.2D, pz, 0.0D, 0.015D, 0.0D);
			}
		}
	}
}
