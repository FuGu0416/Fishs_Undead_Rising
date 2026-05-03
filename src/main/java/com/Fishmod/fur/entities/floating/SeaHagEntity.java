package com.Fishmod.fur.entities.floating;

import java.util.EnumSet;
import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.core.SpawnUtil;
import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURSoundRegistry;
import com.Fishmod.fur.entities.ai.EntityChargeAttackGoal;
import com.Fishmod.fur.entities.aquatic.SwarmerEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Pufferfish;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SeaHagEntity extends FloatingMobEntity implements GeoEntity {
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	
    private static final RawAnimation IDLE = RawAnimation.begin().thenPlay("wraith.model.idle");
    private static final RawAnimation FLOAT = RawAnimation.begin().thenPlay("wraith.model.floating");
    private static final RawAnimation ATTACK = RawAnimation.begin().thenPlay("wraith.model.attacking");
    private static final RawAnimation CAST = RawAnimation.begin().thenPlay("wraith.model.casting");
    
    private static final EntityDataAccessor<Integer> SKIN_TYPE = SynchedEntityData.defineId(SeaHagEntity.class, EntityDataSerializers.INT);
	public static final int SPELL_TIMER = 45;
	
	public SeaHagEntity(EntityType<? extends SeaHagEntity> p_i48549_1_, Level worldIn) {
        super(p_i48549_1_, worldIn);
        this.setPathfindingMalus(BlockPathTypes.WATER, 8.0F);      
    }
	
    @Override
    protected void registerGoals() {   	
    	super.registerGoals();    
    	this.goalSelector.addGoal(1, new SeaHagEntity.GoToWaterGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new EntityChargeAttackGoal(this));
		this.goalSelector.addGoal(3, new SeaHagEntity.AIUseSpell());
    }

    @Override
    protected void applyEntityAI() {
    	this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    	this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    protected Goal wanderGoal() {
    	return new SeaHagEntity.AIMoveRandom();
    }
    
    @Override
	public float getLightLevelDependentMagicValue() {
		return 1.0F;
	}
    
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.175D)
        		.add(Attributes.FOLLOW_RANGE, 32.0D)
        		.add(Attributes.MAX_HEALTH, 30.0D)
        		.add(Attributes.ATTACK_DAMAGE, 5.0D);
    }
    
	public static boolean checkSeaHagSpawnRules(EntityType<SeaHagEntity> p_223332_0_, ServerLevelAccessor p_223332_1_, MobSpawnType p_223332_2_, BlockPos p_223332_3_, RandomSource p_223332_4_) {
        boolean flag = p_223332_1_.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawn(p_223332_1_, p_223332_3_, p_223332_4_) && (p_223332_2_ == MobSpawnType.SPAWNER || p_223332_1_.getFluidState(p_223332_3_).is(FluidTags.WATER));

    	return p_223332_4_.nextInt(5) == 0 && flag;
	}
    
    @Override
    protected void defineSynchedData() {
    	super.defineSynchedData();
    	this.getEntityData().define(SKIN_TYPE, Integer.valueOf(1));
    }
    
    @Override
    public boolean checkSpawnObstruction(LevelReader p_205019_1_) {
        return p_205019_1_.isUnobstructed(this);
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficulty, MobSpawnType p_213386_3_, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag p_213386_5_) {
    	return super.finalizeSpawn(worldIn, difficulty, p_213386_3_, livingdata, p_213386_5_);
    }
    
    @Override
    @Nullable
    protected SimpleParticleType ParticleType() {   	
    	return this.isInWater() ? null : ParticleTypes.FALLING_WATER;
    }
    
	/**
	* Called when the entity is attacked.
	*/
    @Override
	public boolean hurt(DamageSource source, float amount) {
    	if(source.getEntity() instanceof Pufferfish)
    		return false;

    	return super.hurt(source, amount);
    }	  
    
    /**
     * Handler for {@link World#setEntityState}
     */
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        if (id == 10) {
        	this.triggerAnim("trigger_controller", "cast");
        	this.spellTicks = SPELL_TIMER;
        } else if (id == 4) {
        	this.triggerAnim("trigger_controller", "attack");
        } else {
            super.handleEntityEvent(id);
        }
    }
    
    public class AIUseSpell extends Goal {
        protected int spellWarmup;
        protected int spellCooldown;

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean canUse() {
        	if (SeaHagEntity.this.isSpellcasting()) {
                return false;
            } else {
                int i = SeaHagEntity.this.level().getEntitiesOfClass(Pufferfish.class, SeaHagEntity.this.getBoundingBox().inflate(16.0D)).size();               
            	return SeaHagEntity.this.tickCount >= this.spellCooldown 
            			&& ((SeaHagEntity.this.getTarget() != null 
            			&& Math.abs(SeaHagEntity.this.getY() - SeaHagEntity.this.getTarget().getY()) < 4.0D)) 
            			&& i < FURConfig.SeaHag_Ability_Max.get();
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.spellWarmup = this.getCastWarmupTime();
            SeaHagEntity.this.spellTicks = this.getCastingTime();
            SeaHagEntity.this.level().broadcastEntityEvent(SeaHagEntity.this, (byte)10);
            this.spellCooldown = SeaHagEntity.this.tickCount + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();

            if (soundevent != null) {
                SeaHagEntity.this.playSound(soundevent, 1.0F, 1.0F);
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            --this.spellWarmup;

            if (this.spellWarmup == 5) {
        		this.castSpell();        	
                SeaHagEntity.this.playSound(SeaHagEntity.this.getSpellSound(), 4.0F, 1.2F);                         
            }
        }
       
        protected void castSpell() {
            for (int i = 0; i < FURConfig.SeaHag_Ability_Num.get(); ++i) {
            	if (SeaHagEntity.this.level() instanceof ServerLevel) {
	                BlockPos blockpos = SeaHagEntity.this.blockPosition().offset(-2 + SeaHagEntity.this.getRandom().nextInt(3), 1, -2 + SeaHagEntity.this.getRandom().nextInt(3));
	                Mob entity;
	                
	                if (SeaHagEntity.this.isInWaterOrBubble()) {
	                	entity = SpawnUtil.trySpawnEntity(FUREntityRegistry.SWARMER.get(), ((ServerLevel) SeaHagEntity.this.level()), blockpos);
	                	
	                	if (entity != null) {
	                		((SwarmerEntity) entity).setSkin(5);
	                		entity.setTarget(SeaHagEntity.this.getTarget());
	                	}
	                } else {
	                	entity = SpawnUtil.trySpawnEntity(EntityType.PUFFERFISH, ((ServerLevel) SeaHagEntity.this.level()), blockpos);
	                }
	                
	                if (entity != null) {
	                	entity.setAirSupply(80);
	                	entity.addTag("FUR_noLoot");	
	
		                for (int j = 0; j < 4; ++j) {
		                	double d0 = entity.getX() + (double)(SeaHagEntity.this.getRandom().nextFloat() * entity.getBbWidth() * 2.0F) - (double)entity.getBbWidth();
		                	double d1 = entity.getY() + (double)(SeaHagEntity.this.getRandom().nextFloat() * entity.getBbHeight());
		                	double d2 = entity.getZ() + (double)(SeaHagEntity.this.getRandom().nextFloat() * entity.getBbWidth() * 2.0F) - (double)entity.getBbWidth();
		                	((ServerLevel) SeaHagEntity.this.level()).sendParticles(ParticleTypes.BUBBLE_COLUMN_UP, d0, d1, d2, 15, 0.0D, 0.0D, 0.0D, 0.0D);	                	
		                }
	                }
	                
                }
            }
        }

        protected int getCastWarmupTime() {
            return 20;
        }

        protected int getCastingTime() {
            return SPELL_TIMER;
        }

        protected int getCastingInterval() {
        	return FURConfig.SeaHag_Ability_Cooldown.get() * 20;
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound() {
            return null;
        }
    }
    
    static class GoToWaterGoal extends Goal {
        private final SeaHagEntity mob;
        private double wantedX;
        private double wantedY;
        private double wantedZ;
        private final double speedModifier;
        private final Level level;

        public GoToWaterGoal(SeaHagEntity p_i48910_1_, double p_i48910_2_) {
           this.mob = p_i48910_1_;
           this.speedModifier = p_i48910_2_;
           this.level = p_i48910_1_.level();
           this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean canUse() {
           if (!this.level.isDay()) {
              return false;
           } else if (this.mob.isInWater()) {
              return false;
           } else {
              Vec3 vector3d = this.getWaterPos();
              if (vector3d == null) {
                 return false;
              } else {
                 this.wantedX = vector3d.x;
                 this.wantedY = vector3d.y;
                 this.wantedZ = vector3d.z;
                 return true;
              }
           }
        }

        public boolean canContinueToUse() {
        	return !this.mob.getNavigation().isDone();
        }

        public void start() {
        	this.mob.getMoveControl().setWantedPosition(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
        }

        @Nullable
        private Vec3 getWaterPos() {
           RandomSource random = this.mob.getRandom();
           BlockPos blockpos = this.mob.blockPosition();

           for(int i = 0; i < 10; ++i) {
              BlockPos blockpos1 = blockpos.offset(random.nextInt(20) - 10, 2 - random.nextInt(8), random.nextInt(20) - 10);
              if (this.level.getBlockState(blockpos1).is(Blocks.WATER)) {
                 return Vec3.atBottomCenterOf(blockpos1);
              }
           }

           return null;
        }
	}
    
    class AIMoveRandom extends Goal {
        public AIMoveRandom() {
        	this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean canUse() {
            return !SeaHagEntity.this.getMoveControl().hasWanted() && SeaHagEntity.this.getRandom().nextInt(7) == 0;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return false;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {            
            BlockPos blockpos = SeaHagEntity.this.blockPosition();
            int groundHeight = SpawnUtil.getHeight(SeaHagEntity.this).getY();
            int y = SeaHagEntity.this.getRandom().nextInt(11) - 5;

            if (groundHeight > 0) {
            	y = Math.min(groundHeight + 4 - blockpos.getY(), y);
            }

            for(int i = 0; i < 3; ++i) {
               BlockPos blockpos1 = blockpos.offset(SeaHagEntity.this.random.nextInt(15) - 7, y, SeaHagEntity.this.random.nextInt(15) - 7);
               if ((!SeaHagEntity.this.level().isDay() && SeaHagEntity.this.level().isEmptyBlock(blockpos1)) || SeaHagEntity.this.level().isWaterAt(blockpos1)) {
                  SeaHagEntity.this.moveControl.setWantedPosition((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 0.25D);
                  if (SeaHagEntity.this.getTarget() == null) {
                     SeaHagEntity.this.getLookControl().setLookAt((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 180.0F, 20.0F);
                  }
                  break;
               }
            }
        }
    }
    
    public int getSkin() {
        return this.getEntityData().get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
        this.getEntityData().set(SKIN_TYPE, Integer.valueOf(skinType));
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return FURSoundRegistry.SEAHAG_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FURSoundRegistry.BANSHEE_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.SEAHAG_DEATH.get();
    }
    
    protected SoundEvent getSpellSound() {
        return SoundEvents.BUBBLE_COLUMN_UPWARDS_AMBIENT;
    }
    
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setSkin(compound.getInt("Variant"));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", getSkin());
    }
	
    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
    	if (state.isMoving()) {
            state.getController().setAnimation(FLOAT);
        } else {
            state.getController().setAnimation(IDLE);
        }
        
        return PlayState.CONTINUE;
    }
    
	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "controller", 5, this::predicate));
		controllers.add(new AnimationController<>(this, "trigger_controller", 5, state -> PlayState.STOP).triggerableAnim("attack", ATTACK).triggerableAnim("cast", CAST));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
