package com.Fishmod.fur.entities;

import java.util.EnumSet;
import javax.annotation.Nullable;

import com.Fishmod.fur.core.SpawnUtil;
import com.Fishmod.fur.data.providers.FURTags;
import com.Fishmod.fur.entities.ai.FURMeleeAttackGoal;
import com.Fishmod.fur.entities.tameable.unburied.UnburiedEntity;
import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURItemRegistry;
import com.Fishmod.fur.init.FURSoundRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveThroughVillageGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
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

public class UndertakerEntity extends Monster implements IAggressive, GeoEntity {
private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	
    private static final RawAnimation IDLE = RawAnimation.begin().thenPlay("undertaker.model.idle");
    private static final RawAnimation WALK = RawAnimation.begin().thenPlay("undertaker.model.walk");
    private static final RawAnimation ATTACK = RawAnimation.begin().thenPlay("undertaker.model.attack");
    private static final RawAnimation CAST = RawAnimation.begin().thenPlay("undertaker.model.summon");
    
	public static final int ATTACK_TIMER = 40;
	public static final int SPELL_TIMER = 40;
	private int attackTimer;
	protected int spellTicks;
	
	public UndertakerEntity(EntityType<? extends UndertakerEntity> p_i48549_1_, Level worldIn) {
		super(p_i48549_1_, worldIn);
		this.xpReward = 12;
	}
	
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new AICastingApell());
        this.goalSelector.addGoal(2, new UndertakerEntity.AIUseSpell());
        this.goalSelector.addGoal(3, new UndertakerEntity.AttackGoal(this));  
        /*if(!FURConfig.SunScreen_Mode.get())*/this.goalSelector.addGoal(4, new FleeSunGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
    	this.goalSelector.addGoal(6, new MoveThroughVillageGoal(this, 1.0D, true, 4, this::canBreakDoors));
    	this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    	this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }
    
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.21D)
        		.add(Attributes.FOLLOW_RANGE, 16.0D)
        		.add(Attributes.MAX_HEALTH, 40.0D/*FURConfig.Undertaker_Health.get()*/)
        		.add(Attributes.ATTACK_DAMAGE, 6.0D/*FURConfig.Undertaker_Attack.get()*/)
        		.add(Attributes.ARMOR, 3.0D)
        		.add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }
    
    public static boolean checkUndertakerSpawnRules(EntityType<? extends UndertakerEntity> p_223316_0_, ServerLevelAccessor p_223316_1_, MobSpawnType p_223316_2_, BlockPos p_223316_3_, RandomSource p_223316_4_) {
        return Monster.checkMonsterSpawnRules(p_223316_0_, p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_);//SpawnUtil.isAllowedDimension(this.dimension);
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
    
    public boolean canBreakDoors() {
        return false;
	}
    
    @Override
    public double getMyRidingOffset() {
        return -0.75D;
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
        
        if (this.spellTicks > 0) {
            --this.spellTicks;
        }
               
		if (/*!FURConfig.SunScreen_Mode.get() && */this.isSunBurnTick()) {
			this.setSecondsOnFire(40);
		}   	   	        
    }
	
    public boolean doHurtTarget(Entity entityIn) {
    	boolean flag = super.doHurtTarget(entityIn);
    			
    	if (flag) {
    		float f = this.level().getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
    		
    		this.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1.0F, 1.0F);
    		
            if (this.getMainHandItem().isEmpty() && this.isOnFire() && this.random.nextFloat() < f * 0.3F) {
            	entityIn.setSecondsOnFire(2 * (int)f);
            }
    	}

        return flag;
    }
	
    /**
     * Gives armor or weapon for entity based on given DifficultyInstance
     */
    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
    	this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(FURItemRegistry.UNDERTAKER_SHOVEL.get()));
    	this.getMainHandItem().setDamageValue(this.getRandom().nextInt(this.getMainHandItem().getMaxDamage()));
    }
    
	@Override
    public float getStandingEyeHeight(Pose p_213348_1_, EntityDimensions p_213348_2_) {
        return p_213348_2_.height * 0.75F;
    }
    
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.spellTicks = compound.getInt("SpellTicks");
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("SpellTicks", this.spellTicks);
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_213386_1_, DifficultyInstance difficulty, MobSpawnType p_213386_3_, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag p_213386_5_) {
        /*this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Undertaker_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Undertaker_Attack.get());
    	this.setHealth(this.getMaxHealth());*/
    	
		this.populateDefaultEquipmentSlots(this.random, difficulty);
        this.populateDefaultEquipmentEnchantments(this.random, difficulty);
               
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
            this.attackTimer = ATTACK_TIMER;
        } else if (id == 10) {   		
        	this.spellTicks = SPELL_TIMER;
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
            return UndertakerEntity.this.getSpellTicks() > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            super.start();
            UndertakerEntity.this.getNavigation().stop();
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
            if (UndertakerEntity.this.getTarget() != null) {
                UndertakerEntity.this.getLookControl().setLookAt(UndertakerEntity.this.getTarget(), (float)UndertakerEntity.this.getMaxHeadYRot(), (float)UndertakerEntity.this.getMaxHeadXRot());
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
            if (!UndertakerEntity.this.getMainHandItem().getItem().equals(FURItemRegistry.UNDERTAKER_SHOVEL.get()))
            	return false;
            else if (UndertakerEntity.this.getTarget() == null)
                return false;
            else if (UndertakerEntity.this.isSpellcasting() || !UndertakerEntity.this.hasLineOfSight(UndertakerEntity.this.getTarget()))
                return false;
            else {
                int i = UndertakerEntity.this.level().getEntitiesOfClass(UnburiedEntity.class, UndertakerEntity.this.getBoundingBox().inflate(16.0D)).size();
            	return UndertakerEntity.this.tickCount >= this.spellCooldown && i < 4/*FURConfig.Undertaker_Ability_Max.get()*/;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return UndertakerEntity.this.getTarget() != null && this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.spellWarmup = this.getCastWarmupTime();
            UndertakerEntity.this.spellTicks = this.getCastingTime();
            this.spellCooldown = UndertakerEntity.this.tickCount + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();
            UndertakerEntity.this.level().broadcastEntityEvent(UndertakerEntity.this, (byte)10);
            if (soundevent != null) {
                UndertakerEntity.this.playSound(soundevent, 1.0F, 1.0F);
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            --this.spellWarmup;

            if (this.spellWarmup == 0) {
                this.castSpell();
                UndertakerEntity.this.playSound(UndertakerEntity.this.getSpellSound(), 1.0F, 1.0F);
            }
        }

        protected void castSpell() {
            for (int i = 0; i < 4/*FURConfig.Undertaker_Ability_Num.get()*/; ++i) {
            	if (UndertakerEntity.this.level() instanceof ServerLevel) {
	                BlockPos blockpos = UndertakerEntity.this.blockPosition().offset(-6 + UndertakerEntity.this.getRandom().nextInt(12), 0, -6 + UndertakerEntity.this.getRandom().nextInt(12));
	                UnburiedEntity entity;
	                Holder<Biome> Biome = UndertakerEntity.this.level().getBiome(UndertakerEntity.this.blockPosition());
	                
	                if (Biome.containsTag(BiomeTags.HAS_DESERT_PYRAMID)) {
	                	entity = SpawnUtil.trySpawnEntity(FUREntityRegistry.MUMMY.get(), ((ServerLevel) UndertakerEntity.this.level()), blockpos);
	                } else if (Biome.containsTag(BiomeTags.SPAWNS_SNOW_FOXES)) {
	                	entity = SpawnUtil.trySpawnEntity(FUREntityRegistry.FRIGID.get(), ((ServerLevel) UndertakerEntity.this.level()), blockpos);
	                } else if (Biome.containsTag(FURTags.HAS_MYCOSIS)) {
	                	entity = SpawnUtil.trySpawnEntity(FUREntityRegistry.MYCOSIS.get(), ((ServerLevel) UndertakerEntity.this.level()), blockpos);
	                } else {
	                	entity = SpawnUtil.trySpawnEntity(FUREntityRegistry.UNBURIED.get(), ((ServerLevel) UndertakerEntity.this.level()), blockpos);
	                }
	
	                if (entity != null) {
		                entity.setOwnerUUID(UndertakerEntity.this.getUUID());
		                entity.setSpellcasting();   		                
		                
		                if(UndertakerEntity.this.getTarget() != null) {
		                	entity.setTarget(UndertakerEntity.this.getTarget());
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
            return 15/*FURConfig.Undertaker_Ability_Cooldown.get()*/ * 20;
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.EVOKER_PREPARE_ATTACK;
        }
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return FURSoundRegistry.UNDERTAKER_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FURSoundRegistry.UNDERTAKER_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.UNDERTAKER_DEATH.get();
    }
    
    protected SoundEvent getSpellSound() {
        return SoundEvents.EVOKER_CAST_SPELL;
    }

	@Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    	this.playSound(SoundEvents.ZOMBIE_STEP, 0.15F, 1.0F);
	}

    /**
     * Get this Entity's CreatureAttribute
     */
    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }
    
    /**
     * Entity won't drop items or experience points if this returns false
     */
    @Override
    protected boolean shouldDropLoot() {
       return !this.isOnFire() || this.lastHurtByPlayer != null;
    }
    
    static class AttackGoal extends FURMeleeAttackGoal {
        public AttackGoal(PathfinderMob p_i46676_1_) {
           super(p_i46676_1_, 1.25D, false, 32);
        }

    	protected int atkTimerMax() {
    		return ATTACK_TIMER;
    	}
    	
    	protected int atkTimerHit() {
    		return 10;
    	}
    	
    	protected byte atkTimerEvent() {
    		return (byte) 4;
    	}
	}
    
    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
    	if (this.getSpellTicks() >= SPELL_TIMER - 5) {
    		state.getController().setAnimation(CAST);
    	} else if (this.getAttackTimer() == ATTACK_TIMER) {
    		state.getController().setAnimation(ATTACK);
    	} else if (this.isSpellcasting() || this.getAttackTimer() > 0) {
    		return PlayState.CONTINUE;
    	} else if (state.isMoving() && !this.isInWater()) {
            state.getController().setAnimation(WALK);
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
