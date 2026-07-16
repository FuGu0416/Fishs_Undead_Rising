package com.Fishmod.fur.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * The King's Crown lore items: type 0 = Stained (rare villager trade; crafted with Hatred Shards +
 * an emerald into the Cursed variant), type 1 = Cursed (used on a Skeleton Skull in a hot, dry,
 * sandy biome to summon the Skeleton King boss).
 */
public class CrownItem extends Item {
	private final int type;

	public CrownItem(Properties properties, int typeIn) {
		super(properties);
		this.type = typeIn;
	}

	/**
	 * 1.16.5 behaviour: right-clicking a Skeleton Skull with the Cursed crown (type 1) in a
	 * HOT + DRY + SANDY biome consumed the crown, destroyed the skull, struck lightning and
	 * summoned the Skeleton King boss at 10% health with 150 invulnerability ticks.
	 *
	 * Deferred — the SkeletonKing entity is not ported yet, so the summon branch below stays
	 * commented until FUREntityRegistry.SKELETONKING lands. See PLACEHOLDERS.md.
	 */
	@Override
	public InteractionResult useOn(UseOnContext context) {
		/* Deferred — un-comment when the SKELETONKING entity is ported.
		Player player = context.getPlayer();
		InteractionHand hand = context.getHand();
		BlockPos pos = context.getClickedPos();
		Level level = context.getLevel();
		if (this.type == 1 && FURConfig.Spawn_SkeletonKing.get()
				&& level.getBlockState(pos).is(Blocks.SKELETON_SKULL)
				&& level.getBiome(pos).containsTag(Tags.Biomes.IS_HOT)
				&& level.getBiome(pos).containsTag(Tags.Biomes.IS_DRY)
				&& level.getBiome(pos).containsTag(Tags.Biomes.IS_SANDY)) {
			if (!player.isCreative())
				player.getItemInHand(hand).shrink(1);

			level.destroyBlock(pos, false);
			LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(level);
			bolt.moveTo(Vec3.atBottomCenterOf(pos));
			bolt.setCause(player instanceof ServerPlayer ? (ServerPlayer) player : null);
			level.addFreshEntity(bolt);

			if (!level.isClientSide()) {
				SkeletonKingEntity king = FUREntityRegistry.SKELETONKING.get().create(level);
				king.moveTo(pos.getX(), pos.getY(), pos.getZ(), 0.0F, 0.0F);
				level.addFreshEntity(king);
				king.finalizeSpawn((ServerLevel) level, level.getCurrentDifficultyAt(pos), MobSpawnType.MOB_SUMMONED, null, null);
				king.setInvulnerableTicks(150);
				king.setHealth(king.getMaxHealth() * 0.10F);
			}

			level.playSound(player, pos, FURSoundRegistry.SKELETONKING_SPAWN.get(), SoundSource.HOSTILE, 1.0F, 1.0F);

			return InteractionResult.SUCCESS;
		}
		*/
		return super.useOn(context);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(Component.translatable("item.fur.kings_crown." + this.type + ".desc"));
		tooltip.add(Component.empty());
		tooltip.add(Component.translatable("item.fur.kings_crown." + this.type + ".desc0").withStyle(ChatFormatting.ITALIC));
		tooltip.add(Component.translatable("item.fur.kings_crown." + this.type + ".desc1").withStyle(ChatFormatting.ITALIC));
		tooltip.add(Component.translatable("item.fur.kings_crown." + this.type + ".desc2").withStyle(ChatFormatting.ITALIC));
	}
}
