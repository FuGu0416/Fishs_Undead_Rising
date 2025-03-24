package com.Fishmod.fur.entities;

import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;

import com.Fishmod.fur.entities.ai.FURMeleeAttackGoal;
import com.Fishmod.fur.init.FURSoundRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class FogletEntity extends Monster implements IAggressive, GeoEntity {
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final RawAnimation IDLE = RawAnimation.begin().thenPlay("foglet.model.idle");
    private static final RawAnimation WALK = RawAnimation.begin().thenPlay("foglet.model.walking");
    private static final RawAnimation RUN = RawAnimation.begin().thenPlay("foglet.model.running");
    private static final RawAnimation ATTACK = RawAnimation.begin().thenPlay("foglet.model.attacking");
    private static final RawAnimation CAST = RawAnimation.begin().thenPlay("foglet.model.casting");
    
	private static final EntityDataAccessor<Integer> SKIN_TYPE = SynchedEntityData.defineId(FogletEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Byte> CLIMBING = SynchedEntityData.defineId(FogletEntity.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Byte> HANGING = SynchedEntityData.defineId(FogletEntity.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Byte> CASTING = SynchedEntityData.defineId(FogletEntity.class, EntityDataSerializers.BYTE);
	public static final int ATTACK_TIMER = 30;
	public static final int SPELL_TIMER = 20;
	private int attackTimer = 0;
	protected int spellTicks;
	
	public FogletEntity(EntityType<? extends FogletEntity> p_i48549_1_, Level worldIn) {
        super(p_i48549_1_, worldIn);
    }
	
	@Override
    protected void registerGoals() {
		this.goalSelector.addGoal(1, new AIClimbimgTree());
        this.goalSelector.addGoal(2, new AICastingApell());
        this.goalSelector.addGoal(3, new FogletEntity.AIUseSpell());
        this.goalSelector.addGoal(3, new FogletEntity.AISelfImmolation());     
        /*if(!FURConfig.SunScreen_Mode.get())*/ {
            this.goalSelector.addGoal(2, new RestrictSunGoal(this));
            this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0D));
        }
        this.goalSelector.addGoal(4, new FogletEntity.AttackGoal(this));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR));
    }
    
    public static AttributeSupplier.Builder createAttributesFoglet() {    	
        return Monster.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.25D)
        		.add(Attributes.FOLLOW_RANGE, 16.0D)
        		.add(Attributes.MAX_HEALTH, 16.0D/*FURConfig.Foglet_Health.get()*/)
        		.add(Attributes.ATTACK_DAMAGE, 2.0D/*FURConfig.Foglet_Attack.get()*/);
    }
    
    public static AttributeSupplier.Builder createAttributesImp() {    	
        return Monster.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.25D)
        		.add(Attributes.FOLLOW_RANGE, 16.0D)
        		.add(Attributes.MAX_HEALTH, 16.0D/*FURConfig.Foglet_Health.get()*/)
        		.add(Attributes.ATTACK_DAMAGE, 2.0D/*FURConfig.Foglet_Attack.get()*/);
    }
    
    public static boolean checkFogletSpawnRules(EntityType<? extends FogletEntity> p_223316_0_, ServerLevelAccessor p_223316_1_, MobSpawnType p_223316_2_, BlockPos p_223316_3_, RandomSource p_223316_4_) {
        return Monster.checkMonsterSpawnRules(p_223316_0_, p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_);
    }
    
    @Override
    protected void defineSynchedData() {
    	super.defineSynchedData();
        this.getEntityData().define(SKIN_TYPE, Integer.valueOf(0));
        this.getEntityData().define(CLIMBING, Byte.valueOf((byte)0));
        this.getEntityData().define(HANGING, Byte.valueOf((byte)0));
        this.getEntityData().define(CASTING, Byte.valueOf((byte)0));
    }
    
    public boolean isSpellcasting() {
    	return (((Byte)this.getEntityData().get(CASTING)).byteValue() & 1) != 0;
    }
    
    @OnlyIn(Dist.CLIENT)
    public boolean isSpellcastingC() {
    	return (((Byte)this.getEntityData().get(CASTING)).byteValue() & 1) != 0;
    }
    
    protected int getSpellTicks() {
        return this.spellTicks;
    }
    
    @Override
    public double getMyRidingOffset() {
        return -0.25D;
    }
	
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void aiStep() {
        super.aiStep();
        
        if (this.getIsHanging()) {
        	this.setDeltaMovement(Vec3.ZERO);

	        if(!this.isPassenger()) {
		        if(this.level().canSeeSky(this.blockPosition())) {
		        	this.setIsHanging(false);
		        }
		        
		        List<Entity> list = this.level().getEntities(this, this.getBoundingBox().expandTowards(2.0D, 35.0D, 2.0D));
		        
	        	for (Entity entity1 : list) {
	        		if (entity1.getY() < this.getY() && ((entity1 instanceof Player && !((Player)entity1).isCreative()) || entity1 instanceof AbstractVillager)) {
	        			this.setTarget((LivingEntity) entity1);
	        			this.setIsHanging(false);
	        			break;
	        		}
	        	}  
	        }
    	}      
    }
	
	@Override
    public boolean causeFallDamage(float p_150093_, float p_150094_, DamageSource p_150095_) {
    	if(this.getTarget() != null) {
    		return false;
    	}  	
    	
    	return super.causeFallDamage(p_150093_, p_150094_, p_150095_);
    }
    
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void tick() {
        if (this.spellTicks > 0) {
            --this.spellTicks;
        }
        
    	if (this.getAttackTimer() > 0) {
    		--this.attackTimer;
    	}
    	
    	if (/*!FURConfig.SunScreen_Mode.get() && */this.isSunBurnTick()) {
    		this.setSecondsOnFire(8);
        }
    	
        super.tick();
    }
    
    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean hurt(DamageSource source, float amount) {
    	if (super.hurt(source, amount)) {
            LivingEntity LivingEntity = this.getTarget();
        	
            if (LivingEntity == null && source.getDirectEntity() instanceof LivingEntity) {
                LivingEntity = (LivingEntity)source.getDirectEntity();
            }
            
            if(this.getIsClimbing()) {
            	this.setIsClimbing(false);
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean doHurtTarget(Entity entityIn) {
        boolean flag = super.doHurtTarget(entityIn);

        if (flag) {
            float f = this.level().getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
            if (this.getMainHandItem().isEmpty() && this.isOnFire() && this.random.nextFloat() < f * 0.3F) {
            	entityIn.setSecondsOnFire(2 * (int)f);
            }
        }

        return flag;
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @SuppressWarnings("deprecation")
	@Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_213386_1_, DifficultyInstance difficulty, MobSpawnType p_213386_3_, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag p_213386_5_) {
        //this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Foglet_Health.get());
        //this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Foglet_Attack.get());
    	this.setHealth(this.getMaxHealth());
    	
    	/*if (BiomeDictionary.getTypes(SpawnUtil.getRegistryKey(p_213386_1_.getBiome(this.blockPosition()))).contains(Type.JUNGLE)) {
 		   	this.setSkin(1);		   	
    	} else if (this.getType().equals(FUREntityRegistry.IMP)) {
    		this.setSkin(2);
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Imp_Health.get());
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Imp_Attack.get());
        	this.setHealth(this.getMaxHealth());
    	}*/
 	   	   
 	   	return super.finalizeSpawn(p_213386_1_, difficulty, p_213386_3_, livingdata, p_213386_5_);
 	}
    
    public int getSkin() {
        return this.getEntityData().get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
        this.getEntityData().set(SKIN_TYPE, Integer.valueOf(skinType));
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
	@Override
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
		switch(id) {
			case 5:
				this.setAttackTimer(ATTACK_TIMER);
				break;
			case 10:
				this.spellTicks = SPELL_TIMER;
				break;			
			default:
				this.spellTicks = 0;
				super.handleEntityEvent(id);
				break;
		}
    }
    
	@Override
    protected float getStandingEyeHeight(Pose p_213348_1_, EntityDimensions p_213348_2_) {
        if(getIsHanging())
        	return this.getBbHeight() * 0.0F;
        else
        	return this.getBbHeight() * 0.8F;
    }
		
	@Override
    public float getWalkTargetValue(BlockPos p_205022_1_, LevelReader p_205022_2_) {
    	if (p_205022_2_.getBlockState(p_205022_1_).is(BlockTags.LOGS)) {
    		return 10.0F;
    	} else {
    		return super.getWalkTargetValue(p_205022_1_, p_205022_2_);
    	}
    }
    
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.spellTicks = compound.getInt("SpellTicks");
        this.setSkin(compound.getInt("Variant"));
        this.getEntityData().set(HANGING, Byte.valueOf(compound.getByte("Hanging")));
        this.getEntityData().set(CLIMBING, Byte.valueOf(compound.getByte("Climbing")));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("SpellTicks", this.spellTicks);
        compound.putInt("Variant", getSkin());
        compound.putByte("Hanging", ((Byte)this.getEntityData().get(HANGING)).byteValue());
        compound.putByte("Climbing", ((Byte)this.getEntityData().get(CLIMBING)).byteValue());
    }
    
    public boolean getIsHanging() {
        return (((Byte)this.getEntityData().get(HANGING)).byteValue() & 1) != 0;
    }
    
    public boolean getIsClimbing() {
        return (((Byte)this.getEntityData().get(CLIMBING)).byteValue() & 1) != 0;
    }
    
    public void setIsClimbing(boolean isClimbing) {
        byte b0 = ((Byte)this.getEntityData().get(CLIMBING)).byteValue();

        if (isClimbing) {
            this.getEntityData().set(CLIMBING, Byte.valueOf((byte)(b0 | 1)));
        } else {
            this.getEntityData().set(CLIMBING, Byte.valueOf((byte)(b0 & -2)));
        }
    }
    
    public void setIsHanging(boolean isHanging) {
        byte b0 = ((Byte)this.getEntityData().get(HANGING)).byteValue();

        if (isHanging) {
            this.getEntityData().set(HANGING, Byte.valueOf((byte)(b0 | 1)));
        } else {
            this.getEntityData().set(HANGING, Byte.valueOf((byte)(b0 & -2)));
        }
    }
    
    public void setIsCasting(boolean isHanging) {
        byte b0 = ((Byte)this.getEntityData().get(CASTING)).byteValue();

        if (isHanging) {
            this.getEntityData().set(CASTING, Byte.valueOf((byte)(b0 | 1)));
        } else {
            this.getEntityData().set(CASTING, Byte.valueOf((byte)(b0 & -2)));
        }
    }
    
    public class AIClimbimgTree extends Goal {
        private BlockPos TreePos;
    	
    	public AIClimbimgTree() {
        }
    	
    	private boolean canClimb() {
    		return !FogletEntity.this.level().getBlockState(FogletEntity.this.blockPosition().above()).canOcclude() && FogletEntity.this.level().getBlockState(FogletEntity.this.blockPosition().above()).is(BlockTags.LEAVES);
    	}

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
    	@Override
        public boolean canUse() {
    		
    		if (FogletEntity.this.getSkin() != 1) {
    			return false;
    		}
    		
            int i = (int) Math.floor(FogletEntity.this.getX());
            int j = (int) Math.floor(FogletEntity.this.getY());
            int k = (int) Math.floor(FogletEntity.this.getZ());
            BlockPos blockpos = new BlockPos(i, j, k);
            
            TreePos = null;
            
            for(int x = -1 ; x <= 1 ; x++)
            	for(int z = -1 ; z <= 1 ; z++) {
            		if(FogletEntity.this.level().getBlockState(blockpos.offset(x, 0, z)).is(BlockTags.LOGS)) {
            			TreePos = new BlockPos(x, 0, z);
            			break;
            		}
            	}
            
            return !FogletEntity.this.isOnFire() 
            		&& !FogletEntity.this.isAggressive() 
            		&& !FogletEntity.this.level().canSeeSky(blockpos) 
            		&& TreePos != null 
            		&& this.canClimb();
        }
    	
        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        @Override
        public boolean canContinueToUse() {
            return this.canClimb();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
    	@Override
        public void start() {
            super.start();
            FogletEntity.this.setIsClimbing(true);
            FogletEntity.this.getNavigation().stop();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
    	@Override
        public void stop() {
        	super.stop();
            FogletEntity.this.setIsClimbing(false);
            FogletEntity.this.setIsHanging(true);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
    	@Override
        public void tick() {		
        	if (FogletEntity.this.getDeltaMovement().y < 0.0D) {
        		FogletEntity.this.setPosRaw(FogletEntity.this.getX(), FogletEntity.this.getY() + 0.2D, FogletEntity.this.getZ());
        	}
        	
        	if (TreePos != null) {
            	FogletEntity.this.yBodyRot = (TreePos.getX() * 270.0F + (float) Math.toDegrees(Math.atan(TreePos.getZ() / (TreePos.getX() + 0.0000001D)))) % 360.0F;
        	}
        }
    }
    
    public class AICastingApell extends Goal {

        public AICastingApell() {
        	this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        @Override
        public boolean canUse() {
            return FogletEntity.this.getSpellTicks() > 0;
        }
        
        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        @Override
        public boolean canContinueToUse() {
            return FogletEntity.this.getSpellTicks() > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        @Override
        public void start() {
            super.start();
            FogletEntity.this.setIsCasting(true);
            FogletEntity.this.getNavigation().stop();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        @Override
        public void stop() {
            super.stop();
            FogletEntity.this.setIsCasting(false);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        @Override
        public void tick() {
            if (FogletEntity.this.getTarget() != null) {
                FogletEntity.this.getLookControl().setLookAt(FogletEntity.this.getTarget(), (float)FogletEntity.this.getMaxHeadYRot(), (float)FogletEntity.this.getMaxHeadXRot());
            }
        }
    }
    
    public class AIUseSpell extends Goal {
        protected int spellWarmup;
        protected int spellCooldown;

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        @Override
        public boolean canUse() {
            if (FogletEntity.this.getTarget() == null || FogletEntity.this.getSkin() == 2) {
                return false;
            } else if (FogletEntity.this.isSpellcasting()) {
                return false;
            } else {
            	return FogletEntity.this.tickCount >= this.spellCooldown && FogletEntity.this.distanceTo(FogletEntity.this.getTarget()) < 3.0F;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        @Override
        public boolean canContinueToUse() {
            return FogletEntity.this.getTarget() != null && this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        @Override
        public void start() {
            this.spellWarmup = this.getCastWarmupTime();
            FogletEntity.this.spellTicks = this.getCastingTime();
            FogletEntity.this.level().broadcastEntityEvent(FogletEntity.this, (byte)10);
            this.spellCooldown = FogletEntity.this.tickCount + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();

            if (soundevent != null) {
                FogletEntity.this.playSound(soundevent, 1.0F, 1.0F);
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        @Override
        public void tick() {
            --this.spellWarmup;

            if (this.spellWarmup == 0) {
                this.castSpell();
                FogletEntity.this.playSound(FogletEntity.this.getSpellSound(), 1.0F, 1.0F);
                if(FogletEntity.this.getSkin() == 0)
                	FogletEntity.this.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 6 * 20));
            }
        }

        protected void castSpell() {
        	AreaEffectCloud entityareaeffectcloud = new AreaEffectCloud(FogletEntity.this.level(), FogletEntity.this.getX(), FogletEntity.this.getY() + 1.0D, FogletEntity.this.getZ());
        	MobEffect effect;
            
            switch(FogletEntity.this.getSkin()) {
	            case 0:
	            	effect = MobEffects.BLINDNESS;
	                break;
	            case 1:
	            default:
	            	effect = MobEffects.WEAKNESS/*FUREffectRegistry.SOILED*/;
	                break;
            }
            
        	entityareaeffectcloud.setOwner(FogletEntity.this);
            entityareaeffectcloud.setRadius(4.0F);
            entityareaeffectcloud.setRadiusOnUse(-0.5F);
            entityareaeffectcloud.setDuration(120);
            entityareaeffectcloud.setWaitTime(10);
            entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / (float)entityareaeffectcloud.getDuration());
            
            if (FogletEntity.this.getSkin() == 0) {
            	entityareaeffectcloud.setParticle(ParticleTypes.CLOUD);
            } else if (FogletEntity.this.getSkin() == 1) {
            	//entityareaeffectcloud.setPotion(FUREffectRegistry.FOULODOR_POTION);
            }
            
            entityareaeffectcloud.addEffect(new MobEffectInstance(effect, 4 * 20, 1));
            entityareaeffectcloud.setFixedColor(FogletEntity.this.getSkin() == 0 ? 0xFFFFFF : 0x6F5B3C);

            FogletEntity.this.level().addFreshEntity(entityareaeffectcloud);
        }

        protected int getCastWarmupTime() {
            return 20;
        }

        protected int getCastingTime() {
            return 100;
        }

        protected int getCastingInterval() {
            return 200;
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.EVOKER_PREPARE_ATTACK;
        }
    }

    public class AISelfImmolation extends Goal {
        protected int spellWarmup;
        protected int spellCooldown;

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        @Override
        public boolean canUse() {
            if (FogletEntity.this.getTarget() == null || FogletEntity.this.getSkin() != 2) {
                return false;
            } else if (FogletEntity.this.isSpellcasting()) {
                return false;
            } else {
            	return FogletEntity.this.tickCount >= this.spellCooldown && FogletEntity.this.distanceTo(FogletEntity.this.getTarget()) < 3.0F;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        @Override
        public boolean canContinueToUse() {
            return FogletEntity.this.getTarget() != null && this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        @Override
        public void start() {
            this.spellWarmup = this.getCastWarmupTime();
            FogletEntity.this.spellTicks = this.getCastingTime();
            FogletEntity.this.level().broadcastEntityEvent(FogletEntity.this, (byte)10);
            this.spellCooldown = FogletEntity.this.tickCount + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();

            if (soundevent != null) {
                FogletEntity.this.playSound(soundevent, 1.0F, 1.0F);
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        @Override
        public void tick() {
            --this.spellWarmup;

            if (this.spellWarmup == 0) {
                this.castSpell();
                FogletEntity.this.playSound(SoundEvents.BLAZE_SHOOT, 1.0F, 1.0F);
                FogletEntity.this.addEffect(new MobEffectInstance(MobEffects.WEAKNESS/*FUREffectRegistry.IMMOLATION*/, 8 * 20));
            }
        }
        
        protected void castSpell() {
        	List<Entity> list = FogletEntity.this.level().getEntities(FogletEntity.this, FogletEntity.this.getBoundingBox().inflate(3.0D));
        	
        	FogletEntity.this.level().addParticle(ParticleTypes.EXPLOSION, FogletEntity.this.getX(), FogletEntity.this.getY() + FogletEntity.this.getBbHeight(), FogletEntity.this.getZ(), 0.0D, 0.0D, 0.0D);
        	
			if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(FogletEntity.this.level(), FogletEntity.this)) {
				BlockPos blockpos = FogletEntity.this.blockPosition();
				for(int i = -3 ; i < 3 ; i++) {
					for(int j = -3 ; j < 3 ; j++) {
						for(int k = -3 ; k < 3 ; k++) {					
				            if (FogletEntity.this.random.nextFloat() < 0.3F && FogletEntity.this.level().isEmptyBlock(blockpos.offset(i, j, k))) {
				            	FogletEntity.this.level().setBlockAndUpdate(blockpos.offset(i, j, k), BaseFireBlock.getState(FogletEntity.this.level(), blockpos.offset(i, j, k)));
				            }
						}
					}
				}
			}
			
        	for (Entity entity1 : list) {
        		if (entity1 instanceof LivingEntity) {                 
        			if (!((LivingEntity)entity1).fireImmune()) {        				
        				if (((LivingEntity)entity1).hurt(FogletEntity.this.damageSources().mobAttack(FogletEntity.this), (float) FogletEntity.this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 1.0F)) {
        					((LivingEntity)entity1).setRemainingFireTicks(4);
        				}       							
        			}
        		}
        	}
        }

        protected int getCastWarmupTime() {
            return 20;
        }

        protected int getCastingTime() {
            return 100;
        }

        protected int getCastingInterval() {
            return 200;
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.FURNACE_FIRE_CRACKLE;
        }
    }
    
    static class AttackGoal extends FURMeleeAttackGoal {
        public AttackGoal(PathfinderMob p_i46676_1_) {
           super(p_i46676_1_, 1.0D, true);
        }

    	protected int atkTimerMax() {
    		return ATTACK_TIMER;
    	}
    	
    	protected int atkTimerHit() {
    		return 10;
    	}
    	
    	protected byte atkTimerEvent() {
    		return (byte) 5;
    	}
	}
    
    @Override
    protected float getSoundVolume() {
        return this.isDeadOrDying() ? 0.4F : 1.0F;
	}
    
    @Override
    protected SoundEvent getAmbientSound() {
        return FURSoundRegistry.FOGLET_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FURSoundRegistry.FOGLET_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.FOGLET_DEATH.get();
    }
    
    protected SoundEvent getSpellSound() {
        return SoundEvents.EVOKER_CAST_SPELL;
    }

	@Override
    protected void playStepSound(BlockPos pos, BlockState state) {
	    this.playSound(SoundEvents.ZOMBIE_STEP, 0.15F, 1.0F);
	}

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
	}
    
    private RawAnimation getWalkAnimation() {
        if (this.isAggressive()) {
        	return RUN;
        } else {
        	return WALK;
        }
    }

    private RawAnimation getIdleAnimation() {
        return IDLE;
    }    
    
    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
    	if (this.getSpellTicks() >= SPELL_TIMER - 5) {
    		state.getController().setAnimation(CAST);
    	} else if (this.getAttackTimer() == ATTACK_TIMER) {
    		state.getController().setAnimation(ATTACK);
    	} else if (this.isSpellcasting() || this.getAttackTimer() > 0) {
    		return PlayState.CONTINUE;
    	} else if (state.isMoving() && !this.isInWater()) {
            state.getController().setAnimation(this.getWalkAnimation());
        } else {
            state.getController().setAnimation(this.getIdleAnimation());
        }
        
        return PlayState.CONTINUE;
    }

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "controller", 5, this::predicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
