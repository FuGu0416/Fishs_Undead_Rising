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
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import baubles.api.IBauble;

@Optional.Interface(iface = "baubles.api.IBauble", modid = "baubles", striprefs = true)
public class ItemIllagerNose extends ItemArmor implements IBauble {
    private ModelIllagerNose modelIllagerNose;

    public ItemIllagerNose(String registryName, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
        super(FishItems.ARMOR_ILLAGER_NOSE, renderIndexIn, equipmentSlotIn);
        setTranslationKey(mod_LavaCow.MODID + "." + registryName);
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

    /**
     * Baubles support: the nose can be worn in the Baubles HEAD slot (in addition to the vanilla
     * head armor slot), mirroring the Curios integration in the newer versions. Disguise detection
     * checks both slots (see EntityGraveRobber / ModEventHandler).
     */
    @Override
    @Optional.Method(modid = "baubles")
    public baubles.api.BaubleType getBaubleType(ItemStack stack) {
        return baubles.api.BaubleType.HEAD;
    }

    @Override
    @Optional.Method(modid = "baubles")
    public boolean canEquip(ItemStack stack, EntityLivingBase entity) {
        return true;
    }

    @Override
    @Optional.Method(modid = "baubles")
    public boolean canUnequip(ItemStack stack, EntityLivingBase entity) {
        return true;
    }

    @Override
    @Optional.Method(modid = "baubles")
    public void onWornTick(ItemStack stack, EntityLivingBase entity) {
    }

}
