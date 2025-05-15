package com.mgmstudios.projectj.datagen;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.item.ModItems;
import com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen.QuestBookImage;
import com.mgmstudios.projectj.screen.custom.quest_book.QuestBookScreen.QuestBookTemplateType;
import com.mgmstudios.projectj.screen.custom.quest_book.templates.ContentsPageScreen;
import com.mgmstudios.projectj.util.ItemLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import org.objectweb.asm.tree.ModuleNode;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.mgmstudios.projectj.ProjectJ.MOD_ID;
import static com.mgmstudios.projectj.screen.custom.quest_book.QuestBookParser.*;

public class QuestBookPageProvider implements DataProvider {

    protected static final String SHORTCUT_BASICS = "basics";
    protected static final String SHORTCUT_TELEPORTATION = "teleportation";
    protected static final String SHORTCUT_SOCKETS = "sockets";
    protected static final String SHORTCUT_VOODOO = "voodoo";
    protected static final String SHORTCUT_ARMOR = "armor";
    protected static final String SHORTCUT_ANCIENT_ALTAR_RECIPES = "ancient-altar";
    protected static final String SHORTCUT_MAGNETS = "magnets";
    protected static final String SHORTCUT_FOODS = "foods";
    protected static final String SHORTCUT_MOBS = "mobs";

    private final PackOutput packOutput;
    List<JsonObject> pages = new ArrayList<>();

    public QuestBookPageProvider(PackOutput packOutput) {
        this.packOutput = packOutput;
    }

