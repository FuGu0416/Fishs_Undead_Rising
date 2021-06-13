package com.Fishmod.mod_LavaCow.entities.tameable;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.entities.IAggressive;
import com.Fishmod.mod_LavaCow.entities.ai.EntityFishAIBreakDoor;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.init.ModMobEffects;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityUnburied extends EntityFishTameable implements IAggressive{
	
	private boolean isAggressive = false;
	private int attackTimer;
	protected int spellTicks;
	private boolean BirthAnimation;
	private int limitedLifeTicks;
	private int fire_aspect;
	private int sharpness;
	private int knockback;
	private int bane_of_arthropods;
	private int smite;
	private int lifesteal;
	private int poisonous;
	private int corrosive;
	private int unbreaking;
	
	public EntityUnburied(World worldIn)
    {
        super(worldIn);
        BirthAnimation = true;
        this.setSize(1.0F, 1.95F);
        this.limitedLifeTicks = -1;
    }
	
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityFishAIBreakDoor(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(6, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI()
    {
    	this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
    	this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false, new Class[0]));
    	this.targetTasks.addTask(4, new AICopyOwnerTarget(this));
    	this.targetTasks.addTask(5, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, 0, true, true, (p_210136_0_) -> {
	  	      return !(this.getOwner() instanceof EntityPlayer);
	   }));
    	this.targetTasks.addTask(5, new EntityAINearestAttackableTarget<EntityVillager>(this, EntityVillager.class, 0, true, true, (p_210136_0_) -> {
	  	      return !(this.getOwner() instanceof EntityPlayer);
  	   }));
    }
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Unburied_Health);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Unburied_Attack);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
    }
    
    public void setLimitedLife(int limitedLifeTicksIn)
    {
        this.limitedLifeTicks = limitedLifeTicksIn;
    }
    
    public float getBonusDamage(EntityLivingBase entityLivingBaseIn) {
    	return (0.5f * this.sharpness + 0.5f)
				+ (entityLivingBaseIn.getCreatureAttribute().equals(EnumCreatureAttribute.ARTHROPOD) ? (float)bane_of_arthropods * 2.5f : 0)
				+ (entityLivingBaseIn.getCreatureAttribute().equals(EnumCreatureAttribute.UNDEAD) ? (float)smite * 2.5f : 0);
    }
    
    public int getLifestealLevel() {
    	return this.lifesteal;
    }
    
    public boolean isSpellcasting()
    {
    	return this.spellTicks > 0;
    }
    
    @SideOnly(Side.CLIENT)
    public int getSpellTicks()
    {
        return this.spellTicks;
    }
        
    protected boolean isCommandItem(Item itemIn) {
    	return false;
    }
	
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void onUpdate()
    {
        IBlockState state = world.getBlockState(new BlockPos(this.posX, this.posY, this.posZ).down());
        int blockId = Block.getStateId(state);
        
        if (this.isSpellcasting()) {
            
	        if (state.isOpaqueCube()) {
	            if (world.isRemote) {
	            	for(int i = 0; i < 4; i++)
	            		this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, this.posY + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, this.posZ + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, this.rand.nextGaussian() * 0.02D, this.rand.nextGaussian() * 0.02D, this.rand.nextGaussian() * 0.02D, blockId);
	            }
	        }
	        if (this.ticksExisted % 10 == 0) {
	            this.playSound(SoundEvents.BLOCK_SAND_BREAK, 1, 0.5F);
	        }
	        
	        this.motionX = 0;
	        this.motionY = 0;
	        this.motionZ = 0;
        }
		
		super.onUpdate();
    }
    
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void onLivingUpdate()
    {           	
        if(this.BirthAnimation) {
	    	this.spellTicks = 20;
	        this.world.setEntityState(this, (byte)32);
	        this.BirthAnimation = false;
        }
    	
    	if (this.attackTimer > 0) {
            --this.attackTimer;
        }
        
        if (this.spellTicks > 0) {
            --this.spellTicks;
        }
        
        if(this.limitedLifeTicks >= 0 && this.ticksExisted >= this.limitedLifeTicks) {        	       	
        	this.attackEntityFrom(DamageSource.STARVE.setDamageIsAbsolute().setDamageBypassesArmor() , (float) Modconfig.Unburied_Health);
        }
    	
    	if (!Modconfig.SunScreen_Mode && !(this.getOwner() instanceof EntityPlayer) && this.world.isDaytime() && !this.world.isRemote)
    	{
    		float f = this.getBrightness();
    		if (f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.world.canSeeSky(new BlockPos(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ))) {
                boolean flag = true;
                ItemStack itemstack = this.getItemStackFromSlot(EntityEquipmentSlot.HEAD);

                if (!itemstack.isEmpty())
                {
                    if (itemstack.isItemStackDamageable())
                    {
                        itemstack.setItemDamage(itemstack.getItemDamage() + this.rand.nextInt(2));

                        if (itemstack.getItemDamage() >= itemstack.getMaxDamage())
                        {
                            this.renderBrokenItemStack(itemstack);
                            this.setItemStackToSlot(EntityEquipmentSlot.HEAD, ItemStack.EMPTY);
                        }
                    }

                    flag = false;
                }

                if (flag)
                {
                    this.setFire(8);
                }
    		}
    	}
    	
    	super.onLivingUpdate();
    }
    
    /**
     * Gives armor or weapon for entity based on given DifficultyInstance
     */
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
        super.setEquipmentBasedOnDifficulty(difficulty);

        if (this.rand.nextFloat() < (this.world.getDifficulty() == EnumDifficulty.HARD ? 0.05F : 0.01F))
        {
            int i = this.rand.nextInt(3);

            if (i == 0)
            {
                this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
            }
            else
            {
                this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SHOVEL));
            }
        }
    }
    
    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
    	return super.attackEntityFrom(source, amount);
    }

    public boolean attackEntityAsMob(Entity entityIn)
    {
        if (super.attackEntityAsMob(entityIn))
        {
        	this.attackTimer = 5;
	        this.world.setEntityState(this, (byte)4);
	        this.applyEnchantments(this, entityIn);
	        
            if(entityIn instanceof EntityLivingBase) {
	            if(this.fire_aspect > 0)
	            	entityIn.setFire((this.fire_aspect * 4) - 1);
	            
	            if(this.knockback > 0)
	            	((EntityLivingBase)entityIn).knockBack(this, (float)this.knockback * 0.5F, (this.posX - entityIn.posX)/this.getDistance(entityIn), (this.posZ - entityIn.posZ)/this.getDistance(entityIn));
	            
	            if(this.bane_of_arthropods > 0 && (((EntityLivingBase) entityIn).getCreatureAttribute().equals(EnumCreatureAttribute.ARTHROPOD))) {
	                int i = 20 + this.rand.nextInt(10 * bane_of_arthropods);
	                ((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, i, 3));
	            }
	            
	            if(this.poisonous > 0)
	    			((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(MobEffects.POISON, 8*20, this.poisonous - 1));
	            
	            if(this.corrosive > 0)
	            	((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(ModMobEffects.CORRODED, 4*20, this.corrosive - 1));
            }
            
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        
        this.setEquipmentBasedOnDifficulty(difficulty);
        
        return livingdata;
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
    
    @Override
    public boolean isAggressive()
    {
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
    	if (id == 32) {
        	this.spellTicks = 20;
        }
    	else if (id == 4) 
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

    public float getEyeHeight()
    {
        float f = 1.74F;

        if (this.isChild())
        {
            f = (float)((double)f - 0.81D);
        }

        return f;
    }
    
    class AICopyOwnerTarget extends EntityAITarget
    {
        public AICopyOwnerTarget(EntityCreature creature)
        {
            super(creature, false);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
            return EntityUnburied.this.getOwner() != null && EntityUnburied.this.getOwner() instanceof EntityLiving && ((EntityLiving) EntityUnburied.this.getOwner()).getAttackTarget() != null && this.isSuitableTarget(((EntityLiving) EntityUnburied.this.getOwner()).getAttackTarget(), false);
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting()
        {
            EntityUnburied.this.setAttackTarget(((EntityLiving) EntityUnburied.this.getOwner()).getAttackTarget());
            super.startExecuting();
        }
    }
    
    @Override
    protected SoundEvent getAmbientSound()
    {
        return FishItems.ENTITY_ZOMBIEFROZEN_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return FishItems.ENTITY_ZOMBIEFROZEN_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return FishItems.ENTITY_ZOMBIEFROZEN_DEATH;
    }

    protected SoundEvent getStepSound()
    {
        return SoundEvents.ENTITY_ZOMBIE_STEP;
    }

    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }
    
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.setLimitedLife(compound.getInteger("LifeTicks"));
    	this.fire_aspect = compound.getInteger("fire_aspect");
    	this.sharpness = compound.getInteger("sharpness");
    	this.knockback = compound.getInteger("knockback");
    	this.bane_of_arthropods = compound.getInteger("bane_of_arthropods");
    	this.smite = compound.getInteger("fire_aspect");
    	this.lifesteal = compound.getInteger("lifesteal");
    	this.poisonous = compound.getInteger("poisonous");
    	this.corrosive = compound.getInteger("corrosive");
    	this.unbreaking = compound.getInteger("unbreaking");   
    	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.LilSludge_Health + ((float)this.unbreaking * 2.0F));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("LifeTicks", this.limitedLifeTicks - this.ticksExisted);
        compound.setInteger("fire_aspect", this.fire_aspect);
        compound.setInteger("sharpness", this.sharpness);
        compound.setInteger("knockback", this.knockback);
        compound.setInteger("bane_of_arthropods", this.bane_of_arthropods);
        compound.setInteger("smite", this.smite);
        compound.setInteger("lifesteal", this.lifesteal);
        compound.setInteger("poisonous", this.poisonous);
        compound.setInteger("corrosive", this.corrosive);
        compound.setInteger("unbreaking", this.unbreaking);      
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    @Override
    public boolean isBreedingItem(ItemStack stack) {
       return false;
    }
    
    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
    	return LootTableList.ENTITIES_ZOMBIE;
    }
    
    /**
     * Entity won't drop items or experience points if this returns false
     */
    @Override
    protected boolean canDropLoot() {
       return !this.isTamed();
    }

	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		return null;
	}
}
