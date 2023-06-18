package com.Fishmod.mod_LavaCow.entities.flying;

import java.util.Random;
import java.util.UUID;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.VespaCocoonEntity;
import com.Fishmod.mod_LavaCow.entities.ai.FlyerFollowOwnerGoal;
import com.Fishmod.mod_LavaCow.entities.projectiles.MothScalesEntity;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;
import com.Fishmod.mod_LavaCow.init.FURTagRegistry;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.NonTamedTargetGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EnigmothEntity extends RidableFlyingMobEntity {
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.defineId(EnigmothEntity.class, DataSerializers.INT);

	private int skinFixedTick;
	
	public EnigmothEntity(EntityType<? extends EnigmothEntity> p_i48549_1_, World worldIn) {
		super(p_i48549_1_, worldIn);		
	}
	
	@Override
	protected void registerGoals() {
		super.registerGoals();		
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(Items.CHORUS_FRUIT, Items.POPPED_CHORUS_FRUIT), false));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(Items.END_ROD), false));
		this.goalSelector.addGoal(4, new EnigmothEntity.AIUseSpell());  	    
	    
        this.targetSelector.addGoal(4, new NonTamedTargetGoal<>(this, PlayerEntity.class, false, (p_213440_0_) -> {
            return !(p_213440_0_.isPassenger() && p_213440_0_.getVehicle() instanceof EnigmothEntity);
        }).setUnseenMemoryTicks(160));        
        
    	this.targetSelector.addGoal(4, new NonTamedTargetGoal<>(this, LivingEntity.class, false, (p_210136_0_) -> {
    		ITag<EntityType<?>> tag = EntityTypeTags.getAllTags().getTag(FURTagRegistry.ENIGMOTH_TARGETS);
    		return tag != null && !this.isBaby() && p_210136_0_ instanceof LivingEntity && ((LivingEntity)p_210136_0_).attackable() && p_210136_0_.getType().is(tag);
    	}).setUnseenMemoryTicks(160));
	}
	
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.05D)
        		.add(Attributes.FOLLOW_RANGE, 32.0D)
        		.add(Attributes.MAX_HEALTH, FURConfig.Enigmoth_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, FURConfig.Enigmoth_Attack.get())
        		.add(Attributes.FLYING_SPEED, 0.067D);
    }
	
    @Override
    protected void defineSynchedData() {
    	super.defineSynchedData();
    	this.getEntityData().define(SKIN_TYPE, Integer.valueOf(0));   	   	
    }
    
    @Override
    public void setTame(boolean tamed) {
    	super.setTame(tamed);
        
        if (tamed) {
        	this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Enigmoth_Health.get() * 2.0D);
        	this.setHealth(this.getHealth() * 2.0F);
        } else {
        	this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Enigmoth_Health.get());
        	this.setHealth(this.getHealth() * 0.5F);
        }
	}
    
    @Override
    protected Goal wanderGoal() {
    	return (this.isBaby() || this.getNavigation() instanceof GroundPathNavigator) ? new WaterAvoidingRandomWalkingGoal(this, 1.0D) : new FlyingMobEntity.AIRandomFly(this);
    }
    
    @Override
    protected Goal followGoal() {
    	return new FlyerFollowOwnerGoal(this, 1.0D, 10.0F, 4.0F, false, 24.0D);
    }
    
    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
    	ItemStack itemstack = player.getItemInHand(hand);
    	Item item = itemstack.getItem();

    	if (this.isTame() && this.isOwnedBy(player) && !this.isBaby()) {
			if (item.equals(Items.NETHER_WART)) { 	
				this.skinFixedTick = 2 * 60 * 20;
				this.level.broadcastEntityEvent(this, (byte)39);
				this.setSkin(2);
				if (!player.abilities.instabuild) {
					itemstack.shrink(1);
				}
				
	        	this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);

	        	for (int i = 0; i < 16; ++i) {
	                double d0 = new Random().nextGaussian() * 0.02D;
	                double d1 = new Random().nextGaussian() * 0.02D;
	                double d2 = new Random().nextGaussian() * 0.02D;
	                this.level.addParticle(ParticleTypes.ENTITY_EFFECT, this.getX() + (double)(new Random().nextFloat() * this.getBbWidth()) - (double)this.getBbWidth(), this.getY() + (double)(new Random().nextFloat() * this.getBbHeight()), this.getZ() + (double)(new Random().nextFloat() * this.getBbWidth()) - (double)this.getBbWidth(), d0, d1, d2);
	            }	
	        	
				return ActionResultType.SUCCESS;
			} else if (item.equals(Items.BLAZE_POWDER)) { 	
				this.skinFixedTick = 2 * 60 * 20;
				this.level.broadcastEntityEvent(this, (byte)39);
				this.setSkin(1);
				if (!player.abilities.instabuild) {
					itemstack.shrink(1);
                }

	        	this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);

	        	for (int i = 0; i < 16; ++i) {
	                double d0 = new Random().nextGaussian() * 0.02D;
	                double d1 = new Random().nextGaussian() * 0.02D;
	                double d2 = new Random().nextGaussian() * 0.02D;
	                this.level.addParticle(ParticleTypes.ENTITY_EFFECT, this.getX() + (double)(new Random().nextFloat() * this.getBbWidth()) - (double)this.getBbWidth(), this.getY() + (double)(new Random().nextFloat() * this.getBbHeight()), this.getZ() + (double)(new Random().nextFloat() * this.getBbWidth()) - (double)this.getBbWidth(), d0, d1, d2);
	            }	
	        	
				return ActionResultType.SUCCESS;
			} else if (item.equals(Items.ENDER_PEARL)) { 	
				this.skinFixedTick = 2 * 60 * 20;
				this.level.broadcastEntityEvent(this, (byte)39);
				this.setSkin(0);
				if (!player.abilities.instabuild) {
					itemstack.shrink(1);
				}

	        	this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);

	        	for (int i = 0; i < 16; ++i) {
	                double d0 = new Random().nextGaussian() * 0.02D;
	                double d1 = new Random().nextGaussian() * 0.02D;
	                double d2 = new Random().nextGaussian() * 0.02D;
	                this.level.addParticle(ParticleTypes.ENTITY_EFFECT, this.getX() + (double)(new Random().nextFloat() * this.getBbWidth()) - (double)this.getBbWidth(), this.getY() + (double)(new Random().nextFloat() * this.getBbHeight()), this.getZ() + (double)(new Random().nextFloat() * this.getBbWidth()) - (double)this.getBbWidth(), d0, d1, d2);
	            }	
	        	
				return ActionResultType.SUCCESS;
			}			
    	}

    	return super.mobInteract(player, hand); 	
    }
    
    @Override
    public boolean isFood(ItemStack stack) {
        return this.isTame() && (stack.getItem().equals(Items.CHORUS_FRUIT) || stack.getItem().equals(Items.POPPED_CHORUS_FRUIT));
    }
    
    @Override
    protected void ageBoundaryReached() {
    	if(this.isBaby()) {
    		this.setNoGravity(false);
    		this.moveControl = new MovementController(this);
    		this.navigation = new GroundPathNavigator(this, this.level);
    		
	        if(this.isSaddled()) {
	        	this.setSaddled(false);
	            this.spawnAtLocation(Items.SADDLE, 1);
	        }
    		
        	this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Enigmoth_Health.get() * 0.2F);
        	this.setHealth(this.getHealth() * 0.2F);
        	this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Enigmoth_Attack.get() * 0.25F);
    	} else {
    		if (!this.level.isClientSide) {		
    			this.playSound(FURSoundRegistry.PARASITE_WEAVE, 1.0F, 1.0F);
    	        CompoundNBT compoundnbt = new CompoundNBT();
    	        this.addAdditionalSaveData(compoundnbt);
    	         	        
	    		VespaCocoonEntity pupa = FUREntityRegistry.VESPACOCOON.create(this.level);
	    		pupa.serializeNBT().put("EnigmothData", compoundnbt);
	    		pupa.moveTo(this.getX(), this.getY(), this.getZ(), this.yRot, this.xRot);
	    		pupa.setSkin(1);
	    		this.level.addFreshEntity(pupa);
    		}   
    		
    		this.remove();
    	}
    }    
    
    @Override
    protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
    	return p_213348_2_.height * 0.45F;
    }
    
    @Override
	public int abilityCooldown() {
    	return FURConfig.Enigmoth_Ability_Cooldown_Mount.get() * 20;
    }
    
	public void aiStep() {
		if (this.level.isClientSide) {
			if (!this.isBaby()) {
				for(int i = 0; i < 2; ++i) {
					this.level.addParticle(ParticleTypes.PORTAL, this.getRandomX(0.5D), this.getRandomY() - 0.25D, this.getRandomZ(0.5D), (this.random.nextDouble() - 0.5D) * 2.0D, -this.random.nextDouble(), (this.random.nextDouble() - 0.5D) * 2.0D);
				}
			} else if (this.isBaby() && this.getSpellTicks() > 0) {
				for(int i = 0; i < 2 + ((60 - this.getSpellTicks()) / 5); ++i) {
					this.level.addParticle(ParticleTypes.PORTAL, this.getRandomX(0.5D), this.getRandomY() - 0.25D, this.getRandomZ(0.5D), (this.random.nextDouble() - 0.5D) * 2.0D, -this.random.nextDouble(), (this.random.nextDouble() - 0.5D) * 2.0D);
				}				
			}
		}
		
		if (this.skinFixedTick > 0) {
			--this.skinFixedTick;
		}
		
        super.aiStep();
	}
    
    @Override
    public void tick() {
    	super.tick();    
    	
    	if (!this.onGround && this.tickCount % 60 == 0) {
    		this.playSound(this.getFlyingSound(), 1.0F, 1.0F);
    	}
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
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT p_213386_5_) {
    	this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Enigmoth_Health.get() * (this.isBaby() ? 0.2F : 1.0F));
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Enigmoth_Attack.get() * (this.isBaby() ? 0.25F : 1.0F));
    	this.setHealth(this.getMaxHealth());
 
    	if(SpawnUtil.getRegistryKey(worldIn.getBiome(this.blockPosition())).equals(Biomes.END_HIGHLANDS) && p_213386_3_ != SpawnReason.SPAWN_EGG) {
    		this.setBaby(this.level.getRandom().nextFloat() <= 0.8F);
    	}
    	
    	return super.finalizeSpawn(worldIn, difficulty, p_213386_3_, livingdata, p_213386_5_);
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
    public float getWalkTargetValue(BlockPos p_205022_1_, IWorldReader p_205022_2_) {
    	if (p_205022_2_.getBlockState(p_205022_1_).getBlock().equals(Blocks.END_ROD)) {
    		return 20.0F;
    	} else {
    		return super.getWalkTargetValue(p_205022_1_, p_205022_2_);
    	}
    }
    
    /**
     * Handler for {@link World#setEntityState}
     */
	@Override
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
		switch(id) {
			case 10:
				this.spellTicks = 15;
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
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.setSkin(compound.getInt("Variant"));
        this.skinFixedTick = compound.getInt("SkinFixedTick");
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.getSkin());
        compound.putInt("SkinFixedTick", this.skinFixedTick);
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
            } else if (EnigmothEntity.this.isSpellcasting() || EnigmothEntity.this.getAttackTimer() > 0 || EnigmothEntity.this.distanceTo(EnigmothEntity.this.getTarget()) > (EnigmothEntity.this.isBaby() ? 8.0F : 3.0F)) {
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
            EnigmothEntity.this.level.broadcastEntityEvent(EnigmothEntity.this, (byte)10);         
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
            		EnigmothEntity.this.remove();
            	} else {
            		this.castSpell();
            	}
                
            }
        }
        
        protected void castSpell() {
       	 	for(int i = 0 ; i < 8 ; i++) {
       	 		Double d0 = new Random().nextDouble() * 8.0D - 4.0D;
       	 		Double d1 = new Random().nextDouble() * 8.0D - 4.0D;
       	 		MothScalesEntity entityammo = new MothScalesEntity(FUREntityRegistry.MOTH_SCALES, EnigmothEntity.this, d0, - 2.4D, d1, EnigmothEntity.this.level);
       	 		entityammo.setPos(EnigmothEntity.this.getX() + d0 * 0.25D, EnigmothEntity.this.getY() + (double)(EnigmothEntity.this.getBbHeight() / 2.0F) + 1.5D, EnigmothEntity.this.getZ() + d1 * 0.25D);
	       	 	
       	 		if(!EnigmothEntity.this.level.isClientSide()) {
	       	 		EnigmothEntity.this.level.addFreshEntity(entityammo);	
	       	 		entityammo.setScaleType(EnigmothEntity.this.getSkin());
	       	 	}
       	 	}	
       	 	
       	 	if (EnigmothEntity.this.getSkin() == 2) {
       	 		EnigmothEntity.this.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 20 * 20, 0));
       	 	}

       	 	if (EnigmothEntity.this.getSkinFixedTick() == 0) {
       	 		EnigmothEntity.this.setSkin(EnigmothEntity.this.getRandom().nextInt(3));
       	 	}
        }

        protected int getCastWarmupTime() {
            return EnigmothEntity.this.isBaby() ? 60 : 10;
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
	
	public SoundCategory getSoundCategory() {
		return SoundCategory.HOSTILE;
	}

	protected SoundEvent getAmbientSound() {
		return FURSoundRegistry.ENIGMOTH_AMBIENT;
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return FURSoundRegistry.ENIGMOTH_HURT;
	}

	protected SoundEvent getDeathSound() {
		return FURSoundRegistry.ENIGMOTH_DEATH;
	}
	
    public SoundEvent getSpellSound() {
        return this.isBaby() ? SoundEvents.ENDERMAN_TELEPORT : SoundEvents.EVOKER_CAST_SPELL;
    }
	
	protected SoundEvent getFlyingSound() {
		return FURSoundRegistry.ENIGMOTH_FLAP;
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
    public CreatureAttribute getMobType() {
        return CreatureAttribute.ARTHROPOD;
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
    	return this.isBaby() ? new ResourceLocation("mod_lavacow", "entities/enigmoth_larva") : super.getDefaultLootTable();
    }

    @Override
	protected boolean shouldDropLoot() {
    	return true;
	}
	
    @Override
	public EnigmothEntity getBreedOffspring(ServerWorld worldIn, AgeableEntity ageable) {
    	EnigmothEntity entity = FUREntityRegistry.ENIGMOTH.create(worldIn);
		UUID uuid = this.getOwnerUUID();
		if (uuid != null) {
			entity.setOwnerUUID(uuid);
			entity.setTame(true);
			entity.setHealth(this.getMaxHealth());
		}

		return entity;
	}
}
