package com.Fishmod.fur.entities.misc;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * The floating, glowing dagger left behind when a {@link com.Fishmod.fur.entities.tameable.SpectralCutlassEntity}
 * dissipates or is slain. It ignores gravity, slowly descends until roughly a block above solid
 * ground, then hovers. It never despawns so the player's only copy can never be lost, and it is
 * fire-immune (the item itself is {@code fireResistant}). Standard vanilla pickup/merge still apply.
 */
public class SpectralCutlassItemEntity extends ItemEntity {

	public SpectralCutlassItemEntity(EntityType<? extends SpectralCutlassItemEntity> type, Level level) {
		super(type, level);
		this.setNoGravity(true);
		this.setGlowingTag(true);
		this.setUnlimitedLifetime();
	}

	@Override
	public void tick() {
		super.tick();

		if (this.level().isClientSide()) {
			return;
		}

		this.setNoGravity(true);
		// Keep resetting the despawn age so the dagger never disappears on its own.
		if (this.tickCount % 1000 == 0) {
			this.setUnlimitedLifetime();
		}

		Vec3 m = this.getDeltaMovement();
		double nx = m.x * 0.85D;
		double nz = m.z * 0.85D;
		double ny;

		BlockPos oneBelow = BlockPos.containing(this.getX(), this.getY() - 1.0D, this.getZ());
		boolean groundNear = !this.level().getBlockState(oneBelow).isAir();
		if (groundNear) {
			// Settle to a hover ~1 block above the ground.
			ny = Math.abs(m.y) < 0.02D ? 0.0D : m.y * 0.5D;
		} else {
			// Slow, steady descent toward the ground.
			ny = Math.max(m.y - 0.01D, -0.05D);
		}

		this.setDeltaMovement(nx, ny, nz);
	}

	@Override
	public boolean fireImmune() {
		return true;
	}
}
