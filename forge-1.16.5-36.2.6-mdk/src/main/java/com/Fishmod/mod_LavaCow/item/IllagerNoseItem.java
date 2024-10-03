package com.Fishmod.mod_LavaCow.item;

import java.util.List;
import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.model.armor.ModelIllagerNose;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class IllagerNoseItem extends ArmorItem {
	
	public IllagerNoseItem(Item.Properties p_i48534_3_) {
		super(ArmorMaterial.LEATHER, EquipmentSlotType.HEAD, p_i48534_3_);
	}
	
    /**
     * Returns true if this item has an enchantment glint. By default, this returns
     * <code>stack.isItemEnchanted()</code>, but other items can override it (for instance, written books always return
     * true).
     *  
     * Note that if you override this method, you generally want to also call the super version (on {@link Item}) to get
     * the glint for enchanted items. Of course, that is unnecessary if the overwritten version always returns true.
     */
	@Override
	public boolean isFoil(ItemStack stack) {
        return false;
    }
    
	@Override
	public boolean isValidRepairItem(ItemStack armour, ItemStack material) {
		return false;
	}
    
    /**
     * Called to tick armor in the armor slot. Override to do something
     */
	@Override
    public void onArmorTick(ItemStack itemStack, World world, PlayerEntity player) {	
		super.onArmorTick(itemStack, world, player);
    }
    
	@Override
    @OnlyIn(Dist.CLIENT)
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType armorSlot, String type) {
		return "mod_lavacow:textures/armors/illager_nose.png";
	}
	
	@SuppressWarnings("unchecked")
	@Override
    @OnlyIn(Dist.CLIENT)
	public <E extends BipedModel<?>> E getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, E _default) {
		return (E) new ModelIllagerNose<>(1.0F);
	}
	
	@Override
    @OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TranslationTextComponent("tooltip.mod_lavacow:illager_nose").withStyle(TextFormatting.YELLOW));
	}

}
