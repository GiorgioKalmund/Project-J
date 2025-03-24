package com.mgmstudios.projectj.recipe;

import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.item.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

public class AdobeFurnaceRecipe extends AbstractCookingRecipe {
    public AdobeFurnaceRecipe(String group, CookingBookCategory category, Ingredient input, ItemStack result, float experience, int cookingTime) {
        super(group, category, input, result, experience, cookingTime);
    }

    @Override
    public RecipeSerializer<? extends AbstractCookingRecipe> getSerializer() {
        return RecipeSerializer.SMELTING_RECIPE;
    }

    @Override
    public RecipeType<? extends AbstractCookingRecipe> getType() {
        return ModRecipeTypes.ADOBE_FURNACE_TYPE.get();
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return ModRecipeBookCategories.ADOBE_FURNACE_RECIPE_CATEGORY.get();
    }

    @Override
    protected Item furnaceIcon() {
        return ModBlocks.ADOBE_FURNACE.asItem();
    }
}
