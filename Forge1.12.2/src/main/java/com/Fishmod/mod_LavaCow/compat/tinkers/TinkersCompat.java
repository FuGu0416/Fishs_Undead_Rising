package com.Fishmod.mod_LavaCow.compat.tinkers;

import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.init.Modblocks;

import net.minecraftforge.common.MinecraftForge;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.ArrowShaftMaterialStats;
import slimeknights.tconstruct.library.materials.BowMaterialStats;
import slimeknights.tconstruct.library.materials.BowStringMaterialStats;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.FletchingMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.HarvestLevels;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.tools.TinkerMaterials;

import static slimeknights.tconstruct.library.materials.MaterialTypes.HEAD;

import slimeknights.tconstruct.tools.TinkerTraits;
//import slimeknights.tconstruct.tools.TinkerModifiers;

public class TinkersCompat {
    private static final TinkersCompat INSTANCE = new TinkersCompat();
    private static boolean registered = false;

    // Materials are used universally between Tinkers' Construct tools and Construct's Armory armor
    public static final Material MOLTEN_MEAT = new Material("mod_lavacow.moltenbeef", 0xFF542B);
    public static final Material CHITIN = new Material("mod_lavacow.chitin", 0xAE8A6F);
    public static final Material VESPA_CARAPACE = new Material("mod_lavacow.vespa_carapace", 0x85E214);
    public static final Material SCYTHE_CLAW = new Material("mod_lavacow.scythe_claw", 0x625E5E);
    public static final Material ECTOPLASM = new Material("mod_lavacow.ectoplasm", 0x7AF2FF);
    public static final Material HOLY_SLUDGE = new Material("mod_lavacow.holy_sludge", 0xFFD200);
    public static final Material ANCIENT_AMBER = new Material("mod_lavacow.ancient_amber", 0xBA7232);

    // Bow Materials
    public static final Material CURSEWEAVE_FABRIC = new Material("mod_lavacow.curseweave_fabric", 0xD5C583);
    public static final Material HYPHAE = new Material("mod_lavacow.hyphae", 0x9E864D);
    public static final Material MOSSY_STICK = new Material("mod_lavacow.mossy_stick", 0x635545);
    public static final Material CACTUS_THORN = new Material("mod_lavacow.cactus_thorn", 0xD1B66C);
    public static final Material POISON_STINGER = new Material("mod_lavacow.poison_stinger", 0x547037);
    public static final Material BLACK_FEATHER = new Material("mod_lavacow.black_feather", 0x5B4870);

    // These traits are for Tinkers' Construct tools and not Construct's Armory armor
    public static final AbstractTrait BROODMOTHER = new TraitBroodMother();
    public static final AbstractTrait FAMINE = new TraitFamine();
    public static final AbstractTrait UNHOLYTOUCH = new TraitUnholyTouch();
    public static final AbstractTrait AMBER_PHARAOH = new TraitAmberPharaoh();

    public static void register() {
        if (!registered) {
            registered = true;
            MinecraftForge.EVENT_BUS.register(INSTANCE);
            init();
        } else {
            throw new RuntimeException("You can only call TinkersCompat.register() once");
        }
    }

