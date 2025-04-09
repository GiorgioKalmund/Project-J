package com.mgmstudios.projectj.recipe.metate;

import com.mgmstudios.projectj.recipe.acientaltar.AncientAltarInput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.List;

public record MetateInput(ItemStack item) implements RecipeInput {

    @Override
    public ItemStack getItem(int index) {
        if (index != 0) throw new IllegalArgumentException("No item for index " + index);
        return item;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public ItemStack item() {
        return item;
    }
}
