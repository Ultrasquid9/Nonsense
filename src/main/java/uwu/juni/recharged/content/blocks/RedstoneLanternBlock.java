package uwu.juni.recharged.content.blocks;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

@ParametersAreNonnullByDefault
public class RedstoneLanternBlock extends LanternBlock {
	public static BooleanProperty LIT = BlockStateProperties.LIT;

	public RedstoneLanternBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(LIT, Boolean.valueOf(true)));
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(LIT);
		super.createBlockStateDefinition(builder);
	}

	@Override
	protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
		for (Direction direction : Direction.values()) {
			level.updateNeighborsAt(pos.relative(direction), this);
		}
	}

	@Override
	protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!isMoving) {
			for (var dir : Direction.values()) {
				level.updateNeighborsAt(pos.relative(dir), this);
			}
		}
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		var level = context.getLevel();
		var state = super.getStateForPlacement(context);

		if (state != null) {
			level.scheduleTick(context.getClickedPos(), this, 2);
		}

		return state;
	}

	@Override
	protected int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
		return blockState.getValue(LIT) && Direction.DOWN != side ? 15 : 0;
	}

	@Override
	protected int getDirectSignal(BlockState state, BlockGetter getter, BlockPos pos, Direction side) {
		return side == Direction.UP ? state.getSignal(getter, pos, side) : 0;
	}

	protected boolean hasNeighborSignal(Level level, BlockPos pos, BlockState state) {
		return level.hasSignal(pos.above(), Direction.UP);
	}

	@Override
	protected boolean isSignalSource(BlockState state) {
		return true;
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
		if (state.getValue(LIT) == this.hasNeighborSignal(level, pos, state) && !level.getBlockTicks().willTickThisTick(pos, this)) {
			level.scheduleTick(pos, this, 2);
		}
	}

	@Override
	protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		boolean flag = this.hasNeighborSignal(level, pos, state);

		if (state.getValue(LIT) && flag) {
			level.setBlock(pos, state.setValue(LIT, Boolean.FALSE), 3);
		} else if (!flag) {
			level.setBlock(pos, state.setValue(LIT, Boolean.TRUE), 3);
		}
	}
}
