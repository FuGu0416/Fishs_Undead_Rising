package com.Fishmod.fur.integration.jei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.block.blockentity.container.SoulFurnaceRecipe;
import com.Fishmod.fur.core.SpawnUtil;
import com.Fishmod.fur.init.FURBlockRegistry;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class SoulFurnaceJeiCategory implements IRecipeCategory<SoulFurnaceRecipe> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(mod_LavaCow.MODID, "textures/gui/soul_furnace.png");
    private final IDrawable bg;
    private final IDrawable icon;
    private final IDrawable timeIcon;
    private final IDrawable expIcon;
    private final IDrawableAnimated arrow;
	
    public SoulFurnaceJeiCategory(IGuiHelper gui) {
        bg = gui.createDrawable(TEXTURE, 29, 16, 116, 56);
        icon = gui.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(FURBlockRegistry.SOUL_FURNACE.get()));
		timeIcon = gui.createDrawable(TEXTURE, 176, 32, 8, 11);
		expIcon = gui.createDrawable(TEXTURE, 176, 43, 9, 9);
		arrow = gui.drawableBuilder(TEXTURE, 176, 15, 24, 17).buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public RecipeType<SoulFurnaceRecipe> getRecipeType() {
        return FURJeiTypes.SOUL_FURNACE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.fur.soul_furnace");
    }

    @Override
    public IDrawable getBackground() {
        return bg;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SoulFurnaceRecipe recipe, IFocusGroup focuses) {
		NonNullList<Ingredient> recipeIngredients = recipe.getIngredients();
		ItemStack resultStack = recipe.getResultItem(Minecraft.getInstance().level.registryAccess());
		ItemStack containerStack = recipe.getContainer().getItems()[0];

		int borderSlotSize = 18;
		for (int row = 0; row < 2; ++row) {
			for (int column = 0; column < 3; ++column) {
				int inputIndex = row * 3 + column;
				if (inputIndex < recipeIngredients.size()) {
					builder.addSlot(RecipeIngredientRole.INPUT, (column * borderSlotSize) + 1, (row * borderSlotSize) + 1)
							.addItemStacks(Arrays.asList(recipeIngredients.get(inputIndex).getItems()));
				}
			}
		}

		if (!containerStack.isEmpty()) {
			builder.addSlot(RecipeIngredientRole.CATALYST, 95, 39).addItemStack(containerStack);
		}

		builder.addSlot(RecipeIngredientRole.OUTPUT, 95, 10).addItemStack(resultStack);
    }
    
	@Override
	public void draw(SoulFurnaceRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		arrow.draw(guiGraphics, 60, 9);
		timeIcon.draw(guiGraphics, 64, 2);
		if (recipe.getExperience() > 0) {
			expIcon.draw(guiGraphics, 63, 21);
		}
	}
	
	@Override
	public List<Component> getTooltipStrings(SoulFurnaceRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
		if (SpawnUtil.isCursorInsideBounds(61, 2, 22, 28, mouseX, mouseY)) {
			List<Component> tooltipStrings = new ArrayList<>();

			int cookTime = recipe.getTime();
			if (cookTime > 0) {
				int cookTimeSeconds = cookTime / 20;
				tooltipStrings.add(Component.translatable("gui.jei.category.smelting.time.seconds", cookTimeSeconds));
			}
			float experience = recipe.getExperience();
			if (experience > 0) {
				tooltipStrings.add(Component.translatable("gui.jei.category.smelting.experience", experience));
			}

			return tooltipStrings;
		}
		return Collections.emptyList();
	}	
}
