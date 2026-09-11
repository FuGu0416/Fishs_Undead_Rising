package com.Fishmod.fur.block;

import com.Fishmod.fur.entities.tameable.ScarabEntity;
import com.Fishmod.fur.init.FUREntityRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Looks and mines just like its vanilla counterpart (sand, red sand, sandstone or red sandstone),
 * but breaking it without Silk Touch releases 1-3 wild Scarabs instead of dropping the block --
 * same trick as vanilla's InfestedBlock/Silverfish. One shared class covers all four disguises:
 * none of them need block-specific behavior, only different {@code BlockBehaviour.Properties} and
 * textures per registration in {@code FURBlockRegistry}. Scarab is normally a tameable pet (only
 * defends an owner, never initiates), so these wild spawns get an extra
 * NearestAttackableTargetGoal grafted on so the ambush is actually a threat; that goal is added to
 * the individual spawned instance only, not to ScarabEntity itself.
 *
 * <p>{@link #spawnScarabs} is also reused by {@link InfestedFallingBlock} (sand/red sand's falling
 * variant) so both "mined without Silk Touch" and "fallen infested sand hit something" produce the
 * same ambush.
 */
public class InfestedBlock extends Block {
	public InfestedBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	@Override
	public void spawnAfterBreak(BlockState state, ServerLevel level, BlockPos pos, ItemStack tool, boolean dropExperience) {
		super.spawnAfterBreak(state, level, pos, tool, dropExperience);
		if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, tool) == 0) {
			spawnScarabs(level, pos);
		}
	}

	/**
	 * Releases 1-3 wild Scarabs at {@code pos}, each armed with the ambush targeting goal (see class
	 * javadoc). Gated on {@code doTileDrops} the same way a normal block-break drop would be, since
	 * this is standing in for one.
	 */
	public static void spawnScarabs(ServerLevel level, BlockPos pos) {
		if (!level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
			return;
		}

		int count = 1 + level.random.nextInt(3);
		for (int i = 0; i < count; ++i) {
			ScarabEntity scarab = FUREntityRegistry.SCARAB.get().create(level);
			if (scarab == null) {
				continue;
			}

			scarab.moveTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, 0.0F, 0.0F);
			scarab.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(scarab, Player.class, true));
			level.addFreshEntity(scarab);
			// This bypasses finalizeSpawn entirely (vanilla InfestedBlock ambush pattern), so
			// nothing else triggers the burrow-up flourish for it - call it directly, which is
			// safe here since the entity is already tracked by addFreshEntity above. Replaces the
			// old generic vanilla spawnAnim() "poof" with the dedicated burrow-up animation.
			scarab.startBurrowUp();
		}
	}
}
