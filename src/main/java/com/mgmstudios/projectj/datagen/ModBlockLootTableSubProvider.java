package com.mgmstudios.projectj.datagen;

import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.fml.common.Mod;

import java.util.Set;

public class ModBlockLootTableSubProvider extends BlockLootSubProvider {

    protected ModBlockLootTableSubProvider(HolderLookup.Provider lookupProvider) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, lookupProvider);
    }

    // The contents of this Iterable are used for validation.
    // We return an Iterable over our block registry's values here.
    @Override
    protected Iterable<Block> getKnownBlocks() {
        // The contents of our DeferredRegister.
        return ModBlocks.BLOCKS.getEntries()
                .stream()
                // Cast to Block here, otherwise it will be a ? extends Block and Java will complain.
                .map(e -> (Block) e.value())
                .toList();
    }

    // Actually add our loot tables.
    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.RAW_ADOBE.get());
        this.dropSelf(ModBlocks.ADOBE_BRICKS.get());
        this.dropSelf(ModBlocks.ADOBE_FURNACE.get());
        this.dropSelf(ModBlocks.CHIMNEY.get());
        this.dropSelf(ModBlocks.JADE_BLOCK.get());

        this.dropSelf(ModBlocks.BASIC_OLMEC_HEAD.get());
        this.dropSelf(ModBlocks.RESISTANT_OLMEC_HEAD.get());
        this.dropSelf(ModBlocks.CONDUIT_OLMEC_HEAD.get());

        add(ModBlocks.JADE_ORE.get(), block ->
                createMultipleOreDrops(ModBlocks.JADE_ORE.get(), ModItems.RAW_JADE.get(), 2, 5));
        add(ModBlocks.DEEPSLATE_JADE_ORE.get(), block ->
                createMultipleOreDrops(ModBlocks.DEEPSLATE_JADE_ORE.get(), ModItems.RAW_JADE.get(), 2, 5));
    }

    protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))));
    }
}
