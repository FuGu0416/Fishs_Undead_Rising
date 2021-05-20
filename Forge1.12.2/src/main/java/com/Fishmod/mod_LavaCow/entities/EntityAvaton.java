package com.Fishmod.mod_LavaCow.entities;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityWeta;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAvaton extends EntityBanshee {
	
	public EntityAvaton(World worldIn)
    {
        super(worldIn);
        this.setSize(1.25F, 1.5F);
        this.setPathPriority(PathNodeType.WATER, 8.0F);
    }
	
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new AICastingApell());
        this.tasks.addTask(3, new EntityAvaton.AIUseSpell());
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, false));
        if(!Modconfig.SunScreen_Mode)this.tasks.addTask(5, new EntityAIFleeSun(this, 1.0D));
        this.tasks.addTask(7, new EntityBanshee.AIMoveRandom());
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI()
    {
    	this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
    	this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
    	this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
    }
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Avaton_Health);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.175D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Avaton_Attack);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0.0D);
    }
    
    protected boolean isBanshee() {
    	return false;
    }
    
    protected String ParticleType() {
    	return "locust_swarm";
    }
	    
    public class AIUseSpell extends EntityAIBase
    {
        protected int spellWarmup;
        protected int spellCooldown;

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
        	if (EntityAvaton.this.isSpellcasting())
            {
                return false;
            }
            else
            {
                int i = EntityAvaton.this.world.getEntitiesWithinAABB(EntityWeta.class, EntityAvaton.this.getEntityBoundingBox().grow(16.0D)).size();
                boolean farmlandnearby = false;
                for(int x = -2; x <= 2; x++)
                	for(int y = -3; y <= 3; y++)
                		for(int z = -2; z <= 2; z++)
                		{
                			if (EntityAvaton.this.world.getBlockState(new BlockPos(EntityAvaton.this.posX + x, EntityAvaton.this.posY + y, EntityAvaton.this.posZ + z)).getBlock() == Blocks.FARMLAND)
                				farmlandnearby = true;
                		}              
                
            	return EntityAvaton.this.ticksExisted >= this.spellCooldown && ((EntityAvaton.this.getAttackTarget() != null && Math.abs(EntityAvaton.this.posY - EntityAvaton.this.getAttackTarget().posY) < 4.0D) || farmlandnearby) && i < Modconfig.Avaton_Ability_Max;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting()
        {
            return this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting()
        {
            this.spellWarmup = this.getCastWarmupTime();
            EntityAvaton.this.spellTicks = this.getCastingTime();
            EntityAvaton.this.world.setEntityState(EntityAvaton.this, (byte)10);
            this.spellCooldown = EntityAvaton.this.ticksExisted + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();

            if (soundevent != null)
            {
                EntityAvaton.this.playSound(soundevent, 1.0F, 1.0F);
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask()
        {
            --this.spellWarmup;

            if (this.spellWarmup == 5)
            {
                this.castSpell();
                EntityAvaton.this.playSound(EntityAvaton.this.getSpellSound(), 4.0F, 1.2F);
                           
            }
        }

        protected void castSpell()
        {
            for (int i = 0; i < Modconfig.Avaton_Ability_Num; ++i)
            {
                BlockPos blockpos = (new BlockPos(EntityAvaton.this)).add(-2 + EntityAvaton.this.rand.nextInt(5), 1, -2 + EntityAvaton.this.rand.nextInt(5));
                EntityWeta entityvex = new EntityWeta(EntityAvaton.this.world);
                entityvex.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
                entityvex.setHealth(entityvex.getMaxHealth());
                entityvex.moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
                entityvex.onInitialSpawn(EntityAvaton.this.world.getDifficultyForLocation(blockpos), (IEntityLivingData)null);
                entityvex.setOwnerId(EntityAvaton.this.getUniqueID());                             

                if(!EntityAvaton.this.world.isRemote)
                	EntityAvaton.this.world.spawnEntity(entityvex);
                
                if(EntityAvaton.this.getAttackTarget() != null)
                	entityvex.setAttackTarget(EntityAvaton.this.getAttackTarget());
                	
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
        	return Modconfig.Avaton_Ability_Cooldown * 20;
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound()
        {
            return null;
        }
    }
    
    protected SoundEvent getAmbientSound()
    {
        return FishItems.ENTITY_AVATON_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return FishItems.ENTITY_BANSHEE_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return FishItems.ENTITY_AVATON_DEATH;
    }
    
    protected SoundEvent getSpellSound()
    {
        return FishItems.ENTITY_AVATON_SPELL;
    }
    
    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableHandler.AVATON;
    }
}
