package com.mgmstudios.projectj.datagen;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ProjectJ.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.ADOBE_FURNACE.get())
                .add(ModBlocks.ADOBE_BRICKS.get())
                .add(ModBlocks.JADE_ORE.get())
                .add(ModBlocks.DEEPSLATE_JADE_ORE.get())
                .add(ModBlocks.JADE_BLOCK.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.JADE_ORE.get())
                .add(ModBlocks.DEEPSLATE_JADE_ORE.get())
                .add(ModBlocks.JADE_BLOCK.get());

    }
}
