package com.Fishmod.fur.data.providers;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.Fishmod.fur.init.FURItemRegistry;
import com.Fishmod.fur.init.FURTagRegistry;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BannerPatternTagsProvider;
import net.minecraft.world.level.block.entity.BannerPatterns;
import net.minecraftforge.common.data.ExistingFileHelper;

public class FURBannerPatternTagsProvider extends BannerPatternTagsProvider {
	public FURBannerPatternTagsProvider(PackOutput p_256451_, CompletableFuture<Provider> p_256420_, String modId, @Nullable ExistingFileHelper existingFileHelper) {
		super(p_256451_, p_256420_, modId, existingFileHelper);
	}
	
	@Override
    protected void addTags(HolderLookup.@NotNull Provider lookupProvider) {
    	this.tag(FURTagRegistry.PATTERN_SKELETONKING).add(BannerPatterns.CREEPER/*FURItemRegistry.PATTERN_SKELETONKING.getKey()*/);
    	this.tag(FURTagRegistry.PATTERN_WENDIGO).add(FURItemRegistry.PATTERN_WENDIGO.getKey());
    }

}
