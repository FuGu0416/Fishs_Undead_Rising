package com.Fishmod.mod_LavaCow.entities.tameable;

import java.util.UUID;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.entities.EntityVespaCocoon;
import com.Fishmod.mod_LavaCow.entities.flying.EntityEnigmoth;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.init.ModMobEffects;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityEnigmothLarva extends EntityFishTameable {
	protected int spellTicks;
	
	public EntityEnigmothLarva(World worldIn)
    {
        super(worldIn);
        this.setSize(1.0F, 1.0F);
        this.experienceValue = 3;
    }
	
	@Override
	protected void initEntityAI()
    {
		this.tasks.addTask(1, new AICastingSpell());
        this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(4, new EntityEnigmothLarva.AIUseSpell());
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.8D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] {EntityEnigmoth.class}));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true).setUnseenMemoryTicks(160));
    }

	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Enigmoth_Larva_Health);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Enigmoth_Larva_Attack);
    }
	
	@Override
	@Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
	    this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Enigmoth_Larva_Health);
	    this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Enigmoth_Larva_Attack);
	    this.setHealth(this.getMaxHealth());
	    this.setChild(true);
	    this.setGrowingAge(-24000);
	       
	   	return super.onInitialSpawn(difficulty, livingdata);
    }
	
	public boolean getCanSpawnHere() {
		// Middle end island check
		if (this.world.provider.getDimension() == 1) {
			return !Modconfig.Enigmoth_Larva_Middle_End_Island ? this.posX > 500 || this.posX < -500 || this.posZ > 500 || this.posZ < -500 : true;
		}
		   	
		return super.getCanSpawnHere();
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
    protected void onGrowingAdult()
    {
    	this.setChild(false);
    	super.onGrowingAdult();
    }
    
    protected void setChild(boolean isChild) { 	
    	if(isChild) {
            this.experienceValue = 3;
	    	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Enigmoth_Larva_Health);
	        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Enigmoth_Larva_Attack);
    	} else {
            if (!this.world.isRemote) {
	        	this.playSound(FishItems.ENTITY_PARASITE_WEAVE, 1.0F, 1.0F);
	        	
	        	NBTTagCompound compoundnbt = new NBTTagCompound();
    	        this.writeToNBT(compoundnbt);
    	         	        
	    		EntityVespaCocoon pupa = new EntityVespaCocoon(this.world);
	    		pupa.serializeNBT().setTag("EnigmothData", compoundnbt);
	    		pupa.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
	    		pupa.setSkin(1);
	    		this.world.spawnEntity(pupa);
    		}
            
            this.setDead();
    	}
    }
    
    @Override
    public void onUpdate()
    {
        super.onUpdate();
        
        if (this.spellTicks > 0) {
            --this.spellTicks;
        }
    }
	
	@Override
	public void onLivingUpdate()
    {
		
    	if (this.world.isRemote) {
			if (this.getSpellTicks() > 0) {
				for(int i = 0; i < 2 + ((60 - this.getSpellTicks()) / 5); ++i) {
					this.world.spawnParticle(EnumParticleTypes.PORTAL, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
				}				
			}
		}
        
        super.onLivingUpdate();
    }
	
	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack itemstack = player.getHeldItem(hand);

        if (hand == EnumHand.MAIN_HAND && itemstack.isEmpty() && player.isSneaking() && Modconfig.Enigmoth_Larva_Pickup) {
        	player.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
        	
        	if (!player.inventory.addItemStackToInventory(new ItemStack(FishItems.ENIGMOTH_LARVA_ITEM, 1))) {
                player.dropItem(new ItemStack(FishItems.ENIGMOTH_LARVA_ITEM, 1), false);
            }
        	this.setDead();
            return true;
        }
        else {
            return super.processInteract(player, hand);
        }
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
		default:
			super.handleStatusUpdate(id);
			break;
		}
    }
    
	@Override
    public float getEyeHeight() {
        return 0.1F;
    }
	
	@Override
    protected SoundEvent getAmbientSound() {
        return FishItems.ENTITY_PARASITE_AMBIENT;
    }
	
	@Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FishItems.ENTITY_PARASITE_HURT;
    }

	@Override
    protected SoundEvent getDeathSound() {
        return FishItems.ENTITY_PARASITE_DEATH;
    }
	
	@Override
	protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(SoundEvents.ENTITY_SILVERFISH_STEP, 0.15F, 1.0F);
    }
	
    public SoundEvent getSpellSound() {
        return SoundEvents.ENTITY_ENDERMEN_TELEPORT;
    }
    
    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }
	
    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
    	return LootTableHandler.ENIGMOTH_LARVA;
    }
    
    @Override
    protected boolean canDropLoot() {
        return true;
    }
    
    static class AIParasiteAttack extends EntityAIAttackMelee
    {
        public AIParasiteAttack(EntityEnigmothLarva parasite)
        {
            super(parasite, 1.0D, true);
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting()
        {
            float f = this.attacker.getBrightness();

            if (f >= 0.5F && this.attacker.getRNG().nextInt(100) == 0)
            {
                this.attacker.setAttackTarget((EntityLivingBase)null);
                return false;
            }
            else
            {
                return super.shouldContinueExecuting();
            }
        }

        protected double getAttackReachSqr(EntityLivingBase attackTarget)
        {
            return (double)(attackTarget.width + 0.1F);
        }
    }
    
    @Override
	public EntityEnigmothLarva createChild(EntityAgeable ageable) {
    	EntityEnigmothLarva entity = new EntityEnigmothLarva(this.world);
		UUID uuid = this.getOwnerId();
		if (uuid != null) {
			entity.setOwnerId(uuid);
			entity.setTamed(true);
			entity.setHealth(this.getMaxHealth());
		}

		return entity;
	}
    
	// Immune to Corroded and Poison
    @Override
	public boolean isPotionApplicable(PotionEffect effect) {
		return effect.getPotion() != ModMobEffects.CORRODED && effect.getPotion() != MobEffects.POISON && super.isPotionApplicable(effect);
	}
    
    public class AICastingSpell extends EntityAIBase {
        public AICastingSpell() {
        	this.setMutexBits(3);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            return EntityEnigmothLarva.this.getSpellTicks() > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            super.startExecuting();
            EntityEnigmothLarva.this.navigator.clearPath();
        }
        
        public void resetTask() {
            super.resetTask();
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask() {
            if (EntityEnigmothLarva.this.getAttackTarget() != null) {
            	EntityEnigmothLarva.this.getLookHelper().setLookPositionWithEntity(EntityEnigmothLarva.this.getAttackTarget(), (float)EntityEnigmothLarva.this.getHorizontalFaceSpeed(), (float)EntityEnigmothLarva.this.getVerticalFaceSpeed());
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
    		if (EntityEnigmothLarva.this.getAttackTarget() == null)
                return false;
            if (EntityEnigmothLarva.this.isSpellcasting() || EntityEnigmothLarva.this.getDistance(EntityEnigmothLarva.this.getAttackTarget()) > (8.0F))
                return false;
            else {                
            	return EntityEnigmothLarva.this.ticksExisted >= this.spellCooldown;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            return EntityEnigmothLarva.this.getAttackTarget() != null && this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.spellWarmup = this.getCastWarmupTime();
            EntityEnigmothLarva.this.spellTicks = this.getCastingTime();
            this.spellCooldown = EntityEnigmothLarva.this.ticksExisted + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();
            EntityEnigmothLarva.this.world.setEntityState(EntityEnigmothLarva.this, (byte)10);
            if (soundevent != null) {
            	EntityEnigmothLarva.this.playSound(soundevent, 1.0F, 1.0F);
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
        	EntityEnigmothLarva.this.playSound(EntityEnigmothLarva.this.getSpellSound(), 0.175F, 1.0F);
        	EntityEnigmothLarva.this.setDead();
        }

        protected int getCastWarmupTime() {
            return 60;
        }

        protected int getCastingTime() {
            return 60;
        }

        protected int getCastingInterval() {
            return Modconfig.Enigmoth_Larva_Ability_Cooldown * 20;
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound() {
            return null;
        }
    }
}
