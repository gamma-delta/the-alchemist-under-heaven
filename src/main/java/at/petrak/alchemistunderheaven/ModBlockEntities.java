package at.petrak.alchemistunderheaven;

import at.petrak.alchemistunderheaven.blockentities.BEFurnaceByproductCatcher;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {
  public static final BlockEntityType<BEFurnaceByproductCatcher> FURNACE_BYPRODUCT_CATCHER = register(
      "furnace_byproduct_catcher",
      BEFurnaceByproductCatcher::new,
      ModBlocks.CATCH_BASIN,
      ModBlocks.FUME_CONDENSOR
  );

  // WHY does fabric not have a thing for this
  private static <T extends BlockEntity> BlockEntityType<T> register(String name,
      FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory, Block... blocks) {
    return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, TheAlchemistUnderHeaven.modLoc(name),
        FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
  }

  public static void init() {
    // static init
  }
}
