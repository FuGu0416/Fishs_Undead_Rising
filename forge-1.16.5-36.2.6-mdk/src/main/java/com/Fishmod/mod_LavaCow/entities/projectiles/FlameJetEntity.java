package com.Fishmod.mod_LavaCow.entities.projectiles;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class FlameJetEntity extends ProjectileItemEntity {
	
	private float damage = 6.0F;
	
    public FlameJetEntity(EntityType<? extends FlameJetEntity> p_i48540_1_, World worldIn) {
        super(p_i48540_1_, worldIn);
    }

    public FlameJetEntity(EntityType<? extends FlameJetEntity> p_i48540_1_, LivingEntity throwerIn, World worldIn) {
        super(p_i48540_1_, throwerIn, worldIn);
    }

    public FlameJetEntity(EntityType<? extends FlameJetEntity> p_i48540_1_, double x, double y, double z, World worldIn) {
        super(p_i48540_1_, x, y, z, worldIn);
    }

    @Override
    public void tick() {
       super.tick();
       if(this.level.isClientSide())
    	   for(int i = 0 ; i < 8 + this.random.nextInt(8) ; i++) {
    		   this.level.addParticle(this.getParticleType(), this.getX() + this.random.nextDouble() * 0.5D, this.getY() + 0.5D + this.random.nextDouble() * 0.5D, this.getZ() + this.random.nextDouble() * 0.5D, 0.0D, 0.0D, 0.0D);	    	   
    	   }
    }
	   
	/**
	* Called when this EntityFireball hits a block or entity.
	*/
    @Override
    protected void onHitEntity(EntityRayTraceResult result) {
    	super.onHitEntity(result);
	    if (!this.level.isClientSide() && this.getOwner() != null && result.getEntity() instanceof LivingEntity && result.getEntity() != this.getOwner()) {	    	
	    	this.setDamage( (float) ((LivingEntity) this.getOwner()).getAttributeValue(Attributes.ATTACK_DAMAGE));
	    	
	    	if (result.getEntity().hurt(DamageSource.indirectMobAttack(this, (LivingEntity) this.getOwner()).setProjectile(), this.getDamage())) {
	    		float local_difficulty = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
	    		result.getEntity().setRemainingFireTicks(2 * 20 * (int)local_difficulty);
	    	}
	    	
	    	this.remove();
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
	protected void onHit(RayTraceResult p_70227_1_) {
		super.onHit(p_70227_1_);
		if (!this.level.isClientSide) {
			this.remove();
		}
	}
   
    public void setDamage(float damageIn) {
	    this.damage = damageIn;
    }

    public float getDamage() {
	    return this.damage;
    }	   

	protected BasicParticleType getParticleType() {
	    return ParticleTypes.SOUL_FIRE_FLAME;
	}
	
	@Override
	protected Item getDefaultItem() {
		return Items.FIRE_CHARGE;
	}
	
    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}

