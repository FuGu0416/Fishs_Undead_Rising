package com.Fishmod.mod_LavaCow.entities;

import java.util.EnumSet;
import java.util.Random;
import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.ai.EntityFishAIAttackRange;
import com.Fishmod.mod_LavaCow.entities.ai.FURMeleeAttackGoal;
import com.Fishmod.mod_LavaCow.entities.tameable.LilSludgeEntity;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;

import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.CreatureEntity;
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
import net.minecraft.entity.ai.goal.FleeSunGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class SludgeLordEntity extends MonsterEntity implements IAggressive {
	private static final DataParameter<Integer> SKIN_TYPE =  EntityDataManager.defineId(SludgeLordEntity.class, DataSerializers.INT);
	public static final int ATTACK_TIMER = 30;
	private int attackTimer;
	private int RattackTimer;
	protected int spellTicks;
	
	public SludgeLordEntity(EntityType<? extends SludgeLordEntity> p_i48549_1_, World worldIn) {
        super(p_i48549_1_, worldIn);
        this.xpReward = 20;
    }
	
    @Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SKIN_TYPE, Integer.valueOf(0));
	}
	
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new AICastingApell());    	
    	this.goalSelector.addGoal(3, new AttackGoal(this));    	
    	if(!FURConfig.SunScreen_Mode.get())this.goalSelector.addGoal(4, new FleeSunGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new RandomWalkingGoal(this, 1.0D));
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
    
    public boolean isSpellcasting() {
    	return this.spellTicks > 0;
    }
    
    public int getSpellTicks() {
        return this.spellTicks;
    }

    @Override
    public double getMyRidingOffset() {
        return -0.65D;
    }
    
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void tick() {
        super.tick();
        
        if (this.attackTimer > 0) {
            --this.attackTimer;
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
    }
	
	@Override
	public boolean doHurtTarget(Entity entity) {
		boolean flag = super.doHurtTarget(entity);
		
		if (flag) {
			float f = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
			
            if (this.getMainHandItem().isEmpty() && this.isOnFire() && this.getRandom().nextFloat() < f * 0.3F) {
            	entity.setSecondsOnFire(2 * (int)f);
            }
		}
		
		return flag;
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
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT p_213386_5_) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.SludgeLord_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.SludgeLord_Attack.get());
    	this.setHealth(this.getMaxHealth());

 	   	if(BiomeDictionary.getTypes(SpawnUtil.getRegistryKey(worldIn.getBiome(this.blockPosition()))).contains(Type.HOT)
 			   && BiomeDictionary.getTypes(SpawnUtil.getRegistryKey(worldIn.getBiome(this.blockPosition()))).contains(Type.DRY)
 			   && BiomeDictionary.getTypes(SpawnUtil.getRegistryKey(worldIn.getBiome(this.blockPosition()))).contains(Type.SANDY)
 			   && BiomeDictionary.getTypes(SpawnUtil.getRegistryKey(worldIn.getBiome(this.blockPosition()))).contains(Type.OVERWORLD)) {
		   this.setSkin(1);
 	   	}
 	   	
 	   	if (this.getSkin() == 1) {
 	   		this.goalSelector.addGoal(2, new EntityFishAIAttackRange<>(this, FUREntityRegistry.SAPJET, FURSoundRegistry.SLUDGELORD_ATTACK, 1, 2, 0.2D, 8.0D, 1.2D, 0.6D, 1.2D));
 	   	} else {
 	   		this.goalSelector.addGoal(2, new EntityFishAIAttackRange<>(this, FUREntityRegistry.SLUDGEJET, FURSoundRegistry.SLUDGELORD_ATTACK, 1, 2, 0.0D, 8.0D, 1.2D, 0.6D, 1.2D));
 	   		this.goalSelector.addGoal(4, new SludgeLordEntity.AIUseSpell());
 	   	}
 	   	
    	return super.finalizeSpawn(worldIn, difficulty, p_213386_3_, livingdata, p_213386_5_);
    }
    
	@Override
    public float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
        return p_213348_2_.height * 0.8F;
    }
	
    public int getSkin() {
        return this.getEntityData().get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
    	this.getEntityData().set(SKIN_TYPE, Integer.valueOf(skinType));
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
				this.attackTimer = ATTACK_TIMER;
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
        this.setSkin(compound.getInt("Variant"));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("SpellTicks", this.spellTicks);
        compound.putInt("Variant", getSkin());
    }
    
	public class AICastingApell extends Goal {
    	
        public AICastingApell() {
        	this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean canUse() {
            return SludgeLordEntity.this.getSpellTicks() > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            super.start();
            SludgeLordEntity.this.getNavigation().stop();
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
            if (SludgeLordEntity.this.getTarget() != null) {
                SludgeLordEntity.this.getLookControl().setLookAt(SludgeLordEntity.this.getTarget(), (float)SludgeLordEntity.this.getMaxHeadYRot(), (float)SludgeLordEntity.this.getMaxHeadXRot());
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
            if (SludgeLordEntity.this.getTarget() == null) {
                return false;
            } else if (SludgeLordEntity.this.isSpellcasting() || SludgeLordEntity.this.getAttackTimer() > 0 || SludgeLordEntity.this.getRAttackTimer() > 0) {
                return false;
            } else {
                int i = SludgeLordEntity.this.level.getEntitiesOfClass(LilSludgeEntity.class, SludgeLordEntity.this.getBoundingBox().inflate(16.0D)).size();
            	return SludgeLordEntity.this.tickCount >= this.spellCooldown && i < FURConfig.SludgeLord_Ability_Max.get();
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return SludgeLordEntity.this.getTarget() != null && this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.spellWarmup = this.getCastWarmupTime();
            SludgeLordEntity.this.spellTicks = this.getCastingTime();
            this.spellCooldown = SludgeLordEntity.this.tickCount + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();
            SludgeLordEntity.this.level.broadcastEntityEvent(SludgeLordEntity.this, (byte)10);         
            if (soundevent != null) {
                SludgeLordEntity.this.playSound(soundevent, 1.0F, 1.0F);
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            --this.spellWarmup;

            if (this.spellWarmup == 0) {
                this.castSpell();
                SludgeLordEntity.this.playSound(SludgeLordEntity.this.getSpellSound(), 1.0F, 1.0F);
            }
        }

        protected void castSpell() {
            for (int i = 0; i < FURConfig.SludgeLord_Ability_Num.get(); ++i) {
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
                
                if (SludgeLordEntity.this.level instanceof ServerWorld) {
	                for (int j = 0; j < 4; ++j) {
	                	double d0 = entity.getX() + (double)(SludgeLordEntity.this.getRandom().nextFloat() * entity.getBbWidth() * 2.0F) - (double)entity.getBbWidth();
	                	double d1 = entity.getY() + (double)(SludgeLordEntity.this.getRandom().nextFloat() * entity.getBbHeight());
	                	double d2 = entity.getZ() + (double)(SludgeLordEntity.this.getRandom().nextFloat() * entity.getBbWidth() * 2.0F) - (double)entity.getBbWidth();
	                	((ServerWorld) SludgeLordEntity.this.level).sendParticles(ParticleTypes.SPLASH, d0, d1, d2, 15, 0.0D, 0.0D, 0.0D, 0.0D);
	                	
	                }
                }
            }
        }

        protected int getCastWarmupTime() {
            return 80;
        }

        protected int getCastingTime() {
            return 100;
        }

        protected int getCastingInterval() {
        	return FURConfig.SludgeLord_Ability_Cooldown.get() * 20;
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound() {
        	return SoundEvents.EVOKER_PREPARE_ATTACK;
        }
    }
    
	@Override
	public int getAmbientSoundInterval() {
        return 320;
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return FURSoundRegistry.SLUDGELORD_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FURSoundRegistry.SLUDGELORD_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.SLUDGELORD_DEATH;
    }
    
    protected SoundEvent getSpellSound() {
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
    public CreatureAttribute getMobType() {
        return CreatureAttribute.UNDEAD;
    }
    
    @Nullable
    @Override
    protected ResourceLocation getDefaultLootTable() {
    	switch(this.getSkin()) {
    		case 1:
    			return new ResourceLocation("mod_lavacow", "entities/sludgelord1");
    		case 0:
    		default:
    			return super.getDefaultLootTable();
    	}
    }
    
    /**
     * Entity won't drop items or experience points if this returns false
     */
    @Override
    protected boolean shouldDropLoot() {
       return !this.isOnFire() || this.lastHurtByPlayer != null;
    }
    
    static class AttackGoal extends FURMeleeAttackGoal {
        public AttackGoal(CreatureEntity p_i46676_1_) {
           super(p_i46676_1_, 1.0D, false);
        }
        
    	protected int atkTimerMax() {
    		return ATTACK_TIMER;
    	}
    	
    	protected int atkTimerHit() {
    		return 18;
    	}
    	
    	protected byte atkTimerEvent() {
    		return (byte)4;
    	}
    	
    	protected void dmgEvent(LivingEntity target) {  		   		   	   		
			double d0 = this.mob.getX() + 2.5D * this.mob.getLookAngle().normalize().x;
            double d1 = this.mob.getY();
            double d2 = this.mob.getZ() + 2.5D * this.mob.getLookAngle().normalize().z;
            BlockState state = this.mob.level.getBlockState(new BlockPos(d0, d1, d2).below());
         
	        if (state.getMaterial().isSolid()) {
	        	this.mob.playSound(state.getSoundType(this.mob.level, new BlockPos(d0, d1, d2).below(), this.mob).getBreakSound(), 1, 0.5F);
	        	
                if (this.mob.level instanceof ServerWorld) {
                	for (int i = 0; i < 64; i++) {                	
	                	((ServerWorld) this.mob.level).sendParticles(new BlockParticleData(ParticleTypes.BLOCK, state).setPos(new BlockPos(d0, d1, d2).below()), 
	            				d0 + (double) (this.mob.getRandom().nextFloat() * this.mob.getBbWidth() * 2.0F) - (double) this.mob.getBbWidth(), 
	            				d1 + (double) (this.mob.getRandom().nextFloat() * this.mob.getBbWidth() * 2.0F) - (double) this.mob.getBbWidth(), 
	            				d2 + (double) (this.mob.getRandom().nextFloat() * this.mob.getBbWidth() * 2.0F) - (double) this.mob.getBbWidth(), 
	            				15, this.mob.getRandom().nextGaussian() * 0.02D, this.mob.getRandom().nextGaussian() * 0.02D, this.mob.getRandom().nextGaussian() * 0.02D, (double)0.15F);
	                	
	                }
                }
	        }
	        
			for (LivingEntity entitylivingbase : this.mob.level.getEntitiesOfClass(LivingEntity.class, this.mob.getBoundingBox().inflate(1.5D))) {
                if (!this.mob.equals(entitylivingbase) && !this.mob.isAlliedTo(entitylivingbase)) {
                	if (!(entitylivingbase instanceof TameableEntity && ((TameableEntity) entitylivingbase).isOwnedBy(this.mob))) {
                		super.dmgEvent(entitylivingbase);
                	}
                }
            }
    	}
	}
}
