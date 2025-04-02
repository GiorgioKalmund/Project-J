package com.mgmstudios.projectj.block.entity;

import java.util.function.Supplier;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.block.ModBlocks;

import com.mgmstudios.projectj.block.entity.custom.AdobeFurnaceBlockEntity;
import com.mgmstudios.projectj.block.entity.custom.TeleportationBlockEntity;
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

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
