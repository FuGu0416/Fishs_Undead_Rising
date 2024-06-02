package com.Fishmod.mod_LavaCow.item;

import java.util.List;
import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.google.common.collect.Multimap;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemGhostlyArmor extends ItemArmor {
    private static final double[] HEALTH_ADDITION = new double[] {-2.0D, -3.0D, -3.0D, -2.0D};
    private AttributeModifier maxHealth;
	private int set;
	private String armorTexture;
	
	public ItemGhostlyArmor(String registryName, int renderIndex, EntityEquipmentSlot slot) {
		super(FishItems.ARMOR_GHOSTLY, renderIndex, slot);
		setCreativeTab(mod_LavaCow.TAB_ITEMS);
		setUnlocalizedName(mod_LavaCow.MODID + "." + registryName);
        setRegistryName(registryName);
        
        this.maxHealth = new AttributeModifier("2AD3F246-FEE1-4E67-B886-69FD380BB150", HEALTH_ADDITION[slot.getIndex()], 0);

		if (registryName.equals("ghostlyarmor_leggings")) {
			this.armorTexture = "mod_lavacow:textures/armors/ghostly/ghostly_layer_2.png";
		} else {
			this.armorTexture = "mod_lavacow:textures/armors/ghostly/ghostly_layer_1.png";
		}
		
        this.set = 0;
	}
	
	@Override
	public boolean getIsRepairable(ItemStack armour, ItemStack material) {
		return material.getItem() == FishItems.ECTOPLASM_INGOT;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot armorSlot, String type) {
		return this.armorTexture;
	}
	
	public int getSetBonus() {
		return this.set;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		this.set = 0;

		for(ItemStack armor : player.getArmorInventoryList()) {
			if (armor.getItem().getClass() == ItemGhostlyArmor.class) {
				this.set++;
			}
		}
	}
	
    /**
     * Return an item rarity from EnumRarity
     */
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }
	
	@Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == this.armorType) {
            multimap.put(SharedMonsterAttributes.MAX_HEALTH.getName(), this.maxHealth);
        }

        return multimap;
    }
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flag) {
			list.add(TextFormatting.YELLOW + I18n.format("tootip.mod_lavacow.ghostlyarmor"));
			list.add(TextFormatting.YELLOW + I18n.format("tootip.mod_lavacow.ghostlyarmor.l2"));
	}

}
