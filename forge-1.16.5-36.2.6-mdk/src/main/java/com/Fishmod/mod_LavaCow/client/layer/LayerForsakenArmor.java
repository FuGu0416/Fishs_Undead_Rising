package com.Fishmod.mod_LavaCow.client.layer;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.SkeletonModel;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LayerForsakenArmor<T extends MobEntity & IRangedAttackMob, M extends EntityModel<T>> extends LayerRenderer<T, M> {
    private static final ResourceLocation ARMOR_TEXTURES = new ResourceLocation("mod_lavacow:textures/mobs/forsaken/forsaken_overlay.png");
    private final SkeletonModel<T> layerModel = new SkeletonModel<>(0.25F, true);

    public LayerForsakenArmor(IEntityRenderer<T, M> p_i50919_1_) {
       super(p_i50919_1_);
    }

    public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, T p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
       coloredCutoutModelCopyLayerRender(this.getParentModel(), this.layerModel, ARMOR_TEXTURES, p_225628_1_, p_225628_2_, p_225628_3_, p_225628_4_, p_225628_5_, p_225628_6_, p_225628_8_, p_225628_9_, p_225628_10_, p_225628_7_, 1.0F, 1.0F, 1.0F);
    }
}
