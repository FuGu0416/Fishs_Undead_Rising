package com.Fishmod.mod_LavaCow.entities.projectiles;

import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class BasicBombEntity extends ProjectileItemEntity {		
	Item getDefaultItem = FURItemRegistry.BASIC_BOMB;
	SoundEvent usedSound = SoundEvents.GENERIC_EXPLODE;
	float radius = 2.0F;
	
    public BasicBombEntity(EntityType<? extends BasicBombEntity> p_i48540_1_, World worldIn) {
    	super(p_i48540_1_, worldIn);
    	
    	if (p_i48540_1_.equals(FUREntityRegistry.HOLY_GRENADE)) {
    		this.getDefaultItem = FURItemRegistry.HOLY_GRENADE;
    	} else if (p_i48540_1_.equals(FUREntityRegistry.GHOSTBOMB)) {
    		this.getDefaultItem = FURItemRegistry.GHOSTBOMB;
    	} else if (p_i48540_1_.equals(FUREntityRegistry.SONICBOMB)) {
    		this.getDefaultItem = FURItemRegistry.SONICBOMB;
    	}
    }

    public BasicBombEntity(EntityType<? extends BasicBombEntity> p_i48540_1_, LivingEntity throwerIn, World worldIn) {
    	super(p_i48540_1_, throwerIn, worldIn);
    }

    public BasicBombEntity(EntityType<? extends BasicBombEntity> p_i48540_1_, double x, double y, double z, World worldIn) {
    	super(p_i48540_1_, x, y, z, worldIn);
    }
    
    public BasicBombEntity(EntityType<? extends BasicBombEntity> p_i48540_1_, World worldIn, SoundEvent soundIn, float radiusIn) {
    	super(p_i48540_1_, worldIn);
    	this.usedSound = soundIn;
    	this.radius = radiusIn;
    }

    public BasicBombEntity(EntityType<? extends BasicBombEntity> p_i48540_1_, LivingEntity throwerIn, World worldIn, SoundEvent soundIn, float radiusIn) {
    	super(p_i48540_1_, throwerIn, worldIn);
    	this.usedSound = soundIn;
    	this.radius = radiusIn;
    }

    public BasicBombEntity(EntityType<? extends BasicBombEntity> p_i48540_1_, double x, double y, double z, World worldIn, SoundEvent soundIn, float radiusIn) {
    	super(p_i48540_1_, x, y, z, worldIn);
    	this.usedSound = soundIn;
    	this.radius = radiusIn;
    }
    
    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleEntityEvent(byte id) {
    	if (id == 3) {
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
        	
        	if (this.getOwner() != null) {
	        	Dummy.setTame(true);
	        	Dummy.setOwnerUUID(this.getOwner().getUUID());
        	}
        	
        	Dummy.setCustomName(this.getName());
        	this.level.explode(Dummy, this.getX(), this.getY(), this.getZ(), this.radius, false, Explosion.Mode.NONE);
        	Dummy.remove();
        	this.level.playSound(null, this.blockPosition(), this.usedSound, SoundCategory.BLOCKS, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);   	
        	this.remove();
        }
	}
	
	@Override
	protected Item getDefaultItem() {
		return this.getDefaultItem;
	}
	

	
    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
    
}
