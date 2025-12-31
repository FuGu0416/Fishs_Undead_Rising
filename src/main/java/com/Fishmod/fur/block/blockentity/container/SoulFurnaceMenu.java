package com.Fishmod.fur.block.blockentity.container;

import com.Fishmod.fur.init.FURMenuTypesRegistry;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SoulFurnaceMenu extends AbstractContainerMenu {
    private final Container container; 
    private final ContainerData data;
    
    public SoulFurnaceMenu(int id, Inventory inv, FriendlyByteBuf buf) {
        this(id, inv, new SimpleContainer(7), new SimpleContainerData(4));
    }

    public SoulFurnaceMenu(int id, Inventory inv, Container container, ContainerData data) {
        super(FURMenuTypesRegistry.SOUL_FURNACE.get(), id);
        this.container = container;
        this.data = data;
        
        for (int i = 0; i < 5; i++) {
            addSlot(new Slot(container, i, 30 + i * 18, 20));
        }

        addSlot(new Slot(container, 6, 124, 38));

        for (int y = 0; y < 3; y++)
            for (int x = 0; x < 9; x++)
                addSlot(new Slot(inv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));

        for (int x = 0; x < 9; x++)
            addSlot(new Slot(inv, x, 8 + x * 18, 142));
    }    

    @Override
    public boolean stillValid(Player player) {
    	return this.container.stillValid(player);
    }

	@Override
	public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getProgressScaled(int k) {
        int i = this.data.get(0);
        int j = this.data.get(1);
        return j != 0 && i != 0 ? i * k / j : 0;
	}
}
