package at.petrak.alchemistunderheaven.blockentities;

import at.petrak.alchemistunderheaven.ModBlockEntities;
import at.petrak.alchemistunderheaven.ModBlocks;
import at.petrak.alchemistunderheaven.ModRecipeStuff;
import at.petrak.alchemistunderheaven.TheAlchemistUnderHeaven;
import at.petrak.alchemistunderheaven.recipes.ItemStackWithChance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.Optional;

// Sand -> basin salt?
// Kelp -> basin salt? ash? same ash as cooking logs or caustic ash?
// Redstone ore -> basin quicksilver (whole ingot)
// Coal ore -> sulfur
// "Astromyxin"
// Need a way to get niter (potassium nitrate, also called natron archaichally)
// traditionally made by fermenting manure and urine! gross! i'm not adding shit to my mod!
// thermal expansion gets sulfur from grinding nether materials + lapis,
// and niter from grinding sandstone.
// railcraft had sulfur and niter ore but i'd like to skip ore worldgen if i can ...
// maybe nitre is common from end blocks, although you don't smelt anything from there but purpur

// All the stuff you put over and below the furnace are the same blockentity;
// their behavior is switched based on the block
public class BEFurnaceByproductCatcher extends BaseContainerBlockEntity {
  private NonNullList<ItemStack> results;

  public BEFurnaceByproductCatcher(BlockPos blockPos, BlockState blockState) {
    super(ModBlockEntities.FURNACE_BYPRODUCT_CATCHER, blockPos, blockState);
    this.results = NonNullList.withSize(1, ItemStack.EMPTY);
  }

  public static void onFurnaceSmeltItem(ItemStack ingredient, ServerLevel level, BlockPos furnacePos, BlockState furnaceState, AbstractFurnaceBlockEntity furnace) {
    // We cannot easily cache this with a quickcheck -- where would we put it?
    var recipeMan = level.recipeAccess();
    var mbRecipe = recipeMan.getRecipeFor(ModRecipeStuff.FURNACE_BYPRODUCT, new SingleRecipeInput(ingredient), level);
    if (mbRecipe.isEmpty()) return;
    var recipe = mbRecipe.get().value();

    checkFurnaceByproductSlot(level, furnacePos, ModBlocks.CATCH_BASIN, -1, recipe.catchBasin());
    // Using `&&` as control flow here to avoid a huge nested IF
    // depending on how many layers I add
    var _whocares = checkFurnaceByproductSlot(level, furnacePos, ModBlocks.FUME_CONDENSER, 1, recipe.fumeCondenser())
        && checkFurnaceByproductSlot(level, furnacePos, ModBlocks.ESSENCE_FILTER, 2, recipe.essenceFilter());
  }

  private static boolean checkFurnaceByproductSlot(ServerLevel level, BlockPos furnacePos, Block catchTy, int blocksAbove, Optional<ItemStackWithChance> mbOutput) {
    var hopefulPos = furnacePos.offset(0, blocksAbove, 0);
    var bs = level.getBlockState(hopefulPos);
    if (!bs.is(catchTy)) return false;

    var mbCatcher = level.getBlockEntity(hopefulPos, ModBlockEntities.FURNACE_BYPRODUCT_CATCHER);
    if (mbCatcher.isEmpty()) {
      TheAlchemistUnderHeaven.LOGGER.warn("at {} found {}, which should have a BEFurnaceByProductCatcher but didn't",
          hopefulPos, bs
      );
      return false;
    }
    var catcher = mbCatcher.get();

    if (mbOutput.isEmpty()) {
      // Allow further catchers in the stack to work but don't do anything
      return true;
    }
    var output = mbOutput.get();

    if (level.getRandom().nextFloat() >= output.chance()) {
      // Recipe chance failed but further fumes still work
      return true;
    }

    // the catch basin is "inserted" to from "above"
    // all others are "below"
    // there's little reason to do this
    HopperBlockEntity.addItem(null, catcher, output.stack().copy(), blocksAbove > 0 ? Direction.DOWN : Direction.UP);
    return true;
  }

  //#region container implementation

  @Override
  protected Component getDefaultName() {
    return Component.literal("Catch Basin");
  }

  @Override
  protected NonNullList<ItemStack> getItems() {
    return this.results;
  }

  @Override
  protected void setItems(NonNullList<ItemStack> items) {
    this.results = items;
  }

  @Override
  public int getContainerSize() {
    return 1;
  }

  @Override
  protected void loadAdditional(ValueInput input) {
    super.loadAdditional(input);
    ContainerHelper.loadAllItems(input, this.results);
  }

  @Override
  protected void saveAdditional(ValueOutput output) {
    ContainerHelper.saveAllItems(output, this.results);
    super.saveAdditional(output);
  }

  //#endregion


  @Override
  protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
    return null;
  }
}
