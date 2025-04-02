package com.mgmstudios.projectj.datagen;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.item.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

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
        addItem(ModItems.MAGNIFYING_GLASS, "Magnifying Glass");
        addItem(ModItems.PYRITE_INGOT, "Pyrite Ingot");
        addItem(ModItems.RAW_PYRITE, "Raw Pyrite");
        addItem(ModItems.LIQUID_PYRITE_BUCKET, "Liquid Pyrite Bucket");
        addItem(ModItems.FIRE_STARTER, "Fire Starter");
        addItem(ModItems.CRUDE_SACRIFICE_BOWL, "Crude Sacrificial Bowl");
        addItem(ModItems.FILLED_CRUDE_SACRIFICE_BOWL, "Filled Crude Sacrificial Bowl");

        // Blocks
        addBlock(ModBlocks.RAW_ADOBE, "Raw Adobe");
        addBlock(ModBlocks.ADOBE_BRICKS, "Adobe Bricks");
        addBlock(ModBlocks.JADE_ORE, "Jade Ore");
        addBlock(ModBlocks.DEEPSLATE_JADE_ORE, "Deepslate Jade Ore");
        addBlock(ModBlocks.JADE_BLOCK, "Block of Jade");
        addBlock(ModBlocks.CHIMNEY, "Adobe Chimney");
        addBlock(ModBlocks.SNAKE_STATUE, "Snake Statue");
        addBlock(ModBlocks.MAGNIFYING_GLASS_STAND, "Magnifying Glass Stand");
        addBlock(ModBlocks.TELEPORTATION_PAD, "Teleportation Pad");
        addItem(ModItems.TELEPORTATION_KEY, "Teleportation Key");

        addBlock(ModBlocks.PYRITE_BLOCK, "Pyrite Block");
        addBlock(ModBlocks.RAW_PYRITE_BLOCK, "Raw Pyrite Block");
        addBlock(ModBlocks.PYRITE_ORE, "Pyrite Ore");
        addBlock(ModBlocks.LIQUID_PYRITE, "Liquid Pyrite");

        addBlock(ModBlocks.ADOBE_FURNACE, "Adobe Furnace");
        addBlock(ModBlocks.ADOBE_BRICKS_SLAB, "Adobe Bricks Slab");
        addBlock(ModBlocks.ADOBE_BRICKS_STAIRS, "Adobe Bricks Stairs");
        addBlock(ModBlocks.ADOBE_BRICKS_WALL, "Adobe Bricks Wall");

        addBlock(ModBlocks.SERPENTINITE_ROCK, "Serpentinite Rock");
        addBlock(ModBlocks.SERPENTINITE_ROCK_SLAB, "Serpentinite Rock Slab");
        addBlock(ModBlocks.SERPENTINITE_ROCK_STAIRS, "Serpentinite Rock Stairs");
        addBlock(ModBlocks.SERPENTINITE_ROCK_WALL, "Serpentinite Rock Wall");
        addBlock(ModBlocks.SERPENTINITE_ROCK_BUTTON, "Serpentinite Rock Button");
        addBlock(ModBlocks.SERPENTINITE_ROCK_PRESSURE_PLATE, "Serpentinite Rock Pressure Plate");

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
        addBlock(ModBlocks.MESQUITE_WOOD, "Mesquite Wood");
        addBlock(ModBlocks.STRIPPED_MESQUITE_WOOD, "Stripped Mesquite Wood");
        addBlock(ModBlocks.MESQUITE_FENCE, "Mesquite Fence");
        addBlock(ModBlocks.MESQUITE_SLAB, "Mesquite Slab");
        addBlock(ModBlocks.MESQUITE_STAIRS, "Mesquite Stairs");
        addBlock(ModBlocks.MESQUITE_FENCE_GATE, "Mesquite Fence Gate");
        addBlock(ModBlocks.MESQUITE_BUTTON, "Mesquite Button");
        addBlock(ModBlocks.MESQUITE_PRESSURE_PLATE, "Mesquite Pressure Plate");
        addBlock(ModBlocks.MESQUITE_LEAVES, "Mesquite Leaves");
        addBlock(ModBlocks.MESQUITE_BRAZIER, "Mesquite Brazier");
        addBlock(ModBlocks.MESQUITE_SAPLING, "Mesquite Sapling");
        addBlock(ModBlocks.MESQUITE_BENCH, "Mesquite Bench");
        addBlock(ModBlocks.MESQUITE_BENCH_CORNER, "Mesquite Bench Corner");

        // Tooltips

        addTooltip(ModItems.MAGNIFYING_GLASS, "Helps you melt things.");
        addTooltipShift(ModItems.MAGNIFYING_GLASS, "Helps you melt and magnify things.");
        addTooltip(ModBlocks.ADOBE_FURNACE, "A powerful new furnace!\n§9§l[LShift] to learn more§r");
        addTooltipShift(ModBlocks.ADOBE_FURNACE, "A powerful new furnace!\nWith the help of a chimney, can burn for §lEVEN§r longer");

        add("container.projectj.adobe_furnace", "Adobe Furnace");
        add("itemGroup.projectj.projectj_tab", "Project J");

    }

    protected void addTooltip(ItemLike itemLike, String value){
        addSuffixedTooltip(itemLike, value, "");
    }

    protected void addTooltipShift(ItemLike itemLike, String value){
        addSuffixedTooltip(itemLike, value, ".shift_down");
    }

    private void addSuffixedTooltip(ItemLike itemLike, String value, String suffix){
        add("tooltip." + ProjectJ.MOD_ID + "." + BuiltInRegistries.ITEM.getKey(itemLike.asItem()).getPath() + ".tooltip" + suffix, value);
    }
}
