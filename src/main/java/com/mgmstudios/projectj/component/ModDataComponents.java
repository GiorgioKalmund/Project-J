package com.mgmstudios.projectj.component;

import com.mgmstudios.projectj.ProjectJ;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, ProjectJ.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemContainerContents>> MAIN_INVENTORY = register("main_inventory", builder ->
            builder.persistent(ItemContainerContents.CODEC)
                    .networkSynchronized(ItemContainerContents.STREAM_CODEC)
            );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemContainerContents>> SIDE_INVENTORY = register("side_inventory", builder ->
                    builder
                            .persistent(ItemContainerContents.CODEC)
                            .networkSynchronized(ItemContainerContents.STREAM_CODEC)
            );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SimpleFluidContent>> FLUID_INVENTORY = register("fluid_inventory", builder ->
            builder
                    .persistent(SimpleFluidContent.CODEC)
                    .networkSynchronized(SimpleFluidContent.STREAM_CODEC)
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> MAGNET_ACTIVE = register("magnet_active", builder ->
            builder
                    .persistent(Codec.BOOL)
                    .networkSynchronized(ByteBufCodecs.BOOL)
    );

    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator){
        return DATA_COMPONENT_TYPES.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void register(IEventBus eventBus){
        DATA_COMPONENT_TYPES.register(eventBus);;
    }

}
