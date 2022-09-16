package com.Fishmod.mod_LavaCow.entities;

import java.util.EnumSet;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.projectiles.DeathCoilEntity;
import com.Fishmod.mod_LavaCow.entities.projectiles.SandBurstEntity;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;
import com.Fishmod.mod_LavaCow.misc.LootTableHandler;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
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
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.BossInfo;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.Explosion;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerBossInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SkeletonKingEntity extends MonsterEntity implements IAggressive {
	private final ServerBossInfo bossEvent = (ServerBossInfo)(new ServerBossInfo(this.getDisplayName(), BossInfo.Color.WHITE, BossInfo.Overlay.PROGRESS)).setDarkenScreen(false);
	private static final DataParameter<BlockPos> SPAWN_ORIGIN = EntityDataManager.defineId(SkeletonKingEntity.class, DataSerializers.BLOCK_POS);
	private static final DataParameter<Integer> DATA_ID_INV = EntityDataManager.defineId(SkeletonKingEntity.class, DataSerializers.INT);
	private int attackTimer;
	protected int spellTicks[] = {0, 0};
	
	public SkeletonKingEntity(EntityType<? extends SkeletonKingEntity> p_i48549_1_, World worldIn) {
		super(p_i48549_1_, worldIn);
		this.xpReward = 50;
	}
	
    @Override
    protected void registerGoals() {
    	this.goalSelector.addGoal(0, new SkeletonKingEntity.DoNothingGoal());
        this.goalSelector.addGoal(1, new AICastingApell());
        this.goalSelector.addGoal(2, new SkeletonKingEntity.AITeleportSpell());
        this.goalSelector.addGoal(3, new SkeletonKingEntity.AITossSpell());
        this.goalSelector.addGoal(4, new SkeletonKingEntity.AISummoningSpell());
        this.goalSelector.addGoal(5, new SkeletonKingEntity.AISkeletonKingAttackMelee(this, 1.0D, false));
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
        		.add(Attributes.MOVEMENT_SPEED, 0.3D)
        		.add(Attributes.FOLLOW_RANGE, 40.0D)
        		.add(Attributes.MAX_HEALTH, FURConfig.SkeletonKing_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, FURConfig.SkeletonKing_Attack.get())
        		.add(Attributes.ARMOR, 8.0D)
        		.add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }
    
    @Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SPAWN_ORIGIN, new BlockPos(0, 0, 0));
		this.entityData.define(DATA_ID_INV, 0);
	}
    
    @Override
    public boolean addEffect(EffectInstance p_195064_1_) {
    	return false;
	}
    
	@Override
	public boolean removeWhenFarAway(double p_213397_1_) {
		return false;
	}

	@Override
	public boolean updateInWaterStateAndDoFluidPushing() {
		return false;
	}
	
    public boolean isSpellcasting() {
    	for(int i = 0 ; i < 2; i++) {
    		if(this.spellTicks[i] > 0)
    			return true;
    	}

    	return false;
    }
	
    public boolean isSpellcasting(int i) {
    	return this.spellTicks[i] > 0;
    }
    
    public int getSpellTicks(int i) {
        return this.spellTicks[i];
    }
    
    @Override
    public double getMyRidingOffset() {
        return -0.95D;
    }
	
    @Override
    public void tick() {
		super.tick();
		
        if (this.attackTimer > 0) {
            --this.attackTimer;
            if(this.attackTimer > 20 && this.getTarget() != null)
            	this.getLookControl().setLookAt(this.getTarget(), 10.0F, 10.0F);
            this.setDeltaMovement(Vector3d.ZERO);
            
            if(this.attackTimer == 20)
            	this.playSound(FURSoundRegistry.SKELETONKING_ATTACK, 1.0F, 1.0F);
        }
        
        if (this.getInvulnerableTicks() > 0) {
            for(int i1 = 0; i1 < 3; ++i1) {
               this.level.addParticle(ParticleTypes.LARGE_SMOKE, this.getX() + this.random.nextGaussian(), this.getY() + (double)(this.random.nextFloat() * 3.3F), this.getZ() + this.random.nextGaussian(), 0.0D, this.random.nextDouble() * 0.5D, 0.0D);
            }
        }
        
        for(int i = 0 ; i < 2; i++) {
	        if (this.spellTicks[i] > 0) {
	            --this.spellTicks[i];
	            
	            if (this.level.isClientSide()) {
	                for (int j = 0; j < 4; ++j) {
	                    this.level.addParticle(ParticleTypes.LARGE_SMOKE, this.getX() + (this.random.nextDouble() - 0.5D) * (double)this.getBbWidth(), this.getY() + this.random.nextDouble() * (double)this.getBbHeight() - 0.25D, this.getZ() + (this.random.nextDouble() - 0.5D) * (double)this.getBbWidth(), 0.0D, this.random.nextDouble() * 0.5D, 0.0D);
	                }
	            }
	        }    
        }
       
        if (this.getAttackTimer() == 18 && this.deathTime <= 0) {
			double d0 = this.getX() + 2.5D * this.getLookAngle().normalize().x;
            double d1 = this.getY();
            double d2 = this.getZ() + 2.5D * this.getLookAngle().normalize().z;
            BlockState state = this.level.getBlockState(new BlockPos(d0, d1, d2).below());
    		       	
            if (state.getMaterial().isSolid()) {
	        	this.playSound(state.getBlock().getSoundType(state, this.level, new BlockPos(d0, d1, d2), this).getBreakSound(), 1, 0.5F);
	        	
	            if (this.level.isClientSide()) {
	            	for(int i = 0; i < 64; i++)
	            		this.level.addParticle(new BlockParticleData(ParticleTypes.BLOCK, state).setPos(new BlockPos(d0, d1, d2).below()), d0 + (this.random.nextDouble() * 4.0D - 2.0D), d1, d2 + (this.random.nextDouble() * 4.0D - 2.0D), this.random.nextGaussian() * 0.02D, this.random.nextGaussian() * 0.02D, this.random.nextGaussian() * 0.02D);
	            }
	        }
	        
            for (LivingEntity entitylivingbase : this.level.getEntitiesOfClass(LivingEntity.class, new AxisAlignedBB(d0, d1, d2, d0, d1, d2).inflate(1.0D))) {
            	if (!this.equals(entitylivingbase) && !this.isAlliedTo(entitylivingbase)) {
                	entitylivingbase.hurt(DamageSource.mobAttack(this), (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE));
                }
            }
        }   
	}
	
	@Override
	public int getMaxFallDistance() {
		//Doesn't take fall damage
		return 256;
	}

    @Override
	public boolean doHurtTarget(Entity entityIn) {
    	if (this.attackTimer == 0) {
	    	this.attackTimer = 30;	  
	        this.level.broadcastEntityEvent(this, (byte)4);
    	}

		return false;
    }
	
	@Override
	protected void customServerAiStep() {
		super.customServerAiStep();
		
		if (this.getInvulnerableTicks() > 0) {
			this.setInvulnerableTicks(this.getInvulnerableTicks() - 1);
			if (this.tickCount % 5 == 0) {
				this.heal(this.getMaxHealth() * 0.03F);
			}
		}	      
		
		this.bossEvent.setPercent(this.getHealth() / this.getMaxHealth());
	}
	
	@Override
    public int getAttackTimer() {
        return this.attackTimer;
    }

	@Override
	public void setAttackTimer(int i) {
		this.attackTimer = i;
	}
	
	public void makeStuckInBlock(BlockState p_213295_1_, Vector3d p_213295_2_) {
	}
	
    /**
     * Called when the entity is attacked.
     */
	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (this.getInvulnerableTicks() > 0 && source != DamageSource.OUT_OF_WORLD) {
            return false;
		}
		
    	if(source.equals(DamageSource.IN_WALL)) {
    		this.level.explode(this, this.getX(), this.getY(), this.getZ(), (float)(3.0D + this.random.nextDouble() * 1.5D), true, net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this) ? Explosion.Mode.DESTROY : Explosion.Mode.NONE);
    	}
    	  		
    	return super.hurt(source, amount);
    }
	
    /**
     * Handler for {@link World#setEntityState}
     */
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
    	if (id == 4) {
            this.attackTimer = 30;
        } else if (id == 10) {  		
        	this.spellTicks[0] = 30;
        } else if (id == 11) {		
        	this.spellTicks[1] = 15;
        } else {
            super.handleEntityEvent(id);
        }
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT p_213386_5_) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.SkeletonKing_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.SkeletonKing_Attack.get());
    	this.setHealth(this.getMaxHealth());
    	       
    	return super.finalizeSpawn(p_213386_1_, difficulty, p_213386_3_, livingdata, p_213386_5_);
    }
    
    public class AICastingApell extends Goal {
        public AICastingApell() {
        	this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        /**
         * Returns whether the Goal should begin execution.
         */
        public boolean canUse() {
            return SkeletonKingEntity.this.isSpellcasting();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            super.start();
            SkeletonKingEntity.this.getNavigation().stop();
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
            if (SkeletonKingEntity.this.getTarget() != null) {
                SkeletonKingEntity.this.getLookControl().setLookAt(SkeletonKingEntity.this.getTarget(), (float)SkeletonKingEntity.this.getMaxHeadYRot(), (float)SkeletonKingEntity.this.getMaxHeadXRot());
            }
        }
    }
    
    public class AISummoningSpell extends Goal {
        protected int spellWarmup;
        protected int spellCooldown;

        /**
         * Returns whether the Goal should begin execution.
         */
        public boolean canUse() {
        	if (SkeletonKingEntity.this.getTarget() == null || SkeletonKingEntity.this.getAttackTimer() > 0)
                return false;
            else if ((SkeletonKingEntity.this.isSpellcasting() || !SkeletonKingEntity.this.canSee(SkeletonKingEntity.this.getTarget())) && SkeletonKingEntity.this.distanceTo(SkeletonKingEntity.this.getTarget()) > 16.0D)
                return false;

        	return SkeletonKingEntity.this.tickCount >= this.spellCooldown;
        }

        /**
         * Returns whether an in-progress Goal should continue executing
         */
        public boolean canContinueToUse() {
            return SkeletonKingEntity.this.getTarget() != null && this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.spellWarmup = this.getCastWarmupTime();
            SkeletonKingEntity.this.spellTicks[0] = this.getCastingTime();
            this.spellCooldown = SkeletonKingEntity.this.tickCount + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();
            SkeletonKingEntity.this.level.broadcastEntityEvent(SkeletonKingEntity.this, (byte)10);
            if (soundevent != null) {
                SkeletonKingEntity.this.playSound(soundevent, 1.0F, 1.0F);
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            --this.spellWarmup;

            if (this.spellWarmup == 0) {
                this.castSpell();
                //EntitySkeletonKing.this.playSound(EntitySkeletonKing.this.getSpellSound(), 1.0F, 1.0F);
            }
        }

        protected void castSpell() {
            for (int i = 0; i < 11; ++i) {
                BlockPos blockpos = SkeletonKingEntity.this.blockPosition().offset(-12 + SkeletonKingEntity.this.random.nextInt(24), 0, -12 + SkeletonKingEntity.this.random.nextInt(24));

                SandBurstEntity entityevokerfangs = new SandBurstEntity(SkeletonKingEntity.this.level, (double)blockpos.getX(), (double)SpawnUtil.getHeight(SkeletonKingEntity.this).getY(), (double)blockpos.getZ(), 0.0F, 20, SkeletonKingEntity.this);
                SkeletonKingEntity.this.level.addFreshEntity(entityevokerfangs);
            }
            
            SandBurstEntity entityevokerfangs = new SandBurstEntity(SkeletonKingEntity.this.level, SkeletonKingEntity.this.getTarget().getX(), SkeletonKingEntity.this.getTarget().getY(), SkeletonKingEntity.this.getTarget().getZ(), 0.0F, 10, SkeletonKingEntity.this);
            SkeletonKingEntity.this.level.addFreshEntity(entityevokerfangs);
        }

        protected int getCastWarmupTime() {
            return 30;
        }

        protected int getCastingTime() {
            return 30;
        }

        protected int getCastingInterval() {
            return FURConfig.SkeletonKing_AbilityA_Cooldown.get() * 20;
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound() {
        	return FURSoundRegistry.SKELETONKING_SPELL_SUMMON;
        }
    }
    
    public class AITeleportSpell extends Goal {
        protected int spellWarmup;
        protected int spellCooldown;

        /**
         * Returns whether the Goal should begin execution.
         */
        public boolean canUse() {
        	if (SkeletonKingEntity.this.getTarget() == null || SkeletonKingEntity.this.getAttackTimer() > 0)
                return false;
            else if (SkeletonKingEntity.this.isSpellcasting() || SkeletonKingEntity.this.distanceTo(SkeletonKingEntity.this.getTarget()) > 16.0D || SkeletonKingEntity.this.distanceTo(SkeletonKingEntity.this.getTarget()) < 4.0D)
                return false;

        	return SkeletonKingEntity.this.tickCount >= this.spellCooldown;
        }

        /**
         * Returns whether an in-progress Goal should continue executing
         */
        public boolean canContinueToUse() {
            return SkeletonKingEntity.this.getTarget() != null && this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.spellWarmup = this.getCastWarmupTime();
            this.spellCooldown = SkeletonKingEntity.this.tickCount + this.getCastingInterval();
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
        	SkeletonKingEntity.this.randomTeleport(SkeletonKingEntity.this.getTarget().getX(), SkeletonKingEntity.this.getTarget().getY(), SkeletonKingEntity.this.getTarget().getZ(), false);
        	SkeletonKingEntity.this.playSound(this.getSpellPrepareSound(), 1.0F, 1.0F);
        }

        protected int getCastWarmupTime() {
            return 20;
        }

        protected int getCastingTime() {
            return 30;
        }

        protected int getCastingInterval() {
            return FURConfig.SkeletonKing_AbilityB_Cooldown.get() * ((SkeletonKingEntity.this.getHealth() >  SkeletonKingEntity.this.getMaxHealth() * 0.5F) ? 20 : 10);
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound() {
        	return FURSoundRegistry.SKELETONKING_SPELL_TELEPORT;
        }
    }
    
    public class AITossSpell extends Goal {
        protected int spellWarmup;
        protected int spellCooldown;

        /**
         * Returns whether the Goal should begin execution.
         */
        public boolean canUse() {
        	if (SkeletonKingEntity.this.getTarget() == null || SkeletonKingEntity.this.getHealth() >  SkeletonKingEntity.this.getMaxHealth() * 0.5F || SkeletonKingEntity.this.getAttackTimer() > 0)
                return false;
            else if ((SkeletonKingEntity.this.isSpellcasting() || !SkeletonKingEntity.this.canSee(SkeletonKingEntity.this.getTarget())) && SkeletonKingEntity.this.distanceTo(SkeletonKingEntity.this.getTarget()) > 16.0D)
                return false;

        	return SkeletonKingEntity.this.tickCount >= this.spellCooldown;
        }

        /**
         * Returns whether an in-progress Goal should continue executing
         */
        public boolean canContinueToUse() {
            return SkeletonKingEntity.this.getTarget() != null && this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.spellWarmup = this.getCastWarmupTime();
            SkeletonKingEntity.this.spellTicks[1] = this.getCastingTime();
            this.spellCooldown = SkeletonKingEntity.this.tickCount + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();
            SkeletonKingEntity.this.level.broadcastEntityEvent(SkeletonKingEntity.this, (byte)11);
            if (soundevent != null)
            {
                SkeletonKingEntity.this.playSound(soundevent, 1.0F, 1.0F);
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
        	if(SkeletonKingEntity.this.getTarget() == null)
        		return;
        	
        	for(int i = -1; i < 2; i++) {
        		DeathCoilEntity entitysnowball = new DeathCoilEntity(SkeletonKingEntity.this.level, SkeletonKingEntity.this, 0.0D, 0.0D, 0.0D);	  
        		entitysnowball.moveTo(SkeletonKingEntity.this.getX(), SkeletonKingEntity.this.getY() + (double)SkeletonKingEntity.this.getEyeHeight() - 0.1D, SkeletonKingEntity.this.getZ());
	            entitysnowball.shootFromRotation(SkeletonKingEntity.this, SkeletonKingEntity.this.xRot, SkeletonKingEntity.this.yRot + (i * 30.0F), 0.0F, 0.75F, 1.0F);	            
	            SkeletonKingEntity.this.level.addFreshEntity(entitysnowball);
        	}
        }

        protected int getCastWarmupTime() {
            return 15;
        }

        protected int getCastingTime() {
            return 15;
        }

        protected int getCastingInterval() {
            return FURConfig.SkeletonKing_AbilityC_Cooldown.get();
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound() {
            return FURSoundRegistry.SKELETONKING_SPELL_TOSS;
        }
    }
    
    static class AISkeletonKingAttackMelee extends MeleeAttackGoal {
    	private int ticksUntilNextAttack;
    	
		public AISkeletonKingAttackMelee(CreatureEntity creature, double speedIn, boolean useLongMemory) {
			super(creature, speedIn, useLongMemory);
		}

		public void start() {
			super.start();
			this.ticksUntilNextAttack = 0;
		}
		
		public void tick() {
			super.tick();
			this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
		}
		
	    protected void checkAndPerformAttack(LivingEntity p_190102_1_, double p_190102_2_) {
	        double d0 = this.getAttackReachSqr(p_190102_1_);

	        if (p_190102_2_ <= d0 && this.getTicksUntilNextAttack() <= 0) {
	            this.ticksUntilNextAttack = 60;
	            this.mob.swing(Hand.MAIN_HAND);
	            this.mob.doHurtTarget(p_190102_1_);
	        }
	    }

	    protected void resetAttackCooldown() {
	    	this.ticksUntilNextAttack = 60;
	    }

	    protected boolean isTimeToAttack() {
	    	return this.ticksUntilNextAttack <= 0;
	    }

	    protected int getTicksUntilNextAttack() {
	    	return this.ticksUntilNextAttack;
	    }
	    
	    protected double getAttackReachSqr(LivingEntity attackTarget) {
	        return (double)(this.mob.getBbWidth() * 3.0F * this.mob.getBbWidth() * 3.0F + attackTarget.getBbWidth());
	    }	
    }
    
    class DoNothingGoal extends Goal {
        public DoNothingGoal() {
           this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP, Goal.Flag.LOOK));
        }

        public boolean canUse() {
           return SkeletonKingEntity.this.getInvulnerableTicks() > 0;
        }
	}
    
    public int getInvulnerableTicks() {
        return this.entityData.get(DATA_ID_INV);
	}

	public void setInvulnerableTicks(int p_82215_1_) {
		this.entityData.set(DATA_ID_INV, p_82215_1_);
	}
	
	@Override
	public void setCustomName(@Nullable ITextComponent name) {
		super.setCustomName(name);
		this.bossEvent.setName(this.getDisplayName());
	}
	
	@Override
	public void startSeenByPlayer(ServerPlayerEntity p_184178_1_) {
		super.startSeenByPlayer(p_184178_1_);
		this.bossEvent.addPlayer(p_184178_1_);
	}

	@Override
	public void stopSeenByPlayer(ServerPlayerEntity p_184203_1_) {
		super.stopSeenByPlayer(p_184203_1_);
		this.bossEvent.removePlayer(p_184203_1_);
	}
	
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.spellTicks[0] = compound.getInt("SpellTicks0");
        this.spellTicks[1] = compound.getInt("SpellTicks1");
        this.setInvulnerableTicks(compound.getInt("Invul"));
        if (this.hasCustomName()) {
            this.bossEvent.setName(this.getDisplayName());
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("SpellTicks0", this.spellTicks[0]);
        compound.putInt("SpellTicks1", this.spellTicks[1]);
        compound.putInt("Invul", this.getInvulnerableTicks());
    }
	
    @Nullable
    @Override
    protected ResourceLocation getDefaultLootTable() {    	
        return FURConfig.SkeletonKing_Loot_Option.get() ? null : super.getDefaultLootTable();
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.SKELETON_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.SKELETON_HURT;
    }

    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.SKELETONKING_DEATH;
    }
    
    protected SoundEvent getSpellSound() {
        return SoundEvents.EVOKER_CAST_SPELL;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.SKELETON_STEP;
    }
    
    /**
     * Get this Entity's CreatureAttribute
     */
    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.UNDEAD;
    }
	
    /**
     * Called when the mob's health reaches 0.
     */
	@Override
    public void dropAllDeathLoot(DamageSource cause) {		
		super.dropAllDeathLoot(cause);
		
		if (FURConfig.SkeletonKing_Loot_Option.get()) {
			BlockPos position = SpawnUtil.getHeight(this);
			
			this.level.setBlock(position, Blocks.CHEST.defaultBlockState(), 8 | 4 | 2 | 1);
	        if (this.level.getBlockState(position).getBlock() instanceof ChestBlock) {
	        	LockableLootTileEntity.setLootTable(this.level, this.random, position, LootTableHandler.SKELETON_KING);
	        }
		}
    }
}
