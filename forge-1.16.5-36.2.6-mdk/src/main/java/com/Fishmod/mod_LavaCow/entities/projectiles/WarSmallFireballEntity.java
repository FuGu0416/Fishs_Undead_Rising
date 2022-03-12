package com.Fishmod.mod_LavaCow.entities.projectiles;

import com.Fishmod.mod_LavaCow.entities.tameable.SalamanderEntity;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class WarSmallFireballEntity extends EnchantableFireBallEntity {
	
	public WarSmallFireballEntity(EntityType<? extends WarSmallFireballEntity> p_i50163_1_, World worldIn) {
		super(p_i50163_1_, worldIn);
	}

	public WarSmallFireballEntity(EntityType<? extends WarSmallFireballEntity> p_i50163_1_, LivingEntity shooter, double accelX, double accelY, double accelZ, World worldIn) {
		super(p_i50163_1_, shooter, accelX, accelY, accelZ, worldIn);
	}

	public WarSmallFireballEntity(EntityType<? extends WarSmallFireballEntity> p_i50163_1_, double x, double y, double z, double accelX, double accelY, double accelZ, World worldIn) {
		super(p_i50163_1_, x, y, z, accelX, accelY, accelZ, worldIn);
	}
	    
	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void tick()
	{
		if (this.tickCount < 3) {
			this.level.addParticle(ParticleTypes.LAVA, this.getX(), this.getY() + 0.5D, this.getZ(), 0.0D, 0.0D, 0.0D);
			this.level.addParticle(ParticleTypes.FLAME, this.getX(), this.getY() + 0.5D, this.getZ(), 0.0D, 0.0D, 0.0D);
		}
	    	
		super.tick();
	}
	
	@Override
	protected void onHitEntity(EntityRayTraceResult result) {
		super.onHitEntity(result);
		if (!this.level.isClientSide) {
			Entity entity = result.getEntity();
            if(this.getOwner() instanceof SalamanderEntity)
            	this.setDamage((float) ((LivingEntity) this.getOwner()).getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue());
			Entity entity1 = this.getOwner();
            int i = entity.getRemainingFireTicks();
            entity.setSecondsOnFire(5 + flame);
            boolean flag = entity.hurt(DamageSource.fireball(this, entity1), this.getDamage());
            if (!flag) {
            	entity.setRemainingFireTicks(i);
            } else if (entity1 instanceof LivingEntity) {
                if (this.knockbackStrength > 0) {
                	Vector3d vector3d = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale((double)this.knockbackStrength * 0.6D);
                    if (vector3d.lengthSqr() > 0.0D) {
                    	entity.push(vector3d.x, 0.1D, vector3d.z);
                    }
                }               
            	this.doEnchantDamageEffects((LivingEntity)entity1, entity);
            }

		}
	}
	
	@Override
	protected void onHitBlock(BlockRayTraceResult result) {
		super.onHitBlock(result);
		if (!this.level.isClientSide) {
			Entity entity = this.getOwner();
			if (entity == null || !(entity instanceof MobEntity) || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this.getEntity())) {
				BlockPos blockpos = result.getBlockPos().relative(result.getDirection());
	            if (this.level.isEmptyBlock(blockpos)) {
	            	this.level.setBlockAndUpdate(blockpos, AbstractFireBlock.getState(this.level, blockpos));
	            }
			}
		}
	}
	
	@Override
	public boolean isPickable() {
		return false;
	}
	
	@Override
	protected ItemStack getItemRaw() {
		return new ItemStack(Items.FIRE_CHARGE);
	}
	
    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}