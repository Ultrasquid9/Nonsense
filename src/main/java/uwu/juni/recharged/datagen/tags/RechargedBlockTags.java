package uwu.juni.recharged.datagen.tags;

import java.util.concurrent.CompletableFuture;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import uwu.juni.recharged.Recharged;
import uwu.juni.recharged.content.RechargedBlocks;

@ParametersAreNonnullByDefault
public class RechargedBlockTags extends BlockTagsProvider {
	public RechargedBlockTags(
		PackOutput output,
		CompletableFuture<HolderLookup.Provider> provider,
		ExistingFileHelper helper
	) {
		super(output, provider, Recharged.MODID, helper);
	}

	@Override
	protected void addTags(Provider provider) {
		tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
			RechargedBlocks.INJECTOR.get(),
			RechargedBlocks.LATCH.get(),
			RechargedBlocks.HIGH_SPEED_CABLE.get(),
			RechargedBlocks.PRISM.get(),

			RechargedBlocks.OBSIDIAN_STAIRS.get(),
			RechargedBlocks.OBSIDIAN_SLAB.get(),
			RechargedBlocks.OBSIDIAN_WALL.get()
		);
		tag(BlockTags.NEEDS_STONE_TOOL).add(
			RechargedBlocks.INJECTOR.get(),
			RechargedBlocks.LATCH.get(),
			RechargedBlocks.HIGH_SPEED_CABLE.get(),
			RechargedBlocks.PRISM.get()
		);
		tag(BlockTags.NEEDS_DIAMOND_TOOL).add(
			RechargedBlocks.OBSIDIAN_STAIRS.get(),
			RechargedBlocks.OBSIDIAN_SLAB.get(),
			RechargedBlocks.OBSIDIAN_WALL.get()
		);
		tag(BlockTags.WALLS).add(
			RechargedBlocks.OBSIDIAN_WALL.get()
		);
	}
}
