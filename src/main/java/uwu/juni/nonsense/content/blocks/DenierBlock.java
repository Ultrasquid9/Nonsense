package uwu.juni.nonsense.content.blocks;

import javax.annotation.ParametersAreNonnullByDefault;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

@ParametersAreNonnullByDefault
public class DenierBlock extends DiodeBlock {
	public static final MapCodec<DenierBlock> CODEC = simpleCodec(DenierBlock::new);
	public static final IntegerProperty POWER = BlockStateProperties.POWER;

	@Override
	protected MapCodec<? extends DiodeBlock> codec() {
		return CODEC;
	}

	public DenierBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(POWERED, Boolean.FALSE));
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACING, POWERED, POWER);
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
}
