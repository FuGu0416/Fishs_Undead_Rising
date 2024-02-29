package com.Fishmod.mod_LavaCow.entities.tameable.unburied;

import java.util.Random;
import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.entities.tameable.FURTameableEntity;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;

import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.FleeSunGoal;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class FrigidEntity extends UnburiedEntity {

    public FrigidEntity(EntityType<? extends FrigidEntity> p_i48549_1_, World worldIn) {
        super(p_i48549_1_, worldIn);
        this.BirthAnimation = false;
    }
    
    @Override
    protected void registerGoals() {
    	super.registerGoals();
    	if(!FURConfig.SunScreen_Mode.get())this.goalSelector.addGoal(4, new FleeSunGoal(this, 1.0D));
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return ZombieEntity.createAttributes()
        		.add(Attributes.FOLLOW_RANGE, 35.0D)
        		.add(Attributes.MOVEMENT_SPEED, (double)0.23F)
        		.add(Attributes.MAX_HEALTH, FURConfig.ZombieFrozen_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, FURConfig.ZombieFrozen_Attack.get())
        		.add(Attributes.ARMOR, 2.0D)
        		.add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }

    public static boolean checkFrigidSpawnRules(EntityType<? extends FrigidEntity> p_223316_0_, IWorld p_223316_1_, SpawnReason p_223316_2_, BlockPos p_223316_3_, Random p_223316_4_) {
        return FURTameableEntity.checkMonsterSpawnRules(p_223316_0_, (IServerWorld) p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_);//SpawnUtil.isAllowedDimension(this.dimension);
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
        	if (this.isTame()) {
        		if (this.bane_of_arthropods > 0 && (((LivingEntity) par1Entity).getMobType().equals(CreatureAttribute.ARTHROPOD))) {
        			int i = 20 + this.random.nextInt(10 * bane_of_arthropods);
	            	((LivingEntity)par1Entity).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, i, 4));
	            } else {
	            	((LivingEntity)par1Entity).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 2 * 20, 4));
	            }
        	} else {
        		float local_difficulty = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
        		((LivingEntity)par1Entity).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 2 * 20 * (int)local_difficulty, 4));
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
    protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty) {
        super.populateDefaultEquipmentSlots(difficulty);

        if (this.random.nextFloat() < (this.level.getDifficulty() == Difficulty.HARD ? 0.05F : 0.01F)) {
        	this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(FURItemRegistry.FROZENTHIGH));
        }   
    }
    
    /**
     * Called when the entity is attacked.
     */
    public boolean hurt(DamageSource source, float amount) {
    	return super.hurt(source, source.isFire() ? (2.0F * amount) : amount);
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT p_213386_5_) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.ZombieFrozen_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.ZombieFrozen_Attack.get());
    	this.setHealth(this.getMaxHealth());
    	
    	return super.finalizeSpawn(p_213386_1_, difficulty, p_213386_3_, livingdata, p_213386_5_);
    }    
}
