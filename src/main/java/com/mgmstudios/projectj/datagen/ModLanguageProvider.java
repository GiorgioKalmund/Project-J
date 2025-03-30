package com.mgmstudios.projectj.datagen;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {

    public ModLanguageProvider(PackOutput output) {
        super(output, ProjectJ.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {

        // Items
        addItem(ModItems.JADE, "Jade");
        addItem(ModItems.RAW_JADE, "Raw Jade");
        addItem(ModItems.MACUAHUITL, "Macuahuitl");
        addItem(ModItems.OBSIDIAN_TOOTH, "Obsidian Tooth");
        addItem(ModItems.SERPENTINITE_ROD, "Serpentinite Rod");
        addItem(ModItems.SUN_ARMOR_HELMET, "Sun Crown");
        addItem(ModItems.SACRIFICIAL_DAGGER, "Sacrificial Dagger");
        addItem(ModItems.TROWEL, "Trowel");

        // Blocks
        addBlock(ModBlocks.RAW_ADOBE, "Raw Adobe");
        addBlock(ModBlocks.ADOBE_BRICKS, "Adobe Bricks");
        addBlock(ModBlocks.JADE_ORE, "Jade Ore");
        addBlock(ModBlocks.DEEPSLATE_JADE_ORE, "Deepslate Jade Ore");
        addBlock(ModBlocks.JADE_BLOCK, "Block of Jade");
        addBlock(ModBlocks.CHIMNEY, "Adobe Chimney");
        addBlock(ModBlocks.SNAKE_STATUE, "Snake Statue");

        addBlock(ModBlocks.ADOBE_FURNACE, "Adobe Furnace");
        addBlock(ModBlocks.ADOBE_BRICKS_SLAB, "Adobe Bricks Slab");
        addBlock(ModBlocks.ADOBE_BRICKS_STAIRS, "Adobe Bricks Stairs");
        addBlock(ModBlocks.ADOBE_BRICKS_WALL, "Adobe Bricks Wall");

        addBlock(ModBlocks.SERPENTINITE_ROCK, "Serpentinite Rock");
        addBlock(ModBlocks.SERPENTINITE_ROCK_SLAB, "Serpentinite Rock Slab");
        addBlock(ModBlocks.SERPENTINITE_ROCK_STAIRS, "Serpentinite Rock Stairs");
        addBlock(ModBlocks.SERPENTINITE_ROCK_WALL, "Serpentinite Rock Wall");

        addBlock(ModBlocks.SERPENTINITE_BRICKS, "Serpentinite Bricks");
        addBlock(ModBlocks.SERPENTINITE_BRICKS_SLAB, "Serpentinite Bricks Slab");
        addBlock(ModBlocks.SERPENTINITE_BRICKS_STAIRS, "Serpentinite Bricks Stairs");
        addBlock(ModBlocks.SERPENTINITE_BRICKS_WALL, "Serpentinite Bricks Wall");

        addBlock(ModBlocks.COBBLED_SERPENTINITE, "Cobbled Serpentinite");
        addBlock(ModBlocks.COBBLED_SERPENTINITE_SLAB, "Cobbled Serpentinite Slab");
        addBlock(ModBlocks.COBBLED_SERPENTINITE_STAIRS, "Cobbled Serpentinite Stairs");
        addBlock(ModBlocks.COBBLED_SERPENTINITE_WALL, "Cobbled Serpentinite Wall");

        addBlock(ModBlocks.SERPENTINITE_PILLAR, "Serpentinite Pillar");
        addBlock(ModBlocks.SERPENTINITE_BENCH, "Serpentinite Bench");
        addBlock(ModBlocks.SERPENTINITE_BENCH_CORNER, "Serpentinite Bench Corner");
        addBlock(ModBlocks.ANCIENT_ALTAR, "Ancient Altar");

        addBlock(ModBlocks.RESISTANT_OLMEC_HEAD, "Resistant Olmec Head");
        add(ModBlocks.RESISTANT_OLMEC_HEAD.asItem(), "Resistant Olmec Head");
        addBlock(ModBlocks.CONDUIT_OLMEC_HEAD, "Conduit Olmec Head");
        add(ModBlocks.CONDUIT_OLMEC_HEAD.asItem(), "Conduit Olmec Head");
        addBlock(ModBlocks.DAMAGE_OLMEC_HEAD, "Damage Olmec Head");
        add(ModBlocks.DAMAGE_OLMEC_HEAD.asItem(), "Damage Olmec Head");
        addBlock(ModBlocks.REGENERATION_OLMEC_HEAD, "Regeneration Olmec Head");
        add(ModBlocks.REGENERATION_OLMEC_HEAD.asItem(), "Regeneration Olmec Head");

        addBlock(ModBlocks.MESQUITE_PLANKS, "Mesquite Planks");
        addBlock(ModBlocks.MESQUITE_LOG, "Mesquite Log");
        addBlock(ModBlocks.STRIPPED_MESQUITE_LOG, "Stripped Mesquite Log");
        addBlock(ModBlocks.MESQUITE_FENCE, "Mesquite Fence");
        addBlock(ModBlocks.MESQUITE_SLAB, "Mesquite Slab");
        addBlock(ModBlocks.MESQUITE_STAIRS, "Mesquite Stairs");
        addBlock(ModBlocks.MESQUITE_FENCE_GATE, "Mesquite Fence Gate");
        addBlock(ModBlocks.MESQUITE_LEAVES, "Mesquite Leaves");
        addBlock(ModBlocks.MESQUITE_BRAZIER, "Mesquite Brazier");
        addBlock(ModBlocks.MESQUITE_SAPLING, "Mesquite Sapling");
        addBlock(ModBlocks.MESQUITE_BENCH, "Mesquite Bench");
        addBlock(ModBlocks.MESQUITE_BENCH_CORNER, "Mesquite Bench Corner");

        add("container.projectj.adobe_furnace", "Adobe Furnace");
        add("itemGroup.projectj.projectj_tab", "Project J");
        add("tooltip.projectj.adobe_furnace.tooltip", "A powerful new furnace!\n§9§l[LShift] to learn more§r");
        add("tooltip.projectj.adobe_furnace.tooltip.shift_down", "A powerful new furnace!\nWith the help of a chimney, can burn for §lEVEN§r longer");

    }
}
