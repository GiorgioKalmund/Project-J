package com.mgmstudios.projectj.datagen;

import com.google.common.collect.ImmutableList;
import com.mgmstudios.projectj.block.ModBlockFamilies;
import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.item.ModItems;
import com.mgmstudios.projectj.item.custom.MagnifyingGlassItem.MagnifyingRecipeBuilder;
import com.mgmstudios.projectj.recipe.acientaltar.AncientAltarRecipeBuilder;
import com.mgmstudios.projectj.recipe.metate.MetateRecipeBuilder;
import com.mgmstudios.projectj.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {

    public static final ImmutableList<ItemLike> PYRITE_SMELTABLES = ImmutableList.of(ModItems.RAW_PYRITE);

    protected ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        generateForEnabledBlockFamilies(FeatureFlagSet.of(FeatureFlags.VANILLA));

        // CRAFTING
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.PACKED_ADOBE, 4)
                .pattern("SB")
                .pattern("CW")
                .define('B', Items.WATER_BUCKET)
                .define('C', Items.CLAY_BALL)
                .define('W', Items.WHEAT)
                .define('S', ModBlocks.ADOBE_SAND.get())
                .unlockedBy("has_water_bucket", this.has(Items.WATER_BUCKET))
                .unlockedBy("has_wheat", this.has(Items.WHEAT))
                .unlockedBy("has_clay", this.has(Items.CLAY_BALL))
                .unlockedBy("has_adobe_sand", this.has(ModBlocks.ADOBE_SAND))
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
                .unlockedBy("has_adobe_bricks", this.has(ModBlocks.ADOBE_BRICKS))
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

        nineBlockStorageRecipes(RecipeCategory.MISC, ModItems.JADE, RecipeCategory.BUILDING_BLOCKS, ModBlocks.JADE_BLOCK);

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


        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.MESQUITE_BRAZIER.asItem())
                .pattern("NSN")
                .pattern("SCS")
                .pattern("LLL")
                .define('L', ModTags.Items.MESQUITE_LOGS)
                .define('S', Items.STICK)
                .define('C', ItemTags.COALS)
                .define('N', Items.IRON_NUGGET)
                .unlockedBy("has_iron_nugget", this.has(Items.IRON_NUGGET))
                .unlockedBy("has_mesquite_logs", this.has(ModTags.Items.MESQUITE_LOGS))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModItems.SACRIFICIAL_DAGGER)
                .pattern("  Q")
                .pattern(" O ")
                .pattern("S  ")
                .define('Q', Items.QUARTZ)
                .define('S', ModItems.SERPENTINITE_ROD.get())
                .define('O', ModItems.OBSIDIAN_TOOTH.get())
                .unlockedBy("has_serpentinite_rod", this.has(ModItems.SERPENTINITE_ROD.get()))
                .unlockedBy("has_obsidian_tooth", this.has(ModItems.OBSIDIAN_TOOTH.get()))
                .unlockedBy("has_quartz", this.has(Items.QUARTZ))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModItems.TROWEL)
                .pattern("  R")
                .pattern(" G ")
                .pattern("S  ")
                .define('G', ModItems.PYRITE_INGOT)
                .define('S', ModItems.SERPENTINITE_ROD.get())
                .define('R', ModBlocks.SERPENTINITE_ROCK.asItem())
                .unlockedBy("has_serpentinite_rod", this.has(ModItems.SERPENTINITE_ROD.get()))
                .unlockedBy("has_serpentinite_rock", this.has(ModBlocks.SERPENTINITE_ROCK.asItem()))
                .unlockedBy("has_pyrite_ingot", this.has(ModItems.PYRITE_INGOT))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.SERPENTINITE_BENCH, 2)
                .pattern("SPS")
                .pattern("s s")
                .define('S', ModBlocks.SERPENTINITE_ROCK.asItem())
                .define('P', ModBlocks.SERPENTINITE_PILLAR.asItem())
                .define('s', ModBlocks.SERPENTINITE_ROCK_SLAB.asItem())
                .unlockedBy("has_serpentinite_rock", this.has(ModBlocks.SERPENTINITE_ROCK.asItem()))
                .unlockedBy("has_serpentinite_pillar", this.has(ModBlocks.SERPENTINITE_PILLAR.asItem()))
                .unlockedBy("has_serpentinite_rock_slab", this.has(ModBlocks.SERPENTINITE_ROCK_SLAB.asItem()))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.MESQUITE_BENCH, 2)
                .pattern("PLP")
                .pattern("s s")
                .define('L', ModTags.Items.MESQUITE_LOGS)
                .define('P', ModBlocks.MESQUITE_PLANKS.asItem())
                .define('s', ModBlocks.MESQUITE_SLAB.asItem())
                .unlockedBy("has_mesquite_logs", this.has(ModTags.Items.MESQUITE_LOGS))
                .unlockedBy("has_mesquite_planks", this.has(ModBlocks.MESQUITE_PLANKS.asItem()))
                .unlockedBy("has_mesquite_slab", this.has(ModBlocks.MESQUITE_SLAB.asItem()))
                .save(this.output);

        woodFromLogs(ModBlocks.MESQUITE_WOOD.get(), ModBlocks.MESQUITE_LOG.asItem());
        woodFromLogs(ModBlocks.STRIPPED_MESQUITE_WOOD.get(), ModBlocks.STRIPPED_MESQUITE_LOG.asItem());
        planksFromLogs(ModBlocks.MESQUITE_PLANKS, ModTags.Items.MESQUITE_LOGS, 4);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, Items.STICK, 16)
                .pattern("#")
                .pattern("#")
                .define('#', ItemTags.LOGS)
                .unlockedBy("has_log", this.has(ItemTags.LOGS))
                .save(this.output, "sticks_from_log");

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModItems.MAGNIFYING_GLASS)
                .pattern("#")
                .pattern("P")
                .pattern("S")
                .define('#', Tags.Items.GLASS_BLOCKS)
                .define('S', Items.STICK)
                .define('P', ModItems.PYRITE_INGOT)
                .unlockedBy("has_glass_block", this.has(Tags.Items.GLASS_BLOCKS))
                .unlockedBy("has_stick", this.has(Items.STICK))
                .unlockedBy("has_pyrite_ingot", this.has(ModItems.PYRITE_INGOT))
                .save(this.output);

        ShapelessRecipeBuilder.shapeless(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModItems.FIRE_STARTER)
                .requires(Items.STICK, 2)
                .requires(ModItems.PYRITE_INGOT)
                .unlockedBy("has_stick", this.has(Items.STICK))
                .unlockedBy("has_pyrite_ingot", this.has(ModItems.PYRITE_INGOT))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.OLMEC_ALTAR)
                .pattern("SsS")
                .pattern("CJC")
                .pattern("CCC")
                .define('s', ModBlocks.SERPENTINITE_ROCK_SLAB)
                .define('S', ModBlocks.SERPENTINITE_ROCK)
                .define('C', Blocks.STONE)
                .define('J', ModBlocks.JADE_BLOCK)
                .unlockedBy("has_jade_block", this.has(ModBlocks.JADE_BLOCK))
                .unlockedBy("has_serpentinite_rock", this.has(ModBlocks.SERPENTINITE_ROCK))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.ANCIENT_ALTAR)
                .pattern("#P#")
                .pattern(" R ")
                .pattern("sJs")
                .define('#', ModBlocks.ADOBE_BRICKS)
                .define('P', ModItems.PYRITE_INGOT)
                .define('J', ModBlocks.JADE_BLOCK)
                .define('s', ModBlocks.SERPENTINITE_ROCK_SLAB)
                .define('R', ModItems.SERPENTINITE_ROD)
                .unlockedBy("has_pyrite_ingot", this.has(ModItems.PYRITE_INGOT))
                .unlockedBy("has_jade_block", this.has(ModBlocks.JADE_BLOCK.asItem()))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModItems.LITTLE_MAN_VOODOO)
                .pattern(" J ")
                .pattern("WSW")
                .pattern(" s ")
                .define('J', ModBlocks.JADE_BLOCK)
                .define('s', ModBlocks.SERPENTINITE_ROCK_STAIRS)
                .define('S', ModBlocks.SERPENTINITE_ROCK)
                .define('W', ModBlocks.SERPENTINITE_ROCK_WALL)
                .unlockedBy("has_jade", this.has(ModBlocks.JADE_BLOCK.asItem()))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModItems.VOODOO_CATCHER)
                .pattern("  J")
                .pattern(" R ")
                .pattern("R  ")
                .define('R', ModItems.SERPENTINITE_ROD)
                .define('J', ModItems.JADE)
                .unlockedBy("has_jade", this.has(ModItems.JADE))
                .unlockedBy("has_serpentinite_rod", this.has(ModItems.SERPENTINITE_ROD))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModItems.PYRITE_MAGNET)
                .pattern("S S")
                .pattern("# #")
                .pattern("###")
                .define('S', ModBlocks.SERPENTINITE_ROCK)
                .define('#', ModItems.PYRITE_INGOT)
                .unlockedBy("has_serpentinite_rock", this.has(ModBlocks.SERPENTINITE_ROCK.asItem()))
                .unlockedBy("has_pyrite_ingot", this.has(ModItems.PYRITE_INGOT))
                .save(this.output);

        nineBlockStorageRecipes(RecipeCategory.MISC, ModItems.RAW_PYRITE, RecipeCategory.BUILDING_BLOCKS, ModBlocks.RAW_PYRITE_BLOCK);
        nineBlockStorageRecipes(RecipeCategory.MISC, ModItems.PYRITE_INGOT, RecipeCategory.BUILDING_BLOCKS, ModBlocks.PYRITE_BLOCK);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.MAGNIFYING_GLASS_STAND)
                .pattern("###")
                .pattern("# #")
                .define('#', Items.IRON_INGOT)
                .unlockedBy("has_iron_ingot", this.has(Items.IRON_INGOT))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.METATE)
                .pattern("SSS")
                .pattern("# #")
                .define('#', Items.STONE)
                .define('S', Items.STONE_SLAB)
                .unlockedBy("has_stone", this.has(Items.STONE))
                .unlockedBy("has_stone_slab", this.has(Items.STONE_SLAB))
                .save(this.output);

        ShapelessRecipeBuilder.shapeless(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModItems.MAIZE_MASH)
                .requires(ModItems.MAIZE)
                .requires(Items.BOWL )
                .unlockedBy("has_maize", this.has(ModItems.MAIZE))
                .unlockedBy("has_bowl", this.has(Items.BOWL))
                .save(this.output);

        ShapelessRecipeBuilder.shapeless(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModItems.CHILI_CON_CARNE)
                .requires(ModItems.MAIZE)
                .requires(ModItems.CHILI)
                .requires(ModTags.Items.COOKED_MEAT)
                .requires(Items.BOWL)
                .unlockedBy("has_maize", this.has(ModItems.MAIZE))
                .unlockedBy("has_chili", this.has(ModItems.CHILI))
                .unlockedBy("has_cooked_meat", this.has(ModTags.Items.COOKED_MEAT))
                .unlockedBy("has_bowl", this.has(Items.BOWL))
                .save(this.output);

        ShapelessRecipeBuilder.shapeless(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModItems.CHILI_CON_CARNE)
                .requires(ModItems.MAIZE_MASH)
                .requires(ModItems.CHILI)
                .requires(ModTags.Items.COOKED_MEAT)
                .unlockedBy("has_maize_mash", this.has(ModItems.MAIZE_MASH))
                .unlockedBy("has_chili", this.has(ModItems.CHILI))
                .unlockedBy("has_cooked_meat", this.has(ModTags.Items.COOKED_MEAT))
                .save(this.output, "chili_con_carne_from_maize_mash");

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.BOTANY_POT)
                .pattern("#D#")
                .pattern("###")
                .define('#', Items.BRICK)
                .define('D', ItemTags.DIRT)
                .unlockedBy("has_brick", this.has(Items.BRICK))
                .unlockedBy("has_dirt", this.has(ItemTags.DIRT))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModItems.JADE_HELMET)
                .pattern("###")
                .pattern("# #")
                .define('#', ModItems.JADE)
                .unlockedBy("has_jade", this.has(ModItems.JADE))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModItems.JADE_CHESTPLATE)
                .pattern("# #")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.JADE)
                .unlockedBy("has_jade", this.has(ModItems.JADE))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModItems.JADE_LEGGINGS)
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .define('#', ModItems.JADE)
                .unlockedBy("has_jade", this.has(ModItems.JADE))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModItems.JADE_BOOTS)
                .pattern("# #")
                .pattern("# #")
                .define('#', ModItems.JADE)
                .unlockedBy("has_jade", this.has(ModItems.JADE))
                .save(this.output);

        ShapelessRecipeBuilder.shapeless(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModItems.QUEST_BOOK)
                .requires(Items.BOOK)
                .requires(ModItems.JADE)
                .unlockedBy("has_book", this.has(Items.BOOK))
                .unlockedBy("has_jade", this.has(ModItems.JADE))
                .save(this.output);


        // ANCIENT ALTAR

        AncientAltarRecipeBuilder.regularWithPyrite(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModItems.MACUAHUITL)
                .requires(ModItems.SERPENTINITE_ROD)
                .requires(ModItems.OBSIDIAN_TOOTH, 4)
                .requires(ModItems.JADE)
                .requires(Items.STICK )
                .unlockedBy("has_jade", this.has(ModItems.JADE))
                .save(this.output, "macuahuitl_from_altar");

        AncientAltarRecipeBuilder.regularWithPyrite(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModItems.CRUDE_SACRIFICE_BOWL)
                .requires(Items.BOWL)
                .unlockedBy("has_bowl", this.has(Items.BOWL))
                .save(this.output, "crude_sacrifice_bowl_from_altar");

        // METATE

        MetateRecipeBuilder.regular(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModItems.MAIZE, ModItems.MAIZE_SEEDS, 2)
                .unlockedBy("has_maize", this.has(ModItems.MAIZE))
                .save(this.output);

        MetateRecipeBuilder.regular(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, Items.WHEAT, Items.WHEAT_SEEDS, 2)
                .unlockedBy("has_wheat", this.has(Items.WHEAT))
                .save(this.output);

        MetateRecipeBuilder.regular(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModItems.CHILI, ModItems.CHILI_SEEDS, 2)
                .unlockedBy("has_chili", this.has(ModItems.CHILI))
                .save(this.output);

        MetateRecipeBuilder.regular(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, Items.BEETROOT, Items.BEETROOT_SEEDS, 2)
                .unlockedBy("has_beetroot", this.has(Items.BEETROOT))
                .save(this.output);

        MetateRecipeBuilder.regular(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, Blocks.MELON, Items.MELON_SEEDS, 8)
                .unlockedBy("has_melon_block", this.has(Blocks.MELON))
                .save(this.output, "melon_seeds_from_melon_block_metate");

        MetateRecipeBuilder.regular(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, Items.MELON_SLICE, Items.MELON_SEEDS, 2)
                .unlockedBy("has_melon", this.has(Items.MELON_SLICE))
                .save(this.output, "melon_seeds_from_melon_slice_metate");

        MetateRecipeBuilder.regular(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, Items.PUMPKIN, Items.PUMPKIN_SEEDS, 8)
                .unlockedBy("has_pumpkin", this.has(Items.PUMPKIN))
                .save(this.output, "pumpkin_seeds_from_pumpkin_metate");

        // SMELTING

        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(ModBlocks.PACKED_ADOBE.get()),
                        RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.ADOBE_BRICKS.get(),
                        0.1f,
                        200
                )
                .unlockedBy("has_raw_adobe", this.has(ModBlocks.PACKED_ADOBE.asItem()))
                .save(this.output, "adobe_bricks_smelting");

        SimpleCookingRecipeBuilder.smelting(
                this.tag(ModTags.Items.SMELTS_TO_JADE),
                RecipeCategory.MISC,
                ModItems.JADE.get(),
                0.15F,
                200)
                .unlockedBy("has_smelts_to_jade", this.has(ModTags.Items.SMELTS_TO_JADE))
                .save(this.output, "jade_smelting");

        oreSmelting(PYRITE_SMELTABLES, RecipeCategory.MISC, ModItems.PYRITE_INGOT, 0.15F, 200, "pyrite");

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.RAW_PYRITE_BLOCK), RecipeCategory.MISC, ModBlocks.PYRITE_BLOCK.asItem(), 0.15F, 200)
                .unlockedBy("has_raw_pyrite_block", this.has(ModBlocks.RAW_PYRITE_BLOCK.asItem()))
                .save(this.output, "pyrite_block_from_raw_pyrite_block");

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.COBBLED_SERPENTINITE.get()), RecipeCategory.MISC, ModBlocks.SERPENTINITE_ROCK.asItem(), 0.15F, 200)
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

    public static void buildMagnifyingGlassRecipes(){
        MagnifyingRecipeBuilder.magnify(Blocks.SAND, Blocks.GLASS);
        MagnifyingRecipeBuilder.magnify(ModBlocks.ADOBE_SAND.get(), ModBlocks.ADOBE_GLASS.get());
        MagnifyingRecipeBuilder.magnify(Blocks.RAW_IRON_BLOCK, Blocks.IRON_BLOCK);
        MagnifyingRecipeBuilder.magnify(Blocks.RAW_GOLD_BLOCK, Blocks.GOLD_BLOCK);
        MagnifyingRecipeBuilder.magnify(Blocks.RAW_COPPER_BLOCK, Blocks.COPPER_BLOCK);
        MagnifyingRecipeBuilder.magnify(ModBlocks.RAW_PYRITE_BLOCK.get(), ModBlocks.PYRITE_BLOCK.get());
        MagnifyingRecipeBuilder.magnify(ModBlocks.PYRITE_BLOCK.get(), ModBlocks.LIQUID_PYRITE.get());
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
