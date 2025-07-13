package uwu.juni.recharged.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import uwu.juni.recharged.Recharged;
import uwu.juni.recharged.content.RechargedBlocks;
import uwu.juni.recharged.content.blocks.ResistorBlock;

public class RechargedBlockModels extends BlockStateProvider {
	public RechargedBlockModels(PackOutput output, ExistingFileHelper helper) {
		super(output, Recharged.MODID, helper);
	}

	@Override
	protected void registerStatesAndModels() {
		getVariantBuilder(RechargedBlocks.INJECTOR.get()).forAllStates(state -> {
			return ConfiguredModel.builder()
				.modelFile(models().getExistingFile(modLoc("block/injector")))
				.rotationY((int)state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot())
				.build();
		});

		getVariantBuilder(RechargedBlocks.HIGH_SPEED_CABLE.get()).forAllStates(state -> {
			boolean powered = state.getValue(BlockStateProperties.POWERED);
			var name = !powered
				? "high_speed_cable"
				: "high_speed_cable_on";
			var top = !powered
				? "top"
				: "top_on";

			return ConfiguredModel.builder()
				.modelFile(models()
					.withExistingParent(name, mcLoc("cube_bottom_top"))
					.texture("side", modLoc("block/high_speed_cable_side"))
					.texture("bottom", modLoc("block/high_speed_cable_bottom"))
					.texture("top", modLoc("block/high_speed_cable_" + top))
				)
				.rotationX(switch(state.getValue(BlockStateProperties.FACING)) {
					case UP -> 0;
					case DOWN -> 180;
					default -> 90;
				})
				.rotationY(switch(state.getValue(BlockStateProperties.FACING)) {
					case NORTH -> 0;
					case SOUTH -> 180;
					case EAST -> 90;
					case WEST -> 270;
					default -> 0;
				})
				.build();
		});

		getVariantBuilder(RechargedBlocks.DENIER.get()).forAllStates(state -> {
			var name = "denier";

			var powered = state.getValue(BlockStateProperties.POWERED);
			var power = state.getValue(BlockStateProperties.POWER);

			if (powered) {
				name = power > 0 
					? "denier_one" 
					: "denier_both";
			}

			return ConfiguredModel.builder()
				.modelFile(models()
					.withExistingParent(name, modLoc("denier_base"))
					.texture("top", modLoc("block/" + name))
				)
				.rotationY((int)state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot())
				.build();
		});

		getVariantBuilder(RechargedBlocks.RESISTOR.get()).forAllStatesExcept(state -> {
			var name = "resistor";

			var powered = state.getValue(BlockStateProperties.POWERED);

			if (powered) {
				name += "_on";
			}

			return ConfiguredModel.builder()
				.modelFile(models()
					.withExistingParent(name, modLoc("resistor_base"))
					.texture("torch", mcLoc("block/redstone_torch" + (powered ? "" : "_off")))
				)
				.rotationY((int)state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot())
				.build();
		}, ResistorBlock.RESISTANCE);

		getVariantBuilder(RechargedBlocks.LATCH.get()).forAllStates(state -> {
			var powered = state.getValue(BlockStateProperties.POWERED);

			return ConfiguredModel.builder()
				.modelFile(models()
					.withExistingParent(powered ? "latch_on" : "latch", modLoc("latch_base"))
					.texture("torch", mcLoc("block/redstone_torch" + (powered ? "" : "_off")))
					.texture("front", modLoc("block/latch_front"))
					.texture("back", modLoc("block/latch_front" + (powered ? "_on" : "")))
					.texture("side", modLoc("block/latch_side" + (powered ? "_on" : "")))
				)
				.rotationY((int)state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot())
				.build();
		});

		getVariantBuilder(RechargedBlocks.PRISM.get()).forAllStates(state -> {
			var name = "prism_" + String.valueOf(state.getValue(BlockStateProperties.POWER));

			return ConfiguredModel.builder()
				.modelFile(models()
					.withExistingParent(name, mcLoc("cube_all"))
					.texture("all", modLoc("block/" + name))
				)
				.build();
		});

		final var obsidian = mcLoc("block/obsidian");
		stairsBlock(RechargedBlocks.OBSIDIAN_STAIRS.get(), obsidian);
		slabBlock(RechargedBlocks.OBSIDIAN_SLAB.get(), obsidian, obsidian);
		wallBlock(RechargedBlocks.OBSIDIAN_WALL.get(), obsidian);
	}
}
