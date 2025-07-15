package uwu.juni.recharged.datagen.loot;

import java.util.function.BiConsumer;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import uwu.juni.recharged.Recharged;

@ParametersAreNonnullByDefault
public class RechargedMiscLoot implements LootTableSubProvider {
	public static final ResourceKey<LootTable> CORAL_EROSION = createKey("coral_erosion");

	static ResourceKey<LootTable> createKey(String name) {
		return ResourceKey.create(
			Registries.LOOT_TABLE, 
			Recharged.rLoc(name)
		);
	}
	
	public RechargedMiscLoot(HolderLookup.Provider provider) {}

	@Override
	public void generate(BiConsumer<ResourceKey<LootTable>, Builder> output) {
		output.accept(
			CORAL_EROSION,
			LootTable.lootTable().withPool(
				LootPool.lootPool().add(
					LootItem.lootTableItem(Items.SAND)
				)
			)
		);
	}
}
