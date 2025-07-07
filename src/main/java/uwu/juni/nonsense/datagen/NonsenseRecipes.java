package uwu.juni.nonsense.datagen;

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
import uwu.juni.nonsense.content.NonsenseBlocks;
import uwu.juni.nonsense.content.NonsenseItems;

@ParametersAreNonnullByDefault
public class NonsenseRecipes extends RecipeProvider {
    protected NonsenseRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider);
    }

	@Override
	protected void buildRecipes(RecipeOutput output) {
		ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, NonsenseItems.COPPER_COIL, 3)
			.requires(Items.REDSTONE)
			.requires(Items.STICK)
			.requires(Items.COPPER_INGOT, 3)
			.unlockedBy(getHasName(Items.REDSTONE), has(Items.REDSTONE))
			.save(output);

		ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, NonsenseBlocks.INJECTOR, 1)
			.define('C', NonsenseItems.COPPER_COIL)
			.define('I', Items.COPPER_INGOT)
			.define('H', Items.HOPPER)
			.pattern("CIC")
			.pattern("CHC")
			.unlockedBy(getHasName(NonsenseItems.COPPER_COIL), has(NonsenseItems.COPPER_COIL))
			.save(output);
		
		ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, NonsenseBlocks.HIGH_SPEED_CABLE, 1)
			.define('C', NonsenseItems.COPPER_COIL)
			.define('I', Items.IRON_INGOT)
			.define('R', Items.REPEATER)
			.pattern("CIC")
			.pattern("CRC")
			.pattern("CIC")
			.unlockedBy(getHasName(NonsenseItems.COPPER_COIL), has(NonsenseItems.COPPER_COIL))
			.save(output);
	}
}
