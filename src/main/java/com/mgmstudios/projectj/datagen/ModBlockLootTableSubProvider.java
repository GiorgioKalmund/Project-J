package com.mgmstudios.projectj.datagen;

import java.util.Set;

import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.block.custom.AncientAltarBlock;
import com.mgmstudios.projectj.block.custom.TallBlock;
import com.mgmstudios.projectj.block.custom.botany.BotanyBushBlock;
import com.mgmstudios.projectj.component.ModDataComponents;
import com.mgmstudios.projectj.item.ModItems;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

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
        this.dropSelf(ModBlocks.PACKED_ADOBE.get());
        this.dropSelf(ModBlocks.ADOBE_SAND.get());
        this.dropSelf(ModBlocks.ADOBE_FURNACE.get());
        this.dropSelf(ModBlocks.CHIMNEY.get());
        this.dropSelf(ModBlocks.JADE_BLOCK.get());
        this.dropSelf(ModBlocks.RAW_PYRITE_BLOCK.get());
        this.dropSelf(ModBlocks.PYRITE_BLOCK.get());
        this.dropSelf(ModBlocks.MAGNIFYING_GLASS_STAND.get());
        this.dropSelf(ModBlocks.TELEPORTATION_PAD.get());
        this.dropSelf(ModBlocks.ANCIENT_ALTAR.get());
        this.dropSelf(ModBlocks.METATE.get());
        this.dropSelf(ModBlocks.BOTANY_POT.get());
        this.dropSelf(ModBlocks.HOLLOW_MESQUITE_LOG.get());
        this.dropSelf(ModBlocks.CHARCOAL_BLOCK.get());
        this.dropSelf(ModBlocks.SOCKET_WORKBENCH.get());

        this.dropSelf(ModBlocks.ADOBE_BRICKS.get());
        this.dropSelf(ModBlocks.ADOBE_BRICKS_STAIRS.get());
        this.dropSelf(ModBlocks.ADOBE_BRICKS_SLAB.get());
        this.dropSelf(ModBlocks.ADOBE_BRICKS_WALL.get());
        this.dropSelf(ModBlocks.ADOBE_GLASS.get());
        this.dropSelf(ModBlocks.TINTED_ADOBE_GLASS.get());

        this.dropNothing(ModBlocks.JADE_CRYSTAL.get());

        this.dropSelf(ModBlocks.SERPENTINITE_BENCH.get());
        this.dropSelf(ModBlocks.SERPENTINITE_BENCH_CORNER.get());
        this.dropSelf(ModBlocks.SERPENTINITE_PILLAR.get());

        this.dropSelf(ModBlocks.COBBLED_SERPENTINITE.get());
        this.dropSelf(ModBlocks.COBBLED_SERPENTINITE_STAIRS.get());
        this.dropSelf(ModBlocks.COBBLED_SERPENTINITE_SLAB.get());
        this.dropSelf(ModBlocks.COBBLED_SERPENTINITE_WALL.get());

        createdByproductDropWithSilkTouch(ModBlocks.SERPENTINITE_ROCK.get(), ModBlocks.SERPENTINITE_ROCK.asItem(),
                ModItems.OBSIDIAN_TOOTH.get(), 1,2, 0.01F, ModBlocks.COBBLED_SERPENTINITE.asItem());
        this.dropSelf(ModBlocks.SERPENTINITE_ROCK_STAIRS.get());
        this.dropSelf(ModBlocks.SERPENTINITE_ROCK_SLAB.get());
        this.dropSelf(ModBlocks.SERPENTINITE_ROCK_WALL.get());
        this.dropSelf(ModBlocks.SERPENTINITE_ROCK_BUTTON.get());
        this.dropSelf(ModBlocks.SERPENTINITE_ROCK_PRESSURE_PLATE.get());

        this.dropSelf(ModBlocks.SERPENTINITE_BRICKS.get());
        this.dropSelf(ModBlocks.SERPENTINITE_BRICKS_STAIRS.get());
        this.dropSelf(ModBlocks.SERPENTINITE_BRICKS_SLAB.get());
        this.dropSelf(ModBlocks.SERPENTINITE_BRICKS_WALL.get());

        this.dropSelf(ModBlocks.POLISHED_SERPENTINITE.get());
        this.dropSelf(ModBlocks.POLISHED_SERPENTINITE_STAIRS.get());
        this.dropSelf(ModBlocks.POLISHED_SERPENTINITE_SLAB.get());
        this.dropSelf(ModBlocks.POLISHED_SERPENTINITE_WALL.get());

        this.dropSelf(ModBlocks.REGENERATION_OLMEC_HEAD.get());
        this.dropSelf(ModBlocks.DAMAGE_OLMEC_HEAD.get());
        this.dropSelf(ModBlocks.CONDUIT_OLMEC_HEAD.get());
        this.dropSelf(ModBlocks.RESISTANT_OLMEC_HEAD.get());

        this.dropSelf(ModBlocks.MESQUITE_LOG.get());
        this.dropSelf(ModBlocks.STRIPPED_MESQUITE_LOG.get());
        this.dropSelf(ModBlocks.MESQUITE_WOOD.get());
        this.dropSelf(ModBlocks.STRIPPED_MESQUITE_WOOD.get());
        this.dropSelf(ModBlocks.MESQUITE_PLANKS.get());
        this.dropSelf(ModBlocks.MESQUITE_STAIRS.get());
        this.dropSelf(ModBlocks.MESQUITE_SLAB.get());
        this.dropSelf(ModBlocks.MESQUITE_FENCE.get());
        this.dropSelf(ModBlocks.MESQUITE_FENCE_GATE.get());
        this.dropSelf(ModBlocks.MESQUITE_BUTTON.get());
        this.dropSelf(ModBlocks.MESQUITE_PRESSURE_PLATE.get());
        this.dropSelf(ModBlocks.MESQUITE_BRAZIER.get());
        this.dropSelf(ModBlocks.MESQUITE_SAPLING.get());
        this.dropSelf(ModBlocks.MESQUITE_BENCH.get());
        this.dropSelf(ModBlocks.MESQUITE_BENCH_CORNER.get());
        this.dropPottedContents(ModBlocks.POTTED_MESQUITE_SAPLING.get());

        this.dropOther(ModBlocks.POTTED_CHILI_BUSH.get(), ModBlocks.BOTANY_POT.get());
        this.dropOther(ModBlocks.POTTED_MAIZE_CROP.get(), ModBlocks.BOTANY_POT.get());

        add(ModBlocks.ANCIENT_ALTAR.get(), this::createAncientAltarBlock);

        add(ModBlocks.MESQUITE_LEAVES.get(), block -> super.createLeavesDrops(ModBlocks.MESQUITE_LEAVES.get(), ModBlocks.MESQUITE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        add(ModBlocks.SNAKE_STATUE.get(), this::createTallBlockTable);

        add(ModBlocks.LITTLE_MAN_STATUE_BLOCK.get(), block -> createSingleItemTableWithSilkTouch(ModBlocks.LITTLE_MAN_STATUE_BLOCK.get(), ModBlocks.EMPTY_LITTLE_MAN_STATUE_BLOCK.get()));
        this.dropSelf(ModBlocks.EMPTY_LITTLE_MAN_STATUE_BLOCK.get());

        this.dropSelf(ModBlocks.OLMEC_ALTAR.get());

        add(ModBlocks.JADE_ORE.get(), block ->
                createMultipleOreDrops(ModBlocks.JADE_ORE.get(), ModItems.RAW_JADE.get(), 2, 5));
        add(ModBlocks.DEEPSLATE_JADE_ORE.get(), block ->
                createMultipleOreDrops(ModBlocks.DEEPSLATE_JADE_ORE.get(), ModItems.RAW_JADE.get(), 2, 5));
        add(ModBlocks.SERPENTINITE_JADE_ORE.get(), block ->
                createMultipleOreDrops(ModBlocks.SERPENTINITE_JADE_ORE.get(), ModItems.RAW_JADE.get(), 3, 6));
        add(ModBlocks.PYRITE_ORE.get(), block -> createOreDrop(ModBlocks.PYRITE_ORE.get(), ModItems.RAW_PYRITE.get()));

        LootItemCondition.Builder maizeCropDrops = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.MAIZE_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BotanyBushBlock.AGE, 3));
        this.add(ModBlocks.MAIZE_CROP.get(), this.createCropDrops(ModBlocks.MAIZE_CROP.get(), ModItems.MAIZE.get(), ModItems.MAIZE_SEEDS.get(), maizeCropDrops));

        LootItemCondition.Builder chiliCropDrops = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.CHILI_BUSH.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BotanyBushBlock.AGE, 3));
        this.add(ModBlocks.CHILI_BUSH.get(), this.createCropDrops(ModBlocks.CHILI_BUSH.get(), ModItems.CHILI.get(), ModItems.CHILI_SEEDS.get(), chiliCropDrops));
    }

    protected LootTable.Builder createAncientAltarBlock(Block block){
        return this.applyExplosionDecay(
                block,
                LootTable.lootTable()
                        .withPool(applyExplosionCondition(block.asItem(), LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(block.asItem()))))
                        .apply(CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY).include(ModDataComponents.MAIN_INVENTORY.get()).include(ModDataComponents.SIDE_INVENTORY.get()).include(ModDataComponents.FLUID_INVENTORY.get()))
                        .apply(CopyBlockState.copyState(ModBlocks.ANCIENT_ALTAR.get())
                                .copy(AncientAltarBlock.PYRITE_INSIDE)
                                .copy(AncientAltarBlock.BLOOD_INSIDE)
                        ));

    }

    protected LootTable.Builder createItemBushDrop(Block block, Item item, float minDrops, float midDrops, float maxDrops){
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

        return this.applyExplosionDecay(
                block,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .when(
                                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 3))
                                        )
                                        .add(LootItem.lootTableItem(item))
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(midDrops, maxDrops)))
                                        .apply(ApplyBonusCount.addUniformBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .when(
                                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 2))
                                        )
                                        .add(LootItem.lootTableItem(item))
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, midDrops)))
                                        .apply(ApplyBonusCount.addUniformBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
                        )
        );
    }

    protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))));
    }

    protected LootTable.Builder createOreDrop(Block block, Item item) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(
                block,
                this.applyExplosionDecay(
                        block, LootItem.lootTableItem(item).apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
                )
        );
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

    protected LootTable.Builder createTallBlockTable(Block doorBlock) {
        return this.createSinglePropConditionTable(doorBlock, TallBlock.HALF, DoubleBlockHalf.LOWER);
    }

    protected LootTable.Builder dropNothing(Block block) {
        add(block, LootTable.lootTable());
        return LootTable.lootTable();
    }
}
