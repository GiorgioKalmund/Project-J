package com.mgmstudios.projectj.datagen.loot;

import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.item.ModItems;
import com.mgmstudios.projectj.loot.ModLootTables;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public record ModContainerLoot(HolderLookup.Provider registries) implements LootTableSubProvider {
    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer) {
        consumer.accept(ModLootTables.BRAZIER_POINT_LOOT.getKey(), brazierPointLootTable());
    }
    public LootTable.Builder brazierPointLootTable() {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(2.0F, 4.0F))
                        .add(LootItem.lootTableItem(ModItems.JADE).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(ModItems.PYRITE_INGOT).setWeight(15).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F))))
                        .add(LootItem.lootTableItem(ModItems.RAW_PYRITE).setWeight(15).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 7.0F))))
                        .add(LootItem.lootTableItem(ModItems.RAW_JADE).setWeight(15).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(Items.STICK).setWeight(25).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(Items.SADDLE).setWeight(20))
                        .add(LootItem.lootTableItem(Items.IRON_HORSE_ARMOR).setWeight(15))
                        .add(LootItem.lootTableItem(ModItems.QUETZAL_FEATHER).setWeight(15).apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 6.0F))))
                        .add(LootItem.lootTableItem(ModBlocks.PACKED_ADOBE).setWeight(10))
                        .add(LootItem.lootTableItem(ModBlocks.COBBLED_SERPENTINITE).setWeight(5))
                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(20).apply(EnchantRandomlyFunction.randomApplicableEnchantment(this.registries)))
                        .add(LootItem.lootTableItem(Items.GOLDEN_APPLE).setWeight(20)).add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE).setWeight(2))
                        .add(EmptyLootItem.emptyItem().setWeight(15))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(4.0F))
                        .add(LootItem.lootTableItem(Items.BONE).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 8.0F))))
                        .add(LootItem.lootTableItem(Items.GUNPOWDER).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 8.0F))))
                        .add(LootItem.lootTableItem(ModItems.CHILI_CON_CARNE).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 8.0F))))
                        .add(LootItem.lootTableItem(Items.STRING).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 8.0F))))
                        .add(LootItem.lootTableItem(ModBlocks.ADOBE_SAND).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 8.0F))))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(EmptyLootItem.emptyItem().setWeight(6))
                        .add(LootItem.lootTableItem(ModItems.JADE_CHESTPLATE).setWeight(1).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))));
    }
}
