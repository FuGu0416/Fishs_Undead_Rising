package com.Fishmod.fur.item;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.Fishmod.fur.entities.tameable.SpectralDaggerEntity;
import com.Fishmod.fur.init.FUREntityRegistry;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * Spectral Dagger — right-click releases a flying phantom blade ({@link SpectralDaggerEntity}) that
 * fights alongside the player for {@link #SUMMON_DURATION} ticks. The physical item leaves the
 * inventory during the summon (the entity carries the real {@link ItemStack}); it returns on expiry
 * or drops at the death location if the blade is slain.
 *
 * <p>Durability is a soft resource: the blade spends it while attacking, but the item is designed to
 * never break. {@link #damageItem(ItemStack, int, LivingEntity, Consumer)} clamps every point of wear
 * so the damage value can never exceed {@code maxDamage - 1}.
 */
public class SpectralDaggerItem extends FURWeaponItem {

	/** 30 seconds — matches both the blade's lifetime and the per-item summon cooldown. */
	public static final int SUMMON_DURATION = 600;

	public SpectralDaggerItem(Properties properties, Tier material, int damage, float attackspeed, double reach, Supplier<Item> repair, Boolean hasDesc) {
		super(properties, material, damage, attackspeed, reach, repair, hasDesc);
	}

	/**
	 * Durability must NEVER reach 0. Clamp any wear (melee use or the blade's per-hit drain) so the
	 * resulting damage value never passes {@code maxDamage - 1}. Returns the amount actually applied.
	 */
	@Override
	public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
		int room = Math.max(0, (stack.getMaxDamage() - 1) - stack.getDamageValue());
		return Math.min(amount, room);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);

		// Durability exhausted -> summon fails.
		if (stack.getDamageValue() >= stack.getMaxDamage() - 1) {
			if (!level.isClientSide()) {
				level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.DISPENSER_FAIL, SoundSource.PLAYERS, 0.8F, 1.0F);
			}
			return InteractionResultHolder.fail(stack);
		}

		if (level instanceof ServerLevel serverLevel) {
			Vec3 eye = player.getEyePosition();
			SpectralDaggerEntity blade = new SpectralDaggerEntity(FUREntityRegistry.SPECTRAL_DAGGER.get(), level);
			blade.moveTo(eye.x, eye.y, eye.z, player.getYRot(), 0.0F);
			blade.setTame(true);
			blade.setOwnerUUID(player.getUUID());
			blade.setPersistenceRequired();

			// Transfer the REAL stack into the entity, then remove it from the inventory.
			ItemStack carried = stack.copy();
			carried.setCount(1);
			blade.setCarriedStack(carried);
			blade.setLifeTicks(SUMMON_DURATION);
			blade.setHealth(blade.getMaxHealth());
			serverLevel.addFreshEntity(blade);
			stack.setCount(0);

			serverLevel.playSound(null, eye.x, eye.y, eye.z, SoundEvents.SOUL_ESCAPE, SoundSource.PLAYERS, 1.0F, 1.0F);
			serverLevel.sendParticles(ParticleTypes.SOUL, eye.x, eye.y, eye.z, 24, 0.3D, 0.3D, 0.3D, 0.02D);
		}

		// Per-Item-class cooldown: intentionally blocks cycling multiple daggers within one summon.
		player.getCooldowns().addCooldown(this, SUMMON_DURATION);
		player.awardStat(Stats.ITEM_USED.get(this));
		return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
	}
}
