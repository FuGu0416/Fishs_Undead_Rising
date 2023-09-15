package com.Fishmod.mod_LavaCow.entities.tameable;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
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
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
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

public class EntityScarecrow  extends EntityFishTameable {
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.<Integer>createKey(EntityScarecrow.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> DATA_COLLAR_COLOR = EntityDataManager.<Integer>createKey(EntityScarecrow.class, DataSerializers.VARINT);
	private boolean isAggressive = false;
	private int attackTimer;
	private int cleaveTimer;
	/** 4: Vertical 5: Horizontal*/
	public byte AttackStance;
	
	private EntityAIMoveTowardsRestriction move;
	private EntityAIWatchClosest watch;
	private EntityAILookIdle look;
	
	public EntityScarecrow(World worldIn)
    {
        super(worldIn);
        this.setSize(0.8F, 3.0F);
    }
	
    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(SKIN_TYPE, Integer.valueOf(this.rand.nextInt(2)));
        this.getDataManager().register(DATA_COLLAR_COLOR, Integer.valueOf(EnumDyeColor.BROWN.getDyeDamage()));
     }
	
    protected void initEntityAI()
    {
        this.move = new EntityAIMoveTowardsRestriction(this, 1.0D);
        this.watch = new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F);
        this.look = new EntityAILookIdle(this);
    	
