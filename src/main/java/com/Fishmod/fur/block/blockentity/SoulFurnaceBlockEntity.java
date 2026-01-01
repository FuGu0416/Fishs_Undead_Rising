package com.Fishmod.fur.block.blockentity;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import com.Fishmod.fur.block.blockentity.container.SoulFurnaceMenu;
import com.Fishmod.fur.block.blockentity.container.SoulFurnaceRecipe;
import com.Fishmod.fur.init.FURBlockEntityRegistry;
import com.google.common.collect.Lists;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

public class SoulFurnaceBlockEntity extends BlockEntity implements MenuProvider, WorldlyContainer {
    public static final int CONTAINER_SLOT = 7;
    public static final int RESULT_SLOT = 8;
    
    private NonNullList<ItemStack> items = NonNullList.withSize(8, ItemStack.EMPTY);
    private int progress;
    private int maxProgress = 200;
    protected final ContainerData cookingPotData;
    private static final int[] INPUT_SLOTS = {0,1,2,3,4,5};
    private static final int[] CONTAINER_SLOTS = {6};
    private static final int[] OUTPUT_SLOTS = {7};
    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();
    
    public SoulFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(FURBlockEntityRegistry.SOUL_FURNACE.get(), pos, state);
        this.cookingPotData = this.dataAccess();
    }
    
    public static void tick(Level level, BlockPos pos, BlockState state, SoulFurnaceBlockEntity be) {
        if (!level.isClientSide) {
            if (be.canCook()) {
            	if (!state.getValue(BlockStateProperties.LIT)) {
            		level.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.LIT, Boolean.valueOf(true)));
            	}
            	
                be.progress++;
                if (be.progress >= be.maxProgress) {
                	level.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.LIT, Boolean.valueOf(false)));
                    be.craft();
                }
            } else {
            	level.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.LIT, Boolean.valueOf(false)));
                be.progress = 0;
            }
        }        
    }
    
    private Optional<SoulFurnaceRecipe> getCurrentRecipe() {
        SimpleContainer inv = new SimpleContainer(6);
        for (int i = 0; i < 6; i++) {
            inv.setItem(i, this.items.get(i));
        }

        return this.level.getRecipeManager()
            .getRecipeFor(SoulFurnaceRecipe.TYPE, inv, this.level);
    }

    public boolean canCook() {
        Optional<SoulFurnaceRecipe> recipe = getCurrentRecipe();
        
        if (recipe.isEmpty()) return false;
        if (!recipe.get().getContainer().test(this.items.get(6))) return false;
        
        ItemStack result = recipe.get().getResultItem(this.level.registryAccess());
        ItemStack output = this.items.get(7);
        
        if (output.isEmpty()) return true;
        if (!ItemStack.isSameItemSameTags(output, result)) return false;        
        
        return output.getCount() + result.getCount() <= output.getMaxStackSize();
    }
    
    public void craft() {
        Optional<SoulFurnaceRecipe> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return;

        ItemStack result = recipe.get().getResultItem(this.level.registryAccess()).copy();
        
        for (int i = 0; i < 6; i++) {
        	this.items.get(i).shrink(1);
        }

        if (this.items.get(7).isEmpty()) {
        	this.items.set(7, result);
        } else {
        	this.items.get(7).grow(result.getCount());
        }
        
        this.items.get(6).shrink(1);

        this.progress = 0;
        setChanged();
    }
    
    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compound, this.items);
        this.progress = compound.getInt("Progress");
        this.maxProgress = compound.getInt("MaxProgress");
        CompoundTag compoundtag = compound.getCompound("RecipesUsed");

        for(String s : compoundtag.getAllKeys()) {
           this.recipesUsed.put(new ResourceLocation(s), compoundtag.getInt(s));
        }
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.putInt("Progress", this.progress);
        compound.putInt("MaxProgress", this.maxProgress);
        ContainerHelper.saveAllItems(compound, this.items);
        CompoundTag compoundtag = new CompoundTag();
        this.recipesUsed.forEach((p_187449_, p_187450_) -> {
           compoundtag.putInt(p_187449_.toString(), p_187450_);
        });
        compound.put("RecipesUsed", compoundtag);
    }

	@Override
	public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
	    return new SoulFurnaceMenu(id, inv, this, this.cookingPotData);
	}
	
	public ItemStack getContainer() {
		return this.items.get(7);
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
	
	public void setRecipeUsed(@Nullable Recipe<?> p_58345_) {
		if (p_58345_ != null) {
			ResourceLocation resourcelocation = p_58345_.getId();
			this.recipesUsed.addTo(resourcelocation, 1);
		}
	}
	
	@Nullable
	public Recipe<?> getRecipeUsed() {
		return null;
	}
	
	public void awardUsedRecipesAndPopExperience(ServerPlayer p_155004_) {
		List<Recipe<?>> list = this.getRecipesToAwardAndPopExperience(p_155004_.serverLevel(), p_155004_.position());
		p_155004_.awardRecipes(list);

		for(Recipe<?> recipe : list) {
			if (recipe != null) {
				p_155004_.triggerRecipeCrafted(recipe, this.items);
			}
		}

		this.recipesUsed.clear();
	}

	public List<Recipe<?>> getRecipesToAwardAndPopExperience(ServerLevel p_154996_, Vec3 p_154997_) {
		List<Recipe<?>> list = Lists.newArrayList();

		for(Object2IntMap.Entry<ResourceLocation> entry : this.recipesUsed.object2IntEntrySet()) {
			p_154996_.getRecipeManager().byKey(entry.getKey()).ifPresent((p_155023_) -> {
				list.add(p_155023_);
				createExperience(p_154996_, p_154997_, entry.getIntValue(), ((AbstractCookingRecipe)p_155023_).getExperience());
			});
		}

		return list;
	}

	private static void createExperience(ServerLevel p_154999_, Vec3 p_155000_, int p_155001_, float p_155002_) {
		int i = Mth.floor((float)p_155001_ * p_155002_);
		float f = Mth.frac((float)p_155001_ * p_155002_);
		if (f != 0.0F && Math.random() < (double)f) {
			++i;
		}

		ExperienceOrb.award(p_154999_, p_155000_, i);
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
	    return (side == Direction.DOWN) ? OUTPUT_SLOTS : ((side == Direction.UP) ? INPUT_SLOTS : CONTAINER_SLOTS);
	}

	@Override
	public boolean canPlaceItemThroughFace(int slot, ItemStack stack, Direction side) {
	    return slot < 7;
	}

	@Override
	public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction side) {
	    return slot == 7;
	}
	
    public NonNullList<ItemStack> getDroppableInventory() {
        return this.items;
    }
	
	@Override
	public Component getDisplayName() {
		return Component.translatable("block.fur.soul_furnace");
	}
	
	protected final ContainerData dataAccess() {
		return new ContainerData() {
			public int get(int index) {
				return switch (index) {
					case 0 -> SoulFurnaceBlockEntity.this.progress;
					case 1 -> SoulFurnaceBlockEntity.this.maxProgress;
					default -> 0;
				};
			}
	
			public void set(int index, int value) {
				switch (index) {
					case 0 -> SoulFurnaceBlockEntity.this.progress = value;
					case 1 -> SoulFurnaceBlockEntity.this.maxProgress = value;
				}
			}
	
			public int getCount() {
				return 2;
			}
		};
	}
}
