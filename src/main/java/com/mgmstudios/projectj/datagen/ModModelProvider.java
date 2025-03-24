package com.mgmstudios.projectj.datagen;

import com.mgmstudios.projectj.ProjectJ;
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
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

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
        blockModels.createTrivialCube(ModBlocks.ADOBE_BRICKS.get());
        blockModels.createTrivialCube(ModBlocks.JADE_ORE.get());
        blockModels.createTrivialCube(ModBlocks.DEEPSLATE_JADE_ORE.get());
        blockModels.createTrivialCube(ModBlocks.JADE_BLOCK.get());

        itemModels.generateFlatItem(ModItems.RAW_JADE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.JADE.get(), ModelTemplates.FLAT_ITEM);

        createFurnaceUntilTier1(blockModels, ModBlocks.ADOBE_FURNACE.get());

        createOlmecHead(blockModels, itemModels, ModBlocks.RESISTANT_OLMEC_HEAD.get());
        createOlmecHead(blockModels, itemModels, ModBlocks.BASIC_OLMEC_HEAD.get());
        createOlmecHead(blockModels, itemModels, ModBlocks.CONDUIT_OLMEC_HEAD.get());

        createSimpleBlockWithCustomModel(blockModels, ModBlocks.CHIMNEY.get());
    }

    public void createSimpleBlockWithCustomModel(BlockModelGenerators blockModels, Block block){
        ResourceLocation resourcelocation = ModelLocationUtils.getModelLocation(block);
        blockModels.blockStateOutput.accept( MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, resourcelocation)));
    }

    // TODO - Properly manage block states
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

        blockModels.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(getModelLocation(block, "_item")));

    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        event.createProvider(ModModelProvider::new);
    }
}
