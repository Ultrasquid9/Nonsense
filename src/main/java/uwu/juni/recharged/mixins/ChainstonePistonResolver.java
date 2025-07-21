package uwu.juni.recharged.mixins;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.piston.PistonStructureResolver;
import net.minecraft.world.level.block.state.BlockState;
import uwu.juni.recharged.RechargedConfig;
import uwu.juni.recharged.utils.SmartSticky;

@Mixin(PistonStructureResolver.class)
public abstract class ChainstonePistonResolver {
	@Shadow
	private Level level;

	@Shadow
	abstract Direction getPushDirection();
	@Shadow
	abstract boolean addBlockLine(BlockPos pos, Direction dir);

	@WrapMethod(method = "addBranchingBlocks")
	private boolean Recharged_ChainstoneAddBranchingBlocks(BlockPos fromPos, Operation<Boolean> og) {
		if (!RechargedConfig.getConfigValue(RechargedConfig.CHAINSTONE)) {
			return og.call(fromPos);
		}

		var fromState = this.level.getBlockState(fromPos);

		for (var dir : Direction.values()) {
			if (dir.getAxis() == this.getPushDirection().getAxis()) {
				continue;
			}

			var pos = fromPos.relative(dir);
			var state = this.level.getBlockState(pos);

			var opt = checkSmartSticky(state, fromState, pos, fromPos);
			if (opt.isPresent()) {
				if (!opt.get() && !this.addBlockLine(pos, dir)) {
					return false;
				}
			} else if (state.canStickTo(fromState) && fromState.canStickTo(state) && !this.addBlockLine(pos, dir)) {
				return false;
			}
		}

		return true;
	}

	private Optional<Boolean> checkSmartSticky(BlockState selfState, BlockState otherState, BlockPos selfPos, BlockPos otherPos) {
			if (selfState.getBlock() instanceof SmartSticky sticky) {
				return Optional.of(
					sticky.canStickTo(selfState, otherState, selfPos, otherPos)
					&& sticky.canStickTo(otherState, selfState, otherPos, selfPos)
				);
			} else if (otherState.getBlock() instanceof SmartSticky sticky) {
				return Optional.of(
					sticky.canStickTo(selfState, otherState, selfPos, otherPos)
					&& sticky.canStickTo(otherState, selfState, otherPos, selfPos)
				);
			}

		return Optional.empty();
	}
}
