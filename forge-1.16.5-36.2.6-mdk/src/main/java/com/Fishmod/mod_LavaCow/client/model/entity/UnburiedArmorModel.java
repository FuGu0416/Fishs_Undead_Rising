package com.Fishmod.mod_LavaCow.client.model.entity;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class UnburiedArmorModel<T extends LivingEntity> extends BipedModel<T> {

	public UnburiedArmorModel(float scale) {
		super(scale);
		this.head = new ModelRenderer(this, 0, 0);
		this.head.addBox(-4.0F, -3.0F, -8.0F, 8.0F, 8.0F, 8.0F, scale);
		this.head.setPos(0.0F, 0.0F, 0.0F);
		this.hat = new ModelRenderer(this, 32, 0);
		this.hat.addBox(-4.0F, -3.0F, -8.0F, 8.0F, 8.0F, 8.0F, scale + 0.5F);
		this.hat.setPos(0.0F, 0.0F, 0.0F);
	}

}
