package uwu.juni.nonsense.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import uwu.juni.nonsense.Nonsense;
import uwu.juni.nonsense.content.NonsenseBlocks;

public class NonsenseBlockModels extends BlockStateProvider {
	public NonsenseBlockModels(PackOutput output, ExistingFileHelper helper) {
		super(output, Nonsense.MODID, helper);
	}

	@Override
	protected void registerStatesAndModels() {
		getVariantBuilder(NonsenseBlocks.INJECTOR.get()).forAllStates(state -> {
			return ConfiguredModel.builder()
				.modelFile(models().withExistingParent("injector2", this.modLoc("block/injector")))
				.rotationY((int)state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot())
				.build();
		});
	}
}
