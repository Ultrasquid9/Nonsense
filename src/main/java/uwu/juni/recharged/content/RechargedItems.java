package uwu.juni.recharged.content;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import uwu.juni.recharged.Recharged;

public class RechargedItems {
	public static final DeferredRegister.Items REGISTER = DeferredRegister.createItems(Recharged.MODID);

	public static final DeferredItem<Item> COPPER_COIL = REGISTER.registerSimpleItem("copper_coil");
}
