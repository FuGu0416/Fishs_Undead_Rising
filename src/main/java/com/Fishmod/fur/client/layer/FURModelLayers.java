package com.Fishmod.fur.client.layer;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.client.model.armor.FamineArmorModel;
import com.Fishmod.fur.client.model.armor.IllagerNoseModel;
import com.Fishmod.fur.client.model.armor.MoltenArmorModel;
import com.Fishmod.fur.client.model.armor.SkeletonKingCrownModel;
import com.Fishmod.fur.client.model.block.ModelScarecrowHead_common;
import com.Fishmod.fur.client.model.block.ModelScarecrowHead_plague;
import com.Fishmod.fur.client.model.block.ModelScarecrowHead_straw;
import com.Fishmod.fur.client.model.item.ModelVespaShield;
import com.Fishmod.fur.client.model.GraveRobberModel;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;

public class FURModelLayers {
	public static final ModelLayerLocation MOLTEN_ARMOR = createLocation("molten_armor", "main");
	public static final ModelLayerLocation FAMINE_ARMOR = createLocation("famine_armor", "main");
	public static final ModelLayerLocation ILLAGER_NOSE = createLocation("illager_nose", "main");
	public static final ModelLayerLocation SKELETONKING_CROWN = createLocation("skeletonking_crown", "main");
	public static final ModelLayerLocation VESPA_SHIELD = createLocation("vespa_shield", "main");
	public static final ModelLayerLocation GRAVEROBBER = createLocation("graverobber", "main");

    public static void register(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(MOLTEN_ARMOR, () -> MoltenArmorModel.createArmorLayer(new CubeDeformation(0.75F)));
        event.registerLayerDefinition(FAMINE_ARMOR, () -> FamineArmorModel.createArmorLayer(new CubeDeformation(0.75F)));
        event.registerLayerDefinition(ILLAGER_NOSE, () -> IllagerNoseModel.createArmorLayer(new CubeDeformation(1.0F)));
        event.registerLayerDefinition(SKELETONKING_CROWN, () -> SkeletonKingCrownModel.createArmorLayer(new CubeDeformation(1.0F)));
        event.registerLayerDefinition(ModelScarecrowHead_common.LAYER_LOCATION, ModelScarecrowHead_common::createBodyLayer);
        event.registerLayerDefinition(ModelScarecrowHead_straw.LAYER_LOCATION, ModelScarecrowHead_straw::createBodyLayer);
        event.registerLayerDefinition(ModelScarecrowHead_plague.LAYER_LOCATION, ModelScarecrowHead_plague::createBodyLayer);
        event.registerLayerDefinition(VESPA_SHIELD, ModelVespaShield::createBodyLayer);
        event.registerLayerDefinition(GRAVEROBBER, GraveRobberModel::createBodyLayer);
    }

    private static ModelLayerLocation createLocation(String model, String layer) {
        return new ModelLayerLocation(new ResourceLocation(mod_LavaCow.MODID, model), layer);
    }	
}
