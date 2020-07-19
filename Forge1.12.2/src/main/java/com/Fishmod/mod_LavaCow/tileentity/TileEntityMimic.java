package com.Fishmod.mod_LavaCow.tileentity;

import com.Fishmod.mod_LavaCow.entities.tameable.EntityMimic;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class TileEntityMimic extends TileEntityLockableLoot/* implements ITickable*/ {
	private NonNullList<ItemStack> chestContents;
	public IItemHandler itemHandler;
	protected EntityMimic chester;
	
	public TileEntityMimic(EntityMimic chest) {		
		this.chestContents = chest.inventory;
		this.chester = chest;
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}

	/**
	* Returns the number of slots in the inventory.
	*/
	public int getSizeInventory() {
		return this.chestContents.size();
	}
	
	public ItemStack getStackInSlot(int slot) {
		return this.chestContents.get(slot);
	}

	public boolean isEmpty() {
		for(ItemStack itemstack : this.chestContents) {
	         if (!itemstack.isEmpty()) {
	            return false;
	         }
	      }

	      return true;
	}

	public int getInventoryStackLimit() {
		return 64;
	}
	
	@Override
	public Block getBlockType() {
		return Blocks.CHEST;
	}

	public String getName() {
		return this.chester.getName();
	}
	
	/**
	* Don't rename this method to canInteractWith due to conflicts with Container
	*/
	public boolean isUsableByPlayer(EntityPlayer player) {
			return true;
	}

	public String getGuiID() {
		return "minecraft:chest";
	}
	
	public NonNullList<ItemStack> getInventory() {
		return this.chestContents;
	}
	
	protected NonNullList<ItemStack> getItems() {
		return this.chestContents;
	}

	protected void setItems(NonNullList<ItemStack> itemsIn) {
		this.chestContents = itemsIn;		
	}
	
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        this.fillWithLoot(playerIn);
        return new ContainerChest(playerInventory, this, playerIn);
    }
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.loadFromNbt(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		return this.saveToNbt(compound);
	}
	
	public void loadFromNbt(NBTTagCompound compound) {
		setInventory(NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY));
		if (compound.hasKey("Items", 9))
			ItemStackHelper.loadAllItems(compound, getItems());
	}

	public NBTTagCompound saveToNbt(NBTTagCompound compound) {
		ItemStackHelper.saveAllItems(compound, getItems(), false);
		return compound;
	}
	
	private void setInventory(NonNullList<ItemStack> withSize) {
		this.chestContents = withSize;		
	}

	@Override
	public boolean hasCustomName() {
		return true;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		ItemStack itemstack = ItemStackHelper.getAndSplit(getItems(), index, count);
		if (!itemstack.isEmpty())
			this.markDirty();
		return itemstack;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		getItems().set(index, stack);
        if (stack.getCount() > this.getInventoryStackLimit())
            stack.setCount(this.getInventoryStackLimit());
        this.markDirty();	
	}

	@Override
	public void openInventory(EntityPlayer player) {	
		this.chester.playSound(SoundEvents.BLOCK_CHEST_OPEN, 1.0F, 1.0F);
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		this.chester.playSound(SoundEvents.BLOCK_CHEST_CLOSE, 1.0F, 1.0F);
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		this.getItems().clear();
	}
	
	protected IItemHandler createUnSidedHandler() {
		return new InvWrapper(this);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return (T) (itemHandler == null ? (itemHandler = createUnSidedHandler()) : itemHandler);
		return super.getCapability(capability, facing);
	}
}
