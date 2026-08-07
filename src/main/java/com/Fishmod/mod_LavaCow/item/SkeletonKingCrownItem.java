package com.Fishmod.mod_LavaCow.item;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.capability.SimpleCapProvider;
import com.Fishmod.mod_LavaCow.client.model.armor.ModelCrown;
import com.Fishmod.mod_LavaCow.entities.SkeletonKingEntity;
import com.Fishmod.mod_LavaCow.entities.ai.EntityAIFollowEntity;
import com.Fishmod.mod_LavaCow.entities.ai.SkeletonOwnerHurtByTargetGoal;
import com.Fishmod.mod_LavaCow.entities.ai.SkeletonOwnerHurtTargetGoal;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;
import com.Fishmod.mod_LavaCow.integration.curios.CurioIntegration;
import com.Fishmod.mod_LavaCow.mod_LavaCow;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.ModList;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.capability.ICurio;

/**
 * Governance of nearby skeleton-family mobs is a live aura, not a one-time conversion: it is
 * driven entirely from the governed mob's own tick (see EventHandler#onSkeletonGuardUpkeep), which
 * every 20 ticks checks whether the mob's current owner (read back off its
 * {@link EntityAIFollowEntity}) still wears this crown, and releases it the moment that stops
 * being true. There is no NBT-persisted "already tamed" marker: the goal selector itself is the
 * single source of truth ({@link #getOwnerId} returns null when ungoverned), so a mob that unloads
 * and reloads simply comes back with vanilla's own default goals (nothing to desync) and gets
 * re-governed the next time it is next to a wearer. Ported from the 1.20.1 redesign; see that
 * version's item class for the fuller rationale.
 * <p>
 * Eligibility ({@link #isEligible}) is data-driven off the vanilla {@code minecraft:skeletons}
 * entity type tag rather than an {@code instanceof AbstractSkeletonEntity} check, because this mod
 * already extends that tag with its own reskins (see
 * {@code data/minecraft/tags/entity_types/skeletons.json}) for entities that are NOT Java
 * subclasses of {@code AbstractSkeletonEntity} (Bone Worm). Adding a future skeleton-family mob to
 * that tag is enough to bring it under the crown's effect; no Java change is needed here. The boss
 * ({@link SkeletonKingEntity}) is explicitly excluded even though it is in the tag.
 */
public class SkeletonKingCrownItem extends ArmorItem {
	/**
	 * Vanilla target goals stripped from a governed skeleton, keyed by the skeleton itself so they
	 * can be restored verbatim (same instances, same priorities) on release. WeakHashMap so entries
	 * for skeletons that die/unload while governed are reclaimed by the GC instead of leaking.
	 */
	private static final Map<CreatureEntity, List<PrioritizedGoal>> SUPPRESSED_TARGET_GOALS = new WeakHashMap<>();

	/**
	 * 1.16.5's {@link GoalSelector} only exposes {@code getRunningGoals()} (goals currently active),
	 * not the full registered set -- using that here would miss goals that exist but aren't running
	 * at the moment we check (e.g. a target goal with no current target), so both the "is this
	 * skeleton governed" read and the "strip the vanilla target goals" write would be unreliable.
	 * 1.20.1's Forge does expose the full set (getAvailableGoals()); this reaches the same private
	 * field 1.16.5 keeps it in. This project's mappings are 'official' (see build.gradle), so the
	 * field really is named availableGoals at runtime -- no SRG/ObfuscationReflectionHelper needed.
	 */
	private static final Field AVAILABLE_GOALS_FIELD;

	static {
		Field field = null;
		try {
			field = GoalSelector.class.getDeclaredField("availableGoals");
			field.setAccessible(true);
		} catch (NoSuchFieldException e) {
			mod_LavaCow.LOGGER.error("SkeletonKingCrownItem: GoalSelector.availableGoals not found; Skeleton King's Crown will not be able to govern skeletons", e);
		}
		AVAILABLE_GOALS_FIELD = field;
	}

	@SuppressWarnings("unchecked")
	private static Set<PrioritizedGoal> availableGoals(GoalSelector selector) {
		if (AVAILABLE_GOALS_FIELD == null) {
			return Collections.emptySet();
		}
		try {
			return (Set<PrioritizedGoal>) AVAILABLE_GOALS_FIELD.get(selector);
		} catch (IllegalAccessException e) {
			return Collections.emptySet();
		}
	}

	public SkeletonKingCrownItem(Item.Properties p_i48534_3_) {
		super(ArmorMaterial.DIAMOND, EquipmentSlotType.HEAD, p_i48534_3_);
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return true;
	}

	@Override
	public boolean isValidRepairItem(ItemStack armour, ItemStack material) {
		return material.getItem() == FURItemRegistry.HATRED_SHARD;
	}

	/**
	 * Whether this mob is the kind of thing the crown can govern at all: a member of the
	 * (mod-extended) vanilla {@code minecraft:skeletons} entity type tag, excluding the Skeleton
	 * King boss itself.
	 */
	public static boolean isEligible(CreatureEntity mob) {
		return mob.getType().is(EntityTypeTags.SKELETONS) && !(mob instanceof SkeletonKingEntity);
	}

