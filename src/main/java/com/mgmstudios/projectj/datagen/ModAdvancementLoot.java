package com.mgmstudios.projectj.datagen;

import com.mgmstudios.projectj.item.ModItems;
import com.mgmstudios.projectj.loot.ModLootTables;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public record ModAdvancementLoot(HolderLookup.Provider registries) implements LootTableSubProvider {
    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer) {
        consumer.accept(ModLootTables.GRANT_QUEST_BOOK.getKey(), LootTable.lootTable()
                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                .withPool(LootPool.lootPool()
                    .setRolls(UniformGenerator.between(1, 1))
                    .add(LootItem.lootTableItem(ModItems.QUEST_BOOK.get()))
                ));
    }
}
