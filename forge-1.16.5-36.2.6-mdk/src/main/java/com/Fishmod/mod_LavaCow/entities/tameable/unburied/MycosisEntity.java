package com.Fishmod.mod_LavaCow.entities.tameable.unburied;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.entities.tameable.FURTameableEntity;
import com.Fishmod.mod_LavaCow.init.FURBlockRegistry;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.FleeSunGoal;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potions;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class MycosisEntity extends UnburiedEntity {	
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.defineId(MycosisEntity.class, DataSerializers.INT);
	private Vector3f[] spore_color = {new Vector3f(0.83F, 0.73F, 0.5F), new Vector3f(0.0F, 0.98F, 0.93F)};
		
    public MycosisEntity(EntityType<? extends MycosisEntity> p_i48549_1_, World worldIn) {
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
        		.add(Attributes.MOVEMENT_SPEED, (double)0.23F)
        		.add(Attributes.MAX_HEALTH, FURConfig.ZombieMushroom_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, FURConfig.ZombieMushroom_Attack.get())
        		.add(Attributes.ARMOR, 2.0D)
        		.add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }
    
    public static boolean checkMycosisSpawnRules(EntityType<? extends MycosisEntity> p_223316_0_, IWorld p_223316_1_, SpawnReason p_223316_2_, BlockPos p_223316_3_, Random p_223316_4_) {
        return FURTameableEntity.checkMonsterSpawnRules(p_223316_0_, (IServerWorld) p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_);//SpawnUtil.isAllowedDimension(this.dimension);
    }
    
    protected boolean convertsInWater() {
        return false;
    }
    
    protected boolean supportsBreakDoorGoal() {
        return true;
    }
    
    @Override
    public double getMyRidingOffset() {
        return this.isBaby() ? 0.0D : -0.25D;
    }
    
    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
    	super.tick();

        if(this.tickCount % 20 == 0) {
        	List<Entity> list = this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(2.0D));

        	for (Entity entity1 : list) {
        		if (entity1 instanceof LivingEntity && !this.isOwnedBy((LivingEntity) entity1)) {
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
         
    /**
     * Called when the mob's health reaches 0.
     */
    public void die(DamageSource cause) {
       super.die(cause);
       if(!this.level.isClientSide) {						
			if(this.level.getDifficulty() == Difficulty.HARD && !this.isOnFire()) {
				makeAreaOfEffectCloud(this);
			}
       }
    }
    
    private void makeAreaOfEffectCloud(MycosisEntity EntityIn) {
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
    
    @Nullable
    @Override
    protected ResourceLocation getDefaultLootTable() {
    	switch(this.getSkin()) {
    		case 1:
    			return new ResourceLocation("mod_lavacow", "entities/mycosis1");
    		case 0:
    		default:
    			return super.getDefaultLootTable();
    	}
    }
}
