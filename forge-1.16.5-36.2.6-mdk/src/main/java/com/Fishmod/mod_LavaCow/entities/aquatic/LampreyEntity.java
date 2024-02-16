package com.Fishmod.mod_LavaCow.entities.aquatic;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.init.FUREffectRegistry;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;
import com.Fishmod.mod_LavaCow.init.FURTagRegistry;

import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class LampreyEntity extends SwarmerEntity {
	public int lifespawn;
	
    public LampreyEntity(EntityType<? extends LampreyEntity> p_i48549_1_, World worldIn) {
        super(p_i48549_1_, worldIn); 
        this.lifespawn = FURConfig.Lamprey_Lifespan.get() * 20;
        this.xpReward = 1;        
    }
    
    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new AIPiranhaLeapAtTarget(this, 0.6F));
        this.goalSelector.addGoal(2, new LampreyEntity.AttackGoal(this));      
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 10));
        this.applyEntityAI();
	}
    
    @Override
    protected void applyEntityAI() {
    	this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    	this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, (p_210136_0_) -> {
            return !this.requiresCustomPersistence();
    	}));
    	this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, (p_210136_0_) -> {
    		ITag<EntityType<?>> tag = EntityTypeTags.getAllTags().getTag(FURTagRegistry.LAMPREY_TARGETS);
    		return tag != null && p_210136_0_ instanceof LivingEntity && ((LivingEntity)p_210136_0_).attackable() && p_210136_0_.getType().is(tag);
    	}));	
    }
 
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 1.2D)
        		.add(Attributes.FOLLOW_RANGE, 8.0D)
        		.add(Attributes.MAX_HEALTH, FURConfig.Lamprey_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, FURConfig.Lamprey_Attack.get());
    }
    
    public int getMaxSchoolSize() {
        return 12;
    }
    
    @Override
    public boolean canBeAffected(EffectInstance p_70687_1_) {
        if (p_70687_1_.getEffect() == FUREffectRegistry.INFESTED) {
           net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent event = new net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent(this, p_70687_1_);
           net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
           return event.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW;
        }
        return super.canBeAffected(p_70687_1_);
	}
    
	@Override
	public void tick() {
        if (this.lifespawn > 0 && this.getVehicle() == null && !this.requiresCustomPersistence()) {
        	this.lifespawn--;
        }
        
        if (this.lifespawn <= 0) {
        	this.hurt(DamageSource.mobAttack(this).bypassInvul().bypassArmor() , this.getMaxHealth());
        }
        	     
        if (this.getVehicle() != null && this.getVehicle() instanceof LivingEntity && this.getVehicle().isAlive() && !this.level.isClientSide()) {
        	Entity mount = this.getVehicle();
        	
        	if (!((LivingEntity) mount).hasEffect(FUREffectRegistry.INFESTED)) {
        		this.stopRiding();   		
        		this.hurt(DamageSource.mobAttack(this).bypassInvul().bypassArmor() , this.getMaxHealth());
        	} else if (mount.isAlive() && mount.isOnFire()) {
        		this.setRemainingFireTicks(20);
        		this.stopRiding();        		
        	} else if (mount.isAlive() && this.tickCount % 20 == 0) {
        		this.doHurtTarget(mount);
        	}    	
        }
    
        super.tick();
    }
	
	@Override
	public double getMyRidingOffset() {		
		if (this.isPassenger()) {
			if ((this.getVehicle() instanceof PlayerEntity || this.getVehicle() instanceof ZombieEntity || this.getVehicle() instanceof AbstractVillagerEntity || this.getVehicle() instanceof AbstractRaiderEntity || this.getVehicle() instanceof AbstractSkeletonEntity) && !((LivingEntity)this.getVehicle()).isBaby()) {
				return this.getVehicle().getBbHeight()/2  - 0.85F;
			} else {
				return this.getVehicle().getBbHeight() * 0.65D - 1.0D;
			}
		} else {
			return super.getMyRidingOffset();
		}
	}
	
	@Override
	public boolean doHurtTarget(Entity entity) {
		if (super.doHurtTarget(entity)) {
			((LivingEntity) entity).addEffect(new EffectInstance(FUREffectRegistry.INFESTED, 8*20, 0));					
			return true;
		}
		
		return false;
	}

	@Override
    public void push(Entity entityIn) {		
		super.push(entityIn);
		ITag<EntityType<?>> tag = EntityTypeTags.getAllTags().getTag(FURTagRegistry.LAMPREY_TARGETS);
		if (tag != null && FURConfig.Lamprey_Attach.get() && entityIn instanceof LivingEntity && !(entityIn instanceof PlayerEntity) && entityIn.getType().is(tag) && !this.isPassenger() && !this.requiresCustomPersistence()) {
			((LivingEntity) entityIn).addEffect(new EffectInstance(FUREffectRegistry.INFESTED, 8*20, 0));
    		this.startRiding(entityIn, true);
    	}
    }
	
	@Override
	public void playerTouch(PlayerEntity playerIn) {
		super.playerTouch(playerIn);
		if (!playerIn.isCreative() && FURConfig.Lamprey_Attach.get() && !this.isPassenger() && !this.requiresCustomPersistence()) {
			playerIn.addEffect(new EffectInstance(FUREffectRegistry.INFESTED, 8*20, 0));
    		this.startRiding(playerIn, true);
        } 	
	}	
	
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT p_213386_5_) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Lamprey_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Lamprey_Attack.get());
    	this.setHealth(this.getMaxHealth());
    	
    	return super.finalizeSpawn(p_213386_1_, difficulty, p_213386_3_, livingdata, p_213386_5_);
    }
    
    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.WATER;
    }
    
    protected ItemStack getBucketItemStack() {
    	return new ItemStack(FURItemRegistry.LAMPREY_BUCKET);
	}
    
	@Override
	public int getAmbientSoundInterval() {
		return 1000;
	}
    
    protected SoundEvent getAmbientSound() {
    	return FURSoundRegistry.LAMPREY_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.LAMPREY_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return FURSoundRegistry.LAMPREY_HURT;
    }

    protected SoundEvent getFlopSound() {
        return SoundEvents.SALMON_FLOP;
    }
    
    @Override
	protected boolean shouldDropLoot() {
        return this.lifespawn > 0;
	}
    
    static class AttackGoal extends MeleeAttackGoal {
    	public AttackGoal(LampreyEntity p_i46676_1_) {
           super(p_i46676_1_, 1.2D, true);
        }

        public boolean canUse() {
           return super.canUse() && !this.mob.isPassenger();
        }

        protected double getAttackReachSqr(LivingEntity p_179512_1_) {
           return (double)(0.1F + p_179512_1_.getBbWidth());
        }
	}
}
