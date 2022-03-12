package com.Fishmod.mod_LavaCow.entities;

import java.util.EnumSet;
import java.util.Random;
import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.entities.ai.EntityFishAIAttackRange;
import com.Fishmod.mod_LavaCow.entities.tameable.LilSludgeEntity;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;

import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.FleeSunGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SludgeLordEntity extends MonsterEntity implements IAggressive {
	private int attackTimer;
	private int RattackTimer;
	protected int spellTicks;
	
	public SludgeLordEntity(EntityType<? extends SludgeLordEntity> p_i48549_1_, World worldIn)
    {
        super(p_i48549_1_, worldIn);
        this.xpReward = 20;
    }
	
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new AICastingApell());
    	this.goalSelector.addGoal(2, new EntityFishAIAttackRange<>(this, FUREntityRegistry.SLUDGEJET, FURSoundRegistry.SLUDGELORD_ATTACK, 1, 2, 0.0D, 8.0D, 1.2D, 0.6D, 1.2D));
    	this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.0D, false));
    	this.goalSelector.addGoal(4, new SludgeLordEntity.AIUseSpell());
    	if(!FURConfig.SunScreen_Mode.get())this.goalSelector.addGoal(4, new FleeSunGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new RandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI()
    {
    	this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    	this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }
    
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.19D)
        		.add(Attributes.FOLLOW_RANGE, 16.0D)
        		.add(Attributes.MAX_HEALTH, FURConfig.SludgeLord_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, FURConfig.SludgeLord_Attack.get())
        		.add(Attributes.ARMOR, 8.0D)
        		.add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }
    
    public static boolean checkSludgeLordSpawnRules(EntityType<? extends SludgeLordEntity> p_223316_0_, IWorld p_223316_1_, SpawnReason p_223316_2_, BlockPos p_223316_3_, Random p_223316_4_) {
        return MonsterEntity.checkMonsterSpawnRules(p_223316_0_, (IServerWorld) p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_);//SpawnUtil.isAllowedDimension(this.dimension);
    }
    
    public boolean isSpellcasting()
    {
    	return this.spellTicks > 0;
    }
    
    public int getSpellTicks()
    {
        return this.spellTicks;
    }
	
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void tick() {
        super.tick();
        
        if (this.attackTimer > 0) {
            --this.attackTimer;
            if(this.attackTimer > 20 && this.getTarget() != null)
            	this.getLookControl().setLookAt(this.getTarget(), 10.0F, 10.0F);
            this.setDeltaMovement(Vector3d.ZERO);
        }
        
        if (this.RattackTimer > 0) {
            --this.RattackTimer;
        }
        
        if (this.spellTicks > 0) {
            --this.spellTicks;
            this.setDeltaMovement(Vector3d.ZERO);
        }
        
    	if (!FURConfig.SunScreen_Mode.get() && this.isSunBurnTick()) {
    		this.setSecondsOnFire(40);
        } 
    	
        if (this.getAttackTimer() == 18 && this.deathTime <= 0) {       
        	float f = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
			double d0 = this.getX() + 2.5D * this.getLookAngle().normalize().x;
            double d1 = this.getY();
            double d2 = this.getZ() + 2.5D * this.getLookAngle().normalize().z;
            BlockState state = this.level.getBlockState(new BlockPos(d0, d1, d2).below());
                	
	        if (state.getMaterial().isSolid()) {
	        	this.playSound(state.getSoundType(this.level, new BlockPos(d0, d1, d2).below(), this).getBreakSound(), 1, 0.5F);
	        	
	            if (this.level.isClientSide()) {
	            	for(int i = 0; i < 64; i++)
	            		this.level.addParticle(new BlockParticleData(ParticleTypes.BLOCK, state).setPos(new BlockPos(d0, d1, d2).below()), d0 + (double) (this.getRandom().nextFloat() * this.getBbWidth() * 2.0F) - (double) this.getBbWidth(), d1 + (double) (this.getRandom().nextFloat() * this.getBbWidth() * 2.0F) - (double) this.getBbWidth(), d2 + (double) (this.getRandom().nextFloat() * this.getBbWidth() * 2.0F) - (double) this.getBbWidth(), this.getRandom().nextGaussian() * 0.02D, this.getRandom().nextGaussian() * 0.02D, this.getRandom().nextGaussian() * 0.02D);
	            }
	        }
	        
            for (LivingEntity entitylivingbase : this.level.getEntitiesOfClass(LivingEntity.class, new AxisAlignedBB(d0, d1, d2, d0, d1, d2).inflate(1.5D))) {
                if (!this.equals(entitylivingbase) && !this.isAlliedTo(entitylivingbase)) {
                	if(!(entitylivingbase instanceof TameableEntity && ((TameableEntity) entitylivingbase).isOwnedBy(this))) {
                		entitylivingbase.hurt(DamageSource.mobAttack(this), (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE));
                        if (this.getMainHandItem().isEmpty() && this.isOnFire() && this.random.nextFloat() < f * 0.3F) {
                        	entitylivingbase.setSecondsOnFire(2 * (int)f);
                        }
                	}
                }             
            }
        }
    }
    
    /**
     * Called when the entity is attacked.
     */
	@Override
    public boolean hurt(DamageSource source, float amount) {
    	if(source.isFire())
    		return super.hurt(source, 2.0F * amount);
    	return super.hurt(source, amount);
    }

    @Override
    public boolean doHurtTarget(Entity entityIn) {
    	if (this.attackTimer == 0) {
	        this.attackTimer = 30;
	        this.level.broadcastEntityEvent(this, (byte)4);
	        return true;
    	}
        return false;
    }
    
	@Override
    public float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
        return p_213348_2_.height * 0.8F;
    }
	
	public int getAttackTimer() {
        return this.attackTimer;
    }

	@Override
	public void setAttackTimer(int i) {
		this.attackTimer = i;
	}

	public int getRAttackTimer() {
        return this.RattackTimer;
    }

	public void setRAttackTimer(int i) {
		this.RattackTimer = i;
	}
	
    /**
     * Handler for {@link World#setEntityState}
     */
	@Override
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
		switch(id) {
			case 4:
				this.attackTimer = 30;
				break;
			case 5:
				this.RattackTimer = 40;
				break;
			case 10:
				this.spellTicks = 100;
				break;
			default:
				super.handleEntityEvent(id);
				break;
		}
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
    
