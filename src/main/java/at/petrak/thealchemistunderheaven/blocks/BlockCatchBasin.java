package at.petrak.thealchemistunderheaven.blocks;

import at.petrak.thealchemistunderheaven.blockentities.BECatchBasin;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class BlockCatchBasin extends BaseEntityBlock {
  public BlockCatchBasin(Properties properties) {
    super(properties);
  }

  @Override
  protected MapCodec<? extends BaseEntityBlock> codec() {
    return simpleCodec(BlockCatchBasin::new);
  }

  @Override
  public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
    return new BECatchBasin(blockPos, blockState);
  }
}
