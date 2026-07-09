package com.Fishmod.fur.core;

import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;

/**
 * Shared constants and helpers for the Vespa Ovum infestation mechanic.
 *
 * The infestation state lives on the host's {@link LivingEntity#getPersistentData()} so it
 * survives without a dedicated capability: {@link #TICKS_KEY} counts down the incubation and
 * {@link #STAGE_KEY} caches the derived stage (0/1/2) so client-facing particle density can be
 * read without recomputing it from the remaining ticks.
 */
public final class VespaInfestation {
	/** Remaining incubation ticks (int). */
	public static final String TICKS_KEY = "fur:infest_ticks";
	/** Cached stage 0/1/2 derived from the remaining ticks (int). */
	public static final String STAGE_KEY = "fur:infest_stage";
	/** UUID of the player who injected the ovum; the emerged brood is tamed to them. */
	public static final String OWNER_KEY = "fur:infest_owner";
	/** Incubation length applied on injection. */
	public static final int INITIAL_TICKS = 1200;

	private VespaInfestation() {}

	/** True while an infestation is incubating on the host. */
	public static boolean isInfested(LivingEntity entity) {
		return entity.getPersistentData().contains(TICKS_KEY);
	}

	/** Derive the particle-density stage from the remaining incubation ticks: {@code >800 = 0, >400 = 1, else 2}. */
	public static int stageForTicks(int ticks) {
		if (ticks > 800) {
			return 0;
		} else if (ticks > 400) {
			return 1;
		} else {
			return 2;
		}
	}

	/** Seed the host with a fresh infestation, remembering the injecting player as the future owner. */
	public static void inject(LivingEntity target, @Nullable UUID owner) {
		CompoundTag data = target.getPersistentData();
		data.putInt(TICKS_KEY, INITIAL_TICKS);
		data.putInt(STAGE_KEY, stageForTicks(INITIAL_TICKS));
		if (owner != null) {
			data.putUUID(OWNER_KEY, owner);
		}
	}

	/** UUID of the player who injected the ovum, or null if none was recorded. */
	@Nullable
	public static UUID getOwner(LivingEntity target) {
		CompoundTag data = target.getPersistentData();
		return data.hasUUID(OWNER_KEY) ? data.getUUID(OWNER_KEY) : null;
	}

	/** Strip all infestation state from the host. */
	public static void clear(LivingEntity target) {
		CompoundTag data = target.getPersistentData();
		data.remove(TICKS_KEY);
		data.remove(STAGE_KEY);
		data.remove(OWNER_KEY);
	}
}
