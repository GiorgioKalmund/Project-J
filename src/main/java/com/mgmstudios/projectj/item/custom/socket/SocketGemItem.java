package com.mgmstudios.projectj.item.custom.socket;

import com.mgmstudios.projectj.util.Socket;
import com.mgmstudios.projectj.util.SocketComponents;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.Arrays;
import java.util.List;

public class SocketGemItem extends Item {

    private final List<Socket> socketList;

    public SocketGemItem(Properties properties, List<Socket> socket) {
        super(properties.component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true));
        this.socketList = socket;
    }

    public SocketGemItem(Properties properties, Socket ... sockets) {
        this(properties, List.of(sockets));
    }

    public DataComponentType<?> getSocketDataComponent(){
        return socketList.getFirst().getDataComponentType();
    }

    public Socket getSocket(int index){
        return socketList.get(index);
    }

    public List<Socket> getSocketList(){
        return socketList;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.translatable("components.projectj.sockets.gem_applying"));
        for (Socket s : socketList){
            tooltipComponents.add(SocketComponents.socketFor(s));
            if (Screen.hasShiftDown())
                tooltipComponents.add(SocketComponents.socketDescriptionFor(s));
        }
    }
}
