package com.Fishmod.fur.entities.projectiles;

import java.util.List;

import com.Fishmod.fur.init.FUREntityRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraftforge.event.ForgeEventFactory;

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
	/** 1-in-N chance per candidate ground block to catch fire when the pool forms. */
	private static final int IGNITE_CHANCE = 3;

	/** Whether the one-time spawn ignition has run (server only). */
	private boolean ignited = false;

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
			// Continuous campfire-style crackle while the pool burns (matches vanilla campfire cadence).
			if (this.random.nextInt(10) == 0) {
				this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 0.5F + this.random.nextFloat(), this.random.nextFloat() * 0.7F + 0.6F, false);
			}
			return;
		}

		// Server: scatter fire across the ground once when the pool first forms.
		if (!this.ignited) {
			this.ignited = true;
			this.igniteBlocksInRange();
		}

		// Server: ignite and damage anything standing in the pool, on a fixed cadence.
		if (this.tickCount % DAMAGE_INTERVAL == 0) {
			this.burnEntitiesInside();
		}
	}

	/** Randomly scorches the ground beneath/around the pool, gated by the mob-griefing gamerule. */
	private void igniteBlocksInRange() {
		LivingEntity owner = this.getOwner();
		// Respect mob griefing: mob-thrown globs only scorch the world when the gamerule allows it.
		if (owner instanceof Mob && !ForgeEventFactory.getMobGriefingEvent(this.level(), owner)) {
			return;
		}

		float radius = this.getRadius();
		int r = Mth.ceil(radius);
		BlockPos center = this.blockPosition();
		for (BlockPos pos : BlockPos.betweenClosed(center.offset(-r, -1, -r), center.offset(r, 1, r))) {
			double dx = pos.getX() + 0.5D - this.getX();
			double dz = pos.getZ() + 0.5D - this.getZ();
			if (dx * dx + dz * dz > radius * radius) {
				continue;
			}
			if (this.random.nextInt(IGNITE_CHANCE) != 0) {
				continue;
			}
			// Only place fire in an empty cell sitting on a block that can actually hold it.
			if (this.level().isEmptyBlock(pos) && this.level().getBlockState(pos.below()).isFaceSturdy(this.level(), pos.below(), net.minecraft.core.Direction.UP)) {
				this.level().setBlockAndUpdate(pos, BaseFireBlock.getState(this.level(), pos));
			}
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
		// Soul-skin pools (set with the soul-fire disc by the glob) burn with soul fire instead of flame.
		boolean soul = this.getParticle().getType() == ParticleTypes.SOUL_FIRE_FLAME;
		for (int i = 0; i < 2; ++i) {
			double angle = this.random.nextDouble() * Math.PI * 2.0D;
			double dist = Math.sqrt(this.random.nextDouble()) * radius;
			double px = this.getX() + Math.cos(angle) * dist;
			double pz = this.getZ() + Math.sin(angle) * dist;
			this.level().addParticle(soul ? ParticleTypes.SOUL_FIRE_FLAME : ParticleTypes.FLAME, px, this.getY() + 0.1D, pz, 0.0D, 0.02D, 0.0D);
			if (this.random.nextInt(3) == 0) {
				this.level().addParticle(ParticleTypes.LARGE_SMOKE, px, this.getY() + 0.2D, pz, 0.0D, 0.015D, 0.0D);
			}
		}
	}
}
