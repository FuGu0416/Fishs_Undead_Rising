package com.Fishmod.mod_LavaCow.entities;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityCactusThorn;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityCactoid;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityCactyrant extends EntityMob implements IAggressive {	
	private static final DataParameter<Boolean> DATA_IS_CAMOUFLAGING = EntityDataManager.createKey(EntityCactyrant.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.createKey(EntityCactoid.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> GROWING_STAGE = EntityDataManager.createKey(EntityCactyrant.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> HUGGING_CD = EntityDataManager.createKey(EntityCactyrant.class, DataSerializers.VARINT);
	private int attackTimer;
	protected int spellTicks;
	private EntityAIWanderAvoidWater move;
	private EntityAIWatchClosest watch;
	private EntityAILookIdle look;
	
	public EntityCactyrant(World world) {
        super(world);
        this.setSize(1.3F, 2.8F);
        this.experienceValue = 12;
    }
	
    @Override
    protected void initEntityAI() {
        this.move = new EntityAIWanderAvoidWater(this, 1.0D);
        this.watch = new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F);
        this.look = new EntityAILookIdle(this);
        
        this.tasks.addTask(1, new AICastingSpell());
        this.tasks.addTask(2, new EntityCactyrant.AIUseSpell());
        this.tasks.addTask(3, new EntityAIAttackMelee(this, 1.25D, false));
        this.tasks.addTask(6, this.move);
        this.tasks.addTask(8, this.watch);
        this.tasks.addTask(8, this.look);
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true) {
            public boolean shouldExecute() {
            	super.shouldExecute();
				return !this.taskOwner.isSilent();
            }
        });
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.19D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Cactyrant_Health);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Cactyrant_Attack);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    }
    
	@Override
    protected void entityInit() {
		super.entityInit();
		this.getDataManager().register(DATA_IS_CAMOUFLAGING, false);
		this.getDataManager().register(SKIN_TYPE, Integer.valueOf(this.rand.nextInt(2)));
		this.getDataManager().register(GROWING_STAGE, Integer.valueOf(0));
		this.getDataManager().register(HUGGING_CD, Integer.valueOf(0));
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
    
    public boolean isCamouflaging() {
        return this.dataManager.get(DATA_IS_CAMOUFLAGING);
     }

     public void setCamouflaging(boolean i) {
        this.dataManager.set(DATA_IS_CAMOUFLAGING, i);
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
    
    public int getHuggingCooldown() {
        return this.dataManager.get(HUGGING_CD).intValue();
     }
     
     public void setHuggingCooldown(int i) {
         this.dataManager.set(HUGGING_CD, i);
     }
     
    public boolean isSpellcasting() {
    	return this.spellTicks > 0;
    }
    
    public int getSpellTicks() {
        return this.spellTicks;
    }
    
    @Override
    protected void collideWithEntity(Entity entity) {
        if (entity instanceof EntityLivingBase && !(entity instanceof EntityCactoid) && !(entity instanceof EntityCactyrant) && this.getAttackTarget() == null && !(entity.world.getDifficulty() == EnumDifficulty.PEACEFUL)) {
            entity.attackEntityFrom(DamageSource.CACTUS, 2.0F);
        }

        super.collideWithEntity(entity);
    }
    
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void onUpdate() {
        super.onUpdate();
    	EntityLivingBase target = this.getAttackTarget();
    	
        if (this.attackTimer > 0) {
            --this.attackTimer;
        }
        
        if (this.spellTicks > 0) {
            --this.spellTicks;
        }
        
        if (this.getHuggingCooldown() > 0) {
        	this.setHuggingCooldown(this.getHuggingCooldown() - 1);
        }
 
        if(!this.world.isRemote) {
	        if (this.world.isDaytime() && this.getAttackTarget() == null) {
	        	if(!this.isCamouflaging()) {
		            this.tasks.removeTask(this.move);
		            this.tasks.removeTask(this.watch);
		            this.tasks.removeTask(this.look);
		            this.setSilent(true);
		            this.setCamouflaging(true); 
	        	}
	        } else if (this.isCamouflaging()) {
	            this.tasks.addTask(6, this.move);
	            this.tasks.addTask(8, this.watch);
	            this.tasks.addTask(8, this.look);
	            this.setSilent(false);
	            this.setCamouflaging(false);
	        }
	        
	        if(this.getGrowingStage() == 2) {
	        	// Full grown
	        } else if(this.ticksExisted > 20 * 60 * 20) {
	        	if(this.getGrowingStage() != 2) {
	        		this.setGrowingStage(2);
	        		this.playSound(FishItems.ENTITY_CACTYRANT_GROW, 1.0F, 1.0F / (world.rand.nextFloat() * 0.4F + 0.8F));
	        		this.world.setEntityState(this, (byte)14);
	        	}
	        } else if(this.ticksExisted > 10 * 60 * 20) {
	        	if(this.getGrowingStage() != 1) {
	        		this.setGrowingStage(1);
	        		this.playSound(FishItems.ENTITY_CACTYRANT_GROW, 1.0F, 1.0F / (world.rand.nextFloat() * 0.4F + 0.8F));
	        		this.world.setEntityState(this, (byte)14);
	        	}
	        }
        }
  	   	        
        if (target != null && this.getDistanceSq(target) < 4.0D && this.getAttackTimer() == 5 && this.deathTime <= 0 && this.canEntityBeSeen(target)) {       		        	       		            
            if (!this.getAttackTarget().isActiveItemStackBlocking()) {
                if (!this.isBeingRidden() && !this.getAttackTarget().isSneaking() && this.getHuggingCooldown() == 0) {
                    this.getAttackTarget().startRiding(this, true);
                    this.setHuggingCooldown(120);
                } else if (!this.isBeingRidden()) {
                	target.attackEntityFrom(DamageSource.causeMobDamage(this), (float) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
                	this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0F, 1.0F);
                } else {
                	target.attackEntityFrom(DamageSource.causeMobDamage(this), (float) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * 0.25F);
                }
            }
        }
    }
	
    @Override
    public boolean canRiderInteract() {
        return true;
    }

    @Override
    public boolean shouldRiderSit() {
        return false;
    }
    
    @Override
    public void updatePassenger(Entity passenger) {
        if (this.isPassenger(passenger)) {
            float r = 0.4F;
            float angle = (float) ((Math.PI / 180.0F) * this.renderYawOffset);
            double offX = r * Math.sin((float) (Math.PI + angle));
            double offZ = r * Math.cos(angle);
            passenger.setPosition(this.posX + offX, this.posY + 0.8F, this.posZ + offZ);
        }
    }
	
	@Override
    public boolean attackEntityAsMob(Entity entityIn) {
        this.attackTimer = 15;
        this.world.setEntityState(this, (byte)4);
        return true;
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
                entity.attackEntityFrom(DamageSource.causeThornsDamage(this), 2.0F);
            }
        }
        
    	if(source.isFireDamage()) {
    		return super.attackEntityFrom(source, 2.0F * amount);
    	}

    	return super.attackEntityFrom(source, amount);
    }
	
	@Override
    public float getEyeHeight() {
        return this.height * 0.85F;
    }
	
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
    	super.readEntityFromNBT(compound);
    	this.spellTicks = (compound.getInteger("SpellTicks"));
    	this.setSkin(compound.getInteger("Variant"));
    	this.setGrowingStage(compound.getInteger("GrowingStage"));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("SpellTicks", this.spellTicks);
        compound.setInteger("Variant", getSkin());
        compound.setInteger("GrowingStage", this.getGrowingStage());
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Cactyrant_Health);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Cactyrant_Attack);
    	this.setHealth(this.getMaxHealth());
    	
    	// Nether Variant
        if (this.world.provider.isNether()) {
     	   this.setSkin(2);
     	   setFireImmunity();
        }
        
    	return super.onInitialSpawn(difficulty, livingdata);
    }
    
    public boolean setFireImmunity() {
    	return this.isImmuneToFire = true;
    }

    public int getAttackTimer() {
        return this.attackTimer;
    }

	@Override
	public void setAttackTimer(int i) {
		this.attackTimer = i;
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
    	if (id == 4) {
            this.attackTimer = 15;
        } else if (id == 10) {
        	this.spellTicks = 20;
        } else if (id == 14) {
            this.addParticlesAroundSelf(EnumParticleTypes.WATER_WAKE);
        } else {
            super.handleStatusUpdate(id);
        }
    }
	
	@Override
	public boolean isOnSameTeam(Entity entity) {
        if (entity == null) {
            return false;
        }
        else if (entity == this) {
            return true;
        }
        else if (super.isOnSameTeam(entity)) {
            return true;
        }
        else if (entity instanceof EntityCactyrant)
        {
            return this.getTeam() == null && entity.getTeam() == null;
        }
        else {
            return false;
        }
    }
    
    public class AICastingSpell extends EntityAIBase {
        public AICastingSpell() {
        	this.setMutexBits(3);
        }
        
        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            return EntityCactyrant.this.getSpellTicks() > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            super.startExecuting();
            EntityCactyrant.this.getNavigator().clearPath();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask() {
            super.resetTask();
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask() {
            if (EntityCactyrant.this.getAttackTarget() != null) {
            	EntityCactyrant.this.getLookHelper().setLookPositionWithEntity(EntityCactyrant.this.getAttackTarget(), (float)EntityCactyrant.this.getHorizontalFaceSpeed(), (float)EntityCactyrant.this.getVerticalFaceSpeed());
            }
        }
    }
    
    public class AIUseSpell extends EntityAIBase {
        protected int spellWarmup;
        protected int spellCooldown;

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
    		if (EntityCactyrant.this.getAttackTarget() == null || EntityCactyrant.this.isBeingRidden())
                return false;
            else if (EntityCactyrant.this.isSpellcasting() || !EntityCactyrant.this.canEntityBeSeen(EntityCactyrant.this.getAttackTarget()))
                return false;
            else {                
            	return EntityCactyrant.this.ticksExisted >= this.spellCooldown && EntityCactyrant.this.getDistance(EntityCactyrant.this.getAttackTarget()) > 2.0D;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            return EntityCactyrant.this.getAttackTarget() != null && this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.spellWarmup = this.getCastWarmupTime();
            EntityCactyrant.this.spellTicks = this.getCastingTime();
            this.spellCooldown = EntityCactyrant.this.ticksExisted + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();
            EntityCactyrant.this.world.setEntityState(EntityCactyrant.this, (byte)10);
            if (soundevent != null) {
            	EntityCactyrant.this.playSound(soundevent, 1.0F, 1.0F);
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask() {
            --this.spellWarmup;

            if (this.spellWarmup == 0) {
                this.castSpell();
            }
        }

        protected void castSpell() {
        	double d0, d1, d2, d3, f;
        	for(int i = 0 ; i < 6 ; i++) {
	        	EntityCactusThorn abstractarrowentity = new EntityCactusThorn(EntityCactyrant.this.world, EntityCactyrant.this);
	        	EntityLivingBase target = EntityCactyrant.this.getAttackTarget();
	            d0 = target.posX - EntityCactyrant.this.posX;
	            d1 = target.posY + (height * 0.3333333333333333D) - abstractarrowentity.posY;
	            d2 = target.posZ - EntityCactyrant.this.posZ;
	            d3 = (double)MathHelper.sqrt(d0 * d0 + d2 * d2);
	            f = i == 3 ? 0 : MathHelper.sqrt(MathHelper.sqrt(d3)) * 2.0D;
	            abstractarrowentity.shoot(d0 + EntityCactyrant.this.rand.nextGaussian() * f, d1 + d3 * 0.2D, d2 + EntityCactyrant.this.rand.nextGaussian() * f, 1.6F, (float)(14 - EntityCactyrant.this.world.getDifficulty().getDifficultyId() * 4));            
	            EntityCactyrant.this.world.spawnEntity(abstractarrowentity);
        	}
        	EntityCactyrant.this.playSound(FishItems.RANDOM_THORN_SHOOT, 1.0F, 1.0F / (EntityCactyrant.this.rand.nextFloat() * 0.4F + 0.8F));
        }

        protected int getCastWarmupTime() {
            return 10;
        }

        protected int getCastingTime() {
            return 20;
        }

        protected int getCastingInterval() {
            return Modconfig.Cactyrant_Ability_Cooldown * 20;
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP;
        }
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return FishItems.ENTITY_CACTYRANT_AMBIENT;
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
	    this.playSound(SoundEvents.BLOCK_GRASS_STEP, 0.15F, 1.0F);
	}
	
    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableHandler.CACTYRANT;
    }
    
    @Override
    public void onDeath(DamageSource cause) {
       super.onDeath(cause);
       
       int looting = ForgeHooks.getLootingLevel(this, cause.getTrueSource(), cause);
       int chance = this.rand.nextInt(5) + this.rand.nextInt(1 + looting);
       if(!this.world.isRemote && this.getGrowingStage() == 2) {	
    	   for (int amount = 0; amount <= chance; ++amount)
			this.entityDropItem(new ItemStack(FishItems.CACTUS_FRUIT), 0.0F);
       }
    }
    
    @Override
    public boolean canDropLoot() {
    	return this.isBurning() || this.attackingPlayer != null;
    }

	@Override
	public boolean isAggressive() {
		return false;
	}
}
