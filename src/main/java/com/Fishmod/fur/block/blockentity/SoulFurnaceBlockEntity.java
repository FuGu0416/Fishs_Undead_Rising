package com.Fishmod.fur.block.blockentity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Nullable;

import com.Fishmod.fur.block.SoulFurnaceBlock;
import com.Fishmod.fur.block.blockentity.container.SoulFurnaceMenu;
import com.Fishmod.fur.block.blockentity.container.SoulFurnaceRecipe;
import com.Fishmod.fur.init.FURBlockEntityRegistry;
import com.Fishmod.fur.init.FURRecipeTypeRegistry;
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
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import static java.util.Map.entry;

public class SoulFurnaceBlockEntity extends BlockEntity implements MenuProvider, WorldlyContainer {
	public static final int INGREDIENT_SLOT = 5;
    public static final int CONTAINER_SLOT = 6;
    public static final int RESULT_SLOT = 7;
    
	public static final Map<Item, Item> INGREDIENT_REMAINDER_OVERRIDES = Map.ofEntries(
			entry(Items.POWDER_SNOW_BUCKET, Items.BUCKET),
			entry(Items.AXOLOTL_BUCKET, Items.BUCKET),
			entry(Items.COD_BUCKET, Items.BUCKET),
			entry(Items.PUFFERFISH_BUCKET, Items.BUCKET),
			entry(Items.SALMON_BUCKET, Items.BUCKET),
			entry(Items.TROPICAL_FISH_BUCKET, Items.BUCKET),
			entry(Items.SUSPICIOUS_STEW, Items.BOWL),
			entry(Items.MUSHROOM_STEW, Items.BOWL),
			entry(Items.RABBIT_STEW, Items.BOWL),
			entry(Items.BEETROOT_SOUP, Items.BOWL),
			entry(Items.POTION, Items.GLASS_BOTTLE),
			entry(Items.SPLASH_POTION, Items.GLASS_BOTTLE),
			entry(Items.LINGERING_POTION, Items.GLASS_BOTTLE),
			entry(Items.EXPERIENCE_BOTTLE, Items.GLASS_BOTTLE)
	);
	
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
        SimpleContainer inv = new SimpleContainer(CONTAINER_SLOT);
        for (int i = 0; i < CONTAINER_SLOT; i++) {
            inv.setItem(i, this.items.get(i));
        }

        return this.level.getRecipeManager()
            .getRecipeFor(FURRecipeTypeRegistry.SOUL_FURNACE.get(), inv, this.level);
    }

    public boolean canCook() {
        Optional<SoulFurnaceRecipe> recipe = getCurrentRecipe();
        
        if (recipe.isEmpty()) return false;
        if (!recipe.get().getContainer().test(this.items.get(CONTAINER_SLOT))) return false;
        
        ItemStack result = recipe.get().getResultItem(this.level.registryAccess());
        ItemStack output = this.items.get(RESULT_SLOT);
        
        if (output.isEmpty()) return true;
        if (!ItemStack.isSameItemSameTags(output, result)) return false;        
        
        return output.getCount() + result.getCount() <= output.getMaxStackSize();
    }
    
    public void craft() {
        Optional<SoulFurnaceRecipe> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return;

        ItemStack result = recipe.get().getResultItem(this.level.registryAccess()).copy();        

        if (this.items.get(RESULT_SLOT).isEmpty()) {
        	this.items.set(RESULT_SLOT, result);
        } else {
        	this.items.get(RESULT_SLOT).grow(result.getCount());
        }
        
        this.items.get(CONTAINER_SLOT).shrink(1);

        this.progress = 0;
        
		for (int i = 0; i < CONTAINER_SLOT; ++i) {
			ItemStack slotStack = this.items.get(i);
			if (slotStack.hasCraftingRemainingItem()) {
				this.ejectIngredientRemainder(slotStack.getCraftingRemainingItem());
			} else if (INGREDIENT_REMAINDER_OVERRIDES.containsKey(slotStack.getItem())) {
				this.ejectIngredientRemainder(INGREDIENT_REMAINDER_OVERRIDES.get(slotStack.getItem()).getDefaultInstance());
			}
			if (!slotStack.isEmpty())
				slotStack.shrink(1);
		}
		
        setChanged();
    }
    
	protected void ejectIngredientRemainder(ItemStack remainderStack) {
		Direction direction = getBlockState().getValue(SoulFurnaceBlock.FACING).getCounterClockWise();
		double x = this.worldPosition.getX() + 0.5 + (direction.getStepX() * 0.25);
		double y = this.worldPosition.getY() + 0.7;
		double z = this.worldPosition.getZ() + 0.5 + (direction.getStepZ() * 0.25);
		
		ItemEntity entity = new ItemEntity(this.level, x, y, z, remainderStack);
		entity.setDeltaMovement(direction.getStepX() * 0.08F, 0.25F, direction.getStepZ() * 0.08F);
		level.addFreshEntity(entity);
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
        this.recipesUsed.forEach((recipeId, count) -> {
           compoundtag.putInt(recipeId.toString(), count);
        });
        compound.put("RecipesUsed", compoundtag);
    }

	@Override
	public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
	    return new SoulFurnaceMenu(id, inv, this, this.cookingPotData);
	}
	
	public ItemStack getContainer() {
		return this.items.get(CONTAINER_SLOT);
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
	
	public void setRecipeUsed(@Nullable Recipe<?> recipe) {
		if (recipe != null) {
			ResourceLocation resourcelocation = recipe.getId();
			this.recipesUsed.addTo(resourcelocation, 1);
		}
	}
	
	@Nullable
	public Recipe<?> getRecipeUsed() {
		return null;
	}
	
	public void awardUsedRecipesAndPopExperience(ServerPlayer player) {
		List<Recipe<?>> list = this.getRecipesToAwardAndPopExperience(player.serverLevel(), player.position());
		player.awardRecipes(list);

		for(Recipe<?> recipe : list) {
			if (recipe != null) {
				player.triggerRecipeCrafted(recipe, this.items);
			}
		}

		this.recipesUsed.clear();
	}

	public List<Recipe<?>> getRecipesToAwardAndPopExperience(ServerLevel level, Vec3 vec3) {
		List<Recipe<?>> list = Lists.newArrayList();

		for(Object2IntMap.Entry<ResourceLocation> entry : this.recipesUsed.object2IntEntrySet()) {
			level.getRecipeManager().byKey(entry.getKey()).ifPresent((recipe) -> {
				list.add(recipe);
				createExperience(level, vec3, entry.getIntValue(), ((AbstractCookingRecipe)recipe).getExperience());
			});
		}

		return list;
	}

	private static void createExperience(ServerLevel level, Vec3 vec3, int count, float experience) {
		int i = Mth.floor((float)count * experience);
		float f = Mth.frac((float)count * experience);
		if (f != 0.0F && Math.random() < (double)f) {
			++i;
		}

		ExperienceOrb.award(level, vec3, i);
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
	    return (side == Direction.DOWN) ? OUTPUT_SLOTS : ((side == Direction.UP) ? INPUT_SLOTS : CONTAINER_SLOTS);
	}

	@Override
	public boolean canPlaceItemThroughFace(int slot, ItemStack stack, Direction side) {
	    return slot < RESULT_SLOT;
	}

	@Override
	public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction side) {
	    return slot == RESULT_SLOT;
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
