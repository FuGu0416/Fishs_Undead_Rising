package com.Fishmod.mod_LavaCow.entities.floating;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.init.FUREffectRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;

import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class WraithEntity extends FloatingMobEntity {
	public WraithEntity(EntityType<? extends WraithEntity> p_i48549_1_, World worldIn) {
		super(p_i48549_1_, worldIn);
	}
	
    @Override
    protected void registerGoals() {
    	super.registerGoals();      
    	this.goalSelector.addGoal(0, new SwimGoal(this));
    	this.goalSelector.addGoal(2, new WraithEntity.AIUseSpell());
		this.goalSelector.addGoal(3, new FloatingMobEntity.AIChargeAttack());
    }
    
    @Override
    protected void applyEntityAI() {
    	this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    	this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }
    
    @Nullable
    @Override
    protected IParticleData ParticleType() {
    	return ParticleTypes.SOUL_FIRE_FLAME;
    }
    
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.25D)
        		.add(Attributes.FOLLOW_RANGE, 32.0D)
        		.add(Attributes.MAX_HEALTH, FURConfig.Wraith_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, FURConfig.Wraith_Attack.get());
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT p_213386_5_) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Wraith_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Wraith_Attack.get());
    	this.setHealth(this.getMaxHealth());

    	return super.finalizeSpawn(worldIn, difficulty, p_213386_3_, livingdata, p_213386_5_);
    }
    
    public class AIUseSpell extends Goal {
        protected int spellWarmup;
        protected int spellCooldown;

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean canUse() {
            if (WraithEntity.this.getTarget() == null) {
                return false;
            } else if (WraithEntity.this.isSpellcasting()) {
                return false;
            } else {
            	return WraithEntity.this.tickCount >= this.spellCooldown 
            			&& WraithEntity.this.getTarget() instanceof PlayerEntity
            			&& WraithEntity.this.distanceTo(WraithEntity.this.getTarget()) < 8.0 
            			&& WraithEntity.this.getHealth() < WraithEntity.this.getMaxHealth() * 0.5F;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return WraithEntity.this.getTarget() != null && this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.spellWarmup = this.getCastWarmupTime();
            WraithEntity.this.spellTicks = this.getCastingTime();
            WraithEntity.this.level.broadcastEntityEvent(WraithEntity.this, (byte)10);
            this.spellCooldown = WraithEntity.this.tickCount + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();

            for (MobEntity entitylivingbase : WraithEntity.this.level.getEntitiesOfClass(MobEntity.class, WraithEntity.this.getBoundingBox().inflate(8.0D))) {
                if (!WraithEntity.this.equals(entitylivingbase) && entitylivingbase.getTarget() != null && entitylivingbase.getTarget().equals(WraithEntity.this.getTarget())) {
                	WraithEntity.this.setTarget(entitylivingbase);
                }             
            }
            
            if (soundevent != null) {
                WraithEntity.this.playSound(soundevent, 1.0F, 1.0F);
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            --this.spellWarmup;

            if (this.spellWarmup == 5) {
                this.castSpell();
                WraithEntity.this.playSound(WraithEntity.this.getSpellSound(), 4.0F, 1.2F);                        
            }
        }

        protected void castSpell() {
        	if (WraithEntity.this.getTarget() != null) {
        		float local_difficulty = WraithEntity.this.level.getCurrentDifficultyAt(WraithEntity.this.blockPosition()).getEffectiveDifficulty();
        		
        		if (WraithEntity.this.getTarget() instanceof PlayerEntity) {
        			WraithEntity.this.getTarget().addEffect(new EffectInstance(Effects.WEAKNESS, 30 * 20 * (int)local_difficulty, 0));
        			WraithEntity.this.getTarget().addEffect(new EffectInstance(FUREffectRegistry.FRAGILE, 30 * 20 * (int)local_difficulty, 4));    
        		} else {
        			WraithEntity.this.getTarget().addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 2 * 60 * 20, 2));
        			WraithEntity.this.getTarget().addEffect(new EffectInstance(Effects.ABSORPTION, 2 * 60 * 20, 2));        			
        		}
        		
                if (WraithEntity.this.level instanceof ServerWorld) {
	                for (int j = 0; j < 16; ++j) {
	                	double d0 = WraithEntity.this.getTarget().getX() + (double)(WraithEntity.this.getRandom().nextFloat() * WraithEntity.this.getTarget().getBbWidth() * 2.0F) - (double)WraithEntity.this.getTarget().getBbWidth();
	                	double d1 = WraithEntity.this.getTarget().getY() + (double)(WraithEntity.this.getRandom().nextFloat() * WraithEntity.this.getTarget().getBbHeight());
	                	double d2 = WraithEntity.this.getTarget().getZ() + (double)(WraithEntity.this.getRandom().nextFloat() * WraithEntity.this.getTarget().getBbWidth() * 2.0F) - (double)WraithEntity.this.getTarget().getBbWidth();
	                	((ServerWorld) WraithEntity.this.level).sendParticles(ParticleTypes.SOUL, d0, d1, d2, 15, 0.0D, 0.0D, 0.0D, 0.0D);
	                	
	                }
                }
                
                WraithEntity.this.hurt(DamageSource.mobAttack(WraithEntity.this).bypassInvul().bypassArmor(), WraithEntity.this.getMaxHealth());
        	}
        }

        protected int getCastWarmupTime() {
            return 30;
        }

        protected int getCastingTime() {
            return 30;
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
        return FURSoundRegistry.WRAITH_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FURSoundRegistry.BANSHEE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.WRAITH_DEATH;
    }
    
    protected SoundEvent getSpellSound() {
        return FURSoundRegistry.WRAITH_ATTACK;
    }
    
    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.UNDEAD;
    }
}