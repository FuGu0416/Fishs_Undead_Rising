package com.Fishmod.mod_LavaCow.entities;

import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.init.ModMobEffects;
import com.Fishmod.mod_LavaCow.init.ModPotions;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityFoglet extends EntityMob implements IAggressive {
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.<Integer>createKey(EntityFoglet.class, DataSerializers.VARINT);
	private static final DataParameter<Byte> CLIMBING = EntityDataManager.<Byte>createKey(EntityFoglet.class, DataSerializers.BYTE);
	private static final DataParameter<Byte> HANGING = EntityDataManager.<Byte>createKey(EntityFoglet.class, DataSerializers.BYTE);
	private static final DataParameter<Byte> CASTING = EntityDataManager.<Byte>createKey(EntityFoglet.class, DataSerializers.BYTE);
	private boolean isAggressive = false;
	private int attackTimer = 0;
	protected int spellTicks;
	protected boolean daytimeBurning;
	protected boolean isClimber;
	
	public EntityFoglet(World worldIn) {
        super(worldIn);
        this.setSize(0.6F, 1.5F);
        this.daytimeBurning = true;
        this.isClimber = false;
    }
	
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new AICastingApell());
        this.tasks.addTask(3, new EntityFoglet.AIUseSpell());
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, false));
        if(!Modconfig.SunScreen_Mode && this.daytimeBurning()) this.tasks.addTask(5, new EntityAIFleeSun(this, 1.0D));
        this.tasks.addTask(6, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
    	this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
    	this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<EntityVillager>(this, EntityVillager.class, true));
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<EntityIronGolem>(this, EntityIronGolem.class, true));
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Foglet_Health);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Foglet_Attack);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
    }
    
    @Override
	public boolean getCanSpawnHere() {
		return SpawnUtil.isAllowedDimension(this.dimension) && super.getCanSpawnHere();
	}
    
    protected void entityInit() {
    	super.entityInit();
        this.getDataManager().register(SKIN_TYPE, Integer.valueOf(0));
        this.dataManager.register(CLIMBING, Byte.valueOf((byte)0));
        this.dataManager.register(HANGING, Byte.valueOf((byte)0));
        this.dataManager.register(CASTING, Byte.valueOf((byte)0));
    }
    
    public boolean isSpellcasting() {
    	return (((Byte)this.dataManager.get(CASTING)).byteValue() & 1) != 0;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean isSpellcastingC() {
    	return (((Byte)this.dataManager.get(CASTING)).byteValue() & 1) != 0;
    }
    
    protected int getSpellTicks() {
        return this.spellTicks;
    }
    
    public boolean daytimeBurning() {
        return daytimeBurning;
    }
    
    public boolean isClimber() {
        return isClimber;
    }
    
    public double getYOffset() {
    	return -0.25D;
    }
	
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        
        if(this.getIsHanging()) {
	        this.motionX = 0.0D;
	        this.motionY = 0.0D;
	        this.motionZ = 0.0D;

	        if(!this.isRiding()) {
		        if(this.world.canSeeSky(new BlockPos(this.posX, this.posY, this.posZ))) {
		        	this.setIsHanging(false);
		        	this.setSilent(false);
		        }
		        
		        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(2.0D, 35.0D, 2.0D));
		        
	        	for (Entity entity1 : list) {
	        		if (entity1.posY < this.posY && ((entity1 instanceof EntityPlayer && !((EntityPlayer)entity1).isCreative()) || entity1 instanceof EntityVillager)) {
	        			this.setRevengeTarget((EntityLivingBase) entity1);
	        			this.setIsHanging(false);
	        			this.setSilent(false);
	        			break;
	        		}
	        	}  
	        }
    	}      
    }
	
    @Override
    protected SoundEvent getFallSound(int heightIn) {
    	if(this.getRevengeTarget() != null || this.isClimber()) {
    		return null;
    	}
    	else {
    		return super.getFallSound(heightIn);
    	}
    }
	
    @Override
    public void fall(float distance, float damageMultiplier) {
    	if(this.getRevengeTarget() != null || this.isClimber()) {
    		super.fall(distance, 0.0F);
    	}
    	
    	else {
    		super.fall(distance, damageMultiplier);
    	}
    }
    
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void onUpdate() {
        if (this.spellTicks > 0) {
            --this.spellTicks;
        }
        
        if (this.getAttackTimer() > 0) {
            --this.attackTimer;
        }
    	
    	if (!Modconfig.SunScreen_Mode && this.world.isDaytime() && !this.world.isRemote && this.daytimeBurning()) {
        		float f = this.getBrightness();
        		
        		if (f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.world.canSeeSky(new BlockPos(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ))) this.setFire(8);
        	}
    	
        super.onUpdate();
    }
    
    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount) {
    	if (super.attackEntityFrom(source, amount)) {
    		EntityLivingBase LivingEntity = this.getAttackTarget();
        	
            if (LivingEntity == null && source.getTrueSource() instanceof EntityLivingBase) {
                LivingEntity = (EntityLivingBase)source.getTrueSource();
            }
            
            if(this.getIsClimbing()) {
            	this.setIsClimbing(false);
            }
            
            if(this.getIsHanging()) {
            	EntityFoglet.this.setIsHanging(false);
            	EntityFoglet.this.setSilent(false);
            }

            return true;
        } else {
            return false;
        }
    }

    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = super.attackEntityAsMob(entityIn);

        if (flag) {
            float f = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();

            if (this.getHeldItemMainhand().isEmpty() && this.isBurning() && this.rand.nextFloat() < f * 0.3F) {
                entityIn.setFire(2 * (int)f);
            }
        }

        return flag;
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData entityLivingData) {
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Foglet_Health);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Foglet_Attack);
    	this.setHealth(this.getMaxHealth());
    	
    	if(this.isClimber()) {
  		   this.tasks.addTask(1, new AIClimbimgTree());
    	}
    	
    	return super.onInitialSpawn(difficulty, entityLivingData);
 	}
    
    protected void updateAITasks() {
        super.updateAITasks();
        
        if(this.getAttackTarget() != null) {       		
        		isAggressive = true;
        		this.world.setEntityState(this, (byte)11);
        	}
        else  {
        		isAggressive = false;
        		this.world.setEntityState(this, (byte)34);
        	}
    }
    
    public int getSkin() {
        return this.dataManager.get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
        this.dataManager.set(SKIN_TYPE, Integer.valueOf(skinType));
    }
    
    @Override
    public boolean isAggressive() {
    	return isAggressive;
    }
    
    @Override
	public int getAttackTimer() {
		return this.attackTimer;
	}
    
	@Override
	public void setAttackTimer(int i) {
		this.attackTimer = i;
	}
    
    /**
     * Handler for {@link World#setEntityState}
     */
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
		switch(id) {
		case 10:
			this.spellTicks = 60;
			break;
		case 11:
			this.isAggressive = true;
			break;
		case 34:
			this.isAggressive = false;
			break;
		default:
			super.handleStatusUpdate(id);
			break;
		}
    }

    public float getEyeHeight() {
        if(getIsHanging())
        	return this.height * 0.0F;
        else
        	return this.height * 0.8F;
    }
    
    @Override
    public boolean isMovementBlocked() {
    	if (this.getIsHanging()) {
    		return true;
    	}
    	
    	return super.isMovementBlocked();
    }
    
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.spellTicks = compound.getInteger("SpellTicks");
        this.setSkin(compound.getInteger("Variant"));
        this.dataManager.set(HANGING, Byte.valueOf(compound.getByte("Hanging")));
        this.dataManager.set(CLIMBING, Byte.valueOf(compound.getByte("Climbing")));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("SpellTicks", this.spellTicks);
        compound.setInteger("Variant", getSkin());
        compound.setByte("Hanging", ((Byte)this.dataManager.get(HANGING)).byteValue());
        compound.setByte("Climbing", ((Byte)this.dataManager.get(CLIMBING)).byteValue());
    }
    
    public boolean getIsHanging() {
        return (((Byte)this.dataManager.get(HANGING)).byteValue() & 1) != 0;
    }
    
    public boolean getIsClimbing() {
        return (((Byte)this.dataManager.get(CLIMBING)).byteValue() & 1) != 0;
    }
    
    public void setIsClimbing(boolean isClimbing) {
        byte b0 = ((Byte)this.dataManager.get(CLIMBING)).byteValue();

        if (isClimbing) {
            this.dataManager.set(CLIMBING, Byte.valueOf((byte)(b0 | 1)));
        }
        else {
            this.dataManager.set(CLIMBING, Byte.valueOf((byte)(b0 & -2)));
        }
    }
    
    public void setIsHanging(boolean isHanging) {
        byte b0 = ((Byte)this.dataManager.get(HANGING)).byteValue();

        if (isHanging) {
            this.dataManager.set(HANGING, Byte.valueOf((byte)(b0 | 1)));
        }
        else {
            this.dataManager.set(HANGING, Byte.valueOf((byte)(b0 & -2)));
        }
    }
    
    public void setIsCasting(boolean isHanging) {
        byte b0 = ((Byte)this.dataManager.get(CASTING)).byteValue();

        if (isHanging) {
            this.dataManager.set(CASTING, Byte.valueOf((byte)(b0 | 1)));
        }
        else {
            this.dataManager.set(CASTING, Byte.valueOf((byte)(b0 & -2)));
        }
    }
    
    public class AIClimbimgTree extends EntityAIBase {
        private BlockPos TreePos;
    	
    	public AIClimbimgTree() {
            this.setMutexBits(3);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            int i = MathHelper.floor(EntityFoglet.this.posX);
            int j = MathHelper.floor(EntityFoglet.this.posY);
            int k = MathHelper.floor(EntityFoglet.this.posZ);
            BlockPos blockpos = new BlockPos(i, j, k);
            
            TreePos = null;
            
            for(int x = -1 ; x <= 1 ; x++)
            	for(int z = -1 ; z <= 1 ; z++) {
            		if(EntityFoglet.this.world.getBlockState(blockpos.add(x, 0, z)).getMaterial() == Material.WOOD) {
            			TreePos = new BlockPos(x, 0, z);
            			break;
            		}
            	}
            
            return !EntityFoglet.this.isBurning() && !EntityFoglet.this.isAggressive() && !EntityFoglet.this.world.canSeeSky(blockpos) && TreePos != null && EntityFoglet.this.world.getBlockState(blockpos.up(2)).getMaterial() == Material.AIR;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            super.startExecuting();
            EntityFoglet.this.setIsClimbing(true);
            EntityFoglet.this.navigator.clearPath();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask() {
            int i = MathHelper.floor(EntityFoglet.this.posX);
            int j = MathHelper.floor(EntityFoglet.this.posY);
            int k = MathHelper.floor(EntityFoglet.this.posZ);
            BlockPos blockpos = new BlockPos(i, j, k);
            
        	super.resetTask();
            EntityFoglet.this.setIsClimbing(false);
            EntityFoglet.this.motionY += 0.5D;
            
            if(EntityFoglet.this.world.getBlockState(blockpos.up(2)).getMaterial() != Material.AIR) {
            	EntityFoglet.this.setIsHanging(true);
            	EntityFoglet.this.setSilent(true);
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask() {
        	if(EntityFoglet.this.motionY < 0.0D) EntityFoglet.this.motionY += 0.1D;
        	if(TreePos != null) {
            	EntityFoglet.this.renderYawOffset = (TreePos.getX() * 270.0F + (float) Math.toDegrees(Math.atan(TreePos.getZ() / (TreePos.getX() + 0.0000001D)))) % 360.0F;
        	}
        }
    }
    
    public class AICastingApell extends EntityAIBase {
        public AICastingApell() {
            this.setMutexBits(3);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            return EntityFoglet.this.getSpellTicks() > 0;
        }
        
        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
        	return EntityFoglet.this.getSpellTicks() > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            super.startExecuting();
            EntityFoglet.this.setIsCasting(true);
            EntityFoglet.this.navigator.clearPath();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask() {
            super.resetTask();
            EntityFoglet.this.setIsCasting(false);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask() {
            if (EntityFoglet.this.getAttackTarget() != null) {
                EntityFoglet.this.getLookHelper().setLookPositionWithEntity(EntityFoglet.this.getAttackTarget(), (float)EntityFoglet.this.getHorizontalFaceSpeed(), (float)EntityFoglet.this.getVerticalFaceSpeed());
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
            if (EntityFoglet.this.getAttackTarget() == null) {
                return false;
            }
            else if (EntityFoglet.this.isSpellcasting()) {
                return false;
            }
            else {
            	return EntityFoglet.this.ticksExisted >= this.spellCooldown && EntityFoglet.this.getDistance(EntityFoglet.this.getAttackTarget()) < 3.0F;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            return EntityFoglet.this.getAttackTarget() != null && this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.spellWarmup = this.getCastWarmupTime();
            EntityFoglet.this.spellTicks = this.getCastingTime();
            EntityFoglet.this.world.setEntityState(EntityFoglet.this, (byte)10);
            this.spellCooldown = EntityFoglet.this.ticksExisted + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();

            if (soundevent != null) {
                EntityFoglet.this.playSound(soundevent, 1.0F, 1.0F);
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask() {
            --this.spellWarmup;

            if (this.spellWarmup == 0) {
                this.castSpell();
                EntityFoglet.this.playSound(EntityFoglet.this.getSpellSound(), 1.0F, 1.0F);
                
                if (!EntityFoglet.this.isClimber()) {
                	EntityFoglet.this.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 3 * 20));
                	EntityFoglet.this.world.setEntityState(EntityFoglet.this, (byte)11);
                }
            }
        }

        protected void castSpell() {
        	Potion effect;
        	
        	if (EntityFoglet.this.isClimber()) {
        		effect = ModMobEffects.SOILED;
        	} else {
        		effect = MobEffects.BLINDNESS;
        	}
        	
            EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(EntityFoglet.this.world, EntityFoglet.this.posX, EntityFoglet.this.posY + 1.0D, EntityFoglet.this.posZ);
            entityareaeffectcloud.setOwner(EntityFoglet.this);
            entityareaeffectcloud.setRadius(4.0F);
            entityareaeffectcloud.setRadiusOnUse(-0.5F);
            entityareaeffectcloud.setDuration(120);
            entityareaeffectcloud.setWaitTime(10);
            entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / (float)entityareaeffectcloud.getDuration());
            
            if(EntityFoglet.this.isClimber()) {
            	entityareaeffectcloud.setPotion(ModPotions.FOULODOR);
            }
            else {
            	entityareaeffectcloud.setParticle(EnumParticleTypes.CLOUD);
            }
            
            entityareaeffectcloud.addEffect(new PotionEffect(effect, 4 * 20, 1));
            entityareaeffectcloud.setColor(EntityFoglet.this.isClimber() ? 0x6F5B3C : 0xFFFFFF);
            EntityFoglet.this.world.spawnEntity(entityareaeffectcloud);
        }

        protected int getCastWarmupTime() {
            return 20;
        }

        protected int getCastingTime() {
            return 40;
        }

        protected int getCastingInterval() {
            return 150;
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.EVOCATION_ILLAGER_PREPARE_ATTACK;
        }
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return FishItems.ENTITY_FOGLET_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FishItems.ENTITY_FOGLET_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FishItems.ENTITY_FOGLET_DEATH;
    }
    
    protected SoundEvent getSpellSound() {
        return SoundEvents.EVOCATION_ILLAGER_CAST_SPELL;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.ENTITY_ZOMBIE_STEP;
    }

    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
    	return LootTableHandler.FOGLET;
    }
}
