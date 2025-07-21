package uwu.juni.recharged.events;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTab.TabVisibility;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import uwu.juni.recharged.content.RechargedBlocks;
import uwu.juni.recharged.content.RechargedItems;

@EventBusSubscriber
public class RechargedCreative {
	@SubscribeEvent
	static void addToCreative(BuildCreativeModeTabContentsEvent event) {
		new CreativeBuilder(event)
			.tab(CreativeModeTabs.BUILDING_BLOCKS)
			.after(Items.PURPUR_SLAB)
			.insert(
				Blocks.OBSIDIAN,
				RechargedBlocks.OBSIDIAN_STAIRS,
				RechargedBlocks.OBSIDIAN_SLAB,
				RechargedBlocks.OBSIDIAN_WALL
			)
			.tab(CreativeModeTabs.FUNCTIONAL_BLOCKS)
			.after(Items.SOUL_TORCH)
			.insert(RechargedItems.GLOW_TORCH)
			.after(Items.SOUL_LANTERN)
			.insert(
				RechargedBlocks.GLOW_LANTERN,
				RechargedBlocks.REDSTONE_LANTERN
			)
			.tab(CreativeModeTabs.REDSTONE_BLOCKS)
			.after(Items.REDSTONE_TORCH)
			.insert(RechargedBlocks.REDSTONE_LANTERN)
			.after(Items.TARGET)
			.insert(
				RechargedBlocks.HIGH_SPEED_CABLE,
				RechargedBlocks.PRISM
			)
			.after(Items.HOPPER)
			.insert(RechargedBlocks.INJECTOR)
			.after(Items.COMPARATOR)
			.insert(
				RechargedBlocks.DENIER,
				RechargedBlocks.RESISTOR,
				RechargedBlocks.LATCH
			)
			.after(Items.WHITE_WOOL)
			.insert(
				Blocks.OBSIDIAN,
				RechargedBlocks.OBSIDIAN_STAIRS,
				RechargedBlocks.OBSIDIAN_SLAB,
				RechargedBlocks.OBSIDIAN_WALL
			)
			.tab(CreativeModeTabs.FOOD_AND_DRINKS)
			.after(Items.PUMPKIN_PIE)
			.insert(RechargedItems.SATA_ANDAGI)
			.tab(CreativeModeTabs.INGREDIENTS)
			.after(Items.NETHERITE_INGOT)
			.insert(RechargedItems.COPPER_COIL);
	}

	private static class CreativeBuilder {
		private final BuildCreativeModeTabContentsEvent event;

		private ResourceKey<CreativeModeTab> tab = null;
		private ItemLike previous = null;

		CreativeBuilder(BuildCreativeModeTabContentsEvent event) {
			this.event = event;
		}

		public CreativeBuilder tab(ResourceKey<CreativeModeTab> tab) {
			this.tab = tab;
			return this;
		}

		public CreativeBuilder after(ItemLike start) {
			this.previous = start;
			return this;
		}

		public CreativeBuilder insert(ItemLike... items) {
			if (event.getTabKey() != this.tab) {
				return this;
			}

			for (var next : items) {
				event.insertAfter(
					new ItemStack(this.previous), 
					new ItemStack(next),
					TabVisibility.PARENT_AND_SEARCH_TABS
				);

				this.previous = next;
			}

			return this;
		}
	}
}
