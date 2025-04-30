package com.mgmstudios.projectj.util;

import com.mgmstudios.projectj.ProjectJ;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class ModTags {


    public static class Blocks {


        public static final TagKey<Block> MINEABLE_WITH_PAXEL = createTag("mineable/paxel");
        public static final TagKey<Block> SERPENTINITE = createTag("serpentinite");
        public static final TagKey<Block> ADOBE = createTag("adobe");

        public static final TagKey<Block> MESQUITE = createTag("mesquite");
        public static final TagKey<Block> MESQUITE_LOGS = createTag("mesquite_logs");
        public static final TagKey<Block> BENCHES = createTag("benches");

        public static final TagKey<Block> PYRITE_ORE_REPLACEABLES = createTag("pyrite_ore_replaceables");

        public static final TagKey<Block> PYRITE_BLOCKS = createTag("pyrite_blocks");

        private static TagKey<Block> createTag(String name){
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, name));
        }
    }

    public static class Fluids {
        public static final TagKey<Fluid> LIQUID_PYRITE = createTag("liquid_pyrite");

        private static TagKey<Fluid> createTag(String name) {
            return TagKey.create(Registries.FLUID, ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, name));
        }
    }

    public static class Biomes {
        public static final TagKey<Biome> ADOBE_DESERT = createTag("adobe_desert");

        private static TagKey<Biome> createTag(String name) {
            return TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> ADOBE_FURNACE_SMELTABLE = createTag("adobe_furnace_smeltable");
        public static final TagKey<Item> JADE_ORES = createTag("jade_ores");

        public static final TagKey<Item> JADE = createTag("jade");
        public static final TagKey<Item> SMELTS_TO_JADE = createTag("smelts_to_jade");

        public static final TagKey<Item> PYRITE = createTag("pyrite");
        public static final TagKey<Item> PYRITE_BLOCKS = createTag("pyrite_blocks");

        public static final TagKey<Item> ALTAR_CRAFTABLE = createTag("altar_craftable");

        public static final TagKey<Item> MESQUITE = createTag("mesquite");
        public static final TagKey<Item> MESQUITE_LOGS = createTag("mesquite_logs");

        public static final TagKey<Item> SUN_ARMOR = createTag("sun_armor");
        public static final TagKey<Item> SERPENTINITE = createTag("serpentinite");

        public static final TagKey<Item> BENCHES = createTag("benches");

        public static final TagKey<Item> COOKED_MEAT = createTag("cooked_meat");

        private static TagKey<Item> createTag(String name){
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, name));
        }
    }
}
