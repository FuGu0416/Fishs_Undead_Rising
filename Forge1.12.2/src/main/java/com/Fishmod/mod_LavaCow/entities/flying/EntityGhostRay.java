package com.Fishmod.mod_LavaCow.entities.flying;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityGhostRay extends EntityFlyingMob {
	
	public static final float[] SIZE = {1.0F, 1.4F, 1.8F, 2.2F};
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.<Integer>createKey(EntityGhostRay.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> SIZE_VARIANT = EntityDataManager.<Integer>createKey(EntityGhostRay.class, DataSerializers.VARINT);
	
	public EntityGhostRay(World worldIn) {
		super(worldIn, Modconfig.GhostRay_FlyingHeight_limit);
		//this.setSize(3.2F, 0.5F);
	}
	
	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		this.tasks.addTask(1, new EntityAIPanic(this, 1.0D));
	}
	
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.GhostRay_Health);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.03D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0D);
		this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.03D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(5.0D);
	}
	
	@Override
    public boolean getCanSpawnHere() {
    	// Middle end island check
    	if (this.world.provider.getDimension() == 1) {
            return !Modconfig.GhostRay_Middle_End_Island ? this.posX > 500 || this.posX < -500 || this.posZ > 500 || this.posZ < -500 : super.getCanSpawnHere();
    	}
    	
        return super.getCanSpawnHere();
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
	
    protected void entityInit() {
    	super.entityInit();
        this.getDataManager().register(SKIN_TYPE, Integer.valueOf(0));
        this.getDataManager().register(SIZE_VARIANT, Integer.valueOf(this.rand.nextInt(EntityGhostRay.SIZE.length)));
        this.setSize(1.6F * EntityGhostRay.SIZE[this.getSize()], 0.25F * EntityGhostRay.SIZE[this.getSize()]);
    }
    
    /**
	 * Will return how many at most can spawn in a chunk at once.
	*/
	@Override
	public int getMaxSpawnedInChunk() {
		return 1;
	}
    
    public float getEyeHeight() {
    	return this.height * 0.7F;
    }
   
   /**
    * Called when the entity is attacked.
    */
   public boolean attackEntityFrom(DamageSource source, float amount) {
       if(source.getImmediateSource() != null && source.getImmediateSource() instanceof EntityLivingBase && Modconfig.GhostRay_Ghostly_Touch && !source.isCreativePlayer())
    	   ((EntityLivingBase)source.getImmediateSource()).addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 6 * 20, 2));
       
	   return super.attackEntityFrom(source, amount);
   }
   
   @Override
   public void onLivingUpdate() {
       if(this.ticksExisted % 2 == 0 && this.getEntityWorld().isRemote) {
           this.world.spawnParticle(EnumParticleTypes.TOWN_AURA, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
       }
       
       super.onLivingUpdate();
   }
   
   @Override
   public void onEntityUpdate() {
   	// Proper check to make sure that they're always immune to fire
   	if (this.getSkin() == 1) {
   		this.isImmuneToFire = true;
   	}
   	
   	super.onEntityUpdate();
   }
   
   @Nullable
   @Override
   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
       this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.GhostRay_Health);
       this.setHealth(this.getMaxHealth());
   	
   		// Nether (Soul Sand Valley) Variant
       if (this.world.provider.doesWaterVaporize()) {
    	   this.setSkin(1);
       }
       
       // End Variant
       if (this.world.provider.getDimension() == 1) {
    	   this.setSkin(2);
       }
       
   	return super.onInitialSpawn(difficulty, livingdata);
   }
   
   public int getSkin()
   {
       return this.dataManager.get(SKIN_TYPE).intValue();
   }

   public void setSkin(int skinType)
   {
       this.dataManager.set(SKIN_TYPE, Integer.valueOf(skinType));
   }
   
   public int getSize()
   {
       return ((Integer)this.dataManager.get(SIZE_VARIANT)).intValue();
   }

   public void setSize(int size)
   {
       this.dataManager.set(SIZE_VARIANT, Integer.valueOf(size));
   }
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setInteger("Variant", this.getSkin());
		compound.setInteger("Size", this.getSize());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.setSkin(compound.getInteger("Variant"));
		this.setSize(compound.getInteger("Size"));
	}
	
	public SoundCategory getSoundCategory() {
		return SoundCategory.NEUTRAL;
	}

	protected SoundEvent getAmbientSound() {
		return FishItems.ENTITY_GHOSTRAY_AMBIENT;
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return FishItems.ENTITY_GHOSTRAY_HURT;
	}

	protected SoundEvent getDeathSound() {
		return FishItems.ENTITY_GHOSTRAY_DEATH;
	}

	@Nullable
	protected ResourceLocation getLootTable() {
		return LootTableHandler.GHOSTRAY;
	}
	
    public int getTalkInterval() {
        return 1000;
    }
    
	/**
	* Returns the volume for the sounds this mob makes.
	*/
	protected float getSoundVolume() {
		return 2.0F;
	}
}
