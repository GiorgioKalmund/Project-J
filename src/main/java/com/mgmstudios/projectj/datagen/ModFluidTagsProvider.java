package com.mgmstudios.projectj.datagen;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.fluid.ModFluids;
import com.mgmstudios.projectj.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.tags.FluidTags;

import java.util.concurrent.CompletableFuture;

public class ModFluidTagsProvider extends FluidTagsProvider {

    public ModFluidTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider, ProjectJ.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModTags.Fluids.LIQUID_PYRITE)
                .add(ModFluids.PYRITE.get(), ModFluids.FLOWING_PYRITE.get());
    }
}
