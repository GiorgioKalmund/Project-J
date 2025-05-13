package com.mgmstudios.projectj.item.custom.socket;

import com.mgmstudios.projectj.util.Socket;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import software.bernie.geckolib.animatable.GeoItem;

import java.util.List;

import static com.mgmstudios.projectj.item.custom.socket.SocketItem.applySockets;
import static com.mgmstudios.projectj.item.custom.socket.SocketItem.socketHoverText;

public abstract class SocketGeoArmorItem extends ArmorItem implements GeoItem, SocketHolder {
    public SocketGeoArmorItem(ArmorMaterial material, ArmorType armorType, Properties properties, Socket... sockets) {
        super(material, armorType, applySockets(properties, sockets));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        socketHoverText(stack, tooltipComponents);
    }

    @Override
    public List<DataComponentType<?>> getAllowedTypes() {
        return List.of();
    }

    @Override
    public List<DataComponentType<?>> getExcludedTypes() {
        return List.of();
    }
}
