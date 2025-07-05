package uwu.juni.nonsense.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import uwu.juni.nonsense.Nonsense;
import uwu.juni.nonsense.content.NonsenseItems;

public class NonsenseItemModels extends ItemModelProvider {
	public NonsenseItemModels(PackOutput output, ExistingFileHelper fileHelper) {
		super(output, Nonsense.MODID, fileHelper);
	}
	
	@Override
	protected void registerModels() {
		basicItems(NonsenseItems.COPPER_COIL);
	}

	void basicItems(ItemLike... items) {
		for (var item : items) {
			basicItem(item.asItem());
		}
	}
}
