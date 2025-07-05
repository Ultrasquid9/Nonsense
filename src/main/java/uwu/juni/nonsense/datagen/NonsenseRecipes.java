package uwu.juni.nonsense.datagen;

import java.util.concurrent.CompletableFuture;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
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
	}
}
