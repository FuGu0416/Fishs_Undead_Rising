package com.Fishmod.mod_LavaCow.entities.flying;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.entities.ParasiteEntity;
import com.Fishmod.mod_LavaCow.entities.ai.FlyerFollowOwnerGoal;
import com.Fishmod.mod_LavaCow.init.FUREffectRegistry;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;
import com.Fishmod.mod_LavaCow.init.FURTagRegistry;

import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.NonTamedTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class VespaEntity extends RidableFlyingMobEntity {
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.defineId(VespaEntity.class, DataSerializers.INT);
	
	public VespaEntity(EntityType<? extends VespaEntity> p_i48549_1_, World worldIn) {
		super(p_i48549_1_, worldIn);
	}
	
	@Override
	protected void registerGoals() {
		super.registerGoals();		
		
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		
        this.targetSelector.addGoal(4, new NonTamedTargetGoal<>(this, PlayerEntity.class, false, (p_213440_0_) -> {
            return !(p_213440_0_.isPassenger() && p_213440_0_.getVehicle() instanceof VespaEntity);
        }).setUnseenMemoryTicks(160));
        
    	this.targetSelector.addGoal(4, new NonTamedTargetGoal<>(this, LivingEntity.class, false, (p_210136_0_) -> {
    		ITag<EntityType<?>> tag = EntityTypeTags.getAllTags().getTag(FURTagRegistry.VESPA_TARGETS);
    		return tag != null && p_210136_0_ instanceof LivingEntity && ((LivingEntity)p_210136_0_).attackable() && p_210136_0_.getType().is(tag);
    	}).setUnseenMemoryTicks(160));
	}
	
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.1D)
        		.add(Attributes.FOLLOW_RANGE, 32.0D)
        		.add(Attributes.MAX_HEALTH, FURConfig.Vespa_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, FURConfig.Vespa_Attack.get())
        		.add(Attributes.FLYING_SPEED, 0.1D);
    }
	
    @Override
    protected void defineSynchedData() {
    	super.defineSynchedData();
    	this.getEntityData().define(SKIN_TYPE, Integer.valueOf(0));
    }
    
    @Override
    public void setTame(boolean tamed) {
    	super.setTame(tamed);
        
        if (tamed) {
        	this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Vespa_Health.get() * 2.0D);
        	this.setHealth(this.getHealth() * 2.0F);
        } else {
        	this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Vespa_Health.get());
        	this.setHealth(this.getHealth() * 0.5F);
        }
	}
    
    @Override
    protected Goal wanderGoal() {
    	return new FlyingMobEntity.AIRandomFly(this);
    }
    
    @Override
    protected Goal followGoal() {
    	return new FlyerFollowOwnerGoal(this, 1.0D, 10.0F, 4.0F, false, 24.0D);
    }
    
    @Override
    public boolean isFood(ItemStack stack) {
        return false;
    }
    
    @Override
    public boolean canBeAffected(EffectInstance p_70687_1_) {
        if (p_70687_1_.getEffect() == FUREffectRegistry.INFESTED || p_70687_1_.getEffect() == Effects.POISON) {
           net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent event = new net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent(this, p_70687_1_);
           net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
           return event.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW;
        }
        return super.canBeAffected(p_70687_1_);
	}
    
    @Override
    protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
    	return p_213348_2_.height * 0.45F;
    }
    
    @Override
	public int abilityCooldown() {
    	return 30;
    }
    
    @Override
    public void tick() {
    	super.tick();
    	
    	if(!this.onGround && this.tickCount % 20 == 0) {
    		this.playSound(this.getFlyingSound(), 1.0F, 1.0F);
    	}
    	
    	if (this.getAttackTimer() == 6 && this.abilityCooldown > 0 && this.deathTime <= 0) {
			double d0 = this.getX() + 1.75D * this.getLookAngle().normalize().x;
            double d1 = this.getY();
            double d2 = this.getZ() + 1.75D * this.getLookAngle().normalize().z;
    		      		        
            for (LivingEntity entitylivingbase : this.level.getEntitiesOfClass(LivingEntity.class, new AxisAlignedBB(d0, d1, d2, d0, d1, d2).inflate(1.0D))) {
            	if (!this.equals(entitylivingbase) && !this.isAlliedTo(entitylivingbase)) {
            		this.doHurtTarget(entitylivingbase);
                }
            }	
            
            this.playSound(SoundEvents.TRIDENT_THROW, 0.6F, 2.0F);
    	}
    }
    
    @Override
    protected void ageBoundaryReached() {
    	if(this.isBaby()) {
	        if (this.isSaddled()) {
	        	this.setSaddled(false);
	            this.spawnAtLocation(Items.SADDLE, 1);
	        }
	        
    		if (!this.level.isClientSide) {		   	         	        
    			ParasiteEntity larva = FUREntityRegistry.PARASITE.create(this.level);
    			larva.moveTo(this.getX(), this.getY(), this.getZ(), this.yRot, this.xRot);
    			larva.setSkin(2);
    			
	    		if (this.isTame() && this.getOwner() instanceof PlayerEntity) {
	    			larva.tame((PlayerEntity) this.getOwner());
	    			larva.setCustomName(this.getCustomName());
	    		}
    			
	    		this.level.addFreshEntity(larva);
    		}
	        
	        this.remove();
    	}
    }    
   
    @Override
	public boolean doHurtTarget(Entity par1Entity) {
    	ITag<EntityType<?>> tag = EntityTypeTags.getAllTags().getTag(FURTagRegistry.VESPA_TARGETS);

    	if (tag != null && par1Entity instanceof LivingEntity && par1Entity.getType().is(tag)) {
    		this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Vespa_Attack.get() * 2.0D);
    	} else {
    		this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Vespa_Attack.get());
    	}
 	   
    	if (super.doHurtTarget(par1Entity)) {
    		if (par1Entity instanceof LivingEntity) {
    			float local_difficulty = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();

    			((LivingEntity) par1Entity).addEffect(new EffectInstance(Effects.POISON, 6 * 20 * (int)local_difficulty, 0));
				
    			if(this.getRandom().nextInt(5) == 0) {
    				((LivingEntity) par1Entity).addEffect(new EffectInstance(FUREffectRegistry.INFESTED, 6 * 20 * (int)local_difficulty, 0));
    			}
    		}

    		return true;
    	} else {
    		return false;
    	}
	}

    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean hurt(DamageSource source, float amount) {    	
    	ITag<EntityType<?>> tag = EntityTypeTags.getAllTags().getTag(FURTagRegistry.VESPA_TARGETS);
    	
    	if (tag != null && source.getEntity() instanceof LivingEntity && source.getEntity().getType().is(tag)) {
    		return super.hurt(source, amount * 0.5F);
    	}
    	   
 	   	return super.hurt(source, amount);
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT p_213386_5_) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Vespa_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Vespa_Attack.get());
    	this.setHealth(this.getMaxHealth());
    	
    	return super.finalizeSpawn(p_213386_1_, difficulty, p_213386_3_, livingdata, p_213386_5_);
    }
      
    public int getSkin() {
    	return this.getEntityData().get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
    	this.getEntityData().set(SKIN_TYPE, Integer.valueOf(skinType));
    }
    
    @Override
	protected double VehicleSpeedMod() {
		return (this.isInLava() || this.isInWater()) ? 0.2D : 2.0D;
	}
	
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.setSkin(compound.getInt("Variant"));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", getSkin());
    }
	
	@Override
	public int getAmbientSoundInterval() {
		return 1000;
	}
	
	public SoundCategory getSoundCategory() {
		return SoundCategory.HOSTILE;
	}

	protected SoundEvent getAmbientSound() {
		return FURSoundRegistry.VESPA_AMBIENT;
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return FURSoundRegistry.VESPA_HURT;
	}

	protected SoundEvent getDeathSound() {
		return FURSoundRegistry.VESPA_DEATH;
	}
	
	protected SoundEvent getFlyingSound() {
		return FURSoundRegistry.VESPA_FLYING;
	}
	
	@Override
    protected void playStepSound(BlockPos pos, BlockState state) {
		if (this.getLandTimer() > 10) {
			this.playSound(SoundEvents.SPIDER_STEP, 0.15F, 1.0F);
		}
	}
	
    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.ARTHROPOD;
    }

	/**
	* Returns the volume for the sounds this mob makes.
	*/
	protected float getSoundVolume() {
		return 0.7F;
	}
}
