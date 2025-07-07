package uwu.juni.nonsense.misc;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTab.TabVisibility;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import uwu.juni.nonsense.content.NonsenseBlocks;
import uwu.juni.nonsense.content.NonsenseItems;

public class NonsenseCreative {
	public static void addToCreative(BuildCreativeModeTabContentsEvent event) {
		new CreativeBuilder(event)
			.tab(CreativeModeTabs.REDSTONE_BLOCKS)
			.after(Items.TARGET)
			.insert(NonsenseBlocks.HIGH_SPEED_CABLE)
			.after(Items.HOPPER)
			.insert(NonsenseBlocks.INJECTOR)
			.tab(CreativeModeTabs.INGREDIENTS)
			.after(Items.NETHERITE_INGOT)
			.insert(NonsenseItems.COPPER_COIL);
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
