package uwu.juni.recharged.datagen.tags;

import java.util.concurrent.CompletableFuture;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import uwu.juni.recharged.Recharged;
import uwu.juni.recharged.content.RechargedBlocks;
import uwu.juni.recharged.datagen.RechargedTags;

@ParametersAreNonnullByDefault
public class RechargedBlockTags extends BlockTagsProvider {
	public RechargedBlockTags(
		PackOutput output,
		CompletableFuture<HolderLookup.Provider> provider,
		ExistingFileHelper helper
	) {
		super(output, provider, Recharged.MODID, helper);
	}

	@Override
	protected void addTags(Provider provider) {
		tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
			RechargedBlocks.INJECTOR.get(),
			RechargedBlocks.LATCH.get(),
			RechargedBlocks.HIGH_SPEED_CABLE.get(),
			RechargedBlocks.PRISM.get(),

			RechargedBlocks.OBSIDIAN_STAIRS.get(),
			RechargedBlocks.OBSIDIAN_SLAB.get(),
			RechargedBlocks.OBSIDIAN_WALL.get(),

			RechargedBlocks.REDSTONE_LANTERN.get(),
			RechargedBlocks.GLOW_LANTERN.get()
		);
		tag(BlockTags.NEEDS_STONE_TOOL).add(
			RechargedBlocks.INJECTOR.get(),
			RechargedBlocks.LATCH.get(),
			RechargedBlocks.HIGH_SPEED_CABLE.get(),
			RechargedBlocks.PRISM.get()
		);
		tag(BlockTags.NEEDS_DIAMOND_TOOL).add(
			RechargedBlocks.OBSIDIAN_STAIRS.get(),
			RechargedBlocks.OBSIDIAN_SLAB.get(),
			RechargedBlocks.OBSIDIAN_WALL.get()
		);
		tag(BlockTags.WALLS).add(
			RechargedBlocks.OBSIDIAN_WALL.get()
		);
		tag(RechargedTags.Blocks.DEAD_CORALS).add(
			Blocks.DEAD_BRAIN_CORAL,
			Blocks.DEAD_BRAIN_CORAL_BLOCK,
			Blocks.DEAD_BRAIN_CORAL_FAN,
			Blocks.DEAD_BRAIN_CORAL_WALL_FAN,
			Blocks.DEAD_BUBBLE_CORAL,
			Blocks.DEAD_BUBBLE_CORAL_BLOCK,
			Blocks.DEAD_BUBBLE_CORAL_FAN,
			Blocks.DEAD_BUBBLE_CORAL_WALL_FAN,
			Blocks.DEAD_FIRE_CORAL,
			Blocks.DEAD_FIRE_CORAL_BLOCK,
			Blocks.DEAD_FIRE_CORAL_FAN,
			Blocks.DEAD_FIRE_CORAL_WALL_FAN,
			Blocks.DEAD_HORN_CORAL,
			Blocks.DEAD_HORN_CORAL_BLOCK,
			Blocks.DEAD_HORN_CORAL_FAN,
			Blocks.DEAD_HORN_CORAL_WALL_FAN,
			Blocks.DEAD_TUBE_CORAL,
			Blocks.DEAD_TUBE_CORAL_BLOCK,
			Blocks.DEAD_TUBE_CORAL_FAN,
			Blocks.DEAD_TUBE_CORAL_WALL_FAN
		);
	}
}
