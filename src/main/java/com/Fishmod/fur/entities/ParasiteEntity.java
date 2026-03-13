package com.Fishmod.fur.entities;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.core.SpawnUtil;
import com.Fishmod.fur.entities.tameable.CocoonEntity;
import com.Fishmod.fur.init.FUREffectRegistry;
import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURItemRegistry;
import com.Fishmod.fur.init.FURSoundRegistry;
import com.Fishmod.fur.init.FURTagRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.damagesource.DamageSource;


public class ParasiteEntity extends Spider {
	private static final EntityDataAccessor<Integer> SKIN_TYPE = SynchedEntityData.defineId(ParasiteEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Direction> ATTACHED_BLK = SynchedEntityData.defineId(ParasiteEntity.class, EntityDataSerializers.DIRECTION);
	protected static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(ParasiteEntity.class, EntityDataSerializers.BYTE);
	protected static final EntityDataAccessor<Optional<UUID>> DATA_OWNERUUID_ID = SynchedEntityData.defineId(ParasiteEntity.class, EntityDataSerializers.OPTIONAL_UUID);
	private static final Direction[] DIRECTIONS = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
	public int lifespawn;
	
	public ParasiteEntity(EntityType<? extends ParasiteEntity> p_i48549_1_, Level worldIn) {
        super(p_i48549_1_, worldIn);
        this.lifespawn = FURConfig.Parasite_Lifespan.get() * 20; // Can live for 16s only, poor little one :(
        this.xpReward = 1;
    }
	
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));
		this.goalSelector.addGoal(4, new ParasiteEntity.AttackGoal(this));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.applyEntityAI();
    }
	
    protected void applyEntityAI() {
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 0, true, true, (p_213440_0_) -> {
            	return !p_213440_0_.isPassenger() && !this.isTame();
        	}));
    	this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 0, true, true, (p_210136_0_) -> {
    		return ((LivingEntity)p_210136_0_).attackable() && p_210136_0_.getType().is(FURTagRegistry.PARASITE_TARGETS) && !this.isTame();
    	}));	
    }
    
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.2D)
        		.add(Attributes.FOLLOW_RANGE, 8.0D)
        		.add(Attributes.MAX_HEALTH, 6.0D)
        		.add(Attributes.ATTACK_DAMAGE, 1.0D);
    }
    
    @Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SKIN_TYPE, Integer.valueOf(this.getRandom().nextInt(4)));
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
		return !(this.isTame() && this.getOwner() instanceof Player);
	}
	
	@Override
	public boolean requiresCustomPersistence() {
		return (this.isTame() && this.getOwner() instanceof Player) || super.requiresCustomPersistence();
	}
    
    @Override
	public boolean canBeLeashed(Player p_184652_1_) {
    	return !this.isLeashed() && this.isTame();
	}
    
    @Override
    public boolean canBeAffected(MobEffectInstance p_70687_1_) {
        if (p_70687_1_.getEffect() == FUREffectRegistry.INFESTED.get()) {
        	return false;
        }
        
        return super.canBeAffected(p_70687_1_);
	}
	
    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_213386_1_, DifficultyInstance difficulty, MobSpawnType p_213386_3_, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag p_213386_5_) {
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
        	List<Player> list = this.level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(d0));

        	if(!list.isEmpty() || this.isTame()) {
            	this.lifespawn = 5 * 20;
        		
        		if (this.level() instanceof ServerLevel server) {
        			this.playSound(FURSoundRegistry.PARASITE_WEAVE.get(), 1.0F, 1.0F);
        			
		    		CocoonEntity pupa = SpawnUtil.trySpawnEntity(FUREntityRegistry.COCOON.get(), server, this.blockPosition());
		    		
		    		if (pupa != null) {
			    		pupa.setSkin(0);
			    		
			    		if (this.isTame() && this.getOwner() instanceof Player) {
			    			pupa.tame((Player) this.getOwner());
			    			pupa.setCustomName(this.getCustomName());
			    		}
		    		}
        		}   
        		
        		this.discard();
        	} else
        		this.kill();
        /*} else if (!this.isSummoned() && this.getSkin() == 3 && this.isTame()) {
        	double d0 = this.getAttributeValue(Attributes.FOLLOW_RANGE);
        	List<Player> list = this.level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(d0));

        	if(!list.isEmpty() || this.isTame()) {
            	this.lifespawn = 5 * 20;
        		
            	if (this.level() instanceof ServerLevel) {		
        			this.playSound(FURSoundRegistry.PARASITE_WEAVE.get(), 1.0F, 1.0F);
        			
		    		CocoonEntity pupa = SpawnUtil.trySpawnEntity(FUREntityRegistry.BEELZEBUBPUPA, ((ServerLevel) this.level), this.blockPosition());

		    		if (pupa != null && this.isTame() && this.getOwner() instanceof Player) {
		    			pupa.tame((Player) this.getOwner());
		    			pupa.setCustomName(this.getCustomName());
		    		}
        		}   
        		
        		this.discard();
        	} else {
        		this.kill();
        	}*/
        } else {
        	this.kill();
        }
        	     
        if (this.getVehicle() != null && this.getVehicle() instanceof LivingEntity && this.getVehicle().isAlive() && !this.level().isClientSide()) {
        	Entity mount = this.getVehicle();
        	
        	if (!((LivingEntity) mount).hasEffect(FUREffectRegistry.INFESTED.get()) && !this.isSummoned()) {
        		this.stopRiding();   		
        		this.kill();
        	} else if (mount.isAlive() && mount.isOnFire()) {
        		this.setRemainingFireTicks(20);
        		this.stopRiding();        		
        	} else if (mount.isAlive() && this.tickCount % 20 == 0) {
        		this.doHurtTarget(mount);
        	}
        	
        }
             
        if (!this.level().isClientSide()) {
            if (this.onGround() || this.isInWaterOrBubble() || this.isInLava()) {
                this.entityData.set(ATTACHED_BLK, Direction.DOWN);
            } else if (this.verticalCollision) {
                this.entityData.set(ATTACHED_BLK, Direction.UP);
            } else {
                Direction closestDirection = Direction.DOWN;
                double closestDistance = 100;
                
                for (Direction dir : DIRECTIONS) {
                    BlockPos antPos = new BlockPos((int)Math.floor(this.getX()), (int)Math.floor(this.getY()), (int)Math.floor(this.getZ()));
                    BlockPos offsetPos = antPos.relative(dir);
                    Vec3 offset = Vec3.atCenterOf(offsetPos);
                    if (closestDistance > this.position().distanceTo(offset) && this.level().loadedAndEntityCanStandOnFace(offsetPos, this, dir.getOpposite())) {
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
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (itemstack.isEmpty() && !this.isTame() && player.isCrouching() && FURConfig.Parasite_Pickup.get()) {
            ItemStack stack1 = new ItemStack(FURItemRegistry.PARASITE_RAW.get());
            stack1.getOrCreateTag().putInt("variant", this.getSkin());
            
        	player.playSound(SoundEvents.ITEM_PICKUP, 1.0F, 1.0F);
          
        	if (!player.getInventory().add(stack1)) {
                player.spawnAtLocation(stack1);
            }
        	
        	this.discard();
        	
        	return InteractionResult.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }
	
	@Override
	public double getMyRidingOffset() {		
		if (this.isPassenger()) {
			if ((this.getVehicle() instanceof Player || this.getVehicle() instanceof Zombie || this.getVehicle() instanceof AbstractVillager || this.getVehicle() instanceof AbstractIllager || this.getVehicle() instanceof AbstractSkeleton) && !((LivingEntity)this.getVehicle()).isBaby()) {
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
				((LivingEntity) entity).addEffect(new MobEffectInstance(FUREffectRegistry.INFESTED.get(), 8*20, 0));		
			}
			
			if(this.getSkin() == 2) {
				((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.POISON, 4*20, 0));
			}
			
			return true;
		}
		
		return false;
	}
	
	@Override
    public void push(Entity entityIn) {		
		super.push(entityIn);
		
		if (FURConfig.Parasite_Attach.get() && entityIn instanceof LivingEntity && !(entityIn instanceof Player) && entityIn.getType().is(FURTagRegistry.PARASITE_TARGETS) && !this.isPassenger()) {
			if (!this.isSummoned()) {
				((LivingEntity) entityIn).addEffect(new MobEffectInstance(FUREffectRegistry.INFESTED.get(), 8*20, 0));
			}
    		this.startRiding(entityIn);
        }
    }
	
	@Override
	public void playerTouch(Player playerIn) {
		super.playerTouch(playerIn);
		if (!playerIn.isCreative() && FURConfig.Parasite_Attach.get() && !this.isPassenger()) {
			if (!this.isSummoned()) {
				playerIn.addEffect(new MobEffectInstance(FUREffectRegistry.INFESTED.get(), 8*20, 0));
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
	
	public void tame(Player p_193101_1_) {
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
			return uuid == null ? null : this.level().getPlayerByUUID(uuid);
		} catch (IllegalArgumentException illegalargumentexception) {
			return null;
		}
	}
	   
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        UUID uuid;
        
        this.setSkin(compound.getInt("Variant"));
        
        if (compound.hasUUID("Owner")) {
           uuid = compound.getUUID("Owner");
        } else {
           String s = compound.getString("Owner");
           uuid = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), s);
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
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", getSkin());
        
        if (this.getOwnerUUID() != null) {
        	compound.putUUID("Owner", this.getOwnerUUID());
        }
    }
    
	@Override
    public float getStandingEyeHeight(Pose p_213348_1_, EntityDimensions p_213348_2_) {
        return 0.1F;
    }
	
	@Override
	public boolean dampensVibrations() {
		return true;
	}
	
	@Override
    protected SoundEvent getAmbientSound() {
        return FURSoundRegistry.PARASITE_AMBIENT.get();
    }
	
	@Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FURSoundRegistry.PARASITE_HURT.get();
    }

	@Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.PARASITE_DEATH.get();
    }
	
	@Override
    protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(SoundEvents.SILVERFISH_STEP, 0.15F, 1.0F);
	}	
    
    @Override
	public boolean shouldDropExperience() {
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
           float f = this.mob.level().getBrightness(LightLayer.SKY, this.mob.blockPosition());
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
