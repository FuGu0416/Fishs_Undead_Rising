package com.Fishmod.mod_LavaCow.entities.aquatic;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;
import com.google.common.base.Predicate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityZombiePiranha extends EntityAquaMob{
	
	private boolean isAggressive = false;
	private boolean isAmmo = false;
	
    public EntityZombiePiranha(World worldIn)
    {
        super(worldIn);   
        this.setSize(1.0F, 0.8F);
        this.BeachedLife = -1;
    }
    
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(1, new AIPiranhaLeapAtTarget(this, 0.6F));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D, 80));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        
        this.applyEntityAI();
     }
    
    protected void applyEntityAI() {
    	this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
    	if(Modconfig.Piranha_AnimalAttack)
    		this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<>(this, EntityAgeable.class, 0, true, true, new Predicate<Entity>()
            {
                public boolean apply(@Nullable Entity p_apply_1_)
                {
                    return !(p_apply_1_ instanceof EntityTameable) && ((EntityAgeable)p_apply_1_).getHealth() < ((EntityAgeable)p_apply_1_).getMaxHealth();
                }
            }));
    	this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityMob.class, 0, true, true, new Predicate<Entity>()
        {
            public boolean apply(@Nullable Entity p_apply_1_)
            {
                return !(p_apply_1_ instanceof EntityZombiePiranha || p_apply_1_ instanceof EntityCreeper) && ((EntityMob)p_apply_1_).getHealth() < ((EntityMob)p_apply_1_).getMaxHealth();
            }
        }));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.ZombiePiranha_Health);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.ZombiePiranha_Attack);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)1.2F);
    }
    
    public boolean getCanSpawnHere() {
    	return SpawnUtil.isAllowedDimension(this.dimension) && super.getCanSpawnHere();
	}
    
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate() {
    	super.onLivingUpdate();
    }
    
    @Override
	public boolean attackEntityAsMob(Entity entity) {
    	this.playSound(FishItems.ENTITY_ZOMBIEPIRANHA_ATTACK, 1.0F, 1.0F);
    	return super.attackEntityAsMob(entity);
	}
    
    static class AIPiranhaLeapAtTarget extends EntityAIBase {
    	   /** The entity that is leaping. */
    	   private final EntityLiving leaper;
    	   /** The entity that the leaper is leaping towards. */
    	   private EntityLivingBase leapTarget;
    	   /** The entity's motionY after leaping. */
    	   private final float leapMotionY;

    	   public AIPiranhaLeapAtTarget(EntityLiving leapingEntity, float leapMotionYIn) {
    	      this.leaper = leapingEntity;
    	      this.leapMotionY = leapMotionYIn;
    	      this.setMutexBits(5);
    	   }
    	   
    	   /**
    	    * Returns whether the EntityAIBase should begin execution.
    	    */
    	   public boolean shouldExecute() {
    	      this.leapTarget = this.leaper.getAttackTarget();
    	      if (this.leapTarget == null) {
    	         return false;
    	      } else {
    	         double d0 = this.leaper.getDistanceSq(this.leapTarget);
    	         if (!(d0 < 4.0D) && !(d0 > 24.0D) && this.leaper.isInWater()) {
    	               return true;
    	         } else {
    	            return false;
    	         }
    	      }
    	   }
    	   
    	   /**
    	    * Returns whether an in-progress EntityAIBase should continue executing
    	    */
    	   public boolean shouldContinueExecuting() {
    	      return !this.leaper.onGround && !this.leaper.isInWater();
    	   }

    	   /**
    	    * Execute a one shot task or start executing a continuous task
    	    */
    	   public void startExecuting() {
    	      double d0 = this.leapTarget.posX - this.leaper.posX;
    	      double d1 = this.leapTarget.posZ - this.leaper.posZ;
    	      float f = MathHelper.sqrt(d0 * d0 + d1 * d1);
    	      if ((double)f >= 1.0E-4D) {
    	         this.leaper.motionX += d0 / (double)f * 0.5D * (double)0.8F + this.leaper.motionX * (double)0.2F;
    	         this.leaper.motionZ += d1 / (double)f * 0.5D * (double)0.8F + this.leaper.motionZ * (double)0.2F;
    	      }
    	      this.leaper.motionY = (double)this.leapMotionY;
    	   }  	   
    }
    
    protected void updateAITasks()
    {
        if(this.getAttackTarget() != null)
    	{       		
        	isAggressive = true;
    		this.world.setEntityState(this, (byte)11);
    	}
	    else 
    	{
    		isAggressive = false;
    		this.world.setEntityState(this, (byte)34);
    	}
    }
    
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
        if (id == 11)
        {
            this.isAggressive = true;
        }
        else if (id == 34)
        {
            this.isAggressive = false;
        }
        else
        {
            super.handleStatusUpdate(id);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public boolean isLeaping()
    {
    	return this.isAggressive;
    }
    
    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount) {
       return super.attackEntityFrom(source, amount);
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
    	return super.onInitialSpawn(difficulty, livingdata);
    }
    
    protected SoundEvent getAmbientSound() {
        return FishItems.ENTITY_ZOMBIEPIRANHA_AMBIENT;
     }

     protected SoundEvent getDeathSound() {
        return FishItems.ENTITY_ZOMBIEPIRANHA_DEATH;
     }

     protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_GUARDIAN_HURT;
     }
     
     /**
      * Get this Entity's EnumCreatureAttribute
      */
     public EnumCreatureAttribute getCreatureAttribute()
     {
         return EnumCreatureAttribute.UNDEAD;
     }
	
	public float getEyeHeight() {
		return this.height * 0.5F;
	}
	
	public void setIsAmmo(boolean t)
	{
		this.isAmmo = t;
	}
    
    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableHandler.ZOMBIEPIRANHA;
    }
    
    /**
     * Entity won't drop items or experience points if this returns false
     */
    @Override
    protected boolean canDropLoot() {
       return !this.isAmmo;
    }
}
