package com.Fishmod.mod_LavaCow.entities.tameable;

import java.util.UUID;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.IAggressive;
import com.Fishmod.mod_LavaCow.entities.ai.EntityFishAIAttackRange;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityWarSmallFireball;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.init.Modkeys;
import com.Fishmod.mod_LavaCow.message.PacketMountSpecial;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;
import com.google.common.base.Predicate;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySalamander extends EntityFishTameable implements IAggressive{
	private static final DataParameter<Integer> ATTACK_TIMER = EntityDataManager.createKey(EntitySalamander.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> SADDLED = EntityDataManager.createKey(EntitySalamander.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> GROWING_STAGE = EntityDataManager.createKey(EntitySalamander.class, DataSerializers.VARINT);
	
	private boolean isAggressive = false;
	private EntityFishAIAttackRange range_atk;
	private EntityAIAvoidEntity<EntityPlayer> avoid_entity;
	private int barrage_CD;
	
	public EntitySalamander(World worldIn)
    {
        super(worldIn);
        
        this.setSize(2.0F, 1.6F);    
        this.isImmuneToFire = true;
        this.setPathPriority(PathNodeType.WATER, -1.0F);
        this.setPathPriority(PathNodeType.LAVA, 8.0F);
        this.setPathPriority(PathNodeType.DANGER_FIRE, 0.0F);
        this.setPathPriority(PathNodeType.DAMAGE_FIRE, 0.0F);
        this.experienceValue = 20;
        this.barrage_CD = 0;
    }
	
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(SADDLED, Boolean.valueOf(false));
		this.dataManager.register(ATTACK_TIMER, Integer.valueOf(0));
		this.dataManager.register(GROWING_STAGE, Integer.valueOf(0));
	}
	
    protected void initEntityAI()
    {   	
    	super.initEntityAI();
    	if(this.isNymph())
    		this.range_atk = new EntityFishAIAttackRange(this, EntityWarSmallFireball.class, 8, 5, 2.5D, 1.0D, 2.5D);
    	else
    		this.range_atk = new EntityFishAIAttackRange(this, EntityWarSmallFireball.class, 1, 5, 1.0D, 0.1D, 1.0D);
    	
    	this.tasks.addTask(0, new EntityAISwimming(this));
    	this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
    	this.tasks.addTask(4, this.range_atk);
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
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
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Salamander_Health);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Salamander_Attack);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    }
    
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender() {
       return 15728880;
    }

    /**
     * Gets how bright this entity is.
     */
    public float getBrightness() {
       return 1.0F;
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
    
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
    	ItemStack itemstack = player.getHeldItem(hand);
    	
    	if(this.isOwner(player) && hand.equals(EnumHand.MAIN_HAND)) {    	
	    	if (this.canBeSteered() && itemstack.isEmpty()) {	    		
	    		if(player.isSneaking()) {
	    			this.setSaddled(false);
	    			if(!this.world.isRemote)this.dropItem(Items.SADDLE, 1);
	    		}
	    		else if(!player.isRiding())
	    			player.startRiding(this, true);
	    			
	    		return true;
	    	}
	    	else if (itemstack.interactWithEntity(player, this, hand))
        	{
        		return true;
        	}
	    	else if (!this.canBeSteered() && !this.isChild() && itemstack.getItem() == Items.SADDLE) {
	    		this.setSaddled(true);
                if(!this.world.isRemote)this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PIG_SADDLE, SoundCategory.NEUTRAL, 0.5F, 1.0F);
                
                itemstack.shrink(1);    
                
	    		return true;
	    	}
    	}
    	return super.processInteract(player, hand);
    }
    
    protected boolean canTameCondition() {
    	return this.isChild() && super.canTameCondition();
    }
    
    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    public boolean isBreedingItem(ItemStack stack) {
       Item item = stack.getItem();
       return item == FishItems.CANEBEEF;
    }
    
    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    public double getMountedYOffset()
    {
        return (double)this.height * 1.3D;
    }
    
    public void updatePassenger(Entity passenger) {
        super.updatePassenger(passenger);
        if (this.isPassenger(passenger)) {
            renderYawOffset = rotationYaw;
            this.rotationYaw = passenger.rotationYaw;
        }
    }
    
    @Nullable
    public Entity getControllingPassenger()
    {
        for (Entity passenger : this.getPassengers()) {
            if (passenger instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) passenger;
                if (this.isTamed() && this.getOwnerId() != null && this.getOwnerId().equals(player.getUniqueID())) {
                    return player;
                }
            }
        }
        return null;
    }
    
    public boolean isRidingPlayer(EntityPlayer player) {
        return this.getControllingPassenger() != null && this.getControllingPassenger() instanceof EntityPlayer && this.getControllingPassenger().getUniqueID().equals(player.getUniqueID());
    }
    
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
    	if(this.getAttackTimer() > 0)
    		this.setAttackTimer(this.getAttackTimer() - 1);
        if(this.barrage_CD > 0)this.barrage_CD--;
        
    	if (!this.world.isRemote)
        {
            if (this.isTamed() && this.rand.nextInt(this.isInLava() ? 45 : 900) == 0 && this.deathTime == 0)
            {
                this.heal(1.0F);
            }
        }
    	
    	if(this.isServerWorld()) {
	    	if(this.growingAge < -16000) {
	    		if(this.getGrowingStage() != 0)
	    			this.setGrowingStage(0);
	    	}
	    	else if(this.growingAge < -8000) {
	    		if(this.getGrowingStage() != 1) {
		    		this.setGrowingStage(1);
		    		
		    		this.experienceValue = 10;
		    		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Salamander_Health * 0.40D);
		    		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
		    		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Salamander_Attack * 0.65D);
		    		
		    		this.heal(this.getHealth() * (0.15F / 0.25F));
	    		}
	    	}
	    	else if(this.growingAge < 0) {
	    		if(this.getGrowingStage() != 2) {
		    		this.setGrowingStage(2);
		    		
		    		this.experienceValue = 15;
		    		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Salamander_Health * 0.60D);
		    		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
		    		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Salamander_Attack * 0.75D);
		    		
		    		this.heal(this.getHealth() * 0.5F);
	    		}
	    	}
	    	else {	    		
	    		if(this.getGrowingStage() != 3) {
	    			this.setGrowingStage(3);
	    			
	    			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Salamander_Health);
	    			this.heal(this.getHealth() * 2.0F / 3.0F);
	    		}
	    	}
    	}	
    }
    
    @Override
    public void onUpdate() {
    	super.onUpdate();
    		
    	if (world.isRemote) {
            this.ClientControl();
        }

    }
    
    @SideOnly(Side.CLIENT)
    private void ClientControl() {
    	EntityPlayer player = Minecraft.getMinecraft().player;		
		if (this.barrage_CD == 0 && Modkeys.MOUNT_SPECIAL.isKeyDown() && this.isRidingPlayer(player)) {
			this.barrage_CD = 80;	
			mod_LavaCow.NETWORK_WRAPPER.sendToServer(new PacketMountSpecial(this.getEntityId(), this.posX, this.posY, this.posZ));
		}  	
    }
    
    /**
     * Returns true if the mob is currently able to mate with the specified mob.
     */
    public boolean canMateWith(EntityAnimal otherAnimal) {
       if (otherAnimal == this) {
          return false;
       } else if (!this.isTamed()) {
          return false;
       } else if (!(otherAnimal instanceof EntitySalamander)) {
          return false;
       } else {
    	   EntitySalamander entitywolf = (EntitySalamander)otherAnimal;
          if (!entitywolf.isTamed()) {
             return false;
          } else if (entitywolf.isSitting()) {
             return false;
          } else {
             return this.isInLove() && entitywolf.isInLove();
          }
       }
    }
    
    protected void updateAITasks()
    {
        if (this.isWet()) {
            this.attackEntityFrom(DamageSource.DROWN, 1.0F);
         }
        
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
        
        super.updateAITasks();
    }
    
    /**
     * Set or remove the saddle of the pig.
     */
    public void setSaddled(boolean saddled)
    {
    	this.dataManager.set(SADDLED, Boolean.valueOf(saddled));
    }
    
    public boolean isAggressive()
    {
    	return isAggressive;
    }
    
    /**
     * Growing Stage: Nymph -> Child-> Adult
     */
    public int getGrowingStage() {
       return dataManager.get(GROWING_STAGE).intValue();
    }
    
    public void setGrowingStage(int i) {
        dataManager.set(GROWING_STAGE, i);
	}
    
    public boolean isNymph() {
    	return this.getGrowingStage() == 0;
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
    
    /**
     * This is called when Entity's growing age timer reaches 0 (negative values are considered as a child, positive as
     * an adult)
     */
    @Override
    protected void onGrowingAdult()
    {
    	this.setChild(false);
    	super.onGrowingAdult();
    }
    
    /**
     * Set whether this entity is a child.
     */
    private void setChild(boolean isChildIn) {
    	
    	if(isChildIn) {
	    	this.experienceValue = 5;
	    	
	    	this.avoid_entity = new EntityAIAvoidEntity<>(this, EntityPlayer.class, 4.0F, 0.8D, 1.6D);
	    	this.tasks.addTask(3, this.avoid_entity);
	    	this.tasks.removeTask(this.range_atk);
	    	this.range_atk = new EntityFishAIAttackRange(this, EntityWarSmallFireball.class, 1, 5, 1.0D, 0.1D, 1.0D);
	    	this.tasks.addTask(4, this.range_atk);
	    	
	    	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Salamander_Health * 0.25D);
	        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.28D);
	        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Salamander_Attack * 0.5D);
	        
	        if (this.getHealth() > this.getMaxHealth())
	        	this.setHealth(this.getMaxHealth());
	        
	        if(this.canBeSteered()) {
	        	this.setSaddled(false);
	        	
	            if (!this.world.isRemote)
	            {
	            	this.dropItem(Items.SADDLE, 1);
	            }
	        }
    	}
    	else {
	    	this.experienceValue = 20;
	    	
	    	this.tasks.removeTask(this.avoid_entity);
	    	this.tasks.removeTask(this.range_atk);
	    	this.range_atk = new EntityFishAIAttackRange(this, EntityWarSmallFireball.class, 8, 5, 2.5D, 1.0D, 2.5D);
	    	this.tasks.addTask(4, this.range_atk);
	    	
	    	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Salamander_Health);
	        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23D);
	        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Salamander_Attack);	   
    	}
    }
    
    public void setTamed(boolean tamed) {
    	if(tamed) {
    		this.tasks.removeTask(this.avoid_entity);
    	}
    	
    	super.setTamed(tamed);
    }
    
	public EntitySalamander createChild(EntityAgeable ageable) {
		EntitySalamander entity = new EntitySalamander(this.world);
		entity.setGrowingAge(-24000);
		UUID uuid = this.getOwnerId();
		if (uuid != null) {
			entity.setOwnerId(uuid);
			entity.setTamed(true);
			entity.setChild(true);
		}

		return entity;
	}
    
    /**
     * Checks to make sure the light is not too bright where the mob is spawning
     */
    protected boolean isValidLightLevel() {
       return true;
    }
    
    /**
     * returns true if all the conditions for steering the entity are met. For pigs, this is true if it is being ridden
     * by a player and the player is holding a carrot-on-a-stick
     */
    public boolean canBeSteered()
    {
    	return Boolean.valueOf(this.dataManager.get(SADDLED).booleanValue());
    }
    
    @Override
    public void travel(float strafe, float vertical, float forward)
    {
        Entity entity = this.getPassengers().isEmpty() ? null : (Entity)this.getPassengers().get(0);

        if (this.isBeingRidden() && this.canBeSteered())
        {
        	EntityLivingBase controller = (EntityLivingBase)entity;
        	this.rotationYaw = controller.rotationYaw;
            this.prevRotationYaw = this.rotationYaw;
            this.rotationPitch = controller.rotationPitch * 0.5F;
            this.setRotation(this.rotationYaw, this.rotationPitch);
            this.renderYawOffset = this.rotationYaw;
            this.rotationYawHead = this.rotationYaw;
            this.stepHeight = 1.0F;
            this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1F;
            strafe = controller.moveStrafing * 0.5F;
            forward = controller.moveForward;

            if (this.canPassengerSteer())
            {
                float f = (float)this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() * 0.6F;
                if(this.isInLava()) {
                	this.motionX *= 1.5F;
                	this.motionY += 0.02F;
                	this.motionZ *= 1.5F;
                }
                this.setAIMoveSpeed(f);
                super.travel(strafe, vertical, forward);
            }
            else
            {
                this.motionX = 0.0D;
                this.motionY = 0.0D;
                this.motionZ = 0.0D;
            }

            this.prevLimbSwingAmount = this.limbSwingAmount;
            double d1 = this.posX - this.prevPosX;
            double d0 = this.posZ - this.prevPosZ;
            float f1 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;

            if (f1 > 1.0F)
            {
                f1 = 1.0F;
            }

            this.limbSwingAmount += (f1 - this.limbSwingAmount) * 0.4F;
            this.limbSwing += this.limbSwingAmount;
        }
        else
        {
            super.travel(strafe, vertical, forward);
        }
    }

    
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData entityLivingData) {
       entityLivingData = super.onInitialSpawn(difficulty, entityLivingData);
       float chance_to_spawn_as_child = 0.0F;
       
       switch(this.world.getDifficulty()) {
	       case PEACEFUL:
	    	   chance_to_spawn_as_child = 0.85F;
	  			break;
	       case EASY:
	    	   chance_to_spawn_as_child = 0.8F;
	  			break;
	       case NORMAL:
	    	   chance_to_spawn_as_child = 0.5F;
	  			break;
	       case HARD:
	    	   chance_to_spawn_as_child = 0.15F;
       			break;
       		default:
       			break;
       }
       
       if (this.world.rand.nextDouble() <= chance_to_spawn_as_child) {
          this.setGrowingAge(-24000);
          this.setChild(true);
       } else {
    	   this.setGrowingStage(3);
       }
       
       return entityLivingData;
    }
    
    public float getEyeHeight()
    {
        return this.isChild() ? 0.2F : 0.8F;
    }
    
    @Override
    public int getAttackTimer() {
       return dataManager.get(ATTACK_TIMER).intValue();
    }
    
    @Override
    public void setAttackTimer(int i) {
        dataManager.set(ATTACK_TIMER, i);
	}
    
    @Override
    protected SoundEvent getAmbientSound()
    {
        return FishItems.ENTITY_SALAMANDER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return FishItems.ENTITY_SALAMANDER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return FishItems.ENTITY_SALAMANDER_DEATH;
    }

    protected SoundEvent getStepSound()
    {
        return SoundEvents.ENTITY_COW_STEP;
    }

    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }
    
    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource cause)
    {
        super.onDeath(cause);

        if (!this.world.isRemote)
        {
            if (this.canBeSteered())
            {
                this.dropItem(Items.SADDLE, 1);
            }
        }
    }
    
    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return this.isChild() ? LootTableHandler.SALAMANDERLESSER : LootTableHandler.SALAMANDER;
    }
    
    /**
     * Entity won't drop items or experience points if this returns false
     */
    @Override
    protected boolean canDropLoot()
    {
        return true;
    }
    
    public void setGrowingAge(int age) {
    	super.setGrowingAge(age);
    }
    
    /**
     * "Sets the scale for an ageable entity according to the boolean parameter, which says if it's a child."
     */
    public void setScaleForAge(boolean child)
    {
    	float scale = 1.0F;
    	
    	switch (this.getGrowingStage()) {
    		case 0:
    			scale = 0.30F;
    			break;
    		case 1:
    			scale = 0.50F;
    			break;
    		case 2:
    			scale = 0.75F;
    			break;
    		default:
    			scale = 1.0F;
    			break;   			
    	}
    	
    	this.setScale(scale);
    }
    
    /**
     * Writes the extra NBT data specific to this type of entity. Should <em>not</em> be called from outside this class;
     * use {@link #writeUnlessPassenger} or {@link #writeWithoutTypeId} instead.
     */
    public void writeEntityToNBT(NBTTagCompound compound) {
       super.writeEntityToNBT(compound);
       compound.setBoolean("Saddled", this.canBeSteered());
       compound.setInteger("GrowingStage", this.getGrowingStage());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound) {
       super.readEntityFromNBT(compound);
       this.setSaddled(compound.getBoolean("Saddled"));
       this.setGrowingStage(compound.getInteger("GrowingStage"));
    }
}