	/**
	 * The UUID of the player currently commanding this skeleton, or null if it isn't governed. This
	 * is the only place "governed" is decided; nothing else tracks it.
	 */
	@Nullable
	public static UUID getOwnerId(CreatureEntity skeleton) {
		for (PrioritizedGoal wrapped : availableGoals(skeleton.goalSelector)) {
			if (wrapped.getGoal() instanceof EntityAIFollowEntity) {
				return ((EntityAIFollowEntity) wrapped.getGoal()).getOwnerId();
			}
		}
		return null;
	}

	/**
	 * Whether the given entity currently has this crown on (vanilla helmet slot, or a Curios slot
	 * when that mod is loaded).
	 */
	public static boolean isWearingCrown(LivingEntity entity) {
		boolean hasCrown = entity.getItemBySlot(EquipmentSlotType.HEAD).getItem().equals(FURItemRegistry.SKELETONKING_CROWN);
		if (!hasCrown && ModList.get().isLoaded("curios")) {
			hasCrown = CurioIntegration.findItem(FURItemRegistry.SKELETONKING_CROWN, entity) != ItemStack.EMPTY;
		}
		return hasCrown;
	}

	/**
	 * Puts a still-ungoverned skeleton under the given player's command: strips its vanilla
	 * attack-target goals (stashed for later restoration) and wires in the follow / defend-owner
	 * goals. Caller is expected to have already checked {@link #getOwnerId} is null and the
	 * navigation type.
	 */
	public static void govern(CreatureEntity skeleton, PlayerEntity owner) {
		List<PrioritizedGoal> stripped = availableGoals(skeleton.targetSelector).stream()
				.filter(wrapped -> wrapped.getGoal() instanceof NearestAttackableTargetGoal<?>)
				.collect(Collectors.toList());
		stripped.forEach(wrapped -> skeleton.targetSelector.removeGoal(wrapped.getGoal()));
		SUPPRESSED_TARGET_GOALS.put(skeleton, stripped);

		skeleton.playSound(SoundEvents.EVOKER_CAST_SPELL, 1.0F, 1.0F);
		// Broadcast via ServerWorld#sendParticles -- govern() only runs server side, where
		// World#addParticle is a no-op (only ClientWorld overrides it).
		if (skeleton.level instanceof ServerWorld) {
			((ServerWorld) skeleton.level).sendParticles(ParticleTypes.ENTITY_EFFECT, skeleton.getX(), skeleton.getY(0.5D), skeleton.getZ(), 16, skeleton.getBbWidth() * 0.5D, skeleton.getBbHeight() * 0.5D, skeleton.getBbWidth() * 0.5D, 0.02D);
		}

		skeleton.goalSelector.addGoal(6, new EntityAIFollowEntity(skeleton, owner.getUUID(), 1.0D, 10.0F, 2.0F));
		skeleton.targetSelector.addGoal(1, new SkeletonOwnerHurtByTargetGoal(skeleton, owner.getUUID()));
		skeleton.targetSelector.addGoal(2, new SkeletonOwnerHurtTargetGoal(skeleton, owner.getUUID()));
	}

	/**
	 * Releases a governed skeleton: removes the follow / defend-owner goals and restores whatever
	 * vanilla attack-target goals were stripped in {@link #govern}, at their original priorities.
	 */
	public static void release(CreatureEntity skeleton) {
		List<Goal> ownGoals = availableGoals(skeleton.goalSelector).stream()
				.map(PrioritizedGoal::getGoal)
				.filter(goal -> goal instanceof EntityAIFollowEntity)
				.collect(Collectors.toList());
		ownGoals.forEach(skeleton.goalSelector::removeGoal);

		List<Goal> ownTargetGoals = availableGoals(skeleton.targetSelector).stream()
				.map(PrioritizedGoal::getGoal)
				.filter(goal -> goal instanceof SkeletonOwnerHurtByTargetGoal || goal instanceof SkeletonOwnerHurtTargetGoal)
				.collect(Collectors.toList());
		ownTargetGoals.forEach(skeleton.targetSelector::removeGoal);

		List<PrioritizedGoal> restored = SUPPRESSED_TARGET_GOALS.remove(skeleton);
		if (restored != null) {
			for (PrioritizedGoal wrapped : restored) {
				skeleton.targetSelector.addGoal(wrapped.getPriority(), wrapped.getGoal());
			}
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType armorSlot, String type) {
		return "mod_lavacow:textures/armors/kings_crown/kings_crown.png";
	}

	@SuppressWarnings("unchecked")
	@Override
	@OnlyIn(Dist.CLIENT)
	public <E extends BipedModel<?>> E getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, E _default) {
		return (E) new ModelCrown<>(1.0F);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TranslationTextComponent("tooltip.mod_lavacow:skeletonking_crown").withStyle(TextFormatting.YELLOW));
	}

	/**
	 * Curios doesn't occupy the vanilla helmet slot, so {@link #isWearingCrown} needs its own check
	 * for that case -- the capability just needs to exist for that check to find the item; there is
	 * no per-tick behavior to run here anymore.
	 */
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT unused) {
		if (ModList.get().isLoaded("curios")) {
			return new SimpleCapProvider<>(CuriosCapability.ITEM, new ICurio() {
			});
		}

		return super.initCapabilities(stack, unused);
	}
}
