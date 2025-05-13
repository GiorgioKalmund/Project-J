package com.mgmstudios.projectj.item.custom.socket;

import com.mgmstudios.projectj.component.ModDataComponents;
import com.mgmstudios.projectj.util.Socket;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;

import static com.mgmstudios.projectj.component.ModDataComponents.Sockets.*;
import static net.minecraft.core.component.DataComponents.GLIDER;

public class SocketGemItem extends Item {

    private final Socket socket;
    public SocketGemItem(Properties properties, Socket socket) {
        super(properties.component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true));
        this.socket = socket;
    }

    public DataComponentType<?> getSocketDataComponent(){
        return socket.getDataComponentType();
    }

    public int getSocketCount(){
        return socket.getCount();
    }

    public Socket getSocket(){
        return socket;
    }
}
