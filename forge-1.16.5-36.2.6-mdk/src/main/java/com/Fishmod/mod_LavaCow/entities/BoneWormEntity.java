package com.Fishmod.mod_LavaCow.entities;

import java.util.Random;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.projectiles.AcidJetEntity;
import com.Fishmod.mod_LavaCow.entities.projectiles.FlameJetEntity;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;

import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.FleeSunGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BoneWormEntity extends MonsterEntity  implements IRangedAttackMob {
	private static final DataParameter<Boolean> UNDERGROUND = EntityDataManager.defineId(BoneWormEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.defineId(BoneWormEntity.class, DataSerializers.INT);
	public double LocationFix;
	public int attackTimer[] = {0, 0};
	public int diggingTimer[] = {0, 0};
	private RangedAttackGoal range_atk;
	private AvoidEntityGoal<PlayerEntity> avoid_player;
	private int avoid_cooldown;
	
	public BoneWormEntity(EntityType<? extends BoneWormEntity> p_i48549_1_, World worldIn) {
        super(p_i48549_1_, worldIn);
        this.LocationFix = 0.0D;
    }
	
	@Override
    protected void registerGoals() {
    	this.range_atk = new RangedAttackGoal(this, 1.0D, 40, 60, 12.0F);
    	this.avoid_player = new AvoidEntityGoal<PlayerEntity>(this, PlayerEntity.class, 10.0F, 1.0D, 1.2D);
    	
        this.goalSelector.addGoal(0, this.range_atk);
        if(!FURConfig.SunScreen_Mode.get())this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
    	this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    	this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }
    
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.25D)
        		.add(Attributes.FOLLOW_RANGE, 16.0D)
        		.add(Attributes.MAX_HEALTH, FURConfig.BoneWorm_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, FURConfig.BoneWorm_Attack.get())
        		.add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }
    
    public static boolean checkBoneWormSpawnRules(EntityType<? extends BoneWormEntity> p_223316_0_, IWorld p_223316_1_, SpawnReason p_223316_2_, BlockPos p_223316_3_, Random p_223316_4_) {
        return MonsterEntity.checkMonsterSpawnRules(p_223316_0_, (IServerWorld) p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_);//SpawnUtil.isAllowedDimension(this.dimension);
    }
    
    @Override
    protected void defineSynchedData() {
    	super.defineSynchedData();
        this.getEntityData().define(UNDERGROUND, false);
        this.getEntityData().define(SKIN_TYPE, Integer.valueOf(0));
    }
    
    private boolean isWalking() {    	
    	return Math.abs(this.getX() - this.xOld) > 0.05D || Math.abs(this.getY() - this.yOld) > 0.05D || Math.abs(this.getZ() - this.zOld) > 0.05D;
    }
    
    private boolean isDigging() {
    	return this.diggingTimer[0] != 0 || this.diggingTimer[1] != 0;
    }
    
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void aiStep() {
        super.aiStep();
        
        BlockState state = this.level.getBlockState(this.blockPosition().below());
        if (this.isWalking()) {
	        if (state.getMaterial().isSolid()) {
	            if (this.level.isClientSide()) {
	            	for (int i = 0; i < 4; i++)
	            		this.level.addParticle(new BlockParticleData(ParticleTypes.BLOCK, state).setPos(this.blockPosition().below()), this.getX() + (double) (this.getRandom().nextFloat() * this.getBbWidth() * 2.0F) - (double) this.getBbWidth(), this.getY() + (double) (this.getRandom().nextFloat() * this.getBbWidth() * 2.0F) - (double) this.getBbWidth(), this.getZ() + (double) (this.getRandom().nextFloat() * this.getBbWidth() * 2.0F) - (double) this.getBbWidth(), this.getRandom().nextGaussian() * 0.02D, this.getRandom().nextGaussian() * 0.02D, this.getRandom().nextGaussian() * 0.02D);
	            }
	        }
	        
	        if (this.tickCount % 10 == 0) {
	            this.playSound(SoundEvents.SAND_BREAK, 1, 0.5F);
	        }	        
        }
        
        if (!this.level.isClientSide()) {
	        if (this.LocationFix > 0 && !this.isUnderGround() && !this.isDigging()) {
	        	this.clearFire();
	        	this.diggingTimer[0] = 30;
	        	this.setUnderGround(true);
	        	this.level.broadcastEntityEvent(this, (byte)6);
	        	this.playSound(FURSoundRegistry.BONEWORM_BURROW, 0.25F, 1.0F);
	        	this.clearFire();
	        } else if (this.LocationFix <= 1.5D && this.isUnderGround() && !this.isDigging()) {
	        	this.diggingTimer[1] = 20;
	        	this.setUnderGround(false);
	        	this.level.broadcastEntityEvent(this, (byte)7);
	        	this.playSound(FURSoundRegistry.BONEWORM_BURROW, 0.25F, 1.0F);
	        }
        }
        
		if (this.isWalking() && state.getMaterial().isSolid()) {
			if (this.LocationFix <= 3.5D)
				this.LocationFix += 0.125D;
			
			if (this.level.isClientSide())
				for(int i = 0; i < 4; i++)
					this.level.addParticle(new BlockParticleData(ParticleTypes.BLOCK, state).setPos(this.blockPosition().below()), this.getX() + (double) (this.getRandom().nextFloat() * this.getBbWidth() * 2.0F) - (double) this.getBbWidth(), this.getY() + (double) (this.getRandom().nextFloat() * this.getBbWidth() * 2.0F) - (double) this.getBbWidth(), this.getZ() + (double) (this.getRandom().nextFloat() * this.getBbWidth() * 2.0F) - (double) this.getBbWidth(), this.getRandom().nextGaussian() * 0.02D, this.getRandom().nextGaussian() * 0.1D, this.getRandom().nextGaussian() * 0.02D);
			EntitySize.scalable(this.getBbWidth(), Math.max(2.0F - (float)this.LocationFix, 0.5F));
		} else if (this.LocationFix > 0.0D && state.getMaterial().isSolid()) {
			this.LocationFix -= 0.125D;
			if (this.level.isClientSide())
				for(int i = 0; i < 4; i++)
					this.level.addParticle(new BlockParticleData(ParticleTypes.BLOCK, state).setPos(this.blockPosition().below()), this.getX() + (double) (this.getRandom().nextFloat() * this.getBbWidth() * 2.0F) - (double) this.getBbWidth(), this.getY() + (double) (this.getRandom().nextFloat() * this.getBbWidth() * 2.0F) - (double) this.getBbWidth(), this.getZ() + (double) (this.getRandom().nextFloat() * this.getBbWidth() * 2.0F) - (double) this.getBbWidth(), this.getRandom().nextGaussian() * 0.02D, this.getRandom().nextGaussian() * 0.1D, this.getRandom().nextGaussian() * 0.02D);
			EntitySize.scalable(this.getBbWidth(), Math.max(2.0F - (float)this.LocationFix, 0.5F));
		}
		
		if (!this.level.isClientSide()) {
			if (this.avoid_cooldown == 0) {
				this.goalSelector.addGoal(0, this.range_atk);
				this.goalSelector.removeGoal(this.avoid_player);
				this.avoid_cooldown = -1;
			}

			if (this.avoid_cooldown > 0)
				this.avoid_cooldown--;
		}
    }
    
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void tick() {
        for(int i = 0 ; i < 2; i++) {
	    	if (this.attackTimer[i] > 0) {
	            --this.attackTimer[i];
	         }
	    	
	    	if (this.diggingTimer[i] > 0) {
	            --this.diggingTimer[i];
	         }
        }
        
        if (this.getTarget() != null && this.getSensing().canSee(this.getTarget()) && this.getAttackTimer(0) == 7 && this.deathTime <= 0 && this.LocationFix == 0) {
        	this.spit(this.getTarget());
        }
    	        
    	if (!FURConfig.SunScreen_Mode.get() && this.isSunBurnTick()) {
    		this.setSecondsOnFire(8);
        }
    	
    	if(this.isWalking())
    		this.clearFire();
    	
        super.tick();
    }
    
    @OnlyIn(Dist.CLIENT)
    public double getLocationFix() {
    	return this.LocationFix;
    }
    
    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean hurt(DamageSource source, float amount) {
    	if (this.LocationFix > 3.0D) {
            return false;
        } else {
        	return super.hurt(source, amount);
        }
    }
    
    private void spit(LivingEntity target) {
    	ProjectileItemEntity entitysnowball;
    	SoundEvent sound;
    	
        if(this.getSkin() == 1) {
        	entitysnowball = new FlameJetEntity(FUREntityRegistry.FLAMEJET, this, this.level);
        	sound = SoundEvents.BLAZE_SHOOT;
        } else {
        	entitysnowball = new AcidJetEntity(FUREntityRegistry.ACIDJET, this, this.level);
        	sound = FURSoundRegistry.BONEWORM_ATTACK;
        }
        
        double d0 = target.getY() + (double)target.getEyeHeight() - 1.100000023841858D;
        double d1 = target.getX() - this.getX();
        double d2 = d0 - entitysnowball.getY();
        double d3 = target.getZ() - this.getZ();
        float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F;
        entitysnowball.shoot(d1, d2 + (double)f, d3, 1.6F, 1.0F);
        this.playSound(sound, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level.addFreshEntity(entitysnowball);
        
    	if(target instanceof PlayerEntity)
    		this.setRunning(100);
    }
    
	@Override
	public void performRangedAttack(LivingEntity p_82196_1_, float p_82196_2_) {		
    	this.attackTimer[0] = 15;
    	this.level.broadcastEntityEvent(this, (byte)4);
	}
    
    public void setRunning(int CD) {
    	this.avoid_player = new AvoidEntityGoal<PlayerEntity>(this, PlayerEntity.class, 10.0F, 1.0D, 1.2D);
    	
    	this.goalSelector.removeGoal(this.range_atk);
		this.goalSelector.addGoal(1, this.avoid_player);
		this.avoid_cooldown = CD;
    }

    @Override
    public boolean doHurtTarget(Entity entityIn) {
        boolean flag = super.doHurtTarget(entityIn);

        if (flag) {
        	this.attackTimer[1] = 16;
        	this.level.broadcastEntityEvent(this, (byte)5);
        }

        return flag;
    }
    
    @Override
	protected void dropCustomDeathLoot(DamageSource p_213333_1_, int p_213333_2_, boolean p_213333_3_) {
		super.dropCustomDeathLoot(p_213333_1_, p_213333_2_, p_213333_3_);
        Entity entity = p_213333_1_.getEntity();
        
        if (entity instanceof CreeperEntity) {
           CreeperEntity creeperentity = (CreeperEntity)entity;
           if (creeperentity.canDropMobsSkull()) {
              creeperentity.increaseDroppedSkulls();
              this.spawnAtLocation(Items.SKELETON_SKULL);
           }
        }
	}
	
    public int getSkin() {
        return this.getEntityData().get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
        this.getEntityData().set(SKIN_TYPE, Integer.valueOf(skinType));
    }
        
    public int getAttackTimer(int i) {
       return this.attackTimer[i];
    }
    
    public boolean isUnderGround() {
        return this.getEntityData().get(UNDERGROUND).booleanValue();
    }

    public void setUnderGround(boolean isUnderGround) {
        this.getEntityData().set(UNDERGROUND, isUnderGround);
    }
    
    @Override
    public boolean fireImmune() {
        return (this.getSkin() == 1) || super.fireImmune();
    }
    
    /**
     * Handler for {@link World#setEntityState}
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id)
    {
    	if (id == 4) {
            this.attackTimer[0] = 15;
        } else if (id == 5) {
    		this.attackTimer[1] = 16;
    	} else if (id == 6) {
    		this.diggingTimer[0] = 30;
    	} else if (id == 7) {
    		this.diggingTimer[1] = 20;
    	} else {
            super.handleEntityEvent(id);
        }
    }
    
    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT p_213386_5_) {
    	this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.BoneWorm_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.BoneWorm_Attack.get());
    	this.setHealth(this.getMaxHealth());
    	
    	if(SpawnUtil.getRegistryKey(p_213386_1_.getBiome(this.blockPosition())).equals(Biomes.SOUL_SAND_VALLEY)) {
    	   this.setSkin(1);
    	}
          
    	return super.finalizeSpawn(p_213386_1_, difficulty, p_213386_3_, livingdata, p_213386_5_);
    }

    @Override
    protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
        return p_213348_2_.height * 0.8F;
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return FURSoundRegistry.BONEWORM_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FURSoundRegistry.BONEWORM_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.BONEWORM_DEATH;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.SPIDER_STEP;
    }

	@Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.UNDEAD;
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
}
