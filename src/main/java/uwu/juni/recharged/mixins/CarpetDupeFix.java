package uwu.juni.recharged.mixins;

import javax.annotation.ParametersAreNonnullByDefault;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import uwu.juni.recharged.RechargedConfig;

@Mixin(CarpetBlock.class)
@ParametersAreNonnullByDefault
public abstract class CarpetDupeFix extends Block {
	private CarpetDupeFix() {
		super(null);
	}

	@Override
	public @Nullable PushReaction getPistonPushReaction(BlockState state) {
		return RechargedConfig.getConfigValue(RechargedConfig.PISTONS_BREAK_CARPETS)
			? PushReaction.DESTROY
			: super.getPistonPushReaction(state);
	}
}
