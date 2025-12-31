package com.Fishmod.fur.block;

import com.Fishmod.fur.block.blockentity.SoulFurnaceBlockEntity;
import com.Fishmod.fur.init.FURBlockEntityRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

public class SoulFurnaceBlock extends BaseEntityBlock {
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	   
	public SoulFurnaceBlock(Properties props) {
		super(props);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof SoulFurnaceBlockEntity pot) {
                NetworkHooks.openScreen((ServerPlayer) player, pot, pos);
            }
        }
        
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
    
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_48689_) {
        return this.defaultBlockState().setValue(FACING, p_48689_.getHorizontalDirection().getOpposite());
	}
    
    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new SoulFurnaceBlockEntity(pos, state);
	}
	
	@Override
	public BlockState rotate(BlockState p_48722_, Rotation p_48723_) {
		return p_48722_.setValue(FACING, p_48723_.rotate(p_48722_.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState p_48719_, Mirror p_48720_) {
		return p_48719_.rotate(p_48720_.getRotation(p_48719_.getValue(FACING)));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_48725_) {
		p_48725_.add(FACING);
	}

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? null : createTickerHelper(type, FURBlockEntityRegistry.SOUL_FURNACE.get(), SoulFurnaceBlockEntity::tick);
    }
}
