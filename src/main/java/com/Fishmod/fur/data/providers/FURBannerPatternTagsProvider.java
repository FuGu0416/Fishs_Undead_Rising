package com.Fishmod.fur.data.providers;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.init.FURItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BannerPatternTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BannerPatterns;
import net.minecraftforge.common.data.ExistingFileHelper;

public class FURBannerPatternTagsProvider extends BannerPatternTagsProvider {
    public static final TagKey<BannerPattern> PATTERN_SKELETONKING = TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(mod_LavaCow.MODID, "skeletonking"));
    public static final TagKey<BannerPattern> PATTERN_WENDIGO = TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(mod_LavaCow.MODID, "wendigo"));
    
	public FURBannerPatternTagsProvider(PackOutput p_256451_, CompletableFuture<Provider> p_256420_, String modId, @Nullable ExistingFileHelper existingFileHelper) {
		super(p_256451_, p_256420_, modId, existingFileHelper);
	}
	
	@Override
    protected void addTags(HolderLookup.@NotNull Provider lookupProvider) {
    	this.tag(PATTERN_SKELETONKING).add(BannerPatterns.CREEPER/*FURItemRegistry.PATTERN_SKELETONKING.getKey()*/);
    	this.tag(PATTERN_WENDIGO).add(FURItemRegistry.PATTERN_WENDIGO.getKey());
    }

}
