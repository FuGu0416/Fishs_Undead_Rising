package com.Fishmod.mod_LavaCow.entities;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.entities.ai.EntityFishAIBreakDoor;
import com.Fishmod.mod_LavaCow.init.FURBlockRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potions;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MycosisEntity extends ZombieEntity implements IAggressive {
	
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.defineId(MycosisEntity.class, DataSerializers.INT);
	private int attackTimer;
	private Vector3f[] spore_color = {new Vector3f(0.83F, 0.73F, 0.5F), new Vector3f(0.0F, 0.98F, 0.93F)};
    private static final Predicate<Difficulty> DOOR_BREAKING_PREDICATE = (p_213697_0_) -> {
    		return p_213697_0_ == Difficulty.HARD;
		};
		
    public MycosisEntity(EntityType<? extends MycosisEntity> p_i48549_1_, World worldIn)
    {
        super(p_i48549_1_, worldIn);
        this.setCanBreakDoors(false);
    }
    
    @Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SKIN_TYPE, Integer.valueOf(0));
	}
    
    protected void registerGoals()
    {
        super.registerGoals();
        this.goalSelector.addGoal(1, new EntityFishAIBreakDoor(this, DOOR_BREAKING_PREDICATE));
    }    
    
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return ZombieEntity.createAttributes()
        		.add(Attributes.FOLLOW_RANGE, 35.0D)
        		.add(Attributes.MOVEMENT_SPEED, (double)0.23F)
        		.add(Attributes.MAX_HEALTH, FURConfig.ZombieMushroom_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, FURConfig.ZombieMushroom_Attack.get())
        		.add(Attributes.ARMOR, 2.0D)
        		.add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }
    
    public static boolean checkMycosisSpawnRules(EntityType<? extends MycosisEntity> p_223316_0_, IWorld p_223316_1_, SpawnReason p_223316_2_, BlockPos p_223316_3_, Random p_223316_4_) {
        return MonsterEntity.checkMonsterSpawnRules(p_223316_0_, (IServerWorld) p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_);//SpawnUtil.isAllowedDimension(this.dimension);
    }
    
    protected boolean convertsInWater() {
        return false;
    }
    
    protected boolean supportsBreakDoorGoal() {
        return true;
    }
    
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void aiStep()
    {
        super.aiStep();
        
        if (this.attackTimer > 0) {
            --this.attackTimer;
        }     
    }
	
	@Override
    protected boolean isSunSensitive()
    {
        return !FURConfig.SunScreen_Mode.get();
    }
    
    /**
     * Called to update the entity's position/logic.
     */
    public void tick()
    {
    	super.tick();

        if(this.tickCount % 20 == 0)
        {
        	List<Entity> list = this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(2.0D));

        	for (Entity entity1 : list)
        	{
        		if (entity1 instanceof LivingEntity)
        		{
        			float local_difficulty = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
                        
        			if (!((LivingEntity)entity1).hasEffect(Effects.POISON))
        				((LivingEntity)entity1).addEffect(new EffectInstance(Effects.POISON, 2 * 20 * (int)local_difficulty, 0));
        		}
        	}         		
        }
        
        if(this.tickCount % 10 == 0 && this.level.isClientSide);
        	this.level.addParticle(new RedstoneParticleData(this.spore_color[this.getSkin()].x(), this.spore_color[this.getSkin()].y(), this.spore_color[this.getSkin()].z(), 0.6F), 
        			this.getX() + (double)(new Random().nextFloat() * this.getBbWidth() * 2.0F) - (double)this.getBbWidth(), 
        			this.getY() + (double)(new Random().nextFloat() * this.getBbHeight()), 
        			this.getZ() + (double)(new Random().nextFloat() * this.getBbWidth() * 2.0F) - (double)this.getBbWidth(), 0.0D, 0.0D, 0.0D);
    }
    
    public boolean doHurtTarget(Entity par1Entity)
    {
        if (super.doHurtTarget(par1Entity))
        {
        	this.attackTimer = 5;
        	this.level.broadcastEntityEvent(this, (byte)4);
        	
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData entityLivingData, @Nullable CompoundNBT p_213386_5_) {         
    	boolean is_near_shroom = false;
        int dx = MathHelper.floor(this.getX());
        int dy = MathHelper.floor(this.getBoundingBox().minY);
        int dz = MathHelper.floor(this.getZ());
        int r = 4;

        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.ZombieMushroom_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.ZombieMushroom_Attack.get());
    	this.setHealth(this.getMaxHealth());
    	
        for(BlockPos C : BlockPos.betweenClosed(new BlockPos(dx - r, dy - r, dz - r), new BlockPos(dx + r, dy + r, dz + r)))
        	if(worldIn.getBlockState(C).getBlock() == FURBlockRegistry.GLOWSHROOM
        	|| worldIn.getBlockState(C).getBlock() == FURBlockRegistry.GLOWSHROOM_BLOCK_STEM
        	|| worldIn.getBlockState(C).getBlock() == FURBlockRegistry.GLOWSHROOM_BLOCK_CAP)
        		is_near_shroom = true;
    	
    	
    	if(is_near_shroom || (this.getY() < 50.0D && !this.level.canSeeSky(new BlockPos(this.getX(), (double)Math.round(this.getY()), this.getZ()))))
        	this.setSkin(1);
                        
        return super.finalizeSpawn(worldIn, difficulty, p_213386_3_, entityLivingData, p_213386_5_);
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
    	if (id == 4) 
    	{
            this.attackTimer = 5;
        }
        else
        {
            super.handleEntityEvent(id);
        }
    }
    
    @Override
    protected SoundEvent getAmbientSound()
    {
        return FURSoundRegistry.UNBURIED_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return FURSoundRegistry.UNBURIED_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return FURSoundRegistry.UNBURIED_DEATH;
    }
    
    /**
     * Called when the mob's health reaches 0.
     */
    public void die(DamageSource cause) {
       super.die(cause);
       if(!this.level.isClientSide) {
			if (new Random().nextFloat() < 0.1F)
	    	{
	    		int getVariant = this.getSkin();
	    		switch(getVariant)
	    		{
	    			case 0:
	    				this.spawnAtLocation(new ItemStack(FURBlockRegistry.CORDY_SHROOM, 1), 0.0f);
	    				break;
	    			case 1:
	    				this.spawnAtLocation(new ItemStack(FURBlockRegistry.GLOWSHROOM, 1), 0.0f);
	    				break;
	    			default:
	    				break;
	    		}	
	    	}
						
			if(this.level.getDifficulty() == Difficulty.HARD && !this.isOnFire()) {
				makeAreaOfEffectCloud(this);
			}
       }
    }
    
    private void makeAreaOfEffectCloud(MycosisEntity EntityIn)
    {
    	AreaEffectCloudEntity entityareaeffectcloud = new AreaEffectCloudEntity(EntityIn.level, EntityIn.getX(), EntityIn.getY(), EntityIn.getZ());
        float local_difficulty = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
        entityareaeffectcloud.setOwner(EntityIn);
        entityareaeffectcloud.setRadius(3.0F);
        entityareaeffectcloud.setRadiusOnUse(-0.5F);
        entityareaeffectcloud.setWaitTime(10);
        entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / (float)entityareaeffectcloud.getDuration());
        entityareaeffectcloud.setPotion(Potions.POISON);
        entityareaeffectcloud.addEffect(new EffectInstance(Effects.POISON, 2 * 20 * (int)local_difficulty, 0));

        EntityIn.level.addFreshEntity(entityareaeffectcloud);
    }
    
    public int getSkin()
    {
        return this.entityData.get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType)
    {
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
