package at.petrak.alchemistunderheaven;

import at.petrak.alchemistunderheaven.recipes.RecipeFurnaceByproduct;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public final class ModRecipeStuff {
  public static final RecipeType<RecipeFurnaceByproduct> FURNACE_BYPRODUCT = recipeType("furnace_byproduct");

  public static final RecipeSerializer<RecipeFurnaceByproduct> FURNACE_BYPRODUCT_SERIALIZER = serializer(
      "furnace_byproduct", new RecipeFurnaceByproduct.Serializer());

  private static <I extends RecipeInput, R extends Recipe<I>> RecipeType<R> recipeType(String name) {
    var key = ResourceKey.create(Registries.RECIPE_TYPE, TheAlchemistUnderHeaven.modLoc(name));
    return Registry.register(BuiltInRegistries.RECIPE_TYPE, key, new RecipeType<R>() {
      @Override
      public String toString() {
        return key.toString();
      }
    });
  }

  private static <R extends Recipe<?>> RecipeSerializer<R> serializer(String name, RecipeSerializer<R> serializer) {
    var key = ResourceKey.create(Registries.RECIPE_SERIALIZER, TheAlchemistUnderHeaven.modLoc(name));
    return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, key, serializer);
  }

  public static void init() {
    // static init
  }
}
