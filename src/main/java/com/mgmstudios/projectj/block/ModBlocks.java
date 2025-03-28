package com.mgmstudios.projectj.block;

import com.google.common.collect.ImmutableMap;
import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.block.custom.*;
import com.mgmstudios.projectj.item.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Map;
import java.util.function.ToIntFunction;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(ProjectJ.MOD_ID);



    public static final DeferredBlock<Block> RAW_ADOBE = registerBlock("raw_adobe",
            BlockBehaviour.Properties.of().mapColor(MapColor.CLAY).instrument(NoteBlockInstrument.FLUTE).strength(0.6F).sound(SoundType.GRAVEL)
    );

    public static final DeferredBlock<Block> ADOBE_BRICKS = registerBlock("adobe_bricks",
             BlockBehaviour.Properties.ofFullCopy(Blocks.MUD_BRICKS)
    );


    public static final DeferredBlock<Block> ADOBE_BRICKS_STAIRS = registerStairBlock("adobe_bricks_stairs",
            BlockBehaviour.Properties.ofFullCopy(Blocks.MUD_BRICK_STAIRS),
            new Item.Properties()
    );

    public static final DeferredBlock<Block> ADOBE_BRICKS_SLAB = registerSlabBlock("adobe_bricks_slab",
            BlockBehaviour.Properties.ofFullCopy(Blocks.MUD_BRICK_SLAB),
            new Item.Properties()
    );

    public static final DeferredBlock<Block> ADOBE_BRICKS_WALL = registerWallBlock("adobe_bricks_wall",
            BlockBehaviour.Properties.ofFullCopy(Blocks.MUD_BRICK_WALL),
            new Item.Properties()
    );


    public static final DeferredBlock<Block> JADE_ORE = registerDropExperienceBlock("jade_ore",
            BlockBehaviour.Properties.of().
                    mapColor(MapColor.STONE).
                    instrument(NoteBlockInstrument.BASEDRUM).
                    requiresCorrectToolForDrops().
                    strength(3.0F, 3.0F)
    );

    public static final DeferredBlock<Block> JADE_BLOCK = registerBlock("jade_block",
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                    .requiresCorrectToolForDrops()
                    .strength(5.0F, 6.0F)
                    .sound(SoundType.METAL),
            new Item.Properties().rarity(Rarity.RARE)
    );

    public static final DeferredBlock<Block> DEEPSLATE_JADE_ORE = registerDropExperienceBlock("deepslate_jade_ore",
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.DEEPSLATE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops()
                    .strength(3.0F, 6.0F)
                    .sound(SoundType.DEEPSLATE)
    );

    public static final DeferredBlock<Block> ADOBE_FURNACE = registerFurnaceBlock("adobe_furnace",
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.STONE)
                .instrument(NoteBlockInstrument.BASEDRUM)
                .requiresCorrectToolForDrops()
                .strength(3.5F)
                    .lightLevel(litBlockEmission(7))
                    .noOcclusion()
    );

    public static final DeferredBlock<Block> CHIMNEY = registerChimneyBlock("chimney",
            BlockBehaviour.Properties.of()
                    .noOcclusion()
    );

    public static final DeferredBlock<Block> REGENERATION_OLMEC_HEAD = registerOlmecHeadBlock("regeneration_olmec_head",
            ParticleTypes.HAPPY_VILLAGER,
            MobEffects.REGENERATION,
            20
    );

    public static final DeferredBlock<Block> DAMAGE_OLMEC_HEAD = registerOlmecHeadBlock("damage_olmec_head",
            DustParticleOptions.REDSTONE,
            MobEffects.DAMAGE_BOOST,
            20
    );

    public static final DeferredBlock<Block> CONDUIT_OLMEC_HEAD = registerOlmecHeadBlock("conduit_olmec_head",
            ParticleTypes.BUBBLE,
            MobEffects.CONDUIT_POWER,
            20
    );

    public static final DeferredBlock<Block> RESISTANT_OLMEC_HEAD = registerOlmecHeadBlock("resistant_olmec_head",
            ParticleTypes.ELECTRIC_SPARK,
            MobEffects.DAMAGE_RESISTANCE,
            20
    );

    public static final DeferredBlock<Block> SERPENTINITE_BRICKS = registerBlock("serpentinite_bricks",
            BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)
    );

    public static final DeferredBlock<Block> SERPENTINITE_BRICKS_STAIRS = registerStairBlock("serpentinite_bricks_stairs",
            BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_STAIRS),
            new Item.Properties()
    );

    public static final DeferredBlock<Block> SERPENTINITE_BRICKS_SLAB = registerSlabBlock("serpentinite_bricks_slab",
            BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_SLAB),
            new Item.Properties()
    );

    public static final DeferredBlock<Block> SERPENTINITE_BRICKS_WALL = registerWallBlock("serpentinite_bricks_wall",
            BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL),
            new Item.Properties()
    );

    public static final DeferredBlock<Block> SERPENTINITE_ROCK = registerBlock("serpentinite_rock",
            BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
    );

   public static final DeferredBlock<Block> SERPENTINITE_ROCK_STAIRS = registerStairBlock("serpentinite_rock_stairs",
            BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_STAIRS),
            new Item.Properties()
   );

    public static final DeferredBlock<Block> SERPENTINITE_ROCK_SLAB = registerSlabBlock("serpentinite_rock_slab",
            BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_SLAB),
            new Item.Properties()
    );

    public static final DeferredBlock<Block> SERPENTINITE_ROCK_WALL = registerWallBlock("serpentinite_rock_wall",
            BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL),
            new Item.Properties()
    );

    public static final DeferredBlock<Block> ANCIENT_ALTAR = registerAncientAltarBlock("ancient_altar",
            BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).noOcclusion(),
            new Item.Properties().rarity(Rarity.EPIC)
    );

    public static final DeferredBlock<Block> SERPENTINITE_PILLAR = registerMultiAxisDirectionalBlock("serpentinite_pillar",
            BlockBehaviour.Properties.ofFullCopy(Blocks.QUARTZ_PILLAR), new Item.Properties());

    public static final DeferredBlock<Block> COBBLED_SERPENTINITE = registerBlock("cobbled_serpentinite",
            BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE), new Item.Properties());

    public static final DeferredBlock<Block> COBBLED_SERPENTINITE_STAIRS = registerStairBlock("cobbled_serpentinite_stairs",
            BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_STAIRS),
            new Item.Properties()
    );

    public static final DeferredBlock<Block> COBBLED_SERPENTINITE_SLAB = registerSlabBlock("cobbled_serpentinite_slab",
            BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_SLAB),
            new Item.Properties()
    );

    public static final DeferredBlock<Block> COBBLED_SERPENTINITE_WALL = registerWallBlock("cobbled_serpentinite_wall",
            BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL),
            new Item.Properties()
    );

    public static final DeferredBlock<Block> STRIPPED_MESQUITE_LOG = registerRotatedPillarBlock("stripped_mesquite_log", BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG), new Item.Properties());

    public static final DeferredBlock<Block> MESQUITE_LOG = registerStrippableModLogBlock("mesquite_log", BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG), new Item.Properties());

    public static final DeferredBlock<Block> MESQUITE_PLANKS = registerBlock("mesquite_planks", BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));

    public static final DeferredBlock<Block> MESQUITE_STAIRS = registerStairBlock("mesquite_stairs", BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS), new Item.Properties());

    public static final DeferredBlock<Block> MESQUITE_SLAB = registerSlabBlock("mesquite_slab", BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB), new Item.Properties());

    public static final DeferredBlock<Block> MESQUITE_FENCE = registerFenceBlock("mesquite_fence", BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE), new Item.Properties());

    public static final DeferredBlock<Block> MESQUITE_FENCE_GATE = registerFenceGateBlock("mesquite_fence_gate", BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE), new Item.Properties());


    public static final DeferredBlock<Block> TALL_STATUE = registerTallBlock("tall_statue",
            BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).noOcclusion(),
            new Item.Properties()
    );


    private static ToIntFunction<BlockState> litBlockEmission(int lightValue) {
        return properties -> properties.getValue(BlockStateProperties.LIT) ? lightValue : 0;
    }

    private static DeferredBlock<Block> registerAncientAltarBlock(String name, BlockBehaviour.Properties properties, Item.Properties itemProperties) {
        DeferredBlock<Block> toBeRegistered =  BLOCKS.register(name, registryName -> new AncientAltarBlock(properties.setId(ResourceKey.create(Registries.BLOCK, registryName))));
        ModItems.ITEMS.registerSimpleBlockItem(toBeRegistered, itemProperties);
        return toBeRegistered;
    }

    private static DeferredBlock<Block> registerTallBlock(String name, BlockBehaviour.Properties properties, Item.Properties itemProperties) {
        DeferredBlock<Block> toBeRegistered =  BLOCKS.register(name, registryName -> new TallBlock(properties.setId(ResourceKey.create(Registries.BLOCK, registryName))));
        ModItems.ITEMS.registerSimpleBlockItem(toBeRegistered, itemProperties);
        return toBeRegistered;
    }

    private static DeferredBlock<Block> registerBlock(String name, BlockBehaviour.Properties properties, Item.Properties itemProperties) {
        DeferredBlock<Block> toBeRegistered =  BLOCKS.register(name, registryName -> new Block(properties.setId(ResourceKey.create(Registries.BLOCK, registryName))));
        ModItems.ITEMS.registerSimpleBlockItem(toBeRegistered, itemProperties);
        return toBeRegistered;
    }

    private static DeferredBlock<Block> registerStairBlock(String name, BlockBehaviour.Properties properties, Item.Properties itemProperties) {
        DeferredBlock<Block> toBeRegistered =  BLOCKS.register(name, registryName -> new StairBlock(Blocks.COBBLESTONE.defaultBlockState(), properties.setId(ResourceKey.create(Registries.BLOCK, registryName))));
        ModItems.ITEMS.registerSimpleBlockItem(toBeRegistered, itemProperties);
        return toBeRegistered;
    }

    private static DeferredBlock<Block> registerSlabBlock(String name, BlockBehaviour.Properties properties, Item.Properties itemProperties) {
        DeferredBlock<Block> toBeRegistered =  BLOCKS.register(name, registryName -> new SlabBlock(properties.setId(ResourceKey.create(Registries.BLOCK, registryName))));
        ModItems.ITEMS.registerSimpleBlockItem(toBeRegistered, itemProperties);
        return toBeRegistered;
    }

    private static DeferredBlock<Block> registerWallBlock(String name, BlockBehaviour.Properties properties, Item.Properties itemProperties) {
        DeferredBlock<Block> toBeRegistered =  BLOCKS.register(name, registryName -> new WallBlock(properties.setId(ResourceKey.create(Registries.BLOCK, registryName))));
        ModItems.ITEMS.registerSimpleBlockItem(toBeRegistered, itemProperties);
        return toBeRegistered;
    }

    private static DeferredBlock<Block> registerBlock(String name, BlockBehaviour.Properties properties) {
        return registerBlock(name, properties, new Item.Properties());
    }

    private static DeferredBlock<Block> registerBigOlmecHeadBlock(String name, BlockBehaviour.Properties properties, Item.Properties itemProperties) {
        DeferredBlock<Block> toBeRegistered =  BLOCKS.register(name, registryName -> new BigOlmecHeadBlock(properties.setId(ResourceKey.create(Registries.BLOCK, registryName))));
        ModItems.ITEMS.registerSimpleBlockItem(toBeRegistered, itemProperties);
        return toBeRegistered;
    }

    private static DeferredBlock<Block> registerBigOlmecHeadBlock(String name, BlockBehaviour.Properties properties) {
        return registerBigOlmecHeadBlock(name, properties, new Item.Properties());
    }

    private static DeferredBlock<Block> registerOlmecHeadBlock(String name, BlockBehaviour.Properties properties, Item.Properties itemProperties, ParticleOptions particleOptions, Holder<MobEffect> effect, int effectTime) {
        DeferredBlock<Block> toBeRegistered =  BLOCKS.register(name, registryName ->
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

    private static DeferredBlock<Block> registerFurnaceBlock(String name, BlockBehaviour.Properties properties) {
        DeferredBlock<Block> toBeRegistered =  BLOCKS.register(name, registryName -> new AdobeFurnaceBlock(properties.setId(ResourceKey.create(Registries.BLOCK, registryName))));
        ModItems.ITEMS.registerSimpleBlockItem(toBeRegistered);
        return toBeRegistered;
    }

    private static DeferredBlock<Block> registerChimneyBlock(String name, BlockBehaviour.Properties properties) {
        DeferredBlock<Block> toBeRegistered =  BLOCKS.register(name, registryName -> new AdobeChimneyBlock(properties.setId(ResourceKey.create(Registries.BLOCK, registryName))));
        ModItems.ITEMS.registerSimpleBlockItem(toBeRegistered);
        return toBeRegistered;
    }

    private static DeferredBlock<Block> registerRotatedPillarBlock(String name, BlockBehaviour.Properties properties, Item.Properties itemProperties) {
        DeferredBlock<Block> toBeRegistered =  BLOCKS.register(name, registryName -> new RotatedPillarBlock(properties.setId(ResourceKey.create(Registries.BLOCK, registryName))));
        ModItems.ITEMS.registerSimpleBlockItem(toBeRegistered, itemProperties);
        return toBeRegistered;
    }

    private static DeferredBlock<Block> registerMultiAxisDirectionalBlock(String name, BlockBehaviour.Properties properties, Item.Properties itemProperties) {
        DeferredBlock<Block> toBeRegistered =  BLOCKS.register(name, registryName -> new MultiAxisDirectionalBlock(properties.setId(ResourceKey.create(Registries.BLOCK, registryName))));
        ModItems.ITEMS.registerSimpleBlockItem(toBeRegistered, itemProperties);
        return toBeRegistered;
    }

    private static DeferredBlock<Block> registerFenceBlock(String name, BlockBehaviour.Properties properties, Item.Properties itemProperties) {
        DeferredBlock<Block> toBeRegistered =  BLOCKS.register(name, registryName -> new FenceBlock(properties.setId(ResourceKey.create(Registries.BLOCK, registryName))));
        ModItems.ITEMS.registerSimpleBlockItem(toBeRegistered, itemProperties);
        return toBeRegistered;
    }

    private static DeferredBlock<Block> registerFenceGateBlock(String name, BlockBehaviour.Properties properties, Item.Properties itemProperties) {
        DeferredBlock<Block> toBeRegistered = BLOCKS.register(name, registryName -> new FenceGateBlock(WoodType.OAK, properties.setId(ResourceKey.create(Registries.BLOCK, registryName))));
        ModItems.ITEMS.registerSimpleBlockItem(toBeRegistered, itemProperties);
        return toBeRegistered;
    }

    private static DeferredBlock<Block> registerStrippableModLogBlock(String name, BlockBehaviour.Properties properties, Item.Properties itemProperties) {
        DeferredBlock<Block> toBeRegistered = BLOCKS.register(name, registryName -> new StrippableModLogBlock(properties.setId(ResourceKey.create(Registries.BLOCK, registryName))));
        ModItems.ITEMS.registerSimpleBlockItem(toBeRegistered, itemProperties);
        return toBeRegistered;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);

    }
}