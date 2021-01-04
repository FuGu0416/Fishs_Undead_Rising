package com.Fishmod.mod_LavaCow.entities;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityUnburied;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.init.ModEnchantments;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityUndertaker extends EntityMob{
	
	private int attackTimer;
	protected int spellTicks;
	
	public EntityUndertaker(World worldIn) {
		super(worldIn);
		this.setSize(1.8F, 2.0F);
		this.experienceValue = 12;
	}
	
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new AICastingApell());
        this.tasks.addTask(2, new EntityUndertaker.AIUseSpell());
        this.tasks.addTask(3, new EntityAIAttackMelee(this, 1.25D, false));  
        if(!Modconfig.SunScreen_Mode)this.tasks.addTask(4, new EntityAIFleeSun(this, 1.0D));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI()
    {
    	this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
    	this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
    	this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, false));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<EntityVillager>(this, EntityVillager.class, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<EntityIronGolem>(this, EntityIronGolem.class, true));
    }
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Wendigo_Health);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.21D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Wendigo_Attack);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(3.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    }
    
    @Override
	public boolean getCanSpawnHere() {
    	return SpawnUtil.isAllowedDimension(this.dimension) && super.getCanSpawnHere();
	}
    
    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    @Override
    public int getMaxSpawnedInChunk() {
       return 1;
    }
    
    public boolean isSpellcasting()
    {
    	return this.spellTicks > 0;
    }
    
    public int getSpellTicks()
    {
        return this.spellTicks;
    }
    
    protected void updateAITasks()
    {
        super.updateAITasks();
    }
    
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        
        if (this.attackTimer > 0) {
            --this.attackTimer;
         }
        
        if (this.spellTicks > 0) {
            --this.spellTicks;
        }
        
  	   	if (!Modconfig.SunScreen_Mode && this.world.isDaytime() && !this.world.isRemote)
  	   	{
  	   		float f = this.getBrightness();
  	   		if (f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.world.canSeeSky(new BlockPos(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ)))this.setFire(40);
  	   	}   
  	   	
        if (this.getAttackTarget() != null && this.getDistanceSq(this.getAttackTarget()) < 4D && this.getAttackTimer() == 5 && this.deathTime <= 0) {
                 
        	this.getAttackTarget().attackEntityFrom(DamageSource.causeMobDamage(this), (float) this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
            
            float f = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();
            if (this.getHeldItemMainhand().isEmpty() && this.isBurning() && this.rand.nextFloat() < f * 0.3F)
            {
            	this.getAttackTarget().setFire(2 * (int)f);
            }           
        }
    }
	
	   public boolean attackEntityAsMob(Entity entityIn)
	    {
	        this.attackTimer = 15;
	        this.world.setEntityState(this, (byte)4);
	        return true;
	    }
	
    /**
     * Gives armor or weapon for entity based on given DifficultyInstance
     */
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(FishItems.UNDERTAKER_SHOVEL));
        this.getHeldItemMainhand().attemptDamageItem(this.rand.nextInt(this.getHeldItemMainhand().getMaxDamage()), this.rand, null);
    }
    
    public float getEyeHeight()
    {
        return 1.5F;
    }
    
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.spellTicks = compound.getInteger("SpellTicks");
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("SpellTicks", this.spellTicks);
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        this.setEquipmentBasedOnDifficulty(difficulty);
        this.setEnchantmentBasedOnDifficulty(difficulty);
        
        return livingdata;
    }
    
    public int getAttackTimer() {
        return this.attackTimer;
     }
    
    /**
     * Handler for {@link World#setEntityState}
     */
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
    	if (id == 4) 
    	{
            this.attackTimer = 15;
        }
    	else if (id == 10) {
    		
        	this.spellTicks = 30;
        }
        else
        {
            super.handleStatusUpdate(id);
        }
    }
    
    public class AICastingApell extends EntityAIBase
    {
        public AICastingApell()
        {
            this.setMutexBits(3);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
            return EntityUndertaker.this.getSpellTicks() > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting()
        {
            super.startExecuting();
            EntityUndertaker.this.navigator.clearPath();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask()
        {
            super.resetTask();
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask()
        {
            if (EntityUndertaker.this.getAttackTarget() != null)
            {
                EntityUndertaker.this.getLookHelper().setLookPositionWithEntity(EntityUndertaker.this.getAttackTarget(), (float)EntityUndertaker.this.getHorizontalFaceSpeed(), (float)EntityUndertaker.this.getVerticalFaceSpeed());
            }
        }
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
            if (EntityUndertaker.this.getAttackTarget() == null)
            {
                return false;
            }
            else if (EntityUndertaker.this.isSpellcasting())
            {
                return false;
            }
            else
            {
                int i = EntityUndertaker.this.world.getEntitiesWithinAABB(EntityUnburied.class, EntityUndertaker.this.getEntityBoundingBox().grow(16.0D)).size();
            	return EntityUndertaker.this.ticksExisted >= this.spellCooldown && i < Modconfig.Undertaker_Ability_Max;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting()
        {
            return EntityUndertaker.this.getAttackTarget() != null && this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting()
        {
            this.spellWarmup = this.getCastWarmupTime();
            EntityUndertaker.this.spellTicks = this.getCastingTime();
            this.spellCooldown = EntityUndertaker.this.ticksExisted + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();
            EntityUndertaker.this.world.setEntityState(EntityUndertaker.this, (byte)10);
            if (soundevent != null)
            {
                EntityUndertaker.this.playSound(soundevent, 1.0F, 1.0F);
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask()
        {
            --this.spellWarmup;

            if (this.spellWarmup == 0)
            {
                this.castSpell();
                EntityUndertaker.this.playSound(EntityUndertaker.this.getSpellSound(), 1.0F, 1.0F);
            }
        }

        protected void castSpell()
        {
            for (int i = 0; i < Modconfig.Undertaker_Ability_Num; ++i)
            {
                BlockPos blockpos = (new BlockPos(EntityUndertaker.this)).add(-6 + EntityUndertaker.this.rand.nextInt(12), 0, -6 + EntityUndertaker.this.rand.nextInt(12));
                EntityUnburied entityvex = new EntityUnburied(EntityUndertaker.this.world);
                entityvex.moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
                entityvex.onInitialSpawn(EntityUndertaker.this.world.getDifficultyForLocation(blockpos), (IEntityLivingData)null);
                entityvex.setOwnerId(EntityUndertaker.this.getUniqueID());
                
                if(!EntityUndertaker.this.world.isRemote)
                	EntityUndertaker.this.world.spawnEntity(entityvex);
                
                if(EntityUndertaker.this.getAttackingEntity() != null)
                	entityvex.setAttackTarget(EntityUndertaker.this.getAttackingEntity());
            }
        }

        protected int getCastWarmupTime()
        {
            return 30;
        }

        protected int getCastingTime()
        {
            return 30;
        }

        protected int getCastingInterval()
        {
            return Modconfig.Undertaker_Ability_Cooldown * 20;
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound()
        {
            return SoundEvents.EVOCATION_ILLAGER_PREPARE_SUMMON;
        }
    }
    
    @Override
    protected SoundEvent getAmbientSound()
    {
        return FishItems.ENTITY_UNDERTAKER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return FishItems.ENTITY_UNDERTAKER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return FishItems.ENTITY_UNDERTAKER_DEATH;
    }
    
    protected SoundEvent getSpellSound()
    {
        return SoundEvents.EVOCATION_ILLAGER_CAST_SPELL;
    }

    protected SoundEvent getStepSound()
    {
        return SoundEvents.ENTITY_ZOMBIE_STEP;
    }

    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource cause) {
       super.onDeath(cause);
       
       int i = net.minecraftforge.common.ForgeHooks.getLootingLevel(this, cause.getTrueSource(), cause);
       if(this.canDropLoot()) {
    	   LootTableHandler.dropRareLoot(this, FishItems.UNDYINGHEART, Modconfig.UndeadSwine_DropHeart, ModEnchantments.LIFESTEAL, 3, i);
    	   LootTableHandler.dropRareLoot(this, this.getHeldItemMainhand(), 4, i);
       }
    }
    
    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableList.ENTITIES_ZOMBIE;
    }
    
    /**
     * Entity won't drop items or experience points if this returns false
     */
    @Override
    protected boolean canDropLoot() {
       return !this.isBurning() || this.recentlyHit != 0;
    }
}
