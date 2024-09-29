package com.Fishmod.mod_LavaCow.entities;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityUnburied;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.init.ModMobEffects;
import com.Fishmod.mod_LavaCow.message.PacketParticle;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityUndertaker extends EntityMob implements IAggressive {	
	private int attackTimer;
	protected int spellTicks;
	private boolean isAggressive = false;
	
	public EntityUndertaker(World worldIn) {
		super(worldIn);
		this.setSize(1.8F, 2.4F);
		this.experienceValue = 12;
	}
	
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new AICastingApell());
        this.tasks.addTask(2, new EntityUndertaker.AIUseSpell());
        this.tasks.addTask(3, new EntityUndertaker.AIUndertakerAttack(this, 1.25D, false));
        if(!Modconfig.SunScreen_Mode)this.tasks.addTask(4, new EntityAIFleeSun(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
    	this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, true));
    	this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
    	this.targetTasks.addTask(3, new EntityUndertaker.AIUndertakerTarget<>(this, EntityPlayer.class, false));
        this.targetTasks.addTask(3, new EntityUndertaker.AIUndertakerTarget<>(this, EntityVillager.class, true));
        this.targetTasks.addTask(3, new EntityUndertaker.AIUndertakerTarget<>(this, EntityIronGolem.class, true));
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Undertaker_Health);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.21D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Undertaker_Attack);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    }
    
    @Override
	public boolean getCanSpawnHere() {
    	return SpawnUtil.isAllowedDimension(this.dimension) && super.getCanSpawnHere();
	}
    
    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    @Override
    public int getMaxSpawnedInChunk() {
       return 1;
    }
    
    public boolean isSpellcasting() {
    	return this.spellTicks > 0;
    }
    
    public int getSpellTicks() {
        return this.spellTicks;
    }
    
    @Override
    public double getMountedYOffset() {
        return -0.75D;
    }
    
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void onUpdate() {
        super.onUpdate();
        // Should always return EntityLivingBase (according to the documentation).
    	EntityLivingBase target = this.getAttackTarget();
        		
        if (this.attackTimer > 0) {
            --this.attackTimer;
         }
        
        if (this.spellTicks > 0) {
            --this.spellTicks;
        }

        if(this.getAttackTimer() == 10 && !(this.isSpellcasting()) && this.deathTime <= 0) {
        	this.playSound(FishItems.ENTITY_SKELETONKING_ATTACK, 1.0F, 1.25F);	
        }
        
        if (target != null && this.getAttackTimer() == 5 && this.getDistanceSq(target) < 6.0D && !(this.isSpellcasting()) && this.deathTime <= 0 && this.canEntityBeSeen(target)) {
        	float f = this.world.getDifficultyForLocation(target.getPosition()).getAdditionalDifficulty();      	
        	this.getAttackTarget().attackEntityFrom(DamageSource.causeMobDamage(this), (float) this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
        	target.knockBack(target, 2.0F * 0.5F, (double)MathHelper.sin(this.rotationYaw * ((float)Math.PI / 180F)), (double)(-MathHelper.cos(this.rotationYaw * ((float)Math.PI / 180F))));
    		if (target instanceof EntityPlayer) ((EntityPlayer)target).disableShield(true);
    		target.addPotionEffect(new PotionEffect(ModMobEffects.CORRODED, 4 * 20 * (int)f, 1));
    		this.playSound(SoundEvents.BLOCK_ANVIL_PLACE, 0.5F, 0.5F);
        	
            if (this.getHeldItemMainhand().isEmpty() && this.isBurning() && this.rand.nextFloat() < f * 0.3F) {
            	target.setFire(2 * (int)f);
            }
        }
    }
	
    public boolean attackEntityAsMob(Entity entityIn) {
        this.attackTimer = 15;
        this.world.setEntityState(this, (byte)4);
        return true;
    }
	
    /**
     * Gives armor or weapon for entity based on given DifficultyInstance
     */
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
	   this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(FishItems.UNDERTAKER_SHOVEL));
       this.getHeldItemMainhand().attemptDamageItem(this.rand.nextInt(this.getHeldItemMainhand().getMaxDamage()), this.rand, null);
    }
    
    public float getEyeHeight()
    {
        return this.height * 0.75F;
    }
    
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.spellTicks = compound.getInteger("SpellTicks");
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("SpellTicks", this.spellTicks);
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Undertaker_Health);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Undertaker_Attack);
    	this.setHealth(this.getMaxHealth());
    	
        this.setEquipmentBasedOnDifficulty(difficulty);
        this.setEnchantmentBasedOnDifficulty(difficulty);
        
        return livingdata;
    }
    
    protected void updateAITasks() {
        super.updateAITasks();
        
        if(this.getAttackTarget() != null) {       		
        		isAggressive = true;
        		this.world.setEntityState(this, (byte)11);
        	} else {
        		isAggressive = false;
        		this.world.setEntityState(this, (byte)34);
        	}
    }
    
	@Override
	public boolean isAggressive() {
		return isAggressive;
	}
    
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
	@Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
		switch(id) {
		case 4:
			this.attackTimer = 15;
			break;
		case 10:
			this.spellTicks = 30;
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
    
    public class AICastingApell extends EntityAIBase {
        public AICastingApell() {
            this.setMutexBits(3);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            return EntityUndertaker.this.getSpellTicks() > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            super.startExecuting();
            EntityUndertaker.this.navigator.clearPath();
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
            if (EntityUndertaker.this.getAttackTarget() != null) {
                EntityUndertaker.this.getLookHelper().setLookPositionWithEntity(EntityUndertaker.this.getAttackTarget(), (float)EntityUndertaker.this.getHorizontalFaceSpeed(), (float)EntityUndertaker.this.getVerticalFaceSpeed());
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
            if (!EntityUndertaker.this.getHeldItemMainhand().getItem().equals(FishItems.UNDERTAKER_SHOVEL))
            	return false;
            else if (EntityUndertaker.this.getAttackTarget() == null)
                return false;
            else if (EntityUndertaker.this.isSpellcasting() || !EntityUndertaker.this.canEntityBeSeen(EntityUndertaker.this.getAttackTarget()))
                return false;
            else {
                int i = EntityUndertaker.this.world.getEntitiesWithinAABB(EntityUnburied.class, EntityUndertaker.this.getEntityBoundingBox().grow(16.0D)).size();
            	return EntityUndertaker.this.ticksExisted >= this.spellCooldown && i < Modconfig.Undertaker_Ability_Max;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            return EntityUndertaker.this.getAttackTarget() != null && this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.spellWarmup = this.getCastWarmupTime();
            EntityUndertaker.this.spellTicks = this.getCastingTime();
            this.spellCooldown = EntityUndertaker.this.ticksExisted + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();
            EntityUndertaker.this.world.setEntityState(EntityUndertaker.this, (byte)10);
            if (soundevent != null) {
                EntityUndertaker.this.playSound(soundevent, 1.0F, 1.0F);
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask() {
            --this.spellWarmup;

            if (this.spellWarmup == 0) {
                this.castSpell();
                EntityUndertaker.this.playSound(EntityUndertaker.this.getSpellSound(), 1.0F, 1.0F);
            }
        }

        protected void castSpell() {
            for (int i = 0; i < Modconfig.Undertaker_Ability_Num; ++i) {
                BlockPos blockpos = (new BlockPos(EntityUndertaker.this)).add(-6 + EntityUndertaker.this.rand.nextInt(12), 0, -6 + EntityUndertaker.this.rand.nextInt(12));
                if(EntityUndertaker.this.rand.nextFloat() < 0.15F) {
                	if (BiomeDictionary.hasType(EntityUndertaker.this.getEntityWorld().getBiome(EntityUndertaker.this.getPosition()), Type.DRY)) {
                    	EntityMummy entity = new EntityMummy(EntityUndertaker.this.world);
                    	entity.moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
                    	entity.onInitialSpawn(EntityUndertaker.this.world.getDifficultyForLocation(blockpos), (IEntityLivingData)null);
                        entity.setLimitedLife(Modconfig.Unburied_Lifespan * 20);
                    	entity.setCanPickUpLoot(false);
                    	entity.setTamed(true);
                    	entity.setOwnerId(EntityUndertaker.this.getUniqueID());
                        
                        if(!EntityUndertaker.this.world.isRemote)
                        	EntityUndertaker.this.world.spawnEntity(entity);
                        
                        EntityUndertaker.this.world.setEntityState(entity, (byte)32);
                        
                        if(EntityUndertaker.this.getAttackingEntity() != null)
                        	entity.setAttackTarget(EntityUndertaker.this.getAttackingEntity());
                        
                        if(EntityUndertaker.this.world instanceof World) {
                        	for (int j = 0; j < 24; ++j) {
                        		double d0 = entity.posX + (double)(entity.world.rand.nextFloat() * entity.width * 2.0F) - (double)entity.width;
                        		double d1 = entity.posY + (double)(entity.world.rand.nextFloat() * entity.height);
                        		double d2 = entity.posZ + (double)(entity.world.rand.nextFloat() * entity.width * 2.0F) - (double)entity.width;
                        		mod_LavaCow.NETWORK_WRAPPER.sendToAll(new PacketParticle(EnumParticleTypes.SMOKE_LARGE, d0, d1, d2));
                        	}
                        }     
                    } else if (BiomeDictionary.hasType(EntityUndertaker.this.getEntityWorld().getBiome(EntityUndertaker.this.getPosition()), Type.COLD)) {
                    	EntityZombieFrozen entity = new EntityZombieFrozen(EntityUndertaker.this.world);
                    	entity.moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
                    	entity.onInitialSpawn(EntityUndertaker.this.world.getDifficultyForLocation(blockpos), (IEntityLivingData)null);
                        entity.setLimitedLife(Modconfig.Unburied_Lifespan * 20);
                    	entity.setCanPickUpLoot(false);
                    	entity.setTamed(true);
                    	entity.setOwnerId(EntityUndertaker.this.getUniqueID());
                        
                        if(!EntityUndertaker.this.world.isRemote)
                        	EntityUndertaker.this.world.spawnEntity(entity);
                        
                        EntityUndertaker.this.world.setEntityState(entity, (byte)32);
                        
                        if(EntityUndertaker.this.getAttackingEntity() != null)
                        	entity.setAttackTarget(EntityUndertaker.this.getAttackingEntity());
                        
                        if(EntityUndertaker.this.world instanceof World) {
                        	for (int j = 0; j < 24; ++j) {
                        		double d0 = entity.posX + (double)(entity.world.rand.nextFloat() * entity.width * 2.0F) - (double)entity.width;
                        		double d1 = entity.posY + (double)(entity.world.rand.nextFloat() * entity.height);
                        		double d2 = entity.posZ + (double)(entity.world.rand.nextFloat() * entity.width * 2.0F) - (double)entity.width;
                        		mod_LavaCow.NETWORK_WRAPPER.sendToAll(new PacketParticle(EnumParticleTypes.SMOKE_LARGE, d0, d1, d2));
                        	}
                        }    
                    } else if (BiomeDictionary.hasType(EntityUndertaker.this.getEntityWorld().getBiome(EntityUndertaker.this.getPosition()), Type.WET)) {
                    	EntityZombieMushroom entity = new EntityZombieMushroom(EntityUndertaker.this.world);
                    	entity.moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
                        entity.onInitialSpawn(EntityUndertaker.this.world.getDifficultyForLocation(blockpos), (IEntityLivingData)null);
                        entity.setLimitedLife(Modconfig.Unburied_Lifespan * 20);
                    	entity.setCanPickUpLoot(false);
                    	entity.setTamed(true);
                    	entity.setOwnerId(EntityUndertaker.this.getUniqueID());
                        
                        if(!EntityUndertaker.this.world.isRemote)
                        	EntityUndertaker.this.world.spawnEntity(entity);
                        
                        EntityUndertaker.this.world.setEntityState(entity, (byte)32);
                        
                        if(EntityUndertaker.this.getAttackingEntity() != null)
                        	entity.setAttackTarget(EntityUndertaker.this.getAttackingEntity());
                        
                        if(EntityUndertaker.this.world instanceof World) {
                        	for (int j = 0; j < 24; ++j) {
                        		double d0 = entity.posX + (double)(entity.world.rand.nextFloat() * entity.width * 2.0F) - (double)entity.width;
                        		double d1 = entity.posY + (double)(entity.world.rand.nextFloat() * entity.height);
                        		double d2 = entity.posZ + (double)(entity.world.rand.nextFloat() * entity.width * 2.0F) - (double)entity.width;
                        		mod_LavaCow.NETWORK_WRAPPER.sendToAll(new PacketParticle(EnumParticleTypes.SMOKE_LARGE, d0, d1, d2));
                        	}
                        }
                    }
                } else {
                	EntityUnburied entity = new EntityUnburied(EntityUndertaker.this.world);
                	entity.moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
                	entity.onInitialSpawn(EntityUndertaker.this.world.getDifficultyForLocation(blockpos), (IEntityLivingData)null);
                    entity.setLimitedLife(Modconfig.Unburied_Lifespan * 20);
                	entity.setCanPickUpLoot(false);
                	entity.setTamed(true);
                	entity.setOwnerId(EntityUndertaker.this.getUniqueID());
                    
                    if(!EntityUndertaker.this.world.isRemote)
                    	EntityUndertaker.this.world.spawnEntity(entity);
                    
                    EntityUndertaker.this.world.setEntityState(entity, (byte)32);
                    
                    if(EntityUndertaker.this.getAttackingEntity() != null)
                    	entity.setAttackTarget(EntityUndertaker.this.getAttackingEntity());
                    
                    if(EntityUndertaker.this.world instanceof World) {
                    	for (int j = 0; j < 24; ++j) {
                    		double d0 = entity.posX + (double)(entity.world.rand.nextFloat() * entity.width * 2.0F) - (double)entity.width;
                    		double d1 = entity.posY + (double)(entity.world.rand.nextFloat() * entity.height);
                    		double d2 = entity.posZ + (double)(entity.world.rand.nextFloat() * entity.width * 2.0F) - (double)entity.width;
                    		mod_LavaCow.NETWORK_WRAPPER.sendToAll(new PacketParticle(EnumParticleTypes.SMOKE_LARGE, d0, d1, d2));
                    	}
                    }
                }                           
            }
        }

        protected int getCastWarmupTime() {
            return 30;
        }

        protected int getCastingTime() {
            return 30;
        }

        protected int getCastingInterval() {
            return Modconfig.Undertaker_Ability_Cooldown * 20;
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound() {
            return FishItems.ENTITY_SKELETONKING_SPELL_SKELETON_ARMY;
        }
    }
    
    static class AIUndertakerAttack extends EntityAIAttackMelee {
        public AIUndertakerAttack(EntityUndertaker entity, double speedIn, boolean useLongMemory) {
            super(entity, speedIn, useLongMemory);
        }

        @Override
        public boolean shouldContinueExecuting() {
            float f = this.attacker.getBrightness();

            if (f >= 0.5F && this.attacker.getRNG().nextInt(100) == 0) {
                this.attacker.setAttackTarget(null);
                return false;
            } else {
                return super.shouldContinueExecuting();
            }
        }

        @Override
        protected double getAttackReachSqr(EntityLivingBase attackTarget) {
            return 4.0F + attackTarget.width;
        }
    }

    static class AIUndertakerTarget<T extends EntityLivingBase> extends EntityAINearestAttackableTarget<T> {
        public AIUndertakerTarget(EntityUndertaker entity, Class<T> classTarget, boolean checkSight) {
            super(entity, classTarget, checkSight);
        }

        @Override
        public boolean shouldExecute() {
            float f = this.taskOwner.getBrightness();
            return f < 0.5F && super.shouldExecute();
        }
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return FishItems.ENTITY_UNDERTAKER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FishItems.ENTITY_UNDERTAKER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FishItems.ENTITY_UNDERTAKER_DEATH;
    }
    
    protected SoundEvent getSpellSound() {
        return FishItems.ENTITY_SKELETONKING_SPELL_SUMMON;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.ENTITY_ZOMBIE_STEP;
    }

    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(this.getStepSound(), 0.15F, 0.7F);
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
        return LootTableHandler.UNDERTAKER;
    }
}
