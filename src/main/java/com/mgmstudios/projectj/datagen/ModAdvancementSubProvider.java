package com.mgmstudios.projectj.datagen;

import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.item.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import java.util.function.Consumer;

public class ModAdvancementSubProvider implements AdvancementSubProvider {

    @Override
    public void generate(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer) {

        HolderGetter<Item> holdergetter = provider.lookupOrThrow(Registries.ITEM);

        AdvancementHolder root = Advancement.Builder.advancement()
                .display(
                        ModItems.JADE,
                        Component.translatable(createTitleString("craft_ancient_altar")),
                        Component.translatable(createDescriptionString("craft_ancient_altar")),
                        ResourceLocation.withDefaultNamespace("textures/gui/advancements/backgrounds/stone.png"),
                        AdvancementType.GOAL,
                        true,
                        true,
                        false
                )
                .addCriterion("get_ancient_altar", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.JADE))
                .save(consumer, "story/projectj/craft_ancient_altar");
    }

    private String createTitleString(String name){
        return "advancements.projectj.story."+name+".title";
    }
    private String createDescriptionString(String name){
        return "advancements.projectj.story."+name+".description";
    }

    private String createSaveString(String directory, String name){
        return "projectj/" + directory + "/" +name;
    }
}
