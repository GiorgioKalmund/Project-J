package com.mgmstudios.projectj.worldgen.feature;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.worldgen.ModConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class ModTreeGrowers {
    public static final TreeGrower MESQUITE = new TreeGrower(ProjectJ.MOD_ID + ":mesquite",
            Optional.of(ModConfiguredFeatures.MESQUITE_KEY), Optional.of(ModConfiguredFeatures.MESQUITE_KEY), Optional.empty());
}
