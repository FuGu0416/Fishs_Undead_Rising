package com.Fishmod.fur.entities.projectiles;

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
	public int lifesteal = 0;
	public int corrosive = 0;
	public int baseDamage = 0;
	
	@SuppressWarnings("unchecked")
	public FangDaggerEntity(EntityType<?> p_i50158_1_, Level worldIn) {
		super((EntityType<? extends FangDaggerEntity>) p_i50158_1_, worldIn);
	}
	
	public FangDaggerEntity(Level worldIn, LivingEntity shooter) {
		super(FUREntityRegistry.FANG_DAGGER.get(), shooter, worldIn);
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
    
	protected void onHitEntity(EntityHitResult p_36757_) {
		super.onHitEntity(p_36757_);
        Entity entity = p_36757_.getEntity();
        float f = (float)this.getDeltaMovement().length() * 0.67F;
        int i = Mth.ceil(Mth.clamp((double)f * (this.baseDamage + this.getBonusDamage(entity)), 0.0D, (double)Integer.MAX_VALUE));

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

        	   if (this.lifesteal > 0) {
        		   if (entity1 instanceof LivingEntity) {
        			   ((LivingEntity)entity1).heal((this.baseDamage + this.getBonusDamage(entity)) * this.lifesteal * 0.05F);
        		   }
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

           this.playSound(this.getDefaultHitGroundSoundEvent(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
           if (this.getPierceLevel() <= 0) {
        	   this.discard();
           }
        } else {
           entity.setRemainingFireTicks(k);
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
