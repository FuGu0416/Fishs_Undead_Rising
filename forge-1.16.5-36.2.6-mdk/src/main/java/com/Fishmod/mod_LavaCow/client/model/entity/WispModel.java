package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.floating.WispEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * WispModel - PufferFish0_0
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class WispModel<T extends WispEntity> extends FURBaseModel<T> {
    public ModelRenderer Head;
    public ModelRenderer Core;
    //public ModelRenderer Tail;

    public WispModel() {
        this.texWidth = 64;
        this.texHeight = 32;
        this.Core = new ModelRenderer(this, 32, 0);
        this.Core.setPos(0.0F, 0.0F, 0.0F);
        this.Core.addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setPos(0.0F, 22.0F, 0.0F);
        this.Head.addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        /*this.Tail = new ModelRenderer(this, 32, 12);
        this.Tail.setPos(0.0F, 0.0F, 1.0F);
        this.Tail.addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 6.0F, 0.1F, 0.1F, 0.0F);*/
        this.Head.addChild(this.Core);
        //this.Core.addChild(this.Tail);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.Head).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }
}
