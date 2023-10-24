package com.Fishmod.mod_LavaCow.entities;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.entities.flying.EntityEnigmoth;
import com.Fishmod.mod_LavaCow.entities.flying.EntityVespa;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityFishTameable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityVespaCocoon  extends EntityFishTameable {
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.<Integer>createKey(EntityVespaCocoon.class, DataSerializers.VARINT);
	
	private int Lifespan = 8 * 20;
	
	public EntityVespaCocoon(World worldIn)
    {
        super(worldIn);
        this.setSize(0.8F, 1.0F);
    }
	
    protected void initEntityAI()
    {
        this.applyEntityAI();
    }

    protected void applyEntityAI()
    {

    }
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    }
    
    protected void entityInit() {
    	super.entityInit();
        this.getDataManager().register(SKIN_TYPE, Integer.valueOf(0));
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
        if(this.ticksExisted >= Lifespan) {
        	this.playSound(SoundEvents.ENTITY_SLIME_SQUISH, 1.0F, 1.0F);
        	
        	if (!this.world.isRemote) {
        		// Vespa
    			if (this.getSkin() == 0) {
    				EntityVespa adult = new EntityVespa(this.world);
    				adult.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
    				this.world.spawnEntity(adult);
    				adult.addVelocity(0.0D, 0.2D, 0.0D);
		    		if (this.isTamed() && this.getOwner() instanceof EntityPlayer) {
		    			adult.setTamedBy((EntityPlayer) this.getOwner());
		    			adult.setCustomNameTag(this.getCustomNameTag());
		    		}
		    		
		    	// Enigmoth
    			} else if (this.getSkin() == 1) {
    				EntityEnigmoth adult = new EntityEnigmoth(this.world);
    				adult.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
    				this.world.spawnEntity(adult);
    				adult.addVelocity(0.0D, 0.2D, 0.0D);
		    		if (this.isTamed() && this.getOwner() instanceof EntityPlayer) {
		    			adult.setTamedBy((EntityPlayer) this.getOwner());
		    			adult.setCustomNameTag(this.getCustomNameTag());
		    		}
		    		
		    		if (this.serializeNBT().hasKey("EnigmothData")) {
		    			adult.writeToNBT(this.serializeNBT().getCompoundTag("EnigmothData"));
		    		}		
    			}
        	}
        	this.setDead();
        }
     }
    
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void onLivingUpdate()
    {   	
        super.onLivingUpdate();
        this.motionX = 0.0D;
        //this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.prevRenderYawOffset = 180.0F;
        this.renderYawOffset = 180.0F;
        this.rotationYaw = 180.0F;
    }
    
    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
		if (source.equals(DamageSource.IN_WALL) || source.equals(DamageSource.DROWN))
			return false;
		return super.attackEntityFrom(source, amount);
    }

    public boolean attackEntityAsMob(Entity entityIn)
    {
        boolean flag = super.attackEntityAsMob(entityIn);

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
        this.renderYawOffset = 180.0F;
        this.prevRenderYawOffset = 180.0F;
        this.rotationYaw = 180.0F;
        this.prevRotationYaw = 180.0F;
        this.rotationYawHead = 180.0F;
        this.prevRotationYawHead = 180.0F;
        return livingdata;
    }
    
    public int getSkin() {
        return this.dataManager.get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
        this.dataManager.set(SKIN_TYPE, Integer.valueOf(skinType));
    }
    
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("lifespan", Lifespan);
 		nbt.setInteger("Variant", getSkin());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		Lifespan = nbt.getInteger("lifespan");
 		setSkin(nbt.getInteger("Variant"));
	}
	  
    @Override
    public boolean isMovementBlocked() {
    	return true;
    }
    
    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    public boolean canBePushed()
    {
        return false;
    }
    
    /**
     * Applies a velocity to the entities, to push them away from eachother.
     */
    public void applyEntityCollision(Entity entityIn)
    {
    }

    public float getEyeHeight()
    {
        return 0.8F;
    }
    
    @Override
    protected SoundEvent getAmbientSound()
    {
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_SILVERFISH_STEP;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_SLIME_DEATH;
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.ARTHROPOD;
    }
}
