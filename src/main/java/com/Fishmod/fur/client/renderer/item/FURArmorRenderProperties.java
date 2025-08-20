package com.Fishmod.fur.client.renderer.item;

import com.Fishmod.fur.client.model.armor.MoltenArmorModel;
import com.Fishmod.fur.client.model.layer.FURModelLayers;
import com.Fishmod.fur.item.MoltenArmorItem;

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
	protected static HumanoidModel<LivingEntity> INNER_ARMOR_MODEL;
	
    public static void initializeModels() {
        init = true;
        INNER_ARMOR_MODEL = new HumanoidModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.PLAYER_INNER_ARMOR));
        MOLTEN_ARMOR_MODEL = new MoltenArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(FURModelLayers.MOLTEN_ARMOR));
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
        }

        return _default;
    }
    
}
