package com.Fishmod.fur.entities.tameable;

import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.entities.ai.AvoidOrFrightEntityGoal;
import com.Fishmod.fur.init.FURItemRegistry;
import com.Fishmod.fur.init.FURSoundRegistry;
import com.Fishmod.fur.item.BeastcallHornItem;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
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

public class CactoidEntity extends FURTameableEntity implements GeoEntity {
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

	private static final RawAnimation IDLE = RawAnimation.begin().thenPlay("cactoid.model.idle");
    private static final RawAnimation IDLE_SLEEP = RawAnimation.begin().thenPlay("cactoid.model.idle_sleep");
    private static final RawAnimation WALK = RawAnimation.begin().thenPlay("cactoid.model.walking");
    private static final RawAnimation ATTACK = RawAnimation.begin().thenPlay("cactoid.model.attacking");
    
    private static final EntityDataAccessor<Boolean> DATA_IS_SHAKING = SynchedEntityData.defineId(CactoidEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> SKIN_TYPE = SynchedEntityData.defineId(CactoidEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> GROWING_STAGE = SynchedEntityData.defineId(CactoidEntity.class, EntityDataSerializers.INT);
	private LookAtPlayerGoal watch;
	private RandomLookAroundGoal look;
	
	public CactoidEntity(EntityType<? extends CactoidEntity> entityType, Level worldIn) {
        super(entityType, worldIn);
    }
	
	@Override
    protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(DATA_IS_SHAKING, false);
		this.getEntityData().define(SKIN_TYPE, Integer.valueOf(0));
		this.getEntityData().define(GROWING_STAGE, Integer.valueOf(0));
    }
	
    @Override
    protected void registerGoals() {
        this.watch = new LookAtPlayerGoal(this, Player.class, 8.0F);
        this.look = new RandomLookAroundGoal(this);
        
    	super.registerGoals();
    	this.goalSelector.addGoal(1, new FloatGoal(this));
    	this.goalSelector.addGoal(3, new AvoidOrFrightEntityGoal<>(this, Camel.class, 6.0F, 1.0D, 1.6D));
    	this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
    	this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.2D, true));
        this.goalSelector.addGoal(10, this.watch);
        this.goalSelector.addGoal(10, this.look);

