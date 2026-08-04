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

    	// These three all call requiresCorrectToolForDrops() at registration but were never added to
    	// any mineable/* tag - without it, no tool (not even Silk Touch) is ever "correct" for them,
    	// so they silently drop nothing when mined. Tagged here to match their closest vanilla
    	// equivalent's tool requirement (all three are wood-pickaxe-tier, no needs_*_tool gate).
    	this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
    			FURBlockRegistry.SOUL_FURNACE.get(),
    			FURBlockRegistry.TOMBSTONE.get(),
    			FURBlockRegistry.INFESTED_SANDSTONE.get());

    	// Block-side counterpart of forge:storage_blocks (see FURItemTagsProvider for the item side) -
    	// ectoplasm_block is a standard 3x3 ectoplasm_ingot <-> block storage relationship.
    	this.tag(Tags.Blocks.STORAGE_BLOCKS).add(FURBlockRegistry.ECTOPLASM_BLOCK.get());

    	// ── consolidated from formerly hand-authored data/minecraft/tags/blocks/*.json ──────────
    	this.tag(BlockTags.COMBINATION_STEP_SOUND_BLOCKS).add(
    			FURBlockRegistry.MYCELIAL_VEIL.get(),
    			FURBlockRegistry.MYCELIAL_TENDRILS.get());
    	this.tag(BlockTags.DIRT).add(
    			FURBlockRegistry.LUMINOUS_MYCELIUM.get(),
    			FURBlockRegistry.MYCELIAL_MAT.get());
    	this.tag(BlockTags.INFINIBURN_OVERWORLD).add(FURBlockRegistry.ECTOPLASM_BLOCK.get());
    	this.tag(BlockTags.MINEABLE_WITH_HOE).add(
    			FURBlockRegistry.MYCELIAL_MAT.get(),
    			FURBlockRegistry.MYCELIAL_VEIL.get());
    	this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(FURBlockRegistry.LUMINOUS_MYCELIUM.get());
    	this.tag(BlockTags.MUSHROOM_GROW_BLOCK).add(FURBlockRegistry.LUMINOUS_MYCELIUM.get());
    	this.tag(BlockTags.SOUL_FIRE_BASE_BLOCKS).add(FURBlockRegistry.ECTOPLASM_BLOCK.get());
    	this.tag(BlockTags.SOUL_SPEED_BLOCKS).add(FURBlockRegistry.ECTOPLASM_BLOCK.get());
    	this.tag(BlockTags.SWORD_EFFICIENT).add(
    			FURBlockRegistry.MYCELIAL_VEIL.get(),
    			FURBlockRegistry.MYCELIAL_TENDRILS.get());
    }
}
