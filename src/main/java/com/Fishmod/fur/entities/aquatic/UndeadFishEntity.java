package com.Fishmod.fur.entities.aquatic;

import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURItemRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class UndeadFishEntity extends AbstractSchoolingFish {
	public UndeadFishEntity(EntityType<? extends UndeadFishEntity> p_i50279_1_, Level p_i50279_2_) {
		super(p_i50279_1_, p_i50279_2_);
	}
	
    public static boolean isDarkEnoughToSpawn(ServerLevelAccessor p_223323_0_, BlockPos p_223323_1_, RandomSource p_223323_2_) {
        if (p_223323_0_.getBrightness(LightLayer.SKY, p_223323_1_) > p_223323_2_.nextInt(32)) {
           return false;
        } else {
           int i = p_223323_0_.getLevel().isThundering() ? p_223323_0_.getMaxLocalRawBrightness(p_223323_1_, 10) : p_223323_0_.getMaxLocalRawBrightness(p_223323_1_);
           return i <= p_223323_2_.nextInt(8);
        }
	}
	
	public static boolean checkUndeadFishSpawnRules(EntityType<? extends AbstractFish> p_223363_0_, ServerLevelAccessor p_223363_1_, MobSpawnType p_223363_2_, BlockPos p_223363_3_, RandomSource p_223363_4_) {
		return p_223363_1_.getBlockState(p_223363_3_).is(Blocks.WATER) && p_223363_4_.nextInt(15) == 0 && isDarkEnoughToSpawn(p_223363_1_, p_223363_3_, p_223363_4_);
	}
	
    protected void handleAirSupply(int p_209207_1_) {    	
    }

    @Override
	public ItemStack getBucketItemStack() {
		if (this.getType().equals(FUREntityRegistry.MUMMIFIED_COD.get())) {
			return new ItemStack(FURItemRegistry.MUMMIFIED_COD_BUCKET.get());
		} else if  (this.getType().equals(FUREntityRegistry.BONE_TROUT.get())) {
			return new ItemStack(FURItemRegistry.BONE_TROUT_BUCKET.get());
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
    public MobType getMobType() {
        return MobType.UNDEAD;
    }
}