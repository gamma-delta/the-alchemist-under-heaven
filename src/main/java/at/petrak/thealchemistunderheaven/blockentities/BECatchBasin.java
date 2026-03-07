package at.petrak.thealchemistunderheaven.blockentities;

import at.petrak.thealchemistunderheaven.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class BECatchBasin extends BaseContainerBlockEntity {
  private NonNullList<ItemStack> items;

  public BECatchBasin(BlockPos blockPos, BlockState blockState) {
    super(ModBlockEntities.CATCH_BASIN, blockPos, blockState);
    this.items = NonNullList.withSize(1, ItemStack.EMPTY);
  }

  public static void onFurnaceSmeltItem() {

  }

  //#region container implementation

  @Override
  protected Component getDefaultName() {
    return Component.literal("Catch Basin");
  }

  @Override
  protected NonNullList<ItemStack> getItems() {
    return this.items;
  }

  @Override
  protected void setItems(NonNullList<ItemStack> items) {
    this.items = items;
  }

  @Override
  public int getContainerSize() {
    return 1;
  }

  @Override
  protected void loadAdditional(ValueInput input) {
    super.loadAdditional(input);
    ContainerHelper.loadAllItems(input, this.items);
  }

  @Override
  protected void saveAdditional(ValueOutput output) {
    ContainerHelper.saveAllItems(output, this.items);
    super.saveAdditional(output);
  }

  //#endregion


  @Override
  protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
    return null;
  }
}
