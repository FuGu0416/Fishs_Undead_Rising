package com.Fishmod.mod_LavaCow.entities;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBanshee extends EntityMob implements IAggressive{
	
	private static final DataParameter<Byte> CASTING = EntityDataManager.<Byte>createKey(EntityBanshee.class, DataSerializers.BYTE);
	private boolean isAggressive = false;
	private int attackTimer = 0;
	protected int spellTicks;
	
	public EntityBanshee(World worldIn)
    {
        super(worldIn);
        this.moveHelper = new EntityBanshee.AIMoveControl(this);
        this.setSize(0.75F, 2.25F);
    }
	
    /**
     * Tries to move the entity towards the specified location.
     */
    public void move(MoverType type, double x, double y, double z)
    {
        super.move(type, x, y, z);
        this.doBlockCollisions();
    }
	
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new AICastingApell());
        this.tasks.addTask(3, new EntityBanshee.AIUseSpell());
        this.tasks.addTask(4, new EntityBanshee.AIChargeAttack());
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
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<EntityVillager>(this, EntityVillager.class, true));
    }
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Banshee_Health);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Banshee_Attack);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0.0D);
    }
	
	public boolean getCanSpawnHere() {
		return SpawnUtil.isAllowedDimension(this.dimension) && super.getCanSpawnHere();
	}
	
    protected void entityInit() {
    	super.entityInit();
    	this.setNoGravity(true);
    	this.dataManager.register(CASTING, Byte.valueOf((byte)0));
    }
    
    public boolean isSpellcasting()
    {
    	return (((Byte)this.dataManager.get(CASTING)).byteValue() & 1) != 0;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean isSpellcastingC()
    {
    	return (((Byte)this.dataManager.get(CASTING)).byteValue() & 1) != 0;
    }
    
    public int getSpellTicks()
    {
        return this.spellTicks;
    }
    
    protected boolean isBanshee() {
    	return true;
    }
	
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void onUpdate()
    {
        super.onUpdate();
        
        if(this.ticksExisted % 2 == 0 && this.getEntityWorld().isRemote)
        	mod_LavaCow.PROXY.spawnCustomParticle("spore", world, this.posX + (double)(new Random().nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + (double)(new Random().nextFloat() * this.height), this.posZ + (double)(new Random().nextFloat() * this.width * 2.0F) - (double)this.width, 0.0D, 0.0D, 0.0D, 0.20F, 0.21F, 0.23F);
        
        if(this.isBanshee() && this.getSpellTicks() > 8 && this.getSpellTicks() < 13) {
        	this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX, this.posY + this.height, this.posZ, 0.0D, 1.0D, 0.0D);
        }
    }
    
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void onLivingUpdate()
    {
        if (this.spellTicks > 0)
        {
            --this.spellTicks;
        }
    	
    	if (!Modconfig.SunScreen_Mode && this.world.isDaytime() && !this.world.isRemote)
    	{
    		float f = this.getBrightness();
    		if (f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.world.canSeeSky(new BlockPos(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ)))this.setFire(8);
    	}
    	
    	super.onLivingUpdate();
    }
    
    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
    	return super.attackEntityFrom(source, amount);
    }

    public boolean attackEntityAsMob(Entity entityIn)
    {
        boolean flag = super.attackEntityAsMob(entityIn);

        if (flag)
        {
            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        return livingdata;
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
    }
    
    public boolean isAggressive()
    {
    	return isAggressive;
    }
    
    @Override
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
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
        if (id == 10) {
        	this.spellTicks = 30;
        }
    	else if (id == 4) 
    	{
            this.attackTimer = 5;
        }
    	else if (id == 11)
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
    
    public void setIsCasting(boolean isHanging)
    {
        byte b0 = ((Byte)this.dataManager.get(CASTING)).byteValue();

        if (isHanging)
        {
            this.dataManager.set(CASTING, Byte.valueOf((byte)(b0 | 1)));
        }
        else
        {
            this.dataManager.set(CASTING, Byte.valueOf((byte)(b0 & -2)));
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
            return EntityBanshee.this.getSpellTicks() > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting()
        {
            super.startExecuting();
            EntityBanshee.this.setIsCasting(true);
            EntityBanshee.this.navigator.clearPath();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask()
        {
            super.resetTask();
            EntityBanshee.this.setIsCasting(false);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask()
        {
            if (EntityBanshee.this.getAttackTarget() != null)
            {
                EntityBanshee.this.getLookHelper().setLookPositionWithEntity(EntityBanshee.this.getAttackTarget(), (float)EntityBanshee.this.getHorizontalFaceSpeed(), (float)EntityBanshee.this.getVerticalFaceSpeed());
            }
            
            if(EntityBanshee.this.getSpellTicks() > 8 && EntityBanshee.this.getSpellTicks() < 13) {
            	EntityBanshee.this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, EntityBanshee.this.posX, EntityBanshee.this.posY + EntityBanshee.this.height, EntityBanshee.this.posZ, 0.0D, 1.0D, 0.0D);
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
            if (EntityBanshee.this.getAttackTarget() == null)
            {
                return false;
            }
            else if (EntityBanshee.this.isSpellcasting())
            {
                return false;
            }
            else
            {
            	return EntityBanshee.this.ticksExisted >= this.spellCooldown && EntityBanshee.this.getDistance(EntityBanshee.this.getAttackTarget()) < 3.0F;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting()
        {
            return EntityBanshee.this.getAttackTarget() != null && this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting()
        {
            this.spellWarmup = this.getCastWarmupTime();
            EntityBanshee.this.spellTicks = this.getCastingTime();
            EntityBanshee.this.world.setEntityState(EntityBanshee.this, (byte)10);
            this.spellCooldown = EntityBanshee.this.ticksExisted + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();

            if (soundevent != null)
            {
                EntityBanshee.this.playSound(soundevent, 1.0F, 1.0F);
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
                EntityBanshee.this.playSound(EntityBanshee.this.getSpellSound(), 4.0F, 1.2F);
                           
            }
        }

        protected void castSpell()
        {
        	List<Entity> list = EntityBanshee.this.world.getEntitiesWithinAABBExcludingEntity(EntityBanshee.this, EntityBanshee.this.getEntityBoundingBox().grow(Modconfig.Banshee_Ability_Radius, Modconfig.Banshee_Ability_Radius, Modconfig.Banshee_Ability_Radius));
        	EntityBanshee.this.world.setEntityState(EntityBanshee.this, (byte)11);
        	
        	for (Entity entity1 : list)
        	{
        		if (entity1 instanceof EntityLivingBase)
        		{
        			float local_difficulty = EntityBanshee.this.world.getDifficultyForLocation(new BlockPos(EntityBanshee.this)).getAdditionalDifficulty();
                        
        			if (((EntityLivingBase)entity1).getCreatureAttribute() != EnumCreatureAttribute.UNDEAD && ((EntityLivingBase)entity1).getActivePotionEffect(MobEffects.WEAKNESS) == null)
        				((EntityLivingBase)entity1).addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 4 * 20 * (int)local_difficulty, 2));
        				((EntityLivingBase)entity1).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 1 * 20 * (int)local_difficulty, 6));
        				((EntityLivingBase)entity1).attackEntityFrom(DamageSource.causeMobDamage(EntityBanshee.this).setMagicDamage().setDamageBypassesArmor(), (float) EntityBanshee.this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * 0.3F);
        		}
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
            return 160;
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound()
        {
            return null;
        }
    }
    
    class AIChargeAttack extends EntityAIBase
    {
        public AIChargeAttack()
        {
            this.setMutexBits(1);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
            if (EntityBanshee.this.getAttackTarget() != null && !EntityBanshee.this.getMoveHelper().isUpdating() && EntityBanshee.this.rand.nextInt(7) == 0)
            {
                return EntityBanshee.this.getDistanceSq(EntityBanshee.this.getAttackTarget()) > 4.0D;
            }
            else
            {
                return false;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting()
        {
            return EntityBanshee.this.getMoveHelper().isUpdating() && EntityBanshee.this.getAttackTarget() != null && EntityBanshee.this.getAttackTarget().isEntityAlive();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting()
        {
            EntityLivingBase entitylivingbase = EntityBanshee.this.getAttackTarget();
            Vec3d vec3d = entitylivingbase.getPositionEyes(1.0F);
            EntityBanshee.this.moveHelper.setMoveTo(vec3d.x, vec3d.y, vec3d.z, 1.0D);
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask()
        {
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask()
        {
            EntityLivingBase entitylivingbase = EntityBanshee.this.getAttackTarget();

            if (entitylivingbase != null) {
                if (EntityBanshee.this.getEntityBoundingBox().intersects(entitylivingbase.getEntityBoundingBox()))
                {
                    EntityBanshee.this.attackEntityAsMob(entitylivingbase);
                }
                else
                {
                    double d0 = EntityBanshee.this.getDistanceSq(entitylivingbase);

                    if (d0 < 9.0D)
                    {
                        Vec3d vec3d = entitylivingbase.getPositionEyes(1.0F);
                        EntityBanshee.this.moveHelper.setMoveTo(vec3d.x, vec3d.y, vec3d.z, 1.0D);
                    }
                }
            }

           super.updateTask();
        }
    }
    
    class AIMoveControl extends EntityMoveHelper
    {
        public AIMoveControl(EntityBanshee Banshee)
        {
            super(Banshee);
        }

        public void onUpdateMoveHelper()
        {
            if (this.action == EntityMoveHelper.Action.MOVE_TO)
            {
                double d0 = this.posX - EntityBanshee.this.posX;
                double d1 = this.posY - EntityBanshee.this.posY;
                double d2 = this.posZ - EntityBanshee.this.posZ;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                d3 = (double)MathHelper.sqrt(d3);

                if (d3 < EntityBanshee.this.getEntityBoundingBox().getAverageEdgeLength())
                {
                    this.action = EntityMoveHelper.Action.WAIT;
                    EntityBanshee.this.motionX *= 0.5D;
                    EntityBanshee.this.motionY *= 0.5D;
                    EntityBanshee.this.motionZ *= 0.5D;
                }
                else
                {
                    EntityBanshee.this.motionX += d0 / d3 * 0.05D * this.speed;
                    EntityBanshee.this.motionY += d1 / d3 * 0.05D * this.speed;
                    EntityBanshee.this.motionZ += d2 / d3 * 0.05D * this.speed;

                    if (EntityBanshee.this.getAttackTarget() == null)
                    {
                        EntityBanshee.this.rotationYaw = -((float)MathHelper.atan2(EntityBanshee.this.motionX, EntityBanshee.this.motionZ)) * (180F / (float)Math.PI);
                        EntityBanshee.this.renderYawOffset = EntityBanshee.this.rotationYaw;
                    }
                    else
                    {
                        double d4 = EntityBanshee.this.getAttackTarget().posX - EntityBanshee.this.posX;
                        double d5 = EntityBanshee.this.getAttackTarget().posZ - EntityBanshee.this.posZ;
                        EntityBanshee.this.rotationYaw = -((float)MathHelper.atan2(d4, d5)) * (180F / (float)Math.PI);
                        EntityBanshee.this.renderYawOffset = EntityBanshee.this.rotationYaw;
                    }
                }
            }
        }
    }
    
    class AIMoveRandom extends EntityAIBase
    {
        public AIMoveRandom()
        {
            this.setMutexBits(1);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
            return !EntityBanshee.this.getMoveHelper().isUpdating() && EntityBanshee.this.rand.nextInt(7) == 0;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting()
        {
            return false;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask()
        {
            BlockPos blockpos = new BlockPos(EntityBanshee.this);
            int y = EntityBanshee.this.rand.nextInt(11) - 5;
            
            if(Modconfig.FlyingHeight_limit != 0)
            	y = Math.min(SpawnUtil.getHeight(EntityBanshee.this).getY() + Modconfig.FlyingHeight_limit, y);
            
            for (int i = 0; i < 3; ++i)
            {
                BlockPos blockpos1 = blockpos.add(EntityBanshee.this.rand.nextInt(15) - 7, y, EntityBanshee.this.rand.nextInt(15) - 7);

                if (EntityBanshee.this.world.isAirBlock(blockpos1))
                {
                    EntityBanshee.this.moveHelper.setMoveTo((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 0.25D);

                    if (EntityBanshee.this.getAttackTarget() == null)
                    {
                        EntityBanshee.this.getLookHelper().setLookPosition((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 180.0F, 20.0F);
                    }

                    break;
                }
            }
        }
    }

    public float getEyeHeight()
    {
        return this.height * 0.8F;
    }
    
    protected SoundEvent getAmbientSound()
    {
        return FishItems.ENTITY_BANSHEE_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return FishItems.ENTITY_BANSHEE_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return FishItems.ENTITY_BANSHEE_DEATH;
    }
    
    protected SoundEvent getSpellSound()
    {
        return FishItems.ENTITY_BANSHEE_ATTACK;
    }
    
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    public void fall(float distance, float damageMultiplier)
    {
    }

    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos)
    {
    }
    
    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableHandler.BANSHEE;
    }
}
