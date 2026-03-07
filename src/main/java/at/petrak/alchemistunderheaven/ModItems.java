package at.petrak.alchemistunderheaven;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public final class ModItems {
  public static final Item QUICKSILVER_DROP = simple("quicksilver_drop", new Item.Properties());

  // fabric doesn't have a helper method for this?
  // also used in ModBlocks for registering block items
  public static <T extends Item> T register(String name, Function<Item.Properties, T> factory, Item.Properties props) {
    var key = ResourceKey.create(Registries.ITEM, TheAlchemistUnderHeaven.modLoc(name));
    T item = factory.apply(props);
    Registry.register(BuiltInRegistries.ITEM, key, item);
    return item;
  }

  public static Item simple(String name, Item.Properties props) {
    return register(name, Item::new, props);
  }

  public static void init() {
    // static initialize
  }
}
