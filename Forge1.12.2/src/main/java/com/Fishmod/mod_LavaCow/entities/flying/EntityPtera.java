package com.Fishmod.mod_LavaCow.entities.flying;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.entities.EntityFoglet;
import com.Fishmod.mod_LavaCow.entities.ai.EntityAIDropRider;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class EntityPtera extends EntityFlyingMob {
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.<Integer>createKey(EntityPtera.class, DataSerializers.VARINT);
	
	public EntityPtera(World worldIn) {
		super(worldIn);
		this.setSize(1.6F, 0.8F);
	}

    @Override
    public boolean canBeSteered() {
        return false;
    }
	
	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		if(this.world.getDifficulty() == EnumDifficulty.HARD)
			this.tasks.addTask(1, new EntityAIDropRider(this));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true).setUnseenMemoryTicks(160));
	}
	
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Ptera_Attack);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Ptera_Health);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.1D);
	}
	
    protected void entityInit() {
    	super.entityInit();
        this.getDataManager().register(SKIN_TYPE, Integer.valueOf(0));
    }
	
	@Override
	public boolean shouldRiderSit() {
		return false;
	}
    
    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    public double getMountedYOffset()
    {
        if(!this.getPassengers().isEmpty())
        	return -(double)this.getPassengers().get(0).height * 0.75D;
        
        return super.getMountedYOffset();
    }
    
    public float getEyeHeight() {
    	return this.height * 0.35F;
    }
    
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void onLivingUpdate() {
    	super.onLivingUpdate();
    	
    	if(this.isBeingRidden())
    		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.16D);
    	else
    		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.1D);
    }

   /**
    * Called to update the entity's position/logic.
    */
   public void onUpdate() {
      super.onUpdate();

      if (!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL) {
         this.setDead();
      }

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
   
   public void updatePassenger(Entity passenger) {
       super.updatePassenger(passenger);

       passenger.motionX = 0;
       passenger.motionY = 0;
       passenger.motionZ = 0;
   }
   
   /**
    * Called when the entity is attacked.
    */
   public boolean attackEntityFrom(DamageSource source, float amount)
   {
       if(!this.getPassengers().isEmpty())
    	   this.removePassengers();
    	   
	   return super.attackEntityFrom(source, amount);
   }
   
   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData entityLivingData) {
	   if(BiomeDictionary.hasType(this.getEntityWorld().getBiome(this.getPosition()), Type.DRY))
		   this.setSkin(1);
	   
		if(this.world.getDifficulty() == EnumDifficulty.HARD) {
	        if (this.world.rand.nextInt(100) < 2)
	        {
	            EntityCreeper entityRider = new EntityCreeper(this.world);
	            entityRider.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
	            entityRider.onInitialSpawn(difficulty, (IEntityLivingData)null);
	            this.world.spawnEntity(entityRider);
	            entityRider.startRiding(this);	   
	        }
	        else if (this.world.rand.nextInt(100) < 5)
	        {
	            if(this.getSkin() == 0) {
		        	EntityZombie entityRider = new EntityZombie(this.world);
		            entityRider.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
		            entityRider.onInitialSpawn(difficulty, (IEntityLivingData)null);
		            this.world.spawnEntity(entityRider);
		            entityRider.startRiding(this);
	            }
	            else if(this.getSkin() == 1) {
		        	EntityHusk entityRider = new EntityHusk(this.world);
		            entityRider.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
		            entityRider.onInitialSpawn(difficulty, (IEntityLivingData)null);
		            this.world.spawnEntity(entityRider);
		            entityRider.startRiding(this);
	            }
	        }
	        else if (this.world.rand.nextInt(100) < 10 && this.getSkin() == 0)
	        {
	            EntityFoglet entityRider = new EntityFoglet(this.world);
	            entityRider.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
	            entityRider.onInitialSpawn(difficulty, (IEntityLivingData)null);
	            entityRider.setIsHanging(true);
	            this.world.spawnEntity(entityRider);
	            entityRider.startRiding(this);	   
	        }
		}

	   
	   return super.onInitialSpawn(difficulty, entityLivingData);
	}
   
   public int getSkin()
   {
       return ((Integer)this.dataManager.get(SKIN_TYPE)).intValue();
   }

   public void setSkin(int skinType)
   {
       this.dataManager.set(SKIN_TYPE, Integer.valueOf(skinType));
   }
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("Variant", getSkin());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setSkin(nbt.getInteger("Variant"));
	}
	
	public SoundCategory getSoundCategory() {
		return SoundCategory.HOSTILE;
	}

	protected SoundEvent getAmbientSound() {
		return FishItems.ENTITY_PTERA_AMBIENT;
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return FishItems.ENTITY_PTERA_HURT;
	}

	protected SoundEvent getDeathSound() {
		return FishItems.ENTITY_PTERA_DEATH;
	}

	@Nullable
	protected ResourceLocation getLootTable() {
    	switch(this.getSkin()) { 
	        case 0: 
	        	return LootTableHandler.PTERA;
	        case 1: 
	            return LootTableHandler.PTERA1;
	        default: 
	            return null; 
	    } 
	}

	/**
	* Returns the volume for the sounds this mob makes.
	*/
	protected float getSoundVolume() {
		return 0.7F;
	}
}
