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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;

public class FURArrowEntity extends AbstractArrow implements IEntityAdditionalSpawnData {
	Item getDefaultItem = FURItemRegistry.BASIC_BOMB.get();
	
	@SuppressWarnings("unchecked")
	public FURArrowEntity(EntityType<?> p_i50158_1_, Level LevelIn) {
		super((EntityType<? extends FURArrowEntity>) p_i50158_1_, LevelIn);
		
    	if (p_i50158_1_.equals(FUREntityRegistry.GHOUL_ARROW.get())) {
    		this.getDefaultItem = FURItemRegistry.GHOUL_ARROW.get();
    	} else if (p_i50158_1_.equals(FUREntityRegistry.FANG_ARROW.get())) {
    		this.getDefaultItem = FURItemRegistry.FANG_ARROW.get();
    	}
	}

	public FURArrowEntity(EntityType<? extends FURArrowEntity> p_i50158_1_, Level LevelIn, LivingEntity shooter) {
		super(p_i50158_1_, shooter, LevelIn);
	}

	public FURArrowEntity(EntityType<? extends FURArrowEntity> p_i50158_1_, Level LevelIn, double posX, double posY, double posZ) {
		super(p_i50158_1_, posX, posY, posZ, LevelIn);
	}
	
	protected void doPostHurtEffects(LivingEntity p_184548_1_) {
		super.doPostHurtEffects(p_184548_1_);
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

	@Override
	public void tick() {
		super.tick();
		if (this.level().isClientSide && !this.inGround) {
			this.level().addParticle(ParticleTypes.INSTANT_EFFECT, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	protected ItemStack getPickupItem() {
		return new ItemStack(this.getDefaultItem);
	}
	
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
