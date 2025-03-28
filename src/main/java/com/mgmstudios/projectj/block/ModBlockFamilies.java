package com.mgmstudios.projectj.block;

import com.google.common.collect.Maps;
import com.mgmstudios.projectj.ProjectJ;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.Map;
import java.util.stream.Stream;

@EventBusSubscriber (modid = ProjectJ.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModBlockFamilies {

    public static BlockFamily SERPENTINITE_ROCK;
    public static BlockFamily SERPENTINITE_BRICKS;
    public static BlockFamily COBBLED_SERPENTINITE;
    public static BlockFamily ADOBE_BRICKS;

    private static final Map<Block, BlockFamily> MAP = Maps.newHashMap();

    private static BlockFamily.Builder familyBuilder(Block baseBlock) {
        BlockFamily.Builder blockfamily$builder = new BlockFamily.Builder(baseBlock);
        BlockFamily blockfamily = MAP.put(baseBlock, blockfamily$builder.getFamily());
        if (blockfamily != null) {
            throw new IllegalStateException("Duplicate family definition for " + BuiltInRegistries.BLOCK.getKey(baseBlock));
        } else {
            return blockfamily$builder;
        }
    }

    public static Stream<BlockFamily> getAllFamilies() {
        return MAP.values().stream();
    }

    @SubscribeEvent
    public static void onRegisterBlocks(RegisterEvent event) {
        if (event.getRegistryKey().equals(Registries.BLOCK)) {

            SERPENTINITE_ROCK = familyBuilder(ModBlocks.SERPENTINITE_ROCK.get())
                    .stairs(ModBlocks.SERPENTINITE_ROCK_STAIRS.get())
                    .slab(ModBlocks.SERPENTINITE_ROCK_SLAB.get())
                    .wall(ModBlocks.SERPENTINITE_ROCK_WALL.get())
                    .getFamily();

            SERPENTINITE_BRICKS = familyBuilder(ModBlocks.SERPENTINITE_BRICKS.get())
                    .stairs(ModBlocks.SERPENTINITE_BRICKS_STAIRS.get())
                    .slab(ModBlocks.SERPENTINITE_BRICKS_SLAB.get())
                    .wall(ModBlocks.SERPENTINITE_BRICKS_WALL.get())
                    .getFamily();

            COBBLED_SERPENTINITE = familyBuilder(ModBlocks.COBBLED_SERPENTINITE.get())
                    .stairs(ModBlocks.COBBLED_SERPENTINITE_STAIRS.get())
                    .slab(ModBlocks.COBBLED_SERPENTINITE_SLAB.get())
                    .wall(ModBlocks.COBBLED_SERPENTINITE_WALL.get())
                    .getFamily();

            ADOBE_BRICKS = familyBuilder(ModBlocks.ADOBE_BRICKS.get())
                    .stairs(ModBlocks.ADOBE_BRICKS_STAIRS.get())
                    .slab(ModBlocks.ADOBE_BRICKS_SLAB.get())
                    .wall(ModBlocks.ADOBE_BRICKS_WALL.get())
                    .getFamily();
        }
    }
}
