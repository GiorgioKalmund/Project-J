package com.mgmstudios.projectj.util;

import com.mgmstudios.projectj.component.ModDataComponents;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;

public class SocketComponents {
    public static MutableComponent emptySocket(DataComponentType<?> componentType){
        return bulletPointEmpty().withColor(0xAAAAAA).append(socketComponent(dataComponentName(componentType)).withColor(0xAAAAAA));
    }

    public static <T> MutableComponent emptySocket(DeferredHolder<DataComponentType<?>, DataComponentType<T>> componentType){
        return emptySocket(componentType.get());
    }

    public static MutableComponent emptySocket(){
        return emptySocket(ModDataComponents.Sockets.EMPTY);
    }

    public static MutableComponent socketFor(Socket socket){
        if (socket.getCount() > 1){
            return Component.literal(socket.getCount() + "x ").append(socketFor(socket.getDataComponentType()));
        } else {
            return socket.isEmpty() ? emptySocket() : socketFor(socket.getDataComponentType());
        }
    }
    public static MutableComponent socketFor(DataComponentType<?> componentType){
        return bulletPoint().append(socketComponent(dataComponentName(componentType)));
    }
    public static <T> MutableComponent socketFor(DeferredHolder<DataComponentType<?>, DataComponentType<T>> componentType){
        return socketFor(componentType.get());
    }

    public static String dataComponentName(DataComponentType<?> componentType){
        ResourceLocation resourceLocation = BuiltInRegistries.DATA_COMPONENT_TYPE.getKey(componentType);
        String name = "§4ERROR GETTING COMPONENT§r";
        if (resourceLocation != null)
            name = resourceLocation.getPath();
        return name;
    }

    public static <T> String dataComponentName(DeferredHolder<DataComponentType<?>, DataComponentType<T>> componentType){
        return dataComponentName(componentType.get());
    }

    public static MutableComponent bulletPoint(int color){
        return Component.literal("• ").copy().withColor(color);
    }

    public static MutableComponent bulletPoint(){
        return bulletPoint(0xAAAAAA);
    }

    public static MutableComponent bulletPointEmpty(){
        return Component.literal("• §l⛶§r ").copy();
    }

    private static MutableComponent baseSocketComponent(){
        return Component.translatable("components.projectj.sockets.").copy();
    }

    private static MutableComponent socketComponent(String componentName){
        return Component.translatable(baseSocketComponent().append(componentName).getString());
    }
}
