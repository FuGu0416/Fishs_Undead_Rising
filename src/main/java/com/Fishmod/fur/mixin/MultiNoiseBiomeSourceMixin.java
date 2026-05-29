package com.Fishmod.fur.mixin;

import com.Fishmod.fur.worldgen.biome.FURMultiNoiseBiomeSourceAccessor;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MultiNoiseBiomeSource.class, priority = -69420)
public class MultiNoiseBiomeSourceMixin implements FURMultiNoiseBiomeSourceAccessor {

    private long fur_worldSeed;
    private ResourceKey<Level> fur_dimension;
    private Holder<Biome> fur_luminousHolder;

    // Jittered-grid / Voronoi-cell patch placement.
    // Each cell is 32 biome quarts (128 blocks) per side.
    // ~1 in 4 cells hosts a patch center (jittered within the cell).
    // Radius = 8 biome quarts = 32 blocks → 64-block diameter.
    // The biome patch must fully enclose the carved grotto (primary up to ~30 blocks
    // radius, satellite lobes up to ~32 blocks from center) so that vanilla biome
    // features (e.g. lush cave vegetation) cannot generate inside the carved space.
    private static final int  CELL_SIZE = 32;           // biome quarts per cell side
    private static final int  CELL_MASK = CELL_SIZE - 1;
    private static final long RARITY    = 4L;           // 1 in 4 cells has a patch
    private static final int  RADIUS_SQ = 64;           // 8-quart radius = 32-block radius

    @Inject(
        at = @At("HEAD"),
        method = "getNoiseBiome(IIILnet/minecraft/world/level/biome/Climate$Sampler;)Lnet/minecraft/core/Holder;",
        cancellable = true
    )
    private void fur_getNoiseBiome(int x, int y, int z, Climate.Sampler sampler, CallbackInfoReturnable<Holder<Biome>> cir) {
        if (fur_dimension != Level.OVERWORLD) return;
        if (fur_luminousHolder == null) return;

        Climate.TargetPoint target = sampler.sample(x, y, z);
        float depth           = Climate.unquantizeCoord(target.depth());
        float temperature     = Climate.unquantizeCoord(target.temperature());
        float humidity        = Climate.unquantizeCoord(target.humidity());
        float continentalness = Climate.unquantizeCoord(target.continentalness());

        if (depth < 0.4F || depth > 0.9F)             return;
        if (temperature < 0.2F || temperature > 0.9F) return;
        if (humidity < 0.1F)                           return;
        if (continentalness < -0.19F)                  return;

        if (!isInPatch(fur_worldSeed, x, z)) return;

        cir.setReturnValue(fur_luminousHolder);
    }

    // Returns true if (x, z) falls within a Luminous Undergrove patch.
    // Checks the owning cell and all 8 neighbors so patches whose centers sit
    // near a cell boundary are never clipped.
    private static boolean isInPatch(long seed, int x, int z) {
        int cellX = Math.floorDiv(x, CELL_SIZE);
        int cellZ = Math.floorDiv(z, CELL_SIZE);
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                int cx = cellX + dx;
                int cz = cellZ + dz;
                long h = mixHash(seed, cx, cz);
                // Skip cells that don't host a patch this world seed
                if (Long.remainderUnsigned(h, RARITY) != 0L) continue;
                // Jitter the patch center within the cell
                long jh = mixHash(h ^ 0xDEADBEEFCAFEBABEL, cx, cz);
                int px = cx * CELL_SIZE + (int)(jh         & (long) CELL_MASK);
                int pz = cz * CELL_SIZE + (int)((jh >>> 32) & (long) CELL_MASK);
                if ((x - px) * (x - px) + (z - pz) * (z - pz) <= RADIUS_SQ) return true;
            }
        }
        return false;
    }

    private static long mixHash(long seed, int x, int z) {
        long v = seed ^ ((long) x * 6364136223846793005L) ^ ((long) z * 1442695040888963407L);
        v ^= v >>> 33;
        v *= 0xff51afd7ed558ccdL;
        v ^= v >>> 33;
        return v;
    }

    @Override
    public void fur_setWorldSeed(long seed) { this.fur_worldSeed = seed; }

    @Override
    public void fur_setDimension(ResourceKey<Level> dimension) { this.fur_dimension = dimension; }

    @Override
    public void fur_setLuminousHolder(Holder<Biome> holder) { this.fur_luminousHolder = holder; }

    @Override
    public Holder<Biome> fur_getLuminousHolder() { return this.fur_luminousHolder; }
}
