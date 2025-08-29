package uwu.juni.recharged.codecs;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.trading.MerchantOffer;
import uwu.juni.recharged.codecs.extras.ItemRandomData;

public record VillagerTrade(
	String profession,
	int level,
	Offer offer
) {
	public static final Codec<VillagerTrade> CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			Codec.STRING.fieldOf("profession").forGetter(VillagerTrade::profession),
			Codec.INT.fieldOf("level").forGetter(VillagerTrade::level),
			Offer.CODEC.fieldOf("offer").forGetter(VillagerTrade::offer)
		)
		.apply(instance, VillagerTrade::new)
	);

	public ItemListing toItemListing() {
		return this.offer::toMerchantOffer;
	}

	public static record Offer(
		ItemRandomData costA,
		Optional<ItemRandomData> costB,
		ItemRandomData result,
		int maxUses,
		int exp,
		float multiplier
	) {
		public static final Codec<VillagerTrade.Offer> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
				ItemRandomData.CODEC.fieldOf("cost_a").forGetter(VillagerTrade.Offer::costA),
				ExtraCodecs.optionalEmptyMap(ItemRandomData.CODEC)
					.optionalFieldOf("cost_b", Optional.empty())
					.forGetter(VillagerTrade.Offer::costB),
				ItemRandomData.CODEC.fieldOf("result").forGetter(VillagerTrade.Offer::result),
				Codec.INT.fieldOf("max_uses").forGetter(VillagerTrade.Offer::maxUses),
				Codec.INT.fieldOf("exp").forGetter(VillagerTrade.Offer::exp),
				Codec.FLOAT.fieldOf("multiplier").forGetter(VillagerTrade.Offer::multiplier)
			)
			.apply(instance, VillagerTrade.Offer::new)
		);

		MerchantOffer toMerchantOffer(Entity entity, RandomSource random) {
			return new MerchantOffer(
				this.costA.toItemCost(random),
				this.costB.map(i -> i.toItemCost(random)),
				this.result.toItemStack(random),
				this.maxUses,
				this.exp,
				this.multiplier
			);
		}
	}
}
