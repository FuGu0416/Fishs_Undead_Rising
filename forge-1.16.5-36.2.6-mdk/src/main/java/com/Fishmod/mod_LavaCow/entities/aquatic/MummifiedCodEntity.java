package com.Fishmod.mod_LavaCow.entities.aquatic;

import java.util.Random;

import com.Fishmod.mod_LavaCow.init.FURItemRegistry;

import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.passive.fish.AbstractGroupFishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class MummifiedCodEntity extends AbstractGroupFishEntity {
	public MummifiedCodEntity(EntityType<? extends MummifiedCodEntity> p_i50279_1_, World p_i50279_2_) {
		super(p_i50279_1_, p_i50279_2_);
	}
	
	public static boolean checkFishSpawnRules(EntityType<? extends AbstractFishEntity> p_223363_0_, IWorld p_223363_1_, SpawnReason p_223363_2_, BlockPos p_223363_3_, Random p_223363_4_) {
		return p_223363_1_.getBlockState(p_223363_3_).is(Blocks.WATER);
	}
	
    protected void handleAirSupply(int p_209207_1_) {    	
    }

	protected ItemStack getBucketItemStack() {
		return new ItemStack(FURItemRegistry.MUMMIFIED_COD_BUCKET);
	}

	protected SoundEvent getAmbientSound() {
		return SoundEvents.COD_AMBIENT;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.COD_DEATH;
	}

	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		return SoundEvents.COD_HURT;
	}

	protected SoundEvent getFlopSound() {
		return SoundEvents.COD_FLOP;
	}
	
    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.UNDEAD;
    }
}