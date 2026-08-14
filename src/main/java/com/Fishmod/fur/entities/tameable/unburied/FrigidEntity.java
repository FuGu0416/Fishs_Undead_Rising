package com.Fishmod.fur.entities.tameable.unburied;

import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.entities.tameable.FURTameableEntity;
import com.Fishmod.fur.init.FURItemRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class FrigidEntity extends UnburiedEntity {

    public FrigidEntity(EntityType<? extends FrigidEntity> entityType, Level LevelIn) {
        super(entityType, LevelIn);
    }

    @Override
    protected void defineSynchedData() {
    	super.defineSynchedData();
    	this.setSkin(3);
    }

    @Override
    protected void registerGoals() {
    	super.registerGoals();
    	if (!FURConfig.SunScreen_Mode.get())this.goalSelector.addGoal(4, new FleeSunGoal(this, 1.0D));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return  Zombie.createAttributes()
        		.add(Attributes.FOLLOW_RANGE, 35.0D)
        		.add(Attributes.MOVEMENT_SPEED, (double)0.23F)
        		.add(Attributes.MAX_HEALTH, 30.0D)
        		.add(Attributes.ATTACK_DAMAGE, 3.0D)
        		.add(Attributes.ARMOR, 2.0D)
        		.add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }

    public static boolean checkFrigidSpawnRules(EntityType<? extends FrigidEntity> entityTypeIn, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource randomSource) {
        return FURTameableEntity.checkMonsterSpawnRules(entityTypeIn, level, spawnType, pos, randomSource);
    }
    
    @Override
    public double getMyRidingOffset() {
        return this.isBaby() ? 0.0D : -0.25D;
    }
    
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void aiStep() {
        super.aiStep();    
    }
    
	@Override
    public boolean doHurtTarget(Entity par1Entity) {
        if (super.doHurtTarget(par1Entity)) {   
        	int frozen_ticks = par1Entity.getTicksFrozen();
        	
        	if (this.isTame()) {
        		((LivingEntity)par1Entity).setTicksFrozen(Math.min(this.getTicksRequiredToFreeze(), frozen_ticks + 80));
        		if (this.weaponEnchants.getBaneOfArthropods() > 0 && (((LivingEntity) par1Entity).getMobType().equals(MobType.ARTHROPOD))) {
        			int i = 20 + this.random.nextInt(10 * this.weaponEnchants.getBaneOfArthropods());
	            	((LivingEntity)par1Entity).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, i, 4));
	            } else {	            	
	            	((LivingEntity)par1Entity).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 2 * 20, 2));
	            }
        	} else {
        		float local_difficulty = this.level().getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
                ((LivingEntity)par1Entity).setTicksFrozen(Math.min(par1Entity.getTicksRequiredToFreeze(), frozen_ticks + 80 * (int)local_difficulty));
        		((LivingEntity)par1Entity).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 2 * 20 * (int)local_difficulty, 2));
        	}
        	
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Gives armor or weapon for entity based on given DifficultyInstance
     */
    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        super.populateDefaultEquipmentSlots(random, difficulty);

        if (this.random.nextFloat() < (this.level().getDifficulty() == Difficulty.HARD ? 0.05F : 0.01F)) {
        	this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(FURItemRegistry.FROZEN_THIGH.get()));
        }   
    }
    
    /**
     * Called when the entity is attacked.
     */
    public boolean hurt(DamageSource source, float amount) {
    	return super.hurt(source, source.is(DamageTypeTags.IS_FIRE) ? (2.0F * amount) : amount);
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficulty, MobSpawnType spawnTypeIn, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
    	livingdata = super.finalizeSpawn(worldIn, difficulty, spawnTypeIn, livingdata, tag);
    	this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Frigid_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Frigid_Attack.get());
    	this.setHealth(this.getMaxHealth());
    	this.setSkin(3);
    	return livingdata;
    }    
}
