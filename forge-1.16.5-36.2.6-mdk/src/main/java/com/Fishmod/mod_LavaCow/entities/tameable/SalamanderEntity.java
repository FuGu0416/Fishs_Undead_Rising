package com.Fishmod.mod_LavaCow.entities.tameable;

import java.util.Random;
import java.util.UUID;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.IAggressive;
import com.Fishmod.mod_LavaCow.entities.ai.EntityFishAIAttackRange;
import com.Fishmod.mod_LavaCow.entities.projectiles.WarSmallFireballEntity;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;
import com.Fishmod.mod_LavaCow.init.FURKeybindRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;
import com.Fishmod.mod_LavaCow.message.MessageMountSpecial;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IEquipable;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NonTamedTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SalamanderEntity extends FURTameableEntity implements IAggressive, IEquipable {
	private static final DataParameter<Integer> SKIN_TYPE =  EntityDataManager.defineId(SalamanderEntity.class, DataSerializers.INT);
	private static final DataParameter<Integer> ATTACK_TIMER = EntityDataManager.defineId(SalamanderEntity.class, DataSerializers.INT);
	private static final DataParameter<Boolean> SADDLED = EntityDataManager.defineId(SalamanderEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> GROWING_STAGE = EntityDataManager.defineId(SalamanderEntity.class, DataSerializers.INT);
	
	private EntityFishAIAttackRange<WarSmallFireballEntity> range_atk;
	private AvoidEntityGoal<PlayerEntity> avoid_entity;
	private int barrage_CD;
	
	public SalamanderEntity(EntityType<? extends SalamanderEntity> p_i48549_1_, World worldIn) {
        super(p_i48549_1_, worldIn);
        this.setPathfindingMalus(PathNodeType.WATER, -1.0F);
        this.setPathfindingMalus(PathNodeType.LAVA, 8.0F);
        this.setPathfindingMalus(PathNodeType.DANGER_FIRE, 0.0F);
        this.setPathfindingMalus(PathNodeType.DAMAGE_FIRE, 0.0F);
        this.xpReward = 20;
        this.barrage_CD = 0;
    }

    @Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SKIN_TYPE, Integer.valueOf(0));
		this.entityData.define(SADDLED, Boolean.valueOf(false));
		this.entityData.define(ATTACK_TIMER, Integer.valueOf(0));
		this.entityData.define(GROWING_STAGE, Integer.valueOf(0));
	}
	
    @Override
    protected void registerGoals() {   	
    	super.registerGoals();
    	if(this.isNymph())
    		this.range_atk = new EntityFishAIAttackRange<WarSmallFireballEntity>(this, FUREntityRegistry.WAR_SMALL_FIREBALL, 8, 5, 2.5D, 1.0D, 2.5D);
    	else
    		this.range_atk = new EntityFishAIAttackRange<WarSmallFireballEntity>(this, FUREntityRegistry.WAR_SMALL_FIREBALL, 1, 5, 1.0D, 0.1D, 1.0D);
    	
    	this.goalSelector.addGoal(0, new SwimGoal(this));
    	this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
    	this.goalSelector.addGoal(4, this.range_atk);
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(4, new NonTamedTargetGoal<>(this, PlayerEntity.class, false, (p_213440_0_) -> {
            return true;
        }));
    }
    
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.23D)
        		.add(Attributes.MAX_HEALTH, FURConfig.Salamander_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, FURConfig.Salamander_Attack.get())
        		.add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }

    /**
     * Gets how bright this entity is.
     */
	public float getBrightness() {
		return 1.0F;
	}
	
    public static boolean checkSalamanderSpawnRules(EntityType<? extends SalamanderEntity> p_223316_0_, IWorld p_223316_1_, SpawnReason p_223316_2_, BlockPos p_223316_3_, Random p_223316_4_) {
    	return FURTameableEntity.checkMonsterSpawnRules(p_223316_0_, (IServerWorld) p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_);        
    }
    
    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    @Override
    public int getMaxSpawnClusterSize() {
       return 4;
    }
    
    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
    	ItemStack itemstack = player.getItemInHand(hand);   	
        boolean flag = this.isFood(itemstack);
        if (!flag && this.isOwnedBy(player) && this.isSaddled() && !this.isVehicle() && !player.isSecondaryUseActive()) {
        	if(player.isCrouching()) {
    			this.setSaddled(false);
    			if(!this.level.isClientSide)this.spawnAtLocation(Items.SADDLE, 1);
    		} else if (!player.isPassenger()) {
        	   player.startRiding(this);
    		}

           return ActionResultType.sidedSuccess(this.level.isClientSide);
        } else {
        	ActionResultType actionresulttype = super.mobInteract(player, hand);
        	if (!actionresulttype.consumesAction()) {
        		return itemstack.getItem() == Items.SADDLE ? itemstack.interactLivingEntity(player, this, hand) : ActionResultType.PASS;
        	} else {
        		return actionresulttype;
        	}
        }
    }
    
    @Override
    protected boolean canTameCondition() {
    	return this.isBaby() && super.canTameCondition();
    }
    
    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    @Override
    public boolean isFood(ItemStack stack) {
    	return stack.getItem().equals(FURItemRegistry.IMP_HORN);
    }
    
    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    @Override
    public double getPassengersRidingOffset() {
        return (double)this.getBbHeight() * 1.3D;
    }
    
    @Override
    public void positionRider(Entity passenger) {
        super.positionRider(passenger);
        if (passenger instanceof MobEntity) {
        	this.yBodyRot = ((MobEntity)passenger).yBodyRot;
            this.yRot = passenger.yRot;
        }
    }
    
    @Nullable
    public Entity getControllingPassenger() {
        for (Entity passenger : this.getPassengers()) {
            if (passenger instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) passenger;
                if (this.isTame() && this.getOwnerUUID() != null && this.getOwnerUUID().equals(player.getUUID())) {
                    return player;
                }
            }
        }
        return null;
    }
    
    public boolean canBeControlledByRider() {
    	return this.getControllingPassenger() instanceof PlayerEntity;
    }
    
    public boolean isRidingPlayer(PlayerEntity player) {
        return this.getControllingPassenger() != null && this.getControllingPassenger() instanceof PlayerEntity && this.getControllingPassenger().getUUID().equals(player.getUUID());
    }
    
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void tick() {
        super.tick();
        
    	if(this.getAttackTimer() > 0)
    		this.setAttackTimer(this.getAttackTimer() - 1);
    	
        if(this.barrage_CD > 0)
        	this.barrage_CD--;
        
    	if (!this.level.isClientSide) {   		
    		if (this.isTame() && this.getRandom().nextInt(this.isInLava() ? 45 : 900) == 0 && this.deathTime == 0) {
                this.heal(1.0F);
            }

	    	if(this.getAge() < -16000) {
	    		if(this.getGrowingStage() != 0)
	    			this.setGrowingStage(0);
	    	} else if(this.getAge() < -8000) {
	    		if(this.getGrowingStage() != 1) {
		    		this.setGrowingStage(1);
	    		}
	    	} else if(this.getAge() < 0) {
	    		if(this.getGrowingStage() != 2) {
		    		this.setGrowingStage(2);		    	
	    		}
	    	} else {	    		
	    		if(this.getGrowingStage() != 3) {
	    			this.setGrowingStage(3);	    		
	    		}
	    	}
    	}	
    }
    
    @Override
    public void aiStep() {
    	super.aiStep();
    		
    	if (this.level.isClientSide) {
            this.ClientControl();
        }
    }
    
    @OnlyIn(Dist.CLIENT)
    private void ClientControl() {
    	Minecraft game = Minecraft.getInstance();
		if (this.barrage_CD == 0 && FURKeybindRegistry.MOUNT_SPECIAL.isDown() && this.isRidingPlayer(game.player)) {
			this.barrage_CD = 80;
			mod_LavaCow.NETWORK.sendToServer(new MessageMountSpecial(this.getId(), this.getX(), this.getY(), this.getZ()));
		}	
    }
    
    public void onSyncedDataUpdated(DataParameter<?> p_184206_1_) {
        if (GROWING_STAGE.equals(p_184206_1_)) {
           this.refreshDimensions();
        }

        super.onSyncedDataUpdated(p_184206_1_);
	}
    
    /**
     * Set or remove the saddle of the pig.
     */
    public void setSaddled(boolean saddled) {
    	this.getEntityData().set(SADDLED, Boolean.valueOf(saddled));
    }
    
    /**
     * Growing Stage: Nymph -> Child-> Adult
     */
    public int getGrowingStage() {
       return this.getEntityData().get(GROWING_STAGE).intValue();
    }
    
    public void setGrowingStage(int i) {
        this.getEntityData().set(GROWING_STAGE, i);
        this.refreshDimensions();
        
        switch(i) {
	        case 0:
		    	this.xpReward = 5;
		    	
		    	this.avoid_entity = new AvoidEntityGoal<>(this, PlayerEntity.class, 4.0F, 0.8D, 1.6D);
		    	this.goalSelector.addGoal(3, this.avoid_entity);
		    	this.goalSelector.removeGoal(this.range_atk);
		    	this.range_atk = new EntityFishAIAttackRange<WarSmallFireballEntity>(this, FUREntityRegistry.WAR_SMALL_FIREBALL, 1, 5, 1.0D, 0.1D, 1.0D);
		    	this.goalSelector.addGoal(4, this.range_atk);
		    	
		    	this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Salamander_Health.get() * 0.25D);
		        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.28D);
		        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Salamander_Attack.get() * 0.5D);
		        
		        if (this.getHealth() > this.getMaxHealth())
		        	this.setHealth(this.getMaxHealth());
		        
		        if(this.isSaddleable()) {
		        	this.setSaddled(false);
		        	
		            if (!this.level.isClientSide) {
		            	this.spawnAtLocation(Items.SADDLE, 1);
		            }
		        }
	        	break;
	        case 1:   		
	    		this.xpReward = 10;
	    		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Salamander_Health.get() * 0.40D);
	    		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.25D);
	    		this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Salamander_Attack.get() * 0.65D);
	    		
	    		this.heal(this.getHealth() * (0.15F / 0.25F));
	        	break;
	        case 2:
	    		this.xpReward = 15;
	    		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Salamander_Health.get() * 0.60D);
	    		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.25D);
	    		this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Salamander_Attack.get() * 0.75D);
	    		
	    		this.heal(this.getHealth() * 0.5F);
        		break;
        	default:
    			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Salamander_Health.get());
    			this.heal(this.getHealth() * 2.0F / 3.0F);
    			
    	    	this.xpReward = 20;
    	    	
    	    	this.goalSelector.removeGoal(this.avoid_entity);
    	    	this.goalSelector.removeGoal(this.range_atk);
    	    	this.range_atk = new EntityFishAIAttackRange<WarSmallFireballEntity>(this, FUREntityRegistry.WAR_SMALL_FIREBALL, 8, 5, 2.5D, 1.0D, 2.5D);
    	    	this.goalSelector.addGoal(4, this.range_atk);
    	    	
    	    	this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Salamander_Health.get());
    	        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.23D);
    	        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Salamander_Attack.get());	
        		break;
        }
	}
    
    public boolean isNymph() {
    	return this.getGrowingStage() == 0;
    }
    
    public void setTamed(boolean tamed) {
    	if(tamed) {
    		this.goalSelector.removeGoal(this.avoid_entity);
    	}
    	
    	super.setTame(tamed);
    }
    
    @Override
	public SalamanderEntity getBreedOffspring(ServerWorld worldIn, AgeableEntity ageable) {
    	SalamanderEntity entity = FUREntityRegistry.SALAMANDER.create(worldIn);
		UUID uuid = this.getOwnerUUID();
		if (uuid != null) {
			entity.setOwnerUUID(uuid);
			entity.setTame(true);
			entity.setHealth(this.getMaxHealth());
		}

		return entity;
	}
    
    /**
     * returns true if all the conditions for steering the entity are met. For pigs, this is true if it is being ridden
     * by a player and the player is holding a carrot-on-a-stick
     */
    @Override
    public boolean isSaddled() {
    	return this.getEntityData().get(SADDLED).booleanValue();
    }
    
    @Override
    public void travel(Vector3d p_213352_1_) {
        if (this.isAlive()) {
	        if (this.isVehicle() && this.canBeControlledByRider() && this.isSaddled()) {
	        	LivingEntity controller = (LivingEntity)this.getControllingPassenger();
	        	this.yRot = controller.yRot;
	            this.yRotO = this.yRot;
	            this.xRot = controller.xRot * 0.5F;
	            this.setRot(this.yRot, this.xRot);
	            this.yBodyRot = this.yRot;
	            this.yHeadRot = this.yRot;
	            this.maxUpStep = 1.0F;
	            this.flyingSpeed = this.getSpeed() * 0.1F;
	            float f = controller.xxa * 0.5F;
	            float f1 = controller.zza;
	
	            if (this.isControlledByLocalInstance())
	            {
	                if(this.isInLava()) {
	                	this.setDeltaMovement(this.getDeltaMovement().x * 1.5F, this.getDeltaMovement().y + 0.02F, this.getDeltaMovement().z * 1.5F);
	                }
	                this.setSpeed((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED));
	                super.travel(new Vector3d((double)f, p_213352_1_.y, (double)f1));
	            }
	            else
	            {
	            	this.setDeltaMovement(Vector3d.ZERO);
	            }
	
	            this.calculateEntityAnimation(this, false);
	        } else {
	            super.travel(p_213352_1_);
	        }
        }
    }

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT p_213386_5_) {
       float chance_to_spawn_as_child = 0.0F;
       this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Salamander_Health.get());
       this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Salamander_Attack.get());
       this.setHealth(this.getMaxHealth());
   	
       if(SpawnUtil.getRegistryKey(p_213386_1_.getBiome(this.blockPosition())).equals(Biomes.SOUL_SAND_VALLEY)) {
    	   this.setSkin(1);
       }
       
       switch(this.level.getDifficulty()) {
	       case PEACEFUL:
	    	   chance_to_spawn_as_child = 0.85F;
	  			break;
	       case EASY:
	    	   chance_to_spawn_as_child = 0.8F;
	  			break;
	       case NORMAL:
	    	   chance_to_spawn_as_child = 0.5F;
	  			break;
	       case HARD:
	    	   chance_to_spawn_as_child = 0.15F;
       			break;
       		default:
       			break;
       }
       
       this.setBaby(this.level.getRandom().nextFloat() <= chance_to_spawn_as_child);
       
       return super.finalizeSpawn(p_213386_1_, difficulty, p_213386_3_, livingdata, p_213386_5_);
    }
    
	@Override
    protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
        return this.isBaby() ? 0.2F : 0.8F;
    }
    
    @Override
    public int getAttackTimer() {
       return this.getEntityData().get(ATTACK_TIMER).intValue();
    }

    public int getSkin() {
        return this.getEntityData().get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
    	this.getEntityData().set(SKIN_TYPE, Integer.valueOf(skinType));
    }
    
    @Override
    public void setAttackTimer(int i) {
        this.getEntityData().set(ATTACK_TIMER, i);
	}
    
    @Override
    protected SoundEvent getAmbientSound() {
        return FURSoundRegistry.SALAMANDER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FURSoundRegistry.SALAMANDER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.SALAMANDER_DEATH;
    }

	@Override
    protected void playStepSound(BlockPos pos, BlockState state) {
	    this.playSound(SoundEvents.COW_STEP, 0.15F, 1.0F);
	}
	
	@Override
	public boolean isSaddleable() {
		return this.isAlive() && !this.isBaby() && this.isTame();
	}
    
    @Override
    protected void dropEquipment() {
    	super.dropEquipment();
        if (this.isSaddled()) {
           this.spawnAtLocation(Items.SADDLE);
        }
	}
    
    @Override
    public void equipSaddle(@Nullable SoundCategory p_230266_1_) {
    	this.setSaddled(true);
        if (p_230266_1_ != null) {
           this.level.playSound((PlayerEntity)null, this, SoundEvents.HORSE_SADDLE, p_230266_1_, 0.5F, 1.0F);
        }
	}
    
    @Nullable
    @Override
    protected ResourceLocation getDefaultLootTable() {
    	return this.isBaby() ? new ResourceLocation("mod_lavacow", "entities/salamanderlesser") : super.getDefaultLootTable();
    }
    
    /**
     * "Sets the scale for an ageable entity according to the boolean parameter, which says if it's a child."
     */
    @Override
    public float getScale() {
    	float scale = 1.0F;

    	switch (this.getGrowingStage()) {
    		case 0:
    			scale = 0.30F;
    			break;
    		case 1:
    			scale = 0.50F;
    			break;
    		case 2:
    			scale = 0.75F;
    			break;
    		default:
    			scale = 1.0F;
    			break;   			
    	}
    	
    	return scale;
    }
    
    /**
     * Handler for {@link World#setEntityState}
     */
	@Override
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
		switch(id) {
			case 5:
				this.setAttackTimer(80);
				break;
			default:
				super.handleEntityEvent(id);
				break;
		}
    }    
	
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.setSaddled(compound.getBoolean("Saddled"));
        this.setGrowingStage(compound.getInt("GrowingStage"));
        this.setSkin(compound.getInt("Variant"));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Saddled", this.isSaddled());
        compound.putInt("GrowingStage", this.getGrowingStage());
        compound.putInt("Variant", getSkin());
    }
}
