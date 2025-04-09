package com.mgmstudios.projectj.recipe.metate;

import com.mgmstudios.projectj.recipe.ModRecipeBookCategories;
import com.mgmstudios.projectj.recipe.ModRecipeSerializers;
import com.mgmstudios.projectj.recipe.ModRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class MetateRecipe implements Recipe<MetateInput> {

    private final Ingredient inputItem;
    private final ItemStack result;
    final boolean showNotification;


    public MetateRecipe(Ingredient items, ItemStack result, boolean showNotification){
        this.inputItem = items;
        this.result = result;
        this.showNotification = showNotification;
    }

    public MetateRecipe(Ingredient items, ItemStack result){
        this(items, result, true);
    }

    @Override
    public boolean matches(MetateInput input, Level level) {
        return this.inputItem.test(input.item());
    }


    @Override
    public ItemStack assemble(MetateInput input, HolderLookup.Provider registries) {
        return this.result.copy();
    }

    @Override
    public RecipeSerializer<? extends Recipe<MetateInput>> getSerializer() {
        return ModRecipeSerializers.METATE_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<MetateInput>> getType() {
        return ModRecipeTypes.METATE_RECIPE_TYPE.get();
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
        return ModRecipeBookCategories.METATE_RECIPE_CATEGORY.get();
    }

    @Override
    public boolean showNotification() {
        return true;
    }

    public Ingredient getInputItem() {
        return inputItem;
    }
}
