package com.mgmstudios.projectj.datagen;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ProjectJ.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.JADE_ORE.get())
                .add(ModBlocks.DEEPSLATE_JADE_ORE.get())
                .add(ModBlocks.JADE_BLOCK.get())
                .add(ModBlocks.REGENERATION_OLMEC_HEAD.get())
                .add(ModBlocks.DAMAGE_OLMEC_HEAD.get())
                .add(ModBlocks.CONDUIT_OLMEC_HEAD.get())
                .add(ModBlocks.RESISTANT_OLMEC_HEAD.get())
                .add(ModBlocks.SNAKE_STATUE.get());

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .addTag(ModTags.Blocks.ADOBE)
                .addTag(ModTags.Blocks.SERPENTINITE);

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.JADE_ORE.get())
                .add(ModBlocks.DEEPSLATE_JADE_ORE.get())
                .add(ModBlocks.JADE_BLOCK.get());

        tag(ModTags.Blocks.MINEABLE_WITH_PAXEL)
                .addTag(BlockTags.MINEABLE_WITH_PICKAXE)
                .addTag(BlockTags.MINEABLE_WITH_AXE)
                .addTag(BlockTags.MINEABLE_WITH_SHOVEL)
                .addTag(BlockTags.MINEABLE_WITH_HOE);

        tag(BlockTags.SLABS)
                .add(ModBlocks.SERPENTINITE_ROCK_SLAB.get())
                .add(ModBlocks.SERPENTINITE_BRICKS_SLAB.get())
                .add(ModBlocks.COBBLED_SERPENTINITE_SLAB.get())
                .add(ModBlocks.ADOBE_BRICKS_SLAB.get());

        tag(BlockTags.STAIRS)
                .add(ModBlocks.SERPENTINITE_ROCK_STAIRS.get())
                .add(ModBlocks.SERPENTINITE_BRICKS_STAIRS.get())
                .add(ModBlocks.COBBLED_SERPENTINITE_STAIRS.get())
                .add(ModBlocks.ADOBE_BRICKS_STAIRS.get());

        tag(BlockTags.WALLS)
                .add(ModBlocks.SERPENTINITE_ROCK_WALL.get())
                .add(ModBlocks.SERPENTINITE_BRICKS_WALL.get())
                .add(ModBlocks.COBBLED_SERPENTINITE_WALL.get())
                .add(ModBlocks.ADOBE_BRICKS_WALL.get());

        tag(BlockTags.LEAVES)
                .add(ModBlocks.MESQUITE_LEAVES.get());

        tag(ModTags.Blocks.ADOBE)
                .add(ModBlocks.ADOBE_FURNACE.get())
                .add(ModBlocks.CHIMNEY.get())
                .add(ModBlocks.ADOBE_BRICKS.get())
                .add(ModBlocks.ADOBE_BRICKS_STAIRS.get())
                .add(ModBlocks.ADOBE_BRICKS_SLAB.get())
                .add(ModBlocks.ADOBE_BRICKS_WALL.get());

        tag(ModTags.Blocks.SERPENTINITE)
                .add(ModBlocks.SERPENTINITE_PILLAR.get())
                .add(ModBlocks.SERPENTINITE_BENCH.get())
                .add(ModBlocks.COBBLED_SERPENTINITE.get())
                .add(ModBlocks.SERPENTINITE_ROCK.get())
                .add(ModBlocks.SERPENTINITE_ROCK_STAIRS.get())
                .add(ModBlocks.SERPENTINITE_ROCK_SLAB.get())
                .add(ModBlocks.SERPENTINITE_ROCK_WALL.get())
                .add(ModBlocks.SERPENTINITE_BRICKS.get())
                .add(ModBlocks.SERPENTINITE_BRICKS_STAIRS.get())
                .add(ModBlocks.SERPENTINITE_BRICKS_SLAB.get())
                .add(ModBlocks.SERPENTINITE_BRICKS_WALL.get())
                .add(ModBlocks.COBBLED_SERPENTINITE.get())
                .add(ModBlocks.COBBLED_SERPENTINITE_STAIRS.get())
                .add(ModBlocks.COBBLED_SERPENTINITE_SLAB.get())
                .add(ModBlocks.COBBLED_SERPENTINITE_WALL.get());


        tag(BlockTags.DIRT)
                .add(ModBlocks.RAW_ADOBE.get());

        tag(BlockTags.LOGS_THAT_BURN)
                .addTag(ModTags.Blocks.MESQUITE_LOGS);

        tag(ModTags.Blocks.MESQUITE_LOGS)
                .add(ModBlocks.MESQUITE_LOG.get())
                .add(ModBlocks.STRIPPED_MESQUITE_LOG.get());

    }
}
