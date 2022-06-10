package com.Fishmod.mod_LavaCow.entities.projectiles;

import com.Fishmod.mod_LavaCow.init.FURItemRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class GhostBombEntity extends ProjectileItemEntity {

    public GhostBombEntity(EntityType<? extends GhostBombEntity> p_i48540_1_, World worldIn) {
        super(p_i48540_1_, worldIn);
    }

    public GhostBombEntity(EntityType<? extends GhostBombEntity> p_i48540_1_, LivingEntity throwerIn, World worldIn) {
        super(p_i48540_1_, throwerIn, worldIn);
    }

    public GhostBombEntity(EntityType<? extends GhostBombEntity> p_i48540_1_, double x, double y, double z, World worldIn) {
        super(p_i48540_1_, x, y, z, worldIn);
    }
    
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte p_70103_1_) {
       if (p_70103_1_ == 3) {
          for(int i = 0; i < 8; ++i) {
             this.level.addParticle(new ItemParticleData(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - 0.5D) * 0.08D, ((double)this.random.nextFloat() - 0.5D) * 0.08D, ((double)this.random.nextFloat() - 0.5D) * 0.08D);
          }
       }
    }
    
    /**
     * Gets the amount of gravity to apply to the thrown entity with each tick.
     */
    @Override
    protected float getGravity() {
        return 0.075F;
    }

	@Override
	protected void onHit(RayTraceResult result) {
		super.onHit(result);
        if (!this.level.isClientSide()) {
        	WolfEntity Dummy = EntityType.WOLF.create(this.level);
        	Dummy.tame((PlayerEntity) this.getOwner());
        	Dummy.setCustomName(this.getName());
        	this.level.explode(Dummy, this.getX(), this.getY(), this.getZ(), 4.0F, false, Explosion.Mode.NONE);
        	Dummy.remove();
        	this.level.playSound(null, new BlockPos(this.getX(), this.getY(), this.getZ()), FURSoundRegistry.BANSHEE_HURT, SoundCategory.BLOCKS, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
        	this.remove();
        }
	}

	@Override
	protected Item getDefaultItem() {
		return FURItemRegistry.GHOSTBOMB;
	}
	
    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
