package com.Fishmod.fur.data.providers;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.init.FURBlockRegistry;
import com.Fishmod.fur.init.FURItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

public class FURItemTagsProvider extends ItemTagsProvider {    
    public static final TagKey<Item> WOODEN_RODS = TagKey.create(Registries.ITEM, new ResourceLocation("forge", "rods/wooden"));
    public static final TagKey<Item> SERVING_CONTAINERS = TagKey.create(Registries.ITEM, new ResourceLocation("farmersdelight", "serving_containers"));
    public static final TagKey<Item> WARPED_FIREFLY_FOOD = TagKey.create(Registries.ITEM, new ResourceLocation(mod_LavaCow.MODID, "warped_firefly_food"));

	public FURItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, CompletableFuture<TagsProvider.TagLookup<Block>> blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, provider, blockTagProvider, mod_LavaCow.MODID, existingFileHelper);
	}

    protected void addTags(HolderLookup.@NotNull Provider lookupProvider) {
        this.tag(SERVING_CONTAINERS).add(Items.BOWL, Items.GLASS_BOTTLE, Items.BUCKET, FURItemRegistry.CURSEWEAVE_CLOTH.get()).addTag(WOODEN_RODS);
        this.tag(WARPED_FIREFLY_FOOD).add(
        		Items.GLOWSTONE_DUST, 
        		Items.WARPED_FUNGUS, 
        		Items.GLOW_BERRIES, 
        		FURBlockRegistry.EMBERWICK_FUNGUS.get().asItem(), 
        		FURBlockRegistry.GLIMMERCAP.get().asItem(), 
        		FURBlockRegistry.GLOWSHROOM.get().asItem());
    }
}
