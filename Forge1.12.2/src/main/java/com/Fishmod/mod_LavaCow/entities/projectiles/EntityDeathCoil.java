package com.Fishmod.mod_LavaCow.entities.projectiles;

import com.Fishmod.mod_LavaCow.init.ModMobEffects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityDeathCoil extends EntityThrowable {
	
	private float damage = 6.0F;
	
    public EntityDeathCoil(World worldIn)
    {
        super(worldIn);
    }

    public EntityDeathCoil(World worldIn, EntityLivingBase throwerIn)
    {
        super(worldIn, throwerIn);
    }

    public EntityDeathCoil(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    public static void registerFixesAcidJet(DataFixer fixer)
    {
        EntityThrowable.registerFixesThrowable(fixer, "DeathCoil");
    }
	   
    @Override
    public void onUpdate() {
       super.onUpdate();
       if(this.getEntityWorld().isRemote)
    	   for(int i = 0 ; i < 4 + this.rand.nextInt(16) ; i++) {
    		   world.spawnParticle(this.getParticleType(), this.posX + this.rand.nextDouble() * 0.5D, this.posY + 0.5D + this.rand.nextDouble() * 0.5D, this.posZ + this.rand.nextDouble() * 0.5D, 0.0D, 0.0D, 0.0D);	    	   
    	   }
       if(this.ticksExisted >= 10 * 20)
    	   this.setDead();
    }
	   
	/**
	* Called when this EntityFireball hits a block or entity.
	*/
    protected void onImpact(RayTraceResult result) {
	    if (!this.world.isRemote && result.entityHit != null && this.getThrower() != null && result.entityHit instanceof EntityLivingBase && result.entityHit != this.getThrower()) {
	    	float local_difficulty = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();
	    	this.setDamage( (float) this.getThrower().getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
	    	result.entityHit.attackEntityFrom(DamageSource.causeIndirectDamage(this, this.getThrower()).setProjectile(), this.getDamage());
	    	((EntityLivingBase)result.entityHit).addPotionEffect(new PotionEffect(MobEffects.WITHER, 10 * 20 * (int)local_difficulty, 1));
	    	((EntityLivingBase)result.entityHit).addPotionEffect(new PotionEffect(ModMobEffects.FRAGILE, 10 * 20 * (int)local_difficulty, 2));
	    	this.setDead();
	    }
    }

    /**
    * Returns true if other Entities should be prevented from moving through this Entity.
    */
    public boolean canBeCollidedWith() {
    	return false;
    }
    
    /**
     * Gets the amount of gravity to apply to the thrown entity with each tick.
     */
    protected float getGravityVelocity()
    {
        return 0.0F;
    }
   
    public void setDamage(float damageIn) {
	    this.damage = damageIn;
    }

    public float getDamage() {
	    return this.damage;
    }	   

	protected EnumParticleTypes getParticleType()
	{
	    return EnumParticleTypes.SMOKE_NORMAL;
	}
}

