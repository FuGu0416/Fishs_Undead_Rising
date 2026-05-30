package com.Fishmod.fur.item;

import java.util.List;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.Fishmod.fur.client.renderer.FURItemRenderProperties;
import com.Fishmod.fur.init.FURItemRegistry;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class VespaShieldItem extends ShieldItem {

    public VespaShieldItem(Item.Properties properties) {
        super(properties);
    }

    /**
     * Registers the custom BEWR renderer so the shield displays as a 3-D model.
     * This is the 1.20.1 replacement for the old setupISTER proxy pattern.
     */
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new FURItemRenderProperties());
    }

    @Override
    public boolean isValidRepairItem(ItemStack shield, ItemStack material) {
        return material.getItem() == FURItemRegistry.VESPA_CARAPACE.get() || super.isValidRepairItem(shield, material);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable(this.getDescriptionId() + ".desc").withStyle(ChatFormatting.YELLOW));
    }
}
