package com.Fishmod.mod_LavaCow.entities;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;
import com.google.common.base.Optional;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketSetPassengers;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityParasite extends EntitySpider{
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.<Integer>createKey(EntityParasite.class, DataSerializers.VARINT);
	private static final DataParameter<Optional<UUID>> RIDING_ENTITY = EntityDataManager.<Optional<UUID>>createKey(EntityParasite.class, DataSerializers.OPTIONAL_UNIQUE_ID);
	
	public int lifespawn;
	private boolean long_live;
	
	public EntityParasite(World worldIn)
    {
        super(worldIn);
        this.setSize(0.8F, 0.3F);
        this.long_live = false;
        this.lifespawn = 16*20;//Can live for only 16secs, poor little one:(
        this.experienceValue = 1;
    }
	
	public EntityParasite(World worldIn, boolean t)
    {
        super(worldIn);
        this.setSize(0.4F, 0.3F);
        this.long_live = t;
        this.lifespawn = 16*20;//Can live for only 16secs, poor little one:(
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
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.applyEntityAI();
    }
	
    protected void applyEntityAI()
    {
    	this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
    	this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<EntityZombie>(this, EntityZombie.class, true));
        if(Modconfig.Parasite_Plague)
        	this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<EntityLivingBase>(this, EntityLivingBase.class, 0, true, true, (p_210136_0_) -> {
        	      return p_210136_0_ instanceof EntityLivingBase 
        	    		  && ((EntityLivingBase)p_210136_0_).attackable() 
        	    		  && !(p_210136_0_ instanceof EntityParasite || p_210136_0_ instanceof EntityCreeper);
        	   }));
    }

	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
    }
	
    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(SKIN_TYPE, Integer.valueOf(this.rand.nextInt(3)));
        this.getDataManager().register(RIDING_ENTITY, Optional.absent());
     }
	
	@Override
	@Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        return livingdata;
    }
	
    /**
     * Called to update the entity's position/logic.
     */
	@Override
	public void onUpdate() {
		
        /*if (this.isEvolving && getRidingEntity() != null && this.ticksExisted % 3 == 0 && this.getEntityWorld().isRemote) {
        		mod_LavaCow.PROXY.spawnCustomParticle("spore", world, this.posX + (double)(new Random().nextFloat() * this.width) - (double)this.width * 0.5D, this.posY + (double)(new Random().nextFloat() * this.height), this.posZ + (double)(new Random().nextFloat() * this.width) - (double)this.width * 0.5D, 0.0D, 5.0D, 0.0D, 0.0F, 0.5F, 0.2F);
        }*/
		if(this.getRidingEntity() != null) {
			//Entity entity = this.getRidingEntity();
			this.setPositionAndRotation(getRidingEntity().posX, getRidingEntity().posY, getRidingEntity().posZ, getRidingEntity().rotationYaw, getRidingEntity().rotationPitch);
			//this.dismountRidingEntity();
			//this.startRiding(entity);
			//System.out.println("OXO" + this.posX + " " + this.posY + " " + this.posZ);
			//System.out.println("OAO" + this.getRidingEntity().posX + " " + this.getRidingEntity().posY + " " + this.getRidingEntity().posZ);
		}
			
		super.onUpdate();
	}
	
	@Override
	public void onLivingUpdate()
    {
        if (this.lifespawn > 0)
        {
        	if(!long_live)this.lifespawn--;
        }
        /*else if(this.isEvolving) {
        	if (!this.world.isRemote) {
	        	//this.playSound(SoundEvents.ENTITY_SLIME_SQUISH, 1.0F, 1.0F);
	        	EntityVespaCocoon pupa = new EntityVespaCocoon(this.world);
	    		pupa.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
	    		this.world.spawnEntity(pupa);
	    		//if(this.getAttackTarget() != null)pupa.setAttackTarget(this.getAttackTarget());
	        	this.setDead();
        	}
        }*/
        else if (this.getSkin() == 2 && this.rand.nextInt(100) < Modconfig.pSpawnRate_Vespa) {
        	//System.out.println("OXOXOXOXOXOXOXOXO");
        	double d0 = this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue();
        	List<EntityPlayer> list = this.world.getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(d0, d0, d0));

        	if(!list.isEmpty()) {
            	this.lifespawn = 5 * 20;
            	/*this.isEvolving = true;
            	this.setAttackTarget(null);
            	this.setNoAI(true);
            	this.world.setEntityState(this, (byte)4);*/
        		
        		if (!this.world.isRemote) {
		        	EntityVespaCocoon pupa = new EntityVespaCocoon(this.world);
		    		pupa.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
		    		this.world.spawnEntity(pupa);
		    		
	        		for(EntityPlayer E : list)
	            		E.sendMessage(new TextComponentTranslation("entity.mod_lavacow.parasite.name").appendSibling(new TextComponentTranslation("message.mod_lavacow.metamorphosis"))); 
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
        	
        
        if (getRidingEntity() != null && this.isServerWorld()) {
        	this.setPositionAndRotation(getRidingEntity().posX, getRidingEntity().posY, getRidingEntity().posZ, getRidingEntity().rotationYaw, getRidingEntity().rotationPitch);
        	//this.setRotation(getRidingEntity().rotationYaw, 0F);
        	if (this.getRidingEntity() instanceof EntityPlayer && ((EntityLivingBase) getRidingEntity()).getActivePotionEffect(MobEffects.HUNGER) == null) {
        		this.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageIsAbsolute() , this.getMaxHealth());
                if(this.getRidingEntity() instanceof EntityPlayerMP && ((EntityPlayerMP) this.getRidingEntity()).connection != null) {
                    ((EntityPlayerMP) this.getRidingEntity()).connection.sendPacket(new SPacketSetPassengers(this.getRidingEntity()));
                  }
        	}
        	
        	/*if	(this.isEvolving) {
        		this.dismountRidingEntity();
        		this.long_live = false;
        	}*/
        }
        else if (getRidingEntity() == null && this.long_live)
        	this.long_live = false;
               
        super.onLivingUpdate();
    }
	
	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        ItemStack itemstack = player.getHeldItem(hand);

        if (itemstack.isEmpty())
        {
        	player.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
        	//player.setHeldItem(hand, new ItemStack(FishItems.PARASITE_ITEM, 1));
        	
        	if (!player.inventory.addItemStackToInventory(new ItemStack(FishItems.PARASITE_ITEM, 1)))
            {
                player.dropItem(new ItemStack(FishItems.PARASITE_ITEM, 1), false);
            }
        	this.setDead();
            return true;
        }
        else
        {
            return super.processInteract(player, hand);
        }
    }
	
    /*public boolean attackEntityAsMob(Entity entityIn)
    {
        boolean flag = super.attackEntityAsMob(entityIn);
        ((EntityLiving)entityIn).addPotionEffect(new PotionEffect(MobEffects.HUNGER, 8*20, 0));
		Render<Entity> renderer =  Minecraft.getMinecraft().getRenderManager().getEntityClassRenderObject(EntityZombie.class);
		
		if (renderer instanceof RenderLivingBase) {
			((RenderLivingBase<?>) renderer).addLayer(new LayerParasite(((RenderLivingBase<?>) renderer)));
		
		}

        return flag;
    }*/
	
	@Override
	public double getYOffset() {
		if (this.getRidingEntity() != null && (this.getRidingEntity() instanceof EntityPlayer || this.getRidingEntity() instanceof EntityZombie))
			return this.getRidingEntity().height/2  - 0.85F;
		else if (this.getRidingEntity() != null)
			return this.getRidingEntity().height * 0.65D - 1.0D;
		else
			return super.getYOffset();
	}
	
    /**
     * Transforms the entity's current yaw with the given Rotation and returns it. This does not have a side-effect.
     */
	/*@Override
    public float getRotatedYaw(Rotation transformRotation)
    {
        //float f = MathHelper.wrapDegrees(this.rotationYaw);
        if (getRidingEntity() != null && getRidingEntity() instanceof EntityPlayer)return getRidingEntity().getRotatedYaw(transformRotation) + 180.0f;        
        else return super.getRotatedYaw(transformRotation);
    }*/
	
	@Override
	public boolean attackEntityAsMob(Entity entity) {
		if (super.attackEntityAsMob(entity)) {
			if ((entity instanceof EntityPlayer || entity instanceof EntityZombie || Modconfig.Parasite_Plague) && Modconfig.Parasite_Attach/* && !entity.isBeingRidden()*/) {
				if(entity instanceof EntityPlayer) {
					((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.HUNGER, 8*20, 0));
					//setPersistanceOnPlayer((EntityPlayer) entity).getCommandSenderEntity().getName());
				}				
				/*EntityParasite entityParasite = new EntityParasite(getEntityWorld(), true);
				entityParasite.setPosition(entity.posX, entity.posY, entity.posZ);
				entityParasite.startRiding(entity, true);
				getEntityWorld().spawnEntity(entityParasite);
				this.setDead();*/
				//this.long_live = true;
				//this.startRiding(entity, false);
	            //this.navigator.clearPath();
				//this.setPosition(entity.posX, entity.posY + 2.0D, entity.posZ);
			}
			
			if(this.getSkin() == 2)((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.POISON, 4*20, 0));
			return true;
		}
		return false;
	}
	
    protected void collideWithEntity(Entity entityIn)
    {
    	if (entityIn instanceof EntityLivingBase && ((entityIn instanceof EntityPlayer && !((EntityPlayer)entityIn).isCreative()) || entityIn instanceof EntityZombie || Modconfig.Parasite_Plague) && Modconfig.Parasite_Attach && !(entityIn instanceof EntityParasite)) {
    		((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.HUNGER, 8*20, 0));
    		this.startRiding(entityIn);
            this.isJumping = false;
            this.navigator.clearPath();
            this.long_live = true;
            this.setAttackTarget((EntityLivingBase) entityIn);
            this.setRiddenId(entityIn.getCommandSenderEntity().getUniqueID());
            if(this.getRidingEntity() instanceof EntityPlayerMP && ((EntityPlayerMP) this.getRidingEntity()).connection != null) {
                ((EntityPlayerMP) this.getRidingEntity()).connection.sendPacket(new SPacketSetPassengers(this.getRidingEntity()));
              }
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
        return ((Integer)this.dataManager.get(SKIN_TYPE)).intValue();
    }

    public void setSkin(int skinType)
    {
        this.dataManager.set(SKIN_TYPE, Integer.valueOf(skinType));
    }
	
    @Nullable
    public UUID getRiddenId()
    {
        return (UUID)(this.dataManager.get(RIDING_ENTITY)).orNull();
    }

    public void setRiddenId(@Nullable UUID p_184754_1_)
    {
        this.dataManager.set(RIDING_ENTITY, Optional.fromNullable(p_184754_1_));
    }
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("Variant", getSkin());
		
        if (this.getRiddenId() == null)
        {
        	nbt.setString("RiddenUUID", "");
        }
        else
        {
        	nbt.setString("RiddenUUID", this.getRiddenId().toString());
        }
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setSkin(nbt.getInteger("Variant"));
		
        String s = nbt.getString("RiddenUUID");

        if (!s.isEmpty())
        {
        	this.setRiddenId(UUID.fromString(s));
        	EntityPlayer entity = this.world.getPlayerEntityByUUID(this.getRiddenId());
        	if(entity != null)this.startRiding(entity);
        }
	}
	
    /**
     * Handler for {@link World#setEntityState}
     */
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
    	/*if (id == 4) 
    	{
            this.isEvolving = true;
            this.lifespawn = 5 * 20;
        }
        else
        {*/
            super.handleStatusUpdate(id);
        //}
    }
    
	@Override
    public float getEyeHeight()
    {
        return 0.1F;
    }
	
	@Override
	protected boolean canTriggerWalking()
    {
        return false;
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
        return LootTableHandler.PARASITE;
    }
    
    static class AIParasiteAttack extends EntityAIAttackMelee
    {
        public AIParasiteAttack(EntityParasite spider)
        {
            super(spider, 1.0D, true);
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
            return (double)(attackTarget.width + 2.0F);
        }
    }

}
