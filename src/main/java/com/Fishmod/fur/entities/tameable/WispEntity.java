package com.Fishmod.fur.entities.tameable;

import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
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

import javax.annotation.Nullable;

import com.Fishmod.fur.entities.ICharging;
import com.Fishmod.fur.entities.ai.EntityChargeAttackGoal;
import com.Fishmod.fur.entities.ai.FloatingMoveControl;
import com.Fishmod.fur.entities.ai.FloatingMoveRandomGoal;
import com.Fishmod.fur.entities.ai.FlyerFollowOwnerGoal;
import com.Fishmod.fur.entities.ai.WispSwellGoal;
import com.Fishmod.fur.init.FURItemRegistry;
import com.Fishmod.fur.init.FURParticleRegistry;

/**

Updated for Minecraft Forge 1.20.1 using Mojang mappings.
*/
public class WispEntity extends FURTameableEntity implements ICharging, GeoEntity {
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	
    private static final RawAnimation FLOAT = RawAnimation.begin().thenPlay("wisp.model.floating");
    //private static final RawAnimation SPIN = RawAnimation.begin().thenPlay("wisp.model.spinning");
    private static final RawAnimation CHARGE = RawAnimation.begin().thenPlay("wisp.model.charging");
    private static final RawAnimation CAST = RawAnimation.begin().thenPlay("wisp.model.casting");
    
