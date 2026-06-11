package com.Fishmod.fur.item;

import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.fur.data.providers.FURBlockTagsProvider;
import com.Fishmod.fur.worldgen.feature.FURConfiguredFeatures;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Undergrove Heart (菌林之心): a raw material that, when right-clicked onto a mat-replaceable surface
 * block, triggers a one-shot placement of the {@code MYCELIAL_MAT_PATCH} and
 * {@code LUMINOUS_CLUSTER_PATCH_SMALL} configured features at that spot — spreading mycelial mat plus a
 * small cluster of luminous flora, as if a piece of the Luminous Undergrove took root. Consumed on use.
 */
public class UndergroveHeartItem extends Item {

	public UndergroveHeartItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		BlockPos clicked = context.getClickedPos();

		// Only usable on a mat-replaceable surface block (#fur:mat_replaceable).
		if (!level.getBlockState(clicked).is(FURBlockTagsProvider.MAT_REPLACEABLE)) {
			return InteractionResult.PASS;
		}

		if (level instanceof ServerLevel serverLevel) {
			// The features scan down from the origin to the floor, so feed them the air above the block.
			BlockPos origin = clicked.above();
			ChunkGenerator generator = serverLevel.getChunkSource().getGenerator();
			RandomSource random = serverLevel.getRandom();
			Registry<ConfiguredFeature<?, ?>> registry = serverLevel.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);

			// Mycelial mat ground first, then the small luminous plant cluster on top of it.
			registry.getHolderOrThrow(FURConfiguredFeatures.MYCELIAL_MAT_PATCH).value().place(serverLevel, generator, random, origin);
			registry.getHolderOrThrow(FURConfiguredFeatures.LUMINOUS_CLUSTER_PATCH_SMALL).value().place(serverLevel, generator, random, origin);

			context.getItemInHand().shrink(1);
		}

		return InteractionResult.sidedSuccess(level.isClientSide());
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(net.minecraft.world.item.ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.translatable(this.getDescriptionId() + ".desc").withStyle(ChatFormatting.YELLOW));
	}
}
