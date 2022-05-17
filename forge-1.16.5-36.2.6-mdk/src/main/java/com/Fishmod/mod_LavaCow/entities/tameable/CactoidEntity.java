package com.Fishmod.mod_LavaCow.entities.tameable;

import java.util.Random;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;

import net.minecraft.block.BlockState;
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
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.OwnerHurtByTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CactoidEntity extends FURTameableEntity {
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.defineId(CactoidEntity.class, DataSerializers.INT);
	private WaterAvoidingRandomWalkingGoal move;
	private LookAtGoal watch;
	private LookRandomlyGoal look;
	
	public CactoidEntity(EntityType<? extends CactoidEntity> p_i48549_1_, World worldIn) {
        super(p_i48549_1_, worldIn);
    }
	
	@Override
    protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SKIN_TYPE, Integer.valueOf(0));
    }
	
    @Override
    protected void registerGoals() {
    	this.move = new WaterAvoidingRandomWalkingGoal(this, 1.0D);
        this.watch = new LookAtGoal(this, PlayerEntity.class, 8.0F);
        this.look = new LookRandomlyGoal(this);
        
    	super.registerGoals();
    	this.goalSelector.addGoal(1, new SwimGoal(this));
    	this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
    	this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.5D, true));
    	this.goalSelector.addGoal(6, this.move);
        this.goalSelector.addGoal(10, this.watch);
        this.goalSelector.addGoal(10, this.look);

        this.applyEntityAI();
    }

    protected void applyEntityAI() {
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
    }
    
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.25D)
        		.add(Attributes.MAX_HEALTH, FURConfig.LilSludge_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, FURConfig.LilSludge_Attack.get());
    }
    
    public static boolean checkCactoidSpawnRules(EntityType<? extends CactoidEntity> p_223316_0_, IWorld p_223316_1_, SpawnReason p_223316_2_, BlockPos p_223316_3_, Random p_223316_4_) {
        return FURTameableEntity.checkMonsterSpawnRules(p_223316_0_, (IServerWorld) p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_) && p_223316_1_.canSeeSky(p_223316_3_);//SpawnUtil.isAllowedDimension(this.dimension);
    }
    
    public int getSkin() {
        return this.entityData.get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
        this.entityData.set(SKIN_TYPE, Integer.valueOf(skinType));
    }
    
    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    @Override
    public boolean isFood(ItemStack stack) {
    	return stack.getItem().equals(Items.WATER_BUCKET);
    }
    
    @Override
    protected boolean isSunBurnTick() {
        if (this.level.isDay() && !this.level.isClientSide) {
           float f = this.getBrightness();
           BlockPos blockpos = this.getVehicle() instanceof BoatEntity ? (new BlockPos(this.getX(), (double)Math.round(this.getY()), this.getZ())).above() : new BlockPos(this.getX(), (double)Math.round(this.getY()), this.getZ());
           return (f > 0.5F && this.level.canSeeSky(blockpos));
        }
        return false;
    }
    
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void tick() {
    	if (!this.level.isClientSide && !this.isTame()) {
    		if (this.isSunBurnTick()) {
    			this.doSitCommand(null);
    		} else if (this.state != FURTameableEntity.State.WANDERING) {
    			this.doFollowCommand(null);
    			this.doWanderCommand(null);
    		}
    	}    	
    	
    	super.tick();
    }
    
    @Override
	public void doSitCommand(PlayerEntity playerIn) {
    	this.goalSelector.removeGoal(this.move);
        this.goalSelector.removeGoal(this.watch);
        this.goalSelector.removeGoal(this.look);
        this.setSilent(true);
    	super.doSitCommand(playerIn);
    }
    
    @Override
	public void doFollowCommand(PlayerEntity playerIn) {
    	this.goalSelector.addGoal(6, this.move);
        this.goalSelector.addGoal(8, this.watch);
        this.goalSelector.addGoal(8, this.look);
		this.setSilent(false);
        super.doFollowCommand(playerIn);
    }

	@Override
    public boolean doHurtTarget(Entity entityIn) {
        boolean flag = super.doHurtTarget(entityIn);

        return flag;
    }    
	
	/**
	* Called when the entity is attacked.
	*/
    @Override
	public boolean hurt(DamageSource source, float amount) {
        if (!source.isMagic() && !source.isExplosion() && source.getDirectEntity() instanceof LivingEntity) {
            source.getDirectEntity().hurt(DamageSource.thorns(this), 2.0F);
        }
        
    	if(source.isFire())
    		return super.hurt(source, 2.0F * amount);

    	return super.hurt(source, amount);
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
        return p_213348_2_.height * 0.5F;
    }
	
    /**
     * Handler for {@link World#setEntityState}
     */
	@OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
    	if (id == 11) {

        } else {
            super.handleEntityEvent(id);
        }
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return FURSoundRegistry.LILSLUDGE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
    	return SoundEvents.WOOL_BREAK;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.CACTYRANT_DEATH;
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
    public boolean shouldDropLoot() {
    	return this.isTame() || !(this.getOwner() instanceof PlayerEntity);
    }
}
