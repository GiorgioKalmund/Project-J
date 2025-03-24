package com.mgmstudios.projectj.util;

import com.mgmstudios.projectj.ProjectJ;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModTags {


    public static class Blocks {


        private static TagKey<Block> createTag(String name){
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, name));
        }
    }


    public static class Items {
        public static final TagKey<Item> ADOBE_FURNACE_SMELTABLE = createTag("adobe_furnace_smeltable");
        public static final TagKey<Item> JADE_ORES = createTag("jade_ores");
        public static final TagKey<Item> SMELTS_TO_JADE = createTag("smelts_to_jade");

        private static TagKey<Item> createTag(String name){
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, name));
        }
    }
}
