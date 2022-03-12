package com.Fishmod.mod_LavaCow.entities.aquatic;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.entities.ai.EntityAIPickupMeat;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class PiranhaEntity extends SwarmerEntity {
	
    public PiranhaEntity(EntityType<? extends PiranhaEntity> p_i48549_1_, World worldIn)
    {
        super(p_i48549_1_, worldIn); 
    }
    
    @Override
    protected void applyEntityAI() {
    	this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    	if(FURConfig.Piranha_AnimalAttack.get()) {
    		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, SquidEntity.class, true));
    		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, AgeableEntity.class, 10, true, false, (p_210136_0_) -> {
                    return !(p_210136_0_ instanceof TameableEntity) && ((AgeableEntity)p_210136_0_).getHealth() < ((AgeableEntity)p_210136_0_).getMaxHealth();
            }));
    	}
    	this.targetSelector.addGoal(5, new EntityAIPickupMeat<>(this, ItemEntity.class, true));
    }
 
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 1.2D)
        		.add(Attributes.FOLLOW_RANGE, 8.0D)
        		.add(Attributes.MAX_HEALTH, FURConfig.Piranha_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, FURConfig.Piranha_Attack.get());
    }

    public int getMaxSchoolSize() {
        return 12;
    }
    
    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public CreatureAttribute getMobType()
    {
        return CreatureAttribute.UNDEFINED;
    }
    
    protected ItemStack getBucketItemStack() {
    	return new ItemStack(FURItemRegistry.PIRANHA_BUCKET);
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
