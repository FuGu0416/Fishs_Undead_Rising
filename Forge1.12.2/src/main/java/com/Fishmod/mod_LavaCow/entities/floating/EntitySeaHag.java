package com.Fishmod.mod_LavaCow.entities.floating;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.entities.IAggressive;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.message.PacketParticle;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;

import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntitySeaHag extends EntityFloatingMob implements IAggressive {
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.<Integer>createKey(EntitySeaHag.class, DataSerializers.VARINT);
	
	public EntitySeaHag(World worldIn) {
        super(worldIn);
        this.setSize(1.25F, 1.5F);
		this.setPathPriority(PathNodeType.WATER, 8.0F);
        this.daytimeBurning = true;
    }
	
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(3, new EntityFloatingMob.AIChargeAttack());
        this.tasks.addTask(3, new EntitySeaHag.AIUseSpell());;
    }

    protected void applyEntityAI() {
    	this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
    	this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.175D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Sea_Hag_Health);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Sea_Hag_Attack);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
    }
    
    protected void entityInit() {
    	super.entityInit();
        this.getDataManager().register(SKIN_TYPE, Integer.valueOf(0));
    }
    
    /**
     * Gets how bright this entity is.
     */
    @Override
    public float getBrightness() {
       return 1.0F;
    }
    
    @Override
    protected String ParticleType() {
    	return this.isInWater() ? "spore" : "ectoplasm";
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Sea_Hag_Health);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Sea_Hag_Attack);
    	this.setHealth(this.getMaxHealth());
        
        return super.onInitialSpawn(difficulty, livingdata);
    }
	    
    public class AIUseSpell extends EntityAIBase {
        protected int spellWarmup;
        protected int spellCooldown;

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
        	if (EntitySeaHag.this.isSpellcasting()) {
                return false;
            }
            else {
                int i = EntitySeaHag.this.world.getEntitiesWithinAABB(EntityGhostSwarmer.class, EntitySeaHag.this.getEntityBoundingBox().grow(16.0D)).size();                        
            	return EntitySeaHag.this.ticksExisted >= this.spellCooldown && ((EntitySeaHag.this.getAttackTarget() != null && Math.abs(EntitySeaHag.this.posY - EntitySeaHag.this.getAttackTarget().posY) < 4.0D)) && i < Modconfig.Sea_Hag_Ability_Max;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            return this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.spellWarmup = this.getCastWarmupTime();
            EntitySeaHag.this.spellTicks = this.getCastingTime();
            EntitySeaHag.this.world.setEntityState(EntitySeaHag.this, (byte)10);
            this.spellCooldown = EntitySeaHag.this.ticksExisted + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();

            if (soundevent != null) {
                EntitySeaHag.this.playSound(soundevent, 1.0F, 1.0F);
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask() {
            --this.spellWarmup;

            if (this.spellWarmup == 5) {
                this.castSpell();
                EntitySeaHag.this.playSound(EntitySeaHag.this.getSpellSound(), 4.0F, 1.2F);
                           
            }
        }

        protected void castSpell() {
            for (int i = 0; i < Modconfig.Sea_Hag_Ability_Num; ++i) {
                BlockPos blockpos = (new BlockPos(EntitySeaHag.this)).add(-2 + EntitySeaHag.this.rand.nextInt(3), 1, -2 + EntitySeaHag.this.rand.nextInt(3));
                EntityGhostSwarmer entity = new EntityGhostSwarmer(EntitySeaHag.this.world);
                entity.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
                entity.setHealth(entity.getMaxHealth());
                entity.moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
                entity.onInitialSpawn(EntitySeaHag.this.world.getDifficultyForLocation(blockpos), (IEntityLivingData)null);
                entity.setLimitedLife(Modconfig.Ghost_Swarmer_Lifespan * 20);
                entity.setTamed(true);
                entity.setOwnerId(EntitySeaHag.this.getUniqueID());
                entity.setSkin(1);

                if(!EntitySeaHag.this.world.isRemote)
                	EntitySeaHag.this.world.spawnEntity(entity);
                
                entity.setAttackTarget(EntitySeaHag.this.getAttackTarget());
                
                if(EntitySeaHag.this.world instanceof World) {
                	for (int j = 0; j < 24; ++j) {
                		double d0 = entity.posX + (double)(entity.world.rand.nextFloat() * entity.width * 2.0F) - (double)entity.width;
                		double d1 = entity.posY + (double)(entity.world.rand.nextFloat() * entity.height);
                		double d2 = entity.posZ + (double)(entity.world.rand.nextFloat() * entity.width * 2.0F) - (double)entity.width;
                		mod_LavaCow.NETWORK_WRAPPER.sendToAll(new PacketParticle(EnumParticleTypes.WATER_SPLASH, d0, d1, d2));
                	}
                }
            }
        }

        protected int getCastWarmupTime() {
            return 20;
        }

        protected int getCastingTime() {
            return 30;
        }

        protected int getCastingInterval() {
        	return Modconfig.Sea_Hag_Ability_Cooldown * 20;
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound() {
            return FishItems.ENTITY_SEA_HAG_SCREECH;
        }
    }
    
    protected SoundEvent getAmbientSound() {
        return FishItems.ENTITY_SEA_HAG_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FishItems.ENTITY_AVATON_HURT;
    }

    protected SoundEvent getDeathSound() {
        return FishItems.ENTITY_AVATON_DEATH;
    }
    
    protected SoundEvent getSpellSound() {
        return SoundEvents.ENTITY_BOBBER_SPLASH;
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
    
    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableHandler.SEA_HAG;
    }
}
