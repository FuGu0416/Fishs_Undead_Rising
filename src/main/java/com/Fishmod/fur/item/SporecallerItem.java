package com.Fishmod.fur.item;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.entities.tameable.FURTameableEntity;
import com.Fishmod.fur.entities.tameable.ShroomlingEntity;
import com.Fishmod.fur.init.FUREnchantmentRegistry;
import com.Fishmod.fur.init.FUREntityRegistry;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * The Sporecaller staff: a Shroomling-summoning weapon that can be "loaded" with a potion.
 * Right-clicking a potion onto the staff in the inventory injects that potion's effects, which are
 * then applied to every Shroomling the staff summons. Extends {@link FURWeaponItem} so it keeps the
 * shared summon/cooldown logic in {@link FURWeaponItem#use}.
 */
public class SporecallerItem extends FURWeaponItem {
	/** NBT key holding the serialized list of injected potion effects (overwritten on each injection). */
	public static final String INJECTED_EFFECTS_KEY = "InjectedEffects";
	/** Overlay tint used when no potion is injected (a pale cyan default). */
	public static final int DEFAULT_OVERLAY_COLOR = 0x4EFFFF;

	public SporecallerItem(Properties properties, Tier material, int damage, float attackspeed, double reach, Item repair, Boolean hasDesc) {
		super(properties, material, damage, attackspeed, reach, repair, hasDesc);
	}

	/**
	 * Right-click: summon a wave of Shroomlings. The injected potion (if any) decides what each summoned
	 * Shroomling carries:
	 * <ul>
	 *   <li>no potion injected → spore-less Shroomlings (default bubble, no death burst);</li>
	 *   <li>a beneficial potion → buffs the summoner once, and the Shroomlings show that potion's bubble
	 *       colour but release nothing on death;</li>
	 *   <li>any other potion → becomes each Shroomling's spore-burst payload.</li>
	 * </ul>
	 */
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);

		if (level instanceof ServerLevel) {
			int dominion = stack.getEnchantmentLevel(FUREnchantmentRegistry.DOMINION.get());
			int skin = stack.getEnchantmentLevel(Enchantments.FIRE_ASPECT) > 0 ? 1 : 0;
			BlockPos blockpos = BlockPos.containing(player.getX() + player.getLookAngle().x, player.getY() + 0.2D, player.getZ() + player.getLookAngle().z);

			List<MobEffectInstance> injected = getInjectedEffects(stack);
			MobEffectInstance primary = injected.isEmpty() ? null : injected.get(0);
			boolean beneficial = primary != null && primary.getEffect().getCategory() == MobEffectCategory.BENEFICIAL;
			// A beneficial injected potion buffs the summoner instead of becoming a spore-burst payload.
			if (beneficial) {
				player.addEffect(new MobEffectInstance(primary));
			}

			for (int i = 0; i < 1 + dominion; i++) {
				FURTameableEntity summoned = FURWeaponItem.SummonMinion(player, stack, level, blockpos, FUREntityRegistry.SHROOMLING.get(), FURConfig.Shroomling_Lifespan.get() * 20, skin);
				if (summoned instanceof ShroomlingEntity shroomling) {
					if (primary == null) {
						// No injection: spore-less Shroomling (default bubble, no burst).
						shroomling.clearSporeEffect();
					} else if (beneficial) {
						// Beneficial potion: buff the summoned Shroomling itself too (the summoner is buffed
						// once above) and show the potion's colour on the bubble, but release no burst.
						shroomling.addEffect(new MobEffectInstance(primary));
						shroomling.setSporeColorOnly(primary.getEffect().getColor());
					} else {
						// Any other potion becomes the Shroomling's spore-burst payload.
						shroomling.setSporeEffect(primary);
					}
				}
			}

			for (int j = 0; j < 4; ++j) {
				double d0 = blockpos.getX() + (player.getRandom().nextDouble() * 2.0D) - 1.0D;
				double d1 = blockpos.getY() + (player.getRandom().nextDouble() * 2.0D);
				double d2 = blockpos.getZ() + (player.getRandom().nextDouble() * 2.0D) - 1.0D;
				((ServerLevel) level).sendParticles(skin > 0 ? ParticleTypes.FLAME : ParticleTypes.SPLASH, d0, d1, d2, 15, 0.0D, 0.0D, 0.0D, 0.0D);
			}

			stack.hurtAndBreak(8, player, (p) -> p.broadcastBreakEvent(EquipmentSlot.MAINHAND));
			player.getCooldowns().addCooldown(this, FURConfig.SludgeWand_Cooldown.get() * 20);

			return InteractionResultHolder.pass(stack);
		}

		return super.use(level, player, hand);
	}

	/**
	 * Right-clicking a potion onto the staff in an inventory slot:
	 * <ul>
	 *   <li>An effect-carrying potion is injected, overwriting any previously stored effects (the
	 *       staff only ever holds one potion at a time).</li>
	 *   <li>A water bottle (no effects) washes off the currently injected potion. If nothing is
	 *       injected this is a no-op so the water bottle isn't wasted.</li>
	 * </ul>
	 * Either action consumes one bottle, returns an empty glass bottle, and plays the drain sound.
	 */
	@Override
	public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
		if (action != ClickAction.SECONDARY || !(other.getItem() instanceof PotionItem)) {
			return false;
		}

		List<MobEffectInstance> effects = PotionUtils.getMobEffects(other);
		CompoundTag tag = stack.getOrCreateTag();
		if (effects.isEmpty()) {
			// Water bottle: wash off the injected potion. Nothing to wash -> leave the bottle alone.
			if (!tag.contains(INJECTED_EFFECTS_KEY)) {
				return false;
			}
			tag.remove(INJECTED_EFFECTS_KEY);
		} else {
			// Overwrite (not append) the stored effects so only one potion is held at a time.
			ListTag list = new ListTag();
			for (MobEffectInstance effect : effects) {
				list.add(effect.save(new CompoundTag()));
			}
			tag.put(INJECTED_EFFECTS_KEY, list);
		}

		// Consume one bottle from the clicked stack.
		other.shrink(1);

		if (!player.level().isClientSide()) {
			// Return the emptied glass bottle to the player, dropping it if the inventory is full.
			ItemStack bottle = new ItemStack(Items.GLASS_BOTTLE);
			if (!player.getInventory().add(bottle)) {
				player.drop(bottle, false);
			}
			player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_EMPTY, player.getSoundSource(), 1.0F, 1.0F);
		}

		return true;
	}

	/**
	 * Packed RGB tint for the {@code sporecaller_overlay} layer (tint index 1): the blended colour of
	 * the injected potion, or {@link #DEFAULT_OVERLAY_COLOR} when nothing is injected.
	 */
	public static int getOverlayColor(ItemStack stack) {
		List<MobEffectInstance> effects = getInjectedEffects(stack);
		return effects.isEmpty() ? DEFAULT_OVERLAY_COLOR : PotionUtils.getColor(effects);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, level, tooltip, flag);

		// Explain the potion-injection feature, then list the currently injected effect (or "empty").
		tooltip.add(Component.translatable(this.getDescriptionId() + ".inject").withStyle(ChatFormatting.GRAY));

		List<MobEffectInstance> effects = getInjectedEffects(stack);
		if (!effects.isEmpty()) {
			PotionUtils.addPotionTooltip(effects, tooltip, 1.0F);
		} else {
			tooltip.add(Component.translatable(this.getDescriptionId() + ".empty").withStyle(ChatFormatting.GRAY));
		}
	}

	/**
	 * Reads the injected potion effects stored on the given staff stack.
	 * @return the stored effects, or an empty list when nothing has been injected.
	 */
	public static List<MobEffectInstance> getInjectedEffects(ItemStack stack) {
		CompoundTag tag = stack.getTag();
		if (tag == null || !tag.contains(INJECTED_EFFECTS_KEY, Tag.TAG_LIST)) {
			return List.of();
		}

		ListTag list = tag.getList(INJECTED_EFFECTS_KEY, Tag.TAG_COMPOUND);
		List<MobEffectInstance> effects = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			MobEffectInstance effect = MobEffectInstance.load(list.getCompound(i));
			if (effect != null) {
				effects.add(effect);
			}
		}
		return effects;
	}
}
