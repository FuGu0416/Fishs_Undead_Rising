package com.Fishmod.fur.block;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.core.DreamcatcherLogic;
import com.Fishmod.fur.mod_LavaCow;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.Difficulty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * A ceiling-hung ritual block. Sleeping players nearby charge it with nightmares (one stage per night,
 * blockstate property {@link #CHARGE}). Right-clicking a charged dreamcatcher runs a 2-second windup and
 * then releases a budgeted wave of mobs drawn from the {@code fur:dreamcatcher_pool} tag (plain mobs,
 * targeted at the summoner). All charge lives in the blockstate — there is no BlockEntity — so breaking
 * it at any charge drops a fresh (uncharged) item via its loot table.
 */
public class DreamcatcherBlock extends Block {

    public static final IntegerProperty CHARGE = IntegerProperty.create("charge", 0, 5);

    /** Ticks between the right-click and the summon; also the "windup in progress" guard window. */
    public static final int WINDUP_TICKS = 40;

    private static final VoxelShape SHAPE = Block.box(3.0D, 4.0D, 3.0D, 13.0D, 16.0D, 13.0D);

    public DreamcatcherBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(CHARGE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CHARGE);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    /* ------------------------------------------------------------------ */
    /* Ceiling-hung support (vanilla hanging-lantern pattern)             */
    /* ------------------------------------------------------------------ */

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return Block.canSupportCenter(level, pos.above(), Direction.DOWN);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        // Losing the block above drops us (updateShape returning AIR destroys with drops by default).
        if (direction == Direction.UP && !this.canSurvive(state, level, pos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    /* ------------------------------------------------------------------ */
    /* Release                                                             */
    /* ------------------------------------------------------------------ */

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        if (!FURConfig.Dreamcatcher_Enabled.get()) {
            return InteractionResult.PASS;
        }

        // Ignore extra clicks while a windup is already scheduled.
        if (level.getBlockTicks().hasScheduledTick(pos, this)) {
            return InteractionResult.CONSUME;
        }

        if (level.getDifficulty() == Difficulty.PEACEFUL) {
            fail(level, pos, player, "peaceful");
            return InteractionResult.CONSUME;
        }

        int charge = state.getValue(CHARGE);
        if (charge <= 0) {
            fail(level, pos, player, "uncharged");
            return InteractionResult.CONSUME;
        }

        // Affordability pre-check: if nothing in the pool fits the budget, do not consume or wind up.
        if (!DreamcatcherLogic.hasAffordableCandidate(charge)) {
            fail(level, pos, player, "no_candidate");
            return InteractionResult.CONSUME;
        }

        // Begin the windup: rising ominous shriek + converging particles + scheduled summon.
        level.playSound(null, pos, SoundEvents.SCULK_SHRIEKER_SHRIEK, SoundSource.BLOCKS, 1.0F, 0.5F);
        if (level instanceof ServerLevel serverLevel) {
            emitWindupParticles(serverLevel, pos);
        }
        level.scheduleTick(pos, this, WINDUP_TICKS);
        return InteractionResult.CONSUME;
    }

    /** A ring of soul particles that stream inward toward the block, cueing the summon windup. */
    private static void emitWindupParticles(ServerLevel level, BlockPos pos) {
        double cx = pos.getX() + 0.5D;
        double cy = pos.getY() + 0.5D;
        double cz = pos.getZ() + 0.5D;
        int points = 12;
        double radius = 2.5D;
        for (int i = 0; i < points; i++) {
            double angle = (Math.PI * 2.0D) * i / points;
            double px = cx + Math.cos(angle) * radius;
            double pz = cz + Math.sin(angle) * radius;
            // count == 0 makes sendParticles treat the deltas as an explicit velocity (streaming inward).
            level.sendParticles(ParticleTypes.SOUL, px, cy, pz, 0,
                    (cx - px) * 0.12D, 0.02D, (cz - pz) * 0.12D, 0.5D);
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        // The summon cancels with the block: if it was destroyed during windup, we are no longer here.
        if (!state.is(this) || !level.getBlockState(pos).is(this)) {
            return;
        }

        int charge = state.getValue(CHARGE);
        if (charge <= 0) {
            return;
        }

        Player target = level.getNearestPlayer(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 32.0D, false);
        int spawned = DreamcatcherLogic.summonWave(level, pos, charge, target);

        if (spawned > 0) {
            level.setBlock(pos, state.setValue(CHARGE, 0), 3);
            level.playSound(null, pos, SoundEvents.WITHER_SPAWN, SoundSource.BLOCKS, 0.6F, 1.0F);
        } else {
            // All ring positions were blocked — charge is preserved.
            if (target != null) {
                target.displayClientMessage(Component.translatable("message." + mod_LavaCow.MODID + ".dreamcatcher.no_position"), true);
            }
            level.playSound(null, pos, SoundEvents.SCULK_CLICKING, SoundSource.BLOCKS, 1.0F, 0.6F);
        }
    }

    private static void fail(Level level, BlockPos pos, Player player, String reason) {
        player.displayClientMessage(Component.translatable("message." + mod_LavaCow.MODID + ".dreamcatcher." + reason), true);
        level.playSound(null, pos, SoundEvents.SCULK_CLICKING, SoundSource.BLOCKS, 1.0F, 0.6F);
    }

    /* ------------------------------------------------------------------ */
    /* Ambient particles (client-side, scaling with charge)                */
    /* ------------------------------------------------------------------ */

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        int charge = state.getValue(CHARGE);
        if (charge <= 0) {
            return;
        }

        // Sparse at 1–2, denser at 3–5.
        int count = charge >= 3 ? 2 : 1;
        for (int i = 0; i < count; i++) {
            double x = pos.getX() + 0.3D + random.nextDouble() * 0.4D;
            double y = pos.getY() + 0.2D + random.nextDouble() * 0.6D;
            double z = pos.getZ() + 0.3D + random.nextDouble() * 0.4D;
            level.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0D, -0.01D, 0.0D);
            if (charge >= 3 && random.nextInt(2) == 0) {
                level.addParticle(ParticleTypes.SOUL, x, y, z, 0.0D, 0.01D, 0.0D);
            }
        }
    }
}
