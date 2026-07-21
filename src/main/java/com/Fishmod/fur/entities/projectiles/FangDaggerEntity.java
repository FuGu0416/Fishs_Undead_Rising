package com.Fishmod.fur.entities.projectiles;

import com.Fishmod.fur.entities.tameable.FURTameableEntity;
import com.Fishmod.fur.init.FUREffectRegistry;
import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURItemRegistry;
import com.Fishmod.fur.init.FURSoundRegistry;

import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;

public class FangDaggerEntity extends AbstractArrow implements IEntityAdditionalSpawnData {
	public static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(FangDaggerEntity.class, EntityDataSerializers.ITEM_STACK);
	public Direction blockSide = null;
	public int fire_aspect = 0;
	public int sharpness = 0;
	public int knockback = 0;
	public int bane_of_arthropods = 0;
	public int smite = 0;
	public int corrosive = 0;
	public int baseDamage = 0;
	public int bouncesRemaining = 0;
	/** Flat damage carried by ricochet-spawned daggers; < 0 means normal velocity-scaled damage. */
	private float ricochetDamage = -1.0F;
	/** Chained daggers spawn inside the previous victim's hitbox; skip colliding with it. */
	private int ricochetIgnoreId = -1;

	@SuppressWarnings("unchecked")
	public FangDaggerEntity(EntityType<?> entityType, Level worldIn) {
		super((EntityType<? extends FangDaggerEntity>) entityType, worldIn);
	}

	public FangDaggerEntity(Level worldIn, LivingEntity shooter) {
		super(FUREntityRegistry.FANG_DAGGER.get(), shooter, worldIn);
	}

	public FangDaggerEntity(Level worldIn, LivingEntity shooter, int bouncesRemaining, float ricochetDamage) {
		super(FUREntityRegistry.FANG_DAGGER.get(), shooter, worldIn);
		this.bouncesRemaining = bouncesRemaining;
		this.ricochetDamage = ricochetDamage;
	}

