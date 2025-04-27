package com.mgmstudios.projectj.datagen;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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
                .add(ModBlocks.SNAKE_STATUE.get())
                .add(ModBlocks.OLMEC_ALTAR.get())
                .add(ModBlocks.ANCIENT_ALTAR.get())
                .add(ModBlocks.LITTLE_MAN_STATUE_BLOCK.get())
                .add(ModBlocks.EMPTY_LITTLE_MAN_STATUE_BLOCK.get())
                .add(ModBlocks.METATE.get())
                .add(ModBlocks.ADOBE_GLASS.get());

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .addTag(ModTags.Blocks.ADOBE)
                .addTag(ModTags.Blocks.SERPENTINITE)
                .addTag(ModTags.Blocks.PYRITE_BLOCKS);

        tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(ModBlocks.ADOBE_SAND.get())
                .add(ModBlocks.PACKED_ADOBE.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.JADE_ORE.get())
                .add(ModBlocks.DEEPSLATE_JADE_ORE.get())
                .add(ModBlocks.JADE_BLOCK.get());

        tag(ModTags.Blocks.MINEABLE_WITH_PAXEL)
                .addTag(BlockTags.MINEABLE_WITH_PICKAXE)
                .addTag(BlockTags.MINEABLE_WITH_AXE)
                .addTag(BlockTags.MINEABLE_WITH_SHOVEL)
                .addTag(BlockTags.MINEABLE_WITH_HOE);

        tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.MESQUITE_FENCE_GATE.get());

        tag(BlockTags.WOODEN_FENCES)
                .add(ModBlocks.MESQUITE_FENCE.get());

        tag(BlockTags.PLANKS)
                .add(ModBlocks.MESQUITE_PLANKS.get());

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

        tag(BlockTags.STONE_PRESSURE_PLATES)
                .add(ModBlocks.SERPENTINITE_ROCK_PRESSURE_PLATE.get());

        tag(BlockTags.WOODEN_PRESSURE_PLATES)
                .add(ModBlocks.MESQUITE_PRESSURE_PLATE.get());

        tag(BlockTags.WOODEN_BUTTONS)
                .add(ModBlocks.MESQUITE_BUTTON.get());

        tag(BlockTags.STONE_BUTTONS)
                .add(ModBlocks.SERPENTINITE_ROCK_BUTTON.get());

        tag(BlockTags.LEAVES)
                .add(ModBlocks.MESQUITE_LEAVES.get());

        tag(ModTags.Blocks.PYRITE_BLOCKS)
                .add(ModBlocks.RAW_PYRITE_BLOCK.get())
                .add(ModBlocks.PYRITE_BLOCK.get())
                .add(ModBlocks.PYRITE_ORE.get());

        tag(ModTags.Blocks.PYRITE_ORE_REPLACEABLES)
                .add(Blocks.SAND)
                .add(Blocks.SANDSTONE);

        tag(BlockTags.OVERWORLD_CARVER_REPLACEABLES)
                .add(ModBlocks.PYRITE_ORE.get())
                .add(ModBlocks.RAW_PYRITE_BLOCK.get());

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
                .add(ModBlocks.SERPENTINITE_ROCK_BUTTON.get())
                .add(ModBlocks.SERPENTINITE_ROCK_PRESSURE_PLATE.get())
                .add(ModBlocks.SERPENTINITE_BRICKS.get())
                .add(ModBlocks.SERPENTINITE_BRICKS_STAIRS.get())
                .add(ModBlocks.SERPENTINITE_BRICKS_SLAB.get())
                .add(ModBlocks.SERPENTINITE_BRICKS_WALL.get())
                .add(ModBlocks.COBBLED_SERPENTINITE.get())
                .add(ModBlocks.COBBLED_SERPENTINITE_STAIRS.get())
                .add(ModBlocks.COBBLED_SERPENTINITE_SLAB.get())
                .add(ModBlocks.COBBLED_SERPENTINITE_WALL.get());

        tag(BlockTags.DIRT)
                .add(ModBlocks.PACKED_ADOBE.get());

        tag(BlockTags.SAND)
                .add(ModBlocks.ADOBE_SAND.get());

        tag(BlockTags.LOGS_THAT_BURN)
                .addTag(ModTags.Blocks.MESQUITE_LOGS);

        tag(ModTags.Blocks.MESQUITE_LOGS)
                .add(ModBlocks.MESQUITE_LOG.get())
                .add(ModBlocks.STRIPPED_MESQUITE_LOG.get())
                .add(ModBlocks.MESQUITE_WOOD.get())
                .add(ModBlocks.STRIPPED_MESQUITE_WOOD.get())
                .add(ModBlocks.HOLLOW_MESQUITE_LOG.get());

        tag(ModTags.Blocks.BENCHES)
                .add(ModBlocks.MESQUITE_BENCH.get())
                .add(ModBlocks.MESQUITE_BENCH_CORNER.get())
                .add(ModBlocks.SERPENTINITE_BENCH.get())
                .add(ModBlocks.SERPENTINITE_BENCH_CORNER.get());

    }
}
