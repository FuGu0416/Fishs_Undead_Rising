package com.Fishmod.fur.data.providers;

import java.util.concurrent.CompletableFuture;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.init.FUREntityRegistry;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeEntityTypeTagsProvider;

public class FUREntityTypeTagsProvider extends ForgeEntityTypeTagsProvider {
    // ── Custom Tag Keys ──────────────────────────────────────────────────────
    public static final TagKey<EntityType<?>> FISHES =
            TagKey.create(net.minecraft.core.registries.Registries.ENTITY_TYPE, new ResourceLocation(mod_LavaCow.MODID, "fishes"));
    public static final TagKey<EntityType<?>> LIVESTOCKS =
            TagKey.create(net.minecraft.core.registries.Registries.ENTITY_TYPE, new ResourceLocation(mod_LavaCow.MODID, "livestocks"));
    public static final TagKey<EntityType<?>> ZOMBIES =
            TagKey.create(net.minecraft.core.registries.Registries.ENTITY_TYPE, new ResourceLocation(mod_LavaCow.MODID, "zombies"));
    public static final TagKey<EntityType<?>> ENIGMOTH_TARGETS =
            TagKey.create(net.minecraft.core.registries.Registries.ENTITY_TYPE, new ResourceLocation(mod_LavaCow.MODID, "enigmoth_targets"));
    public static final TagKey<EntityType<?>> LAMPREY_TARGETS =
            TagKey.create(net.minecraft.core.registries.Registries.ENTITY_TYPE, new ResourceLocation(mod_LavaCow.MODID, "lamprey_targets"));
    public static final TagKey<EntityType<?>> PARASITE_TARGETS =
            TagKey.create(net.minecraft.core.registries.Registries.ENTITY_TYPE, new ResourceLocation(mod_LavaCow.MODID, "parasite_targets"));
    public static final TagKey<EntityType<?>> PIRANHA_TARGETS =
            TagKey.create(net.minecraft.core.registries.Registries.ENTITY_TYPE, new ResourceLocation(mod_LavaCow.MODID, "piranha_targets"));
    public static final TagKey<EntityType<?>> PTERA_TARGETS =
            TagKey.create(net.minecraft.core.registries.Registries.ENTITY_TYPE, new ResourceLocation(mod_LavaCow.MODID, "ptera_targets"));
    public static final TagKey<EntityType<?>> PTERA_CARGOS = 
    		TagKey.create(net.minecraft.core.registries.Registries.ENTITY_TYPE, new ResourceLocation(mod_LavaCow.MODID, "ptera_cargos"));
    public static final TagKey<EntityType<?>> SWARMER_TARGETS =
            TagKey.create(net.minecraft.core.registries.Registries.ENTITY_TYPE, new ResourceLocation(mod_LavaCow.MODID, "swarmer_targets"));
    public static final TagKey<EntityType<?>> WENDIGO_TARGETS =
            TagKey.create(net.minecraft.core.registries.Registries.ENTITY_TYPE, new ResourceLocation(mod_LavaCow.MODID, "wendigo_targets"));
    public static final TagKey<EntityType<?>> BEELZEBUB_TARGETS = 
    		TagKey.create(net.minecraft.core.registries.Registries.ENTITY_TYPE, new ResourceLocation(mod_LavaCow.MODID, "beelzebub_targets"));
    public static final TagKey<EntityType<?>> VESPA_TARGETS =
    		TagKey.create(net.minecraft.core.registries.Registries.ENTITY_TYPE, new ResourceLocation(mod_LavaCow.MODID, "vespa_targets"));
    public static final TagKey<EntityType<?>> DROPS_ILLAGER_NOSE =
    		TagKey.create(net.minecraft.core.registries.Registries.ENTITY_TYPE, new ResourceLocation(mod_LavaCow.MODID, "drops_illager_nose"));
    
