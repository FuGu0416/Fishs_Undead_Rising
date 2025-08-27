package com.Fishmod.fur.item;

import com.Fishmod.fur.mod_LavaCow;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class ScarecrowHeadItem extends BlockItem implements Equipable {

	public ScarecrowHeadItem(Block block, Item.Properties p_i48527_2_) {
		super(block, p_i48527_2_);
	}
	
	public InteractionResultHolder<ItemStack> use(Level p_40395_, Player p_40396_, InteractionHand p_40397_) {
		return this.swapWithEquipmentSlot(this, p_40395_, p_40396_, p_40397_);
	}    
    
    @Override
    public void initializeClient(java.util.function.Consumer<IClientItemExtensions> consumer) {
        consumer.accept((IClientItemExtensions) mod_LavaCow.PROXY.getISTERProperties());
    }

	@Override
	public EquipmentSlot getEquipmentSlot() {
		return EquipmentSlot.HEAD;
	}
}
