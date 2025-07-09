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
		dropSelf(RechargedBlocks.INJECTOR);
		dropSelf(RechargedBlocks.HIGH_SPEED_CABLE);
		dropSelf(RechargedBlocks.DENIER);
		dropSelf(RechargedBlocks.LATCH);
		dropSelf(RechargedBlocks.PRISM);
	}

	void dropSelf(DeferredBlock<?> b) {
		dropSelf(b.get());
	}
}
