package com.mgmstudios.projectj.datagen;

import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.entity.ModEntities;
import com.mgmstudios.projectj.entity.custom.LittleManEntity;
import com.mgmstudios.projectj.item.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.EntityTypePredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.TradeTrigger;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

public class ModAdvancementSubProvider implements AdvancementSubProvider {

    @Override
    public void generate(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer) {

        HolderGetter<Item> holdergetter = provider.lookupOrThrow(Registries.ITEM);

        AdvancementHolder root = Advancement.Builder.advancement()
                .display(
                        ModBlocks.COBBLED_SERPENTINITE,
                        Component.translatable(createTitleString("root")),
                        Component.translatable(createDescriptionString("root")),
                        ResourceLocation.withDefaultNamespace("textures/gui/advancements/backgrounds/stone.png"),
                        AdvancementType.TASK,
                        false,
                        false,
                        false
                )
                .addCriterion("cobbled_serpentinite", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.COBBLED_SERPENTINITE))
                .save(consumer, createSaveString("story", "root"));

        AdvancementHolder getJade = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        ModItems.JADE,
                        Component.translatable(createTitleString("get_jade")),
                        Component.translatable(createDescriptionString("get_jade")),
                        null,
                        AdvancementType.GOAL,
                        true,
                        true,
                        false
                )
                .addCriterion("get_jade", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.JADE))
                .save(consumer, createSaveString("story", "get_jade"));

        AdvancementHolder botanyPots = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        ModBlocks.BOTANY_POT,
                        Component.translatable(createTitleString("botany_pot")),
                        Component.translatable(createDescriptionString("botany_pot")),
                        null,
                        AdvancementType.GOAL,
                        true,
                        true,
                        false
                )
                .addCriterion("botany_pot", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.BOTANY_POT))
                .save(consumer, createSaveString("story", "botany_pot"));
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
