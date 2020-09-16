package com.Fishmod.mod_LavaCow.entities;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.ai.WendigoAITargetItem;
import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.init.ModEnchantments;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;
import com.google.common.base.Predicate;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityWendigo extends EntityMob{
	
	private boolean isAggressive = false;
	private int attackTimer;
	/** set the Cooldown to pounce attack*/
	private int jumpTimer;
	/** 40: Attack with both hands 41: right hand 42: left hand */
	public byte AttackStance;
	//private final NonNullList<ItemStack> inventoryHands = NonNullList.withSize(8, ItemStack.EMPTY);
	
	public EntityWendigo(World worldIn)
    {
        super(worldIn);
        this.setSize(1.6F, 2.6F);
        //this.setCanPickUpLoot(true);
        //this.getHeldEquipment() = NonNullList.withSize(16, ItemStack.EMPTY);
        this.experienceValue = 20;
    }
	
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        if(!Modconfig.SunScreen_Mode)this.tasks.addTask(1, new EntityAIFleeSun(this, 1.0D));
        this.tasks.addTask(2, new AIWendigoLeapAtTarget(this, 0.7F));
        this.tasks.addTask(3, new EntityAIAttackMelee(this, 1.25D, false));  
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI()
    {
    	this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
    	this.targetTasks.addTask(2, new WendigoAITargetItem<>(this, EntityItem.class, true));
    	this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, false));
    	if(Modconfig.Wendigo_AnimalAttack)this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<>(this, EntityAgeable.class, 0, false, true, new Predicate<Entity>()
        {
            public boolean apply(@Nullable Entity p_apply_1_)
            {
                return !(p_apply_1_ instanceof EntityTameable);
            }
        }));
    }
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Wendigo_Health);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Wendigo_Attack);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    }
    
    @Override
	public boolean getCanSpawnHere() {
    	return SpawnUtil.isAllowedDimension(this.dimension) && super.getCanSpawnHere();
	}
    
    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    @Override
    public int getMaxSpawnedInChunk() {
       return 1;
    }
	
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        
        if (this.attackTimer > 0) {
            --this.attackTimer;
         }
        
        if (this.jumpTimer > 0) {
            --this.jumpTimer;
         }
        
        /*for(ItemStack S: this.getHeldEquipment())
        {
        	if(S.getItem() instanceof ItemFood && ((ItemFood)S.getItem()).isWolfsFavoriteMeat() && this.rand.nextInt(128) < S.getCount())S.shrink(1);
        }*/
        
  	   	if (!Modconfig.SunScreen_Mode && this.world.isDaytime() && !this.world.isRemote)
  	   	{
  	   		float f = this.getBrightness();
  	   		if (f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.world.canSeeSky(new BlockPos(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ)))this.setFire(40);
  	   		//if (this.isBurning())this.attackEntityFrom(DamageSource.ON_FIRE , 1.0F);
  	   	}   
  	   	
        if (this.getAttackTarget() != null && this.getDistanceSq(this.getAttackTarget()) < 4D && this.getAttackTimer() == 10 && this.deathTime <= 0) {
            
            //System.out.println("S" + this.AttackStance);
            
            if(this.AttackStance == (byte)40) {
            	this.getAttackTarget().attackEntityFrom(DamageSource.causeMobDamage(this), (float) this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * 1.5F);
            	if(this.getAttackTarget() instanceof EntityPlayer)
            		((EntityPlayer) this.getAttackTarget()).disableShield(true);
            }
            else
            	this.getAttackTarget().attackEntityFrom(DamageSource.causeMobDamage(this), (float) this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
            
            float f = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();
            //this.setFog_counter = 100;
            //this.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, this.setFog_counter));
            if (this.getHeldItemMainhand().isEmpty() && this.isBurning() && this.rand.nextFloat() < f * 0.3F)
            {
            	this.getAttackTarget().setFire(2 * (int)f);
            }
            
            if (this.getAttackTarget() instanceof EntityLivingBase)
            {
                float local_difficulty = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();

                ((EntityLivingBase)this.getAttackTarget()).addPotionEffect(new PotionEffect(MobEffects.HUNGER, 7 * 20 * (int)local_difficulty, 4));
            }
        }
    }

    public boolean attackEntityAsMob(Entity entityIn)
    {
        //boolean flag = super.attackEntityAsMob(entityIn);
        
        this.attackTimer = 20;
        this.AttackStance = (byte)(40 + this.rand.nextInt(3));
        this.world.setEntityState(this, this.AttackStance);

        return true;
        /*if (flag)
        {
            float f = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();
            //this.setFog_counter = 100;
            //this.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, this.setFog_counter));
            if (this.getHeldItemMainhand().isEmpty() && this.isBurning() && this.rand.nextFloat() < f * 0.3F)
            {
                entityIn.setFire(2 * (int)f);
            }
            
            if (entityIn instanceof EntityLivingBase)
            {
                float local_difficulty = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();

                ((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(MobEffects.HUNGER, 7 * 20 * (int)local_difficulty, 4));
            }
        }

        return flag;*/
    }
    
    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount) {
    	if(source.isFireDamage())
    		return super.attackEntityFrom(source, 2.0F * amount);
    	return super.attackEntityFrom(source, amount);
    }
    
    protected void updateAITasks()
    {
        super.updateAITasks();
        if(this.getAttackTarget() != null)
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
    	return isAggressive;
    }
    
    //@SideOnly(Side.CLIENT)
    public int getAttackTimer() {
       return this.attackTimer;
    }
    
    @SideOnly(Side.CLIENT)
    public void setAttackStance(byte byteIn) {
    	this.AttackStance = byteIn;
    }
    
    @SideOnly(Side.CLIENT)
    public byte getAttackStance() {
       return this.AttackStance;
    }
    
    /**
     * Handler for {@link World#setEntityState}
     */
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
    	if (id == 40 || id == 41 || id == 42)
    	{
    		this.attackTimer = 20;
    		this.AttackStance = id;
    		//System.out.println("C" + this.AttackStance);
    	}
    	if (id == 4) 
    	{
            this.attackTimer = 20;
            //this.AttackStance = this.getAttackStance();
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
    
    static class AIWendigoLeapAtTarget extends EntityAIBase {
 	   /** The entity that is leaping. */
 	   private final EntityLiving leaper;
 	   /** The entity that the leaper is leaping towards. */
 	   private EntityLivingBase leapTarget;
 	   /** The entity's motionY after leaping. */
 	   private final float leapMotionY;

 	   public AIWendigoLeapAtTarget(EntityLiving leapingEntity, float leapMotionYIn) {
 		  //super(leapingEntity, leapMotionYIn);
 	      this.leaper = leapingEntity;
 	      this.leapMotionY = leapMotionYIn;
 	      this.setMutexBits(5);
 	   }
 	   
 	   /**
 	    * Returns whether the EntityAIBase should begin execution.
 	    */
 	   public boolean shouldExecute() {
 	      this.leapTarget = this.leaper.getAttackTarget();
 	      if (this.leapTarget == null || ((EntityWendigo)this.leaper).jumpTimer > 0/* || this.leapTarget.posY > this.leaper.posY*/) {
 	         return false;
 	      } else {
 	         double d0 = this.leaper.getDistance(this.leapTarget);
 	         if (!(d0 < 26.0D) && !(d0 > 36.0D)) {
 	            if (!this.leaper.onGround) {
 	               return false;
 	            } else {
 	            	return true;
 	            }
 	         } else {
 	            return false;
 	         }
 	      }
 	   }
 	   
 	   /**
 	    * Returns whether an in-progress EntityAIBase should continue executing
 	    */
 	   public boolean shouldContinueExecuting() {
 		  return !this.leaper.onGround;
 	   }
 	   
 	    /**
 	     * Keep ticking a continuous task that has already been started
 	     */
 	    public void updateTask() {
 	    }

 	   /**
 	    * Execute a one shot task or start executing a continuous task
 	    */
 	   public void startExecuting() {
 	      double d0 = this.leapTarget.posX - this.leaper.posX;
 	      double d1 = this.leapTarget.posZ - this.leaper.posZ;
 	      float f = MathHelper.sqrt(d0 * d0 + d1 * d1);
 	     this.leaper.faceEntity(this.leapTarget, 100.0F, 100.0F); 
 	     this.leaper.playSound(FishItems.ENTITY_WENDIGO_ATTACK, 4.0F, 1.0F);
 	    ((EntityWendigo)this.leaper).jumpTimer = 240;
 	      if ((double)f >= 1.0E-4D) {
 	         this.leaper.motionX += d0 / (double)f * 0.5D * (double)8.4F + this.leaper.motionX * (double)8.4F;
 	         this.leaper.motionZ += d1 / (double)f * 0.5D * (double)8.4F + this.leaper.motionZ * (double)8.4F;
 	      }

 	      this.leaper.motionY = (double)this.leapMotionY;
 	      
          /*Explosion explosion = new Explosion(this.leaper.world, this.leaper, this.leaper.posX, this.leaper.posY, this.leaper.posZ, 2.0F, false, false);
          explosion.doExplosionA();
          explosion.doExplosionB(true);*/
 	   }  	
 	   
       /**
        * Reset the task's internal state. Called when this task is interrupted by another one
        */
       public void resetTask() {
    	   //this.cooldown = 0;
       }
    }

    public float getEyeHeight()
    {
        return 2.4F;
    }
    
	@Override
	public void playLivingSound() {
		if(this.rand.nextDouble() < 0.25D)
			super.playLivingSound();
	}
    
    @Override
    protected SoundEvent getAmbientSound()
    {
        return FishItems.ENTITY_WENDIGO_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return FishItems.ENTITY_WENDIGO_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return FishItems.ENTITY_WENDIGO_DEATH;
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
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource cause) {
       super.onDeath(cause);
       
       int i = net.minecraftforge.common.ForgeHooks.getLootingLevel(this, cause.getTrueSource(), cause);
       if(this.canDropLoot())
    	   LootTableHandler.dropRareLoot(this, FishItems.UNDYINGHEART, Modconfig.UndeadSwine_DropHeart, ModEnchantments.LIFESTEAL, 3, i);
    }
    
    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableHandler.WENDIGO;
    }
    
    /**
     * Entity won't drop items or experience points if this returns false
     */
    @Override
    protected boolean canDropLoot() {
       return !this.isBurning() || this.recentlyHit != 0;
    }
}
