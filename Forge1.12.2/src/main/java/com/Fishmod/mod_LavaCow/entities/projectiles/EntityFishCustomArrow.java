package com.Fishmod.mod_LavaCow.entities.projectiles;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

/* Arrow Types
 *
 * 0 -> Ghoulish
 * 1 -> Fang
 *
 */
public class EntityFishCustomArrow extends EntityArrow implements IEntityAdditionalSpawnData {
    private static final DataParameter<Integer> ARROW_TYPE = EntityDataManager.<Integer>createKey(EntityFishCustomArrow.class, DataSerializers.VARINT);
    private Item item;

    public EntityFishCustomArrow(World world) {
        super(world);
    }

    public EntityFishCustomArrow(World world, double x, double y, double z, Item arrow) {
        super(world, x, y, z);
        this.item = arrow;
    }

    public EntityFishCustomArrow(World world, EntityLivingBase shooter, Item arrow) {
        super(world, shooter);
        this.item = arrow;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(ARROW_TYPE, Integer.valueOf(0));
    }

    public int getArrowType() {
        return this.dataManager.get(ARROW_TYPE).intValue();
    }

    public void setArrowType(int type) {
        this.dataManager.set(ARROW_TYPE, Integer.valueOf(type));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (this.world.isRemote && !this.inGround) {
            // 0 - Ghoulish
            if (this.getArrowType() == 0) {
                this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
                // 1 - Fang
            } else if (this.getArrowType() == 1) {
                this.world.spawnParticle(EnumParticleTypes.CRIT_MAGIC, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(item, 1, 0);
    }

    // Fixes buggy projectile behavior on the client
    @Override
    public void writeSpawnData(ByteBuf data) {
        data.writeInt(shootingEntity != null ? shootingEntity.getEntityId() : -1);
    }

    @Override
    public void readSpawnData(ByteBuf data) {
        final Entity shooter = world.getEntityByID(data.readInt());

        if (shooter instanceof EntityLivingBase) {
            this.shootingEntity = (EntityLivingBase) shooter;
        }
    }
}
