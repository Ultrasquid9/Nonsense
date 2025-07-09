package uwu.juni.recharged.content.blocks;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import uwu.juni.recharged.content.RechargedBlockEntities;
import uwu.juni.recharged.content.blocks.block_entities.InjectorBlockEntity;

@ParametersAreNonnullByDefault
public class InjectorBlock extends BaseEntityBlock {
	public static final MapCodec<InjectorBlock> CODEC = simpleCodec(InjectorBlock::new);
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

	private static final VoxelShape SHAPE_BASE = Shapes.or(
		Block.box(6, 0, 6, 10, 2, 10), 
		Block.box(4, 2, 4, 12, 5, 12)
	);
	private static final VoxelShape SHAPE_NORTH_SOUTH = Shapes.or(
		Block.box(3, 5, 0, 13, 12, 16),
		SHAPE_BASE
	);
	private static final VoxelShape SHAPE_EAST_WEST = Shapes.or(
		Block.box(0, 5, 3, 16, 12, 13),
		SHAPE_BASE
	);

	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return CODEC;
	}

	public InjectorBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	protected VoxelShape getShape(
		BlockState state,
		BlockGetter level,
		BlockPos pos,
		CollisionContext context
	) {
		return switch (state.getValue(FACING)) {
			case Direction.NORTH, Direction.SOUTH -> SHAPE_NORTH_SOUTH;
			case Direction.EAST, Direction.WEST -> SHAPE_EAST_WEST;
			default -> Shapes.block();
		};
	}

	@Override
	protected RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
	@Nullable
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new InjectorBlockEntity(pos, state);
	}

	@Override
	protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		Containers.dropContentsOnDestroy(state, newState, level, pos);
		super.onRemove(state, level, pos, newState, isMoving);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
		Level level,
		BlockState state,
		BlockEntityType<T> blockEntityType
	) {
		return level.isClientSide
			? null
			: createTickerHelper(
				blockEntityType, 
				RechargedBlockEntities.INJECTOR.get(),
				InjectorBlockEntity::onTick
			);
	}
}
