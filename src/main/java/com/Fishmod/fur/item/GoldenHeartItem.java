package com.Fishmod.fur.item;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.init.FURItemRegistry;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class GoldenHeartItem extends FURItem {

	public GoldenHeartItem(Properties properties) {
		super(properties, 0, UseAnim.NONE, 1);
	}

	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repairWith) {
		return repairWith.getItem().equals(Items.GOLD_INGOT);
	}

	private static boolean isBlackListed(ItemStack stack) {
		if (stack.isEmpty() || stack.getItem().equals(FURItemRegistry.GOLDEN_HEART.get())) {
			return true;
		}
		String id = ForgeRegistries.ITEMS.getKey(stack.getItem()).toString();
		for (String entry : FURConfig.GoldenHeart_bl.get()) {
			if (id.equals(entry)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int itemSlot, boolean isSelected) {
		if (entity instanceof Player player && itemSlot < 9 && entity.tickCount % 20 == 0) {
			onTick(stack, player);
		}
	}

	public static void onTick(ItemStack stack, Player player) {
		boolean flag = false;

		if ((stack.getMaxDamage() > 0 && stack.getDamageValue() != stack.getMaxDamage()) || stack.getMaxDamage() == 0) {
			if (!player.hasEffect(MobEffects.REGENERATION) && player.isHurt() && FURConfig.GoldenHeart_GrantsRegeneration.get()) {
				player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 8 * 20, 0));
				flag = true;
			} else if (player.getHealth() == player.getMaxHealth() && FURConfig.GoldenHeart_RepairsEquipment.get()) {
				for (ItemStack item : player.getAllSlots()) {
					if (!isBlackListed(item) && item.getMaxDamage() != 0 && item.isDamageableItem()
							&& (item.isEnchantable() || item.isEnchanted()) && item.getDamageValue() > 0) {
						item.setDamageValue(Math.max(item.getDamageValue() - 1, 0));
						flag = true;
					}
				}
			}
		}

		if (flag && !player.isCreative() && stack.getMaxDamage() != 0
				&& player.getRandom().nextInt(100) < FURConfig.GoldenHeart_dur.get()) {
			stack.hurtAndBreak(1, player, e -> e.broadcastBreakEvent(EquipmentSlot.OFFHAND));
		}
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
		if (ModList.get().isLoaded("curios")) {
			return new CuriosProvider(stack);
		}
		return super.initCapabilities(stack, nbt);
	}

	private static class CuriosProvider implements ICapabilityProvider {
		private final LazyOptional<ICurio> curio;

		CuriosProvider(ItemStack stack) {
			this.curio = LazyOptional.of(() -> new ICurio() {
				@Override
				public ItemStack getStack() {
					return stack;
				}

				@Override
				public void curioTick(SlotContext slotContext) {
					LivingEntity entity = slotContext.entity();
					if (entity instanceof Player player && entity.tickCount % 20 == 0) {
						onTick(stack, player);
					}
				}
			});
		}

		@Nonnull
		@Override
		public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
			return CuriosCapability.ITEM.orEmpty(cap, this.curio);
		}
	}
}
