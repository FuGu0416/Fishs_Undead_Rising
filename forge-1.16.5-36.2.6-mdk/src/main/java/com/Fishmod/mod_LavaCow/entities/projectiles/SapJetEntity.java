package com.Fishmod.mod_LavaCow.entities.projectiles;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.entities.tameable.ScarabEntity;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;
import com.Fishmod.mod_LavaCow.init.FURParticleRegistry;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.network.IPacket;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.IParticleData;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class SapJetEntity extends AbstractFireballEntity {	
	private float damage = 6.0F;
	
	public SapJetEntity(EntityType<? extends SapJetEntity> p_i48540_1_, World worldIn) {
		super(p_i48540_1_, worldIn);
	}

	public SapJetEntity(EntityType<? extends SapJetEntity> p_i48540_1_, LivingEntity shooter, double x, double y, double z, double accelX, double accelY, double accelZ, World worldIn) {
		super(p_i48540_1_, x, y, z, accelX, accelY, accelZ, worldIn);
	}

	public SapJetEntity(EntityType<? extends SapJetEntity> p_i48540_1_, LivingEntity shooter, double accelX, double accelY, double accelZ, World worldIn) {
		super(p_i48540_1_, shooter, accelX, accelY, accelZ, worldIn);
	}
	   
	@Override
	public void tick() {
		super.tick();
		if(!this.horizontalCollision && !this.verticalCollision)
			this.yPower -= 0.006D;

		if(this.level.isClientSide())
			for(int i = 0 ; i < 4 + this.random.nextInt(4) ; i++) {
				this.level.addParticle(this.getParticleType(), this.getX() + this.random.nextDouble() * 0.5D, this.getY() + 0.5D + this.random.nextDouble() * 0.5D, this.getZ() + this.random.nextDouble() * 0.5D, 0.0D, 0.0D, 0.0D);
			}
	}

	/**
	 * Called when this EntityFireball hits a block or entity.
	*/
    @Override
    protected void onHitEntity(EntityRayTraceResult result) {
    	super.onHitEntity(result);
    	if (!this.level.isClientSide() && this.getOwner() != null && result.getEntity() instanceof LivingEntity && this.getOwner() instanceof LivingEntity && result.getEntity() != this.getOwner()) {   		
    		this.setDamage( (float) ((LivingEntity) this.getOwner()).getAttributeValue(Attributes.ATTACK_DAMAGE));
    		
    		if (result.getEntity().hurt(DamageSource.indirectMobAttack(this, (LivingEntity) this.getOwner()).setProjectile(), this.getDamage())) {
    			float local_difficulty = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
        		((LivingEntity)result.getEntity()).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 4 * 20 * (int)local_difficulty, 4));         		
	            this.doEnchantDamageEffects((LivingEntity)this.getOwner(), result.getEntity());
    		}        	
    		
			Vector3d pos = result.getLocation();
			
            ScarabEntity entity = FUREntityRegistry.SCARAB.create(this.level);
            entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0D);
            entity.setHealth(entity.getMaxHealth());
            entity.moveTo(pos.x, pos.y, pos.z, 0.0F, 0.0F);                             
            entity.setLimitedLife(FURConfig.Scarab_Lifespan.get() * 20);
            
            this.level.addFreshEntity(entity);
 
        	entity.setOwnerUUID(this.getOwner().getUUID());
        	
        	if (this.getOwner() instanceof MobEntity && ((MobEntity) this.getOwner()).getTarget() != null) {
        		entity.setTarget(((MobEntity) this.getOwner()).getTarget());
        	} 

    		this.remove();
    	}
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
	   
	protected BasicParticleType getParticleType() {
		return FURParticleRegistry.SAP_JET;
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
		return FURParticleRegistry.SAP_JET;
	}
}