package com.Fishmod.fur.entities.tameable;

import java.util.EnumSet;
import java.util.UUID;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.entities.ai.FURRangeAttackGoal;
import com.Fishmod.fur.entities.LavaCowEntity;
import com.Fishmod.fur.entities.ai.FURMeleeAttackGoal;
import com.Fishmod.fur.entities.projectiles.EnchantableFireBallEntity;
import com.Fishmod.fur.entities.projectiles.MoltenGlobEntity;
import com.Fishmod.fur.entities.projectiles.WarSmallFireballEntity;
import com.Fishmod.fur.init.FURBlockRegistry;
import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURItemRegistry;
import com.Fishmod.fur.item.BeastcallHornItem;
import com.Fishmod.fur.init.FURKeybindRegistry;
import com.Fishmod.fur.init.FURSoundRegistry;
import com.Fishmod.fur.message.MessageMountSpecial;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
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

public class SalamanderEntity extends FURTameableEntity implements Saddleable, RangedAttackMob, GeoEntity {
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	
    private static final RawAnimation IDLE = RawAnimation.begin().thenPlay("salamander.model.idle");
    private static final RawAnimation WALK = RawAnimation.begin().thenPlay("salamander.model.walking");
    private static final RawAnimation SWIM = RawAnimation.begin().thenPlay("salamander.model.swimming");
    private static final RawAnimation LIE = RawAnimation.begin().thenPlay("salamander.model.lying");
    private static final RawAnimation HEAT = RawAnimation.begin().thenPlay("salamander.model.lying_furnace");
    private static final RawAnimation ATTACK_RANGE = RawAnimation.begin().thenPlay("salamander.model.attacking_range");
    private static final RawAnimation ATTACK_MELEE = RawAnimation.begin().thenPlay("salamander.model.attacking_melee");
    private static final RawAnimation ATTACK_RIDDEN = RawAnimation.begin().thenPlay("salamander.model.attacking_ridden");
	
	private static final EntityDataAccessor<Integer> SKIN_TYPE =  SynchedEntityData.defineId(SalamanderEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> GROWING_STAGE = SynchedEntityData.defineId(SalamanderEntity.class, EntityDataSerializers.INT);
	// 0: isSaddled, 1: isBoostingFurnace
	protected static final EntityDataAccessor<Byte> DATA_FLAGS = SynchedEntityData.defineId(SalamanderEntity.class, EntityDataSerializers.BYTE);
	
	private static final int RANGE = 2;
	public static final int ATTACK_TIMER = 20;
	
	// Holds either a war-fireball goal (nymph/child) or a molten-glob goal (adult),
	// so it is typed to their shared base rather than a single projectile.
	private FURRangeAttackGoal<? extends EnchantableFireBallEntity> range_atk;
	private AvoidEntityGoal<Player> avoid_entity;
	private int barrage_CD;
	@Nullable
	public BlockPos savedFurnacePos = null;
	
	public SalamanderEntity(EntityType<? extends SalamanderEntity> entityType, Level worldIn) {
        super(entityType, worldIn);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.LAVA, 8.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0F);
        this.xpReward = 20;
    }

