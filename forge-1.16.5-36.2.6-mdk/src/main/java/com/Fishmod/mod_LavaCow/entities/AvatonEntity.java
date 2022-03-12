package com.Fishmod.mod_LavaCow.entities;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.entities.tameable.WetaEntity;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;
import com.Fishmod.mod_LavaCow.init.FURParticleRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.MoveThroughVillageGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AvatonEntity extends BansheeEntity {
	
	public AvatonEntity(EntityType<? extends AvatonEntity> p_i48549_1_, World worldIn)
    {
        super(p_i48549_1_, worldIn);
        this.setPathfindingMalus(PathNodeType.WATER, 8.0F);
    }
	
    @Override
    protected void registerGoals()
    {
    	this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(2, new AICastingApell());
        this.goalSelector.addGoal(3, new AvatonEntity.AIUseSpell());
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(6, new MoveThroughVillageGoal(this, 1.0D, true, 4, this::canBreakDoors));
        this.goalSelector.addGoal(7, new BansheeEntity.AIMoveRandom());
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
        		.add(Attributes.MOVEMENT_SPEED, 0.175D)
        		.add(Attributes.FOLLOW_RANGE, 32.0D)
        		.add(Attributes.MAX_HEALTH, FURConfig.Avaton_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, FURConfig.Avaton_Attack.get());
    }
    
    protected IParticleData ParticleType() {
    	return FURParticleRegistry.LOCUST_SWARM;
    }
    
    protected boolean isSunBurnTick() {
    	return false;
    }
    
    public boolean canBreakDoors() {
        return false;
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
        	if (AvatonEntity.this.isSpellcasting())
            {
                return false;
            }
            else
            {
                int i = AvatonEntity.this.level.getEntitiesOfClass(WetaEntity.class, AvatonEntity.this.getBoundingBox().inflate(16.0D)).size();
                boolean farmlandnearby = false;
                for(int x = -2; x <= 2; x++)
                	for(int y = -3; y <= 3; y++)
                		for(int z = -2; z <= 2; z++)
                		{
                			if (AvatonEntity.this.level.getBlockState(new BlockPos(AvatonEntity.this.getX() + x, AvatonEntity.this.getY() + y, AvatonEntity.this.getZ() + z)).getBlock() == Blocks.FARMLAND)
                				farmlandnearby = true;
                		}              
                
            	return AvatonEntity.this.tickCount >= this.spellCooldown && ((AvatonEntity.this.getTarget() != null && Math.abs(AvatonEntity.this.getY() - AvatonEntity.this.getTarget().getY()) < 4.0D) || farmlandnearby) && i < 16/*Modconfig.Avaton_Ability_Max*/;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse()
        {
            return this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start()
        {
            this.spellWarmup = this.getCastWarmupTime();
            AvatonEntity.this.spellTicks = this.getCastingTime();
            AvatonEntity.this.level.broadcastEntityEvent(AvatonEntity.this, (byte)10);
            this.spellCooldown = AvatonEntity.this.tickCount + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();

            if (soundevent != null)
            {
                AvatonEntity.this.playSound(soundevent, 1.0F, 1.0F);
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick()
        {
            --this.spellWarmup;

            if (this.spellWarmup == 5)
            {
                this.castSpell();
                AvatonEntity.this.playSound(AvatonEntity.this.getSpellSound(), 4.0F, 1.2F);
                           
            }
        }

        protected void castSpell()
        {
            for (int i = 0; i < FURConfig.Avaton_Ability_Num.get(); ++i)
            {
                BlockPos blockpos = AvatonEntity.this.blockPosition().offset(-2 + AvatonEntity.this.getRandom().nextInt(5), 1, -2 + AvatonEntity.this.getRandom().nextInt(5));
                WetaEntity entityvex = FUREntityRegistry.WETA.create(AvatonEntity.this.level);
                entityvex.moveTo(blockpos, 0.0F, 0.0F);
                entityvex.setOwnerUUID(AvatonEntity.this.getUUID());                             

                if(!AvatonEntity.this.level.isClientSide())
                	AvatonEntity.this.level.addFreshEntity(entityvex);
                
                if(AvatonEntity.this.getTarget() != null)
                	entityvex.setTarget(AvatonEntity.this.getTarget());
                	
            }
        }

        protected int getCastWarmupTime()
        {
            return 20;
        }

        protected int getCastingTime()
        {
            return 30;
        }

        protected int getCastingInterval()
        {
        	return FURConfig.Avaton_Ability_Cooldown.get() * 20;
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound()
        {
            return null;
        }
    }
    
    protected SoundEvent getAmbientSound()
    {
        return FURSoundRegistry.AVATON_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return FURSoundRegistry.BANSHEE_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return FURSoundRegistry.AVATON_DEATH;
    }
    
    protected SoundEvent getSpellSound()
    {
        return FURSoundRegistry.AVATON_SPELL;
    }
}
