package com.Fishmod.fur.block.blockentity.container;

import com.Fishmod.fur.init.FURRecipeRegistry;
import com.google.gson.JsonObject;

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
	public static final RecipeType<SoulFurnaceRecipe> TYPE = new RecipeType<>(){};
	
    private final ResourceLocation id;
    private final Ingredient[] ingredients;
    private final Ingredient container;
    private final ItemStack result;
    private final int time;

    public SoulFurnaceRecipe(ResourceLocation id, Ingredient[] ingredients, Ingredient container,ItemStack result, int time) {
    	this.id = id;
    	this.ingredients = ingredients;
    	this.container = container;
    	this.result = result;
    	this.time = time;
    }
    
    @Override
    public boolean matches(Container inv, Level level) {
        for (int i = 0; i < 5; i++) {
            if (!ingredients[i].test(inv.getItem(i))) {
                return false;
            }
        }
        return true;
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
		return TYPE;
	}

	public Ingredient getContainer() {
		return container;
	}
	
    public int getTime() {
        return time;
    }
    
	public static class Serializer implements RecipeSerializer<SoulFurnaceRecipe> {
		public Serializer() {
		}
		
	    @Override
	    public SoulFurnaceRecipe fromJson(ResourceLocation id, JsonObject json) {
	        var ingredients = GsonHelper.getAsJsonArray(json, "ingredients");

	        Ingredient[] input = new Ingredient[5];
	        for (int i = 0; i < 5; i++) {
	            input[i] = i < ingredients.size()
	                ? Ingredient.fromJson(ingredients.get(i))
	                : Ingredient.EMPTY;
	        }

	        Ingredient container = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "container"));

	        ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
	        int time = GsonHelper.getAsInt(json, "time", 200);

	        return new SoulFurnaceRecipe(id, input, container, result, time);
	    }

	    @Override
	    public SoulFurnaceRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
	        Ingredient[] input = new Ingredient[5];
	        for (int i = 0; i < 5; i++) {
	            input[i] = Ingredient.fromNetwork(buf);
	        }

	        Ingredient container = Ingredient.fromNetwork(buf);
	        ItemStack result = buf.readItem();
	        int time = buf.readVarInt();

	        return new SoulFurnaceRecipe(id, input, container, result, time);
	    }

	    @Override
	    public void toNetwork(FriendlyByteBuf buf, SoulFurnaceRecipe recipe) {
	        for (Ingredient i : recipe.getIngredients()) {
	            i.toNetwork(buf);
	        }

	        recipe.getContainer().toNetwork(buf);
	        buf.writeItem(recipe.result);
	        buf.writeVarInt(recipe.getTime());
	    }
	}
}
