package com.Fishmod.fur.entities.tameable.unburied;

import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.core.SpawnUtil;
import com.Fishmod.fur.entities.tameable.FURTameableEntity;
import com.Fishmod.fur.init.FUREffectRegistry;
import com.Fishmod.fur.init.FURSoundRegistry;

import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class UnburiedEntity extends FURTameableEntity implements GeoEntity {
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final RawAnimation IDLE = RawAnimation.begin().thenPlay("unburied.model.idle");
    private static final RawAnimation WALK = RawAnimation.begin().thenPlay("unburied.model.walking");
    private static final RawAnimation RUN = RawAnimation.begin().thenPlay("unburied.model.running");
    private static final RawAnimation ATTACK = RawAnimation.begin().thenPlay("unburied.model.attacking");
    private static final RawAnimation BIRTH = RawAnimation.begin().thenPlay("unburied.model.birth");
    
    private static final EntityDataAccessor<Integer> SKIN_TYPE = SynchedEntityData.defineId(UnburiedEntity.class, EntityDataSerializers.INT);
	public static final int SPELL_TIMER = 75;
	
	protected int spellTicks;
	private int limitedLifeTicks;
	private int fire_aspect;
	private int sharpness;
	private int knockback;
	protected int bane_of_arthropods;
	protected int smite;
	private int lifesteal;
	protected int corrosive;
	private int unbreaking;
	private boolean isSmoking = false;
	
	public UnburiedEntity(EntityType<? extends UnburiedEntity> p_i48549_1_, Level worldIn) {
        super(p_i48549_1_, worldIn);
        this.limitedLifeTicks = -1;
    }
	
    @Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SKIN_TYPE, Integer.valueOf(0));
	}
	
    protected void registerGoals() {
    	super.registerGoals();
    	this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, true));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)));
    	this.targetSelector.addGoal(4, new AICopyOwnerTarget(this));
    	this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, (p_210136_0_) -> {
	  	      return !(this.getOwner() instanceof Player);
	   }));
    	this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, 10, true, false, (p_210136_0_) -> {
	  	      return !(this.getOwner() instanceof Player);
  	   }));
    	this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, IronGolem.class, 10, true, false, (p_210136_0_) -> {
	  	      return !(this.getOwner() instanceof Player);
	   }));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.23D)
        		.add(Attributes.FOLLOW_RANGE, 16.0D)
        		.add(Attributes.MAX_HEALTH, 20.0D)
        		.add(Attributes.ATTACK_DAMAGE, 3.0D)
        		.add(Attributes.ARMOR, 2.0D);
    }
    
    public void setLimitedLife(int limitedLifeTicksIn) {
    	if (limitedLifeTicksIn != 0) {
    		this.limitedLifeTicks = limitedLifeTicksIn;
    	}
    }
    
    public float getBonusDamage(LivingEntity LivingEntityIn) {
    	return (0.5f * this.sharpness + 0.5f)
				+ (LivingEntityIn.getMobType().equals(MobType.ARTHROPOD) ? (float)bane_of_arthropods * 2.5f : 0)
				+ (LivingEntityIn.getMobType().equals(MobType.UNDEAD) ? (float)smite * 2.5f : 0);
    }
    
    public int getLifestealLevel() {
    	return this.lifesteal;
    }
    
    public void setSpellcasting() {
    	this.spellTicks = SPELL_TIMER;
    	this.level().broadcastEntityEvent(this, (byte)32);
    }
    
    public boolean isSpellcasting() {
    	return this.spellTicks > 0;
    }
    
    @OnlyIn(Dist.CLIENT)
    public int getSpellTicks() {
        return this.spellTicks;
    }
       
    @Override
    protected boolean isCommandable() {
    	return false;
    }
    
    @Override
    public double getMyRidingOffset() {
        return this.isBaby() ? 0.0D : -0.25D;
    }
	
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void aiStep() {
        BlockState state = this.level().getBlockState(this.getOnPos().below());
        
        super.aiStep();
        
        if (this.isSpellcasting()) {         
	        if (state.isSolidRender(this.level(), this.getOnPos().below())) {
	            if (this.level().isClientSide()) {
	            	for(int i = 0; i < 4; i++)
	            		this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, state).setPos(this.getOnPos().below()), this.getX() + (double) (this.random.nextFloat() * this.getBbWidth() * 2.0F) - (double) this.getBbWidth(), this.getY() + (double) (this.random.nextFloat() * this.getBbWidth() * 2.0F) - (double) this.getBbWidth(), this.getZ() + (double) (this.random.nextFloat() * this.getBbWidth() * 2.0F) - (double) this.getBbWidth(), this.random.nextGaussian() * 0.02D, this.random.nextGaussian() * 0.02D, this.random.nextGaussian() * 0.02D);
	            }
	        }
	        
	        if (this.tickCount % 10 == 0) {
	            this.playSound(SoundEvents.SAND_BREAK, 1, 0.5F);
	        }      
        }
        
        if(this.isSmoking) {
	    	for (int j = 0; j < 8; ++j) {
	            float f = this.random.nextFloat() * ((float)Math.PI * 2F);
	            float f1 = this.getBbHeight() * 0.4F + this.random.nextFloat() * 0.5F;
	            float f2 = Mth.sin(f) * f1;
	            float f3 = Mth.cos(f) * f1;
	            Level world = this.level();
	            SimpleParticleType enumparticletypes = ParticleTypes.CAMPFIRE_COSY_SMOKE;
	            double d0 = this.getX() + (double)f2;
	            double d1 = this.getZ() + (double)f3;
	            world.addParticle(enumparticletypes, d0, this.getBoundingBox().minY + (double)f1, d1, 0.0D, 0.05D, 0.0D);
	        }
        }
    }
    
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void tick() {        
    	super.tick();

        if (this.spellTicks > 0) {
            --this.spellTicks;
        }
        
        if (this.limitedLifeTicks >= 0 && this.tickCount >= this.limitedLifeTicks) {
            if (FURConfig.Show_Expire_Death_Messege.get() && !this.level().isClientSide() && this.level().getGameRules().getBoolean(GameRules.RULE_SHOWDEATHMESSAGES) && this.getOwner() instanceof Player) {
                this.getOwner().sendSystemMessage(SpawnUtil.TimeupDeathMessage(this));
            }        
            this.level().broadcastEntityEvent(this, (byte)11);
            this.playSound(this.getDeathSound(), this.getSoundVolume(), this.getVoicePitch());
            this.discard();
        }
    	
        if (this.isAlive()) {
            boolean flag = !FURConfig.SunScreen_Mode.get() && !(this.getOwner() instanceof Player) && this.isSunBurnTick();
            if (flag) {
               ItemStack itemstack = this.getItemBySlot(EquipmentSlot.HEAD);
               if (!itemstack.isEmpty()) {
                  if (itemstack.isDamageableItem()) {
                     itemstack.setDamageValue(itemstack.getDamageValue() + this.random.nextInt(2));
                     if (itemstack.getDamageValue() >= itemstack.getMaxDamage()) {
                        this.broadcastBreakEvent(EquipmentSlot.HEAD);
                        this.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
                     }
                  }

                  flag = false;
               }

               if (flag) {
                  this.setSecondsOnFire(8);
               }
            }
        }
    }
    
    @Override
    public void travel(Vec3 p_213352_1_) {
		if (this.isSpellcasting()) {	
			this.setDeltaMovement(0.0D, -2.0D, 0.0D);
		} else {
			super.travel(p_213352_1_);
		}
	}
    
    /**
     * Gives armor or weapon for entity based on given DifficultyInstance
     */
    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        super.populateDefaultEquipmentSlots(random, difficulty);
        if (this.random.nextFloat() < (this.level().getDifficulty() == Difficulty.HARD ? 0.05F : 0.01F)) {
           int i = this.random.nextInt(3);
           if (i == 0) {
              this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
           } else {
              this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SHOVEL));
           }
        }
    }    
    
	@Override
    public boolean doHurtTarget(Entity entityIn) {
        if (super.doHurtTarget(entityIn)) {
	        this.level().broadcastEntityEvent(this, (byte)4);

            if(entityIn instanceof LivingEntity) {
	            if (this.fire_aspect > 0)
	            	entityIn.setSecondsOnFire((this.fire_aspect * 4) - 1);
	            
	            if (this.knockback > 0)
	            	((LivingEntity)entityIn).knockback((float)this.knockback * 0.5F, (this.getX() - entityIn.getX())/this.distanceTo(entityIn), (this.getZ() - entityIn.getZ())/this.distanceTo(entityIn));
	            
	            if (this.bane_of_arthropods > 0 && (((LivingEntity) entityIn).getMobType().equals(MobType.ARTHROPOD))) {
	                int i = 20 + this.random.nextInt(10 * bane_of_arthropods);
	                ((LivingEntity)entityIn).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, i, 3));
	            }
	            
	            if (this.corrosive > 0)
	            	((LivingEntity)entityIn).addEffect(new MobEffectInstance(FUREffectRegistry.CORRODED.get(), 4 * 20, this.corrosive - 1));
            }
            
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficulty, MobSpawnType p_213386_3_, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag p_213386_5_) {
        livingdata = super.finalizeSpawn(worldIn, difficulty, p_213386_3_, livingdata, p_213386_5_);       
        
        this.setSkin(0);
        this.setLeftHanded(true);
        this.populateDefaultEquipmentSlots(this.random, difficulty);        
        return livingdata;
    }    
    
    /**
     * Handler for {@link World#setEntityState}
     */
	@OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
    	if (id == 32) {
    		this.triggerAnim("trigger_controller", "birth");
        	this.spellTicks = SPELL_TIMER;
        } else if (id == 4) {
        	this.triggerAnim("trigger_controller", "attack");
        } else if (id == 11) {
            this.isSmoking = true;
        } else {
            super.handleEntityEvent(id);
        }
    }
   
    class AICopyOwnerTarget extends TargetGoal {
    	private final TargetingConditions copyOwnerTargeting = TargetingConditions.forNonCombat().ignoreLineOfSight().ignoreInvisibilityTesting();
    	private LivingEntity owner = UnburiedEntity.this.getOwner();
    	
        public AICopyOwnerTarget(PathfinderMob creature) {
            super(creature, false);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean canUse() {
            return this.owner != null && this.owner instanceof Monster && ((Monster) this.owner).isAggressive() && this.canAttack(((Monster) this.owner).getTarget(), this.copyOwnerTargeting);
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            UnburiedEntity.this.setTarget(((Monster) this.owner).getTarget());
            super.start();
        }
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return FURSoundRegistry.UNBURIED_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FURSoundRegistry.UNBURIED_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.UNBURIED_DEATH.get();
    }
    
    public int getSkin() {
        return this.entityData.get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
    	this.entityData.set(SKIN_TYPE, Integer.valueOf(skinType));
    }
    
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
       super.readAdditionalSaveData(compound);
        this.setLimitedLife(compound.getInt("LifeTicks"));
    	this.fire_aspect = compound.getInt("fire_aspect");
    	this.sharpness = compound.getInt("sharpness");
    	this.knockback = compound.getInt("knockback");
    	this.bane_of_arthropods = compound.getInt("bane_of_arthropods");
    	this.smite = compound.getInt("fire_aspect");
    	this.lifesteal = compound.getInt("lifesteal");
    	this.corrosive = compound.getInt("corrosive");
    	this.unbreaking = compound.getInt("unbreaking");  
    	this.setSkin(compound.getInt("Variant"));
    	this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D/*Modconfig.Unburied_Health*/ + ((float)this.unbreaking * 2.0F));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("LifeTicks", this.limitedLifeTicks - this.tickCount);
        compound.putInt("fire_aspect", this.fire_aspect);
        compound.putInt("sharpness", this.sharpness);
        compound.putInt("knockback", this.knockback);
        compound.putInt("bane_of_arthropods", this.bane_of_arthropods);
        compound.putInt("smite", this.smite);
        compound.putInt("lifesteal", this.lifesteal);
        compound.putInt("corrosive", this.corrosive);
        compound.putInt("unbreaking", this.unbreaking);     
        compound.putInt("Variant", this.getSkin());
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }
        
    /**
     * Entity won't drop items or experience points if this returns false
     */
    @Override
    public boolean shouldDropLoot() {
    	return !this.isTame() || (this.isTame() && !(this.getOwner() instanceof Player));
    }
    
    private RawAnimation getWalkAnimation() {
        if (this.isAggressive()) {
        	return RUN;
        } else {
        	return WALK;
        }
    }
    
    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
    	if (state.isMoving() && !this.isInWater()) {
            state.getController().setAnimation(this.getWalkAnimation());
        } else {
            state.getController().setAnimation(IDLE);
        }
        
        return PlayState.CONTINUE;
    }

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "controller", 5, this::predicate));
		controllers.add(new AnimationController<>(this, "trigger_controller", 5, state -> PlayState.STOP).triggerableAnim("attack", ATTACK).triggerableAnim("birth", BIRTH));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
