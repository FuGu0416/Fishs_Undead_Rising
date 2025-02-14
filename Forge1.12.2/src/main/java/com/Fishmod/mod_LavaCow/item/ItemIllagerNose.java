package com.Fishmod.mod_LavaCow.item;

import java.util.List;
import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.client.model.armor.ModelIllagerNose;
import com.Fishmod.mod_LavaCow.init.FishItems;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemIllagerNose extends ItemArmor {
    private ModelIllagerNose modelIllagerNose;

    public ItemIllagerNose(String registryName, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
        super(FishItems.ARMOR_ILLAGER_NOSE, renderIndexIn, equipmentSlotIn);
        setUnlocalizedName(mod_LavaCow.MODID + "." + registryName);
        setRegistryName(registryName);
    }

    /**
     * Return an item rarity from EnumRarity
     */
    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }

    @Override
    public boolean isDamageable() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot armorSlot, String type) {
        return "mod_lavacow:textures/armors/illager_nose.png";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase player, ItemStack stack, EntityEquipmentSlot armorSlot, ModelBiped modelBiped) {
        if (this.modelIllagerNose == null) {
            this.modelIllagerNose = new ModelIllagerNose(1.0F);
        }

        return this.modelIllagerNose;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flag) {
        list.add(TextFormatting.YELLOW + I18n.format("tootip.mod_lavacow.illager_nose"));
    }

}
