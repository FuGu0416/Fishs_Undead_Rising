package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.floating.WispEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
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
    public ModelRenderer Jaw;

    public WispModel() {
        this.texWidth = 64;
        this.texHeight = 32;
        this.Core = new ModelRenderer(this, 32, 0);
        this.Core.setPos(0.0F, 0.0F, 0.0F);
        this.Core.addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setPos(0.0F, 22.0F, 0.0F);
        this.Head.addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.Jaw = new ModelRenderer(this, 0, 16);
        this.Jaw.setPos(0.0F, 0.0F, 0.0F);
        this.Jaw.addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.Head.addChild(this.Core);
        this.Head.addChild(this.Jaw);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.Head).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }
    
    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) { 
    	if(entityIn.isAggressive()) {
    		this.Jaw.xRot = 0.3515093006990136F;
    		this.Jaw.y = 2.0F;
    	} else {
    		this.Jaw.xRot = 0.0F;
    		this.Jaw.y = MathHelper.cos(ageInTicks * 0.18F) * 0.6F;
    	}
    	
    	if(entityIn.getSwellDir() > 0) {
    		this.Head.yRot += 0.25F;
    	} else {
    		this.Head.yRot = 0.0F;
    	}
    }
}
