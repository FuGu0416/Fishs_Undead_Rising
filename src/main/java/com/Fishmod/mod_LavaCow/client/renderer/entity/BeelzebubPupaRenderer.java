package com.Fishmod.mod_LavaCow.client.renderer.entity;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.model.entity.BeelzebubPupaModel;
import com.Fishmod.mod_LavaCow.entities.VespaCocoonEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BeelzebubPupaRenderer extends MobRenderer<VespaCocoonEntity, BeelzebubPupaModel<VespaCocoonEntity>>  {
	private static final ResourceLocation TEXTURES = new ResourceLocation("mod_lavacow:textures/mobs/beelzebub/beelzebub_pupa.png");
	static{
        System.out.println(TEXTURES.getPath());
    }

    public BeelzebubPupaRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new BeelzebubPupaModel<VespaCocoonEntity>(), 0.2F);
    }
    
    @Override
    public ResourceLocation getTextureLocation(VespaCocoonEntity entity) {
        return TEXTURES;
    }
    
    @Override
	protected void scale(VespaCocoonEntity entity, MatrixStack matrixStackIn, float partialTickTime) {
    	float f = (0.85f + 0.15f * MathHelper.sin(entity.tickCount * 0.25F));
    	
    	if(!entity.isBaby())
    		matrixStackIn.scale(1.0F, f, 1.0F);
	}
    
    @Nullable
    @Override
    protected RenderType getRenderType(VespaCocoonEntity p_230496_1_, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
    	return RenderType.entityTranslucent(this.getTextureLocation(p_230496_1_));
    }
}
