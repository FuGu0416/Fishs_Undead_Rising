package com.Fishmod.mod_LavaCow.entities.flying;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.entities.EntityParasite;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.init.ModMobEffects;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFollowOwnerFlying;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityVespa extends EntityRideableFlyingMob {
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.<Integer>createKey(EntityVespa.class, DataSerializers.VARINT);
	
	public EntityVespa(World worldIn) {
		super(worldIn, Modconfig.Vespa_FlyingHeight_limit);
		this.setSize(1.6F, 1.0F);
	}
	
	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true).setUnseenMemoryTicks(160));
	}
	
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(6.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.1D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Vespa_Health);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Vespa_Attack);
		this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.1D);
	}
	
    protected void entityInit() {
    	super.entityInit();
        this.getDataManager().register(SKIN_TYPE, Integer.valueOf(0));
    }
    
    @Override
    public void setTamed(boolean tamed) {
    	super.setTamed(tamed);
    	
        if (tamed) {
        	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Vespa_Health * 2.0D);
        	this.setHealth(this.getHealth() * 2.0F);
        } else {
        	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Vespa_Health);
        	this.setHealth(this.getHealth());
        }
    }
    
    @Override
    protected EntityAIBase wanderGoal() {
        return new EntityFlyingMob.AIRandomFly(this);
    }

    @Override
    protected EntityAIBase followGoal() {
        return new EntityAIFollowOwnerFlying(this, 1.0D, 10.0F, 4.0F);
    }
    
    @Override
    public boolean isBreedingItem(ItemStack stack) {
    	return false;
    }
    
    @Override
    public float getEyeHeight() {
    	return this.height * 0.45F;
    }
    
    @Override
    public void onUpdate() {
    	super.onUpdate();
    	
    	if(!this.onGround && this.ticksExisted % 20 == 0) {
    		this.playSound(this.getFlyingSound(), 1.0F, 1.0F);
    	}
    }
    
    @Override
    protected void onGrowingAdult() { 	
    	if(this.isChild()) {
	        if(this.canBeSteered()) {
	        	this.setSaddled(false);
	        	
	            if (!this.world.isRemote) {
	            	this.dropItem(Items.SADDLE, 1);
	            }
	        }
	        
            if (!this.world.isRemote) {  
	        	EntityParasite larva = new EntityParasite(this.world);
	        	larva.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
	        	larva.setSkin(2);
	        	
	    		if (this.isTamed() && this.getOwner() instanceof EntityPlayer) {
	    			//larva.setTamedBy((EntityPlayer) this.getOwner());
	    			larva.setCustomNameTag(this.getCustomNameTag());
	    		}
	    		
	    		this.world.spawnEntity(larva);
    		}
            
            this.setDead();
    	}
    }
   
   public boolean attackEntityAsMob(Entity par1Entity) {
       if (super.attackEntityAsMob(par1Entity)) {
           if (par1Entity instanceof EntityLivingBase) {
           		float local_difficulty = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();

           		((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(MobEffects.POISON, 6 * 20 * (int)local_difficulty, 0));
           		
           		if(rand.nextInt(5) == 0 && Modconfig.Vespa_Spread_Parasites) {
           			((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(ModMobEffects.INFESTED, 6 * 20 * (int)local_difficulty, 0));
           		}
           }

           return true;
       }
       else {
           return false;
       }
   }
   
   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData entityLivingData) {
	   if (this.world.rand.nextDouble() <= Modconfig.Vespa_Hornet_Variant) {
		   this.setSkin(1);
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
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setInteger("Variant", getSkin());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		setSkin(compound.getInteger("Variant"));
	}
	
	@Override
    public int getTalkInterval() {
        return 1000;
    }
	
	public SoundCategory getSoundCategory() {
		return SoundCategory.HOSTILE;
	}

	protected SoundEvent getAmbientSound() {
		return FishItems.ENTITY_VESPA_AMBIENT;
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return FishItems.ENTITY_VESPA_HURT;
	}

	protected SoundEvent getDeathSound() {
		return FishItems.ENTITY_VESPA_DEATH;
	}
	
	protected SoundEvent getFlyingSound() {
		return FishItems.ENTITY_VESPA_FLYING;
	}
	
    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }

	@Nullable
	protected ResourceLocation getLootTable() {
		return LootTableHandler.VESPA;
	}

	/**
	* Returns the volume for the sounds this mob makes.
	*/
	protected float getSoundVolume() {
		return 0.7F;
	}
}