    private static void init() {
        TinkerMaterials.materials.add(MOLTEN_MEAT);
        TinkerRegistry.integrate(MOLTEN_MEAT).preInit();
        MOLTEN_MEAT.addItem(FishItems.MOLTENBEEF, 1, Material.VALUE_Ingot);
        MOLTEN_MEAT.setRepresentativeItem(FishItems.MOLTENBEEF);
        MOLTEN_MEAT.setCraftable(true);
        MOLTEN_MEAT.setCastable(false);
        TinkerRegistry.addMaterialStats(MOLTEN_MEAT,
                new HeadMaterialStats(500, 5.5F, 5.5F, HarvestLevels.OBSIDIAN),
                new HandleMaterialStats(1.0F, 100),
                new ExtraMaterialStats(200),
                new BowMaterialStats(0.5f, 0.4F, 3.0F));
        MOLTEN_MEAT.addTrait(TinkerTraits.autosmelt, HEAD);
        MOLTEN_MEAT.addTrait(TinkerTraits.superheat, HEAD);
        MOLTEN_MEAT.addTrait(TinkerTraits.superheat);

        TinkerMaterials.materials.add(CHITIN);
        TinkerRegistry.integrate(CHITIN).preInit();
        CHITIN.addItem(FishItems.CHITIN, 1, Material.VALUE_Ingot);
        CHITIN.setRepresentativeItem(FishItems.CHITIN);
        CHITIN.setCraftable(true);
        CHITIN.setCastable(false);
        TinkerRegistry.addMaterialStats(CHITIN,
                new HeadMaterialStats(200, 5.0F, 3.0F, HarvestLevels.IRON),
                new HandleMaterialStats(0.9F, 50),
                new ExtraMaterialStats(50),
                new BowMaterialStats(0.95F, 0.6F, 0.0F));
        CHITIN.addTrait(TinkerTraits.sharp, HEAD);
        CHITIN.addTrait(TinkerTraits.fractured);

        TinkerMaterials.materials.add(VESPA_CARAPACE);
        TinkerRegistry.integrate(VESPA_CARAPACE).preInit();
        VESPA_CARAPACE.addItem(FishItems.VESPA_CARAPACE, 1, Material.VALUE_Ingot);
        VESPA_CARAPACE.addItem(Modblocks.item_block_vespa_carapace, 1, Material.VALUE_Block);
        VESPA_CARAPACE.setRepresentativeItem(FishItems.VESPA_CARAPACE);
        VESPA_CARAPACE.setCraftable(true);
        VESPA_CARAPACE.setCastable(false);
        TinkerRegistry.addMaterialStats(VESPA_CARAPACE,
                new HeadMaterialStats(600, 6.0F, 4.0F, HarvestLevels.COBALT),
                new HandleMaterialStats(0.9F, 100),
                new ExtraMaterialStats(150),
                new BowMaterialStats(1.0F, 1.2F, 1.0F));
        VESPA_CARAPACE.addTrait(BROODMOTHER, HEAD);
        VESPA_CARAPACE.addTrait(TinkerTraits.poisonous, HEAD);
        VESPA_CARAPACE.addTrait(TinkerTraits.poisonous);

        TinkerMaterials.materials.add(SCYTHE_CLAW);
        TinkerRegistry.integrate(SCYTHE_CLAW).preInit();
        SCYTHE_CLAW.addItem(FishItems.SCYTHE_CLAW, 1, Material.VALUE_Shard);
        SCYTHE_CLAW.addItem(Modblocks.item_block_scythe_claw, 1, Material.VALUE_Block);
        SCYTHE_CLAW.setRepresentativeItem(FishItems.SCYTHE_CLAW);
        SCYTHE_CLAW.setCraftable(true);
        SCYTHE_CLAW.setCastable(false);
        TinkerRegistry.addMaterialStats(SCYTHE_CLAW,
                new HeadMaterialStats(200, 10.0F, 8.0F, HarvestLevels.IRON),
                new HandleMaterialStats(1.2F, 0),
                new ExtraMaterialStats(100),
                new BowMaterialStats(1.05F, 1.2F, 0.0F));
        SCYTHE_CLAW.addTrait(FAMINE, HEAD);
        SCYTHE_CLAW.addTrait(TinkerTraits.coldblooded);

        TinkerMaterials.materials.add(ECTOPLASM);
        TinkerRegistry.integrate(ECTOPLASM).preInit();
        ECTOPLASM.addItem(FishItems.ECTOPLASM_INGOT, 1, Material.VALUE_Ingot);
        ECTOPLASM.addItem(Modblocks.item_block_ectoplasm, 1, Material.VALUE_Block);
        ECTOPLASM.setRepresentativeItem(FishItems.ECTOPLASM_INGOT);
        ECTOPLASM.setCraftable(true);
        ECTOPLASM.setCastable(false);
        TinkerRegistry.addMaterialStats(ECTOPLASM,
                new HeadMaterialStats(2000, 14.0F, 0.0F, HarvestLevels.STONE),
                new HandleMaterialStats(1.4F, -50),
                new ExtraMaterialStats(220),
                new BowMaterialStats(3.0F, 1.0F, -1.0F));
        ECTOPLASM.addTrait(UNHOLYTOUCH);

        TinkerMaterials.materials.add(HOLY_SLUDGE);
        TinkerRegistry.integrate(HOLY_SLUDGE).preInit();
        HOLY_SLUDGE.addItem(FishItems.HOLY_SLUDGE, 1, Material.VALUE_Ingot);
        HOLY_SLUDGE.setRepresentativeItem(FishItems.HOLY_SLUDGE);
        HOLY_SLUDGE.setCraftable(true);
        HOLY_SLUDGE.setCastable(false);
        TinkerRegistry.addMaterialStats(HOLY_SLUDGE,
                new HeadMaterialStats(740, 3.0F, 5.0F, HarvestLevels.STONE),
                new HandleMaterialStats(1.1F, -100),
                new ExtraMaterialStats(175),
                new BowMaterialStats(1.0F, 1.0F, 1.0F));
        HOLY_SLUDGE.addTrait(TinkerTraits.holy);

        TinkerMaterials.materials.add(ANCIENT_AMBER);
        TinkerRegistry.integrate(ANCIENT_AMBER).preInit();
        ANCIENT_AMBER.addItem(FishItems.ANCIENT_AMBER, 1, Material.VALUE_Ingot);
        ANCIENT_AMBER.setRepresentativeItem(FishItems.ANCIENT_AMBER);
        ANCIENT_AMBER.setCraftable(true);
        ANCIENT_AMBER.setCastable(false);
        TinkerRegistry.addMaterialStats(ANCIENT_AMBER,
                new HeadMaterialStats(550, 7.0F, 5.0F, HarvestLevels.IRON),
                new HandleMaterialStats(1.0F, 100),
                new ExtraMaterialStats(125),
                new BowMaterialStats(1.1F, 1.1F, 1.0F));
        ANCIENT_AMBER.addTrait(AMBER_PHARAOH, HEAD);
        ANCIENT_AMBER.addTrait(TinkerTraits.aridiculous, HEAD);
        ANCIENT_AMBER.addTrait(TinkerTraits.aridiculous);

        /* Bow Materials */
        TinkerMaterials.materials.add(CURSEWEAVE_FABRIC);
        TinkerRegistry.integrate(CURSEWEAVE_FABRIC).preInit();
        CURSEWEAVE_FABRIC.addItem(FishItems.CURSED_FABRIC, 1, Material.VALUE_Ingot);
        CURSEWEAVE_FABRIC.setRepresentativeItem(FishItems.CURSED_FABRIC);
        CURSEWEAVE_FABRIC.setCraftable(true);
        CURSEWEAVE_FABRIC.setCastable(false);
        TinkerRegistry.addMaterialStats(CURSEWEAVE_FABRIC,
                new BowStringMaterialStats(1.3F));

        TinkerMaterials.materials.add(HYPHAE);
        TinkerRegistry.integrate(HYPHAE).preInit();
        HYPHAE.addItem(FishItems.HYPHAE, 1, Material.VALUE_Ingot);
        HYPHAE.setRepresentativeItem(FishItems.HYPHAE);
        HYPHAE.setCraftable(true);
        HYPHAE.setCastable(false);
        TinkerRegistry.addMaterialStats(HYPHAE,
                new BowStringMaterialStats(2.0F));

        TinkerMaterials.materials.add(MOSSY_STICK);
        TinkerRegistry.integrate(MOSSY_STICK).preInit();
        MOSSY_STICK.addItem(FishItems.MOSSY_STICK, 1, Material.VALUE_Ingot);
        MOSSY_STICK.setRepresentativeItem(FishItems.MOSSY_STICK);
        MOSSY_STICK.setCraftable(true);
        MOSSY_STICK.setCastable(false);
        TinkerRegistry.addMaterialStats(MOSSY_STICK,
                new ArrowShaftMaterialStats(1.4F, 10));
        MOSSY_STICK.addTrait(TinkerTraits.ecological);

        TinkerMaterials.materials.add(CACTUS_THORN);
        TinkerRegistry.integrate(CACTUS_THORN).preInit();
        CACTUS_THORN.addItem(FishItems.CACTUS_THORN, 1, Material.VALUE_Ingot);
        CACTUS_THORN.setRepresentativeItem(FishItems.CACTUS_THORN);
        CACTUS_THORN.setCraftable(true);
        CACTUS_THORN.setCastable(false);
        TinkerRegistry.addMaterialStats(CACTUS_THORN,
                new ArrowShaftMaterialStats(0.75F, 10));
        CACTUS_THORN.addTrait(TinkerTraits.splitting);

        TinkerMaterials.materials.add(POISON_STINGER);
        TinkerRegistry.integrate(POISON_STINGER).preInit();
        POISON_STINGER.addItem(FishItems.POISONSTINGER, 1, Material.VALUE_Ingot);
        POISON_STINGER.setRepresentativeItem(FishItems.POISONSTINGER);
        POISON_STINGER.setCraftable(true);
        POISON_STINGER.setCastable(false);
        TinkerRegistry.addMaterialStats(POISON_STINGER,
                new ArrowShaftMaterialStats(1.5F, 40));
        POISON_STINGER.addTrait(TinkerTraits.splitting);
        POISON_STINGER.addTrait(TinkerTraits.poisonous);

        TinkerMaterials.materials.add(BLACK_FEATHER);
        TinkerRegistry.integrate(BLACK_FEATHER).preInit();
        BLACK_FEATHER.addItem(FishItems.FEATHER_BLACK, 1, Material.VALUE_Ingot);
        BLACK_FEATHER.setRepresentativeItem(FishItems.FEATHER_BLACK);
        BLACK_FEATHER.setCraftable(true);
        BLACK_FEATHER.setCastable(false);
        TinkerRegistry.addMaterialStats(BLACK_FEATHER,
                new FletchingMaterialStats(1.0F, 1.2F));
    }

    protected static boolean isSmelteryLoaded() {
        return TConstruct.pulseManager.isPulseLoaded(TinkerSmeltery.PulseId);
    }

    public static void post() {
        /*if (TinkerModifiers.modNecrotic != null) {
            TinkerModifiers.modNecrotic.addItem(FishItems.UNDYINGHEART);
            TinkerModifiers.modSmite.addItem(FishItems.HOLY_SLUDGE);
            TinkerTraits.poisonous.addItem(FishItems.POISONSPORE);
            TinkerTraits.poisonous.addItem(FishItems.POISONSTINGER);
        }*/
    }
}
