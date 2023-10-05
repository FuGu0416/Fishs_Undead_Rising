package com.Fishmod.mod_LavaCow.entities.aquatic;

import java.util.Random;

import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;

import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.passive.fish.AbstractGroupFishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class UndeadFishEntity extends AbstractGroupFishEntity {
	public UndeadFishEntity(EntityType<? extends UndeadFishEntity> p_i50279_1_, World p_i50279_2_) {
		super(p_i50279_1_, p_i50279_2_);
	}
	
    public static boolean isDarkEnoughToSpawn(IServerWorld p_223323_0_, BlockPos p_223323_1_, Random p_223323_2_) {
        if (p_223323_0_.getBrightness(LightType.SKY, p_223323_1_) > p_223323_2_.nextInt(32)) {
           return false;
        } else {
           int i = p_223323_0_.getLevel().isThundering() ? p_223323_0_.getMaxLocalRawBrightness(p_223323_1_, 10) : p_223323_0_.getMaxLocalRawBrightness(p_223323_1_);
           return i <= p_223323_2_.nextInt(8);
        }
	}
	
	public static boolean checkUndeadFishSpawnRules(EntityType<? extends AbstractFishEntity> p_223363_0_, IWorld p_223363_1_, SpawnReason p_223363_2_, BlockPos p_223363_3_, Random p_223363_4_) {
		return p_223363_1_.getBlockState(p_223363_3_).is(Blocks.WATER) && p_223363_4_.nextInt(15) == 0 && isDarkEnoughToSpawn((IServerWorld) p_223363_1_, p_223363_3_, p_223363_4_);
	}
	
    protected void handleAirSupply(int p_209207_1_) {    	
    }

	protected ItemStack getBucketItemStack() {
		if (this.getType().equals(FUREntityRegistry.MUMMIFIEDCOD)) {
			return new ItemStack(FURItemRegistry.MUMMIFIED_COD_BUCKET);
		} else if  (this.getType().equals(FUREntityRegistry.BONETROUT)) {
			return new ItemStack(FURItemRegistry.BONE_TROUT_BUCKET);
		}
		
		return new ItemStack(Items.WATER_BUCKET);
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