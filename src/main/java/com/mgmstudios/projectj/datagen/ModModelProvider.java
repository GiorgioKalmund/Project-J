package com.mgmstudios.projectj.datagen;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.block.ModBlockFamilies;
import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.item.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.blockstates.Variant;
import net.minecraft.client.data.models.blockstates.VariantProperties;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.properties.select.DisplayContext;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;

import static com.mgmstudios.projectj.block.custom.AdobeFurnaceBlock.TIER1;
import static net.minecraft.client.data.models.BlockModelGenerators.*;

@EventBusSubscriber (modid = ProjectJ.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModModelProvider extends ModelProvider {

    public ModModelProvider(PackOutput output) {
        super(output, ProjectJ.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        // Generate models and associated files here

        blockModels.createTrivialCube(ModBlocks.RAW_ADOBE.get());
        blockModels.createTrivialCube(ModBlocks.JADE_ORE.get());
        blockModels.createTrivialCube(ModBlocks.DEEPSLATE_JADE_ORE.get());
        blockModels.createTrivialCube(ModBlocks.JADE_BLOCK.get());
        createSerpentinitePillar(blockModels, itemModels, ModBlocks.SERPENTINITE_PILLAR.get());

        blockModels.createAxisAlignedPillarBlock(ModBlocks.MESQUITE_LOG.get(), TexturedModel.COLUMN_ALT);
        blockModels.createAxisAlignedPillarBlock(ModBlocks.STRIPPED_MESQUITE_LOG.get(), TexturedModel.COLUMN_ALT);

        itemModels.generateFlatItem(ModItems.RAW_JADE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.JADE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.OBSIDIAN_TOOTH.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SERPENTINITE_ROD.get(), ModelTemplates.FLAT_HANDHELD_ROD_ITEM);
        itemModels.generateSpyglass(ModItems.MACUAHUITL.get());

        createFurnaceUntilTier1(blockModels, ModBlocks.ADOBE_FURNACE.get());

        createOlmecHead(blockModels, itemModels, ModBlocks.DAMAGE_OLMEC_HEAD.get());
        createOlmecHead(blockModels, itemModels, ModBlocks.REGENERATION_OLMEC_HEAD.get());
        createOlmecHead(blockModels, itemModels, ModBlocks.CONDUIT_OLMEC_HEAD.get());
        createOlmecHead(blockModels, itemModels, ModBlocks.RESISTANT_OLMEC_HEAD.get());

        createSimpleBlockWithCustomModel(blockModels, ModBlocks.CHIMNEY.get());
        createSimpleBlockWithCustomModel(blockModels, ModBlocks.TALL_STATUE.get());
        createHorizontalDirectionalBlockWithCustomModel(blockModels, ModBlocks.ANCIENT_ALTAR.get());


        ModBlockFamilies.getAllFamilies()
                .filter(BlockFamily::shouldGenerateModel)
                .forEach(family -> this.family(blockModels, family.getBaseBlock()).generateFor(family));

    }

    public BlockModelGenerators.BlockFamilyProvider family(BlockModelGenerators blockModels, Block block) {
        TexturedModel texturedmodel = blockModels.texturedModels.getOrDefault(block, TexturedModel.CUBE.get(block));
        return blockModels.new BlockFamilyProvider(texturedmodel.getMapping()).fullBlock(block, texturedmodel.getTemplate());
    }

    public void createSimpleBlockWithCustomModel(BlockModelGenerators blockModels, Block block){
        ResourceLocation resourcelocation = ModelLocationUtils.getModelLocation(block);
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.multiVariant(block, Variant.variant()
                        .with(VariantProperties.MODEL, resourcelocation))
        );
    }

    public void createHorizontalDirectionalBlockWithCustomModel(BlockModelGenerators blockModels, Block block){
        ResourceLocation resourcelocation = ModelLocationUtils.getModelLocation(block);
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.multiVariant(block, Variant.variant()
                        .with(VariantProperties.MODEL, resourcelocation))
                        .with(createHorizontalFacingDispatch())
        );
    }

    public void createFurnaceUntilTier1(BlockModelGenerators blockModels, Block block){

        TexturedModel.ORIENTABLE.create(block, blockModels.modelOutput);
        ResourceLocation resourcelocation = TextureMapping.getBlockTexture(block, "_front_on");
        TexturedModel.ORIENTABLE.get(block)
                .updateTextures(p_388889_ -> p_388889_.put(TextureSlot.FRONT, resourcelocation))
                .createWithSuffix(block, "_on", blockModels.modelOutput);

        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.multiVariant(block)
                        .with(createHorizontalFacingDispatch())
                        .with(
                                PropertyDispatch.properties(BlockStateProperties.LIT, TIER1)
                                        .select(
                                                false, false,
                                                Variant.variant().with(VariantProperties.MODEL, getModelLocation(ModBlocks.ADOBE_FURNACE.get()))
                                        )
                                        .select(
                                                true, false,
                                                Variant.variant().with(VariantProperties.MODEL, getModelLocation(ModBlocks.ADOBE_FURNACE.get(), "_on"))
                                        )
                                        .select(
                                                false, true,
                                                Variant.variant().with(VariantProperties.MODEL, getModelLocation(ModBlocks.ADOBE_FURNACE.get(), "_tier1"))
                                        )
                                        .select(
                                                true, true,
                                                Variant.variant().with(VariantProperties.MODEL, getModelLocation(ModBlocks.ADOBE_FURNACE.get(), "_on_tier1"))
                                        )
                        )
        );



    }

    public static ResourceLocation getModelLocation(Block block, String suffix) {
        ResourceLocation resourcelocation = BuiltInRegistries.BLOCK.getKey(block);
        return resourcelocation.withPath(p_388420_ -> "block/" + p_388420_ + suffix);
    }

    public static ResourceLocation getModelLocation(Block block) {
        ResourceLocation resourcelocation = BuiltInRegistries.BLOCK.getKey(block);
        return resourcelocation.withPrefix("block/");
    }

    public void createOlmecHead(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block block) {
        ResourceLocation resourcelocation = ModelLocationUtils.getModelLocation(block);
        ResourceLocation resourcelocation1 = ModelLocationUtils.getModelLocation(block, "_on");

        // This would also create the models, however since these are supplied custom, we only want to create the blockstate
        //ResourceLocation resourcelocation = TexturedModel.ORIENTABLE_ONLY_TOP.create(block, blockModels.modelOutput);
        //ResourceLocation resourcelocation1 = TexturedModel.ORIENTABLE_ONLY_TOP.get(block).createWithSuffix(block, "_on", blockModels.modelOutput);

        blockModels.blockStateOutput
                .accept(
                        MultiVariantGenerator.multiVariant(block)
                                .with(createBooleanModelDispatch(BlockStateProperties.LIT, resourcelocation1, resourcelocation))
                                .with(createHorizontalFacingDispatch())
                );

        Item blockItem = block.asItem();
        ItemModel.Unbaked blockModel = ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(block));
        ItemModel.Unbaked itemModel = ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(block, "_item"));
        itemModels.itemModelOutput.accept(blockItem, createMultiStateDispatch(itemModel, blockModel));

    }

    public static ItemModel.Unbaked createMultiStateDispatch(ItemModel.Unbaked itemModel, ItemModel.Unbaked blockModel) {
        return ItemModelUtils.select(
                new DisplayContext(),
                itemModel,
                ItemModelUtils.when(List.of(ItemDisplayContext.GUI, ItemDisplayContext.GROUND, ItemDisplayContext.FIXED), itemModel),
                ItemModelUtils.when(List.of(ItemDisplayContext.HEAD, ItemDisplayContext.FIRST_PERSON_LEFT_HAND, ItemDisplayContext.FIRST_PERSON_RIGHT_HAND, ItemDisplayContext.THIRD_PERSON_LEFT_HAND, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND), blockModel)
        );
    }

    public void createSerpentinitePillar(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block block) {
        TexturedModel.COLUMN_ALT.create(block, blockModels.modelOutput);
        blockModels.createRotatableColumn(block);
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        event.createProvider(ModModelProvider::new);
    }
}
