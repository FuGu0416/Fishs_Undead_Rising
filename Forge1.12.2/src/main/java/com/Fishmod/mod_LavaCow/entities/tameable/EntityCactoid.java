package com.Fishmod.mod_LavaCow.entities.tameable;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.EntityCactyrant;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityCactoid extends EntityFishTameable {
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.createKey(EntityCactoid.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> GROWING_STAGE = EntityDataManager.createKey(EntityCactoid.class, DataSerializers.VARINT);
	private EntityAIWatchClosest watch;
	private EntityAILookIdle look;
	
	public EntityCactoid(World worldIn) {
        super(worldIn);
        this.setSize(1.1F, 2.2F);
    }
	
	@Override
    protected void entityInit() {
		super.entityInit();
		this.dataManager.register(SKIN_TYPE, Integer.valueOf(this.rand.nextInt(3)));
		this.dataManager.register(GROWING_STAGE, Integer.valueOf(0));
    }
	
    @Override
    protected void initEntityAI() {
        this.watch = new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F);
        this.look = new EntityAILookIdle(this);
        
    	super.initEntityAI();
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(4, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(5, new EntityAIAttackMelee(this, 1.5D, false));
        this.tasks.addTask(10, this.watch);
        this.tasks.addTask(10, this.look);

        this.applyEntityAI();
    }

    protected void applyEntityAI() {
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false));
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Cactoid_Health);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Cactoid_Attack);
    }
    
    @Override
    public boolean getCanSpawnHere() {
    	BlockPos pos = new BlockPos(this.posX, this.posY, this.posZ);
        	       
        return SpawnUtil.isAllowedDimension(this.dimension)
        		&& this.world.canSeeSky(pos) 
        		&& super.getCanSpawnHere();
    }
    
    @Override
    public void notifyDataManagerChange(DataParameter<?> key) {
        if (GROWING_STAGE.equals(key)) {
        }

        super.notifyDataManagerChange(key);
	}
    
    /**
     * Growing Stage: Normal -> Flowering-> Fruited
     */
    public int getGrowingStage() {
       return this.dataManager.get(GROWING_STAGE).intValue();
    }
    
    public void setGrowingStage(int i) {
        this.dataManager.set(GROWING_STAGE, i);
    }
    
    public int getSkin() {
        return this.dataManager.get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
        this.dataManager.set(SKIN_TYPE, Integer.valueOf(skinType));
    }
    
    @Override
    public boolean isBreedingItem(ItemStack stack) {
        if (!this.isTamed()) {
            if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
                IFluidHandlerItem fluidHandlerItem = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
                FluidStack fluidStack = fluidHandlerItem.drain(Fluid.BUCKET_VOLUME, false);
                if (fluidStack != null) {
                    if (this.getSkin() == 3 && fluidStack.getFluid() == FluidRegistry.LAVA) {
                        return true;
                    } else if (this.getSkin() != 3 && fluidStack.getFluid() == FluidRegistry.WATER) {
                        return true;
                    }
                }
            }
        } else {
        	return stack.isItemEqual(new ItemStack(Items.DYE, 1, 15));
        }
    	return false;
    }
    
    @Override
    protected void collideWithEntity(Entity entity) {
        if (entity instanceof EntityLivingBase && !(entity instanceof EntityCactoid) && !(entity instanceof EntityCactyrant) && this.getAttackTarget() == null && !this.isTamed() && !(entity.world.getDifficulty() == EnumDifficulty.PEACEFUL)) {
            entity.attackEntityFrom(DamageSource.CACTUS, 1.0F);
        }

        super.collideWithEntity(entity);
    }
    
    protected boolean isInDaylight() {
    	if (this.world.isDaytime() && !this.world.isRemote)
        {
            float f = this.getBrightness();
            BlockPos blockpos = this.getRidingEntity() instanceof EntityBoat ? (new BlockPos(this.posX, (double)Math.round(this.posY), this.posZ)).up() : new BlockPos(this.posX, (double)Math.round(this.posY), this.posZ);

            return f > 0.5F && this.world.canSeeSky(blockpos);
        }
    	
		return false;
    }
    
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void onLivingUpdate() {   	
    	if (!this.world.isRemote && !this.isTamed() && this.getAttackTarget() == null) {
    		if (this.isInDaylight()) {
    			this.doSitCommand(null);
    		} else if (this.state != EntityFishTameable.State.WANDERING) {
    			this.doFollowCommand(null);
    			this.doWanderCommand(null);
    		}
    	}
    	
    	if (!this.world.isRemote) {
	        if (this.growingAge < -12000) {
	        	if(this.getGrowingStage() != 0) {
	        		this.setGrowingStage(0);
	        	}
	        } else if (this.growingAge < 0) {
	        	if(this.getGrowingStage() != 1) {
	        		this.setGrowingStage(1);
	        		this.playSound(FishItems.ENTITY_CACTYRANT_GROW, 1.0F, 1.0F / (world.rand.nextFloat() * 0.4F + 0.8F));
	        		this.world.setEntityState(this, (byte)14);
	        	}       	
	        } else if (this.growingAge == 0) {
	        	if(this.getGrowingStage() != 2) {
	        		this.setGrowingStage(2);
	        		this.playSound(FishItems.ENTITY_CACTYRANT_GROW, 1.0F, 1.0F / (world.rand.nextFloat() * 0.4F + 0.8F));
	        		this.world.setEntityState(this, (byte)14);
	        	}       	
	        }
    	}
    	
    	super.onLivingUpdate();
    }
    
    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        if (!player.world.isRemote && this.isTamed() && this.getGrowingStage() == 2) {
    		this.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
    		this.entityDropItem(new ItemStack(FishItems.CACTUS_FRUIT), 0.0F);
    		this.setGrowingStage(0);
    		this.setGrowingAge(-24000);
    		
    		return true;
    	} else {
    		return super.processInteract(player, hand);
    	}
    }
    
    @Override
	public void doSitCommand(EntityPlayer player) {
        this.tasks.removeTask(this.watch);
        this.tasks.removeTask(this.look);
        this.setSilent(true);
    	super.doSitCommand(player);
    }
    
    @Override
	public void doFollowCommand(EntityPlayer player) {
        this.tasks.addTask(10, this.watch);
        this.tasks.addTask(10, this.look);
		this.setSilent(false);
        super.doFollowCommand(player);
    }

	@Override
    public boolean attackEntityAsMob(Entity entity) {
        boolean flag = super.attackEntityAsMob(entity);

        return flag;
    }    
	
	/**
	* Called when the entity is attacked.
	*/
    @Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
    	if (source == DamageSource.CACTUS) return false;
    	
        if (!source.isMagicDamage() && source.getImmediateSource() instanceof EntityLivingBase) {
            EntityLivingBase entity = (EntityLivingBase)source.getImmediateSource();
            
            
            if (!source.isExplosion())
            {
                entity.attackEntityFrom(DamageSource.causeThornsDamage(this), 1.0F);
            }
        }
        
    	if(source.isFireDamage()) {
    		return super.attackEntityFrom(source, 2.0F * amount);
    	}

    	return super.attackEntityFrom(source, amount);
    }

    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
    	this.setGrowingAge(-24000);
    	
    	// Nether (Basalt Deltas) Variant
        if (this.world.provider.doesWaterVaporize()) {
     	   this.setSkin(3);
     	   setFireImmunity();
        }
        
    	return super.onInitialSpawn(difficulty, livingdata);
    }	
    
    public boolean setFireImmunity() {
    	return this.isImmuneToFire = true;
    }
    
	@Override
    public float getEyeHeight() {
        return this.height * 0.34F;
    }
	
    /**
     * The speed it takes to move the entityliving's rotationPitch through the faceEntity method. This is only currently
     * use in wolves.
     */
    @Override
    public int getVerticalFaceSpeed() {
        return this.isSilent() ? 0 : super.getVerticalFaceSpeed();
    }

    @Override
    public int getHorizontalFaceSpeed() {
        return this.isSilent() ? 0 : super.getHorizontalFaceSpeed();
    }
	
    @SideOnly(Side.CLIENT)
	protected void addParticlesAroundSelf(EnumParticleTypes type) {
        for (int i = 0; i < 5; ++i) {
            double d0 = this.rand.nextGaussian() * 0.02D;
            double d1 = this.rand.nextGaussian() * 0.02D;
            double d2 = this.rand.nextGaussian() * 0.02D;
            this.world.spawnParticle(type, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + 0.5D + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d0, d1, d2);
        }
	}
	
    /**
     * Handler for {@link World#setEntityState}
     */
	@Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
		if (id == 14) {
            this.addParticlesAroundSelf(EnumParticleTypes.WATER_WAKE);
        } else {
            super.handleStatusUpdate(id);
        }
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return FishItems.ENTITY_LILSLUDGE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
    	return SoundEvents.BLOCK_CLOTH_BREAK;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FishItems.ENTITY_CACTYRANT_DEATH;
    }

	@Override
    protected void playStepSound(BlockPos pos, Block block) {
	    this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15F, 1.0F);
	}
	
    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableHandler.CACTOID;
    }
    
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
    	super.readEntityFromNBT(compound);
    	this.setSkin(compound.getInteger("Variant"));
    	this.setGrowingStage(compound.getInteger("GrowingStage"));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("Variant", getSkin());
        compound.setInteger("GrowingStage", this.getGrowingStage());
    }
	
    /**
     * Called when the mob's health reaches 0.
     */
    @Override
    public void onDeath(DamageSource cause) {
       super.onDeath(cause);
       
       if(!this.world.isRemote && this.getGrowingStage() == 2) {			
			this.entityDropItem(new ItemStack(FishItems.CACTUS_FRUIT), 0.0F);
       }
    }
    
    @Override
    public boolean canDropLoot() {
    	return this.isTamed() || !(this.getOwner() instanceof EntityPlayer);
    }
}
