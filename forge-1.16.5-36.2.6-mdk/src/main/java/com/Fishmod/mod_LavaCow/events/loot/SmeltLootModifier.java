package com.Fishmod.mod_LavaCow.events.loot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.Fishmod.mod_LavaCow.item.MoltenAxeItem;
import com.google.gson.JsonObject;

import net.minecraft.block.material.Material;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.items.ItemHandlerHelper;

public class SmeltLootModifier extends LootModifier {
    //private final Item addition;

    protected SmeltLootModifier(ILootCondition[] conditionsIn/*, Item addition*/) {
        super(conditionsIn);
        //this.addition = addition;
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        generatedLoot.forEach((stack) -> ret.add(smelt(stack, context)));
        return ret;
    }

    private static ItemStack smelt(ItemStack stack, LootContext context) {
    	if(stack.getItem() instanceof BlockItem) {
	    	Material material = ((BlockItem)stack.getItem()).getBlock().defaultBlockState().getMaterial();
	    	
	        return (MoltenAxeItem.DIGGABLE_MATERIALS.contains(material)) ? context.getLevel().getRecipeManager().getRecipeFor(IRecipeType.SMELTING, new Inventory(stack), context.getLevel())
	                .map(FurnaceRecipe::getResultItem)
	                .filter(itemStack -> !itemStack.isEmpty())
	                .map(itemStack -> ItemHandlerHelper.copyStackWithSize(itemStack, stack.getCount() * itemStack.getCount()))
	                .orElse(stack) : stack;
    	}
    	
    	return stack;
    }
    
    public static class Serializer extends GlobalLootModifierSerializer<SmeltLootModifier> {
        @Override
        public SmeltLootModifier read(ResourceLocation name, JsonObject object, ILootCondition[] conditionsIn) {
            /*Item addition = ForgeRegistries.ITEMS.getValue(
                    new ResourceLocation(JSONUtils.getAsString(object, "addition")));*/
            return new SmeltLootModifier(conditionsIn/*, addition*/);
        }
        
        @Override
        public JsonObject write(SmeltLootModifier instance) {
            JsonObject json = makeConditions(instance.conditions);
            //json.addProperty("addition", ForgeRegistries.ITEMS.getKey(instance.addition).toString());
            return json;
        }
    }
}
