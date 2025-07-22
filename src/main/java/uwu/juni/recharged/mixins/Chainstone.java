package uwu.juni.recharged.mixins;

import javax.annotation.ParametersAreNonnullByDefault;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
	public boolean canStickTo(
		BlockState selfState,
		BlockState otherState,
		BlockPos selfPos,
		BlockPos otherPos,
		Direction dir
	) {
		return selfState.getValue(AXIS) == dir.getAxis();
	}
}
