package com.Fishmod.fur.entities.tameable;

import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.core.SpawnUtil;
import com.Fishmod.fur.entities.ai.ScarabReturnHomeGoal;
import com.Fishmod.fur.init.FUREffectRegistry;
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
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ScarabEntity extends FURTameableEntity implements GeoEntity {
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final RawAnimation IDLE = RawAnimation.begin().thenPlay("scarab.model.idle");
    private static final RawAnimation WALK = RawAnimation.begin().thenPlay("scarab.model.walk");
    private static final RawAnimation FLY = RawAnimation.begin().thenPlay("scarab.model.fly");
    private static final RawAnimation ATTACK = RawAnimation.begin().thenPlay("scarab.model.attack");
    
	private static final EntityDataAccessor<Integer> SKIN_TYPE = SynchedEntityData.defineId(ScarabEntity.class, EntityDataSerializers.INT);
	private int attackTimer = 10;
	private int limitedLifeTicks;
	private int fire_aspect;
	private int sharpness;
	private int knockback;
	private int bane_of_arthropods;
	private int smite;
	private int corrosive;
	private int unbreaking;
	private boolean isSmoking = false;
	@Nullable
	private BlockPos homePos;   // set for Bone Pile scarabs; they walk back here at night
	
	public ScarabEntity(EntityType<? extends ScarabEntity> entityType, Level worldIn) {
        super(entityType, worldIn);
        this.limitedLifeTicks = -1;
    }
	
	@Override
    protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SKIN_TYPE, Integer.valueOf(0));
    }
	
    @Override
    protected void registerGoals() {
    	this.goalSelector.addGoal(1, new FloatGoal(this));
    	this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
    	this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
    	this.goalSelector.addGoal(6, new ScarabReturnHomeGoal(this));
    	this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));

        this.applyEntityAI();
    }

    protected void applyEntityAI() {
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
    	this.targetSelector.addGoal(4, new AICopyOwnerTarget(this));
    }
    
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.263D)
        		.add(Attributes.MAX_HEALTH, 8.0D)
        		.add(Attributes.ATTACK_DAMAGE, 1.0D);
    }
    
    public void setLimitedLife(int limitedLifeTicksIn) {    	
    	if (limitedLifeTicksIn != 0) {
    		this.limitedLifeTicks = limitedLifeTicksIn;
    	}
    }
    
    public float getBonusDamage(LivingEntity LivingEntityIn) {
    	return (0.5f * this.sharpness + 0.5f)
				+ (LivingEntityIn.getMobType().equals(MobType.ARTHROPOD) ? (float)bane_of_arthropods * 2.5f : 0)
				+ (LivingEntityIn.getMobType().equals(MobType.UNDEAD) ? (float)smite * 2.5f : 0);
    }
    
    public int getSkin() {
        return this.entityData.get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
        this.entityData.set(SKIN_TYPE, Integer.valueOf(skinType));
    }

    @Nullable
    public BlockPos getHomePos() {
        return this.homePos;
    }

    public void setHomePos(@Nullable BlockPos pos) {
        this.homePos = pos;
    }
    
	@Override
    protected boolean isCommandable() {
    	return false;
    }

	@Override
    public boolean isSummonedMinion() {
    	return true;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void aiStep() {  	
        if (this.isSmoking) {
	    	for (int j = 0; j < 8; ++j) {
	            float f = this.random.nextFloat() * ((float)Math.PI * 2F);
	            float f1 = this.getBbHeight() * 0.4F + this.random.nextFloat() * 0.5F;
	            float f2 = Mth.sin(f) * f1;
	            float f3 = Mth.cos(f) * f1;
	            Level world = this.level();
	            SimpleParticleType enumparticletypes = ParticleTypes.CAMPFIRE_COSY_SMOKE;
	            double d0 = this.getX() + (double)f2;
	            double d1 = this.getZ() + (double)f3;
	            world.addParticle(enumparticletypes, d0, this.getBoundingBox().minY + (double)f1, d1, 0.0D, 0.05D, 0.0D);
	        }
        }
    	
    	super.aiStep();
    }
    
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
	@Override
    public void tick() {
		super.tick();
		
    	if (this.attackTimer > 0)
    		this.attackTimer--;
    	
    	if (this.limitedLifeTicks >= 0 && this.tickCount >= this.limitedLifeTicks) {    		
            if (FURConfig.Show_Expire_Death_Messege.get() && !this.level().isClientSide() && this.level().getGameRules().getBoolean(GameRules.RULE_SHOWDEATHMESSAGES) && this.getOwner() instanceof Player) {
                this.getOwner().sendSystemMessage(SpawnUtil.TimeupDeathMessage(this));
            }  
            this.level().broadcastEntityEvent(this, (byte)11);
            this.playSound(this.getDeathSound(), this.getSoundVolume(), this.getVoicePitch());
            this.discard();
        }
	}

	@Override
    public boolean doHurtTarget(Entity entityIn) {
        boolean flag = super.doHurtTarget(entityIn);

        if (flag) {
        	this.attackTimer = 10;
        	this.level().broadcastEntityEvent(this, (byte)40);
        	
            if(entityIn instanceof LivingEntity) {
	            if(this.fire_aspect > 0)
	            	entityIn.setSecondsOnFire((this.fire_aspect * 4) - 1);
	            
	            if(this.knockback > 0)
	            	((LivingEntity)entityIn).knockback((float)this.knockback * 0.5F, (this.getX() - entityIn.getX())/this.distanceTo(entityIn), (this.getZ() - entityIn.getZ())/this.distanceTo(entityIn));
	            
	            if(this.bane_of_arthropods > 0 && (((LivingEntity) entityIn).getMobType().equals(MobType.ARTHROPOD))) {
	                int i = 20 + this.random.nextInt(10 * bane_of_arthropods);
	                ((LivingEntity)entityIn).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, i, 3));
	            }
	            
	            if(this.corrosive > 0)
	            	((LivingEntity)entityIn).addEffect(new MobEffectInstance(FUREffectRegistry.CORRODED.get(), 4*20, this.corrosive - 1));
	            
            	((LivingEntity)entityIn).addEffect(new MobEffectInstance(FUREffectRegistry.SOILED.get(), 8 * 20, 1));
            }
        }

        return flag;
    }    
         
    /**
     * If Animal, checks if the age timer is negative
     */
	@Override
    public boolean isBaby() {
       return true;
    }
	
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {        
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Scarab_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Scarab_Attack.get());
    	this.setHealth(this.getMaxHealth());
    	
    	return super.finalizeSpawn(worldIn, difficulty, spawnType, livingdata, tag);
    }	
    
	@Override
    public float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.6F;
    }
	
	/**
	* Called when the entity is attacked.
	*/
    @Override
	public boolean hurt(DamageSource source, float amount) {      
    	return source.is(DamageTypeTags.IS_FALL) ? false : super.hurt(source, amount);
	}	
	
    /**
     * Handler for {@link World#setEntityState}
     */
	@OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
		if (id == 40) {
            this.attackTimer = 10;
        } else if (id == 11) {
            this.isSmoking = true;
        } else {
            super.handleEntityEvent(id);
        }
    }
    
    class AICopyOwnerTarget extends TargetGoal {
    	private final TargetingConditions copyOwnerTargeting = TargetingConditions.forNonCombat().ignoreLineOfSight().ignoreInvisibilityTesting();
    	private LivingEntity owner = ScarabEntity.this.getOwner();
    	
        public AICopyOwnerTarget(PathfinderMob creature) {
            super(creature, false);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean canUse() {
            return this.owner != null && this.owner instanceof Monster && ((Monster) this.owner).isAggressive() && this.canAttack(((Monster) this.owner).getTarget(), this.copyOwnerTargeting);
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            ScarabEntity.this.setTarget(((Monster) this.owner).getTarget());
            super.start();
        }
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return FURSoundRegistry.SCARAB_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FURSoundRegistry.SCARAB_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.SCARAB_DEATH.get();
    }

	@Override
    protected void playStepSound(BlockPos pos, BlockState state) {
	    this.playSound(SoundEvents.CHICKEN_STEP, 0.15F, 1.0F);
	}
    
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
       super.readAdditionalSaveData(compound);
        this.setLimitedLife(compound.getInt("LifeTicks"));
        this.setSkin(compound.getInt("Variant"));
    	this.fire_aspect = compound.getInt("fire_aspect");
    	this.sharpness = compound.getInt("sharpness");
    	this.knockback = compound.getInt("knockback");
    	this.bane_of_arthropods = compound.getInt("bane_of_arthropods");
    	this.smite = compound.getInt("fire_aspect");
    	this.corrosive = compound.getInt("corrosive");
    	this.unbreaking = compound.getInt("unbreaking");
    	this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Scarab_Health.get() + ((float)this.unbreaking * 2.0F));
    	this.homePos = compound.contains("HomePos") ? BlockPos.of(compound.getLong("HomePos")) : null;
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("LifeTicks", this.limitedLifeTicks - this.tickCount);
        compound.putInt("Variant", getSkin());
        compound.putInt("fire_aspect", this.fire_aspect);
        compound.putInt("sharpness", this.sharpness);
        compound.putInt("knockback", this.knockback);
        compound.putInt("bane_of_arthropods", this.bane_of_arthropods);
        compound.putInt("smite", this.smite);
        compound.putInt("corrosive", this.corrosive);
        compound.putInt("unbreaking", this.unbreaking);
        if (this.homePos != null) {
            compound.putLong("HomePos", this.homePos.asLong());
        }
    }

    /**
     * Get this Entity's MobType
     */
    @Override
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }
    
    @Override
    public boolean shouldDropLoot() {
    	return !this.isTame() || (this.isTame() && !(this.getOwner() instanceof Player));
    }

    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
		if (this.onGround()) {
			if (state.isMoving() || !this.getNavigation().isDone()) {
				state.getController().setAnimation(WALK);
			} else {
				state.getController().setAnimation(IDLE);
			}
		} else {
			state.getController().setAnimation(FLY);
		}
        
        return PlayState.CONTINUE;
    }

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "controller", 5, this::predicate));
		controllers.add(new AnimationController<>(this, "trigger_controller", 5, state -> PlayState.STOP).triggerableAnim("attack", ATTACK));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
