package com.Fishmod.fur.entities.tameable;

import java.util.UUID;

import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.core.SpawnUtil;
import com.Fishmod.fur.core.WeaponEnchantments;
import com.Fishmod.fur.item.FURStewItem;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.dimension.DimensionType;

public class FURTameableEntity extends TamableAnimal {
	protected FURTameableEntity.State state;
	protected Goal wander;
	protected Goal follow;
	protected SitWhenOrderedToGoal aiSit;
	/** Weapon-enchantment levels inherited from the summoning weapon; empty (all zero) for wild/tamed-in-world mobs. */
	protected final WeaponEnchantments weaponEnchants = new WeaponEnchantments();

	public FURTameableEntity(EntityType<? extends FURTameableEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
		this.setTame(false);
	}
	
	@Override
    protected void defineSynchedData() {
		super.defineSynchedData();
		this.state = FURTameableEntity.State.WANDERING;
	}
	
	@Override
	protected void registerGoals() {
    	this.wander = this.wanderGoal();
    	this.follow = this.followGoal();
    	this.aiSit = new SitWhenOrderedToGoal(this);
    	// Must be registered so it keeps re-stopping navigation every tick while sitting —
    	// switchState's one-shot getNavigation().stop() alone isn't enough: other goals like
    	// BreedGoal don't check isInSittingPose() and would otherwise walk a "sitting" pet.
    	this.goalSelector.addGoal(1, this.aiSit);
    	this.goalSelector.addGoal(7, this.wander);
	}
	
	@Override
	public float getWalkTargetValue(BlockPos pos, LevelReader level) {
		return 10.0F;
	}
	
	public static boolean isDarkEnoughToSpawn(ServerLevelAccessor level, BlockPos pos, RandomSource random) {
		if (level.getBrightness(LightLayer.SKY, pos) > random.nextInt(32)) {
			return false;
		} else {
			DimensionType dimensiontype = level.dimensionType();
			int i = dimensiontype.monsterSpawnBlockLightLimit();
			if (i < 15 && level.getBrightness(LightLayer.BLOCK, pos) > i) {
				return false;
			} else {
				int j = level.getLevel().isThundering() ? level.getMaxLocalRawBrightness(pos, 10) : level.getMaxLocalRawBrightness(pos);
				return j <= dimensiontype.monsterSpawnLightTest().sample(random);
			}
		}
	}

	public static boolean checkMonsterSpawnRules(EntityType<? extends FURTameableEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		return checkMonsterSpawnRules(entityType, level, spawnType, pos, random, false);
	}

	// ignoreLight skips the darkness requirement (e.g. Nether Fortress chests sit over lava and are brightly lit)
	public static boolean checkMonsterSpawnRules(EntityType<? extends FURTameableEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random, boolean ignoreLight) {
		return level.getDifficulty() != Difficulty.PEACEFUL && (ignoreLight || isDarkEnoughToSpawn(level, pos, random)) && checkMobSpawnRules(entityType, level, spawnType, pos, random);
	}

	// Keepability is decided by isTame() alone — NOT by getOwner() being resolvable. The owner is a
	// player whose entity is null while offline / not-yet-loaded (e.g. on a Peaceful-world login),
	// so requiring "getOwner() instanceof Player" would wrongly despawn a tamed pet. isTame() is
	// restored from NBT on load and is the reliable signal.
	@Override
	public boolean removeWhenFarAway(double distance) {
		return !this.isTame();
	}

	@Override
    protected boolean shouldDespawnInPeaceful() {
	    return !this.isTame();
    }

	@Override
	public boolean requiresCustomPersistence() {
		return this.isTame() || super.requiresCustomPersistence();
	}
	
	public void setLimitedLife(int limitedLifeTicksIn) {    	
    }
    
    protected boolean isCommandable() {
    	return true;
    }

    protected boolean canSitCondition() {
    	return true;
    }

    /**
     * True for temporary, player-summoned minions (e.g. Scarab, Shroomling, Unburied)
     * that should be dismissed when their summoner logs off instead of lingering in the
     * world. Permanent tamed pets return false so they persist across sessions.
     */
    public boolean isSummonedMinion() {
    	return false;
    }

    /**
     * Whether a Beastcall Horn may bind/recall this pet. Pets that take no sit/wander/follow
     * commands ({@link #isCommandable()} == false) or that are temporary player summons
     * ({@link #isSummonedMinion()} == true) can't be bound to a horn.
     */
    public boolean canBeBoundByHorn() {
    	return this.isCommandable() && !this.isSummonedMinion();
    }

    public void doSitCommand(Player playerIn) {
    	this.switchState(FURTameableEntity.State.SITTING, playerIn);
    }

    public void doFollowCommand(Player playerIn) {
    	this.switchState(FURTameableEntity.State.FOLLOWING, playerIn);
    }

    public void doWanderCommand(Player playerIn) {
    	this.switchState(FURTameableEntity.State.WANDERING, playerIn);
    }

