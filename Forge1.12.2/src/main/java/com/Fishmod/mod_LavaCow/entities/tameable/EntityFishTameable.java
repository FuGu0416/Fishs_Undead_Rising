package com.Fishmod.mod_LavaCow.entities.tameable;

import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class EntityFishTameable extends EntityTameable{
	protected static final DataParameter<Float> DATA_HEALTH = EntityDataManager.createKey(EntityFishTameable.class, DataSerializers.FLOAT);
	protected EntityFishTameable.State state;
	private EntityAIWanderAvoidWater wander;
	private EntityAIFollowOwner follow;
	
	public EntityFishTameable(World worldIn) {
		super(worldIn);
		this.setTamed(false);
	}
	
    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(DATA_HEALTH, this.getHealth());
        this.state = EntityFishTameable.State.WANDERING;
     }
    
    protected void initEntityAI()
    {   	
    	this.wander = new EntityAIWanderAvoidWater(this, 1.0D, 0.0F);
    	this.follow = new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F);
    	this.aiSit = new EntityAISit(this);
    	
        this.tasks.addTask(7, this.wander);
    }
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
             
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);       
    }
    
    /**
     * Checks to make sure the light is not too bright where the mob is spawning
     */
    protected boolean isValidLightLevel()
    {
        BlockPos blockpos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);

        if (this.world.getLightFor(EnumSkyBlock.SKY, blockpos) > this.rand.nextInt(32))
        {
            return false;
        }
        else
        {
            int i = this.world.getLightFromNeighbors(blockpos);

            if (this.world.isThundering())
            {
                int j = this.world.getSkylightSubtracted();
                this.world.setSkylightSubtracted(10);
                i = this.world.getLightFromNeighbors(blockpos);
                this.world.setSkylightSubtracted(j);
            }

            return i <= this.rand.nextInt(8);
        }
    }
    
    public float getBlockPathWeight(BlockPos pos)
    {
        return 10.0F - this.world.getLightBrightness(pos);
    }
    
    public boolean getCanSpawnHere() {
    	
    	IBlockState iblockstate = this.world.getBlockState((new BlockPos(this)).down());
    	//System.out.println("OAO " + this.isValidLightLevel() + " " + this.getBlockPathWeight(new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ)) + " " + iblockstate.canEntitySpawn(this));
    	return this.isValidLightLevel() 
    			&& this.world.getDifficulty() != EnumDifficulty.PEACEFUL
    			&& this.getBlockPathWeight(new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ)) >= 0.0F 
    			&& iblockstate.canEntitySpawn(this);
    }
    
    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
    {
        return !this.isTamed() && !(this.getOwner() instanceof EntityPlayer);
    }
    
    protected boolean isCommandItem(Item itemIn) {
    	return itemIn.equals(Items.STICK);
    }
    
    protected void doSitCommand(EntityPlayer playerIn) {
    	this.tasks.removeTask(this.wander);
        this.isJumping = false;
        this.navigator.clearPath();
		this.state = EntityFishTameable.State.SITTING;
		playerIn.sendStatusMessage(new TextComponentTranslation(this.getName()).appendSibling(new TextComponentTranslation("command.mod_lavacow.sitting")), true);
    }
    
    protected void doFollowCommand(EntityPlayer playerIn) {
		this.follow = new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F);
		this.tasks.addTask(6, this.follow);
		this.navigator.clearPath();
		this.state = EntityFishTameable.State.FOLLOWING;
		playerIn.sendStatusMessage(new TextComponentTranslation(this.getName()).appendSibling(new TextComponentTranslation("command.mod_lavacow.following")), true);
    }
    
    protected void doWanderCommand(EntityPlayer playerIn) {
		this.tasks.removeTask(this.follow);
		this.wander = new EntityAIWanderAvoidWater(this, 1.0D, 0.0F);
		this.tasks.addTask(7, this.wander);
		this.navigator.clearPath();
		this.state = EntityFishTameable.State.WANDERING;
		playerIn.sendStatusMessage(new TextComponentTranslation(this.getName()).appendSibling(new TextComponentTranslation("command.mod_lavacow.wandering")), true);
    }
    
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
            	
    	if(this.isServerWorld() && this.isTamed() && this.isOwner(player) && hand.equals(EnumHand.MAIN_HAND)) {    	
	    	if (this.isCommandItem(itemstack.getItem()) && !this.isBeingRidden()) {
	    		if(this.state.equals(EntityFishTameable.State.WANDERING)) {
	    			this.doSitCommand(player);
	    		}
	    		else if(this.state.equals(EntityFishTameable.State.SITTING)) {
	    			this.doFollowCommand(player);
	    		}
	    		else if(this.state.equals(EntityFishTameable.State.FOLLOWING)) {
	    			this.doWanderCommand(player);
	    		}
	    		
	    		return true;
	    	}
    	}
        
        if(this.canTameCondition()) {
	        if (this.isBreedingItem(itemstack)) {
	           if (!player.capabilities.isCreativeMode) {
	              itemstack.shrink(1);
	           }
	
	           if (!this.world.isRemote) {
	              if (this.rand.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
	            	  this.setTamedBy(player);
	                 this.navigator.clearPath();
	                 this.setAttackTarget((EntityLivingBase)null);
	                 //this.aiSit.setSitting(true);
	                 this.setHealth(this.getMaxHealth());
	                 this.playTameEffect(true);
	                 this.world.setEntityState(this, (byte)7);
	              } else {
	                 this.playTameEffect(false);
	                 this.world.setEntityState(this, (byte)6);
	              }
	           }
	
	           return true;
	        }
        }
	        
        return super.processInteract(player, hand);
    }
    
    protected boolean canTameCondition() {
    	return !this.isTamed();
    }
    
    @Override
    public void setTamedBy(EntityPlayer player)
    {
    	//this.doSitCommand(player);
    	this.doFollowCommand(player);
    	super.setTamedBy(player);
    }
    
    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();

        if (!this.world.isRemote && !this.isTamed() && this.world.getDifficulty() == EnumDifficulty.PEACEFUL)
        {
            this.setDead();
        }
        
        if (!this.world.isRemote && (this.getOwner() != null && (!(this.getOwner() instanceof EntityPlayer) && !this.getOwner().isEntityAlive()))) {
        	this.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageIsAbsolute().setDamageBypassesArmor() , this.getMaxHealth());
        }
    }
    
    protected void updateAITasks()
    {
        super.updateAITasks();
        this.dataManager.set(DATA_HEALTH, this.getHealth());
    }
    
    public boolean attackEntityAsMob(Entity entityIn)
    {
        return entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((int)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue()));
    }
    
    public static EntityLivingBase getEntityByUniqueId(UUID uniqueId, World worldIn){
        
    	for(Entity E : worldIn.loadedEntityList) {
    		if(E instanceof EntityLivingBase && E.getUniqueID().equals(uniqueId))
    			return (EntityLivingBase) E;
    	}

        return null;
    }
    
    @Override
    @Nullable
    public EntityLivingBase getOwner()
    {
        try
        {
            UUID uuid = this.getOwnerId();
            return uuid == null ? null : this.getEntityByUniqueId(uuid, this.getEntityWorld());
        }
        catch (IllegalArgumentException var2)
        {
            return null;
        }
    }
    
    @Override
    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    public boolean isBreedingItem(ItemStack stack)
    {
        return false;
    }

	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		return null;
	}
	
    /**
     * Writes the extra NBT data specific to this type of entity. Should <em>not</em> be called from outside this class;
     * use {@link #writeUnlessPassenger} or {@link #writeWithoutTypeId} instead.
     */
    public void writeEntityToNBT(NBTTagCompound compound) {
       super.writeEntityToNBT(compound);
       if(this.state.equals(EntityFishTameable.State.WANDERING)) {
    	   compound.setByte("state", (byte)0);
		}
		else if(this.state.equals(EntityFishTameable.State.SITTING)) {
			compound.setByte("state", (byte)1);
		}
		else if(this.state.equals(EntityFishTameable.State.FOLLOWING)) {
			compound.setByte("state", (byte)2);
		}
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound) {
       super.readEntityFromNBT(compound);
       switch(compound.getByte("state")) {
	       case (byte)0:
	    	   this.state = EntityFishTameable.State.WANDERING;
	       		this.navigator.clearPath();
	  			break;
	       case (byte)1:
	    	   this.state = EntityFishTameable.State.SITTING;
	    	   this.tasks.removeTask(this.wander);
               this.isJumping = false;
               this.navigator.clearPath();
	  			break;
	       case (byte)2:
	    	   this.state = EntityFishTameable.State.FOLLOWING;
	       	   this.follow = new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F);
	       	   this.navigator.clearPath();
	    	   this.tasks.removeTask(this.wander);
	    	   this.tasks.addTask(6, this.follow);
	   			break;
	   		default:
	   			break;
       }
    }
    
    public boolean isPreventingPlayerRest(EntityPlayer playerIn)
    {
        return !this.isTamed();
    }
	
    //@SideOnly(Side.CLIENT)
    static enum State
    {
        SITTING,
        WANDERING,
        FOLLOWING;
    }
}
