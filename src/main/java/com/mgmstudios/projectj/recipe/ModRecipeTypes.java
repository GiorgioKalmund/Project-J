package com.mgmstudios.projectj.recipe;

import com.mgmstudios.projectj.ProjectJ;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, ProjectJ.MOD_ID);

    public static final Supplier<RecipeType<AdobeFurnaceRecipe>> ADOBE_FURNACE_TYPE =
            RECIPE_TYPES.register("adobe_furnace_type",
                    resourceLocation -> new RecipeType<AdobeFurnaceRecipe>() {
                        @Override
                        public String toString() {
                            return resourceLocation.toString();
                        }
                    }
                    );

    public static void register(IEventBus eventBus){
        RECIPE_TYPES.register(eventBus);
    }
}
