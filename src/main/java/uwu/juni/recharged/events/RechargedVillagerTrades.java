package uwu.juni.recharged.events;

import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;

@EventBusSubscriber
public class RechargedVillagerTrades {
	@SubscribeEvent
	public static void villagerTrades(VillagerTradesEvent event) {
		final var tradeLookup = event.getRegistryAccess().lookupOrThrow(RechargedDatapacks.VILLAGER_TRADE);
		final var trades = event.getTrades();

		tradeLookup.listElements().forEach(ref -> {
			var trade = ref.value();

			if (!event.getType().name().contains(trade.profession())) {
				return;
			}

			trades.get(trade.level()).add(trade.toItemListing());
		});
	}

	@SubscribeEvent
	static void wandererTrades(WandererTradesEvent event) {
		var trades = event.getGenericTrades();

		trades.add(offer(
			new ItemCost(Items.EMERALD, 2),
			new ItemStack(Items.SPORE_BLOSSOM),
			3,
			5
		));
	}

	static ItemListing offer(
		ItemCost cost, 
		ItemStack stack,
		int maxUses,
		int exp
	) {
		return (a, b) -> new MerchantOffer(
			cost,
			stack,
			maxUses,
			exp,
			0.05F
		);
	}
}
