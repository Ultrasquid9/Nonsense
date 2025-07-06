package uwu.juni.nonsense.datagen;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import uwu.juni.nonsense.Nonsense;

@EventBusSubscriber(modid = Nonsense.MODID)
public class Datagen {
	@SubscribeEvent
	public static void datagen(GatherDataEvent event) {
		final var datagen = event.getGenerator();
		final var provider = event.getLookupProvider();
		final var helper = event.getExistingFileHelper();
		final var output = datagen.getPackOutput();

		datagen.addProvider(event.includeClient(), new NonsenseBlockModels(output, helper));
		datagen.addProvider(event.includeClient(), new NonsenseItemModels(output, helper));
		datagen.addProvider(event.includeServer(), new NonsenseRecipes(output, provider));
	}
}
