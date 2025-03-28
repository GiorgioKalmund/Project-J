package com.mgmstudios.projectj.block;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.block.custom.*;
import com.mgmstudios.projectj.item.ModItems;
import com.mgmstudios.projectj.worldgen.tree.ModTreeGrowers;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.ToIntFunction;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(ProjectJ.MOD_ID);

    public static final DeferredBlock<Block> RAW_ADOBE = register("raw_adobe", BlockBehaviour.Properties.of().mapColor(MapColor.CLAY).instrument(NoteBlockInstrument.FLUTE).strength(0.6F).sound(SoundType.GRAVEL));

    public static final DeferredBlock<Block> ADOBE_BRICKS = register("adobe_bricks", BlockBehaviour.Properties.ofFullCopy(Blocks.MUD_BRICKS));

    public static final DeferredBlock<Block> ADOBE_BRICKS_STAIRS = registerStairBlock("adobe_bricks_stairs", BlockBehaviour.Properties.ofFullCopy(Blocks.MUD_BRICK_STAIRS), new Item.Properties());

    public static final DeferredBlock<Block> ADOBE_BRICKS_SLAB = register("adobe_bricks_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.MUD_BRICK_SLAB));

    public static final DeferredBlock<Block> ADOBE_BRICKS_WALL = register("adobe_bricks_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.MUD_BRICK_WALL));

    public static final DeferredBlock<Block> JADE_ORE = registerDropExperienceBlock("jade_ore", BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE));

    public static final DeferredBlock<Block> JADE_BLOCK = register("jade_block", BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK),new Item.Properties().rarity(Rarity.RARE));

    public static final DeferredBlock<Block> DEEPSLATE_JADE_ORE = registerDropExperienceBlock("deepslate_jade_ore", BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE));

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

    public static final DeferredBlock<Block> ANCIENT_ALTAR = register("ancient_altar", AncientAltarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).noOcclusion(), new Item.Properties().rarity(Rarity.EPIC));

    public static final DeferredBlock<Block> SERPENTINITE_PILLAR = register("serpentinite_pillar", MultiAxisDirectionalBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.QUARTZ_PILLAR), new Item.Properties());

    public static final DeferredBlock<Block> COBBLED_SERPENTINITE = register("cobbled_serpentinite", BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE));

    public static final DeferredBlock<Block> COBBLED_SERPENTINITE_STAIRS = registerStairBlock("cobbled_serpentinite_stairs", BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_STAIRS), new Item.Properties());

    public static final DeferredBlock<Block> COBBLED_SERPENTINITE_SLAB = register("cobbled_serpentinite_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_SLAB));

    public static final DeferredBlock<Block> COBBLED_SERPENTINITE_WALL = register("cobbled_serpentinite_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICK_WALL));

    public static final DeferredBlock<Block> STRIPPED_MESQUITE_LOG = register("stripped_mesquite_log", RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG), new Item.Properties());

    public static final DeferredBlock<Block> MESQUITE_LOG = register("mesquite_log", StrippableModLogBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG), new Item.Properties());

    public static final DeferredBlock<Block> MESQUITE_PLANKS = register("mesquite_planks", BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));

    public static final DeferredBlock<Block> MESQUITE_STAIRS = registerStairBlock("mesquite_stairs", BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS), new Item.Properties());

    public static final DeferredBlock<Block> MESQUITE_SLAB = register("mesquite_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB), new Item.Properties());

    public static final DeferredBlock<Block> MESQUITE_FENCE = register("mesquite_fence", FenceBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE), new Item.Properties());

    public static final DeferredBlock<Block> MESQUITE_FENCE_GATE = registerFenceGateBlock("mesquite_fence_gate", BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE), new Item.Properties());

    public static final DeferredBlock<Block> MESQUITE_LEAVES = register("mesquite_leaves", LeavesBlock::new, leavesProperties(SoundType.AZALEA_LEAVES), new Item.Properties());

    public static final DeferredBlock<Block> MESQUITE_SAPLING = registerSaplingBlock("mesquite_sapling", BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING), new Item.Properties());

    public static final DeferredBlock<Block> POTTED_MESQUITE_SAPLING = registerPottedSaplingBlock("potted_mesquite_sapling", flowerPotProperties(), new Item.Properties());

    public static final DeferredBlock<Block> SNAKE_STATUE = register("snake_statue", TallStatueBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).noOcclusion(), new Item.Properties().rarity(Rarity.RARE));

    public static final DeferredBlock<Block> MESQUITE_BRAZIER = register("mesquite_brazier", BrazierBlock::new,  BlockBehaviour.Properties.of().mapColor(MapColor.PODZOL).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).lightLevel(state -> 15).noOcclusion().ignitedByLava());

    private static DeferredBlock<Block> register(String name, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties properties, Item.Properties itemProperties) {
        DeferredBlock<Block> toBeRegistered =  BLOCKS.register(name, registryName -> factory.apply(properties.setId(ResourceKey.create(Registries.BLOCK, registryName))));
        ModItems.ITEMS.registerSimpleBlockItem(toBeRegistered, itemProperties);
        return toBeRegistered;
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

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);

    }

    private static ToIntFunction<BlockState> litBlockEmission(int lightValue) {
        return properties -> properties.getValue(BlockStateProperties.LIT) ? lightValue : 0;
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