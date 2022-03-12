package com.Fishmod.mod_LavaCow.entities;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.init.FUREffectRegistry;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.CreatureAttribute;
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
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class FogletEntity extends MonsterEntity implements IAggressive {
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.defineId(FogletEntity.class, DataSerializers.INT);
	private static final DataParameter<Byte> CLIMBING = EntityDataManager.defineId(FogletEntity.class, DataSerializers.BYTE);
	private static final DataParameter<Byte> HANGING = EntityDataManager.defineId(FogletEntity.class, DataSerializers.BYTE);
	private static final DataParameter<Byte> CASTING = EntityDataManager.defineId(FogletEntity.class, DataSerializers.BYTE);
	private int attackTimer = 0;
	protected int spellTicks;
	
	public FogletEntity(EntityType<? extends FogletEntity> p_i48549_1_, World worldIn)
    {
        super(p_i48549_1_, worldIn);
    }
	
	@Override
    protected void registerGoals()
    {
        this.goalSelector.addGoal(2, new AICastingApell());
        this.goalSelector.addGoal(3, new FogletEntity.AIUseSpell());
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, true));
        /*if(!Modconfig.SunScreen_Mode)*/this.goalSelector.addGoal(5, new FleeSunGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI()
    {
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, TurtleEntity.class, 10, true, false, TurtleEntity.BABY_ON_LAND_SELECTOR));
    }
    
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.25D)
        		.add(Attributes.FOLLOW_RANGE, 16.0D)
        		.add(Attributes.MAX_HEALTH, FURConfig.Foglet_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, FURConfig.Foglet_Attack.get());
    }
    
    public static boolean checkFogletSpawnRules(EntityType<? extends FogletEntity> p_223316_0_, IWorld p_223316_1_, SpawnReason p_223316_2_, BlockPos p_223316_3_, Random p_223316_4_) {
        return MonsterEntity.checkMonsterSpawnRules(p_223316_0_, (IServerWorld) p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_);//SpawnUtil.isAllowedDimension(this.dimension);
    }
    
    @Override
    protected void defineSynchedData() {
    	super.defineSynchedData();
        this.getEntityData().define(SKIN_TYPE, Integer.valueOf(0));
        this.getEntityData().define(CLIMBING, Byte.valueOf((byte)0));
        this.getEntityData().define(HANGING, Byte.valueOf((byte)0));
        this.getEntityData().define(CASTING, Byte.valueOf((byte)0));
    }
    
    public boolean isSpellcasting()
    {
    	return (((Byte)this.getEntityData().get(CASTING)).byteValue() & 1) != 0;
    }
    
    @OnlyIn(Dist.CLIENT)
    public boolean isSpellcastingC()
    {
    	return (((Byte)this.getEntityData().get(CASTING)).byteValue() & 1) != 0;
    }
    
    protected int getSpellTicks()
    {
        return this.spellTicks;
    }
	
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void aiStep()
    {
        super.aiStep();
        
        if(this.getIsHanging()) {
        	this.setDeltaMovement(Vector3d.ZERO);

	        if(!this.isPassenger()) {
		        if(this.level.canSeeSky(new BlockPos(this.getX(), this.getY(), this.getZ()))) {
		        	this.setIsHanging(false);
		        }
		        
		        List<Entity> list = this.level.getEntities(this, this.getBoundingBox().expandTowards(2.0D, 35.0D, 2.0D));
		        
	        	for (Entity entity1 : list)
	        	{
	        		if (entity1.getY() < this.getY() && ((entity1 instanceof PlayerEntity && !((PlayerEntity)entity1).isCreative()) || entity1 instanceof AbstractVillagerEntity))
	        		{
	        			this.setTarget((LivingEntity) entity1);
	        			this.setIsHanging(false);
	        			break;
	        		}
	        	}  
	        }
    	}      
    }
	
	@Override
    public boolean causeFallDamage(float distance, float damageMultiplier) {
    	if(this.getTarget() != null) {
    		return false;
    	}  	
    	return super.causeFallDamage(distance, damageMultiplier);
    }
    
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void tick()
    {
        if (this.spellTicks > 0) {
            --this.spellTicks;
            
            if(this.getType().equals(FUREntityRegistry.IMP) && this.tickCount % 10 == 0 && this.spellTicks < 80) {
            	List<Entity> list = this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(4.0D));

            	for (Entity entity1 : list) {                        
        			if (!entity1.isOnFire() && !entity1.fireImmune() && this.getRandom().nextFloat() < 0.4F)
        				entity1.setSecondsOnFire(3);
            	}         		
            }
        }
    	
    	if (!FURConfig.SunScreen_Mode.get() && this.isSunBurnTick()) {
    		this.setSecondsOnFire(8);
        }
    	
        super.tick();
    }
    
    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean hurt(DamageSource source, float amount)
    {
    	if (super.hurt(source, amount))
        {
            LivingEntity LivingEntity = this.getTarget();
        	
            if (LivingEntity == null && source.getDirectEntity() instanceof LivingEntity)
            {
                LivingEntity = (LivingEntity)source.getDirectEntity();
            }
            
            if(this.getIsClimbing()) {
            	this.setIsClimbing(false);
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean doHurtTarget(Entity entityIn)
    {
        boolean flag = super.doHurtTarget(entityIn);

        if (flag)
        {
            float f = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
            if (this.getMainHandItem().isEmpty() && this.isOnFire() && this.random.nextFloat() < f * 0.3F) {
            	entityIn.setSecondsOnFire(2 * (int)f);
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
 	   if(BiomeDictionary.getTypes(SpawnUtil.getRegistryKey(p_213386_1_.getBiome(this.blockPosition()))).contains(Type.JUNGLE)) {
 		   this.setSkin(1);
 		   this.goalSelector.addGoal(1, new AIClimbimgTree());
 	   } else if(this.getType().equals(FUREntityRegistry.IMP)) {
 		  this.setSkin(2);
 	   }
 	   
 	   return super.finalizeSpawn(p_213386_1_, difficulty, p_213386_3_, livingdata, p_213386_5_);
 	}
    
    /**
     * Called when the mob's health reaches 0.
     */
    @Override
    public void die(DamageSource cause) {
       super.die(cause);
       
       int looting = net.minecraftforge.common.ForgeHooks.getLootingLevel(this, cause.getDirectEntity(), cause);
       int chance = this.random.nextInt(2) + this.random.nextInt(1 + looting);
       if(!this.level.isClientSide() && this.getSkin() == 1) {			
			for (int amount = 0; amount < chance; ++amount)
				this.spawnAtLocation(new ItemStack(FURItemRegistry.FOUL_BRISTLE), 0.0F);
       }
    }
    
    public int getSkin()
    {
        return this.getEntityData().get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType)
    {
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
    public void handleEntityEvent(byte id)
    {
        if (id == 10) {
        	this.spellTicks = 100;
        }
        else {
        	this.spellTicks = 0;

            super.handleEntityEvent(id);
        }
    }
    
	@Override
    protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
        if(getIsHanging())
        	return this.getBbHeight() * 0.0F;
        else
        	return this.getBbHeight() * 0.8F;
    }
    
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.spellTicks = compound.getInt("SpellTicks");
        this.setSkin(compound.getInt("Variant"));
        this.getEntityData().set(HANGING, Byte.valueOf(compound.getByte("Hanging")));
        this.getEntityData().set(CLIMBING, Byte.valueOf(compound.getByte("Climbing")));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("SpellTicks", this.spellTicks);
        compound.putInt("Variant", getSkin());
        compound.putByte("Hanging", ((Byte)this.getEntityData().get(HANGING)).byteValue());
        compound.putByte("Climbing", ((Byte)this.getEntityData().get(CLIMBING)).byteValue());
    }
    
    public boolean getIsHanging()
    {
        return (((Byte)this.getEntityData().get(HANGING)).byteValue() & 1) != 0;
    }
    
    public boolean getIsClimbing()
    {
        return (((Byte)this.getEntityData().get(CLIMBING)).byteValue() & 1) != 0;
    }
    
    public void setIsClimbing(boolean isClimbing)
    {
        byte b0 = ((Byte)this.getEntityData().get(CLIMBING)).byteValue();

        if (isClimbing)
        {
            this.getEntityData().set(CLIMBING, Byte.valueOf((byte)(b0 | 1)));
        }
        else
        {
            this.getEntityData().set(CLIMBING, Byte.valueOf((byte)(b0 & -2)));
        }
    }
    
    public void setIsHanging(boolean isHanging)
    {
        byte b0 = ((Byte)this.getEntityData().get(HANGING)).byteValue();

        if (isHanging)
        {
            this.getEntityData().set(HANGING, Byte.valueOf((byte)(b0 | 1)));
        }
        else
        {
            this.getEntityData().set(HANGING, Byte.valueOf((byte)(b0 & -2)));
        }
    }
    
    public void setIsCasting(boolean isHanging)
    {
        byte b0 = ((Byte)this.getEntityData().get(CASTING)).byteValue();

        if (isHanging)
        {
            this.getEntityData().set(CASTING, Byte.valueOf((byte)(b0 | 1)));
        }
        else
        {
            this.getEntityData().set(CASTING, Byte.valueOf((byte)(b0 & -2)));
        }
    }
    
    public class AIClimbimgTree extends Goal
    {
        private BlockPos TreePos;
    	
    	public AIClimbimgTree() {
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
    	@Override
        public boolean canUse()
        {
            int i = MathHelper.floor(FogletEntity.this.getX());
            int j = MathHelper.floor(FogletEntity.this.getY());
            int k = MathHelper.floor(FogletEntity.this.getZ());
            BlockPos blockpos = new BlockPos(i, j, k);
            
            TreePos = null;
            
            for(int x = -1 ; x <= 1 ; x++)
            	for(int z = -1 ; z <= 1 ; z++) {
            		if(FogletEntity.this.level.getBlockState(blockpos.offset(x, 0, z)).getMaterial() == Material.WOOD) {
            			TreePos = new BlockPos(x, 0, z);
            			break;
            		}
            	}
            
            return !FogletEntity.this.isOnFire() && !FogletEntity.this.isAggressive() && !FogletEntity.this.level.canSeeSky(blockpos) && TreePos != null && FogletEntity.this.level.getBlockState(blockpos.above(2)).getMaterial() == Material.AIR;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
    	@Override
        public void start()
        {
            super.start();
            FogletEntity.this.setIsClimbing(true);
            FogletEntity.this.getNavigation().stop();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
    	@Override
        public void stop()
        {
            int i = MathHelper.floor(FogletEntity.this.getX());
            int j = MathHelper.floor(FogletEntity.this.getY());
            int k = MathHelper.floor(FogletEntity.this.getZ());
            BlockPos blockpos = new BlockPos(i, j, k);
            
        	super.stop();
            FogletEntity.this.setIsClimbing(false);
            FogletEntity.this.setDeltaMovement(0.0D, 1.0D, 0.0D);
            if(FogletEntity.this.level.getBlockState(blockpos.above(2)).getMaterial().isSolid()) {
            	FogletEntity.this.setIsHanging(true);
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
    	@Override
        public void tick()
        {
        	if(FogletEntity.this.getDeltaMovement().y < 0.0D)
        		FogletEntity.this.setDeltaMovement(0.0D, 0.05D, 0.0D);
        	if(TreePos != null) {
            	FogletEntity.this.yBodyRot = (TreePos.getX() * 270.0F + (float) Math.toDegrees(Math.atan(TreePos.getZ() / (TreePos.getX() + 0.0000001D)))) % 360.0F;
        	}
        }
    }
    
    public class AICastingApell extends Goal {

        public AICastingApell() {
        	this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        @Override
        public boolean canUse()
        {
            return FogletEntity.this.getSpellTicks() > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        @Override
        public void start()
        {
            super.start();
            FogletEntity.this.setIsCasting(true);
            FogletEntity.this.getNavigation().stop();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        @Override
        public void stop()
        {
            super.stop();
            FogletEntity.this.setIsCasting(false);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        @Override
        public void tick()
        {
            if (FogletEntity.this.getTarget() != null)
            {
                FogletEntity.this.getLookControl().setLookAt(FogletEntity.this.getTarget(), (float)FogletEntity.this.getMaxHeadYRot(), (float)FogletEntity.this.getMaxHeadXRot());
            }
        }
    }
    
    public class AIUseSpell extends Goal
    {
        protected int spellWarmup;
        protected int spellCooldown;

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        @Override
        public boolean canUse()
        {
            if (FogletEntity.this.getTarget() == null)
            {
                return false;
            }
            else if (FogletEntity.this.isSpellcasting())
            {
                return false;
            }
            else
            {
            	return FogletEntity.this.tickCount >= this.spellCooldown && FogletEntity.this.distanceTo(FogletEntity.this.getTarget()) < 3.0F;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        @Override
        public boolean canContinueToUse()
        {
            return FogletEntity.this.getTarget() != null && this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        @Override
        public void start()
        {
            this.spellWarmup = this.getCastWarmupTime();
            FogletEntity.this.spellTicks = this.getCastingTime();
            FogletEntity.this.level.broadcastEntityEvent(FogletEntity.this, (byte)10);
            this.spellCooldown = FogletEntity.this.tickCount + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();

            if (soundevent != null)
            {
                FogletEntity.this.playSound(soundevent, 1.0F, 1.0F);
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        @Override
        public void tick()
        {
            --this.spellWarmup;

            if (this.spellWarmup == 0)
            {
                this.castSpell();
                FogletEntity.this.playSound(FogletEntity.this.getSpellSound(), 1.0F, 1.0F);
                if(FogletEntity.this.getSkin() == 0)
                	FogletEntity.this.addEffect(new EffectInstance(Effects.INVISIBILITY, 3 * 20));
            }
        }

        protected void castSpell()
        {
        	AreaEffectCloudEntity entityareaeffectcloud = new AreaEffectCloudEntity(FogletEntity.this.level, FogletEntity.this.getX(), FogletEntity.this.getY() + 1.0D, FogletEntity.this.getZ());
            Effect effect;
            
            switch(FogletEntity.this.getSkin()) {
	            case 0:
	            	effect = Effects.BLINDNESS;
	                break;
	            case 1:
	            	effect = FUREffectRegistry.SOILED;
	                break;
	            case 2:
	            default:
	            	effect = Effects.HARM;
	                break;
            }
            
        	entityareaeffectcloud.setOwner(FogletEntity.this);
            entityareaeffectcloud.setRadius(4.0F);
            entityareaeffectcloud.setRadiusOnUse(-0.5F);
            entityareaeffectcloud.setDuration(120);
            entityareaeffectcloud.setWaitTime(10);
            entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / (float)entityareaeffectcloud.getDuration());
            if (FogletEntity.this.getSkin() == 0)
            	entityareaeffectcloud.setParticle(ParticleTypes.CLOUD);
            else if (FogletEntity.this.getSkin() == 1)
            	entityareaeffectcloud.setPotion(FUREffectRegistry.FOULODOR_POTION);
            else
            	entityareaeffectcloud.setParticle(ParticleTypes.FLAME);
            entityareaeffectcloud.addEffect(new EffectInstance(effect, 4 * 20, 1));
            entityareaeffectcloud.setFixedColor(FogletEntity.this.getSkin() == 0 ? 0xFFFFFF : 0x6F5B3C);

            FogletEntity.this.level.addFreshEntity(entityareaeffectcloud);
        }

        protected int getCastWarmupTime()
        {
            return 20;
        }

        protected int getCastingTime()
        {
            return 100;
        }

        protected int getCastingInterval()
        {
            return 200;
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound()
        {
            return SoundEvents.EVOKER_PREPARE_ATTACK;
        }
    }
    
    @Override
    protected SoundEvent getAmbientSound()
    {
        return FURSoundRegistry.FOGLET_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return FURSoundRegistry.FOGLET_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return FURSoundRegistry.FOGLET_DEATH;
    }
    
    protected SoundEvent getSpellSound()
    {
        return SoundEvents.EVOKER_CAST_SPELL;
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
}
