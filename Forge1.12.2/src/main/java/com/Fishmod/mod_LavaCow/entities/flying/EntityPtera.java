package com.Fishmod.mod_LavaCow.entities.flying;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.entities.EntityFoglet;
import com.Fishmod.mod_LavaCow.entities.ai.EntityAIDropRider;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntityPtera extends EntityFlyingMob {
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.<Integer>createKey(EntityPtera.class, DataSerializers.VARINT);
	
	public EntityPtera(World worldIn) {
		super(worldIn, Modconfig.Ptera_FlyingHeight_limit);
		this.setSize(1.6F, 0.8F);
	}

    @Override
    public boolean canBeSteered() {
        return false;
    }
	
	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		this.tasks.addTask(1, new EntityAIDropRider(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true).setUnseenMemoryTicks(160));
	}
	
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.1D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Ptera_Health);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Ptera_Attack);
		this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.1D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
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
	@Override
    public double getMountedYOffset() {
        if(!this.getPassengers().isEmpty())
        	return -(double)this.getPassengers().get(0).height * 0.75D;
        
        return super.getMountedYOffset();
    }
    
    public float getEyeHeight() {
    	return this.height * 0.35F;
    }
   
   /**
    * For vehicles, the first passenger is generally considered the controller and "drives" the vehicle. For example,
    * Pigs, Horses, and Boats are generally "steered" by the controlling passenger.
    */
   @Nullable
   @Override
   public Entity getControllingPassenger() {
       return null;
   }
   
   /**
    * Called when the entity is attacked.
    */
   @Override
   public boolean attackEntityFrom(DamageSource source, float amount) {
       if(!this.getPassengers().isEmpty())
    	   this.removePassengers();
    	   
	   return super.attackEntityFrom(source, amount);
   }
   
   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData entityLivingData) {
	   if(BiomeDictionary.hasType(this.getEntityWorld().getBiome(this.getPosition()), Type.SAVANNA))
		   this.setSkin(2);
	   else if(BiomeDictionary.hasType(this.getEntityWorld().getBiome(this.getPosition()), Type.SWAMP))
		   this.setSkin(3);
	   else if(BiomeDictionary.hasType(this.getEntityWorld().getBiome(this.getPosition()), Type.DRY))
		   this.setSkin(1);
				
		if (!this.isRiding() && this.world.rand.nextDouble() <= Modconfig.Ptera_Ability_Chance) {
			Biome.SpawnListEntry Result = ((Biome.SpawnListEntry) WeightedRandom.getRandomItem(this.rand, LootTableHandler.PTERA_PASSENGERLIST));
			Entity entityRider = EntityRegistry.getEntry(Result.entityClass).newInstance(world);
			
			   if(entityRider instanceof EntityLiving) {
				   entityRider.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
				   this.world.spawnEntity(entityRider);
				   entityRider.startRiding(this);
				   ((EntityLiving) entityRider).onInitialSpawn(difficulty, (IEntityLivingData)null);
					
				   // Foglet hanging does not seem to work with Pteras at the moment
					if(entityRider instanceof EntityFoglet) {
						((EntityFoglet) entityRider).setIsHanging(true);
					}
			   }
		}

	   
	   return super.onInitialSpawn(difficulty, entityLivingData);
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
		nbt.setInteger("Variant", getSkin());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setSkin(nbt.getInteger("Variant"));
	}
	
	@Override
	public SoundCategory getSoundCategory() {
		return SoundCategory.HOSTILE;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return FishItems.ENTITY_PTERA_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return FishItems.ENTITY_PTERA_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return FishItems.ENTITY_PTERA_DEATH;
	}

	@Nullable
	protected ResourceLocation getLootTable() {
    	switch(this.getSkin()) {     	        	
	        case 1: 
	            return LootTableHandler.PTERA1;
	        case 2: 
	            return LootTableHandler.PTERA2;
	        case 3: 
	            return LootTableHandler.PTERA3;
	        case 0: 
	        default: 
	        	return LootTableHandler.PTERA;
	    } 
	}

	/**
	* Returns the volume for the sounds this mob makes.
	*/
	@Override
	protected float getSoundVolume() {
		return 0.7F;
	}
}
