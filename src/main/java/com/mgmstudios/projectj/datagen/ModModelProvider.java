package com.mgmstudios.projectj.datagen;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.item.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.Variant;
import net.minecraft.client.data.models.blockstates.VariantProperties;
import net.minecraft.client.data.models.model.*;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

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
        blockModels.createTrivialCube(ModBlocks.ADOBE_BRICKS.get());
        blockModels.createTrivialCube(ModBlocks.CHIMNEY.get());
        blockModels.createTrivialCube(ModBlocks.JADE_ORE.get());
        blockModels.createTrivialCube(ModBlocks.DEEPSLATE_JADE_ORE.get());
        blockModels.createTrivialCube(ModBlocks.JADE_BLOCK.get());

        // These files are modified inside the 'generated' folder as the basic generation is not enough (skill issue)
        blockModels.createFurnace(ModBlocks.ADOBE_FURNACE.get(), TexturedModel.ORIENTABLE);

        itemModels.generateFlatItem(ModItems.RAW_JADE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.JADE.get(), ModelTemplates.FLAT_ITEM);

        createOlmecHead(blockModels, ModBlocks.RESISTANT_OLMEC_HEAD.get());
        createOlmecHead(blockModels, ModBlocks.BASIC_OLMEC_HEAD.get());

    }

    public void createOlmecHead(BlockModelGenerators blockModels, Block block) {
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
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        event.createProvider(ModModelProvider::new);
    }
}
