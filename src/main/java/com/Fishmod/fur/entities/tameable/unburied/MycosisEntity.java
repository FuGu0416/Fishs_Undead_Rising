package com.Fishmod.fur.entities.tameable.unburied;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

import org.joml.Vector3f;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.tameable.FURTameableEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class MycosisEntity extends UnburiedEntity {	
	private static final Vector3f[] SPORE_COLOR = {new Vector3f(0.83F, 0.73F, 0.5F), new Vector3f(0.0F, 0.98F, 0.93F)};
		
    public MycosisEntity(EntityType<? extends MycosisEntity> p_i48549_1_, Level worldIn) {
        super(p_i48549_1_, worldIn);
    }
    
    @Override
    protected void registerGoals() {
    	super.registerGoals();
    	/*if(!FURConfig.SunScreen_Mode.get())*/this.goalSelector.addGoal(4, new FleeSunGoal(this, 1.0D));
    }
    
    public static AttributeSupplier.Builder createAttributes() {
        return Zombie.createAttributes()
        		.add(Attributes.FOLLOW_RANGE, 35.0D)
        		.add(Attributes.MOVEMENT_SPEED, (double)0.23F)
        		.add(Attributes.MAX_HEALTH, 20.0D/*FURConfig.ZombieMushroom_Health.get()*/)
        		.add(Attributes.ATTACK_DAMAGE, 3.0D/*FURConfig.ZombieMushroom_Attack.get()*/)
        		.add(Attributes.ARMOR, 2.0D)
        		.add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }
    
    public static boolean checkMycosisSpawnRules(EntityType<? extends MycosisEntity> p_223316_0_, ServerLevelAccessor p_223316_1_, MobSpawnType p_223316_2_, BlockPos p_223316_3_, RandomSource p_223316_4_) {
        return FURTameableEntity.checkMonsterSpawnRules(p_223316_0_, p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_);
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
        	List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(2.0D));

        	for (LivingEntity entity1 : list) {
        		if (!this.isOwnedBy((LivingEntity) entity1)) {
        			float local_difficulty = this.level().getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
                        
        			if (!entity1.hasEffect(MobEffects.POISON))
        				entity1.addEffect(new MobEffectInstance(MobEffects.POISON, 2 * 20 * (int)local_difficulty, 0));
        		}
        	}         		
        }
        
        if(this.tickCount % 10 == 0 && this.level().isClientSide);
        	this.level().addParticle(new DustParticleOptions(SPORE_COLOR[this.getSkin() - 1], 0.6F), 
        			this.getX() + (double)(new Random().nextFloat() * this.getBbWidth() * 2.0F) - (double)this.getBbWidth(), 
        			this.getY() + (double)(new Random().nextFloat() * this.getBbHeight()), 
        			this.getZ() + (double)(new Random().nextFloat() * this.getBbWidth() * 2.0F) - (double)this.getBbWidth(), 0.0D, 0.0D, 0.0D);
    }
    
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficulty, MobSpawnType p_213386_3_, @Nullable SpawnGroupData entityLivingData, @Nullable CompoundTag p_213386_5_) {         
    	entityLivingData = super.finalizeSpawn(worldIn, difficulty, p_213386_3_, entityLivingData, p_213386_5_);
    	/*boolean is_near_shroom = false;
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
        	this.setSkin(1);*/
    	
    	if (p_213386_3_ == MobSpawnType.COMMAND || p_213386_3_ == MobSpawnType.SPAWN_EGG || p_213386_3_ == MobSpawnType.SPAWNER || p_213386_3_ == MobSpawnType.DISPENSER) {
        	this.setSkin(Integer.valueOf(this.random.nextInt(2) + 1));
        } else {       
        	this.setSkin(1);      
        }
    	
        return entityLivingData;
    }
         
    /**
     * Called when the mob's health reaches 0.
     */
    public void die(DamageSource cause) {
       super.die(cause);
       if (!this.level().isClientSide()) {				
			if (this.level().getDifficulty() == Difficulty.HARD && !this.isOnFire()) {
				makeAreaOfEffectCloud(this);
			}
       }
    }
    
    private void makeAreaOfEffectCloud(MycosisEntity EntityIn) {
    	AreaEffectCloud entityareaeffectcloud = new AreaEffectCloud(EntityIn.level(), EntityIn.getX(), EntityIn.getY(), EntityIn.getZ());
        float local_difficulty = this.level().getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
        entityareaeffectcloud.setOwner(EntityIn);
        entityareaeffectcloud.setRadius(3.0F);
        entityareaeffectcloud.setRadiusOnUse(-0.5F);
        entityareaeffectcloud.setWaitTime(10);
        entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / (float)entityareaeffectcloud.getDuration());
        entityareaeffectcloud.setPotion(Potions.POISON);
        entityareaeffectcloud.addEffect(new MobEffectInstance(MobEffects.POISON, 2 * 20 * (int)local_difficulty, 0));

        EntityIn.level().addFreshEntity(entityareaeffectcloud);
    }
    
    @Nullable
    @Override
    protected ResourceLocation getDefaultLootTable() {
    	switch(this.getSkin()) {
    		case 2:
    			return new ResourceLocation(mod_LavaCow.MODID, "entities/mycosis1");
    		case 1:
    		default:
    			return super.getDefaultLootTable();
    	}
    }
}
