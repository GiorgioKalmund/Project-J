package com.mgmstudios.projectj.block.entity;

import java.util.function.Supplier;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.block.entity.custom.*;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = 
        DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, ProjectJ.MOD_ID);
    
    public static final Supplier<BlockEntityType<AdobeFurnaceBlockEntity>> ADOBE_FURNACE_BE=
        BLOCK_ENTITIES.register("adobe_furnace",() -> new BlockEntityType<>(AdobeFurnaceBlockEntity::new,ModBlocks.ADOBE_FURNACE.get()));

    public static final Supplier<BlockEntityType<TeleportationBlockEntity>> TELEPORTATION_PAD_BE =
            BLOCK_ENTITIES.register("teleportation_pad",() -> new BlockEntityType<>(TeleportationBlockEntity::new, ModBlocks.TELEPORTATION_PAD.get()));

    public static final Supplier<BlockEntityType<AncientAltarBlockEntity>> ANCIENT_ALTAR_BE =
            BLOCK_ENTITIES.register("ancient_altar",() -> new BlockEntityType<>(AncientAltarBlockEntity::new, ModBlocks.ANCIENT_ALTAR.get()));

    public static final Supplier<BlockEntityType<MetateBlockEntity>> METATE_BE =
            BLOCK_ENTITIES.register("metate",() -> new BlockEntityType<>(MetateBlockEntity::new, ModBlocks.METATE.get()));

    public static final Supplier<BlockEntityType<JadeCrystalBlockEntity>> JADE_CRYSTAL_BE =
            BLOCK_ENTITIES.register("jade_crystal",() -> new BlockEntityType<>(JadeCrystalBlockEntity::new, ModBlocks.JADE_CRYSTAL.get()));

    public static final Supplier<BlockEntityType<SocketWorkbenchBlockEntity>> SOCKET_WORKBENCH_BE =
            BLOCK_ENTITIES.register("socket_workbench", () -> new BlockEntityType<>(SocketWorkbenchBlockEntity::new, ModBlocks.SOCKET_WORKBENCH.get()));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
