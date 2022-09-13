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

public class EntityAcidJet extends EntityThrowable {
	
	private float damage = 6.0F;
	
    public EntityAcidJet(World worldIn)
    {
        super(worldIn);
    }

    public EntityAcidJet(World worldIn, EntityLivingBase throwerIn)
    {
        super(worldIn, throwerIn);
    }

    public EntityAcidJet(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    public static void registerFixesAcidJet(DataFixer fixer)
    {
        EntityThrowable.registerFixesThrowable(fixer, "AcidJet");
    }
	   
    @Override
    public void onUpdate() {
       super.onUpdate();
       if(this.getEntityWorld().isRemote)
    	   for(int i = 0 ; i < 4 + this.rand.nextInt(16) ; i++) {
    		   world.spawnParticle(this.getParticleType(), this.posX + this.rand.nextDouble() * 0.5D, this.posY + 0.5D + this.rand.nextDouble() * 0.5D, this.posZ + this.rand.nextDouble() * 0.5D, 0.0D, 0.0D, 0.0D);	    	   
    	   }
    }
	   
	/**
	* Called when this EntityFireball hits a block or entity.
	*/
    protected void onImpact(RayTraceResult result) {
	    if (!this.world.isRemote && result.entityHit != null && this.getThrower() != null && result.entityHit instanceof EntityLivingBase && result.entityHit != this.getThrower()) {	    	
	    	this.setDamage( (float) this.getThrower().getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
	    	
	    	if (result.entityHit.attackEntityFrom(DamageSource.causeIndirectDamage(this, this.getThrower()).setProjectile(), this.getDamage())) {
	    		float local_difficulty = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();
		    	((EntityLivingBase)result.entityHit).addPotionEffect(new PotionEffect(MobEffects.POISON, 2 * 20 * (int)local_difficulty, 0));
		    	((EntityLivingBase)result.entityHit).addPotionEffect(new PotionEffect(ModMobEffects.CORRODED, 2 * 20 * (int)local_difficulty, 3));
	    	}
	    	
	    	this.setDead();
	    }
    }

    /**
    * Returns true if other Entities should be prevented from moving through this Entity.
    */
    public boolean canBeCollidedWith() {
    	return false;
    }
   
    public void setDamage(float damageIn) {
	    this.damage = damageIn;
    }

    public float getDamage() {
	    return this.damage;
    }	   

	protected EnumParticleTypes getParticleType()
	{
	    return EnumParticleTypes.SLIME;
	}
}

