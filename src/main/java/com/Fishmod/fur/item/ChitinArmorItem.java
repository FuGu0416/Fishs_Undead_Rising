package com.Fishmod.fur.item;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.init.FURItemRegistry;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ChitinArmorItem extends ArmorItem {

	// ── Set piece thresholds ─────────────────────────────────────────────────
	/** Pieces required for the 2-piece bonus (Arthropod detection dampening + anti-mounting). */
	public static final int TWO_PIECE_THRESHOLD = 2;
	/** Pieces required for the full-set bonus (silent sneaking + fall/vibration immunity). */
	public static final int FULLSET_THRESHOLD = 4;
	/** Fraction of a targeting Arthropod's normal detection range still effective against the wearer. */
	public static final double DETECTION_RANGE_FACTOR = 0.25D;
	/** Fall distance (in blocks) under which the full-set wearer takes no fall damage. */
	public static final float FALL_IMMUNITY_DISTANCE = 6.0F;

	/**
	 * Every vanilla block's footstep sound, used to pick footsteps out from a player's other
	 * self-sounds (hurt, eat, ...) which broadcast through the same position-based sound path.
	 */
	private static final Set<SoundEvent> STEP_SOUNDS = collectStepSounds();

	public ChitinArmorItem(ArmorItem.Type slot, Item.Properties properties) {
		super(FURArmorMaterial.CHITIN, slot, properties);
	}

	@Override
	public boolean isValidRepairItem(ItemStack armour, ItemStack material) {
		return material.getItem() == FURItemRegistry.CHITIN.get();
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		if (slot.equals(EquipmentSlot.LEGS)) {
			return mod_LavaCow.MODID + ":textures/armors/chitin/chitin_layer_2.png";
		} else {
			return mod_LavaCow.MODID + ":textures/armors/chitin/chitin_layer_1.png";
		}
	}

	// ── Static helpers called from FURServerEvents ───────────────────────────

	/**
	 * Returns the number of Chitin Armor pieces the given entity is wearing.
	 */
	public static int countChitinPieces(LivingEntity entity) {
		int count = 0;
		for (ItemStack stack : entity.getArmorSlots()) {
			if (stack.getItem() instanceof ChitinArmorItem)
				count++;
		}
		return count;
	}

	/**
	 * Returns true if the entity is wearing at least {@code count} Chitin Armor pieces.
	 * Shared by all four set-bonus effects so the equipment check lives in one place.
	 */
	public static boolean hasChitinPieces(LivingEntity entity, int count) {
		return countChitinPieces(entity) >= count;
	}

	private static Set<SoundEvent> collectStepSounds() {
		Set<SoundEvent> sounds = new HashSet<>();
		for (Field field : SoundType.class.getFields()) {
			if (Modifier.isStatic(field.getModifiers()) && field.getType() == SoundType.class) {
				try {
					sounds.add(((SoundType) field.get(null)).getStepSound());
				} catch (IllegalAccessException ignored) {
					// Unreachable: only public static fields are matched above.
				}
			}
		}
		return sounds;
	}

	/**
	 * Returns true if {@code sound} is a block footstep sound, as opposed to some other self-sound
	 * (hurt, eat, drink, ...) broadcast through the same position-based {@code Level#playSound} path.
	 */
	public static boolean isStepSound(@Nullable SoundEvent sound) {
		return sound != null && STEP_SOUNDS.contains(sound);
	}

	@Override
    @OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.translatable("item.fur.chitin_armor.desc0").withStyle(ChatFormatting.YELLOW));
		tooltip.add(Component.translatable("item.fur.chitin_armor.desc1").withStyle(ChatFormatting.YELLOW));
	}
}
