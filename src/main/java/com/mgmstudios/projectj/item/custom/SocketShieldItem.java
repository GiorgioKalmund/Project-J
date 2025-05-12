package com.mgmstudios.projectj.item.custom;

import com.mgmstudios.projectj.util.Socket;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.List;

import static com.mgmstudios.projectj.component.ModDataComponents.Sockets.SOCKETS;
import static com.mgmstudios.projectj.item.custom.SocketItem.applySockets;
import static com.mgmstudios.projectj.item.custom.SocketItem.socketHoverText;
import static com.mgmstudios.projectj.util.SocketComponents.socketFor;

public class SocketShieldItem extends ShieldItem {

    public SocketShieldItem(Properties properties, Socket... sockets) {
        super(applySockets(properties, sockets));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, tooltipContext, tooltipComponents, tooltipFlag);
        socketHoverText(stack, tooltipComponents);
    }
}
