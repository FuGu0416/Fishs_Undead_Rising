package com.Fishmod.mod_LavaCow.entities.floating;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.entities.tameable.WetaEntity;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;
import com.Fishmod.mod_LavaCow.init.FURParticleRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;

import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.MoveThroughVillageGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class AvatonEntity extends FloatingMobEntity {
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.defineId(AvatonEntity.class, DataSerializers.INT);
	
	public AvatonEntity(EntityType<? extends AvatonEntity> p_i48549_1_, World worldIn) {
        super(p_i48549_1_, worldIn);
    }
	
    @Override
    protected void registerGoals() {
    	super.registerGoals();  
        this.goalSelector.addGoal(0, new SwimGoal(this));
    	this.goalSelector.addGoal(3, new AvatonEntity.AIUseSpell());
    	this.goalSelector.addGoal(6, new MoveThroughVillageGoal(this, 1.0D, true, 4, this::canBreakDoors));
    }

    @Override
    protected void applyEntityAI() {
    	this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    	this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }
    
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.175D)
        		.add(Attributes.FOLLOW_RANGE, 32.0D)
        		.add(Attributes.MAX_HEALTH, FURConfig.Avaton_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, FURConfig.Avaton_Attack.get());
    }
        
    @Override
    protected void defineSynchedData() {
    	super.defineSynchedData();
    	this.getEntityData().define(SKIN_TYPE, Integer.valueOf(0));
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT p_213386_5_) {
    	this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Avaton_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Avaton_Attack.get());
    	this.setHealth(this.getMaxHealth());   		

    	return super.finalizeSpawn(worldIn, difficulty, p_213386_3_, livingdata, p_213386_5_);
    }
    
    @Override
    @Nullable
    protected IParticleData ParticleType() {
    	return FURParticleRegistry.LOCUST_SWARM;
    }
    
    protected boolean isSunBurnTick() {
		return false;
    }
    
    public boolean canBreakDoors() {
        return false;
	}
    	    
    public class AIUseSpell extends Goal {
        protected int spellWarmup;
        protected int spellCooldown;

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean canUse() {
        	if (AvatonEntity.this.isSpellcasting()) {
                return false;
            } else {
                int i = AvatonEntity.this.level.getEntitiesOfClass(WetaEntity.class, AvatonEntity.this.getBoundingBox().inflate(16.0D)).size();
                boolean farmlandnearby = false;
                for(int x = -2; x <= 2; x++)
                	for(int y = -3; y <= 3; y++)
                		for(int z = -2; z <= 2; z++) {
                			if (AvatonEntity.this.level.getBlockState(new BlockPos(AvatonEntity.this.getX() + x, AvatonEntity.this.getY() + y, AvatonEntity.this.getZ() + z)).getBlock() == Blocks.FARMLAND)
                				farmlandnearby = true;
                		}              
                
            	return AvatonEntity.this.tickCount >= this.spellCooldown && ((AvatonEntity.this.getTarget() != null && Math.abs(AvatonEntity.this.getY() - AvatonEntity.this.getTarget().getY()) < 4.0D) || farmlandnearby) && i < FURConfig.Avaton_Ability_Max.get();
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.spellWarmup = this.getCastWarmupTime();
            AvatonEntity.this.spellTicks = this.getCastingTime();
            AvatonEntity.this.level.broadcastEntityEvent(AvatonEntity.this, (byte)10);
            this.spellCooldown = AvatonEntity.this.tickCount + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();

            if (soundevent != null) {
                AvatonEntity.this.playSound(soundevent, 1.0F, 1.0F);
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            --this.spellWarmup;

            if (this.spellWarmup == 5) {
            	this.castSpell();           	
                AvatonEntity.this.playSound(AvatonEntity.this.getSpellSound(), 4.0F, 1.2F);                         
            }
        }

        protected void castSpell() {
            for (int i = 0; i < FURConfig.Avaton_Ability_Num.get(); ++i) {
                BlockPos blockpos = AvatonEntity.this.blockPosition().offset(-2 + AvatonEntity.this.getRandom().nextInt(5), 1, -2 + AvatonEntity.this.getRandom().nextInt(5));
                WetaEntity entityvex = FUREntityRegistry.WETA.create(AvatonEntity.this.level);
                entityvex.moveTo(blockpos, 0.0F, 0.0F);
                entityvex.setOwnerUUID(AvatonEntity.this.getUUID());                             

                if(!AvatonEntity.this.level.isClientSide())
                	AvatonEntity.this.level.addFreshEntity(entityvex);
                
                if(AvatonEntity.this.getTarget() != null)
                	entityvex.setTarget(AvatonEntity.this.getTarget());  
                
                if (AvatonEntity.this.level instanceof ServerWorld) {
	                for (int j = 0; j < 4; ++j) {
	                	double d0 = entityvex.getX() + (double)(AvatonEntity.this.getRandom().nextFloat() * entityvex.getBbWidth() * 2.0F) - (double)entityvex.getBbWidth();
	                	double d1 = entityvex.getY() + (double)(AvatonEntity.this.getRandom().nextFloat() * entityvex.getBbHeight());
	                	double d2 = entityvex.getZ() + (double)(AvatonEntity.this.getRandom().nextFloat() * entityvex.getBbWidth() * 2.0F) - (double)entityvex.getBbWidth();
	                	((ServerWorld) AvatonEntity.this.level).sendParticles(FURParticleRegistry.LOCUST_SWARM, d0, d1, d2, 15, 0.0D, 0.0D, 0.0D, 0.0D);
	                	
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
        	return FURConfig.Avaton_Ability_Cooldown.get() * 20;
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound() {
            return null;
        }
    }
   
    public int getSkin() {
        return this.getEntityData().get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
        this.getEntityData().set(SKIN_TYPE, Integer.valueOf(skinType));
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return FURSoundRegistry.AVATON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FURSoundRegistry.BANSHEE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.AVATON_DEATH;
    }
    
    protected SoundEvent getSpellSound() {
        return FURSoundRegistry.AVATON_SPELL;
    }
    
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.setSkin(compound.getInt("Variant"));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", getSkin());
    }
	
    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.UNDEAD;
    }
}
