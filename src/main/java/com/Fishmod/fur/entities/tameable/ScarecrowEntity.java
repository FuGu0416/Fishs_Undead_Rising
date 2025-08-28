package com.Fishmod.fur.entities.tameable;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.IAggressive;
import com.Fishmod.fur.entities.ai.FURMeleeAttackGoal;
import com.Fishmod.fur.init.FUREffectRegistry;
import com.Fishmod.fur.init.FURSoundRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
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

public class ScarecrowEntity extends FURTameableEntity implements IAggressive, GeoEntity {
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public static final RawAnimation IDLE = RawAnimation.begin().thenPlay("scarecrow.model.idle");
    private static final RawAnimation SIT = RawAnimation.begin().thenPlay("scarecrow.model.sit");
    private static final RawAnimation WALK = RawAnimation.begin().thenPlay("scarecrow.model.walking");
    private static final RawAnimation ATTACK_0 = RawAnimation.begin().thenPlay("scarecrow.model.attack_0");
    private static final RawAnimation ATTACK_1 = RawAnimation.begin().thenPlay("scarecrow.model.attack_1");
    private static final RawAnimation ATTACK_SWIPE = RawAnimation.begin().thenPlay("scarecrow.model.attack_swipe");
    
	private static final EntityDataAccessor<Integer> SKIN_TYPE =  SynchedEntityData.defineId(ScarecrowEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> DATA_COLLAR_COLOR = SynchedEntityData.defineId(ScarecrowEntity.class, EntityDataSerializers.INT);
	public static final int ATTACK_TIMER = 25;
	private static final int RANGE = 5;
	
	private int attackTimer;
	private int cleaveTimer;
	/** 4: Vertical 5: Horizontal*/
	public byte AttackStance;
	
	private LookAtPlayerGoal watch;
	private RandomLookAroundGoal look;
	
	public ScarecrowEntity(EntityType<? extends ScarecrowEntity> p_i48549_1_, Level worldIn) {
        super(p_i48549_1_, worldIn);        
    }
	
	@Override
    protected void defineSynchedData() {
		super.defineSynchedData();
        this.entityData.define(SKIN_TYPE, 0);
        this.entityData.define(DATA_COLLAR_COLOR, DyeColor.BROWN.getId());
	}
	
    @Override
    protected void registerGoals() {
        this.watch = new LookAtPlayerGoal(this, Player.class, 8.0F);
        this.look = new RandomLookAroundGoal(this);
    	
    	super.registerGoals();
        this.goalSelector.addGoal(2, new ScarecrowEntity.AttackGoal(this));
        this.goalSelector.addGoal(8, this.watch);
        this.goalSelector.addGoal(8, this.look);
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
    	this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(4, new NonTameRandomTargetGoal<>(this, Player.class, false, (p_213440_0_) -> {
            return true;
        }));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.25D)
        		.add(Attributes.MAX_HEALTH, 40.0D/*FURConfig.Scarecrow_Health.get()*/)
        		.add(Attributes.ATTACK_DAMAGE, 8.0D/*FURConfig.Scarecrow_Attack.get()*/)
        		.add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }
    
    public static boolean checkScarecrowSpawnRules(EntityType<? extends ScarecrowEntity> p_223316_0_, ServerLevelAccessor p_223316_1_, MobSpawnType p_223316_2_, BlockPos p_223316_3_, RandomSource p_223316_4_) {
        return FURTameableEntity.checkMonsterSpawnRules(p_223316_0_, p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_) && (p_223316_1_.canSeeSky(p_223316_3_) || p_223316_1_.dimensionType().hasCeiling());
    }
    
    @Override
    protected boolean isSunBurnTick() {
        if (this.level().isDay() && !this.level().isClientSide) {
           float f = this.level().getBrightness(LightLayer.SKY, this.blockPosition());
           BlockPos blockpos = this.getVehicle() instanceof Boat ? (new BlockPos((int)this.getX(), (int)Math.round(this.getY()), (int)this.getZ())).above() : new BlockPos((int)this.getX(), (int)Math.round(this.getY()), (int)this.getZ());
           return (f > 0.5F && this.level().canSeeSky(blockpos));
        }
        return false;
    }
    
    @Override
    public double getMyRidingOffset() {
        return -1.0D;
    }
    
