package com.Fishmod.mod_LavaCow.entities;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.ai.EntityAIDestroyCrops;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityWeta extends EntitySpider implements IAggressive{

	private boolean isAggressive = false;
	private int attackTimer = 0;
	
	public EntityWeta(World worldIn) {
		super(worldIn);
		this.setSize(0.8F, 0.5F);
		this.experienceValue = 1;
	}
	
	@Override
	protected void initEntityAI()
    {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIDestroyCrops(this, 1.1D));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.8D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.applyEntityAI();
    }
	
    protected void applyEntityAI()
    {
    	this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
    }
    
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
    }
	
    @Override
	public boolean getCanSpawnHere() {
		return SpawnUtil.isAllowedDimension(this.dimension) && super.getCanSpawnHere();
	}
	
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void onLivingUpdate() {
    	if (this.attackTimer > 0) {
            --this.attackTimer;
        }
    	
    	super.onLivingUpdate();
    }
    
    protected void updateAITasks()
    {
        super.updateAITasks();
        
        if(this.getAttackTarget() != null)
        	{       		
        		isAggressive = true;
        		this.world.setEntityState(this, (byte)11);
        	}
        else 
        	{
        		isAggressive = false;
        		this.world.setEntityState(this, (byte)34);
        	}
    }
    
    public boolean attackEntityAsMob(Entity entityIn)
    {
        if (super.attackEntityAsMob(entityIn))
        {
        	this.attackTimer = 5;
	        this.world.setEntityState(this, (byte)4);
	        this.applyEnchantments(this, entityIn);
	        
            return true;
        }
        else
        {
            return false;
        }
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
    public void handleStatusUpdate(byte id)
    {
    	if (id == 4) 
    	{
            this.attackTimer = 5;
        }
    	else if (id == 11)
        {
        	this.isAggressive = true;
        }
        else if (id == 34)
        {
            this.isAggressive = false;
        }
        else
        {
            super.handleStatusUpdate(id);
        }
    }
    
	@Override
    public float getEyeHeight()
    {
        return 0.1F;
    }
    
	@Override
    protected SoundEvent getAmbientSound()
    {
        return FishItems.ENTITY_WETA_AMBIENT;
    }
	
	@Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return FishItems.ENTITY_WETA_HURT;
    }

	@Override
    protected SoundEvent getDeathSound()
    {
        return FishItems.ENTITY_WETA_DEATH;
    }
	
    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableHandler.WETA;
    }
}