    @Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SKIN_TYPE, Integer.valueOf(0));		
		this.entityData.define(GROWING_STAGE, Integer.valueOf(-1));
		this.entityData.define(DATA_FLAGS, (byte)0);
	}
	
    @Override
    protected void registerGoals() {   	
    	super.registerGoals();
    	if (this.isNymph()) {
    		this.range_atk = new FURRangeAttackGoal<WarSmallFireballEntity>(this, FUREntityRegistry.WAR_SMALL_FIREBALL.get(), 8, 5, 2.5D, 1.0D, 2.5D).withWindup(9);
    	} else {
    		this.range_atk = new FURRangeAttackGoal<WarSmallFireballEntity>(this, FUREntityRegistry.WAR_SMALL_FIREBALL.get(), 1, 5, 1.0D, 0.1D, 1.0D).withWindup(13);
    	}
    	
    	this.goalSelector.addGoal(0, new FloatGoal(this));
    	this.goalSelector.addGoal(1, new BreedGoal(this, 1.0D));
    	this.goalSelector.addGoal(2, this.range_atk);
    	this.goalSelector.addGoal(3, new SalamanderEntity.AttackGoal(this));   	
    	this.goalSelector.addGoal(4, new LookatFurnaceGoal(this));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
        this.applyEntityAI();
    }
    
    protected void applyEntityAI() {
        if (FURConfig.Salamander_Defender.get()) {
    		this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
    		this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
    	}
    	this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
    	this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, LavaCowEntity.class, true));
    	this.targetSelector.addGoal(4, new NonTameRandomTargetGoal<>(this, Player.class, false, (candidate) -> {
    		return !(candidate.isPassenger() && candidate.getVehicle() instanceof SalamanderEntity);
    	}));    	
    }
    
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.23D)
        		.add(Attributes.MAX_HEALTH, 60.0D)
        		.add(Attributes.ATTACK_DAMAGE, 4.0D)
        		.add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }
    
    @Override
    protected Goal followGoal() {
    	return new FollowOwnerGoal(this, 1.5D, 6.0F, 2.0F, false);
    }
    
    /**
     * Gets how bright this entity is.
     */
	public float getBrightness() {
		return 1.0F;
	}
	
    public static boolean checkSalamanderSpawnRules(EntityType<? extends SalamanderEntity> entityTypeIn, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource randomSource) {
    	return FURTameableEntity.checkMonsterSpawnRules(entityTypeIn, (ServerLevelAccessor) level, spawnType, pos, randomSource);        
    }
    
    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    @Override
    public int getMaxSpawnClusterSize() {
    	return 4;
    }

    protected ItemStack getFishBucket() {
    	ItemStack stack = new ItemStack(FURItemRegistry.SALAMANDER_BUCKET.get());
        CompoundTag CompoundTag = new CompoundTag();
        this.addAdditionalSaveData(CompoundTag);
        stack.getOrCreateTag().put("SalamanderData", CompoundTag);
        
        if (this.hasCustomName()) {
            stack.setHoverName(this.getCustomName());
        }
        
        return stack;
    }
    
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
    	ItemStack itemstack = player.getItemInHand(hand);   	
        boolean flag = this.isFood(itemstack);
        
        if (this.isOwnedBy(player) && itemstack.getItem() == Items.BLAZE_POWDER && itemstack.getCount() >= 64 && this.isAlive() && this.getSkin() == 1) {
        	if (!player.isCreative()) {
        		itemstack.shrink(64);
        	}
        	this.setSkin(0);      	
        	this.playSound(SoundEvents.AMBIENT_CAVE.get(), 1.0F, 1.0F);
        	for (int i = 0; i < 16; ++i) {
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;
                this.level().addParticle(ParticleTypes.ENTITY_EFFECT, this.getX() + (this.random.nextFloat() * this.getBbWidth()) - this.getBbWidth(), this.getY() + (this.random.nextFloat() * this.getBbHeight()), this.getZ() + (this.random.nextFloat() * this.getBbWidth()) - this.getBbWidth(), d0, d1, d2);
            }
        	
        	return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else if (this.isTame() && itemstack.getItem() == FURItemRegistry.ECTOPLASM.get() && itemstack.getCount() >= 64 && this.isAlive() && this.getSkin() == 0) {
        	if (!player.isCreative()) {
        		itemstack.shrink(64);
        	}
        	this.setSkin(1);  	
        	this.playSound(SoundEvents.AMBIENT_CAVE.get(), 1.0F, 1.0F);
        	for (int i = 0; i < 16; ++i) {
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;
                this.level().addParticle(ParticleTypes.ENTITY_EFFECT, this.getX() + (this.random.nextFloat() * this.getBbWidth()) - this.getBbWidth(), this.getY() + (this.random.nextFloat() * this.getBbHeight()), this.getZ() + (this.random.nextFloat() * this.getBbWidth()) - this.getBbWidth(), d0, d1, d2);
            }
        	
        	return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else if (this.isTame() && this.isNymph() && itemstack.getItem() == Items.LAVA_BUCKET && this.isAlive()) {
            this.playSound(SoundEvents.BUCKET_FILL_FISH, 1.0F, 1.0F);
            itemstack.shrink(1);
            ItemStack itemstack1 = this.getFishBucket();
            if (!this.level().isClientSide) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, itemstack1);
            }

            if (itemstack.isEmpty()) {
                player.setItemInHand(hand, itemstack1);
            } else if (!player.getInventory().add(itemstack1)) {
                player.drop(itemstack1, false);
            }

            this.discard();            
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else if (!flag && this.isOwnedBy(player) && this.isSaddled() && !this.isVehicle()) {
        	if (itemstack.getItem().equals(Items.SHEARS)) {
    			this.setSaddled(false);  			
    			this.spawnAtLocation(Items.SADDLE, 1);      		
    			return InteractionResult.sidedSuccess(this.level().isClientSide);
    		} else if (!player.isSecondaryUseActive() && !player.isPassenger()
    				&& !(itemstack.getItem() instanceof BeastcallHornItem)) {
        	   player.startRiding(this);
        	   return InteractionResult.sidedSuccess(this.level().isClientSide);
    		}    	
        }
        
        return super.mobInteract(player, hand);
    }
    
    @Override
    protected boolean canTameCondition() {
    	return this.isBaby() && super.canTameCondition();
    }
    
    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    @Override
    public boolean isFood(ItemStack stack) {
    	return stack.getItem().equals(FURItemRegistry.IMP_HORN.get()) || stack.getItem().equals(FURItemRegistry.KUNG_PAO_CHICKEN.get());
    }
    
    @Override
    protected int TameRate(ItemStack stack) {
    	if (stack.getItem().equals(FURItemRegistry.IMP_HORN.get())) {
    		return 3;
    	} else if (stack.getItem().equals(FURItemRegistry.KUNG_PAO_CHICKEN.get())) {
    		return 1;
    	} else { 
    		return super.TameRate(stack);
    	}
    }
    
    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    @Override
    public double getPassengersRidingOffset() {
        return (double)this.getBbHeight() * 1.3D;
    }
    
    @Override
    public void positionRider(Entity passenger, Entity.MoveFunction moveFunction) {
        super.positionRider(passenger, moveFunction);
        if (passenger instanceof Mob) {
        	this.yBodyRot = ((Mob)passenger).yBodyRot;
            this.setYRot(passenger.getYRot());
        }
    }
    
    @Nullable
    public LivingEntity getControllingPassenger() {
        for (Entity passenger : this.getPassengers()) {
            if (passenger instanceof Player) {
                Player player = (Player) passenger;
                if (this.isTame() && this.getOwnerUUID() != null && this.getOwnerUUID().equals(player.getUUID())) {
                    return player;
                }
            }
        }
        return null;
    }
    
    public boolean canBeControlledByRider() {
    	return this.getControllingPassenger() instanceof Player;
    }
    
    public boolean isRidingPlayer(Player player) {
        LivingEntity controller = this.getControllingPassenger();
        return controller instanceof Player p && p.getUUID().equals(player.getUUID());
    }
    
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void tick() {
        super.tick();
    	
        if(this.barrage_CD > 0)
        	this.barrage_CD--;
        
    	if (!this.level().isClientSide) {   		
    		if (this.isTame() && this.getRandom().nextInt(this.isInLava() ? 45 : 900) == 0 && this.deathTime == 0) {
                this.heal(1.0F);
            }
    		
    		// Top the rider's Fire Resistance up every tick so it never lapses while mounted. The old
    		// 40-tick conditional refresh granted only 3s and re-applied just when the effect was gone,
    		// leaving ~1s gaps where a lava-swimming Salamander could cook its rider. Re-applying each
    		// tick with a duration well over the interval keeps it gap-free and the icon steady;
    		// addEffect won't clobber a longer-lasting Fire Resistance from another source.
    		LivingEntity rider = this.getControllingPassenger();
    		if (this.isVehicle() && rider != null) {
    			rider.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 60, 0, false, false, true));
    		}

	    	int age = this.getAge();
	    	int expectedStage = age < -16000 ? 0 : age < -8000 ? 1 : age < 0 ? 2 : 3;
	    	if (this.getGrowingStage() != expectedStage)
	    		this.setGrowingStage(expectedStage);

	    	// savedFurnacePos no longer valid — sitting check first (cheap); block state throttled to every 20 ticks
    		if (this.savedFurnacePos != null
    				&& (!this.isInSittingPose()
    				|| (this.tickCount % 20 == 0
    					&& (this.blockPosition().distSqr(this.savedFurnacePos) > (this.searchRange() * this.searchRange())
    					|| !(this.level().getBlockState(this.savedFurnacePos).getBlock() instanceof AbstractFurnaceBlock))))) {
    			this.savedFurnacePos = null;
    			this.setBoostingFurnace(false);
    		}
	    	
    		if (this.tickCount % 80 == 0 && this.isAlive() && this.isTame() && this.isInSittingPose()) {      			
	    		// update savedFurnacePos
	    		if (this.savedFurnacePos == null) {
					int r = this.searchRange();
					BlockPos center = this.blockPosition();
					for (BlockPos p : BlockPos.betweenClosed(center.offset(-r, -r, -r), center.offset(r, r, r))) {
						if (this.level().getBlockState(p).getBlock() instanceof AbstractFurnaceBlock) {
							this.savedFurnacePos = p.immutable();
							break;
						}
					}
	    		}
	    		
	    		// boost furnace
	    		if (this.savedFurnacePos != null) {
	    			AbstractFurnaceBlockEntity furnaceTileEntity = (AbstractFurnaceBlockEntity) this.level().getBlockEntity(this.savedFurnacePos);
		    		BlockState blockstate = this.level().getBlockState(this.savedFurnacePos);
		    		this.setBoostingFurnace(true);
		    		
			        if (furnaceTileEntity != null && !furnaceTileEntity.getItem(0).isEmpty()) {
			        	CompoundTag CompoundTag = furnaceTileEntity.saveWithoutMetadata();
					      
						if (CompoundTag.contains("BurnTime") && CompoundTag.getInt("BurnTime") <= 100) {
							CompoundTag.putInt("BurnTime", 200);										
							furnaceTileEntity.load(CompoundTag);
							furnaceTileEntity.setChanged();
							this.level().setBlock(this.savedFurnacePos, blockstate.setValue(BlockStateProperties.LIT, Boolean.valueOf(true)), 3);
						}	
			        }
	    		}
	    	}	
    	}
    	
    	
    }
    
    @Override
    public void aiStep() {
    	super.aiStep();
    		
    	if (this.level().isClientSide) {
            this.ClientControl();
        }
    }
    
    public boolean causeFallDamage(float fallDistance, float damageMultiplier) {
    	if (fallDistance > 1.0F) {
    		this.playSound(SoundEvents.HORSE_LAND, 0.4F, 1.0F);
    	}

    	int i = this.calculateFallDamage(fallDistance, damageMultiplier);
    	if (i <= 0) {
    		return false;
    	} else {
    		this.hurt(this.damageSources().fall(), (float)i);
    		if (this.isVehicle()) {
    			for(Entity entity : this.getIndirectPassengers()) {
    				entity.hurt(this.damageSources().fall(), (float)i);
    			}
    		}

    		this.playBlockFallSound();
    		return true;
    	}
	}

	protected int calculateFallDamage(float fallDistance, float damageMultiplier) {
		return (int) Math.ceil((fallDistance * 0.5F - 3.0F) * damageMultiplier);
	}
    
    @OnlyIn(Dist.CLIENT)
    private void ClientControl() {
    	Minecraft game = Minecraft.getInstance();
    	
		if (this.barrage_CD == 0 && FURKeybindRegistry.MOUNT_SPECIAL.isDown() && this.isRidingPlayer(game.player)) {
			this.barrage_CD = 80;
			mod_LavaCow.NETWORK.sendToServer(new MessageMountSpecial(this.getId(), this.getX(), this.getY(), this.getZ()));
		}	
    }
    
    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (GROWING_STAGE.equals(key)) {
           this.refreshDimensions();
        }

        super.onSyncedDataUpdated(key);
	}
    
    public boolean isTame() {
        return (this.entityData.get(DATA_FLAGS_ID) & 4) != 0;
     }
   
    /**
     * Growing Stage: Nymph -> Child-> Adult
     */
    public int getGrowingStage() {
       return this.getEntityData().get(GROWING_STAGE).intValue();
    }
    
    public void setGrowingStage(int i) {
        this.getEntityData().set(GROWING_STAGE, i);
        this.refreshDimensions();
        
        switch(i) {
	        case 0:
		    	this.xpReward = 5;
		    	this.avoid_entity = new AvoidEntityGoal<>(this, Player.class, 4.0F, 0.8D, 1.6D);
		    	this.goalSelector.addGoal(3, this.avoid_entity);
		    	this.goalSelector.removeGoal(this.range_atk);
		    	this.range_atk = new FURRangeAttackGoal<WarSmallFireballEntity>(this, FUREntityRegistry.WAR_SMALL_FIREBALL.get(), 1, 5, 1.0D, 0.1D, 1.0D).withWindup(13);
		    	this.goalSelector.addGoal(4, this.range_atk);
		    	
		    	this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Salamander_Health.get() * 0.25D);
		        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.28D);
		        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Salamander_Attack.get() * 0.5D);
		        
		        if (this.getHealth() > this.getMaxHealth())
		        	this.setHealth(this.getMaxHealth());
		        
		        if(this.isSaddled()) {
		        	this.setSaddled(false);
		            this.spawnAtLocation(Items.SADDLE, 1);
		        }
	        	break;
	        case 1:   		
	    		this.xpReward = 10;
	    		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Salamander_Health.get() * 0.40D);
	    		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.25D);
	    		this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Salamander_Attack.get() * 0.65D);
	    		
	    		this.heal(this.getHealth() * (0.15F / 0.25F));
	        	break;
	        case 2:
	    		this.xpReward = 15;
	    		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Salamander_Health.get() * 0.60D);
	    		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.25D);
	    		this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Salamander_Attack.get() * 0.75D);
	    		
	    		this.heal(this.getHealth() * 0.5F);
        		break;
        	default:
    			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Salamander_Health.get());
    			this.heal(this.getHealth() * 2.0F / 3.0F);
    			
    	    	this.xpReward = 20;
    	    	
    	    	this.goalSelector.removeGoal(this.avoid_entity);
    	    	this.goalSelector.removeGoal(this.range_atk);
    	    	// Adults lob a single Molten Glob (times = 1) in a parabolic arc (curve compensates
    	    	// for the glob's gravity) instead of a straight war-fireball barrage.
    	    	this.range_atk = new FURRangeAttackGoal<MoltenGlobEntity>(this, FUREntityRegistry.MOLTEN_GLOB.get(), FURSoundRegistry.SALAMANDER_ATTACK_RANGE.get(), 1, 5, 4.0D, this.getAttribute(Attributes.FOLLOW_RANGE).getValue(), 2.5D, 1.0D, 2.5D).withWindup(9);
    	    	this.goalSelector.addGoal(4, this.range_atk);
    	    	
    	        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.23D);
    	        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Salamander_Attack.get());	
        		break;
        }
	}
    
    public boolean isNymph() {
    	return this.getGrowingStage() == 0;
    }
    
    public void setTamed(boolean tamed) {
    	if(tamed) {
    		this.goalSelector.removeGoal(this.avoid_entity);
    	}
    	
    	super.setTame(tamed);
    }
    
    public void spawnChildFromBreeding(ServerLevel server, Animal animal) {
        ItemStack itemstack = new ItemStack(FURBlockRegistry.SALAMANDER_EGG.get());
        CompoundTag tag = itemstack.getOrCreateTag();
        tag.putInt("variant", this.getRandom().nextBoolean() ? this.getSkin() : ((SalamanderEntity) animal).getSkin());
        
        ItemEntity itementity = new ItemEntity(server, this.position().x(), this.position().y(), this.position().z(), itemstack);
        itementity.setDefaultPickUpDelay();
        this.finalizeSpawnChildFromBreeding(server, animal, (AgeableMob)null);
        this.playSound(SoundEvents.SNIFFER_EGG_PLOP, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 0.5F);
        server.addFreshEntity(itementity);
	}
    
    @Override
	public SalamanderEntity getBreedOffspring(ServerLevel worldIn, AgeableMob ageable) {
    	SalamanderEntity entity = FUREntityRegistry.SALAMANDER.get().create(worldIn);
		UUID uuid = this.getOwnerUUID();
		
		if (uuid != null) {
			entity.setOwnerUUID(uuid);
			entity.setTame(true);
		}
		
		entity.setHealth(entity.getMaxHealth());
		entity.setSkin(this.getRandom().nextBoolean() ? this.getSkin() : ((SalamanderEntity) ageable).getSkin());
		
		return entity;
	}
    
    /**
     * returns true if all the conditions for steering the entity are met. For pigs, this is true if it is being ridden
     * by a player and the player is holding a carrot-on-a-stick
     */
    @Override
    public boolean isSaddled() {
    	return (this.entityData.get(DATA_FLAGS) & 1) != 0;
    }
    
    /**
     * Set or remove the saddle of the pig.
     */
    public void setSaddled(boolean saddled) {
    	byte b0 = this.entityData.get(DATA_FLAGS);
        if (saddled) {
        	this.entityData.set(DATA_FLAGS, (byte)(b0 | 1));
        } else {
        	this.entityData.set(DATA_FLAGS, (byte)(b0 & -2));
        }
    }
    
	public boolean isBoostingFurnace() {
        return (this.entityData.get(DATA_FLAGS) & 2) != 0;
	}

	public void setBoostingFurnace(boolean boosting) {
		byte b0 = this.entityData.get(DATA_FLAGS);
        if (boosting) {
           this.entityData.set(DATA_FLAGS, (byte)(b0 | 2));
        } else {
           this.entityData.set(DATA_FLAGS, (byte)(b0 & -3));
        }
	}
    
    @Override
    public void travel(Vec3 travelVector) {
        if (this.isAlive()) {
	        if (this.isVehicle() && this.canBeControlledByRider() && this.isSaddled()) {
	        	LivingEntity controller = (LivingEntity)this.getControllingPassenger();
	        	this.setYRot(controller.getYRot());
	            this.yRotO = this.getYRot();
	            this.setXRot(controller.getXRot() * 0.5F);
	            this.setRot(this.getYRot(), this.getXRot());
	            this.yBodyRot = this.getYRot();
	            this.yHeadRot = this.getYRot();
	            this.setMaxUpStep(1.0F);
	            float f = controller.xxa * 0.5F;
	            float f1 = controller.zza;
	
	            if (this.isControlledByLocalInstance()) {
	                if(this.isInLava()) {
	                	this.setDeltaMovement(this.getDeltaMovement().x * 1.5F, this.getDeltaMovement().y + 0.02F, this.getDeltaMovement().z * 1.5F);
	                }
	                this.setSpeed((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED));
	                super.travel(new Vec3((double)f, travelVector.y, (double)f1));
	            } else {
	            	this.setDeltaMovement(Vec3.ZERO);
	            }
	
	            this.calculateEntityAnimation(false);
	        } else {
	            super.travel(travelVector);
	        }
        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnTypeIn, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
       float chance_to_spawn_as_child = 0.0F;

       this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Salamander_Health.get());
       this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Salamander_Attack.get());
   		this.setHealth(this.getMaxHealth());
   	
       if (world.getBiome(this.blockPosition()).is(Biomes.SOUL_SAND_VALLEY)) {
    	   this.setSkin(1);
       }
       
       switch(this.level().getDifficulty()) {
	       case PEACEFUL:
	    	   chance_to_spawn_as_child = 0.85F;
	    	   break;
	       case EASY:
	    	   chance_to_spawn_as_child = 0.8F;
	    	   break;
	       case NORMAL:
	    	   chance_to_spawn_as_child = 0.5F;
	    	   break;
	       case HARD:
	    	   chance_to_spawn_as_child = 0.15F;
	    	   break;
       		default:
       			break;
       }
       
       this.setBaby(this.level().getRandom().nextFloat() <= chance_to_spawn_as_child);
       
       return super.finalizeSpawn(world, difficulty, spawnTypeIn, livingdata, tag);
    }
    
	@Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return this.isBaby() ? 0.2F : 0.8F;
    }

    public int getSkin() {
        return this.getEntityData().get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
    	this.getEntityData().set(SKIN_TYPE, Integer.valueOf(skinType));
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return FURSoundRegistry.SALAMANDER_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FURSoundRegistry.SALAMANDER_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.SALAMANDER_DEATH.get();
    }

	@Override
    protected void playStepSound(BlockPos pos, BlockState state) {
	    this.playSound(SoundEvents.COW_STEP, 0.15F, 1.0F);
	}
	
	@Override
	public boolean isSaddleable() {
		return this.isAlive() && !this.isBaby() && this.isTame();
	}
    
    @Override
    protected void dropEquipment() {
    	super.dropEquipment();
        if (this.isSaddled()) {
           this.spawnAtLocation(Items.SADDLE);
        }
	}
    
    @Override
    public void equipSaddle(@Nullable SoundSource source) {
    	this.setSaddled(true);
        if (source != null) {
           this.level().playSound((Player)null, this, SoundEvents.HORSE_SADDLE, source, 0.5F, 1.0F);
        }
	}
    
    @Nullable
    @Override
    protected ResourceLocation getDefaultLootTable() {
    	return this.isBaby() ? new ResourceLocation(mod_LavaCow.MODID, "entities/salamander_nymph") : super.getDefaultLootTable();
    }
    
    @Override
	public boolean shouldDropExperience() {
        return true;
	}

    @Override
	protected boolean shouldDropLoot() {
    	return true;
	}
    
    /**
     * "Sets the scale for an ageable entity according to the boolean parameter, which says if it's a child."
     */
    @Override
    public float getScale() {
    	float scale = 1.0F;

    	switch (this.getGrowingStage()) {
    		case 0:
    			scale = 0.30F;
    			break;
    		case 1:
    			scale = 0.50F;
    			break;
    		case 2:
    			scale = 0.75F;
    			break;
    		default:
    			scale = 1.0F;
    			break;   			
    	}
    	
    	return scale;
    }
    
    private int searchRange() {
    	return RANGE + this.getGrowingStage();
    }
    
    /**
     * Handler for {@link World#setEntityState}
     */
	@Override
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
		switch (id) {
			case 70:
				this.triggerAnim("trigger_controller", "attacking_range");
				break;
			case 71:
				this.triggerAnim("trigger_controller", "attacking_melee");
				break;
			case 72:
				this.triggerAnim("trigger_controller", "attacking_ridden");
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
        this.setSaddled(compound.getBoolean("Saddled"));
        this.setGrowingStage(compound.getInt("GrowingStage"));
        this.setSkin(compound.getInt("Variant"));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Saddled", this.isSaddled());
        compound.putInt("GrowingStage", this.getGrowingStage());
        compound.putInt("Variant", getSkin());
    }
	
	public class LookatFurnaceGoal extends Goal {
		private final SalamanderEntity mob;

		public LookatFurnaceGoal(SalamanderEntity salamander) {
			this.mob = salamander;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		}

		public boolean canUse() {
			return this.mob.isAlive() && this.mob.isTame() && this.mob.isInSittingPose() && this.mob.savedFurnacePos != null && !this.mob.isAggressive();
		}
		
		public void tick() {
			this.mob.getLookControl().setLookAt((double)this.mob.savedFurnacePos.getX() + 0.5D, this.mob.savedFurnacePos.getY(), (double)this.mob.savedFurnacePos.getZ() + 0.5D);	
			
            if (this.mob.level() instanceof ServerLevel serverLevel && this.mob.tickCount % 20 == 0) {
            	BlockPos bp = this.mob.blockPosition();
            	double d0 = this.mob.getLookControl().getWantedX() - (double) bp.getX();
            	double d1 = this.mob.getLookControl().getWantedY() - (double) bp.getY();
            	double d2 = this.mob.getLookControl().getWantedZ() - (double) bp.getZ();
            	Vec3 dir = new Vec3(d0, d1, d2).normalize();
            	double reach = (this.mob.getGrowingStage() + 1.0D) * 0.5D;
            	serverLevel.sendParticles((this.mob.getSkin() == 0) ? ParticleTypes.FLAME : ParticleTypes.SOUL_FIRE_FLAME,
            			(double) bp.getX() + 0.5D + dir.x * reach,
            			(double) bp.getY() + (double)(this.mob.getBbHeight() * 0.2F),
            			(double) bp.getZ() + 0.5D + dir.z * reach,
            			15, 0.2D + dir.x * reach * 0.1D, 0.2D, 0.2D + dir.z * reach * 0.1D, 0.01D);
            }
		}
	}
	
    static class AttackGoal extends FURMeleeAttackGoal {
        public AttackGoal(PathfinderMob mob) {
           super(mob, 1.0D, true);
        }
        
        @Override
    	protected int atkTimerMax() {
    		return ATTACK_TIMER;
    	}
    	
    	@Override
    	protected int atkTimerHit() {
			return 13; 		
    	}
    	
    	@Override
    	protected byte atkTimerEvent() {
    		return (byte)71;
    	}
    	
    	@Override
    	protected void dmgEvent(LivingEntity target) {
    		float pitch = 0.4F + (this.mob.getRandom().nextFloat() - 0.5F) * 0.05F;
    		this.mob.playSound(FURSoundRegistry.SALAMANDER_ATTACK.get(), 0.8F, pitch);
    		this.mob.playSound(FURSoundRegistry.SALAMANDER_ATTACK.get(), 0.3F, pitch * 0.5F);
    		super.dmgEvent(target);
    	}
	}
	
    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
    	if (this.isInSittingPose()) {
    		if (this.isBoostingFurnace()) {
    			state.getController().setAnimation(HEAT);
    		} else {
    			state.getController().setAnimation(LIE);
    		}
    	} else if (this.isInFluidType()) {
    		state.getController().setAnimation(SWIM);
    		if (state.isMoving()) {
    			state.setControllerSpeed(1.0F);
    		} else {
    			state.setControllerSpeed(0.5F);
    		}
    	} else if (state.isMoving() || !this.getNavigation().isDone()) {
			state.getController().setAnimation(WALK);
        } else {
            state.getController().setAnimation(IDLE);
        }

        return PlayState.CONTINUE;
    }

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "controller", 10, this::predicate));
		controllers.add(new AnimationController<>(this, "trigger_controller", 5, state -> PlayState.STOP)
				.triggerableAnim("attacking_range", ATTACK_RANGE)
				.triggerableAnim("attacking_melee", ATTACK_MELEE)
				.triggerableAnim("attacking_ridden", ATTACK_RIDDEN));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	@Override
	public void performRangedAttack(LivingEntity target, float distanceFactor) {
	}
}
