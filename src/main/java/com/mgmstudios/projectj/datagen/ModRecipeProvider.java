package com.mgmstudios.projectj.datagen;

import com.mgmstudios.projectj.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {

    protected ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.RAW_ADOBE, 4)
                .pattern("SB ")
                .pattern("CW ")
                .define('B', Items.WATER_BUCKET)
                .define('C', Items.CLAY_BALL)
                .define('W', Items.WHEAT)
                .define('S', Blocks.SAND)
                .unlockedBy("has_iron_ingot", this.has(Items.IRON_INGOT))
                .unlockedBy("has_clay", this.has(Items.CLAY_BALL))
                .unlockedBy("has_sand", this.has(Blocks.SAND))
                .save(this.output);

        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(ModBlocks.RAW_ADOBE.get()),
                        RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.ADOBE_BRICKS.get(),
                        0.1f,
                        200
                )
                .unlockedBy("has_raw_adobe", this.has(ModBlocks.RAW_ADOBE.get()))
                .save(this.output, "adobe_bricks_smelting");
    }

    // The runner to add to the data generator
    public static class Runner extends RecipeProvider.Runner {
        // Get the parameters from the `GatherDataEvent`s.
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            return new ModRecipeProvider(registries, output);
        }

        @Override
        public String getName() {
            return "ModRecipeProvider";
        }
    }
}
