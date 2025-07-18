package uwu.juni.recharged.content.blocks;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import uwu.juni.recharged.content.blocks.block_entities.LatchBlockEntity;

@ParametersAreNonnullByDefault
public class LatchBlock extends DiodeBlock implements EntityBlock {
	public static final MapCodec<LatchBlock> CODEC = simpleCodec(LatchBlock::new);
	private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 7, 16);
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	@Override
	protected MapCodec<? extends DiodeBlock> codec() {
		return CODEC;
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	public LatchBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState()
			.setValue(POWERED, Boolean.FALSE)
			.setValue(WATERLOGGED, Boolean.FALSE)
		);
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACING, POWERED, WATERLOGGED);
	}

	@Override
	@Nullable
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new LatchBlockEntity(pos, state);
	}

	@Override
	protected int getDelay(BlockState state) {
		return 2;
	}

	@Override
	protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		return true;
	}

	@Override
	protected boolean shouldTurnOn(Level level, BlockPos pos, BlockState state) {
		var signal = this.getInputSignal(level, pos, state);

		if (level.getBlockEntity(pos) instanceof LatchBlockEntity latch) {
			signal |= latch.getOutputSignal();
		}

		return signal != 0;
	}

	@Override
	protected void neighborChanged(
		BlockState state,
		Level level,
		BlockPos pos,
		Block block,
		BlockPos fromPos,
		boolean isMoving
	) {
		if (this.getReleaseSignal(level, pos, state) > 0) {
			level.scheduleTick(pos, this, this.getDelay(state));
		}

		super.neighborChanged(state, level, pos, block, fromPos, isMoving);
	}

	@Override
	protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		this.updateLatch(level, pos, state);
		super.tick(state, level, pos, random);
	}

	@Override
	protected int getInputSignal(Level level, BlockPos pos, BlockState state) {
		var input = super.getInputSignal(level, pos, state);

		if (level.getBlockEntity(pos) instanceof LatchBlockEntity latch) {
			input = Math.max(input, latch.getOutputSignal());
		}
		
		return input;
	}
	
	void updateLatch(Level level, BlockPos pos, BlockState state) {
		var release = this.getReleaseSignal(level, pos, state);
		var input = super.getInputSignal(level, pos, state);

		if (level.getBlockEntity(pos) instanceof LatchBlockEntity latch) {
			latch.setHasUb(false);
			
			if (release > 0 && input > 0) {
				latch.setHasUb(true);
				latch.setUbOutputSignal(level.getRandom());
			} else if (release > 0) {
				latch.setOutputSignal(0);
			} else if (input > 0) {
				latch.setOutputSignal(input);
			}

			this.updateNeighborsInFront(level, pos, state);
		}
	}

	protected int getReleaseSignal(Level level, BlockPos pos, BlockState state) {
		var dir = state.getValue(FACING).getCounterClockWise();
		var release = level.getSignal(pos.relative(dir), dir);
		
		return release;
	}

	@Override
	protected int getOutputSignal(BlockGetter level, BlockPos pos, BlockState state) {
		if (level.getBlockEntity(pos) instanceof LatchBlockEntity latch) {
			return latch.getOutputSignal();
		}
		return 0;
	}


	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		if (!super.canSurvive(this.defaultBlockState(), context.getLevel(), context.getClickedPos())) {
			return null;
		}

		var fluidstate = context.getLevel().getFluidState(context.getClickedPos());
		var flag = fluidstate.getType() == Fluids.WATER;
		return super.getStateForPlacement(context).setValue(BlockStateProperties.WATERLOGGED, flag);
	}

	@Override
	protected FluidState getFluidState(BlockState state) {
		return state.getValue(BlockStateProperties.WATERLOGGED)
			? Fluids.WATER.getSource(false)
			: super.getFluidState(state);
	}

	@Override
	protected BlockState updateShape(
		BlockState state,
		Direction dir,
		BlockState neighborState,
		LevelAccessor level,
		BlockPos pos,
		BlockPos neighborPos
	) {
		if (state.getValue(BlockStateProperties.WATERLOGGED)) {
			level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}
		return super.updateShape(state, dir, neighborState, level, pos, neighborPos);
	}
}
