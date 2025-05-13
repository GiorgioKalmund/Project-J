package com.mgmstudios.projectj.item.custom.socket;

import com.mgmstudios.projectj.util.Socket;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;

import static com.mgmstudios.projectj.item.custom.socket.SocketItem.*;
import static com.mgmstudios.projectj.util.SocketComponents.socketFor;

public class SocketShieldItem extends ShieldItem implements SocketHolder{

    public SocketShieldItem(Properties properties, Socket... sockets) {
        super(applySockets(properties, sockets));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, tooltipContext, tooltipComponents, tooltipFlag);
        socketHoverText(stack, tooltipComponents);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        applyInventoryTickEffects(stack, this, level, entity);
    }

    @Override
    public List<DataComponentType<?>> getAllowedTypes() {
        return List.of();
    }

    @Override
    public List<DataComponentType<?>> getExcludedTypes() {
        return List.of();
    }

    @Override
    public boolean canAddExtraSlots() {
        return false;
    }
}
