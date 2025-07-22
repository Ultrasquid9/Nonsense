package uwu.juni.recharged.mixins;

import javax.annotation.ParametersAreNonnullByDefault;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChainBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import uwu.juni.recharged.RechargedConfig;
import uwu.juni.recharged.utils.SmartSticky;

@Mixin(ChainBlock.class)
@ParametersAreNonnullByDefault
public class Chainstone extends RotatedPillarBlock implements SimpleWaterloggedBlock, SmartSticky {
	private Chainstone() {
		super(null);
	}

	@Override
	public boolean isStickyBlock(BlockState state) {
		return RechargedConfig.getConfigValue(RechargedConfig.CHAINSTONE);
	}

	@Override
	public boolean canStickTo(BlockState selfState, BlockState otherState, BlockPos selfPos, BlockPos otherPos) {
		if (selfState.getBlock() != Blocks.CHAIN || otherState.getBlock() != Blocks.CHAIN) {
			return selfState.canStickTo(otherState) || otherState.canStickTo(selfState);
		}

		if (checkAxis(selfState, selfPos, otherPos) || checkAxis(otherState, otherPos, selfPos)) {
			return true;
		} else {
			return selfState.canStickTo(otherState) || otherState.canStickTo(selfState);
		}
	}

	private boolean checkAxis(BlockState selfState, BlockPos selfPos, BlockPos otherPos) {
		BlockPos pos1;
		BlockPos pos2;

		switch (selfState.getValue(AXIS)) {
			case X -> {
				pos1 = selfPos.north();
				pos2 = selfPos.south();	
			}
			case Y -> {
				pos1 = selfPos.above();
				pos2 = selfPos.below();
			}
			case Z -> {
				pos1 = selfPos.east();
				pos2 = selfPos.west();
			}
			default -> throw new Error();
		}

		return otherPos == pos1 || otherPos == pos2;
	}
}
