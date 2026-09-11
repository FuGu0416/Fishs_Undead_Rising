package com.Fishmod.fur.block;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Sand/red sand's infested disguise: falls exactly like real sand when unsupported (inherits
 * {@code FallingBlock}'s scheduled-tick/fall trigger unchanged, so the world spawns a plain vanilla
 * {@code FallingBlockEntity} carrying this block's state, same as any other falling block) but
 * never actually settles back down as a placed block.
 *
 * <p>Landing on solid ground bursts it into wild Scarabs via {@link #onLand} instead of placing the
 * block. Touching any other entity mid-fall does the same, but vanilla has no hook for "a falling
 * block touched an entity" - {@link #falling} records each spawned {@code FallingBlockEntity}'s
 * UUID into {@link #FALLING_UUIDS}, which {@code FURServerEvents}' per-level tick handler polls
 * every tick (cheap: an empty-set check in the overwhelmingly common case where nothing of this
 * type is currently falling) to check for entity contact and, if found, does the same burst.
 */
public class InfestedFallingBlock extends FallingBlock {
	/** UUIDs of currently-falling FallingBlockEntity instances carrying one of this block's states,
	 *  across all dimensions. Entries are removed the moment an entity lands, breaks, or is found
	 *  touching something - never persisted, same as the other in-memory-only per-instance state
	 *  tracked elsewhere in FURServerEvents (a fall is a couple of seconds of flight, not something
	 *  that needs to survive a restart). */
	public static final Set<UUID> FALLING_UUIDS = ConcurrentHashMap.newKeySet();

	public InfestedFallingBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	@Override
	protected void falling(FallingBlockEntity fallingBlockEntity) {
		FALLING_UUIDS.add(fallingBlockEntity.getUUID());
	}

	@Override
	public void onLand(Level level, BlockPos pos, BlockState fallingState, BlockState hitState, FallingBlockEntity fallingBlockEntity) {
		FALLING_UUIDS.remove(fallingBlockEntity.getUUID());
		// onLand fires only after FallingBlockEntity has already placed fallingState back into the
		// world (see vanilla FallingBlockEntity#tick) - undo that placement and burst into Scarabs
		// instead of leaving the disguise sitting there intact.
		if (level instanceof ServerLevel serverLevel) {
			serverLevel.removeBlock(pos, false);
			InfestedBlock.spawnScarabs(serverLevel, pos);
		}
	}

	@Override
	public void onBrokenAfterFall(Level level, BlockPos pos, FallingBlockEntity fallingBlockEntity) {
		// Rare edge case: the landing spot was already occupied by something non-replaceable by the
		// time it got there, so FallingBlockEntity gave up on placing and drops itself as an item
		// instead (see vanilla FallingBlockEntity#tick) - just let that item drop happen rather than
		// also bursting Scarabs on top of it.
		FALLING_UUIDS.remove(fallingBlockEntity.getUUID());
	}
}
