package uwu.juni.nonsense.content.blocks;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import uwu.juni.nonsense.content.blocks.block_entities.LatchBlockEntity;

@ParametersAreNonnullByDefault
public class LatchBlock extends DiodeBlock implements EntityBlock {
	public static final MapCodec<LatchBlock> CODEC = simpleCodec(LatchBlock::new);

	private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 7, 16);

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
		this.registerDefaultState(this.defaultBlockState().setValue(POWERED, Boolean.FALSE));
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACING, POWERED);
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
	protected int getInputSignal(Level level, BlockPos pos, BlockState state) {
		var dir = state.getValue(FACING).getCounterClockWise();
		var release = level.getSignal(pos.relative(dir), dir);
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
			return latch.getOutputSignal();
		}

		return 0;
	}

	@Override
	protected int getOutputSignal(BlockGetter level, BlockPos pos, BlockState state) {
		if (level.getBlockEntity(pos) instanceof LatchBlockEntity latch) {
			return latch.getOutputSignal();
		}
		return 0;
	}
}
