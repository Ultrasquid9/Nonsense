package uwu.juni.recharged.mixins;

import java.util.ArrayList;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import uwu.juni.recharged.RechargedConfig;

@Mixin(RedStoneWireBlock.class)
public abstract class RedstoneUpdateOrderFix {
	@Shadow
	abstract int calculateTargetStrength(Level level, BlockPos pos);
	
	@WrapMethod(method = "updatePowerStrength")
	private void Recharged_UpdateOrderFix(Level level, BlockPos pos, BlockState state, Operation<Void> og) {
		if (!RechargedConfig.getConfigValue(RechargedConfig.NON_POSITIONAL_REDSTONE)) {
			og.call(level, pos, state);
			return;
		}
		
		var power = this.calculateTargetStrength(level, pos);
		if (state.getValue(BlockStateProperties.POWER) == power) {
			return;
		}

		if (level.getBlockState(pos) == state) {
			level.setBlock(pos, state.setValue(BlockStateProperties.POWER, Integer.valueOf(power)), 2);
		}

		var list = new ArrayList<BlockPos>();
		list.add(pos);

		for (Direction direction : Direction.values()) {
			list.add(pos.relative(direction));
		}

		for (var blockpos : list) {
			level.updateNeighborsAt(blockpos, (RedStoneWireBlock)(Object)this);
		}
	}
}
