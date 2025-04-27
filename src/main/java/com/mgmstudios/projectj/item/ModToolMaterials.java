package com.mgmstudios.projectj.item;

import com.mgmstudios.projectj.util.ModTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ToolMaterial;
import net.neoforged.neoforge.common.Tags;

public class ModToolMaterials {

    public static final ToolMaterial PAXEL_MATERIAL =  new ToolMaterial(
        BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 310, 9.0F, 4.0F, 20, ModTags.Items.JADE
    );

    public static final ToolMaterial SACRIFICIAL_DAGGER_MATERIAL =  new ToolMaterial(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 3, 1.0F, 0.0F, 10, Tags.Items.GEMS_QUARTZ
    );

    public static final ToolMaterial TROWEL_MATERIAL =  new ToolMaterial(
            BlockTags.INCORRECT_FOR_STONE_TOOL, 132, 4.0F, 1.0F, 5, ModTags.Items.SERPENTINITE
    );
}
