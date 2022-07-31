package com.Fishmod.mod_LavaCow.client.renderer.entity;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.model.entity.WispModel;
import com.Fishmod.mod_LavaCow.entities.floating.WispEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WispRenderer extends MobRenderer<WispEntity, WispModel<WispEntity>> {
	
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/wisp/wisp.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/wisp/wisp1.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/wisp/wisp2.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/wisp/wisp3.png")
	};
	
	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getPath());
    }

    public WispRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new WispModel<>(), 0.0F);
    }
    
    @Override
    public ResourceLocation getTextureLocation(WispEntity entity) {
    	return TEXTURES[entity.getSkin()];
    }
    
    @Nullable
    @Override
    protected RenderType getRenderType(WispEntity p_230496_1_, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
    	return RenderType.entityTranslucent(this.getTextureLocation(p_230496_1_));
    }
    
    protected void scale(WispEntity p_225620_1_, MatrixStack p_225620_2_, float p_225620_3_) {
        float f = p_225620_1_.getSwelling(p_225620_3_) * 2.0F;
        float f1 = 1.0F + MathHelper.sin(f * 100.0F) * f * 0.01F;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        f = f * f;
        f = f * f;
        float f2 = (1.0F + f * 0.4F) * f1;
        float f3 = (1.0F + f * 0.1F) / f1;
        p_225620_2_.scale(f2, f3, f2);
	}

	protected float getWhiteOverlayProgress(WispEntity p_225625_1_, float p_225625_2_) {
		float f = p_225625_1_.getSwelling(p_225625_2_);
        return (int)(f * 10.0F) % 2 == 0 ? 0.0F : MathHelper.clamp(f, 0.5F, 1.0F);
	}
    
    protected int getBlockLightLevel(WispEntity p_225624_1_, BlockPos p_225624_2_) {
        return 15;
    }
}
