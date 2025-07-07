package uwu.juni.nonsense.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import uwu.juni.nonsense.misc.NonsenseConfig;

@Mixin(DebugScreenOverlay.class)
public abstract class ModOnlyDebugScreen {
	@Shadow
	private final Minecraft minecraft;

	private ModOnlyDebugScreen() {
		throw new Error();
	}

	@WrapMethod(method = "showDebugScreen")
	private boolean Nonsense_ModOnlyShowDebugScreen(Operation<Boolean> og) {
		if (!NonsenseConfig.getConfigValue(NonsenseConfig.MOD_ONLY_DEBUG_SCREEN)) {
			return og.call();
		}

		return og.call() && minecraft.player != null && minecraft.player.hasPermissions(1);
	}
}
