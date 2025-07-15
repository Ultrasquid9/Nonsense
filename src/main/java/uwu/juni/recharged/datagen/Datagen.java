package uwu.juni.recharged.datagen;

import java.util.List;
import java.util.Set;

import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableProvider.SubProviderEntry;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import uwu.juni.recharged.Recharged;
import uwu.juni.recharged.datagen.loot.RechargedBlockLoot;
import uwu.juni.recharged.datagen.loot.RechargedMiscLoot;
import uwu.juni.recharged.datagen.tags.RechargedBlockTags;

@EventBusSubscriber(modid = Recharged.MODID)
public class Datagen {
	@SubscribeEvent
	public static void datagen(GatherDataEvent event) {
		final var datagen = event.getGenerator();
		final var provider = event.getLookupProvider();
		final var helper = event.getExistingFileHelper();
		final var output = datagen.getPackOutput();

		// Normal //

		datagen.addProvider(event.includeClient(), new RechargedBlockModels(output, helper));
		datagen.addProvider(event.includeClient(), new RechargedItemModels(output, helper));
		datagen.addProvider(event.includeServer(), new RechargedRecipes(output, provider));

		// Tags //

		final var blocktags = new RechargedBlockTags(output, provider, helper);
		datagen.addProvider(event.includeServer(), blocktags);

		// Loot //

		final var entries = List.of(
			new SubProviderEntry(RechargedBlockLoot::new, LootContextParamSets.BLOCK),
			new SubProviderEntry(RechargedMiscLoot::new, LootContextParamSets.ALL_PARAMS)
		);
		datagen.addProvider(event.includeServer(), new LootTableProvider(
			output, 
			Set.of(), 
			entries,
			provider
		));
	}
}
