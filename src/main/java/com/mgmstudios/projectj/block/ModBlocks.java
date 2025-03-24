package com.mgmstudios.projectj.block;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.block.custom.AdobeChimneyBlock;
import com.mgmstudios.projectj.block.custom.AdobeFurnaceBlock;
import com.mgmstudios.projectj.block.custom.OlmecHeadBlock;
import com.mgmstudios.projectj.item.ModItems;
import net.minecraft.core.Holder;
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
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.ToIntFunction;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(ProjectJ.MOD_ID);

    public static final DeferredBlock<Block> RAW_ADOBE = registerBlock("raw_adobe",
            BlockBehaviour.Properties.of().mapColor(MapColor.CLAY).instrument(NoteBlockInstrument.FLUTE).strength(0.6F).sound(SoundType.GRAVEL)
    );

    public static final DeferredBlock<Block> ADOBE_BRICKS = registerBlock("adobe_bricks",
             BlockBehaviour.Properties.of()
            .mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .requiresCorrectToolForDrops()
            .strength(1.5F, 3.0F)
            .sound(SoundType.MUD_BRICKS)
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

    public static final DeferredBlock<Block> BASIC_OLMEC_HEAD = registerOlmecHeadBlock("basic_olmec_head",
            ParticleTypes.HAPPY_VILLAGER,
            MobEffects.MOVEMENT_SPEED,
            10
    );

    public static final DeferredBlock<Block> RESISTANT_OLMEC_HEAD = registerOlmecHeadBlock("resistant_olmec_head",
            ParticleTypes.ELECTRIC_SPARK,
            MobEffects.DAMAGE_BOOST,
            10
    );

    public static final DeferredBlock<Block> CONDUIT_OLMEC_HEAD = registerOlmecHeadBlock("conduit_olmec_head",
            ParticleTypes.BUBBLE,
            MobEffects.CONDUIT_POWER,
            10
    );


    private static ToIntFunction<BlockState> litBlockEmission(int lightValue) {
        return properties -> properties.getValue(BlockStateProperties.LIT) ? lightValue : 0;
    }

    private static DeferredBlock<Block> registerBlock(String name, BlockBehaviour.Properties properties, Item.Properties itemProperties) {
        DeferredBlock<Block> toBeRegistered =  BLOCKS.register(name, registryName -> new Block(properties.setId(ResourceKey.create(Registries.BLOCK, registryName))));
        ModItems.ITEMS.registerSimpleBlockItem(toBeRegistered, itemProperties);
        return toBeRegistered;
    }


    private static DeferredBlock<Block> registerBlock(String name, BlockBehaviour.Properties properties) {
        return registerBlock(name, properties, new Item.Properties());
    }

    private static DeferredBlock<Block> registerOlmecHeadBlock(String name, BlockBehaviour.Properties properties, Item.Properties itemProperties, ParticleOptions particleOptions, Holder<MobEffect> effect, int effectTime) {
        DeferredBlock<Block> toBeRegistered =  BLOCKS.register(name, registryName ->
                new OlmecHeadBlock(
                        properties.setId(ResourceKey.create(Registries.BLOCK, registryName)),
                        particleOptions,
                        effect,
                        effectTime
                ));
        ModItems.ITEMS.registerSimpleBlockItem(toBeRegistered, itemProperties);
        return toBeRegistered;
    }

    private static DeferredBlock<Block> registerOlmecHeadBlock(String name, ParticleOptions particleOptions, Holder<MobEffect> effect, int effectTime) {
        BlockBehaviour.Properties properties =
                BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0F, 3.0F).sound(SoundType.GILDED_BLACKSTONE).lightLevel(litBlockEmission(15)).noOcclusion();
        Item.Properties itemProperties = new Item.Properties().rarity(Rarity.EPIC).equippableUnswappable(EquipmentSlot.HEAD);
        return registerOlmecHeadBlock(name, properties, itemProperties, particleOptions, effect, effectTime);
    }

    private static DeferredBlock<Block> registerOlmecHeadBlock(String name, BlockBehaviour.Properties properties, ParticleOptions particleOptions, Holder<MobEffect> effect, int effectTime) {
        return registerOlmecHeadBlock(name, properties, new Item.Properties(), particleOptions, effect, effectTime);
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

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}