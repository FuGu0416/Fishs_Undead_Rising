package com.Fishmod.fur.entities.aquatic;

import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.entities.ai.EntityAIPickupMeat;
import com.Fishmod.fur.init.FURItemRegistry;
import com.Fishmod.fur.init.FURTagRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class PiranhaEntity extends SwarmerEntity {
	
    public PiranhaEntity(EntityType<? extends PiranhaEntity> p_i48549_1_, Level worldIn) {
        super(p_i48549_1_, worldIn); 
    }
    
    @Override
    protected void applyEntityAI() {
    	this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
    	this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<LivingEntity>(this, LivingEntity.class, 10, true, false, (p_210136_0_) -> {
    		return !this.requiresCustomPersistence() && p_210136_0_ instanceof LivingEntity && ((LivingEntity)p_210136_0_).attackable() && p_210136_0_.getType().is(FURTagRegistry.PIRANHA_TARGETS) && ((LivingEntity)p_210136_0_).getHealth() < ((LivingEntity)p_210136_0_).getMaxHealth();
    	}));
    	this.targetSelector.addGoal(5, new EntityAIPickupMeat<>(this, ItemEntity.class, true));
    }
 
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 1.2D)
        		.add(Attributes.FOLLOW_RANGE, 8.0D)
        		.add(Attributes.MAX_HEALTH, 3.0D)
        		.add(Attributes.ATTACK_DAMAGE, 1.0D);
    }
    
    public static boolean checkPiranhaSpawnRules(EntityType<? extends PiranhaEntity> p_223316_0_, ServerLevelAccessor p_223316_1_, MobSpawnType p_223316_2_, BlockPos p_223316_3_, RandomSource p_223316_4_) {
        return WaterAnimal.checkSurfaceWaterAnimalSpawnRules(p_223316_0_, p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_) && p_223316_1_.getDifficulty() != Difficulty.PEACEFUL;
    }

    public int getMaxSchoolSize() {
        return 12;
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_213386_1_, DifficultyInstance difficulty, MobSpawnType p_213386_3_, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag p_213386_5_) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Piranha_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Piranha_Attack.get());
    	this.setHealth(this.getMaxHealth());
    	
    	return super.finalizeSpawn(p_213386_1_, difficulty, p_213386_3_, livingdata, p_213386_5_);
    }
    
    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public MobType getMobType()
    {
        return MobType.WATER;
    }
    
    public ItemStack getBucketItemStack() {
    	return new ItemStack(FURItemRegistry.PIRANHA_BUCKET.get());
	}
    
    protected SoundEvent getAmbientSound() {
    	return SoundEvents.SALMON_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.SALMON_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return SoundEvents.SALMON_HURT;
    }

    protected SoundEvent getFlopSound() {
        return SoundEvents.SALMON_FLOP;
    }
}
