package uwu.juni.recharged.misc;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTab.TabVisibility;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import uwu.juni.recharged.content.RechargedBlocks;
import uwu.juni.recharged.content.RechargedItems;

public class RechargedCreative {
	public static void addToCreative(BuildCreativeModeTabContentsEvent event) {
		new CreativeBuilder(event)
			.tab(CreativeModeTabs.REDSTONE_BLOCKS)
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
				RechargedBlocks.LATCH
			)
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
