package com.Fishmod.fur.init;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;

public class FURCarvers {

    public static final ResourceKey<ConfiguredWorldCarver<?>> LUMINOUS_GROTTO =
        ResourceKey.create(Registries.CONFIGURED_CARVER,
            new ResourceLocation("fur", "luminous_grotto"));

    public static void bootstrap(BootstapContext<ConfiguredWorldCarver<?>> context) {
        HolderSet<Block> replaceables = context.lookup(Registries.BLOCK)
                .getOrThrow(BlockTags.OVERWORLD_CARVER_REPLACEABLES);
        context.register(LUMINOUS_GROTTO,
            FURCarverRegistry.LUMINOUS_GROTTO.get().configured(
                new CaveCarverConfiguration(
                    0.35f,
                    UniformHeight.of(VerticalAnchor.absolute(-56), VerticalAnchor.absolute(-24)),
                    ConstantFloat.of(0.5f),
                    VerticalAnchor.absolute(-54),
                    replaceables,
                    ConstantFloat.of(1.0f),
                    ConstantFloat.of(1.0f),
                    ConstantFloat.of(-1.0f)
                )
            )
        );
    }
}
