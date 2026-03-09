package at.petrak.alchemistunderheaven.datagen;

import at.petrak.alchemistunderheaven.ModItems;
import at.petrak.alchemistunderheaven.recipes.ItemStackWithChance;
import at.petrak.alchemistunderheaven.recipes.RecipeFurnaceByproduct;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import org.apache.commons.lang3.ArrayUtils;
import org.jspecify.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public final class DatagenRecipes extends FabricRecipeProvider {
  public DatagenRecipes(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture
  ) {
    super(output, registriesFuture);
  }

  // WHY does it need to be done like this. this is awful
  @Override
  protected RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
    return new RecipeProvider(registryLookup, exporter) {
      private HolderLookup.RegistryLookup<Item> itemLookup = this.registries.lookupOrThrow(Registries.ITEM);

      @Override
      public void buildRecipes() {
        // i believe that this covers all recipes except for the ones that turn gear into nuggets, and wet sponge

        // ORES
        furnaceByproduct(Items.RAW_GOLD, iswc(ModItems.QUICKSILVER_DROP), null, iswc(ModItems.REDOLENT_LUSTRE, 0.01f));
        furnaceByproduct(Items.RAW_COPPER, iswc(ModItems.SILVER_NUGGET), null, iswc(ModItems.REDOLENT_LUSTRE, 0.01f));
        // TODO: should this make something else
        furnaceByproduct(Items.RAW_IRON, iswc(Items.IRON_NUGGET, 5), null, iswc(ModItems.REDOLENT_LUSTRE, 0.01f));
        furnaceByproduct(tag(ConventionalItemTags.RAW_MEAT_FOODS), iswc(ModItems.GRISTLE));
        furnaceByproduct(tag(ConventionalItemTags.RAW_FISH_FOODS),
            iswc(ModItems.GRISTLE, 0.5f), null, iswc(ModItems.FISHSCALE_GLINT, 0.1f)
        );
        // minecraft:gold_ores contains nether gold ore, which I want to produce sulphur too
        furnaceByproduct(Ingredient.of(Blocks.GOLD_ORE, Blocks.DEEPSLATE_GOLD_ORE),
            iswc(ModItems.QUICKSILVER), null, iswc(ModItems.REDOLENT_LUSTRE, 0.1f)
        );
        furnaceByproduct(tag(ItemTags.IRON_ORES),
            iswc(Items.IRON_INGOT), null, iswc(ModItems.REDOLENT_LUSTRE, 0.1f)
        );
        furnaceByproduct(tag(ItemTags.REDSTONE_ORES),
            iswc(ModItems.QUICKSILVER), null
        );
        furnaceByproduct(tag(ItemTags.COPPER_ORES),
            iswc(ModItems.SILVER_INGOT), null, iswc(ModItems.REDOLENT_LUSTRE, 0.1f)
        );
        furnaceByproduct(tag(ItemTags.COAL_ORES), null, iswc(ModItems.SULPHUR));
        furnaceByproduct(tag(ItemTags.LAPIS_ORES), null, iswc(ModItems.SULPHUR), iswc(ModItems.REDOLENT_LUSTRE));
        furnaceByproduct(Blocks.NETHER_GOLD_ORE,
            iswc(ModItems.QUICKSILVER), iswc(ModItems.SULPHUR, 0.5f), iswc(ModItems.PAIN_OF_DEATH, 0.1f)
        );
        furnaceByproduct(Blocks.NETHER_QUARTZ_ORE,
            null, iswc(ModItems.SULPHUR), iswc(ModItems.PAIN_OF_DEATH, 0.1f)
        );
        furnaceByproduct(Blocks.ANCIENT_DEBRIS,
            iswc(Items.NETHERITE_SCRAP, 0.5f), iswc(ModItems.SULPHUR), iswc(ModItems.PAIN_OF_DEATH)
        );
        furnaceByproduct(tag(ItemTags.DIAMOND_ORES), null, null, iswc(ModItems.REDOLENT_LUSTRE));
        furnaceByproduct(tag(ItemTags.EMERALD_ORES), null, null, iswc(ModItems.REDOLENT_LUSTRE));

        // PLANTS
        // primary natron sources
        furnaceByproduct(Items.KELP,
            iswc(ModItems.ASHES), iswc(ModItems.NATRON, 0.2f), iswc(ModItems.FISHSCALE_GLINT, 0.1f)
        );
        furnaceByproduct(Items.SEA_PICKLE,
            iswc(ModItems.ASHES), iswc(ModItems.NATRON, 0.5f), iswc(ModItems.FISHSCALE_GLINT, 0.2f)
        );
        furnaceByproduct(Blocks.LEAF_LITTER, iswc(ModItems.ASHES, 0.1f), null, iswc(ModItems.VERDURE, 0.01f));
        // there is no clean way to do "all logs, BUT spruce" sadly
        furnaceByproduct(tag(ItemTags.LOGS_THAT_BURN),
            iswc(ModItems.ASHES), iswc(Items.RESIN_CLUMP, 0.1f), iswc(ModItems.VERDURE, 0.01f)
        );
        furnaceByproduct(Items.RESIN_CLUMP, iswc(Items.RESIN_CLUMP, 0.1f), iswc(ModItems.PAIN_OF_DEATH, 0.1f));
        furnaceByproduct(Blocks.CACTUS, iswc(Items.SLIME_BALL, 0.1f), null, iswc(ModItems.VERDURE, 0.1f));
        furnaceByproduct(Items.POTATO, null, null, iswc(ModItems.VERDURE, 0.1f));
        // makes redolent lustre farmable
        furnaceByproduct(Items.CHORUS_FRUIT, null, null, iswc(ModItems.REDOLENT_LUSTRE, 0.01f));

        // STONE AND STONE BYPRODUCTS
        furnaceByproduct(Blocks.COBBLESTONE, null, null, iswc(ModItems.CAVE_AIR, 0.01f));
        furnaceByproduct(Blocks.COBBLED_DEEPSLATE, null, null, iswc(ModItems.CAVE_AIR, 0.02f));
        furnaceByproduct(Ingredient.of(Blocks.STONE_BRICKS, Blocks.DEEPSLATE_BRICKS, Blocks.DEEPSLATE_TILES),
            null, null, iswc(ModItems.CAVE_AIR, 0.02f)
        );
        furnaceByproduct(Blocks.STONE, null, null, iswc(ModItems.CAVE_AIR, 0.01f));
        furnaceByproduct(Items.CLAY, null, iswc(ModItems.CHTHONIC_WATER, 0.5f));
        furnaceByproduct(Blocks.CLAY, null, iswc(ModItems.CHTHONIC_WATER));
        furnaceByproduct(tag(ItemTags.TERRACOTTA), null, iswc(ModItems.CHTHONIC_WATER, 0.5f));
        // worse than kelp for natron, but easier to find
        furnaceByproduct(tag(ItemTags.SAND), null, iswc(ModItems.NATRON, 0.1f));
        furnaceByproduct(tag(ConventionalItemTags.SANDSTONE_BLOCKS),
            null, iswc(ModItems.NATRON, 0.2f), iswc(ModItems.CAVE_AIR, 0.01f)
        );
        furnaceByproduct(
            Ingredient.of(Blocks.NETHER_BRICKS, Blocks.BASALT, Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.QUARTZ_BLOCK),
            null, iswc(ModItems.SULPHUR, 0.5f), iswc(ModItems.PAIN_OF_DEATH, 0.01f)
        );
        furnaceByproduct(Items.NETHERRACK, null, iswc(ModItems.SULPHUR, 0.1f), iswc(ModItems.PAIN_OF_DEATH, 0.01f));
      }

      private ItemStackWithChance iswc(ItemLike item) {
        return ItemStackWithChance.one(item);
      }

      private ItemStackWithChance iswc(ItemLike item, int count) {
        return new ItemStackWithChance(new ItemStack(item, count), 1f);
      }

      private ItemStackWithChance iswc(ItemLike item, float chance) {
        return new ItemStackWithChance(new ItemStack(item, 1), chance);
      }

      private void furnaceByproduct(Ingredient in, @Nullable ItemStackWithChance... stacks) {
        var recipe = new RecipeFurnaceByproduct(in,
            ArrayUtils.get(stacks, 0, null),
            ArrayUtils.get(stacks, 1, null)
        );
        new SimpleRecipeDatagen<>(recipe).save(this.output);
      }

      private void furnaceByproduct(ItemLike in, @Nullable ItemStackWithChance... stacks) {
        this.furnaceByproduct(Ingredient.of(in), stacks);
      }
    };
  }

  @Override
  public String getName() {
    return "The Alchemist Under Heaven Recipe Provider";
  }

}
