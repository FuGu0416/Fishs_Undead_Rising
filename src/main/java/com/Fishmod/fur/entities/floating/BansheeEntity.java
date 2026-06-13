package com.Fishmod.fur.entities.floating;

import java.util.List;
import javax.annotation.Nullable;

import org.joml.Vector3f;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.entities.ai.EntityChargeAttackGoal;
import com.Fishmod.fur.init.FUREffectRegistry;
import com.Fishmod.fur.init.FURParticleRegistry;
import com.Fishmod.fur.init.FURSoundRegistry;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
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

public class BansheeEntity extends FloatingMobEntity implements GeoEntity {
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final RawAnimation IDLE = RawAnimation.begin().thenPlay("banshee.model.idle");
    private static final RawAnimation FLOAT = RawAnimation.begin().thenPlay("banshee.model.floating");
    private static final RawAnimation ATTACK = RawAnimation.begin().thenPlay("banshee.model.attacking");
    private static final RawAnimation CAST = RawAnimation.begin().thenPlay("banshee.model.casting");
 
	public static final int SPELL_TIMER = 45;
	
	public BansheeEntity(EntityType<? extends BansheeEntity> entityType, Level LevelIn) {
        super(entityType, LevelIn);
    }
		
	@Override
    protected void registerGoals() {
		super.registerGoals();
        this.goalSelector.addGoal(2, new BansheeEntity.AIUseSpell());
        this.goalSelector.addGoal(3, new EntityChargeAttackGoal(this));  
    }

	@Override
    protected void applyEntityAI() {
    	this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    	this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }
    
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.25D)
        		.add(Attributes.FOLLOW_RANGE, 32.0D)
        		.add(Attributes.MAX_HEALTH, 34.0D)
        		.add(Attributes.ATTACK_DAMAGE, 7.0D);
    }

    @Override
    @Nullable
    protected ParticleOptions ParticleType() {
    	return new DustParticleOptions(new Vector3f(0.20F, 0.21F, 0.23F), 0.6F);
    }
	
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void aiStep() {
        super.aiStep();
        
        if (this.getSpellTicks() > 12 && this.getSpellTicks() < 16) {
        	this.level().addParticle(FURParticleRegistry.BANSHEE_SHRIEK.get(), this.getX(), this.getY() + this.getBbHeight(), this.getZ(), 0.0D, 1.0D, 0.0D);
        }
    }
	
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {        
    	this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Banshee_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Banshee_Attack.get());
    	this.setHealth(this.getMaxHealth());
    	
    	return super.finalizeSpawn(level, difficulty, spawnType, livingdata, tag);
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
            if (BansheeEntity.this.getTarget() == null) {
                return false;
            } else if (BansheeEntity.this.isSpellcasting()) {
                return false;
            } else {
            	return BansheeEntity.this.tickCount >= this.spellCooldown && BansheeEntity.this.distanceTo(BansheeEntity.this.getTarget()) < 3.0F;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return BansheeEntity.this.getTarget() != null && this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.spellWarmup = this.getCastWarmupTime();
            BansheeEntity.this.spellTicks = this.getCastingTime();
            BansheeEntity.this.level().broadcastEntityEvent(BansheeEntity.this, (byte)10);
            this.spellCooldown = BansheeEntity.this.tickCount + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();

            if (soundevent != null) {
                BansheeEntity.this.playSound(soundevent, 1.0F, 1.0F);
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            --this.spellWarmup;

            if (this.spellWarmup == 5) {
                this.castSpell();
                BansheeEntity.this.playSound(BansheeEntity.this.getSpellSound(), 4.0F, 1.2F);                        
            }
        }

        protected void castSpell() {
        	List<Entity> list = BansheeEntity.this.level().getEntities(BansheeEntity.this, BansheeEntity.this.getBoundingBox().inflate(FURConfig.Banshee_Ability_Radius.get()));
        	
        	for (Entity entity1 : list) {
        		if (entity1 instanceof LivingEntity livingentity) {     
    				if (livingentity.hurt(BansheeEntity.this.damageSources().sonicBoom(BansheeEntity.this), (float) BansheeEntity.this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 1.0F)) {
    					float local_difficulty = BansheeEntity.this.level().getCurrentDifficultyAt(BansheeEntity.this.blockPosition()).getEffectiveDifficulty();
    					livingentity.addEffect(new MobEffectInstance(FUREffectRegistry.FEAR.get(), 2 * 20 * (int)local_difficulty, 2));       	
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
            return 320;
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound() {
            return null;
        }
    }
       
    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.8F;
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return FURSoundRegistry.BANSHEE_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FURSoundRegistry.BANSHEE_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.BANSHEE_DEATH.get();
    }
    
    protected SoundEvent getSpellSound() {
        return FURSoundRegistry.BANSHEE_ATTACK.get();
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
		controllers.add(new AnimationController<>(this, "controller", 30, this::predicate));		
		controllers.add(new AnimationController<>(this, "trigger_controller", 5, state -> PlayState.STOP).triggerableAnim("attack", ATTACK).triggerableAnim("cast", CAST));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