    @Override
    public double getPassengersRidingOffset() {
    	return this.getSkin() != 0 ? 2.6D : 2.2D;
    }
    
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void tick() {
    	if (this.attackTimer > 0) {
    		--this.attackTimer;
    		this.setDeltaMovement(Vec3.ZERO);
    	}

    	if (this.cleaveTimer > 0) {
    		--this.cleaveTimer;
    	}
    	
    	if (!this.isTame()) {
    		if (this.isSunBurnTick()) {
    			this.doSitCommand(null);
    		} else if (this.state != FURTameableEntity.State.WANDERING) {
    			this.doFollowCommand(null);
    			this.doWanderCommand(null);
    		}
    	}
        
        // accelerate crop growing
        if (this.tickCount % 80 == 0 && this.isAlive() && this.isTame() && this.isInSittingPose()) {
        	int x = this.blockPosition().getX() + this.getRandom().nextInt(RANGE * 2 + 1) - RANGE;
			int z = this.blockPosition().getZ() + this.getRandom().nextInt(RANGE * 2 + 1) - RANGE;   
			
			for (int i = 4; i > -2; i--) {
				int y = this.blockPosition().getY() + i;
				BlockPos blockpos = new BlockPos(x, y, z);
				BlockState blockstate = this.level().getBlockState(blockpos);
				Block block = blockstate.getBlock();
				BlockState blockstate1 = null;
				boolean flag = false;
				
				if (this.level().isEmptyBlock(blockpos)) {
					continue;
				}
	               
				if (blockstate.is(BlockTags.BEE_GROWABLES)) {
					if (block instanceof CropBlock) {
						CropBlock cropblock = (CropBlock)block;
						if (!cropblock.isMaxAge(blockstate)) {
							flag = true;
							blockstate1 = cropblock.getStateForAge(cropblock.getAge(blockstate) + 1);
						}
					} else if (block instanceof StemBlock) {
						int k = blockstate.getValue(StemBlock.AGE);
						if (k < 7) {
							flag = true;
							blockstate1 = blockstate.setValue(StemBlock.AGE, Integer.valueOf(k + 1));
						}
					} else if (block == Blocks.SWEET_BERRY_BUSH) {
						int j = blockstate.getValue(SweetBerryBushBlock.AGE);
						if (j < 3) {
	                        flag = true;
	                        blockstate1 = blockstate.setValue(SweetBerryBushBlock.AGE, Integer.valueOf(j + 1));
						}
					} else if (blockstate.is(Blocks.CAVE_VINES) || blockstate.is(Blocks.CAVE_VINES_PLANT)) {
						((BonemealableBlock)blockstate.getBlock()).performBonemeal((ServerLevel)this.level(), this.random, blockpos, blockstate);
					}

					if (flag) {
						this.level().levelEvent(2005, blockpos, 0);
						this.level().setBlockAndUpdate(blockpos, blockstate1);
						break;
					}
				}
			}
        }
    	
        super.tick();
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
    	ItemStack itemstack = player.getItemInHand(hand);
    	Item item = itemstack.getItem();
    	
    	if (this.isTame() && this.isOwnedBy(player)) {
	    	if (item instanceof DyeItem) {          
	            DyeColor dyecolor = ((DyeItem)item).getDyeColor();
	            
	            if (dyecolor != this.getCollarColor()) {
	            	this.setCollarColor(dyecolor);
	            	if (!player.getAbilities().instabuild) {
	            		itemstack.shrink(1);
	            	}
	
	            	return InteractionResult.CONSUME;
	            }
	            
	    	} else if (item instanceof TieredItem) {
	    		if (!this.getMainHandItem().isEmpty()) {
	    			this.spawnAtLocation(this.getMainHandItem());
	    		}
	    		
	    		this.setItemSlot(EquipmentSlot.MAINHAND, itemstack.copy());
	    		
            	if (!player.getAbilities().instabuild) {
            		itemstack.shrink(1);
            	}	    		
	    		
	    		return InteractionResult.SUCCESS;
	    	
	    	} else if (player.isCrouching() && !this.getMainHandItem().isEmpty()) {
	    		this.spawnAtLocation(this.getMainHandItem());
	    		this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
	    		
	    		return InteractionResult.SUCCESS;
	    		
	    	}/* else if (!this.isVehicle() && player.hasPassenger(RavenEntity.class)) {
	    		for(Entity passenger : player.getPassengers()) {
	    			if (passenger instanceof RavenEntity) {
	    	    		passenger.removeVehicle();
	    	    		passenger.startRiding(this, true);
	    			}
	    		}
	  		
	    		return InteractionResult.SUCCESS;
	    		
	    	} else if (this.isVehicle() && this.hasPassenger(RavenEntity.class)) {
	    		for(Entity passenger : this.getPassengers()) {
	    			if (passenger instanceof RavenEntity) {
	    	    		passenger.removeVehicle();
	    			}
	    		}
	    		
	    		return InteractionResult.SUCCESS;
	    	}*/
    	}

    	return super.mobInteract(player, hand); 	
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
        this.goalSelector.addGoal(8, this.watch);
        this.goalSelector.addGoal(8, this.look);
		this.setSilent(false);
        super.doFollowCommand(playerIn);
    }
    
