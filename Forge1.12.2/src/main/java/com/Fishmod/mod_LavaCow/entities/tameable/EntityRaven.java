package com.Fishmod.mod_LavaCow.entities.tameable;

import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.ai.EntityAITargetItem;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFollowOwnerFlying;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWaterFlying;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityFlyHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityFlying;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketSetPassengers;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityRaven extends EntityFishTameable implements EntityFlying{
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.<Integer>createKey(EntityRaven.class, DataSerializers.VARINT);
    private final Set<Item> TAME_ITEMS = Sets.newHashSet(Items.SPIDER_EYE, Items.FERMENTED_SPIDER_EYE);
    public float flap;
    public float flapSpeed;
    public float oFlapSpeed;
    public float oFlap;
    public float flapping = 1.0F;
    private boolean partyParrot;
    private BlockPos jukeboxPosition;
    private int ridingCooldown;
    
    private float TargetLocationX = -1.0F;
    private float TargetLocationY = -1.0F;
    private float TargetLocationZ = -1.0F;
    
    private EntityAITargetItem<EntityItem> AITargetItem;
	
	public EntityRaven(World worldIn) {
		super(worldIn);
        this.setSize(0.5F, 0.9F);
        this.ridingCooldown = 30;
        this.moveHelper = new EntityFlyHelper(this);
        this.setCanPickUpLoot(true);
	}
	
    protected void initEntityAI()
    {
        super.initEntityAI();
    	this.wander = new EntityAIWanderAvoidWaterFlying(this, 1.0D);
    	this.follow = new EntityAIFollowOwnerFlying(this, 1.0D, 5.0F, 1.0F);
        
        this.tasks.addTask(0, new AIMovetoTargetLocation());
        this.tasks.addTask(1, new EntityAIPanic(this, 1.25D));
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.applyEntityAI();
    }
    
    protected void applyEntityAI()
    {
    	this.AITargetItem = new EntityAITargetItem<>(this, EntityItem.class, true);
    	this.targetTasks.addTask(1, this.AITargetItem);
	}
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Raven_Health);
        this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.6D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20000000298023224D);
    }
    
    /**
     * Returns new PathNavigateGround instance
     */
    protected PathNavigate createNavigator(World worldIn)
    {
        PathNavigateFlying pathnavigateflying = new PathNavigateFlying(this, worldIn);
        pathnavigateflying.setCanOpenDoors(false);
        pathnavigateflying.setCanFloat(true);
        pathnavigateflying.setCanEnterDoors(true);
        return pathnavigateflying;
    }
    
    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
    {
        return false;
    }
	
    public float getEyeHeight()
    {
        return this.height * 0.6F;
    }
    
    private void SetDismount(Entity ridden) {  	
    	this.dismountRidingEntity();
		this.setPosition(ridden.posX, ridden.posY + ridden.height/2 - 0.35F, ridden.posZ);
        this.isJumping = false;
        this.navigator.clearPath();
        this.setAttackTarget((EntityLivingBase)null);
        
        if(ridden instanceof EntityPlayerMP && ((EntityPlayerMP) ridden).connection != null) {
            ((EntityPlayerMP) ridden).connection.sendPacket(new SPacketSetPassengers(ridden));
          }
        
		this.ridingCooldown = 30;
    }
    
    public void setTargetLocation(float X, float Y, float Z) {
    	this.TargetLocationX = X;
    	this.TargetLocationY = Y;
    	this.TargetLocationZ = Z;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    { 	
    	super.onLivingUpdate();
    	
        if (this.jukeboxPosition == null || this.jukeboxPosition.distanceSq(this.posX, this.posY, this.posZ) > 12.0D || this.world.getBlockState(this.jukeboxPosition).getBlock() != Blocks.JUKEBOX)
        {
            this.partyParrot = false;
            this.jukeboxPosition = null;
        }
        
        if(!this.isFetching() && this.isTamed()) {
        	if(this.ridingCooldown > 0)this.ridingCooldown--;
        	
	        if (this.getRidingEntity() != null && this.getRidingEntity() instanceof EntityPlayer) {
	        	this.setRotation(getRidingEntity().rotationYaw, 0F);
	        	
	        	if(Modconfig.Raven_Slowfall && 
	        			!this.getRidingEntity().onGround && 
	        			this.getRidingEntity().motionY < 0.0D && 
	        			!this.getRidingEntity().hasNoGravity() && 
	        			!((EntityPlayer)this.getRidingEntity()).isElytraFlying()) {
	        		this.getRidingEntity().motionY *= 0.5D;
	        	}
	        	
	        	if(this.getRidingEntity().isSneaking() || this.getRidingEntity().isInWater()) {
	        		this.SetDismount(this.getRidingEntity());
	        	}
	        }
	        
	    	if(!this.isSitting() && !this.isRiding() && this.getHeldItemMainhand().isEmpty() && this.ticksExisted % 200 == 0 && this.rand.nextFloat() < 0.02f) {
	    	    ItemStack chosenDrop = null;
                Map<ItemStack, Float> lootTable;

                switch (this.getSkin()) {
                    case 2:
                        lootTable = LootTableHandler.LOOT_SEAGULL;
                        break;
                    case 3:
                        lootTable = LootTableHandler.LOOT_SPECTRAL_RAVEN;
                        break;
                    default:
                        lootTable = LootTableHandler.LOOT_RAVEN;
                        break;
                }

                for(Map.Entry<ItemStack, Float> entry : lootTable.entrySet()) {
                    if (this.rand.nextFloat() < entry.getValue()) {
                        chosenDrop = entry.getKey();
                        break;
                    }
                }

	    	    // These are the fallback items in-case no special drop is chosen.
	    	    if (chosenDrop == null) {
                    switch(this.getSkin()) {
                        case 1:
                            chosenDrop = new ItemStack(Items.IRON_NUGGET, 1);
                            break;
                        case 2:
                            chosenDrop = new ItemStack(Items.FISH, 1);
                            break;
                        case 3:
                            chosenDrop = new ItemStack(FishItems.ECTOPLASM, 1);
                            break;
                        default:
                            chosenDrop = new ItemStack(FishItems.FEATHER_BLACK, 1);
                            break;
                    }
                }

	    	    this.setHeldItem(getActiveHand(), new ItemStack(chosenDrop.getItem(), this.rand.nextInt(chosenDrop.getCount()) + 1, chosenDrop.getMetadata()));
	    	}
        }
        
        if(this.canPickUpLoot() && !this.getHeldItemMainhand().isEmpty()) {
        	this.setCanPickUpLoot(false);
        }
        else if(!this.canPickUpLoot() && this.getHeldItemMainhand().isEmpty())
        	this.setCanPickUpLoot(true);
             
        this.calculateFlapping();
    }

    @SideOnly(Side.CLIENT)
    public void setPartying(BlockPos pos, boolean p_191987_2_)
    {
        this.jukeboxPosition = pos;
        this.partyParrot = p_191987_2_;
    }

    @SideOnly(Side.CLIENT)
    public boolean isPartying()
    {
        return this.partyParrot;
    }

    private void calculateFlapping()
    {
        this.oFlap = this.flap;
        this.oFlapSpeed = this.flapSpeed;
        this.flapSpeed = (float)((double)this.flapSpeed + (double)(this.onGround ? -1 : 4) * 0.3D);
        this.flapSpeed = MathHelper.clamp(this.flapSpeed, 0.0F, 1.0F);

        if (!this.onGround && this.flapping < 1.0F)
        {
            this.flapping = 1.0F;
        }

        this.flapping = (float)((double)this.flapping * 0.9D);

        if (!this.onGround && this.motionY < 0.0D)
        {
            this.motionY *= 0.6D;
        }

        this.flap += this.flapping * 2.0F;
    }

    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        ItemStack itemstack = player.getHeldItem(hand);
        
        if(this.isOwner(player) && hand.equals(EnumHand.MAIN_HAND)) {
        	
	    	if (itemstack.interactWithEntity(player, this, hand)) {
	    		return true;
	    	}
	    	else if(this.isBreedingItem(itemstack) && this.getHealth() < this.getMaxHealth()) {
	        	
	            if (!player.capabilities.isCreativeMode)
	            {
	                itemstack.shrink(1);
	            }
	
	            if (!this.isSilent())
	            {
	                this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PARROT_EAT, this.getSoundCategory(), 1.0F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
	            }
	        	
	        	this.heal(2.0F);
	        	
	        	return true;
	        }
	        else if (itemstack.getItem() == FishItems.GHOSTJELLY && this.getSkin() == 0) {
	            if (!player.capabilities.isCreativeMode)
	            {
	                itemstack.shrink(1);
	            }
	        	this.setSkin(3);
	        	this.playSound(SoundEvents.AMBIENT_CAVE, 1.0F, 1.0F);
	        	for (int i = 0; i < 16; ++i)
	            {
	                double d0 = new Random().nextGaussian() * 0.02D;
	                double d1 = new Random().nextGaussian() * 0.02D;
	                double d2 = new Random().nextGaussian() * 0.02D;
	                this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, this.posX + (double)(new Random().nextFloat() * this.width) - (double)this.width, this.posY + (double)(new Random().nextFloat() * this.height), this.posZ + (double)(new Random().nextFloat() * this.width) - (double)this.width, d0, d1, d2);
	            }
	        	
	        	return true;
	        }
	        else if (!this.world.isRemote && itemstack.isEmpty() && !this.getHeldItemMainhand().isEmpty())
	        {     	
            	player.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
            	
            	if (!player.inventory.addItemStackToInventory(this.getHeldItemMainhand().copy()))
                {
                    player.dropItem(this.getHeldItemMainhand().copy(), false);
                }
            	
            	this.getHeldItemMainhand().shrink(this.getHeldItemMainhand().getCount());
        		
        		return true;	            
	        }
        }
        
        return super.processInteract(player, hand);
    }

    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    public boolean isBreedingItem(ItemStack stack) {
       return TAME_ITEMS.contains(stack.getItem());
    }
    
    @Override
    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        int i = MathHelper.floor(this.posX);
        int j = MathHelper.floor(this.getEntityBoundingBox().minY);
        int k = MathHelper.floor(this.posZ);
        BlockPos blockpos = new BlockPos(i, j, k);
        Block block = this.world.getBlockState(blockpos.down()).getBlock();
        return SpawnUtil.isAllowedDimension(this.dimension) 
        		&& block instanceof BlockLeaves || block == Blocks.GRASS || block instanceof BlockLog || block == Blocks.AIR 
        		&& this.world.getLight(blockpos) > 8 
        		&& this.getBlockPathWeight(new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ)) >= 0.0F 
            	&& this.world.getBlockState((new BlockPos(this)).down()).canEntitySpawn(this);
    }

    public void fall(float distance, float damageMultiplier)
    {
    }

    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos)
    {
    }

    /**
     * Returns true if the mob is currently able to mate with the specified mob.
     */
    public boolean canMateWith(EntityAnimal otherAnimal)
    {
        return false;
    }

    @Nullable
    public EntityAgeable createChild(EntityAgeable ageable)
    {
        return null;
    }
    
	@Override
	public double getYOffset() {
		if (this.getRidingEntity() != null && this.getRidingEntity() instanceof EntityPlayer)
			return this.getRidingEntity().height/2  - 0.35F;
		else
			return super.getYOffset();
	}

    public boolean attackEntityAsMob(Entity entityIn)
    {
        return entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), 3.0F);
    }

    @Nullable
    public SoundEvent getAmbientSound()
    {
        if(this.getSkin() == 2)
        	return FishItems.ENTITY_SEAGULL_AMBIENT;
        else
        	return FishItems.ENTITY_RAVEN_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        if(this.getSkin() == 2)
        	return FishItems.ENTITY_SEAGULL_HURT;
        else
        	return FishItems.ENTITY_RAVEN_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        if(this.getSkin() == 2)
        	return FishItems.ENTITY_SEAGULL_DEATH;
        else
        	return FishItems.ENTITY_RAVEN_DEATH;
    }

    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(SoundEvents.ENTITY_PARROT_STEP, 0.15F, 1.0F);
    }

    protected float playFlySound(float p_191954_1_)
    {
        this.playSound(SoundEvents.ENTITY_PARROT_FLY, 0.15F, 1.0F);
        return p_191954_1_ + this.flapSpeed / 2.0F;
    }

    protected boolean makeFlySound()
    {
        return true;
    }

    public SoundCategory getSoundCategory()
    {
        return SoundCategory.NEUTRAL;
    }
    
    /**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumCreatureAttribute getCreatureAttribute()
    {
        if(this.getSkin() == 3)
        	return EnumCreatureAttribute.UNDEAD;
        else
        	return EnumCreatureAttribute.UNDEFINED;
    }

    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    public boolean canBePushed()
    {
        return true;
    }

    protected void collideWithEntity(Entity entityIn)
    {
        if(Modconfig.Raven_Perch && entityIn instanceof EntityLivingBase
        		&& this.isOwner((EntityLivingBase) entityIn) 
        		&& !entityIn.isBeingRidden() 
        		&& !entityIn.isSneaking() 
        		&& !this.isSitting() 
        		&& this.ridingCooldown == 0) {
        	this.startRiding(entityIn, false);
            this.isJumping = false;
            this.navigator.clearPath();
            this.setAttackTarget((EntityLivingBase)null);
            if(entityIn instanceof EntityPlayerMP && ((EntityPlayerMP) entityIn).connection != null) {
                ((EntityPlayerMP) entityIn).connection.sendPacket(new SPacketSetPassengers(entityIn));
              }
        }
        else
        	super.collideWithEntity(entityIn);
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (this.isEntityInvulnerable(source) || this.getSkin() == 3)
        {
            return false;
        }
        else
        {
            if (this.aiSit != null)
            {
                this.aiSit.setSitting(false);
            }
            
            if(!this.world.isRemote && !this.getHeldItemMainhand().isEmpty()) {
            	this.entityDropItem(this.getHeldItemMainhand().copy(), 0.2f);
            	this.getHeldItemMainhand().shrink(100);
            }
            
            return super.attackEntityFrom(source, amount);
        }
    }
    
    /**
     * Returns whether this Entity is invulnerable to the given DamageSource.
     */
    public boolean isEntityInvulnerable(DamageSource source)
    {
        return super.isEntityInvulnerable(source) || this.isRiding();
    }

    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(SKIN_TYPE, Integer.valueOf(this.rand.nextFloat() < 0.1F ? 1 : 0));
     }
    
    public String getName() {
    	if(this.getSkin() == 2) {
    		if (this.hasCustomName())
            {
                return this.getCustomNameTag();
            }
            else
            {
            	return new TextComponentTranslation("entity.mod_lavacow.raven.seagull").getFormattedText();
            }
    	}
    	else return super.getName();
    }
    
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData entityLivingData) {
 	   if(BiomeDictionary.hasType(this.getEntityWorld().getBiome(this.getPosition()), Type.BEACH)) {
 		   this.TAME_ITEMS.clear();
 		   this.TAME_ITEMS.add(Items.FISH);
 		   this.setSkin(2);
 	   }
 	   return super.onInitialSpawn(difficulty, entityLivingData);
 	}
    
    public int getSkin()
    {
        return this.dataManager.get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType)
    {
        this.dataManager.set(SKIN_TYPE, skinType);
    }
    
    private boolean isFetching() {
    	return EntityRaven.this.TargetLocationX != -1.0F || EntityRaven.this.TargetLocationY != -1.0F || EntityRaven.this.TargetLocationZ != -1.0F;
    }
    
    @Override
    public void travel(float strafe, float vertical, float forward)
    {
    	if(!this.isSitting() || !this.getEntityWorld().getBlockState(this.getPosition().down()).isOpaqueCube())
    		super.travel(strafe, vertical, forward);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("Variant", getSkin());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        setSkin(compound.getInteger("Variant"));
    }
    
    public class AIMovetoTargetLocation extends EntityAIBase
    {
        public AIMovetoTargetLocation()
        {
            this.setMutexBits(3);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
            return EntityRaven.this.isFetching();
        }
        
        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting()
        {
            return EntityRaven.this.getDistance(EntityRaven.this.TargetLocationX, EntityRaven.this.TargetLocationY, EntityRaven.this.TargetLocationZ) > 1.0D;
        }
        
        /**
         * Determine if this AI Task is interruptible by a higher (= lower value) priority task. All vanilla AITask have
         * this value set to true.
         */
        public boolean isInterruptible()
        {
            return false;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting()
        {
            super.startExecuting();
            EntityRaven.this.navigator.clearPath();
            EntityRaven.this.aiSit.setSitting(false);
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask()
        {
            super.resetTask();
            EntityRaven.this.TargetLocationX = EntityRaven.this.TargetLocationY = EntityRaven.this.TargetLocationZ = -1.0F;
            EntityRaven.this.navigator.clearPath();
            EntityRaven.this.aiSit.setSitting(true);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask()
        {
        	EntityRaven.this.getNavigator().tryMoveToXYZ(EntityRaven.this.TargetLocationX, EntityRaven.this.TargetLocationY, EntityRaven.this.TargetLocationZ, 1.5D);
        }
    }

	@Override
	protected void dropFewItems(boolean recentlyHit, int looting) {
		if (recentlyHit) {
            int chance = rand.nextInt(3) + rand.nextInt(1 + looting);
            for (int amount = 0; amount < chance; ++amount)
                entityDropItem(new ItemStack(this.getSkin() == 2 ? Items.FEATHER : FishItems.FEATHER_BLACK), 0.0F);
		}
	}
	
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender()
    {
    	return this.getSkin() == 3 ? 15728880 : super.getBrightnessForRender();
    }

    /**
     * Gets how bright this entity is.
     */
    public float getBrightness()
    {
        return this.getSkin() == 3 ? 1.0F : super.getBrightness();
    }
	
	@Nullable
	@Override
	protected ResourceLocation getLootTable() {
		return null;
	}

    public boolean isFlying()
    {
        return (!this.onGround && !this.isRiding()) || (this.getRidingEntity() != null && !this.getRidingEntity().onGround && this.getRidingEntity().motionY < 0.0D);
    }
    
	/**
	* Called when the mob's health reaches 0.
	*/
	public void onDeath(DamageSource cause) {
		if (!this.isTamed() && !this.world.isDaytime() && this.rand.nextInt(100) < Modconfig.pScarecrow_PlagueDoctor && !getEntityWorld().isRemote) {
        	EntityScarecrow entityscarecrow = new EntityScarecrow(this.world);
        	entityscarecrow.setLocationAndAngles(this.posX, this.posY + 2.0D, this.posZ, 0.0F, 0.0F);
        	entityscarecrow.setSkin(2);
        	this.world.spawnEntity(entityscarecrow);
        	this.playSound(SoundEvents.AMBIENT_CAVE, 1.0F, 1.0F);
        	
        	for(int i = 0; i < 8; i++) {
	            double d3 = this.posX + this.rand.nextDouble();
	            double d4 = this.posY + this.rand.nextDouble();
	            double d5 = this.posZ + this.rand.nextDouble();
	            this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d3, d4, d5, 0.0D, 0.0D, 0.0D);
        	}
        	
        	entityscarecrow.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, 8 * 20, 2));
        	entityscarecrow.addPotionEffect(new PotionEffect(MobEffects.SPEED, 3 * 20, 1));
		
        	if(cause.getTrueSource() != null && cause.getTrueSource() instanceof EntityLivingBase)
        		entityscarecrow.setAttackTarget((EntityLivingBase) cause.getTrueSource());
		}

		super.onDeath(cause);
	}
}