public class AICastingApell extends Goal {
    	
        public AICastingApell() {
        	this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean canUse()
        {
            return SludgeLordEntity.this.getSpellTicks() > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start()
        {
            super.start();
            SludgeLordEntity.this.getNavigation().stop();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop()
        {
            super.stop();
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick()
        {
            if (SludgeLordEntity.this.getTarget() != null)
            {
                SludgeLordEntity.this.getLookControl().setLookAt(SludgeLordEntity.this.getTarget(), (float)SludgeLordEntity.this.getMaxHeadYRot(), (float)SludgeLordEntity.this.getMaxHeadXRot());
            }
        }
    }

    public class AIUseSpell extends Goal
    {
        protected int spellWarmup;
        protected int spellCooldown;

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean canUse()
        {
            if (SludgeLordEntity.this.getTarget() == null)
            {
                return false;
            }
            else if (SludgeLordEntity.this.isSpellcasting() || SludgeLordEntity.this.getAttackTimer() > 0 || SludgeLordEntity.this.getRAttackTimer() > 0)
            {
                return false;
            }
            else
            {
                int i = SludgeLordEntity.this.level.getEntitiesOfClass(LilSludgeEntity.class, SludgeLordEntity.this.getBoundingBox().inflate(16.0D)).size();
            	return SludgeLordEntity.this.tickCount >= this.spellCooldown && i < FURConfig.SludgeLord_Ability_Max.get();
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse()
        {
            return SludgeLordEntity.this.getTarget() != null && this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start()
        {
            this.spellWarmup = this.getCastWarmupTime();
            SludgeLordEntity.this.spellTicks = this.getCastingTime();
            this.spellCooldown = SludgeLordEntity.this.tickCount + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();
            SludgeLordEntity.this.level.broadcastEntityEvent(SludgeLordEntity.this, (byte)10);         
            if (soundevent != null)
            {
                SludgeLordEntity.this.playSound(soundevent, 1.0F, 1.0F);
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick()
        {
            --this.spellWarmup;

            if (this.spellWarmup == 0)
            {
                this.castSpell();
                SludgeLordEntity.this.playSound(SludgeLordEntity.this.getSpellSound(), 1.0F, 1.0F);
            }
        }

        protected void castSpell()
        {
            for (int i = 0; i < FURConfig.SludgeLord_Ability_Num.get(); ++i)
            {
                BlockPos blockpos = SludgeLordEntity.this.blockPosition().offset(-2 + SludgeLordEntity.this.getRandom().nextInt(5), 1, -2 + SludgeLordEntity.this.getRandom().nextInt(5));
                LilSludgeEntity entity = FUREntityRegistry.LILSLUDGE.create(SludgeLordEntity.this.level);
                entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0D);
                entity.setHealth(entity.getMaxHealth());
                entity.moveTo(blockpos, 0.0F, 0.0F);
                entity.setOwnerUUID(SludgeLordEntity.this.getUUID());
                entity.setTame(true);
                entity.setLimitedLife(20 * (30 + SludgeLordEntity.this.getRandom().nextInt(90)));
                
                if(!SludgeLordEntity.this.level.isClientSide())
                	SludgeLordEntity.this.level.addFreshEntity(entity);
                
                entity.setTarget(SludgeLordEntity.this.getTarget());
                
                for (int j = 0; j < 24; ++j)
                {
                	double d0 = entity.getX() + (double)(SludgeLordEntity.this.getRandom().nextFloat() * entity.getBbWidth() * 2.0F) - (double)entity.getBbWidth();
                	double d1 = entity.getY() + (double)(SludgeLordEntity.this.getRandom().nextFloat() * entity.getBbHeight());
                	double d2 = entity.getZ() + (double)(SludgeLordEntity.this.getRandom().nextFloat() * entity.getBbWidth() * 2.0F) - (double)entity.getBbWidth();
                	SludgeLordEntity.this.level.addParticle(ParticleTypes.SPLASH, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                }
            }
        }

        protected int getCastWarmupTime()
        {
            return 80;
        }

        protected int getCastingTime()
        {
            return 100;
        }

        protected int getCastingInterval()
        {
        	return FURConfig.SludgeLord_Ability_Cooldown.get() * 20;
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound()
        {
        	return SoundEvents.EVOKER_PREPARE_ATTACK;
        }
    }
    
	@Override
    public int getAmbientSoundInterval() {
        return 320;
    }
    
    @Override
    protected SoundEvent getAmbientSound()
    {
        return FURSoundRegistry.SLUDGELORD_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return FURSoundRegistry.SLUDGELORD_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return FURSoundRegistry.SLUDGELORD_DEATH;
    }
    
    protected SoundEvent getSpellSound()
    {
        return SoundEvents.EVOKER_CAST_SPELL;
    }

	@Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    	this.playSound(SoundEvents.IRON_GOLEM_STEP, 0.15F, 1.0F);
	}

    /**
     * Get this Entity's CreatureAttribute
     */
    @Override
    public CreatureAttribute getMobType()
    {
        return CreatureAttribute.UNDEAD;
    }
    
    /**
     * Entity won't drop items or experience points if this returns false
     */
    @Override
    protected boolean shouldDropLoot() {
       return !this.isOnFire() || this.lastHurtByPlayer != null;
    }
}
