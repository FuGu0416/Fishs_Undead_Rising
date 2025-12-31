package com.Fishmod.fur.block.blockentity;

import java.util.Optional;

import com.Fishmod.fur.block.blockentity.container.SoulFurnaceMenu;
import com.Fishmod.fur.block.blockentity.container.SoulFurnaceRecipe;
import com.Fishmod.fur.init.FURBlockEntityRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SoulFurnaceBlockEntity extends BlockEntity implements MenuProvider, WorldlyContainer {
    private final NonNullList<ItemStack> items = NonNullList.withSize(7, ItemStack.EMPTY);
    private int progress;
    private int maxProgress = 200;
    private static final int[] INPUT_SLOTS = {0,1,2,3,4};
    private static final int[] OUTPUT_SLOTS = {6};
    
    public SoulFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(FURBlockEntityRegistry.SOUL_FURNACE.get(), pos, state);
    }
    
    public static void tick(Level level, BlockPos pos, BlockState state, SoulFurnaceBlockEntity be) {
        if (!level.isClientSide) {
            if (be.canCook()) {
                be.progress++;
                if (be.progress >= be.maxProgress) {
                    be.craft();
                }
            } else {
                be.progress = 0;
            }
        }
        
        if (level.isClientSide) {
            if (level.random.nextFloat() < 0.2f) {
                level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 0, 0.05, 0);
            }
        }
    }
    
    private Optional<SoulFurnaceRecipe> getCurrentRecipe() {
        SimpleContainer inv = new SimpleContainer(5);
        for (int i = 0; i < 5; i++) {
            inv.setItem(i, items.get(i));
        }

        return level.getRecipeManager()
            .getRecipeFor(SoulFurnaceRecipe.TYPE, inv, level);
    }

    public boolean canCook() {
        Optional<SoulFurnaceRecipe> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return false;
        if (!recipe.get().getContainer().test(items.get(5))) return false;
        
        ItemStack result = recipe.get().getResultItem(level.registryAccess());
        ItemStack output = items.get(6);

        if (output.isEmpty()) return true;
        if (!ItemStack.isSameItemSameTags(output, result)) return false;        
        
        return output.getCount() + result.getCount() <= output.getMaxStackSize();
    }
    
    public void craft() {
        Optional<SoulFurnaceRecipe> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return;

        ItemStack result = recipe.get().getResultItem(level.registryAccess()).copy();
        
        for (int i = 0; i < 5; i++) {
            items.get(i).shrink(1);
        }

        if (items.get(6).isEmpty()) {
            items.set(6, result);
        } else {
            items.get(6).grow(result.getCount());
        }
        
        items.get(5).shrink(1);

        progress = 0;
        setChanged();
    }

	@Override
	public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
	    return new SoulFurnaceMenu(id, inv, this, this.dataAccess);
	}

	@Override
	public int getContainerSize() {
		return this.items.size();
	}

	@Override
	public boolean isEmpty() {
		for(ItemStack itemstack : this.items) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}

		return true;
	}

	@Override
	public ItemStack getItem(int i) {
		return this.items.get(i);
	}

	@Override
	public ItemStack removeItem(int i, int j) {
		return ContainerHelper.removeItem(this.items, i, j);
	}

	@Override
	public ItemStack removeItemNoUpdate(int i) {
		return ContainerHelper.takeItem(this.items, i);
	}

	@Override
	public void setItem(int i, ItemStack stack) {
	      ItemStack itemstack = this.items.get(i);
	      boolean flag = !stack.isEmpty() && ItemStack.isSameItemSameTags(itemstack, stack);
	      this.items.set(i, stack);
	      if (stack.getCount() > this.getMaxStackSize()) {
	    	  stack.setCount(this.getMaxStackSize());
	      }

	      if (i == 0 && !flag) {
	         this.progress = 0;
	         this.setChanged();
	      }		
	}

	@Override
	public boolean stillValid(Player player) {
		return Container.stillValidBlockEntity(this, player);
	}

	@Override
	public void clearContent() {
		this.items.clear();		
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
	    return side == Direction.DOWN ? OUTPUT_SLOTS : INPUT_SLOTS;
	}

	@Override
	public boolean canPlaceItemThroughFace(int slot, ItemStack stack, Direction side) {
	    return slot < 5;
	}

	@Override
	public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction side) {
	    return slot == 6;
	}
	
	@Override
	public Component getDisplayName() {
		return Component.translatable("block.fur.soul_furnace");
	}
	
	protected final ContainerData dataAccess = new ContainerData() {
		public int get(int p_58431_) {
			switch (p_58431_) {
				case 0:
					return SoulFurnaceBlockEntity.this.progress;
				case 1:
					return SoulFurnaceBlockEntity.this.maxProgress;
				default:
					return 0;
			}
		}

		public void set(int p_58433_, int p_58434_) {
			switch (p_58433_) {
				case 0:
					SoulFurnaceBlockEntity.this.progress = p_58434_;
					break;
				case 1:
					SoulFurnaceBlockEntity.this.maxProgress = p_58434_;
					break;
			}

		}

		public int getCount() {
			return 4;
		}
	};
}
