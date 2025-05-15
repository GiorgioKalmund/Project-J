package com.mgmstudios.projectj.component;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.util.Socket;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Unit;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
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

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> AWAKENED_ARMOR_CHARGED = register("awakened_armor_charge", builder ->
            builder
                    .persistent(Codec.BOOL)
                    .networkSynchronized(ByteBufCodecs.BOOL)
    );

    public static class Sockets {

        public static final DeferredRegister<DataComponentType<?>> SOCKET_DATA_COMPONENT_TYPES = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, ProjectJ.MOD_ID);

        public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> EMPTY = registerSocket("empty", builder ->
                builder
                        .persistent(Unit.CODEC)
                        .networkSynchronized(StreamCodec.unit(Unit.INSTANCE))
        );
        public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> ZOMBIE_PACIFYING = registerSocket("zombie_pacifying", builder ->
                builder
                        .persistent(Codec.BOOL)
                        .networkSynchronized(ByteBufCodecs.BOOL)
        );
        public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> REMOVE_AI = registerSocket("remove_ai", builder ->
                builder
                        .persistent(Codec.BOOL)
                        .networkSynchronized(ByteBufCodecs.BOOL)
        );
        public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> GIVE_AI = registerSocket("give_ai", builder ->
                builder
                        .persistent(Codec.BOOL)
                        .networkSynchronized(ByteBufCodecs.BOOL)
        );
        public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> LEGENDARY_STATUS = registerSocket("legendary_status", builder ->
                builder
                        .persistent(Codec.BOOL)
                        .networkSynchronized(ByteBufCodecs.BOOL)
        );
        public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<com.mgmstudios.projectj.util.Socket>>> SOCKETS = registerSocket("sockets", builder ->
                builder
                        .persistent(com.mgmstudios.projectj.util.Socket.SOCKET_LIST_CODEC)
                        .networkSynchronized(com.mgmstudios.projectj.util.Socket.SOCKET_LIST_STREAM_CODEC)
        );

        public static final DeferredHolder<DataComponentType<?>, DataComponentType<com.mgmstudios.projectj.util.Socket>> SOCKET = registerSocket("socket", builder ->
                builder
                        .persistent(Socket.SOCKET_CODEC)
                        .networkSynchronized(Socket.SOCKET_STREAM_CODEC)
        );
    }


    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator){
        return DATA_COMPONENT_TYPES.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }
    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> registerSocket(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator){
        return Sockets.SOCKET_DATA_COMPONENT_TYPES.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void register(IEventBus eventBus){
        DATA_COMPONENT_TYPES.register(eventBus);
        Sockets.SOCKET_DATA_COMPONENT_TYPES.register(eventBus);
    }

}
