package com.Fishmod.mod_LavaCow.entities.tameable;

import java.util.Random;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;

import net.minecraft.advancements.CriteriaTriggers;
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
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.OwnerHurtByTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CactoidEntity extends FURTameableEntity {
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.defineId(CactoidEntity.class, DataSerializers.INT);
	private static final DataParameter<Integer> GROWING_STAGE = EntityDataManager.defineId(CactoidEntity.class, DataSerializers.INT);
	private LookAtGoal watch;
	private LookRandomlyGoal look;
	
	public CactoidEntity(EntityType<? extends CactoidEntity> p_i48549_1_, World worldIn) {
        super(p_i48549_1_, worldIn);
    }
	
	@Override
    protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SKIN_TYPE, Integer.valueOf(0));
		this.entityData.define(GROWING_STAGE, Integer.valueOf(0));
    }
	
    @Override
    protected void registerGoals() {
        this.watch = new LookAtGoal(this, PlayerEntity.class, 8.0F);
        this.look = new LookRandomlyGoal(this);
        
    	super.registerGoals();
    	this.goalSelector.addGoal(1, new SwimGoal(this));
    	this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
    	this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.5D, true));
        this.goalSelector.addGoal(10, this.watch);
        this.goalSelector.addGoal(10, this.look);

        this.applyEntityAI();
    }

    protected void applyEntityAI() {
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
    }
    
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.25D)
        		.add(Attributes.MAX_HEALTH, FURConfig.Cactoid_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, FURConfig.Cactoid_Attack.get());
    }
    
    public static boolean checkCactoidSpawnRules(EntityType<? extends CactoidEntity> p_223316_0_, IWorld p_223316_1_, SpawnReason p_223316_2_, BlockPos p_223316_3_, Random p_223316_4_) {
        return FURTameableEntity.checkMonsterSpawnRules(p_223316_0_, (IServerWorld) p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_) && p_223316_1_.canSeeSky(p_223316_3_);//SpawnUtil.isAllowedDimension(this.dimension);
    }
    
    @Override
    public void onSyncedDataUpdated(DataParameter<?> p_184206_1_) {
        if (GROWING_STAGE.equals(p_184206_1_)) {
           this.refreshDimensions();
        }

        super.onSyncedDataUpdated(p_184206_1_);
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
    	if (!this.isTame())
    		return stack.getItem().equals(Items.WATER_BUCKET);
    	else
    		return stack.getItem().equals(Items.BONE_MEAL);
    }
    
    @Override
    protected boolean isSunBurnTick() {
        if (this.level.isDay() && !this.level.isClientSide) {
           float f = this.getBrightness();
           BlockPos blockpos = this.getVehicle() instanceof BoatEntity ? (new BlockPos(this.getX(), (double)Math.round(this.getY()), this.getZ())).above() : new BlockPos(this.getX(), (double)Math.round(this.getY()), this.getZ());
           return (f > 0.5F && this.level.canSeeSky(blockpos));
        }
        return false;
    }
    
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void tick() {   	
    	if (!this.level.isClientSide && !this.isTame()) {
    		if (this.isSunBurnTick()) {
    			this.doSitCommand(null);
    		} else if (this.state != FURTameableEntity.State.WANDERING) {
    			this.doFollowCommand(null);
    			this.doWanderCommand(null);
    		}
    	}    	
    	
    	if (!this.level.isClientSide) {
	        if (this.getAge() < -12000) {
	        	if(this.getGrowingStage() != 0) {
	        		this.setGrowingStage(0);
	        	}
	        } else if (this.getAge() < 0) {
	        	if(this.getGrowingStage() != 1) {
	        		this.setGrowingStage(1);
	        		this.playSound(SoundEvents.BEE_POLLINATE, 1.0F, 1.0F);
	        		this.level.broadcastEntityEvent(this, (byte)14);
	        	}       	
	        } else if (this.getAge() == 0) {
	        	if(this.getGrowingStage() != 2) {
	        		this.setGrowingStage(2);
	        		this.playSound(SoundEvents.BEE_POLLINATE, 1.0F, 1.0F);
	        		this.level.broadcastEntityEvent(this, (byte)14);
	        	}       	
	        }
    	}
    	
    	super.tick();
    }
    
    protected ItemStack getFishBucket() {
    	ItemStack stack = new ItemStack(FURItemRegistry.CACTOID_POT);
        CompoundNBT compoundnbt = new CompoundNBT();
        this.addAdditionalSaveData(compoundnbt);
        stack.getOrCreateTag().put("CactoidData", compoundnbt);
        
        if (this.hasCustomName()) {
            stack.setHoverName(this.getCustomName());
        }
        
        return stack;
    }
    
    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
    	ItemStack itemstack = player.getItemInHand(hand);
    	
        if (itemstack.getItem() == Items.FLOWER_POT && this.isAlive()) {
            this.playSound(SoundEvents.BUCKET_FILL_FISH, 1.0F, 1.0F);
            itemstack.shrink(1);
            ItemStack itemstack1 = this.getFishBucket();
            if (!this.level.isClientSide) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayerEntity) player, itemstack1);
            }

            if (itemstack.isEmpty()) {
                player.setItemInHand(hand, itemstack1);
            } else if (!player.inventory.add(itemstack1)) {
                player.drop(itemstack1, false);
            }

            this.remove();            
            return ActionResultType.sidedSuccess(this.level.isClientSide);
        } if (this.isTame() && this.getGrowingStage() == 2) {
    		this.playSound(SoundEvents.ITEM_PICKUP, 1.0F, 1.0F);
    		this.spawnAtLocation(new ItemStack(FURItemRegistry.CACTUS_FRUIT), 0.0F);
    		this.setGrowingStage(0);
    		this.setAge(-24000);
    		
    		return ActionResultType.sidedSuccess(this.level.isClientSide);
    	} else {
    		return super.mobInteract(player, hand);
    	}
    }
    
    @Override
	public void doSitCommand(PlayerEntity playerIn) {
        this.goalSelector.removeGoal(this.watch);
        this.goalSelector.removeGoal(this.look);
        this.setSilent(true);
    	super.doSitCommand(playerIn);
    }
    
    @Override
	public void doFollowCommand(PlayerEntity playerIn) {
        this.goalSelector.addGoal(8, this.watch);
        this.goalSelector.addGoal(8, this.look);
		this.setSilent(false);
        super.doFollowCommand(playerIn);
    }

	@Override
    public boolean doHurtTarget(Entity entityIn) {
        boolean flag = super.doHurtTarget(entityIn);

        return flag;
    }    
	
	/**
	* Called when the entity is attacked.
	*/
    @Override
	public boolean hurt(DamageSource source, float amount) {
        if (!source.isMagic() && !source.isExplosion() && source.getDirectEntity() instanceof LivingEntity) {
            source.getDirectEntity().hurt(DamageSource.thorns(this), 2.0F);
        }
        
    	if(source.isFire())
    		return super.hurt(source, 2.0F * amount);

    	return super.hurt(source, amount);
    }

    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT p_213386_5_) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Cactoid_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Cactoid_Attack.get());
    	this.setHealth(this.getMaxHealth());
    	this.setAge(-24000);
    	return super.finalizeSpawn(p_213386_1_, difficulty, p_213386_3_, livingdata, p_213386_5_);
    }	
    
	@Override
    public float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
        return p_213348_2_.height * 0.5F;
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
	@OnlyIn(Dist.CLIENT)
	@Override
    public void handleEntityEvent(byte id) {
		if (id == 14) {
            this.addParticlesAroundSelf(ParticleTypes.FALLING_NECTAR);
        } else {
            super.handleEntityEvent(id);
        }
    }
	
	@Override
	public boolean canFallInLove() {
		return false;
	}
    
    @Override
    protected SoundEvent getAmbientSound() {
        return FURSoundRegistry.LILSLUDGE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
    	return SoundEvents.WOOL_BREAK;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.CACTYRANT_DEATH;
    }

	@Override
    protected void playStepSound(BlockPos pos, BlockState state) {
	    this.playSound(SoundEvents.CHICKEN_STEP, 0.15F, 1.0F);
	}
    
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
    	super.readAdditionalSaveData(compound);
    	this.setSkin(compound.getInt("Variant"));
    	this.setGrowingStage(compound.getInt("GrowingStage"));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundNBT compound) {
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
       
       if(!this.level.isClientSide() && this.getGrowingStage() == 2) {			
			this.spawnAtLocation(new ItemStack(FURItemRegistry.CACTUS_FRUIT), 0.0F);
       }
    }
    
    @Override
    public boolean shouldDropLoot() {
    	return this.isTame() || !(this.getOwner() instanceof PlayerEntity);
    }
}
