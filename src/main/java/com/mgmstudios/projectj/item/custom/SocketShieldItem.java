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

import static com.mgmstudios.projectj.util.SocketComponents.socketFor;

public class SocketShieldItem extends SocketItem {

    public SocketShieldItem(Properties properties, Socket... sockets) {
        super(properties, sockets);
    }

    public Component getName(ItemStack p_371901_) {
        DyeColor dyecolor = (DyeColor)p_371901_.get(DataComponents.BASE_COLOR);
        Object var3;
        if (dyecolor != null) {
            String var10000 = this.descriptionId;
            var3 = Component.translatable(var10000 + "." + dyecolor.getName());
        } else {
            var3 = super.getName(p_371901_);
        }

        return (Component)var3;
    }

    public void appendHoverText(ItemStack itemStack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        BannerItem.appendHoverTextFromBannerBlockEntityTag(itemStack, tooltipComponents);
        super.appendHoverText(itemStack, context, tooltipComponents, tooltipFlag);
    }

    public ItemUseAnimation getUseAnimation(ItemStack p_43105_) {
        return ItemUseAnimation.BLOCK;
    }

    public int getUseDuration(ItemStack p_43107_, LivingEntity p_346168_) {
        return 72000;
    }

    public InteractionResult use(Level level, Player player, InteractionHand interactionHand) {
        player.startUsingItem(interactionHand);
        super.use(level, player, interactionHand);
        return InteractionResult.CONSUME;
    }

    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        return ItemAbilities.DEFAULT_SHIELD_ACTIONS.contains(itemAbility);
    }

}
