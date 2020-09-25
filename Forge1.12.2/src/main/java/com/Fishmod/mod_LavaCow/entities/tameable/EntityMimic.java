package com.Fishmod.mod_LavaCow.entities.tameable;

import java.util.UUID;
import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.tileentity.TileEntityMimic;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityMimic extends EntityFishTameable{
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.<Integer>createKey(EntityMimic.class, DataSerializers.VARINT);

	private boolean isAggressive = false;
	private int AttackTimer = 40;
	public NonNullList<ItemStack> inventory;
	//public TileEntityMimic Tileentitymimic = null;
	
	public EntityMimic(World worldIn)
    {
        super(worldIn);
        this.setSize(1.0F, 1.0F);
        this.inventory = NonNullList.<ItemStack>withSize(27, ItemStack.EMPTY);
        //this.Tileentitymimic = new TileEntityMimic(this);
        this.setCanPickUpLoot(true);
        this.setTamed(false);
    }
	
    protected void initEntityAI()
    {
    	//this.aiSit = new EntityAISit(this);
    	super.initEntityAI();
    	
    	this.tasks.addTask(1, this.aiSit);
    	//this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        //this.tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
        //this.tasks.addTask(3, new EntityAITempt(this, 1.25D, Ingredient.fromItems(FishItems.MOUSSE), false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        //this.tasks.addTask(6, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        //this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        //this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        //this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI()
    {
    	this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
    	this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false, new Class[0]));
        /*this.targetTasks.addTask(4, new EntityAITargetNonTamed<>(this, EntityPlayer.class, false, (p_210130_0_) -> {
            return this.world.getDifficulty() != EnumDifficulty.PEACEFUL;
         }));*/
    	//this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
        //this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<EntityVillager>(this, EntityVillager.class, true));
        //this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<EntityIronGolem>(this, EntityIronGolem.class, true));
	}
    
    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(SKIN_TYPE, Integer.valueOf((4 + this.rand.nextInt(5)) % 6));
     }
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        
        if (this.isTamed()) 
        {
        	//this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
        	this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        	this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
        } 
        else
        {
        	//this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(4.0D);
        	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        	this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.22D);
        	this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
        }
        
    }
    
    @Override
    public boolean getCanSpawnHere() {
    	
    	boolean is_near_chest = false;
        int dx = MathHelper.floor(this.posX);
        int dy = MathHelper.floor(this.getEntityBoundingBox().minY);
        int dz = MathHelper.floor(this.posZ);
        int r = 2;
        
        for(BlockPos C : BlockPos.getAllInBox(new BlockPos(dx - r, dy - r, dz - r), new BlockPos(dx + r, dy + r, dz + r)))
        	if(this.getEntityWorld().getBlockState(C).getBlock() == Blocks.CHEST)is_near_chest = true;
        
        //if(worldIn.getBlockState(new BlockPos(dx, dy, dz).down()).getBlock() == Blocks.COBBLESTONE)is_near_chest = true;
        
        return SpawnUtil.isAllowedDimension(this.dimension) && is_near_chest && super.getCanSpawnHere();
     }
    
    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    @Override
    public int getMaxSpawnedInChunk() {
       return 1;
    }
    
    public boolean canBreatheUnderwater() {
        return true;
     }
    
    public void setTamed(boolean tamed) {
        super.setTamed(tamed);
        
        if (tamed) 
        {
        	//this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
        	this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        	this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
        	this.setSilent(false);
        } 
        else
        {
        	//this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(4.0D);
        	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        	this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.22D);
        	this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
        }
     }
    
    private void hasSpace(ItemStack itemstackIn)
    {
    	if (!getEntityWorld().isRemote) {
			for (int i = 0; i < this.inventory.size();i++)
				/*if (this.inventory.get(i).isEmpty() || this.inventory.get(i).getItem() == itemstackIn.getItem())
				{
					int j = this.inventory.get(i).getCount();
					this.inventory.set(i, new ItemStack(itemstackIn.getItem(), Math.min(itemstackIn.getItem().getItemStackLimit(itemstackIn), itemstackIn.getCount() + j)));
					itemstackIn.shrink(itemstackIn.getItem().getItemStackLimit(itemstackIn) - j); 
					if(itemstackIn.getCount() <= 0)return;
				}*/
				if (this.inventory.get(i).isEmpty()) {
					this.inventory.set(i, new ItemStack(Items.EMERALD));
					this.inventory.set(i, itemstackIn.copy());
					itemstackIn.shrink(itemstackIn.getCount());
					return;
				}
			
			/*if(this.getSkin() == this.getVoidSkin()) {
				for (int i = 0; i < this.inventory.size();i++) {
					if (!this.inventory.get(i).isEmpty())
						((EntityPlayer) this.getOwner()).getInventoryEnderChest().addItem(this.inventory.get(i));
					//this.inventory.get(i).shrink(1);
				}
			}*/
				
    	}
    }
    
    private void EmergencyFood()
    {
    	if (!getEntityWorld().isRemote)
			for (int i = 0; i < this.inventory.size();i++)
				if (this.isBreedingItem(this.inventory.get(i)))
				{
					ItemFood itemfood = (ItemFood)this.inventory.get(i).getItem();
					this.playSound(SoundEvents.ENTITY_GENERIC_EAT, 0.4F, 1.0F);
                    this.heal((float)itemfood.getHealAmount(this.inventory.get(i)));
                    this.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 8*20, 0));
                    this.inventory.set(i, new ItemStack(this.inventory.get(i).getItem(), this.inventory.get(i).getCount() - 1));
				}
    }
	
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void onLivingUpdate()
    {
		super.onLivingUpdate();
		if(AttackTimer > 0)AttackTimer--;
		if(!getEntityWorld().isRemote && !isAggressive && !this.isTamed())
		{
			this.posX = MathHelper.floor(posX) + 0.5;
			this.posY = MathHelper.floor(posY);
			this.posZ = MathHelper.floor(posZ) + 0.55;
			this.rotationYaw = prevRotationYaw = 0F;
			this.renderYawOffset = prevRenderYawOffset = 0F;
			//this.motionX = 0.0D;
			//this.motionY = 0.0D;
			//this.motionZ = 0.0D;

			if (getEntityWorld().getBlockState(getPosition().down()) instanceof BlockAir)
				posY -= 1;
			this.setSilent(true);
			this.setAIMoveSpeed(0.0F);
			//this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.0D);
		}
		else if(this.getAttackingEntity() != null)
		{
			AttackTimer = 200;
			this.setSilent(false);
			this.setAIMoveSpeed(0.19F);
			//this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.19D);
		}
		
		if(!this.isTamed() && this.getAttackTarget() != null && this.getDistance(this.getAttackTarget()) > this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue()) {
			this.setAttackTarget((EntityLivingBase)null);
		}
		
		for(ItemStack S: this.getHeldEquipment())
			if(!S.isEmpty())this.hasSpace(S);
		
		if(this.isTamed() && this.getHealth() <= this.getMaxHealth() * 0.5F)this.EmergencyFood();
		
		if(this.getSkin() == getVoidSkin() && this.ticksExisted % 100 == 0) {
            for (int i = 0; i < 8; ++i)
            {
                int j = rand.nextInt(2) * 2 - 1;
                int k = rand.nextInt(2) * 2 - 1;
                double d0 = (double)this.posX + 0.5D + 0.25D * (double)j;
                double d1 = (double)((float)this.posY + rand.nextFloat());
                double d2 = (double)this.posZ + 0.5D + 0.25D * (double)k;
                double d3 = (double)(rand.nextFloat() * (float)j);
                double d4 = ((double)rand.nextFloat() - 0.5D) * 0.125D;
                double d5 = (double)(rand.nextFloat() * (float)k);
                this.world.spawnParticle(EnumParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5);
            }
		}
    }
	
	@Override
    public void travel(float strafe, float vertical, float forward) {
		if(!isAggressive && !this.isTamed()) {
            this.motionX = 0.0D;
            this.motionY = 0.0D;
            this.motionZ = 0.0D;
		}
		else
			super.travel(strafe, vertical, forward);
	}
    
    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
    	Entity entity = source.getTrueSource();
    	if (this.aiSit != null) {
    		this.aiSit.setSitting(false);
    	}

    	if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow)) {
    		amount = (amount + 1.0F) / 2.0F;
    	}

    	return super.attackEntityFrom(source, amount);
    }

    public boolean attackEntityAsMob(Entity entityIn)
    {
    	boolean flag = super.attackEntityAsMob(entityIn);
        if (flag) {
        	this.playSound(FishItems.ENTITY_ZOMBIEPIRANHA_ATTACK, 1.0F, 1.0F);
        	this.applyEnchantments(this, entityIn);
        }

        return flag;
    }
    
    protected void doSitCommand(EntityPlayer playerIn) {
    	super.doSitCommand(playerIn);
    	if(!this.world.isRemote)
    		this.aiSit.setSitting(true);
    }
    
    protected void doFollowCommand(EntityPlayer playerIn) {
    	super.doFollowCommand(playerIn);
    	
    	if(this.getSkin() == this.getVoidSkin()) {
      	   this.setCanPickUpLoot(false);
     	   
      	   for (ItemStack is : this.inventory)
  				if (!is.isEmpty()) {
  					this.entityDropItem(is.copy(), 0.2F);
  					is.shrink(is.getCount());
  				}
    	}
    	
    	if(!this.world.isRemote)
    		this.aiSit.setSitting(false);
    }
    
    
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        Item item = itemstack.getItem();
        
        if (itemstack.getItem() == Items.SPAWN_EGG)
        {
            return super.processInteract(player, hand);
        }
        else if(this.isTamed())
        {
        	if (player.isSneaking())
        	{
        		if(!this.world.isRemote) {
        			if(this.getSkin() == getVoidSkin()) {	
        				player.displayGUIChest(player.getInventoryEnderChest());
        				this.playSound(SoundEvents.BLOCK_ENDERCHEST_OPEN, 1.0F, 1.0F);
        			}
        			else
        				player.displayGUIChest(new TileEntityMimic(this));
                	}
                    return true;
                }

            if (!itemstack.isEmpty())
            {
            	if (itemstack.interactWithEntity(player, this, hand))
            	{
            		return true;
            	}
            	
            	if (item instanceof ItemFood) {
                    ItemFood itemfood = (ItemFood)item;
                    if (itemfood.isWolfsFavoriteMeat() && this.dataManager.get(DATA_HEALTH) < this.getMaxHealth()) {
                       if (!player.capabilities.isCreativeMode) {
                          itemstack.shrink(1);
                       }
                       //System.out.println(this.dataManager.get(DATA_HEALTH_ID) + " " + this.getMaxHealth());
                       this.playSound(SoundEvents.ENTITY_GENERIC_EAT, 0.4F, 1.0F);
                       this.heal((float)itemfood.getHealAmount(itemstack));
                       return true;
                    }
            	}
                else if (this.isOwner(player) && this.getSkin() != getVoidSkin() && item == Items.ENDER_EYE) {
             	   //System.out.println("O_O 3");
             	   if (!player.capabilities.isCreativeMode) {
                        itemstack.shrink(1);
                     }
             	   this.setSkin(getVoidSkin());
             	   this.setCanPickUpLoot(false);
             	   
 	       			for (ItemStack is : this.inventory)
 	    				if (!is.isEmpty()) {
 	    					this.entityDropItem(is.copy(), 0.2F);
 	    					is.shrink(is.getCount());
 	    				}

 	       			return true;
                }
            	/*else if(!this.world.isRemote && this.isOwner(player)) {
 	               if (item == Items.STICK) {
 	            	   this.aiSit.setSitting(!this.isSitting());
 	            	   this.isJumping = false;
 	            	   this.navigator.clearPath();
 	            	   this.setAttackTarget((EntityLivingBase)null);
 		                   
 	            	   if(this.isSitting()) player.sendStatusMessage(new TextComponentTranslation(this.getName()).appendSibling(new TextComponentTranslation("command.mod_lavacow.following")), true);
 	            	   else player.sendStatusMessage(new TextComponentTranslation(this.getName()).appendSibling(new TextComponentTranslation("command.mod_lavacow.sitting")), true);
 	            	   
 	                   return true;
 	               }
            	}*/
            }

            if (this.isChild())
            {
                return super.processInteract(player, hand);
            }
            else if (itemstack.interactWithEntity(player, this, hand))
            {
                return true;
            }
        }
        
        /*System.out.print(itemstack.interactWithEntity(player, this, hand));
        if(itemstack.interactWithEntity(player, this, hand)) {
        	
        	
        	return true;
        }

        if (this.isTamed() && hand == player.getActiveHand()) {
	            if(itemstack.interactWithEntity(player, this, hand)) {
	            	return true;
	            }
	            else if (item instanceof ItemFood) {
                  ItemFood itemfood = (ItemFood)item;
                  if (itemfood.isWolfsFavoriteMeat() && this.dataManager.get(DATA_HEALTH_ID) < this.getMaxHealth()) {
                     if (!player.capabilities.isCreativeMode) {
                        itemstack.shrink(1);
                     }
                     //System.out.println(this.dataManager.get(DATA_HEALTH_ID) + " " + this.getMaxHealth());
                     this.playSound(SoundEvents.ENTITY_GENERIC_EAT, 0.4F, 1.0F);
                     this.heal((float)itemfood.getHealAmount(itemstack));
                     return true;
                  }

               }
               else if (this.isOwner(player) && this.getSkin() != getVoidSkin() && item == Items.ENDER_EYE) {
            	   //System.out.println("O_O 3");
            	   if (!player.capabilities.isCreativeMode) {
                       itemstack.shrink(1);
                    }
            	   this.setSkin(getVoidSkin());
            	   this.setCanPickUpLoot(false);
            	   
	       			for (ItemStack is : this.inventory)
	    				if (!is.isEmpty())
	    					this.entityDropItem(is, 0.2F);

	       			return true;
               }
               else if(!this.world.isRemote && this.isOwner(player)) {
	               if (item == Items.STICK) {
	            	   this.aiSit.setSitting(!this.isSitting());
	            	   this.isJumping = false;
	            	   this.navigator.clearPath();
	            	   this.setAttackTarget((EntityLivingBase)null);
		                   
	            	   if(this.isSitting()) player.sendStatusMessage(new TextComponentTranslation(this.getName()).appendSibling(new TextComponentTranslation("command.mod_lavacow.following")), true);
	            	   else player.sendStatusMessage(new TextComponentTranslation(this.getName()).appendSibling(new TextComponentTranslation("command.mod_lavacow.sitting")), true);
	            	   
	                   return true;
	               }
	               else if(player.isSneaking() && item != FishItems.FISSIONPOTION && (Modconfig.Mimic_noGUI || !player.isSneaking()))
	               {
	            	   //System.out.println("O_O 4");
	            	   if(this.getSkin() == getVoidSkin()) {	
		            	   player.displayGUIChest(player.getInventoryEnderChest());
		            	   this.playSound(SoundEvents.BLOCK_ENDERCHEST_OPEN, 1.0F, 1.0F);
	            	   }
		               else
		            	   player.displayGUIChest(new TileEntityMimic(this));
	
		               return super.processInteract(player, hand);
	               }
               }
               //else System.out.println("O_O throw");
        }*/
        
        if(!this.isTamed() && this.getDistanceSq(player) < 2.0D) {
	        this.playSound(SoundEvents.BLOCK_CHEST_OPEN, 1.0F, 1.0F);
	        this.playSound(FishItems.ENTITY_MIMIC_AMBIENT, 0.4F, 1.0F);
	        this.setAttackTarget(player);
        }

        return super.processInteract(player, hand);
     }
    
    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    public boolean isBreedingItem(ItemStack stack) {
       Item item = stack.getItem();
       return item == FishItems.CANEROTTENMEAT;
    }
    
    /**
     * Returns true if the mob is currently able to mate with the specified mob.
     */
    public boolean canMateWith(EntityAnimal otherAnimal) {
       if (otherAnimal == this) {
          return false;
       } else if (!this.isTamed()) {
          return false;
       } else if (!(otherAnimal instanceof EntityMimic)) {
          return false;
       } else {
          EntityMimic entitywolf = (EntityMimic)otherAnimal;
          if (!entitywolf.isTamed()) {
             return false;
          } else if (entitywolf.isSitting()) {
             return false;
          } else {
             return this.isInLove() && entitywolf.isInLove();
          }
       }
    }
    
    protected void updateAITasks()
    {
        super.updateAITasks();
        this.dataManager.set(DATA_HEALTH, Float.valueOf(this.getHealth()));
        if((this.getAttackTarget() != null || AttackTimer > 0 || this.recentlyHit > 58))
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
    
    public int getSkin()
    {
        return ((Integer)this.dataManager.get(SKIN_TYPE)).intValue();
    }

    public void setSkin(int skinType)
    {
        this.dataManager.set(SKIN_TYPE, Integer.valueOf(skinType));
    }
	
	public int getVoidSkin() {
		return 3;//RenderMimic.getVoidSkin();
	}
    
	@SideOnly(Side.CLIENT)
    public boolean isAggressive()
    {
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
        return 0.7F;
    }
    
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		inventory = NonNullList.<ItemStack>withSize(this.inventory.size(), ItemStack.EMPTY);
		if (compound.hasKey("Items"))
			ItemStackHelper.loadAllItems(compound, inventory);
		this.setSkin(compound.getInteger("Variant"));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		ItemStackHelper.saveAllItems(compound, inventory, false);
		compound.setInteger("Variant", getSkin());
	}
    
    @Override
    protected SoundEvent getAmbientSound()
    {
        return FishItems.ENTITY_MIMIC_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return FishItems.ENTITY_MIMIC_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return FishItems.ENTITY_MIMIC_DEATH;
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
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    public boolean canBePushed()
    {
        return false;
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.ARTHROPOD;
    }
    
    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableHandler.MIMIC;
    }
    
    /**
     * Entity won't drop items or experience points if this returns false
     */
    @Override
    protected boolean canDropLoot() {
       return !this.isTamed();
    }
	
	public EntityMimic createChild(EntityAgeable ageable) {
		EntityMimic entity = new EntityMimic(this.world);
		UUID uuid = this.getOwnerId();
		if (uuid != null) {
			entity.setOwnerId(uuid);
			entity.setTamed(true);
			entity.setHealth(this.getMaxHealth());
			entity.setSkin(this.rand.nextBoolean() ? this.getSkin() : ((EntityMimic)ageable).getSkin());
			if(entity.getSkin() == 3)entity.setSkin(0);
		}

		return entity;
	}
	
	/**
	* Called when the mob's health reaches 0.
	*/
	public void onDeath(DamageSource cause) {
		if (!getEntityWorld().isRemote) {
			for (ItemStack is : this.inventory)
				if (!is.isEmpty())
					this.entityDropItem(is, 0.2F); 
			/*if(!this.isTamed()) {
				this.entityDropItem(LootTableList.CHESTS_JUNGLE_TEMPLE, 0.0F);
			}	*/			
		}

		super.onDeath(cause);
	}
}
