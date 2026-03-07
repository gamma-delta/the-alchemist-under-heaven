package at.petrak.alchemistunderheaven.recipes;

import at.petrak.alchemistunderheaven.ModRecipeStuff;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

// the forge docs are better than the fabric docs here
// https://docs.neoforged.net/docs/resources/server/recipes/custom/
public record RecipeFurnaceByproduct(Ingredient ingredient, @Nullable ItemStack catchBasin,
                                     @Nullable ItemStack fumeCatcher) implements Recipe<SingleRecipeInput> {
  @Override
  public boolean matches(SingleRecipeInput recipeInput, Level level) {
    return this.ingredient.test(recipeInput.item());
  }

  // Advanced technique called LYING
  // Pretend there's only one output. Lol. Lmao even.
  @Override
  public ItemStack assemble(SingleRecipeInput recipeInput, HolderLookup.Provider provider) {
    if (this.catchBasin == null) {
      return ItemStack.EMPTY;
    } else {
      return this.catchBasin.copy();
    }
  }

  @Override
  public RecipeSerializer<? extends Recipe<SingleRecipeInput>> getSerializer() {
    return ModRecipeStuff.FURNACE_BYPRODUCT_SERIALIZER;
  }

  @Override
  public RecipeType<? extends Recipe<SingleRecipeInput>> getType() {
    return ModRecipeStuff.FURNACE_BYPRODUCT;
  }

  @Override
  public PlacementInfo placementInfo() {
    return PlacementInfo.create(this.ingredient);
  }

  @Override
  public RecipeBookCategory recipeBookCategory() {
    // no one uses the recipe book
    return RecipeBookCategories.FURNACE_MISC;
  }

  public static final class Serializer implements RecipeSerializer<RecipeFurnaceByproduct> {
    public static final MapCodec<RecipeFurnaceByproduct> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
        Ingredient.CODEC.fieldOf("ingredient").forGetter(RecipeFurnaceByproduct::ingredient),
        ItemStack.CODEC.lenientOptionalFieldOf("catchBasin", ItemStack.EMPTY).forGetter(RecipeFurnaceByproduct::catchBasin),
        ItemStack.CODEC.lenientOptionalFieldOf("fumeCatcher", ItemStack.EMPTY).forGetter(RecipeFurnaceByproduct::fumeCatcher)
    ).apply(i, RecipeFurnaceByproduct::new));
    // this codec doesn't need to be written as extensible, as the bytebufs only exist while the game is running
    // so, if the schema changes, there will never be a mismatch (the server and client will agree on the new schema)
    // not so for the mapcodec! because that is used for reading the datagen
    public static final StreamCodec<RegistryFriendlyByteBuf, RecipeFurnaceByproduct> STREAM_CODEC =
        StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC, RecipeFurnaceByproduct::ingredient,
            ItemStack.OPTIONAL_STREAM_CODEC, RecipeFurnaceByproduct::catchBasin,
            ItemStack.OPTIONAL_STREAM_CODEC, RecipeFurnaceByproduct::fumeCatcher,
            RecipeFurnaceByproduct::new
        );

    @Override
    public MapCodec<RecipeFurnaceByproduct> codec() {
      return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, RecipeFurnaceByproduct> streamCodec() {
      return STREAM_CODEC;
    }
  }
}
