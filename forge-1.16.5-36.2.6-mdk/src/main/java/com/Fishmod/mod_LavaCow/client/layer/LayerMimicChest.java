package com.Fishmod.mod_LavaCow.client.layer;

import com.Fishmod.mod_LavaCow.client.model.entity.MimicModel;
import com.Fishmod.mod_LavaCow.entities.tameable.MimicEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class LayerMimicChest<T extends MimicEntity> extends LayerRenderer<T, MimicModel<T>>{
    private static final ResourceLocation TEXTURE_ENDER = new ResourceLocation("textures/entity/chest/ender.png");
    private ResourceLocation textureLocation;

    public LayerMimicChest(IEntityRenderer<T, MimicModel<T>> p_i50931_1_) {
        super(p_i50931_1_);
    }
    
    @Override
    public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, T entitylivingbaseIn, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        if (entitylivingbaseIn.getSkin() == entitylivingbaseIn.getVoidSkin()) {
            this.textureLocation = TEXTURE_ENDER;
        } else {
            String chestTexture = entitylivingbaseIn.getChestTexture();

            // In the event that compatibility is no longer enabled (or the mod was removed), we reset the chest texture.
            if (!MimicEntity.TEXTURE_POOL.contains(chestTexture)) {
                chestTexture = "minecraft:textures/entity/chest/normal.png";
            }

            this.textureLocation = new ResourceLocation(chestTexture);
        }

        IVertexBuilder ivertexbuilder = p_225628_2_.getBuffer(RenderType.entityCutoutNoCull(this.textureLocation));
        this.getParentModel().renderToBuffer(p_225628_1_, ivertexbuilder, p_225628_3_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
	}
}
