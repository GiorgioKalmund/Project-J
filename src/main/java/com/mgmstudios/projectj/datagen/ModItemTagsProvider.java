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
import net.neoforged.neoforge.common.Tags;

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

        tag(ModTags.Items.JADE)
                .add(ModItems.JADE.get());

        tag(ItemTags.MINING_ENCHANTABLE)
                .add(ModItems.PAXEL.get());

        tag(ItemTags.DURABILITY_ENCHANTABLE)
                .add(ModItems.PAXEL.get());

        tag(ItemTags.MINING_LOOT_ENCHANTABLE)
                .add(ModItems.PAXEL.get());

        tag(ModTags.Items.ALTAR_CRAFTABLE)
                .add(Items.NETHERITE_PICKAXE);
    }
}
