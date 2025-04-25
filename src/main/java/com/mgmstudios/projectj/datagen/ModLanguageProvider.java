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
        addItem(ModItems.JADE_CHESTPLATE, "Jade Chestplate");
        addItem(ModItems.JADE_LEGGINGS, "Jade Leggings");
        addItem(ModItems.JADE_BOOTS, "Jade Boots");
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
        addItem(ModItems.LITTLE_KING_SPAWN_EGG, "Little King Spawn Egg");
        addItem(ModItems.LITTLE_KING_VOODOO, "Little King Voodoo");
        addItem(ModItems.VOODOO_CATCHER, "Voodoo Catcher");
        addItem(ModItems.PYRITE_MAGNET, "Pyrite Magnet");
        addItem(ModItems.JADE_MAGNET, "Jade Magnet");
        addItem(ModItems.MAIZE, "Maize");
        addItem(ModItems.MAIZE_SEEDS, "Maize Seeds");
        addItem(ModItems.MAIZE_MASH, "Maize Mash");
        addItem(ModItems.CHILI, "Chili");
        addItem(ModItems.CHILI_SEEDS, "Chili Seeds");
        addItem(ModItems.CHILI_CON_CARNE, "Chili con Carne");
        addItem(ModItems.STONE_MANO, "Stone Mano");
        addItem(ModItems.QUETZAL_FEATHER, "Quetzal Feather");
        addItem(ModItems.QUETZAL_SPAWN_EGG, "Quetzal Spawn Egg");
        addItem(ModItems.QUETZAL_EGG, "Quetzal Egg");
        addItem(ModItems.QUEST_BOOK, "Ancient Codex");
        addItem(ModItems.TELEPORTATION_CORE, "Teleportation Core");

        // Blocks
        addBlock(ModBlocks.PACKED_ADOBE, "Packed Adobe");
        addBlock(ModBlocks.ADOBE_SAND, "Adobe Sand");
        addBlock(ModBlocks.ADOBE_GLASS, "Adobe Glass");
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
        addBlock(ModBlocks.POTTED_MAIZE_CROP, "Potted Maize Crop");
        addBlock(ModBlocks.CHILI_BUSH, "Chili Bush");
        addBlock(ModBlocks.POTTED_CHILI_BUSH, "Potted Chili Bush");
        addBlock(ModBlocks.METATE, "Metate and Mano");
        addBlock(ModBlocks.BOTANY_POT, "Botany Pot");

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
        addTooltip("magnet_item.radius", "§7+ Radius: %d§r");
        addTooltip("magnet_item.strength", "§7+ Strength: %d§r");

        add("container.projectj.adobe_furnace", "Adobe Furnace");
        add("itemGroup.projectj.projectj_tab", "Project J");

        // Entity
        addEntity(ModEntities.LITTLE_MAN_ENTITY.get(), "Little Man");
        addEntity(ModEntities.LITTLE_KING_ENTITY.get(), "Little King");
        addEntity(ModEntities.QUETZAL_ENTITY.get(), "Quetzal");

        // Advancements
        addAdvancementStoryTitle("root", "Project J");
        addAdvancementStoryDescription("root", "Your Project J journey begins!");
        addAdvancementStoryTitle("get_jade", "Oh, Shiny...er?");
        addAdvancementStoryDescription("get_jade", "Find a Jade Ore and smelt the Result");
        addAdvancementStoryTitle("botany_pot", "Botany Pots?");
        addAdvancementStoryDescription("botany_pot", "Not quite, but still useful! (and pretty)");
        addAdvancementStoryTitle("magnifying_glass", "Precise Inspection");
        addAdvancementStoryDescription("magnifying_glass", "Don't look into the sun with it!");
        addAdvancementStoryTitle("magnifying_glass_stand", "Hand's off");
        addAdvancementStoryDescription("magnifying_glass_stand", "Automate your Magnifying Glass using a Magnifying Glass Stand");
        addAdvancementStoryTitle("pyrite", "Gold?");
        addAdvancementStoryDescription("pyrite", "Find Pyrite, also known as Fool's Fold");
        addAdvancementStoryTitle("liquid_pyrite_bucket", "Liquify!");
        addAdvancementStoryDescription("liquid_pyrite_bucket", "Collect some Liquid Pyrite");
        addAdvancementStoryTitle("ancient_altar", "Ancient Rirtuals");
        addAdvancementStoryDescription("ancient_altar", "Craft an Ancient Altar");
        addAdvancementStoryTitle("crude_sacrificial_bowl", "Sacrifices will be made");
        addAdvancementStoryDescription("crude_sacrificial_bowl", "Obtain a Crude Sacrificial Bowl by using a Bowl and some Liquid Pyrite in the Ancient Altar");
        addAdvancementStoryTitle("sacrificial_dagger", "Oh, Sharp!");
        addAdvancementStoryDescription("sacrificial_dagger", "Craft a Sacrificial Dagger");
        addAdvancementStoryTitle("filled_crude_sacrificial_bowl", "Centeotl's Will");
        addAdvancementStoryDescription("filled_crude_sacrificial_bowl", "Perform a self sacrifice to obtain a Filled Crude Sacrificial Bowl");

        // GUI
        addGuiButton("close", "Close");
    }

    public void addTooltip(String name, String value){
        addSuffixedTooltip(name, value, "");
    }
    public void addTooltip(ItemLike itemLike, String value){
        addSuffixedTooltip(getName(itemLike), value, "");
    }

    public void addTooltipShift(ItemLike itemLike, String value){
        addSuffixedTooltip(getName(itemLike), value, ".shift_down");
    }

    public void addSuffixedTooltip(String name, String value, String suffix){
        add("tooltip." + ProjectJ.MOD_ID + "." + name  + ".tooltip" + suffix, value);
    }

    public void addEntity(EntityType<? extends Entity> entity, String name){
        add("entity." + ProjectJ.MOD_ID + "." + BuiltInRegistries.ENTITY_TYPE.getKey(entity).getPath(), name);
    }

    public void addAdvancementTitle(String folder, String name, String title){
        add("advancements.projectj."+folder+"."+name+".title", title);
    }

    public void addAdvancementStoryTitle(String name, String title){
        add("advancements.projectj.story."+name+".title", title);
    }

    public void addAdvancementDescription(String folder, String name, String description){
        add("advancements.projectj."+folder+"."+name+".description", description);
    }

    public void addAdvancementStoryDescription(String name, String description){
        add("advancements.projectj.story."+name+".description", description);
    }

    public void addGuiButton(String name, String description){
        add("gui.projectj." + name, description);
    }


    String getName(ItemLike itemLike){
        return BuiltInRegistries.ITEM.getKey(itemLike.asItem()).getPath();
    }

}
