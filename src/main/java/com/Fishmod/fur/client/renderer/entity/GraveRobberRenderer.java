package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.client.layer.FURModelLayers;
import com.Fishmod.fur.client.model.GraveRobberModel;
import com.Fishmod.fur.entities.GraveRobberEntity;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GraveRobberRenderer extends IllagerRenderer<GraveRobberEntity> {
	private static final ResourceLocation GRAVEROBBER = new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/graverobber/graverobber.png");

	public GraveRobberRenderer(EntityRendererProvider.Context p_i47189_1_) {
		super(p_i47189_1_, new GraveRobberModel<>(p_i47189_1_.bakeLayer(FURModelLayers.GRAVEROBBER)), 0.5F);
		this.addLayer(new ItemInHandLayer<GraveRobberEntity, IllagerModel<GraveRobberEntity>>(this, p_i47189_1_.getItemInHandRenderer()) {
			public void render(PoseStack p_225628_1_, MultiBufferSource p_225628_2_, int p_225628_3_, GraveRobberEntity p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
				if (p_225628_4_.isAggressive() || !p_225628_4_.getOffhandItem().isEmpty()) {
					super.render(p_225628_1_, p_225628_2_, p_225628_3_, p_225628_4_, p_225628_5_, p_225628_6_, p_225628_7_, p_225628_8_, p_225628_9_, p_225628_10_);
				}
			}
		});
	}

	@Override
	public ResourceLocation getTextureLocation(GraveRobberEntity p_110775_1_) {
		return GRAVEROBBER;
	}
}
