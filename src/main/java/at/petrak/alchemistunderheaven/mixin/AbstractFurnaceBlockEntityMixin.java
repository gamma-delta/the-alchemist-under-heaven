package at.petrak.alchemistunderheaven.mixin;

import at.petrak.alchemistunderheaven.ModBlockEntities;
import at.petrak.alchemistunderheaven.blockentities.BEFurnaceByproductCatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(AbstractFurnaceBlockEntity.class)
public class AbstractFurnaceBlockEntityMixin {
  @Inject(method = "serverTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/AbstractFurnaceBlockEntity;setRecipeUsed(Lnet/minecraft/world/item/crafting/RecipeHolder;)V"))
  private static void onCookItem(ServerLevel serverLevel, BlockPos blockPos, BlockState blockState, AbstractFurnaceBlockEntity furnace, CallbackInfo ci) {
    // At this point the ingredient itemstack has already been shrunk.
    // We need a "real" itemstack to check if it's the right ingredient,
    // and most of the ItemStack methods politely return ItemStack.NONE if the stack is empty,
    // so I need to jankily clone it myself
    var cookedItem = furnace.getItem(0);
    var checkItem = cookedItem.copy();
    var itemEmpty = cookedItem.isEmpty();
    if (itemEmpty) {
      // this is the only way I can figure out how to "revive" the itemstack
      cookedItem.grow(1);
      checkItem = cookedItem.copy();
      cookedItem.shrink(1);
    }

    BEFurnaceByproductCatcher.onFurnaceSmeltItem(checkItem, serverLevel, blockPos, blockState, furnace);
  }
}