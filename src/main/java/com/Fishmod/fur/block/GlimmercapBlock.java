package com.Fishmod.fur.block;

import com.Fishmod.fur.init.FURBlockRegistry;
import com.Fishmod.fur.mod_LavaCow;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Collections;
import java.util.List;

public class GlimmercapBlock extends Block implements BonemealableBlock {

    // ── Block state properties ────────────────────────────────────────────────

    /** Whether this block is the upper half of a 2-tall glimmercap. */
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    /** Whether this is a 2-tall glimmercap (true) or a 1-tall glimmercap (false). */
    public static final BooleanProperty TALL = BooleanProperty.create("tall");

    // ── Hitbox ────────────────────────────────────────────────────────────────

    // Mushroom-sized hitbox — slightly smaller than a full block
    private static final VoxelShape SHAPE_LOWER = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    private static final VoxelShape SHAPE_UPPER = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 10.0D, 12.0D);

    // ── Constructor ───────────────────────────────────────────────────────────

    public GlimmercapBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HALF, DoubleBlockHalf.LOWER).setValue(TALL, false));
    }

    // ── Block state registration ──────────────────────────────────────────────

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF, TALL);
    }

    // ── Shape ─────────────────────────────────────────────────────────────────

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(HALF) == DoubleBlockHalf.UPPER ? SHAPE_UPPER : SHAPE_LOWER;
    }

    // ── Placement ─────────────────────────────────────────────────────────────

    /**
     * When placed by a player, randomly decide whether to spawn as 1-tall or 2-tall
     * (50/50 chance), matching the natural spawning behavior.
     */
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos   = context.getClickedPos();
        Level    level = context.getLevel();

        if (!canSurviveAt(level, pos))
            return null;

        boolean spawnTall = level.getRandom().nextBoolean() && level.isEmptyBlock(pos.above());

        if (spawnTall) {
            // Place upper half above
            level.setBlock(pos.above(), this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER).setValue(TALL, true), Block.UPDATE_ALL);
            return this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER).setValue(TALL, true);
        }

        return this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER).setValue(TALL, false);
    }

    // ── Survival / neighbor updates ──────────────────────────────────────────

    /**
     * Mirrors vanilla mushroom placement rules:
     * must be on a solid opaque block, in low light or on mycelium/podzol.
     */
    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            if (state.getValue(TALL)) {
                // Paired upper half: must have a lower half directly below
                BlockState below = level.getBlockState(pos.below());
                return below.is(this) && below.getValue(HALF) == DoubleBlockHalf.LOWER;
            }
            // Standalone cap (TALL=false): survives on any valid surface
            return canSurviveAt(level, pos);
        }
        return canSurviveAt(level, pos);
    }

    private static boolean canSurviveAt(LevelReader level, BlockPos pos) {
        BlockPos belowPos = pos.below();
        BlockState belowState = level.getBlockState(belowPos);
        Block belowBlock = belowState.getBlock();

        if (belowBlock == Blocks.MYCELIUM 
        		|| belowBlock == Blocks.PODZOL 
        		|| belowBlock == FURBlockRegistry.LUMINOUS_MYCELIUM.get()
        		|| belowBlock == FURBlockRegistry.MYCELIAL_MAT.get())
            return true;

        // Otherwise must be solid opaque and in light level <= 12
        return belowState.isFaceSturdy(level, belowPos, Direction.UP) && level.getRawBrightness(pos, 0) <= 12;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        DoubleBlockHalf half = state.getValue(HALF);
        boolean tall = state.getValue(TALL);

        if (half == DoubleBlockHalf.LOWER && direction == Direction.UP && tall) {
            if (!neighborState.is(this) || neighborState.getValue(HALF) != DoubleBlockHalf.UPPER) {
                // UPPER is missing — become a standalone cap at this position
                return this.defaultBlockState()
                        .setValue(HALF, DoubleBlockHalf.UPPER)
                        .setValue(TALL, false);
            }
        } else if (half == DoubleBlockHalf.UPPER && tall && direction == Direction.DOWN) {
            // Paired upper half: must have a valid lower half below; otherwise pop off
            if (!neighborState.is(this) || neighborState.getValue(HALF) != DoubleBlockHalf.LOWER) {
                return Blocks.AIR.defaultBlockState();
            }
        }

        if (!state.canSurvive(level, pos))
            return Blocks.AIR.defaultBlockState();

        return state;
    }

    // ── Breaking behavior ────────────────────────────────────────────────────

    /**
     * When either half is destroyed, remove the other half silently (no extra drops)
     * and drop exactly one glimmercap item at the lower position.
     */
    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide()) {
            DoubleBlockHalf half = state.getValue(HALF);
            boolean isTall = state.getValue(TALL);

            if (isTall) {
                BlockPos otherPos = (half == DoubleBlockHalf.LOWER) ? pos.above() : pos.below();
                BlockState otherState = level.getBlockState(otherPos);

                if (otherState.is(this)) {
                    // Remove the other half without triggering drops
                    level.setBlock(otherPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL | Block.UPDATE_SUPPRESS_DROPS);
                }
            }

            // Drop one glimmercap item at the block's own position (or lower pos for paired UPPER)
			if (!player.isCreative()) {
	            BlockPos dropPos = (isTall && half == DoubleBlockHalf.UPPER) ? pos.below() : pos;
	            Block.popResource(level, dropPos, new ItemStack(FURBlockRegistry.GLIMMERCAP.get()));
			}
        }
        super.playerWillDestroy(level, pos, state, player);
    }

    /**
     * Suppress the default loot table drop — we handle drops manually in playerWillDestroy.
     */
    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        return Collections.emptyList();
    }

    // ── Bonemeal support ──────────────────────────────────────────────────────

    /**
     * Bonemeal targets: a 1-tall glimmercap (lower half, space above) grows into a 2-tall glimmercap;
     * a 2-tall glimmercap (either half) grows into the Giant Glimmercap.
     */
    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
        if (state.getValue(HALF) == DoubleBlockHalf.LOWER && !state.getValue(TALL)) {
            return level.isEmptyBlock(pos.above());
        }
        return state.getValue(TALL);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    /**
     * 1-tall → grow into a 2-tall glimmercap. 2-tall → grow into the Giant Glimmercap feature.
     */
    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        if (!state.getValue(TALL)) {
            // 1-tall -> 2-tall
            level.setBlock(pos, state.setValue(TALL, true), Block.UPDATE_ALL);
            level.setBlock(pos.above(), this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER).setValue(TALL, true), Block.UPDATE_ALL);
            return;
        }

        // 2-tall -> Giant Glimmercap, grown from the lower (ground) half's position
        BlockPos lowerPos = state.getValue(HALF) == DoubleBlockHalf.UPPER ? pos.below() : pos;
        BlockPos upperPos = lowerPos.above();

        var feature = level.registryAccess()
                .registryOrThrow(Registries.CONFIGURED_FEATURE)
                .get(new ResourceLocation(mod_LavaCow.MODID, "giant_glimmercap"));
        if (feature == null) {
            return;
        }

        // Clear both halves, then try to grow the feature; restore the 2-tall if it can't fit.
        level.removeBlock(upperPos, false);
        level.removeBlock(lowerPos, false);

        if (!feature.place(level, level.getChunkSource().getGenerator(), random, lowerPos)) {
            level.setBlock(lowerPos, this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER).setValue(TALL, true), Block.UPDATE_ALL);
            level.setBlock(upperPos, this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER).setValue(TALL, true), Block.UPDATE_ALL);
        }
    }
}
