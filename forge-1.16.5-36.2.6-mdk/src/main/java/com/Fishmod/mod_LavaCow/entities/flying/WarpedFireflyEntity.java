package com.Fishmod.mod_LavaCow.entities.flying;

import java.util.EnumSet;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.init.FURBlockRegistry;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
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
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WarpedFireflyEntity extends FlyingMobEntity {
	private static final DataParameter<BlockPos> GLOWING_POS = EntityDataManager.defineId(WarpedFireflyEntity.class, DataSerializers.BLOCK_POS);
	private int glowTimer = 0;
	
	public WarpedFireflyEntity(EntityType<? extends WarpedFireflyEntity> p_i48549_1_, World worldIn) {
		super(p_i48549_1_, worldIn);
		this.moveControl = new FlyingMovementController(this, 20, true);
	}
	
	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, EnigmothEntity.class, 6.0F, 1.0D, 1.2D));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(Items.WARPED_FUNGUS, Items.WARPED_FUNGUS_ON_A_STICK), false));
		this.goalSelector.addGoal(8, new WarpedFireflyEntity.WanderGoal());
	}
	
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.6D)
        		.add(Attributes.MAX_HEALTH, FURConfig.WarpedFirefly_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, 0.0D)
        		.add(Attributes.FLYING_SPEED, 0.6D);
    }
   
    @Override
    protected void defineSynchedData() {
    	super.defineSynchedData();
    	this.getEntityData().define(GLOWING_POS, BlockPos.ZERO);
    }
    
	@Override
	public boolean removeWhenFarAway(double p_213397_1_) {
		return !this.isLeashed();
	}
	
	@Override
    protected boolean shouldDespawnInPeaceful() {
	    return false;
    } 
    
    @Override
    public boolean canBeLeashed(PlayerEntity p_184652_1_) {
        return !this.isLeashed();
	}
    
    @OnlyIn(Dist.CLIENT)
    @Override
    public Vector3d getLeashOffset() {
       return new Vector3d(0.0D, (double)this.getEyeHeight() * 0.5F, (double)(this.getBbWidth() * 0.2F));
    }
    
    @Override
    public void setLeashedTo(Entity p_110162_1_, boolean p_110162_2_) {
    	this.setPersistenceRequired();
    	super.setLeashedTo(p_110162_1_, p_110162_2_);
    }
    
    @Override
    public void tick() {
    	super.tick();

        if (this.glowTimer > -6) {
            --this.glowTimer;
        }
        
    	if(!this.level.isClientSide() && this.glowTimer > -6) {
    		Block blk = this.level.getBlockState(this.blockPosition()).getBlock();
	    	if(blk.equals(Blocks.AIR) || blk.equals(Blocks.CAVE_AIR) || blk.equals(Blocks.VOID_AIR)) {
		    	if(this.tickCount % 5 == 0 && this.level.getBlockState(this.getGlowingPos()).getBlock().equals(FURBlockRegistry.GLOWING_AIR)) {
		    		this.level.setBlock(this.getGlowingPos(), Blocks.AIR.defaultBlockState(), 3);
		    	}
		    	
		    	if(this.tickCount % 5 == 0 && this.glowTimer > 0) {
		    		this.level.setBlock(this.blockPosition(), FURBlockRegistry.GLOWING_AIR.defaultBlockState(), 3);
		    		this.setGlowingPos(this.blockPosition());
		    	}
	    	}	
    	}
    }
    
    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
    	ItemStack itemstack = player.getItemInHand(hand);
    	Item item = itemstack.getItem();
    	ActionResultType actionresulttype = super.mobInteract(player, hand);  	
    	if (this.glowTimer == -6) {   
    		if (item.equals(Items.GLOWSTONE_DUST)) {
    			this.glowTimer = 8 * 60 * 20 + 6;
    			this.setPersistenceRequired();
    			if (player instanceof ServerPlayerEntity) {
    				CriteriaTriggers.SUMMONED_ENTITY.trigger((ServerPlayerEntity)player, this);
    			}
    		} else if (item.equals(Items.WARPED_FUNGUS)) {
    			this.glowTimer = 3 * 60 * 20 + 6;
    			this.setPersistenceRequired();
    			if (player instanceof ServerPlayerEntity) {
    				CriteriaTriggers.SUMMONED_ENTITY.trigger((ServerPlayerEntity)player, this);
    			}
    		} else {
    			return actionresulttype;
    		}
    		
    		if (!player.abilities.instabuild) {
                itemstack.shrink(1);
    		}        
    		
    		this.playSound(SoundEvents.BEE_LOOP, 1.0F, 1.0F);
    		  		
    		return ActionResultType.SUCCESS;
    	} else {
    		return actionresulttype;
    	}
    }
    
    @Override
    protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
    	return p_213348_2_.height * 0.5F;
    }
   
	/**
	* Called when the entity is attacked.
	*/
    @Override
	public boolean hurt(DamageSource source, float amount) {      
    	return super.hurt(source, amount);
	}
    
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData entityLivingData, @Nullable CompoundNBT p_213386_5_) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.WarpedFirefly_Health.get());
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
       this.glowTimer = compound.getInt("glowTimer");
       this.setGlowingPos(new BlockPos(compound.getInt("glowPosX"), compound.getInt("glowPosY"), compound.getInt("glowPosZ")));
	}

	/**
    * (abstract) Protected helper method to write subclass entity data to NBT.
    */
	@Override
	public void addAdditionalSaveData(CompoundNBT compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("glowPosX", this.getGlowingPos().getX());
		compound.putInt("glowPosY", this.getGlowingPos().getY());
		compound.putInt("glowPosZ", this.getGlowingPos().getZ());
		compound.putInt("glowTimer", this.glowTimer);
	}
	
	@Override
	protected boolean makeFlySound() {
		return true;
	}
	
	@Override
	protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return null;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.BEE_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.BEE_DEATH;
	}
	
    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.ARTHROPOD;
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