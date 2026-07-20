package com.Fishmod.fur.item;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.SkeletonKingEntity;
import com.Fishmod.fur.entities.ai.EntityAIFollowEntity;
import com.Fishmod.fur.entities.ai.SkeletonOwnerHurtByTargetGoal;
import com.Fishmod.fur.entities.ai.SkeletonOwnerHurtTargetGoal;
import com.Fishmod.fur.init.FURItemRegistry;
import com.Fishmod.fur.integration.curios.CurioIntegration;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.ModList;

import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.capability.ICurio;

/**
 * Governance of nearby skeleton-family mobs is a live aura, not a one-time conversion: it is
 * driven entirely from the governed mob's own tick (see FURServerEvents#onSkeletonGuardUpkeep),
 * which every 20 ticks checks whether the mob's current owner (read back off its
 * {@link EntityAIFollowEntity}) still wears this crown, and releases it the moment that stops
 * being true. There is no NBT-persisted "already tamed" marker: the goal selector itself is the
 * single source of truth ({@link #getOwnerId} returns null when ungoverned), so a mob that unloads
 * and reloads simply comes back with vanilla's own default goals (nothing to desync) and gets
 * re-governed the next time it is next to a wearer.
 * <p>
 * Eligibility ({@link #isEligible}) is data-driven off the vanilla {@code minecraft:skeletons}
 * entity type tag rather than an {@code instanceof AbstractSkeleton} check, because this mod
 * already extends that tag with its own reskins (see
 * {@code data/minecraft/tags/entity_types/skeletons.json}) for entities that are NOT Java
 * subclasses of {@code AbstractSkeleton} (e.g. Bone Worm). Adding a future skeleton-family mob to
 * that tag is enough to bring it under the crown's effect; no Java change is needed here. The boss
 * ({@link SkeletonKingEntity}) is explicitly excluded even though it is in the tag.
 */
public class SkeletonKingCrownItem extends ArmorItem {
	/**
	 * Vanilla target goals stripped from a governed mob, keyed by the mob itself so they can be
	 * restored verbatim (same instances, same priorities) on release. WeakHashMap so entries for
	 * mobs that die/unload while governed are reclaimed by the GC instead of leaking.
	 */
	private static final Map<PathfinderMob, List<WrappedGoal>> SUPPRESSED_TARGET_GOALS = new WeakHashMap<>();

	public SkeletonKingCrownItem(Item.Properties properties) {
		super(ArmorMaterials.DIAMOND, ArmorItem.Type.HELMET, properties);
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return true;
	}

	@Override
	public boolean isValidRepairItem(ItemStack armour, ItemStack material) {
		return material.getItem() == FURItemRegistry.HATRED_SHARD.get();
	}

	/**
	 * Whether this mob is the kind of thing the crown can govern at all: a member of the
	 * (mod-extended) vanilla {@code minecraft:skeletons} entity type tag, excluding the Skeleton
	 * King boss itself.
	 */
	public static boolean isEligible(PathfinderMob mob) {
		return mob.getType().is(EntityTypeTags.SKELETONS) && !(mob instanceof SkeletonKingEntity);
	}

	/**
	 * The UUID of the player currently commanding this mob, or null if it isn't governed. This is
	 * the only place "governed" is decided; nothing else tracks it.
	 */
	@Nullable
	public static UUID getOwnerId(PathfinderMob mob) {
		for (WrappedGoal wrapped : mob.goalSelector.getAvailableGoals()) {
			if (wrapped.getGoal() instanceof EntityAIFollowEntity follow) {
				return follow.getOwnerId();
			}
		}
		return null;
	}

	/**
	 * Whether the given entity currently has this crown on (vanilla helmet slot, or a Curios slot
	 * when that mod is loaded).
	 */
	public static boolean isWearingCrown(LivingEntity entity) {
		boolean hasCrown = entity.getItemBySlot(EquipmentSlot.HEAD).getItem().equals(FURItemRegistry.SKELETONKING_CROWN.get());
		if (!hasCrown && ModList.get().isLoaded("curios")) {
			hasCrown = CurioIntegration.findItem(FURItemRegistry.SKELETONKING_CROWN.get(), entity) != ItemStack.EMPTY;
		}
		return hasCrown;
	}

