package com.Fishmod.fur.worldgen.structure;

import java.util.Optional;

import com.Fishmod.fur.entities.GraveRobberEntity;
import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURStructureTypeRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
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
 * Drop-in replacement for {@code minecraft:jigsaw} used only by {@code graveyard} - identical
 * JSON schema and placement behaviour (delegates straight to {@link JigsawPlacement#addPieces}),
 * but adds an {@link #afterPlace} pass that rolls a 50% chance to spawn 2-4 {@link GraveRobberEntity}
 * somewhere on the structure's own footprint once it finishes generating (mirrors the vanilla Ocean
 * Monument's {@code spawnElder} - {@code EntityType.create} + {@code finalizeSpawn} +
 * {@code addFreshEntityWithPassengers} on the {@link WorldGenLevel} directly, since worldgen only
 * has a {@code WorldGenLevel}, not a full {@code ServerLevel}).
 *
 * <p>{@link Structure#afterPlace} is invoked once per chunk the structure's bounding box touches
 * (see {@code StructureStart#placeInChunk}), not once per structure - so the spawn roll is gated to
 * only run when the chunk currently being processed is the structure's own starting chunk, or a
 * multi-chunk graveyard would roll its 50% chance (and place a fresh batch of Graverobbers) once per
 * chunk it spans instead of once total.
 */
public final class FURGraveyardStructure extends Structure {
	public static final Codec<FURGraveyardStructure> CODEC = RecordCodecBuilder.<FURGraveyardStructure>mapCodec((instance) -> {
		return instance.group(settingsCodec(instance),
				StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter((s) -> s.startPool),
				ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter((s) -> s.startJigsawName),
				Codec.intRange(0, 7).fieldOf("size").forGetter((s) -> s.maxDepth),
				HeightProvider.CODEC.fieldOf("start_height").forGetter((s) -> s.startHeight),
				Codec.BOOL.fieldOf("use_expansion_hack").forGetter((s) -> s.useExpansionHack),
				Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter((s) -> s.projectStartToHeightmap),
				Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter((s) -> s.maxDistanceFromCenter))
				.apply(instance, FURGraveyardStructure::new);
	}).codec();

	/** 50% chance for the batch to happen at all. */
	private static final float SPAWN_CHANCE = 0.5F;
	private static final int MIN_COUNT = 2;
	private static final int MAX_COUNT = 4;

	private final Holder<StructureTemplatePool> startPool;
	private final Optional<ResourceLocation> startJigsawName;
	private final int maxDepth;
	private final HeightProvider startHeight;
	private final boolean useExpansionHack;
	private final Optional<Heightmap.Types> projectStartToHeightmap;
	private final int maxDistanceFromCenter;

	public FURGraveyardStructure(Structure.StructureSettings settings, Holder<StructureTemplatePool> startPool, Optional<ResourceLocation> startJigsawName,
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
		if (pieces.isEmpty()) {
			return;
		}

		BlockPos originPos = pieces.pieces().get(0).getBoundingBox().getCenter();
		if (!chunkPos.equals(new ChunkPos(originPos))) {
			return; // afterPlace fires once per chunk the structure spans - only roll once, on its own starting chunk
		}

		if (random.nextFloat() >= SPAWN_CHANCE) {
			return;
		}

		BoundingBox structureBox = pieces.calculateBoundingBox();
		int count = Mth.nextInt(random, MIN_COUNT, MAX_COUNT);

		for (int i = 0; i < count; i++) {
			int x = Mth.nextInt(random, structureBox.minX(), structureBox.maxX());
			int z = Mth.nextInt(random, structureBox.minZ(), structureBox.maxZ());
			int y = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z);

			GraveRobberEntity graverobber = FUREntityRegistry.GRAVEROBBER.get().create(level.getLevel());
			if (graverobber == null) {
				continue;
			}

			graverobber.moveTo((double) x + 0.5D, y, (double) z + 0.5D, random.nextFloat() * 360.0F, 0.0F);
			graverobber.finalizeSpawn(level, level.getCurrentDifficultyAt(graverobber.blockPosition()), MobSpawnType.STRUCTURE, null, null);
			level.addFreshEntityWithPassengers(graverobber);
		}
	}

	public StructureType<?> type() {
		return FURStructureTypeRegistry.GRAVEYARD.get();
	}
}
