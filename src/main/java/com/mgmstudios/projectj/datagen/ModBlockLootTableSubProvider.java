package com.mgmstudios.projectj.datagen;

import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

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
        this.dropSelf(ModBlocks.ADOBE_FURNACE.get());
        this.dropSelf(ModBlocks.CHIMNEY.get());
        this.dropSelf(ModBlocks.JADE_BLOCK.get());

        this.dropSelf(ModBlocks.ADOBE_BRICKS.get());
        this.dropSelf(ModBlocks.ADOBE_BRICKS_STAIRS.get());
        this.dropSelf(ModBlocks.ADOBE_BRICKS_SLAB.get());
        this.dropSelf(ModBlocks.ADOBE_BRICKS_WALL.get());
        this.dropSelf(ModBlocks.TALL_STATUE.get());

        this.dropSelf(ModBlocks.SERPENTINITE_PILLAR.get());

        this.dropSelf(ModBlocks.COBBLED_SERPENTINITE.get());
        this.dropSelf(ModBlocks.COBBLED_SERPENTINITE_STAIRS.get());
        this.dropSelf(ModBlocks.COBBLED_SERPENTINITE_SLAB.get());
        this.dropSelf(ModBlocks.COBBLED_SERPENTINITE_WALL.get());

        createdByproductDropWithSilkTouch(ModBlocks.SERPENTINITE_ROCK.get(), ModBlocks.SERPENTINITE_ROCK.asItem(),
                ModItems.OBSIDIAN_TOOTH.get(), 1,2, 0.025F, ModBlocks.COBBLED_SERPENTINITE.asItem());
        this.dropSelf(ModBlocks.SERPENTINITE_ROCK_STAIRS.get());
        this.dropSelf(ModBlocks.SERPENTINITE_ROCK_SLAB.get());
        this.dropSelf(ModBlocks.SERPENTINITE_ROCK_WALL.get());

        this.dropSelf(ModBlocks.SERPENTINITE_BRICKS.get());
        this.dropSelf(ModBlocks.SERPENTINITE_BRICKS_STAIRS.get());
        this.dropSelf(ModBlocks.SERPENTINITE_BRICKS_SLAB.get());
        this.dropSelf(ModBlocks.SERPENTINITE_BRICKS_WALL.get());

        this.dropSelf(ModBlocks.REGENERATION_OLMEC_HEAD.get());
        this.dropSelf(ModBlocks.DAMAGE_OLMEC_HEAD.get());
        this.dropSelf(ModBlocks.CONDUIT_OLMEC_HEAD.get());
        this.dropSelf(ModBlocks.RESISTANT_OLMEC_HEAD.get());

        this.dropSelf(ModBlocks.MESQUITE_LOG.get());
        this.dropSelf(ModBlocks.MESQUITE_PLANKS.get());
        this.dropSelf(ModBlocks.MESQUITE_STAIRS.get());
        this.dropSelf(ModBlocks.MESQUITE_SLAB.get());
        this.dropSelf(ModBlocks.MESQUITE_FENCE.get());
        this.dropSelf(ModBlocks.MESQUITE_FENCE_GATE.get());


        this.dropSelf(ModBlocks.ANCIENT_ALTAR.get());

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

    protected void createdByproductDrop(Block block, Item mainItem, Item byproduct, int minDrops, int maxDrops, float probability){
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        LootPoolEntryContainer.Builder<?> mainItemDrop = this.applyExplosionCondition(byproduct, LootItem.lootTableItem(mainItem));
        LootPoolEntryContainer.Builder<?> byproductDrop = this.applyExplosionCondition(byproduct, LootItem.lootTableItem(byproduct)
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
                .when(LootItemRandomChanceCondition.randomChance(probability)));


        LootPool.Builder byProductPool= LootPool.lootPool()
                .setBonusRolls(ConstantValue.exactly(1))
                .add(mainItemDrop)
                .add(byproductDrop);

        this.add(block, LootTable.lootTable().withPool(byProductPool));
    }

    protected void createdByproductDropWithSilkTouch(Block block, Item mainItem, Item byproduct, int minDrops, int maxDrops, float probability, ItemLike nonSilkTouchDrop){
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        var mainItemDrop = createSilkTouchDispatchTable(block, this.applyExplosionCondition(mainItem, LootItem.lootTableItem(nonSilkTouchDrop)));

        LootPoolEntryContainer.Builder<?> byproductDrop = this.applyExplosionCondition(byproduct, LootItem.lootTableItem(byproduct)
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
                .when(LootItemRandomChanceCondition.randomChance(probability)));


        LootPool.Builder byProductPool= LootPool.lootPool()
                .setBonusRolls(ConstantValue.exactly(1))
                .add(byproductDrop);

        this.add(block, mainItemDrop.withPool(byProductPool));
    }
}
