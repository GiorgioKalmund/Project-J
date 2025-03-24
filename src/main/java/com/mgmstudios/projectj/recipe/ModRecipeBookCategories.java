package com.mgmstudios.projectj.recipe;

import com.mgmstudios.projectj.ProjectJ;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModRecipeBookCategories {
    public static final DeferredRegister<RecipeBookCategory> RECIPE_BOOK_CATEGORIES =
            DeferredRegister.create(Registries.RECIPE_BOOK_CATEGORY, ProjectJ.MOD_ID);

    public static final Supplier<RecipeBookCategory> ADOBE_FURNACE_RECIPE_CATEGORY =
            RECIPE_BOOK_CATEGORIES.register("adobe_furnace_recipe_category", RecipeBookCategory::new);

    public static void register(IEventBus eventBus){
        RECIPE_BOOK_CATEGORIES.register(eventBus);
    }
}
