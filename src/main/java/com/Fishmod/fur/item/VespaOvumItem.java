package com.Fishmod.fur.item;

import com.Fishmod.fur.core.VespaInfestation;
import com.Fishmod.fur.data.providers.FUREntityTypeTagsProvider;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * Vespa Ovum: right-clicking a valid host injects a Vespa infestation. Still edible
 * (food properties from {@link FURItem}); the injection path only runs on entity interaction.
 */
public class VespaOvumItem extends FURItem {
	public VespaOvumItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
		// Injection is a server-authoritative state change; let the client no-op.
		if (player.level().isClientSide) {
			return InteractionResult.PASS;
		}
		// Only entity types tagged as Vespa targets can host an infestation.
		if (!target.getType().is(FUREntityTypeTagsProvider.VESPA_TARGETS)) {
			return InteractionResult.PASS;
		}
		// Do not re-inject a host that is already incubating.
		if (VespaInfestation.isInfested(target)) {
			return InteractionResult.PASS;
		}

		VespaInfestation.inject(target, player.getUUID());

		if (!player.getAbilities().instabuild) {
			stack.shrink(1);
		}

		target.playSound(SoundEvents.BEE_STING, 1.0F, 1.0F);
		return InteractionResult.SUCCESS;
	}
}
