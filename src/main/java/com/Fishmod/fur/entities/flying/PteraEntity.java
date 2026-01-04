package com.Fishmod.fur.entities.flying;

import javax.annotation.Nullable;

import com.Fishmod.fur.entities.IAggressive;
import com.Fishmod.fur.entities.ai.EntityAIDropRider;
import com.Fishmod.fur.init.FURSoundRegistry;
import com.Fishmod.fur.init.FURTagRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class PteraEntity extends FlyingMobEntity implements IAggressive, GeoEntity {
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

	private static final RawAnimation IDLE = RawAnimation.begin().thenPlay("ptera.model.idle");
    private static final RawAnimation FLY = RawAnimation.begin().thenPlay("ptera.model.flying");
    private static final RawAnimation ATTACK = RawAnimation.begin().thenPlay("ptera.model.attacking");
    
	private static final EntityDataAccessor<Integer> SKIN_TYPE = SynchedEntityData.defineId(PteraEntity.class, EntityDataSerializers.INT);
	public static final int ATTACK_TIMER = 20;
	private int attackTimer;
	
	public PteraEntity(EntityType<? extends PteraEntity> p_i48549_1_, Level worldIn) {
		super(p_i48549_1_, worldIn);
	}

    @Override
    public boolean isControlledByLocalInstance() {
        return false;
    }
	
	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(5, new FlyingMobEntity.AIRandomFly(this));
		if(this.level().getDifficulty() == Difficulty.HARD)
			this.goalSelector.addGoal(1, new EntityAIDropRider(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true).setUnseenMemoryTicks(160));
    	this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 120, true, true, (p_210136_0_) -> {
    		return ((LivingEntity)p_210136_0_).attackable() && p_210136_0_.getType().is(FURTagRegistry.PTERA_TARGETS);
    	}).setUnseenMemoryTicks(160));
	}
	
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.1D)
        		.add(Attributes.FOLLOW_RANGE, 32.0D)
        		.add(Attributes.MAX_HEALTH, 10.0D/*FURConfig.Ptera_Health.get()*/)
        		.add(Attributes.ATTACK_DAMAGE, 3.0D/*FURConfig.Ptera_Attack.get()*/)
        		.add(Attributes.FLYING_SPEED, 0.1D);
    }
    
    public static boolean checkPteraSpawnRules(EntityType<? extends PteraEntity> p_223316_0_, ServerLevelAccessor p_223316_1_, MobSpawnType p_223316_2_, BlockPos p_223316_3_, RandomSource p_223316_4_) {
        return FlyingMobEntity.checkFlyerSpawnRulesNoSky(p_223316_0_, p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_) && (p_223316_1_.canSeeSky(p_223316_3_) || p_223316_1_.getBiome(p_223316_3_).is(Biomes.LUSH_CAVES));
    }
	
    @Override
    protected void defineSynchedData() {
    	super.defineSynchedData();
    	this.getEntityData().define(SKIN_TYPE, Integer.valueOf(0));
    }
	
	@Override
	public boolean shouldRiderSit() {
		return false;
	}
    
    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
	@Override
    public double getPassengersRidingOffset() {
		if(!this.getPassengers().isEmpty())
        	return -(double)this.getPassengers().get(0).getBbHeight() * 0.75D;
        
        return super.getPassengersRidingOffset();
    }
    
	protected float getStandingEyeHeight(Pose p_213348_1_, EntityDimensions p_213348_2_) {
    	return p_213348_2_.height * 0.35F;
    }
  
	/**
	 * For vehicles, the first passenger is generally considered the controller and "drives" the vehicle. For example,
	 * Pigs, Horses, and Boats are generally "steered" by the controlling passenger.
	 */
	@Nullable
	@Override
	public LivingEntity getControllingPassenger() {
		return null;
	}
   
	@Override
	public int getAttackTimer() {
		return this.attackTimer;
	}
   
	@Override
	public void setAttackTimer(int i) {
		this.attackTimer = i;
	}
	
	@Override
    public void tick() {   
    	if (this.attackTimer > 0) {
    		--this.attackTimer;
    	}
    	
    	super.tick();
	}

	@Override
	public boolean doHurtTarget(Entity par1Entity) {
		if (par1Entity.getType().is(FURTagRegistry.PTERA_TARGETS)) {
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(3.0D/*FURConfig.Ptera_Attack.get()*/ * 2.0D);
		} else {
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(3.0D/*FURConfig.Ptera_Attack.get()*/);
		}
		
		boolean flag = super.doHurtTarget(par1Entity);
		
        if (flag) {
        	this.level().broadcastEntityEvent(this, (byte)4);
        }
        
		return flag;
	}
   
	/**
	 * Called when the entity is attacked.
	 */
	public boolean hurt(DamageSource source, float amount) {
		if (!this.getPassengers().isEmpty()) {
			this.ejectPassengers();
		}
    	   
		return super.hurt(source, amount);
	}
   
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficulty, MobSpawnType p_213386_3_, @Nullable SpawnGroupData entityLivingData, @Nullable CompoundTag p_213386_5_) {
		/*this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Ptera_Health.get());
       	this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Ptera_Attack.get());
       	this.setHealth(this.getMaxHealth());*/
	   
		if (p_213386_3_ == MobSpawnType.COMMAND || p_213386_3_ == MobSpawnType.SPAWN_EGG || p_213386_3_ == MobSpawnType.SPAWNER || p_213386_3_ == MobSpawnType.DISPENSER) {
			this.setSkin(Integer.valueOf(this.random.nextInt(6)));
		} else if (worldIn.getBiome(this.blockPosition()).containsTag(BiomeTags.IS_SAVANNA)) {
			this.setSkin(2);
		} else if (worldIn.getBiome(this.blockPosition()).containsTag(Tags.Biomes.IS_SWAMP)) {
			this.setSkin(3);
		} else if (worldIn.getBiome(this.blockPosition()).containsTag(Tags.Biomes.IS_DESERT) || worldIn.getBiome(this.blockPosition()).containsTag(BiomeTags.IS_BADLANDS)) {
			this.setSkin(1);
		} else if (worldIn.getBiome(this.blockPosition()).is(Biomes.LUSH_CAVES)) {
			this.setSkin(5);
		} else {       
			this.setSkin(this.random.nextInt(2) * 4);
		}
	   
		/*if (!this.isPassenger() && this.random.nextInt(100) < FURConfig.Ptera_Ability_Chance.get()) {
		   	MobSpawnInfo.Spawners Result = ((MobSpawnInfo.Spawners)WeightedRandom.getRandomItem(this.random, LootTableHandler.PTERA_LIST));
		   	Entity entityRider = Result.type.create(this.level);
			
		   	if (entityRider instanceof Monster) {					
				entityRider.moveTo(this.blockPosition(), this.yRot, 0.0F);
				this.level().addFreshEntity(entityRider);
				entityRider.startRiding(this);
				((Monster) entityRider).finalizeSpawn(worldIn, difficulty, SpawnReason.MOB_SUMMONED, (ILivingEntityData)null, (CompoundTag)null);								
				
				if(entityRider instanceof FogletEntity) {
					((FogletEntity) entityRider).setIsHanging(true);
				}
		   	}
	   	}*/
	   	   
		return super.finalizeSpawn(worldIn, difficulty, p_213386_3_, entityLivingData, p_213386_5_);
	}  
	
    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    @Override
    public boolean isFood(ItemStack stack) {
    	return stack.is(ItemTags.FISHES);
    }
   
	public int getSkin() {
   		return this.getEntityData().get(SKIN_TYPE).intValue();
	}

	public void setSkin(int skinType) {
   		this.getEntityData().set(SKIN_TYPE, Integer.valueOf(skinType));
	}
	
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setSkin(compound.getInt("Variant"));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", getSkin());
    }
	
    /**
     * Handler for {@link World#setEntityState}
     */
	@OnlyIn(Dist.CLIENT)
	@Override
    public void handleEntityEvent(byte id) {
    	if (id == 4) {
            this.attackTimer = ATTACK_TIMER;
        } else {
            super.handleEntityEvent(id);
        }
    }
	
	public SoundSource getSoundSource() {
		return SoundSource.HOSTILE;
	}

	protected SoundEvent getAmbientSound() {
		return FURSoundRegistry.PTERA_AMBIENT.get();
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return FURSoundRegistry.PTERA_HURT.get();
	}

	protected SoundEvent getDeathSound() {
		return FURSoundRegistry.PTERA_DEATH.get();
	}

	/**
	* Returns the volume for the sounds this mob makes.
	*/
	protected float getSoundVolume() {
		return 0.7F;
	}

    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
    	if (this.getAttackTimer() == (ATTACK_TIMER - 1)) {
			state.getController().setAnimation(ATTACK);			 		
    	} else if (this.getAttackTimer() > 0) {
    		return PlayState.CONTINUE;
    	} else if (state.isMoving()) {
            state.getController().setAnimation(FLY);
        } else {
            state.getController().setAnimation(IDLE);
        }
        
        return PlayState.CONTINUE;
    }

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "controller", 5, this::predicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
