package com.Fishmod.fur.client.renderer;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class FURItemRenderProperties implements IClientItemExtensions {
    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return new FURItemstackRenderer();
    }
}
