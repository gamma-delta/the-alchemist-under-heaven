package at.petrak.thealchemistunderheaven;

import at.petrak.thealchemistunderheaven.blockentities.BECatchBasin;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {
  public static final BlockEntityType<BECatchBasin> CATCH_BASIN = register("catch_basin", BECatchBasin::new, ModBlocks.CATCH_BASIN);

  // WHY does fabric not have a thing for this
  private static <T extends BlockEntity> BlockEntityType<T> register(String name, FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory, Block... blocks) {
    return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, TheAlchemistUnderHeaven.modLoc(name), FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
  }

  public static void init() {
    // static init
  }
}
