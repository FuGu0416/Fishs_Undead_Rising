package com.Fishmod.fur.block;

import javax.annotation.Nullable;

import com.Fishmod.fur.block.blockentity.BonePileBlockEntity;
import com.Fishmod.fur.init.FURBlockEntityRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * A low pile of bones that scatters across desert surfaces. It behaves like a beehive: it starts
 * empty and slowly fills with Scarabs that roam by day and return by night (see
 * {@link BonePileBlockEntity}). Harvesting yields 2–4 bones, or the pile itself with Silk Touch.
 * Breaking it without Silk Touch releases its Scarabs plus 2–3 angry extras; Silk Touch is safe but
 * resets the stored count to 0. It pops off when the block beneath it is removed.
 */
public class BonePileBlock extends BaseEntityBlock {
    // ~0.6 block tall (9.6/16), inset on the horizontal so it reads as a heaped pile.
    private static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 9.6D, 15.0D);

    public BonePileBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        // Follow the per-position XZ render offset so collision/outline line up with the model.
        Vec3 offset = state.getOffset(getter, pos);
        return SHAPE.move(offset.x, offset.y, offset.z);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BonePileBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? null : createTickerHelper(type, FURBlockEntityRegistry.BONE_PILE.get(), BonePileBlockEntity::serverTick);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos below = pos.below();
        return level.getBlockState(below).isFaceSturdy(level, below, Direction.UP);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighbor, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return !state.canSurvive(level, pos)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(state, direction, neighbor, level, pos, neighborPos);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        // Breaking it angers the swarm — unless harvested with Silk Touch (which is safe but, since the
        // dropped block carries no block-entity data, resets the accumulated count to 0 when replaced).
        if (!level.isClientSide && level instanceof ServerLevel server
                && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, player.getMainHandItem()) == 0
                && level.getBlockEntity(pos) instanceof BonePileBlockEntity pile) {
            pile.onAngryBreak(server, pos, player);
        }

        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            // Clean up any roaming scarabs whenever the pile goes away (Silk Touch, support loss, etc.).
            if (level instanceof ServerLevel server && level.getBlockEntity(pos) instanceof BonePileBlockEntity pile) {
                pile.discardRoaming(server);
            }
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }
}
