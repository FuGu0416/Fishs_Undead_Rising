package com.Fishmod.mod_LavaCow.entities;

import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;
import com.google.common.base.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityPingu  extends EntityMob{
	
	private boolean isAggressive = false;
	protected Block spawnableBlock = Blocks.ICE;
	private boolean HPbelow30 = false;
	private boolean HPbelow50 = false;
	
	public EntityPingu(World worldIn)
    {
        super(worldIn);
        this.setSize(0.5F, 0.8F);
    }
	
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 2.0D, false));
        //this.tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        //this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI()
    {
    	this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
    }
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        //this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.16D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(20.0D);
    }
    
    public float getBlockPathWeight(BlockPos pos)
    {
    	return this.world.getBlockState(pos.down()).getBlock() == this.spawnableBlock ? 10.0F : 10.0F - this.world.getLightBrightness(pos);
    }
    
    @Override
	public boolean getCanSpawnHere() {
    	IBlockState iblockstate = this.world.getBlockState((new BlockPos(this)).down());
    	
		if(this.dimension == DimensionType.OVERWORLD.getId())
			return this.getBlockPathWeight(new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ)) >= 0.0F 
	    			&& iblockstate.canEntitySpawn(this);
		else return false;
	}
    
    public boolean canBreatheUnderwater()
    {
        return true;
    }
    
    protected float getWaterSlowDown()
    {
        return 1.0F;
    }
    
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void onUpdate()
    {
        super.onUpdate();
    }
    
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void onLivingUpdate()
    {
        if (!Modconfig.SunScreen_Mode && !this.world.isRemote && this.ticksExisted % 20 == 0 && !this.isWet())
        {
            int i = MathHelper.floor(this.posX);
            int j = MathHelper.floor(this.posY);
            int k = MathHelper.floor(this.posZ);

            if (this.world.getBiome(new BlockPos(i, 0, k)).getTemperature(new BlockPos(i, j, k)) > 1.0F)
            {
                this.attackEntityFrom(DamageSource.ON_FIRE, 1.0F);
            }
        }
    	
        if(this.getHealth() < this.getMaxHealth() * 0.3F && !this.HPbelow30) {
        	this.playSound(SoundEvents.BLOCK_GLASS_BREAK, 1.0F, 1.0F);
        	this.HPbelow30 = true;
        }
        else if(this.getHealth() < this.getMaxHealth() * 0.5F && !this.HPbelow50) {
        	this.playSound(SoundEvents.BLOCK_GLASS_BREAK, 1.0F, 1.0F);
        	this.HPbelow50 = true;
        }
        else if(this.getHealth() >= this.getMaxHealth() * 0.5F && this.HPbelow30 && this.HPbelow50) {
        	this.HPbelow30 = false;
        	this.HPbelow50 = false;
        }
        
        
        
        super.onLivingUpdate();
    }
    
    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (this.isEntityInvulnerable(source))
        {
            return false;
        }
        else if(source.getTrueSource() instanceof EntityLivingBase)
        {
            Entity entity = source.getTrueSource();

            List<Entity> list = this.world.getEntitiesInAABBexcluding(this, this.getEntityBoundingBox().grow(16.0D, 16.0D, 16.0D), new Predicate<Entity>()
            {
                public boolean apply(@Nullable Entity p_apply_1_)
                {
                    return p_apply_1_ instanceof EntityPingu;
                }
            });
            
            for(Entity E : list)
            	//if(((EntityPingu)E).getAttackingEntity() != null)
            		((EntityPingu)E).setAttackTarget((EntityLivingBase) entity);

            
        }
        
        return super.attackEntityFrom(source, amount);
        //return super.attackEntityFrom(source, source.isFireDamage()? 2.0F * amount : amount);
    }

    public boolean attackEntityAsMob(Entity entityIn)
    {
        boolean flag = super.attackEntityAsMob(entityIn);

        if (flag)
        {
            float f = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();
            if (this.getHeldItemMainhand().isEmpty() && this.isBurning() && this.rand.nextFloat() < f * 0.3F)
            {
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
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        livingdata = super.onInitialSpawn(difficulty, livingdata);

        return livingdata;
    }
    
    protected void updateAITasks()
    {
        super.updateAITasks();
        if(this.getAttackTarget() != null && !this.getAttackTarget().isDead)
        	{       		
        		isAggressive = true;
        		this.world.setEntityState(this, (byte)11);
        		//System.out.println("O_O throw");
        		//setFog_counter = 166;
        	}
        else 
        	{
        		isAggressive = false;
        		this.world.setEntityState(this, (byte)34);
        	}
    }
    
    @SideOnly(Side.CLIENT)
    public boolean isAggressive()
    {
    	//if(this.getAttackTarget() != null)System.out.println("O_O" + this.getAttackTarget().getName());
    	//else System.out.println("OAO");
    	return isAggressive;
    }
    
    /**
     * Handler for {@link World#setEntityState}
     */
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
        if (id == 11)
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

    public float getEyeHeight()
    {
        return this.height * 0.8F;
    }
    
    @Override
    protected SoundEvent getAmbientSound()
    {
        return FishItems.ENTITY_PINGU_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return FishItems.ENTITY_PINGU_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return FishItems.ENTITY_PINGU_DEATH;
    }

    protected SoundEvent getStepSound()
    {
        return SoundEvents.ENTITY_CHICKEN_STEP;
    }

    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        if(!this.isAggressive)
        	this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableHandler.PINGU;
    }
}
