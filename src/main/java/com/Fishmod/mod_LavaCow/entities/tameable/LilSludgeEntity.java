package com.Fishmod.mod_LavaCow.entities.tameable;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.init.FUREffectRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;

import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.OwnerHurtByTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.GameRules;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LilSludgeEntity extends FURTameableEntity {
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.defineId(LilSludgeEntity.class, DataSerializers.INT);
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
	
	public LilSludgeEntity(EntityType<? extends LilSludgeEntity> p_i48549_1_, World worldIn) {
        super(p_i48549_1_, worldIn);
        this.limitedLifeTicks = -1;
    }
	
	@Override
    protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SKIN_TYPE, Integer.valueOf(0));
    }
	
    @Override
    protected void registerGoals() {
    	this.goalSelector.addGoal(1, new SwimGoal(this));
    	this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
    	this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
    	this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
    	this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(10, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(10, new LookRandomlyGoal(this));

        this.applyEntityAI();
    }

    protected void applyEntityAI() {
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
    	this.targetSelector.addGoal(4, new AICopyOwnerTarget(this));
    }
    
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.25D)
        		.add(Attributes.MAX_HEALTH, FURConfig.LilSludge_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, FURConfig.LilSludge_Attack.get());
    }
    
    public void setLimitedLife(int limitedLifeTicksIn) {
    	if (limitedLifeTicksIn != 0) {
    		this.limitedLifeTicks = limitedLifeTicksIn;
    	}
    }
    
    public float getBonusDamage(LivingEntity LivingEntityIn) {
    	return (0.5f * this.sharpness + 0.5f)
				+ (LivingEntityIn.getMobType().equals(CreatureAttribute.ARTHROPOD) ? (float)bane_of_arthropods * 2.5f : 0)
				+ (LivingEntityIn.getMobType().equals(CreatureAttribute.UNDEAD) ? (float)smite * 2.5f : 0);
    }
    
    public int getLifestealLevel() {
    	return this.lifesteal;
    }
    
    public int getSkin() {
        return this.entityData.get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
        this.entityData.set(SKIN_TYPE, Integer.valueOf(skinType));
    }
    
	@Override
    protected boolean isCommandable() {
    	return false;
    }
	
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void aiStep() {
    	if (this.limitedLifeTicks >= 0 && this.tickCount >= this.limitedLifeTicks) {    		
            if (FURConfig.Show_Expire_Death_Messege.get() && !this.level.isClientSide() && this.level.getGameRules().getBoolean(GameRules.RULE_SHOWDEATHMESSAGES) && this.getOwner() instanceof PlayerEntity) {
                this.getOwner().sendMessage(SpawnUtil.TimeupDeathMessage(this), uuid);
            }
        	this.level.broadcastEntityEvent(this, (byte)11);
            this.playSound(this.getDeathSound(), this.getSoundVolume(), this.getVoicePitch());
            this.remove();
        }
    	
        if (this.isSmoking) {
	    	for (int j = 0; j < 8; ++j) {
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
           	
    	if (!FURConfig.SunScreen_Mode.get() && !(this.getOwner() instanceof PlayerEntity) && this.isSunBurnTick()) {
    		this.setSecondsOnFire(8);
        }
    	
    	super.aiStep();
    }

	@Override
    public boolean doHurtTarget(Entity entityIn) {
        boolean flag = super.doHurtTarget(entityIn);

        if (flag) {
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
	            
	            if(this.corrosive > 0)
	            	((LivingEntity)entityIn).addEffect(new EffectInstance(FUREffectRegistry.CORRODED, 4*20, this.corrosive - 1));
            }
        }

        return flag;
    }    
         
    /**
     * If Animal, checks if the age timer is negative
     */
	@Override
    public boolean isBaby() {
       return true;
    }
	
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT p_213386_5_) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.LilSludge_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.LilSludge_Attack.get());
    	this.setHealth(this.getMaxHealth());
        
    	return super.finalizeSpawn(p_213386_1_, difficulty, p_213386_3_, livingdata, p_213386_5_);
    }	
    
	@Override
    public float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
        return 0.6F;
    }
	
    /**
     * Handler for {@link World#setEntityState}
     */
	@OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
    	if (id == 11) {
            this.isSmoking = true;
        } else {
            super.handleEntityEvent(id);
        }
    }
    
    class AICopyOwnerTarget extends TargetGoal {
    	private final EntityPredicate copyOwnerTargeting = (new EntityPredicate()).allowUnseeable().ignoreInvisibilityTesting();
    	private LivingEntity owner = LilSludgeEntity.this.getOwner();
    	
        public AICopyOwnerTarget(CreatureEntity creature) {
            super(creature, false);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean canUse() {
            return this.owner != null && this.owner instanceof MobEntity && ((MobEntity) this.owner).isAggressive() && this.canAttack(((MobEntity) this.owner).getTarget(), this.copyOwnerTargeting);
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            LilSludgeEntity.this.setTarget(((MobEntity) this.owner).getTarget());
            super.start();
        }
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return FURSoundRegistry.LILSLUDGE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FURSoundRegistry.SLUDGELORD_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.LILSLUDGE_DEATH;
    }

	@Override
    protected void playStepSound(BlockPos pos, BlockState state) {
	    this.playSound(SoundEvents.CHICKEN_STEP, 0.15F, 1.0F);
	}
    
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
       super.readAdditionalSaveData(compound);
        this.setLimitedLife(compound.getInt("LifeTicks"));
        this.setSkin(compound.getInt("Variant"));
    	this.fire_aspect = compound.getInt("fire_aspect");
    	this.sharpness = compound.getInt("sharpness");
    	this.knockback = compound.getInt("knockback");
    	this.bane_of_arthropods = compound.getInt("bane_of_arthropods");
    	this.smite = compound.getInt("fire_aspect");
    	this.lifesteal = compound.getInt("lifesteal");
    	this.poisonous = compound.getInt("poisonous");
    	this.corrosive = compound.getInt("corrosive");
    	this.unbreaking = compound.getInt("unbreaking");   
    	this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.LilSludge_Health.get() + ((float)this.unbreaking * 2.0F));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("LifeTicks", this.limitedLifeTicks - this.tickCount);
        compound.putInt("Variant", getSkin());
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
     * Get this Entity's CreatureAttribute
     */
    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.UNDEAD;
    }
    
    @Override
    public boolean shouldDropLoot() {
    	return !this.isTame() || (this.isTame() && !(this.getOwner() instanceof PlayerEntity));
    }
}
