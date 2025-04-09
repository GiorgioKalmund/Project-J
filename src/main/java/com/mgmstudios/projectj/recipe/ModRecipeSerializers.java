package com.mgmstudios.projectj.recipe;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.recipe.acientaltar.AncientAltarRecipe;
import com.mgmstudios.projectj.recipe.acientaltar.AncientAltarRecipeSerializer;
import com.mgmstudios.projectj.recipe.metate.MetateRecipe;
import com.mgmstudios.projectj.recipe.metate.MetateRecipeSerializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, ProjectJ.MOD_ID);

    public static final Supplier<RecipeSerializer<AncientAltarRecipe>> ANCIENT_ALTAR_SERIALIZER =
            RECIPE_SERIALIZERS.register("ancient_altar", AncientAltarRecipeSerializer::new);

    public static final Supplier<RecipeSerializer<MetateRecipe>> METATE_SERIALIZER =
            RECIPE_SERIALIZERS.register("metate", MetateRecipeSerializer::new);

    public static void register(IEventBus bus){
        RECIPE_SERIALIZERS.register(bus);
    }
}
