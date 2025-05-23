package com.mgmstudios.projectj.block;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.block.custom.*;

import static com.mgmstudios.projectj.block.custom.AncientAltarBlock.PYRITE_INSIDE;
import static com.mgmstudios.projectj.block.custom.TeleportationBlock.UNLOCKED;
import com.mgmstudios.projectj.block.custom.botany.BotanyBushBlock;
import com.mgmstudios.projectj.block.custom.botany.BotanyPotBlock;
import com.mgmstudios.projectj.fluid.ModFluids;
import com.mgmstudios.projectj.item.ModItems;
import com.mgmstudios.projectj.item.custom.AncientAltarBlockItem;
import com.mgmstudios.projectj.worldgen.feature.ModTreeGrowers;

import net.minecraft.core.Holder;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.ColoredFallingBlock;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(ProjectJ.MOD_ID);

    public static final DeferredBlock<Block> PACKED_ADOBE = register("packed_adobe", BlockBehaviour.Properties.ofFullCopy(Blocks.PACKED_MUD));

    public static final DeferredBlock<Block> ADOBE_SAND = register("adobe_sand", (properties) -> new ColoredFallingBlock(new ColorRGBA(11098145), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.SAND));

    public static final DeferredBlock<Block> ADOBE_BRICKS = register("adobe_bricks", BlockBehaviour.Properties.ofFullCopy(Blocks.MUD_BRICKS));

    public static final DeferredBlock<Block> ADOBE_BRICKS_STAIRS = registerStairBlock("adobe_bricks_stairs", BlockBehaviour.Properties.ofFullCopy(Blocks.MUD_BRICK_STAIRS), new Item.Properties());

    public static final DeferredBlock<Block> ADOBE_BRICKS_SLAB = register("adobe_bricks_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.MUD_BRICK_SLAB));

    public static final DeferredBlock<Block> ADOBE_BRICKS_WALL = register("adobe_bricks_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.MUD_BRICK_WALL));

    public static final DeferredBlock<Block> JADE_ORE = registerDropExperienceBlock("jade_ore", BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE));

    public static final DeferredBlock<Block> JADE_BLOCK = register("jade_block", BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK),new Item.Properties().rarity(Rarity.RARE));

    public static final DeferredBlock<Block> DEEPSLATE_JADE_ORE = registerDropExperienceBlock("deepslate_jade_ore", BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE));

    public static final DeferredBlock<Block> SERPENTINITE_JADE_ORE = registerDropExperienceBlock("serpentinite_jade_ore", BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE));

    public static final DeferredBlock<Block> ADOBE_FURNACE = register("adobe_furnace", AdobeFurnaceBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.FURNACE).noOcclusion());

    public static final DeferredBlock<Block> CHIMNEY = register("chimney", AdobeChimneyBlock::new, BlockBehaviour.Properties.of().noOcclusion());

    public static final DeferredBlock<Block> REGENERATION_OLMEC_HEAD = registerOlmecHeadBlock("regeneration_olmec_head", ParticleTypes.HAPPY_VILLAGER, MobEffects.REGENERATION, 20);

    public static final DeferredBlock<Block> DAMAGE_OLMEC_HEAD = registerOlmecHeadBlock("damage_olmec_head", DustParticleOptions.REDSTONE, MobEffects.DAMAGE_BOOST, 20);

    public static final DeferredBlock<Block> CONDUIT_OLMEC_HEAD = registerOlmecHeadBlock("conduit_olmec_head", ParticleTypes.BUBBLE, MobEffects.CONDUIT_POWER, 20);

    public static final DeferredBlock<Block> RESISTANT_OLMEC_HEAD = registerOlmecHeadBlock("resistant_olmec_head", ParticleTypes.ELECTRIC_SPARK, MobEffects.DAMAGE_RESISTANCE, 20);

    public static final DeferredBlock<Block> SERPENTINITE_BRICKS = register("serpentinite_bricks", BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS));

    public static final DeferredBlock<Block> SERPENTINITE_BRICKS_STAIRS = registerStairBlock("serpentinite_bricks_stairs", BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_STAIRS), new Item.Properties());

    public static final DeferredBlock<Block> SERPENTINITE_BRICKS_SLAB = register("serpentinite_bricks_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_SLAB));

    public static final DeferredBlock<Block> SERPENTINITE_BRICKS_WALL = register("serpentinite_bricks_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL));

    public static final DeferredBlock<Block> SERPENTINITE_ROCK = register("serpentinite_rock", BlockBehaviour.Properties.ofFullCopy(Blocks.STONE));

   public static final DeferredBlock<Block> SERPENTINITE_ROCK_STAIRS = registerStairBlock("serpentinite_rock_stairs", BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_STAIRS), new Item.Properties());

    public static final DeferredBlock<Block> SERPENTINITE_ROCK_SLAB = register("serpentinite_rock_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_SLAB), new Item.Properties());

    public static final DeferredBlock<Block> SERPENTINITE_ROCK_WALL = register("serpentinite_rock_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL), new Item.Properties());

    public static final DeferredBlock<Block> SERPENTINITE_ROCK_BUTTON = registerButtonBlock("serpentinite_rock_button", BlockSetType.STONE, 20, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BUTTON), new Item.Properties());

    public static final DeferredBlock<Block> SERPENTINITE_ROCK_PRESSURE_PLATE = registerPressurePlateBlock("serpentinite_rock_pressure_plate", BlockSetType.STONE, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BUTTON), new Item.Properties());

    public static final DeferredBlock<Block> OLMEC_ALTAR = register("olmec_altar", OlmecAltarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).noOcclusion(), new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredBlock<Block> SERPENTINITE_PILLAR = register("serpentinite_pillar", MultiAxisDirectionalBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.QUARTZ_PILLAR), new Item.Properties());

    public static final DeferredBlock<Block> COBBLED_SERPENTINITE = register("cobbled_serpentinite", BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE));

    public static final DeferredBlock<Block> COBBLED_SERPENTINITE_STAIRS = registerStairBlock("cobbled_serpentinite_stairs", BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_STAIRS), new Item.Properties());

    public static final DeferredBlock<Block> COBBLED_SERPENTINITE_SLAB = register("cobbled_serpentinite_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_SLAB));

    public static final DeferredBlock<Block> COBBLED_SERPENTINITE_WALL = register("cobbled_serpentinite_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL));

    public static final DeferredBlock<Block> POLISHED_SERPENTINITE = register("polished_serpentinite", BlockBehaviour.Properties.ofFullCopy(Blocks.SMOOTH_STONE));

    public static final DeferredBlock<Block> POLISHED_SERPENTINITE_STAIRS = registerStairBlock("polished_serpentinite_stairs", BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_STAIRS), new Item.Properties());

    public static final DeferredBlock<Block> POLISHED_SERPENTINITE_SLAB = register("polished_serpentinite_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_SLAB));

    public static final DeferredBlock<Block> POLISHED_SERPENTINITE_WALL = register("polished_serpentinite_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL));

    public static final DeferredBlock<Block> MESQUITE_LOG = register("mesquite_log", StrippableModLogBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG), new Item.Properties());

    public static final DeferredBlock<Block> STRIPPED_MESQUITE_LOG = register("stripped_mesquite_log", RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG), new Item.Properties());

    public static final DeferredBlock<Block> MESQUITE_WOOD = register("mesquite_wood", StrippableModLogBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG), new Item.Properties());

    public static final DeferredBlock<Block> STRIPPED_MESQUITE_WOOD = register("stripped_mesquite_wood", StrippableModLogBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG), new Item.Properties());

    public static final DeferredBlock<Block> MESQUITE_PLANKS = register("mesquite_planks", BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));

    public static final DeferredBlock<Block> MESQUITE_STAIRS = registerStairBlock("mesquite_stairs", BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS), new Item.Properties());

    public static final DeferredBlock<Block> MESQUITE_SLAB = register("mesquite_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB), new Item.Properties());

    public static final DeferredBlock<Block> MESQUITE_FENCE = register("mesquite_fence", FenceBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE), new Item.Properties());

    public static final DeferredBlock<Block> MESQUITE_FENCE_GATE = registerFenceGateBlock("mesquite_fence_gate", BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE), new Item.Properties());

    public static final DeferredBlock<Block> MESQUITE_BUTTON = registerButtonBlock("mesquite_button", BlockSetType.OAK, 30, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE), new Item.Properties());

    public static final DeferredBlock<Block> MESQUITE_PRESSURE_PLATE = registerPressurePlateBlock("mesquite_pressure_plate", BlockSetType.OAK,  BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE), new Item.Properties());

    public static final DeferredBlock<Block> MESQUITE_LEAVES = register("mesquite_leaves", LeavesBlock::new, leavesProperties(SoundType.AZALEA_LEAVES), new Item.Properties());

    public static final DeferredBlock<Block> MESQUITE_SAPLING = registerSaplingBlock("mesquite_sapling", BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING), new Item.Properties());

    public static final DeferredBlock<Block> POTTED_MESQUITE_SAPLING = registerPottedSaplingBlock("potted_mesquite_sapling", flowerPotProperties(), new Item.Properties());

    public static final DeferredBlock<Block> SNAKE_STATUE = register("snake_statue", TallStatueBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).noOcclusion(), new Item.Properties().rarity(Rarity.RARE));

    public static final DeferredBlock<Block> MESQUITE_BRAZIER = register("mesquite_brazier", BrazierBlock::new,  BlockBehaviour.Properties.of().mapColor(MapColor.PODZOL).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).lightLevel(state -> 15).noOcclusion().ignitedByLava());

    public static final DeferredBlock<Block> SERPENTINITE_BENCH = register("serpentinite_bench", SittableBlock::new,  BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_SLAB).noOcclusion());

    public static final DeferredBlock<Block> SERPENTINITE_BENCH_CORNER = register("serpentinite_bench_corner", BenchCornerBlock::new,  BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_SLAB).noOcclusion());

    public static final DeferredBlock<Block> MESQUITE_BENCH = register("mesquite_bench", SittableBlock::new,  BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_SLAB).noOcclusion());

    public static final DeferredBlock<Block> MESQUITE_BENCH_CORNER = register("mesquite_bench_corner", BenchCornerBlock::new,  BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_SLAB).noOcclusion());

    public static final DeferredBlock<Block> PYRITE_BLOCK = register("pyrite_block", BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK));

    public static final DeferredBlock<Block> RAW_PYRITE_BLOCK = register("raw_pyrite_block", BlockBehaviour.Properties.ofFullCopy(Blocks.RAW_GOLD_BLOCK));

    public static final DeferredBlock<Block> PYRITE_ORE = register("pyrite_ore", BlockBehaviour.Properties.ofFullCopy(Blocks.SANDSTONE));

    public static final DeferredBlock<LiquidPyriteBlock> LIQUID_PYRITE  = registerWithoutItem("liquid_pyrite", (properties) -> new LiquidPyriteBlock(ModFluids.PYRITE.get(), properties), () -> Block.Properties.ofFullCopy(Blocks.LAVA));

    public static final DeferredBlock<Block> MAGNIFYING_GLASS_STAND = registerMagnifyingGlassStandBlock("magnifying_glass_stand", BlockBehaviour.Properties.of().sound(SoundType.WOOD).noOcclusion().randomTicks(), new Item.Properties(), 3);

    public static final DeferredBlock<Block> TELEPORTATION_PAD = register("teleportation_pad", TeleportationBlock::new, BlockBehaviour.Properties.of().explosionResistance(1200F).noOcclusion().lightLevel(teleportationPadEmission(10)));

    public static final DeferredBlock<Block> ANCIENT_ALTAR = registerWithCustomItem("ancient_altar", AncientAltarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BLACKSTONE).noOcclusion().lightLevel(ancientAltarBlockEmission(12)), AncientAltarBlockItem::new, new Item.Properties().rarity(Rarity.RARE));

    public static final DeferredBlock<Block> LITTLE_MAN_STATUE_BLOCK = register("little_man_statue_block", LittleManStatueBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BLACKSTONE).noOcclusion(), new Item.Properties());

    public static final DeferredBlock<Block> EMPTY_LITTLE_MAN_STATUE_BLOCK = register("empty_little_man_statue_block", EmptyLittleManStatueBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BLACKSTONE).noOcclusion(), new Item.Properties());

    public static final DeferredBlock<Block> METATE = register("metate", MetateBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BLACKSTONE).noOcclusion());

    public static final DeferredBlock<Block> BOTANY_POT = register("botany_pot", BotanyPotBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DECORATED_POT));

    public static final DeferredBlock<Block> MAIZE_CROP = registerBotanyPlant("maize_crop", ModItems.MAIZE, BlockBehaviour.Properties.ofFullCopy(Blocks.BEETROOTS));

    public static final DeferredBlock<Block> POTTED_MAIZE_CROP = registerPottedBotanyPlant("potted_maize_crop", ModItems.MAIZE, ModItems.MAIZE_SEEDS, BlockBehaviour.Properties.ofFullCopy(Blocks.BEETROOTS));

    public static final DeferredBlock<Block> CHILI_BUSH = registerBotanyPlant("chili_bush", ModItems.CHILI, BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH));

    public static final DeferredBlock<Block> POTTED_CHILI_BUSH = registerPottedBotanyPlant("potted_chili_bush", ModItems.CHILI, ModItems.CHILI_SEEDS, BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH));

    public static final DeferredBlock<Block> ADOBE_GLASS = register("adobe_glass", AdobeGlassBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS));

    public static final DeferredBlock<Block> TINTED_ADOBE_GLASS = register("tinted_adobe_glass", TintedAdobeGlassBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.TINTED_GLASS));

    public static final DeferredBlock<Block> JADE_CRYSTAL = register("jade_crystal", JadeCrystalBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK).noOcclusion().requiresCorrectToolForDrops().strength(5.0F), new Item.Properties());

    public static final DeferredBlock<Block> HOLLOW_MESQUITE_LOG = register("hollow_mesquite_log", HollowTreeBlock::new, BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava());

    public static final DeferredBlock<Block> CHARCOAL_BLOCK = register("charcoal_block", BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD));

    public static final DeferredBlock<Block> SOCKET_WORKBENCH = register("socket_workbench", SocketWorkbenchBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL).noOcclusion());

    private static DeferredBlock<Block> register(String name, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties properties, Item.Properties itemProperties) {
        DeferredBlock<Block> toBeRegistered =  BLOCKS.register(name, registryName -> factory.apply(properties.setId(ResourceKey.create(Registries.BLOCK, registryName))));
        ModItems.ITEMS.registerSimpleBlockItem(toBeRegistered, itemProperties);

        return toBeRegistered;
    }

    private static DeferredBlock<Block> registerWithCustomItem(String name, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties properties, Function<Item.Properties, Item> itemFactory, Item.Properties itemProperties) {
        DeferredBlock<Block> toBeRegistered =  BLOCKS.register(name, registryName -> factory.apply(properties.setId(ResourceKey.create(Registries.BLOCK, registryName))));
        String id = (toBeRegistered.unwrapKey().orElseThrow()).location().getPath();
        Objects.requireNonNull(toBeRegistered);
        ModItems.register(id, itemFactory, itemProperties);

        return toBeRegistered;
    }


    private static DeferredBlock<Block> registerWithoutItem(String name, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties properties) {
        return BLOCKS.register(name, registryName -> factory.apply(properties.setId(ResourceKey.create(Registries.BLOCK, registryName))));
    }

    private static DeferredBlock<Block> registerBotanyPlant(String name, Supplier<Item> item, BlockBehaviour.Properties properties) {
        return BLOCKS.register(name, registryName -> new BotanyBushBlock(item, properties.setId(ResourceKey.create(Registries.BLOCK, registryName))){
        });
    }

    private static DeferredBlock<Block> registerPottedBotanyPlant(String name, Supplier<Item> item, Supplier<Item> seeds, BlockBehaviour.Properties properties) {
        return BLOCKS.register(name, registryName -> new BotanyPotBlock(item, seeds, properties.setId(ResourceKey.create(Registries.BLOCK, registryName))){
        });
    }

    private static DeferredBlock<Block> register(String name, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties properties) {
        return register(name, factory, properties, new Item.Properties());
    }

    private static DeferredBlock<Block> register(String name, BlockBehaviour.Properties properties, Item.Properties itemProperties) {
        return register(name, Block::new, properties, itemProperties);
    }

    private static DeferredBlock<Block> register(String name, BlockBehaviour.Properties properties) {
        return register(name, Block::new, properties, new Item.Properties());
    }

    private static DeferredBlock<Block> registerStairBlock(String name, BlockBehaviour.Properties properties, Item.Properties itemProperties) {
        DeferredBlock<Block> toBeRegistered =  BLOCKS.register(name, registryName -> new StairBlock(Blocks.COBBLESTONE.defaultBlockState(), properties.setId(ResourceKey.create(Registries.BLOCK, registryName))));
        ModItems.ITEMS.registerSimpleBlockItem(toBeRegistered, itemProperties);
        return toBeRegistered;
    }

    private static DeferredBlock<Block> registerButtonBlock(String name, BlockSetType type, int tickStayPressed, BlockBehaviour.Properties properties, Item.Properties itemProperties) {
        DeferredBlock<Block> toBeRegistered =  BLOCKS.register(name, registryName -> new ButtonBlock(type, tickStayPressed, properties.setId(ResourceKey.create(Registries.BLOCK, registryName))));
        ModItems.ITEMS.registerSimpleBlockItem(toBeRegistered, itemProperties);
        return toBeRegistered;
    }

    private static DeferredBlock<Block> registerPressurePlateBlock(String name, BlockSetType type, BlockBehaviour.Properties properties, Item.Properties itemProperties) {
        DeferredBlock<Block> toBeRegistered =  BLOCKS.register(name, registryName -> new PressurePlateBlock(type, properties.setId(ResourceKey.create(Registries.BLOCK, registryName))));
        ModItems.ITEMS.registerSimpleBlockItem(toBeRegistered, itemProperties);
        return toBeRegistered;
    }

    private static DeferredBlock<Block> registerOlmecHeadBlock(String name, BlockBehaviour.Properties properties, Item.Properties itemProperties, ParticleOptions particleOptions, Holder<MobEffect> effect, int effectTime) {DeferredBlock<Block> toBeRegistered =  BLOCKS.register(name, registryName ->
                new OlmecHeadBlock(
                        properties.setId(ResourceKey.create(Registries.BLOCK, registryName)),
                        particleOptions,
                        effect,
                        effectTime
                ));

        ModItems.registerOlmecHeadItem(toBeRegistered, itemProperties, effect);
        return toBeRegistered;
    }


    private static DeferredBlock<Block> registerOlmecHeadBlock(String name, ParticleOptions particleOptions, Holder<MobEffect> effect, int effectTime) {
        BlockBehaviour.Properties properties =
                BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0F, 3.0F).sound(SoundType.GILDED_BLACKSTONE).lightLevel(litBlockEmission(15)).noOcclusion();
        Item.Properties itemProperties = new Item.Properties().rarity(Rarity.EPIC).equippableUnswappable(EquipmentSlot.HEAD);
        return registerOlmecHeadBlock(name, properties, itemProperties, particleOptions, effect, effectTime);
    }

    private static DeferredBlock<Block> registerDropExperienceBlock(String name, BlockBehaviour.Properties properties) {
        DeferredBlock<Block> toBeRegistered =  BLOCKS.register(name, registryName -> new DropExperienceBlock(
                ConstantInt.of(2),
                properties.setId(ResourceKey.create(Registries.BLOCK, registryName)))
        );
        ModItems.ITEMS.registerSimpleBlockItem(toBeRegistered);
        return toBeRegistered;
    }

    private static DeferredBlock<Block> registerFenceGateBlock(String name, BlockBehaviour.Properties properties, Item.Properties itemProperties) {
        DeferredBlock<Block> toBeRegistered = BLOCKS.register(name, registryName -> new FenceGateBlock(WoodType.OAK, properties.setId(ResourceKey.create(Registries.BLOCK, registryName))));
        ModItems.ITEMS.registerSimpleBlockItem(toBeRegistered, itemProperties);
        return toBeRegistered;
    }

    private static DeferredBlock<Block> registerSaplingBlock(String name, BlockBehaviour.Properties properties, Item.Properties itemProperties) {
        DeferredBlock<Block> toBeRegistered = BLOCKS.register(name, registryName -> new SaplingBlock(ModTreeGrowers.MESQUITE, properties.setId(ResourceKey.create(Registries.BLOCK, registryName))));
        ModItems.ITEMS.registerSimpleBlockItem(toBeRegistered, itemProperties);
        return toBeRegistered;
    }

    private static DeferredBlock<Block> registerPottedSaplingBlock(String name, BlockBehaviour.Properties properties, Item.Properties itemProperties) {
        DeferredBlock<Block> toBeRegistered = BLOCKS.register(name, registryName -> new FlowerPotBlock(ModBlocks.MESQUITE_SAPLING.get(), properties.setId(ResourceKey.create(Registries.BLOCK, registryName))));
        ModItems.ITEMS.registerSimpleBlockItem(toBeRegistered, itemProperties);
        return toBeRegistered;
    }

    private static DeferredBlock<Block> registerMagnifyingGlassStandBlock(String name, BlockBehaviour.Properties properties, Item.Properties itemProperties, int conversionThreshold) {
        DeferredBlock<Block> toBeRegistered = BLOCKS.register(name, registryName -> new MagnifyingGlassStandBlock(conversionThreshold, properties.setId(ResourceKey.create(Registries.BLOCK, registryName))));
        ModItems.ITEMS.registerSimpleBlockItem(toBeRegistered, itemProperties);
        return toBeRegistered;
    }

    private static DeferredBlock<LiquidBlock> registerLiquidBlock(String name, FlowingFluid fluid, BlockBehaviour.Properties properties){
        return BLOCKS.register(name, registryName -> new LiquidBlock(fluid, properties.setId(ResourceKey.create(Registries.BLOCK, registryName))));
    }

    private static <T extends Block> DeferredBlock<Block> registerWithoutItem(String name, Supplier<Block.Properties> properties) {
        return registerWithoutItem(name, Block::new, properties);
    }

    private static <T extends Block> DeferredBlock<T> registerWithoutItem(String name, Function<Block.Properties, T> builder, Supplier<Block.Properties> properties) {
        return registerWithoutItem(name, ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, name)), builder, properties);
    }

    private static <T extends Block> DeferredBlock<T> registerWithoutItem(String name, ResourceKey<Block> key, Function<Block.Properties, T> builder, Supplier<Block.Properties> properties) {
        return BLOCKS.register(name, () -> builder.apply(properties.get().setId(key)));
    }


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    private static ToIntFunction<BlockState> litBlockEmission(int lightValue) {
        return properties -> properties.getValue(BlockStateProperties.LIT) ? lightValue : 0;
    }

    private static ToIntFunction<BlockState> ancientAltarBlockEmission(int lightValue) {
        return properties -> properties.getValue(PYRITE_INSIDE) ? lightValue : 3;
    }

    private static ToIntFunction<BlockState> teleportationPadEmission(int lightValue) {
        return properties -> {
            boolean lit = properties.getValue(BlockStateProperties.LIT);
            boolean unlocked = properties.getValue(UNLOCKED);
            if (lit && unlocked)
                return lightValue;
            else if (lit){
               return (int)(lightValue * 0.75F);
            }
            return 0;
        };
    }

    private static BlockBehaviour.Properties leavesProperties(SoundType sound) {
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.PLANT)
                .strength(0.2F)
                .randomTicks()
                .sound(sound)
                .noOcclusion()
                .isValidSpawn(Blocks::ocelotOrParrot)
                .ignitedByLava()
                .pushReaction(PushReaction.DESTROY);
    }

    private static BlockBehaviour.Properties flowerPotProperties() {
        return BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY);
    }
}