	@Override
	public boolean doHurtTarget(Entity entity) {
		boolean flag = super.doHurtTarget(entity);
		
		if (flag) {
			float f = this.level().getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
			
            if (this.getMainHandItem().isEmpty() && this.isOnFire() && this.getRandom().nextFloat() < f * 0.3F) {
            	entity.setSecondsOnFire(2 * (int)f);
            }
            
			if (this.getSkin() != 2)
				((LivingEntity)entity).addEffect(new MobEffectInstance(FUREffectRegistry.CORRODED.get(), 4 * 20 * (int)f, 1));
			else
				((LivingEntity)entity).addEffect(new MobEffectInstance(MobEffects.WITHER, 4 * 20 * (int)f, 1));
		}
		
		return flag;
	}
    
    /**
     * Called when the entity is attacked.
     */
	@Override
    public boolean hurt(DamageSource source, float amount) {
    	if (source.is(DamageTypeTags.IS_FIRE))
    		return super.hurt(source, 2.0F * amount);
    	return super.hurt(source, amount);
    }  
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_213386_1_, DifficultyInstance difficulty, MobSpawnType p_213386_3_, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag p_213386_5_) {
        livingdata = super.finalizeSpawn(p_213386_1_, difficulty, p_213386_3_, livingdata, p_213386_5_);
        /*this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Scarecrow_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Scarecrow_Attack.get());
    	this.setHealth(this.getMaxHealth());*/
        
    	/*if (this.random.nextFloat() < 0.00625F * FURConfig.pSpawnRate_Raven.get() && !this.level.isClientSide) {
    		RavenEntity crowpet = FUREntityRegistry.RAVEN.create(this.level);
    		crowpet.moveTo(this.getX(), this.getY(), this.getZ(), this.yRot, this.xRot);
    		crowpet.startRiding(this, true);
    		this.level.addFreshEntity(crowpet);
    	}*/
        
        this.setSkin(Integer.valueOf(this.random.nextInt(2)));
        this.setLeftHanded(true);
        
        return livingdata;
    }
    
    @Override
    public int getAttackTimer() {
       return this.attackTimer;
    }
    
	@Override
	public void setAttackTimer(int i) {
		this.attackTimer = i;
	}
    
    public int getSkin() {
        return this.getEntityData().get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
    	this.getEntityData().set(SKIN_TYPE, Integer.valueOf(skinType));
    	
    	if (skinType == 2) {
    		this.setCollarColor(DyeColor.BLACK);
    	}
    }
    
    public DyeColor getCollarColor() {
        return DyeColor.byId(this.entityData.get(DATA_COLLAR_COLOR));
	}

	public void setCollarColor(DyeColor p_175547_1_) {
		this.entityData.set(DATA_COLLAR_COLOR, p_175547_1_.getId());
	}
    
    /**
     * Handler for {@link World#setEntityState}
     */
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
    	if (id == 4 || id == 5 || id == 6) {
            this.attackTimer = ATTACK_TIMER;
            this.AttackStance = id;
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    protected float getStandingEyeHeight(Pose p_213348_1_, EntityDimensions p_213348_2_) {
        return 2.6F;
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
	
    @Override
    public void travel(Vec3 p_213352_1_) {
    	if (!this.isSilent() || !this.level().getBlockState(this.blockPosition().below()).isSolid()) {
    		super.travel(p_213352_1_);
    	}
    }
    
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setSkin(compound.getInt("Variant"));
        if (compound.contains("CollarColor", 99)) {
            this.setCollarColor(DyeColor.byId(compound.getInt("CollarColor")));
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", getSkin());
        compound.putByte("CollarColor", (byte)this.getCollarColor().getId());
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return FURSoundRegistry.SCARECROW_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.WOOL_BREAK;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.SCARECROW_DEATH.get();
    }

	@Override
    protected void playStepSound(BlockPos pos, BlockState state) {
	    this.playSound(SoundEvents.STRAY_STEP, 0.15F, 1.0F);
	}

    /**
     * Get this Entity's CreatureAttribute
     */
    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    @Nullable
    @Override
    protected ResourceLocation getDefaultLootTable() {
    	switch(this.getSkin()) {
    		case 1:
    			return new ResourceLocation(mod_LavaCow.MODID, "entities/scarecrow1");
    		case 2:
    			return new ResourceLocation(mod_LavaCow.MODID, "entities/scarecrow2");
    		case 0:
    		default:
    			return super.getDefaultLootTable();
    	}
    }
    
    /**
     * Entity won't drop items or experience points if this returns false
     */
    @Override
    public boolean shouldDropLoot() {
    	return !this.isOnFire() || this.lastHurtByPlayer != null;
    }
    
    @Override
    protected void dropEquipment() {
    	super.dropEquipment();
        
		if (!this.getMainHandItem().isEmpty()) {
			this.spawnAtLocation(this.getMainHandItem());
		}
	}
   
    static class AttackGoal extends FURMeleeAttackGoal {
        public AttackGoal(PathfinderMob p_i46676_1_) {
           super(p_i46676_1_, 1.0D, true);
        }
        
        public boolean canUse() {
        	return !this.mob.isSilent() && super.canUse();
        }
        
    	protected int atkTimerMax() {
    		return ATTACK_TIMER;
    	}
    	
    	protected int atkTimerHit() {
    		if (((ScarecrowEntity)this.mob).AttackStance == (byte)4) {
    			return 12;
    		} else {
    			return 5;
    		}  		
    	}
    	
    	protected byte atkTimerEvent() {
	        if(((ScarecrowEntity)this.mob).cleaveTimer == 0) {
	        	((ScarecrowEntity)this.mob).AttackStance = (byte)6;
	        	((ScarecrowEntity)this.mob).cleaveTimer = 140;
	        } else if (this.mob.getRandom().nextBoolean()) {
	        	((ScarecrowEntity)this.mob).AttackStance = (byte)5;
	        } else {
	        	((ScarecrowEntity)this.mob).AttackStance = (byte)4;
	        }
	        
    		return ((ScarecrowEntity)this.mob).AttackStance;
    	}
    	
    	protected void dmgEvent(LivingEntity target) {  		
    		this.mob.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1.0F, 1.0F);
    		
    		if (((ScarecrowEntity)this.mob).AttackStance == (byte)4 || ((ScarecrowEntity)this.mob).AttackStance == (byte)5) {
    			super.dmgEvent(target);
    		} else {               
    			for (LivingEntity entitylivingbase : this.mob.level().getEntitiesOfClass(LivingEntity.class, this.mob.getBoundingBox().inflate(2.0D))) {
                    if (!this.mob.equals(entitylivingbase) && !this.mob.isAlliedTo(entitylivingbase)) {
                    	if (!(entitylivingbase instanceof TamableAnimal && ((TamableAnimal) entitylivingbase).isOwnedBy(this.mob))) {
                    		super.dmgEvent(entitylivingbase);
                    	}
                    }
                }
    		}   		  		         
    	}
    	
        protected double getAttackReachSqr(LivingEntity p_179512_1_) {
            return (double)(this.mob.getBbWidth() * 4.0F * this.mob.getBbWidth() * 4.0F + p_179512_1_.getBbWidth());
        }
	}
    
    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
    	if (this.getAttackTimer() == (ATTACK_TIMER - 1)) {
    		if (this.AttackStance == (byte)4) {
				state.getController().setAnimation(ATTACK_0);			
    		} else if (this.AttackStance == (byte)5) { 
    			state.getController().setAnimation(ATTACK_1);
    		} else if (this.AttackStance == (byte)6) { 
    			state.getController().setAnimation(ATTACK_SWIPE);
    		}   		
    	} else if (this.getAttackTimer() > 0) {
    		return PlayState.CONTINUE;
    	} else if (state.isMoving()) {
            state.getController().setAnimation(WALK);
    	} else if (this.isInSittingPose()) {
    		state.getController().setAnimation(SIT);
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
