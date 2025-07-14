package uwu.juni.recharged.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import uwu.juni.recharged.RechargedConfig;

@Mixin(CarpetBlock.class)
public class CarpetDupeFix {
	@Inject(method = "<init>", at = @At("HEAD"))
	private static void Recharged_BrokenByPiston(BlockBehaviour.Properties properties, CallbackInfo ci) {
		if (RechargedConfig.getConfigValue(RechargedConfig.PISTONS_BREAK_CARPETS)) {
			properties.pushReaction(PushReaction.DESTROY);
		}
	}
}
