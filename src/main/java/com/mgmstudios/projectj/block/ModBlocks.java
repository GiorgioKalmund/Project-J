package com.mgmstudios.projectj.block;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.block.custom.AdobeChimneyBlock;
import com.mgmstudios.projectj.block.custom.AdobeFurnaceBlock;
import com.mgmstudios.projectj.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
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


    public static final DeferredBlock<Block> JADE_ORE = registerBlock("jade_ore",
            BlockBehaviour.Properties.of().
                    mapColor(MapColor.STONE).
                    instrument(NoteBlockInstrument.BASEDRUM).
                    requiresCorrectToolForDrops().
                    strength(3.0F, 3.0F)
    );

    public static final DeferredBlock<Block> ADOBE_FURNACE = registerFurnaceBlock("adobe_furnace",
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.STONE)
                .instrument(NoteBlockInstrument.BASEDRUM)
                .requiresCorrectToolForDrops()
                .strength(3.5F)
                    .lightLevel(litBlockEmission(7))
    );

    public static final DeferredBlock<Block> CHIMNEY = registerChimneyBlock("chimney",
            BlockBehaviour.Properties.of()
                    .noOcclusion()
    );

    private static ToIntFunction<BlockState> litBlockEmission(int lightValue) {
        return p_50763_ -> p_50763_.getValue(BlockStateProperties.LIT) ? lightValue : 0;
    }

    private static DeferredBlock<Block> registerBlock(String name, BlockBehaviour.Properties properties) {
        DeferredBlock<Block> toBeRegistered =  BLOCKS.register(name, registryName -> new Block(properties.setId(ResourceKey.create(Registries.BLOCK, registryName))));
        ModItems.ITEMS.registerSimpleBlockItem(toBeRegistered);
        return toBeRegistered;
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