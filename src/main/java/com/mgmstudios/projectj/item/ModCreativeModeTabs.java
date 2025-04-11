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
import org.objectweb.asm.tree.ModuleNode;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, ProjectJ.MOD_ID);

    public static final Supplier<CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("projectj_tab", () -> CreativeModeTab.builder()
            //Set the title of the tab. Don't forget to add a translation!
            .title(Component.translatable("itemGroup." + ProjectJ.MOD_ID + ".projectj_tab"))
            .icon(() -> new ItemStack(ModItems.JADE.get()))
            //Add your items to the tab.
            .displayItems((params, output) -> {
                output.accept(ModBlocks.PACKED_ADOBE.get());
                output.accept(ModBlocks.ADOBE_SAND.get());
                output.accept(ModBlocks.ADOBE_FURNACE.get());
                output.accept(ModBlocks.CHIMNEY.get());

                output.accept(ModBlocks.SNAKE_STATUE.get());
                output.accept(ModBlocks.MAGNIFYING_GLASS_STAND.get());
                output.accept(ModBlocks.TELEPORTATION_PAD.get());

                output.accept(ModBlocks.JADE_ORE.get());
                output.accept(ModBlocks.DEEPSLATE_JADE_ORE.get());
                output.accept(ModItems.RAW_JADE.get());
                output.accept(ModItems.JADE.get());
                output.accept(ModBlocks.JADE_BLOCK.get());
                output.accept(ModItems.RAW_PYRITE.get());
                output.accept(ModItems.PYRITE_INGOT.get());
                output.accept(ModBlocks.RAW_PYRITE_BLOCK.get());
                output.accept(ModBlocks.PYRITE_BLOCK.get());
                output.accept(ModBlocks.PYRITE_ORE.get());
                output.accept(ModItems.LIQUID_PYRITE_BUCKET.get());
                output.accept(ModItems.PYRITE_MAGNET.get());

                output.accept(ModBlocks.SERPENTINITE_PILLAR.get());
                output.accept(ModBlocks.SERPENTINITE_BENCH.get());
                output.accept(ModBlocks.SERPENTINITE_BENCH_CORNER.get());
                output.accept(ModItems.SERPENTINITE_ROD.get());

                output.accept(ModItems.MAIZE);
                output.accept(ModItems.MAIZE_SEEDS);
                output.accept(ModItems.CHILI);
                output.accept(ModItems.CHILI_SEEDS);
                output.accept(ModItems.MAIZE_MASH);
                output.accept(ModItems.CHILI_CON_CARNE);
                output.accept(ModBlocks.METATE);
                output.accept(ModItems.STONE_MANO);
                output.accept(ModBlocks.BOTANY_POT);

                output.accept(ModBlocks.REGENERATION_OLMEC_HEAD.get());
                output.accept(ModBlocks.DAMAGE_OLMEC_HEAD.get());
                output.accept(ModBlocks.CONDUIT_OLMEC_HEAD.get());
                output.accept(ModBlocks.RESISTANT_OLMEC_HEAD.get());

                output.accept(ModBlocks.OLMEC_ALTAR.get());
                output.accept(ModBlocks.ANCIENT_ALTAR.get());
                output.accept(ModItems.MACUAHUITL.get());
                output.accept(ModItems.OBSIDIAN_TOOTH.get());
                output.accept(ModItems.SACRIFICIAL_DAGGER.get());
                output.accept(ModItems.TROWEL.get());
                output.accept(ModItems.SUN_ARMOR_HELMET.get());
                output.accept(ModItems.JADE_HELMET.get());
                output.accept(ModItems.MAGNIFYING_GLASS.get());
                output.accept(ModItems.FIRE_STARTER.get());
                output.accept(ModItems.CRUDE_SACRIFICE_BOWL.get());
                output.accept(ModItems.FILLED_CRUDE_SACRIFICE_BOWL.get());
                output.accept(ModItems.TELEPORTATION_KEY.get());

                output.accept(ModBlocks.MESQUITE_LOG.get());
                output.accept(ModBlocks.STRIPPED_MESQUITE_LOG.get());
                output.accept(ModBlocks.MESQUITE_WOOD.get());
                output.accept(ModBlocks.STRIPPED_MESQUITE_WOOD.get());
                output.accept(ModBlocks.MESQUITE_LEAVES.get());
                output.accept(ModBlocks.MESQUITE_BRAZIER.get());
                output.accept(ModBlocks.MESQUITE_SAPLING.get());
                output.accept(ModBlocks.MESQUITE_BENCH.get());
                output.accept(ModBlocks.MESQUITE_BENCH_CORNER.get());

                output.accept(ModItems.LITTLE_MAN_SPAWN_EGG.get());
                output.accept(ModBlocks.LITTLE_MAN_STATUE_BLOCK.get());
                output.accept(ModBlocks.EMPTY_LITTLE_MAN_STATUE_BLOCK.get());
                output.accept(ModItems.LITTLE_MAN_VOODOO.get());
                output.accept(ModItems.VOODOO_CATCHER.get());

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
