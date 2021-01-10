package com.Fishmod.mod_LavaCow.entities.projectiles;

import com.Fishmod.mod_LavaCow.init.FishItems;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityGhostBomb extends EntityThrowable {

    public EntityGhostBomb(World worldIn)
    {
        super(worldIn);
    }

    public EntityGhostBomb(World worldIn, EntityLivingBase throwerIn)
    {
        super(worldIn, throwerIn);
    }

    public EntityGhostBomb(World worldIn, double x, double y, double z)
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
        if (!this.world.isRemote)
        {
        	this.world.createExplosion(null, this.posX, this.posY, this.posZ, 4.0F, false);
        	this.world.playSound(null, new BlockPos(this.posX, this.posY, this.posZ), FishItems.ENTITY_BANSHEE_HURT, SoundCategory.BLOCKS, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
        	this.setDead();
        }
	}
}