    /**
     * Centralized wandering/sitting/following transition. Drops whichever movement
     * goal is currently active, stops navigation, then installs the goal that matches
     * {@code newState}. The movement goal is rebuilt through {@link #wanderGoal()} /
     * {@link #followGoal()} so subclasses can vary it with the entity's current state
     * (e.g. baby vs. adult). Pass a non-null player to echo the state-change message.
     */
    protected void switchState(FURTameableEntity.State newState, @Nullable Player playerIn) {
    	this.goalSelector.removeGoal(this.wander);
    	this.goalSelector.removeGoal(this.follow);
    	this.getNavigation().stop();
    	this.state = newState;

    	switch (newState) {
    		case SITTING:
    			this.jumping = false;
    			this.setInSittingPose(true);
    			this.setOrderedToSit(true);
    			break;
    		case FOLLOWING:
    			this.follow = this.followGoal();
    			this.goalSelector.addGoal(6, this.follow);
    			this.setInSittingPose(false);
    			this.setOrderedToSit(false);
    			break;
    		case WANDERING:
    		default:
    			this.wander = this.wanderGoal();
    			this.goalSelector.addGoal(7, this.wander);
    			this.setInSittingPose(false);
    			this.setOrderedToSit(false);
    			break;
    	}

    	if (playerIn != null)
    		playerIn.displayClientMessage(Component.translatable(newState.message, this.getName()), true);
    }
    
    protected Goal wanderGoal() {
    	return new WaterAvoidingRandomStrollGoal(this, 1.0D, 0.0F);
    }
    
