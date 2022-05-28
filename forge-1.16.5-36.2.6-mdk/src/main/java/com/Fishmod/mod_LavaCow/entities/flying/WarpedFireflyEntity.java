package com.Fishmod.mod_LavaCow.entities.flying;

import java.util.EnumSet;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.init.FURBlockRegistry;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class WarpedFireflyEntity extends FlyingMobEntity {
	private static final DataParameter<BlockPos> GLOWING_POS = EntityDataManager.defineId(WarpedFireflyEntity.class, DataSerializers.BLOCK_POS);
	
	public WarpedFireflyEntity(EntityType<? extends WarpedFireflyEntity> p_i48549_1_, World worldIn) {
		super(p_i48549_1_, worldIn);
		this.moveControl = new FlyingMovementController(this, 20, true);
	}
	
	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(Items.WARPED_FUNGUS, Items.WARPED_FUNGUS_ON_A_STICK), false));
		this.goalSelector.addGoal(8, new WarpedFireflyEntity.WanderGoal());
	}
	
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.6D)
        		.add(Attributes.MAX_HEALTH, FURConfig.GhostRay_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, 6.0D)
        		.add(Attributes.FLYING_SPEED, 0.6D);
    }
   
    @Override
    protected void defineSynchedData() {
    	super.defineSynchedData();
    	this.getEntityData().define(GLOWING_POS, BlockPos.ZERO);
    }	
    
    @Override
    public void tick() {
    	super.tick();
    	
    	if(!this.level.isClientSide() && this.tickCount % 5 == 0) {
	    	if(this.level.getBlockState(this.blockPosition()).getBlock().equals(Blocks.AIR)) {
		    	if(this.level.getBlockState(this.getGlowingPos()).getBlock().equals(FURBlockRegistry.GLOWING_AIR)) {
		    		this.level.setBlock(this.getGlowingPos(), Blocks.AIR.defaultBlockState(), 3);
		    	}
	    		this.level.setBlock(this.blockPosition(), FURBlockRegistry.GLOWING_AIR.defaultBlockState(), 3);
	    		this.setGlowingPos(this.blockPosition());
	    	}	   
    	}
    }
    
    @Override
    protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
    	return p_213348_2_.height * 0.7F;
    }
   
	/**
	* Called when the entity is attacked.
	*/
    @Override
	public boolean hurt(DamageSource source, float amount) {      
    	return super.hurt(source, amount);
	}
    
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData entityLivingData, @Nullable CompoundNBT p_213386_5_) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.GhostRay_Health.get());
    	this.setHealth(this.getMaxHealth());
    	
    	return super.finalizeSpawn(worldIn, difficulty, p_213386_3_, entityLivingData, p_213386_5_);
    }
    
    public BlockPos getGlowingPos() {
    	return this.getEntityData().get(GLOWING_POS);
    }

    public void setGlowingPos(BlockPos pos) {
    	this.getEntityData().set(GLOWING_POS, pos);
    }

	/**
	* (abstract) Protected helper method to read subclass entity data from NBT.
	*/
	@Override
	public void readAdditionalSaveData(CompoundNBT compound) {
       super.readAdditionalSaveData(compound);
	}

	/**
    * (abstract) Protected helper method to write subclass entity data to NBT.
    */
	@Override
	public void addAdditionalSaveData(CompoundNBT compound) {
		super.addAdditionalSaveData(compound);
	}
	
	public SoundCategory getSoundCategory() {
		return SoundCategory.NEUTRAL;
	}

	protected SoundEvent getAmbientSound() {
		return null;
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.BEE_HURT;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.BEE_DEATH;
	}
	
	class WanderGoal extends Goal {
		WanderGoal() {
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		public boolean canUse() {
			return WarpedFireflyEntity.this.navigation.isDone() && WarpedFireflyEntity.this.random.nextInt(10) == 0;
		}

		public boolean canContinueToUse() {
			return WarpedFireflyEntity.this.navigation.isInProgress();
		}

		public void start() {
			Vector3d vector3d = this.findPos();
			if (vector3d != null) {
				WarpedFireflyEntity.this.navigation.moveTo(WarpedFireflyEntity.this.navigation.createPath(new BlockPos(vector3d), 1), 1.0D);
			}

		}

		@Nullable
		private Vector3d findPos() {
			Vector3d vector3d = WarpedFireflyEntity.this.getViewVector(0.0F);
			Vector3d vector3d2 = RandomPositionGenerator.getAboveLandPos(WarpedFireflyEntity.this, 8, 7, vector3d, ((float)Math.PI / 2F), 2, 1);
			return vector3d2 != null ? vector3d2 : RandomPositionGenerator.getAirPos(WarpedFireflyEntity.this, 8, 4, -2, vector3d, (double)((float)Math.PI / 2F));
		}
	}
}