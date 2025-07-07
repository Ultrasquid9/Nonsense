package uwu.juni.nonsense.datagen;

import java.util.function.Supplier;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import uwu.juni.nonsense.Nonsense;
import uwu.juni.nonsense.content.NonsenseBlocks;
import uwu.juni.nonsense.content.NonsenseItems;

public class NonsenseItemModels extends ItemModelProvider {
	public NonsenseItemModels(PackOutput output, ExistingFileHelper fileHelper) {
		super(output, Nonsense.MODID, fileHelper);
	}
	
	@Override
	protected void registerModels() {
		basicItems(
			NonsenseItems.COPPER_COIL,
			NonsenseBlocks.INJECTOR
		);

		simpleBlockItems(
			NonsenseBlocks.HIGH_SPEED_CABLE
		);
	}

	final void basicItems(ItemLike... items) {
		for (var item : items) {
			basicItem(item.asItem());
		}
	}

	@SafeVarargs
	final void simpleBlockItems(Supplier<? extends Block>... blocks) {
		for (var block : blocks) {
			simpleBlockItem(block.get());
		}
	}
}