    protected Goal followGoal() {
    	return new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false);
    }
    
    protected int TameRate(ItemStack stack) {
    	return 3;
    }
    
    public boolean isWandering() {
    	return this.state.equals(FURTameableEntity.State.WANDERING);
    }
    
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        
        if (!itemstack.isEmpty()) {
        	InteractionResult actionresulttype = itemstack.interactLivingEntity(player, this, hand);
        	// consumesAction() covers both SUCCESS (client) and CONSUME (server) — vanilla item
        	// interactions like SaddleItem/name tag return sidedSuccess(), i.e. CONSUME on the
        	// server. Returning only on SUCCESS let those fall through to the state-cycle branch
        	// below, so saddling also flipped the pet's sit/wander/follow state.
        	if (actionresulttype.consumesAction()) {
        		return actionresulttype;
        	}
        }
        
        if (!this.level().isClientSide) {             
	    	if (this.isFood(itemstack) && canTameCondition()) {
	            if (!player.getAbilities().instabuild) {	               
	               if (itemstack.getItem() instanceof BucketItem) {
	            	   itemstack.shrink(1);
	            	   player.setItemInHand(hand, new ItemStack(Items.BUCKET));
	               } else if (itemstack.getItem() instanceof FURStewItem) {
	            	   itemstack.shrink(1);
	            	   if (itemstack.isEmpty()) {
	            		   player.setItemInHand(hand, new ItemStack(Items.BOWL));
	            	   } else if (!player.getInventory().add(new ItemStack(Items.BOWL))) {
	            		   player.spawnAtLocation(new ItemStack(Items.BOWL));
	                   }
	               } else if (itemstack.hasCraftingRemainingItem()) {
	            	   player.spawnAtLocation(itemstack.getCraftingRemainingItem());
	               } else {
	            	   itemstack.shrink(1);
	               }
	            }
	
	            if (this.random.nextInt(this.TameRate(itemstack)) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
	               this.tame(player);
	               this.navigation.stop();
	               this.setTarget((LivingEntity)null);
	               this.setHealth(this.getMaxHealth());
	               this.level().broadcastEntityEvent(this, (byte)7);
	            } else {
	               this.level().broadcastEntityEvent(this, (byte)6);
	            }
	
	            return InteractionResult.CONSUME;
	    	} else if (this.isTame() && this.isOwnedBy(player) && this.isCommandable() && hand == InteractionHand.MAIN_HAND) {
	    		if (!this.isFood(itemstack) && this.getPassengers().isEmpty()) {
	    			if (this.state.equals(FURTameableEntity.State.WANDERING)) {
	    				if (this.canSitCondition()) {
	    					this.doSitCommand(player);
	    				}
	    			} else if (this.state.equals(FURTameableEntity.State.SITTING)) {
	    				this.doFollowCommand(player);
	    			} else if (this.state.equals(FURTameableEntity.State.FOLLOWING)) {
	    				this.doWanderCommand(player);
	    			}
			    		
	    			return InteractionResult.SUCCESS;
	    		}
	    	}
        }

        return super.mobInteract(player, hand);
    }
    
    protected boolean canTameCondition() {
    	return !this.isTame();
    }
    
    @Override
    public void tame(Player player) {
    	super.tame(player);
    	this.setPersistenceRequired();
    	// Goes straight to FOLLOWING — doSitCommand(null) here would just be
    	// immediately overwritten by doFollowCommand's own goal churn.
    	this.doFollowCommand(null);
    }
    
    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide && FURConfig.Suicidal_Minion.get() && (this.getOwner() != null && (!(this.getOwner() instanceof Player) && !this.getOwner().isAlive()))) {
        	this.addTag("FUR_noLoot");
        	this.kill();
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
           return false;
        } else {
           Entity entity = source.getEntity();
           this.setOrderedToSit(false);
           // getEntity() is the indirect/causing entity (e.g. the skeleton that fired an arrow),
           // never the projectile itself, so checking it for AbstractArrow was always false and
           // never actually excluded arrow damage. getDirectEntity() is the projectile.
           if (entity != null && !(entity instanceof Player) && !(source.getDirectEntity() instanceof AbstractArrow)) {
              amount = (amount + 1.0F) / 2.0F;
           }

           return super.hurt(source, amount);
        }
	}
    
    @Override
    @Nullable
    public LivingEntity getOwner() {
    	try
        {
            UUID uuid = this.getOwnerUUID();
            LivingEntity owner = null;
            if(uuid == null) {
            	return null;
            } else {
            	owner = this.level().getPlayerByUUID(uuid);
            	if(owner == null)
                    if (this.level() instanceof ServerLevel) {                 	
                    	owner = SpawnUtil.getEntityByUniqueId(uuid, (ServerLevel)this.level());
                    }
            }
            
            return owner;
        }
        catch (IllegalArgumentException var2)
        {
            return null;
        }
    }
    
    @Override
    public int getMaxHeadXRot() {
        return this.isInSittingPose() ? 20 : super.getMaxHeadXRot();
	}
    
    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    @Override
    public boolean isFood(ItemStack stack) {
        return false;
    }
    
    public WeaponEnchantments getWeaponEnchants() {
    	return this.weaponEnchants;
    }
    
	@Override
	public AgeableMob getBreedOffspring(ServerLevel worldIn, AgeableMob entity) {
		return null;
	}
	
	@Override
	public boolean canMate(Animal target) {
	    if (target == this) {
	        return false;
	    }
	    
	    if (!(target instanceof FURTameableEntity mate)) {
	        return false;
	    }

	    if (!(target.getType().equals(this.getType()))) {
	        return false;
	    }

	    if (this.isTame() != mate.isTame()) {
	        return false;
	    }

	    if (this.isTame()) {
	        if (this.isInSittingPose() || mate.isInSittingPose()) {
	            return false;
	        }
	    }

	    return this.isInLove() && mate.isInLove();
	}
	
	@Override
	public boolean wantsToAttack(LivingEntity target, LivingEntity owner) {		
		if (!(target instanceof Creeper) && !(target instanceof Ghast)) {
			if (target instanceof FURTameableEntity) {
				FURTameableEntity pal = (FURTameableEntity)target;
				return !pal.isTame() || pal.getOwner() != owner;
			} else if (target instanceof Player && owner instanceof Player && !((Player)owner).canHarmPlayer((Player)target)) {
				return false;
			} else if (target instanceof AbstractHorse && ((AbstractHorse)target).isTamed()) {
				return false;
			} else {
				return !(target instanceof TamableAnimal) || !((TamableAnimal)target).isTame();
			}
		} else {
			return false;
		}
	}
	
    public int getSkin() {
    	return 0;
    }

    public void setSkin(int skinType) {
    }
	
    /**
     * Writes the extra NBT data specific to this type of entity. Should <em>not</em> be called from outside this class;
     * use {@link #writeUnlessPassenger} or {@link #writeWithoutTypeId} instead.
     */
	@Override
    public void addAdditionalSaveData(CompoundTag compound) {
       super.addAdditionalSaveData(compound);
       compound.putByte("state", this.state.saveId);
       this.weaponEnchants.save(compound);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
       super.readAdditionalSaveData(compound);
       this.weaponEnchants.load(compound);
       switch(FURTameableEntity.State.byId(compound.getByte("state"))) {
	       case SITTING:
	       		this.doSitCommand(null);
	    	   	break;
	       case FOLLOWING:
	       		this.doFollowCommand(null);
	       	   	break;
	       case WANDERING:
	       default:
	       		this.doWanderCommand(null);
	  			break;
       }
    }

    static enum State
    {
        SITTING((byte)1, "command.fur.sitting"),
        WANDERING((byte)0, "command.fur.wandering"),
        FOLLOWING((byte)2, "command.fur.following");

        /** Persisted NBT id; kept stable for save compatibility. */
        private final byte saveId;
        /** Translation key shown when the owner toggles into this state. */
        private final String message;

        State(byte saveId, String message) {
            this.saveId = saveId;
            this.message = message;
        }

        static FURTameableEntity.State byId(byte id) {
            for (FURTameableEntity.State state : values()) {
                if (state.saveId == id) {
                    return state;
                }
            }
            return WANDERING;
        }
    }
}
