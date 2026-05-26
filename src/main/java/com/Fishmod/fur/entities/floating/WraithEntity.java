package com.Fishmod.fur.entities.floating;

import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.entities.ICharging;
import com.Fishmod.fur.entities.ai.EntityChargeAttackGoal;
import com.Fishmod.fur.init.FUREffectRegistry;
import com.Fishmod.fur.init.FURSoundRegistry;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class WraithEntity extends FloatingMobEntity implements GeoEntity {
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	
    private static final RawAnimation IDLE = RawAnimation.begin().thenPlay("wraith.model.idle");
    private static final RawAnimation FLOAT = RawAnimation.begin().thenPlay("wraith.model.floating");
    private static final RawAnimation ATTACK = RawAnimation.begin().thenPlay("wraith.model.attacking");
    private static final RawAnimation CAST = RawAnimation.begin().thenPlay("wraith.model.casting");
    
	private static final EntityDataAccessor<Boolean> ISFADING = SynchedEntityData.defineId(WraithEntity.class, EntityDataSerializers.BOOLEAN);
	public static final int SPELL_WARMUP_TIMER = 50;
	public static final int SPELL_TIMER = 30;
    private float fadeProgress = SPELL_WARMUP_TIMER;
    
	public WraithEntity(EntityType<? extends WraithEntity> p_i48549_1_, Level worldIn) {
		super(p_i48549_1_, worldIn);
	}
	
    @Override
    protected void registerGoals() {
    	super.registerGoals();      
    	this.goalSelector.addGoal(2, new WraithEntity.AIUseSpell());
		this.goalSelector.addGoal(3, new EntityChargeAttackGoal(this));
    }
    
    @Override
    protected void applyEntityAI() {
    	this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    	this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }
    
    @Override
    protected void defineSynchedData() {
    	super.defineSynchedData();
        this.getEntityData().define(ISFADING, false);
    }
    
    @Nullable
    @Override
    protected SimpleParticleType ParticleType() {
    	return ParticleTypes.SOUL_FIRE_FLAME;
    }
    
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.25D)
        		.add(Attributes.FOLLOW_RANGE, 32.0D)
        		.add(Attributes.MAX_HEALTH, 20.0D)
        		.add(Attributes.ATTACK_DAMAGE, 5.0D);
    }
    
    public boolean isFading() {
        return this.entityData.get(ISFADING);
    }

    public void setFading(boolean bool) {
        this.entityData.set(ISFADING, bool);
    }

    public float getFadeIn(float ageInTicks) {
        return Math.max(0.0F, (fadeProgress / SPELL_WARMUP_TIMER));
    }
    
    @Override
    public void tick() {
        super.tick();

        if (this.isFading() && fadeProgress > 0) {
        	fadeProgress--;
        }
        
        if (!this.isFading() && fadeProgress < SPELL_WARMUP_TIMER) {
        	fadeProgress++;
        }
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficulty, MobSpawnType p_213386_3_, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag p_213386_5_) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Wraith_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Wraith_Attack.get());
    	this.setHealth(this.getMaxHealth());
    	
    	return super.finalizeSpawn(worldIn, difficulty, p_213386_3_, livingdata, p_213386_5_);
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
        protected int spellCooldown;
        protected LivingEntity target;
        
        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean canUse() {
            if (WraithEntity.this.isSpellcasting() || WraithEntity.this.isOnFire()) {
                return false;
            } else {
            	return WraithEntity.this.tickCount >= this.spellCooldown 
            			&& WraithEntity.this.getHealth() < WraithEntity.this.getMaxHealth() * 0.5F;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return WraithEntity.this.tickCount >= this.getCastingInterval() && WraithEntity.this.getMoveControl().hasWanted() && this.target != null && this.target.isAlive();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            SoundEvent soundevent = this.getSpellPrepareSound();

            for (Monster entitylivingbase : WraithEntity.this.level().getEntitiesOfClass(Monster.class, WraithEntity.this.getBoundingBox().inflate(8.0D))) {
                if (!(entitylivingbase instanceof ICharging) && !entitylivingbase.hasEffect(FUREffectRegistry.POSSESSED.get())) {
                	this.target = entitylivingbase;
                	break;
                }             
            }
            
            if (this.target != null) {
            	WraithEntity.this.spellTicks = this.getCastingTime();
            	WraithEntity.this.level().broadcastEntityEvent(WraithEntity.this, (byte)10);
            	this.spellCooldown = WraithEntity.this.tickCount + this.getCastingInterval();
            }
            
            if (soundevent != null) {
                WraithEntity.this.playSound(soundevent, 1.0F, 1.0F);
            }            
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
        	if (this.target == null) return;

        	Vec3 aim = this.target.position().add(0.0D, target.getBbHeight() * 0.5D, 0.0D);
        	WraithEntity.this.getMoveControl().setWantedPosition(aim.x(), aim.y(), aim.z(), 1.0D);
        	
            if (WraithEntity.this.distanceTo(this.target) < this.target.getBbWidth()) {
            	WraithEntity.this.doHurtTarget(this.target);
            	WraithEntity.this.setDeltaMovement(WraithEntity.this.getDeltaMovement().scale(0.2D));
            }
            
            if (WraithEntity.this.distanceTo(this.target) < 2.0D) {
                this.castSpell();
                WraithEntity.this.setFading(true);
                WraithEntity.this.playSound(WraithEntity.this.getSpellSound(), 4.0F, 1.2F);                        
            }
        }

        protected void castSpell() {
        	if (this.target != null) {
    			this.target.addEffect(new MobEffectInstance(FUREffectRegistry.POSSESSED.get(), 10 * 20, 2));  
    			this.target.setHealth(Math.min(this.target.getHealth() + WraithEntity.this.getHealth(), this.target.getMaxHealth()));
       		
                if (WraithEntity.this.level() instanceof ServerLevel) {
	                for (int j = 0; j < 8; ++j) {
	                	double d0 = this.target.getX() + (double)(WraithEntity.this.getRandom().nextFloat() * this.target.getBbWidth() * 2.0F) - (double)this.target.getBbWidth();
	                	double d1 = this.target.getY() + (double)(WraithEntity.this.getRandom().nextFloat() * this.target.getBbHeight());
	                	double d2 = this.target.getZ() + (double)(WraithEntity.this.getRandom().nextFloat() * this.target.getBbWidth() * 2.0F) - (double)this.target.getBbWidth();
	                	((ServerLevel) WraithEntity.this.level()).sendParticles(ParticleTypes.SOUL, d0, d1, d2, 15, 0.0D, 0.0D, 0.0D, 0.0D);
	                }
                }
                
                WraithEntity.this.remove(Entity.RemovalReason.KILLED);
        	}
        	
        }

        protected int getCastingTime() {
            return SPELL_TIMER;
        }

        protected int getCastingInterval() {
            return 320;
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound() {
            return null;
        }
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return FURSoundRegistry.WRAITH_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FURSoundRegistry.BANSHEE_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.WRAITH_DEATH.get();
    }
    
    protected SoundEvent getSpellSound() {
        return FURSoundRegistry.WRAITH_ATTACK.get();
    }
    
    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
    	if (state.isMoving() || !this.getNavigation().isDone()) {
            state.getController().setAnimation(FLOAT);
        } else {
            state.getController().setAnimation(IDLE);
        }
        
        return PlayState.CONTINUE;
    }
    
	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "controller", 5, this::predicate));
		controllers.add(new AnimationController<>(this, "trigger_controller", 5, state -> PlayState.STOP).triggerableAnim("attack", ATTACK).triggerableAnim("cast", CAST));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
