package com.Fishmod.fur.data.providers;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.Fishmod.fur.mod_LavaCow;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.StructureTagsProvider;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraftforge.common.data.ExistingFileHelper;

public class FURStructureTagsProvider extends StructureTagsProvider {
    public FURStructureTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, mod_LavaCow.MODID, existingFileHelper);
    }

    protected void addTags(HolderLookup.@NotNull Provider lookupProvider) {
        this.tag(FURTags.HAS_SEAHAG).addTag(StructureTags.SHIPWRECK).addTag(StructureTags.OCEAN_RUIN);
        this.tag(FURTags.HAS_MUMMY).add(BuiltinStructures.BASTION_REMNANT);
        this.tag(FURTags.HAS_MIMIC).add(BuiltinStructures.JUNGLE_TEMPLE)
        						   .add(BuiltinStructures.MINESHAFT)
        						   .add(BuiltinStructures.FORTRESS)
        						   .add(BuiltinStructures.STRONGHOLD)
        						   .addTag(StructureTags.VILLAGE)
        						   .add(BuiltinStructures.WOODLAND_MANSION)
        						   .add(BuiltinStructures.SHIPWRECK)
        						   .addTag(StructureTags.OCEAN_RUIN);
    }
}
