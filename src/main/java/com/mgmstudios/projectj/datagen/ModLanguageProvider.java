package com.mgmstudios.projectj.datagen;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.entity.ModEntities;
import com.mgmstudios.projectj.item.ModItems;

import com.mgmstudios.projectj.worldgen.ModBiomes;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredHolder;

import static com.mgmstudios.projectj.component.ModDataComponents.Sockets.*;
import static net.minecraft.core.component.DataComponents.*;
import static com.mgmstudios.projectj.util.SocketComponents.dataComponentName;

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
        addItem(ModItems.CHIMALLI_SHIELD, "Chimalli Shield");
        addItem(ModItems.OBSIDIAN_ARROW, "Obsidian Arrow");
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
        addItem(ModItems.DEATH_WHISTLE, "Death Whistle");
        addItem(ModItems.HATCHET, "Hatchet");
        addItem(ModItems.RUNNER_SPAWN_EGG, "Runner Spawn Egg");
        addItem(ModItems.FLESH, "Cooked Flesh");

        addItem(ModItems.SUN_ARMOR_HELMET, "Sun Crown");
        addItem(ModItems.SUN_ARMOR_CHESTPLATE, "Sun Chestplate");
        addItem(ModItems.SUN_ARMOR_LEGGINGS, "Sun Leggings");
        addItem(ModItems.SUN_ARMOR_BOOTS, "Sun Boots");

        addItem(ModItems.AWAKENED_SUN_ARMOR_HELMET, "Awakened Sun Crown");
        addItem(ModItems.AWAKENED_SUN_ARMOR_CHESTPLATE, "Awakened Sun Chestplate");
        addItem(ModItems.AWAKENED_SUN_ARMOR_LEGGINGS, "Awakened Sun Leggings");
        addItem(ModItems.AWAKENED_SUN_ARMOR_BOOTS, "Awakened Sun Boots");

        addItem(ModItems.JADE_HELMET, "Jade Helmet");
        addItem(ModItems.JADE_CHESTPLATE, "Jade Chestplate");
        addItem(ModItems.JADE_LEGGINGS, "Jade Leggings");
        addItem(ModItems.JADE_BOOTS, "Jade Boots");

        addItem(ModItems.GLIDER_SOCKET, "Glider Gem");
        addItem(ModItems.PACIFYING_SOCKET, "Pacifying Gem");
        addItem(ModItems.REMOVE_AI_SOCKET, "Numbness Gem");
        addItem(ModItems.GIVE_AI_SOCKET, "Clarity Gem");
        addItem(ModItems.EVERYTHING_SOCKET, "Universal Gem");
        addItem(ModItems.EMPTY_SOCKET, "Empty Gem");
        addItem(ModItems.SOCKET_TESTER, "Socket Tester");
        addItem(ModItems.TOPAZ_GEM, "Topaz Gem");


        // Blocks
        addBlock(ModBlocks.PACKED_ADOBE, "Packed Adobe");
        addBlock(ModBlocks.ADOBE_SAND, "Adobe Sand");
        addBlock(ModBlocks.ADOBE_GLASS, "Adobe Glass");
        addBlock(ModBlocks.TINTED_ADOBE_GLASS, "Tinted Adobe Glass");
        addBlock(ModBlocks.ADOBE_BRICKS, "Adobe Bricks");
        addBlock(ModBlocks.JADE_ORE, "Jade Ore");
        addBlock(ModBlocks.DEEPSLATE_JADE_ORE, "Deepslate Jade Ore");
        addBlock(ModBlocks.SERPENTINITE_JADE_ORE, "Serpentinite Jade Ore");
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
        addBlock(ModBlocks.JADE_CRYSTAL, "Jade Crystal");
        addBlock(ModBlocks.HOLLOW_MESQUITE_LOG, "Hollow Mesquite Log");
        addBlock(ModBlocks.CHARCOAL_BLOCK, "Block of Charcoal");
        addBlock(ModBlocks.SOCKET_WORKBENCH, "Socket Workbench");

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

        addBlock(ModBlocks.POLISHED_SERPENTINITE, "Polished Serpentinite");
        addBlock(ModBlocks.POLISHED_SERPENTINITE_SLAB, "Polished Serpentinite Slab");
        addBlock(ModBlocks.POLISHED_SERPENTINITE_STAIRS, "Polished Serpentinite Stairs");
        addBlock(ModBlocks.POLISHED_SERPENTINITE_WALL, "Polished Serpentinite Wall");

        addBlock(ModBlocks.COBBLED_SERPENTINITE, "Cobbled Serpentinite");
        addBlock(ModBlocks.COBBLED_SERPENTINITE_SLAB, "Cobbled Serpentinite Slab");
        addBlock(ModBlocks.COBBLED_SERPENTINITE_STAIRS, "Cobbled Serpentinite Stairs");
        addBlock(ModBlocks.COBBLED_SERPENTINITE_WALL, "Cobbled Serpentinite Wall");

        addBlock(ModBlocks.SERPENTINITE_PILLAR, "Serpentinite Pillar");
        addBlock(ModBlocks.SERPENTINITE_BENCH, "Serpentinite Bench");
        addBlock(ModBlocks.SERPENTINITE_BENCH_CORNER, "Serpentinite Bench Corner");
        addBlock(ModBlocks.OLMEC_ALTAR, "Olmec Altar");
        addBlock(ModBlocks.ANCIENT_ALTAR, "Ancient Altar");
        add(ModBlocks.ANCIENT_ALTAR.asItem(), "Ancient Altar");

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
        addTooltip("awakened_armor.charged", "§4§lCH§6§lAR§e§lGED§r");
        addTooltip("awakened_armor", "§8Charges when wearing full set§r");

        // Containers
        addContainer("adobe_furnace", "Adobe Furnace");
        addContainer("socket_workbench", "Socket Workbench");
        addContainer("socket_workbench.gem_tooltip", "Socket Gems");

        // Item Groups
        add("itemGroup.projectj.projectj_tab", "Project J");

        // Entity
        addEntity(ModEntities.LITTLE_MAN_ENTITY.get(), "Little Man");
        addEntity(ModEntities.LITTLE_KING_ENTITY.get(), "Little King");
        addEntity(ModEntities.QUETZAL_ENTITY.get(), "Quetzal");
        addEntity(ModEntities.RUNNER_ENTITY.get(), "Runner");

        // Advancements
        addAdvancementStoryTitle("root", "Project J");
        addAdvancementStoryDescription("root", "Your Project J journey begins!");
        addAdvancementStoryTitle("visit_adobe_desert", "Baby it's hot outside");
        addAdvancementStoryDescription("visit_adobe_desert", "Find a Adobe Desert");
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
        addAdvancementStoryTitle("sun_armor", "Cover me in ... Gold?");
        addAdvancementStoryDescription("sun_armor", "Sun Armor might save lives, but it certainly doesn't save your bank account.");
        addAdvancementStoryTitle("awakened_sun_armor", "Phoenix! SC?");
        addAdvancementStoryDescription("awakened_sun_armor", "Obtain a full set of Awakened Sun Armor. Project J endgame!");

        // GUI
        addGuiButton("close", "Close");

        // Biomes
        addBiome(ModBiomes.ADOBE_DESERT, "Adobe Desert");
        addBiome(ModBiomes.SERPENTINITE_HILLS, "Serpentinite Hills");

        // Components
        addComponentSocket("gem_applying", "§8Applies:§r");
        addComponentSocket(EMPTY, "§oEmpty Socket§r");
        addComponentSocketDescription(EMPTY, "§oEmpty§r");
        addComponentSocket(ZOMBIE_PACIFYING, "§o§aPacifying§r");
        addComponentSocketDescription(ZOMBIE_PACIFYING, "§oNear Zombies stop attacking§r");
        addComponentSocket(GLIDER, "§o§5Glider§r");
        addComponentSocketDescription(GLIDER, "§oGives you the power of an Elytra§r");
        addComponentSocket(REMOVE_AI, "§o§cNumbness§r");
        addComponentSocketDescription(REMOVE_AI, "§oPermanently remove all AI from nearby monsters§r");
        addComponentSocket(GIVE_AI, "§o§fClarity§r");
        addComponentSocketDescription(GIVE_AI, "§oRe-enable all AI from nearby monsters§r");
    }

    public void addContainer(String containerId, String value){
        add("container.projectj." + containerId, value);
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

    public void addBiome(ResourceKey<Biome> biome, String name){
        add("biome.projectj." + biome.location().getPath(), name);
    }
    public void addComponentSocket(DataComponentType<?> componentType, String name){
       addComponentSocket(dataComponentName(componentType), name);
    }
    public void addComponentSocketDescription(DataComponentType<?> componentType, String name){
        addComponentSocketDescription(dataComponentName(componentType), name);
    }
    public <T> void addComponentSocket(DeferredHolder<DataComponentType<?>, DataComponentType<T>> componentType, String name){
        addComponentSocket(dataComponentName(componentType), name);
    }
    public <T> void addComponentSocketDescription(DeferredHolder<DataComponentType<?>, DataComponentType<T>> componentType, String name){
        addComponentSocketDescription(dataComponentName(componentType), name);
    }

    public void addComponentSocket(String componentType, String name){
        addComponent("sockets", componentType, name);
    }

    public void addComponentSocketDescription(String componentType, String name){
        addComponentDescription("sockets", componentType, name);
    }
    public void addComponent(String componentClass, String componentType, String name){
        add("components.projectj." + componentClass + "."  + componentType, name);
    }
    public void addComponentDescription(String componentClass, String componentType, String name){
        add("components.projectj." + componentClass + "."  + componentType + ".description", name);
    }

    String getName(ItemLike itemLike){
        return BuiltInRegistries.ITEM.getKey(itemLike.asItem()).getPath();
    }

}
