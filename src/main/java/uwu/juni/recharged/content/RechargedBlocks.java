package uwu.juni.recharged.content;

import java.util.function.Supplier;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.StatePredicate;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import uwu.juni.recharged.Recharged;
import uwu.juni.recharged.content.blocks.DenierBlock;
import uwu.juni.recharged.content.blocks.GlowTorchBlock;
import uwu.juni.recharged.content.blocks.GlowWallTorchBlock;
import uwu.juni.recharged.content.blocks.HighSpeedCableBlock;
import uwu.juni.recharged.content.blocks.InjectorBlock;
import uwu.juni.recharged.content.blocks.LatchBlock;
import uwu.juni.recharged.content.blocks.PrismBlock;
import uwu.juni.recharged.content.blocks.ResistorBlock;

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
			BlockBehaviour.Properties.ofFullCopy(Blocks.REPEATER)
		)
	);

	public static final DeferredBlock<?> RESISTOR = registerBlockAndItem(
		"resistor",
		() -> new ResistorBlock(
			BlockBehaviour.Properties.ofFullCopy(Blocks.REPEATER)
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

	public static final DeferredBlock<?> PRISM = registerBlockAndItem(
		"prism",
		() -> new PrismBlock(
			BlockBehaviour
				.Properties
				.ofFullCopy(Blocks.SEA_LANTERN)
				.isRedstoneConductor(falsePredicate())
				.lightLevel(PrismBlock::getLightLevel)
		)
	);

	public static final DeferredBlock<StairBlock> OBSIDIAN_STAIRS = registerBlockAndItem(
		"obsidian_stairs",
		() -> new StairBlock(
			Blocks.OBSIDIAN.defaultBlockState(),
			BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
		)
	);

	public static final DeferredBlock<SlabBlock> OBSIDIAN_SLAB = registerBlockAndItem(
		"obsidian_slab",
		() -> new SlabBlock(
			BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
		)
	);

	public static final DeferredBlock<WallBlock> OBSIDIAN_WALL = registerBlockAndItem(
		"obsidian_wall",
		() -> new WallBlock(
			BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
		)
	);

	public static final DeferredBlock<GlowTorchBlock> GLOW_TORCH = REGISTER.register(
		"glow_torch",
		() -> new GlowTorchBlock(
			BlockBehaviour.Properties.ofFullCopy(Blocks.TORCH).lightLevel(a -> 12)
		)
	);

	public static final DeferredBlock<GlowWallTorchBlock> GLOW_WALL_TORCH = REGISTER.register(
		"glow_wall_torch",
		() -> new GlowWallTorchBlock(
			BlockBehaviour.Properties.ofFullCopy(Blocks.WALL_TORCH).lightLevel(a -> 12).lootFrom(GLOW_TORCH)
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
