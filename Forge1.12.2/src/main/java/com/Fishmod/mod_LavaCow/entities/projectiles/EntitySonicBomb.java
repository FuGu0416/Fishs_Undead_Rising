package com.Fishmod.mod_LavaCow.entities.projectiles;

import java.util.List;

import com.Fishmod.mod_LavaCow.entities.EntityFoglet;
import com.Fishmod.mod_LavaCow.init.FishItems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntitySonicBomb extends EntityThrowable {
	
    public EntitySonicBomb(World worldIn)
    {
        super(worldIn);
    }

    public EntitySonicBomb(World worldIn, EntityLivingBase throwerIn)
    {
        super(worldIn, throwerIn);
    }

    public EntitySonicBomb(World worldIn, double x, double y, double z)
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
        	EntityFoglet Dummy = new EntityFoglet(this.world);
        	Dummy.setCustomNameTag("Sonic Bomb");
        	this.world.createExplosion(Dummy, this.posX, this.posY, this.posZ, 4.0F, false);
        	Dummy.setDead();
        	this.world.playSound((EntityPlayer)null, new BlockPos(this.posX, this.posY, this.posZ), FishItems.ENTITY_BANSHEE_ATTACK, SoundCategory.BLOCKS, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);        	       	
        	this.setDead();
        }
	}
}
