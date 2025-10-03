package com.Fishmod.fur.entities.floating;

import javax.annotation.Nullable;

import com.Fishmod.fur.core.SpawnUtil;
import com.Fishmod.fur.entities.tameable.WetaEntity;
import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURParticleRegistry;
import com.Fishmod.fur.init.FURSoundRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MoveThroughVillageGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class AvatonEntity extends FloatingMobEntity implements GeoEntity {
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final RawAnimation IDLE = RawAnimation.begin().thenPlay("wraith.model.idle");
    private static final RawAnimation FLOAT = RawAnimation.begin().thenPlay("wraith.model.floating");
    private static final RawAnimation ATTACK = RawAnimation.begin().thenPlay("wraith.model.attacking");
    private static final RawAnimation CAST = RawAnimation.begin().thenPlay("wraith.model.casting");
    
	private static final EntityDataAccessor<Integer> SKIN_TYPE = SynchedEntityData.defineId(AvatonEntity.class, EntityDataSerializers.INT);
	public static final int ATTACK_TIMER = 30;
	public static final int SPELL_TIMER = 45;
	
	public AvatonEntity(EntityType<? extends AvatonEntity> p_i48549_1_, Level worldIn) {
        super(p_i48549_1_, worldIn);
    }
	
    @Override
    protected void registerGoals() {
    	super.registerGoals();  
    	this.goalSelector.addGoal(3, new AvatonEntity.AIUseSpell());
    	this.goalSelector.addGoal(6, new MoveThroughVillageGoal(this, 1.0D, true, 4, this::canBreakDoors));
    }

    @Override
    protected void applyEntityAI() {
    	this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    	this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }
    
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.175D)
        		.add(Attributes.FOLLOW_RANGE, 32.0D)
        		.add(Attributes.MAX_HEALTH, 30.0D/*FURConfig.Avaton_Health.get()*/)
        		.add(Attributes.ATTACK_DAMAGE, 5.0D/*FURConfig.Avaton_Attack.get()*/);
    }
    
    public static boolean checkAvatonSpawnRules(EntityType<? extends AvatonEntity> p_223316_0_, ServerLevelAccessor p_223316_1_, MobSpawnType p_223316_2_, BlockPos p_223316_3_, RandomSource p_223316_4_) {
        return Monster.checkMonsterSpawnRules(p_223316_0_, p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_);
    }
        
    @Override
    protected void defineSynchedData() {
    	super.defineSynchedData();
    	this.getEntityData().define(SKIN_TYPE, Integer.valueOf(0));
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficulty, MobSpawnType p_213386_3_, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag p_213386_5_) {
    	/*this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Avaton_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Avaton_Attack.get());
    	this.setHealth(this.getMaxHealth());*/  		

    	return super.finalizeSpawn(worldIn, difficulty, p_213386_3_, livingdata, p_213386_5_);
    }
    
    @Override
    @Nullable
    protected SimpleParticleType ParticleType() {
    	return FURParticleRegistry.LOCUST_SWARM.get();
    }
    
    protected boolean isSunBurnTick() {
		return false;
    }
    
    public boolean canBreakDoors() {
        return false;
	}
    	    
    public class AIUseSpell extends Goal {
        protected int spellWarmup;
        protected int spellCooldown;

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean canUse() {
        	if (AvatonEntity.this.isSpellcasting()) {
                return false;
            } else {
                int i = AvatonEntity.this.level().getEntitiesOfClass(WetaEntity.class, AvatonEntity.this.getBoundingBox().inflate(16.0D)).size();
                boolean farmlandnearby = false;
                for(int x = -2; x <= 2; x++)
                	for(int y = -3; y <= 3; y++)
                		for(int z = -2; z <= 2; z++) {
                			if (AvatonEntity.this.level().getBlockState(AvatonEntity.this.blockPosition().offset(x, y, z)).getBlock() == Blocks.FARMLAND)
                				farmlandnearby = true;
                		}              
                
            	return AvatonEntity.this.tickCount >= this.spellCooldown && ((AvatonEntity.this.getTarget() != null && Math.abs(AvatonEntity.this.getY() - AvatonEntity.this.getTarget().getY()) < 4.0D) || farmlandnearby) && i < 16/*FURConfig.Avaton_Ability_Max.get()*/;
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
            AvatonEntity.this.spellTicks = this.getCastingTime();
            AvatonEntity.this.level().broadcastEntityEvent(AvatonEntity.this, (byte)10);
            this.spellCooldown = AvatonEntity.this.tickCount + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();

            if (soundevent != null) {
                AvatonEntity.this.playSound(soundevent, 1.0F, 1.0F);
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            --this.spellWarmup;

            if (this.spellWarmup == 5) {
            	this.castSpell();           	
                AvatonEntity.this.playSound(AvatonEntity.this.getSpellSound(), 4.0F, 1.2F);                         
            }
        }

        protected void castSpell() {
            for (int i = 0; i < 2/*FURConfig.Avaton_Ability_Num.get()*/; ++i) {
            	if (AvatonEntity.this.level() instanceof ServerLevel) {
	                BlockPos blockpos = AvatonEntity.this.blockPosition().offset(-2 + AvatonEntity.this.getRandom().nextInt(5), 1, -2 + AvatonEntity.this.getRandom().nextInt(5));
	                WetaEntity entity = SpawnUtil.trySpawnEntity(FUREntityRegistry.WETA.get(), ((ServerLevel) AvatonEntity.this.level()), blockpos);
	                
	                if (entity != null) {
		                entity.setOwnerUUID(AvatonEntity.this.getUUID());                             
		                
		                if(AvatonEntity.this.getTarget() != null)
		                	entity.setTarget(AvatonEntity.this.getTarget());  
	                
		                for (int j = 0; j < 4; ++j) {
		                	double d0 = entity.getX() + (double)(AvatonEntity.this.getRandom().nextFloat() * entity.getBbWidth() * 2.0F) - (double)entity.getBbWidth();
		                	double d1 = entity.getY() + (double)(AvatonEntity.this.getRandom().nextFloat() * entity.getBbHeight());
		                	double d2 = entity.getZ() + (double)(AvatonEntity.this.getRandom().nextFloat() * entity.getBbWidth() * 2.0F) - (double)entity.getBbWidth();
		                	((ServerLevel) AvatonEntity.this.level()).sendParticles(FURParticleRegistry.LOCUST_SWARM.get(), d0, d1, d2, 15, 0.0D, 0.0D, 0.0D, 0.0D);
		                	
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
        	return 8/*FURConfig.Avaton_Ability_Cooldown.get()*/ * 20;
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound() {
            return null;
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
        return FURSoundRegistry.AVATON_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FURSoundRegistry.BANSHEE_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.AVATON_DEATH.get();
    }
    
    protected SoundEvent getSpellSound() {
        return FURSoundRegistry.AVATON_SPELL.get();
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
    	if (this.getSpellTicks() >= SPELL_TIMER - 5) {
    		state.getController().setAnimation(CAST);
    	} else if (this.getAttackTimer() == ATTACK_TIMER) {
    		state.getController().setAnimation(ATTACK);
    	} else if (this.isSpellcasting() || this.getAttackTimer() > 0) {
    		return PlayState.CONTINUE;
    	} else if (state.isMoving()) {
            state.getController().setAnimation(FLOAT);
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
