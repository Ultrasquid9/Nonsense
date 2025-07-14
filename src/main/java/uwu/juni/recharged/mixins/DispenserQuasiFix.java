package uwu.juni.recharged.mixins;

import org.spongepowered.asm.mixin.Mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import uwu.juni.recharged.RechargedConfig;

@Mixin(DispenserBlock.class)
public class DispenserQuasiFix {
	@WrapMethod(method = "neighborChanged")
	private void Recharged_FixedNeighborChange(
		BlockState state,
		Level level,
		BlockPos pos,
		Block block,
		BlockPos fromPos,
		boolean isMoving,
		Operation<Void> og
	) {
		if (!RechargedConfig.getConfigValue(RechargedConfig.DISABLE_QUASI_CONNECTIVITY)) {
			og.call(state, level, pos, block, fromPos, isMoving);
			return;
		}

		final var triggered = BlockStateProperties.TRIGGERED;

        var hasSignal = level.hasNeighborSignal(pos);
        var isTriggered = state.getValue(triggered);

        if (hasSignal && !isTriggered) {
            level.scheduleTick(pos, Blocks.DISPENSER, 4);
            level.setBlock(pos, state.setValue(triggered, Boolean.valueOf(true)), 2);
        } else if (!hasSignal && isTriggered) {
            level.setBlock(pos, state.setValue(triggered, Boolean.valueOf(false)), 2);
		}
	}
}
