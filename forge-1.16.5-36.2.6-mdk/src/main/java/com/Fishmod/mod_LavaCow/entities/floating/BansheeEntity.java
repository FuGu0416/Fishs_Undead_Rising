package com.Fishmod.mod_LavaCow.entities.floating;

import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.init.FUREffectRegistry;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;

import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class BansheeEntity extends FloatingMobEntity {
	
	public BansheeEntity(EntityType<? extends BansheeEntity> p_i48549_1_, World worldIn) {
        super(p_i48549_1_, worldIn);
    }
		
	@Override
    protected void registerGoals() {
		super.registerGoals();
        this.goalSelector.addGoal(2, new BansheeEntity.AIUseSpell());
        this.goalSelector.addGoal(3, new FloatingMobEntity.AIChargeAttack());  
    }

	@Override
    protected void applyEntityAI() {
    	this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    	this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }
    
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.25D)
        		.add(Attributes.FOLLOW_RANGE, 32.0D)
        		.add(Attributes.MAX_HEALTH, FURConfig.Banshee_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, FURConfig.Banshee_Attack.get());
    }

    @Override
    @Nullable
    protected IParticleData ParticleType() {
    	return new RedstoneParticleData(0.20F, 0.21F, 0.23F, 0.6F);
    }
	
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void aiStep() {
        super.aiStep();
        
        if(this.getType() == FUREntityRegistry.BANSHEE && this.getSpellTicks() > 8 && this.getSpellTicks() < 13) {
        	this.level.addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY() + this.getBbHeight(), this.getZ(), 0.0D, 1.0D, 0.0D);
        }
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT p_213386_5_) {
        if(this.getType().equals(FUREntityRegistry.BANSHEE)) {
	    	this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Banshee_Health.get());
	        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Banshee_Attack.get());
	    	this.setHealth(this.getMaxHealth());
        }
        
    	return super.finalizeSpawn(p_213386_1_, difficulty, p_213386_3_, livingdata, p_213386_5_);
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
            BansheeEntity.this.level.broadcastEntityEvent(BansheeEntity.this, (byte)10);
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
        	List<Entity> list = BansheeEntity.this.level.getEntities(BansheeEntity.this, BansheeEntity.this.getBoundingBox().inflate(FURConfig.Banshee_Ability_Radius.get()));
        	BansheeEntity.this.level.broadcastEntityEvent(BansheeEntity.this, (byte)11);
        	
        	for (Entity entity1 : list) {
        		if (entity1 instanceof LivingEntity) {                 
        			if (((LivingEntity)entity1).getMobType() != CreatureAttribute.UNDEAD) {        				
        				if (((LivingEntity)entity1).hurt(DamageSource.mobAttack(BansheeEntity.this).setMagic(), (float) BansheeEntity.this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 1.0F)) {
        					float local_difficulty = BansheeEntity.this.level.getCurrentDifficultyAt(BansheeEntity.this.blockPosition()).getEffectiveDifficulty();
        					((LivingEntity)entity1).addEffect(new EffectInstance(FUREffectRegistry.FEAR, 2 * 20 * (int)local_difficulty, 2));       	
        				}       							
        			}
        		}
        	} 
        }

        protected int getCastWarmupTime() {
            return 20;
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
    
    class AIChargeAttack extends Goal {
    	
        public AIChargeAttack() {
        	this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean canUse() {
            if (BansheeEntity.this.getTarget() != null && !BansheeEntity.this.getMoveControl().hasWanted() && BansheeEntity.this.getRandom().nextInt(7) == 0) {
                return BansheeEntity.this.distanceToSqr(BansheeEntity.this.getTarget()) > 4.0D;
            } else {
                return false;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return BansheeEntity.this.getMoveControl().hasWanted() && BansheeEntity.this.getTarget() != null && BansheeEntity.this.getTarget().isAlive();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            LivingEntity LivingEntity = BansheeEntity.this.getTarget();
            Vector3d vec3d = LivingEntity.getEyePosition(1.0F);
            BansheeEntity.this.moveControl.setWantedPosition(vec3d.x, vec3d.y, vec3d.z, 1.2D);
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            LivingEntity livingentity = BansheeEntity.this.getTarget();
            if (BansheeEntity.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
               BansheeEntity.this.doHurtTarget(livingentity);
            } else {
               double d0 = BansheeEntity.this.distanceToSqr(livingentity);
               if (d0 < 9.0D) {
                  Vector3d vector3d = livingentity.getEyePosition(1.0F);
                  BansheeEntity.this.moveControl.setWantedPosition(vector3d.x, vector3d.y, vector3d.z, 1.0D);
                  if(BansheeEntity.this.getAttackTimer() == 0) {
	   	               BansheeEntity.this.setAttackTimer(20);
	   	               BansheeEntity.this.level.broadcastEntityEvent(BansheeEntity.this, (byte)4);
                  }
               }
            }
        }
    }
    
    @Override
    protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
        return p_213348_2_.height * 0.8F;
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return FURSoundRegistry.BANSHEE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FURSoundRegistry.BANSHEE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.BANSHEE_DEATH;
    }
    
    protected SoundEvent getSpellSound() {
        return FURSoundRegistry.BANSHEE_ATTACK;
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.UNDEAD;
    }
}
