package uwu.juni.nonsense.content;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import uwu.juni.nonsense.Nonsense;

public class NonsenseItems {
	public static final DeferredRegister.Items REGISTER = DeferredRegister.createItems(Nonsense.MODID);

	public static final DeferredItem<Item> COPPER_COIL = REGISTER.registerSimpleItem("copper_coil");
}
