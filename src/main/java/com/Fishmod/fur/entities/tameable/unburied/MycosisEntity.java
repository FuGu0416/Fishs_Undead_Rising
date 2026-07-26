package com.Fishmod.fur.entities.tameable.unburied;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

import org.joml.Vector3f;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.entities.tameable.FURTameableEntity;
import com.Fishmod.fur.init.FURBiomesRegistry;
import com.Fishmod.fur.init.FURBlockRegistry;
import com.Fishmod.fur.init.FUREffectRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class MycosisEntity extends UnburiedEntity {
	private static final Vector3f[] SPORE_COLOR = {new Vector3f(0.83F, 0.73F, 0.5F), new Vector3f(0.0F, 0.98F, 0.93F)};
	private static final EntityDataAccessor<Boolean> SHEARED = SynchedEntityData.defineId(MycosisEntity.class, EntityDataSerializers.BOOLEAN);

    public MycosisEntity(EntityType<? extends MycosisEntity> entityType, Level worldIn) {
        super(entityType, worldIn);
    }

    @Override
    protected void defineSynchedData() {
    	super.defineSynchedData();
    	this.entityData.define(SHEARED, Boolean.valueOf(false));
    }

    public boolean isSheared() {
    	return this.entityData.get(SHEARED).booleanValue();
    }

    @Override
    protected void registerGoals() {
    	super.registerGoals();
    	if (!FURConfig.SunScreen_Mode.get())this.goalSelector.addGoal(4, new FleeSunGoal(this, 1.0D));
    }
    
    public static AttributeSupplier.Builder createAttributes() {
        return Zombie.createAttributes()
        		.add(Attributes.FOLLOW_RANGE, 35.0D)
        		.add(Attributes.MOVEMENT_SPEED, (double)0.23F)
        		.add(Attributes.MAX_HEALTH, 20.0D)
        		.add(Attributes.ATTACK_DAMAGE, 3.0D)
        		.add(Attributes.ARMOR, 2.0D)
        		.add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }
    
    public static boolean checkMycosisSpawnRules(EntityType<? extends MycosisEntity> entityTypeIn, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource randomSource) {
        // Luminous Undergrove is a naturally-lit cave biome — skip the darkness
        // check so Mycosis can still spawn in its home biome.
        if (level.getBiome(pos).is(FURBiomesRegistry.LUMINOUS_UNDERGROVE)) {
            return level.getDifficulty() != Difficulty.PEACEFUL
                    && FURTameableEntity.checkMobSpawnRules(entityTypeIn, level, spawnType, pos, randomSource);
        }
        return FURTameableEntity.checkMonsterSpawnRules(entityTypeIn, level, spawnType, pos, randomSource);
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

    	if (!this.isSheared()) {
	    	List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(2.0D));

	    	for (LivingEntity entity1 : list) {
	    		if (!this.isOwnedBy(entity1)) {
	    			float local_difficulty = this.level().getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();

	    			MobEffect effect = this.getSkin() == 2 ? FUREffectRegistry.SPOREROT.get() : MobEffects.POISON;
	    			// Re-applied every tick, same as Salamander topping up its rider's Fire Resistance:
	    			// a short duration continuously refreshed while in range, with a normal (non-ambient)
	    			// icon — not the translucent "aura"/beacon-style border ambient=true would show.
	    			entity1.addEffect(new MobEffectInstance(effect, 2 * 20 * (int)local_difficulty, 0, false, false, true));
	    		}
	    	}
    	}

        if (this.tickCount % 10 == 0 && this.level().isClientSide) {
        	this.level().addParticle(new DustParticleOptions(SPORE_COLOR[this.getSkin() - 1], 0.6F),
        			this.getX() + (double)(new Random().nextFloat() * this.getBbWidth() * 2.0F) - (double)this.getBbWidth(),
        			this.getY() + (double)(new Random().nextFloat() * this.getBbHeight()),
        			this.getZ() + (double)(new Random().nextFloat() * this.getBbWidth() * 2.0F) - (double)this.getBbWidth(), 0.0D, 0.0D, 0.0D);
        }
    }
    
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficulty, MobSpawnType spawnTypeIn, @Nullable SpawnGroupData entityLivingData, @Nullable CompoundTag tag) {         
    	entityLivingData = super.finalizeSpawn(worldIn, difficulty, spawnTypeIn, entityLivingData, tag);
 
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Mycosis_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Mycosis_Attack.get());
    	this.setHealth(this.getMaxHealth());
    	
    	if (spawnTypeIn == MobSpawnType.COMMAND || spawnTypeIn == MobSpawnType.SPAWN_EGG || spawnTypeIn == MobSpawnType.SPAWNER || spawnTypeIn == MobSpawnType.DISPENSER) {
        	this.setSkin(Integer.valueOf(this.random.nextInt(2) + 1));
        } else if (worldIn.getBiome(this.blockPosition()).is(FURBiomesRegistry.LUMINOUS_UNDERGROVE)) {
        	this.setSkin(2);
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
        if (this.getSkin() == 2) {
        	entityareaeffectcloud.setPotion(FUREffectRegistry.SPOREROT_POTION.get());
        	entityareaeffectcloud.addEffect(new MobEffectInstance(FUREffectRegistry.SPOREROT.get(), 2 * 20 * (int)local_difficulty, 0));
        } else {
        	entityareaeffectcloud.setPotion(Potions.POISON);
        	entityareaeffectcloud.addEffect(new MobEffectInstance(MobEffects.POISON, 2 * 20 * (int)local_difficulty, 0));
        }

        EntityIn.level().addFreshEntity(entityareaeffectcloud);
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        if (this.getSkin() == 2 && effect.getEffect() == FUREffectRegistry.SPOREROT.get()) {
            return false;
        }
        return super.canBeAffected(effect);
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

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
    	ItemStack itemstack = player.getItemInHand(hand);
    	if (itemstack.getItem() == Items.SHEARS && !this.isSheared()) {
    		if (!this.level().isClientSide) {
    			this.entityData.set(SHEARED, Boolean.valueOf(true));

    			switch (this.getSkin()) {
    				case 2:
    					this.spawnAtLocation(new ItemStack(FURBlockRegistry.GLOWSHROOM.get(), 2), 0.0F);
    					break;
    				case 1:
    				default:
    					this.spawnAtLocation(new ItemStack(FURBlockRegistry.CORDY_SHROOM.get(), 2), 0.0F);
    					break;
    			}

    			itemstack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
    			this.playSound(SoundEvents.SHEEP_SHEAR, 1.0F, 1.0F);
    		}
    		return InteractionResult.sidedSuccess(this.level().isClientSide);
    	}
    	return super.mobInteract(player, hand);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
    	super.addAdditionalSaveData(compound);
    	compound.putBoolean("Sheared", this.isSheared());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
    	super.readAdditionalSaveData(compound);
    	this.entityData.set(SHEARED, Boolean.valueOf(compound.getBoolean("Sheared")));
    }
}