        this.applyEntityAI();
    }

    protected void applyEntityAI() {
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
    }
    
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.25D)
        		.add(Attributes.MAX_HEALTH, 20.0D)
        		.add(Attributes.ATTACK_DAMAGE, 3.0D);
    }
    
    public static boolean checkCactoidSpawnRules(EntityType<? extends CactoidEntity> entityTypeIn, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource randomSource) {
        return FURTameableEntity.checkMonsterSpawnRules(entityTypeIn, level, spawnType, pos, randomSource) && (level.canSeeSky(pos) || level.dimensionType().ultraWarm());
    }
    
    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (GROWING_STAGE.equals(key)) {
           this.refreshDimensions();
        }

        super.onSyncedDataUpdated(key);
	}    
	
    public boolean isShaking() {
    	return this.entityData.get(DATA_IS_SHAKING);
	}

	public void setShaking(boolean shaking) {
		this.entityData.set(DATA_IS_SHAKING, shaking);
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
    
    public int getSkin() {
        return this.entityData.get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
        this.entityData.set(SKIN_TYPE, Integer.valueOf(skinType));
    }
    
    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    @Override
    public boolean isFood(ItemStack stack) {
    	if (!this.isTame()) {
    		if (this.getSkin() == 3)
    			return stack.getItem().equals(Items.LAVA_BUCKET);
    		else
    			return stack.getItem().equals(Items.WATER_BUCKET);
    	} else
    		return stack.getItem().equals(Items.BONE_MEAL);
    }
    
    @Override
    protected boolean isSunBurnTick() {
        if (this.level().isDay() && !this.level().isClientSide) {
           float f = this.getLightLevelDependentMagicValue();
           BlockPos blockpos = this.getVehicle() instanceof Boat ? (new BlockPos((int)this.getX(), (int)Math.round(this.getY()), (int)this.getZ())).above() : new BlockPos((int)this.getX(), (int)Math.round(this.getY()), (int)this.getZ());
           return (f > 0.5F && this.level().canSeeSky(blockpos));
        }
        return this.level().dimensionType().ultraWarm();
    }
    
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void tick() {   
    	if (!this.level().isClientSide && !this.isTame()) {
    		if (this.isSunBurnTick() && !this.isAggressive() && this.getLastHurtByMob() == null) {
    			this.doSitCommand(null);
    		} else if (this.state != FURTameableEntity.State.WANDERING) {
    			this.doWanderCommand(null);
    		}
    	}    	
    	
    	if (!this.level().isClientSide) {
	        if (this.getAge() < -12000) {
	        	if(this.getGrowingStage() != 0) {
	        		this.setGrowingStage(0);
	        	}
	        } else if (this.getAge() < 0) {
	        	if(this.getGrowingStage() != 1) {
	        		this.setGrowingStage(1);
	        		this.playSound(SoundEvents.BEE_POLLINATE, 1.0F, 1.0F);
	        		this.level().broadcastEntityEvent(this, (byte)14);
	        	}       	
	        } else if (this.getAge() == 0) {
	        	if(this.getGrowingStage() != 2) {
	        		this.setGrowingStage(2);
	        		this.playSound(SoundEvents.BEE_POLLINATE, 1.0F, 1.0F);
	        		this.level().broadcastEntityEvent(this, (byte)14);
	        	}       	
	        }
    	}
    	
    	super.tick();
    }
    
    protected ItemStack getFishBucket() {
    	ItemStack stack = new ItemStack(FURItemRegistry.CACTOID_POT.get());
        CompoundTag compound = new CompoundTag();
        this.addAdditionalSaveData(compound);
        stack.getOrCreateTag().put("CactoidData", compound);
        stack.getOrCreateTag().putInt("BucketVariantTag", this.getSkin());
        
        if (this.hasCustomName()) {
            stack.setHoverName(this.getCustomName());
        }
        
        return stack;
    }
    
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
    	ItemStack itemstack = player.getItemInHand(hand);
    	
        if (itemstack.getItem() == Items.FLOWER_POT && this.isAlive()) {
            this.playSound(SoundEvents.BUCKET_FILL_FISH, 1.0F, 1.0F);
            itemstack.shrink(1);
            ItemStack itemstack1 = this.getFishBucket();
            if (!this.level().isClientSide) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, itemstack1);
            }

            if (itemstack.isEmpty()) {
                player.setItemInHand(hand, itemstack1);
            } else if (!player.getInventory().add(itemstack1)) {
                player.drop(itemstack1, false);
            }

            this.discard();            
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else if (this.isTame() && this.getGrowingStage() == 2 && !(itemstack.getItem() instanceof BeastcallHornItem)) {
    		this.playSound(SoundEvents.ITEM_PICKUP, 1.0F, 1.0F);
    		this.spawnAtLocation(new ItemStack(FURItemRegistry.CACTUS_FRUIT.get(), (this.getSkin() == 3) ? 2 : 1), 0.0F);    	    		
    		this.setGrowingStage(0);
    		this.setAge(-24000);
    		
    		return InteractionResult.sidedSuccess(this.level().isClientSide);
    	} else {
    		return super.mobInteract(player, hand);
    	}
    }
    
    @Override
	public void doSitCommand(Player playerIn) {
        this.goalSelector.removeGoal(this.watch);
        this.goalSelector.removeGoal(this.look);
        this.setSilent(true);
    	super.doSitCommand(playerIn);
    }
    
    @Override
	public void doFollowCommand(Player playerIn) {
        this.goalSelector.addGoal(10, this.watch);
        this.goalSelector.addGoal(10, this.look);
		this.setSilent(false);
        super.doFollowCommand(playerIn);
    }

	@Override
    public boolean doHurtTarget(Entity entityIn) {
        boolean flag = super.doHurtTarget(entityIn);
        
        if (flag) {
        	this.level().broadcastEntityEvent(this, (byte)4);
        }
        
        return flag;
    }    
	
	/**
	* Called when the entity is attacked.
	*/
    @Override
	public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.THORNS)) {
        	return false;
        }

        boolean hurt = source.is(DamageTypeTags.IS_FIRE) ? super.hurt(source, 2.0F * amount) : super.hurt(source, amount);

        if (hurt && !source.is(DamageTypeTags.AVOIDS_GUARDIAN_THORNS) && source.getDirectEntity() instanceof LivingEntity le) {
            le.hurt(this.damageSources().thorns(this), 2.0F);
        }

    	return hurt;
    }

    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficulty, MobSpawnType spawnTypeIn, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Cactoid_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Cactoid_Attack.get());
    	this.setHealth(this.getMaxHealth());
    	this.setAge(-24000);
    	
    	if (spawnTypeIn == MobSpawnType.BUCKET && tag != null && tag.contains("BucketVariantTag", 3)) {
			this.setSkin(tag.getInt("BucketVariantTag"));
    	} else if (spawnTypeIn == MobSpawnType.COMMAND || spawnTypeIn == MobSpawnType.SPAWN_EGG || spawnTypeIn == MobSpawnType.SPAWNER || spawnTypeIn == MobSpawnType.DISPENSER) {
        	this.setSkin(Integer.valueOf(this.random.nextInt(4)));
        } else if (worldIn.getBiome(this.blockPosition()).is(Biomes.BASALT_DELTAS)) {
        	this.setSkin(3);
        } else {       
        	this.setSkin(Integer.valueOf(this.random.nextInt(3)));
        }
    	        
    	return super.finalizeSpawn(worldIn, difficulty, spawnTypeIn, livingdata, tag);
    }	
    
	@Override
    public float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.5F;
    }
	
    /**
     * The speed it takes to move the entityliving's rotationPitch through the faceEntity method. This is only currently
     * use in wolves.
     */
    @Override
    public int getMaxHeadXRot() {
        return this.isSilent() ? 0 : super.getMaxHeadXRot();
    }

    @Override
    public int getMaxHeadYRot() {
        return this.isSilent() ? 0 : super.getMaxHeadYRot();
    }
    
    @Override
    public int getHeadRotSpeed() {
        return this.isSilent() ? 0 : super.getHeadRotSpeed();
	}
	
	@OnlyIn(Dist.CLIENT)
	protected void addParticlesAroundSelf(SimpleParticleType particle) {
		for(int i = 0; i < 5; ++i) {
			double d0 = this.random.nextGaussian() * 0.02D;
			double d1 = this.random.nextGaussian() * 0.02D;
			double d2 = this.random.nextGaussian() * 0.02D;
			this.level().addParticle(particle, this.getRandomX(1.0D), this.getRandomY() + 1.0D, this.getRandomZ(1.0D), d0, d1, d2);
		}
	}
	
    @Override
    public boolean fireImmune() {
        return (this.getSkin() == 3) || super.fireImmune();
    }
	
    /**
     * Handler for {@link World#setEntityState}
     */
	@OnlyIn(Dist.CLIENT)
	@Override
    public void handleEntityEvent(byte id) {
    	if (id == 4) {
    		this.triggerAnim("trigger_controller", "attack");
        } else if (id == 14) {
            this.addParticlesAroundSelf(ParticleTypes.FALLING_NECTAR);
        } else {
            super.handleEntityEvent(id);
        }
    }
	
	@Override
	public float getScale() {
		return 1.0F;
	}
	
	@Override
	public boolean canFallInLove() {
		return false;
	}
    
    @Override
    protected SoundEvent getAmbientSound() {
        return FURSoundRegistry.SHROOMLING_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
    	return SoundEvents.WOOL_BREAK;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.CACTYRANT_DEATH.get();
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
    	this.setSkin(compound.getInt("Variant"));
    	this.setGrowingStage(compound.getInt("GrowingStage"));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", getSkin());
        compound.putInt("GrowingStage", this.getGrowingStage());
    }
	
    /**
     * Called when the mob's health reaches 0.
     */
    @Override
    public void die(DamageSource cause) {
       super.die(cause);

       if(!this.level().isClientSide() && this.shouldDropLoot() && this.getGrowingStage() == 2) {
			this.spawnAtLocation(new ItemStack(FURItemRegistry.CACTUS_FRUIT.get()), 0.0F);
       }
    }
    
    @Override
    public boolean shouldDropLoot() {
    	return this.isTame() || !(this.getOwner() instanceof Player);
    }

    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
    	if (this.isSilent()) {
    		state.getController().setAnimation(IDLE_SLEEP);
    	} else if (state.isMoving() || !this.getNavigation().isDone()) {
            state.getController().setAnimation(WALK);
        } else {
            state.getController().setAnimation(IDLE);
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
