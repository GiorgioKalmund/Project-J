package com.mgmstudios.projectj.item.custom;

import com.mgmstudios.projectj.util.SocketComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ChimalliShieldItem extends ShieldItem {

    public ChimalliShieldItem(Properties properties) {
        super(properties);
    }

     @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(SocketComponents.emptySocket());
    }
}
