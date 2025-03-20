package com.mgmstudios.projectj.datagen;

import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.item.ModItems;
import com.mgmstudios.projectj.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;

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
    }
}
