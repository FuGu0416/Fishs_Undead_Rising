package com.Fishmod.mod_LavaCow.entities;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.ai.EntityFishAIAttackRange;
import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntitySludgeJet;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityLilSludge;
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
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySludgeLord extends EntityMob{
	private boolean isAggressive = false;
	protected int spellTicks;
	
	public EntitySludgeLord(World worldIn)
    {
        super(worldIn);
        this.setSize(2.5F, 3.0F);
        this.experienceValue = 20;
    }
	
    protected void initEntityAI()
    {
        this.tasks.addTask(1, new AICastingApell());
    	this.tasks.addTask(2, new EntityFishAIAttackRange(this, EntitySludgeJet.class, SoundEvents.ENTITY_SLIME_SQUISH, 1, 2, 5.0D, 8.0D, 1.2D, 0.6D, 1.2D));
    	this.tasks.addTask(3, new EntityAIAttackMelee(this, 1.0D, false));
    	this.tasks.addTask(4, new EntitySludgeLord.AIUseSpell());
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
    	//this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityCow.class, true));
    }
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.SludgeLord_Health);
        //this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.19D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.SludgeLord_Attack);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(8.0D);
    }
    
    @Override
	public boolean getCanSpawnHere() {
		if(this.dimension == DimensionType.OVERWORLD.getId())
			return super.getCanSpawnHere();
		else return false;
	}
    
    public boolean isSpellcasting()
    {
    	return this.spellTicks > 0;
    }
    
    protected int getSpellTicks()
    {
        return this.spellTicks;
    }
	
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void onLivingUpdate() {
  	   	if (!Modconfig.SunScreen_Mode && this.world.isDaytime() && !this.world.isRemote)
  	   	{
  	   		float f = this.getBrightness();
  	   		if (f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.world.canSeeSky(new BlockPos(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ)))this.setFire(40);
  	   	}

         super.onLivingUpdate();
    }
    
    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount) {
    	if(source.isFireDamage())
    		return super.attackEntityFrom(source, 2.0F * amount);
    	return super.attackEntityFrom(source, amount);
    }

    public boolean attackEntityAsMob(Entity entityIn)
    {
        boolean flag = super.attackEntityAsMob(entityIn);

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
    
    protected void updateAITasks()
    {
        super.updateAITasks();
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
        
        if (this.spellTicks > 0)
        {
            --this.spellTicks;
        }
    }
    
    @SideOnly(Side.CLIENT)
    public boolean isAggressive()
    {
    	return isAggressive;
    }
    
    /**
     * Handler for {@link World#setEntityState}
     */
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

    public float getEyeHeight()
    {
        return 1.8F;
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
            return EntitySludgeLord.this.getSpellTicks() > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting()
        {
            super.startExecuting();
            EntitySludgeLord.this.navigator.clearPath();
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
            if (EntitySludgeLord.this.getAttackTarget() != null)
            {
                EntitySludgeLord.this.getLookHelper().setLookPositionWithEntity(EntitySludgeLord.this.getAttackTarget(), (float)EntitySludgeLord.this.getHorizontalFaceSpeed(), (float)EntitySludgeLord.this.getVerticalFaceSpeed());
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
            if (EntitySludgeLord.this.getAttackTarget() == null)
            {
                return false;
            }
            else if (EntitySludgeLord.this.isSpellcasting())
            {
                return false;
            }
            else
            {
                int i = EntitySludgeLord.this.world.getEntitiesWithinAABB(EntityLilSludge.class, EntitySludgeLord.this.getEntityBoundingBox().grow(16.0D)).size();
            	return EntitySludgeLord.this.ticksExisted >= this.spellCooldown && i < Modconfig.SludgeLord_Ability_Max;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting()
        {
            return EntitySludgeLord.this.getAttackTarget() != null && this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting()
        {
            this.spellWarmup = this.getCastWarmupTime();
            EntitySludgeLord.this.spellTicks = this.getCastingTime();
            this.spellCooldown = EntitySludgeLord.this.ticksExisted + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();

            if (soundevent != null)
            {
                EntitySludgeLord.this.playSound(soundevent, 1.0F, 1.0F);
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
                EntitySludgeLord.this.playSound(EntitySludgeLord.this.getSpellSound(), 1.0F, 1.0F);
            }
        }

        protected void castSpell()
        {
            for (int i = 0; i < Modconfig.SludgeLord_Ability_Num; ++i)
            {
                BlockPos blockpos = (new BlockPos(EntitySludgeLord.this)).add(-2 + EntitySludgeLord.this.rand.nextInt(5), 1, -2 + EntitySludgeLord.this.rand.nextInt(5));
                EntityLilSludge entityvex = new EntityLilSludge(EntitySludgeLord.this.world);
                entityvex.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
                entityvex.setHealth(entityvex.getMaxHealth());
                entityvex.moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
                entityvex.onInitialSpawn(EntitySludgeLord.this.world.getDifficultyForLocation(blockpos), (IEntityLivingData)null);
                entityvex.setOwnerId(EntitySludgeLord.this.getUniqueID());
                //entityvex.setTamed(true);
                entityvex.setLimitedLife(20 * (30 + EntitySludgeLord.this.rand.nextInt(90)));
                
                if(!EntitySludgeLord.this.world.isRemote)
                	EntitySludgeLord.this.world.spawnEntity(entityvex);
                
                //ItemFishCustomWeapon.LavaBurst(EntitySludgeLord.this.world, entityvex.posX, entityvex.posY, entityvex.posZ, 1.0D, EnumParticleTypes.WATER_BUBBLE);
                //if(EntitySludgeLord.this.getAttackingEntity() != null)
                	//entityvex.setAttackTarget(EntitySludgeLord.this.getAttackingEntity());
            }
        }

        protected int getCastWarmupTime()
        {
            return 20;
        }

        protected int getCastingTime()
        {
            return 100;
        }

        protected int getCastingInterval()
        {
            return Modconfig.SludgeLord_Ability_Cooldown * 20;
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound()
        {
            return SoundEvents.EVOCATION_ILLAGER_PREPARE_SUMMON;
        }
    }
    
	@Override
	public void playLivingSound() {
		if(this.rand.nextDouble() < 0.25D)
			super.playLivingSound();
	}
    
    @Override
    protected SoundEvent getAmbientSound()
    {
        return FishItems.ENTITY_SLUDGELORD_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return FishItems.ENTITY_SLUDGELORD_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return FishItems.ENTITY_SLUDGELORD_DEATH;
    }
    
    protected SoundEvent getSpellSound()
    {
        return SoundEvents.EVOCATION_ILLAGER_CAST_SPELL;
    }

    protected SoundEvent getStepSound()
    {
        return SoundEvents.ENTITY_IRONGOLEM_STEP;
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
    
    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableHandler.SLUDGELORD;
    }
    
    /**
     * Entity won't drop items or experience points if this returns false
     */
    @Override
    protected boolean canDropLoot() {
       return !this.isBurning() || this.recentlyHit != 0;
    }
}
