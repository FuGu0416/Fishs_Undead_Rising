package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.client.model.entity.GraveRobberModel;
import com.Fishmod.mod_LavaCow.entities.GraveRobberEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.IllagerModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GraveRobberRenderer extends IllagerRenderer<GraveRobberEntity> {
	   private static final ResourceLocation GraveRobber = new ResourceLocation("mod_lavacow:textures/mobs/graverobber/graverobber.png");

	   public GraveRobberRenderer(EntityRendererManager p_i47189_1_) {
	      super(p_i47189_1_, new GraveRobberModel<>(0.0F, 0.0F, 128, 64), 0.5F);
	      this.addLayer(new HeldItemLayer<GraveRobberEntity, IllagerModel<GraveRobberEntity>>(this) {
	          public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, GraveRobberEntity p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
	             if (p_225628_4_.isAggressive() || !p_225628_4_.getOffhandItem().isEmpty()) {
	                super.render(p_225628_1_, p_225628_2_, p_225628_3_, p_225628_4_, p_225628_5_, p_225628_6_, p_225628_7_, p_225628_8_, p_225628_9_, p_225628_10_);
	             }

	          }
	       });
	      this.model.getHat().visible = true;
	   }

	   public ResourceLocation getTextureLocation(GraveRobberEntity p_110775_1_) {
	      return GraveRobber;
	   }
	}