	public FangDaggerEntity(Level worldIn, double posX, double posY, double posZ) {
		super(FUREntityRegistry.FANG_DAGGER.get(), posX, posY, posZ, worldIn);
	}	
	
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_ITEM_STACK, ItemStack.EMPTY);
    }

	@Override
	public void tick() {
		super.tick();
		if (!this.inGround) {
			this.level().addParticle(ParticleTypes.INSTANT_EFFECT, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
			 if (this.fire_aspect > 0 && !this.isOnFire()) {
				 this.setRemainingFireTicks(5);
			 }
		}
	}
	
    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
    	super.onHitBlock(hitResult);
    	this.blockSide = hitResult.getDirection();
    }
    
    public float getBonusDamage(Entity entity) {
    	if (!(entity instanceof LivingEntity livingentity)) return 0;
    	return (this.sharpness > 0 ? (0.5f * this.sharpness + 0.5f) : 0.0f)
				+ (livingentity.getMobType().equals(MobType.ARTHROPOD) ? (float)bane_of_arthropods * 2.5f : 0)
				+ (livingentity.getMobType().equals(MobType.UNDEAD) ? (float)smite * 2.5f : 0);
    }
    
	@Override
	protected boolean canHitEntity(Entity target) {
		return target.getId() != this.ricochetIgnoreId && super.canHitEntity(target);
	}

	protected void onHitEntity(EntityHitResult result) {
		// Chained daggers arrive well inside the target's 20-tick invulnerability window with a
		// lower (decayed) damage value, which LivingEntity#hurt would reject outright — clear the
		// window so each bounce actually lands.
		if (this.ricochetDamage >= 0.0F && result.getEntity() instanceof LivingEntity hitliving) {
			hitliving.invulnerableTime = 0;
		}
		super.onHitEntity(result);
        Entity entity = result.getEntity();
        float f = (float)this.getDeltaMovement().length() * 0.67F;
        int i = this.ricochetDamage >= 0.0F
        		? Mth.ceil(this.ricochetDamage)
        		: Mth.ceil(Mth.clamp((double)f * (this.baseDamage + this.getBonusDamage(entity)), 0.0D, (double)Integer.MAX_VALUE));

        Entity entity1 = this.getOwner();
        DamageSource damagesource;
        if (entity1 == null) {
        	damagesource = this.damageSources().arrow(this, this);
        } else {
        	damagesource = this.damageSources().arrow(this, entity1);
        	if (entity1 instanceof LivingEntity) {
        		((LivingEntity)entity1).setLastHurtMob(entity);
        	}
        }

        boolean flag = entity.getType() == EntityType.ENDERMAN;
        int k = entity.getRemainingFireTicks();

        if (this.fire_aspect > 0 && !flag)
        	entity.setSecondsOnFire((this.fire_aspect * 4) - 1);

        if (entity.hurt(damagesource, (float)i)) {
           if (flag) {
        	   return;
           }

           if (entity instanceof LivingEntity livingentity) {
        	   if (!this.level().isClientSide && this.getPierceLevel() <= 0) {
        		   livingentity.setArrowCount(livingentity.getArrowCount() + 1);
        	   }

        	   if (this.knockback > 0) {
        		   double d0 = Math.max(0.0D, 1.0D - livingentity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
        		   Vec3 vec3 = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale((double)this.knockback * 0.6D * d0);
        		   if (vec3.lengthSqr() > 0.0D) {
        			   livingentity.push(vec3.x, 0.1D, vec3.z);
        		   }
        	   }

        	   if (this.bane_of_arthropods > 0 && (livingentity.getMobType().equals(MobType.ARTHROPOD))) {
        		   int l = 20 + this.random.nextInt(10 * bane_of_arthropods);
        		   livingentity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, l, 3));
        	   }
	            
        	   if (this.corrosive > 0) {
        		   livingentity.addEffect(new MobEffectInstance(FUREffectRegistry.CORRODED.get(), 4 * 20, this.corrosive - 1));
        	   }

        	   if (this.getRenderItem().getItem() == FURItemRegistry.VESPA_DAGGER.get()) {
        		   livingentity.addEffect(new MobEffectInstance(MobEffects.POISON, 8 * 20, 1));
        	   }

        	   if (!this.level().isClientSide && entity1 instanceof LivingEntity) {
        		   EnchantmentHelper.doPostHurtEffects(livingentity, entity1);
        		   EnchantmentHelper.doPostDamageEffects((LivingEntity)entity1, livingentity);
        	   }

        	   this.doPostHurtEffects(livingentity);
        	   if (entity1 != null && livingentity != entity1 && livingentity instanceof Player && entity1 instanceof ServerPlayer && !this.isSilent()) {
        		   ((ServerPlayer)entity1).connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
        	   }
           }

           if (!this.level().isClientSide) {
        	   this.tryRicochet(entity, this.ricochetDamage >= 0.0F ? this.ricochetDamage : (float)i);
           }

           this.playSound(this.getDefaultHitGroundSoundEvent(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
           if (this.getPierceLevel() <= 0) {
        	   this.discard();
           }
        } else {
           entity.setRemainingFireTicks(k);
           // hurt() also fails when the vanilla arrow damage from super.onHitEntity already killed
           // the target this impact (isDeadOrDying) — the chain must still continue from the corpse.
           if (!this.level().isClientSide && this.tryRicochet(entity, this.ricochetDamage >= 0.0F ? this.ricochetDamage : (float)i)) {
        	   this.discard();
        	   return;
           }
           this.setDeltaMovement(this.getDeltaMovement().scale(-0.1D));
           this.setYRot(this.getYRot() + 180.0F);
           this.yRotO += 180.0F;
           if (!this.level().isClientSide && this.getDeltaMovement().lengthSqr() < 1.0E-7D) {
        	   this.discard();
           }
        }

	}
	
	protected SoundEvent getDefaultHitGroundSoundEvent() {
		return FURSoundRegistry.RANDOM_FANG_DAGGER_HIT.get();
	}

	/**
	 * Ricochet enchantment: after a hit, chain a new dagger toward the nearest valid target.
	 * Chained daggers carry a flat, pre-reduced damage value and never re-read the ItemStack's
	 * enchantment level, so the chain always decays. Returns whether a chained dagger spawned.
	 */
	private boolean tryRicochet(Entity hitEntity, float dealtDamage) {
		if (this.bouncesRemaining <= 0) return false;

		float nextDamage = dealtDamage * 0.8F;
		if (nextDamage < 1.0F) return false;

		Entity owner = this.getOwner();
		LivingEntity target = null;
		double bestDistSqr = Double.MAX_VALUE;

		for (LivingEntity candidate : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(4.0D))) {
			if (candidate == hitEntity || candidate == owner || !candidate.isAlive() || candidate.isSpectator() || !candidate.attackable()) continue;
			if (candidate instanceof FURTameableEntity tameable && owner != null && tameable.getOwner() == owner) continue;

			double distSqr = this.distanceToSqr(candidate);
			if (distSqr < bestDistSqr) {
				bestDistSqr = distSqr;
				target = candidate;
			}
		}

		if (target == null) return false;

		FangDaggerEntity dagger;
		if (owner instanceof LivingEntity livingowner) {
			dagger = new FangDaggerEntity(this.level(), livingowner, this.bouncesRemaining - 1, nextDamage);
		} else {
			dagger = new FangDaggerEntity(this.level(), this.getX(), this.getY(), this.getZ());
			dagger.bouncesRemaining = this.bouncesRemaining - 1;
			dagger.ricochetDamage = nextDamage;
		}

		dagger.setPos(this.getX(), this.getY(), this.getZ());
		dagger.shoot(target.getX() - this.getX(), target.getY(0.5D) - this.getY(), target.getZ() - this.getZ(), 1.6F, 0.0F);
		// Vanilla AbstractArrow#onHitEntity deals its own velocity * getBaseDamage() hit before this
		// class's flat ricochetDamage is applied; zero it so late (low-damage) bounces aren't
		// overshadowed by the fixed vanilla portion and the decay sequence stays exact.
		dagger.setBaseDamage(0.0D);
		dagger.ricochetIgnoreId = hitEntity.getId();
		dagger.fire_aspect = this.fire_aspect;
		dagger.knockback = this.knockback;
		dagger.corrosive = this.corrosive;
		dagger.setRenderItem(this.getRenderItem());
		dagger.pickup = AbstractArrow.Pickup.DISALLOWED;
		this.level().addFreshEntity(dagger);
		return true;
	}
    
    public boolean isInGround() {
    	return this.inGround;
    }

	@Override
	protected ItemStack getPickupItem() {
		return ItemStack.EMPTY;
	}
	
    public void setRenderItem(ItemStack stack) {
        this.getEntityData().set(DATA_ITEM_STACK, stack.copyWithCount(1));
    }

    public ItemStack getRenderItem() {
        return this.getEntityData().get(DATA_ITEM_STACK);
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
