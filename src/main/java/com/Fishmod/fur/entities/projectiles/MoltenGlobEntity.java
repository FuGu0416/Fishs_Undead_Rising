package com.Fishmod.fur.entities.projectiles;

import com.Fishmod.fur.entities.tameable.SalamanderEntity;
import com.Fishmod.fur.init.FURParticleRegistry;
import com.Fishmod.fur.init.FURSoundRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

/**
 * Molten Glob — a thrown blob of molten rock. Behaves like a fireball (it inherits the
 * mod's {@link EnchantableFireBallEntity}, which itself extends vanilla {@code Fireball}),
 * but trails lava the whole way and leaves fire where it lands.
 */
public class MoltenGlobEntity extends EnchantableFireBallEntity {

	/** Seconds the struck entity keeps burning after a direct hit. */
	private static final int BURN_SECONDS = 8;

	/**
	 * Constant downward acceleration (blocks/tick²). Unlike a straight-flying fireball, the glob
	 * is heavy: AbstractHurtingProjectile adds {@code yPower} to the motion every tick, so a
	 * negative value here bends the flight path into a parabola. Tunable alongside the shooter's
	 * launch "curve" (see FURRangeAttackGoal) — higher gravity needs a higher curve to still reach.
	 */
	private static final double GRAVITY = 0.03D;

	/**
	 * Visual variant, mirroring the shooter Salamander's skin (0 = flame, 1 = soul). Fixed when the
	 * glob is launched and carried to the client in the spawn packet so the renderer can pick the
	 * matching cube texture without resolving the (possibly unloaded) owner client-side.
	 */
	private int variant = 0;

	@SuppressWarnings("unchecked")
	public MoltenGlobEntity(EntityType<?> entityType, Level worldIn) {
		super((EntityType<? extends MoltenGlobEntity>) entityType, worldIn);
		this.yPower = -GRAVITY;
	}

	public MoltenGlobEntity(EntityType<? extends MoltenGlobEntity> entityType, LivingEntity shooter, double accelX, double accelY, double accelZ, Level worldIn) {
		super(entityType, shooter, accelX, accelY, accelZ, worldIn);
	}

	public MoltenGlobEntity(EntityType<? extends MoltenGlobEntity> entityType, double x, double y, double z, double accelX, double accelY, double accelZ, Level worldIn) {
		super(entityType, x, y, z, accelX, accelY, accelZ, worldIn);
	}

	/** Visual variant (0 = flame, 1 = soul); set from the shooter's skin and synced to the client. */
	public int getVariant() {
		return this.variant;
	}

	@Override
	public void writeSpawnData(FriendlyByteBuf buffer) {
		super.writeSpawnData(buffer);
		// Owner is already set by the time the spawn packet is built, so derive the variant here.
		if (this.getOwner() instanceof SalamanderEntity salamander) {
			this.variant = salamander.getSkin();
		}
		buffer.writeInt(this.variant);
	}

	@Override
	public void readSpawnData(FriendlyByteBuf additionalData) {
		super.readSpawnData(additionalData);
		this.variant = additionalData.readInt();
	}

	/**
	 * Whether this is the soul-skin glob (variant 1), which trails soul/soul-fire particles instead
	 * of lava/flame. Prefers the live owner skin (authoritative on the server); falls back to the
	 * synced variant when the owner isn't available (e.g. on the client).
	 */
	private boolean isSoul() {
		if (this.getOwner() instanceof SalamanderEntity salamander) {
			return salamander.getSkin() == 1;
		}
		return this.variant == 1;
	}

	/**
	 * Called to update the entity's position/logic. Trails lava and flame particles for the
	 * whole flight so the glob reads as molten rather than a plain fireball.
	 */
	@Override
	public void tick() {
		if (this.level().isClientSide) {
			boolean soul = this.isSoul();
			this.level().addParticle(soul ? FURParticleRegistry.SPIRIT.get() : ParticleTypes.LAVA, this.getX(), this.getY() + 0.25D, this.getZ(), 0.0D, 0.0D, 0.0D);
			this.level().addParticle(soul ? ParticleTypes.SOUL_FIRE_FLAME : ParticleTypes.FLAME, this.getX(), this.getY() + 0.25D, this.getZ(), 0.0D, 0.0D, 0.0D);
		}

		super.tick();
	}

