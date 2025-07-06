package uwu.juni.nonsense.datagen;

import java.util.List;
import java.util.Set;

import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableProvider.SubProviderEntry;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import uwu.juni.nonsense.Nonsense;
import uwu.juni.nonsense.datagen.loot.NonsenseBlockLoot;
import uwu.juni.nonsense.datagen.tags.NonsenseBlockTags;

@EventBusSubscriber(modid = Nonsense.MODID)
public class Datagen {
	@SubscribeEvent
	public static void datagen(GatherDataEvent event) {
		final var datagen = event.getGenerator();
		final var provider = event.getLookupProvider();
		final var helper = event.getExistingFileHelper();
		final var output = datagen.getPackOutput();

		// Normal //

		datagen.addProvider(event.includeClient(), new NonsenseBlockModels(output, helper));
		datagen.addProvider(event.includeClient(), new NonsenseItemModels(output, helper));
		datagen.addProvider(event.includeServer(), new NonsenseRecipes(output, provider));

		// Tags //

		final var blocktags = new NonsenseBlockTags(output, provider, helper);
		datagen.addProvider(event.includeServer(), blocktags);

		// Loot //

		final var entries = List.of(
			new SubProviderEntry(NonsenseBlockLoot::new, LootContextParamSets.BLOCK)
		);
		datagen.addProvider(event.includeServer(), new LootTableProvider(
			output, 
			Set.of(), 
			entries,
			provider
		));
	}
}
