package com.mgmstudios.projectj.datagen;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.entity.ModEntities;
import com.mgmstudios.projectj.item.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ItemLike;
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
        addItem(ModItems.JADE_HELMET, "Jade Helmet");
        addItem(ModItems.SACRIFICIAL_DAGGER, "Sacrificial Dagger");
        addItem(ModItems.TROWEL, "Trowel");
        addItem(ModItems.MAGNIFYING_GLASS, "Magnifying Glass");
        addItem(ModItems.PYRITE_INGOT, "Pyrite Ingot");
        addItem(ModItems.RAW_PYRITE, "Raw Pyrite");
        addItem(ModItems.LIQUID_PYRITE_BUCKET, "Liquid Pyrite Bucket");
        addItem(ModItems.FIRE_STARTER, "Fire Starter");
        addItem(ModItems.CRUDE_SACRIFICE_BOWL, "Crude Sacrificial Bowl");
        addItem(ModItems.FILLED_CRUDE_SACRIFICE_BOWL, "Filled Crude Sacrificial Bowl");
        addItem(ModItems.TELEPORTATION_KEY, "Teleportation Key");
        addItem(ModItems.LITTLE_MAN_SPAWN_EGG, "Little Man Spawn Egg");
        addItem(ModItems.LITTLE_MAN_VOODOO, "Little Man Voodoo");
        addItem(ModItems.VOODOO_CATCHER, "Voodoo Catcher");
        addItem(ModItems.PYRITE_MAGNET, "Pyrite Magnet");
        addItem(ModItems.MAIZE, "Maize");
        addItem(ModItems.MAIZE_SEEDS, "Maize Seeds");
        addItem(ModItems.MAIZE_MASH, "Maize Mash");
        addItem(ModItems.CHILI, "Chili");
        addItem(ModItems.CHILI_SEEDS, "Chili Seeds");
        addItem(ModItems.STONE_MANO, "Stone Mano");

        // Blocks
        addBlock(ModBlocks.PACKED_ADOBE, "Packed Adobe");
        addBlock(ModBlocks.ADOBE_SAND, "Adobe Sand");
        addBlock(ModBlocks.ADOBE_BRICKS, "Adobe Bricks");
        addBlock(ModBlocks.JADE_ORE, "Jade Ore");
        addBlock(ModBlocks.DEEPSLATE_JADE_ORE, "Deepslate Jade Ore");
        addBlock(ModBlocks.JADE_BLOCK, "Block of Jade");
        addBlock(ModBlocks.CHIMNEY, "Adobe Chimney");
        addBlock(ModBlocks.SNAKE_STATUE, "Snake Statue");
        addBlock(ModBlocks.MAGNIFYING_GLASS_STAND, "Magnifying Glass Stand");
        addBlock(ModBlocks.TELEPORTATION_PAD, "Teleportation Pad");
        addBlock(ModBlocks.LITTLE_MAN_STATUE_BLOCK, "Little Man Statue");
        addBlock(ModBlocks.EMPTY_LITTLE_MAN_STATUE_BLOCK, "Empty Little Man Statue");
        addBlock(ModBlocks.MAIZE_CROP, "Maize Crop");
        addBlock(ModBlocks.CHILI_BUSH, "Chili Bush");
        addBlock(ModBlocks.METATE, "Metate and Mano");

        addBlock(ModBlocks.PYRITE_BLOCK, "Pyrite Block");
        addBlock(ModBlocks.RAW_PYRITE_BLOCK, "Raw Pyrite Block");
        addBlock(ModBlocks.PYRITE_ORE, "Pyrite Ore");
        addBlock(ModBlocks.LIQUID_PYRITE, "Liquid Pyrite");

        addBlock(ModBlocks.ADOBE_FURNACE, "Adobe Furnace");
        addBlock(ModBlocks.ADOBE_BRICKS_SLAB, "Adobe Brick Slab");
        addBlock(ModBlocks.ADOBE_BRICKS_STAIRS, "Adobe Brick Stairs");
        addBlock(ModBlocks.ADOBE_BRICKS_WALL, "Adobe Brick Wall");

        addBlock(ModBlocks.SERPENTINITE_ROCK, "Serpentinite Rock");
        addBlock(ModBlocks.SERPENTINITE_ROCK_SLAB, "Serpentinite Rock Slab");
        addBlock(ModBlocks.SERPENTINITE_ROCK_STAIRS, "Serpentinite Rock Stairs");
        addBlock(ModBlocks.SERPENTINITE_ROCK_WALL, "Serpentinite Rock Wall");
        addBlock(ModBlocks.SERPENTINITE_ROCK_BUTTON, "Serpentinite Rock Button");
        addBlock(ModBlocks.SERPENTINITE_ROCK_PRESSURE_PLATE, "Serpentinite Rock Pressure Plate");

        addBlock(ModBlocks.SERPENTINITE_BRICKS, "Serpentinite Bricks");
        addBlock(ModBlocks.SERPENTINITE_BRICKS_SLAB, "Serpentinite Brick Slab");
        addBlock(ModBlocks.SERPENTINITE_BRICKS_STAIRS, "Serpentinite Brick Stairs");
        addBlock(ModBlocks.SERPENTINITE_BRICKS_WALL, "Serpentinite Brick Wall");

        addBlock(ModBlocks.COBBLED_SERPENTINITE, "Cobbled Serpentinite");
        addBlock(ModBlocks.COBBLED_SERPENTINITE_SLAB, "Cobbled Serpentinite Slab");
        addBlock(ModBlocks.COBBLED_SERPENTINITE_STAIRS, "Cobbled Serpentinite Stairs");
        addBlock(ModBlocks.COBBLED_SERPENTINITE_WALL, "Cobbled Serpentinite Wall");

        addBlock(ModBlocks.SERPENTINITE_PILLAR, "Serpentinite Pillar");
        addBlock(ModBlocks.SERPENTINITE_BENCH, "Serpentinite Bench");
        addBlock(ModBlocks.SERPENTINITE_BENCH_CORNER, "Serpentinite Bench Corner");
        addBlock(ModBlocks.OLMEC_ALTAR, "Olmec Altar");
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

        // Entity
        addEntity(ModEntities.LITTLE_MAN_ENTITY.get(), "Little Man");

    }

    protected void addTooltip(ItemLike itemLike, String value){
        addSuffixedTooltip(itemLike, value, "");
    }

    protected void addTooltipShift(ItemLike itemLike, String value){
        addSuffixedTooltip(itemLike, value, ".shift_down");
    }

    private void addSuffixedTooltip(ItemLike itemLike, String value, String suffix){
        add("tooltip." + ProjectJ.MOD_ID + "." + getName(itemLike)  + ".tooltip" + suffix, value);
    }

    private void addEntity(EntityType<? extends Entity> entity, String name){
        add("entity." + ProjectJ.MOD_ID + "." + BuiltInRegistries.ENTITY_TYPE.getKey(entity).getPath(), name);
    }

    String getName(ItemLike itemLike){
        return BuiltInRegistries.ITEM.getKey(itemLike.asItem()).getPath();
    }

}
