package com.Fishmod.mod_LavaCow.entities;

import java.util.List;
import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityFishTameable;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.init.ModMobEffects;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketSetPassengers;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityParasite extends EntityFishTameable {
    private static final DataParameter<Boolean> CLIMBING = EntityDataManager.createKey(EntityParasite.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.<Integer>createKey(EntityParasite.class, DataSerializers.VARINT);
	
	public int lifespawn;
	private boolean long_live;
	
	public EntityParasite(World worldIn)
    {
        super(worldIn);
        this.setSize(0.4F, 0.3F);
        this.long_live = false;
        this.lifespawn = Modconfig.Parasite_Lifespan * 20; // Can live for only a short time, poor little one :(
        this.experienceValue = 1;
    }
	
	public EntityParasite(World worldIn, boolean t)
    {
        super(worldIn);
        this.setSize(0.4F, 0.3F);
        this.long_live = t;
        this.lifespawn = Modconfig.Parasite_Lifespan * 20; // Can live for only a short time, poor little one :(
        this.experienceValue = 1;
    }
	
	public static void registerFixesParasite(DataFixer fixer)
    {
        EntityLiving.registerFixesMob(fixer, EntityParasite.class);
    }
	
	@Override
	protected void initEntityAI()
    {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(4, new EntityParasite.AIParasiteAttack(this));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.8D));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.applyEntityAI();
    }
	
    protected void applyEntityAI()
    {
    	this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, 0, true, true, (p_210136_0_) -> {
	  	      return !p_210136_0_.isPassenger(this) && !(this.getOwner() instanceof EntityPlayer);
	   }));
    	this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<EntityLivingBase>(this, EntityLivingBase.class, 0, true, true, (p_210136_0_) -> {
	  	      return p_210136_0_ instanceof EntityLivingBase && ((EntityLivingBase)p_210136_0_).attackable() && !(this.getOwner() instanceof EntityPlayer) 
	  	    		  && ((Modconfig.Parasite_Plague && !(p_210136_0_ instanceof EntityParasite || p_210136_0_ instanceof EntityCreeper)) || LootTableHandler.PARASITE_HOSTLIST.contains(EntityList.getKey(p_210136_0_)));
	  	   }));
    }

	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Parasite_Health);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Parasite_Attack);
    }
	
    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(CLIMBING, Boolean.FALSE);
        this.getDataManager().register(SKIN_TYPE, Integer.valueOf(this.rand.nextInt(3)));
     }
	
	@Override
	@Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        return livingdata;
    }
	
    /**
     * Returns new PathNavigateGround instance
     */
	@Override
    protected PathNavigate createNavigator(World worldIn)
    {
        return new PathNavigateClimber(this, worldIn);
    }
	
    /**
     * Called to update the entity's position/logic.
     */
	@Override
	public void onUpdate() {
		
		if(this.getRidingEntity() != null) {
			this.setPositionAndRotation(getRidingEntity().posX, getRidingEntity().posY, getRidingEntity().posZ, getRidingEntity().rotationYaw, getRidingEntity().rotationPitch);
		}
		
        if (!this.world.isRemote) {
            this.setBesideClimbableBlock(this.collidedHorizontally);
        }
			
        this.renderYawOffset = this.rotationYaw;
		super.onUpdate();
	}

    /**
     * Set the render yaw offset
     */
	@Override
    public void setRenderYawOffset(float offset)
    {
        this.rotationYaw = offset;
        super.setRenderYawOffset(offset);
    }
	
    @Override
    public boolean isOnLadder() {
        return this.isBesideClimbableBlock();
    }

    public boolean isBesideClimbableBlock() {
        return this.dataManager.get(CLIMBING);
    }

    public void setBesideClimbableBlock(boolean climbing) {
        this.dataManager.set(CLIMBING, climbing);
    }
    
    @Override
    protected SoundEvent getFallSound(int heightIn) {
        return null;
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
    }
	
	@Override
	public void onLivingUpdate()
    {
        if (this.lifespawn > 0)
        {
        	if(!long_live)this.lifespawn--;
        }
        else if (this.getSkin() == 2 && this.rand.nextInt(100) < Modconfig.pEvolveRate_Vespa || this.isTamed()) {
        	double d0 = this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue();
        	List<EntityPlayer> list = this.world.getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(d0, d0, d0));

        	if(!list.isEmpty() || this.isTamed()) {
            	this.lifespawn = 5 * 20;
        		
        		if (!this.world.isRemote) {
                	this.playSound(FishItems.ENTITY_PARASITE_WEAVE, 1.0F, 1.0F);
                	
		        	EntityVespaCocoon pupa = new EntityVespaCocoon(this.world);
		    		pupa.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.prevRotationPitch);
		    		pupa.setSkin(0);
		    		
		    		if (this.isTamed() && this.getOwner() instanceof EntityPlayer) {
		    			pupa.setTamedBy((EntityPlayer) this.getOwner());
		    			pupa.setCustomNameTag(this.getCustomNameTag());
		    		}
		    		
		    		this.world.spawnEntity(pupa);
        		}
        		this.setDead();

        	}
        	else
        		this.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageIsAbsolute() , this.getMaxHealth());
        }
        else
        {
        	this.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageIsAbsolute() , this.getMaxHealth());
        }
        	
        
        if (this.getRidingEntity() != null && this.getRidingEntity() instanceof EntityLivingBase && this.isServerWorld()) {
        	Entity mount = this.getRidingEntity();

        	if (((EntityLivingBase) mount).getActivePotionEffect(ModMobEffects.INFESTED) == null) {
        		this.dismountEntity(mount);
        		this.dismountRidingEntity();     		
        		this.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageIsAbsolute() , this.getMaxHealth());
        	}        	
        	else if(this.getRidingEntity().isBurning()) {
        		this.setFire(20);
        		this.dismountEntity(mount);
        		this.dismountRidingEntity();
        		
        	}
        }
        else if (getRidingEntity() == null && this.long_live)
        	this.long_live = false;
               
        super.onLivingUpdate();
    }
	
	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        ItemStack itemstack = player.getHeldItem(hand);

        if (hand == EnumHand.MAIN_HAND && !this.isTamed() && itemstack.isEmpty() && player.isSneaking() && Modconfig.Parasite_Pickup)
        {
        	player.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
        	
        	if (!player.inventory.addItemStackToInventory(new ItemStack(FishItems.PARASITE_ITEM, 1, this.getSkin())))
            {
                player.dropItem(new ItemStack(FishItems.PARASITE_ITEM, 1, this.getSkin()), false);
            }
        	this.setDead();
            return true;
        }
        else
        {
            return super.processInteract(player, hand);
        }
    }
	
	@Override
	public double getYOffset() {
		if (this.getRidingEntity() != null && (this.getRidingEntity() instanceof EntityPlayer || this.getRidingEntity() instanceof EntityZombie))
			return this.getRidingEntity().height/2  - 0.85F;
		else if (this.getRidingEntity() != null)
			return this.getRidingEntity().height * 0.65D - 1.0D;
		else
			return super.getYOffset();
	}
	
	@Override
	public boolean attackEntityAsMob(Entity entity) {
		if (super.attackEntityAsMob(entity)) {
			if ((entity instanceof EntityPlayer || entity instanceof EntityZombie || Modconfig.Parasite_Plague) && Modconfig.Parasite_Attach/* && !entity.isBeingRidden()*/) {
				if(entity instanceof EntityPlayer) {
					((EntityLivingBase) entity).addPotionEffect(new PotionEffect(ModMobEffects.INFESTED, 8*20, 0));
				}				
			}
			
			if(this.getSkin() == 2)((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.POISON, 4*20, 0));
			return true;
		}
		return false;
	}
	
    protected void collideWithEntity(Entity entityIn)
    {
    	if (!this.world.isRemote && entityIn instanceof EntityLivingBase && ((entityIn instanceof EntityPlayer && !((EntityPlayer)entityIn).isCreative()) || LootTableHandler.PARASITE_HOSTLIST.contains(EntityList.getKey(entityIn)) || Modconfig.Parasite_Plague) && Modconfig.Parasite_Attach && !(entityIn instanceof EntityParasite)) {
    		((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(ModMobEffects.INFESTED, 8*20, 0));
    		this.startRiding(entityIn);
    		this.getServer().getPlayerList().sendPacketToAllPlayers(new SPacketSetPassengers(entityIn));
            this.isJumping = false;
            this.navigator.clearPath();
            this.long_live = true;
            this.setAttackTarget((EntityLivingBase) entityIn);
        }
        else
        	super.collideWithEntity(entityIn);
    }
	
	public static EntityParasite gotParasite(List<Entity> listIn)
	{
		for(Entity C : listIn)
			if(C instanceof EntityParasite)return (EntityParasite)C;
		
		return null;
	}
	
    public int getSkin()
    {
        return this.dataManager.get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType)
    {
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
     * Handler for {@link World#setEntityState}
     */
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
    	super.handleStatusUpdate(id);
    }
    
	@Override
    public float getEyeHeight()
    {
        return 0.1F;
    }
	
	@Override
    protected SoundEvent getAmbientSound()
    {
        return FishItems.ENTITY_PARASITE_AMBIENT;
    }
	
	@Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return FishItems.ENTITY_PARASITE_HURT;
    }

	@Override
    protected SoundEvent getDeathSound()
    {
        return FishItems.ENTITY_PARASITE_DEATH;
    }
	
	@Override
	protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(SoundEvents.ENTITY_SILVERFISH_STEP, 0.15F, 1.0F);
    }
	
    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
    	switch(this.getSkin()) {
        default: 
	        case 0: 
	        	return LootTableHandler.PARASITE;
	        case 1: 
	            return LootTableHandler.PARASITE1;
	        case 2: 
	        	return LootTableHandler.PARASITE2;
	    } 
    }
    
    static class AIParasiteAttack extends EntityAIAttackMelee
    {
        public AIParasiteAttack(EntityParasite parasite)
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
    protected boolean canDropLoot() {
       return this.lifespawn > 0;
    }
}
