package com.Fishmod.mod_LavaCow.entities;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.entities.floating.GraveRobberGhostEntity;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;
import com.Fishmod.mod_LavaCow.misc.LootTableHandler;
import com.google.common.collect.Maps;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.GroundPathHelper;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.raid.Raid;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GraveRobberEntity extends AbstractIllagerEntity {
	public int tradeTimer = 0;
	
	public GraveRobberEntity(EntityType<? extends GraveRobberEntity> p_i48556_1_, World p_i48556_2_) {
		super(p_i48556_1_, p_i48556_2_);
		this.setCanPickUpLoot(true);
	}
	
	protected void customServerAiStep() {
		if (!this.isNoAi() && GroundPathHelper.hasGroundPathNavigation(this)) {
			boolean flag = ((ServerWorld)this.level).isRaided(this.blockPosition());
			((GroundPathNavigator)this.getNavigation()).setCanOpenDoors(flag);
		}

		super.customServerAiStep();
	}
	
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(2, new AbstractIllagerEntity.RaidOpenDoorGoal(this));
		this.goalSelector.addGoal(3, new AbstractRaiderEntity.FindTargetGoal(this, 10.0F));
		this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, true));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, AbstractRaiderEntity.class)).setAlertOthers());
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 0, true, false, (p_210136_0_) -> {
				return this.getHealth() > this.getMaxHealth() * 0.5F &&  p_210136_0_.getMobType().equals(CreatureAttribute.UNDEAD);
			}));	
		this.goalSelector.addGoal(7, new GraveRobberEntity.TradeGoal(this));
		this.goalSelector.addGoal(8, new RandomWalkingGoal(this, 0.6D));
		this.goalSelector.addGoal(9, new LookAtGoal(this, PlayerEntity.class, 3.0F, 1.0F));
		this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 8.0F));
	}
	
	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return MonsterEntity.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, (double)0.35F)
				.add(Attributes.FOLLOW_RANGE, 12.0D)
				.add(Attributes.MAX_HEALTH, FURConfig.GraveRobber_Health.get())
				.add(Attributes.ATTACK_DAMAGE, FURConfig.GraveRobber_Attack.get());
	}
	
	@OnlyIn(Dist.CLIENT)
	public AbstractIllagerEntity.ArmPose getArmPose() {
		if (this.isAggressive()) {
			return AbstractIllagerEntity.ArmPose.ATTACKING;
		} else if (!this.getOffhandItem().isEmpty()) {
			return AbstractIllagerEntity.ArmPose.CROSSBOW_HOLD;
		} else {
			return this.isCelebrating() ? AbstractIllagerEntity.ArmPose.CELEBRATING : AbstractIllagerEntity.ArmPose.CROSSED;
		}
	}
	
	public void aiStep() {
	      super.aiStep();
	      if (!this.level.isClientSide && this.tickCount % 20 == 0) {	    	  
	      }
	}
	
	@Override
	public boolean canHoldItem(ItemStack stack) {
		return stack.getItem() == Items.EMERALD && !this.isAggressive() && this.getOffhandItem().isEmpty();
	}
	
	@Override
	protected boolean canReplaceCurrentItem(ItemStack stack_pickup, ItemStack stack_onhand) {
		return this.getMainHandItem().getItem() == Items.IRON_SHOVEL && this.getOffhandItem().isEmpty();
	}
	
	@Override
	protected void pickUpItem(ItemEntity stack) {
		this.onItemPickup(stack);
        this.setItemSlot(EquipmentSlotType.OFFHAND, stack.getItem());
        this.setGuaranteedDrop(EquipmentSlotType.OFFHAND);
        this.take(stack, stack.getItem().getCount());
        stack.remove();
	}
	
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.tradeTimer = compound.getInt("tradeTimer");
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("tradeTimer", this.tradeTimer);
    }
	
	@Nullable
	public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance p_213386_2_, SpawnReason p_213386_3_, @Nullable ILivingEntityData p_213386_4_, @Nullable CompoundNBT p_213386_5_) {
		ILivingEntityData ilivingentitydata = super.finalizeSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, p_213386_5_);
		((GroundPathNavigator)this.getNavigation()).setCanOpenDoors(true);
		this.populateDefaultEquipmentSlots(p_213386_2_);
		this.populateDefaultEquipmentEnchantments(p_213386_2_);
		
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.GraveRobber_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.GraveRobber_Attack.get());
    	this.setHealth(this.getMaxHealth());
    	
		return ilivingentitydata;
	}
	   
	@Override
	protected void populateDefaultEquipmentSlots(DifficultyInstance p_180481_1_) {
		if (this.getCurrentRaid() == null) {
			this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.IRON_SHOVEL));
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
				this.tradeTimer = 60;
				break;
			default:
				super.handleEntityEvent(id);
				break;
		}
    }
	
	public boolean isAlliedTo(Entity p_184191_1_) {
		if (super.isAlliedTo(p_184191_1_)) {
			return true;
		} else if (p_184191_1_ instanceof LivingEntity && ((LivingEntity)p_184191_1_).getMobType() == CreatureAttribute.ILLAGER) {
			return this.getTeam() == null && p_184191_1_.getTeam() == null;
		} else {
			return false;
		}
	}
	
	static class TradeGoal extends Goal {
		private final GraveRobberEntity mob;
			
        public TradeGoal(GraveRobberEntity entity) {
        	this.mob = entity;
        	this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }
        
        private static List<ItemStack> getItemStacks(GraveRobberEntity mob) {
            LootTable loottable = mob.level.getServer().getLootTables().get(LootTableHandler.TRADE_LOOT);
            LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerWorld)mob.level)).withParameter(LootParameters.ORIGIN, mob.position()).withParameter(LootParameters.THIS_ENTITY, mob).withRandom(mob.getRandom());
            return loottable.getRandomItems(lootcontext$builder.create(LootParameterSets.PIGLIN_BARTER));
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        @Override
        public boolean canUse() {
            return this.mob.tradeTimer <= 0  && !this.mob.getOffhandItem().isEmpty() && !this.mob.isAggressive();
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        @Override
        public boolean canContinueToUse() {
            return this.mob.tradeTimer > 0  && !this.mob.getOffhandItem().isEmpty() && !this.mob.isAggressive();
        }
        
        @Override
        public void start() {
        	this.mob.tradeTimer = 60;
        	this.mob.level.broadcastEntityEvent(this.mob, (byte)4);
        	this.mob.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.15F);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        @Override
        public void tick() {            
        	if (this.mob.tradeTimer > 0) {
        		this.mob.tradeTimer --;
        	}
        }
        
        @Override
        public void stop() {     
        	this.mob.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.35F);
        	this.mob.tradeTimer = 0;        	
        	
        	if (!this.mob.isAggressive()) {       		
        		List<ItemStack> lootList = getItemStacks(this.mob);
        		 if (lootList.size() > 0) {
                     ItemStack loot = lootList.remove(0).copy();
                     this.mob.spawnAtLocation(loot);
                 }      		
        	} else {
        		this.mob.spawnAtLocation(this.mob.getOffhandItem().copy());
        	}
        	
        	ItemStack stack = this.mob.getOffhandItem().copy();
        	stack.setCount(stack.getCount() - 1);
        	this.mob.setItemSlot(EquipmentSlotType.OFFHAND, stack);
        }
    }
	
	protected SoundEvent getAmbientSound() {
		return SoundEvents.VINDICATOR_AMBIENT;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.VINDICATOR_DEATH;
	}

	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		return SoundEvents.VINDICATOR_HURT;
	}

	@Override
	public void applyRaidBuffs(int p_213660_1_, boolean p_213660_2_) {	
		ItemStack itemstack = new ItemStack(Items.IRON_SHOVEL);
		Raid raid = this.getCurrentRaid();
		int i = 1;
		if (p_213660_1_ > raid.getNumGroups(Difficulty.NORMAL)) {
			i = 2;
		}

		boolean flag = this.random.nextFloat() <= raid.getEnchantOdds();
		if (flag) {
			Map<Enchantment, Integer> map = Maps.newHashMap();
			map.put(Enchantments.KNOCKBACK, i);
			EnchantmentHelper.setEnchantments(map, itemstack);
		}

		this.setItemSlot(EquipmentSlotType.MAINHAND, itemstack);
	}

	@Override
	public SoundEvent getCelebrateSound() {
		return SoundEvents.VINDICATOR_CELEBRATE;
	}
	
    /**
     * Called when the mob's health reaches 0.
     */
    @Override
    public void die(DamageSource cause) {
       super.die(cause);
       
		if (!this.level.isDay() && this.getRandom().nextInt(100) < FURConfig.pSpawnRate_GraveRobberGhost.get() && !this.level.isClientSide()) {
        	GraveRobberGhostEntity entity = FUREntityRegistry.GRAVEROBBERGHOST.create(this.level);
        	entity.setPos(this.getX(), this.getY() + 2.0D, this.getZ());
        	this.level.addFreshEntity(entity);
        			
        	if(cause.getEntity() != null && cause.getEntity() instanceof LivingEntity)
        		if (!(cause.getEntity() instanceof PlayerEntity && ((PlayerEntity)cause.getEntity()).isCreative())) {
        			entity.setTarget((LivingEntity) cause.getEntity());
        		}
		}
    }
}
