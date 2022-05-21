package com.Fishmod.mod_LavaCow.entities;

import java.util.EnumSet;
import java.util.Random;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.tameable.UnburiedEntity;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;

import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureAttribute;
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
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.MoveThroughVillageGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class UndertakerEntity extends MonsterEntity implements IAggressive {
	
	private int attackTimer;
	protected int spellTicks;
	
	public UndertakerEntity(EntityType<? extends UndertakerEntity> p_i48549_1_, World worldIn) {
		super(p_i48549_1_, worldIn);
		this.xpReward = 12;
	}
	
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new AICastingApell());
        this.goalSelector.addGoal(2, new UndertakerEntity.AIUseSpell());
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.25D, false));  
        if(!FURConfig.SunScreen_Mode.get())this.goalSelector.addGoal(4, new FleeSunGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
    	this.goalSelector.addGoal(6, new MoveThroughVillageGoal(this, 1.0D, true, 4, this::canBreakDoors));
    	this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    	this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
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
    
    public static boolean checkUndertakerSpawnRules(EntityType<? extends UndertakerEntity> p_223316_0_, IWorld p_223316_1_, SpawnReason p_223316_2_, BlockPos p_223316_3_, Random p_223316_4_) {
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
        // Should always return LivingEntity (according to the documentation).
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
	
    /**
     * Gives armor or weapon for entity based on given DifficultyInstance
     */
    @Override
    protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty) {
    	this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(FURItemRegistry.UNDERTAKER_SHOVEL));
    	this.getMainHandItem().setDamageValue(this.getRandom().nextInt(this.getMainHandItem().getMaxDamage()));
    }
    
	@Override
    public float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
        return p_213348_2_.height * 0.75F;
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
    	
		this.populateDefaultEquipmentSlots(difficulty);
        this.populateDefaultEquipmentEnchantments(difficulty);
               
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
        	this.spellTicks = 30;
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
            if (!UndertakerEntity.this.getMainHandItem().getItem().equals(FURItemRegistry.UNDERTAKER_SHOVEL))
            	return false;
            else if (UndertakerEntity.this.getTarget() == null)
                return false;
            else if (UndertakerEntity.this.isSpellcasting() || !UndertakerEntity.this.canSee(UndertakerEntity.this.getTarget()))
                return false;
            else {
                int i = UndertakerEntity.this.level.getEntitiesOfClass(UnburiedEntity.class, UndertakerEntity.this.getBoundingBox().inflate(16.0D)).size();
            	return UndertakerEntity.this.tickCount >= this.spellCooldown && i < FURConfig.Undertaker_Ability_Max.get();
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
            UndertakerEntity.this.level.broadcastEntityEvent(UndertakerEntity.this, (byte)10);
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
            for (int i = 0; i < FURConfig.Undertaker_Ability_Num.get(); ++i) {
                BlockPos blockpos = UndertakerEntity.this.blockPosition().offset(-6 + UndertakerEntity.this.getRandom().nextInt(12), 0, -6 + UndertakerEntity.this.getRandom().nextInt(12));
                if(UndertakerEntity.this.getRandom().nextFloat() < 0.15F) {
                	if (BiomeDictionary.getTypes(SpawnUtil.getRegistryKey(UndertakerEntity.this.level.getBiome(UndertakerEntity.this.blockPosition()))).contains(Type.DRY)) {
                		
                    	MummyEntity entityvex = FUREntityRegistry.MUMMY.create(UndertakerEntity.this.level);
                        entityvex.moveTo(blockpos, 0.0F, 0.0F);
                        
                        if(!UndertakerEntity.this.level.isClientSide())
                        	UndertakerEntity.this.level.addFreshEntity(entityvex);
                        
                        if(UndertakerEntity.this.getTarget() != null)
                        	entityvex.setTarget(UndertakerEntity.this.getTarget());
                        
                    } else if (BiomeDictionary.getTypes(SpawnUtil.getRegistryKey(UndertakerEntity.this.level.getBiome(UndertakerEntity.this.blockPosition()))).contains(Type.COLD)) {
                    	
                    	FrigidEntity entityvex = FUREntityRegistry.FRIGID.create(UndertakerEntity.this.level);
                        entityvex.moveTo(blockpos, 0.0F, 0.0F);
                        
                        if(!UndertakerEntity.this.level.isClientSide())
                        	UndertakerEntity.this.level.addFreshEntity(entityvex);
                        
                        if(UndertakerEntity.this.getTarget() != null)
                        	entityvex.setTarget(UndertakerEntity.this.getTarget());
                        
                    } else if (BiomeDictionary.getTypes(SpawnUtil.getRegistryKey(UndertakerEntity.this.level.getBiome(UndertakerEntity.this.blockPosition()))).contains(Type.WET)) {
                    	
                    	MycosisEntity entityvex = FUREntityRegistry.MYCOSIS.create(UndertakerEntity.this.level);
                        entityvex.moveTo(blockpos, 0.0F, 0.0F);
                        
                        if(!UndertakerEntity.this.level.isClientSide())
                        	UndertakerEntity.this.level.addFreshEntity(entityvex);
                        
                        if(UndertakerEntity.this.getTarget() != null)
                        	entityvex.setTarget(UndertakerEntity.this.getTarget());
                        
                    }                  
                } else {
                	
                	UnburiedEntity entityvex = FUREntityRegistry.UNBURIED.create(UndertakerEntity.this.level);
                    entityvex.moveTo(blockpos, 0.0F, 0.0F);
                    entityvex.setOwnerUUID(UndertakerEntity.this.getUUID());
                    
                    if(!UndertakerEntity.this.level.isClientSide())
                    	UndertakerEntity.this.level.addFreshEntity(entityvex);
                    
                    if(UndertakerEntity.this.getTarget() != null)
                    	entityvex.setTarget(UndertakerEntity.this.getTarget());                   
                }                             
            }
        }

        protected int getCastWarmupTime() {
            return 30;
        }

        protected int getCastingTime() {
            return 30;
        }

        protected int getCastingInterval() {
            return FURConfig.Undertaker_Ability_Cooldown.get() * 20;
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.EVOKER_PREPARE_ATTACK;
        }
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return FURSoundRegistry.UNDERTAKER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FURSoundRegistry.UNDERTAKER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.UNDERTAKER_DEATH;
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
    public CreatureAttribute getMobType() {
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