    public FUREntityTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, existingFileHelper);
    }
   
    @Override
	public void addTags(HolderLookup.Provider provider) {
        this.addFishesTag();
        this.addLivestocksTag();
        this.addZombiesTag();
        this.addEnigmothTargetsTag();
        this.addLampreyTargetsTag();
        this.addParasiteTargetsTag();
        this.addPiranhaTargetsTag();
        this.addPteraTargetsTag();
        this.addPteraCargosTag();
        this.addSwarmerTargetsTag();
        this.addWendigoTargetsTag();
        this.addVespaTargetsTag();
        this.addBeelzebubTargetsTag();
        this.addDropsIllagerNoseTag();
    }

    // ── fur:fishes ───────────────────────────────────────────────────────────
    private void addFishesTag() {
        tag(FISHES)
                .add(EntityType.COD)
                .add(EntityType.PUFFERFISH)
                .add(EntityType.SALMON)
                .add(EntityType.TROPICAL_FISH)
                .add(FUREntityRegistry.PIRANHA.get())
                .add(FUREntityRegistry.SWARMER.get())
		        .add(FUREntityRegistry.MUMMIFIED_COD.get())
		        .add(FUREntityRegistry.BONE_TROUT.get())
		        .add(FUREntityRegistry.LAMPREY.get());
    }

    // ── fur:livestocks ───────────────────────────────────────────────────────
    private void addLivestocksTag() {
        tag(LIVESTOCKS)
                .add(EntityType.CAMEL)
                .add(EntityType.CHICKEN)
                .add(EntityType.COW)
                .add(EntityType.DONKEY)
                .add(EntityType.HORSE)
                .add(EntityType.LLAMA)
                .add(EntityType.MOOSHROOM)
                .add(EntityType.MULE)
                .add(EntityType.PANDA)
                .add(EntityType.PIG)
                .add(EntityType.RABBIT)
                .add(EntityType.SHEEP)
                .add(EntityType.TRADER_LLAMA)
                .add(FUREntityRegistry.LAVACOW.get());
    }

    // ── fur:zombies ──────────────────────────────────────────────────────────
    private void addZombiesTag() {
        tag(ZOMBIES)
                .add(EntityType.DROWNED)
                .add(EntityType.HUSK)
                .add(EntityType.ZOMBIE)
                .add(EntityType.ZOMBIFIED_PIGLIN)
                .add(EntityType.ZOMBIE_VILLAGER)
                .add(FUREntityRegistry.MYCOSIS.get())
                .add(FUREntityRegistry.FRIGID.get())
                .add(FUREntityRegistry.MUMMY.get())
                .add(FUREntityRegistry.UNBURIED.get())
                .add(FUREntityRegistry.GHOUL.get());
    }

    // ── fur:enigmoth_targets ─────────────────────────────────────────────────
    private void addEnigmothTargetsTag() {
        tag(ENIGMOTH_TARGETS)
        	.add(FUREntityRegistry.WARPEDFIREFLY.get());
    }

    // ── fur:lamprey_targets ──────────────────────────────────────────────────
    private void addLampreyTargetsTag() {
        tag(LAMPREY_TARGETS)
                .addTag(ZOMBIES);
    }

    // ── fur:parasite_targets ─────────────────────────────────────────────────
    private void addParasiteTargetsTag() {
        tag(PARASITE_TARGETS)
                .addTag(ZOMBIES);
    }

    // ── fur:piranha_targets ──────────────────────────────────────────────────
    private void addPiranhaTargetsTag() {
        tag(PIRANHA_TARGETS)
                .addTag(LIVESTOCKS)
                .add(EntityType.COD)
                .add(EntityType.GLOW_SQUID)
                .add(EntityType.PUFFERFISH)
                .add(EntityType.SALMON)
                .add(EntityType.SQUID)
                .add(EntityType.TROPICAL_FISH);
    }

    // ── fur:ptera_targets ────────────────────────────────────────────────────
    private void addPteraTargetsTag() {
        tag(PTERA_TARGETS)
                .addTag(FISHES)
                .add(EntityType.SQUID)
                .add(EntityType.GLOW_SQUID);
    }

    // ── fur:ptera_cargos ────────────────────────────────────────────────────
    private void addPteraCargosTag() {
        tag(PTERA_CARGOS)
        		.add(FUREntityRegistry.FOGLET.get())
        		.add(FUREntityRegistry.ISNACHI.get())
                .add(EntityType.DROWNED)
                .add(EntityType.HUSK)
                .add(EntityType.ZOMBIE)
                .add(EntityType.CREEPER);
    }
    
    // ── fur:swarmer_targets ──────────────────────────────────────────────────
    private void addSwarmerTargetsTag() {
        tag(SWARMER_TARGETS)
                .addTag(LIVESTOCKS)
                .addTag(net.minecraft.tags.EntityTypeTags.RAIDERS)
                .add(EntityType.CAT)
                .add(EntityType.CAVE_SPIDER)
                .add(EntityType.COD)
                .add(EntityType.DROWNED)
                .add(EntityType.FOX)
                .add(EntityType.FROG)
                .add(EntityType.GLOW_SQUID)
                .add(EntityType.HOGLIN)
                .add(EntityType.HUSK)
                .add(EntityType.OCELOT)
                .add(EntityType.PIGLIN)
                .add(EntityType.PIGLIN_BRUTE)
                .add(EntityType.PUFFERFISH)
                .add(EntityType.SALMON)
                .add(EntityType.SPIDER)
                .add(EntityType.SQUID)
                .add(EntityType.TROPICAL_FISH)
                .add(EntityType.VILLAGER)
                .add(EntityType.WANDERING_TRADER)
                .add(EntityType.WOLF)
                .add(EntityType.ZOGLIN)
                .add(EntityType.ZOMBIE)
                .add(EntityType.ZOMBIE_HORSE)
                .add(EntityType.ZOMBIFIED_PIGLIN)
                .add(EntityType.ZOMBIE_VILLAGER)
                .add(FUREntityRegistry.MYCOSIS.get())
                .add(FUREntityRegistry.PARASITE.get())
                .add(FUREntityRegistry.FOGLET.get())
                .add(FUREntityRegistry.ISNACHI.get())
                .add(FUREntityRegistry.IMP.get())
                .add(FUREntityRegistry.FRIGID.get())
                .add(FUREntityRegistry.UNBURIED.get())
                .add(FUREntityRegistry.WETA.get())
                .add(FUREntityRegistry.MUMMY.get())
             // .add(FUREntityRegistry.GRAVEROBBER.get())
        		.add(FUREntityRegistry.SCARAB.get());
    }

    // ── fur:wendigo_targets ──────────────────────────────────────────────────
    private void addWendigoTargetsTag() {
        tag(WENDIGO_TARGETS)
                .addTag(LIVESTOCKS)
                .addTag(net.minecraft.tags.EntityTypeTags.RAIDERS)
                .add(EntityType.VILLAGER)
                .add(EntityType.WANDERING_TRADER);
    }

    // ── fur:vespa_targets ────────────────────────────────────────────────────
    private void addVespaTargetsTag() {
        tag(VESPA_TARGETS)
                .addTag(ZOMBIES);
    }

    // ── fur:beelzebub_targets ────────────────────────────────────────────────
    private void addBeelzebubTargetsTag() {
        tag(BEELZEBUB_TARGETS)
                .addTag(ZOMBIES);
    }

    // ── fur:drops_illager_nose ───────────────────────────────────────────────
    // Deliberately not minecraft:raiders (that would include Ravager).
    private void addDropsIllagerNoseTag() {
        tag(DROPS_ILLAGER_NOSE)
                .add(EntityType.PILLAGER)
                .add(EntityType.VINDICATOR)
                .add(EntityType.EVOKER)
                .add(EntityType.ILLUSIONER)
                .add(EntityType.WITCH)
                .add(FUREntityRegistry.GRAVEROBBER.get());
    }
}
