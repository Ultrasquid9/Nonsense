package uwu.juni.nonsense.content;

import java.util.function.Supplier;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.StatePredicate;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import uwu.juni.nonsense.Nonsense;
import uwu.juni.nonsense.content.blocks.InjectorBlock;

public class NonsenseBlocks {
	public static final DeferredRegister.Blocks REGISTER = DeferredRegister.createBlocks(Nonsense.MODID);

	public static final DeferredBlock<InjectorBlock> INJECTOR = registerBlockAndItem(
		"injector",
		() -> new InjectorBlock(
			BlockBehaviour
				.Properties
				.of()
				.mapColor(MapColor.COLOR_ORANGE)
				.requiresCorrectToolForDrops()
				.strength(3.0F, 6.0F)
				.isViewBlocking(falsePredicate())
				.noOcclusion()
				.sound(SoundType.COPPER)
		)
	);

	private static final <T extends Block> DeferredBlock<T> registerBlockAndItem(String name, Supplier<T> blockSupplier) {
		final var block = REGISTER.register(name, blockSupplier);
		NonsenseItems.REGISTER.registerSimpleBlockItem(block);
		return block;
	}

	private static final StatePredicate falsePredicate() {
		return (a, b, c) -> false;
	}
}
