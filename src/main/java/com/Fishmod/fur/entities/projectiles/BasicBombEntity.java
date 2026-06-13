package com.Fishmod.fur.entities.projectiles;

import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURItemRegistry;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

public class BasicBombEntity extends ThrowableItemProjectile {		
	Item getDefaultItem = FURItemRegistry.BASIC_BOMB.get();
	SoundEvent usedSound = SoundEvents.GENERIC_EXPLODE;
	float radius = 2.0F;

    @SuppressWarnings("unchecked")
	public BasicBombEntity(EntityType<?> entityType, Level worldIn) {
    	super((EntityType<? extends BasicBombEntity>) entityType, worldIn);
    	
    	if (entityType.equals(FUREntityRegistry.HOLY_GRENADE.get())) {
    		this.getDefaultItem = FURItemRegistry.HOLY_GRENADE.get();
    	} else if (entityType.equals(FUREntityRegistry.GHOST_BOMB.get())) {
    		this.getDefaultItem = FURItemRegistry.GHOST_BOMB.get();
    	} else if (entityType.equals(FUREntityRegistry.SONIC_BOMB.get())) {
    		this.getDefaultItem = FURItemRegistry.SONIC_BOMB.get();
    	}
    }

    public BasicBombEntity(EntityType<? extends BasicBombEntity> entityType, LivingEntity throwerIn, Level worldIn) {
    	super(entityType, throwerIn, worldIn);
    	
    	if (entityType.equals(FUREntityRegistry.HOLY_GRENADE.get())) {
    		this.getDefaultItem = FURItemRegistry.HOLY_GRENADE.get();
    	} else if (entityType.equals(FUREntityRegistry.GHOST_BOMB.get())) {
    		this.getDefaultItem = FURItemRegistry.GHOST_BOMB.get();
    	} else if (entityType.equals(FUREntityRegistry.SONIC_BOMB.get())) {
    		this.getDefaultItem = FURItemRegistry.SONIC_BOMB.get();
    	}
    }

    public BasicBombEntity(EntityType<? extends BasicBombEntity> entityType, double x, double y, double z, Level worldIn) {
    	super(entityType, x, y, z, worldIn);
    	
    	if (entityType.equals(FUREntityRegistry.HOLY_GRENADE.get())) {
    		this.getDefaultItem = FURItemRegistry.HOLY_GRENADE.get();
    	} else if (entityType.equals(FUREntityRegistry.GHOST_BOMB.get())) {
    		this.getDefaultItem = FURItemRegistry.GHOST_BOMB.get();
    	} else if (entityType.equals(FUREntityRegistry.SONIC_BOMB.get())) {
    		this.getDefaultItem = FURItemRegistry.SONIC_BOMB.get();
    	}
    }
    
    public BasicBombEntity(EntityType<? extends BasicBombEntity> entityType, Level worldIn, SoundEvent soundIn, float radiusIn) {
    	super(entityType, worldIn);
    	this.usedSound = soundIn;
    	this.radius = radiusIn;
    	
    	if (entityType.equals(FUREntityRegistry.HOLY_GRENADE.get())) {
    		this.getDefaultItem = FURItemRegistry.HOLY_GRENADE.get();
    	} else if (entityType.equals(FUREntityRegistry.GHOST_BOMB.get())) {
    		this.getDefaultItem = FURItemRegistry.GHOST_BOMB.get();
    	} else if (entityType.equals(FUREntityRegistry.SONIC_BOMB.get())) {
    		this.getDefaultItem = FURItemRegistry.SONIC_BOMB.get();
    	}
    }

    public BasicBombEntity(EntityType<? extends BasicBombEntity> entityType, LivingEntity throwerIn, Level worldIn, SoundEvent soundIn, float radiusIn) {
    	super(entityType, throwerIn, worldIn);
    	this.usedSound = soundIn;
    	this.radius = radiusIn;
    	
    	if (entityType.equals(FUREntityRegistry.HOLY_GRENADE.get())) {
    		this.getDefaultItem = FURItemRegistry.HOLY_GRENADE.get();
    	} else if (entityType.equals(FUREntityRegistry.GHOST_BOMB.get())) {
    		this.getDefaultItem = FURItemRegistry.GHOST_BOMB.get();
    	} else if (entityType.equals(FUREntityRegistry.SONIC_BOMB.get())) {
    		this.getDefaultItem = FURItemRegistry.SONIC_BOMB.get();
    	}
    }

    public BasicBombEntity(EntityType<? extends BasicBombEntity> entityType, double x, double y, double z, Level worldIn, SoundEvent soundIn, float radiusIn) {
    	super(entityType, x, y, z, worldIn);
    	this.usedSound = soundIn;
    	this.radius = radiusIn;
    	
    	if (entityType.equals(FUREntityRegistry.HOLY_GRENADE.get())) {
    		this.getDefaultItem = FURItemRegistry.HOLY_GRENADE.get();
    	} else if (entityType.equals(FUREntityRegistry.GHOST_BOMB.get())) {
    		this.getDefaultItem = FURItemRegistry.GHOST_BOMB.get();
    	} else if (entityType.equals(FUREntityRegistry.SONIC_BOMB.get())) {
    		this.getDefaultItem = FURItemRegistry.SONIC_BOMB.get();
    	}
    }
    
    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleEntityEvent(byte id) {
    	if (id == 3) {
    	   for(int i = 0; i < 8; ++i) {
    		   this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - 0.5D) * 0.08D, ((double)this.random.nextFloat() - 0.5D) * 0.08D, ((double)this.random.nextFloat() - 0.5D) * 0.08D);
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
	protected void onHit(HitResult result) {
		super.onHit(result);
        if (!this.level().isClientSide()) {
        	Wolf Dummy = EntityType.WOLF.create(this.level());
        	
        	if (this.getOwner() != null) {
	        	Dummy.setTame(true);
	        	Dummy.setOwnerUUID(this.getOwner().getUUID());
        	}
        	
        	Dummy.setCustomName(this.getName());
        	this.level().explode(Dummy, this.getX(), this.getY(), this.getZ(), this.radius, false, Level.ExplosionInteraction.NONE);
        	Dummy.discard();
        	this.level().playSound(null, this.blockPosition(), this.usedSound, SoundSource.BLOCKS, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);   	
        	this.discard();
        }
	}
	
	@Override
	protected Item getDefaultItem() {
		return this.getDefaultItem;
	}
		
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
