package com.Fishmod.mod_LavaCow.entities.tameable;

import java.util.EnumSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.ai.EntityAITargetItem;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;
import com.Fishmod.mod_LavaCow.misc.LootTableHandler;
import com.google.common.collect.Sets;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.FollowMobGoal;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RavenEntity extends FURTameableEntity implements IFlyingAnimal {
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.defineId(RavenEntity.class, DataSerializers.INT);
    private final Set<Item> TAME_ITEMS = Sets.newHashSet(FURItemRegistry.PARASITE_COMMON, FURItemRegistry.PARASITE_COOKED, FURItemRegistry.PARASITE_DESERT);
    public float flap;
    public float flapSpeed;
    public float oFlapSpeed;
    public float oFlap;
    public float flapping = 1.0F;
    private boolean partyParrot;
    private BlockPos jukebox;
    private int ridingCooldown;
    public int callTimer;
    
    private float TargetLocationX = -1.0F;
    private float TargetLocationY = -1.0F;
    private float TargetLocationZ = -1.0F;
    
    private EntityAITargetItem<ItemEntity> AITargetItem;
	
	public RavenEntity(EntityType<? extends RavenEntity> p_i48549_1_, World worldIn) {
		super(p_i48549_1_, worldIn);
        this.ridingCooldown = 30;
        this.moveControl = new FlyingMovementController(this, 10, false);
        this.setPathfindingMalus(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(PathNodeType.DAMAGE_FIRE, -1.0F);
        this.setCanPickUpLoot(true);
	}
	
    @Override
    protected void registerGoals() {
        super.registerGoals();       
        this.goalSelector.addGoal(0, new AIMovetoTargetLocation());
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new FollowMobGoal(this, 1.0D, 3.0F, 7.0F));
        this.applyEntityAI();
    }
    
    @Override
    protected WaterAvoidingRandomWalkingGoal wanderGoal() {
    	return new WaterAvoidingRandomFlyingGoal(this, 1.0D);
    }
    
    @Override
    protected FollowOwnerGoal followGoal() {
    	return new FollowOwnerGoal(this, 1.0D, 5.0F, 1.0F, true);
    }
    
    protected void applyEntityAI() {
    	this.AITargetItem = new EntityAITargetItem<>(this, ItemEntity.class, true);
    	this.targetSelector.addGoal(1, this.AITargetItem);
	}
    
    public static AttributeModifierMap.MutableAttribute createAttributes() {
    	return MobEntity.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, FURConfig.Raven_Health.get())
    			.add(Attributes.FLYING_SPEED, (double)0.6F)
    			.add(Attributes.MOVEMENT_SPEED, (double)0.2F);
    }
    
    @Override
    protected PathNavigator createNavigation(World worldIn) {
        FlyingPathNavigator flyingpathnavigator = new FlyingPathNavigator(this, worldIn);
        flyingpathnavigator.setCanOpenDoors(false);
        flyingpathnavigator.setCanFloat(true);
        flyingpathnavigator.setCanPassDoors(true);
        return flyingpathnavigator;
	}
    
	@Override
	public boolean removeWhenFarAway(double p_213397_1_) {
		return false;
	}
	
	@Override
    protected boolean shouldDespawnInPeaceful() {
	    return false;
    } 
	
	@Override
	protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
        return p_213348_2_.height * 0.6F;
    }
    
    private void SetDismount(Entity ridden) {  	
    	this.removeVehicle();
		this.setPos(ridden.getX(), ridden.getY() + ridden.getBbHeight()/2 - 0.35F, ridden.getZ());
        this.setJumping(false);
        this.getNavigation().stop();
        this.setTarget(null);       
    }
    
    public void setTargetLocation(float X, float Y, float Z) {
    	this.TargetLocationX = X;
    	this.TargetLocationY = Y;
    	this.TargetLocationZ = Z;
    }
    
    @Override
    public void rideTick() {
    	super.rideTick();
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void aiStep() { 	
    	super.aiStep();
    	
    	if (this.callTimer > 0 ) {
    		this.callTimer--;
    	}
    	
        if (this.jukebox == null || !this.jukebox.closerThan(this.position(), 3.46D) || !this.level.getBlockState(this.jukebox).is(Blocks.JUKEBOX)) {
            this.partyParrot = false;
            this.jukebox = null;
        }
        
        if(!this.isFetching() && this.isTame()) {
        	if(this.ridingCooldown > 0)
        		this.ridingCooldown--;
        	
	        if (this.isPassenger() && this.getVehicle() instanceof PlayerEntity) {
	        	this.setRot(this.getVehicle().yRot, 0F);
	        	
	        	if(FURConfig.Raven_Slowfall.get() && !this.getVehicle().isOnGround() && this.getVehicle().getDeltaMovement().y < 0.0D && 
	        			!this.getVehicle().isNoGravity() && !((PlayerEntity)this.getVehicle()).isFallFlying()) {
	        		((LivingEntity) this.getVehicle()).addEffect(new EffectInstance(Effects.SLOW_FALLING, 3 * 20, 0));
	        	}
	        	
	        	if(this.ridingCooldown == 0 && (this.getVehicle().isCrouching() || this.getVehicle().isInWater())) {
	        		this.SetDismount(this.getVehicle());
	        	}
	        }
	        
	    	if(!this.isInSittingPose() && !this.isPassenger() && this.getMainHandItem().isEmpty() && this.tickCount % 200 == 0 && this.getRandom().nextFloat() < 0.02f) {
	    	    ItemStack chosenDrop = null;
                Map<ItemStack, Float> lootTable = null;

                switch (this.getSkin()) {
                    case 2:
                        lootTable = LootTableHandler.LOOT_SEAGULL;
                        break;
                    case 3:
                        lootTable = LootTableHandler.LOOT_SPECTRAL_RAVEN;
                        break;
                    default:
                        lootTable = LootTableHandler.LOOT_RAVEN;
                        break;
                }

                for(Map.Entry<ItemStack, Float> entry : lootTable.entrySet()) {
                    if (this.getRandom().nextFloat() < entry.getValue()) {
                        chosenDrop = entry.getKey();
                        break;
                    }
                }

	    	    // These are the fallback items in-case no special drop is chosen.
	    	    if (chosenDrop == null) {
                    switch(this.getSkin()) {
                        case 1:
                            chosenDrop = new ItemStack(Items.IRON_NUGGET, 1);
                            break;
                        case 2:
                            chosenDrop = new ItemStack(Items.TROPICAL_FISH, 1);
                            break;
                        case 3:
                            chosenDrop = new ItemStack(FURItemRegistry.ECTOPLASM, 1);
                            break;
                        default:
                            chosenDrop = new ItemStack(FURItemRegistry.FEATHER_BLACK, 1);
                            break;
                    }
                }

	    	    this.setItemInHand(this.getUsedItemHand(), new ItemStack(chosenDrop.getItem(), this.getRandom().nextInt(chosenDrop.getCount()) + 1));
	    	}
        }
        
        if(this.canPickUpLoot() && !this.getMainHandItem().isEmpty()) {
        	this.setCanPickUpLoot(false);
        } else if (!this.canPickUpLoot() && this.getMainHandItem().isEmpty())
        	this.setCanPickUpLoot(true);
             
        this.calculateFlapping();
    }
    
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void tick() {    	
    	this.noPhysics = (this.getSkin() == 3 && this.getY() > SpawnUtil.getHeight(this).getY() + 0.5D);
    	super.tick();
        this.noPhysics = false;
    }

    @OnlyIn(Dist.CLIENT)
    public void setRecordPlayingNearby(BlockPos p_191987_1_, boolean p_191987_2_) {
       this.jukebox = p_191987_1_;
       this.partyParrot = p_191987_2_;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isPartyParrot() {
       return this.partyParrot;
    }

    private void calculateFlapping() {
        this.oFlap = this.flap;
        this.oFlapSpeed = this.flapSpeed;
        this.flapSpeed = (float)((double)this.flapSpeed + (double)(!this.onGround ? 4 : -1) * 0.3D);
        this.flapSpeed = MathHelper.clamp(this.flapSpeed, 0.0F, 1.0F);
        if (!this.onGround && this.flapping < 1.0F) {
           this.flapping = 1.0F;
        }

        this.flapping = (float)((double)this.flapping * 0.9D);
        Vector3d vector3d = this.getDeltaMovement();
        if (!this.onGround && vector3d.y < 0.0D) {
           this.setDeltaMovement(vector3d.multiply(1.0D, 0.6D, 1.0D));
        }

        this.flap += this.flapping * 2.0F;
    }

    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        
        if(this.isOwnedBy(player) && hand.equals(Hand.MAIN_HAND)) {      	
        	if (FURConfig.Raven_Perch.get() && player.isShiftKeyDown() && player.getPassengers().isEmpty()) {
                this.startRiding(player);
                this.ridingCooldown = 20;
                return ActionResultType.SUCCESS;
            } if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
	        	
	            if (!player.isCreative()) {
	                itemstack.shrink(1);
	            }
	
	            if (!this.isSilent()) {
	                this.level.playSound((PlayerEntity)null, this.getX(), this.getY(), this.getZ(), SoundEvents.PARROT_EAT, this.getSoundSource(), 1.0F, 1.0F + (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F);
	            }
	        	
	        	this.heal(2.0F);
	        	
	        	return ActionResultType.sidedSuccess(this.level.isClientSide);
	        } else if (itemstack.getItem() == FURItemRegistry.GHOSTJELLY && this.getSkin() == 0) {
	            if (!player.isCreative()) {
	                itemstack.shrink(1);
	            }
	        	this.setSkin(3);
	        	this.playSound(SoundEvents.AMBIENT_CAVE, 1.0F, 1.0F);
	        	for (int i = 0; i < 16; ++i) {
	                double d0 = new Random().nextGaussian() * 0.02D;
	                double d1 = new Random().nextGaussian() * 0.02D;
	                double d2 = new Random().nextGaussian() * 0.02D;
	                this.level.addParticle(ParticleTypes.ENTITY_EFFECT, this.getX() + (double)(new Random().nextFloat() * this.getBbWidth()) - (double)this.getBbWidth(), this.getY() + (double)(new Random().nextFloat() * this.getBbHeight()), this.getZ() + (double)(new Random().nextFloat() * this.getBbWidth()) - (double)this.getBbWidth(), d0, d1, d2);
	            }
	        	
	        	return ActionResultType.CONSUME;
	        } else if (!this.level.isClientSide() && itemstack.isEmpty() && !this.getMainHandItem().isEmpty()) {     	
            	player.playSound(SoundEvents.ITEM_PICKUP, 1.0F, 1.0F);
            	
            	if (!player.inventory.add(this.getMainHandItem().copy())) {
                    player.spawnAtLocation(this.getMainHandItem().copy());
                }
            	
            	this.getMainHandItem().shrink(this.getMainHandItem().getCount());
        		
        		return ActionResultType.CONSUME;	            
	        }
        }
        
        return super.mobInteract(player, hand);
    }

    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    @Override
    public boolean isFood(ItemStack stack) {
       return TAME_ITEMS.contains(stack.getItem());
    }
    
    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public static boolean checkRavenSpawnRules(EntityType<RavenEntity> p_223317_0_, IWorld p_223317_1_, SpawnReason p_223317_2_, BlockPos p_223317_3_, Random p_223317_4_) {
        BlockState blockstate = p_223317_1_.getBlockState(p_223317_3_.below());
        return /*SpawnUtil.isAllowedDimension(this.dimension) &&*/ (blockstate.is(BlockTags.LEAVES) || blockstate.is(Blocks.GRASS_BLOCK) || blockstate.is(BlockTags.LOGS) || blockstate.is(Blocks.AIR)) && p_223317_1_.getRawBrightness(p_223317_3_, 0) > 8;
	}

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public static boolean checkSeagullSpawnRules(EntityType<RavenEntity> p_223317_0_, IWorld p_223317_1_, SpawnReason p_223317_2_, BlockPos p_223317_3_, Random p_223317_4_) {
        BlockState blockstate = p_223317_1_.getBlockState(p_223317_3_.below());
        return /*SpawnUtil.isAllowedDimension(this.dimension) &&*/ (blockstate.is(BlockTags.SAND) || blockstate.is(Blocks.GRASS_BLOCK) || blockstate.is(Blocks.AIR)) && p_223317_1_.getRawBrightness(p_223317_3_, 0) > 8;
	}
    
    @Override
    public boolean causeFallDamage(float p_225503_1_, float p_225503_2_) {
        return false;
	}

    @Override
	protected void checkFallDamage(double p_184231_1_, boolean p_184231_3_, BlockState p_184231_4_, BlockPos p_184231_5_) {
	}

    @Override
    public boolean canMate(AnimalEntity p_70878_1_) {
        return false;
	}

	@Nullable
	@Override
	public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return null;
	}
    
	@Override
	public double getMyRidingOffset() {
		if (this.getVehicle() != null && this.getVehicle() instanceof PlayerEntity)
			return this.getVehicle().getBbHeight()/2  - 0.35F;
		else
			return super.getMyRidingOffset();
	}

	@Override
    public boolean doHurtTarget(Entity entityIn) {
        return entityIn.hurt(DamageSource.mobAttack(this), 3.0F);
	}
    
    @Nullable
    @Override
    public SoundEvent getAmbientSound()
    {
        if(this.getSkin() == 2)
        	return FURSoundRegistry.SEAGULL_AMBIENT;
        else
        	return FURSoundRegistry.RAVEN_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        if(this.getSkin() == 2)
        	return FURSoundRegistry.SEAGULL_HURT;
        else
        	return FURSoundRegistry.RAVEN_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        if(this.getSkin() == 2)
        	return FURSoundRegistry.SEAGULL_DEATH;
        else
        	return FURSoundRegistry.RAVEN_DEATH;
    }
    
    @Override 
    public void playAmbientSound() {
    	super.playAmbientSound();
    	this.callTimer = 10;
    }

    @Override
    protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
        this.playSound(SoundEvents.PARROT_STEP, 0.15F, 1.0F);
	}

    @Override
	protected float playFlySound(float p_191954_1_) {
		this.playSound(SoundEvents.PARROT_FLY, 0.15F, 1.0F);
        return p_191954_1_ + this.flapSpeed / 2.0F;
	}

    @Override
    protected boolean makeFlySound() {
        return true;
    }

    @Override
    public SoundCategory getSoundSource() {
        return SoundCategory.NEUTRAL;
	}
    
    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public CreatureAttribute getMobType() {
        if(this.getSkin() == 3)
        	return CreatureAttribute.UNDEAD;
        else
        	return CreatureAttribute.UNDEFINED;
    }

    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    @Override
    public boolean isPushable() {
        return true;
	}
    
    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source) || this.getSkin() == 3) {
            return false;
        } else {
            if (this.aiSit != null)
            {
                this.setInSittingPose(false);
            }
            
            if(!this.level.isClientSide() && !this.getMainHandItem().isEmpty()) {
            	this.spawnAtLocation(this.getMainHandItem().copy(), 0.2f);
            	this.getMainHandItem().shrink(100);
            }
            
            return super.hurt(source, amount);
        }
    }
    
    /**
     * Returns whether this Entity is invulnerable to the given DamageSource.
     */
    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return super.isInvulnerableTo(source) || this.isPassenger();
    }

    @Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SKIN_TYPE, Integer.valueOf(this.getRandom().nextFloat() < 0.1F ? 1 : 0));
	}
    
    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT p_213386_5_) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Raven_Health.get());
    	this.setHealth(this.getMaxHealth());
    	
    	if(this.getType().equals(FUREntityRegistry.SEAGULL)) {
 		   this.TAME_ITEMS.clear();
 		   this.TAME_ITEMS.add(Items.TROPICAL_FISH);
 		   this.setSkin(2);
 	   	}
    	
 	   	return super.finalizeSpawn(worldIn, difficulty, p_213386_3_, livingdata, p_213386_5_);
 	}
    
    public int getSkin() {
        return this.getEntityData().get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
        this.getEntityData().set(SKIN_TYPE, Integer.valueOf(skinType));
    }
    
    private boolean isFetching() {
    	return RavenEntity.this.TargetLocationX != -1.0F || RavenEntity.this.TargetLocationY != -1.0F || RavenEntity.this.TargetLocationZ != -1.0F;
    }
    
    @Override
    public void travel(Vector3d p_213352_1_) {
    	if(!this.isInSittingPose() || !this.level.getBlockState(this.blockPosition().below()).getMaterial().isSolid())
    		super.travel(p_213352_1_);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.setSkin(compound.getInt("Variant"));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", getSkin());
    }
    
    public class AIMovetoTargetLocation extends Goal {
        public AIMovetoTargetLocation() {
        	this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean canUse() {
            return RavenEntity.this.isFetching();
        }
        
        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return RavenEntity.this.distanceToSqr(RavenEntity.this.TargetLocationX, RavenEntity.this.TargetLocationY, RavenEntity.this.TargetLocationZ) > 1.0D;
        }
        
        /**
         * Determine if this AI Task is interruptible by a higher (= lower value) priority task. All vanilla AITask have
         * this value set to true.
         */
        public boolean isInterruptable() {
            return false;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            super.start();
            RavenEntity.this.getNavigation().stop();
            RavenEntity.this.setInSittingPose(false);
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
            super.stop();
            RavenEntity.this.TargetLocationX = RavenEntity.this.TargetLocationY = RavenEntity.this.TargetLocationZ = -1.0F;
            RavenEntity.this.getNavigation().stop();
            RavenEntity.this.setInSittingPose(true);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
        	RavenEntity.this.getMoveControl().setWantedPosition(RavenEntity.this.TargetLocationX, RavenEntity.this.TargetLocationY, RavenEntity.this.TargetLocationZ, 1.5D);
        }
    }
	
	@Override
	public float getBrightness() {
		return 1.0F;
	}
	
    @Nullable
    @Override
    protected ResourceLocation getDefaultLootTable() {
    	return this.getSkin() == 2 ? new ResourceLocation("mod_lavacow", "entities/seagull") : super.getDefaultLootTable();
    }

    public boolean isFlying() {
        return (!this.isOnGround() && !this.isPassenger()) || (this.getVehicle() != null && !this.getVehicle().isOnGround() && this.getVehicle().getDeltaMovement().y < 0.0D);
    }
    
	/**
	* Called when the mob's health reaches 0.
	*/
    @Override
	public void die(DamageSource cause) {
		if (!this.isTame() && !this.level.isDay() && this.getRandom().nextInt(100) < FURConfig.pScarecrow_PlagueDoctor.get() && !this.level.isClientSide()) {
        	ScarecrowEntity entityscarecrow = FUREntityRegistry.SCARECROW.create(this.level);
        	entityscarecrow.setPos(this.getX(), this.getY() + 2.0D, this.getZ());
        	entityscarecrow.setSkin(2);
        	this.level.addFreshEntity(entityscarecrow);
        	this.playSound(SoundEvents.AMBIENT_CAVE, 1.0F, 1.0F);
        	
        	for(int i = 0; i < 8; i++) {
	            double d3 = this.getX() + this.getRandom().nextDouble();
	            double d4 = this.getY() + this.getRandom().nextDouble();
	            double d5 = this.getZ() + this.getRandom().nextDouble();
	            this.level.addParticle(ParticleTypes.SMOKE, d3, d4, d5, 0.0D, 0.0D, 0.0D);
        	}
        	
        	entityscarecrow.addEffect(new EffectInstance(Effects.HEALTH_BOOST, 8 * 20, 2));
        	entityscarecrow.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 3 * 20, 1));
		
        	if(cause.getEntity() != null && cause.getEntity() instanceof LivingEntity)
        		entityscarecrow.setTarget((LivingEntity) cause.getEntity());
		}

		super.die(cause);
	}
}
