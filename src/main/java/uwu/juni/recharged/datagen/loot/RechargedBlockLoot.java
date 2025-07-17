package uwu.juni.recharged.datagen.loot;

import java.util.Set;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import uwu.juni.recharged.content.RechargedBlocks;

public class RechargedBlockLoot extends BlockLootSubProvider {
	public RechargedBlockLoot(HolderLookup.Provider provider) {
		super(Set.of(), FeatureFlags.DEFAULT_FLAGS, provider);
	}

	@Override
	protected Iterable<Block> getKnownBlocks() {
		return RechargedBlocks
			.REGISTER
			.getEntries()
			.stream()
			.map(block -> (Block) block.value())
			.toList();
	}

	@Override
	protected void generate() {
		dropSelf(
			RechargedBlocks.INJECTOR,
			RechargedBlocks.HIGH_SPEED_CABLE,
			RechargedBlocks.DENIER,
			RechargedBlocks.RESISTOR,
			RechargedBlocks.LATCH,
			RechargedBlocks.PRISM,
			RechargedBlocks.OBSIDIAN_STAIRS,
			RechargedBlocks.OBSIDIAN_SLAB,
			RechargedBlocks.OBSIDIAN_WALL,
			RechargedBlocks.REDSTONE_LANTERN,
			RechargedBlocks.GLOW_LANTERN,
			RechargedBlocks.GLOW_TORCH
		);
	}

	void dropSelf(DeferredBlock<?>... blocks) {
		for (var block : blocks) {
			dropSelf(block);
		}
	}

	void dropSelf(DeferredBlock<?> b) {
		dropSelf(b.get());
	}
}
