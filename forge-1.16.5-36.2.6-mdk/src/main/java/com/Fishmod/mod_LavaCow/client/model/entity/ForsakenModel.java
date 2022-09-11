package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.ForsakenEntity;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.SkeletonModel;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;

public class ForsakenModel<T extends ForsakenEntity> extends SkeletonModel<T> {
	public ForsakenModel() {
		this(0.0F, false);
	}

	public ForsakenModel(float p_i46303_1_, boolean p_i46303_2_) {
		super(p_i46303_1_, p_i46303_2_);
	}

	@Override
	public void prepareMobModel(T p_212843_1_, float p_212843_2_, float p_212843_3_, float p_212843_4_) {	
		this.rightArmPose = BipedModel.ArmPose.EMPTY;
		this.leftArmPose = BipedModel.ArmPose.EMPTY;
		ItemStack itemstack_main = p_212843_1_.getItemInHand(Hand.MAIN_HAND);
		ItemStack itemstack_off = p_212843_1_.getItemInHand(Hand.OFF_HAND);
		
		if (itemstack_main.getItem() == FURItemRegistry.FORSAKEN_STAFF && p_212843_1_.isAggressive()) {
			this.rightArmPose = BipedModel.ArmPose.CROSSBOW_HOLD;			
		} else if (itemstack_off.getItem() == Items.SHIELD && p_212843_1_.isAggressive()) {
			if (p_212843_1_.getMainArm() == HandSide.RIGHT) {
				this.leftArmPose = BipedModel.ArmPose.BLOCK;
			} else {
				this.rightArmPose = BipedModel.ArmPose.BLOCK;
			}
		} else {
			super.prepareMobModel(p_212843_1_, p_212843_2_, p_212843_3_, p_212843_4_);
		}
	}
}
