package com.Fishmod.mod_LavaCow.entities.tameable;

import java.util.Random;
import java.util.UUID;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.entities.IAggressive;
import com.Fishmod.mod_LavaCow.entities.ai.EntityAIDestroyCrops;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;

import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.OwnerHurtByTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WetaEntity extends FURTameableEntity implements IAggressive {
	private int attackTimer = 0;
	private EntityAIDestroyCrops DestroyCrops;
	
	public WetaEntity(EntityType<? extends WetaEntity> p_i48549_1_, World worldIn) {
		super(p_i48549_1_, worldIn);
		this.xpReward = 1;
	}
	
	@Override
    protected void registerGoals()
    {
		this.DestroyCrops = new EntityAIDestroyCrops(this, 1.1D, false);
		
		super.registerGoals();
		this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, Ingredient.of(FURItemRegistry.PLAGUED_PORKCHOP), false));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));    
        this.goalSelector.addGoal(5, this.DestroyCrops);
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
        this.applyEntityAI();
    }
	
    protected void applyEntityAI()
    {
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
    }
    
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.23D)
        		.add(Attributes.FOLLOW_RANGE, 16.0D)
        		.add(Attributes.MAX_HEALTH, FURConfig.Weta_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, FURConfig.Weta_Attack.get());
    }
    
    public static boolean checkWetaSpawnRules(EntityType<? extends WetaEntity> p_223316_0_, IWorld p_223316_1_, SpawnReason p_223316_2_, BlockPos p_223316_3_, Random p_223316_4_) {
        return FURTameableEntity.checkMonsterSpawnRules(p_223316_0_, (IServerWorld) p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_);//SpawnUtil.isAllowedDimension(this.dimension);
    }

    @Override
    public void aiStep() {
    	if (this.attackTimer > 0) {
            --this.attackTimer;
        }
    	
    	super.aiStep();
    }
    
    /**
     * Called when the entity is attacked.
     */
    public boolean hurt(DamageSource source, float amount) {
    	if(source.equals(DamageSource.CACTUS) || source.equals(DamageSource.SWEET_BERRY_BUSH))
    		return false;
    	return super.hurt(source, amount);
    }
    
    protected float getJumpPower() {
        return 1.5F * super.getJumpPower();
    }
    
    @Override
	public void doSitCommand(PlayerEntity playerIn) {
    	this.goalSelector.removeGoal(this.DestroyCrops);
    	super.doSitCommand(playerIn);
    }
    
    @Override
	public void doWanderCommand(PlayerEntity playerIn) {
    	this.DestroyCrops = new EntityAIDestroyCrops(this, 1.1D, true);
    	this.goalSelector.addGoal(5, this.DestroyCrops);
    	super.doWanderCommand(playerIn);
    }
        
    @Override
    protected void reassessTameGoals()
    {
    	if(this.isTame())
    		this.goalSelector.removeGoal(this.DestroyCrops);
    }
    
    public boolean doHurtTarget(Entity entityIn)
    {
        if (super.doHurtTarget(entityIn)) {
        	this.attackTimer = 5;
        	this.level.broadcastEntityEvent(this, (byte)4);
	        
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
       return stack.getItem().equals(FURItemRegistry.PLAGUED_PORKCHOP) || stack.getItem().equals(FURItemRegistry.GREEN_BACON_AND_EGGS);
    }

    @Override
    protected boolean canTameCondition() {
    	return !this.isTame() && !this.isBaby();
    }
    
    @Override
    protected int TameRate(ItemStack stack) {
    	if(stack.getItem().equals(FURItemRegistry.PLAGUED_PORKCHOP))
    		return 3;
    	else if(stack.getItem().equals(FURItemRegistry.GREEN_BACON_AND_EGGS))
    		return 1;
    	else
    		return super.TameRate(stack);
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT p_213386_5_) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Weta_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Weta_Attack.get());
    	this.setHealth(this.getMaxHealth());
    	
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
	
    /**
     * Handler for {@link World#setEntityState}
     */
	@OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id)
    {
    	if (id == 4) 
    	{
            this.attackTimer = 5;
        }
        else
        {
            super.handleEntityEvent(id);
        }
    }
    
	@Override
    public float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
        return 0.1F;
    }
    
	@Override
    protected SoundEvent getAmbientSound()
    {
        return FURSoundRegistry.WETA_AMBIENT;
    }
	
	@Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return FURSoundRegistry.WETA_HURT;
    }

	@Override
    protected SoundEvent getDeathSound()
    {
        return FURSoundRegistry.WETA_DEATH;
    }

	@Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    	this.playSound(SoundEvents.SPIDER_STEP, 0.15F, 1.0F);
	}
    
	public WetaEntity getBreedOffspring(ServerWorld worldIn, AgeableEntity ageable) {
		WetaEntity entity = FUREntityRegistry.WETA.create(worldIn);
		UUID uuid = this.getOwnerUUID();
		if (uuid != null) {
			entity.setOwnerUUID(uuid);
			entity.setTame(true);
			entity.setHealth(this.getMaxHealth());
		}

		return entity;
	}
}