	@Override
	protected void onHitEntity(EntityHitResult result) {
		super.onHitEntity(result);
		if (!this.level().isClientSide) {
			Entity entity = result.getEntity();
			Entity owner = this.getOwner();

			// A Salamander's glob hits for the lizard's full attack value (matches the war fireball).
			if (owner instanceof SalamanderEntity)
				this.setDamage((float) ((LivingEntity) owner).getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue());

			if (entity.fireImmune()) {
				entity.hurt(this.damageSources().fireball(this, owner), this.getDamage());
			} else {
				entity.setSecondsOnFire(BURN_SECONDS + this.flame);
				entity.hurt(this.damageSources().indirectMagic(this, owner), this.getDamage());
			}

			if (owner instanceof LivingEntity) {
				if (this.knockbackStrength > 0) {
					Vec3 push = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale((double) this.knockbackStrength * 0.6D);
					if (push.lengthSqr() > 0.0D) {
						entity.push(push.x, 0.1D, push.z);
					}
				}
				this.doEnchantDamageEffects((LivingEntity) owner, entity);
			}
		}
	}

	@Override
	protected void onHitBlock(BlockHitResult result) {
		super.onHitBlock(result);
		if (!this.level().isClientSide) {
			Entity owner = this.getOwner();
			// Respect mob griefing: only scorch the world when a player threw it, or
			// when the gamerule allows mobs to do so.
			if (owner == null || !(owner instanceof Mob) || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level(), owner)) {
				BlockPos blockpos = result.getBlockPos().relative(result.getDirection());
				if (this.level().isEmptyBlock(blockpos)) {
					this.level().setBlockAndUpdate(blockpos, BaseFireBlock.getState(this.level(), blockpos));
				}
			}
		}
	}

	/**
	 * Runs for both block and entity impacts (the super dispatches to onHitBlock/onHitEntity, then
	 * EnchantableFireBallEntity discards the glob). On the server we leave a Molten Pool at the
	 * impact point that burns anything standing in it for a few seconds.
	 */
	@Override
	protected void onHit(HitResult result) {
		super.onHit(result);
		if (this.level() instanceof ServerLevel serverLevel) {
			Vec3 loc = result.getLocation();
			serverLevel.playSound(null, loc.x, loc.y, loc.z, FURSoundRegistry.RANDOM_MOLTEN_GLOB_IMPACT.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
			// Lava-splatter burst at the point of impact (the in-flight trail stops once the glob is discarded).
			serverLevel.sendParticles(this.isSoul() ? FURParticleRegistry.SPIRIT.get() : ParticleTypes.LAVA, loc.x, loc.y, loc.z, 12, 0.2D, 0.2D, 0.2D, 0.0D);
			this.spawnMoltenPool(loc);
		}
	}

	/** Leaves a lingering, burning Molten Pool (an AreaEffectCloud) at the given position. */
	private void spawnMoltenPool(Vec3 pos) {
		MoltenPoolEntity pool = new MoltenPoolEntity(this.level(), pos.x, pos.y, pos.z);
		if (this.getOwner() instanceof LivingEntity owner) {
			pool.setOwner(owner);
		}
		pool.setParticle(this.isSoul() ? ParticleTypes.SOUL_FIRE_FLAME : ParticleTypes.FLAME);   // flame disc (soul-fire for variant 1)
		pool.setRadius(2.0F);
		pool.setDuration(60);                    // 3 seconds (20 ticks/s)
		pool.setWaitTime(0);                     // active immediately
		pool.setRadiusOnUse(0.0F);
		pool.setRadiusPerTick(0.0F);
		this.level().addFreshEntity(pool);
	}

	@Override
	public boolean isPickable() {
		return false;
	}

	/** Visual sprite used by the ThrownItemRenderer — a glob of magma cream. */
	@Override
	protected ItemStack getItemRaw() {
		return new ItemStack(Items.MAGMA_CREAM);
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
