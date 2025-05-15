package com.mgmstudios.projectj.util;

import com.mgmstudios.projectj.component.ModDataComponents;
import net.minecraft.client.gui.screens.Screen;
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
        MutableComponent base;
        if (socket.getCount() > 1){
            base = bulletPoint().append(Component.literal("§f"+socket.getCount() + "x §r").append(socketFor(socket.getDataComponentType())));
        } else {
            base = socket.isEmpty() ? emptySocket() : bulletPoint().append(socketFor(socket.getDataComponentType()));
        }
        if (!socket.isEmpty()){
            String maxCount = socket.getMaxCount() == Integer.MAX_VALUE ? "∞" : socket.getMaxCount() + "";
            base.append(" §8§o["+ maxCount +"]§r");
        }
        if (socket.isAdditive()){
            base.append(" §e✦§r");
        }
        return base;
    }

    public static MutableComponent socketDescriptionFor(Socket socket){
        return socketComponentDescription(dataComponentName(socket.getDataComponentType())).withColor(0x555555);
    }


    public static MutableComponent socketFor(DataComponentType<?> componentType){
        return socketComponent(dataComponentName(componentType));
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
        return Component.literal("• §r").copy().withColor(color);
    }

    public static MutableComponent bulletPoint(){
        return bulletPoint(0xAAAAAA);
    }

    public static MutableComponent bulletPointEmpty(int color){
        return Component.literal("• §l⛶§r ").copy().withColor(color);
    }
    public static MutableComponent bulletPointEmpty(){
        return bulletPointEmpty(0xAAAAAA);
    }

    private static MutableComponent baseSocketComponent(){
        return Component.translatable("components.projectj.sockets.").copy();
    }

    private static MutableComponent socketComponent(String componentName){
        return Component.translatable(baseSocketComponent().append(componentName).getString());
    }

    private static MutableComponent socketComponentDescription(String componentName){
        return Component.translatable("components.projectj.sockets." + componentName + ".description").copy();
    }

}
