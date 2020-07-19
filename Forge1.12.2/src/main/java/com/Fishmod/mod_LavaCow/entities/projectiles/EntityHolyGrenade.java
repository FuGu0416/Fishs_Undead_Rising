package com.Fishmod.mod_LavaCow.entities.projectiles;

import java.util.List;

import com.Fishmod.mod_LavaCow.entities.EntityFoglet;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityHolyGrenade extends EntityThrowable {
	
    public EntityHolyGrenade(World worldIn)
    {
        super(worldIn);
    }

    public EntityHolyGrenade(World worldIn, EntityLivingBase throwerIn)
    {
        super(worldIn, throwerIn);
    }

    public EntityHolyGrenade(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }
    
    /**
     * Gets the amount of gravity to apply to the thrown entity with each tick.
     */
    protected float getGravityVelocity()
    {
        return 0.01F;
    }

	@Override
	protected void onImpact(RayTraceResult result) {
    	List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(4.0D, 4.0D, 4.0D));

    	for (Entity entity1 : list)
    	{
    		if (entity1 instanceof EntityLivingBase && ((EntityLivingBase) entity1).getCreatureAttribute().equals(EnumCreatureAttribute.UNDEAD))
    		{
    			entity1.setFire(8);
    			//entity1.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 4.0F);
    		}
    	}  

    	
        if (!this.world.isRemote)
        {
        	EntityFoglet Dummy = new EntityFoglet(this.world);
        	Dummy.setCustomNameTag("Holy Grenade");
        	this.world.createExplosion(Dummy, this.posX, this.posY, this.posZ, 4.0F, false);
        	Dummy.setDead();
            //Explosion explosion = new Explosion(this.world, this, this.posX, this.posY, this.posZ, 4.0F, false, true);
            //explosion.doExplosionB(true);
        	this.world.playSound((EntityPlayer)null, new BlockPos(this.posX, this.posY, this.posZ), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
        	this.setDead();
        }
	}
}
