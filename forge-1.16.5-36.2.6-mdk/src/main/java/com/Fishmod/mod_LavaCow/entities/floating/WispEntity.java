package com.Fishmod.mod_LavaCow.entities.floating;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.ai.WispSwellGoal;
import com.Fishmod.mod_LavaCow.entities.flying.WarpedFireflyEntity;
import com.Fishmod.mod_LavaCow.init.FURParticleRegistry;

import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.Explosion;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WispEntity extends FloatingMobEntity {
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.defineId(WispEntity.class, DataSerializers.INT);
	private static final DataParameter<Integer> DATA_SWELL_DIR = EntityDataManager.defineId(WispEntity.class, DataSerializers.INT);
	private int oldSwell;
	private int swell;
	private int maxSwell = 30;
	   
	public WispEntity(EntityType<? extends FloatingMobEntity> p_i48549_1_, World worldIn) {
		super(p_i48549_1_, worldIn);
	}
	
    @Override
    protected void registerGoals() {
    	super.registerGoals();        
    	this.goalSelector.addGoal(2, new WispSwellGoal(this));
    	this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, WarpedFireflyEntity.class, 6.0F, 1.0D, 1.2D));
		this.goalSelector.addGoal(3, new FloatingMobEntity.AIChargeAttack());
    }

    @Override
    protected void applyEntityAI() {
    	this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    	this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    	this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, ZombieEntity.class, true));
    }
    
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.2D)
        		.add(Attributes.FOLLOW_RANGE, 16.0D)
        		.add(Attributes.MAX_HEALTH, FURConfig.Wisp_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, 1.0D);
    }
    
    @Override
    protected void defineSynchedData() {
    	super.defineSynchedData();
    	this.getEntityData().define(DATA_SWELL_DIR, -1);
    	this.getEntityData().define(SKIN_TYPE, Integer.valueOf(this.getRandom().nextInt(3)));
    }
    
    public void tick() {
        if (this.isAlive()) {
           this.oldSwell = this.swell;

           int i = this.getSwellDir();
           if (i > 0 && this.swell == 0) {
              this.playSound(SoundEvents.CREEPER_PRIMED, 1.0F, 0.5F);
           }

           this.swell += i;
           if (this.swell < 0) {
              this.swell = 0;
           }

           if (this.swell >= this.maxSwell) {
              this.swell = this.maxSwell;
              this.explodeWisp();
           }
           
           if (this.isGastly() && this.getSkin() != 3) {
        	   this.setSkin(3);
           }
        }

        super.tick();
	}
    
    private void explodeWisp() {
        if (!this.level.isClientSide) {
           this.dead = true;
           this.level.explode(this, this.getX(), this.getY(), this.getZ(), FURConfig.Wisp_ExplosionPower.get().floatValue(), net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this), Explosion.Mode.NONE);
           this.remove();
        }

     }
       
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT p_213386_5_) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.SeaHag_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.SeaHag_Attack.get());
    	this.setHealth(this.getMaxHealth());

    	if (SpawnUtil.getRegistryKey(worldIn.getBiome(this.blockPosition())).equals(Biomes.NETHER_WASTES)) {
		   this.setSkin(0);
    	} else if (SpawnUtil.getRegistryKey(worldIn.getBiome(this.blockPosition())).equals(Biomes.SOUL_SAND_VALLEY)) {
 		   this.setSkin(1);
     	} else if (SpawnUtil.getRegistryKey(worldIn.getBiome(this.blockPosition())).equals(Biomes.BASALT_DELTAS)) {
  		   this.setSkin(2);
      	}
    	
    	this.setDeltaMovement(this.getDeltaMovement().add(0.0D, 0.5D, 0.0D));
    	
    	return super.finalizeSpawn(worldIn, difficulty, p_213386_3_, livingdata, p_213386_5_);
    }
    
    @Override
    @Nullable
    protected IParticleData ParticleType() { 
    	switch(this.getSkin()) {
			case 1:
				return ParticleTypes.FLAME;
			case 2:
				return ParticleTypes.POOF;
			case 3:
				return FURParticleRegistry.GHOST_FLAME;
			case 0:
			default:
				return ParticleTypes.SOUL_FIRE_FLAME;			
		}
    }
    
    @OnlyIn(Dist.CLIENT)
    public float getSwelling(float p_70831_1_) {
       return MathHelper.lerp(p_70831_1_, (float)this.oldSwell, (float)this.swell) / (float)(this.maxSwell - 2);
    }

    public int getSwellDir() {
       return this.entityData.get(DATA_SWELL_DIR);
    }

    public void setSwellDir(int p_70829_1_) {
       this.entityData.set(DATA_SWELL_DIR, p_70829_1_);
    }
    
    public int getSkin() {
        return this.getEntityData().get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
        this.getEntityData().set(SKIN_TYPE, Integer.valueOf(skinType));
    }
    
    @Override
    protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
        return p_213348_2_.height * 0.5F;
    }
    
    private boolean isGastly() {
    	String s = TextFormatting.stripFormatting(this.getName().getString());
        return s != null && s.toLowerCase().contains("gastly");
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return null;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.FURNACE_FIRE_CRACKLE;
    }
    
    protected SoundEvent getSpellSound() {
        return null;
    }
    
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Fuse", 99)) {
            this.maxSwell = compound.getShort("Fuse");
        }
        this.setSkin(compound.getInt("Variant"));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putShort("Fuse", (short)this.maxSwell);
        compound.putInt("Variant", getSkin());
    }
	
    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.UNDEAD;
    }
}
