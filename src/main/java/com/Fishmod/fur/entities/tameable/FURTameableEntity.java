package com.Fishmod.fur.entities.tameable;

import java.util.UUID;

import javax.annotation.Nullable;

import com.Fishmod.fur.core.SpawnUtil;
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
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
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
	
	public FURTameableEntity(EntityType<? extends FURTameableEntity> p_i50240_1_, Level worldIn) {
		super(p_i50240_1_, worldIn);
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
    	this.goalSelector.addGoal(7, this.wander);
	}
	
	@Override
	public float getWalkTargetValue(BlockPos pos, LevelReader p_205022_2_) {
		return 10.0F;
	}
	
	public static boolean isDarkEnoughToSpawn(ServerLevelAccessor p_223323_0_, BlockPos pos, RandomSource p_223323_2_) {
		if (p_223323_0_.getBrightness(LightLayer.SKY, pos) > p_223323_2_.nextInt(32)) {
			return false;
		} else {
			DimensionType dimensiontype = p_223323_0_.dimensionType();
			int i = dimensiontype.monsterSpawnBlockLightLimit();
			if (i < 15 && p_223323_0_.getBrightness(LightLayer.BLOCK, pos) > i) {
				return false;
			} else {
				int j = p_223323_0_.getLevel().isThundering() ? p_223323_0_.getMaxLocalRawBrightness(pos, 10) : p_223323_0_.getMaxLocalRawBrightness(pos);
				return j <= dimensiontype.monsterSpawnLightTest().sample(p_223323_2_);
			}
		}
	}

	public static boolean checkMonsterSpawnRules(EntityType<? extends FURTameableEntity> p_223325_0_, ServerLevelAccessor p_223325_1_, MobSpawnType p_223325_2_, BlockPos p_223325_3_, RandomSource p_223325_4_) {
		return p_223325_1_.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawn(p_223325_1_, p_223325_3_, p_223325_4_) && checkMobSpawnRules(p_223325_0_, p_223325_1_, p_223325_2_, p_223325_3_, p_223325_4_);
	}

	@Override
	public boolean removeWhenFarAway(double p_213397_1_) {
		return !(this.isTame() && this.getOwner() instanceof Player);
	}
	
	@Override
    protected boolean shouldDespawnInPeaceful() {
	    return !(this.isTame() && this.getOwner() instanceof Player);
    }   
	
	@Override
	public boolean requiresCustomPersistence() {
		return (this.isTame() && this.getOwner() instanceof Player) || super.requiresCustomPersistence();
	}
	
	public void setLimitedLife(int limitedLifeTicksIn) {    	
    }
    
    protected boolean isCommandable() {
    	return true;
    }
    
    protected boolean canSitCondition() {
    	return true;
    }
    
    public void doSitCommand(Player playerIn) {    	
    	this.goalSelector.removeGoal(this.wander);
    	this.goalSelector.removeGoal(this.follow);
        this.jumping = false;
        this.getNavigation().stop();
		this.state = FURTameableEntity.State.SITTING;		
		this.setInSittingPose(true);
		if (playerIn != null)
			playerIn.displayClientMessage(Component.translatable("command.fur.sitting", this.getName()), true);
    }
    
    public void doFollowCommand(Player playerIn) {
    	this.goalSelector.removeGoal(this.wander);
		this.follow = this.followGoal();
		this.goalSelector.addGoal(6, this.follow);
		this.getNavigation().stop();
		this.state = FURTameableEntity.State.FOLLOWING;
		this.setInSittingPose(false);
		if (playerIn != null)
			playerIn.displayClientMessage(Component.translatable("command.fur.following", this.getName()), true);
    }
    
    public void doWanderCommand(Player playerIn) {
		this.goalSelector.removeGoal(this.follow);
		this.wander = this.wanderGoal();
		this.goalSelector.addGoal(7, this.wander);
		this.getNavigation().stop();
		this.state = FURTameableEntity.State.WANDERING;
		this.setInSittingPose(false);
		if (playerIn != null)
			playerIn.displayClientMessage(Component.translatable("command.fur.wandering", this.getName()), true);
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
        	if (actionresulttype.equals(InteractionResult.SUCCESS)) {
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
	    	} else if (this.isTame() && this.isOwnedBy(player) && this.isCommandable() && this.getUsedItemHand().equals(hand)) {  
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
    	this.doSitCommand(null);
    	this.doFollowCommand(null);
    }
    
    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        super.tick();
        
        if (!this.level().isClientSide/* && FURConfig.Suicidal_Minion.get()*/ && (this.getOwner() != null && (!(this.getOwner() instanceof Player) && !this.getOwner().isAlive()))) {
        	this.hurt(this.damageSources().genericKill(), this.getMaxHealth());
        	this.addTag("FUR_noLoot");
        }
    }      
    
    public boolean hurt(DamageSource p_70097_1_, float p_70097_2_) {
        if (this.isInvulnerableTo(p_70097_1_)) {
           return false;
        } else {
           Entity entity = p_70097_1_.getEntity();
           this.setOrderedToSit(false);
           if (entity != null && !(entity instanceof Player) && !(entity instanceof AbstractArrow)) {
              p_70097_2_ = (p_70097_2_ + 1.0F) / 2.0F;
           }

           return super.hurt(p_70097_1_, p_70097_2_);
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
    
    public float getBonusDamage(LivingEntity LivingEntityIn, int sharpness, int bane_of_arthropods, int smite) {
    	return (0.5F * sharpness + 0.5F)
				+ (LivingEntityIn.getMobType().equals(MobType.ARTHROPOD) ? bane_of_arthropods * 2.5F : 0)
				+ (LivingEntityIn.getMobType().equals(MobType.UNDEAD) ? smite * 2.5F : 0);
    }
    
	@Override
	public AgeableMob getBreedOffspring(ServerLevel worldIn, AgeableMob entity) {
		return null;
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
       if(this.state.equals(FURTameableEntity.State.WANDERING)) {
    	    compound.putByte("state", (byte)0);
		}
		else if(this.state.equals(FURTameableEntity.State.SITTING)) {
			compound.putByte("state", (byte)1);
		}
		else if(this.state.equals(FURTameableEntity.State.FOLLOWING)) {
			compound.putByte("state", (byte)2);
		}
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
       super.readAdditionalSaveData(compound);
       switch(compound.getByte("state")) {
	       case (byte)0:
	       		this.doWanderCommand(null);
	  			break;
	       case (byte)1:
	       		this.doSitCommand(null);
	    	   	break;
	       case (byte)2:
	       		this.doFollowCommand(null);
	       	   	break;
	   		default:
	   			break;
       }
    }
	
    static enum State
    {
        SITTING,
        WANDERING,
        FOLLOWING;
    }
}
