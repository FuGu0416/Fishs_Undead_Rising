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
 * Looks and mines just like sandstone, but breaking it without Silk Touch releases 1-3 wild
 * Scarabs instead of dropping the block -- same trick as vanilla's InfestedBlock/Silverfish.
 * Scarab is normally a tameable pet (only defends an owner, never initiates), so these wild
 * spawns get an extra NearestAttackableTargetGoal grafted on so the ambush is actually a threat;
 * that goal is added to the individual spawned instance only, not to ScarabEntity itself.
 */
public class InfestedSandstoneBlock extends Block {
	public InfestedSandstoneBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	@Override
	public void spawnAfterBreak(BlockState state, ServerLevel level, BlockPos pos, ItemStack tool, boolean dropExperience) {
		super.spawnAfterBreak(state, level, pos, tool, dropExperience);
		if (level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, tool) == 0) {
			int count = 1 + level.random.nextInt(3);
			for (int i = 0; i < count; ++i) {
				ScarabEntity scarab = FUREntityRegistry.SCARAB.get().create(level);
				if (scarab == null) {
					continue;
				}

				scarab.moveTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, 0.0F, 0.0F);
				scarab.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(scarab, Player.class, true));
				level.addFreshEntity(scarab);
				scarab.spawnAnim();
			}
		}
	}
}
