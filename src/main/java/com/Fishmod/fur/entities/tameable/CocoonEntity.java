package com.Fishmod.fur.entities.tameable;

import com.Fishmod.fur.core.SpawnUtil;
import com.Fishmod.fur.entities.flying.EnigmothEntity;
import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURSoundRegistry;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class CocoonEntity extends FURTameableEntity implements GeoEntity {
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	
	private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("cocoon.model.idle");
	
	private static final EntityDataAccessor<Integer> SKIN_TYPE = SynchedEntityData.defineId(CocoonEntity.class, EntityDataSerializers.INT);
	private int Lifespan = 8 * 20;
	
	public CocoonEntity(EntityType<? extends CocoonEntity> p_i48549_1_, Level worldIn) {
        super(p_i48549_1_, worldIn);
    }
	
	@Override
    protected void registerGoals() {
		super.registerGoals();
    }
    
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
        		.add(Attributes.MAX_HEALTH, 20.0D)
        		.add(Attributes.ARMOR, 10.0D)
        		.add(Attributes.MOVEMENT_SPEED, 0.0D)
        		.add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }
    
    @Override
    protected void defineSynchedData() {
    	super.defineSynchedData();
    	this.getEntityData().define(SKIN_TYPE, Integer.valueOf(0));
    }
    
    protected boolean isCommandable() {
    	return false;
    }
    
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void aiStep() {
        super.aiStep();
        
        if (this.tickCount >= Lifespan) {
        	this.playSound(SoundEvents.SLIME_SQUISH, 1.0F, 1.0F);
        	
    		if (this.level() instanceof ServerLevel server) {		
    			/*if (this.getType().equals(FUREntityRegistry.VESPACOCOON) && this.getSkin() == 0) {
		    		VespaEntity adult = SpawnUtil.trySpawnEntity(FUREntityRegistry.VESPA, server, this.blockPosition());
		    		
		    		if (adult != null && this.isTame() && this.getOwner() instanceof Player) {
		    			adult.tame((Player) this.getOwner());
		    			adult.setCustomName(this.getCustomName());
		    		}
		    		
    			} else if (this.getType().equals(FUREntityRegistry.BEELZEBUBPUPA)) {
		    		BeelzebubEntity adult = SpawnUtil.trySpawnEntity(FUREntityRegistry.BEELZEBUB, server, this.blockPosition());
		    		
		    		if (adult != null && this.isTame() && this.getOwner() instanceof Player) {
		    			adult.tame((Player) this.getOwner());
		    			adult.setCustomName(this.getCustomName());
		    		}    		
		    		
    			} else */if (this.getType().equals(FUREntityRegistry.COCOON.get()) && this.getSkin() == 1) {
    				EnigmothEntity adult = SpawnUtil.trySpawnEntity(FUREntityRegistry.ENIGMOTH.get(), server, this.blockPosition());
    				
    				if (adult != null && this.isTame() && this.getOwner() instanceof Player) {
		    			adult.tame((Player) this.getOwner());
		    			adult.setCustomName(this.getCustomName());
  		    		
			    		if (this.serializeNBT().contains("EnigmothData")) {
			    			adult.addAdditionalSaveData(this.serializeNBT().getCompound("EnigmothData"));
			    		}
    				}
    			}
    		}
        	
    		this.discard();
        }
    }
    
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void tick() {   	
        super.tick();
        this.setDeltaMovement(0.0D, this.getDeltaMovement().y, 0.0D);
    }
    
    public int getSkin() {
    	return this.getEntityData().get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
    	this.getEntityData().set(SKIN_TYPE, Integer.valueOf(skinType));
    }
    
    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean hurt(DamageSource source, float amount) {
		if (source.is(DamageTypes.IN_WALL) || source.is(DamageTypes.DROWN))
			return false;
		return super.hurt(source, amount);
    }
    
    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("lifespan", Lifespan);
		compound.putInt("Variant", this.getSkin());
	}

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		Lifespan = compound.getInt("lifespan");
		this.setSkin(compound.getInt("Variant"));
	}
    
    @Override
    public boolean isImmobile() {
        return true;
    }
    
    /**
     * Applies a velocity to the entities, to push them away from eachother.
     */
    @Override
    public void push(Entity entityIn) {
    }

    protected float getStandingEyeHeight(Pose p_213348_1_, EntityDimensions p_213348_2_) {
        return 0.8F;
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.WOOL_BREAK;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.PARASITE_DEATH.get();
    }

    public MobType getMobType() {
	    return MobType.ARTHROPOD;
	}

    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
        state.getController().setAnimation(IDLE);
    	return PlayState.CONTINUE;
    }    

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "controller", 5, this::predicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
