package uwu.juni.recharged.mixins.waterlog;

import javax.annotation.ParametersAreNonnullByDefault;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

@Mixin(LeverBlock.class)
@ParametersAreNonnullByDefault
public abstract class WaterlogLevers extends FaceAttachedHorizontalDirectionalBlock implements SimpleWaterloggedBlock {
	private WaterlogLevers() {
		super(null);
	}

	@WrapMethod(method = "createBlockStateDefinition")
	protected void Recharged_AddWaterlogBlockState(Builder<Block, BlockState> builder, Operation<Void> og) {
		builder.add(BlockStateProperties.WATERLOGGED);
		og.call(builder);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void Recharged_SetWaterlogDefaultState(BlockBehaviour.Properties properties, CallbackInfo ci) {
		this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, Boolean.FALSE));
	}

	@WrapMethod(method = "updateShape")
	private BlockState Recharged_UpdateShapeWaterlog(
		BlockState state,
		Direction dir,
		BlockState neighborState,
		LevelAccessor level,
		BlockPos pos,
		BlockPos neighborPos,
		Operation<BlockState> og
	) {
		if (state.getValue(BlockStateProperties.WATERLOGGED)) {
			level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}
		return og.call(state, dir, neighborState, level, pos, neighborPos);
	}

	@Override
	protected FluidState getFluidState(BlockState state) {
		return state.getValue(BlockStateProperties.WATERLOGGED)
			? Fluids.WATER.getSource(false)
			: super.getFluidState(state);
	}


	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		var fluidstate = context.getLevel().getFluidState(context.getClickedPos());
		var flag = fluidstate.getType() == Fluids.WATER;

		var state = super.getStateForPlacement(context);
		return state != null
			? state.setValue(BlockStateProperties.WATERLOGGED, flag)
			: null;
	}
}
