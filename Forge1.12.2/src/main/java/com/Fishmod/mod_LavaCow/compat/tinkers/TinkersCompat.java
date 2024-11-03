package com.Fishmod.mod_LavaCow.compat.tinkers;

import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.init.Modblocks;

import net.minecraftforge.common.MinecraftForge;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.BowMaterialStats;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.HarvestLevels;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.tools.TinkerMaterials;

import static slimeknights.tconstruct.library.materials.MaterialTypes.HEAD;

import slimeknights.tconstruct.tools.TinkerTraits;
import slimeknights.tconstruct.tools.TinkerModifiers;

public class TinkersCompat {
    private static final TinkersCompat INSTANCE = new TinkersCompat();
    private static boolean registered = false;

    public static final Material MOLTEN_MEAT = new Material("mod_lavacow.moltenbeef", 0xFF542B);
    public static final Material VESPA_CARAPACE = new Material("mod_lavacow.vespa_carapace", 0x85E214);
    public static final Material SCYTHE_CLAW = new Material("mod_lavacow.scythe_claw", 0x625E5E);
    public static final Material ECTOPLASM = new Material("mod_lavacow.ectoplasm", 0x7AF2FF);

    public static final AbstractTrait BROODMOTHER = new TraitBroodMother();
    public static final AbstractTrait FAMINE = new TraitFamine();
    public static final AbstractTrait UNHOLYTOUCH = new TraitUnholyTouch();

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
        SCYTHE_CLAW.addTrait(TinkerTraits.coldblooded, HEAD);
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
