package uwu.juni.recharged.mixins;

import org.spongepowered.asm.mixin.Mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import uwu.juni.recharged.misc.RechargedConfig;

@Mixin(Feature.class)
public class PortalBreakFix {
	@WrapMethod(method = "setBlock")
	protected void setBlock(LevelWriter levelWriter, BlockPos pos, BlockState state, Operation<Void> og) {
		if (RechargedConfig.getConfigValue(RechargedConfig.FIX_PORTAL_BREAK) && levelWriter instanceof Level level) {
			if (level.getBlockState(pos).getBlock().defaultDestroyTime() < 0) {
				return;
			}
		}
		og.call(levelWriter, pos, state);
	}
}
