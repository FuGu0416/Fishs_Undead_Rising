package com.Fishmod.mod_LavaCow.entities.tameable;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.IAggressive;
import com.Fishmod.mod_LavaCow.init.FUREffectRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;

import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtByTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtTargetGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.GameRules;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class UnburiedEntity extends FURTameableEntity implements IAggressive{
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
	private boolean isSmoking = false;
	
	public UnburiedEntity(EntityType<? extends UnburiedEntity> p_i48549_1_, World worldIn)
    {
        super(p_i48549_1_, worldIn);
        BirthAnimation = true;
        this.limitedLifeTicks = -1;
    }
	
    protected void registerGoals()
    {
    	//this.goalSelector.addGoal(1, new EntityFishAIBreakDoor(this));
    	this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, true));
    	this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
    	this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI()
    {
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)));
    	//this.targetSelector.addGoal(4, new AICopyOwnerTarget(this));
    	this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, (p_210136_0_) -> {
	  	      return !(this.getOwner() instanceof PlayerEntity);
	   }));
    	this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, 10, true, false, (p_210136_0_) -> {
	  	      return !(this.getOwner() instanceof PlayerEntity);
  	   }));
    	this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, 10, true, false, (p_210136_0_) -> {
	  	      return !(this.getOwner() instanceof PlayerEntity);
	   }));
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.23D)
        		.add(Attributes.FOLLOW_RANGE, 16.0D)
        		.add(Attributes.MAX_HEALTH, FURConfig.Unburied_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, FURConfig.Unburied_Attack.get())
        		.add(Attributes.ARMOR, 2.0D);
    }
    
    public void setLimitedLife(int limitedLifeTicksIn)
    {
        this.limitedLifeTicks = limitedLifeTicksIn;
    }
    
    public float getBonusDamage(LivingEntity LivingEntityIn) {
    	return (0.5f * this.sharpness + 0.5f)
				+ (LivingEntityIn.getMobType().equals(CreatureAttribute.ARTHROPOD) ? (float)bane_of_arthropods * 2.5f : 0)
				+ (LivingEntityIn.getMobType().equals(CreatureAttribute.UNDEAD) ? (float)smite * 2.5f : 0);
    }
    
    public int getLifestealLevel() {
    	return this.lifesteal;
    }
    
    public boolean isSpellcasting()
    {
    	return this.spellTicks > 0;
    }
    
    @OnlyIn(Dist.CLIENT)
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
    public void aiStep()
    {
        BlockState state = this.level.getBlockState(this.getOnPos().below());
        
        if (this.isSpellcasting()) {
            
	        if (state.isSolidRender(this.level, this.getOnPos().below())) {
	            if (this.level.isClientSide()) {
	            	for(int i = 0; i < 4; i++)
	            		this.level.addParticle(new BlockParticleData(ParticleTypes.BLOCK, state).setPos(this.getOnPos().below()), this.getX() + (double) (this.random.nextFloat() * this.getBbWidth() * 2.0F) - (double) this.getBbWidth(), this.getY() + (double) (this.random.nextFloat() * this.getBbWidth() * 2.0F) - (double) this.getBbWidth(), this.getZ() + (double) (this.random.nextFloat() * this.getBbWidth() * 2.0F) - (double) this.getBbWidth(), this.random.nextGaussian() * 0.02D, this.random.nextGaussian() * 0.02D, this.random.nextGaussian() * 0.02D);
	            }
	        }
	        
	        if (this.tickCount % 10 == 0) {
	            this.playSound(SoundEvents.SAND_BREAK, 1, 0.5F);
	        }
	        
	        this.setDeltaMovement(Vector3d.ZERO);
        }
        
        if(this.isSmoking) {
	    	for (int j = 0; j < 8; ++j)
	        {
	            float f = this.random.nextFloat() * ((float)Math.PI * 2F);
	            float f1 = this.getBbHeight() * 0.4F + this.random.nextFloat() * 0.5F;
	            float f2 = MathHelper.sin(f) * f1;
	            float f3 = MathHelper.cos(f) * f1;
	            World world = this.level;
	            BasicParticleType enumparticletypes = ParticleTypes.CAMPFIRE_COSY_SMOKE;
	            double d0 = this.getX() + (double)f2;
	            double d1 = this.getZ() + (double)f3;
	            world.addParticle(enumparticletypes, d0, this.getBoundingBox().minY + (double)f1, d1, 0.0D, 0.05D, 0.0D);
	        }
        }
		
		super.aiStep();
    }
    
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void tick()
    {           	
        if(this.BirthAnimation) {
	    	this.spellTicks = 20;
	    	this.level.broadcastEntityEvent(this, (byte)32);
	        this.BirthAnimation = false;
        }
    	
    	if (this.attackTimer > 0) {
            --this.attackTimer;
        }
        
        if (this.spellTicks > 0) {
            --this.spellTicks;
        }
        
        if(this.limitedLifeTicks >= 0 && this.tickCount >= this.limitedLifeTicks) {
            if (!this.level.isClientSide() && this.level.getGameRules().getBoolean(GameRules.RULE_SHOWDEATHMESSAGES) && this.getOwner() instanceof PlayerEntity)
            {
                this.getOwner().sendMessage(SpawnUtil.TimeupDeathMessage(this), uuid);
            }        
            this.level.broadcastEntityEvent(this, (byte)11);
            this.playSound(this.getDeathSound(), this.getSoundVolume(), this.getVoicePitch());
            this.remove();
        }
    	
        if (this.isAlive()) {
            boolean flag = !FURConfig.SunScreen_Mode.get() && !(this.getOwner() instanceof PlayerEntity) && this.isSunBurnTick();
            if (flag) {
               ItemStack itemstack = this.getItemBySlot(EquipmentSlotType.HEAD);
               if (!itemstack.isEmpty()) {
                  if (itemstack.isDamageableItem()) {
                     itemstack.setDamageValue(itemstack.getDamageValue() + this.random.nextInt(2));
                     if (itemstack.getDamageValue() >= itemstack.getMaxDamage()) {
                        this.broadcastBreakEvent(EquipmentSlotType.HEAD);
                        this.setItemSlot(EquipmentSlotType.HEAD, ItemStack.EMPTY);
                     }
                  }

                  flag = false;
               }

               if (flag) {
                  this.setSecondsOnFire(8);
               }
            }
        }
          	
    	super.tick();
    }
    
    /**
     * Gives armor or weapon for entity based on given DifficultyInstance
     */
    @Override
    protected void populateDefaultEquipmentSlots(DifficultyInstance p_180481_1_) {
        super.populateDefaultEquipmentSlots(p_180481_1_);
        if (this.random.nextFloat() < (this.level.getDifficulty() == Difficulty.HARD ? 0.05F : 0.01F)) {
           int i = this.random.nextInt(3);
           if (i == 0) {
              this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.IRON_SWORD));
           } else {
              this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.IRON_SHOVEL));
           }
        }

    }
    
    public void setDefaultEquipment(DifficultyInstance p_180481_1_) {
    	this.populateDefaultEquipmentSlots(p_180481_1_);
    }
    
	@Override
    public boolean doHurtTarget(Entity entityIn)
    {
        if (super.doHurtTarget(entityIn))
        {
        	this.attackTimer = 5;
	        this.level.broadcastEntityEvent(this, (byte)4);
	        
            if(entityIn instanceof LivingEntity) {
	            if(this.fire_aspect > 0)
	            	entityIn.setSecondsOnFire((this.fire_aspect * 4) - 1);
	            
	            if(this.knockback > 0)
	            	((LivingEntity)entityIn).knockback((float)this.knockback * 0.5F, (this.getX() - entityIn.getX())/this.distanceTo(entityIn), (this.getZ() - entityIn.getZ())/this.distanceTo(entityIn));
	            
	            if(this.bane_of_arthropods > 0 && (((LivingEntity) entityIn).getMobType().equals(CreatureAttribute.ARTHROPOD))) {
	                int i = 20 + this.random.nextInt(10 * bane_of_arthropods);
	                ((LivingEntity)entityIn).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, i, 3));
	            }
	            
	            if(this.poisonous > 0)
	    			((LivingEntity)entityIn).addEffect(new EffectInstance(Effects.POISON, 8*20, this.poisonous - 1));
	            
	            if(this.corrosive > 0);
	            	((LivingEntity)entityIn).addEffect(new EffectInstance(FUREffectRegistry.CORRODED, 4*20, this.corrosive - 1));
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
	public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT p_213386_5_) {
        livingdata = super.finalizeSpawn(p_213386_1_, difficulty, p_213386_3_, livingdata, p_213386_5_);       
        this.populateDefaultEquipmentSlots(difficulty);        
        return livingdata;
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
	@OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id)
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
            this.isSmoking = true;
        }
        else
        {
            super.handleEntityEvent(id);
        }
    }
   
    class AICopyOwnerTarget extends TargetGoal {
    	private final EntityPredicate copyOwnerTargeting = (new EntityPredicate()).allowUnseeable().ignoreInvisibilityTesting();
    	private LivingEntity owner = UnburiedEntity.this.getOwner();
    	
        public AICopyOwnerTarget(CreatureEntity creature) {
            super(creature, false);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean canUse()
        {
            return this.owner != null && this.owner instanceof MobEntity && ((MobEntity) this.owner).isAggressive() && this.canAttack(((MobEntity) this.owner).getTarget(), this.copyOwnerTargeting);
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start()
        {
            UnburiedEntity.this.setTarget(((MobEntity) this.owner).getTarget());
            super.start();
        }
    }
    
    @Override
    protected SoundEvent getAmbientSound()
    {
        return FURSoundRegistry.UNBURIED_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return FURSoundRegistry.UNBURIED_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return FURSoundRegistry.UNBURIED_DEATH;
    }
    
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
       super.readAdditionalSaveData(compound);
        this.setLimitedLife(compound.getInt("LifeTicks"));
    	this.fire_aspect = compound.getInt("fire_aspect");
    	this.sharpness = compound.getInt("sharpness");
    	this.knockback = compound.getInt("knockback");
    	this.bane_of_arthropods = compound.getInt("bane_of_arthropods");
    	this.smite = compound.getInt("fire_aspect");
    	this.lifesteal = compound.getInt("lifesteal");
    	this.poisonous = compound.getInt("poisonous");
    	this.corrosive = compound.getInt("corrosive");
    	this.unbreaking = compound.getInt("unbreaking");   
    	this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D/*Modconfig.Unburied_Health*/ + ((float)this.unbreaking * 2.0F));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("LifeTicks", this.limitedLifeTicks - this.tickCount);
        compound.putInt("fire_aspect", this.fire_aspect);
        compound.putInt("sharpness", this.sharpness);
        compound.putInt("knockback", this.knockback);
        compound.putInt("bane_of_arthropods", this.bane_of_arthropods);
        compound.putInt("smite", this.smite);
        compound.putInt("lifesteal", this.lifesteal);
        compound.putInt("poisonous", this.poisonous);
        compound.putInt("corrosive", this.corrosive);
        compound.putInt("unbreaking", this.unbreaking);     
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public CreatureAttribute getMobType()
    {
        return CreatureAttribute.UNDEAD;
    }
        
    /**
     * Entity won't drop items or experience points if this returns false
     */
    @Override
    public boolean shouldDropLoot() {
    	return this.isTame() || !(this.getOwner() instanceof PlayerEntity);
    }
}
