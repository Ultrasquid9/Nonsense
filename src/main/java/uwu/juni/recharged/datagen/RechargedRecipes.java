package uwu.juni.recharged.datagen;

import java.util.concurrent.CompletableFuture;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import uwu.juni.recharged.content.RechargedBlocks;
import uwu.juni.recharged.content.RechargedItems;

@ParametersAreNonnullByDefault
public class RechargedRecipes extends RecipeProvider {
    protected RechargedRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider);
    }

	@Override
	protected void buildRecipes(RecipeOutput output) {
		ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, RechargedItems.COPPER_COIL, 3)
			.requires(Items.REDSTONE)
			.requires(Items.STICK)
			.requires(Items.COPPER_INGOT, 3)
			.unlockedBy(getHasName(Items.REDSTONE), has(Items.REDSTONE))
			.save(output);

		ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, RechargedBlocks.INJECTOR, 1)
			.define('C', RechargedItems.COPPER_COIL)
			.define('I', Items.COPPER_INGOT)
			.define('H', Items.HOPPER)
			.pattern("CIC")
			.pattern("CHC")
			.unlockedBy(getHasName(RechargedItems.COPPER_COIL), has(RechargedItems.COPPER_COIL))
			.save(output);

		ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, RechargedBlocks.LATCH, 1)
			.define('C', RechargedItems.COPPER_COIL)
			.define('I', Items.COPPER_INGOT)
			.define('A', Items.AMETHYST_SHARD)
			.pattern("CAC")
			.pattern("III")
			.unlockedBy(getHasName(RechargedItems.COPPER_COIL), has(RechargedItems.COPPER_COIL))
			.save(output);

		ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, RechargedBlocks.DENIER, 1)
			.define('A', Items.AMETHYST_SHARD)
			.define('R', Items.REDSTONE)
			.define('S', Items.STONE)
			.pattern(" A ")
			.pattern("RAR")
			.pattern("SSS")
			.unlockedBy(getHasName(Items.AMETHYST_SHARD), has(Items.AMETHYST_SHARD))
			.save(output);

		ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, RechargedBlocks.RESISTOR, 1)
			.define('P', Items.POPPED_CHORUS_FRUIT)
			.define('R', Items.REDSTONE_TORCH)
			.define('S', Items.STONE)
			.pattern("RPR")
			.pattern("SSS")
			.unlockedBy(getHasName(Items.AMETHYST_SHARD), has(Items.AMETHYST_SHARD))
			.save(output);
		
		ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, RechargedBlocks.HIGH_SPEED_CABLE, 1)
			.define('C', RechargedItems.COPPER_COIL)
			.define('I', Items.IRON_INGOT)
			.define('R', Items.REPEATER)
			.pattern("CIC")
			.pattern("CRC")
			.pattern("CIC")
			.unlockedBy(getHasName(RechargedItems.COPPER_COIL), has(RechargedItems.COPPER_COIL))
			.save(output);

		ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, RechargedBlocks.PRISM, 1)
			.define('C', RechargedItems.COPPER_COIL)
			.define('P', Items.PRISMARINE_CRYSTALS)
			.pattern("CPC")
			.pattern("P P")
			.pattern("CPC")
			.unlockedBy(getHasName(Items.PRISMARINE_CRYSTALS), has(Items.PRISMARINE_CRYSTALS))
			.save(output);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, RechargedBlocks.OBSIDIAN_STAIRS, 4)
			.define('O', Items.OBSIDIAN)
			.pattern("  O")
			.pattern(" OO")
			.pattern("OOO")
			.unlockedBy(getHasName(Items.OBSIDIAN), has(Items.OBSIDIAN))
			.save(output);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, RechargedBlocks.OBSIDIAN_SLAB, 6)
			.define('O', Items.OBSIDIAN)
			.pattern("OOO")
			.unlockedBy(getHasName(Items.OBSIDIAN), has(Items.OBSIDIAN))
			.save(output);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, RechargedBlocks.OBSIDIAN_WALL, 6)
			.define('O', Items.OBSIDIAN)
			.pattern("OOO")
			.pattern("OOO")
			.unlockedBy(getHasName(Items.OBSIDIAN), has(Items.OBSIDIAN))
			.save(output);
	}
}
