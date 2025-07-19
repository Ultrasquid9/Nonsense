package uwu.juni.recharged.mixins;

import javax.annotation.ParametersAreNonnullByDefault;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChainBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import uwu.juni.recharged.content.RechargedBlocks;

@Mixin(ChainBlock.class)
@ParametersAreNonnullByDefault
public abstract class ChainRedstoneLantern extends RotatedPillarBlock {
	private ChainRedstoneLantern() {
		super(null);
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
		var blockpos = pos;
		BlockState blockstate;

		for (var i = 0; i < 15; i++) {
			blockpos = blockpos.below();
			blockstate = level.getBlockState(blockpos);

			if (blockstate.getBlock() == RechargedBlocks.REDSTONE_LANTERN.get()) {
				level.scheduleTick(blockpos, blockstate.getBlock(), 2);
				break;
			}
			if (blockstate.getBlock() != Blocks.CHAIN) {
				break;
			}
			if (blockstate.getValue(BlockStateProperties.AXIS) != Direction.Axis.Y) {
				break;
			}
		}

		super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
	}
}
