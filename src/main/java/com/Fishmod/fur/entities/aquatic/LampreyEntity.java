package com.Fishmod.fur.entities.aquatic;

import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.data.providers.FUREntityTypeTagsProvider;
import com.Fishmod.fur.init.FUREffectRegistry;
import com.Fishmod.fur.init.FURItemRegistry;
import com.Fishmod.fur.init.FURSoundRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.object.PlayState;

public class LampreyEntity extends SwarmerEntity {
    private static final RawAnimation SWIM = RawAnimation.begin().thenPlay("lamprey.model.swim");
    private static final RawAnimation LEECH = RawAnimation.begin().thenPlay("lamprey.model.leeching");
    private static final RawAnimation LAND = RawAnimation.begin().thenPlay("lamprey.model.onland");
    private static final RawAnimation ATTACK = RawAnimation.begin().thenPlay("lamprey.model.attacking_blend");
    
	public int lifespawn;
	
    public LampreyEntity(EntityType<? extends LampreyEntity> p_i48549_1_, Level worldIn) {
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
    	this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, (p_210136_0_) -> {
            return !this.requiresCustomPersistence();
    	}));
    	this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, (p_210136_0_) -> {
    		return ((LivingEntity)p_210136_0_).attackable() && p_210136_0_.getType().is(FUREntityTypeTagsProvider.LAMPREY_TARGETS) && !this.requiresCustomPersistence();
    	}));	
    }
 
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 1.2D)
        		.add(Attributes.FOLLOW_RANGE, 8.0D)
        		.add(Attributes.MAX_HEALTH, 6.0D)
        		.add(Attributes.ATTACK_DAMAGE, 1.0D);
    }
    
    public int getMaxSchoolSize() {
        return 12;
    }
    
    @Override
    public boolean canBeAffected(MobEffectInstance p_70687_1_) {
        if (p_70687_1_.getEffect() == FUREffectRegistry.INFESTED.get()) {
        	return false;
        }
        
        return super.canBeAffected(p_70687_1_);
	}
    
	@Override
	public void tick() {
        if (this.lifespawn > 0 && this.getVehicle() == null && !this.requiresCustomPersistence()) {
        	this.lifespawn--;
        }
        
        if (this.lifespawn <= 0) {
        	this.kill();
        }
        	     
        if (this.getVehicle() != null && this.getVehicle() instanceof LivingEntity && this.getVehicle().isAlive() && !this.level().isClientSide()) {
        	Entity mount = this.getVehicle();
        	
        	if (!((LivingEntity) mount).hasEffect(FUREffectRegistry.INFESTED.get())) {
        		this.stopRiding();   		
        		this.kill();
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
			if ((this.getVehicle() instanceof Player || this.getVehicle() instanceof Zombie || this.getVehicle() instanceof AbstractVillager || this.getVehicle() instanceof AbstractIllager || this.getVehicle() instanceof AbstractSkeleton) && !((LivingEntity)this.getVehicle()).isBaby()) {
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
			((LivingEntity) entity).addEffect(new MobEffectInstance(FUREffectRegistry.INFESTED.get(), 8*20, 0));					
			return true;
		}
		
		return false;
	}

	@Override
    public void push(Entity entityIn) {		
		super.push(entityIn);
		
		if (FURConfig.Lamprey_Attach.get() && entityIn instanceof LivingEntity && !(entityIn instanceof Player) && entityIn.getType().is(FUREntityTypeTagsProvider.LAMPREY_TARGETS) && !this.isPassenger() && !this.requiresCustomPersistence()) {
			((LivingEntity) entityIn).addEffect(new MobEffectInstance(FUREffectRegistry.INFESTED.get(), 8*20, 0));
    		this.startRiding(entityIn);
        }
    }
	
	@Override
	public void playerTouch(Player playerIn) {
		super.playerTouch(playerIn);
		if (!playerIn.isCreative() && FURConfig.Lamprey_Attach.get() && !this.isPassenger() && !this.requiresCustomPersistence()) {
			playerIn.addEffect(new MobEffectInstance(FUREffectRegistry.INFESTED.get(), 8*20, 0));
    		this.startRiding(playerIn, true);
        } 	
	}	
	
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_213386_1_, DifficultyInstance difficulty, MobSpawnType p_213386_3_, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag p_213386_5_) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Lamprey_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Lamprey_Attack.get());
    	this.setHealth(this.getMaxHealth());
    	
    	return super.finalizeSpawn(p_213386_1_, difficulty, p_213386_3_, livingdata, p_213386_5_);
    }
    
    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public MobType getMobType() {
        return MobType.WATER;
    }
    
    @Override
    public ItemStack getBucketItemStack() {
    	return new ItemStack(FURItemRegistry.LAMPREY_BUCKET.get());
	}
    
	@Override
	public int getAmbientSoundInterval() {
		return 1000;
	}
    
    protected SoundEvent getAmbientSound() {
    	return FURSoundRegistry.LAMPREY_AMBIENT.get();
    }

    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.LAMPREY_DEATH.get();
    }

    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return FURSoundRegistry.LAMPREY_HURT.get();
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
    
    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
    	if (this.isPassenger()) {
    		state.getController().setAnimation(LEECH);
    	} else if (!this.isInWaterOrBubble() && !this.isAggressive()) {
    		state.getController().setAnimation(LAND);
        } else {
            state.getController().setAnimation(SWIM);
        }
        
        return PlayState.CONTINUE;
    }
    
	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "controller", 5, this::predicate));
		controllers.add(new AnimationController<>(this, "trigger_controller", 5, state -> PlayState.STOP).triggerableAnim("attack", ATTACK));
	}
}
