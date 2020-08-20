package com.Fishmod.mod_LavaCow.entities.tameable;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.init.ModMobEffects;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;
import com.google.common.base.Predicate;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityScarecrow  extends EntityFishTameable{
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.<Integer>createKey(EntityScarecrow.class, DataSerializers.VARINT);
	private boolean isAggressive = false;
	private int attackTimer;
	/** 4: Vertical 5: Horizontal*/
	public byte AttackStance;
	
	public EntityScarecrow(World worldIn)
    {
        super(worldIn);
        this.setSize(0.8F, 3.0F);
        //this.setCanPickUpLoot(true);
    }
	
    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(SKIN_TYPE, Integer.valueOf(this.rand.nextInt(2)));
     }
	
    protected void initEntityAI()
    {
        super.initEntityAI();
    	this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityScarecrow.AIScarecrowAttackMelee(this, 1.0D, false));
    	//this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        //this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI()
    {
    	this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(4, new EntityAITargetNonTamed<>(this, EntityPlayer.class, false, new Predicate<Entity>()
        {
            public boolean apply(@Nullable Entity p_apply_1_)
            {
                return true;
            }
        }));
    	//this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityCow>(this, EntityCow.class, true));
    }
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Scarecrow_Health);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Scarecrow_Attack);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0.0D);
    }
    
    @Override
	public boolean getCanSpawnHere() {
		return super.getCanSpawnHere();
	}
	
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void onUpdate()
    {
        super.onUpdate();
    }
    
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void onLivingUpdate()
    {
        if (this.attackTimer > 0) {
            --this.attackTimer;
         }
        //System.out.println("O_O " + this.getEntityWorld().getBlockState(this.getPosition().down()).toString());	
    	if (!this.isTamed() && !this.world.isRemote)
        	{
				/*if (getEntityWorld().getBlockState(getPosition().down()) instanceof BlockAir)
					posY -= 1.0D;*/
    			float f = this.getBrightness();
        		BlockPos blockpos = new BlockPos(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ);
        		if (this.getEntityWorld().getBlockState(this.getPosition().down()).isOpaqueCube() && this.world.isDaytime() && f > 0.5F && !this.isAIDisabled() && this.world.canSeeSky(blockpos)) {
        			//this.setFire(8);
        			//this.motionY += 50.0D;
        			this.setNoAI(true);
        			this.setSilent(true);
        		}
        		else if ((f <= 0.5F || !this.world.canSeeSky(blockpos) || !this.getEntityWorld().getBlockState(this.getPosition().down()).isOpaqueCube()) && this.isAIDisabled()) {
        			this.setNoAI(false);
        			this.setSilent(false);
        		}
        	}
    	
        if (this.getAttackTarget() != null && this.getDistanceSq(this.getAttackTarget()) < 9.0D && this.getAttackTimer() == 5 && this.deathTime <= 0) {
        	float f = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();
        	this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0F, 1.0F);
        	
        	if(this.AttackStance == (byte)4) {
        		this.getAttackTarget().attackEntityFrom(DamageSource.causeMobDamage(this), (float) this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
        		if (this.getAttackTarget() instanceof EntityLivingBase)
        			if(this.getSkin() != 2)
        				((EntityLivingBase)this.getAttackTarget()).addPotionEffect(new PotionEffect(ModMobEffects.CORRODED, 4 * 20 * (int)f, 1));
        			else
        				((EntityLivingBase)this.getAttackTarget()).addPotionEffect(new PotionEffect(MobEffects.WITHER, 4 * 20 * (int)f, 1));
        		}
        			
        	else {
                for (EntityLivingBase entitylivingbase : this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getAttackTarget().getEntityBoundingBox().grow(2.0D, 0.25D, 2.0D)))
                {
                    if (entitylivingbase != this && !this.isOnSameTeam(entitylivingbase))
                    {
                        entitylivingbase.knockBack(this, 0.4F, (double)MathHelper.sin(this.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(this.rotationYaw * 0.017453292F)));
                        entitylivingbase.attackEntityFrom(DamageSource.causeMobDamage(this), (float) this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
            			if(this.getSkin() != 2)
            				((EntityLivingBase)this.getAttackTarget()).addPotionEffect(new PotionEffect(ModMobEffects.CORRODED, 4 * 20 * (int)f, 1));
            			else
            				((EntityLivingBase)this.getAttackTarget()).addPotionEffect(new PotionEffect(MobEffects.WITHER, 4 * 20 * (int)f, 1));
                    }
                }
        	}
        		
        
            
            if (this.getHeldItemMainhand().isEmpty() && this.isBurning() && this.rand.nextFloat() < f * 0.3F)
            {
            	this.getAttackTarget().setFire(2 * (int)f);
            }
            
            /*if (this.getAttackTarget() instanceof EntityLivingBase)
            {
                
            }*/
        }
    	
        super.onLivingUpdate();
    }
    
    public boolean attackEntityAsMob(Entity entityIn)
    {
        //boolean flag = super.attackEntityAsMob(entityIn);

        this.attackTimer = 15;
        this.AttackStance = this.rand.nextFloat() < 0.4F ? (byte)5 : (byte)4;
        this.world.setEntityState(this, this.AttackStance);
        
        return true;
        /*if (flag)
        {
            float f = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();
            if (this.getHeldItemMainhand().isEmpty() && this.isBurning() && this.rand.nextFloat() < f * 0.3F)
            {
                entityIn.setFire(2 * (int)f);
            }
            
            if (entityIn instanceof EntityLivingBase)
            {
                ((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 4 * 20 * (int)f, -2));
            }
        }

        return flag;*/
    }
    
    protected void doFollowCommand(EntityPlayer playerIn) {
    	super.doFollowCommand(playerIn);
    	
    }
    
    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount) {
    	if(source.isFireDamage())
    		return super.attackEntityFrom(source, 2.0F * amount);
    	return super.attackEntityFrom(source, amount);
    }
    
    /**
     * Gives armor or weapon for entity based on given DifficultyInstance
     */
    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
        super.setEquipmentBasedOnDifficulty(difficulty);

        //this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(FishItems.REAPERS_SCYTHE));
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        this.setEquipmentBasedOnDifficulty(difficulty);
        
    	if (this.rand.nextFloat() < 0.00625F * Modconfig.pSpawnRate_Raven && !this.world.isRemote) 
    	{
    		EntityRaven crowpet = new EntityRaven(this.world);
    		crowpet.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
    		crowpet.startRiding(this, false);
    		this.world.spawnEntity(crowpet);
    	}
        
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
    
    @SideOnly(Side.CLIENT)
    public boolean isAggressive()
    {
    	return isAggressive;
    }
    
    //@SideOnly(Side.CLIENT)
    public int getAttackTimer() {
       return this.attackTimer;
    }
    
    public int getSkin()
    {
        return ((Integer)this.dataManager.get(SKIN_TYPE)).intValue();
    }

    public void setSkin(int skinType)
    {
        this.dataManager.set(SKIN_TYPE, Integer.valueOf(skinType));
    }
    
    /**
     * Handler for {@link World#setEntityState}
     */
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
    	if (id == 4 || id == 5) 
    	{
            this.attackTimer = 15;
            this.AttackStance = id;
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

    public float getEyeHeight()
    {
        return 2.6F;
    }
    
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.setSkin(compound.getInteger("Variant"));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setInteger("Variant", getSkin());
	}
    
    @Override
    protected SoundEvent getAmbientSound()
    {
        return FishItems.ENTITY_SCARECROW_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return FishItems.ENTITY_SCARECROW_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return FishItems.ENTITY_SCARECROW_DEATH;
    }

    protected SoundEvent getStepSound()
    {
        return SoundEvents.ENTITY_STRAY_STEP;
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
        return this.getSkin()==0 ? LootTableHandler.SCARECROW : LootTableHandler.SCARECROW1;
    }
    
    /**
     * Entity won't drop items or experience points if this returns false
     */
    @Override
    protected boolean canDropLoot() {
       return !this.isBurning() || this.recentlyHit != 0;
    }
    
    static class AIScarecrowAttackMelee extends EntityAIAttackMelee {

		public AIScarecrowAttackMelee(EntityCreature creature, double speedIn, boolean useLongMemory) {
			super(creature, speedIn, useLongMemory);
		}
		
	    protected void checkAndPerformAttack(EntityLivingBase p_190102_1_, double p_190102_2_)
	    {
	        double d0 = this.getAttackReachSqr(p_190102_1_);

	        if (p_190102_2_ <= d0 && this.attackTick <= 0)
	        {
	            this.attackTick = 40;
	            this.attacker.swingArm(EnumHand.MAIN_HAND);
	            this.attacker.attackEntityAsMob(p_190102_1_);
	        }
	    }

	    protected double getAttackReachSqr(EntityLivingBase attackTarget)
	    {
	        return (double)(this.attacker.width * 9.0F * this.attacker.width * 9.0F + attackTarget.width);
	    }
    	
    }
}
