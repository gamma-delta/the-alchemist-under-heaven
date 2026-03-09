package at.petrak.alchemistunderheaven;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

import java.util.function.Function;

public final class ModItems {
  public static final Item QUICKSILVER_DROP = simple("quicksilver_drop", new Item.Properties());
  public static final Item QUICKSILVER = simple("quicksilver", new Item.Properties());
  public static final Item SILVER_NUGGET = simple("silver_nugget", new Item.Properties());
  public static final Item SILVER_INGOT = simple("silver_ingot", new Item.Properties());
  public static final Item GRISTLE = simple("gristle", new Item.Properties().food(new FoodProperties(1, 0.5f, false)));
  public static final Item ASHES = simple("ashes", new Item.Properties());

  public static final Item SULPHUR = simple("sulphur", new Item.Properties());
  public static final Item NATRON = simple("natron", new Item.Properties());
  public static final Item CHTHONIC_WATER = simple("chthonic_water", new Item.Properties());

  public static final Item VERDURE = simple("verdure", new Item.Properties());
  // what did you think was going to happen
  public static final Item PAIN_OF_DEATH = simple("pain_of_death",
      new Item.Properties().component(DataComponents.CONSUMABLE, Consumable.builder()
          .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.INSTANT_DAMAGE, 4)))
          .build())
  );
  public static final Item CAVE_AIR = simple("cave_air", new Item.Properties());
  public static final Item REDOLENT_LUSTRE = simple("redolent_lustre", new Item.Properties());
  public static final Item FISHSCALE_GLINT = simple("fishscale_glint", new Item.Properties());

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
