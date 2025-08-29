package uwu.juni.recharged.events;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import uwu.juni.recharged.Recharged;
import uwu.juni.recharged.codecs.VillagerTrade;

@EventBusSubscriber(modid = Recharged.MODID)
public class RechargedDatapacks {
	public static final ResourceKey<Registry<VillagerTrade>> VILLAGER_TRADE = ResourceKey.createRegistryKey(
		Recharged.rLoc("villager_trades")
	);

	@SubscribeEvent
	public static void datapackRegistry(DataPackRegistryEvent.NewRegistry event) {
		event.dataPackRegistry(VILLAGER_TRADE, VillagerTrade.CODEC);
	}
}
