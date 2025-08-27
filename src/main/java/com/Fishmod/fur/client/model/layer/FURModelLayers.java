package com.Fishmod.fur.client.model.layer;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.client.model.armor.MoltenArmorModel;
import com.Fishmod.fur.client.model.block.ModelScarecrowHead_common;
import com.Fishmod.fur.client.model.block.ModelScarecrowHead_plague;
import com.Fishmod.fur.client.model.block.ModelScarecrowHead_straw;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;

public class FURModelLayers {
	public static final ModelLayerLocation MOLTEN_ARMOR = createLocation("molten_armor", "main");
	
    public static void register(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(MOLTEN_ARMOR, () -> MoltenArmorModel.createArmorLayer(new CubeDeformation(0.75F)));
        event.registerLayerDefinition(ModelScarecrowHead_common.LAYER_LOCATION, ModelScarecrowHead_common::createBodyLayer);
        event.registerLayerDefinition(ModelScarecrowHead_straw.LAYER_LOCATION, ModelScarecrowHead_straw::createBodyLayer);
        event.registerLayerDefinition(ModelScarecrowHead_plague.LAYER_LOCATION, ModelScarecrowHead_plague::createBodyLayer);
    }

    private static ModelLayerLocation createLocation(String model, String layer) {
        return new ModelLayerLocation(new ResourceLocation(mod_LavaCow.MODID, model), layer);
    }	
}
