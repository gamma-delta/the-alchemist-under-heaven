package at.petrak.alchemistunderheaven.recipes;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

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
  public static final Codec<ItemStackWithChance> NICE_CODEC = Codec.either(ItemStack.CODEC, CODEC).xmap(
      either -> either.map(stack -> new ItemStackWithChance(stack, 1f), iswc -> iswc),
      iswc -> Either.left(iswc.stack)
  );

  public static final StreamCodec<RegistryFriendlyByteBuf, ItemStackWithChance> STREAM_CODEC = StreamCodec.composite(
      ItemStack.STREAM_CODEC, ItemStackWithChance::stack,
      ByteBufCodecs.FLOAT, ItemStackWithChance::chance,
      ItemStackWithChance::new
  );
}
