package com.Fishmod.fur.entities;

import java.util.EnumSet;
import javax.annotation.Nullable;

import com.Fishmod.fur.entities.ai.FURMeleeAttackGoal;
import com.Fishmod.fur.init.FURItemRegistry;
import com.Fishmod.fur.init.FURSoundRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class CactyrantEntity extends Monster implements IAggressive, GeoEntity {	
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final RawAnimation IDLE = RawAnimation.begin().thenPlay("cactyrant.model.idle");
    private static final RawAnimation IDLE_SLEEP = RawAnimation.begin().thenPlay("cactyrant.model.idle_sleep");
    private static final RawAnimation WALK = RawAnimation.begin().thenPlay("cactyrant.model.walking");
    private static final RawAnimation ATTACKING = RawAnimation.begin().thenPlay("cactyrant.model.attacking");
    private static final RawAnimation ATTACKING_GRAB = RawAnimation.begin().thenPlay("cactyrant.model.attacking_grab");
    private static final RawAnimation ATTACKING_VOLLEY = RawAnimation.begin().thenPlay("cactyrant.model.attacking_volley");
    
	private static final EntityDataAccessor<Boolean> DATA_IS_CAMOUFLAGING = SynchedEntityData.defineId(CactyrantEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> GROWING_STAGE = SynchedEntityData.defineId(CactyrantEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> HUGGING_CD = SynchedEntityData.defineId(CactyrantEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SKIN_TYPE = SynchedEntityData.defineId(CactyrantEntity.class, EntityDataSerializers.INT);
	public static final int ATTACK_TIMER = 15;
	public static final int SPELL_TIMER = 20;
	private int attackTimer;
	protected int spellTicks;
	private WaterAvoidingRandomStrollGoal move;
	private LookAtPlayerGoal watch;
	private RandomLookAroundGoal look;
	
	public CactyrantEntity(EntityType<? extends CactyrantEntity> p_i48549_1_, Level worldIn) {
		super(p_i48549_1_, worldIn);
		this.xpReward = 12;
	}
	
    @Override
    protected void registerGoals() {
        this.move = new WaterAvoidingRandomStrollGoal(this, 1.0D);
        this.watch = new LookAtPlayerGoal(this, Player.class, 8.0F);
        this.look = new RandomLookAroundGoal(this);
        
        this.goalSelector.addGoal(1, new AICastingApell());
        this.goalSelector.addGoal(2, new CactyrantEntity.AIUseSpell());
        this.goalSelector.addGoal(3, new AttackGoal(this));  
        this.goalSelector.addGoal(6, this.move);
        this.goalSelector.addGoal(8, this.watch);
        this.goalSelector.addGoal(8, this.look);
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
    	this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    	this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, true, (p_210136_0_) -> {
	  	      return !this.isSilent();
	  	   }));
    }
    
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_IS_CAMOUFLAGING, false);
        this.getEntityData().define(GROWING_STAGE, Integer.valueOf(0));
        this.getEntityData().define(HUGGING_CD, Integer.valueOf(0));
        this.getEntityData().define(SKIN_TYPE, Integer.valueOf(0));
	}
    
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.19D)
        		.add(Attributes.FOLLOW_RANGE, 16.0D)
        		.add(Attributes.MAX_HEALTH, 60.0D/*FURConfig.Cactyrant_Health.get()*/)
        		.add(Attributes.ATTACK_DAMAGE, 8.0D/*FURConfig.Cactyrant_Attack.get()*/)
        		.add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }
    
    public static boolean checkCactyrantSpawnRules(EntityType<? extends CactyrantEntity> p_223316_0_, ServerLevelAccessor p_223316_1_, MobSpawnType p_223316_2_, BlockPos p_223316_3_, RandomSource p_223316_4_) {
        return Monster.checkMonsterSpawnRules(p_223316_0_, p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_) && (p_223316_1_.canSeeSky(p_223316_3_) || p_223316_1_.dimensionType().hasCeiling());
    }
 
    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> p_184206_1_) {
        if (GROWING_STAGE.equals(p_184206_1_)) {
           this.refreshDimensions();
        }

        super.onSyncedDataUpdated(p_184206_1_);
	}
    
    public int getSkin() {
        return this.getEntityData().get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
        this.getEntityData().set(SKIN_TYPE, Integer.valueOf(skinType));
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

    public int getHuggingCooldown() {
        return this.getEntityData().get(HUGGING_CD).intValue();
	}
     
	public void setHuggingCooldown(int i) {
		this.getEntityData().set(HUGGING_CD, i);
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
    	
        if (this.attackTimer > 0) {
            --this.attackTimer;
        }
        
        if (this.spellTicks > 0) {
            --this.spellTicks;
        }
        
        if (this.getHuggingCooldown() > 0) {
        	this.setHuggingCooldown(this.getHuggingCooldown() - 1);
        }
 
        if(!this.level().isClientSide()) {
	        if (this.level().isDay() && this.getTarget() == null) {
	        	if(!this.isCamouflaging()) {
		            this.goalSelector.removeGoal(this.move);
		            this.goalSelector.removeGoal(this.watch);
		            this.goalSelector.removeGoal(this.look);
		            this.setSilent(true);
		            this.setCamouflaging(true);
	        	}
	        } else if (this.isCamouflaging()) {
	            this.goalSelector.addGoal(6, this.move);
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
	        		this.level().broadcastEntityEvent(this, (byte)14);
	        	}
	        } else if(this.tickCount > 10 * 60 * 20) {
	        	if(this.getGrowingStage() != 1) {
	        		this.setGrowingStage(1);
	        		this.playSound(SoundEvents.BEE_POLLINATE, 1.0F, 1.0F);
	        		this.level().broadcastEntityEvent(this, (byte)14);
	        	}
	        }
        }
    }
	
    @Override
    public boolean canRiderInteract() {
        return true;
    }

    @Override
    public boolean shouldRiderSit() {
        return false;
    }
    
    @Override
    public void positionRider(Entity passenger, Entity.MoveFunction p_289551_) {
        if (this.hasPassenger(passenger)) {
            float r = 0.4F;
            float angle = (float) ((Math.PI / 180.0F) * this.yBodyRot);
            double offX = r * Math.sin((float) (Math.PI + angle));
            double offZ = r * Math.cos(angle);
            passenger.setPos(this.getX() + offX, this.getY() + 0.8F, this.getZ() + offZ);
        }
    }
	
	/**
	* Called when the entity is attacked.
	*/
    @Override
	public boolean hurt(DamageSource source, float amount) {
        if (!source.is(DamageTypeTags.BYPASSES_ARMOR) && !source.is(DamageTypeTags.IS_EXPLOSION) && source.getDirectEntity() instanceof LivingEntity) {
            source.getDirectEntity().hurt(this.damageSources().thorns(this), 2.0F);
        }
        
    	if(source.is(DamageTypeTags.IS_FIRE))
    		return super.hurt(source, 2.0F * amount);

    	return super.hurt(source, amount);
    }
    
	@Override
    public float getStandingEyeHeight(Pose p_213348_1_, EntityDimensions p_213348_2_) {
        return p_213348_2_.height * 0.85F;
    }
    
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.spellTicks = compound.getInt("SpellTicks");
        this.setSkin(compound.getInt("Variant"));
        this.setGrowingStage(compound.getInt("GrowingStage"));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("SpellTicks", this.spellTicks);
        compound.putInt("Variant", getSkin());
        compound.putInt("GrowingStage", this.getGrowingStage());
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_213386_1_, DifficultyInstance difficulty, MobSpawnType p_213386_3_, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag p_213386_5_) {
        /*this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Cactyrant_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Cactyrant_Attack.get());
    	this.setHealth(this.getMaxHealth());*/
        
		if (p_213386_1_.getBiome(this.blockPosition()).containsTag(Tags.Biomes.IS_HOT_NETHER)) {
    		this.setSkin(1);
    	}
		
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
	protected void addParticlesAroundSelf(SimpleParticleType p_213718_1_) {
		for(int i = 0; i < 5; ++i) {
			double d0 = this.random.nextGaussian() * 0.02D;
			double d1 = this.random.nextGaussian() * 0.02D;
			double d2 = this.random.nextGaussian() * 0.02D;
			this.level().addParticle(p_213718_1_, this.getRandomX(1.0D), this.getRandomY() + 1.0D, this.getRandomZ(1.0D), d0, d1, d2);
		}
	}
    
    /**
     * Handler for {@link World#setEntityState}
     */
	@Override
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
    	if (id == 4) {
            this.attackTimer = ATTACK_TIMER;
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
    		if (CactyrantEntity.this.getTarget() == null || CactyrantEntity.this.isVehicle())
                return false;
            else if (CactyrantEntity.this.isSpellcasting() || !CactyrantEntity.this.getSensing().hasLineOfSight(CactyrantEntity.this.getTarget()))
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
            CactyrantEntity.this.level().broadcastEntityEvent(CactyrantEntity.this, (byte)10);
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
        	/*double d0, d1, d2, d3, f;
        	for(int i = 0 ; i < 6 ; i++) {
	        	CactusThornEntity abstractarrowentity = new CactusThornEntity(CactyrantEntity.this.level(), CactyrantEntity.this);
	            LivingEntity target = CactyrantEntity.this.getTarget();
	            d0 = target.getX() - CactyrantEntity.this.getX();
	            d1 = target.getY(0.3333333333333333D) - abstractarrowentity.getY();
	            d2 = target.getZ() - CactyrantEntity.this.getZ();
	            d3 = (double)Math.sqrt(d0 * d0 + d2 * d2);
	            f = i == 3 ? 0 : Math.sqrt(Math.sqrt(d3)) * 2.0D;
	            abstractarrowentity.shoot(d0 + CactyrantEntity.this.getRandom().nextGaussian() * f, d1 + d3 * 0.2D, d2 + CactyrantEntity.this.getRandom().nextGaussian() * f, 1.6F, (float)(14 - CactyrantEntity.this.level().getDifficulty().getId() * 4));            
	            CactyrantEntity.this.level().addFreshEntity(abstractarrowentity);
        	}*/
        	CactyrantEntity.this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (CactyrantEntity.this.getRandom().nextFloat() * 0.4F + 0.8F));
        }

        protected int getCastWarmupTime() {
            return 10;
        }

        protected int getCastingTime() {
            return 20;
        }

        protected int getCastingInterval() {
            return 3/*FURConfig.Cactyrant_Ability_Cooldown.get()*/ * 20;
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.PLAYER_ATTACK_SWEEP;
        }
    }
    
    @Override
    protected SoundEvent getAmbientSound()
    {
        return FURSoundRegistry.CACTYRANT_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.WOOL_BREAK;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return FURSoundRegistry.CACTYRANT_DEATH.get();
    }

	@Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    	this.playSound(SoundEvents.GRASS_STEP, 0.15F, 1.0F);
	}
	
    /**
     * Called when the mob's health reaches 0.
     */
    @Override
    public void die(DamageSource cause) {
       super.die(cause);
       
       int looting = net.minecraftforge.common.ForgeHooks.getLootingLevel(this, cause.getDirectEntity(), cause);
       int chance = this.random.nextInt(5) + this.random.nextInt(1 + looting);
       if (!this.level().isClientSide() && this.getGrowingStage() == 2) {			
			for (int amount = 0; amount <= chance; ++amount) {
				this.spawnAtLocation(new ItemStack(FURItemRegistry.CACTUS_FRUIT.get()), 0.0F);
			}
       }
    }
    
    /**
     * Entity won't drop items or experience points if this returns false
     */
    @Override
    protected boolean shouldDropLoot() {
       return !this.isOnFire() || this.lastHurtByPlayer != null;
    }
    
    static class AttackGoal extends FURMeleeAttackGoal {
        public AttackGoal(PathfinderMob p_i46676_1_) {
           super(p_i46676_1_, 1.25D, false);
        }
        
    	protected int atkTimerMax() {
    		return ATTACK_TIMER;
    	}
    	
    	protected int atkTimerHit() {
    		return 5;
    	}
    	
    	protected byte atkTimerEvent() {	        
    		return (byte)4;
    	}
    	
    	protected void dmgEvent(LivingEntity target) {  		
    		this.mob.swing(InteractionHand.MAIN_HAND);
    		float f = (float)this.mob.getAttributeValue(Attributes.ATTACK_DAMAGE);
    		float f1 = (float)this.mob.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
    		float f2 = this.mob.level().getCurrentDifficultyAt(this.mob.blockPosition()).getEffectiveDifficulty();
    		boolean flag = false;
    		
    		if (!this.mob.getTarget().isBlocking()) {
                if (!this.mob.isVehicle() && !this.mob.getTarget().isShiftKeyDown() && ((CactyrantEntity)this.mob).getHuggingCooldown() == 0) {
                	this.mob.getTarget().startRiding(this.mob, true);
                	((CactyrantEntity)this.mob).setHuggingCooldown(120);
                } else if (!this.mob.isVehicle()) {
                	flag = target.hurt(this.mob.damageSources().mobAttack(this.mob), f);
                	this.mob.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1.0F, 1.0F);
                } else {
                	flag = target.hurt(this.mob.damageSources().mobAttack(this.mob), f * 0.25F);     
                	f1 = 0.0F;
                }
                
                if (flag) {
        			if (f1 > 0.0F && target instanceof LivingEntity) {
        				((LivingEntity)target).knockback(f1 * 0.5F, (double)Math.sin(this.mob.getYRot() * ((float)Math.PI / 180F)), (double)(-Math.cos(this.mob.getYRot() * ((float)Math.PI / 180F))));
        				this.mob.setDeltaMovement(this.mob.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
        			}

        			this.mob.doEnchantDamageEffects(this.mob, target);
        			this.mob.setLastHurtMob(target);
        			
                    if (this.mob.getMainHandItem().isEmpty() && this.mob.isOnFire() && this.mob.getRandom().nextFloat() < f2 * 0.3F) {
                    	target.setSecondsOnFire(2 * (int)f2);
                    }
        		}
            }    		  		         
    	}    	
	}

    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
    	if (this.isVehicle()) {
    		state.getController().setAnimation(ATTACKING_GRAB);
    	} else if (this.getSpellTicks() >= SPELL_TIMER - 5) {
    		state.getController().setAnimation(ATTACKING_VOLLEY);
    	} else if (this.getAttackTimer() == ATTACK_TIMER) {
    		state.getController().setAnimation(ATTACKING);
    	} else if (this.isSpellcasting() || this.getAttackTimer() > 0) {
    		return PlayState.CONTINUE;
    	} else if (this.isCamouflaging()) {
    		state.getController().setAnimation(IDLE_SLEEP);
    	} else if (state.isMoving() && !this.isInWater()) {
            state.getController().setAnimation(WALK);
        } else {
        	state.getController().setAnimation(IDLE);
        }
        
        return PlayState.CONTINUE;
    }
    
	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "controller", 5, this::predicate));		
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	} 
}
