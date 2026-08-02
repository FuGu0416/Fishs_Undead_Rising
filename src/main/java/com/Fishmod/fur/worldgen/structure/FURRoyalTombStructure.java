package com.Fishmod.fur.worldgen.structure;

import java.util.Optional;

import com.Fishmod.fur.init.FURStructureTypeRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

/**
 * Drop-in replacement for {@code minecraft:jigsaw} used only by {@code royal_tomb} - identical
 * JSON schema and placement behaviour (delegates straight to {@link JigsawPlacement#addPieces}),
 * but adds an {@link #afterPlace} pass that vanilla jigsaw structures don't have.
 *
 * <p>{@code terrain_adaptation} (beard_thin/beard_box/bury) only ever *adds* terrain to stop a
 * piece from floating over a gap - it cannot remove terrain that is taller than the structure,
 * which is exactly the desert case here: dunes generate after the tomb's sunken courtyard is
 * carved, leaving a sharp vertical sand cliff around the exposed opening. This class carves a
 * smoothstep-eased ramp in the ring of blocks just outside the structure's own bounding box,
 * tapering from whatever height the structure's own edge happens to be (sampled live, so it
 * adapts to the actual built geometry without any hardcoded coordinates) down to the untouched
 * natural terrain a few blocks out - so re-editing/re-exporting the build never requires
 * re-tuning this class.
 */
public final class FURRoyalTombStructure extends Structure {
	public static final Codec<FURRoyalTombStructure> CODEC = ExtraCodecs.validate(RecordCodecBuilder.mapCodec((instance) -> {
		return instance.group(settingsCodec(instance),
				StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter((s) -> s.startPool),
				ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter((s) -> s.startJigsawName),
				Codec.intRange(0, 7).fieldOf("size").forGetter((s) -> s.maxDepth),
				HeightProvider.CODEC.fieldOf("start_height").forGetter((s) -> s.startHeight),
				Codec.BOOL.fieldOf("use_expansion_hack").forGetter((s) -> s.useExpansionHack),
				Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter((s) -> s.projectStartToHeightmap),
				Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter((s) -> s.maxDistanceFromCenter))
				.apply(instance, FURRoyalTombStructure::new);
	}), FURRoyalTombStructure::verifyRange).codec();

	/** How far outside the structure's own footprint the carved slope extends. Must stay well
	 *  under 12 - {@link Structure#adjustBoundingBox} only inflates the chunk-coverage box (and
	 *  therefore how far out {@link #afterPlace} even gets called) by 12 blocks when terrain
	 *  adaptation is enabled. */
	private static final int SLOPE_RADIUS = 6;

	private final Holder<StructureTemplatePool> startPool;
	private final Optional<ResourceLocation> startJigsawName;
	private final int maxDepth;
	private final HeightProvider startHeight;
	private final boolean useExpansionHack;
	private final Optional<Heightmap.Types> projectStartToHeightmap;
	private final int maxDistanceFromCenter;

	public FURRoyalTombStructure(Structure.StructureSettings settings, Holder<StructureTemplatePool> startPool, Optional<ResourceLocation> startJigsawName,
			int maxDepth, HeightProvider startHeight, boolean useExpansionHack, Optional<Heightmap.Types> projectStartToHeightmap, int maxDistanceFromCenter) {
		super(settings);
		this.startPool = startPool;
		this.startJigsawName = startJigsawName;
		this.maxDepth = maxDepth;
		this.startHeight = startHeight;
		this.useExpansionHack = useExpansionHack;
		this.projectStartToHeightmap = projectStartToHeightmap;
		this.maxDistanceFromCenter = maxDistanceFromCenter;
	}

	public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext context) {
		ChunkPos chunkPos = context.chunkPos();
		int y = this.startHeight.sample(context.random(), new WorldGenerationContext(context.chunkGenerator(), context.heightAccessor()));
		BlockPos blockPos = new BlockPos(chunkPos.getMinBlockX(), y, chunkPos.getMinBlockZ());
		return JigsawPlacement.addPieces(context, this.startPool, this.startJigsawName, this.maxDepth, blockPos, this.useExpansionHack, this.projectStartToHeightmap, this.maxDistanceFromCenter);
	}

	@Override
	public void afterPlace(WorldGenLevel level, StructureManager structureManager, ChunkGenerator chunkGenerator, RandomSource random, BoundingBox box, ChunkPos chunkPos, PiecesContainer pieces) {
		BoundingBox structureBox = pieces.calculateBoundingBox();
		BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();

		for (int x = box.minX(); x <= box.maxX(); x++) {
			for (int z = box.minZ(); z <= box.maxZ(); z++) {
				int clampedX = Mth.clamp(x, structureBox.minX(), structureBox.maxX());
				int clampedZ = Mth.clamp(z, structureBox.minZ(), structureBox.maxZ());
				int dx = x - clampedX;
				int dz = z - clampedZ;
				double dist = Math.sqrt((double) (dx * dx + dz * dz));
				if (dist <= 0.0D || dist > SLOPE_RADIUS) {
					continue; // inside the structure itself, or beyond the blend radius
				}

				// The anchor column sits on/in the structure's own footprint, so its height
				// already reflects the real built geometry (courtyard floor, wall top, ...).
				int anchorTop = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, clampedX, clampedZ) - 1;
				int naturalTop = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z) - 1;
				if (naturalTop <= anchorTop) {
					continue; // nothing here is taller than the structure's own edge - no cliff to hide
				}

				double t = dist / SLOPE_RADIUS;
				t = t * t * (3.0D - 2.0D * t); // smoothstep: eases in/out instead of a straight ramp
				int targetTop = anchorTop + (int) Math.round((naturalTop - anchorTop) * t);

				for (int y = naturalTop; y > targetTop; y--) {
					cursor.set(x, y, z);
					if (!box.isInside(cursor)) {
						continue;
					}
					if (!level.getFluidState(cursor).isEmpty()) {
						break; // don't drain an oasis into the carved slope
					}
					level.setBlock(cursor, Blocks.AIR.defaultBlockState(), 2);
				}
			}
		}
	}

	public StructureType<?> type() {
		return FURStructureTypeRegistry.ROYAL_TOMB.get();
	}

	private static com.mojang.serialization.DataResult<FURRoyalTombStructure> verifyRange(FURRoyalTombStructure structure) {
		byte inflation;
		switch (structure.terrainAdaptation()) {
			case NONE:
				inflation = 0;
				break;
			case BURY:
			case BEARD_THIN:
			case BEARD_BOX:
				inflation = 12;
				break;
			default:
				throw new IncompatibleClassChangeError();
		}

		return structure.maxDistanceFromCenter + inflation > 128
				? com.mojang.serialization.DataResult.error(() -> "Structure size including terrain adaptation must not exceed 128")
				: com.mojang.serialization.DataResult.success(structure);
	}
}
