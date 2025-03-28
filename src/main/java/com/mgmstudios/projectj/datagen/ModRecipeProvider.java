package com.mgmstudios.projectj.datagen;

import com.mgmstudios.projectj.block.ModBlockFamilies;
import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.item.ModItems;
import com.mgmstudios.projectj.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
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
        generateForEnabledBlockFamilies(FeatureFlagSet.of(FeatureFlags.VANILLA));

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

        ShapelessRecipeBuilder.shapeless(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.JADE_BLOCK.get())
                .requires(ModItems.JADE, 9)
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


        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, Items.OBSIDIAN)
                .pattern("###")
                .pattern("#D#")
                .pattern("###")
                .define('#', ModItems.OBSIDIAN_TOOTH.get())
                .define('D', Items.DIRT)
                .unlockedBy("has_obsidian_tooth", this.has(ModItems.OBSIDIAN_TOOTH.get()))
                .save(this.output);

        ShapelessRecipeBuilder.shapeless(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.MESQUITE_PLANKS, 4)
                .requires(ModBlocks.MESQUITE_LOG.get())
                .unlockedBy("has_mesquite_log", this.has(ModBlocks.MESQUITE_LOG.get()))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.MESQUITE_BRAZIER.asItem())
                .pattern("NSN")
                .pattern("SCS")
                .pattern("LLL")
                .define('L', ModTags.Items.MESQUITE_LOGS)
                .define('S', Items.STICK)
                .define('C', ItemTags.COALS)
                .define('N', Items.IRON_NUGGET)
                .unlockedBy("has_stick", this.has(Items.STICK))
                .unlockedBy("has_coal", this.has(ItemTags.COALS))
                .unlockedBy("has_iron_nugget", this.has(Items.IRON_NUGGET))
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

        SimpleCookingRecipeBuilder.smelting(
                    Ingredient.of(ModBlocks.COBBLED_SERPENTINITE.get()),
                        RecipeCategory.MISC,
                        ModBlocks.SERPENTINITE_ROCK.asItem(),
                        0.15F,
                        200)
                .unlockedBy("has_cobbled_serpentinite", this.has(ModBlocks.COBBLED_SERPENTINITE.asItem()))
                .save(this.output, "cobbled_serpentinite_smelting");

        // STONECUTTING
        stonecutterResultFromBase(RecipeCategory.MISC,  ModItems.OBSIDIAN_TOOTH.get(), Blocks.OBSIDIAN,8);
        stonecutterResultFromBase(RecipeCategory.MISC, ModBlocks.SERPENTINITE_ROCK_SLAB.asItem(), ModBlocks.SERPENTINITE_ROCK.get(), 2);
        stonecutterResultFromBase(RecipeCategory.MISC, ModBlocks.SERPENTINITE_ROCK_STAIRS.asItem(), ModBlocks.SERPENTINITE_ROCK.get());
        stonecutterResultFromBase(RecipeCategory.MISC, ModBlocks.SERPENTINITE_ROCK_WALL.asItem(), ModBlocks.SERPENTINITE_ROCK.get());
        stonecutterResultFromBase(RecipeCategory.MISC, ModBlocks.SERPENTINITE_BRICKS.asItem(), ModBlocks.SERPENTINITE_ROCK.get());
        stonecutterResultFromBase(RecipeCategory.MISC, ModBlocks.SERPENTINITE_PILLAR.asItem(), ModBlocks.SERPENTINITE_ROCK.get());
        stonecutterResultFromBase(RecipeCategory.MISC, ModBlocks.SERPENTINITE_BRICKS_SLAB.asItem(), ModBlocks.SERPENTINITE_BRICKS.get(), 2);
        stonecutterResultFromBase(RecipeCategory.MISC, ModBlocks.SERPENTINITE_BRICKS_STAIRS.asItem(), ModBlocks.SERPENTINITE_BRICKS.get());
        stonecutterResultFromBase(RecipeCategory.MISC, ModBlocks.SERPENTINITE_BRICKS_WALL.asItem(), ModBlocks.SERPENTINITE_BRICKS.get());
        stonecutterResultFromBase(RecipeCategory.MISC, ModBlocks.COBBLED_SERPENTINITE_SLAB.asItem(), ModBlocks.COBBLED_SERPENTINITE.get(), 2);
        stonecutterResultFromBase(RecipeCategory.MISC, ModBlocks.COBBLED_SERPENTINITE_STAIRS.asItem(), ModBlocks.COBBLED_SERPENTINITE.get());
        stonecutterResultFromBase(RecipeCategory.MISC, ModBlocks.COBBLED_SERPENTINITE_WALL.asItem(), ModBlocks.COBBLED_SERPENTINITE.get());
        stonecutterResultFromBase(RecipeCategory.MISC, ModBlocks.ADOBE_BRICKS_SLAB.asItem(), ModBlocks.ADOBE_BRICKS.get(), 2);
        stonecutterResultFromBase(RecipeCategory.MISC, ModBlocks.ADOBE_BRICKS_STAIRS.asItem(), ModBlocks.ADOBE_BRICKS.get());
        stonecutterResultFromBase(RecipeCategory.MISC, ModBlocks.ADOBE_BRICKS_WALL.asItem(), ModBlocks.ADOBE_BRICKS.get());


    }

    protected void generateForEnabledBlockFamilies(FeatureFlagSet enabledFeatures) {
        ModBlockFamilies.getAllFamilies().filter(BlockFamily::shouldGenerateRecipe).forEach(p_359455_ -> this.generateRecipes(p_359455_, enabledFeatures));
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
