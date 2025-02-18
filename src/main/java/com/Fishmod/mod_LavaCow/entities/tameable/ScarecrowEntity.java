package com.Fishmod.mod_LavaCow.entities.tameable;

import java.util.Random;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.entities.IAggressive;
import com.Fishmod.mod_LavaCow.entities.ai.FURMeleeAttackGoal;
import com.Fishmod.mod_LavaCow.init.FUREffectRegistry;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.StemBlock;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NonTamedTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtByTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtTargetGoal;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.state.IntegerProperty;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ScarecrowEntity extends FURTameableEntity implements IAggressive {
	private static final DataParameter<Integer> SKIN_TYPE =  EntityDataManager.defineId(ScarecrowEntity.class, DataSerializers.INT);
	private static final DataParameter<Integer> DATA_COLLAR_COLOR = EntityDataManager.defineId(ScarecrowEntity.class, DataSerializers.INT);
	public static final int ATTACK_TIMER = 15;
	private static final int RANGE = 5;
	
	private int attackTimer;
	private int cleaveTimer;
	/** 4: Vertical 5: Horizontal*/
	public byte AttackStance;
	
	private LookAtGoal watch;
	private LookRandomlyGoal look;
	
	public ScarecrowEntity(EntityType<? extends ScarecrowEntity> p_i48549_1_, World worldIn) {
        super(p_i48549_1_, worldIn);
    }
	
	@Override
    protected void defineSynchedData() {
		super.defineSynchedData();
        this.entityData.define(SKIN_TYPE, Integer.valueOf(this.random.nextInt(2)));
        this.entityData.define(DATA_COLLAR_COLOR, DyeColor.BROWN.getId());
	}
	
    @Override
    protected void registerGoals() {
        this.watch = new LookAtGoal(this, PlayerEntity.class, 8.0F);
        this.look = new LookRandomlyGoal(this);
    	
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
        this.targetSelector.addGoal(4, new NonTamedTargetGoal<>(this, PlayerEntity.class, false, (p_213440_0_) -> {
            return true;
        }));
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.25D)
        		.add(Attributes.MAX_HEALTH, FURConfig.Scarecrow_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, FURConfig.Scarecrow_Attack.get())
        		.add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }
    
    public static boolean checkScarecrowSpawnRules(EntityType<? extends ScarecrowEntity> p_223316_0_, IWorld p_223316_1_, SpawnReason p_223316_2_, BlockPos p_223316_3_, Random p_223316_4_) {
        return FURTameableEntity.checkMonsterSpawnRules(p_223316_0_, (IServerWorld) p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_);//SpawnUtil.isAllowedDimension(this.dimension);
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
    	}

    	if (this.cleaveTimer > 0) {
    		--this.cleaveTimer;
    	}
    	
    	if (!this.level.isClientSide && !this.isTame()) {
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
				BlockState blockstate = this.level.getBlockState(blockpos);
				Block block = blockstate.getBlock();
				IntegerProperty integerproperty = null;
				boolean flag = false;
				
				if (this.level.isEmptyBlock(blockpos)) {
					continue;
				}
	               
				if (block.is(BlockTags.BEE_GROWABLES)) {
					if (block instanceof CropsBlock) {
						CropsBlock cropsblock = (CropsBlock)block;
						if (!cropsblock.isMaxAge(blockstate)) {
							flag = true;
							integerproperty = cropsblock.getAgeProperty();
						}
					} else if (block instanceof StemBlock) {
						int j = blockstate.getValue(StemBlock.AGE);
						if (j < 7) {
							flag = true;
	                        integerproperty = StemBlock.AGE;
						}
					} else if (block == Blocks.SWEET_BERRY_BUSH) {
						int k = blockstate.getValue(SweetBerryBushBlock.AGE);
						if (k < 3) {
	                        flag = true;
	                        integerproperty = SweetBerryBushBlock.AGE;
						}
					}

					if (flag) {
						this.level.levelEvent(2005, blockpos, 0);
						this.level.setBlockAndUpdate(blockpos, blockstate.setValue(integerproperty, Integer.valueOf(blockstate.getValue(integerproperty) + 1)));
						break;
					}
				}
			}
        }
    	
        super.tick();
    }

    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
    	ItemStack itemstack = player.getItemInHand(hand);
    	Item item = itemstack.getItem();

    	if (this.isTame() && this.isOwnedBy(player)) {
	    	if (item instanceof DyeItem) {          
	            DyeColor dyecolor = ((DyeItem)item).getDyeColor();
	            
	            if (dyecolor != this.getCollarColor()) {
	            	this.setCollarColor(dyecolor);
	            	if (!player.abilities.instabuild) {
	            		itemstack.shrink(1);
	            	}
	
	            	return ActionResultType.CONSUME;
	            }
	            
	    	} else if (item instanceof SwordItem) {
	    		if (!this.getMainHandItem().isEmpty()) {
	    			this.spawnAtLocation(this.getMainHandItem());
	    		}
	    		
	    		this.setItemSlot(EquipmentSlotType.MAINHAND, itemstack.copy());
	    		
            	if (!player.abilities.instabuild) {
            		itemstack.shrink(1);
            	}	    		
	    		
	    		return ActionResultType.SUCCESS;
	    	
	    	} else if (player.isCrouching() && !this.getMainHandItem().isEmpty()) {
	    		this.spawnAtLocation(this.getMainHandItem());
	    		this.setItemSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
	    		
	    		return ActionResultType.SUCCESS;
	    		
	    	} else if (!this.isVehicle() && player.hasPassenger(RavenEntity.class)) {
	    		for(Entity passenger : player.getPassengers()) {
	    			if (passenger instanceof RavenEntity) {
	    	    		passenger.removeVehicle();
	    	    		passenger.startRiding(this, true);
	    			}
	    		}
	  		
	    		return ActionResultType.SUCCESS;
	    		
	    	} else if (this.isVehicle() && this.hasPassenger(RavenEntity.class)) {
	    		for(Entity passenger : this.getPassengers()) {
	    			if (passenger instanceof RavenEntity) {
	    	    		passenger.removeVehicle();
	    			}
	    		}
	    		
	    		return ActionResultType.SUCCESS;
	    	}
    	}

    	return super.mobInteract(player, hand); 	
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
	public boolean doHurtTarget(Entity entity) {
		boolean flag = super.doHurtTarget(entity);
		
		if (flag) {
			float f = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
			
            if (this.getMainHandItem().isEmpty() && this.isOnFire() && this.getRandom().nextFloat() < f * 0.3F) {
            	entity.setSecondsOnFire(2 * (int)f);
            }
            
			if (this.getSkin() != 2)
				((LivingEntity)entity).addEffect(new EffectInstance(FUREffectRegistry.CORRODED, 4 * 20 * (int)f, 1));
			else
				((LivingEntity)entity).addEffect(new EffectInstance(Effects.WITHER, 4 * 20 * (int)f, 1));
		}
		
		return flag;
	}
    
    /**
     * Called when the entity is attacked.
     */
	@Override
    public boolean hurt(DamageSource source, float amount) {
    	if (source.isFire())
    		return super.hurt(source, 2.0F * amount);
    	return super.hurt(source, amount);
    }  
    
    @Override
    public boolean canBeControlledByRider() {
    	return false;
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT p_213386_5_) {
        livingdata = super.finalizeSpawn(p_213386_1_, difficulty, p_213386_3_, livingdata, p_213386_5_);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Scarecrow_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Scarecrow_Attack.get());
    	this.setHealth(this.getMaxHealth());
        
    	if (this.random.nextFloat() < 0.00625F * FURConfig.pSpawnRate_Raven.get() && !this.level.isClientSide) {
    		RavenEntity crowpet = FUREntityRegistry.RAVEN.create(this.level);
    		crowpet.moveTo(this.getX(), this.getY(), this.getZ(), this.yRot, this.xRot);
    		crowpet.startRiding(this, true);
    		this.level.addFreshEntity(crowpet);
    	}
        
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
    	if (id == 4 || id == 5) {
            this.attackTimer = ATTACK_TIMER;
            this.AttackStance = id;
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
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
    public void travel(Vector3d p_213352_1_) {
    	if (!this.isSilent() || !this.level.getBlockState(this.blockPosition().below()).getMaterial().isSolidBlocking()) {
    		super.travel(p_213352_1_);
    	}
    }
    
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
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
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", getSkin());
        compound.putByte("CollarColor", (byte)this.getCollarColor().getId());
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return FURSoundRegistry.SCARECROW_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.WOOL_BREAK;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.SCARECROW_DEATH;
    }

	@Override
    protected void playStepSound(BlockPos pos, BlockState state) {
	    this.playSound(SoundEvents.STRAY_STEP, 0.15F, 1.0F);
	}

    /**
     * Get this Entity's CreatureAttribute
     */
    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.UNDEAD;
    }

    @Nullable
    @Override
    protected ResourceLocation getDefaultLootTable() {
    	switch(this.getSkin()) {
    		case 1:
    			return new ResourceLocation("mod_lavacow", "entities/scarecrow1");
    		case 2:
    			return new ResourceLocation("mod_lavacow", "entities/scarecrow2");
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
        public AttackGoal(CreatureEntity p_i46676_1_) {
           super(p_i46676_1_, 1.0D, true);
        }
        
        public boolean canUse() {
        	return !this.mob.isSilent() && super.canUse();
        }
        
    	protected int atkTimerMax() {
    		return ATTACK_TIMER;
    	}
    	
    	protected int atkTimerHit() {
    		return 5;
    	}
    	
    	protected byte atkTimerEvent() {
	        if(((ScarecrowEntity)this.mob).cleaveTimer == 0) {
	        	((ScarecrowEntity)this.mob).AttackStance = (byte)5;
	        	((ScarecrowEntity)this.mob).cleaveTimer = 140;
	        } else {
	        	((ScarecrowEntity)this.mob).AttackStance = (byte)4;
	        }
	        
    		return ((ScarecrowEntity)this.mob).AttackStance;
    	}
    	
    	protected void dmgEvent(LivingEntity target) {  		
    		this.mob.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1.0F, 1.0F);
    		
    		if (((ScarecrowEntity)this.mob).AttackStance == (byte)4) {
    			super.dmgEvent(target);
    		} else {               
    			for (LivingEntity entitylivingbase : this.mob.level.getEntitiesOfClass(LivingEntity.class, this.mob.getBoundingBox().inflate(2.0D))) {
                    if (!this.mob.equals(entitylivingbase) && !this.mob.isAlliedTo(entitylivingbase)) {
                    	if (!(entitylivingbase instanceof TameableEntity && ((TameableEntity) entitylivingbase).isOwnedBy(this.mob))) {
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
}
