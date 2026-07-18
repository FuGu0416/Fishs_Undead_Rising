package com.Fishmod.fur.entities.flying;

import java.util.Random;
import java.util.UUID;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.block.EnigmothEggBlock;
import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.core.SpawnUtil;
import com.Fishmod.fur.data.providers.FUREntityTypeTagsProvider;
import com.Fishmod.fur.entities.ai.FlyerFollowOwnerGoal;
import com.Fishmod.fur.entities.projectiles.MothScalesEntity;
import com.Fishmod.fur.entities.tameable.CocoonEntity;
import com.Fishmod.fur.init.FURBlockRegistry;
import com.Fishmod.fur.init.FUREffectRegistry;
import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURItemRegistry;
import com.Fishmod.fur.init.FURSoundRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
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

public class EnigmothEntity extends RidableFlyingMobEntity implements GeoEntity {
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	
	private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("enigmoth.model.idle");
	private static final RawAnimation WALK = RawAnimation.begin().thenLoop("enigmoth.model.walk");
    private static final RawAnimation FLY = RawAnimation.begin().thenLoop("enigmoth.model.fly");
    private static final RawAnimation ATTACK = RawAnimation.begin().thenPlay("enigmoth.model.attack_blend");
    private static final RawAnimation CAST = RawAnimation.begin().thenPlay("enigmoth.model.cast_blend");
    
	private static final EntityDataAccessor<Integer> SKIN_TYPE = SynchedEntityData.defineId(EnigmothEntity.class, EntityDataSerializers.INT);

	private int skinFixedTick;
	private int dustCooldown = 0;
	private static final int DUST_COOLDOWN_TIME = 6000; // 5 minutes
	
	public EnigmothEntity(EntityType<? extends EnigmothEntity> entityType, Level worldIn) {
		super(entityType, worldIn);		
	}
	
