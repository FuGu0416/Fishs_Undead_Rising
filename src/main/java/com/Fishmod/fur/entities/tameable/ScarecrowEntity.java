package com.Fishmod.fur.entities.tameable;

import java.util.EnumSet;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.core.SpawnUtil;
import com.Fishmod.fur.data.providers.FURBiomeTagsProvider;
import com.Fishmod.fur.entities.ai.FURMeleeAttackGoal;
import com.Fishmod.fur.init.FURBlockRegistry;
import com.Fishmod.fur.init.FUREffectRegistry;
import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURItemRegistry;
import com.Fishmod.fur.item.BeastcallHornItem;
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
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.Level;
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

public class ScarecrowEntity extends FURTameableEntity implements GeoEntity {
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
	
	private int cleaveTimer;
	/** 4: Vertical 5: Horizontal*/
	public byte AttackStance;

	private LookAtPlayerGoal watch;
	private RandomLookAroundGoal look;
	/** Set only while {@link CreepyGlanceGoal} is actively holding a look - see {@link #getMaxHeadXRot()}. */
	private boolean glancing;
	
	public ScarecrowEntity(EntityType<? extends ScarecrowEntity> entityType, Level worldIn) {
        super(entityType, worldIn);
        this.xpReward = 10;
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
        // Not removed by doSitCommand like watch/look - it needs to keep running (and self-gates on
        // isInSittingPose()) precisely while disguised, so it can occasionally interrupt the frozen
        // pose with a brief creepy glance at a nearby player at night.
        this.goalSelector.addGoal(9, new ScarecrowEntity.CreepyGlanceGoal(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
    	this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(4, new NonTameRandomTargetGoal<>(this, Player.class, false, (candidate) -> {
            return true;
        }));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.25D)
        		.add(Attributes.MAX_HEALTH, 40.0D)
        		.add(Attributes.ATTACK_DAMAGE, 8.0D)
        		.add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }
    
    public static boolean checkScarecrowSpawnRules(EntityType<? extends ScarecrowEntity> entityTypeIn, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource randomSource) {
        return FURTameableEntity.checkMonsterSpawnRules(entityTypeIn, level, spawnType, pos, randomSource) && (level.canSeeSky(pos) || level.dimensionType().hasCeiling());
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

    	if (this.cleaveTimer > 0) {
    		--this.cleaveTimer;
    	}
    	
    	// Disguise (sitting, head frozen) is the default resting state at all times now, not just in
    	// daylight - a wild Scarecrow looks like an inanimate prop until something is actually after
    	// it. Target acquisition (targetSelector) still runs while sitting, so it can still notice
    	// approaching players; getting a target is what wakes it (FURMeleeAttackGoal already refuses
    	// to swing while isInSittingPose(), so waking is required before it can actually attack).
    	// Losing the target (killed, fled out of range, ...) puts it back to sleep next tick.
    	if (!this.level().isClientSide && !this.isTame()) {
    		if (this.getTarget() == null) {
    			// guarded so the full switchState goal churn doesn't rerun every tick while idle
    			if (this.state != FURTameableEntity.State.SITTING) {
    				this.doSitCommand(null);
    			}
    		} else if (this.state != FURTameableEntity.State.WANDERING) {
    			this.doWanderCommand(null);
    		}
    	}

        // accelerate crop growing (server only — the client must not mutate block states,
        // and the cave-vines branch casts the level to ServerLevel)
        if (!this.level().isClientSide && this.tickCount % 80 == 0 && this.isAlive() && this.isTame() && this.isInSittingPose()) {
        	BlockPos origin = this.blockPosition();
        	int x = origin.getX() + this.getRandom().nextInt(RANGE * 2 + 1) - RANGE;
			int z = origin.getZ() + this.getRandom().nextInt(RANGE * 2 + 1) - RANGE;

			for (int i = 4; i > -2; i--) {
				int y = origin.getY() + i;
				BlockPos blockpos = new BlockPos(x, y, z);
				BlockState blockstate = this.level().getBlockState(blockpos);
				Block block = blockstate.getBlock();
				BlockState blockstate1 = null;
				boolean flag = false;

				if (blockstate.isAir()) {
					continue;
				}
	               
				if (blockstate.is(BlockTags.BEE_GROWABLES)) {
					if (block instanceof CropBlock cropblock) {
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

	    		return InteractionResult.sidedSuccess(this.level().isClientSide);
	    	
	    	} else if (player.isCrouching() && !this.getMainHandItem().isEmpty()
	    			&& !(item instanceof BeastcallHornItem)) {
	    		this.spawnAtLocation(this.getMainHandItem());
	    		this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);

	    		return InteractionResult.sidedSuccess(this.level().isClientSide);
	    		
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
    	this.restoreIdleBehavior();
        super.doFollowCommand(playerIn);
    }

    @Override
	public void doWanderCommand(Player playerIn) {
    	this.restoreIdleBehavior();
        super.doWanderCommand(playerIn);
    }

    /** Undo doSitCommand: re-install the look goals (remove first so repeated commands can't stack copies) and unmute. */
    private void restoreIdleBehavior() {
        this.goalSelector.removeGoal(this.watch);
        this.goalSelector.removeGoal(this.look);
        this.goalSelector.addGoal(8, this.watch);
        this.goalSelector.addGoal(8, this.look);
        this.setSilent(false);
    }
    
	@Override
	public boolean doHurtTarget(Entity entity) {
		boolean flag = super.doHurtTarget(entity);
		
		if (flag) {
			float f = this.level().getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
			
            if (this.getMainHandItem().isEmpty() && this.isOnFire() && this.getRandom().nextFloat() < f * 0.3F) {
            	entity.setSecondsOnFire(2 * (int)f);
            }
            
			if (entity instanceof LivingEntity le) {
				le.addEffect(this.getSkin() != 2
						? new MobEffectInstance(FUREffectRegistry.CORRODED.get(), 4 * 20 * (int)f, 1)
						: new MobEffectInstance(MobEffects.WITHER, 4 * 20 * (int)f, 1));
			}
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
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficulty, MobSpawnType spawnTypeIn, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
        livingdata = super.finalizeSpawn(worldIn, difficulty, spawnTypeIn, livingdata, tag);

        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Scarecrow_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Scarecrow_Attack.get());
    	this.setHealth(this.getMaxHealth());
    	
    	// Only scarecrows that spawn in a biome where ravens occur (HAS_RAVEN) can come with a raven rider.
    	if (this.random.nextFloat() < 0.05F
    			&& worldIn.getBiome(this.blockPosition()).is(FURBiomeTagsProvider.HAS_RAVEN)) {
    		// trySpawnEntity goes through EntityType.spawn, which already adds the raven to the world.
    		RavenEntity crowpet = SpawnUtil.trySpawnEntity(FUREntityRegistry.RAVEN.get(), worldIn.getLevel(), this.blockPosition());
    		if (crowpet != null) {
    			crowpet.startRiding(this, true);
    		}
    	}
        
        if (spawnTypeIn == MobSpawnType.COMMAND || spawnTypeIn == MobSpawnType.SPAWN_EGG || spawnTypeIn == MobSpawnType.SPAWNER || spawnTypeIn == MobSpawnType.DISPENSER) {
        	this.setSkin(this.random.nextInt(3));
        } else {
        	this.setSkin(this.random.nextInt(2));
        }
        
        this.setLeftHanded(true);
        
        return livingdata;
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

	public void setCollarColor(DyeColor color) {
		this.entityData.set(DATA_COLLAR_COLOR, color.getId());
	}
    
    /**
     * Handler for {@link World#setEntityState}
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
    	if (id == 4) {
            this.AttackStance = id;
            this.triggerAnim("trigger_controller", "attack_0");
    	} else if (id == 5) {
            this.AttackStance = id;
            this.triggerAnim("trigger_controller", "attack_1");
    	} else if (id == 6) {
            this.AttackStance = id;
            this.triggerAnim("trigger_controller", "attack_swipe");
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return 2.6F;
    }
    
    /**
     * The speed it takes to move the entityliving's rotationPitch through the faceEntity method. This is only currently
     * use in wolves.
     */
    // "Planted" checks use the synced sitting pose rather than isSilent() — the silent flag
    // is only a sound-mute side effect and can be flipped by commands or other mods.
    // The glancing exception lets CreepyGlanceGoal briefly turn the head while still disguised —
    // everywhere else, sitting means frozen.
    @Override
    public int getMaxHeadXRot() {
        return (this.isInSittingPose() && !this.glancing) ? 0 : super.getMaxHeadXRot();
    }

    @Override
    public int getMaxHeadYRot() {
        return (this.isInSittingPose() && !this.glancing) ? 0 : super.getMaxHeadYRot();
    }

    @Override
    public int getHeadRotSpeed() {
        return (this.isInSittingPose() && !this.glancing) ? 0 : super.getHeadRotSpeed();
	}

    @Override
    public void travel(Vec3 travelVector) {
    	if (!this.isInSittingPose() || !this.level().getBlockState(this.blockPosition().below()).isSolid()) {
    		super.travel(travelVector);
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
    
    @Override
    public void die(DamageSource damageSource) {
        super.die(damageSource);

        if (this.isTame() && !this.level().isClientSide) {
            Block headBlock = switch (this.getSkin()) {
                case 1 -> FURBlockRegistry.SCARECROWHEAD_STRAW.get();
                case 2 -> FURBlockRegistry.SCARECROWHEAD_PLAGUE.get();
                default -> FURBlockRegistry.SCARECROWHEAD_COMMON.get();
            };

            this.spawnAtLocation(new ItemStack(headBlock.asItem()), 0.5F);
            this.spawnAtLocation(new ItemStack(FURItemRegistry.UNDYING_HEART.get()), 0.5F);
        }
    }
    
    /**
     * Entity won't drop items or experience points if this returns false
     */
    @Override
    public boolean shouldDropLoot() {
    	return !this.isTame() && (!this.isOnFire() || this.lastHurtByPlayer != null);
    }
    
    @Override
    protected void dropEquipment() {
    	super.dropEquipment();
        
		if (!this.getMainHandItem().isEmpty()) {
			this.spawnAtLocation(this.getMainHandItem());
			// Clear the slot so dropCustomDeathLoot can't roll a second copy of the same weapon.
			this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
		}
	}
   
    static class AttackGoal extends FURMeleeAttackGoal {
        public AttackGoal(PathfinderMob mob) {
           super(mob, 1.0D, true);
        }
        
        // No canUse override needed: FURMeleeAttackGoal already refuses while a
        // TamableAnimal is in its sitting ("planted") pose.

    	@Override
    	protected int atkTimerMax() {
    		return ATTACK_TIMER;
    	}

    	@Override
    	protected int atkTimerHit() {
    		return ((ScarecrowEntity) this.mob).AttackStance == (byte) 4 ? 12 : 5;
    	}

    	@Override
    	protected byte atkTimerEvent() {
    		ScarecrowEntity sc = (ScarecrowEntity) this.mob;
	        if (sc.cleaveTimer == 0) {
	        	sc.AttackStance = (byte)6;
	        	sc.cleaveTimer = 140;
	        } else if (this.mob.getRandom().nextBoolean()) {
	        	sc.AttackStance = (byte)5;
	        } else {
	        	sc.AttackStance = (byte)4;
	        }
    		return sc.AttackStance;
    	}
    	
    	@Override
    	protected void dmgEvent(LivingEntity target) {
    		this.mob.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1.0F, 1.0F);
    		ScarecrowEntity sc = (ScarecrowEntity) this.mob;
    		if (sc.AttackStance == (byte)4 || sc.AttackStance == (byte)5) {
    			super.dmgEvent(target);
    		} else {
    			// Cleave always lands on the primary target — the trigger reach (getAttackReachSqr)
    			// exceeds the 2-block AoE box, so an edge-of-reach swipe would otherwise whiff it.
    			super.dmgEvent(target);
    			for (LivingEntity entitylivingbase : this.mob.level().getEntitiesOfClass(LivingEntity.class, this.mob.getBoundingBox().inflate(2.0D))) {
                    if (!entitylivingbase.equals(target) && !this.mob.equals(entitylivingbase) && !this.mob.isAlliedTo(entitylivingbase)) {
                    	// Spare only pets that share this scarecrow's owner.
                    	if (!(entitylivingbase instanceof TamableAnimal tamable && tamable.getOwnerUUID() != null && tamable.getOwnerUUID().equals(sc.getOwnerUUID()))) {
                    		super.dmgEvent(entitylivingbase);
                    	}
                    }
                }
    		}
    	}

    	@Override
        protected double getAttackReachSqr(LivingEntity target) {
            return (double)(this.mob.getBbWidth() * 4.0F * this.mob.getBbWidth() * 4.0F + target.getBbWidth());
        }
	}

    /**
     * Purely cosmetic: while disguised (sitting) at night, occasionally turns the head to track a
     * nearby player for a few seconds before freezing again - a "is it actually just a prop?" moment.
     * Does not acquire a target and does not wake the Scarecrow up; {@link #glancing} is the only
     * thing it flips, which just lets {@link #getMaxHeadXRot()} briefly stop clamping head rotation
     * to zero. Modeled after vanilla LookAtPlayerGoal, with sitting/night gating and the glancing flag
     * added since that vanilla goal has neither and isn't otherwise reusable here (it's already removed
     * from the goal selector for the whole time this entity is sitting, via doSitCommand).
     */
    static class CreepyGlanceGoal extends Goal {
    	private static final float LOOK_DISTANCE = 10.0F;
    	/** Rolled once per tick while eligible - low enough to feel rare/unsettling, not constant. */
    	private static final float PROBABILITY = 0.001F;

    	private final ScarecrowEntity scarecrow;
    	private Player lookAt;
    	private int lookTime;

    	CreepyGlanceGoal(ScarecrowEntity scarecrow) {
    		this.scarecrow = scarecrow;
    		this.setFlags(EnumSet.of(Goal.Flag.LOOK));
    	}

    	private boolean eligible() {
    		return this.scarecrow.isInSittingPose() && !this.scarecrow.level().isDay();
    	}

    	@Override
    	public boolean canUse() {
    		if (!this.eligible() || this.scarecrow.getRandom().nextFloat() >= PROBABILITY) {
    			return false;
    		}
    		this.lookAt = this.scarecrow.level().getNearestPlayer(this.scarecrow, (double)LOOK_DISTANCE);
    		return this.lookAt != null;
    	}

    	@Override
    	public boolean canContinueToUse() {
    		return this.eligible() && this.lookTime > 0 && this.lookAt != null && this.lookAt.isAlive()
    				&& this.scarecrow.distanceToSqr(this.lookAt) <= (double)(LOOK_DISTANCE * LOOK_DISTANCE);
    	}

    	@Override
    	public void start() {
    		this.lookTime = 40 + this.scarecrow.getRandom().nextInt(60); // 2-5s
    		this.scarecrow.glancing = true;
    	}

    	@Override
    	public void stop() {
    		this.lookAt = null;
    		this.scarecrow.glancing = false;
    	}

    	@Override
    	public void tick() {
    		if (this.lookAt != null && this.lookAt.isAlive()) {
    			this.scarecrow.getLookControl().setLookAt(this.lookAt, (float)this.scarecrow.getMaxHeadYRot(), (float)this.scarecrow.getMaxHeadXRot());
    		}
    		--this.lookTime;
    	}
    }
    
    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
    	if (this.isInSittingPose()) {
    		state.getController().setAnimation(SIT);
    	} else if (state.isMoving() || !this.getNavigation().isDone()) {
            state.getController().setAnimation(WALK);
        } else {
            state.getController().setAnimation(IDLE);
        }

        return PlayState.CONTINUE;
    }

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "controller", 5, this::predicate));
		controllers.add(new AnimationController<>(this, "trigger_controller", 5, state -> PlayState.STOP)
				.triggerableAnim("attack_0", ATTACK_0)
				.triggerableAnim("attack_1", ATTACK_1)
				.triggerableAnim("attack_swipe", ATTACK_SWIPE));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	} 
}
