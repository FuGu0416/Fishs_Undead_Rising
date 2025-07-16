package com.Fishmod.fur.client.model.layered;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.client.model.armor.MoltenArmorModel;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;

public class FURModelLayers {
	public static final ModelLayerLocation MOLTEN_ARMOR = createLocation("molten_armor", "main");
	
    public static void register(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(MOLTEN_ARMOR, () -> MoltenArmorModel.createArmorLayer(new CubeDeformation(0.75F)));
    }

    private static ModelLayerLocation createLocation(String model, String layer) {
        return new ModelLayerLocation(new ResourceLocation(mod_LavaCow.MODID, model), layer);
    }	
}
