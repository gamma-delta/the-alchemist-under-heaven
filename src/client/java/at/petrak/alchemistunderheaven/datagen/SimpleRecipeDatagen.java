package at.petrak.alchemistunderheaven.datagen;

import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import org.jspecify.annotations.Nullable;

public record SimpleRecipeDatagen<T extends Recipe<?>>(T recipe) implements RecipeBuilder {

  @Override
  public RecipeBuilder unlockedBy(String string, Criterion<?> criterion) {
    // who cares
    return this;
  }

  @Override
  public RecipeBuilder group(@Nullable String string) {
    return this;
  }

  @Override
  public Item getResult() {
    // shhhh
    return Items.AIR;
  }

  @Override
  public void save(RecipeOutput output, ResourceKey<Recipe<?>> key) {
    output.accept(key, this.recipe, null);
  }
}
