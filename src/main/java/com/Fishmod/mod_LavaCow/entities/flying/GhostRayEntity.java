package com.Fishmod.mod_LavaCow.entities.flying;

import java.util.Random;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.tameable.GhostSwarmerEntity;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class GhostRayEntity extends FlyingMobEntity {
	public static final float[] SIZE = {1.0F, 1.4F, 1.8F, 2.2F};
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.defineId(GhostRayEntity.class, DataSerializers.INT);
	private static final DataParameter<Integer> SIZE_VARIANT = EntityDataManager.defineId(GhostRayEntity.class, DataSerializers.INT);
	
	public GhostRayEntity(EntityType<? extends GhostRayEntity> p_i48549_1_, World worldIn) {
		super(p_i48549_1_, worldIn);
	}
	
	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.0D));
		this.goalSelector.addGoal(5, new FlyingMobEntity.AIRandomFly(this));
	}
	
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.03D)
        		.add(Attributes.MAX_HEALTH, FURConfig.GhostRay_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, 0.0D)
        		.add(Attributes.FLYING_SPEED, 0.03D);
    }
    
    @Override
    protected void defineSynchedData() {
    	super.defineSynchedData();
    	this.getEntityData().define(SKIN_TYPE, Integer.valueOf(0));
    	this.getEntityData().define(SIZE_VARIANT, Integer.valueOf(this.getRandom().nextInt(GhostRayEntity.SIZE.length)));
    }	
    
    public static boolean checkGhostRaySpawnRules(EntityType<? extends GhostRayEntity> p_223316_0_, IWorld p_223316_1_, SpawnReason p_223316_2_, BlockPos p_223316_3_, Random p_223316_4_) {
    	BlockPos ground = p_223316_1_.getHeightmapPos(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, p_223316_3_);
    	boolean flag = (p_223316_1_ == World.END) ? ((ground.getY() == 0) && (p_223316_1_.canSeeSky(p_223316_3_))) : p_223316_4_.nextInt(10) == 0;   	
    	return flag && FlyingMobEntity.checkFlyerSpawnRulesNoRestriction(p_223316_0_, p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_);
    }
    
    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    @Override
    public int getMaxSpawnClusterSize() {
       return 1;
    }
    
	@Override
    public void tick() {
    	this.noPhysics = true;
    	super.tick();
        this.noPhysics = false;
        this.setNoGravity(true);
	}
    
    public float getScale() {
        return GhostRayEntity.SIZE[this.getSize()];
    }
    
    @Override
    protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
    	return p_213348_2_.height * 0.7F;
    }
   
	/**
	* Called when the entity is attacked.
	*/
    @Override
	public boolean hurt(DamageSource source, float amount) {
    	if(source.getDirectEntity() != null && source.getDirectEntity() instanceof LivingEntity)
    		((LivingEntity)source.getDirectEntity()).addEffect(new EffectInstance(Effects.WEAKNESS, 6 * 20, 2));
       
    	return super.hurt(source, amount);
	}
    
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData entityLivingData, @Nullable CompoundNBT p_213386_5_) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.GhostRay_Health.get());
    	this.setHealth(this.getMaxHealth());
    	
    	if (SpawnUtil.getRegistryKey(worldIn.getBiome(this.blockPosition())).equals(Biomes.SOUL_SAND_VALLEY)) {
		   this.setSkin(1);
    	} else if (BiomeDictionary.getTypes(SpawnUtil.getRegistryKey(worldIn.getBiome(this.blockPosition()))).contains(Type.END)) {
 		   this.setSkin(2);
 		   if (p_213386_3_ != SpawnReason.SPAWN_EGG) {
 			   this.moveTo(this.blockPosition().above(70), 0.0F, 0.0F);
 		   }
     	}
    	
    	if (this.getSkin() != 2) {
	    	if (this.random.nextFloat() < 0.01F * (float)FURConfig.pSpawnRate_GhostSwarmer.get()) {
		    	for (int i = 0; i < 2 + this.random.nextInt(2); ++i) {
		    		if (this.level instanceof ServerWorld) {
			    		BlockPos blockpos = this.blockPosition().offset(-6 + this.getRandom().nextInt(12), 0, -6 + this.getRandom().nextInt(12));
			    		GhostSwarmerEntity entity = SpawnUtil.trySpawnEntity(FUREntityRegistry.GHOSTSWARMER, ((ServerWorld) this.level), blockpos);
			    		
			    		if (entity != null) {
					        entity.setOwnerUUID(this.getUUID());
					        entity.doSitCommand(null);
					        entity.doFollowCommand(null);
			    		}
		    		}
		    	}
	    	}
    	}
    	
    	return super.finalizeSpawn(worldIn, difficulty, p_213386_3_, entityLivingData, p_213386_5_);
    }
    
	public int getSkin() {
   		return this.getEntityData().get(SKIN_TYPE).intValue();
	}

	public void setSkin(int skinType) {
   		this.getEntityData().set(SKIN_TYPE, Integer.valueOf(skinType));
	}
   
	public int getSize() {
	   return this.getEntityData().get(SIZE_VARIANT).intValue();
	}

	public void setSize(int size) {
	   this.getEntityData().set(SIZE_VARIANT, Integer.valueOf(size));
	}

	/**
	* (abstract) Protected helper method to read subclass entity data from NBT.
	*/
	@Override
	public void readAdditionalSaveData(CompoundNBT compound) {
       super.readAdditionalSaveData(compound);
       this.setSkin(compound.getInt("Variant"));
       this.setSize(compound.getInt("Size"));
	}

	/**
    * (abstract) Protected helper method to write subclass entity data to NBT.
    */
	@Override
	public void addAdditionalSaveData(CompoundNBT compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("Variant", getSkin());
		compound.putInt("Size", this.getSize());
	}
	
	@Override
	public int getAmbientSoundInterval() {
		return 1000;
	}
	
	public SoundCategory getSoundCategory() {
		return SoundCategory.NEUTRAL;
	}

	protected SoundEvent getAmbientSound() {
		return FURSoundRegistry.GHOSTRAY_AMBIENT;
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return FURSoundRegistry.BANSHEE_HURT;
	}

	protected SoundEvent getDeathSound() {
		return FURSoundRegistry.GHOSTRAY_DEATH;
	}
	
    @Override
    public boolean fireImmune() {
        return (this.getSkin() == 1) || super.fireImmune();
    }

	/**
	* Returns the volume for the sounds this mob makes.
	*/
	protected float getSoundVolume() {
		return 0.5F;
	}
}
