package uwu.juni.recharged.datagen;

import java.util.function.Supplier;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import uwu.juni.recharged.Recharged;
import uwu.juni.recharged.content.RechargedBlocks;
import uwu.juni.recharged.content.RechargedItems;

public class RechargedItemModels extends ItemModelProvider {
	public RechargedItemModels(PackOutput output, ExistingFileHelper fileHelper) {
		super(output, Recharged.MODID, fileHelper);
	}
	
	@Override
	protected void registerModels() {
		basicItems(
			RechargedItems.COPPER_COIL,
			RechargedBlocks.INJECTOR,
			RechargedBlocks.DENIER
		);

		simpleBlockItems(
			RechargedBlocks.HIGH_SPEED_CABLE,
			RechargedBlocks.LATCH
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
