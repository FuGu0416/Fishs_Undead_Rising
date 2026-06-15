package com.Fishmod.fur.entities.projectiles;

import java.util.List;

import org.joml.Vector3f;

import com.Fishmod.fur.init.FUREffectRegistry;
import com.Fishmod.fur.particle.MothScaleOptions;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

public class MothScalesEntity extends Fireball {	
	private static final EntityDataAccessor<Integer> SKIN_TYPE = SynchedEntityData.defineId(MothScalesEntity.class, EntityDataSerializers.INT);
	// Colors match each type's granted potion: type 0 = Void Dust 0xD146FF (209,70,255),
	// type 2 = Strength/DAMAGE_BOOST 0xFFC700 (255,199,0).
	private static final float[][] SCALECOLOR = {{0.82F, 0.275F, 1.00F}, {1.00F, 0.59F, 0.06F}, {1.00F, 0.78F, 0.0F}};

	@SuppressWarnings("unchecked")
	public MothScalesEntity(EntityType<?> entityType, Level worldIn) {
		super((EntityType<? extends MothScalesEntity>)entityType, worldIn);
	}

	public MothScalesEntity(EntityType<? extends MothScalesEntity> entityType, LivingEntity shooter, double x, double y, double z, double accelX, double accelY, double accelZ, Level worldIn) {
		super(entityType, x, y, z, accelX, accelY, accelZ, worldIn);
	}

	public MothScalesEntity(EntityType<? extends MothScalesEntity> entityType, LivingEntity shooter, double accelX, double accelY, double accelZ, Level worldIn) {
		super(entityType, shooter, accelX, accelY, accelZ, worldIn);
	}
	
    @Override
    protected void defineSynchedData() {
    	super.defineSynchedData();
    	this.getEntityData().define(SKIN_TYPE, Integer.valueOf(0));   	   	
    }
	   
	@Override
	public void tick() {
		super.tick();
		if(!this.horizontalCollision && !this.verticalCollision)
			this.yPower -= 0.006D;
		
		if(this.level().isClientSide()) {
			int type = this.getScaleType();
			for(int i = 0 ; i < 2 + this.random.nextInt(2) ; i++) {
				this.level().addParticle(this.getParticleType(type), this.getX() + this.random.nextDouble() * 0.5D, this.getY() + 0.5D + this.random.nextDouble() * 0.5D, this.getZ() + this.random.nextDouble() * 0.5D, 0.0D, 0.0D, 0.0D);

				if(this.random.nextFloat() < 0.15F) {
					this.level().addParticle(this.getAdditionalParticle(type), this.getX() + this.random.nextDouble() * 0.5D, this.getY() + 0.5D + this.random.nextDouble() * 0.5D, this.getZ() + this.random.nextDouble() * 0.5D, 0.0D, 0.0D, 0.0D);
				}
			}
		}
	}
	
	@Override
	protected boolean canHitEntity(Entity target) {
		if (!super.canHitEntity(target)) {
			return false;
		}
		// Vanilla only shields passengers that share the owner's vehicle; when the owner *is* the mount
		// (a ridden Enigmoth), that leaves the mount and its rider hittable, so the volley would strike
		// them on spawn and drop the effect cloud right on the Enigmoth. Exclude the whole mount stack.
		Entity owner = this.getOwner();
		return owner == null || target.getRootVehicle() != owner.getRootVehicle();
	}

	@Override
	protected void onHitEntity(EntityHitResult result) {
		super.onHitEntity(result);
		if (!this.level().isClientSide && this.getScaleType() == 1) {
			this.igniteArea(result.getEntity().blockPosition());
		}
	}

	@Override
	protected void onHitBlock(BlockHitResult result) {
		super.onHitBlock(result);
		if (!this.level().isClientSide && this.getScaleType() == 1) {
			this.igniteArea(result.getBlockPos().relative(result.getDirection()));
		}
	}

