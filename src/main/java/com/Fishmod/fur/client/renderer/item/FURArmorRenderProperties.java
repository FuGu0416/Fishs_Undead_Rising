package com.Fishmod.fur.client.renderer.item;

import com.Fishmod.fur.client.layer.FURModelLayers;
import com.Fishmod.fur.client.model.armor.FamineArmorModel;
import com.Fishmod.fur.client.model.armor.IllagerNoseModel;
import com.Fishmod.fur.client.model.armor.MoltenArmorModel;
import com.Fishmod.fur.client.model.armor.SkeletonKingCrownModel;
import com.Fishmod.fur.item.ChitinArmorItem;
import com.Fishmod.fur.item.FamineArmorItem;
import com.Fishmod.fur.item.GhostlyArmorItem;
import com.Fishmod.fur.item.IllagerNoseItem;
import com.Fishmod.fur.item.MoltenArmorItem;
import com.Fishmod.fur.item.SkeletonKingCrownItem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class FURArmorRenderProperties implements IClientItemExtensions {
	private static boolean init;
	public static MoltenArmorModel<LivingEntity> MOLTEN_ARMOR_MODEL;
	public static FamineArmorModel<LivingEntity> FAMINE_ARMOR_MODEL;
	public static IllagerNoseModel<LivingEntity> ILLAGER_NOSE_MODEL;
	public static SkeletonKingCrownModel<LivingEntity> SKELETONKING_CROWN_MODEL;
	protected static HumanoidModel<LivingEntity> OUTER_ARMOR_MODEL;
	protected static HumanoidModel<LivingEntity> INNER_ARMOR_MODEL;	
	
    public static void initializeModels() {
        init = true;
        MOLTEN_ARMOR_MODEL = new MoltenArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(FURModelLayers.MOLTEN_ARMOR));
        FAMINE_ARMOR_MODEL = new FamineArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(FURModelLayers.FAMINE_ARMOR));
        ILLAGER_NOSE_MODEL = new IllagerNoseModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(FURModelLayers.ILLAGER_NOSE));
        SKELETONKING_CROWN_MODEL = new SkeletonKingCrownModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(FURModelLayers.SKELETONKING_CROWN));
        OUTER_ARMOR_MODEL = new HumanoidModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR));
        INNER_ARMOR_MODEL = new HumanoidModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.PLAYER_INNER_ARMOR));        
    }
    
    @Override
    public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {
        if (!init) {
            initializeModels();
        }
        
        if (itemStack.getItem() instanceof MoltenArmorItem) {
        	if (armorSlot == EquipmentSlot.LEGS) {
        		return INNER_ARMOR_MODEL;
        	} else {
        		return MOLTEN_ARMOR_MODEL;
        	}
        } else if (itemStack.getItem() instanceof ChitinArmorItem) {
        	if (armorSlot == EquipmentSlot.LEGS) {
        		return INNER_ARMOR_MODEL;
        	} else {
        		return OUTER_ARMOR_MODEL;
        	}
        } else if (itemStack.getItem() instanceof FamineArmorItem) {
        	if (armorSlot == EquipmentSlot.LEGS) {
        		return INNER_ARMOR_MODEL;
        	} else {
        		return FAMINE_ARMOR_MODEL;
        	}
        } else if (itemStack.getItem() instanceof GhostlyArmorItem) {
        	if (armorSlot == EquipmentSlot.LEGS) {
        		return INNER_ARMOR_MODEL;
        	} else {
        		return OUTER_ARMOR_MODEL;
        	}
        } else if (itemStack.getItem() instanceof IllagerNoseItem) {
        	// Helmet-only item: the 3D nose on the head bone (head/hat cubes are transparent).
        	return ILLAGER_NOSE_MODEL;
        } else if (itemStack.getItem() instanceof SkeletonKingCrownItem) {
        	// Helmet-only item: the 3D crown on the head bone (head/hat cubes are transparent).
        	return SKELETONKING_CROWN_MODEL;
        }

        return _default;
    }
    
}
