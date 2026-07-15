package com.Fishmod.fur.item;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.Fishmod.fur.entities.tameable.SpectralCutlassEntity;
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
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

/**
 * Spectral Cutlass — right-click releases a flying phantom blade ({@link SpectralCutlassEntity}) that
 * fights alongside the player for {@link #SUMMON_DURATION} ticks. The physical item leaves the
 * inventory during the summon (the entity carries the real {@link ItemStack}); it returns on expiry
 * or drops at the death location if the blade is slain.
 *
 * <p>Durability is a soft resource: the blade spends it while attacking, but the item is designed to
 * never break. {@link #damageItem(ItemStack, int, LivingEntity, Consumer)} clamps every point of wear
 * so the damage value can never exceed {@code maxDamage - 1}.
 */
public class SpectralCutlassItem extends FURWeaponItem {

	/** 30 seconds — matches both the blade's lifetime and the per-item summon cooldown. */
	public static final int SUMMON_DURATION = 600;

	public SpectralCutlassItem(Properties properties, Tier material, int damage, float attackspeed, double reach, Supplier<Item> repair, Boolean hasDesc) {
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
			// Spawn ~2 blocks in front of the player, but shorten the distance if a wall is in the way so
			// the blade sits against the wall instead of inside/behind it.
			Vec3 eye = player.getEyePosition();
			Vec3 look = player.getLookAngle();
			Vec3 target = eye.add(look.scale(2.0D));
			BlockHitResult hit = level.clip(new ClipContext(eye, target, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
			double dist = hit.getType() == HitResult.Type.BLOCK ? Math.max(0.0D, eye.distanceTo(hit.getLocation()) - 0.5D) : 2.0D;
			Vec3 spawnPos = eye.add(look.scale(dist));
			SpectralCutlassEntity blade = new SpectralCutlassEntity(FUREntityRegistry.SPECTRAL_CUTLASS.get(), level);
			blade.moveTo(spawnPos.x, spawnPos.y, spawnPos.z, player.getYRot(), 0.0F);
			blade.setTame(true);
			blade.setOwnerUUID(player.getUUID());
			blade.setPersistenceRequired();

			// Transfer the REAL stack into the entity, then remove it from the inventory.
			ItemStack carried = stack.copy();
			carried.setCount(1);
			blade.setCarriedStack(carried);
			blade.setLifeTicks(SUMMON_DURATION);
			// Lock the creative flag at summon time: creative keeps its item and gets nothing back.
			blade.setFromCreative(player.getAbilities().instabuild);
			// Unbreaking on the item proportionally raises the blade's max health.
			blade.applyUnbreakingHealthBonus(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.UNBREAKING, stack));
			blade.setHealth(blade.getMaxHealth());
			serverLevel.addFreshEntity(blade);
			// Survival consumes the item; creative keeps it (setCount(0) is a no-op there anyway, but the
			// blade's fromCreative flag ensures nothing is returned/dropped, so no net duplication).
			if (!player.getAbilities().instabuild) {
				stack.setCount(0);
			}

			serverLevel.playSound(null, spawnPos.x, spawnPos.y, spawnPos.z, SoundEvents.SOUL_ESCAPE, SoundSource.PLAYERS, 1.0F, 1.0F);
			serverLevel.sendParticles(ParticleTypes.SOUL, spawnPos.x, spawnPos.y, spawnPos.z, 24, 0.3D, 0.3D, 0.3D, 0.02D);
		}

		// Per-Item-class cooldown: intentionally blocks cycling multiple daggers within one summon.
		player.getCooldowns().addCooldown(this, SUMMON_DURATION);
		player.awardStat(Stats.ITEM_USED.get(this));
		return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
	}
}