    public void generatePages(){
        Builder.create()
                .hideHomeButton()
                .setTemplate(QuestBookTemplateType.COVER)
                .setPageMessage("§f§lNo. 1§r")
                .save(pages);

        Builder.create()
                .hideHomeButton()
                .setTemplate(QuestBookTemplateType.CONTENTS_PAGE)
                .setTemplateSpacing(40)
                .addTemplateContentsPageEntry(new ContentsPageScreen.ContentsPageEntry(ModItems.JADE, "Intro", SHORTCUT_BASICS))
                .addTemplateContentsPageEntry(new ContentsPageScreen.ContentsPageEntry(ModItems.SUN_ARMOR_HELMET, "Armor", SHORTCUT_ARMOR))
                .addTemplateContentsPageEntry(new ContentsPageScreen.ContentsPageEntry(ModItems.MAIZE, "Food", SHORTCUT_FOODS))
                .addTemplateContentsPageEntry(new ContentsPageScreen.ContentsPageEntry(ModItems.RUNNER_SPAWN_EGG, "Mobs", SHORTCUT_MOBS))
                .addTemplateContentsPageEntry(new ContentsPageScreen.ContentsPageEntry(ModBlocks.ANCIENT_ALTAR, "Ancient Altar", SHORTCUT_ANCIENT_ALTAR_RECIPES))
                .addTemplateContentsPageEntry(new ContentsPageScreen.ContentsPageEntry(ModItems.PYRITE_MAGNET, "Magnets", SHORTCUT_MAGNETS))
                .addTemplateContentsPageEntry(new ContentsPageScreen.ContentsPageEntry(ModItems.VOODOO_CATCHER, "Voodoo", SHORTCUT_VOODOO))
                .addTemplateContentsPageEntry(new ContentsPageScreen.ContentsPageEntry(ModItems.TELEPORTATION_CORE, "§oSwoosh§r", SHORTCUT_TELEPORTATION))
                .addTemplateContentsPageEntry(new ContentsPageScreen.ContentsPageEntry(ModItems.PACIFYING_SOCKET, "Gems", SHORTCUT_SOCKETS))
                .setText("§lTable of Contents§r", true)
                .save(pages);

        // CHAPTER:Basics
        Builder.create()
                .setPageShortcut(SHORTCUT_BASICS)
                .setTemplate(QuestBookTemplateType.CHAPTER_COVER)
                .showPageMessage(false)
                .addImage(QuestBookImage.BASICS_CHAPTER)
                .setTemplateChapterTitle("§f§o§lThe Basics§r")
                .setText("§f§oWelcome to Project J. Let's cover some of the mod's basics items and elements!§r", false, true)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.ITEM_SHOWCASE)
                .addImage(new QuestBookImage(ModBlocks.MESQUITE_SAPLING, true))
                .setText("§nThe Adobe Desert§r\n\nThe Adobe Desert is a new biome added by ProjectJ. It is home to most blocks of the mod! Also home of the §oRunner§r and the §oLittle King§r.", true)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.ITEM_LIST)
                .defaultTemplateSpacing()
                .addImage(new QuestBookImage(ModBlocks.ADOBE_SAND))
                .addImage(new QuestBookImage(ModBlocks.PACKED_ADOBE))
                .setText("§nAdobe§r\n\nThe Adobe Desert is a large, dry desert. (Packed) Adobe is an firm but simple building material made from a mixture of water, sand, wheat and clay!", true)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.ITEM_LIST)
                .setTemplateSpacing(20)
                .addImage(new QuestBookImage(ModBlocks.MESQUITE_SAPLING))
                .addImage(new QuestBookImage(ModBlocks.MESQUITE_PLANKS))
                .addImage(new QuestBookImage(ModBlocks.MESQUITE_BENCH))
                .addImage(new QuestBookImage(ModBlocks.MESQUITE_LEAVES))
                .addImage(new QuestBookImage(ModBlocks.MESQUITE_BRAZIER))
                .addImage(new QuestBookImage(ModBlocks.MESQUITE_LOG))
                .setText("§nMesquite Tree§r\n\nThe Mesquite Tree can be found in the §6Adobe§r §6Desert§r biome and can be used to make a great variety of useful blocks! Above you can see some of the related blocks but there are many more!", true)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.ITEM_LIST)
                .defaultTemplateSpacing()
                .addImage(new QuestBookImage(ModItems.PYRITE_INGOT))
                .addImage(new QuestBookImage(ModItems.RAW_PYRITE))
                .addImage(new QuestBookImage(ModItems.SUN_ARMOR_HELMET))
                .addImage(new QuestBookImage(ModBlocks.PYRITE_ORE))
                .setText("§nPyrite§r\n \nGold? Not Quite... Pyrite, or also sometimes referred to as §oFool's Gold§r can be found on the Adobe Desert's surface. Can be used to craft various shiny items.", true)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.RECIPE_LIST)
                .defaultTemplateSpacing()
                .setPageMessage("and:    ")
                .addSecondaryImage(new QuestBookImage(ModItems.MAGNIFYING_GLASS, true))
                .addSecondaryImage(new QuestBookImage(ModBlocks.MAGNIFYING_GLASS_STAND))
                .addImage(new QuestBookImage(ModBlocks.PYRITE_BLOCK))
                .addImage(new QuestBookImage(ModBlocks.ADOBE_GLASS))
                .addImage(new QuestBookImage(ModItems.LIQUID_PYRITE_BUCKET))
                .addImage(new QuestBookImage(ModBlocks.ADOBE_SAND))
                .setText("§nMagnifying Glass§r\n\nThe Magnifying Glass is as mighty and powerful as its real-life counterpart. Use it on specific blocks during daytime to smelt them down!", true)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.ITEM_SHOWCASE)
                .addImage(new QuestBookImage(ModItems.FLESH, true))
                .setText("§nCooked Flesh§r\n\nNo one really enjoys piling up huge amounts of rotten flesh. That is why you can now cook it and turn it into a yummy steak.", true)
                .save(pages);


        Builder.create()
                .setTemplate(QuestBookTemplateType.ITEM_SHOWCASE)
                .addImage(new QuestBookImage(ModBlocks.SERPENTINITE_ROCK, true))
                .setText("§nSerpentinite Hills§r\n\nSerpentinite Hills are the second biome added by ProjectJ. They are usually around cold and tall biomes. I have also heard about some secret crystals, but maybe I misheard something...", true)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.ITEM_LIST)
                .defaultTemplateSpacing()
                .addImage(new QuestBookImage(ModBlocks.SERPENTINITE_PILLAR))
                .addImage(new QuestBookImage(ModBlocks.COBBLED_SERPENTINITE))
                .addImage(new QuestBookImage(ModBlocks.SERPENTINITE_BENCH))
                .addImage(new QuestBookImage(ModBlocks.SERPENTINITE_ROCK))
                .setText("§nSerpentinite§r\n\nThis green rock spawns naturally below the Adobe layer in its desert. If you dig long enough you might find some left over relics! Easy to work with using a stone cutter!", true)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.ITEM_LIST)
                .defaultTemplateSpacing()
                .addImage(new QuestBookImage(ModBlocks.DEEPSLATE_JADE_ORE))
                .addImage(new QuestBookImage(ModBlocks.JADE_ORE))
                .addImage(new QuestBookImage(ModBlocks.SERPENTINITE_JADE_ORE))
                .setText("§nJade Ores§r\n\nJade is the building block of Project J. It is used for most recipes, so get digging! Most commonly found in Serpentinite but also around lush caves.", true)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.ITEM_LIST)
                .defaultTemplateSpacing()
                .addImage(new QuestBookImage(ModBlocks.JADE_BLOCK))
                .addImage(new QuestBookImage(ModItems.RAW_JADE))
                .addImage(new QuestBookImage(ModItems.JADE_HELMET))
                .addImage(new QuestBookImage(ModItems.JADE))
                .setText("§nJade Items§r\n\nJade can also be used to craft a variety of blocks and items. Including Armor!", true)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.ITEM_LIST)
                .setTemplateSpacing(30)
                .addImage(new QuestBookImage(ModItems.OBSIDIAN_ARROW, true))
                .addImage(new QuestBookImage(ModItems.OBSIDIAN_TOOTH, true))
                .setText("§nObsidian Tooth§r\n\nObsidian Teeth can be found when mining serpentinite. However, you can also craft them using a stone cutter and some obsidian.", true)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.ITEM_LIST)
                .setTemplateSpacing(30)
                .addImage(new QuestBookImage(ModBlocks.HOLLOW_MESQUITE_LOG, true))
                .addImage(new QuestBookImage(ModItems.HATCHET, true))
                .setText("§nHatchet§r\n\nThis little axe lookin fella can hollow out stripped logs.", true)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.ITEM_SHOWCASE)
                .addImage(new QuestBookImage(ModItems.DEATH_WHISTLE, true))
                .setText("§nDeath Whistle§r\n\nReally loud and really annoying. Probably scares you more than anyone else.", true)
                .save(pages);

        // CHAPTER:Foods
        Builder.create()
                .setPageShortcut(SHORTCUT_FOODS)
                .setTemplate(QuestBookTemplateType.CHAPTER_COVER)
                .showPageMessage(false)
                .addImage(QuestBookImage.FOODS_CHAPTER)
                .setTemplateChapterTitle("§f§o§lFood & Crops§r")
                .setText("§f§oYummy yummy in my tummy!§r", false, true)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.ITEM_LIST)
                .defaultTemplateSpacing()
                .addImage(new QuestBookImage(ModItems.CHILI))
                .addImage(new QuestBookImage(ModItems.MAIZE_SEEDS))
                .addImage(new QuestBookImage(ModItems.CHILI_SEEDS))
                .addImage(new QuestBookImage(ModItems.MAIZE))
                .setText("§nMaize & Chili§r\n\nTwo new yummy crops. But be careful when eating to much chili, it might hurt your stomach! Can be found when trading with the Little King.", true)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.ITEM_LIST)
                .setTemplateSpacing(30)
                .addImage(new QuestBookImage(ModItems.CHILI_CON_CARNE))
                .addImage(new QuestBookImage(ModItems.MAIZE_MASH))
                .setText("§nBowls§r\n\nClassic aztec meals. The maize mash is very simple, yet nutritious recipe. Chili con carne might even be more popular today then during the Aztec's times.", true)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.ITEM_SHOWCASE)
                .addImage(new QuestBookImage(ModBlocks.METATE, true))
                .setText("§nMetate and Mano§r\n\nThis primitive pestle and mortar variant allows you to grind down any crop into its seeds.", true)
                .save(pages);

        // CHAPTER:Mobs
        Builder.create()
                .setPageShortcut(SHORTCUT_MOBS)
                .setTemplate(QuestBookTemplateType.CHAPTER_COVER)
                .showPageMessage(false)
                .addImage(QuestBookImage.MOBS_CHAPTER)
                .setTemplateChapterTitle("§f§o§lMobs & Entities§r")
                .setText("§f§oGrrrrrr...Huh?§r", false, true)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.ITEM_SHOWCASE)
                .addImage(new QuestBookImage(ModItems.RUNNER_SPAWN_EGG, true))
                .setText("§nRunner§r\n\nThis Adobe Desert version of the Zombie is a bit faster than his brothers. I would also not touch him or you'll be wobbling for a while.", true)
                .save(pages);


        // CHAPTER:Armor
        Builder.create()
                .setPageShortcut(SHORTCUT_ARMOR)
                .setTemplate(QuestBookTemplateType.CHAPTER_COVER)
                .showPageMessage(false)
                .addImage(QuestBookImage.ARMOR_CHAPTER)
                .setTemplateChapterTitle("§f§o§lArmor§r")
                .setText("§f§oLooking shiny out there. Keep it up!§r", false, true)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.ITEM_LIST)
                .defaultTemplateSpacing()
                .addImage(new QuestBookImage(ModItems.JADE_LEGGINGS))
                .addImage(new QuestBookImage(ModItems.JADE_CHESTPLATE))
                .addImage(new QuestBookImage(ModItems.JADE_BOOTS))
                .addImage(new QuestBookImage(ModItems.JADE_HELMET))
                .setText("§nJade Armor§r\n\nJade Armor is crafted using §aJade§r. That's it for now :)", true)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.ITEM_LIST)
                .defaultTemplateSpacing()
                .addImage(new QuestBookImage(ModItems.SUN_ARMOR_LEGGINGS))
                .addImage(new QuestBookImage(ModItems.SUN_ARMOR_CHESTPLATE))
                .addImage(new QuestBookImage(ModItems.SUN_ARMOR_BOOTS))
                .addImage(new QuestBookImage(ModItems.SUN_ARMOR_HELMET))
                .setText("§nSun Armor§r\n\nSun Armor is crafted using §6Pyrite§r and although very tough and resistant, similar to it's fancier counterpart, it is very brittle.", true) .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.ITEM_LIST)
                .defaultTemplateSpacing()
                .addImage(new QuestBookImage(ModItems.AWAKENED_SUN_ARMOR_LEGGINGS))
                .addImage(new QuestBookImage(ModItems.AWAKENED_SUN_ARMOR_CHESTPLATE))
                .addImage(new QuestBookImage(ModItems.AWAKENED_SUN_ARMOR_BOOTS))
                .addImage(new QuestBookImage(ModItems.AWAKENED_SUN_ARMOR_HELMET))
                .setText("§nAwakened Sun Armor§r\n\nForged using the power of the Nether and End, the Awakened Sun Armor applies its full effect when worn as a full set. Crafted in the Ancient Altar.", true)
                .save(pages);

        // CHAPTER:Ancient Altar
        Builder.create()
                .setPageShortcut(SHORTCUT_ANCIENT_ALTAR_RECIPES)
                .setTemplate(QuestBookTemplateType.CHAPTER_COVER)
                .showPageMessage(false)
                .addImage(QuestBookImage.ANCIENT_ALTAR_RECIPES_CHAPTER)
                .setTemplateChapterTitle("§f§o§lAncient Altar & Crafting§r")
                .setText("§f§oPowerful sacrifices using Blood and Pyrite§r", false, true)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.ITEM_SHOWCASE)
                .addImage(new QuestBookImage(ModItems.SACRIFICIAL_DAGGER))
                .setText("§nSacrificial Dagger§r\n\nThis holy dagger is used to activate every Ancient Altar recipe. Right click the altar to start a valid recipe, or right click the air to perform a self sacrifice.", true)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.RECIPE_LIST)
                .setTemplateSpacing(30)
                .setInLocationMessage()
                .addSecondaryImage(new QuestBookImage(ModItems.CRUDE_SACRIFICE_BOWL,true))
                .addSecondaryImage(new QuestBookImage(ModBlocks.ANCIENT_ALTAR))
                .addImage(new QuestBookImage(ModItems.LIQUID_PYRITE_BUCKET))
                .addImage(new QuestBookImage(Items.BOWL))
                .setText("§nCrude Sacrificial Bowl§r\n\nThe Basis of many more things. Allows you to fill it with a bloody sacrifice when standing close to an ancient altar.", true)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.RECIPE_LIST)
                .setTemplateSpacing(30)
                .setPageMessage("next to:    ")
                .addSecondaryImage(new QuestBookImage(ModItems.FILLED_CRUDE_SACRIFICE_BOWL, true))
                .addSecondaryImage(new QuestBookImage(ModBlocks.ANCIENT_ALTAR, false))
                .addImage(new QuestBookImage(ModItems.SACRIFICIAL_DAGGER, false))
                .addImage(new QuestBookImage(ModItems.CRUDE_SACRIFICE_BOWL, false))
                .setText("§nFilled Crude Sacrificial Bowl§r\n\nPerform a self sacrifice, or sacrifice an animal near an Ancient Altar to fill its bowl.", false, false)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.RECIPE_LIST)
                .setTemplateSpacing(30)
                .setInLocationMessage()
                .addSecondaryImage(new QuestBookImage(ModItems.CHIMALLI_SHIELD,true))
                .addSecondaryImage(new QuestBookImage(ModBlocks.ANCIENT_ALTAR))
                .addImage(new QuestBookImage(Items.SHIELD))
                .addImage(new QuestBookImage(ModItems.LIQUID_PYRITE_BUCKET))
                .addImage(new QuestBookImage(ModItems.FILLED_CRUDE_SACRIFICE_BOWL))
                .setText("§nChīmalli§r\n\nPowerful shield capable of blocking projectiles and hits.", true)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.RECIPE_LIST)
                .setTemplateSpacing(20)
                .setInLocationMessage()
                .addSecondaryImage(new QuestBookImage(ModItems.MACUAHUITL,true))
                .addSecondaryImage(new QuestBookImage(ModBlocks.ANCIENT_ALTAR))
                .addImage(new QuestBookImage(ModItems.OBSIDIAN_TOOTH,4))
                .addImage(new QuestBookImage(Items.STICK))
                .addImage(new QuestBookImage(ModItems.SERPENTINITE_ROD))
                .addImage(new QuestBookImage(ModItems.JADE))
                .addImage(new QuestBookImage(ModItems.LIQUID_PYRITE_BUCKET))
                .setText("§nMacuahuitl§r\n\nStrong weapon which can handle almost every type of block.", true)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.RECIPE_LIST)
                .setTemplateSpacing(30)
                .setInLocationMessage()
                .addSecondaryImage(new QuestBookImage(ModItems.TELEPORTATION_CORE,true))
                .addSecondaryImage(new QuestBookImage(ModBlocks.ANCIENT_ALTAR))
                .addImage(new QuestBookImage(ModItems.JADE,4 ))
                .addImage(new QuestBookImage(Items.ENDER_PEARL, 2))
                .addImage(new QuestBookImage(Items.WIND_CHARGE))
                .setText("§nTeleportation Core§r\n\nThe Teleportation Core opens the door to a whole lot of blocks and items allowing you to travel faster.", true)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.RECIPE_LIST)
                .defaultTemplateSpacing()
                .setInLocationMessage()
                .addSecondaryImage(new QuestBookImage(ModItems.AWAKENED_SUN_ARMOR_HELMET,true))
                .addSecondaryImage(new QuestBookImage(ModBlocks.ANCIENT_ALTAR))
                .addImage(new QuestBookImage(ModItems.SUN_ARMOR_HELMET))
                .addImage(new QuestBookImage(ModItems.QUETZAL_FEATHER, 2))
                .addImage(new QuestBookImage(Items.FERMENTED_SPIDER_EYE))
                .addImage(new QuestBookImage(ModItems.LIQUID_PYRITE_BUCKET))
                .addImage(new QuestBookImage(ModItems.FILLED_CRUDE_SACRIFICE_BOWL))
                .setText("§nAwakened Sun Crown§r\n\nYour hair might catch on fire when wearing.", true)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.RECIPE_LIST)
                .defaultTemplateSpacing()
                .setInLocationMessage()
                .addSecondaryImage(new QuestBookImage(ModItems.AWAKENED_SUN_ARMOR_CHESTPLATE,true))
                .addSecondaryImage(new QuestBookImage(ModBlocks.ANCIENT_ALTAR))
                .addImage(new QuestBookImage(ModItems.SUN_ARMOR_CHESTPLATE))
                .addImage(new QuestBookImage(ModItems.QUETZAL_FEATHER, 3))
                .addImage(new QuestBookImage(Items.NETHERITE_INGOT))
                .addImage(new QuestBookImage(Items.ELYTRA))
                .addImage(new QuestBookImage(ModItems.LIQUID_PYRITE_BUCKET))
                .addImage(new QuestBookImage(ModItems.FILLED_CRUDE_SACRIFICE_BOWL))
                .setText("§nAwakened Sun Chestplate§r\n\nYou look hot.", false)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.RECIPE_LIST)
                .defaultTemplateSpacing()
                .setInLocationMessage()
                .addSecondaryImage(new QuestBookImage(ModItems.AWAKENED_SUN_ARMOR_LEGGINGS,true))
                .addSecondaryImage(new QuestBookImage(ModBlocks.ANCIENT_ALTAR))
                .addImage(new QuestBookImage(ModItems.SUN_ARMOR_LEGGINGS))
                .addImage(new QuestBookImage(ModItems.QUETZAL_FEATHER, 3))
                .addImage(new QuestBookImage(Items.MAGMA_CREAM, 2))
                .addImage(new QuestBookImage(ModItems.LIQUID_PYRITE_BUCKET))
                .addImage(new QuestBookImage(ModItems.FILLED_CRUDE_SACRIFICE_BOWL))
                .setText("§nAwakened Sun Leggings§r\n\nShiny legs you got there!", false)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.RECIPE_LIST)
                .defaultTemplateSpacing()
                .setInLocationMessage()
                .addSecondaryImage(new QuestBookImage(ModItems.AWAKENED_SUN_ARMOR_BOOTS,true))
                .addSecondaryImage(new QuestBookImage(ModBlocks.ANCIENT_ALTAR))
                .addImage(new QuestBookImage(ModItems.SUN_ARMOR_BOOTS))
                .addImage(new QuestBookImage(ModItems.QUETZAL_FEATHER, 2))
                .addImage(new QuestBookImage(Items.SLIME_BALL, 2))
                .addImage(new QuestBookImage(ModItems.LIQUID_PYRITE_BUCKET))
                .addImage(new QuestBookImage(ModItems.FILLED_CRUDE_SACRIFICE_BOWL))
                .setText("§nAwakened Sun Boots§r\n\nThe floor is lava!", true)
                .save(pages);

        // CHAPTER:Magnets
        Builder.create()
                .setPageShortcut(SHORTCUT_MAGNETS)
                .setTemplate(QuestBookTemplateType.CHAPTER_COVER)
                .showPageMessage(false)
                .addImage(QuestBookImage.MAGNETS_CHAPTER)
                .setTemplateChapterTitle("§f§o§lMagnets§r")
                .setText("§f§oOur connection seems to get stronger ;)§r", false, true)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.ITEM_LIST)
                .setTemplateSpacing(30)
                .addImage(new QuestBookImage(ModItems.PYRITE_MAGNET, true))
                .addImage(new QuestBookImage(ModItems.JADE_MAGNET, true))
                .setText("§nMagnets§r\n\nMagnets are harness the magnetism to attract nearby items. §oTheir materials determine their radius and strength§r", true)
                .save(pages);

        // CHAPTER:Voodoo
        Builder.create()
                .setPageShortcut(SHORTCUT_VOODOO)
                .setTemplate(QuestBookTemplateType.CHAPTER_COVER)
                .showPageMessage(false)
                .addImage(QuestBookImage.VOODOO_CHAPTER)
                .setTemplateChapterTitle("§f§o§lVoodoo§r")
                .setText("§f§oIs it a totem? Is it a plane? No! It's a retextured totem.§r", false, true)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.ITEM_SHOWCASE)
                .addImage(new QuestBookImage(ModItems.VOODOO_CATCHER, true))
                .setText("§nVoodoo Catcher§r\n\nThe Voodoo Catcher is a magical wand used in catching some friendly mobs running around your world. Take a look at the following pages to learn more!", true)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.ITEM_LIST)
                .addImage(new QuestBookImage(ModItems.LITTLE_MAN_VOODOO, true))
                .setText("§nLittle Man§r\nThe Little Man Voodoo is a power totem which can be crafted. It pacifies zombies around you. Putting it onto a empty statue block, it can permanently scare away those pesky zomberts!", true)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.ITEM_SHOWCASE)
                .addImage(new QuestBookImage(ModItems.LITTLE_KING_VOODOO, true))
                .setText("§nLittle King§r\n\nThe Little King Voodoo looks nice in your offhand. However, you might manage to find him roaming around in the Adobe Desert!", true)
                .save(pages);


        // CHAPTER:Teleportation
        Builder.create()
                .setPageShortcut(SHORTCUT_TELEPORTATION)
                .setTemplate(QuestBookTemplateType.CHAPTER_COVER)
                .showPageMessage(false)
                .addImage(QuestBookImage.TELEPORTATION_CHAPTER)
                .setTemplateChapterTitle("§f§o§lTeleportation§r")
                .setText("§f§oSwoosh!§r", false, true)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.ITEM_LIST)
                .setTemplateSpacing(30)
                .setImages(new QuestBookImage(ModItems.TELEPORTATION_KEY))
                .addImage(new QuestBookImage(ModItems.TELEPORTATION_CORE))
                .addImage(new QuestBookImage(ModBlocks.TELEPORTATION_PAD))
                .setText("Teleportation is a powerful force, harnessed only by the most elite mages and technicians. The Teleportation Core lays its foundation. Have a look at the Ancient Altar recipes to find out how to craft it!", false)
                .save(pages);

        // CHAPTER:Gems
        Builder.create()
                .setPageShortcut(SHORTCUT_SOCKETS)
                .setTemplate(QuestBookTemplateType.CHAPTER_COVER)
                .showPageMessage(false)
                .addImage(QuestBookImage.SOCKETS_CHAPTER)
                .setTemplateChapterTitle("§f§o§lSockets & Gems§r")
                .setText("§f§oCustomization. FOREVER!§r", false, true)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.ITEM_SHOWCASE)
                .addImage(new QuestBookImage(ModBlocks.SOCKET_WORKBENCH, true))
                .setText("§nSocket Workbench§r\nThe Socket Workbench is the crafting station for all of you gem application activities!", true)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.ITEM_LIST)
                .setTemplateSpacing(22)
                .setImages(new QuestBookImage(ModItems.PACIFYING_SOCKET))
                .addImage(new QuestBookImage(ModItems.GIVE_AI_SOCKET))
                .addImage(new QuestBookImage(ModItems.REMOVE_AI_SOCKET))
                .addImage(new QuestBookImage(ModItems.GLIDER_SOCKET))
                .addImage(new QuestBookImage(ModItems.EMPTY_SOCKET))
                .setText("§nGems§r\nGems modify your gear with various effects and abilities! Every effect has a maximum stack limit the stack limit §8§o[x]§r. However this limit can also be overridden and the gems themselves can also be improved! (turn)", true)
                .save(pages);

        Builder.create()
                .setTemplate(QuestBookTemplateType.DOUBLE_ITEM_SHOWCASE)
                .setTemplateSpacing(30)
                .addImage(new QuestBookImage(ModItems.EVERYTHING_SOCKET, false))
                .addImage(new QuestBookImage(ModItems.TOPAZ_GEM, true))
                .setText("§nTopaz Gem§r\nThis special gem turns your gem or socket §6§o✦Legendary✦§r, allowing it to be additively applied to any supported socket item.Like an Universal Gem for example!", true)
                .save(pages);
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput){
        Path basePath = packOutput.getOutputFolder()
                .resolve("assets/" + MOD_ID + "/quest_book/pages/");

        generatePages();

        List<CompletableFuture<?>> futures = new ArrayList<>();
        HashMap<String, Integer> shortcutsMap = new HashMap<>();
        Gson gson = new Gson();
        for (int index = 0; index < pages.size(); index++){
            if (pages.get(index).has(KEY_PAGE_SHORTCUT))
                shortcutsMap.put(pages.get(index).get(KEY_PAGE_SHORTCUT).getAsString(), index);

            Path path = basePath.resolve("page_" + index + ".json");
            JsonObject page = pages.get(index);
            futures.add(DataProvider.saveStable(cachedOutput, page, path));
        }
        Path shortcutsPath = basePath.resolve("shortcuts.json");
        JsonElement jsonElement = gson.toJsonTree(shortcutsMap);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        jsonObject.addProperty(KEY_TOTAL_PAGES, pages.size());
        futures.add(DataProvider.saveStable(cachedOutput, jsonObject, shortcutsPath));

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }

    @Override
    public String getName() {
        return "Quest Book Builder: " + MOD_ID;
    }

    public static class Builder{

        private JsonObject json = new JsonObject();

        static Builder create(){
            return new Builder();
        }

        Builder showPageMessage(boolean value){
            json.addProperty(KEY_SHOW_PAGE_MSG, value);
            return this;
        }

        Builder showHomeButton(boolean value){
            json.addProperty(KEY_SHOW_HOME_BUTTON, value);
            return this;
        }

        Builder hideHomeButton(){
            return this.showHomeButton(false);
        }

        Builder setPageMessage(String message){
            JsonObject messageObject = new JsonObject();
            messageObject.addProperty(KEY_MESSAGE, message);
            json.add(KEY_PAGE_MSG, messageObject);
            return this;
        }

        Builder setInLocationMessage(){
            return this.setPageMessage("in:    ");
        }

        Builder setPageMessageKey(String key){
            JsonObject keyObject = new JsonObject();
            keyObject.addProperty(KEY_KEY, key);
            json.add(KEY_PAGE_MSG, keyObject);
            return this;
        }

        Builder setEmpty(boolean value){
            json.addProperty(KEY_EMPTY, value);
            return this;
        }

        Builder setPageShortcut(String shortcut){
            json.addProperty(KEY_PAGE_SHORTCUT, shortcut);
            return this;
        }

        Builder setTemplate(QuestBookTemplateType type){
            JsonObject templateObject = new JsonObject();
            templateObject.addProperty(KEY_NAME, type.getDisplayName());
            json.add(KEY_TEMPLATE, templateObject);
            return this;
        }

        Builder setTemplateSpacing(int spacing){
            JsonObject templateObject = json.getAsJsonObject(KEY_TEMPLATE);
            if (templateObject != null){
                templateObject.addProperty(KEY_SPACING, spacing);
            }
            return this;
        }

        Builder defaultTemplateSpacing(){
            return setTemplateSpacing(20);
        }

        Builder addSecondaryImage(QuestBookImage recipeResult){
            JsonObject templateObject = json.getAsJsonObject(KEY_TEMPLATE);
            JsonArray recipeResults;
            if (templateObject.has(KEY_SECONDARY_IMAGES))
                recipeResults = templateObject.getAsJsonArray(KEY_SECONDARY_IMAGES);
            else{
                recipeResults = new JsonArray();
                templateObject.add(KEY_SECONDARY_IMAGES, recipeResults);
            }
            addImage(recipeResult, recipeResults);
            return this;
        }

        Builder setTemplateChapterTitle(String chapterTitle){
            JsonObject templateObject = json.getAsJsonObject(KEY_TEMPLATE);
            if (templateObject != null){
                templateObject.addProperty(KEY_CHAPTER_TITLE, chapterTitle);
            }
            return this;
        }

        Builder setTemplateShowFuel(boolean showFuel){
            JsonObject templateObject = json.getAsJsonObject(KEY_TEMPLATE);
            if (templateObject != null){
                templateObject.addProperty(KEY_SHOW_FUEL, showFuel);
            }
            return this;
        }

        Builder setTemplateContentsPageEntries(List<ContentsPageScreen.ContentsPageEntry> entries){
            for (ContentsPageScreen.ContentsPageEntry entry : entries)
                addTemplateContentsPageEntry(entry);
            return this;
        }

        Builder addTemplateContentsPageEntry(ContentsPageScreen.ContentsPageEntry entry){
            JsonObject entryObject = new JsonObject();

            ResourceLocation resourceLocation = ItemLookup.getResourceLocation(entry.displayItem());
            entryObject.addProperty(KEY_ITEM, resourceLocation.getNamespace() + ":" + resourceLocation.getPath());
            entryObject.addProperty(KEY_DESCRIPTION, entry.displayText());
            if (entry.connectedPage() != -1)
                entryObject.addProperty(KEY_CONNECTED_PAGE, entry.connectedPage());
            else
                entryObject.addProperty(KEY_CONNECTED_PAGE, entry.connectedShortcut());

            JsonArray objectsArray;
            JsonObject templateObject = json.getAsJsonObject(KEY_TEMPLATE);
            if (templateObject.has(KEY_OBJECTS))
                objectsArray = templateObject.getAsJsonArray(KEY_OBJECTS);
            else{
                objectsArray = new JsonArray();
                templateObject.add(KEY_OBJECTS, objectsArray);
            }
            objectsArray.add(entryObject);
            return this;
        }
        Builder addImage(QuestBookImage image){
            JsonArray imagesArray;
            if (json.has(KEY_IMAGES))
                imagesArray = json.getAsJsonArray(KEY_IMAGES);
            else{
                imagesArray = new JsonArray();
                json.add(KEY_IMAGES, imagesArray);
            }

            addImage(image, imagesArray);
            return this;
        }

        Builder addImage(QuestBookImage image, JsonArray array){
            JsonObject imageObject = new JsonObject();
            if (image.hasShortHand())
                imageObject.addProperty(KEY_IMAGE, image.shorthand());
            else
                imageObject.addProperty(KEY_IMAGE, image.resourceLocation().getNamespace() + ":" + image.resourceLocation().getPath());
            if (image.count() > 1)
                imageObject.addProperty(KEY_COUNT, image.count());
            if (image.showBorder())
                imageObject.addProperty(KEY_SHOW_BORDER, true);
            if (!image.type().equals(QuestBookImage.Type.ITEM))
                imageObject.addProperty(KEY_TYPE, image.type().toString().toLowerCase());

            array.add(imageObject);
            return this;
        }

        Builder setImages(QuestBookImage ... images){
            for (QuestBookImage i : images){
              addImage(i);
            }
            return this;
        }

        Builder setText(String text, boolean showTitle, boolean alignCenter){
            JsonObject textObject = new JsonObject();
            textObject.addProperty(KEY_CONTENT, text);
            textObject.addProperty(KEY_SHOW_TITLE, showTitle);
            textObject.addProperty(KEY_ALIGN_CENTER, alignCenter);
            json.add(KEY_TEXT, textObject);
            return this;
        }
        Builder setText(String text, boolean showTitle){
            return setText(text, showTitle, false);
        }

        Builder setText(String text){
            return setText(text, false, false);
        }

        void save(List<JsonObject> pages){
            pages.add(this.json);
        }
    }
}
