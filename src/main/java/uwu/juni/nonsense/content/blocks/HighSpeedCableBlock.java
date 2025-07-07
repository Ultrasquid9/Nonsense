package uwu.juni.nonsense.content.blocks;

import javax.annotation.ParametersAreNonnullByDefault;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

@ParametersAreNonnullByDefault
public class HighSpeedCableBlock extends DirectionalBlock {
	public static final MapCodec<HighSpeedCableBlock> CODEC = simpleCodec(HighSpeedCableBlock::new);
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

	@Override
	protected MapCodec<? extends DirectionalBlock> codec() {
		return CODEC;
	}

	public HighSpeedCableBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(POWERED, Boolean.FALSE));
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACING, POWERED);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite());
	}

	@Override
	protected boolean isSignalSource(BlockState state) {
		return true;
	}

	@Override
	protected int getSignal(
		BlockState state,
		BlockGetter level,
		BlockPos pos,
		Direction dir
	) {
		return (dir == state.getValue(FACING).getOpposite() && state.getValue(POWERED)) ? 15 : 0;
	}

	@Override
	protected int getDirectSignal(
		BlockState state,
		BlockGetter level,
		BlockPos pos,
		Direction direction
	) {
		return getSignal(state, level, pos, direction);
	}

	@Override
	protected void neighborChanged(
		BlockState state,
		Level level,
		BlockPos pos,
		Block neighborBlock,
		BlockPos neighborPos,
		boolean movedByPiston
	) {
		level.scheduleTick(pos, this, 1);
	}

	@Override
	protected void onPlace(
		BlockState state,
		Level level,
		BlockPos pos,
		BlockState oldState,
		boolean movedByPiston
	) {
		level.scheduleTick(pos, this, 1);
	}

	@Override
	protected void tick(
		BlockState state,
		ServerLevel level,
		BlockPos pos,
		RandomSource random
	) {
		level.setBlock(
			pos,
			state.setValue(POWERED, this.isPowered(level, pos, state)),
			3
		);
	}

	boolean isPowered(Level level, BlockPos pos, BlockState state) {
		for (var dir: Direction.values()) {
			if (dir == state.getValue(FACING)) {
				continue;
			}

			if (level.getSignal(pos.relative(dir), dir) > 0) {
				return true;
			}
		}
		return false;
	}
}
