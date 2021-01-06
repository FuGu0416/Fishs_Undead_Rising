package com.Fishmod.mod_LavaCow.entities;

import java.util.List;
import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.init.ModEnchantments;
import com.Fishmod.mod_LavaCow.init.Modblocks;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIEatGrass;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityUndeadSwine extends EntityMob{
	
	/** Used to control movement as well as wool regrowth. Set to 40 on handleHealthUpdate and counts down with each tick. */
	private int sheepTimer;
	private int attackTimer;
	private EntityAIEatGrass entityAIEatGrass;
	private AIChargeAttack entityAICharge;
	private final List<Block> DIGOUT_SHROOM = Lists.newArrayList(
				Modblocks.GLOWSHROOM, 
				Modblocks.CORDY_SHROOM, 
				Modblocks.VEIL_SHROOM, 
				Blocks.BROWN_MUSHROOM, 
				Blocks.RED_MUSHROOM
			);
	
    public EntityUndeadSwine(World worldIn)
    {
        super(worldIn);       
        this.setSize(1.6f, 1.8f);
        this.experienceValue = 20;
    }
    
    @Override
    protected void initEntityAI()
    {
    	this.entityAIEatGrass = new EntityAIEatGrass(this);
    	this.entityAICharge = new EntityUndeadSwine.AIChargeAttack(this);
    	this.tasks.addTask(0, new EntityAISwimming(this));
    	this.tasks.addTask(2, this.entityAICharge);
        this.tasks.addTask(4, this.entityAIEatGrass);
        if(!Modconfig.SunScreen_Mode)this.tasks.addTask(4, new EntityAIFleeSun(this, 1.0D));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI()
    {
    	this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
    	this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
    }
    
    protected void updateAITasks() {
        this.sheepTimer = this.entityAIEatGrass.getEatingGrassTimer();
        super.updateAITasks();
	}
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.UndeadSwine_Health);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.UndeadSwine_Attack);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    }
    
    @Override
	public boolean getCanSpawnHere() {
		return SpawnUtil.isAllowedDimension(this.dimension) && super.getCanSpawnHere();
	}
    
    public void applyEntityCollision(Entity entityIn) {
    	super.applyEntityCollision(entityIn);
		if(this.entityAICharge != null && this.entityAICharge.isCharging())
		{
			this.attackEntityAsMob(entityIn);
			entityIn.addVelocity(this.getLookVec().x, this.getLookVec().y, this.getLookVec().z);
		}
    }
    
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate() {
        if (this.world.isRemote) {
            this.sheepTimer = Math.max(0, this.sheepTimer - 1);
         }
        
        if (this.attackTimer > 0) {
            --this.attackTimer;
         }
                  
  	   	if (!Modconfig.SunScreen_Mode && this.world.isDaytime() && !this.world.isRemote)
  	   	{
  	   		float f = this.getBrightness();
  	   		if (f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.world.canSeeSky(new BlockPos(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ)))this.setFire(40);
  	   	}

         super.onLivingUpdate();
    }
    
    @Override
	public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = super.attackEntityAsMob(entityIn);     
        this.attackTimer = 10;
        this.world.setEntityState(this, (byte)4);
        if (flag)
        {
            float f = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();
            if (this.getHeldItemMainhand().isEmpty() && this.isBurning() && this.rand.nextFloat() < f * 0.3F)
            {
                entityIn.setFire(2 * (int)f);
            }
        }

        return flag;
	}
    
    @SideOnly(Side.CLIENT)
    public boolean isDigging()
    {
    	return this.sheepTimer > 0;
    }
    
    @SideOnly(Side.CLIENT)
    public int getAttackTimer() {
       return this.attackTimer;
    }
    
    /**
     * Handler for {@link World#setEntityState}
     */
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
       
    	if (id == 4) 
    	{
            this.attackTimer = 10;
        }
    	else if (id == 10) {
          this.sheepTimer = 40;
       } else {
          super.handleStatusUpdate(id);
       }
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        livingdata = super.onInitialSpawn(difficulty, livingdata);

        if (this.world.rand.nextInt(100) == 0)
        {
            EntitySkeleton entityRider = new EntitySkeleton(this.world);
            entityRider.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
            entityRider.onInitialSpawn(difficulty, (IEntityLivingData)null);
            this.world.spawnEntity(entityRider);
            entityRider.startRiding(this);
        }
        else if (this.world.rand.nextInt(100) == 1)
        {
            EntityZombie entityRider = new EntityZombie(this.world);
            entityRider.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
            entityRider.onInitialSpawn(difficulty, (IEntityLivingData)null);
            this.world.spawnEntity(entityRider);
            entityRider.startRiding(this, true);
        }

        return livingdata;
    }
    
    static class AIChargeAttack extends EntityAIBase {
        private final EntityUndeadSwine blaze;
        private int attackStep;
        private int attackTime;

        public AIChargeAttack(EntityUndeadSwine entitySalamander) {
           this.blaze = entitySalamander;
           this.setMutexBits(3);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
        	EntityLivingBase entitylivingbase = this.blaze.getAttackTarget();
           return entitylivingbase != null && entitylivingbase.isEntityAlive() && entitylivingbase.posY <= this.blaze.posY;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
           this.attackStep = 0;
           this.blaze.playSound(FishItems.ENTITY_UNDEADSWINE_CHARGE, 4.0F, 1.0F);
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask() {
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask() {
           --this.attackTime;
           EntityLivingBase entitylivingbase = this.blaze.getAttackTarget();

           if (entitylivingbase != null) {
               double d0 = this.blaze.getDistance(entitylivingbase);

               if (d0 < 2.2D) {
                  if (this.attackTime <= 0) {
                     this.attackTime = 30;
                     this.blaze.attackEntityAsMob(entitylivingbase);
                  }

               } else if (d0 < this.getFollowDistance() * this.getFollowDistance()) {
                  double v = 4.0D;
            	  double d1 = v * (entitylivingbase.posX - this.blaze.posX)/d0;
                  double d2 = v * (entitylivingbase.posY - this.blaze.posY)/d0;
                  double d3 = v * (entitylivingbase.posZ - this.blaze.posZ)/d0;
                  if (this.attackTime <= 0) {
                     ++this.attackStep;
                     if (this.attackStep > 20) {
                        this.blaze.moveHelper.setMoveTo(this.blaze.posX + d1, this.blaze.posY + d2, this.blaze.posZ + d3, 2.0D);
                     } else if(this.attackStep > 100) {
                    	 this.attackTime = 60;
                    	 this.attackStep = 0;
                     }
                  }

                  this.blaze.getLookHelper().setLookPositionWithEntity(entitylivingbase, 100.0F, 100.0F);
               } else {
                  this.blaze.getNavigator().clearPath();
                  this.blaze.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
               }
           }

           super.updateTask();
        }
        
        private double getFollowDistance() {
            IAttributeInstance iattributeinstance = this.blaze.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
            return iattributeinstance == null ? 16.0D : iattributeinstance.getBaseValue();
         }
         
        public boolean isCharging() {
        	return this.attackStep > 20;
        }
         
    }
    
    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource cause) {
       super.onDeath(cause);

       int i = net.minecraftforge.common.ForgeHooks.getLootingLevel(this, cause.getTrueSource(), cause);
       if(this.canDropLoot())
    	   LootTableHandler.dropRareLoot(this, FishItems.UNDYINGHEART, Modconfig.UndeadSwine_DropHeart, ModEnchantments.LIFESTEAL, 3, i);
    }
    
    /**
     * This function applies the benefits of growing back wool and faster growing up to the acting entity. (This function
     * is used in the AIEatGrass)
     */
    public void eatGrassBonus() {
    	this.dropItem(new ItemStack(DIGOUT_SHROOM.get(this.rand.nextInt(DIGOUT_SHROOM.size()))).getItem(), 1); 
    }
    
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    public float getEyeHeight()
    {
        return this.height * 0.6F;
    }
    
    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_PIG_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return FishItems.ENTITY_UNDEADSWINE_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return FishItems.ENTITY_UNDEADSWINE_DEATH;
    }

    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15F, 1.0F);
    }
    
    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableHandler.UNDEADSWINE;
    }
    
    /**
     * Entity won't drop items or experience points if this returns false
     */
    @Override
    protected boolean canDropLoot() {
       return !this.isBurning() || this.recentlyHit != 0;
    }
}
