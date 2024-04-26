package com.Fishmod.mod_LavaCow.entities.tameable.unburied;

import java.util.Random;
import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.entities.tameable.FURTameableEntity;
import com.Fishmod.mod_LavaCow.init.FUREffectRegistry;
import com.Fishmod.mod_LavaCow.init.FURParticleRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.FleeSunGoal;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class MummyEntity extends UnburiedEntity {
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.defineId(MummyEntity.class, DataSerializers.INT);

    public MummyEntity(EntityType<? extends MummyEntity> p_i48549_1_, World worldIn) {
        super(p_i48549_1_, worldIn);
    }
    
    @Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SKIN_TYPE, Integer.valueOf(0));
	}
    
    @Override
    protected void registerGoals() {
    	super.registerGoals();
    	if(!FURConfig.SunScreen_Mode.get())this.goalSelector.addGoal(4, new FleeSunGoal(this, 1.0D));
    }
   
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return ZombieEntity.createAttributes()
        		.add(Attributes.FOLLOW_RANGE, 35.0D)
        		.add(Attributes.MOVEMENT_SPEED, (double)0.21F)
        		.add(Attributes.MAX_HEALTH, FURConfig.Mummy_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, FURConfig.Mummy_Attack.get())
        		.add(Attributes.ARMOR, 4.0D)
        		.add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }

    @Override
    public double getMyRidingOffset() {
        return this.isBaby() ? 0.0D : -0.25D;
    }
    
    public static boolean checkMummySpawnRules(EntityType<? extends MummyEntity> p_223316_0_, IWorld p_223316_1_, SpawnReason p_223316_2_, BlockPos p_223316_3_, Random p_223316_4_) {
        return FURTameableEntity.checkMonsterSpawnRules(p_223316_0_, (IServerWorld) p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_);//SpawnUtil.isAllowedDimension(this.dimension);
    }
    
    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
    	super.tick();

    	if(this.tickCount % 2 == 0)
    		this.level.addParticle(FURParticleRegistry.LOCUST_SWARM, this.getX() + (double)(this.getRandom().nextFloat() * this.getBbWidth() * 2.0F) - (double)this.getBbWidth(), this.getY() + (double)((0.4F + new Random().nextFloat() * 0.6F) * this.getBbHeight()), this.getZ() + (double)(new Random().nextFloat() * this.getBbWidth() * 2.0F) - (double)this.getBbWidth(), 0.0D, 0.0D, 0.0D);
    }
    
    public boolean doHurtTarget(Entity par1Entity) {
        if (super.doHurtTarget(par1Entity)) {       	
        	if (par1Entity instanceof LivingEntity) {          	
            	if (this.isTame()) {
		            if (this.corrosive > 0) {
		            	((LivingEntity)par1Entity).addEffect(new EffectInstance(FUREffectRegistry.CORRODED, 4 * 20, Math.max(1, this.corrosive - 1)));
		            } else {
		            	((LivingEntity)par1Entity).addEffect(new EffectInstance(FUREffectRegistry.CORRODED, 2 * 20, 1));
		            }
            	} else {
            		float local_difficulty = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
                	((LivingEntity)par1Entity).addEffect(new EffectInstance(FUREffectRegistry.CORRODED, 2 * 20 * (int)local_difficulty, 1));
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
    public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT p_213386_5_) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Mummy_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Mummy_Attack.get());
    	this.setHealth(this.getMaxHealth());
    	
    	return super.finalizeSpawn(p_213386_1_, difficulty, p_213386_3_, livingdata, p_213386_5_);
    }
       
    public int getSkin() {
        return this.entityData.get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
    	this.entityData.set(SKIN_TYPE, Integer.valueOf(skinType));
    }
	
    @Override
    public void addAdditionalSaveData(CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("Variant", this.getSkin());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);
        this.setSkin(nbt.getInt("Variant"));
    }
}
