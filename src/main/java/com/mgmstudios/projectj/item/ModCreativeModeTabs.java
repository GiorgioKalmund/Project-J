package com.mgmstudios.projectj.item;


import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, ProjectJ.MOD_ID);

    public static final Supplier<CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("projectj_tab", () -> CreativeModeTab.builder()
            //Set the title of the tab. Don't forget to add a translation!
            .title(Component.translatable("itemGroup." + ProjectJ.MOD_ID + ".projectj_tab"))
            .icon(() -> new ItemStack(ModBlocks.ADOBE_FURNACE.get()))
            //Add your items to the tab.
            .displayItems((params, output) -> {
                output.accept(ModBlocks.RAW_ADOBE.get());
                output.accept(ModBlocks.ADOBE_BRICKS.get());
                output.accept(ModBlocks.ADOBE_FURNACE.get());


                output.accept(ModItems.JADE.get());
                output.accept(ModItems.RAW_JADE.get());
            })
            .build()
    );

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