    	super.initEntityAI();
    	this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityScarecrow.AIScarecrowAttackMelee(this, 1.0D, true));
        this.tasks.addTask(5, this.move);
        this.tasks.addTask(8, this.watch);
        this.tasks.addTask(8, this.look);
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
    }
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Scarecrow_Health);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Scarecrow_Attack);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    }
    
    @Override
	public boolean getCanSpawnHere() {
		return SpawnUtil.isAllowedDimension(this.dimension) && super.getCanSpawnHere();
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
        
    	if (this.cleaveTimer > 0) {
    		--this.cleaveTimer;
    	}

    	if (!this.world.isRemote && !this.isTamed()) {
			float f = this.getBrightness();
    		if (this.world.isDaytime() && f > 0.5F && this.world.canSeeSky(this.getPosition())) {
    			if(this.state != EntityFishTameable.State.SITTING)
    				this.doSitCommand(null);
    		}
    		else if (this.state != EntityFishTameable.State.WANDERING) {
    			this.doFollowCommand(null);
    			this.doWanderCommand(null);
    		}
    	}
    	
    	if(this.isBeingRidden())
    		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
    	else
    		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);

    	// Should always return EntityLivingBase (according to the documentation).
    	EntityLivingBase target = this.getAttackTarget();
    	
    	if(target != null && this.getAttackTimer() == 10 && this.deathTime <= 0 && this.AttackStance == (byte)4) {
    		this.playSound(FishItems.ENTITY_SCARECROW_SCYTHE, 1.0F, 0.85F / (this.world.rand.nextFloat() * 0.4F + 0.8F));
    	} else if (target != null && this.getAttackTimer() == 14 && this.deathTime <= 0 && this.AttackStance == (byte)5) {
    		this.playSound(FishItems.ENTITY_SCARECROW_SPIN, 1.0F, 0.85F);
    	}
    	
        if (target != null && this.getDistanceSq(target) < 9.0D && this.getAttackTimer() == 5 && this.deathTime <= 0 && this.canEntityBeSeen(target)) {
        	float f = this.world.getDifficultyForLocation(target.getPosition()).getAdditionalDifficulty();
        	
        	if(this.AttackStance == (byte)4) {
        		if (target.attackEntityFrom(DamageSource.causeMobDamage(this), (float) this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue())) {
	    			if(this.getSkin() != 2)
	    				target.addPotionEffect(new PotionEffect(ModMobEffects.CORRODED, 4 * 20 * (int)f, 1));
	    			else
	    				target.addPotionEffect(new PotionEffect(MobEffects.WITHER, 4 * 20 * (int)f, 1));
	    			
	                if (this.getHeldItemMainhand().isEmpty() && this.isBurning() && this.rand.nextFloat() < f * 0.3F) {
	                	target.setFire(2 * (int)f);
	                }
        		}
        	} else {
                for (EntityLivingBase entitylivingbase : this.world.getEntitiesWithinAABB(EntityLivingBase.class, target.getEntityBoundingBox().grow(2.0D, 0.25D, 2.0D))) {
                    if (!this.isEntityEqual(entitylivingbase) && !this.isOnSameTeam(entitylivingbase)) {
                    	if (entitylivingbase.attackEntityFrom(DamageSource.causeMobDamage(this), (float) this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue())) {
	                        entitylivingbase.knockBack(this, 0.4F, (double)MathHelper.sin(this.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(this.rotationYaw * 0.017453292F)));
	                        
	            			if(this.getSkin() != 2)
	            				target.addPotionEffect(new PotionEffect(ModMobEffects.CORRODED, 4 * 20 * (int)f, 1));
	            			else
	            				target.addPotionEffect(new PotionEffect(MobEffects.WITHER, 4 * 20 * (int)f, 1));
	            			
	    	                if (this.getHeldItemMainhand().isEmpty() && this.isBurning() && this.rand.nextFloat() < f * 0.3F) {
	    	                	target.setFire(2 * (int)f);
	    	                }
                    	}
                    }
                }
        	}
        }
    	
        super.onLivingUpdate();
    }
    
    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
    	ItemStack itemstack = player.getHeldItem(hand);
    	Item item = itemstack.getItem();

    	if (this.isTamed() && item instanceof ItemDye) {          
            EnumDyeColor dyecolor = EnumDyeColor.byDyeDamage(itemstack.getMetadata());

            if (dyecolor != this.getCollarColor()) {
                this.setCollarColor(dyecolor);

                if (!player.capabilities.isCreativeMode) {
                    itemstack.shrink(1);
                }
                
                return true;
            }
    	} else if (this.isTamed() && this.isOwner(player)) {
    	    for (Entity passenger : (this.isBeingRidden() ? this : player).getPassengers()) {
    	        if (passenger instanceof EntityRaven) {
    	            if (!this.isBeingRidden()) passenger.startRiding(this, true);
    	            else passenger.startRiding(player, true);
    	        }
    	    }
    	    return true;
    	}
    	
		return super.processInteract(player, hand);	
    }
    
    @Override
    public double getMountedYOffset() {
        return 2.45D;
    }
    
    public boolean attackEntityAsMob(Entity entityIn)
    {
    	if (this.attackTimer == 0) {
	        this.attackTimer = 15;
	        if(this.cleaveTimer == 0) {
	        	this.AttackStance = (byte)5;
	        	this.cleaveTimer = 140;
	        } else {
	        	this.AttackStance = (byte)4;
	        }
	        this.world.setEntityState(this, this.AttackStance);
	        
	        return true;
    	}
    	
    	return false;
    }
    
    @Override
    protected void doSitCommand(EntityPlayer playerIn) {
        this.tasks.removeTask(this.move);
        this.tasks.removeTask(this.watch);
        this.tasks.removeTask(this.look);
        this.setSilent(true);
    	super.doSitCommand(playerIn);
    }
    
    @Override
    protected void doFollowCommand(EntityPlayer playerIn) {
        this.tasks.addTask(5, this.move);
        this.tasks.addTask(8, this.watch);
        this.tasks.addTask(8, this.look);
		this.setSilent(false);
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
    }
    
    @Override
    public boolean canBeSteered() {
        return false;
    }
    
    /**
     * For vehicles, the first passenger is generally considered the controller and "drives" the vehicle. For example,
     * Pigs, Horses, and Boats are generally "steered" by the controlling passenger.
     */
    @Nullable
    public Entity getControllingPassenger()
    {
        return null;
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
    		crowpet.startRiding(this, true);
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
    
    public int getAttackTimer() {
       return this.attackTimer;
    }
    
    public int getSkin()
    {
        return this.dataManager.get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType)
    {
        this.dataManager.set(SKIN_TYPE, Integer.valueOf(skinType));
        
    	if (skinType == 2) {
    		this.setCollarColor(EnumDyeColor.BLACK);
    	}
    }
    
    public EnumDyeColor getCollarColor()
    {
        return EnumDyeColor.byDyeDamage(((Integer)this.dataManager.get(DATA_COLLAR_COLOR)).intValue() & 15);
    }

    public void setCollarColor(EnumDyeColor collarcolor)
    {
        this.dataManager.set(DATA_COLLAR_COLOR, Integer.valueOf(collarcolor.getDyeDamage()));
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
    
    /**
     * The speed it takes to move the entityliving's rotationPitch through the faceEntity method. This is only currently
     * use in wolves.
     */
    public int getVerticalFaceSpeed()
    {
        return this.isSilent() ? 0 : super.getVerticalFaceSpeed();
    }

    public int getHorizontalFaceSpeed()
    {
        return this.isSilent() ? 0 : super.getHorizontalFaceSpeed();
    }
    
    public void updatePassenger(Entity passenger) {
        super.updatePassenger(passenger);

        passenger.motionX = 0;
        passenger.motionY = 0;
        passenger.motionZ = 0;
    }
	
    @Override
    public void travel(float strafe, float vertical, float forward)
    {
    	if(!this.isSilent() || !this.getEntityWorld().getBlockState(this.getPosition().down()).isOpaqueCube()) {
    		super.travel(strafe, vertical, forward);
    	}
    }
    
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.setSkin(compound.getInteger("Variant"));
        if (compound.hasKey("CollarColor", 99)) {
            this.setCollarColor(EnumDyeColor.byDyeDamage(compound.getByte("CollarColor")));
        }
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setInteger("Variant", getSkin());
        compound.setByte("CollarColor", (byte)this.getCollarColor().getDyeDamage());
	}
    
    @Override
    protected SoundEvent getAmbientSound()
    {
        return FishItems.ENTITY_SCARECROW_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.BLOCK_CLOTH_BREAK;
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
    	switch(this.getSkin()) { 
	        case 0: 
	        	return LootTableHandler.SCARECROW;
	        case 1: 
	            return LootTableHandler.SCARECROW1;
	        case 2: 
	        	return LootTableHandler.SCARECROW2;
	        default: 
	            return null; 
	    }       
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
		
	    public boolean shouldExecute()
	    {
        	return !this.attacker.isSilent() && super.shouldExecute();
	    }

	    protected double getAttackReachSqr(EntityLivingBase attackTarget)
	    {
	        return (double)(this.attacker.width * 4.0F * this.attacker.width * 4.0F + attackTarget.width);
	    }
    	
    }
}
