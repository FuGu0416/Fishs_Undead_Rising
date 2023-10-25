package com.Fishmod.mod_LavaCow.entities.projectiles;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import java.util.List;

import com.Fishmod.mod_LavaCow.init.FUREffectRegistry;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class MothScalesEntity extends AbstractFireballEntity {	
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.defineId(MothScalesEntity.class, DataSerializers.INT);
	private static final float[][] SCALECOLOR = {{0.42F, 0.4F, 1.00F}, {1.00F, 0.59F, 0.06F}, {0.99F, 0.0F, 0.32F}};	
	private float damage = 6.0F;	
	
	public MothScalesEntity(EntityType<? extends MothScalesEntity> p_i48540_1_, World worldIn) {
		super(p_i48540_1_, worldIn);
	}

	public MothScalesEntity(EntityType<? extends MothScalesEntity> p_i48540_1_, LivingEntity shooter, double x, double y, double z, double accelX, double accelY, double accelZ, World worldIn) {
		super(p_i48540_1_, x, y, z, accelX, accelY, accelZ, worldIn);
	}

	public MothScalesEntity(EntityType<? extends MothScalesEntity> p_i48540_1_, LivingEntity shooter, double accelX, double accelY, double accelZ, World worldIn) {
		super(p_i48540_1_, shooter, accelX, accelY, accelZ, worldIn);
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
		
		if(this.level.isClientSide())
			for(int i = 0 ; i < 2 + this.random.nextInt(2) ; i++) {
				this.level.addParticle(this.getParticleType(this.getScaleType()), this.getX() + this.random.nextDouble() * 0.5D, this.getY() + 0.5D + this.random.nextDouble() * 0.5D, this.getZ() + this.random.nextDouble() * 0.5D, 0.0D, 0.0D, 0.0D);
				if(this.random.nextFloat() < 0.15F) {
					this.level.addParticle(this.getAdditionalParticle(this.getScaleType()), this.getX() + this.random.nextDouble() * 0.5D, this.getY() + 0.5D + this.random.nextDouble() * 0.5D, this.getZ() + this.random.nextDouble() * 0.5D, 0.0D, 0.0D, 0.0D);
				}
			}
	}
	
	@Override
	protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
		super.onHitEntity(p_213868_1_);
		Entity entity = p_213868_1_.getEntity();
		
		if (!this.level.isClientSide && this.getScaleType() == 1) {
			Entity entity1 = this.getOwner();
			if (entity1 == null || !(entity1 instanceof MobEntity) || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this.getEntity())) {
				BlockPos blockpos = entity.blockPosition();
				for(int i = -2 ; i < 2 ; i++) {
					for(int j = -2 ; j < 2 ; j++) {
						for(int k = -2 ; k < 2 ; k++) {					
				            if (this.random.nextFloat() < 0.15F && this.level.isEmptyBlock(blockpos.offset(i, j, k))) {
				            	this.level.setBlockAndUpdate(blockpos.offset(i, j, k), AbstractFireBlock.getState(this.level, blockpos.offset(i, j, k)));
				            }
						}
					}
				}
			}
		}
	}
	
	@Override
	protected void onHitBlock(BlockRayTraceResult result) {
		super.onHitBlock(result);
		if (!this.level.isClientSide && this.getScaleType() == 1) {
			Entity entity = this.getOwner();
			if (entity == null || !(entity instanceof MobEntity) || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this.getEntity())) {
				BlockPos blockpos = result.getBlockPos().relative(result.getDirection());
				for(int i = -2 ; i < 2 ; i++) {
					for(int j = -2 ; j < 2 ; j++) {
						for(int k = -2 ; k < 2 ; k++) {					
				            if (this.random.nextFloat() < 0.15F && this.level.isEmptyBlock(blockpos.offset(i, j, k))) {
				            	this.level.setBlockAndUpdate(blockpos.offset(i, j, k), AbstractFireBlock.getState(this.level, blockpos.offset(i, j, k)));
				            }
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
    protected void onHit(RayTraceResult p_70227_1_) {
        super.onHit(p_70227_1_);
        Entity entity = this.getOwner();
                
		if (!this.level.isClientSide && (p_70227_1_.getType() != RayTraceResult.Type.MISS || !((EntityRayTraceResult)p_70227_1_).getEntity().is(entity))) {
			switch (this.getScaleType()) {    		   
				case 0 :					
				case 2 :
	        		List<LivingEntity> list = this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(4.0D, 2.0D, 4.0D));
	             	AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(this.level, this.getX(), this.getY(), this.getZ());
	             	if (entity instanceof LivingEntity) {
	             		areaeffectcloudentity.setOwner((LivingEntity)entity);
	             	}
	                
	             	areaeffectcloudentity.setRadius(2.0F);
	             	areaeffectcloudentity.setRadiusOnUse(-0.5F);
	             	areaeffectcloudentity.setWaitTime(10);
	             	areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float)areaeffectcloudentity.getDuration());
	             	
	             	if (this.getScaleType() == 0) {
	             		areaeffectcloudentity.setPotion(new Potion(new EffectInstance(FUREffectRegistry.VOID_SCALES, 3600)));
	             		areaeffectcloudentity.addEffect(new EffectInstance(FUREffectRegistry.VOID_SCALES, 4 * 20, 4));	   	             		
	             	} else {
	             		areaeffectcloudentity.setPotion(Potions.STRENGTH);
	             		areaeffectcloudentity.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 20 * 20, 0));	   
	             	}
	             	
	             	if (!list.isEmpty()) {
	             		for(LivingEntity livingentity : list) {
	             			double d0 = this.distanceToSqr(livingentity);
	             			if (d0 < 16.0D) {
	             				areaeffectcloudentity.setPos(livingentity.getX(), livingentity.getY(), livingentity.getZ());
	             				break;
	             			}
	             		}
	             	}
	
	             	this.level.addFreshEntity(areaeffectcloudentity);
    		    	break;  
    		    
    		    default :
    		    	break;
			}               
		}
		
     	this.remove();
	}
    
	/**
	 * Return the motion factor for this projectile. The factor is multiplied by the original motion.
	 */
	@Override
	protected float getInertia() {
		return 0.33F;
	}
	   
	public void setDamage(float damageIn) {
		this.damage = damageIn;
	}

	public float getDamage() {
		return this.damage;
	}	   
	   
	protected RedstoneParticleData getParticleType(int Skin) {		
		return new RedstoneParticleData(Math.max(0.0F, Math.min(1.0F, SCALECOLOR[Skin][0] + (this.random.nextFloat() * 2.0F - 1.0F) * 0.05F)), 
										Math.max(0.0F, Math.min(1.0F, SCALECOLOR[Skin][1] + (this.random.nextFloat() * 2.0F - 1.0F) * 0.05F)), 
										Math.max(0.0F, Math.min(1.0F, SCALECOLOR[Skin][2] + (this.random.nextFloat() * 2.0F - 1.0F) * 0.05F)), 
										0.66F + this.random.nextFloat() * 0.33F);
	}	
	
	private IParticleData getAdditionalParticle(int Skin) {
		switch (Skin) { 
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
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
    
    @Override
    protected boolean shouldBurn() {
        return false;
	}

    @Override
	protected IParticleData getTrailParticle() {
		return ParticleTypes.ASH;
	}
    
    public int getScaleType() {
    	return this.getEntityData().get(SKIN_TYPE).intValue();
    }
    
    public void setScaleType(int type) {
    	this.level.broadcastEntityEvent(this, (byte) (type + 6));
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
    
    public void addAdditionalSaveData(CompoundNBT p_213281_1_) {
    	super.addAdditionalSaveData(p_213281_1_);
        p_213281_1_.putInt("ScaleType", this.getScaleType());
        this.level.broadcastEntityEvent(this, (byte) (this.getScaleType() + 6));
	}

	public void readAdditionalSaveData(CompoundNBT p_70037_1_) {
		super.readAdditionalSaveData(p_70037_1_);
		if (p_70037_1_.contains("ScaleType", 99)) {
			this.setScaleType(p_70037_1_.getInt("ScaleType"));
		}
	}
}