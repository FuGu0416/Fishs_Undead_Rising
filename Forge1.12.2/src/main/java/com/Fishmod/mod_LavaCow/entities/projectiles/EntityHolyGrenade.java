package com.Fishmod.mod_LavaCow.entities.projectiles;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityWolf;
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
        return 0.075F;
    }

	@Override
	protected void onImpact(RayTraceResult result) {	
        if (!this.world.isRemote)
        {
        	EntityWolf Dummy = new EntityWolf(this.world);
        	Dummy.setOwnerId(thrower.getUniqueID());
        	Dummy.setTamed(true);
        	Dummy.setCustomNameTag("Holy Grenade");
        	this.world.createExplosion(Dummy, this.posX, this.posY, this.posZ, 4.0F, false);
        	Dummy.setDead();
        	this.world.playSound(null, new BlockPos(this.posX, this.posY, this.posZ), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
        	this.setDead();
        }
	}
}
