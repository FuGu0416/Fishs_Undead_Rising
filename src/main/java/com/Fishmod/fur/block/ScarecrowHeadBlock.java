package com.Fishmod.fur.block;

import com.Fishmod.fur.block.blockentity.ScarecrowHead_commonTileEntity;
import com.Fishmod.fur.block.blockentity.ScarecrowHead_plagueTileEntity;
import com.Fishmod.fur.block.blockentity.ScarecrowHead_strawTileEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ScarecrowHeadBlock extends BaseEntityBlock implements Equipable {
	private final ScarecrowHeadBlock.Types type;
	public static final int MAX = RotationSegment.getMaxSegmentIndex();
	private static final int ROTATIONS = MAX + 1;
	public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;
	protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D);
	   
    public ScarecrowHeadBlock(ScarecrowHeadBlock.Types typeIn, BlockBehaviour.Properties propertiesIn) {
        super(propertiesIn);
        this.type = typeIn;
        this.registerDefaultState(this.stateDefinition.any().setValue(ROTATION, Integer.valueOf(0)));
    }

    public VoxelShape getShape(BlockState p_56331_, BlockGetter p_56332_, BlockPos p_56333_, CollisionContext p_56334_) {
        return SHAPE;
	}

	public VoxelShape getOcclusionShape(BlockState p_56336_, BlockGetter p_56337_, BlockPos p_56338_) {
		return Shapes.empty();
	}

	public BlockState getStateForPlacement(BlockPlaceContext p_56321_) {
        return this.defaultBlockState().setValue(ROTATION, Integer.valueOf(RotationSegment.convertToSegment(p_56321_.getRotation())));
	}

	public BlockState rotate(BlockState p_56326_, Rotation p_56327_) {
        return p_56326_.setValue(ROTATION, Integer.valueOf(p_56327_.rotate(p_56326_.getValue(ROTATION), ROTATIONS)));
	}

	public BlockState mirror(BlockState p_56323_, Mirror p_56324_) {
        return p_56323_.setValue(ROTATION, Integer.valueOf(p_56324_.mirror(p_56323_.getValue(ROTATION), ROTATIONS)));
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_56329_) {
        p_56329_.add(ROTATION);
	}
     
	@Override
	public BlockEntity newBlockEntity(BlockPos Pos, BlockState State) {
		if (this.type.equals(ScarecrowHeadBlock.Types.SCARECROW_COMMON)) {
			return new ScarecrowHead_commonTileEntity(Pos, State);
		} else if (this.type.equals(ScarecrowHeadBlock.Types.SCARECROW_STRAW)) {
			return new ScarecrowHead_strawTileEntity(Pos, State);
		} else if (this.type.equals(ScarecrowHeadBlock.Types.SCARECROW_PLAGUE)) {
			return new ScarecrowHead_plagueTileEntity(Pos, State);
		}
		
		return new ScarecrowHead_commonTileEntity(Pos, State);
	}
	
	public boolean isPathfindable(BlockState p_48750_, BlockGetter p_48751_, BlockPos p_48752_, PathComputationType p_48753_) {
		return false;
	}

	public EquipmentSlot getEquipmentSlot() {
		return EquipmentSlot.HEAD;
	}
   
    public interface Type {
    }
    
    public static enum Types implements ScarecrowHeadBlock.Type {
    	SCARECROW_COMMON,
    	SCARECROW_STRAW,
    	SCARECROW_PLAGUE;
	}
}
