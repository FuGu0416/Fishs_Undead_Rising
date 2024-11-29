package com.Fishmod.mod_LavaCow.entities.projectiles;

import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

public class FURArrowEntity extends AbstractArrowEntity implements IEntityAdditionalSpawnData {
	Item getDefaultItem = FURItemRegistry.BASIC_BOMB;
	
	public FURArrowEntity(EntityType<? extends FURArrowEntity> p_i50158_1_, World worldIn) {
		super(p_i50158_1_, worldIn);
		
    	if (p_i50158_1_.equals(FUREntityRegistry.GHOUL_ARROW)) {
    		this.getDefaultItem = FURItemRegistry.GHOUL_ARROW;
    	} else if (p_i50158_1_.equals(FUREntityRegistry.FANG_ARROW)) {
    		this.getDefaultItem = FURItemRegistry.FANG_ARROW;
    	}
	}

	public FURArrowEntity(EntityType<? extends FURArrowEntity> p_i50158_1_, World worldIn, LivingEntity shooter) {
		super(p_i50158_1_, shooter, worldIn);
	}

	public FURArrowEntity(EntityType<? extends FURArrowEntity> p_i50158_1_, World worldIn, double posX, double posY, double posZ) {
		super(p_i50158_1_, posX, posY, posZ, worldIn);
	}
	
	protected void doPostHurtEffects(LivingEntity p_184548_1_) {
		super.doPostHurtEffects(p_184548_1_);
	}
	
	@Override
	public void writeSpawnData(PacketBuffer buffer) {
		buffer.writeInt(this.getOwner() != null ? this.getOwner().getId() : -1);		
	}

	@Override
	public void readSpawnData(PacketBuffer additionalData) {
        final Entity shooter = this.level.getEntity(additionalData.readInt());

        if (shooter != null) {
            this.setOwner(shooter);
        }
	}

	@Override
	public void tick() {
		super.tick();
		if (this.level.isClientSide && !this.inGround) {
			this.level.addParticle(ParticleTypes.INSTANT_EFFECT, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	protected ItemStack getPickupItem() {
		return new ItemStack(FURItemRegistry.GHOUL_ARROW);
	}
	
    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
