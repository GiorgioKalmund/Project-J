package com.mgmstudios.projectj.datagen;

import java.util.List;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.block.ModBlockFamilies;
import com.mgmstudios.projectj.block.ModBlocks;
import static com.mgmstudios.projectj.block.custom.AdobeFurnaceBlock.TIER1;
import static com.mgmstudios.projectj.block.custom.AncientAltarBlock.BLOOD_INSIDE;
import static com.mgmstudios.projectj.block.custom.AncientAltarBlock.CRAFTING;
import static com.mgmstudios.projectj.block.custom.AncientAltarBlock.PYRITE_INSIDE;
import static com.mgmstudios.projectj.block.custom.HollowTreeBlock.CONNECTED;
import static com.mgmstudios.projectj.block.custom.LittleManStatueBlock.SUMMONING;
import com.mgmstudios.projectj.block.custom.MagnifyingGlassStandBlock;
import static com.mgmstudios.projectj.block.custom.TeleportationBlock.UNLOCKED;

import com.mgmstudios.projectj.component.ModDataComponents;
import com.mgmstudios.projectj.item.ModEquipmentAssets;
import com.mgmstudios.projectj.item.ModItems;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.color.item.Dye;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.BlockModelGenerators.PlantType;
import static net.minecraft.client.data.models.BlockModelGenerators.createBooleanModelDispatch;
import static net.minecraft.client.data.models.BlockModelGenerators.createHorizontalFacingDispatch;
import static net.minecraft.client.data.models.BlockModelGenerators.createSimpleBlock;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.blockstates.Variant;
import net.minecraft.client.data.models.blockstates.VariantProperties;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.RangeSelectItemModel;
import net.minecraft.client.renderer.item.properties.numeric.UseDuration;
import net.minecraft.client.renderer.item.properties.select.DisplayContext;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.model.generators.template.ExtendedModelTemplateBuilder;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber (modid = ProjectJ.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModModelProvider extends ModelProvider {

    public ModModelProvider(PackOutput output) {
        super(output, ProjectJ.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        // Generate models and associated files here

        blockModels.createTrivialCube(ModBlocks.PACKED_ADOBE.get());
        blockModels.createTrivialCube(ModBlocks.ADOBE_SAND.get());
        blockModels.createTrivialCube(ModBlocks.JADE_ORE.get());
        blockModels.createTrivialCube(ModBlocks.DEEPSLATE_JADE_ORE.get());
        blockModels.createTrivialCube(ModBlocks.SERPENTINITE_JADE_ORE.get());
        blockModels.createTrivialCube(ModBlocks.JADE_BLOCK.get());
        blockModels.createTrivialCube(ModBlocks.RAW_PYRITE_BLOCK.get());
        blockModels.createTrivialCube(ModBlocks.PYRITE_BLOCK.get());
        blockModels.createTrivialCube(ModBlocks.PYRITE_ORE.get());
        blockModels.createTrivialCube(ModBlocks.CHARCOAL_BLOCK.get());
        createTrivialCutoutCube(blockModels, ModBlocks.MESQUITE_LEAVES.get());
        createTrivialTranslucentCube(blockModels, ModBlocks.ADOBE_GLASS.get());
        createTrivialTranslucentCube(blockModels, ModBlocks.TINTED_ADOBE_GLASS.get());
        createCutoutPlantWithDefaultItem(blockModels, ModBlocks.MESQUITE_SAPLING.get(), ModBlocks.POTTED_MESQUITE_SAPLING.get(), PlantType.NOT_TINTED);

        createAncientAltar(blockModels, itemModels, ModBlocks.ANCIENT_ALTAR.get());

        createSerpentinitePillar(blockModels, itemModels, ModBlocks.SERPENTINITE_PILLAR.get());

        blockModels.woodProvider(ModBlocks.MESQUITE_LOG.get()).logWithHorizontal(ModBlocks.MESQUITE_LOG.get()).wood(ModBlocks.MESQUITE_WOOD.get());
        blockModels.woodProvider(ModBlocks.STRIPPED_MESQUITE_LOG.get()).logWithHorizontal(ModBlocks.STRIPPED_MESQUITE_LOG.get()).wood(ModBlocks.STRIPPED_MESQUITE_WOOD.get());

        createHorizontallyFacingDoubleBlock(blockModels, ModBlocks.SNAKE_STATUE.get());

        createTeleportationPad(blockModels, itemModels, ModBlocks.TELEPORTATION_PAD.get());

        blockModels.createNonTemplateModelBlock(ModBlocks.LIQUID_PYRITE.get());

        itemModels.generateFlatItem(ModItems.TROWEL.get(), ModelTemplates.FLAT_HANDHELD_ROD_ITEM);
        itemModels.generateFlatItem(ModItems.TELEPORTATION_KEY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.FIRE_STARTER.get(), ModelTemplates.FLAT_HANDHELD_ROD_ITEM);
        itemModels.generateFlatItem(ModItems.SACRIFICIAL_DAGGER.get(), ModelTemplates.FLAT_HANDHELD_ROD_ITEM);
        itemModels.generateFlatItem(ModItems.CRUDE_SACRIFICE_BOWL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.FILLED_CRUDE_SACRIFICE_BOWL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RAW_JADE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.JADE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RAW_PYRITE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.PYRITE_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.LIQUID_PYRITE_BUCKET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.OBSIDIAN_TOOTH.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SERPENTINITE_ROD.get(), ModelTemplates.FLAT_HANDHELD_ROD_ITEM);
        itemModels.generateFlatItem(ModItems.LITTLE_MAN_VOODOO.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.LITTLE_KING_VOODOO.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.VOODOO_CATCHER.get(), ModelTemplates.FLAT_HANDHELD_ROD_ITEM);
        itemModels.generateFlatItem(ModItems.HATCHET.get(), ModelTemplates.FLAT_HANDHELD_ROD_ITEM);
        itemModels.generateFlatItem(ModItems.PYRITE_MAGNET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.JADE_MAGNET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.QUETZAL_FEATHER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.QUETZAL_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.QUETZAL_EGG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.QUEST_BOOK.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.TELEPORTATION_CORE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.OBSIDIAN_ARROW.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RUNNER_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.FLESH.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.GLIDER_SOCKET.get(), ModelTemplates.FLAT_ITEM);
        generateGemWithLegendary(itemModels, ModItems.PACIFYING_SOCKET.get());
        itemModels.generateFlatItem(ModItems.REMOVE_AI_SOCKET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.GIVE_AI_SOCKET.get(), ModelTemplates.FLAT_ITEM);
        generateGemWithLegendary(itemModels, ModItems.EVERYTHING_SOCKET.get());
        generateGemWithLegendary(itemModels, ModItems.EMPTY_SOCKET.get());
        itemModels.generateFlatItem(ModItems.SOCKET_TESTER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.TOPAZ_GEM.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.SUN_ARMOR_HELMET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SUN_ARMOR_CHESTPLATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SUN_ARMOR_LEGGINGS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SUN_ARMOR_BOOTS.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.AWAKENED_SUN_ARMOR_HELMET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.AWAKENED_SUN_ARMOR_CHESTPLATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.AWAKENED_SUN_ARMOR_LEGGINGS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.AWAKENED_SUN_ARMOR_BOOTS.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateTrimmableItem(ModItems.JADE_HELMET.get(), ModEquipmentAssets.JADE, "helmet", false);
        itemModels.generateTrimmableItem(ModItems.JADE_CHESTPLATE.get(), ModEquipmentAssets.JADE, "chestplate", false);
        itemModels.generateTrimmableItem(ModItems.JADE_LEGGINGS.get(), ModEquipmentAssets.JADE, "leggings", false);
        itemModels.generateTrimmableItem(ModItems.JADE_BOOTS.get(), ModEquipmentAssets.JADE, "boots", false);

        itemModels.generateSpyglass(ModItems.MACUAHUITL.get());
        itemModels.generateSpyglass(ModItems.MAGNIFYING_GLASS.get());
        itemModels.generateSpyglass(ModItems.STONE_MANO.get());
        itemModels.generateSpyglass(ModItems.DEATH_WHISTLE.get());

        itemModels.generateFlatItem(ModItems.LITTLE_MAN_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.LITTLE_KING_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);

        createCustomShield(itemModels, ModItems.CHIMALLI_SHIELD.get());

        createFurnaceUntilTier1(blockModels, ModBlocks.ADOBE_FURNACE.get());

        createOlmecHead(blockModels, itemModels, ModBlocks.DAMAGE_OLMEC_HEAD.get());
        createOlmecHead(blockModels, itemModels, ModBlocks.REGENERATION_OLMEC_HEAD.get());
        createOlmecHead(blockModels, itemModels, ModBlocks.CONDUIT_OLMEC_HEAD.get());
        createOlmecHead(blockModels, itemModels, ModBlocks.RESISTANT_OLMEC_HEAD.get());

        createSimpleBlockWithCustomModel(blockModels, ModBlocks.CHIMNEY.get());
        createSimpleBlockWithCustomModel(blockModels, ModBlocks.JADE_CRYSTAL.get());
        createSimpleBlockWithCustomModel(blockModels, ModBlocks.BOTANY_POT.get());
        createSimpleBlockWithCustomModelAndFlatItem(blockModels, itemModels, ModBlocks.MESQUITE_BRAZIER.get());
        createHorizontalDirectionalBlockWithCustomModel(blockModels, ModBlocks.OLMEC_ALTAR.get());
        createHorizontalDirectionalBlockWithCustomModel(blockModels, ModBlocks.SERPENTINITE_BENCH.get());
        createHorizontalDirectionalBlockWithCustomModel(blockModels, ModBlocks.SERPENTINITE_BENCH_CORNER.get());
        createHorizontalDirectionalBlockWithCustomModel(blockModels, ModBlocks.MESQUITE_BENCH.get());
        createHorizontalDirectionalBlockWithCustomModel(blockModels, ModBlocks.MESQUITE_BENCH_CORNER.get());
        createHorizontalDirectionalBlockWithCustomModel(blockModels, ModBlocks.EMPTY_LITTLE_MAN_STATUE_BLOCK.get());
        createHorizontalDirectionalBlockWithCustomModel(blockModels, ModBlocks.METATE.get());
        createHorizontalDirectionalBlockWithCustomModel(blockModels, ModBlocks.SOCKET_WORKBENCH.get());

        createLittleManStatue(blockModels, ModBlocks.LITTLE_MAN_STATUE_BLOCK.get());

        createMagnifyingGlassBlock(blockModels, itemModels, ModBlocks.MAGNIFYING_GLASS_STAND.get());

        createHollowTreeBlock(blockModels, ModBlocks.HOLLOW_MESQUITE_LOG.get());

        ModBlockFamilies.getAllFamilies()
                .filter(BlockFamily::shouldGenerateModel)
                .forEach(family -> this.family(blockModels, family.getBaseBlock()).generateFor(family));

        itemModels.generateFlatItem(ModItems.MAIZE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.MAIZE_SEEDS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.MAIZE_MASH.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CHILI.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CHILI_SEEDS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CHILI_CON_CARNE.get(), ModelTemplates.FLAT_ITEM);

        createBushBlock(blockModels, ModBlocks.CHILI_BUSH.get());
        createPottedBushBlock(blockModels, ModBlocks.POTTED_CHILI_BUSH.get());
        createBushBlock(blockModels, ModBlocks.MAIZE_CROP.get());
        createPottedBushBlock(blockModels, ModBlocks.POTTED_MAIZE_CROP.get());

    }

    public void generateGemWithLegendary(ItemModelGenerators itemModels, Item gemItem) {
        ItemModel.Unbaked regularModel = ItemModelUtils.plainModel(itemModels.createFlatItemModel(gemItem, ModelTemplates.FLAT_ITEM));
        ItemModel.Unbaked legendaryModel = ItemModelUtils.plainModel(itemModels.createFlatItemModel(gemItem, "_legendary", ModelTemplates.FLAT_ITEM));
        itemModels.generateBooleanDispatch(gemItem, ItemModelUtils.hasComponent(ModDataComponents.Sockets.LEGENDARY_STATUS.get()), legendaryModel, regularModel);
    }


    public void generateBasicArmourItem(ItemModelGenerators itemModels, Item item, boolean dyeable) {
        ResourceLocation resourcelocation = ModelLocationUtils.getModelLocation(item);
        ResourceLocation resourcelocation1 = TextureMapping.getItemTexture(item);
        ResourceLocation resourcelocation2 = TextureMapping.getItemTexture(item, "_overlay");

        ItemModel.Unbaked itemmodel$unbaked1;
        if (dyeable) {
            ModelTemplates.TWO_LAYERED_ITEM.create(resourcelocation, TextureMapping.layered(resourcelocation1, resourcelocation2), itemModels.modelOutput);
            itemmodel$unbaked1 = ItemModelUtils.tintedModel(resourcelocation, new ItemTintSource[]{new Dye(-6265536)});
        } else {
            ModelTemplates.FLAT_ITEM.create(resourcelocation, TextureMapping.layer0(resourcelocation1), itemModels.modelOutput);
            itemmodel$unbaked1 = ItemModelUtils.plainModel(resourcelocation);
        }

        itemModels.itemModelOutput.accept(item, itemmodel$unbaked1);
    }

    public void createTrivialCutoutCube(BlockModelGenerators blockModels, Block block){
        ResourceLocation resourcelocation = ModelTemplates.CUBE_ALL
                .extend()
                .renderType("minecraft:cutout")
                .build()
                .create(block, TextureMapping.cube(block), blockModels.modelOutput);
        blockModels.blockStateOutput.accept(createSimpleBlock(block, resourcelocation));
    }

    public void createTrivialTranslucentCube(BlockModelGenerators blockModels, Block block){
        ResourceLocation resourcelocation = ModelTemplates.CUBE_ALL
                .extend()
                .renderType("minecraft:translucent")
                .build()
                .create(block, TextureMapping.cube(block), blockModels.modelOutput);
        blockModels.blockStateOutput.accept(createSimpleBlock(block, resourcelocation));
    }

    public void createSimpleItemWithCustomModel(ItemModelGenerators itemModelGenerators, Item item){
        ResourceLocation resourceLocation = ModelLocationUtils.getModelLocation(item);
        ItemModel.Unbaked itemModel = ItemModelUtils.plainModel(resourceLocation);
        itemModelGenerators.itemModelOutput.accept(item, itemModel);
    }

    public void createCustomShield(ItemModelGenerators itemModelGenerators, Item shieldItem){
        ResourceLocation resourceLocation = ModelLocationUtils.getModelLocation(shieldItem);
        ResourceLocation resourceLocation1 = ModelLocationUtils.getModelLocation(shieldItem, "_blocking");

        ItemModel.Unbaked specialModel = ItemModelUtils.plainModel(resourceLocation);
        ItemModel.Unbaked specialModel1 = ItemModelUtils.plainModel(resourceLocation1);
        itemModelGenerators.generateBooleanDispatch(shieldItem, ItemModelUtils.isUsingItem(), specialModel1, specialModel);
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

    public void createSimpleBlockWithCustomModelAndFlatItem(BlockModelGenerators blockModels, ItemModelGenerators itemModelGenerators, Block block){
        ResourceLocation resourcelocation = ModelLocationUtils.getModelLocation(block);
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.multiVariant(block, Variant.variant()
                        .with(VariantProperties.MODEL, resourcelocation))
        );

        ItemModel.Unbaked itemmodel$unbaked = ItemModelUtils.plainModel(itemModelGenerators.createFlatItemModel(block.asItem(), ModelTemplates.FLAT_ITEM));
        itemModelGenerators.itemModelOutput.accept(block.asItem(), itemmodel$unbaked);
    }

    public void createCropBlock(BlockModelGenerators blockModelGenerators, Block cropBlock, Property<Integer> ageProperty, int... ageToVisualStageMapping) {
        if (ageProperty.getPossibleValues().size() != ageToVisualStageMapping.length) {
            throw new IllegalArgumentException();
        } else {
            Int2ObjectMap<ResourceLocation> int2objectmap = new Int2ObjectOpenHashMap<>();
            PropertyDispatch propertydispatch = PropertyDispatch.property(ageProperty)
                    .generate(
                            p_388091_ -> {
                                int i = ageToVisualStageMapping[p_388091_];
                                ResourceLocation resourcelocation = int2objectmap.computeIfAbsent(
                                        i, property -> {
                                            var modelBuilder = ModelTemplates.CROP.extend().renderType("minecraft:cutout");

                                            return blockModelGenerators
                                                    .createSuffixedVariant(cropBlock, "_stage" + i, modelBuilder.build(), TextureMapping::crop);
                                        }
                                );
                                return Variant.variant().with(VariantProperties.MODEL, resourcelocation);
                            }
                    );
            blockModelGenerators.registerSimpleFlatItemModel(cropBlock.asItem());
            blockModelGenerators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(cropBlock).with(propertydispatch));
        }
    }

    public void createCutoutPlantWithDefaultItem(BlockModelGenerators blockModelGenerators, Block block, Block pottedBlock, PlantType plantType){
        blockModelGenerators.registerSimpleItemModel(block.asItem(), plantType.createItemModel(blockModelGenerators, block));
        createCutoutPlant(blockModelGenerators, block, pottedBlock, plantType);
    }

    public void createCutoutPlant(BlockModelGenerators blockModelGenerators, Block block, Block pottedBlock, PlantType plantType){
        createCutoutCrossBlock(blockModelGenerators, block, plantType);
        TextureMapping texturemapping = plantType.getPlantTextureMapping(block);
        var modelBuilder = ExtendedModelTemplateBuilder.of(plantType.getCrossPot());
        modelBuilder.renderType("minecraft:cutout");
        ResourceLocation resourcelocation = modelBuilder.build().create(pottedBlock, texturemapping, blockModelGenerators.modelOutput);
        blockModelGenerators.blockStateOutput.accept(createSimpleBlock(pottedBlock, resourcelocation));
    }

    public void createCutoutCrossBlock(BlockModelGenerators blockModelGenerators, Block block, BlockModelGenerators.PlantType plantType) {
        TextureMapping texturemapping = plantType.getTextureMapping(block);
        createCutoutCrossBlock(blockModelGenerators, block, plantType, texturemapping);
    }

    public void createCutoutCrossBlock(BlockModelGenerators blockModelGenerators, Block block, BlockModelGenerators.PlantType plantType, TextureMapping textureMapping) {
        ResourceLocation resourcelocation = plantType.getCross().extend().renderType("minecraft:cutout").build().create(block, textureMapping, blockModelGenerators.modelOutput);
        blockModelGenerators.blockStateOutput.accept(createSimpleBlock(block, resourcelocation));
    }

    public void createHorizontalDirectionalBlockWithCustomModel(BlockModelGenerators blockModels, Block block){
        ResourceLocation resourcelocation = ModelLocationUtils.getModelLocation(block);
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.multiVariant(block, Variant.variant()
                        .with(VariantProperties.MODEL, resourcelocation))
                        .with(createHorizontalFacingDispatch())
        );
    }

    public void createLittleManStatue(BlockModelGenerators blockModels, Block block){
        ResourceLocation regular = ModelLocationUtils.getModelLocation(block );
        ResourceLocation summoning = ModelLocationUtils.getModelLocation(block, "_summoning");
        blockModels.blockStateOutput
                .accept(
                        MultiVariantGenerator.multiVariant(block)
                                .with(createHorizontalFacingDispatch())
                                .with(createBooleanModelDispatch(SUMMONING, summoning, regular))
                );
    }

    public void createDoubleBlock(BlockModelGenerators blockModels, Block block){
        ResourceLocation top = ModelLocationUtils.getModelLocation(block, "_top");
        ResourceLocation bottom = ModelLocationUtils.getModelLocation(block, "_bottom");
        blockModels.blockStateOutput
                .accept(
                        MultiVariantGenerator.multiVariant(block)
                                .with(
                                        PropertyDispatch.property(BlockStateProperties.DOUBLE_BLOCK_HALF)
                                                .select(DoubleBlockHalf.LOWER, Variant.variant().with(VariantProperties.MODEL, bottom))
                                                .select(DoubleBlockHalf.UPPER, Variant.variant().with(VariantProperties.MODEL, top))
                                )
                );
    }

    public void createHorizontallyFacingDoubleBlock(BlockModelGenerators blockModels, Block block){
        ResourceLocation top = ModelLocationUtils.getModelLocation(block, "_top");
        ResourceLocation bottom = ModelLocationUtils.getModelLocation(block, "_bottom");
        blockModels.blockStateOutput
                .accept(
                        MultiVariantGenerator.multiVariant(block)
                                .with(
                                        PropertyDispatch.property(BlockStateProperties.DOUBLE_BLOCK_HALF)
                                                .select(DoubleBlockHalf.LOWER, Variant.variant().with(VariantProperties.MODEL, bottom))
                                                .select(DoubleBlockHalf.UPPER, Variant.variant().with(VariantProperties.MODEL, top))
                                )
                                .with(createHorizontalFacingDispatch())
                );
    }

    public void createHollowTreeBlock(BlockModelGenerators blockModels, Block block){
        ResourceLocation connecting = ModelLocationUtils.getModelLocation(block);
        ResourceLocation end = ModelLocationUtils.getModelLocation(block, "_connected");
        blockModels.blockStateOutput
                .accept(
                        MultiVariantGenerator.multiVariant(block)
                                .with(createBooleanModelDispatch(CONNECTED, end, connecting))
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


    public static ResourceLocation getPrefixedModelLocation(Block block, String prefix, String suffix) {
        ResourceLocation resourcelocation = BuiltInRegistries.BLOCK.getKey(block);
        return resourcelocation.withPath((p_388420_) -> "block/" + prefix + p_388420_ + suffix);
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

    public void createTeleportationPad(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block block) {
        ResourceLocation modelLocation = ModelLocationUtils.getModelLocation(block);
        ResourceLocation lit = ModelLocationUtils.getModelLocation(block, "_lit");
        ResourceLocation locked = ModelLocationUtils.getModelLocation(block, "_locked");

        blockModels.blockStateOutput
                .accept(
                        MultiVariantGenerator.multiVariant(block)
                                .with(createHorizontalFacingDispatch())
                                .with(
                                        PropertyDispatch.properties(BlockStateProperties.LIT, UNLOCKED)
                                                .select(
                                                        false, false,
                                                        Variant.variant().with(VariantProperties.MODEL, locked)
                                                )
                                                .select(
                                                        true, false,
                                                        Variant.variant().with(VariantProperties.MODEL, locked)
                                                )
                                                .select(
                                                        false, true,
                                                        Variant.variant().with(VariantProperties.MODEL, modelLocation)
                                                )
                                                .select(
                                                        true, true,
                                                        Variant.variant().with(VariantProperties.MODEL, lit)
                                                )
                                )
                );
    }

    public void createAncientAltar(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block block) {
        ResourceLocation modelLocation = ModelLocationUtils.getModelLocation(block);
        ResourceLocation crafting = ModelLocationUtils.getModelLocation(block, "_crafting");
        ResourceLocation filled = ModelLocationUtils.getModelLocation(block, "_filled");
        ResourceLocation craftingFilled = ModelLocationUtils.getModelLocation(block, "_crafting_filled");
        ResourceLocation pyrite = ModelLocationUtils.getModelLocation(block, "_pyrite");
        ResourceLocation pyriteFilled = ModelLocationUtils.getModelLocation(block, "_pyrite_filled");

        blockModels.blockStateOutput
                .accept(
                        MultiVariantGenerator.multiVariant(block)
                                .with(
                                        PropertyDispatch.properties(CRAFTING, BLOOD_INSIDE, PYRITE_INSIDE)
                                                .select(
                                                        false, false, false,
                                                        Variant.variant().with(VariantProperties.MODEL, modelLocation)
                                                )
                                                .select(
                                                        true, false, false,
                                                        Variant.variant().with(VariantProperties.MODEL, crafting)
                                                )
                                                .select(
                                                        false, true, false,
                                                        Variant.variant().with(VariantProperties.MODEL, filled)
                                                )
                                                .select(
                                                        true, true, false,
                                                        Variant.variant().with(VariantProperties.MODEL, craftingFilled)
                                                )
                                                .select(
                                                        false, false, true,
                                                        Variant.variant().with(VariantProperties.MODEL, pyrite)
                                                )
                                                .select(
                                                        true, false, true,
                                                        Variant.variant().with(VariantProperties.MODEL, crafting)
                                                )
                                                .select(
                                                        false, true, true,
                                                        Variant.variant().with(VariantProperties.MODEL, pyriteFilled)
                                                )
                                                .select(
                                                        true, true, true,
                                                        Variant.variant().with(VariantProperties.MODEL, craftingFilled)
                                                )
                                )
                );
    }

    public void createMagnifyingGlassBlock(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block block) {
        ResourceLocation resourcelocation = ModelLocationUtils.getModelLocation(block);
        ResourceLocation resourcelocation1 = ModelLocationUtils.getModelLocation(block, "_inserted");

        blockModels.blockStateOutput
                .accept(
                        MultiVariantGenerator.multiVariant(block)
                                .with(createBooleanModelDispatch(MagnifyingGlassStandBlock.MAGNIFYNG_GLASS_INSIDE, resourcelocation1, resourcelocation))
                                .with(createHorizontalFacingDispatch())
                );

        // Texture is available but not good, so we use 3d block model
        //ItemModel.Unbaked itemmodel$unbaked = ItemModelUtils.plainModel(itemModels.createFlatItemModel(block.asItem(), ModelTemplates.FLAT_ITEM));
        //itemModels.itemModelOutput.accept(block.asItem(), itemmodel$unbaked);

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

    public void createBushBlock(BlockModelGenerators blockModels, Block block) {
        blockModels.blockStateOutput
                .accept(
                        MultiVariantGenerator.multiVariant(block)
                                .with(
                                        PropertyDispatch.property(BlockStateProperties.AGE_3)
                                                .generate(
                                                        p_388136_ -> Variant.variant()
                                                                .with(
                                                                        VariantProperties.MODEL,
                                                                        blockModels.createSuffixedVariant(
                                                                                block, "_stage" + p_388136_, ModelTemplates.CROSS.extend().renderType("minecraft:cutout").build(), TextureMapping::cross
                                                                        )
                                                                )
                                                )
                                )
                );
    }

    public void createPottedBushBlock(BlockModelGenerators blockModels, Block block) {
        blockModels.blockStateOutput
                .accept(
                        MultiVariantGenerator.multiVariant(block)
                                .with(
                                        PropertyDispatch.property(BlockStateProperties.AGE_3)
                                                .generate(
                                                        integer -> Variant.variant()
                                                                .with(
                                                                        VariantProperties.MODEL,
                                                                        getPrefixedModelLocation(block, "crops/", "_stage" + integer)
                                                                )
                                                )
                                )
                );
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        event.createProvider(ModModelProvider::new);
    }


}
