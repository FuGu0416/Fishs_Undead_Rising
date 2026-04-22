package com.Fishmod.fur.block.blockentity.container;

import java.util.Objects;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.block.blockentity.SoulFurnaceBlockEntity;
import com.Fishmod.fur.data.providers.FURItemTagsProvider;
import com.Fishmod.fur.init.FURMenuTypesRegistry;
import com.mojang.datafixers.util.Pair;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class SoulFurnaceMenu extends AbstractContainerMenu {
	public static final ResourceLocation EMPTY_CONTAINER_SLOT_BOWL = new ResourceLocation(mod_LavaCow.MODID, "item/empty_container_slot_bowl");
	
	public final SoulFurnaceBlockEntity blockEntity;
    private final ContainerData data;
    
    public SoulFurnaceMenu(int id, Inventory inv, FriendlyByteBuf buf) {
        this(id, inv, getBlockEntity(inv, buf), new SimpleContainerData(4));
    }

    public SoulFurnaceMenu(int id, final Inventory inv, final SoulFurnaceBlockEntity blockEntity, ContainerData data) {
        super(FURMenuTypesRegistry.SOUL_FURNACE.get(), id);
        this.blockEntity = blockEntity;
        this.data = data;
        
        this.addDataSlots(this.data);
        
        // Ingrediant
		int inputStartX = 30;
		int inputStartY = 17;
		int borderSlotSize = 18;
		for (int row = 0; row < 2; ++row) {
			for (int column = 0; column < 3; ++column) {
				this.addSlot(new Slot(blockEntity, (row * 3) + column,
						inputStartX + (column * borderSlotSize),
						inputStartY + (row * borderSlotSize)));
			}
		}
        
        // Container
        this.addSlot(new Slot(blockEntity, 6, 124, 55)
		{
			public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
				return Pair.of(InventoryMenu.BLOCK_ATLAS, EMPTY_CONTAINER_SLOT_BOWL);
			}
		});
        
        // Result
		this.addSlot(new Slot(blockEntity, 7, 124, 26));

        // Inventory
        for (int y = 0; y < 3; y++)
            for (int x = 0; x < 9; x++)
            	this.addSlot(new Slot(inv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));

        // Hotbar
        for (int x = 0; x < 9; x++)
        	this.addSlot(new Slot(inv, x, 8 + x * 18, 142));
    }    
    
	private static SoulFurnaceBlockEntity getBlockEntity(final Inventory playerInventory, final FriendlyByteBuf data) {
		Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
		Objects.requireNonNull(data, "data cannot be null");
		final BlockEntity tileAtPos = playerInventory.player.level().getBlockEntity(data.readBlockPos());
		if (tileAtPos instanceof SoulFurnaceBlockEntity) {
			return (SoulFurnaceBlockEntity) tileAtPos;
		}
		throw new IllegalStateException("Block Entity is not correct! " + tileAtPos);
	}

    @Override
    public boolean stillValid(Player player) {
    	return this.blockEntity.stillValid(player);
    }

	@Override
	public ItemStack quickMoveStack(Player playerIn, int index) {
        int indexContainerInput = 6;
        int indexOutput = 7;
        int startPlayerInv = indexOutput + 1;
        int endPlayerInv = startPlayerInv + 36;
        ItemStack slotStackCopy = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            slotStackCopy = slotStack.copy();
            if (index == indexOutput) {
				if (!this.moveItemStackTo(slotStack, startPlayerInv, endPlayerInv, true)) {
					return ItemStack.EMPTY;
				}
			} else if (index > indexOutput) {
				boolean isValidContainer = slotStack.is(FURItemTagsProvider.SERVING_CONTAINERS) || slotStack.is(blockEntity.getContainer().getItem());
				if (isValidContainer && !this.moveItemStackTo(slotStack, indexContainerInput, indexContainerInput + 1, false)) {
					return ItemStack.EMPTY;
				} else if (!this.moveItemStackTo(slotStack, 0, indexContainerInput, false)) {
					return ItemStack.EMPTY;
				} else if (!this.moveItemStackTo(slotStack, indexContainerInput, indexOutput, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(slotStack, startPlayerInv, endPlayerInv, false)) {
				return ItemStack.EMPTY;
			}

            if (slotStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (slotStack.getCount() == slotStackCopy.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, slotStack);
        }
        
        return slotStackCopy;
	}

	public int getProgressScaled(int k) {
        int i = this.data.get(0);
        int j = this.data.get(1);
        
        return j != 0 && i != 0 ? i * k / j : 0;
	}
}
