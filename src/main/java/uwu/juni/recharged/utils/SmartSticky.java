package uwu.juni.recharged.utils;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

@ParametersAreNonnullByDefault
public interface SmartSticky {
	boolean canStickTo(
		BlockState selfState,
		BlockState otherState,
		BlockPos selfPos,
		BlockPos otherPos,
		Direction dir
	);
}
