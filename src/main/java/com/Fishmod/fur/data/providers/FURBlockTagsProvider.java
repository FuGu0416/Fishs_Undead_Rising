package com.Fishmod.fur.data.providers;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.init.FURBlockRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class FURBlockTagsProvider extends BlockTagsProvider {
    public static final TagKey<Block> SALAMANDER_EGG_HATCH_BOOST = TagKey.create(Registries.BLOCK, new ResourceLocation(mod_LavaCow.MODID, "salamander_egg_hatch_boost"));

    /** Blocks the mycelial mat may overwrite when covering the cave floor/ceiling:
     *  everything vanilla moss replaces, plus ores so they don't poke through the mat. */
    public static final TagKey<Block> MAT_REPLACEABLE = TagKey.create(Registries.BLOCK, new ResourceLocation(mod_LavaCow.MODID, "mat_replaceable"));

    /** Blocks GraveRobberEntity's flavor-loot goal will walk to and mime digging/opening —
     *  purely cosmetic, no block/NBT changes. Tagged (not hardcoded) so more block types
     *  can be added later without touching the goal's code. */
    public static final TagKey<Block> TOMB_LOOT_FLAVOR = TagKey.create(Registries.BLOCK, new ResourceLocation(mod_LavaCow.MODID, "tomb_loot_flavor"));

	public FURBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, mod_LavaCow.MODID, existingFileHelper);
	}

    protected void addTags(HolderLookup.@NotNull Provider lookupProvider) {
    	this.tag(SALAMANDER_EGG_HATCH_BOOST).add(Blocks.MAGMA_BLOCK, FURBlockRegistry.SOUL_FURNACE.get());
    	this.tag(MAT_REPLACEABLE).addTag(BlockTags.MOSS_REPLACEABLE).addTag(Tags.Blocks.ORES);
    	this.tag(TOMB_LOOT_FLAVOR).add(Blocks.SUSPICIOUS_SAND, Blocks.DECORATED_POT);
    }
}
