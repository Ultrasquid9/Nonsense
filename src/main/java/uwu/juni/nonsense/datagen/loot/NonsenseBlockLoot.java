package uwu.juni.nonsense.datagen.loot;

import java.util.Set;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import uwu.juni.nonsense.content.NonsenseBlocks;

public class NonsenseBlockLoot extends BlockLootSubProvider {
	public NonsenseBlockLoot(HolderLookup.Provider provider) {
		super(Set.of(), FeatureFlags.DEFAULT_FLAGS, provider);
	}

	@Override
	protected Iterable<Block> getKnownBlocks() {
		return NonsenseBlocks
			.REGISTER
			.getEntries()
			.stream()
			.map(block -> (Block) block.value())
			.toList();
	}

	@Override
	protected void generate() {
		dropSelf(NonsenseBlocks.INJECTOR);
		dropSelf(NonsenseBlocks.HIGH_SPEED_CABLE);
		dropSelf(NonsenseBlocks.DENIER);
	}

	void dropSelf(DeferredBlock<?> b) {
		dropSelf(b.get());
	}
}
