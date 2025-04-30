package com.mgmstudios.projectj.datagen;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.entity.ModEntities;
import com.mgmstudios.projectj.entity.custom.LittleManEntity;
import com.mgmstudios.projectj.item.ModItems;
import com.mgmstudios.projectj.loot.ModLootTables;
import com.mgmstudios.projectj.worldgen.ModBiomes;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.data.advancements.packs.VanillaAdventureAdvancements;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class ModAdvancementSubProvider implements AdvancementSubProvider {


    protected static Advancement.Builder addBiomes(Advancement.Builder builder, HolderLookup.Provider levelRegistry, List<ResourceKey<Biome>> biomes) {
        HolderGetter<Biome> holdergetter = levelRegistry.lookupOrThrow(Registries.BIOME);

        for(ResourceKey<Biome> resourcekey : biomes) {
            builder.addCriterion(resourcekey.location().toString(), net.minecraft.advancements.critereon.PlayerTrigger.TriggerInstance.located(net.minecraft.advancements.critereon.LocationPredicate.Builder.inBiome(holdergetter.getOrThrow(resourcekey))));
        }

        return builder;
    }

    @Override
    public void generate(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer) {

        HolderGetter<Item> holdergetter = provider.lookupOrThrow(Registries.ITEM);

        AdvancementHolder root = Advancement.Builder.advancement()
                .display(
                        ModItems.QUEST_BOOK,
                        Component.translatable(createTitleString("root")),
                        Component.translatable(createDescriptionString("root")),
                        ResourceLocation.withDefaultNamespace("textures/gui/advancements/backgrounds/stone.png"),
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion(
                        "tick",
                        CriteriaTriggers.TICK.createCriterion(
                                new PlayerTrigger.TriggerInstance(Optional.empty())
                        )
                )
                .rewards(
                        AdvancementRewards.Builder.loot(ModLootTables.GRANT_QUEST_BOOK.getKey())
                )
                .save(consumer, createSaveString("story", "root"));

        /*
        var visitAdobeDesert = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        ModBlocks.ADOBE_SAND,
                        Component.translatable(createTitleString("visit_adobe_desert")),
                        Component.translatable(createDescriptionString("visit_adobe_desert")),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                );
        addBiomes(visitAdobeDesert, provider, List.of(ModBiomes.ADOBE_DESERT)).save(consumer, createSaveString("story", "visit_adobe_desert"));
         */

        AdvancementHolder getJade = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        ModItems.RAW_JADE,
                        Component.translatable(createTitleString("get_jade")),
                        Component.translatable(createDescriptionString("get_jade")),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_jade", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.RAW_JADE))
                .save(consumer, createSaveString("story", "get_jade"));

        AdvancementHolder botanyPots = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        ModBlocks.BOTANY_POT,
                        Component.translatable(createTitleString("botany_pot")),
                        Component.translatable(createDescriptionString("botany_pot")),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("botany_pot", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.BOTANY_POT))
                .save(consumer, createSaveString("story", "botany_pot"));

        AdvancementHolder magnifyingGlass = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        ModItems.MAGNIFYING_GLASS,
                        Component.translatable(createTitleString("magnifying_glass")),
                        Component.translatable(createDescriptionString("magnifying_glass")),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("magnifying_glass", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.MAGNIFYING_GLASS))
                .save(consumer, createSaveString("story", "magnifying_glass"));

        AdvancementHolder magnifyingGlassStand = Advancement.Builder.advancement()
                .parent(magnifyingGlass)
                .display(
                        ModBlocks.MAGNIFYING_GLASS_STAND,
                        Component.translatable(createTitleString("magnifying_glass_stand")),
                        Component.translatable(createDescriptionString("magnifying_glass_stand")),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("magnifying_glass_stand", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.MAGNIFYING_GLASS_STAND))
                .save(consumer, createSaveString("story", "magnifying_glass_stand"));

        AdvancementHolder liquidPyriteBucket = Advancement.Builder.advancement()
                .parent(magnifyingGlass)
                .display(
                        ModItems.LIQUID_PYRITE_BUCKET,
                        Component.translatable(createTitleString("liquid_pyrite_bucket")),
                        Component.translatable(createDescriptionString("liquid_pyrite_bucket")),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("liquid_pyrite_bucket", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.LIQUID_PYRITE_BUCKET))
                .save(consumer, createSaveString("story", "liquid_pyrite_bucket"));

        AdvancementHolder pyrite = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        ModItems.RAW_PYRITE,
                        Component.translatable(createTitleString("pyrite")),
                        Component.translatable(createDescriptionString("pyrite")),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("pyrite", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.RAW_PYRITE))
                .save(consumer, createSaveString("story", "pyrite"));

        AdvancementHolder ancientAltar = Advancement.Builder.advancement()
                .parent(pyrite)
                .display(
                        ModBlocks.ANCIENT_ALTAR,
                        Component.translatable(createTitleString("ancient_altar")),
                        Component.translatable(createDescriptionString("ancient_altar")),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("ancient_altar", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.ANCIENT_ALTAR))
                .save(consumer, createSaveString("story", "ancient_altar"));

        AdvancementHolder crudeSacrificialBowl = Advancement.Builder.advancement()
                .parent(liquidPyriteBucket)
                .display(
                        ModItems.CRUDE_SACRIFICE_BOWL,
                        Component.translatable(createTitleString("crude_sacrificial_bowl")),
                        Component.translatable(createDescriptionString("crude_sacrificial_bowl")),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                       false
                )
                .addCriterion("crude_sacrificial_bowl", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CRUDE_SACRIFICE_BOWL))
                .save(consumer, createSaveString("story", "crude_sacrificial_bowl"));

        AdvancementHolder sacrificialDagger = Advancement.Builder.advancement()
                .parent(ancientAltar)
                .display(
                        ModItems.SACRIFICIAL_DAGGER,
                        Component.translatable(createTitleString("sacrificial_dagger")),
                        Component.translatable(createDescriptionString("sacrificial_dagger")),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                      false
                )
                .addCriterion("sacrificial_dagger", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SACRIFICIAL_DAGGER))
                .save(consumer, createSaveString("story", "sacrificial_dagger"));


        AdvancementHolder filledCrudeSacrificialBowl = Advancement.Builder.advancement()
                .parent(sacrificialDagger)
                .display(
                        ModItems.FILLED_CRUDE_SACRIFICE_BOWL,
                        Component.translatable(createTitleString("filled_crude_sacrificial_bowl")),
                        Component.translatable(createDescriptionString("filled_crude_sacrificial_bowl")),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                      false
                )
                .addCriterion("filled_crude_sacrificial_bowl", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.FILLED_CRUDE_SACRIFICE_BOWL))
                .save(consumer, createSaveString("story", "filled_crude_sacrificial_bowl"));
    }

    public static String createTitleString(String name){
        return "advancements.projectj.story."+name+".title";
    }
    public static String createDescriptionString(String name){
        return "advancements.projectj.story."+name+".description";
    }

    public static String createSaveString(String directory, String name){
        return "projectj/" + directory + "/" +name;
    }


}
