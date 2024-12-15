package com.Fishmod.mod_LavaCow.compat.tinkers;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import slimeknights.tconstruct.library.client.MaterialRenderInfo;

public class TinkersCompatClient {

    public static void preInit() {
        MaterialRenderInfo MoltenMeatInfo = new MaterialRenderInfo.BlockTexture(new ResourceLocation("mod_lavacow:tinkers/molten_meat"));
        TinkersCompat.MOLTEN_MEAT.setRenderInfo(MoltenMeatInfo);
        MaterialRenderInfo ChitinInfo = new MaterialRenderInfo.Default(9926497).setTextureSuffix("contrast");
        TinkersCompat.CHITIN.setRenderInfo(ChitinInfo);
        MaterialRenderInfo VespaCarapaceInfo = new MaterialRenderInfo.BlockTexture(new ResourceLocation("mod_lavacow:tinkers/vespa_carapace"));
        TinkersCompat.VESPA_CARAPACE.setRenderInfo(VespaCarapaceInfo);
        MaterialRenderInfo ScytheClawInfo = new MaterialRenderInfo.BlockTexture(new ResourceLocation("mod_lavacow:tinkers/scythe_claw"));
        TinkersCompat.SCYTHE_CLAW.setRenderInfo(ScytheClawInfo);
        MaterialRenderInfo HolySludgeInfo = new MaterialRenderInfo.MultiColor(16776949, 16765440, 15986627);
        TinkersCompat.HOLY_SLUDGE.setRenderInfo(HolySludgeInfo);
        MaterialRenderInfo AncientAmberInfo = new MaterialRenderInfo.MultiColor(9125644, 16690740, 16769920);
        TinkersCompat.ANCIENT_AMBER.setRenderInfo(AncientAmberInfo);

        /* Bow Materials */
        MaterialRenderInfo HyphaeInfo = new MaterialRenderInfo.MultiColor(5786162, 11453031, 6838076);
        TinkersCompat.HYPHAE.setRenderInfo(HyphaeInfo);
        MaterialRenderInfo MossyStickInfo = new MaterialRenderInfo.MultiColor(1840912, 5689855, 4340014);
        TinkersCompat.MOSSY_STICK.setRenderInfo(MossyStickInfo);
        MaterialRenderInfo BlackFeatherInfo = new MaterialRenderInfo.Default(4404564).setTextureSuffix("feather");
        TinkersCompat.BLACK_FEATHER.setRenderInfo(BlackFeatherInfo);
    }

    public static void registerModels(ModelRegistryEvent event) {

    }
}
