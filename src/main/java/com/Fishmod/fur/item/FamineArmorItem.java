package com.Fishmod.fur.item;

import java.util.List;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.init.FURItemRegistry;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class FamineArmorItem extends ArmorItem {	
	public FamineArmorItem(ArmorItem.Type slot, Item.Properties p_i48534_3_) {
		super(ArmorMaterials.DIAMOND, slot, p_i48534_3_);
	}		
		
	@Override
	public boolean isValidRepairItem(ItemStack armour, ItemStack material) {
		return material.getItem() == FURItemRegistry.FOUL_HIDE.get();
	}
	
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept((IClientItemExtensions) mod_LavaCow.PROXY.getArmorProperties());
    }
    
    /**
     * Called by the powdered snow block to check if a living entity wearing this can walk on the snow, granting the same behavior as leather boots.
     * Only affects items worn in the boots slot.
     *
     * @param stack  Stack instance
     * @param wearer The entity wearing this ItemStack
     *
     * @return True if the entity can walk on powdered snow
     */
    @Override
    public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer) {
        return stack.is(FURItemRegistry.FAMINE_ARMOR_BOOTS.get());
    }

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		if (slot == EquipmentSlot.LEGS) {
			return mod_LavaCow.MODID + ":textures/armors/famine/famine_layer_2.png";
		} else {
			return mod_LavaCow.MODID + ":textures/armors/famine/famine_layer_1.png";
		}
	}
	
	@Override
    @OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(Component.translatable("item.fur.famine_armor.desc0").withStyle(ChatFormatting.YELLOW));
		tooltip.add(Component.translatable("item.fur.famine_armor.desc1").withStyle(ChatFormatting.YELLOW));
	}
}
