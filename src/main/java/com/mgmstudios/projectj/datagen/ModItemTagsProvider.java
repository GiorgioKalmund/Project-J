package com.mgmstudios.projectj.datagen;

import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.item.ModItems;
import com.mgmstudios.projectj.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        tag(ItemTags.ARROWS)
                .add(ModItems.OBSIDIAN_ARROW.get());

        tag(ModTags.Items.JADE_ORES)
                .add(ModBlocks.JADE_ORE.asItem())
                .add(ModBlocks.DEEPSLATE_JADE_ORE.asItem());

        tag(ModTags.Items.ADOBE_FURNACE_SMELTABLE)
               .add(ModItems.RAW_JADE.get())
               .add(ModBlocks.PACKED_ADOBE.asItem());

        tag(ModTags.Items.SMELTS_TO_JADE)
                .add(ModBlocks.JADE_ORE.asItem())
                .add(ModBlocks.DEEPSLATE_JADE_ORE.asItem())
                .add(ModItems.RAW_JADE.get());

        tag(ModTags.Items.PYRITE)
                .add(ModItems.PYRITE_INGOT.get())
                .add(ModItems.RAW_PYRITE.get())
                .add(ModBlocks.RAW_PYRITE_BLOCK.asItem())
                .add(ModBlocks.PYRITE_BLOCK.asItem())
                .add(ModItems.LIQUID_PYRITE_BUCKET.get());

        tag(ModTags.Items.SUN_ARMOR)
                .add(ModItems.SUN_ARMOR_HELMET.get());

        tag(ModTags.Items.JADE)
                .add(ModItems.JADE.get());

        tag(ItemTags.PICKAXES)
                .add(ModItems.MACUAHUITL.get());
        tag(ItemTags.AXES)
                .add(ModItems.MACUAHUITL.get());
        tag(ItemTags.SHOVELS)
                .add(ModItems.MACUAHUITL.get());
        tag(ItemTags.HOES)
                .add(ModItems.MACUAHUITL.get());

        tag(ItemTags.DURABILITY_ENCHANTABLE)
                .add(ModItems.SACRIFICIAL_DAGGER.get())
                .add(ModItems.TROWEL.get())
                .add(ModItems.CHIMALLI_SHIELD.get());

        tag(ItemTags.SWORD_ENCHANTABLE)
                .add(ModItems.MACUAHUITL.get());

        tag(ItemTags.HEAD_ARMOR)
                .add(ModItems.SUN_ARMOR_HELMET.get())
                .add(ModItems.JADE_HELMET.get());

        tag(ItemTags.CHEST_ARMOR)
                .add(ModItems.JADE_CHESTPLATE.get());

        tag(ItemTags.LEG_ARMOR)
                .add(ModItems.JADE_LEGGINGS.get());

        tag(ItemTags.FOOT_ARMOR)
                .add(ModItems.JADE_BOOTS.get());

        tag(ModTags.Items.ALTAR_CRAFTABLE)
                .add(Items.NETHERITE_PICKAXE)
                .add(ModItems.RAW_JADE.get());

        tag(ItemTags.LOGS_THAT_BURN)
                .addTag(ModTags.Items.MESQUITE_LOGS);

        tag(ItemTags.STONE_TOOL_MATERIALS)
                .add(ModBlocks.COBBLED_SERPENTINITE.asItem());

        tag(ItemTags.STONE_CRAFTING_MATERIALS)
                .add(ModBlocks.COBBLED_SERPENTINITE.asItem());

        copy(ModTags.Blocks.MESQUITE_LOGS, ModTags.Items.MESQUITE_LOGS);
        copy(ModTags.Blocks.SERPENTINITE, ModTags.Items.SERPENTINITE);
        copy(ModTags.Blocks.BENCHES, ModTags.Items.BENCHES);
        copy(ModTags.Blocks.PYRITE_BLOCKS, ModTags.Items.PYRITE_BLOCKS);

        tag(ModTags.Items.COOKED_MEAT)
                .add(Items.COOKED_BEEF)
                .add(Items.COOKED_CHICKEN)
                .add(Items.COOKED_MUTTON)
                .add(Items.COOKED_PORKCHOP);

        tag(ItemTags.SAPLINGS)
                .add(ModBlocks.MESQUITE_SAPLING.asItem());

        tag(ModTags.Items.MESQUITE).add(
                        ModBlocks.MESQUITE_SAPLING.asItem(),
                        ModBlocks.MESQUITE_BRAZIER.asItem(),
                        ModBlocks.MESQUITE_LEAVES.asItem(),
                        ModBlocks.MESQUITE_PLANKS.asItem(),
                        ModBlocks.MESQUITE_STAIRS.asItem(),
                        ModBlocks.MESQUITE_SLAB.asItem(),
                        ModBlocks.MESQUITE_FENCE.asItem(),
                        ModBlocks.MESQUITE_FENCE_GATE.asItem(),
                        ModBlocks.MESQUITE_BUTTON.asItem(),
                        ModBlocks.MESQUITE_PRESSURE_PLATE.asItem(),
                        ModBlocks.MESQUITE_BENCH.asItem(),
                        ModBlocks.MESQUITE_BENCH_CORNER.asItem()
                )
                .addTag(ModTags.Items.MESQUITE_LOGS);

        tag(ItemTags.PLANKS)
                .add(ModBlocks.MESQUITE_PLANKS.asItem());

        tag(ItemTags.SLABS)
                .add(ModBlocks.SERPENTINITE_BRICKS_SLAB.asItem())
                .add(ModBlocks.SERPENTINITE_ROCK_SLAB.asItem())
                .add(ModBlocks.ADOBE_BRICKS_SLAB.asItem())
                .add(ModBlocks.MESQUITE_SLAB.asItem());

        tag(ItemTags.STAIRS)
                .add(ModBlocks.SERPENTINITE_BRICKS_STAIRS.asItem())
                .add(ModBlocks.SERPENTINITE_ROCK_STAIRS.asItem())
                .add(ModBlocks.ADOBE_BRICKS_STAIRS.asItem())
                .add(ModBlocks.MESQUITE_STAIRS.asItem());

        tag(ItemTags.WALLS)
                .add(ModBlocks.SERPENTINITE_BRICKS_WALL.asItem())
                .add(ModBlocks.SERPENTINITE_ROCK_WALL.asItem())
                .add(ModBlocks.ADOBE_BRICKS_WALL.asItem());

        tag(ItemTags.FENCE_GATES)
                .add(ModBlocks.MESQUITE_FENCE_GATE.asItem());

        tag(ItemTags.WOODEN_FENCES)
                .add(ModBlocks.MESQUITE_FENCE.asItem());

        tag(ItemTags.STONE_BUTTONS)
                .add(ModBlocks.SERPENTINITE_ROCK_BUTTON.asItem());

        tag(ItemTags.WOODEN_BUTTONS)
                .add(ModBlocks.MESQUITE_BUTTON.asItem());

        tag(ItemTags.WOODEN_PRESSURE_PLATES)
                .add(ModBlocks.MESQUITE_PRESSURE_PLATE.asItem());
    }
}
