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
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class FURItemTagsProvider extends ItemTagsProvider {
    public static final TagKey<Item> WOODEN_RODS = TagKey.create(Registries.ITEM, new ResourceLocation("forge", "rods/wooden"));
    public static final TagKey<Item> SERVING_CONTAINERS = TagKey.create(Registries.ITEM, new ResourceLocation("farmersdelight", "serving_containers"));
    public static final TagKey<Item> FLAREFLY_FOOD = TagKey.create(Registries.ITEM, new ResourceLocation(mod_LavaCow.MODID, "flarefly_food"));

    // forge:<x> tags that aren't part of Forge's own Tags.Items constant set (either legacy/no
    // longer maintained by Forge itself, or FUR-invented sub-categories) - defined the same way as
    // WOODEN_RODS/SERVING_CONTAINERS above.
    public static final TagKey<Item> HEART = TagKey.create(Registries.ITEM, new ResourceLocation("forge", "heart"));
    public static final TagKey<Item> RAW_BEEF = TagKey.create(Registries.ITEM, new ResourceLocation("forge", "raw_beef"));
    public static final TagKey<Item> RAW_CHICKEN = TagKey.create(Registries.ITEM, new ResourceLocation("forge", "raw_chicken"));
    public static final TagKey<Item> RAW_FISHES = TagKey.create(Registries.ITEM, new ResourceLocation("forge", "raw_fishes"));
    public static final TagKey<Item> RAW_MUTTON = TagKey.create(Registries.ITEM, new ResourceLocation("forge", "raw_mutton"));
    public static final TagKey<Item> RAW_PORK = TagKey.create(Registries.ITEM, new ResourceLocation("forge", "raw_pork"));
    public static final TagKey<Item> TOOLS_AXES = TagKey.create(Registries.ITEM, new ResourceLocation("forge", "tools/axes"));
    public static final TagKey<Item> TOOLS_SHOVELS = TagKey.create(Registries.ITEM, new ResourceLocation("forge", "tools/shovels"));

	public FURItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, CompletableFuture<TagsProvider.TagLookup<Block>> blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, provider, blockTagProvider, mod_LavaCow.MODID, existingFileHelper);
	}

    protected void addTags(HolderLookup.@NotNull Provider lookupProvider) {
        this.tag(SERVING_CONTAINERS).add(Items.BOWL, Items.GLASS_BOTTLE, Items.BUCKET, FURItemRegistry.CURSEWEAVE_CLOTH.get()).addTag(WOODEN_RODS);
        this.tag(FLAREFLY_FOOD).add(
        		Items.GLOWSTONE_DUST,
        		Items.WARPED_FUNGUS,
        		Items.GLOW_BERRIES,
        		FURBlockRegistry.EMBERWICK_FUNGUS.get().asItem(),
        		FURBlockRegistry.GLIMMERCAP.get().asItem(),
        		FURBlockRegistry.GLOWSHROOM.get().asItem());

        this.tag(Tags.Items.INGOTS).add(
        		FURItemRegistry.ECTOPLASM_INGOT.get(),
        		FURItemRegistry.MOLTEN_ALLOY.get());

        this.tag(Tags.Items.STORAGE_BLOCKS).add(FURBlockRegistry.ECTOPLASM_BLOCK.get().asItem());

        // Mushroom-cap decor/food blocks - flavor-wise all "small mushrooms" like vanilla's own.
        this.tag(Tags.Items.MUSHROOMS).add(
        		FURBlockRegistry.BLOODTOOTH_SHROOM.get().asItem(),
        		FURBlockRegistry.CORDY_SHROOM.get().asItem(),
        		FURBlockRegistry.GLOWSHROOM.get().asItem(),
        		FURBlockRegistry.VEIL_SHROOM.get().asItem(),
        		FURBlockRegistry.GLIMMERCAP.get().asItem(),
        		FURBlockRegistry.EMBERWICK_FUNGUS.get().asItem(),
        		FURBlockRegistry.MYCELIAL_TENDRILS.get().asItem(),
        		FURBlockRegistry.LUMINOUS_FILAMENT.get().asItem());

        // ── consolidated from formerly hand-authored data/minecraft/tags/items/*.json ───────────
        this.tag(ItemTags.ARROWS).add(
        		FURItemRegistry.GHOUL_ARROW.get(),
        		FURItemRegistry.FANG_ARROW.get());
        this.tag(ItemTags.FISHES).add(
        		FURItemRegistry.PIRANHA_RAW.get(),
        		FURItemRegistry.PIRANHA_COOKED.get(),
        		FURItemRegistry.SWARMER_RAW.get(),
        		FURItemRegistry.SWARMER_COOKED.get());
        this.tag(ItemTags.FREEZE_IMMUNE_WEARABLES).add(
        		FURItemRegistry.MOLTEN_ARMOR_HELMET.get(),
        		FURItemRegistry.MOLTEN_ARMOR_CHESTPLATE.get(),
        		FURItemRegistry.MOLTEN_ARMOR_LEGGINGS.get(),
        		FURItemRegistry.MOLTEN_ARMOR_BOOTS.get(),
        		FURItemRegistry.FAMINE_ARMOR_HELMET.get(),
        		FURItemRegistry.FAMINE_ARMOR_CHESTPLATE.get(),
        		FURItemRegistry.FAMINE_ARMOR_LEGGINGS.get(),
        		FURItemRegistry.FAMINE_ARMOR_BOOTS.get());
        this.tag(ItemTags.PIGLIN_LOVED).add(FURItemRegistry.GOLDEN_HEART.get());

        // ── consolidated from formerly hand-authored data/forge/tags/items/*.json ───────────────
        this.tag(Tags.Items.ARMORS_BOOTS).add(
        		FURItemRegistry.CHITIN_ARMOR_BOOTS.get(),
        		FURItemRegistry.GHOSTLY_ARMOR_BOOTS.get(),
        		FURItemRegistry.MOLTEN_ARMOR_BOOTS.get(),
        		FURItemRegistry.FAMINE_ARMOR_BOOTS.get());
        this.tag(Tags.Items.ARMORS_CHESTPLATES).add(
        		FURItemRegistry.CHITIN_ARMOR_CHESTPLATE.get(),
        		FURItemRegistry.GHOSTLY_ARMOR_CHESTPLATE.get(),
        		FURItemRegistry.MOLTEN_ARMOR_CHESTPLATE.get(),
        		FURItemRegistry.FAMINE_ARMOR_CHESTPLATE.get());
        this.tag(Tags.Items.ARMORS_HELMETS).add(
        		FURItemRegistry.CHITIN_ARMOR_HELMET.get(),
        		FURItemRegistry.GHOSTLY_ARMOR_HELMET.get(),
        		FURItemRegistry.MOLTEN_ARMOR_HELMET.get(),
        		FURItemRegistry.FAMINE_ARMOR_HELMET.get());
        this.tag(Tags.Items.ARMORS_LEGGINGS).add(
        		FURItemRegistry.CHITIN_ARMOR_LEGGINGS.get(),
        		FURItemRegistry.GHOSTLY_ARMOR_LEGGINGS.get(),
        		FURItemRegistry.MOLTEN_ARMOR_LEGGINGS.get(),
        		FURItemRegistry.FAMINE_ARMOR_LEGGINGS.get());
        this.tag(Tags.Items.BONES).add(FURItemRegistry.BONE_TROUT.get());
        this.tag(Tags.Items.EGGS).add(
        		FURItemRegistry.MIMIC_EGG.get(),
        		FURBlockRegistry.SALAMANDER_EGG.get().asItem(),
        		FURBlockRegistry.ENIGMOTH_EGG.get().asItem());
        this.tag(Tags.Items.FEATHERS).add(FURItemRegistry.FEATHER_BLACK.get());
        this.tag(HEART).add(
        		FURItemRegistry.UNDYING_HEART.get(),
        		FURItemRegistry.MOOTEN_HEART.get(),
        		FURItemRegistry.ACIDIC_HEART.get(),
        		FURItemRegistry.SOULFORGED_HEART.get(),
        		FURItemRegistry.GOLDEN_HEART.get());
        this.tag(Tags.Items.LEATHER).add(FURItemRegistry.PIGBOARHIDE.get());
        this.tag(RAW_BEEF).add(Items.BEEF);
        this.tag(RAW_CHICKEN).add(Items.CHICKEN);
        this.tag(RAW_FISHES).add(
        		FURItemRegistry.PIRANHA_RAW.get(),
        		FURItemRegistry.SWARMER_RAW.get(),
        		FURItemRegistry.LAMPREY_RAW.get());
        this.tag(RAW_MUTTON).add(Items.MUTTON);
        this.tag(RAW_PORK).add(Items.PORKCHOP);
        this.tag(Tags.Items.SLIMEBALLS).add(FURItemRegistry.SPORE_GEL.get());
        this.tag(TOOLS_AXES).add(
        		FURItemRegistry.MOLTEN_AXE.get(),
        		FURItemRegistry.SOULFORGED_AXE.get());
        this.tag(TOOLS_SHOVELS).add(FURItemRegistry.UNDERTAKER_SHOVEL.get());
        this.tag(Tags.Items.TOOLS).addTag(TOOLS_AXES).addTag(TOOLS_SHOVELS);
    }
}
