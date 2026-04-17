package com.Fishmod.fur.block.blockentity.container;

import com.Fishmod.fur.init.FURRecipeRegistry;
import com.Fishmod.fur.init.FURRecipeTypeRegistry;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;

public class SoulFurnaceRecipe implements Recipe<Container> {
	public static final int INPUT_SLOTS = 6;
	
    private final ResourceLocation id;
    private final NonNullList<Ingredient> ingredients;
    private final Ingredient container;
    private final ItemStack result;
    private final int time;
    private final float exp;
    
    public SoulFurnaceRecipe(ResourceLocation id, NonNullList<Ingredient> ingredients, Ingredient container, ItemStack result, float experience, int time) {
    	this.id = id;
    	this.ingredients = ingredients;
    	this.container = container;
    	this.result = result;
    	this.time = time;
    	this.exp = experience;
    }
    
    @Override
    public boolean matches(Container inv, Level level) {
		java.util.List<ItemStack> inputs = new java.util.ArrayList<>();
		int i = 0;

		for (int j = 0; j < INPUT_SLOTS; ++j) {
			ItemStack itemstack = inv.getItem(j);
			if (!itemstack.isEmpty()) {
				++i;
				inputs.add(itemstack);
			}
		}		
		
		return i == this.ingredients.size() && net.minecraftforge.common.util.RecipeMatcher.findMatches(inputs, this.ingredients) != null;
    }

    @Override
    public ItemStack assemble(Container inv, RegistryAccess access) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int w, int h) {
        return true;
    }
    
    @Override
	public NonNullList<Ingredient> getIngredients() {
        return this.ingredients;
	}

    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        return result;
    }

	@Override
    public ResourceLocation getId() {
        return id;
    }

	@Override
	public RecipeSerializer<?> getSerializer() {
		return FURRecipeRegistry.SOUL_FURNACE.get();
	}

	@Override
	public RecipeType<?> getType() {
		return FURRecipeTypeRegistry.SOUL_FURNACE.get();
	}

	public Ingredient getContainer() {
		return this.container;
	}
	
    public int getTime() {
        return this.time;
    }
    
	public float getExperience() {
		return this.exp;
	}
    
	public static class Serializer implements RecipeSerializer<SoulFurnaceRecipe> {
		public Serializer() {
		}
		
	    @Override
	    public SoulFurnaceRecipe fromJson(ResourceLocation id, JsonObject json) {
	        NonNullList<Ingredient> input = readIngredients(GsonHelper.getAsJsonArray(json, "ingredients"));
	        Ingredient container = Ingredient.EMPTY;
	        
	        if (json.has("container")) {
	        	container = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "container"));
	        }
	        
	        ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
	        float experience  = GsonHelper.getAsFloat(json, "experience", 0.0F);
	        int time = GsonHelper.getAsInt(json, "time", 200);

	        return new SoulFurnaceRecipe(id, input, container, result, experience, time);
	    }
	    
		private static NonNullList<Ingredient> readIngredients(JsonArray ingredientArray) {
			NonNullList<Ingredient> nonnulllist = NonNullList.create();

			for (int i = 0; i < ingredientArray.size(); ++i) {
				Ingredient ingredient = Ingredient.fromJson(ingredientArray.get(i));
				if (!ingredient.isEmpty()) {
					nonnulllist.add(ingredient);
				}
			}

			return nonnulllist;
		}

	    @Override
	    public SoulFurnaceRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
	    	NonNullList<Ingredient> input = NonNullList.withSize(buf.readVarInt(), Ingredient.EMPTY);
	    	
	        for (int i = 0; i < input.size(); i++) {
	            input.set(i, Ingredient.fromNetwork(buf));
	        }

	        Ingredient container = Ingredient.EMPTY;
	        if (buf.readBoolean()) {
	            container = Ingredient.fromNetwork(buf);
	        }
	        
	        ItemStack result = buf.readItem();
	        float experience = buf.readFloat();
	        int time = buf.readVarInt();

	        return new SoulFurnaceRecipe(id, input, container, result, experience, time);
	    }

	    @Override
	    public void toNetwork(FriendlyByteBuf buf, SoulFurnaceRecipe recipe) {
	        for (Ingredient i : recipe.getIngredients()) {
	            i.toNetwork(buf);
	        }

	        buf.writeBoolean(!recipe.getContainer().isEmpty());
	        if (!recipe.getContainer().isEmpty()) {
	            recipe.getContainer().toNetwork(buf);
	        }
	        
	        buf.writeItem(recipe.result);
	        buf.writeFloat(recipe.getExperience());
	        buf.writeVarInt(recipe.getTime());
	    }
	}
}
