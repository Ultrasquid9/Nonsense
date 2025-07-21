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
import net.minecraft.world.level.ItemLike;
import uwu.juni.recharged.content.RechargedBlocks;
import uwu.juni.recharged.content.RechargedItems;

@ParametersAreNonnullByDefault
public class RechargedRecipes extends RecipeProvider {
    protected RechargedRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider);
    }

	@Override
	protected void buildRecipes(RecipeOutput output) {
		modRecipes(output);
		vanillaRecipes(output);
	}

	void modRecipes(RecipeOutput output) {
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

		ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, RechargedBlocks.REDSTONE_LANTERN, 1)
			.define('I', Items.IRON_NUGGET)
			.define('R', Items.REDSTONE_TORCH)
			.pattern("III")
			.pattern("IRI")
			.pattern("III")
			.unlockedBy(getHasName(Items.REDSTONE_TORCH), has(Items.REDSTONE_TORCH))
			.save(output);

		ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, RechargedBlocks.GLOW_LANTERN, 1)
			.define('I', Items.IRON_NUGGET)
			.define('T', RechargedItems.GLOW_TORCH)
			.pattern("III")
			.pattern("ITI")
			.pattern("III")
			.unlockedBy(getHasName(RechargedItems.GLOW_TORCH), has(RechargedItems.GLOW_TORCH))
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

		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, RechargedItems.GLOW_TORCH, 3)
			.define('G', Items.GLOW_INK_SAC)
			.define('S', Items.STICK)
			.pattern("G")
			.pattern("S")
			.unlockedBy(getHasName(Items.GLOW_INK_SAC), has(Items.GLOW_INK_SAC))
			.save(output);

		ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, RechargedItems.SATA_ANDAGI, 2)
			.requires(Items.EGG)
			.requires(Items.WHEAT)
			.requires(Items.SUGAR)
			.unlockedBy(getHasName(Items.WHEAT), has(Items.WHEAT))
			.save(output);
	}

	void vanillaRecipes(RecipeOutput output) {
		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Items.GILDED_BLACKSTONE)
			.requires(Items.BLACKSTONE)
			.requires(Items.GOLD_INGOT)
			.unlockedBy(getHasName(Items.BLACKSTONE), has(Items.BLACKSTONE))
			.save(output);

		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.NAME_TAG)
			.requires(Items.IRON_NUGGET)
			.requires(Items.INK_SAC)
			.requires(Items.PAPER)
			.unlockedBy(getHasName(Items.PAPER), has(Items.PAPER))
			.save(output);
		
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.SADDLE, 1)
			.define('X', Items.LEATHER)
			.define('#', Items.IRON_INGOT)
			.pattern(" X ")
			.pattern("X#X")
			.unlockedBy(getHasName(Items.LEATHER), has(Items.LEATHER))
			.save(output);

		horseArmor(Items.IRON_HORSE_ARMOR, Items.IRON_INGOT, output);
		horseArmor(Items.GOLDEN_HORSE_ARMOR, Items.GOLD_INGOT, output);
		horseArmor(Items.DIAMOND_HORSE_ARMOR, Items.DIAMOND, output);
	}

	void horseArmor(ItemLike armor, ItemLike material, RecipeOutput output) {
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, armor, 1)
			.define('X', material)
			.pattern("X X")
			.pattern("XXX")
			.pattern("X X")
			.unlockedBy(getHasName(material), has(material))
			.save(output);
	}
}
