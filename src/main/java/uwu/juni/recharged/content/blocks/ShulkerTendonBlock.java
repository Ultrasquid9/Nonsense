package uwu.juni.recharged.content.blocks;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@ParametersAreNonnullByDefault
public class ShulkerTendonBlock extends DirectionalBlock {
	public static final int DELAY = 4;
	public static final MapCodec<ShulkerTendonBlock> CODEC = simpleCodec(ShulkerTendonBlock::new);

	@Override
	protected MapCodec<? extends DirectionalBlock> codec() {
		return CODEC;
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		// Cursed workaround to Minecraft's weird rendering code, so that the block isn't darkened when placed next to another block
		return Block.box(0.01, 0.01, 0.01, 15.99, 15.99, 15.99);
	}

	public ShulkerTendonBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACING);
		super.createBlockStateDefinition(builder);
	}

	@Override
	protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
		level.scheduleTick(pos, this, DELAY);
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
		level.scheduleTick(pos, this, DELAY);
	}

	@Override
	protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (!level.hasNeighborSignal(pos)) {
			return;
		}

		var facing = state.getValue(FACING);
		var targetPos = pos.relative(facing);
		var targetState = level.getBlockState(targetPos);
		var targetStrength = targetState.getDestroySpeed(level, targetPos);

		if (targetStrength > 5 || targetStrength < 0 || !level.isInWorldBounds(targetPos)) {
			return;
		}	

		// TODO: make this work
		level.playLocalSound(pos, SoundEvents.SHULKER_TELEPORT, SoundSource.BLOCKS, 5, 1, false);

		level.destroyBlock(targetPos, true);

		level.setBlock(targetPos, state, UPDATE_ALL);
		level.setBlock(pos, Blocks.AIR.defaultBlockState(), UPDATE_ALL);
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite());
	}
}
