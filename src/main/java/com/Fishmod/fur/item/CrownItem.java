package com.Fishmod.fur.item;

import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.entities.SkeletonKingEntity;
import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURSoundRegistry;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags;

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
	 * Right-clicking a Skeleton Skull with the Cursed crown (type 1) in a HOT + DRY + SANDY
	 * biome consumes the crown, destroys the skull, strikes lightning and summons the Skeleton
	 * King boss at 10% health with 150 invulnerability ticks.
	 */
	@Override
	public InteractionResult useOn(UseOnContext context) {
		Player player = context.getPlayer();
		InteractionHand hand = context.getHand();
		BlockPos pos = context.getClickedPos();
		Level level = context.getLevel();
		if (this.type == 1 && player != null && FURConfig.Spawn_SkeletonKing.get()
				&& level.getBlockState(pos).is(Blocks.SKELETON_SKULL)
				&& level.getBiome(pos).is(Tags.Biomes.IS_HOT)
				&& level.getBiome(pos).is(Tags.Biomes.IS_DRY)
				&& level.getBiome(pos).is(Tags.Biomes.IS_SANDY)) {
			if (!player.isCreative())
				player.getItemInHand(hand).shrink(1);

			level.destroyBlock(pos, false);
			LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(level);
			if (bolt != null) {
				bolt.moveTo(Vec3.atBottomCenterOf(pos));
				bolt.setCause(player instanceof ServerPlayer ? (ServerPlayer) player : null);
				level.addFreshEntity(bolt);
			}

			if (level instanceof ServerLevel server) {
				SkeletonKingEntity king = FUREntityRegistry.SKELETONKING.get().create(server);
				if (king != null) {
					king.moveTo(pos.getX(), pos.getY(), pos.getZ(), 0.0F, 0.0F);
					server.addFreshEntity(king);
					king.finalizeSpawn(server, server.getCurrentDifficultyAt(pos), MobSpawnType.MOB_SUMMONED, null, null);
					king.setInvulnerableTicks(150);
					king.setHealth(king.getMaxHealth() * 0.10F);
				}
			}

			level.playSound(player, pos, FURSoundRegistry.SKELETONKING_SPAWN.get(), SoundSource.HOSTILE, 1.0F, 1.0F);

			return InteractionResult.SUCCESS;
		}

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
