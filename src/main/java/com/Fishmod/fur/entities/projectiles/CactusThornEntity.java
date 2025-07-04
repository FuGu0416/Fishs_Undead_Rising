package com.Fishmod.fur.entities.projectiles;

import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURItemRegistry;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;

public class CactusThornEntity extends AbstractArrow implements IEntityAdditionalSpawnData {
	public CactusThornEntity(EntityType<?> p_i50158_1_, Level worldIn) {
		super((EntityType<? extends CactusThornEntity>) p_i50158_1_, worldIn);
	}
	
	public CactusThornEntity(Level worldIn, LivingEntity shooter) {
		super(FUREntityRegistry.CACTUS_THORN.get(), shooter, worldIn);
	}

	public CactusThornEntity(Level worldIn, double posX, double posY, double posZ) {
		super(FUREntityRegistry.CACTUS_THORN.get(), posX, posY, posZ, worldIn);
	}	

	@Override
	public void tick() {
		super.tick();
		if (!this.inGround) {
			this.level().addParticle(ParticleTypes.INSTANT_EFFECT, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	protected ItemStack getPickupItem() {
		return new ItemStack(FURItemRegistry.CACTUS_THORN.get());
	}
	
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

	@Override
	public void writeSpawnData(FriendlyByteBuf buffer) {
		buffer.writeInt(this.getOwner() != null ? this.getOwner().getId() : -1);
		
	}

	@Override
	public void readSpawnData(FriendlyByteBuf additionalData) {
        final Entity shooter = this.level().getEntity(additionalData.readInt());

        if (shooter != null) {
            this.setOwner(shooter);
        }		
	}
}
