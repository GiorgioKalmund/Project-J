package com.mgmstudios.projectj.util;

import net.minecraft.network.chat.Component;

public class SocketComponents {

    public static Component emptySocket(){
        return Component.literal("• §l⛶§r ").append(Component.translatable("components.projectj.sockets.empty")).withColor(0xAAAAAA);
    }
}
