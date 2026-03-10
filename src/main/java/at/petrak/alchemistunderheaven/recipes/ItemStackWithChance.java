package at.petrak.alchemistunderheaven.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.Optional;

// Based on: https://github.com/BluSunrize/ImmersiveEngineering/blob/1.21.1/src/api/java/blusunrize/immersiveengineering/api/crafting/StackWithChance.java
public record ItemStackWithChance(ItemStack stack, float chance) {
  public static final ItemStackWithChance EMPTY = new ItemStackWithChance(ItemStack.EMPTY, 0);

  public static ItemStackWithChance one(ItemLike item) {
    return new ItemStackWithChance(new ItemStack(item), 1f);
  }

  public static final Codec<ItemStackWithChance> CODEC = RecordCodecBuilder.create(i -> i.group(
      ItemStack.CODEC.fieldOf("stack").forGetter(ItemStackWithChance::stack),
      Codec.FLOAT.optionalFieldOf("chance", 1f).forGetter(ItemStackWithChance::chance)
  ).apply(i, ItemStackWithChance::new));
  // Nicer codec that allows just an itemstack, for the sake of writing manual datapacks
  public static final Codec<ItemStackWithChance> NICE_CODEC = Codec.withAlternative(CODEC, ItemStack.CODEC.xmap(
      is -> new ItemStackWithChance(is, 1f),
      iswc -> iswc.stack
  ));

  public static final StreamCodec<RegistryFriendlyByteBuf, ItemStackWithChance> STREAM_CODEC = StreamCodec.composite(
      ItemStack.STREAM_CODEC, ItemStackWithChance::stack,
      ByteBufCodecs.FLOAT, ItemStackWithChance::chance,
      ItemStackWithChance::new
  );
  public static final StreamCodec<RegistryFriendlyByteBuf, Optional<ItemStackWithChance>> OPTIONAL_STREAM_CODEC = STREAM_CODEC.map(
      iswc -> Optional.of(iswc),
      optional -> optional.orElse(ItemStackWithChance.EMPTY)
  );
}
