package com.Fishmod.fur.entities.aquatic;

import javax.annotation.Nullable;

import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURItemRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class UndeadFishEntity extends AbstractSchoolingFish implements GeoEntity {
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	
	private static final RawAnimation SWIM = RawAnimation.begin().thenPlay("undead_fish.model.swim");
	
	private static final EntityDataAccessor<Integer> SKIN_TYPE = SynchedEntityData.defineId(UndeadFishEntity.class, EntityDataSerializers.INT);
	
	public UndeadFishEntity(EntityType<? extends UndeadFishEntity> p_i50279_1_, Level p_i50279_2_) {
		super(p_i50279_1_, p_i50279_2_);
	}
	
	@Override
    protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(SKIN_TYPE, Integer.valueOf(0));
    }
	
    public static boolean isDarkEnoughToSpawn(ServerLevelAccessor p_223323_0_, BlockPos p_223323_1_, RandomSource p_223323_2_) {
        if (p_223323_0_.getBrightness(LightLayer.SKY, p_223323_1_) > p_223323_2_.nextInt(32)) {
           return false;
        } else {
           int i = p_223323_0_.getLevel().isThundering() ? p_223323_0_.getMaxLocalRawBrightness(p_223323_1_, 10) : p_223323_0_.getMaxLocalRawBrightness(p_223323_1_);
           return i <= p_223323_2_.nextInt(8);
        }
	}
	
	public static boolean checkUndeadFishSpawnRules(EntityType<? extends AbstractFish> entity, ServerLevelAccessor server, MobSpawnType type, BlockPos pos, RandomSource rand) {
		return server.getBlockState(pos).is(Blocks.WATER) 
				&& rand.nextInt(15) == 0 
				&& isDarkEnoughToSpawn(server, pos, rand);
	}
	
	public static boolean checkBoneTroutSpawnRules(EntityType<? extends AbstractFish> entity, ServerLevelAccessor server, MobSpawnType type, BlockPos pos, RandomSource rand) {
		return server.getBlockState(pos).is(Blocks.WATER) 
				&& rand.nextInt(15) == 0 
				&& isDarkEnoughToSpawn(server, pos, rand) 
				&& !server.canSeeSky(pos) 
				&& pos.getY() <= server.getSeaLevel() - 33;
	}
	
    protected void handleAirSupply(int p_209207_1_) {    	
    }

    @Override
	public ItemStack getBucketItemStack() {
		if (this.getType().equals(FUREntityRegistry.MUMMIFIED_COD.get())) {
			return new ItemStack(FURItemRegistry.MUMMIFIED_COD_BUCKET.get());
		} else if  (this.getType().equals(FUREntityRegistry.BONE_TROUT.get())) {
			return new ItemStack(FURItemRegistry.BONE_TROUT_BUCKET.get());
		}
		
		return new ItemStack(Items.WATER_BUCKET);
	}
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_213386_1_, DifficultyInstance difficulty, MobSpawnType p_213386_3_, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag p_213386_5_) {   	
		if (this.getType().equals(FUREntityRegistry.MUMMIFIED_COD.get())) {
			this.setSkin(1);
		} else if (this.getType().equals(FUREntityRegistry.BONE_TROUT.get())) {
    		this.setSkin(0);
    	}
    	
    	return super.finalizeSpawn(p_213386_1_, difficulty, p_213386_3_, livingdata, p_213386_5_);
    }
    
    public int getSkin() {
        return this.getEntityData().get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
        this.getEntityData().set(SKIN_TYPE, Integer.valueOf(skinType));
    }

	protected SoundEvent getAmbientSound() {
		return SoundEvents.COD_AMBIENT;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.COD_DEATH;
	}

	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		return SoundEvents.COD_HURT;
	}

	protected SoundEvent getFlopSound() {
		return SoundEvents.COD_FLOP;
	}
	
    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }
    
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
		this.setSkin(compound.getInt("Variant"));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", getSkin());
    }

    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
        state.getController().setAnimation(SWIM);       
        return PlayState.CONTINUE;
    }
    
	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "controller", 5, this::predicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}