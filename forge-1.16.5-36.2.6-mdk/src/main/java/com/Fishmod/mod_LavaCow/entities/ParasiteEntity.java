package com.Fishmod.mod_LavaCow.entities;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.init.FUREffectRegistry;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;
import com.Fishmod.mod_LavaCow.init.FURTagRegistry;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ParasiteEntity extends SpiderEntity {
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.defineId(ParasiteEntity.class, DataSerializers.INT);
	private static final DataParameter<Direction> ATTACHED_BLK = EntityDataManager.defineId(ParasiteEntity.class, DataSerializers.DIRECTION);
	protected static final DataParameter<Byte> DATA_FLAGS_ID = EntityDataManager.defineId(ParasiteEntity.class, DataSerializers.BYTE);
	protected static final DataParameter<Optional<UUID>> DATA_OWNERUUID_ID = EntityDataManager.defineId(ParasiteEntity.class, DataSerializers.OPTIONAL_UUID);
	private static final Direction[] DIRECTIONS = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
	private static final Item[] ParasiteDrop = { FURItemRegistry.PARASITE_COMMON, FURItemRegistry.PARASITE_DESERT, FURItemRegistry.PARASITE_JUNGLE, FURItemRegistry.PARASITE_COOKED };
	public int lifespawn;
	
	public ParasiteEntity(EntityType<? extends ParasiteEntity> p_i48549_1_, World worldIn) {
        super(p_i48549_1_, worldIn);
        this.lifespawn = FURConfig.Parasite_Lifespan.get() * 20; // Can live for 16s only, poor little one :(
        this.xpReward = 1;
    }
	
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new SwimGoal(this));
		this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));
		this.goalSelector.addGoal(4, new ParasiteEntity.AttackGoal(this));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.8D));
		this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
        this.applyEntityAI();
    }
	
    protected void applyEntityAI() {
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 0, true, true, (p_213440_0_) -> {
            	return !p_213440_0_.isPassenger() && !this.isTame();
        	}));
    	this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 0, true, true, (p_210136_0_) -> {
    		ITag<EntityType<?>> tag = EntityTypeTags.getAllTags().getTag(FURTagRegistry.PARASITE_TARGETS);
    		return tag != null && p_210136_0_ instanceof LivingEntity && ((LivingEntity)p_210136_0_).attackable() && p_210136_0_.getType().is(tag) && !this.isTame();
    	}));	
    }
    
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.2D)
        		.add(Attributes.FOLLOW_RANGE, 8.0D)
        		.add(Attributes.MAX_HEALTH, FURConfig.Parasite_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, FURConfig.Parasite_Attack.get());
    }
    
    @Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SKIN_TYPE, Integer.valueOf(this.getRandom().nextInt(3)));
		this.entityData.define(ATTACHED_BLK, Direction.DOWN);
		this.entityData.define(DATA_FLAGS_ID, (byte)0);
		this.entityData.define(DATA_OWNERUUID_ID, Optional.empty());
	}
    
	@Override
    protected boolean shouldDespawnInPeaceful() {
	    return !this.isTame();
    } 
	
	@Override
	public boolean removeWhenFarAway(double p_213397_1_) {
		return !(this.isTame() && this.getOwner() instanceof PlayerEntity);
	}
	
	@Override
	public boolean requiresCustomPersistence() {
		return (this.isTame() && this.getOwner() instanceof PlayerEntity) || super.requiresCustomPersistence();
	}
    
    @Override
	public boolean canBeLeashed(PlayerEntity p_184652_1_) {
    	return !this.isLeashed() && this.isTame();
	}
    
    @Override
    public boolean canBeAffected(EffectInstance p_70687_1_) {
        if (p_70687_1_.getEffect() == FUREffectRegistry.INFESTED) {
           net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent event = new net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent(this, p_70687_1_);
           net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
           return event.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW;
        }
        return super.canBeAffected(p_70687_1_);
	}
	
    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT p_213386_5_) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Parasite_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Parasite_Attack.get());
    	this.setHealth(this.getMaxHealth());
    	
    	return livingdata;
    }
	
	@Override
	public void tick() {
        if (this.lifespawn > 0) {
        	if (this.getVehicle() == null) {
        		this.lifespawn--;
        	}
        } else if (!this.isSummoned() && this.getSkin() == 2 && (this.getRandom().nextInt(100) < FURConfig.pEvolveRate_Vespa.get() || this.isTame())) {
        	double d0 = this.getAttributeValue(Attributes.FOLLOW_RANGE);
        	List<PlayerEntity> list = this.level.getEntitiesOfClass(PlayerEntity.class, this.getBoundingBox().inflate(d0));

        	if(!list.isEmpty() || this.isTame()) {
            	this.lifespawn = 5 * 20;
        		
        		if (this.level instanceof ServerWorld) {
        			this.playSound(FURSoundRegistry.PARASITE_WEAVE, 1.0F, 1.0F);
        			
		    		VespaCocoonEntity pupa = SpawnUtil.trySpawnEntity(FUREntityRegistry.VESPACOCOON, ((ServerWorld) this.level), this.blockPosition());
		    		
		    		if (pupa != null) {
			    		pupa.setSkin(0);
			    		
			    		if (this.isTame() && this.getOwner() instanceof PlayerEntity) {
			    			pupa.tame((PlayerEntity) this.getOwner());
			    			pupa.setCustomName(this.getCustomName());
			    		}
		    		}
        		}   
        		
        		this.remove();
        	} else
        		this.hurt(DamageSource.mobAttack(this).bypassInvul().bypassArmor() , this.getMaxHealth());
        } else if (!this.isSummoned() && this.getSkin() == 3 && this.isTame()) {
        	double d0 = this.getAttributeValue(Attributes.FOLLOW_RANGE);
        	List<PlayerEntity> list = this.level.getEntitiesOfClass(PlayerEntity.class, this.getBoundingBox().inflate(d0));

        	if(!list.isEmpty() || this.isTame()) {
            	this.lifespawn = 5 * 20;
        		
            	if (this.level instanceof ServerWorld) {		
        			this.playSound(FURSoundRegistry.PARASITE_WEAVE, 1.0F, 1.0F);
        			
		    		VespaCocoonEntity pupa = SpawnUtil.trySpawnEntity(FUREntityRegistry.BEELZEBUBPUPA, ((ServerWorld) this.level), this.blockPosition());

		    		if (pupa != null && this.isTame() && this.getOwner() instanceof PlayerEntity) {
		    			pupa.tame((PlayerEntity) this.getOwner());
		    			pupa.setCustomName(this.getCustomName());
		    		}
        		}   
        		
        		this.remove();
        	} else {
        		this.hurt(DamageSource.mobAttack(this).bypassInvul().bypassArmor() , this.getMaxHealth());
        	}
        } else {
        	this.hurt(DamageSource.mobAttack(this).bypassInvul().bypassArmor() , this.getMaxHealth());
        }
        	     
        if (this.getVehicle() != null && this.getVehicle() instanceof LivingEntity && this.getVehicle().isAlive() && !this.level.isClientSide()) {
        	Entity mount = this.getVehicle();
        	
        	if (!((LivingEntity) mount).hasEffect(FUREffectRegistry.INFESTED) && !this.isSummoned()) {
        		this.stopRiding();   		
        		this.hurt(DamageSource.mobAttack(this).bypassInvul().bypassArmor() , this.getMaxHealth());
        	} else if (mount.isAlive() && mount.isOnFire()) {
        		this.setRemainingFireTicks(20);
        		this.stopRiding();        		
        	} else if (mount.isAlive() && this.tickCount % 20 == 0) {
        		this.doHurtTarget(mount);
        	}
        	
        }
             
        if (!this.level.isClientSide()) {
            if (this.isOnGround() || this.isInWaterOrBubble() || this.isInLava()) {
                this.entityData.set(ATTACHED_BLK, Direction.DOWN);
            } else if (this.verticalCollision) {
                this.entityData.set(ATTACHED_BLK, Direction.UP);
            } else {
                Direction closestDirection = Direction.DOWN;
                double closestDistance = 100;
                
                for (Direction dir : DIRECTIONS) {
                    BlockPos antPos = new BlockPos(Math.floor(this.getX()), Math.floor(this.getY()), Math.floor(this.getZ()));
                    BlockPos offsetPos = antPos.relative(dir);
                    Vector3d offset = Vector3d.atCenterOf(offsetPos);
                    if (closestDistance > this.position().distanceTo(offset) && level.loadedAndEntityCanStandOnFace(offsetPos, this, dir.getOpposite())) {
                        closestDistance = this.position().distanceTo(offset);
                        closestDirection = dir;
                    }
                }
                
                this.entityData.set(ATTACHED_BLK, closestDirection);
            }
        }      
        super.tick();
    }
	
    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (itemstack.isEmpty() && !this.isTame() && player.isCrouching() && FURConfig.Parasite_Pickup.get()) {
        	player.playSound(SoundEvents.ITEM_PICKUP, 1.0F, 1.0F);
        	
        	if (!player.inventory.add(new ItemStack(ParasiteDrop[this.getSkin()]))) {
                player.spawnAtLocation(new ItemStack(ParasiteDrop[this.getSkin()]));
            }
        	
        	this.remove();
        	
        	return ActionResultType.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }
	
	@Override
	public double getMyRidingOffset() {		
		if (this.isPassenger()) {
			if ((this.getVehicle() instanceof PlayerEntity || this.getVehicle() instanceof ZombieEntity || this.getVehicle() instanceof AbstractVillagerEntity || this.getVehicle() instanceof AbstractRaiderEntity || this.getVehicle() instanceof AbstractSkeletonEntity) && !((LivingEntity)this.getVehicle()).isBaby()) {
				return this.getVehicle().getBbHeight()/2  - 0.85F;
			} else {
				return this.getVehicle().getBbHeight() * 0.65D - 1.0D;
			}
		} else {
			return super.getMyRidingOffset();
		}
	}
	
	@Override
	public boolean doHurtTarget(Entity entity) {
		if (super.doHurtTarget(entity)) {
			if (!this.isSummoned()) {
				((LivingEntity) entity).addEffect(new EffectInstance(FUREffectRegistry.INFESTED, 8*20, 0));		
			}
			
			if(this.getSkin() == 2) {
				((LivingEntity) entity).addEffect(new EffectInstance(Effects.POISON, 4*20, 0));
			}
			
			return true;
		}
		
		return false;
	}
	
	@Override
    public void push(Entity entityIn) {		
		super.push(entityIn);
		ITag<EntityType<?>> tag = EntityTypeTags.getAllTags().getTag(FURTagRegistry.PARASITE_TARGETS);

		if (tag != null && FURConfig.Parasite_Attach.get() && entityIn instanceof LivingEntity && !(entityIn instanceof PlayerEntity) && entityIn.getType().is(tag) && !this.isPassenger()) {
			if (!this.isSummoned()) {
				((LivingEntity) entityIn).addEffect(new EffectInstance(FUREffectRegistry.INFESTED, 8*20, 0));
			}
    		this.startRiding(entityIn);
        }
    }
	
	@Override
	public void playerTouch(PlayerEntity playerIn) {
		super.playerTouch(playerIn);
		if (!playerIn.isCreative() && FURConfig.Parasite_Attach.get() && !this.isPassenger()) {
			if (!this.isSummoned()) {
				playerIn.addEffect(new EffectInstance(FUREffectRegistry.INFESTED, 8*20, 0));
			}
    		this.startRiding(playerIn);
        } 	
	}
	
    public Direction getAttachedBlock() {
        return this.getEntityData().get(ATTACHED_BLK);
    }
	
    public int getSkin() {
        return this.getEntityData().get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
        this.getEntityData().set(SKIN_TYPE, Integer.valueOf(skinType));
    }
    
    public boolean isSummoned() {
    	return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
    }

    public void setSummoned(boolean i) {
		byte b0 = this.entityData.get(DATA_FLAGS_ID);
		if (i) {
			this.entityData.set(DATA_FLAGS_ID, (byte)(b0 | 1));
		} else {
			this.entityData.set(DATA_FLAGS_ID, (byte)(b0 & -2));
		}        
    }

	public boolean isTame() {
		return (this.entityData.get(DATA_FLAGS_ID) & 4) != 0;
	}

	public void setTame(boolean i) {
		byte b0 = this.entityData.get(DATA_FLAGS_ID);
		if (i) {
			this.entityData.set(DATA_FLAGS_ID, (byte)(b0 | 4));
		} else {
			this.entityData.set(DATA_FLAGS_ID, (byte)(b0 & -5));
		}
	}
	
	public void tame(PlayerEntity p_193101_1_) {
		this.setTame(true);
		this.setOwnerUUID(p_193101_1_.getUUID());
	}

	@Nullable
	public UUID getOwnerUUID() {
		return this.entityData.get(DATA_OWNERUUID_ID).orElse((UUID)null);
	}

	public void setOwnerUUID(@Nullable UUID p_184754_1_) {
		this.entityData.set(DATA_OWNERUUID_ID, Optional.ofNullable(p_184754_1_));
	}
	
	@Nullable
	public LivingEntity getOwner() {
		try {
			UUID uuid = this.getOwnerUUID();
			return uuid == null ? null : this.level.getPlayerByUUID(uuid);
		} catch (IllegalArgumentException illegalargumentexception) {
			return null;
		}
	}
	   
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        UUID uuid;
        
        this.setSkin(compound.getInt("Variant"));
        
        if (compound.hasUUID("Owner")) {
           uuid = compound.getUUID("Owner");
        } else {
           String s = compound.getString("Owner");
           uuid = PreYggdrasilConverter.convertMobOwnerIfNecessary(this.getServer(), s);
        }

        if (uuid != null) {
           try {
              this.setOwnerUUID(uuid);
              this.setTame(true);
           } catch (Throwable throwable) {
              this.setTame(false);
           }
        }

    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", getSkin());
        
        if (this.getOwnerUUID() != null) {
        	compound.putUUID("Owner", this.getOwnerUUID());
        }
    }
    
	@Override
    public float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
        return 0.1F;
    }
	
	@Override
	protected boolean isMovementNoisy() {
		return false;
	}
	
	@Override
    protected SoundEvent getAmbientSound() {
        return FURSoundRegistry.PARASITE_AMBIENT;
    }
	
	@Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FURSoundRegistry.PARASITE_HURT;
    }

	@Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.PARASITE_DEATH;
    }
	
	@Override
    protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(SoundEvents.SILVERFISH_STEP, 0.15F, 1.0F);
	} 
	
    @Nullable
    @Override
    protected ResourceLocation getDefaultLootTable() {
    	switch(this.getSkin()) {
    		case 1:
    			return new ResourceLocation("mod_lavacow", "entities/parasite1");
    		case 2:
    			return new ResourceLocation("mod_lavacow", "entities/parasite2");
    		case 0:
    		default:
    			return super.getDefaultLootTable();
    	}
    }
    
    @Override
    protected boolean shouldDropExperience() {
        return !this.isSummoned();
	}

    @Override
	protected boolean shouldDropLoot() {
        return !this.isSummoned() && this.lifespawn > 0;
	}
    
    static class AttackGoal extends MeleeAttackGoal {
        public AttackGoal(ParasiteEntity p_i46676_1_) {
           super(p_i46676_1_, 1.0D, true);
        }

        public boolean canUse() {
           return super.canUse() && !this.mob.isPassenger();
        }

        public boolean canContinueToUse() {
           float f = this.mob.getBrightness();
           if (f >= 0.5F && this.mob.getRandom().nextInt(100) == 0) {
              this.mob.setTarget((LivingEntity)null);
              return false;
           } else {
              return super.canContinueToUse();
           }
        }

        protected double getAttackReachSqr(LivingEntity p_179512_1_) {
           return (double)(0.1F + p_179512_1_.getBbWidth());
        }
     }
}
