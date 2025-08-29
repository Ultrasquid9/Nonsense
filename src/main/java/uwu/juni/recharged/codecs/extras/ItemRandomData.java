package uwu.juni.recharged.codecs.extras;

import java.util.List;
import java.util.function.Function;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.ItemCost;

public record ItemRandomData(
	Holder<Item> item,
	MaybeRandomInt count,
	Either<DataComponentPredicate, List<DataComponentPredicate>> components
) {
	public static final Codec<ItemRandomData> CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			ItemStack.ITEM_NON_AIR_CODEC.fieldOf("id").forGetter(ItemRandomData::item),
			MaybeRandomInt.CODEC.optionalFieldOf("count", new MaybeRandomInt(1)).forGetter(ItemRandomData::count),
			Codec.either(DataComponentPredicate.CODEC, DataComponentPredicate.CODEC.listOf())
				.optionalFieldOf("components", Either.left(DataComponentPredicate.EMPTY))
				.forGetter(ItemRandomData::components)
		)
		.apply(instance, ItemRandomData::new)
	);

	DataComponentPredicate getComponents(RandomSource random) {
		return this.components.map(
			Function.identity(),
			l -> {
				var index = random.nextInt(0, l.size());
				return l.get(index);
			}
		);
	}

	public ItemStack toItemStack(RandomSource random) {
		return new ItemStack(
			this.item,
			this.count.get(random),
			this.getComponents(random).asPatch()
		);
	}

	public ItemCost toItemCost(RandomSource random) {
		return new ItemCost(
			this.item,
			this.count.get(random),
			this.getComponents(random)
		);
	}
}
