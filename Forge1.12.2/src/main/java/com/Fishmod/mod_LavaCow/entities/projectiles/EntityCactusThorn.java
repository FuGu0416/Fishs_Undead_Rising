package com.Fishmod.mod_LavaCow.entities.projectiles;

import com.Fishmod.mod_LavaCow.init.FishItems;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityCactusThorn extends EntityArrow implements IEntityAdditionalSpawnData {
	public EntityCactusThorn(World world) {
		super(world);
	}
	
    public EntityCactusThorn(World world, double x, double y, double z)
    {
        super(world, x, y, z);
    }

    public EntityCactusThorn(World world, EntityLivingBase shooter)
    {
    	super(world, shooter);
    }
    
    @Override
    public void writeSpawnData(ByteBuf data) {
        data.writeInt(shootingEntity != null ? shootingEntity.getEntityId() : -1);
    }

    @Override
    public void readSpawnData(ByteBuf data) {
        final Entity shooter = world.getEntityByID(data.readInt());

        if (shooter instanceof EntityLivingBase) {
            this.shootingEntity = (EntityLivingBase)shooter;
        }
    }
    
    protected void onHit(RayTraceResult raytraceResult) {
    	super.onHit(raytraceResult);
        Entity entity = raytraceResult.entityHit;
    	
        if (entity instanceof EntityLivingBase) {
        	this.setDead();
        }
    }
	
	@Override
    public void onUpdate() {
        super.onUpdate();

        if (this.world.isRemote && !this.inGround) {
            this.world.spawnParticle(EnumParticleTypes.SPELL_INSTANT, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
        }
    }

	@Override
	protected ItemStack getArrowStack() {
		return new ItemStack(FishItems.CACTUS_THORN);
	}

}