package com.Fishmod.fur.entities.projectiles;

import com.Fishmod.fur.entities.tameable.SalamanderEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public class WarSmallFireballEntity extends EnchantableFireBallEntity {
	@SuppressWarnings("unchecked")
	public WarSmallFireballEntity(EntityType<?> p_i50158_1_, Level worldIn) {
		super((EntityType<? extends WarSmallFireballEntity>) p_i50158_1_, worldIn);
	}

	public WarSmallFireballEntity(EntityType<? extends WarSmallFireballEntity> p_i50163_1_, LivingEntity shooter, double accelX, double accelY, double accelZ, Level worldIn) {
		super(p_i50163_1_, shooter, accelX, accelY, accelZ, worldIn);
	}

	public WarSmallFireballEntity(EntityType<? extends WarSmallFireballEntity> p_i50163_1_, double x, double y, double z, double accelX, double accelY, double accelZ, Level worldIn) {
		super(p_i50163_1_, x, y, z, accelX, accelY, accelZ, worldIn);
	}
	    
	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void tick() {
		if (this.tickCount < 3) {
			this.level().addParticle(ParticleTypes.LAVA, this.getX(), this.getY() + 0.5D, this.getZ(), 0.0D, 0.0D, 0.0D);
			this.level().addParticle(ParticleTypes.FLAME, this.getX(), this.getY() + 0.5D, this.getZ(), 0.0D, 0.0D, 0.0D);
		}
	    	
		super.tick();
	}
	
	@Override
	protected void onHitEntity(EntityHitResult result) {
		super.onHitEntity(result);
		if (!this.level().isClientSide) {
			Entity entity = result.getEntity();
            if (this.getOwner() instanceof SalamanderEntity)
            	this.setDamage((float) ((LivingEntity) this.getOwner()).getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue());
			Entity entity1 = this.getOwner();
            int i = entity.getRemainingFireTicks();
            entity.setSecondsOnFire(5 + flame);
            boolean flag = entity.hurt(this.damageSources().fireball(this, entity1), this.getDamage());
            if (!flag) {
            	entity.setRemainingFireTicks(i);
            } else if (entity1 instanceof LivingEntity) {
                if (this.knockbackStrength > 0) {
                	Vec3 vector3d = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale((double)this.knockbackStrength * 0.6D);
                    if (vector3d.lengthSqr() > 0.0D) {
                    	entity.push(vector3d.x, 0.1D, vector3d.z);
                    }
                }               
            	this.doEnchantDamageEffects((LivingEntity)entity1, entity);
            }

		}
	}
	
	@Override
	protected void onHitBlock(BlockHitResult result) {
		super.onHitBlock(result);
		if (!this.level().isClientSide) {
			Entity entity = this.getOwner();
			if (entity == null || !(entity instanceof Mob) || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level(), entity)) {
				BlockPos blockpos = result.getBlockPos().relative(result.getDirection());
	            if (this.level().isEmptyBlock(blockpos)) {
	            	this.level().setBlockAndUpdate(blockpos, BaseFireBlock.getState(this.level(), blockpos));
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
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}