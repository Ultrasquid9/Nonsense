package uwu.juni.recharged.mixins;

import org.spongepowered.asm.mixin.Mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SignalGetter;
import net.minecraft.world.level.block.piston.MovingPistonBlock;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import uwu.juni.recharged.RechargedConfig;

@Mixin(PistonBaseBlock.class)
public class PistonBugfixes {
	@WrapMethod(method = "getNeighborSignal")
	private boolean Recharged_FixedNeighborSignal(
		SignalGetter signalGetter,
		BlockPos pos,
		Direction facing,
		Operation<Boolean> og
	) {
		if (!RechargedConfig.getConfigValue(RechargedConfig.DISABLE_QUASI_CONNECTIVITY)) {
			return og.call(signalGetter, pos, facing);
		}

		for (var dir : Direction.values()) {
			if (dir != facing && signalGetter.hasSignal(pos.relative(dir), dir)) {
				return true;
			}
		}

		return signalGetter.hasSignal(pos, Direction.DOWN);
	}

	@WrapMethod(method = "triggerEvent")
	protected boolean Recharged_FixedHeadless(
		BlockState state,
		Level level,
		BlockPos pos,
		int id,
		int param,
		Operation<Boolean> og
	) {
		if (!RechargedConfig.getConfigValue(RechargedConfig.FIX_PORTAL_BREAK)) {
			return og.call(state, level, pos, id, param);
		}

		var newPos = pos.relative(state.getValue(BlockStateProperties.FACING));
		var block = level.getBlockState(newPos).getBlock();
		var pistonHeadExists = block instanceof PistonHeadBlock || block instanceof MovingPistonBlock;
		var extended = state.getValue(BlockStateProperties.EXTENDED);

		if (!pistonHeadExists && extended) {
			level.setBlock(pos, state.setValue(BlockStateProperties.EXTENDED, Boolean.FALSE), 2);
			return false;
		}

		return og.call(state, level, pos, id, param);
	}
}
