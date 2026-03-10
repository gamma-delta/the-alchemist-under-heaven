package at.petrak.alchemistunderheaven;

import at.petrak.alchemistunderheaven.blocks.BlockFurnaceByproductCatcher;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

public final class ModBlocks {
  public static BlockFurnaceByproductCatcher CATCH_BASIN = register("catch_basin", BlockFurnaceByproductCatcher::new,
      Blocks.CAULDRON.properties(), true
  );
  public static BlockFurnaceByproductCatcher FUME_CONDENSER = register("fume_condenser",
      BlockFurnaceByproductCatcher::new, Blocks.CAULDRON.properties(), true
  );
  public static BlockFurnaceByproductCatcher ESSENCE_FILTER = register("essence_filter",
      BlockFurnaceByproductCatcher::new, Blocks.CAULDRON.properties(), true
  );

  private static <T extends Block> T register(String name, Function<BlockBehaviour.Properties, T> factory,
                                              BlockBehaviour.Properties props, boolean registerItem
  ) {
    var blockKey = ResourceKey.create(Registries.BLOCK, TheAlchemistUnderHeaven.modLoc(name));
    T block = factory.apply(props.setId(blockKey));
    if (registerItem) {
      var itemKey = ResourceKey.create(Registries.ITEM, TheAlchemistUnderHeaven.modLoc(name));
      var blockItem = new BlockItem(block, new Item.Properties().setId(itemKey).useBlockDescriptionPrefix());
      Registry.register(BuiltInRegistries.ITEM, itemKey, blockItem);
    }
    return Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
  }

  private static Block simple(String name, BlockBehaviour.Properties props) {
    return register(name, Block::new, props, true);
  }

  public static void init() {
    // static init
  }
}
