package com.Fishmod.fur.data.providers;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.events.loot.SmeltLootModifier;
import com.Fishmod.fur.init.FURItemRegistry;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class FURGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public FURGlobalLootModifiersProvider(PackOutput output) {
        super(output, mod_LavaCow.MODID);
    }

    @Override
    protected void start() {
        add("molten_axe_smelt", new SmeltLootModifier(new LootItemCondition[] {
        		MatchTool.toolMatches(ItemPredicate.Builder.item().of(FURItemRegistry.MOLTEN_AXE.get())).build()
                }));
        add("soulforged_axe_smelt", new SmeltLootModifier(new LootItemCondition[] {
        		MatchTool.toolMatches(ItemPredicate.Builder.item().of(FURItemRegistry.SOULFORGED_AXE.get())).build()
                }));
    }
}