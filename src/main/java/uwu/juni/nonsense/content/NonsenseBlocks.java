package uwu.juni.nonsense.content;

import java.util.function.Supplier;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import uwu.juni.nonsense.Nonsense;

public class NonsenseBlocks {
	public static final DeferredRegister.Blocks REGISTER = DeferredRegister.createBlocks(Nonsense.MODID);

	// TODO - remove
	public static final DeferredBlock<Block> EXAMPLE_BLOCK = registerBlockAndItem("example_block", () -> new Block(
		BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
	));

	private static final <T extends Block> DeferredBlock<T> registerBlockAndItem(String name, Supplier<T> blockSupplier) {
		final var block = REGISTER.register(name, blockSupplier);
		NonsenseItems.REGISTER.registerSimpleBlockItem(block);
		return block;
	}
}
