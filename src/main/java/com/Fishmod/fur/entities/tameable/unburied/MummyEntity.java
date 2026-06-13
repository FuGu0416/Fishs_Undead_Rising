package com.Fishmod.fur.entities.tameable.unburied;

import java.util.Random;

import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.entities.tameable.FURTameableEntity;
import com.Fishmod.fur.init.FUREffectRegistry;
import com.Fishmod.fur.init.FURParticleRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class MummyEntity extends UnburiedEntity {
	private static final EntityDataAccessor<Integer> SKIN_TYPE = SynchedEntityData.defineId(MummyEntity.class, EntityDataSerializers.INT);

    public MummyEntity(EntityType<? extends MummyEntity> entityType, Level LevelIn) {
        super(entityType, LevelIn);
    }
    
    @Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SKIN_TYPE, Integer.valueOf(0));
	}
    
    @Override
    protected void registerGoals() {
    	super.registerGoals();
    	if (!FURConfig.SunScreen_Mode.get())this.goalSelector.addGoal(4, new FleeSunGoal(this, 1.0D));
    }
   
    public static AttributeSupplier.Builder createAttributes() {
        return Zombie.createAttributes()
        		.add(Attributes.FOLLOW_RANGE, 35.0D)
        		.add(Attributes.MOVEMENT_SPEED, (double)0.21F)
        		.add(Attributes.MAX_HEALTH, 24.0D)
        		.add(Attributes.ATTACK_DAMAGE, 4.0D)
        		.add(Attributes.ARMOR, 4.0D)
        		.add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }

    @Override
    public double getMyRidingOffset() {
        return this.isBaby() ? 0.0D : -0.25D;
    }
    
    public static boolean checkMummySpawnRules(EntityType<? extends MummyEntity> entityTypeIn, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource randomSource) {
        return FURTameableEntity.checkMonsterSpawnRules(entityTypeIn, level, spawnType, pos, randomSource);//SpawnUtil.isAllowedDimension(this.dimension);
    }
    
    @Override
    protected boolean isSunBurnTick() {
        return false;
    }
    
    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
    	super.tick();

    	if(this.tickCount % 2 == 0)
    		this.level().addParticle(FURParticleRegistry.LOCUST_SWARM.get(), this.getX() + (double)(this.getRandom().nextFloat() * this.getBbWidth() * 2.0F) - (double)this.getBbWidth(), this.getY() + (double)((0.4F + new Random().nextFloat() * 0.6F) * this.getBbHeight()), this.getZ() + (double)(new Random().nextFloat() * this.getBbWidth() * 2.0F) - (double)this.getBbWidth(), 0.0D, 0.0D, 0.0D);
    }
    
    public boolean doHurtTarget(Entity par1Entity) {
        if (super.doHurtTarget(par1Entity)) {       	
        	if (par1Entity instanceof LivingEntity) {          	
            	if (this.isTame()) {
		            if (this.corrosive > 0) {
		            	((LivingEntity)par1Entity).addEffect(new MobEffectInstance(FUREffectRegistry.CORRODED.get(), 4 * 20, Math.max(1, this.corrosive - 1)));
		            } else {
		            	((LivingEntity)par1Entity).addEffect(new MobEffectInstance(FUREffectRegistry.CORRODED.get(), 2 * 20, 1));
		            }
            	} else {
            		float local_difficulty = this.level().getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
                	((LivingEntity)par1Entity).addEffect(new MobEffectInstance(FUREffectRegistry.CORRODED.get(), 2 * 20 * (int)local_difficulty, 1));
            	}
            }

            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficulty, MobSpawnType spawnTypeIn, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
    	livingdata = super.finalizeSpawn(worldIn, difficulty, spawnTypeIn, livingdata, tag);
    	this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Mummy_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Mummy_Attack.get());
    	this.setHealth(this.getMaxHealth());
    	this.setSkin(4);
    	return livingdata;
    }
       
    public int getSkin() {
        return this.entityData.get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
    	this.entityData.set(SKIN_TYPE, Integer.valueOf(skinType));
    }
	
    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("Variant", this.getSkin());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setSkin(nbt.getInt("Variant"));
    }
}
