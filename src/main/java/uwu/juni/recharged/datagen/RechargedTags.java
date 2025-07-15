package uwu.juni.recharged.datagen;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import uwu.juni.recharged.Recharged;

public class RechargedTags {
	public static final class Blocks {
		public static final TagKey<Block> DEAD_CORALS = TagKey.create(
			Registries.BLOCK,
			Recharged.rLoc("dead_corals")
		);			
	}
}
