package com.mgmstudios.projectj.block.entity.custom;

import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.block.entity.ModBlockEntities;
import com.mgmstudios.projectj.screen.custom.AdobeFurnaceMenu;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.FuelValues;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.attachment.AttachmentType;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class AdobeFurnaceBlockEntity extends AbstractFurnaceBlockEntity {

    public AdobeFurnaceBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.ADOBE_FURNACE_BE.get(), pos, blockState, RecipeType.SMELTING);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.projectj.adobe_furnace");
    }

    @Override
    protected int getBurnDuration(FuelValues fuelValues, ItemStack stack) {

        int baseBurnTime = super.getBurnDuration(fuelValues, stack);
        if (level != null){
            BlockPos abovePos = getBlockPos().above();
            BlockState aboveBlock = level.getBlockState(abovePos);

            if (aboveBlock.is(ModBlocks.CHIMNEY.get())){
                return baseBurnTime * 3;
            }
        }
        return (int)(baseBurnTime * 1.5);
    }

    @Override
    public <T> @Nullable T setData(Supplier<AttachmentType<T>> type, T data) {
        return super.setData(type, data);
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new AdobeFurnaceMenu(containerId, inventory, this, this.dataAccess);
    }
}
