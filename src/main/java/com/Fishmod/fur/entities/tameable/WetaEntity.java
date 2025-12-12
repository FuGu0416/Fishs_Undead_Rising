package com.Fishmod.fur.entities.tameable;

import java.util.UUID;

import javax.annotation.Nullable;

import com.Fishmod.fur.entities.IAggressive;
import com.Fishmod.fur.entities.ai.EntityAIDestroyCrops;
import com.Fishmod.fur.init.FUREffectRegistry;
import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURItemRegistry;
import com.Fishmod.fur.init.FURSoundRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
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

public class WetaEntity extends FURTameableEntity implements IAggressive, GeoEntity {
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	
	private static final RawAnimation IDLE = RawAnimation.begin().thenPlay("weta.model.idle");
    private static final RawAnimation WALK = RawAnimation.begin().thenPlay("weta.model.walking");
    private static final RawAnimation ATTACK = RawAnimation.begin().thenPlay("weta.model.attacking");
    private static final RawAnimation NIBBLE = RawAnimation.begin().thenPlay("weta.model.nibbling");
    
	private static final EntityDataAccessor<Integer> SKIN_TYPE = SynchedEntityData.defineId(WetaEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> DATA_IS_NIBBLING = SynchedEntityData.defineId(WetaEntity.class, EntityDataSerializers.BOOLEAN);
	private int attackTimer = 0;
	private EntityAIDestroyCrops DestroyCrops;
	public static final int ATTACK_TIMER = 20;
	
	public WetaEntity(EntityType<? extends WetaEntity> p_i48549_1_, Level worldIn) {
		super(p_i48549_1_, worldIn);
		this.xpReward = 1;
	}
	
	@Override
    protected void registerGoals() {
		this.DestroyCrops = new EntityAIDestroyCrops(this, 1.1D, this.isTame());
		
		super.registerGoals();
		this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(Items.ROTTEN_FLESH/*FURItemRegistry.PLAGUED_PORKCHOP*/), false));
        //this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(FURItemRegistry.GREEN_BACON_AND_EGGS), false));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));    
        this.goalSelector.addGoal(5, this.DestroyCrops);
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.applyEntityAI();
    }
	
    protected void applyEntityAI() {
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
    }
    
    @Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SKIN_TYPE, Integer.valueOf(0));
		this.getEntityData().define(DATA_IS_NIBBLING, false);
	}
    
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.23D)
        		.add(Attributes.FOLLOW_RANGE, 16.0D)
        		.add(Attributes.MAX_HEALTH, 12.0D/*FURConfig.Weta_Health.get()*/)
        		.add(Attributes.ATTACK_DAMAGE, 1.0D/*FURConfig.Weta_Attack.get()*/);
    }
    
    public static boolean checkWetaSpawnRules(EntityType<? extends WetaEntity> p_223316_0_, ServerLevelAccessor p_223316_1_, MobSpawnType p_223316_2_, BlockPos p_223316_3_, RandomSource p_223316_4_) {
        return FURTameableEntity.checkMonsterSpawnRules(p_223316_0_, p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_);
    }

    @Override
    public void tick() {
    	if (this.attackTimer > 0) {
            --this.attackTimer;
        }

    	super.tick();
    }
    
    /**
     * Called when the entity is attacked.
     */
    public boolean hurt(DamageSource source, float amount) {
    	if(source.equals(damageSources().cactus()) || source.equals(damageSources().sweetBerryBush()))
    		return false;
    	return super.hurt(source, amount);
    }
    
    protected float getJumpPower() {
        return 1.5F * super.getJumpPower();
    }
    
    @Override
	public void doSitCommand(Player playerIn) {
    	this.goalSelector.removeGoal(this.DestroyCrops);
    	super.doSitCommand(playerIn);
    }
    
    @Override
	public void doWanderCommand(Player playerIn) {
    	this.DestroyCrops = new EntityAIDestroyCrops(this, 1.1D, this.isTame());
    	this.goalSelector.addGoal(5, this.DestroyCrops);
    	super.doWanderCommand(playerIn);
    }
        
    @Override
    protected void reassessTameGoals() {
    	if (this.isTame() && !this.isWandering() && this.DestroyCrops != null && this.getOwner() instanceof Player) {
    		this.goalSelector.removeGoal(this.DestroyCrops);
    	}
    }
    
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
    	ItemStack itemstack = player.getItemInHand(hand);
           	
    	if (itemstack.getItem() == FURItemRegistry.DISEASED_BREAD.get() && this.getSkin() == 0) {
    		if (!player.isCreative()) {
    			itemstack.shrink(1);
    		}
            
        	this.setSkin(2);
        	
        	this.playSound(SoundEvents.AMBIENT_CAVE.get(), 1.0F, 1.0F);
        	for (int i = 0; i < 16; ++i) {
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;
                this.level().addParticle(ParticleTypes.ENTITY_EFFECT, this.getX() + (double)(this.random.nextFloat() * this.getBbWidth()) - (double)this.getBbWidth(), this.getY() + (double)(this.random.nextFloat() * this.getBbHeight()), this.getZ() + (double)(this.random.nextFloat() * this.getBbWidth()) - (double)this.getBbWidth(), d0, d1, d2);
            }
        	
        	return InteractionResult.CONSUME;
        }
    
        return super.mobInteract(player, hand);
    }
    
    public boolean doHurtTarget(Entity entityIn) {
        if (super.doHurtTarget(entityIn)) {
        	this.attackTimer = 5;
        	this.level().broadcastEntityEvent(this, (byte)4);

            if(entityIn instanceof LivingEntity && this.getSkin() == 2) {	            
            	((LivingEntity)entityIn).addEffect(new MobEffectInstance(FUREffectRegistry.SOILED.get(), 8 * 20, 1));
            }
            
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    @Override
    public boolean isFood(ItemStack stack) {
       return stack.getItem().equals(Items.ROTTEN_FLESH);//stack.getItem().equals(FURItemRegistry.PLAGUED_PORKCHOP) || stack.getItem().equals(FURItemRegistry.GREEN_BACON_AND_EGGS);
    }

    @Override
    protected boolean canTameCondition() {
    	return !this.isTame() && !this.isBaby() && this.getSkin() != 2;
    }
    
   /* @Override
    protected int TameRate(ItemStack stack) {
    	if (stack.getItem().equals(FURItemRegistry.PLAGUED_PORKCHOP)) {
    		return 3;
    	} else if (stack.getItem().equals(FURItemRegistry.GREEN_BACON_AND_EGGS)) {
    		return 1;
    	} else {
    		return super.TameRate(stack);
    	}
    }*/
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_213386_1_, DifficultyInstance difficulty, MobSpawnType p_213386_3_, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag p_213386_5_) {
        /*this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Weta_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Weta_Attack.get());
    	this.setHealth(this.getMaxHealth());*/
    	
    	this.setSkin(this.getRandom().nextFloat() < 0.05F ? 2 : 0);
    	
    	return super.finalizeSpawn(p_213386_1_, difficulty, p_213386_3_, livingdata, p_213386_5_);
    }

    @Override
	public int getAttackTimer() {
		return this.attackTimer;
	}
    
	@Override
	public void setAttackTimer(int i) {
		this.attackTimer = i;
	}
	
	public boolean isNibbling() {
		return this.entityData.get(DATA_IS_NIBBLING);
	}
    
	public void setNibbling(boolean i) {
		this.entityData.set(DATA_IS_NIBBLING, i);
	}
	
    public int getSkin() {
        return this.getEntityData().get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
        this.getEntityData().set(SKIN_TYPE, skinType);
    }
	
    /**
     * Handler for {@link World#setEntityState}
     */
	@OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
    	if (id == 4) {
            this.attackTimer = ATTACK_TIMER;
        } else {
            super.handleEntityEvent(id);
        }
    }
    
	@Override
    public float getStandingEyeHeight(Pose p_213348_1_, EntityDimensions p_213348_2_) {
        return 0.1F;
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
    
	@Override
    protected SoundEvent getAmbientSound() {
        return FURSoundRegistry.WETA_AMBIENT.get();
    }
	
	@Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FURSoundRegistry.WETA_HURT.get();
    }

	@Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.WETA_DEATH.get();
    }

	@Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    	this.playSound(SoundEvents.SPIDER_STEP, 0.15F, 1.0F);
	}
    
	public WetaEntity getBreedOffspring(ServerLevel worldIn, AgeableMob ageable) {
		WetaEntity entity = FUREntityRegistry.WETA.get().create(worldIn);
		UUID uuid = this.getOwnerUUID();
		if (uuid != null) {
			entity.setOwnerUUID(uuid);
			entity.setTame(true);
			entity.setHealth(this.getMaxHealth());
		}

		return entity;
	}
	
    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public MobType getMobType() {
        if (this.getSkin() == 2) {
        	return MobType.UNDEAD;
        } else {
        	return MobType.ARTHROPOD;
        }
    }

    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
    	if (this.getAttackTimer() == (ATTACK_TIMER - 1)) {
    		state.getController().setAnimation(ATTACK);	 		
    	} else if (this.getAttackTimer() > 0) {
    		return PlayState.CONTINUE;
    	} else if (state.isMoving()) {
            state.getController().setAnimation(WALK);
    	} else if (this.isNibbling()) {
    		state.getController().setAnimation(NIBBLE);
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
