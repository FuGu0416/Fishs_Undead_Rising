package com.Fishmod.mod_LavaCow.entities;

import java.util.EnumSet;
import java.util.Random;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.entities.projectiles.CactusThornEntity;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CactyrantEntity extends MonsterEntity implements IAggressive {
	
	private int attackTimer;
	protected int spellTicks;
	
	public CactyrantEntity(EntityType<? extends CactyrantEntity> p_i48549_1_, World worldIn) {
		super(p_i48549_1_, worldIn);
		this.xpReward = 12;
	}
	
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new AICastingApell());
        this.goalSelector.addGoal(2, new CactyrantEntity.AIUseSpell());
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.25D, false));  
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
    	this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    	this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }
    
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.21D)
        		.add(Attributes.FOLLOW_RANGE, 16.0D)
        		.add(Attributes.MAX_HEALTH, FURConfig.Undertaker_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, FURConfig.Undertaker_Attack.get())
        		.add(Attributes.ARMOR, 3.0D)
        		.add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }
    
    public static boolean checkCactyrantSpawnRules(EntityType<? extends CactyrantEntity> p_223316_0_, IWorld p_223316_1_, SpawnReason p_223316_2_, BlockPos p_223316_3_, Random p_223316_4_) {
        return MonsterEntity.checkMonsterSpawnRules(p_223316_0_, (IServerWorld) p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_);//SpawnUtil.isAllowedDimension(this.dimension);
    }
    
    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    @Override
    public int getMaxSpawnClusterSize() {
       return 1;
    }
    
    public boolean isSpellcasting() {
    	return this.spellTicks > 0;
    }
    
    public int getSpellTicks() {
        return this.spellTicks;
    }
    
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void tick() {
        super.tick();
    	LivingEntity target = this.getTarget();
    	
        if (this.attackTimer > 0) {
            --this.attackTimer;
        }
        
        if (this.spellTicks > 0) {
            --this.spellTicks;
        }
               
		if (!FURConfig.SunScreen_Mode.get() && this.isSunBurnTick()) {
			this.setSecondsOnFire(40);
		} 
  	   	        
        if (target != null && this.distanceToSqr(target) < 4.0D && this.getAttackTimer() == 5 && this.deathTime <= 0 && this.canSee(target)) {
        	float f = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
        	this.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1.0F, 1.0F);	        	
        	target.hurt(DamageSource.mobAttack(this), (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE));	            
            if (this.getMainHandItem().isEmpty() && this.isOnFire() && this.random.nextFloat() < f * 0.3F) {
            	target.setSecondsOnFire(2 * (int)f);
            }
        }
    }
	
    public boolean doHurtTarget(Entity entityIn) {
        this.attackTimer = 15;
        this.level.broadcastEntityEvent(this, (byte)4);
        return true;
    }
    
	@Override
    public float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
        return p_213348_2_.height * 0.85F;
    }
    
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.spellTicks = compound.getInt("SpellTicks");
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("SpellTicks", this.spellTicks);
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
	@Nullable
	public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT p_213386_5_) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Undertaker_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Undertaker_Attack.get());
    	this.setHealth(this.getMaxHealth());
        
        return livingdata;
    }

    public int getAttackTimer() {
        return this.attackTimer;
    }

	@Override
	public void setAttackTimer(int i) {
		this.attackTimer = i;
	}
    
    /**
     * Handler for {@link World#setEntityState}
     */
	@Override
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
    	if (id == 4) {
            this.attackTimer = 15;
        } else if (id == 10) {
        	this.spellTicks = 20;
        } else {
            super.handleEntityEvent(id);
        }
    }
    
    public class AICastingApell extends Goal {
        public AICastingApell() {
        	this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean canUse() {
            return CactyrantEntity.this.getSpellTicks() > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            super.start();
            CactyrantEntity.this.getNavigation().stop();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
            super.stop();
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (CactyrantEntity.this.getTarget() != null) {
                CactyrantEntity.this.getLookControl().setLookAt(CactyrantEntity.this.getTarget(), (float)CactyrantEntity.this.getMaxHeadYRot(), (float)CactyrantEntity.this.getMaxHeadXRot());
            }
        }
    }
    
    public class AIUseSpell extends Goal {
        protected int spellWarmup;
        protected int spellCooldown;

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean canUse() {
    		if (CactyrantEntity.this.getTarget() == null)
                return false;
            else if (CactyrantEntity.this.isSpellcasting() || !CactyrantEntity.this.canSee(CactyrantEntity.this.getTarget()))
                return false;
            else {                
            	return CactyrantEntity.this.tickCount >= this.spellCooldown && CactyrantEntity.this.distanceTo(CactyrantEntity.this.getTarget()) > 2.0D;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return CactyrantEntity.this.getTarget() != null && this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.spellWarmup = this.getCastWarmupTime();
            CactyrantEntity.this.spellTicks = this.getCastingTime();
            this.spellCooldown = CactyrantEntity.this.tickCount + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();
            CactyrantEntity.this.level.broadcastEntityEvent(CactyrantEntity.this, (byte)10);
            if (soundevent != null) {
                CactyrantEntity.this.playSound(soundevent, 1.0F, 1.0F);
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            --this.spellWarmup;

            if (this.spellWarmup == 0) {
                this.castSpell();
            }
        }

        protected void castSpell() {
        	double d0, d1, d2, d3, f;
        	for(int i = 0 ; i < 6 ; i++) {
	        	CactusThornEntity abstractarrowentity = new CactusThornEntity(CactyrantEntity.this.level, CactyrantEntity.this);
	            LivingEntity target = CactyrantEntity.this.getTarget();
	            d0 = target.getX() - CactyrantEntity.this.getX();
	            d1 = target.getY(0.3333333333333333D) - abstractarrowentity.getY();
	            d2 = target.getZ() - CactyrantEntity.this.getZ();
	            d3 = (double)MathHelper.sqrt(d0 * d0 + d2 * d2);
	            f = i == 3 ? 0 : MathHelper.sqrt(MathHelper.sqrt(d3)) * 2.0D;
	            abstractarrowentity.shoot(d0 + CactyrantEntity.this.getRandom().nextGaussian() * f, d1 + d3 * 0.2D, d2 + CactyrantEntity.this.getRandom().nextGaussian() * f, 1.6F, (float)(14 - CactyrantEntity.this.level.getDifficulty().getId() * 4));            
	            CactyrantEntity.this.level.addFreshEntity(abstractarrowentity);
        	}
        	CactyrantEntity.this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (CactyrantEntity.this.getRandom().nextFloat() * 0.4F + 0.8F));
        }

        protected int getCastWarmupTime() {
            return 10;
        }

        protected int getCastingTime() {
            return 20;
        }

        protected int getCastingInterval() {
            return 60;//FURConfig.Undertaker_Ability_Cooldown.get() * 20;
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.PLAYER_ATTACK_SWEEP;
        }
    }
    
    @Override
    protected SoundEvent getAmbientSound()
    {
        return FURSoundRegistry.UNDERTAKER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return FURSoundRegistry.UNDERTAKER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return FURSoundRegistry.UNDERTAKER_DEATH;
    }

	@Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    	this.playSound(SoundEvents.ZOMBIE_STEP, 0.15F, 1.0F);
	}
    
    /**
     * Entity won't drop items or experience points if this returns false
     */
    @Override
    protected boolean shouldDropLoot() {
       return !this.isOnFire() || this.lastHurtByPlayer != null;
    }
}
