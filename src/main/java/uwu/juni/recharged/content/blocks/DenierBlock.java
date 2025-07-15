package uwu.juni.recharged.content.blocks;

import javax.annotation.ParametersAreNonnullByDefault;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

@ParametersAreNonnullByDefault
public class DenierBlock extends DiodeBlock implements SimpleWaterloggedBlock {
	public static final MapCodec<DenierBlock> CODEC = simpleCodec(DenierBlock::new);
	public static final IntegerProperty POWER = BlockStateProperties.POWER;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	@Override
	protected MapCodec<? extends DiodeBlock> codec() {
		return CODEC;
	}

	public DenierBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState()
			.setValue(POWERED, Boolean.FALSE)
			.setValue(WATERLOGGED, Boolean.FALSE)
		);
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACING, POWERED, POWER, WATERLOGGED);
	}

	@Override
	protected int getDelay(BlockState state) {
		return 0;
	}

	@Override
	protected int getInputSignal(Level level, BlockPos pos, BlockState state) {
		var dir = state.getValue(FACING);

		var dir1 = dir.getClockWise();
		var signal1 = level.getSignal(pos.relative(dir1), dir1);

		var dir2 = dir.getCounterClockWise();
		var signal2 = level.getSignal(pos.relative(dir2), dir2);

		var power = Math.max(signal1, signal2);
		level.setBlock(pos, state.setValue(POWER, (signal1 > 0) != (signal2 > 0) ? power : 0), 3);
		return power;
	}

	@Override
	protected int getOutputSignal(BlockGetter level, BlockPos pos, BlockState state) {
		return state.getValue(POWER);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
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
