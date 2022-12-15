package com.Fishmod.mod_LavaCow.entities.tameable;

import java.util.EnumSet;
import java.util.Random;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.ai.FlyerFollowOwnerGoal;
import com.Fishmod.mod_LavaCow.entities.ai.WispSwellGoal;
import com.Fishmod.mod_LavaCow.entities.flying.WarpedFireflyEntity;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;
import com.Fishmod.mod_LavaCow.init.FURParticleRegistry;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NonTamedTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtByTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.passive.IFlyingAnimal;
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
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.Explosion;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WispEntity extends FURTameableEntity implements IFlyingAnimal {
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.defineId(WispEntity.class, DataSerializers.INT);
	private static final DataParameter<Integer> DATA_SWELL_DIR = EntityDataManager.defineId(WispEntity.class, DataSerializers.INT);
	private int oldSwell;
	private int swell;
	private int maxSwell = 30;
	   
	public WispEntity(EntityType<? extends WispEntity> p_i48549_1_, World worldIn) {
		super(p_i48549_1_, worldIn);
	      this.moveControl = new FlyingMovementController(this, 20, true);
	}
	
    /**
     * Tries to move the entity towards the specified location.
     */
	@Override
    public void move(MoverType type, Vector3d p_213315_2_) {
        super.move(type, p_213315_2_);
        this.checkInsideBlocks();
    }
	
    @Override
    protected boolean isCommandable() {
    	return false;
    }
	
    @Override
    protected void registerGoals() {
    	super.registerGoals();        
        this.goalSelector.addGoal(0, new SwimGoal(this));
    	this.goalSelector.addGoal(2, new WispSwellGoal(this));
    	this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, WarpedFireflyEntity.class, 6.0F, 1.0D, 1.2D));
		this.goalSelector.addGoal(3, new WispEntity.AIChargeAttack());
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));  
        this.goalSelector.addGoal(8, new WispEntity.AIMoveRandom());
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
    	this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(4, new NonTamedTargetGoal<>(this, PlayerEntity.class, false, (p_213440_0_) -> {
            return true;
        }));
    }
    
    @Override
    protected Goal wanderGoal() {
    	return new WispEntity.AIMoveRandom();
    }
    
    @Override
    protected Goal followGoal() {
    	return new FlyerFollowOwnerGoal(this, 1.0D, 10.0F, 4.0F, true);
    }
    
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.3D)
        		.add(Attributes.FLYING_SPEED, 0.6F)
        		.add(Attributes.FOLLOW_RANGE, 16.0D)
        		.add(Attributes.MAX_HEALTH, FURConfig.Wisp_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, 1.0D)
        		.add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }
    
    public static boolean checkWispSpawnRules(EntityType<? extends WispEntity> p_223316_0_, IWorld p_223316_1_, SpawnReason p_223316_2_, BlockPos p_223316_3_, Random p_223316_4_) {
        return FURTameableEntity.checkMonsterSpawnRules(p_223316_0_, (IServerWorld) p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_);//SpawnUtil.isAllowedDimension(this.dimension);
    }
    
    @Override
    protected void defineSynchedData() {
    	super.defineSynchedData();
    	this.getEntityData().define(DATA_SWELL_DIR, -1);
    	this.getEntityData().define(SKIN_TYPE, Integer.valueOf(this.getRandom().nextInt(3)));
    }
    
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void aiStep() {
        super.aiStep();
        
        if(this.tickCount % 2 == 0 && this.ParticleType() != null) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            this.level.addParticle(this.ParticleType(), this.getRandomX(0.5D), this.getRandomY() + (this.getBbHeight() * 0.5D), this.getRandomZ(0.5D), d0, d1, d2);
        }     	
    }
	
	@Override
    public void tick() {
        if (this.isAlive() && (!this.isTame() || (this.isTame() && FURConfig.Wisp_Tamed_Explosion.get()))) {
           this.oldSwell = this.swell;

           int i = this.getSwellDir();
           if (i > 0 && this.swell == 0) {
              this.playSound(SoundEvents.CREEPER_PRIMED, 1.0F, 0.5F);
           }

           this.swell += i;
           if (this.swell < 0) {
              this.swell = 0;
           }

           if (this.swell >= this.maxSwell) {
              this.swell = this.maxSwell;
              this.explodeWisp();
           }
        }
        
        if (this.isGastly() && this.getSkin() != 3) {
        	this.setSkin(3);
        }      

    	this.noPhysics = true;
    	super.tick();
        this.noPhysics = false;
        this.setNoGravity(true);
	}
    
    private void explodeWisp() {
        if (!this.level.isClientSide) {
           this.dead = true;
           this.level.explode(this, this.getX(), this.getY(), this.getZ(), FURConfig.Wisp_ExplosionPower.get().floatValue(), net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this), Explosion.Mode.NONE);
           if(this.isTame())
        	   this.spawnAtLocation(this.getAshes(), 1); 
           this.remove();
        }
	}
    
    @Override
    public boolean causeFallDamage(float p_225503_1_, float p_225503_2_) {
        return false;
	}

    @Override
	protected void checkFallDamage(double p_184231_1_, boolean p_184231_3_, BlockState p_184231_4_, BlockPos p_184231_5_) {
	}
    
	/**
	* Called when the entity is attacked.
	*/
    @Override
	public boolean hurt(DamageSource source, float amount) {
    	if(source.isExplosion())
    		return false;
    				
		return super.hurt(source, amount);
    }
    
    protected ItemStack getAshes() {
    	ItemStack stack = new ItemStack(FURItemRegistry.WISP_ASHES);
        CompoundNBT compoundnbt = new CompoundNBT();
        this.addAdditionalSaveData(compoundnbt);
        stack.getOrCreateTag().put("WispData", compoundnbt);
        
        if (this.hasCustomName()) {
            stack.setHoverName(this.getCustomName());
        }
        
        return stack;
    }
    
    protected ItemStack getFishBucket() {
    	ItemStack stack = new ItemStack(FURItemRegistry.WISP_IN_A_BOTTLE);
        CompoundNBT compoundnbt = new CompoundNBT();
        this.addAdditionalSaveData(compoundnbt);
        stack.getOrCreateTag().put("WispData", compoundnbt);
        
        if (this.hasCustomName()) {
            stack.setHoverName(this.getCustomName());
        }
        
        return stack;
    }
    
    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
    	ItemStack itemstack = player.getItemInHand(hand);   	
        
        if (itemstack.getItem() == Items.GLASS_BOTTLE && this.isAlive()) {
        	this.tame(player);
            this.playSound(SoundEvents.BOTTLE_FILL, 1.0F, 1.0F);
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
        }
        
        ActionResultType actionResultType = itemstack.interactLivingEntity(player, this, hand);
        
        if (actionResultType.consumesAction()) {
        	return actionResultType;
        }
        
        return super.mobInteract(player, hand);
    }
       
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT p_213386_5_) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Wisp_Health.get());
    	this.setHealth(this.getMaxHealth());

    	if (SpawnUtil.getRegistryKey(worldIn.getBiome(this.blockPosition())).equals(Biomes.NETHER_WASTES)) {
		   this.setSkin(1);
    	} else if (SpawnUtil.getRegistryKey(worldIn.getBiome(this.blockPosition())).equals(Biomes.SOUL_SAND_VALLEY)) {
 		   this.setSkin(0);
     	} else if (SpawnUtil.getRegistryKey(worldIn.getBiome(this.blockPosition())).equals(Biomes.BASALT_DELTAS)) {
  		   this.setSkin(2);
      	}
    	
    	this.setDeltaMovement(this.getDeltaMovement().add(0.0D, 0.5D, 0.0D));
    	
    	return super.finalizeSpawn(worldIn, difficulty, p_213386_3_, livingdata, p_213386_5_);
    }
    
    protected PathNavigator createNavigation(World p_175447_1_) {
        FlyingPathNavigator flyingpathnavigator = new FlyingPathNavigator(this, p_175447_1_) {
           public boolean isStableDestination(BlockPos p_188555_1_) {
              return !this.level.getBlockState(p_188555_1_.below()).getMaterial().equals(Material.AIR);
           }
        };
        flyingpathnavigator.setCanOpenDoors(false);
        flyingpathnavigator.setCanFloat(true);
        flyingpathnavigator.setCanPassDoors(true);
        return flyingpathnavigator;
	}
    
    @Nullable
    protected IParticleData ParticleType() { 
    	switch(this.getSkin()) {
			case 1:
				return ParticleTypes.FLAME;
			case 2:
				return ParticleTypes.POOF;
			case 3:
				return FURParticleRegistry.GHOST_FLAME;
			case 0:
			default:
				return ParticleTypes.SOUL_FIRE_FLAME;			
		}
    }
    
    @OnlyIn(Dist.CLIENT)
    public float getSwelling(float p_70831_1_) {
       return MathHelper.lerp(p_70831_1_, (float)this.oldSwell, (float)this.swell) / (float)(this.maxSwell - 2);
    }

    public int getSwellDir() {
       return this.entityData.get(DATA_SWELL_DIR);
    }

    public void setSwellDir(int p_70829_1_) {
       this.entityData.set(DATA_SWELL_DIR, p_70829_1_);
    }
    
    public int getSkin() {
        return this.getEntityData().get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
        this.getEntityData().set(SKIN_TYPE, Integer.valueOf(skinType));
    }
    
    @Override
    protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
        return p_213348_2_.height * 0.5F;
    }
    
    private boolean isGastly() {
    	String s = TextFormatting.stripFormatting(this.getName().getString());
        return s != null && s.toLowerCase().contains("gastly");
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return null;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.FURNACE_FIRE_CRACKLE;
    }
    
    protected SoundEvent getSpellSound() {
        return null;
    }
    
	@Override
	protected boolean isMovementNoisy() {
		return false;
	}
	
	@Override
    protected void playStepSound(BlockPos pos, BlockState state) {
	} 
    
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Fuse", 99)) {
            this.maxSwell = compound.getShort("Fuse");
        }
        this.setSkin(compound.getInt("Variant"));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putShort("Fuse", (short)this.maxSwell);
        compound.putInt("Variant", getSkin());
    }
	
    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.UNDEAD;
    }
    
    class AIChargeAttack extends Goal {
    	
        public AIChargeAttack() {
        	this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean canUse() {
            if (WispEntity.this.getTarget() != null/* && !WispEntity.this.getMoveControl().hasWanted() && WispEntity.this.getRandom().nextInt(7) == 0*/) {
                return WispEntity.this.distanceToSqr(WispEntity.this.getTarget()) > 4.0D;
            } else {
                return false;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return WispEntity.this.getMoveControl().hasWanted() && WispEntity.this.getTarget() != null && WispEntity.this.getTarget().isAlive();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            LivingEntity LivingEntity = WispEntity.this.getTarget();
            Vector3d vec3d = LivingEntity.getEyePosition(1.0F);
            WispEntity.this.moveControl.setWantedPosition(vec3d.x, vec3d.y, vec3d.z, 1.2D);
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            LivingEntity livingentity = WispEntity.this.getTarget();
            if (WispEntity.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
               WispEntity.this.doHurtTarget(livingentity);
            } else {
               double d0 = WispEntity.this.distanceToSqr(livingentity);
               if (d0 < 9.0D) {
                  Vector3d vector3d = livingentity.getEyePosition(1.0F);
                  WispEntity.this.moveControl.setWantedPosition(vector3d.x, vector3d.y, vector3d.z, 1.0D);
               }
            }
        }
    }
    
    class AIMoveRandom extends Goal {
    	AIMoveRandom() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    	}

    	public boolean canUse() {
    		return WispEntity.this.navigation.isDone() && WispEntity.this.random.nextInt(10) == 0;
    	}

    	public boolean canContinueToUse() {
            return WispEntity.this.navigation.isInProgress();
    	}

    	public void start() {
            Vector3d vector3d = this.findPos();
            if (vector3d != null) {
               WispEntity.this.navigation.moveTo(WispEntity.this.navigation.createPath(new BlockPos(vector3d), 1), 1.0D);
            }

    	}

    	@Nullable
    	private Vector3d findPos() {
            Vector3d vector3d = WispEntity.this.getViewVector(0.0F);
            Vector3d vector3d2 = RandomPositionGenerator.getAboveLandPos(WispEntity.this, 8, 7, vector3d, ((float)Math.PI / 2F), 2, 1);
            return vector3d2 != null ? vector3d2 : RandomPositionGenerator.getAirPos(WispEntity.this, 8, 4, -2, vector3d, (double)((float)Math.PI / 2F));
    	}
    }
    
    /**
     * Called when the mob's health reaches 0.
     */
	@Override
    public void dropAllDeathLoot(DamageSource cause) {
		if(this.isTame())
			this.spawnAtLocation(this.getAshes(), 1); 
		
		super.dropAllDeathLoot(cause);
	}
}
