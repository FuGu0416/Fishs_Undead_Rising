package com.Fishmod.fur.mixin;

import com.Fishmod.fur.init.FURBiomesRegistry;
import com.Fishmod.fur.worldgen.biome.FURMultiNoiseBiomeSourceAccessor;
import com.mojang.datafixers.util.Either;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ThreadedLevelLightEngine;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;

@Mixin(ChunkStatus.class)
public class ChunkStatusMixin {

    @Inject(
        at = @At("HEAD"),
        method = "generate(Ljava/util/concurrent/Executor;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/chunk/ChunkGenerator;Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplateManager;Lnet/minecraft/server/level/ThreadedLevelLightEngine;Ljava/util/function/Function;Ljava/util/List;)Ljava/util/concurrent/CompletableFuture;"
    )
    private void fur_generate(
            Executor executor,
            ServerLevel serverLevel,
            ChunkGenerator chunkGenerator,
            StructureTemplateManager structureTemplateManager,
            ThreadedLevelLightEngine lightEngine,
            Function<ChunkAccess, CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>>> function,
            List<ChunkAccess> chunks,
            CallbackInfoReturnable<CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>>> cir) {
        if (!(chunkGenerator.getBiomeSource() instanceof FURMultiNoiseBiomeSourceAccessor accessor)) return;
        accessor.fur_setWorldSeed(serverLevel.getSeed());
        accessor.fur_setDimension(serverLevel.dimension());
        // Populate the luminous holder once per biome source instance
        if (serverLevel.dimension() == Level.OVERWORLD && accessor.fur_getLuminousHolder() == null) {
            serverLevel.registryAccess().registry(Registries.BIOME).ifPresent(reg ->
                reg.getHolder(FURBiomesRegistry.LUMINOUS_UNDERGROVE).ifPresent(accessor::fur_setLuminousHolder)
            );
        }
    }
}
