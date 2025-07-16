package uwu.juni.recharged.content;

import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import uwu.juni.recharged.Recharged;

public class RechargedItems {
	public static final DeferredRegister.Items REGISTER = DeferredRegister.createItems(Recharged.MODID);

	public static final DeferredItem<Item> COPPER_COIL = REGISTER.registerSimpleItem("copper_coil");

	public static final DeferredItem<?> GLOW_TORCH = REGISTER.register(
		"glow_torch",
		() -> new StandingAndWallBlockItem(
			RechargedBlocks.GLOW_TORCH.get(),
			RechargedBlocks.GLOW_WALL_TORCH.get(),
			new Item.Properties(),
			Direction.DOWN
		)
	);
}