	@Override
	protected void registerGoals() {
		super.registerGoals();		
		this.goalSelector.addGoal(2, new AIFlyingAttackMelee(this, 1.0D, true));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(Items.CHORUS_FRUIT, Items.POPPED_CHORUS_FRUIT), false));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(Items.END_ROD), false));
		this.goalSelector.addGoal(4, new EnigmothEntity.AIUseSpell());  	
		
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));		
		
        this.targetSelector.addGoal(4, new NonTameRandomTargetGoal<>(this, Player.class, false, (target) -> {
            return !(target.isPassenger() && target.getVehicle() instanceof EnigmothEntity);
        }).setUnseenMemoryTicks(160));        
        
    	this.targetSelector.addGoal(4, new NonTameRandomTargetGoal<>(this, LivingEntity.class, false, (target) -> {
    		return !this.isBaby() && ((LivingEntity)target).attackable() && target.getType().is(FUREntityTypeTagsProvider.ENIGMOTH_TARGETS);
    	}).setUnseenMemoryTicks(160));
	}
	
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.05D)
        		.add(Attributes.FOLLOW_RANGE, 32.0D)
        		.add(Attributes.MAX_HEALTH, 50.0D)
        		.add(Attributes.ATTACK_DAMAGE, 8.0D)
        		.add(Attributes.FLYING_SPEED, 0.67D);
    }
	
    @Override
    protected void defineSynchedData() {
    	super.defineSynchedData();
    	this.getEntityData().define(SKIN_TYPE, Integer.valueOf(0));   	   	
    }
    
    public static boolean checkEnigmothSpawnRules(EntityType<? extends EnigmothEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
    	boolean flag = ((level instanceof ServerLevel serverLevel) && (serverLevel.dimension() == Level.END)) ? (random.nextInt(3) == 0) : (random.nextInt(20) == 0);
    	BlockPos ground = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, pos);
    	return flag && (pos.getY() >= ground.getY() - 2) && FlyingMobEntity.checkFlyerSpawnRulesNoRestriction(entityType, level, spawnType, pos, random);
    }
    
    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    @Override
    public int getMaxSpawnClusterSize() {
       return 1;
    }

    @Override
    public void setBaby(boolean baby) {
        super.setBaby(baby);
        
    	if (this.isWandering()) {
    		this.goalSelector.removeGoal(this.wander);
    		this.wander = this.wanderGoal();
    		this.goalSelector.addGoal(7, this.wander);
    	}
	}
    
    @Override
    protected Goal wanderGoal() {
    	return (this.isBaby() || this.getNavigation() instanceof GroundPathNavigation) ? new WaterAvoidingRandomStrollGoal(this, 1.0D) : new FlyingMobEntity.AIRandomFly(this, 1.0D);
    }
    
    @Override
    protected Goal followGoal() {
    	return new FlyerFollowOwnerGoal(this, 1.0D, 10.0F, 4.0F, false, 24.0D);
    }
    
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
    	ItemStack stack = player.getItemInHand(hand);

    	if (this.isTame() && this.isOwnedBy(player) && !this.isBaby()) {
            if (stack.is(Items.BRUSH) && this.dustCooldown <= 0) {
            	this.spawnAtLocation(new ItemStack(FURItemRegistry.ENIGMOTH_DUST.get()), 0.0F);
                this.swing(InteractionHand.MAIN_HAND);
                this.playSound(SoundEvents.BRUSH_GENERIC, 0.8F, 1.0F);
                
                if (!player.getAbilities().instabuild) {
	                stack.hurtAndBreak(1, player, (p) -> {
	                    p.broadcastBreakEvent(hand);
	                });
                }

                this.dustCooldown = DUST_COOLDOWN_TIME;
                
                return InteractionResult.SUCCESS;
            } else if (stack.is(Items.NETHER_WART)) { 	
				this.skinFixedTick = 2 * 60 * 20;
				this.level().broadcastEntityEvent(this, (byte)39);
				this.setSkin(2);
				if (!player.getAbilities().instabuild) {
					stack.shrink(1);
				}
				
	        	this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);

	        	for (int i = 0; i < 16; ++i) {
	                double d0 = new Random().nextGaussian() * 0.02D;
	                double d1 = new Random().nextGaussian() * 0.02D;
	                double d2 = new Random().nextGaussian() * 0.02D;
	                this.level().addParticle(ParticleTypes.ENTITY_EFFECT, this.getX() + (double)(new Random().nextFloat() * this.getBbWidth()) - (double)this.getBbWidth(), this.getY() + (double)(new Random().nextFloat() * this.getBbHeight()), this.getZ() + (double)(new Random().nextFloat() * this.getBbWidth()) - (double)this.getBbWidth(), d0, d1, d2);
	            }	
	        	
				return InteractionResult.SUCCESS;
			} else if (stack.is(Items.BLAZE_POWDER)) { 	
				this.skinFixedTick = 2 * 60 * 20;
				this.level().broadcastEntityEvent(this, (byte)39);
				this.setSkin(1);
				if (!player.getAbilities().instabuild) {
					stack.shrink(1);
                }

	        	this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);

	        	for (int i = 0; i < 16; ++i) {
	                double d0 = new Random().nextGaussian() * 0.02D;
	                double d1 = new Random().nextGaussian() * 0.02D;
	                double d2 = new Random().nextGaussian() * 0.02D;
	                this.level().addParticle(ParticleTypes.ENTITY_EFFECT, this.getX() + (double)(new Random().nextFloat() * this.getBbWidth()) - (double)this.getBbWidth(), this.getY() + (double)(new Random().nextFloat() * this.getBbHeight()), this.getZ() + (double)(new Random().nextFloat() * this.getBbWidth()) - (double)this.getBbWidth(), d0, d1, d2);
	            }	
	        	
				return InteractionResult.SUCCESS;
			} else if (stack.is(Items.ENDER_PEARL)) { 	
				this.skinFixedTick = 2 * 60 * 20;
				this.level().broadcastEntityEvent(this, (byte)39);
				this.setSkin(0);
				if (!player.getAbilities().instabuild) {
					stack.shrink(1);
				}

	        	this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);

	        	for (int i = 0; i < 16; ++i) {
	                double d0 = new Random().nextGaussian() * 0.02D;
	                double d1 = new Random().nextGaussian() * 0.02D;
	                double d2 = new Random().nextGaussian() * 0.02D;
	                this.level().addParticle(ParticleTypes.ENTITY_EFFECT, this.getX() + (double)(new Random().nextFloat() * this.getBbWidth()) - (double)this.getBbWidth(), this.getY() + (double)(new Random().nextFloat() * this.getBbHeight()), this.getZ() + (double)(new Random().nextFloat() * this.getBbWidth()) - (double)this.getBbWidth(), d0, d1, d2);
	            }	
	        	
				return InteractionResult.SUCCESS;
			}			
    	}

    	return super.mobInteract(player, hand); 	
    }
    
    @Override
    public boolean isFood(ItemStack stack) {
        return /*this.isTame() && */(stack.getItem().equals(Items.CHORUS_FRUIT) || stack.getItem().equals(Items.POPPED_CHORUS_FRUIT));
    }
    
    @Override
    protected int TameRate(ItemStack stack) {
		return super.TameRate(stack);
    }
    
    @Override
    protected void ageBoundaryReached() {
    	if (this.isBaby()) {
    		this.setNoGravity(false);
    		this.moveControl = new MoveControl(this);
    		this.navigation = new GroundPathNavigation(this, this.level());
    		
	        if (this.isSaddled()) {
	        	this.setSaddled(false);
	            this.spawnAtLocation(Items.SADDLE, 1);
	        }
    		
        	this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Enigmoth_Health.get() * 0.2F);
        	this.setHealth(this.getHealth() * 0.2F);
        	this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Enigmoth_Attack.get() * 0.25F);
    	} else {
    		if (this.level() instanceof ServerLevel server) {		
    			this.playSound(FURSoundRegistry.PARASITE_WEAVE.get(), 1.0F, 1.0F);
    	        CompoundTag CompoundTag = new CompoundTag();
    	        this.addAdditionalSaveData(CompoundTag);
    	         	        
	    		CocoonEntity pupa = SpawnUtil.trySpawnEntity(FUREntityRegistry.COCOON.get(), server, this.blockPosition());
	    		
	    		if (pupa != null) {
	    			pupa.serializeNBT().put("EnigmothData", CompoundTag);
	    			pupa.setSkin(1);
	    			
	    			if (this.isTame() && this.getOwner() instanceof Player player) {
	    				pupa.tame(player);
	    			}
	    		}
    		}   
    		
    		this.discard();
    	}
    }   
    
    public boolean canBeAffected(MobEffectInstance effect) {
        if (effect.getEffect() == MobEffects.POISON || effect.getEffect() == FUREffectRegistry.VOID_DUST.get()) {
           return false;
        }
        
        return super.canBeAffected(effect);
	}
    
    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
    	return dimensions.height * 0.45F;
    }
    
    @Override
	public int abilityCooldown() {
    	return FURConfig.Enigmoth_Ability_Cooldown_Mount.get() * 20;
    }
    
	public void aiStep() {
		if (this.level().isClientSide && this.dustCooldown <= 0) {
			if (!this.isBaby()) {
				for(int i = 0; i < 2; ++i) {
					this.level().addParticle(ParticleTypes.PORTAL, this.getRandomX(0.5D), this.getRandomY() - 0.25D, this.getRandomZ(0.5D), (this.random.nextDouble() - 0.5D) * 2.0D, -this.random.nextDouble(), (this.random.nextDouble() - 0.5D) * 2.0D);
				}
			} else if (this.isBaby() && this.getSpellTicks() > 0) {
				for(int i = 0; i < 2 + ((60 - this.getSpellTicks()) / 5); ++i) {
					this.level().addParticle(ParticleTypes.PORTAL, this.getRandomX(0.5D), this.getRandomY() - 0.25D, this.getRandomZ(0.5D), (this.random.nextDouble() - 0.5D) * 2.0D, -this.random.nextDouble(), (this.random.nextDouble() - 0.5D) * 2.0D);
				}				
			}
		}
		
		if (this.skinFixedTick > 0) {
			--this.skinFixedTick;
		}
		
	    if (this.dustCooldown > 0) {
	    	--this.dustCooldown;
	    }
	    
        super.aiStep();
	}
    
    @Override
    public void tick() {
    	super.tick();    
    	
    	if (!this.isBaby() && !this.onGround() && this.tickCount % 60 == 0 && !this.level().isClientSide()) {
    		this.playSound(this.getFlyingSound(), 1.0F, 1.0F);
    	}
    }
    
	@Override
	public boolean doHurtTarget(Entity par1Entity) {
		boolean flag = super.doHurtTarget(par1Entity);
		
        if (flag) {
        	this.level().broadcastEntityEvent(this, (byte)4);
        }
        
		return flag;
	}

    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean hurt(DamageSource source, float amount) {    	    	   
 	   	return super.hurt(source, amount);
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
    	this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Enigmoth_Health.get() * (this.isBaby() ? 0.2F : 1.0F));
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Enigmoth_Attack.get() * (this.isBaby() ? 0.25F : 1.0F));
    	this.setHealth(this.getMaxHealth());
 
    	if ((worldIn.getBiome(this.blockPosition()).is(Biomes.END_HIGHLANDS) || worldIn.getBiome(this.blockPosition()).is(Biomes.END_MIDLANDS)) 
    			&& (spawnType != MobSpawnType.SPAWN_EGG && spawnType != MobSpawnType.MOB_SUMMONED) && (this.level().getRandom().nextFloat() <= 0.8F)) {    
		    this.setBaby(true);   		
    	}
    	
    	return super.finalizeSpawn(worldIn, difficulty, spawnType, livingdata, tag);
    }
      
    public int getSkin() {
    	return this.getEntityData().get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
    	this.getEntityData().set(SKIN_TYPE, Integer.valueOf(skinType));
    }
    
    public int getSkinFixedTick() {
       return this.skinFixedTick;
    }
    
    @Override
	protected double VehicleSpeedMod() {
		return (this.isInLava() || this.isInWater()) ? 0.2D : 2.0D;
	}
    
	@Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
    	if (level.getBlockState(pos).getBlock().equals(Blocks.END_ROD)) {
    		return 20.0F;
    	} else {
    		return super.getWalkTargetValue(pos, level);
    	}
    }
    
    /**
     * Handler for {@link World#setEntityState}
     */
	@Override
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
		switch(id) {
			case 4:
				this.triggerAnim("trigger_controller", "attack");
				break;
			case 10:
				this.triggerAnim("trigger_controller", "cast");
				break;
			case 39:
				this.skinFixedTick = 2 * 60 * 20;
				break;
			default:
				super.handleEntityEvent(id);
				break;
		}
    }
	
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setSkin(compound.getInt("Variant"));
        this.skinFixedTick = compound.getInt("SkinFixedTick");
        this.dustCooldown = compound.getInt("dustCooldown");
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.getSkin());
        compound.putInt("SkinFixedTick", this.skinFixedTick);
        compound.putInt("dustCooldown", this.dustCooldown);
    }
		
	public class AIUseSpell extends Goal {
        protected int spellWarmup;
        protected int spellCooldown;

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean canUse() {       	
            if (EnigmothEntity.this.getTarget() == null) {
                return false;
            } else if (EnigmothEntity.this.isSpellcasting() || EnigmothEntity.this.distanceTo(EnigmothEntity.this.getTarget()) > (EnigmothEntity.this.isBaby() ? 8.0F : 3.0F)) {
                return false;
            } else {
            	return EnigmothEntity.this.tickCount >= this.spellCooldown;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return EnigmothEntity.this.getTarget() != null && this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.spellWarmup = this.getCastWarmupTime();
            EnigmothEntity.this.spellTicks = this.getCastingTime();
            this.spellCooldown = EnigmothEntity.this.tickCount + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();
            EnigmothEntity.this.level().broadcastEntityEvent(EnigmothEntity.this, (byte)10);         
            if (soundevent != null) {
                EnigmothEntity.this.playSound(soundevent, 1.0F, 1.0F);
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            --this.spellWarmup;          
    		
            if (this.spellWarmup == 0) {
            	EnigmothEntity.this.playSound(EnigmothEntity.this.getSpellSound(), 0.175F, 1.0F);
            	if (EnigmothEntity.this.isBaby()) {
            		EnigmothEntity.this.discard();
            	} else {
            		this.castSpell();
            	}
                
            }
        }
        
        protected void castSpell() {
       	 	for(int i = 0 ; i < 8 ; i++) {
       	 		Double d0 = new Random().nextDouble() * 8.0D - 4.0D;
       	 		Double d1 = new Random().nextDouble() * 8.0D - 4.0D;
       	 		MothScalesEntity entityammo = new MothScalesEntity(FUREntityRegistry.MOTH_SCALES.get(), EnigmothEntity.this, d0, - 2.4D, d1, EnigmothEntity.this.level());
       	 		entityammo.setPos(EnigmothEntity.this.getX() + d0 * 0.25D, EnigmothEntity.this.getY() + (double)(EnigmothEntity.this.getBbHeight() / 2.0F) + 1.5D, EnigmothEntity.this.getZ() + d1 * 0.25D);
	       	 	
       	 		if(!EnigmothEntity.this.level().isClientSide()) {
	       	 		EnigmothEntity.this.level().addFreshEntity(entityammo);	
	       	 		entityammo.setScaleType(EnigmothEntity.this.getSkin());
	       	 	}
       	 	}	
       	 	
       	 	if (EnigmothEntity.this.getSkin() == 2) {
       	 		EnigmothEntity.this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 * 20, 0));
       	 	}

       	 	if (EnigmothEntity.this.getSkinFixedTick() == 0) {
       	 		EnigmothEntity.this.setSkin(EnigmothEntity.this.getRandom().nextInt(3));
       	 	}
        }

        protected int getCastWarmupTime() {
            return EnigmothEntity.this.isBaby() ? 60 : 15;
        }

        protected int getCastingTime() {
            return EnigmothEntity.this.isBaby() ? 60: 15;
        }

        protected int getCastingInterval() {
        	return FURConfig.Enigmoth_Ability_Cooldown.get() * 20;
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound() {
        	return null;
        }
    }
		
	@Override
	public int getAmbientSoundInterval() {
		return 1000;
	}
	
	public SoundSource getSoundSource() {
		return SoundSource.HOSTILE;
	}

	protected SoundEvent getAmbientSound() {
		return this.isBaby() ? FURSoundRegistry.PARASITE_AMBIENT.get() : FURSoundRegistry.ENIGMOTH_AMBIENT.get();
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return this.isBaby() ? FURSoundRegistry.PARASITE_HURT.get() : FURSoundRegistry.ENIGMOTH_HURT.get();
	}

	protected SoundEvent getDeathSound() {
		return this.isBaby() ? FURSoundRegistry.PARASITE_DEATH.get() : FURSoundRegistry.ENIGMOTH_DEATH.get();
	}
	
    public SoundEvent getSpellSound() {
        return this.isBaby() ? SoundEvents.ENDERMAN_TELEPORT : SoundEvents.EVOKER_CAST_SPELL;
    }
	
	protected SoundEvent getFlyingSound() {
		return FURSoundRegistry.ENIGMOTH_FLAP.get();
	}
	
	@Override
    protected void playStepSound(BlockPos pos, BlockState state) {
		if (this.getLandTimer() > 10) {
			this.playSound(SoundEvents.SPIDER_STEP, 0.15F, 1.0F);
		}
	}
	
    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

	/**
	* Returns the volume for the sounds this mob makes.
	*/
	protected float getSoundVolume() {
		return 0.5F;
	}
	
    @Nullable
    @Override
    protected ResourceLocation getDefaultLootTable() {
    	return this.isBaby() ? new ResourceLocation(mod_LavaCow.MODID, "entities/enigmoth_larva") : super.getDefaultLootTable();
    }

    @Override
	protected boolean shouldDropLoot() {
    	return true;
	}
	
    @Override
    public void spawnChildFromBreeding(ServerLevel level, Animal other) {
        BlockPos eggPos = findEnigmothEggPos(level);
        if (eggPos != null) {
            // Spawn a cluster of 2–4 eggs, matching EnigmothEggBlock.canBeReplaced stacking behavior
            int eggCount = 2 + level.getRandom().nextInt(3); // 2, 3, or 4

            level.setBlock(eggPos, FURBlockRegistry.ENIGMOTH_EGG.get().defaultBlockState().setValue(EnigmothEggBlock.EGGS, eggCount), 3);
        }
        this.finalizeSpawnChildFromBreeding(level, other, null);
    }

    private BlockPos findEnigmothEggPos(ServerLevel level) {
        BlockPos origin = this.blockPosition();
        for (BlockPos p : BlockPos.betweenClosed(origin.offset(-3, -1, -3), origin.offset(3, 1, 3))) {
            if (level.getBlockState(p).is(Blocks.END_STONE)) {
                BlockPos above = p.above();
                if (level.getBlockState(above).isAir()) {
                    return above.immutable();
                }
            }
        }
        for (BlockPos p : BlockPos.betweenClosed(origin.offset(-2, -1, -2), origin.offset(2, 1, 2))) {
            BlockPos above = p.above();
            if (level.getBlockState(p).isFaceSturdy(level, p, Direction.UP)
                    && level.getBlockState(above).isAir()) {
                return above.immutable();
            }
        }
        return null;
    }

    @Override
	public EnigmothEntity getBreedOffspring(ServerLevel worldIn, AgeableMob ageable) {
    	EnigmothEntity entity = FUREntityRegistry.ENIGMOTH.get().create(worldIn);
		UUID uuid = this.getOwnerUUID();
		if (uuid != null) {
			entity.setOwnerUUID(uuid);
			entity.setTame(true);
			entity.setHealth(this.getMaxHealth());
		}

		return entity;
	}

    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
		if (this.onGround() || this.isBaby()) {
			if (state.isMoving() || !this.getNavigation().isDone()) {
				state.getController().setAnimation(WALK);
			} else {
				state.getController().setAnimation(IDLE);
			}
		} else {
			state.getController().setAnimation(FLY);
		}
        
    	return PlayState.CONTINUE;
    }    

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "controller", 5, this::predicate));
		controllers.add(new AnimationController<>(this, "trigger_controller", 5, state -> PlayState.STOP).triggerableAnim("attack", ATTACK).triggerableAnim("cast", CAST));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
