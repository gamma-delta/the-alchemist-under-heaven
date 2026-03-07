package at.petrak.alchemistunderheaven.blockentities;

import at.petrak.alchemistunderheaven.ModBlockEntities;
import at.petrak.alchemistunderheaven.ModRecipeStuff;
import at.petrak.alchemistunderheaven.TheAlchemistUnderHeaven;
import at.petrak.alchemistunderheaven.recipes.RecipeFurnaceByproduct;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;

// All the stuff you put over and below the furnace are the same blockentity;
// their behavior is switched based on the block
public class BEFurnaceByproductCatcher extends BaseContainerBlockEntity {
  private NonNullList<ItemStack> items;
  private final RecipeManager.CachedCheck<SingleRecipeInput, RecipeFurnaceByproduct> quickCheck;

  public BEFurnaceByproductCatcher(BlockPos blockPos, BlockState blockState) {
    super(ModBlockEntities.FURNACE_BYPRODUCT_CATCHER, blockPos, blockState);
    this.items = NonNullList.withSize(1, ItemStack.EMPTY);
    // I believe this caches the most recently used recipe to avoid one million lookups
    this.quickCheck = RecipeManager.createCheck(ModRecipeStuff.FURNACE_BYPRODUCT);
  }

  public static void onFurnaceSmeltItem(ItemStack ingredient, ServerLevel level, BlockPos furnacePos,
      BlockState furnaceState, AbstractFurnaceBlockEntity furnace) {
  }

  private static boolean checkFurnaceByproductSlot(ServerLevel level, BlockPos furnacePos, Block catchTy,
      int blocksAbove, @Nullable ItemStack output) {
    var hopefulPos = furnacePos.offset(0, blocksAbove, 0);
    var bs = level.getBlockState(furnacePos);
    if (!bs.is(catchTy)) return false;

    var mbCatcher = level.getBlockEntity(hopefulPos, ModBlockEntities.FURNACE_BYPRODUCT_CATCHER);
    if (mbCatcher.isEmpty()) {
      TheAlchemistUnderHeaven.LOGGER.warn("at {} found {}, which should have a BEFurnaceByProductCatcher but didn't",
          hopefulPos, bs);
      return false;
    }
    var catcher = mbCatcher.get();

    if (output == null) {
      // Allow further catchers in the stack to work but don't do anything
      return true;
    }

    // the catch basin is "inserted" to from "above"
    // all others are "below"
    // there's little reason to do this
    HopperBlockEntity.addItem(null, catcher, output, blocksAbove > 0 ? Direction.DOWN : Direction.UP);
    return true;
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
