package com.mgmstudios.projectj.datagen;

import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.item.ModItems;
import com.mgmstudios.projectj.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        tag(ModTags.Items.JADE_ORES)
                .add(ModBlocks.JADE_ORE.asItem())
                .add(ModBlocks.DEEPSLATE_JADE_ORE.asItem());

        tag(ModTags.Items.ADOBE_FURNACE_SMELTABLE)
               .add(ModItems.RAW_JADE.get())
               .add(ModBlocks.RAW_ADOBE.asItem());

        tag(ModTags.Items.SMELTS_TO_JADE)
                .add(ModBlocks.JADE_ORE.asItem())
                .add(ModBlocks.DEEPSLATE_JADE_ORE.asItem())
                .add(ModItems.RAW_JADE.get());

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
                .add(ModItems.SACRIFICIAL_DAGGER.get());

        tag(ItemTags.HEAD_ARMOR)
                .add(ModItems.SUN_ARMOR_HELMET.get());

        tag(ModTags.Items.ALTAR_CRAFTABLE)
                .add(Items.NETHERITE_PICKAXE)
                .add(ModItems.RAW_JADE.get());

        tag(ItemTags.PLANKS)
                .add(ModBlocks.MESQUITE_PLANKS.asItem());

        copy(ModTags.Blocks.MESQUITE_LOGS, ModTags.Items.MESQUITE_LOGS);

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
                        ModBlocks.MESQUITE_FENCE_GATE.asItem())
                        .addTag(ModTags.Items.MESQUITE_LOGS);

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


        tag(ItemTags.WOODEN_FENCES)
                .add(ModBlocks.MESQUITE_FENCE.asItem());

        tag(ItemTags.FENCE_GATES)
                .add(ModBlocks.MESQUITE_FENCE_GATE.asItem());
    }
}
