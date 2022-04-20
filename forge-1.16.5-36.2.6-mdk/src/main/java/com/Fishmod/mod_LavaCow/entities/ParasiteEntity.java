package com.Fishmod.mod_LavaCow.entities;

import java.util.List;
import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.init.FUREffectRegistry;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;
import com.Fishmod.mod_LavaCow.misc.LootTableHandler;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class ParasiteEntity extends SpiderEntity {
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.defineId(ParasiteEntity.class, DataSerializers.INT);
	private static final DataParameter<Direction> ATTACHED_BLK = EntityDataManager.defineId(ParasiteEntity.class, DataSerializers.DIRECTION);
	private static final Direction[] DIRECTIONS = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
	private static final Item[] ParasiteDrop = { FURItemRegistry.PARASITE_COMMON, FURItemRegistry.PARASITE_DESERT, FURItemRegistry.PARASITE_JUNGLE, FURItemRegistry.PARASITE_COOKED };
	public int lifespawn;
	private boolean long_live;
	
	public ParasiteEntity(EntityType<? extends ParasiteEntity> p_i48549_1_, World worldIn) {
        super(p_i48549_1_, worldIn);
        this.long_live = false;
        this.lifespawn = 16*20;//Can live for only 16secs, poor little one:(
        this.xpReward = 1;
    }
	
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new SwimGoal(this));
		this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));
		this.goalSelector.addGoal(4, new ParasiteEntity.AttackGoal(this));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.8D));
		this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
        this.applyEntityAI();
    }
	
    protected void applyEntityAI() {
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    	this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 0, true, true, (p_210136_0_) -> {
	  	      return p_210136_0_ instanceof LivingEntity && ((LivingEntity)p_210136_0_).attackable() 
	  	    		  && LootTableHandler.PARASITE_HOSTLIST.contains(p_210136_0_.getType().getRegistryName());
	  	   }));
    }
    
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.2D)
        		.add(Attributes.FOLLOW_RANGE, 8.0D)
        		.add(Attributes.MAX_HEALTH, FURConfig.Parasite_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, FURConfig.Parasite_Attack.get());
    }
    
    @Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SKIN_TYPE, Integer.valueOf(this.getRandom().nextInt(3)));
		this.entityData.define(ATTACHED_BLK, Direction.DOWN);
	}
	
    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT p_213386_5_) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Parasite_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Parasite_Attack.get());
    	this.setHealth(this.getMaxHealth());
    	
    	return livingdata;
    }
	
	@Override
	public void tick() {
        if (this.lifespawn > 0) {
        	if(!long_live)this.lifespawn--;
        } else if (this.getSkin() == 2 && this.getRandom().nextInt(100) < FURConfig.pSpawnRate_Vespa.get()) {
        	double d0 = this.getAttributeValue(Attributes.FOLLOW_RANGE);
        	List<PlayerEntity> list = this.level.getEntitiesOfClass(PlayerEntity.class, this.getBoundingBox().inflate(d0));

        	if(!list.isEmpty()) {
            	this.lifespawn = 5 * 20;
        		
        		if (!this.level.isClientSide) {		
        			this.playSound(FURSoundRegistry.PARASITE_WEAVE, 1.0F, 1.0F);
        			
		    		VespaCocoonEntity pupa = FUREntityRegistry.VESPACOCOON.create(this.level);
		    		pupa.moveTo(this.getX(), this.getY(), this.getZ(), this.yRot, this.xRot);
		    		this.level.addFreshEntity(pupa);
        		}     		
        		this.remove();
        	} else
        		this.hurt(DamageSource.mobAttack(this).bypassInvul().bypassArmor() , this.getMaxHealth());
        } else {
        	this.hurt(DamageSource.mobAttack(this).bypassInvul().bypassArmor() , this.getMaxHealth());
        }
        	     
        if (this.getVehicle() != null && this.getVehicle() instanceof LivingEntity && this.getVehicle().isAlive() && !this.level.isClientSide()) {
        	Entity mount = this.getVehicle();
        	
        	if (!((LivingEntity) mount).hasEffect(FUREffectRegistry.INFESTED)) {
        		this.stopRiding();   		
        		this.hurt(DamageSource.mobAttack(this).bypassInvul().bypassArmor() , this.getMaxHealth());
        	} else if (mount.isAlive() && mount.isOnFire()) {
        		this.setRemainingFireTicks(20);
        		this.stopRiding();        		
        	} else if (mount.isAlive() && this.tickCount % 20 == 0) {
        		this.doHurtTarget(mount);
        	}
        } else if (getVehicle() == null && this.long_live)
        	this.long_live = false;
             
        if (!this.level.isClientSide()) {
            if (this.isOnGround() || this.isInWaterOrBubble() || this.isInLava()) {
                this.entityData.set(ATTACHED_BLK, Direction.DOWN);
            } else if (this.verticalCollision) {
                this.entityData.set(ATTACHED_BLK, Direction.UP);
            } else {
                Direction closestDirection = Direction.DOWN;
                double closestDistance = 100;
                for (Direction dir : DIRECTIONS) {
                    BlockPos antPos = new BlockPos(Math.floor(this.getX()), Math.floor(this.getY()), Math.floor(this.getZ()));
                    BlockPos offsetPos = antPos.relative(dir);
                    Vector3d offset = Vector3d.atCenterOf(offsetPos);
                    if (closestDistance > this.position().distanceTo(offset) && level.loadedAndEntityCanStandOnFace(offsetPos, this, dir.getOpposite())) {
                        closestDistance = this.position().distanceTo(offset);
                        closestDirection = dir;
                    }
                }
                this.entityData.set(ATTACHED_BLK, closestDirection);
            }
        }      
        super.tick();
    }
	
    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (itemstack.isEmpty()) {
        	player.playSound(SoundEvents.ITEM_PICKUP, 1.0F, 1.0F);
        	
        	if (!player.inventory.add(new ItemStack(ParasiteDrop[this.getSkin()]))) {
                player.spawnAtLocation(new ItemStack(ParasiteDrop[this.getSkin()]));
            }
        	this.remove();
        	
        	return ActionResultType.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }
	
	@Override
	public double getMyRidingOffset() {
		if (this.getVehicle() != null && (this.getVehicle() instanceof PlayerEntity || this.getVehicle() instanceof ZombieEntity))
			return this.getVehicle().getBbHeight()/2  - 0.85F;
		else if (this.getVehicle() != null)
			return this.getVehicle().getBbHeight() * 0.65D - 1.0D;
		else
			return super.getMyRidingOffset();
	}
	
	@Override
	public boolean doHurtTarget(Entity entity) {
		if (super.doHurtTarget(entity)) {
			((LivingEntity) entity).addEffect(new EffectInstance(FUREffectRegistry.INFESTED, 8*20, 0));
			if(this.getSkin() == 2)
				((LivingEntity) entity).addEffect(new EffectInstance(Effects.POISON, 4*20, 0));
			return true;
		}
		return false;
	}
	
	@Override
    public void push(Entity entityIn) {		
		if (entityIn instanceof LivingEntity && LootTableHandler.PARASITE_HOSTLIST.contains(entityIn.getType().getRegistryName()) && FURConfig.Parasite_Attach.get()) {
    		((LivingEntity) entityIn).addEffect(new EffectInstance(FUREffectRegistry.INFESTED, 8*20, 0));
    		this.startRiding(entityIn);
            this.long_live = true;
        } else
        	super.push(entityIn);
    }
	
	@Override
	public void playerTouch(PlayerEntity playerIn) {
		super.playerTouch(playerIn);
		if (!playerIn.isCreative() && FURConfig.Parasite_Attach.get()) {
			playerIn.addEffect(new EffectInstance(FUREffectRegistry.INFESTED, 8*20, 0));
    		this.startRiding(playerIn);
            this.long_live = true;
        } 	
	}
	
	public static ParasiteEntity gotParasite(List<Entity> listIn) {
		for(Entity C : listIn)
			if(C instanceof ParasiteEntity)return (ParasiteEntity)C;		
		return null;
	}
	
    public Direction getAttachedBlock() {
        return this.getEntityData().get(ATTACHED_BLK);
    }
	
    public int getSkin() {
        return this.getEntityData().get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
        this.getEntityData().set(SKIN_TYPE, Integer.valueOf(skinType));
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
    
	@Override
    public float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
        return 0.1F;
    }
	
	@Override
	protected boolean isMovementNoisy() {
		return false;
	}
	
	@Override
    protected SoundEvent getAmbientSound() {
        return FURSoundRegistry.PARASITE_AMBIENT;
    }
	
	@Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FURSoundRegistry.PARASITE_HURT;
    }

	@Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.PARASITE_DEATH;
    }
	
	@Override
    protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(SoundEvents.SILVERFISH_STEP, 0.15F, 1.0F);
	} 
	
    @Nullable
    @Override
    protected ResourceLocation getDefaultLootTable() {
    	switch(this.getSkin()) {
    		case 1:
    			return new ResourceLocation("mod_lavacow", "entities/parasite1");
    		case 2:
    			return new ResourceLocation("mod_lavacow", "entities/parasite2");
    		case 0:
    		default:
    			return super.getDefaultLootTable();
    	}
    }
    
    static class AttackGoal extends MeleeAttackGoal {
        public AttackGoal(ParasiteEntity p_i46676_1_) {
           super(p_i46676_1_, 1.0D, true);
        }

        public boolean canUse() {
           return super.canUse() && !this.mob.isVehicle();
        }

        public boolean canContinueToUse() {
           float f = this.mob.getBrightness();
           if (f >= 0.5F && this.mob.getRandom().nextInt(100) == 0) {
              this.mob.setTarget((LivingEntity)null);
              return false;
           } else {
              return super.canContinueToUse();
           }
        }

        protected double getAttackReachSqr(LivingEntity p_179512_1_) {
           return (double)(0.1F + p_179512_1_.getBbWidth());
        }
     }
}
