package com.Fishmod.mod_LavaCow.entities.flying;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityGhostRay extends EntityFlyingMob {
	
	public static final float[] SIZE = {1.0F, 1.4F, 1.8F, 2.2F};
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.<Integer>createKey(EntityGhostRay.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> SIZE_VARIANT = EntityDataManager.<Integer>createKey(EntityGhostRay.class, DataSerializers.VARINT);
	
	public EntityGhostRay(World worldIn) {
		super(worldIn);
		//this.setSize(3.2F, 0.5F);
	}
	
	@Override
	protected void initEntityAI() {
		super.initEntityAI();
	}
	
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.GhostRay_Health);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.03D);
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
	
	public boolean getCanSpawnHere() {
		return this.dimension == DimensionType.OVERWORLD.getId()
				&& this.world.canSeeSky(new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ))
				&& super.getCanSpawnHere();
	}
	
    protected void entityInit() {
    	super.entityInit();
        this.getDataManager().register(SKIN_TYPE, Integer.valueOf(0));
        this.getDataManager().register(SIZE_VARIANT, Integer.valueOf(this.rand.nextInt(EntityGhostRay.SIZE.length)));
        this.setSize(1.6F * EntityGhostRay.SIZE[this.getSize()], 0.25F * EntityGhostRay.SIZE[this.getSize()]);
    }
    
    public float getEyeHeight() {
    	return this.height * 0.7F;
    }

   /**
    * Called to update the entity's position/logic.
    */
   public void onUpdate() {
      super.onUpdate();

      /*if (!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL) {
         this.setDead();
      }*/

   }
   
   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData entityLivingData) {
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
   
   public int getSize()
   {
       return ((Integer)this.dataManager.get(SIZE_VARIANT)).intValue();
   }

   public void setSize(int size)
   {
       this.dataManager.set(SIZE_VARIANT, Integer.valueOf(size));
   }
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("Variant", this.getSkin());
		nbt.setInteger("Size", this.getSize());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		this.setSkin(nbt.getInteger("Variant"));
		this.setSize(nbt.getInteger("Size"));
	}
	
	@Override
	public void playLivingSound() {
		if(this.rand.nextDouble() < 0.33D)
			super.playLivingSound();
	}
	
	public SoundCategory getSoundCategory() {
		return SoundCategory.NEUTRAL;//.HOSTILE;
	}

	protected SoundEvent getAmbientSound() {
		return FishItems.ENTITY_GHOSTRAY_AMBIENT;
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return FishItems.ENTITY_BANSHEE_HURT;
	}

	protected SoundEvent getDeathSound() {
		return FishItems.ENTITY_GHOSTRAY_DEATH;
	}

	@Nullable
	protected ResourceLocation getLootTable() {
		return LootTableHandler.GHOSTRAY;
	}

	/**
	* Returns the volume for the sounds this mob makes.
	*/
	protected float getSoundVolume() {
		return 3.0F;
	}
}
