package uwu.juni.nonsense.content;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import uwu.juni.nonsense.Nonsense;

public class NonsenseItems {
	public static final DeferredRegister.Items REGISTER = DeferredRegister.createItems(Nonsense.MODID);

	// Example item
	// TODO - remove
	public static final DeferredItem<Item> EXAMPLE_ITEM = REGISTER.registerSimpleItem(
		"example_item",
		new Item
			.Properties()
			.food(new FoodProperties
				.Builder()
				.alwaysEdible()
				.nutrition(1)
				.saturationModifier(2f)
				.build()
			)
	);
}
