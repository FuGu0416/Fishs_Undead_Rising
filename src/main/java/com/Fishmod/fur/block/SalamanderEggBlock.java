package com.Fishmod.fur.block;

import java.util.UUID;

import javax.annotation.Nullable;

import com.Fishmod.fur.block.blockentity.SalamanderEggBlockEntity;
import com.Fishmod.fur.data.providers.FURBlockTagsProvider;
import com.Fishmod.fur.entities.tameable.SalamanderEntity;
import com.Fishmod.fur.init.FUREntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SalamanderEggBlock extends BaseEntityBlock {
	public static final int MAX_HATCH_LEVEL = 2;
	public static final IntegerProperty HATCH = BlockStateProperties.HATCH;
	public static final IntegerProperty VARIANT = BlockStateProperties.AGE_1;
	private static final int REGULAR_HATCH_TIME_TICKS = 24000;
	private static final int BOOSTED_HATCH_TIME_TICKS = 12000;
	private static final int RANDOM_HATCH_OFFSET_TICKS = 300;
	private static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 8.0D, 11.0D);

	public SalamanderEggBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(HATCH, Integer.valueOf(0)).setValue(VARIANT, Integer.valueOf(0)));
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateDefinition) {
		stateDefinition.add(HATCH, VARIANT);
	}

	public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext cxt) {
		return SHAPE;
	}

	public int getHatchLevel(BlockState state) {
		return state.getValue(HATCH);
	}

	private boolean isReadyToHatch(BlockState state) {
		return this.getHatchLevel(state) == 2;
	}

	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
		if (!this.isReadyToHatch(state)) {
			level.playSound((Player)null, pos, SoundEvents.SNIFFER_EGG_CRACK, SoundSource.BLOCKS, 0.7F, 0.9F + rand.nextFloat() * 0.2F);
			level.setBlock(pos, state.setValue(HATCH, Integer.valueOf(this.getHatchLevel(state) + 1)), 2);
		} else {
			level.playSound((Player)null, pos, SoundEvents.SNIFFER_EGG_HATCH, SoundSource.BLOCKS, 0.7F, 0.9F + rand.nextFloat() * 0.2F);
			BlockEntity be = level.getBlockEntity(pos);

		    if (!(be instanceof SalamanderEggBlockEntity eggBE)) return;
		    
	    	SalamanderEntity entity = FUREntityRegistry.SALAMANDER.get().create(level);

	    	if (entity != null) {
				UUID uuid = eggBE.getPlacer();
				Vec3 vec3 = pos.getCenter();
				
				if (uuid != null) {
					entity.setOwnerUUID(uuid);
					entity.setTame(true);
				}
				
				entity.setBaby(true);
				entity.moveTo(vec3.x(), vec3.y(), vec3.z(), Mth.wrapDegrees(level.random.nextFloat() * 360.0F), 0.0F);
				entity.setSkin(eggBE.getSkin());	    		
	            level.addFreshEntity(entity);
	            level.destroyBlock(pos, false);
	    	}
		}
	}
	
	@Override
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
	    if (!level.isClientSide && placer instanceof Player player) {
	        BlockEntity be = level.getBlockEntity(pos);
	        int variant = 0;
	        if (be instanceof SalamanderEggBlockEntity eggBE) {
	            eggBE.setPlacer(player.getUUID());
	            
	            if (stack.getTag() != null && stack.getTag().contains("variant")) {
	            	variant = stack.getTag().getInt("variant");
	            }
	            
	            eggBE.setSkin(variant);
	            level.setBlock(pos, state.setValue(VARIANT, Integer.valueOf(variant)), 2);	
	        }
	    }
	}

	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
		boolean flag = hatchBoost(level, pos);
		if (!level.isClientSide() && flag) {
			level.levelEvent(3009, pos, 0);
		}

		int i = flag ? BOOSTED_HATCH_TIME_TICKS : REGULAR_HATCH_TIME_TICKS;
		int j = i / 3;
		level.gameEvent(GameEvent.BLOCK_PLACE, pos, GameEvent.Context.of(state));
		level.scheduleTick(pos, this, j + level.random.nextInt(RANDOM_HATCH_OFFSET_TICKS));
	}

	public boolean isPathfindable(BlockState state, BlockGetter getter, BlockPos pos, PathComputationType type) {
		return false;
	}

	public static boolean hatchBoost(BlockGetter getter, BlockPos pos) {
		return getter.getBlockState(pos.below()).is(FURBlockTagsProvider.SALAMANDER_EGG_HATCH_BOOST);
	}

	@Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
	   
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new SalamanderEggBlockEntity(pos, state);
	}
}