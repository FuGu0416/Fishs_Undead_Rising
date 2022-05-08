package com.Fishmod.mod_LavaCow.entities;

import java.util.EnumSet;
import java.util.Random;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.entities.projectiles.CactusThornEntity;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CactyrantEntity extends MonsterEntity implements IAggressive {	
	private static final DataParameter<Boolean> DATA_IS_CAMOUFLAGING = EntityDataManager.defineId(CactyrantEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> GROWING_STAGE = EntityDataManager.defineId(CactyrantEntity.class, DataSerializers.INT);
	private int attackTimer;
	protected int spellTicks;
	private WaterAvoidingRandomWalkingGoal move;
	private LookAtGoal watch;
	private LookRandomlyGoal look;
	
	public CactyrantEntity(EntityType<? extends CactyrantEntity> p_i48549_1_, World worldIn) {
		super(p_i48549_1_, worldIn);
		this.xpReward = 12;
	}
	
    @Override
    protected void registerGoals() {
        this.move = new WaterAvoidingRandomWalkingGoal(this, 1.0D);
        this.watch = new LookAtGoal(this, PlayerEntity.class, 8.0F);
        this.look = new LookRandomlyGoal(this);
        
        this.goalSelector.addGoal(1, new AICastingApell());
        this.goalSelector.addGoal(2, new CactyrantEntity.AIUseSpell());
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.25D, false));  
        this.goalSelector.addGoal(6, this.move);
        this.goalSelector.addGoal(8, this.watch);
        this.goalSelector.addGoal(8, this.look);
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
    	this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    	this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, true, (p_210136_0_) -> {
	  	      return !this.isSilent();
	  	   }));
    	this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PigEntity.class, true));
    }
    
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_IS_CAMOUFLAGING, false);
        this.entityData.define(GROWING_STAGE, Integer.valueOf(0));
	}
    
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.19D)
        		.add(Attributes.FOLLOW_RANGE, 16.0D)
        		.add(Attributes.MAX_HEALTH, FURConfig.Cactyrant_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, FURConfig.Cactyrant_Attack.get())
        		.add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }
    
    public static boolean checkCactyrantSpawnRules(EntityType<? extends CactyrantEntity> p_223316_0_, IWorld p_223316_1_, SpawnReason p_223316_2_, BlockPos p_223316_3_, Random p_223316_4_) {
        return MonsterEntity.checkMonsterSpawnRules(p_223316_0_, (IServerWorld) p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_) && p_223316_1_.canSeeSky(p_223316_3_);//SpawnUtil.isAllowedDimension(this.dimension);
    }
 
    @Override
    public void onSyncedDataUpdated(DataParameter<?> p_184206_1_) {
        if (GROWING_STAGE.equals(p_184206_1_)) {
           this.refreshDimensions();
        }

        super.onSyncedDataUpdated(p_184206_1_);
	}
    
    public boolean isCamouflaging() {
       return this.entityData.get(DATA_IS_CAMOUFLAGING);
    }

    public void setCamouflaging(boolean p_175454_1_) {
       this.entityData.set(DATA_IS_CAMOUFLAGING, p_175454_1_);
    }
    
    /**
     * Growing Stage: Normal -> Flowering-> Fruited
     */
    public int getGrowingStage() {
       return this.getEntityData().get(GROWING_STAGE).intValue();
    }
    
    public void setGrowingStage(int i) {
        this.getEntityData().set(GROWING_STAGE, i);
        this.refreshDimensions();
    }
    
    public boolean isSpellcasting() {
    	return this.spellTicks > 0;
    }
    
    public int getSpellTicks() {
        return this.spellTicks;
    }
    
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void tick() {
        super.tick();
    	LivingEntity target = this.getTarget();
    	
        if (this.attackTimer > 0) {
            --this.attackTimer;
        }
        
        if (this.spellTicks > 0) {
            --this.spellTicks;
        }
 
        if(!this.level.isClientSide()) {
	        if (this.level.isDay() && this.getTarget() == null) {
	        	if(!this.isCamouflaging()) {
		            this.goalSelector.removeGoal(this.move);
		            this.goalSelector.removeGoal(this.watch);
		            this.goalSelector.removeGoal(this.look);
		            this.setSilent(true);
		            this.setCamouflaging(true);
	        	}
	        } else if (this.isCamouflaging()) {
	            this.goalSelector.addGoal(5, this.move);
	            this.goalSelector.addGoal(8, this.watch);
	            this.goalSelector.addGoal(8, this.look);
	            this.setSilent(false);
	            this.setCamouflaging(false);
	        }
	        
	        if(this.getGrowingStage() == 2) {
	        	// Full grown
	        } else if(this.tickCount > 20 * 60 * 20) {
	        	if(this.getGrowingStage() != 2) {
	        		this.setGrowingStage(2);
	        		this.playSound(SoundEvents.BEE_POLLINATE, 1.0F, 1.0F);
	        		this.level.broadcastEntityEvent(this, (byte)14);
	        	}
	        } else if(this.tickCount > 10 * 60 * 20) {
	        	if(this.getGrowingStage() != 1) {
	        		this.setGrowingStage(1);
	        		this.playSound(SoundEvents.BEE_POLLINATE, 1.0F, 1.0F);
	        		this.level.broadcastEntityEvent(this, (byte)14);
	        	}
	        }
        }
  	   	        
        if (target != null && this.distanceToSqr(target) < 4.0D && this.getAttackTimer() == 5 && this.deathTime <= 0 && this.canSee(target)) {
        	float f = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
        	this.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1.0F, 1.0F);	        	
        	target.hurt(DamageSource.mobAttack(this), (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE));	            
            if (this.getMainHandItem().isEmpty() && this.isOnFire() && this.random.nextFloat() < f * 0.3F) {
            	target.setSecondsOnFire(2 * (int)f);
            }
        }
    }
	
	@Override
    public boolean doHurtTarget(Entity entityIn) {
        this.attackTimer = 15;
        this.level.broadcastEntityEvent(this, (byte)4);
        return true;
    }
	
	/**
	* Called when the entity is attacked.
	*/
    @Override
	public boolean hurt(DamageSource source, float amount) {
    	if(source.getEntity() != null && source.getDirectEntity() != null && source.getEntity().equals(source.getDirectEntity()) && !source.isProjectile())
    		if(source instanceof EntityDamageSource && !((EntityDamageSource) source).isThorns())
    			source.getEntity().hurt(DamageSource.thorns(this), 1.0F);
    	return super.hurt(source, amount);
    }
    
	@Override
    public float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
        return p_213348_2_.height * 0.85F;
    }
    
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.spellTicks = compound.getInt("SpellTicks");
        this.setGrowingStage(compound.getInt("GrowingStage"));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("SpellTicks", this.spellTicks);
        compound.putInt("GrowingStage", this.getGrowingStage());
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
	@Nullable
	public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT p_213386_5_) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Cactyrant_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Cactyrant_Attack.get());
    	this.setHealth(this.getMaxHealth());
        
        return livingdata;
    }

    public int getAttackTimer() {
        return this.attackTimer;
    }

	@Override
	public void setAttackTimer(int i) {
		this.attackTimer = i;
	}
	
	@OnlyIn(Dist.CLIENT)
	protected void addParticlesAroundSelf(IParticleData p_213718_1_) {
		for(int i = 0; i < 5; ++i) {
			double d0 = this.random.nextGaussian() * 0.02D;
			double d1 = this.random.nextGaussian() * 0.02D;
			double d2 = this.random.nextGaussian() * 0.02D;
			this.level.addParticle(p_213718_1_, this.getRandomX(1.0D), this.getRandomY() + 1.0D, this.getRandomZ(1.0D), d0, d1, d2);
		}
	}
    
    /**
     * Handler for {@link World#setEntityState}
     */
	@Override
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
    	if (id == 4) {
            this.attackTimer = 15;
        } else if (id == 10) {
        	this.spellTicks = 20;
        } else if (id == 14) {
            this.addParticlesAroundSelf(ParticleTypes.FALLING_NECTAR);
        } else {
            super.handleEntityEvent(id);
        }
    }
    
    public class AICastingApell extends Goal {
        public AICastingApell() {
        	this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean canUse() {
            return CactyrantEntity.this.getSpellTicks() > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            super.start();
            CactyrantEntity.this.getNavigation().stop();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
            super.stop();
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (CactyrantEntity.this.getTarget() != null) {
                CactyrantEntity.this.getLookControl().setLookAt(CactyrantEntity.this.getTarget(), (float)CactyrantEntity.this.getMaxHeadYRot(), (float)CactyrantEntity.this.getMaxHeadXRot());
            }
        }
    }
    
    public class AIUseSpell extends Goal {
        protected int spellWarmup;
        protected int spellCooldown;

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean canUse() {
    		if (CactyrantEntity.this.getTarget() == null)
                return false;
            else if (CactyrantEntity.this.isSpellcasting() || !CactyrantEntity.this.canSee(CactyrantEntity.this.getTarget()))
                return false;
            else {                
            	return CactyrantEntity.this.tickCount >= this.spellCooldown && CactyrantEntity.this.distanceTo(CactyrantEntity.this.getTarget()) > 2.0D;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return CactyrantEntity.this.getTarget() != null && this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.spellWarmup = this.getCastWarmupTime();
            CactyrantEntity.this.spellTicks = this.getCastingTime();
            this.spellCooldown = CactyrantEntity.this.tickCount + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();
            CactyrantEntity.this.level.broadcastEntityEvent(CactyrantEntity.this, (byte)10);
            if (soundevent != null) {
                CactyrantEntity.this.playSound(soundevent, 1.0F, 1.0F);
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            --this.spellWarmup;

            if (this.spellWarmup == 0) {
                this.castSpell();
            }
        }

        protected void castSpell() {
        	double d0, d1, d2, d3, f;
        	for(int i = 0 ; i < 6 ; i++) {
	        	CactusThornEntity abstractarrowentity = new CactusThornEntity(CactyrantEntity.this.level, CactyrantEntity.this);
	            LivingEntity target = CactyrantEntity.this.getTarget();
	            d0 = target.getX() - CactyrantEntity.this.getX();
	            d1 = target.getY(0.3333333333333333D) - abstractarrowentity.getY();
	            d2 = target.getZ() - CactyrantEntity.this.getZ();
	            d3 = (double)MathHelper.sqrt(d0 * d0 + d2 * d2);
	            f = i == 3 ? 0 : MathHelper.sqrt(MathHelper.sqrt(d3)) * 2.0D;
	            abstractarrowentity.shoot(d0 + CactyrantEntity.this.getRandom().nextGaussian() * f, d1 + d3 * 0.2D, d2 + CactyrantEntity.this.getRandom().nextGaussian() * f, 1.6F, (float)(14 - CactyrantEntity.this.level.getDifficulty().getId() * 4));            
	            CactyrantEntity.this.level.addFreshEntity(abstractarrowentity);
        	}
        	CactyrantEntity.this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (CactyrantEntity.this.getRandom().nextFloat() * 0.4F + 0.8F));
        }

        protected int getCastWarmupTime() {
            return 10;
        }

        protected int getCastingTime() {
            return 20;
        }

        protected int getCastingInterval() {
            return FURConfig.Cactyrant_Ability_Cooldown.get() * 20;
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.PLAYER_ATTACK_SWEEP;
        }
    }
    
    @Override
    protected SoundEvent getAmbientSound()
    {
        return FURSoundRegistry.UNDERTAKER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return FURSoundRegistry.UNDERTAKER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return FURSoundRegistry.UNDERTAKER_DEATH;
    }

	@Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    	this.playSound(SoundEvents.ZOMBIE_STEP, 0.15F, 1.0F);
	}
	
    /**
     * Called when the mob's health reaches 0.
     */
    @Override
    public void die(DamageSource cause) {
       super.die(cause);
       
       int looting = net.minecraftforge.common.ForgeHooks.getLootingLevel(this, cause.getDirectEntity(), cause);
       int chance = this.random.nextInt(5) + this.random.nextInt(1 + looting);
       if(!this.level.isClientSide() && this.getGrowingStage() == 2) {			
			for (int amount = 0; amount <= chance; ++amount)
				this.spawnAtLocation(new ItemStack(FURItemRegistry.CACTUS_FRUIT), 0.0F);
       }
    }
    
    /**
     * Entity won't drop items or experience points if this returns false
     */
    @Override
    protected boolean shouldDropLoot() {
       return !this.isOnFire() || this.lastHurtByPlayer != null;
    }
}
