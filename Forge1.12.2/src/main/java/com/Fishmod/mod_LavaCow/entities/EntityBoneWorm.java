package com.Fishmod.mod_LavaCow.entities;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.ai.EntityFishAIAttackRange;
import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityAcidJet;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.init.ModEnchantments;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBoneWorm  extends EntityMob{
	
	private boolean isAggressive = false;
	public double LocationFix;
	public int attackTimer;
	private EntityFishAIAttackRange range_atk;
	private EntityAIAvoidEntity<EntityPlayer> avoid_player;
	private int avoid_cooldown;
	
	public EntityBoneWorm(World worldIn)
    {
        super(worldIn);
        this.setSize(0.8F, 2.0F);
        this.LocationFix = 0.0D;
    }
	
    protected void initEntityAI()
    {
    	this.range_atk = new EntityFishAIAttackRange(this, EntityAcidJet.class, FishItems.ENTITY_BONEWORM_ATTACK, 1, 1, 0.0D, 12.0D, 0.0D, 0.65D, 0.0D);
    	this.avoid_player = new EntityAIAvoidEntity<EntityPlayer>(this, EntityPlayer.class, 10.0F, 1.0D, 1.2D);
    	
    	//this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(0, this.range_atk);
        //this.tasks.addTask(1, this.avoid_player);
        //this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        if(!Modconfig.SunScreen_Mode)this.tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI()
    {
    	this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
    	this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
    	//this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPig>(this, EntityPig.class, true));
    }
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.BoneWorm_Health);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.BoneWorm_Attack);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    }
    
    @Override
	public boolean getCanSpawnHere() {
		if(this.dimension == DimensionType.OVERWORLD.getId())
			return super.getCanSpawnHere();
		else return false;
	}
    
    private boolean isWalking() {
    	//return this.getDistance(this.lastTickPosX, this.lastTickPosY, this.lastTickPosZ) > 0;
    	return (this.posX - this.prevPosX != 0) || (this.posY - this.prevPosY != 0) || (this.posZ - this.prevPosZ != 0);
    	//return Math.abs(this.limbSwingAmount) > 0.01F; 
    }
    
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void onUpdate()
    {
        super.onUpdate();
        
        IBlockState state = world.getBlockState(new BlockPos(this.posX, this.posY, this.posZ).down());
        int blockId = Block.getStateId(state);
        if(this.isWalking()) {
	        if (state.isOpaqueCube()) {
	            if (world.isRemote) {
	            	for(int i = 0; i < 4; i++)
	            		this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, this.posY + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, this.posZ + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, this.rand.nextGaussian() * 0.02D, this.rand.nextGaussian() * 0.02D, this.rand.nextGaussian() * 0.02D, blockId);
	            }
	        }
	        if (this.ticksExisted % 10 == 0) {
	            this.playSound(SoundEvents.BLOCK_SAND_BREAK, 1, 0.5F);
	        }
	        
        }
        
        if(this.LocationFix > 0 && !this.isImmuneToFire) {
        	this.extinguish();
        	this.isImmuneToFire = true;
        }
        else if(this.LocationFix <= 0 && this.isImmuneToFire) {
        	this.isImmuneToFire = false;
        }
        
		if(this.isWalking() && state.isOpaqueCube()) {
			if(this.LocationFix <= 3.5D)this.LocationFix += 0.5D;
			if (world.isRemote)
				for(int i = 0; i < 4; i++)
					this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, this.posY + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, this.posZ + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, this.rand.nextGaussian() * 0.02D, this.rand.nextGaussian() * 0.1D, this.rand.nextGaussian() * 0.02D, blockId);
			this.setSize(this.width, Math.max(2.0F - (float)this.LocationFix, 0.5F));
        	//System.out.println("O_O " + this.LocationFix);
		}
		else if(this.LocationFix > 0.0D && state.isOpaqueCube()) {
			this.LocationFix -= 0.25D;
			if (world.isRemote)
				for(int i = 0; i < 4; i++)
					this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, this.posY + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, this.posZ + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, this.rand.nextGaussian() * 0.02D, this.rand.nextGaussian() * 0.1D, this.rand.nextGaussian() * 0.02D, blockId);
        	this.setSize(this.width, Math.max(2.0F - (float)this.LocationFix, 0.5F));
        	//System.out.println("O+O " + this.LocationFix);
		}
		
		if (!this.world.isRemote) {
			if (this.avoid_cooldown == 0) {
				this.tasks.addTask(0, this.range_atk);
				this.tasks.removeTask(this.avoid_player);
				this.avoid_cooldown = -1;
			}

			if (this.avoid_cooldown > 0)
				this.avoid_cooldown--;
		}

    }
    
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void onLivingUpdate()
    {
        if (this.attackTimer > 0) {
            --this.attackTimer;
         }
    	
    	if (!Modconfig.SunScreen_Mode && this.world.isDaytime() && !this.world.isRemote)
        	{
        		float f = this.getBrightness();
        		if (f > 0.5F && !this.isImmuneToFire && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.world.canSeeSky(new BlockPos(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ)))this.setFire(8);
        	}
    	
    	if(this.isWalking())this.extinguish();
    	/*if (!this.world.isRemote)
    		if (!getEntityWorld().getBlockState(getPosition().down()).isOpaqueCube() || !getEntityWorld().getBlockState(getPosition().down(2)).isOpaqueCube() || !getEntityWorld().getBlockState(getPosition().down(3)).isOpaqueCube())
    			this.posY -= 1.0D;*/
    	
        super.onLivingUpdate();
    }
    
    @SideOnly(Side.CLIENT)
    public double getLocationFix()
    {
    	return this.LocationFix;
    }
    
    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
    	//System.out.println("O+O " + this.isWalking() + " " + this.LocationFix + " " + source.getDamageType());
    	if (this.LocationFix > 0)
        {
            return false;
        }
        else
        {
            //this.setRunning(60);
        	return super.attackEntityFrom(source, amount);
        }
    }
    
    public void setRunning(int CD) {
    	//this.tasks.addTask(0, this.range_atk);
    	this.tasks.removeTask(this.range_atk);
		this.tasks.addTask(1, this.avoid_player);
		this.avoid_cooldown = CD;
    }

    public boolean attackEntityAsMob(Entity entityIn)
    {
        boolean flag = super.attackEntityAsMob(entityIn);

        if (flag) {
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
    
    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource cause) {
       super.onDeath(cause);
       
       int i = net.minecraftforge.common.ForgeHooks.getLootingLevel(this, cause.getTrueSource(), cause);
       if(this.canDropLoot())
    	   LootTableHandler.dropRareLoot(this, FishItems.ACIDICHEART, Modconfig.BoneWorm_DropHeart, ModEnchantments.CORROSIVE, 3, i);
       
       if(!world.isRemote) {			
			
	        if (cause.getTrueSource() instanceof EntityCreeper)
	        {
	            EntityCreeper entitycreeper = (EntityCreeper)cause.getTrueSource();

	            if (entitycreeper.getPowered() && entitycreeper.ableToCauseSkullDrop())
	            {
	                entitycreeper.incrementDroppedSkulls();
	                this.entityDropItem(new ItemStack(Items.SKULL, 1, 0), 0.0F);
	            }
	        }
       }
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
    
    @SideOnly(Side.CLIENT)
    public boolean isAggressive()
    {
    	return isAggressive;
    }
    
    @SideOnly(Side.CLIENT)
    public int getAttackTimer() {
       return this.attackTimer;
    }
    
    /**
     * Handler for {@link World#setEntityState}
     */
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
    	if (id == 4) 
    	{
            this.attackTimer = 15;
        }
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
        return FishItems.ENTITY_BONEWORM_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return FishItems.ENTITY_BONEWORM_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return FishItems.ENTITY_BONEWORM_DEATH;
    }

    protected SoundEvent getStepSound()
    {
        return SoundEvents.ENTITY_SPIDER_STEP;
    }

    protected void playStepSound(BlockPos pos, Block blockIn)
    {
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
        return LootTableHandler.BONEWORM;
    }
}
