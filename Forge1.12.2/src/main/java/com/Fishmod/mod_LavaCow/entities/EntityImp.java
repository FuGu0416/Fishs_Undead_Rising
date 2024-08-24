package com.Fishmod.mod_LavaCow.entities;

import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.init.ModMobEffects;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.ForgeEventFactory;

public class EntityImp extends EntityFoglet implements IAggressive {
	public EntityImp(World worldIn) {
        super(worldIn);
        this.setSize(0.6F, 1.5F);
        this.daytimeBurning = false;
        this.isImmuneToFire = true;
    }
	
	@Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new AICastingApell());
        this.tasks.addTask(3, new EntityImp.AIUseSpell());
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, false));
        if(!Modconfig.SunScreen_Mode)this.tasks.addTask(5, new EntityAIFleeSun(this, 1.0D));
        this.tasks.addTask(6, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }
    
	@Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Imp_Health);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Imp_Attack);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(4.0D);
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData entityLivingData) {
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Imp_Health);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Imp_Attack);
    	this.setHealth(this.getMaxHealth());
    	
    	return super.onInitialSpawn(difficulty, entityLivingData);
 	}
    
    public class AICastingApell extends EntityAIBase {
        public AICastingApell() {
            this.setMutexBits(3);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            return EntityImp.this.getSpellTicks() > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            super.startExecuting();
            EntityImp.this.setIsCasting(true);
            EntityImp.this.navigator.clearPath();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask() {
            super.resetTask();
            EntityImp.this.setIsCasting(false);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask() {
            if (EntityImp.this.getAttackTarget() != null) {
                EntityImp.this.getLookHelper().setLookPositionWithEntity(EntityImp.this.getAttackTarget(), (float)EntityImp.this.getHorizontalFaceSpeed(), (float)EntityImp.this.getVerticalFaceSpeed());
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
            if (EntityImp.this.getAttackTarget() == null) {
                return false;
            }
            else if (EntityImp.this.isSpellcasting()) {
                return false;
            }
            else {
            	return EntityImp.this.ticksExisted >= this.spellCooldown && EntityImp.this.getDistance(EntityImp.this.getAttackTarget()) < 3.0F;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            return EntityImp.this.getAttackTarget() != null && this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.spellWarmup = this.getCastWarmupTime();
            EntityImp.this.spellTicks = this.getCastingTime();
            EntityImp.this.world.setEntityState(EntityImp.this, (byte)10);
            this.spellCooldown = EntityImp.this.ticksExisted + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();

            if (soundevent != null) {
                EntityImp.this.playSound(soundevent, 1.0F, 1.0F);
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask() {
            --this.spellWarmup;

            if (this.spellWarmup == 0) {
                this.castSpell();
                EntityImp.this.playSound(EntityImp.this.getSpellSound(), 1.0F, 1.0F);
            	EntityImp.this.addPotionEffect(new PotionEffect(ModMobEffects.IMMOLATION, 8 * 20));
            }
        }

        protected void castSpell() {
            List<Entity> list = EntityImp.this.world.getEntitiesWithinAABBExcludingEntity((Entity)EntityImp.this, EntityImp.this.getEntityBoundingBox().grow(3.0D));
            
        	if (EntityImp.this.world instanceof WorldServer) {
        		((WorldServer) EntityImp.this.world).spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, EntityImp.this.posX, EntityImp.this.posY + EntityImp.this.height, EntityImp.this.posZ, 10, 0.0D, 1.0D, 0.0D, 0.0D);
        	}
        	
        	if (!EntityImp.this.world.isRemote) {
    			if (ForgeEventFactory.getMobGriefingEvent(EntityImp.this.world, EntityImp.this)) {
    				BlockPos blockpos = EntityImp.this.getPosition();
    				for(int i = -3 ; i < 3 ; i++) {
    					for(int j = -3 ; j < 3 ; j++) {
    						for(int k = -3 ; k < 3 ; k++) {					
    				            if (EntityImp.this.rand.nextFloat() < 0.3F && EntityImp.this.world.isAirBlock(blockpos.add(i, j, k))) {
    				            	EntityImp.this.world.setBlockState(blockpos.add(i, j, k), Blocks.FIRE.getDefaultState());
    				            }
    						}
    					}
    				}
    			}
    			
            	for (Entity entity1 : list) {
            		if (entity1 instanceof EntityLivingBase) {                 
            			if (!((EntityLivingBase)entity1).isImmuneToFire()) {        				
            				if (((EntityLivingBase)entity1).attackEntityFrom(DamageSource.causeMobDamage(EntityImp.this).setMagicDamage(), (float) EntityImp.this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * 1.0F)) {
            					((EntityLivingBase)entity1).setFire(4);
            				}       							
            			}
            		}
            	}
    		}
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
            return SoundEvents.ENTITY_ZOMBIE_INFECT;
        }
    }
    
    @Override
    protected SoundEvent getSpellSound() {
        return SoundEvents.ENTITY_BLAZE_SHOOT;
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEFINED;
    }
    
    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
    	return LootTableHandler.IMP;
    }
}
