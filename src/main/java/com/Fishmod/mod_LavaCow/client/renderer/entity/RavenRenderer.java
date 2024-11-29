package com.Fishmod.mod_LavaCow.client.renderer.entity;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.layer.LayerRaven;
import com.Fishmod.mod_LavaCow.client.model.entity.RavenModel;
import com.Fishmod.mod_LavaCow.entities.tameable.RavenEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class RavenRenderer extends MobRenderer<RavenEntity, RavenModel<RavenEntity>> {	
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/raven/raven.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/raven/raven1.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/raven/raven2.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/raven/raven3.png")
	};
	
	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getPath());
    }

    public RavenRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new RavenModel<>(), 0.3F);
        this.addLayer(new LayerRaven<>(this));
    }
    
    @Override
    public ResourceLocation getTextureLocation(RavenEntity entity) {
        return TEXTURES[entity.getSkin()];
    }

    @Override
	protected void scale(RavenEntity entity, MatrixStack matrixStackIn, float partialTickTime) {
    	if (entity.getSkin() == 1) {
    		matrixStackIn.scale(1.2F, 1.2F, 1.2F);
    	}
	}
    
    public float getBob(RavenEntity p_77044_1_, float p_77044_2_) {
        float f = MathHelper.lerp(p_77044_2_, p_77044_1_.oFlap, p_77044_1_.flap);
        float f1 = MathHelper.lerp(p_77044_2_, p_77044_1_.oFlapSpeed, p_77044_1_.flapSpeed);
        return (MathHelper.sin(f) + 1.0F) * f1;
	}
    
    protected int getBlockLightLevel(RavenEntity p_225624_1_, BlockPos p_225624_2_) {
        return p_225624_1_.getSkin() == 3 ? 15 : super.getBlockLightLevel(p_225624_1_, p_225624_2_);
    }
    
    @Nullable
    @Override
    protected RenderType getRenderType(RavenEntity p_230496_1_, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
    	return RenderType.entityTranslucent(this.getTextureLocation(p_230496_1_));
    }
}
