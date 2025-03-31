package com.mgmstudios.projectj.datagen;

import com.mgmstudios.projectj.ProjectJ;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber (modid = ProjectJ.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenProvider extends LootTableProvider {

    public DataGenProvider(PackOutput output, Set<ResourceKey<LootTable>> requiredTables, List<SubProviderEntry> subProviders, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, requiredTables, subProviders, registries);
    }

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent.Client event) {
        event.createProvider((output, lookupProvider) -> new LootTableProvider(
                output,
                Set.of(),
                List.of(
                        new SubProviderEntry(ModBlockLootTableSubProvider::new, LootContextParamSets.BLOCK)
                ),
            lookupProvider
        ));

        event.createProvider(ModRecipeProvider.Runner::new);

        event.createBlockAndItemTags(ModBlockTagsProvider::new, ModItemTagsProvider::new);

        event.createProvider(ModFluidTagsProvider::new);

        event.createProvider(ModLanguageProvider::new);

        event.createProvider(ModDataPackProvider::new);

        event.createProvider(ModEquipmentInfoProvider::new);

    }


}
