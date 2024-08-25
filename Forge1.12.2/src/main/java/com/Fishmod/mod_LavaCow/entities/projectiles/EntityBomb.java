package com.Fishmod.mod_LavaCow.entities.projectiles;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityBomb extends EntityThrowable implements IEntityAdditionalSpawnData {
	String getDefaultItem = "Basic Bomb";
	SoundEvent usedSound = SoundEvents.ENTITY_GENERIC_EXPLODE;
	float radius = 4.0F;
	
    public EntityBomb(World worldIn) {
        super(worldIn);
    }

    public EntityBomb(World world, EntityLivingBase thrower) {
        super(world, thrower);
    }

    public EntityBomb(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }
    
    public EntityBomb(World world, SoundEvent sound, float radius, String item) {
    	super(world);
    	this.usedSound = sound;
    	this.radius = radius;
    	this.getDefaultItem = item;
    }

    public EntityBomb(World world, EntityLivingBase thrower, SoundEvent sound, float radius, String item) {
    	super(world, thrower);
    	this.usedSound = sound;
    	this.radius = radius;
    	this.getDefaultItem = item;
    }

    public EntityBomb(World world, double x, double y, double z, SoundEvent sound, float radius, String item) {
    	super(world, x, y, z);
    	this.usedSound = sound;
    	this.radius = radius;
    	this.getDefaultItem = item;
    }
    
    @Override
    public void writeSpawnData(ByteBuf data) {
        data.writeInt(thrower != null ? thrower.getEntityId() : -1);
    }

    @Override
    public void readSpawnData(ByteBuf data) {
        final Entity thrower = world.getEntityByID(data.readInt());

        if (thrower instanceof EntityLivingBase) {
            this.thrower = (EntityLivingBase)thrower;
        }
    }
    
    /**
     * Gets the amount of gravity to apply to the thrown entity with each tick.
     */
    protected float getGravityVelocity() {
        return 0.055F;
    }

	@Override
	protected void onImpact(RayTraceResult result) {	
        if (!this.world.isRemote && result != null && result.typeOfHit != RayTraceResult.Type.MISS) {
        	EntityWolf Dummy = new EntityWolf(this.world);
        	
        	if (this.getThrower() != null) {
	        	Dummy.setTamed(true);
	        	Dummy.setOwnerId(thrower.getUniqueID());
        	}
        	
        	Dummy.setCustomNameTag(this.getDefaultItem);
        	this.world.createExplosion(Dummy, this.posX, this.posY, this.posZ, this.radius, false);
        	Dummy.setDead();
        	this.world.playSound(null, new BlockPos(this.posX, this.posY, this.posZ), this.usedSound, SoundCategory.BLOCKS, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
        	this.setDead();
        }
	}
}