	/**
	 * Scatter fire in a 4x4x4 box around {@code center}, unless the owner is a mob and mob-griefing
	 * is disabled for that mob.
	 */
	private void igniteArea(BlockPos center) {
		Entity owner = this.getOwner();
		if (!(owner instanceof Mob) || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level(), owner)) {
			for (int i = -2 ; i < 2 ; i++) {
				for (int j = -2 ; j < 2 ; j++) {
					for (int k = -2 ; k < 2 ; k++) {
						BlockPos pos = center.offset(i, j, k);
						if (this.random.nextFloat() < 0.15F && this.level().isEmptyBlock(pos)) {
							this.level().setBlockAndUpdate(pos, BaseFireBlock.getState(this.level(), pos));
						}
					}
				}
			}
		}
	}

	/**
	 * Called when this EntityFireball hits a block or entity.
	*/
    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        Entity entity = this.getOwner();
                
		if (!this.level().isClientSide && (result.getType() != HitResult.Type.MISS || !((EntityHitResult)result).getEntity().is(entity))) {
			switch (this.getScaleType()) {    		   
				case 0 :					
				case 2 :
	        		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(4.0D, 2.0D, 4.0D));
	        		AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
	             	if (entity instanceof LivingEntity) {
	             		areaeffectcloudentity.setOwner((LivingEntity)entity);
	             	}
	                
	             	areaeffectcloudentity.setRadius(2.0F);
	             	areaeffectcloudentity.setRadiusOnUse(-0.5F);
	             	areaeffectcloudentity.setWaitTime(10);
	             	areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float)areaeffectcloudentity.getDuration());
	             	
	             	if (this.getScaleType() == 0) {
	             		areaeffectcloudentity.setPotion(FUREffectRegistry.VOID_DUST_POTION.get());
	             		areaeffectcloudentity.addEffect(new MobEffectInstance(FUREffectRegistry.VOID_DUST.get(), 4 * 20, 4));	   	             		
	             	} else {
	             		areaeffectcloudentity.setPotion(Potions.STRENGTH);
	             		areaeffectcloudentity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 * 20, 0));	   
	             	}
	             	
	             	if (!list.isEmpty()) {
	             		for(LivingEntity livingentity : list) {
	             			// Never snap the cloud onto the firing mount or its rider.
	             			if (entity != null && livingentity.getRootVehicle() == entity.getRootVehicle()) {
	             				continue;
	             			}
	             			double d0 = this.distanceToSqr(livingentity);
	             			if (d0 < 16.0D) {
	             				areaeffectcloudentity.setPos(livingentity.getX(), livingentity.getY(), livingentity.getZ());
	             				break;
	             			}
	             		}
	             	}
	
	             	this.level().addFreshEntity(areaeffectcloudentity);
    		    	break;  
    		    
    		    default :
    		    	break;
			}               
		}
		
     	this.discard();
	}
    
	/**
	 * Return the motion factor for this projectile. The factor is multiplied by the original motion.
	 */
	@Override
	protected float getInertia() {
		return 0.33F;
	}
	   
	protected MothScaleOptions getParticleType(int skin) {
		return new MothScaleOptions(new Vector3f(Math.max(0.0F, Math.min(1.0F, SCALECOLOR[skin][0] + (this.random.nextFloat() * 2.0F - 1.0F) * 0.05F)),
										Math.max(0.0F, Math.min(1.0F, SCALECOLOR[skin][1] + (this.random.nextFloat() * 2.0F - 1.0F) * 0.05F)), 
										Math.max(0.0F, Math.min(1.0F, SCALECOLOR[skin][2] + (this.random.nextFloat() * 2.0F - 1.0F) * 0.05F))),
										(0.66F + this.random.nextFloat() * 0.33F) * (0.3F + this.random.nextFloat() * 0.2F));
	}	
	
	private ParticleOptions getAdditionalParticle(int skin) {
		switch (skin) { 
			case 0 :
				return ParticleTypes.PORTAL;
		    case 1 :
		    	return ParticleTypes.FLAME;  
		    case 2 :
		    default :
		    	return ParticleTypes.ASH;
		}
	}

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
    
    @Override
    protected boolean shouldBurn() {
        return false;
	}

    @Override
    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.ASH;
	}
    
    public int getScaleType() {
    	return this.getEntityData().get(SKIN_TYPE).intValue();
    }
    
    public void setScaleType(int type) {
    	this.level().broadcastEntityEvent(this, (byte) (type + 6));
    	this.getEntityData().set(SKIN_TYPE, Integer.valueOf(type));
    }
    
    /**
     * Handler for {@link World#setEntityState}
     */
	@OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
    	if (id == 6) {
    		this.setScaleType(0);
        } else if (id == 7) {
        	this.setScaleType(1);
        } else if (id == 8) {
        	this.setScaleType(2);
        } else {
            super.handleEntityEvent(id);
        }
    }
    
    public void addAdditionalSaveData(CompoundTag compound) {
    	super.addAdditionalSaveData(compound);
        compound.putInt("ScaleType", this.getScaleType());
        this.level().broadcastEntityEvent(this, (byte) (this.getScaleType() + 6));
	}

	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("ScaleType", 99)) {
			this.setScaleType(compound.getInt("ScaleType"));
		}
	}
}