package uwu.juni.nonsense.datagen.tags;

import java.util.concurrent.CompletableFuture;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import uwu.juni.nonsense.Nonsense;
import uwu.juni.nonsense.content.NonsenseBlocks;

@ParametersAreNonnullByDefault
public class NonsenseBlockTags extends BlockTagsProvider {
	public NonsenseBlockTags(
		PackOutput output,
		CompletableFuture<HolderLookup.Provider> provider,
		ExistingFileHelper helper
	) {
		super(output, provider, Nonsense.MODID, helper);
	}

	@Override
	protected void addTags(Provider provider) {
		tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
			NonsenseBlocks.INJECTOR.get()
		);
		tag(BlockTags.NEEDS_STONE_TOOL).add(
			NonsenseBlocks.INJECTOR.get()
		);
	}
}
