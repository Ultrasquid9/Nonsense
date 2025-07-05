package uwu.juni.nonsense.mixins;

import org.spongepowered.asm.mixin.Mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.SignalGetter;
import net.minecraft.world.level.block.piston.PistonBaseBlock;

@Mixin(PistonBaseBlock.class)
public class PistonQuasiFix {
	@WrapMethod(method = "getNeighborSignal")
	private boolean Nonsense_FixedNeighborSignal(
		SignalGetter signalGetter,
		BlockPos pos,
		Direction facing,
		Operation<Boolean> og
	) {
		for (var dir : Direction.values()) {
			if (dir != facing && signalGetter.hasSignal(pos.relative(dir), dir)) {
				return true;
			}
		}

		return signalGetter.hasSignal(pos, Direction.DOWN);
	}
}