	/**
	 * Puts a still-ungoverned, {@link #isEligible} mob under the given player's command: strips its
	 * vanilla attack-target goals (stashed for later restoration) and wires in the follow /
	 * defend-owner goals. Caller is expected to have already checked {@link #getOwnerId} is null and
	 * the navigation type.
	 */
	public static void govern(PathfinderMob mob, Player owner) {
		List<WrappedGoal> stripped = mob.targetSelector.getAvailableGoals().stream()
				.filter(wrapped -> wrapped.getGoal() instanceof NearestAttackableTargetGoal<?>)
				.collect(Collectors.toList());
		stripped.forEach(wrapped -> mob.targetSelector.removeGoal(wrapped.getGoal()));
		SUPPRESSED_TARGET_GOALS.put(mob, stripped);

		// Level#addParticle is a no-op on the server (only ClientLevel renders it) and this method
		// only ever runs server-side, so both effects go through explicit ServerLevel broadcasts
		// instead of the Entity/Level convenience wrappers.
		if (mob.level() instanceof ServerLevel serverLevel) {
			serverLevel.playSound(null, mob.getX(), mob.getY(), mob.getZ(), SoundEvents.EVOKER_CAST_SPELL, mob.getSoundSource(), 1.0F, 1.0F);
			serverLevel.sendParticles(ParticleTypes.ENTITY_EFFECT, mob.getX(), mob.getY() + mob.getBbHeight() * 0.5D, mob.getZ(), 16, mob.getBbWidth() * 0.5D, mob.getBbHeight() * 0.5D, mob.getBbWidth() * 0.5D, 0.02D);
		}

		mob.goalSelector.addGoal(6, new EntityAIFollowEntity(mob, owner.getUUID(), 1.0D, 10.0F, 2.0F));
		mob.targetSelector.addGoal(1, new SkeletonOwnerHurtByTargetGoal(mob, owner.getUUID()));
		mob.targetSelector.addGoal(2, new SkeletonOwnerHurtTargetGoal(mob, owner.getUUID()));
	}

	/**
	 * Releases a governed mob: removes the follow / defend-owner goals and restores whatever vanilla
	 * attack-target goals were stripped in {@link #govern}, at their original priorities.
	 */
	public static void release(PathfinderMob mob) {
		List<Goal> ownGoals = mob.goalSelector.getAvailableGoals().stream()
				.map(WrappedGoal::getGoal)
				.filter(EntityAIFollowEntity.class::isInstance)
				.collect(Collectors.toList());
		ownGoals.forEach(mob.goalSelector::removeGoal);

		List<Goal> ownTargetGoals = mob.targetSelector.getAvailableGoals().stream()
				.map(WrappedGoal::getGoal)
				.filter(goal -> goal instanceof SkeletonOwnerHurtByTargetGoal || goal instanceof SkeletonOwnerHurtTargetGoal)
				.collect(Collectors.toList());
		ownTargetGoals.forEach(mob.targetSelector::removeGoal);

		List<WrappedGoal> restored = SUPPRESSED_TARGET_GOALS.remove(mob);
		if (restored != null) {
			for (WrappedGoal wrapped : restored) {
				mob.targetSelector.addGoal(wrapped.getPriority(), wrapped.getGoal());
			}
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot armorSlot, String type) {
		return mod_LavaCow.MODID + ":textures/armors/kings_crown/kings_crown.png";
	}

	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept((IClientItemExtensions) mod_LavaCow.PROXY.getArmorProperties());
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(Component.translatable(this.getDescriptionId() + ".desc").withStyle(ChatFormatting.YELLOW));
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
		if (ModList.get().isLoaded("curios")) {
			return new CuriosProvider(stack);
		}
		return super.initCapabilities(stack, nbt);
	}

	/**
	 * Curios doesn't occupy the vanilla helmet slot, so {@link #isWearingCrown} needs its own check
	 * for that case — the capability just needs to exist for that check to find the item; there is
	 * no per-tick behavior to run here anymore.
	 */
	private class CuriosProvider implements ICapabilityProvider {
		private final LazyOptional<ICurio> curio;

		CuriosProvider(ItemStack stack) {
			this.curio = LazyOptional.of(() -> new ICurio() {
				@Override
				public ItemStack getStack() {
					return stack;
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
