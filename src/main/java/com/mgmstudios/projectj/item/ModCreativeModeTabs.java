package com.mgmstudios.projectj.item;


import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.block.ModBlockFamilies;
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
            .icon(() -> new ItemStack(ModItems.JADE.get()))
            //Add your items to the tab.
            .displayItems((params, output) -> {
                output.accept(ModBlocks.RAW_ADOBE.get());
                output.accept(ModBlocks.ADOBE_FURNACE.get());
                output.accept(ModBlocks.CHIMNEY.get());
                output.accept(ModBlocks.JADE_ORE.get());
                output.accept(ModBlocks.DEEPSLATE_JADE_ORE.get());
                output.accept(ModItems.RAW_JADE.get());
                output.accept(ModItems.JADE.get());
                output.accept(ModBlocks.JADE_BLOCK.get());
                output.accept(ModBlocks.SNAKE_STATUE.get());

                output.accept(ModBlocks.SERPENTINITE_PILLAR.get());
                output.accept(ModItems.SERPENTINITE_ROD.get());

                output.accept(ModBlocks.REGENERATION_OLMEC_HEAD.get());
                output.accept(ModBlocks.DAMAGE_OLMEC_HEAD.get());
                output.accept(ModBlocks.CONDUIT_OLMEC_HEAD.get());
                output.accept(ModBlocks.RESISTANT_OLMEC_HEAD.get());

                output.accept(ModBlocks.ANCIENT_ALTAR.get());
                output.accept(ModItems.MACUAHUITL.get());
                output.accept(ModItems.OBSIDIAN_TOOTH.get());

                output.accept(ModBlocks.MESQUITE_LOG.get());
                output.accept(ModBlocks.STRIPPED_MESQUITE_LOG.get());
                output.accept(ModBlocks.MESQUITE_LEAVES.get());
                output.accept(ModBlocks.MESQUITE_BRAZIER.get());

                ModBlockFamilies.getAllFamilies()
                        .forEach(family -> {

                            output.accept(family.getBaseBlock());

                            family.getVariants().values().stream()
                                    .map(block -> new ItemStack(block.asItem()))
                                    .forEach(output::accept);
                        });
            })
            .build()
    );

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