	private static final EntityDataAccessor<Integer> SKIN_TYPE = SynchedEntityData.defineId(WispEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> DATA_SWELL_DIR = SynchedEntityData.defineId(WispEntity.class, EntityDataSerializers.INT);

	private int oldSwell;
	private int swell;
	private int maxSwell = 30;
	public boolean isCharging = false;
	
	public WispEntity(EntityType<? extends WispEntity> type, Level level) {
		super(type, level);
		this.moveControl = new FloatingMoveControl(this);
		this.setNoGravity(true);
	}

    /**
     * Tries to move the entity towards the specified location.
     */	
	@Override
	public void move(MoverType type, Vec3 pos) {
		super.move(type, pos);
		this.checkInsideBlocks();
	}

    @Override
    protected boolean isCommandable() {
    	return false;
    }
 
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(2, new WispSwellGoal(this));
		//this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, WarpedFireflyEntity.class, 6.0F, 1.0D, 1.2D));
		this.goalSelector.addGoal(3, new EntityChargeAttackGoal(this));
		this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));  
        this.goalSelector.addGoal(8, new FloatingMoveRandomGoal(this));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
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

    @Override
    protected Goal wanderGoal() {
    	return new FloatingMoveRandomGoal(this);
    }
    
    @Override
    protected Goal followGoal() {
    	return new FlyerFollowOwnerGoal(this, 1.0D, 10.0F, 4.0F, true, 24.0D);
    }

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.15D)
        		.add(Attributes.FOLLOW_RANGE, 16.0D)
        		.add(Attributes.MAX_HEALTH, 8.0D/*FURConfig.Wisp_Health.get()*/)
        		.add(Attributes.ATTACK_DAMAGE, 1.0D)
        		.add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
	}

    public static boolean checkWispSpawnRules(EntityType<? extends WispEntity> p_223316_0_, ServerLevelAccessor p_223316_1_, MobSpawnType p_223316_2_, BlockPos p_223316_3_, RandomSource p_223316_4_) {
        return FURTameableEntity.checkMonsterSpawnRules(p_223316_0_, p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_);
    }

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SKIN_TYPE, 0);
		this.entityData.define(DATA_SWELL_DIR, -1);
	}

    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void aiStep() {
        super.aiStep();
        
        if (this.tickCount % 2 == 0 && this.ParticleType() != null) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            this.level().addParticle(this.ParticleType(), this.getRandomX(0.5D), this.getRandomY() + (this.getBbHeight() * 0.5D), this.getRandomZ(0.5D), d0, d1, d2);
        }     	
    }
	
	@Override
	public void tick() {
		if (this.isAlive() && (!this.isTame() || (this.isTame()/* && FURConfig.Wisp_Tamed_Explosion.get()*/))) {
			this.oldSwell = this.swell;

            int i = this.getSwellDir();
            if (i > 0 && this.swell == 0) {
               this.playSound(SoundEvents.CREEPER_PRIMED, 1.0F, 0.5F);
            }

            this.swell += i;
            this.level().broadcastEntityEvent(this, (byte) 4);
            if (this.swell < 0) {
               this.swell = 0;
            }

            if (this.swell >= this.maxSwell) {
               this.swell = this.maxSwell;
               this.isCharging = false;
               this.level().broadcastEntityEvent(this, (byte) 5);
               this.explodeWisp();
            }
		}
         
		if (this.isGastly() && this.getSkin() != 3) {
			this.setSkin(3);
		}      

        if (this.isAggressive()) this.noPhysics = true;
     	super.tick();
     	this.noPhysics = false;
     	this.setNoGravity(true);
	}

    private void explodeWisp() {
        if (!this.level().isClientSide()) {
           this.dead = true;
           this.level().explode(this, this.getX(), this.getY(), this.getZ(), 3.0F/*FURConfig.Wisp_ExplosionPower.get().floatValue()*/, net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level(), this), Level.ExplosionInteraction.NONE);
           if(this.isTame())
        	   this.spawnAtLocation(this.getAshes(), 1); 
           this.discard();
        }
	}

	@Override
	public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
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
    	if(source.is(DamageTypeTags.IS_EXPLOSION))
    		return false;
    				
		return super.hurt(source, amount);
    }

    protected ItemStack getAshes() {
    	ItemStack stack = new ItemStack(FURItemRegistry.WISP_ASHES.get());
    	CompoundTag compoundnbt = new CompoundTag();
        this.addAdditionalSaveData(compoundnbt);
        stack.getOrCreateTag().put("WispData", compoundnbt);
        
        if (this.hasCustomName()) {
            stack.setHoverName(this.getCustomName());
        }
        
        return stack;
    }
    
    protected ItemStack getFishBucket() {
    	ItemStack stack = new ItemStack(FURItemRegistry.WISP_IN_A_BOTTLE.get());
    	CompoundTag compoundnbt = new CompoundTag();
        this.addAdditionalSaveData(compoundnbt);
        stack.getOrCreateTag().put("WispData", compoundnbt);
        
        if (this.hasCustomName()) {
            stack.setHoverName(this.getCustomName());
        }
        
        return stack;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
    	ItemStack itemstack = player.getItemInHand(hand);   	
        
        if (itemstack.getItem() == Items.GLASS_BOTTLE && this.isAlive()) {
        	this.tame(player);
            this.playSound(SoundEvents.BOTTLE_FILL, 1.0F, 1.0F);
            itemstack.shrink(1);
            ItemStack itemstack1 = this.getFishBucket();
            if (!this.level().isClientSide()) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, itemstack1);
            }

            if (itemstack.isEmpty()) {
                player.setItemInHand(hand, itemstack1);
            } else if (!player.getInventory().add(itemstack1)) {
                player.drop(itemstack1, false);
            }

            this.discard();            
            return InteractionResult.sidedSuccess(this.level().isClientSide());
        }
        
        return super.mobInteract(player, hand);
    }

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData data, @Nullable CompoundTag tag) {
        /*this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Wisp_Health.get());
    	this.setHealth(this.getMaxHealth());*/
		
		if (reason != MobSpawnType.BUCKET) {
			this.entityData.set(SKIN_TYPE, this.random.nextInt(3));
			
			if (world.getBiome(this.blockPosition()).is(Biomes.NETHER_WASTES)) {
			   this.setSkin(1);
	    	} else if (world.getBiome(this.blockPosition()).is(Biomes.SOUL_SAND_VALLEY)) {
	 		   this.setSkin(0);
	     	} else if (world.getBiome(this.blockPosition()).is(Biomes.BASALT_DELTAS)) {
	  		   this.setSkin(2);
	      	}
	    			
			this.setDeltaMovement(this.getDeltaMovement().add(0.0D, 0.2D, 0.0D));
		}
		
		return super.finalizeSpawn(world, difficulty, reason, data, tag);
	}

    @Override
    public float getWalkTargetValue(BlockPos p_205022_1_, LevelReader p_205022_2_) {
    	if (p_205022_2_.getBrightness(LightLayer.BLOCK, p_205022_1_) > 11) {
    		return -1.0F;
    	} else {
    		return super.getWalkTargetValue(p_205022_1_, p_205022_2_);
    	}
    }
    
    protected PathNavigation createNavigation(Level p_175447_1_) {
    	FlyingPathNavigation flyingpathnavigator = new FlyingPathNavigation(this, p_175447_1_) {
           public boolean isStableDestination(BlockPos p_188555_1_) {
              return !this.level.getBlockState(p_188555_1_.below()).isAir();
           }
        };
        flyingpathnavigator.setCanOpenDoors(false);
        flyingpathnavigator.setCanFloat(true);
        flyingpathnavigator.setCanPassDoors(true);
        return flyingpathnavigator;
	}
    
    @Nullable
    protected SimpleParticleType ParticleType() { 
    	switch(this.getSkin()) {
			case 1:
				return ParticleTypes.FLAME;
			case 2:
				return ParticleTypes.POOF;
			case 3:
				return FURParticleRegistry.GHOST_FLAME.get();
			case 0:
			default:
				return ParticleTypes.SOUL_FIRE_FLAME;			
		}
    }
    
    public float getSwelling(float p_70831_1_) {
       return Mth.lerp(p_70831_1_, (float)this.oldSwell, (float)this.swell) / (float)(this.maxSwell - 2);
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
    
	public boolean isCharging() {
        return this.isCharging;
	}

	public void setIsCharging(boolean bool) {
        this.isCharging = bool;
	}
    
    @Override
    protected float getStandingEyeHeight(Pose p_213348_1_, EntityDimensions p_213348_2_) {
        return p_213348_2_.height * 0.5F;
    }
    
    private boolean isGastly() {
    	String s = ChatFormatting.stripFormatting(this.getName().getString());
        return s != null && s.toLowerCase().contains("gastly");
    }
    
	@Override
	protected SoundEvent getAmbientSound() {
		return null;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
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
    protected void playStepSound(BlockPos pos, BlockState state) {
	} 
	
	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
        if (tag.contains("Fuse", 99)) {
            this.maxSwell = tag.getShort("Fuse");
        }
        this.setSkin(tag.getInt("Variant"));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		tag.putShort("Fuse", (short)this.maxSwell);
		tag.putInt("Variant", getSkin());
	}

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }
    
    /**
     * Handler for {@link World#setEntityState}
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
    	if (id == 4) {
    		this.swell += this.getSwellDir();
    	} else if (id == 5) {
    		this.swell = this.maxSwell;
    		this.isCharging = false;
		} else if (id == 6) {	
    		this.isCharging = true;
    	} else {
            super.handleEntityEvent(id);
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
	
    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
    	if (this.isCharging) {
    		state.getController().setAnimation(CHARGE);
    	} else if (this.swell > 0 && this.swell <= this.maxSwell) {
    		state.getController().setAnimation(CAST);
    	/*} else if (state.isMoving() && this.random.nextFloat() < 0.05F) {
			state.getController().setAnimation(SPIN);*/
        } else {
            state.getController().setAnimation(FLOAT);
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