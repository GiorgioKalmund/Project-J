package com.mgmstudios.projectj.recipe.acientaltar;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.List;

public record AncientAltarInput(FluidStack fluidStack, List<ItemStack> items) implements RecipeInput {

    @Override
    public ItemStack getItem(int index) {
        return items.get(index);
    }

    @Override
    public int size() {
        return items.size();
    }

    public List<ItemStack> getItems() {
        return items;
    }
}
