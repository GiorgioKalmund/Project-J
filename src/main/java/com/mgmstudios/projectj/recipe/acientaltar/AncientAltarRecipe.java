package com.mgmstudios.projectj.recipe.acientaltar;

import com.mgmstudios.projectj.recipe.ModRecipeBookCategories;
import com.mgmstudios.projectj.recipe.ModRecipeSerializers;
import com.mgmstudios.projectj.recipe.ModRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.List;

public class AncientAltarRecipe implements Recipe<AncientAltarInput> {

    private final FluidStack fluidStack;
    private final List<Ingredient> inputItems;
    private final ItemStack result;


    public AncientAltarRecipe(FluidStack fluidStack, List<Ingredient> items, ItemStack result){
        this.fluidStack = fluidStack;
        this.inputItems = items;
        this.result = result;
    }

    public AncientAltarRecipe(List<Ingredient> items, ItemStack result){
        this.fluidStack = new FluidStack(Fluids.WATER, 1); // We use water as FluidStack.EMPTY cannot be used in recipes
        this.inputItems = items;
        this.result = result;
    }

    @Override
    public boolean matches(AncientAltarInput input, Level level) {
        if (fluidStack.getFluid() != Fluids.WATER && !FluidStack.isSameFluidSameComponents(input.fluidStack(), fluidStack)) {
            //System.out.println(input.fluidStack().getFluid() + " vs " + fluidStack.getFluid());
            return false;
        }

        if (input.size() > this.inputItems.size()) {
            //System.out.println(input.size() + " vs " + this.inputItems.size());
            return false;
        }

        var nonEmptyItems = new java.util.ArrayList<ItemStack>(input.size());
        for (var item : input.items())
            if (!item.isEmpty())
                nonEmptyItems.add(item);

        boolean valid = net.neoforged.neoforge.common.util.RecipeMatcher.findMatches(nonEmptyItems, this.inputItems) != null;
        if (!valid)
            return false;

        System.out.println("VALID");
        return true;
    }


    @Override
    public ItemStack assemble(AncientAltarInput input, HolderLookup.Provider registries) {
        return this.result.copy();
    }

    @Override
    public RecipeSerializer<? extends Recipe<AncientAltarInput>> getSerializer() {
        return ModRecipeSerializers.ANCIENT_ALTAR_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<AncientAltarInput>> getType() {
        return ModRecipeTypes.ANCIENT_ALTAR_RECIPE_TYPE.get();
    }

    public FluidStack getFluidStack() {
        return fluidStack;
    }

    public List<Ingredient> getInputItems() {
        return inputItems;
    }

    public ItemStack getResult() {
        return result;
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }


    @Override
    public RecipeBookCategory recipeBookCategory() {
        return ModRecipeBookCategories.ANCIENT_ALTAR_RECIPE_CATEGORY.get();
    }
}
