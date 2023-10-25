package com.Fishmod.mod_LavaCow.entities;

import java.util.Random;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.entities.ai.AquaticMovementController;
import com.Fishmod.mod_LavaCow.entities.ai.SemiAquaticNavigator;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class PinguEntity extends MonsterEntity implements ISemiAquatic {
	private boolean HPbelow30 = false;
	private boolean HPbelow50 = false;
	private int SwimTimer;
	private boolean isAquaticMove;
	
	public PinguEntity(EntityType<? extends PinguEntity> p_i48549_1_, World worldIn) {
        super(p_i48549_1_, worldIn);
        this.setPathfindingMalus(PathNodeType.WATER, 0.0F);
        this.setPathfindingMalus(PathNodeType.WATER_BORDER, 0.0F);
        this.SwimTimer = 0;
        this.isAquaticMove = false;
        this.navigation = new SemiAquaticNavigator(this, worldIn);
    }
	
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 2.0D, false));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
    	this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.16D)
        		.add(Attributes.MAX_HEALTH, FURConfig.Pingu_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, FURConfig.Pingu_Attack.get())
        		.add(Attributes.ARMOR, 20.0D);
    }
    
    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */    
    public static boolean checkPinguSpawnRules(EntityType<PinguEntity> p_223320_0_, IWorld p_223320_1_, SpawnReason p_223320_2_, BlockPos p_223320_3_, Random p_223320_4_) {
    	return MonsterEntity.checkMonsterSpawnRules(p_223320_0_, (IServerWorld) p_223320_1_, p_223320_2_, p_223320_3_, p_223320_4_);//SpawnUtil.isAllowedDimension(this.dimension);
	}
    
    @Override
    public boolean isPushedByFluid() {
        return false;
	}

    @Override
	public boolean canBreatheUnderwater() {
        return true;
	}
	
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void tick() {
    	if (this.isInWaterOrBubble()) {
    		SwimTimer++;
    		if(!this.isAquaticMove) {
    			this.moveControl = new AquaticMovementController(this);
    			this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(1.6F);
    			this.isAquaticMove = true;
    		}
    	} else {
    		SwimTimer--;
    		if(this.isAquaticMove) {
    			this.moveControl = new MovementController(this);
    			this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.16F);
    			this.isAquaticMove = false;
    		}
    	}
    	
    	if (!FURConfig.SunScreen_Mode.get() && !this.level.isClientSide() && this.tickCount % 20 == 0 && !this.isInWaterRainOrBubble()) {
            int i = MathHelper.floor(this.getX());
            int j = MathHelper.floor(this.getY());
            int k = MathHelper.floor(this.getZ());

            if (this.level.getBiome(new BlockPos(i, 0, k)).getTemperature(new BlockPos(i, j, k)) > 1.0F) {
                this.hurt(DamageSource.ON_FIRE, 1.0F);
            }
        }
       
        if(this.getHealth() < this.getMaxHealth() * 0.3F && !this.HPbelow30) {
    		if (!this.level.isClientSide() && !this.isOnFire()) {
    			this.playSound(SoundEvents.GLASS_BREAK, 1.0F, 1.0F);
    			int chance = this.getRandom().nextInt(2) + 1;
    			for (int amount = 0; amount < chance; ++amount)
    				this.spawnAtLocation(new ItemStack(FURItemRegistry.SHATTERED_ICE), 0.0F);
    		}
        	this.HPbelow30 = true;
        }
        else if(this.getHealth() < this.getMaxHealth() * 0.5F && !this.HPbelow50) {
    		if (!this.level.isClientSide() && !this.isOnFire()) {
    			this.playSound(SoundEvents.GLASS_BREAK, 1.0F, 1.0F);
    			int chance = this.getRandom().nextInt(2) + 1;
    			for (int amount = 0; amount < chance; ++amount)
    				this.spawnAtLocation(new ItemStack(FURItemRegistry.SHATTERED_ICE), 0.0F);
    		}
        	this.HPbelow50 = true;
        }
        else if(this.getHealth() >= this.getMaxHealth() * 0.5F && this.HPbelow30 && this.HPbelow50) {
        	this.HPbelow30 = false;
        	this.HPbelow50 = false;
        }
        
        if(this.lastHurtByPlayer == null && this.isInWaterRainOrBubble() && this.getRandom().nextInt(50) < 2)
        	this.heal(0.5F);
        
        super.tick();
    }

	@Override
    public boolean doHurtTarget(Entity p_70652_1_) {
        boolean flag = super.doHurtTarget(p_70652_1_);
        if (flag) {
           float f = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
           if (this.getMainHandItem().isEmpty() && this.isOnFire() && this.random.nextFloat() < f * 0.3F) {
              p_70652_1_.setSecondsOnFire(2 * (int)f);
           }
        }

        return flag;
	}
	
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT p_213386_5_) {
        livingdata = super.finalizeSpawn(p_213386_1_, difficulty, p_213386_3_, livingdata, p_213386_5_);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Pingu_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Pingu_Attack.get());
    	this.setHealth(this.getMaxHealth());
    	
        int i = p_213386_1_.getEntitiesOfClass(PinguEntity.class, this.getBoundingBox().inflate(16.0D)).size();        
    	if (i < 4 + this.getRandom().nextInt(4) && (p_213386_3_.equals(SpawnReason.CHUNK_GENERATION) || p_213386_3_.equals(SpawnReason.NATURAL)) && !this.level.isClientSide) {
    		PinguEntity crowpet = FUREntityRegistry.PINGU.create(this.level);
    		crowpet.moveTo(this.getX() - 2.5F + this.getRandom().nextFloat() * 5.0F, this.getY(), this.getZ() - 2.5F + this.getRandom().nextFloat() * 5.0F, this.yRot, this.xRot);
    		this.level.addFreshEntity(crowpet);
    	}
        
        return livingdata;
    }

	@Override
    public float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
        return p_213348_2_.height * 0.8F;
    }
	
	@Override
    public int getAmbientSoundInterval() {
        return 300;
    }
    
    @Override
    protected SoundEvent getAmbientSound()
    {
        return this.HPbelow50 ? null : FURSoundRegistry.PINGU_AMBIENT;
    }

    @Override
	protected SoundEvent getSwimSound() {
        return SoundEvents.TURTLE_SWIM;
	}

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
    	return this.HPbelow50 ? null : FURSoundRegistry.PINGU_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return FURSoundRegistry.PINGU_DEATH;
    }
    
	@Override
    protected void playStepSound(BlockPos pos, BlockState state) {
		if(!this.isAggressive())
			this.playSound(SoundEvents.CHICKEN_STEP, 0.15F, 1.0F);
	}

    /**
     * Get this Entity's CreatureAttribute
     */
    @Override
    public CreatureAttribute getMobType()
    {
        return CreatureAttribute.UNDEAD;
    }

	@Override
	public boolean ShouldSwin() {
		return this.HPbelow30 || (this.SwimTimer < -2000 && !this.ShouldLand() && this.getTarget() == null);
	}

	@Override
	public boolean ShouldLand() {
		return this.SwimTimer > 600 && !this.ShouldSwin() && (this.getTarget() != null && !this.getTarget().isInWater());
	}
}
