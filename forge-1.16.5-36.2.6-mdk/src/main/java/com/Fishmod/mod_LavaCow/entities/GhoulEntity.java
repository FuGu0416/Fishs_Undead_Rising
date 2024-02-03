package com.Fishmod.mod_LavaCow.entities;

import java.util.Random;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.ai.EntityAIPickupMeat;
import com.Fishmod.mod_LavaCow.entities.ai.FURMeleeAttackGoal;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.CreatureEntity;
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
import net.minecraft.entity.ai.goal.FleeSunGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
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
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class GhoulEntity extends MonsterEntity implements IAggressive {
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.defineId(GhoulEntity.class, DataSerializers.INT);
	public static final int ATTACK_TIMER = 12;
	private int attackTimer = 0;
	
	
	public GhoulEntity(EntityType<? extends GhoulEntity> p_i48549_1_, World worldIn) {
        super(p_i48549_1_, worldIn);
    }
	
	@Override
    protected void registerGoals() {
		this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(4, new AttackGoal(this));       
        if(!FURConfig.SunScreen_Mode.get())this.goalSelector.addGoal(5, new FleeSunGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers()); 
    	this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 0, true, true, (p_210136_0_) -> {
    		return p_210136_0_ instanceof LivingEntity 
    				&& ((LivingEntity)p_210136_0_).attackable() 
    				&& ((LivingEntity)p_210136_0_).getHealth() <= ((LivingEntity)p_210136_0_).getMaxHealth() * ((float)FURConfig.Ghoul_targetHPThreshold.get() / 100.0F);
    	}));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(4, new EntityAIPickupMeat<>(this, ItemEntity.class, true));
    }
    
    public static AttributeModifierMap.MutableAttribute createAttributes() {    	
        return MobEntity.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.266D)
        		.add(Attributes.FOLLOW_RANGE, 32.0D)
        		.add(Attributes.MAX_HEALTH, FURConfig.Ghoul_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, FURConfig.Ghoul_Attack.get())
        		.add(Attributes.ARMOR, 2.0D);
    }   
    
    public static boolean checkGhoulSpawnRules(EntityType<? extends GhoulEntity> p_223316_0_, IWorld p_223316_1_, SpawnReason p_223316_2_, BlockPos p_223316_3_, Random p_223316_4_) {
        return MonsterEntity.checkMonsterSpawnRules(p_223316_0_, (IServerWorld) p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_);//SpawnUtil.isAllowedDimension(this.dimension);
    }
    
    @Override
    protected void defineSynchedData() {
    	super.defineSynchedData();
        this.getEntityData().define(SKIN_TYPE, Integer.valueOf(0));
    }    
    
    @Override
    public double getMyRidingOffset() {
        return -0.25D;
    }
	
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void aiStep() {
        super.aiStep();     
    }
	
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void tick() {       
    	if(this.getAttackTimer() > 0) {
    		--this.attackTimer;
    	}
    	
    	if (!FURConfig.SunScreen_Mode.get() && this.isSunBurnTick()) {
    		this.setSecondsOnFire(8);
        }
    	
        super.tick();
    }
    
    @Override
    public boolean doHurtTarget(Entity entityIn) {
        boolean flag = super.doHurtTarget(entityIn);

        if (flag) {
            float f = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();

            if (this.getMainHandItem().isEmpty() && this.isOnFire() && this.random.nextFloat() < f * 0.3F) {
            	entityIn.setSecondsOnFire(2 * (int)f);
            }
            
            if (entityIn instanceof LivingEntity 
    				&& ((LivingEntity)entityIn).attackable() 
    				&& ((LivingEntity)entityIn).getHealth() <= ((LivingEntity)entityIn).getMaxHealth() * ((float)FURConfig.Ghoul_targetHPThreshold.get() / 100.0F)) {
            	this.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 4 * 20, 2));
            }
            
            if (entityIn instanceof LivingEntity && !(((LivingEntity)entityIn).deathTime > 0)) {
            	this.heal(2.0F);
            }
        }

        return flag;
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT p_213386_5_) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Ghoul_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Ghoul_Attack.get());
    	this.setHealth(this.getMaxHealth());
    	
    	if (BiomeDictionary.getTypes(SpawnUtil.getRegistryKey(p_213386_1_.getBiome(this.blockPosition()))).contains(Type.COLD)) {
    		// variant 2, 4
    		this.setSkin(2 + this.getRandom().nextInt(1) * 2);		   	
    	} else if (BiomeDictionary.getTypes(SpawnUtil.getRegistryKey(p_213386_1_.getBiome(this.blockPosition()))).contains(Type.DENSE)) {
    		// variant 1, 3
    		this.setSkin(1 + (this.getRandom().nextInt(1) * 2));		   	
    	} else {
    		// variant 0, 1
    		this.setSkin(this.getRandom().nextInt(2));	
    	}
 	   	   
 	   	return super.finalizeSpawn(p_213386_1_, difficulty, p_213386_3_, livingdata, p_213386_5_);
 	}
    
    /**
     * Called when the mob's health reaches 0.
     */
    @Override
    public void die(DamageSource cause) {
       super.die(cause);
    }
    
    public int getSkin() {
        return this.getEntityData().get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
        this.getEntityData().set(SKIN_TYPE, Integer.valueOf(skinType));
    }
    
    @Override
	public int getAttackTimer() {
		return this.attackTimer;
	}
    
	@Override
	public void setAttackTimer(int i) {
		this.attackTimer = i;
	}
    
    /**
     * Handler for {@link World#setEntityState}
     */
	@Override
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
		switch(id) {
			case 5:
				this.setAttackTimer(ATTACK_TIMER);
				break;
			default:
				super.handleEntityEvent(id);
				break;
		}
    }
    
	@Override
    protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
        	return this.getBbHeight() * 0.8F;
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
    protected SoundEvent getAmbientSound() {
        return FURSoundRegistry.FOGLET_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FURSoundRegistry.FOGLET_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.FOGLET_DEATH;
    }

	@Override
    protected void playStepSound(BlockPos pos, BlockState state) {
	    this.playSound(SoundEvents.ZOMBIE_STEP, 0.15F, 1.0F);
	}

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.UNDEAD;
    }
    
    static class AttackGoal extends FURMeleeAttackGoal {
        public AttackGoal(CreatureEntity p_i46676_1_) {
           super(p_i46676_1_, 1.3D, true);
        }

    	protected int atkTimerMax() {
    		return ATTACK_TIMER;
    	}
    	
    	protected int atkTimerHit() {
    		return 5;
    	}
    	
    	protected byte atkTimerEvent() {
    		return (byte) 5;
    	}
	}
}
