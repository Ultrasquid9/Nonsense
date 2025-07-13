package uwu.juni.recharged.mixins;

import org.spongepowered.asm.mixin.Mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.piston.MovingPistonBlock;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

@Mixin(BlockBehaviour.BlockStateBase.class)
public class PistonSideSupport {
	@WrapMethod(method = "Lnet/minecraft/world/level/block/state/BlockBehaviour$BlockStateBase;isFaceSturdy(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;Lnet/minecraft/world/level/block/SupportType;)Z")
	public boolean Recharged_IsPistonOrSturdy(
		BlockGetter level,
		BlockPos pos,
		Direction direction,
		SupportType supportType,
		Operation<Boolean> og
	) {
		var block = level.getBlockState(pos).getBlock();
		if (block instanceof PistonBaseBlock || block instanceof MovingPistonBlock) {
			return true;
		}
		return og.call(level, pos, direction, supportType);
	}
}
