package com.mgmstudios.projectj.datagen;

import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.item.ModItems;
import com.mgmstudios.projectj.util.ModTags;
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
        // CRAFTING

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.RAW_ADOBE, 4)
                .pattern("SB")
                .pattern("CW")
                .define('B', Items.WATER_BUCKET)
                .define('C', Items.CLAY_BALL)
                .define('W', Items.WHEAT)
                .define('S', Blocks.SAND)
                .unlockedBy("has_water_bucket", this.has(Items.WATER_BUCKET))
                .unlockedBy("has_wheat", this.has(Items.WHEAT))
                .unlockedBy("has_clay", this.has(Items.CLAY_BALL))
                .unlockedBy("has_sand", this.has(Blocks.SAND))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.ADOBE_FURNACE.get())
                .pattern("C#C")
                .pattern("#F#")
                .pattern("C#C")
                .define('#', ModBlocks.ADOBE_BRICKS.get())
                .define('F', Items.FURNACE)
                .define('C', Items.CLAY_BALL)
                .unlockedBy("has_furnace", this.has(Items.FURNACE))
                .unlockedBy("has_clay", this.has(Items.CLAY_BALL))
                .unlockedBy("has_sand", this.has(Blocks.SAND))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHIMNEY.get())
                .pattern("#")
                .pattern("#")
                .pattern("C")
                .define('#', ModBlocks.ADOBE_BRICKS.get())
                .define('C', Items.CLAY_BALL)
                .unlockedBy("has_clay", this.has(Items.CLAY_BALL))
                .unlockedBy("has_adobe_bricks", this.has(ModBlocks.ADOBE_BRICKS.get()))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.JADE_BLOCK.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.JADE.get())
                .unlockedBy("has_jade", this.has(ModItems.JADE.get()))
                .save(this.output);

        ShapelessRecipeBuilder.shapeless(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModItems.JADE.get(), 9)
                .requires(ModBlocks.JADE_BLOCK.asItem())
                .unlockedBy("has_jade_block", this.has(ModBlocks.JADE_BLOCK.get()))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.SERPENTINITE_ROCK.get(), 1)
                .pattern("###")
                .pattern("#J#")
                .pattern("###")
                .define('#', Items.COBBLESTONE)
                .define('J', ModItems.JADE.get())
                .unlockedBy("has_jade", this.has(ModItems.JADE.get()))
                .unlockedBy("has_cobblestone", this.has(Items.COBBLESTONE))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.SERPENTINITE_BRICKS.get(), 4)
                .pattern("##")
                .pattern("##")
                .define('#', ModBlocks.SERPENTINITE_ROCK.get())
                .unlockedBy("has_serpentinite_rock", this.has(ModBlocks.SERPENTINITE_ROCK.get()))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.REGENERATION_OLMEC_HEAD.get())
                .pattern("S#A")
                .pattern("#J#")
                .pattern("###")
                .define('S', Items.SUGAR)
                .define('A', Items.GOLDEN_APPLE)
                .define('J', ModBlocks.JADE_BLOCK.asItem())
                .define('#', Items.STONE_BRICKS)
                .unlockedBy("has_sugar", this.has(Items.SUGAR))
                .unlockedBy("has_golden_apple", this.has(Items.GOLDEN_APPLE))
                .unlockedBy("has_jade_block", this.has(ModBlocks.JADE_BLOCK.asItem()))
                .unlockedBy("has_stone_bricks", this.has(Items.STONE_BRICKS))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.DAMAGE_OLMEC_HEAD.get())
                .pattern("F#S")
                .pattern("#J#")
                .pattern("###")
                .define('F', Items.ROTTEN_FLESH)
                .define('S', Items.IRON_SWORD)
                .define('J', ModBlocks.JADE_BLOCK.asItem())
                .define('#', Items.STONE_BRICKS)
                .unlockedBy("has_rotten_flesh", this.has(Items.ROTTEN_FLESH))
                .unlockedBy("has_iron_sword", this.has(Items.IRON_SWORD))
                .unlockedBy("has_jade_block", this.has(ModBlocks.JADE_BLOCK.asItem()))
                .unlockedBy("has_stone_bricks", this.has(Items.STONE_BRICKS))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.RESISTANT_OLMEC_HEAD.get())
                .pattern("T#O")
                .pattern("#J#")
                .pattern("###")
                .define('T', Items.TURTLE_HELMET)
                .define('O', Items.OBSIDIAN)
                .define('J', ModBlocks.JADE_BLOCK.asItem())
                .define('#', Items.STONE_BRICKS)
                .unlockedBy("has_turtle_helmet", this.has(Items.TURTLE_HELMET))
                .unlockedBy("has_obsidian", this.has(Items.OBSIDIAN))
                .unlockedBy("has_jade_block", this.has(ModBlocks.JADE_BLOCK.asItem()))
                .unlockedBy("has_stone_bricks", this.has(Items.STONE_BRICKS))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.CONDUIT_OLMEC_HEAD.get())
                .pattern("H#S")
                .pattern("#J#")
                .pattern("###")
                .define('H', Items.HEART_OF_THE_SEA)
                .define('S', Items.SPONGE)
                .define('J', ModBlocks.JADE_BLOCK.asItem())
                .define('#', Items.STONE_BRICKS)
                .unlockedBy("has_heart_of_the_sea", this.has(Items.HEART_OF_THE_SEA))
                .unlockedBy("has_sponge", this.has(Items.SPONGE))
                .unlockedBy("has_jade_block", this.has(ModBlocks.JADE_BLOCK.asItem()))
                .unlockedBy("has_stone_bricks", this.has(Items.STONE_BRICKS))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.COMBAT, ModItems.SERPENTINITE_ROD.get(), 4)
                .pattern("S")
                .pattern("S")
                .define('S', ModBlocks.SERPENTINITE_ROCK.asItem())
                .unlockedBy("has_serpentinite_block", this.has(ModBlocks.SERPENTINITE_ROCK.asItem()))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.COMBAT, ModItems.MACUAHUITL.get())
                .pattern("OJO")
                .pattern("OTO")
                .pattern(" S ")
                .define('S', ModItems.SERPENTINITE_ROD)
                .define('J', ModItems.JADE)
                .define('O', ModItems.OBSIDIAN_TOOTH)
                .define('T', Items.STICK)
                .unlockedBy("has_jade", this.has(ModItems.JADE))
                .unlockedBy("has_obsidian_tooth", this.has(ModItems.OBSIDIAN_TOOTH))
                .unlockedBy("has_serpentinite_rod", this.has(ModItems.SERPENTINITE_ROD))
                .save(this.output);

        // SMELTING

        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(ModBlocks.RAW_ADOBE.get()),
                        RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.ADOBE_BRICKS.get(),
                        0.1f,
                        200
                )
                .unlockedBy("has_raw_adobe", this.has(ModBlocks.RAW_ADOBE.asItem()))
                .save(this.output, "adobe_bricks_smelting");

        SimpleCookingRecipeBuilder.smelting(
                this.tag(ModTags.Items.SMELTS_TO_JADE),
                RecipeCategory.MISC,
                ModItems.JADE.get(),
                0.15F,
                200)
                .unlockedBy("has_jade_ore", this.has(ModTags.Items.SMELTS_TO_JADE))
                .save(this.output, "jade_smelting");
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
