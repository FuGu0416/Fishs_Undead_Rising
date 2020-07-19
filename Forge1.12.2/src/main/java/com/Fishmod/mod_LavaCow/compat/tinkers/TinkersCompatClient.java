package com.Fishmod.mod_LavaCow.compat.tinkers;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import slimeknights.tconstruct.library.client.MaterialRenderInfo;

public class TinkersCompatClient {
	
    public static void preInit() {
    	MaterialRenderInfo MoltenMeatInfo = new MaterialRenderInfo.BlockTexture(new ResourceLocation("mod_lavacow:tinkers/molten_meat"));
        TinkersCompat.MOLTEN_MEAT.setRenderInfo(MoltenMeatInfo);
        MaterialRenderInfo VespaCarapaceInfo = new MaterialRenderInfo.BlockTexture(new ResourceLocation("mod_lavacow:tinkers/vespa_carapace"));
        TinkersCompat.VESPA_CARAPACE.setRenderInfo(VespaCarapaceInfo);
        MaterialRenderInfo ScytheClawInfo = new MaterialRenderInfo.BlockTexture(new ResourceLocation("mod_lavacow:tinkers/scythe_claw"));
        TinkersCompat.SCYTHE_CLAW.setRenderInfo(ScytheClawInfo);
    }

    public static void registerModels(ModelRegistryEvent event) {

    }
}
