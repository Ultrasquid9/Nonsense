package uwu.juni.recharged.events;

import net.minecraft.world.entity.npc.VillagerProfession;
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
	static void villagerTrades(VillagerTradesEvent event) {
		var trades = event.getTrades();

		if (event.getType() == VillagerProfession.MASON) {
			trades.get(1).add(offer(
				new ItemCost(Items.EMERALD),
				new ItemStack(Items.COBBLED_DEEPSLATE, 6),
				14,
				3
			));

			trades.get(2).add(offer(
				new ItemCost(Items.EMERALD),
				new ItemStack(Items.TUFF, 4),
				16,
				5
			));
			trades.get(2).add(offer(
				new ItemCost(Items.EMERALD),
				new ItemStack(Items.CALCITE, 4),
				16,
				5
			));
		}
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
