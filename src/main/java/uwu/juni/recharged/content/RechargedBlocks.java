package uwu.juni.recharged.content;

import java.util.function.Supplier;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.StatePredicate;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import uwu.juni.recharged.Recharged;
import uwu.juni.recharged.content.blocks.DenierBlock;
import uwu.juni.recharged.content.blocks.HighSpeedCableBlock;
import uwu.juni.recharged.content.blocks.InjectorBlock;
import uwu.juni.recharged.content.blocks.LatchBlock;

public class RechargedBlocks {
	public static final DeferredRegister.Blocks REGISTER = DeferredRegister.createBlocks(Recharged.MODID);

	public static final DeferredBlock<?> INJECTOR = registerBlockAndItem(
		"injector",
		() -> new InjectorBlock(
			BlockBehaviour
				.Properties
				.ofFullCopy(Blocks.COPPER_BLOCK)
				.isViewBlocking(falsePredicate())
				.noOcclusion()
		)
	);

	public static final DeferredBlock<?> DENIER = registerBlockAndItem(
		"denier",
		() -> new DenierBlock(
			BlockBehaviour
				.Properties
				.ofFullCopy(Blocks.REPEATER)
		)
	);

	public static final DeferredBlock<?> LATCH = registerBlockAndItem(
		"latch",
		() -> new LatchBlock(
			BlockBehaviour
				.Properties
				.ofFullCopy(Blocks.COPPER_BLOCK)
				.isViewBlocking(falsePredicate())
				.noOcclusion()
		)
	);

	public static final DeferredBlock<?> HIGH_SPEED_CABLE = registerBlockAndItem(
		"high_speed_cable",
		() -> new HighSpeedCableBlock(
			BlockBehaviour
				.Properties
				.ofFullCopy(Blocks.COPPER_BLOCK)
				.isRedstoneConductor(falsePredicate())
		)
	);

	private static final <T extends Block> DeferredBlock<T> registerBlockAndItem(String name, Supplier<T> blockSupplier) {
		final var block = REGISTER.register(name, blockSupplier);
		RechargedItems.REGISTER.registerSimpleBlockItem(block);
		return block;
	}

	private static final StatePredicate falsePredicate() {
		return (a, b, c) -> false;
	}
}
